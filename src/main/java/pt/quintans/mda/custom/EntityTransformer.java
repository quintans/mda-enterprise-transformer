package pt.quintans.mda.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import pt.quintans.mda.core.AbstractTransformer;
import pt.quintans.mda.core.ElementType;
import pt.quintans.mda.core.Event;
import pt.quintans.mda.core.EventMediator;
import pt.quintans.mda.core.MRoot;
import pt.quintans.mda.core.Model;
import pt.quintans.mda.core.ModelElement;
import pt.quintans.mda.core.ModelEventListener;
import pt.quintans.mda.core.Tools;
import pt.quintans.mda.core.WorkerStore;
import pt.quintans.mda.model.Reference;
import pt.quintans.mda.model.dto.DataTranferObject;
import pt.quintans.mda.model.entity.Association;
import pt.quintans.mda.model.entity.Attribute;
import pt.quintans.mda.model.entity.Bool;
import pt.quintans.mda.model.entity.Constraint;
import pt.quintans.mda.model.entity.DateComposite;
import pt.quintans.mda.model.entity.Element;
import pt.quintans.mda.model.entity.FlagBool;
import pt.quintans.mda.model.entity.FlagLov;
import pt.quintans.mda.model.entity.Flags;
import pt.quintans.mda.model.entity.Index;
import pt.quintans.mda.model.entity.Operation;
import pt.quintans.mda.model.entity.Relation;
import pt.quintans.mda.model.entity.UniqueGroup;
import pt.quintans.mda.model.lov.Item;
import pt.quintans.mda.model.lov.ListOfValues;
import pt.quintans.mda.model.service.Role;
import pt.quintans.mda.raw.domain.BaseAttributeType;
import pt.quintans.mda.raw.domain.Basic;
import pt.quintans.mda.raw.domain.ConstraintType;
import pt.quintans.mda.raw.domain.DaoOperationType;
import pt.quintans.mda.raw.domain.EntBoolean;
import pt.quintans.mda.raw.domain.EntDateComposite;
import pt.quintans.mda.raw.domain.EntFlags;
import pt.quintans.mda.raw.domain.EntLov;
import pt.quintans.mda.raw.domain.EntNested;
import pt.quintans.mda.raw.domain.Entity;
import pt.quintans.mda.raw.domain.FlagsBool;
import pt.quintans.mda.raw.domain.FlagsLov;
import pt.quintans.mda.raw.domain.HasManyType;
import pt.quintans.mda.raw.domain.IndexMemberType;
import pt.quintans.mda.raw.domain.IndexType;
import pt.quintans.mda.raw.domain.OpBaseAttributeType;
import pt.quintans.mda.raw.domain.OpCustom;
import pt.quintans.mda.raw.domain.OpModel;
import pt.quintans.mda.raw.domain.ReferenceType;
import pt.quintans.mda.raw.domain.RelationType;
import pt.quintans.mda.raw.domain.SimpleAssociationType;
import pt.quintans.mda.raw.domain.UniqueGroupMemberType;
import pt.quintans.mda.raw.domain.UniqueGroupType;

public class EntityTransformer extends AbstractTransformer {

	private Attribute defineAttribute(Attribute at, BaseAttributeType attr) {
		at.setId(attr.getId());
		at.setName(attr.getName());
		at.setAlias(Tools.isEmpty(attr.getAlias()) ? attr.getName() : attr.getAlias());
		at.setNullable(attr.getNullable());
		at.setType(attr.getElementType());
		if (attr.getLength() != null)
			at.setLength(Integer.parseInt(attr.getLength()));
		at.setDefaultValue(attr.getDefault());
		at.setComments(attr.getComments());
		at.setDeleter(attr.getDeleter());
		
		return at;
	}

	private void defineKey(pt.quintans.mda.model.entity.Entity ent, Attribute at, BaseAttributeType attr) {
		List<Attribute> keys = ent.getKeys();
		if (attr.getKey()) {
			at.setKey(attr.getKey());
			keys.add(at);
			if (attr.getUnique() && !WorkerStore.get().isQuiet())
				System.out.println(String.format(
						"==> A configuracao Unique em %s.%s sera ignorada porque esta definida como key", ent.getName(),
						attr.getName()));
		} else {
			at.setUnique(attr.getUnique());
		}
	}

	public pt.quintans.mda.model.entity.Entity instanciate() {
		return new pt.quintans.mda.model.entity.Entity();
	}
	
	@Override
	public void create(final Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

		if (me.getTransformed() != null)
			return;

		if (!WorkerStore.get().isQuiet())
			System.out.println(String.format("Transforming <<%s>> %s", stereotype, objectName));

		Basic basic = me.getSource();
		Entity modelEntity = (Entity) basic;

		final pt.quintans.mda.model.entity.Entity ent = instanciate();
		me.setTransformed(ent);
		ent.setId(modelEntity.getId());
		ent.setReadOnly(modelEntity.getReadonly());
        ent.setNested(modelEntity.getNested());
		ent.setName(objectName);
		ent.setAlias(modelEntity.getAlias() == null ? modelEntity.getName() : modelEntity.getAlias());
		ent.setNamespace(basic.getNamespace());
		ent.setLocation(basic.getLocation());
		ent.setStereotype(getStereotype());
		ent.setStereotypeAlias(getStereotypeAlias());
		ent.setBehaviors(basic.getBehaviors());
		ent.setRoot(basic.getRoot());
		ent.setComments(basic.getComments());

		// attributes
		if (modelEntity.getAttributes() != null) {
			List<Attribute> attrs = ent.getAttributes();
			List<Attribute> keys = new ArrayList<Attribute>();
			ent.setKeys(keys);
			for (BaseAttributeType attr : modelEntity.getAttributes().getAttributesList()) {
				Attribute at = null;
				// System.out.println("======> " + ent.getName() + "." + attr.getName() + ": " + attr.getElementType());
				if ("identity".equals(attr.getElementType())) {
					at = defineAttribute(new Attribute(ent), attr);
					at.setKey(true);
					ent.setIdentity(at);
					keys.add(at);
				} else if ("version".equals(attr.getElementType())) {
					at = defineAttribute(new Attribute(ent), attr);
					ent.setVersion(at);
				} else {

					if ("boolean".equals(attr.getElementType())) {
						EntBoolean entBool = (EntBoolean) attr;
						Bool bool = new Bool(ent);
						at = defineAttribute(bool, attr);
						if (entBool.getFormat() != null)
							bool.setFormat(entBool.getFormat().value());
						bool.setTrueValue(entBool.getTrueValue() != null ? entBool.getTrueValue() : "1");
						bool.setFalseValue(entBool.getFalseValue() != null ? entBool.getFalseValue() : "0");

						defineKey(ent, at, attr);
						attrs.add(at);
					} else if ("flags".equals(attr.getElementType())) {
						EntFlags entFlags = (EntFlags) attr;
						Flags flags = new Flags(ent);
						at = defineAttribute(flags, attr);

						// carrega lovs
						for (BaseAttributeType bat : entFlags.getFlagList()) {
							if (bat instanceof FlagsBool) {
								FlagsBool fsb = (FlagsBool) bat;
								FlagBool fb = new FlagBool();
								fb.setName(fsb.getName());
								fb.setPosition(fsb.getPosition());
								fb.setTrueValue(fsb.getTrueValue());
								fb.setFalseValue(fsb.getFalseValue());
								if (fsb.getDefault() != null)
									fb.setDefaultValue(fsb.getDefault());

								flags.getBools().add(fb);

							} else if (bat instanceof FlagsLov) {
								final FlagsLov fsl = (FlagsLov) bat;
								final FlagLov fl = new FlagLov();
								fl.setName(fsl.getName());
								fl.setPosition(fsl.getPosition());
								if (fsl.getDefault() != null)
									fl.setDefaultValue(fsl.getDefault());

								// o target é adiccionado quando estiver tudo configurado
								EventMediator.addListener(EventMediator.EVT_MODEL_CONFIGURED, new ModelEventListener() {

									@Override
									public void onEvent(Event event) {
										pt.quintans.mda.model.lov.ListOfValues lov = (pt.quintans.mda.model.lov.ListOfValues) model
												.getTransformedObject(fsl.getTarget());
										fl.setTarget(lov);
										ent.addDependency(lov);
									}

								});

								flags.getLovs().add(fl);

							}
						}

						attrs.add(at);
					} else if ("dateComposite".equals(attr.getElementType())) {
						EntDateComposite entDateComposite = (EntDateComposite) attr;
						DateComposite dateComposite = new DateComposite(ent);
						at = defineAttribute(dateComposite, attr);
						at.setComposite(true);
						ent.getAllAttributes().add(at); // adiccionado numa lista à parte

						if (entDateComposite.getDay() != null) {
							Attribute atx = defineAttribute(new Attribute(ent), entDateComposite.getDay());
							dateComposite.setDay(atx);
							atx.setName(at.getName() + Tools.capitalizeFirst(atx.getName()));
							attrs.add(dateComposite.getDay());
							defineKey(ent, dateComposite.getDay(), entDateComposite.getDay());
						}

						if (entDateComposite.getMonth() != null) {
							Attribute atx = defineAttribute(new Attribute(ent), entDateComposite.getMonth());
							dateComposite.setMonth(atx);
							atx.setName(at.getName() + Tools.capitalizeFirst(atx.getName()));
							attrs.add(dateComposite.getMonth());
							defineKey(ent, dateComposite.getMonth(), entDateComposite.getMonth());
						}

						if (entDateComposite.getYear() != null) {
							Attribute atx = defineAttribute(new Attribute(ent), entDateComposite.getYear());
							dateComposite.setYear(atx);
							atx.setName(at.getName() + Tools.capitalizeFirst(atx.getName()));
							attrs.add(dateComposite.getYear());
							defineKey(ent, dateComposite.getYear(), entDateComposite.getYear());
						}
                    } else if ("nested".equals(attr.getElementType())) {
                        // skip
					} else {
						at = defineAttribute(new Attribute(ent), attr);
						defineKey(ent, at, attr);
						attrs.add(at);
					}
					
					if(at != null){
						if(attr.getDeleter())
							ent.setDeleter(at);
						
						at.setBehaviors(attr.getBehaviors());
					}

					List<ConstraintType> contraintsType = attr.getConstraint();
					if(contraintsType != null && contraintsType.size() > 0) {
                        List<Constraint> constraints = new ArrayList<Constraint>();
                        at.setConstraints(constraints);
                        for(ConstraintType ct : contraintsType) {
                            Constraint c = new Constraint();
                            constraints.add(c);                            
                            c.setTarget(at);
        
                            c.setParameter(ct.getParameter());
                            Object value = null;
                            if(!c.isParameter()) {
                                ListOfValues lov = c.getTarget().getLov();
                                if (lov != null) {
                                    for (Item item : lov.getItems()) {
                                        if (item.getName().equals(ct.getValue())) {
                                            value = item;
                                        }
                                    }
                                    if (value == null) {
                                        throw new RuntimeException("The attribute \"value\" declared in " + at.toString() +
                                                " must be one of values of the LOV " + lov.getName());
                                    }
                                }
                            }
                            if(value == null){
                                value = ct.getValue();
                            }
                            c.setValue(value);
                        }
					}
				}
				
			}
			ent.getAllAttributes().addAll(ent.getAttributes());
		}
	}

	/**
	 * Este procedimento é evocado uma vez por cada elemento do modelo
	 */
	@Override
	public void relate(Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

		if (!WorkerStore.get().isQuiet())
			System.out.println(String.format("Relating <<%s>> %s", stereotype, objectName));

		Basic basic = me.getSource();
		Entity modelEntity = (Entity) basic;
		pt.quintans.mda.model.entity.Entity ent = (pt.quintans.mda.model.entity.Entity) me.getTransformed();

		// operations
		if (modelEntity.getDao() != null) {
			List<Operation> operations = new ArrayList<Operation>();
			ent.setOperations(operations);
			List<DaoOperationType> baseTypes = modelEntity.getDao().getDaoList();
			for (DaoOperationType baseType : baseTypes) {
				if (baseType instanceof DaoOperationType) {
					DaoOperationType oper = (DaoOperationType) baseType;

					Operation operation = new Operation();
					operations.add(operation);
					operation.setName(oper.getName());
					operation.setAlias(oper.getAlias() == null ? oper.getName() : oper.getAlias());
					operation.setType(oper.getType());
					if (model.hasObject(oper.getType())) {
						ent.addDependency((Element) model.getTransformedObject(oper.getType()));
						operation.setModeledType(true);
					} else if (oper.getType() != null && oper.getType().indexOf(".") > 0)
						operation.setCustomType(true);
					operation.setSingle(oper.getSingle());
					operation.setPaginate(oper.getPaginate());
					// exposed to roles
					operation.setRoles(buildRoles(model, modelEntity, oper.getRoles()));
					operation.setBehaviors(oper.getBehaviors());
					operation.setComments(oper.getComments());
					operation.setTransactional(oper.getTransactional());

					// attributes
					if (oper.getAttributesList() != null) {
						List<Attribute> attrs = new ArrayList<Attribute>();
						operation.setAttributes(attrs);
						for (OpBaseAttributeType attr : oper.getAttributesList()) {
							Attribute at = new Attribute(ent);
							attrs.add(at);
							at.setName(attr.getName());
							at.setNullable(attr.getNullable());
							if (attr.getElementType().equals("model")) {
								OpModel opModel = (OpModel) attr;
								if (model.hasObject(opModel.getType())) {
									Element element = model.getTransformedObject(opModel.getType());
									ent.addDependency(element);
									at.setType(opModel.getType());
									at.setModel(element);
									if (element instanceof ListOfValues)
										at.setLov((ListOfValues) element);
								} else
									throw new RuntimeException(String.format("Element model %s was not found", opModel.getType()));
							} else if (attr.getElementType().equals("custom")) {
								OpCustom custom = (OpCustom) attr;
								at.setType(custom.getType());
								at.setCustomType(true);
							} else {
								at.setType(attr.getElementType());
							}

							at.setSingle(attr.getSingle());
							at.setComments(attr.getComments());
						}
					}
				}
			}
		}

		// associations
		if (modelEntity.getAssociations() != null) {
			List<Association> associations = ent.getAssociations();
			for (SimpleAssociationType assoc : modelEntity.getAssociations().getAssociationsList()) {
				Association association = new Association(ent);
				associations.add(association);
				association.setId(assoc.getId());
				association.setName(assoc.getName());
				pt.quintans.mda.model.entity.Entity targetEntity = (pt.quintans.mda.model.entity.Entity) model.getTransformedObject(assoc
						.getEntity());
				ent.addDependency(targetEntity);
				association.setTarget(targetEntity);
				association.setCascade(assoc.getCascade());
				association.setWeak(assoc.getWeak() || assoc.getCascade());
				association.setNoForeignKey(assoc.getNoForeignKey());
				boolean many = assoc instanceof HasManyType;
				if (many)
					ent.setManyAssociated(true);
				else
					ent.setSingleAssociated(true);
				association.setMany(many);
				association.setNavigate(assoc.getNavigate());
				association.setNullable(assoc.getNullable());
				association.setOwner(assoc.getOwner());
				// association.setAlias(assoc.getAlias() == null ? assoc.getName() : assoc.getAlias());
				association.setAlias(assoc.getAlias());
				association.setOtherEnd(assoc.getOtherEnd());
				association.setFetchType(assoc.getFetchType());
				association.setComments(assoc.getComments());

				// converte as relações dos campos
				if (assoc.getRelationAndConstraint().size() > 0) {
                    List<Relation> relations = null;
					List<Constraint> constraints = null;
					for (ElementType et : assoc.getRelationAndConstraint()) {
					    if(et instanceof RelationType){
					        if(relations == null) {
					            relations = new ArrayList<Relation>();
			                    association.setRelations(relations);
					        }
					        RelationType rt = (RelationType) et;
					        
    						Relation r = new Relation();
    						relations.add(r);
    
    						r.setKey(targetEntity.fetchAttribute(rt.getKey()));
    						r.setForeign(ent.fetchAttribute(rt.getForeign()));
    
    						if (r.getKey() == null)
    							throw new RuntimeException("O atributo " + targetEntity.getName() + "." + rt.getKey()
    									+ " da associação " + assoc.getName() + " não foi encontrado.");
    						if (r.getForeign() == null)
    							throw new RuntimeException("O atributo " + ent.getName() + "." + rt.getForeign() + " da associação "
    									+ assoc.getName() + " não foi encontrado.");
    					}
					    else if(et instanceof ConstraintType){
                            if(constraints == null) {
                                constraints = new ArrayList<Constraint>();
                                association.setConstraints(constraints);
                            }
                            ConstraintType ct = (ConstraintType) et;
                            
                            Constraint c = new Constraint();
                            constraints.add(c);
                            
                            // find the attribute
                            String target = ct.getTarget();
                            if(target != null){
                                pt.quintans.mda.model.entity.Entity e = null;
                                if(target.startsWith(".")){ // is local
                                    target = target.substring(1);
                                    e = ent;
                                } else {
                                    e = targetEntity;
                                }
                                for(Attribute attr : e.getAttributes()) {
                                    if(attr.getName().equals(target)) {
                                        c.setTarget(attr);
                                        break;
                                    }
                                }
                                if(c.getTarget() == null) {
                                    throw new RuntimeException("Unable to find the attribute \""+ target +
                                            "\" in the entity " + e.getName() + " refered by the association " + assoc.getName());
                                }
                            } else {
                                throw new RuntimeException("The attribute \"target\" was not found in the association " + assoc.getName());
                            }

                            c.setParameter(ct.getParameter());
                            Object value = null;
                            if(!c.isParameter()) {
                                ListOfValues lov = c.getTarget().getLov();
                                if (lov != null) {
                                    for (Item item : lov.getItems()) {
                                        if (item.getName().equals(ct.getValue())) {
                                            value = item;
                                        }
                                    }
                                    if (value == null) {
                                        throw new RuntimeException("The attribute \"value\" declared in " + assoc.toString() +
                                                " must be one of values of the LOV " + lov.getName());
                                    }
                                }
                            }
                            if(value == null){
                                value = ct.getValue();
                            }
                            c.setValue(value);
					    }
					}
				}

				// System.out.println(String.format("A adicionar o evento para a definicao da propriedade fromTarget da associacao %s.%s para o event %s.",
				// association.getEntity().getName(), association.getName(), EventMediator.EVT_MODEL_CONFIGURED));
				// posterior configuração do 'fromTarget' da associação
				EventMediator.addListener(EventMediator.EVT_MODEL_CONFIGURED, association);

				/*
				 * // procura a associaçãoo inversa int cnt = 0; // contador para o numero de associações encontradas. Association
				 * a = null; for(Association ass : targetEntity.getAssociations()){
				 * if(ent.getName().equals(ass.getTarget().getName())){ if(association.getOtherEnd() != null){
				 * if(association.getOtherEnd().equals(ass.getName())){ a = ass; break; } } else if(ass.getOtherEnd() != null){ //
				 * verifica se do outro lado existe otherEnd if(ass.getOtherEnd().equals(association.getName())){ a = ass; break;
				 * } } else { a = ass; cnt++; } } }
				 * 
				 * // foi encontrada mais do que uma possivel associação if(cnt > 1) throw new RuntimeException(String.format(
				 * "More than one possible matching association was found, in the entity \"%s\" for association \"%s\"\nUse attribute \"otherEnd\" to define the matching association"
				 * , targetEntity.getName(), association.getName()));
				 * 
				 * if(a == null){ EventMediator.addListener(EventMediator.EVT_MODEL_CONFIGURED, association); } else {
				 * association.setFromTarget(a);
				 * 
				 * // check if a many2many has owner if(association.getMany() && a.getMany() && !association.getOwner() &&
				 * !a.getOwner()) throw new RuntimeException("Association [" + ent.getName() + "]." + association.getName() + "-["
				 * + targetEntity.getName() + "]." + a.getName() +
				 * " needs to define the owner because its a many to many relanship."); }
				 */
			}
		}

		// references
		if (modelEntity.getReferences() != null && modelEntity.getReferences().getReference() != null) {
			List<ReferenceType> list = modelEntity.getReferences().getReference();
			for (ReferenceType reference : list) {
				ModelElement ref = model.getModelElement(reference.getName());
				Element element = (Element) ref.getTransformed();
				if (element == null)
					throw new RuntimeException(String.format("Unknown reference %s in %s does not exist in the model",
							reference.getName(), basic.getName()));

				String nome = reference.getAlias() != null ? reference.getAlias() : reference.getName();
				Reference r = new Reference(nome, element, reference.getSingle());
				if (element instanceof DataTranferObject) {
					r.setToDTO(true);
				} else if (element instanceof pt.quintans.mda.model.entity.Entity) {
					r.setToEntity(true);
				} else {
					throw new RuntimeException(String.format("Reference %s not allowed for %s", nome, basic.getName()));
				}
				ent.addDependency(element);
				ent.getReferences().add(r);
			}
		}

		// exposed to roles
		ent.setExposedToRoles(buildRoles(model, modelEntity, modelEntity.getExposedToRoles()));
		ent.setWritingRoles(buildRoles(model, modelEntity, modelEntity.getWritingRoles()));

		// LOV's - termina de configurar
		for (BaseAttributeType attr : modelEntity.getAttributes().getAttributesList()) {
			if ("lov".equals(attr.getElementType())) {
				// procura o attributo ja' instanciado
				EntLov entLov = (EntLov) attr;
				for (Attribute at : ent.getAttributes()) {
					if (at.getName().equals(entLov.getName())) {
						pt.quintans.mda.model.lov.ListOfValues lov = (pt.quintans.mda.model.lov.ListOfValues) model
								.getTransformedObject(entLov.getTarget());
						ent.addDependency(lov);
						at.setLov(lov);
						at.setType("string");
					}
				}
			}
		}

		// index
		if (modelEntity.getIndex() != null) {
			List<Index> indexes = new ArrayList<Index>();
			ent.setIndexes(indexes);
			for (IndexType index : modelEntity.getIndex()) {
				Index idx = new Index();
				indexes.add(idx);
				idx.setId(index.getId());
				idx.setName(index.getName());
				for (IndexMemberType indexMemberType : index.getMember()) {
					Element member = null;
					if (ent.getIdentity() != null && indexMemberType.getName().equals(ent.getIdentity().getName())) {
						member = ent.getIdentity();
					}
					if (member == null) {
						for (Attribute at : ent.getAttributes())
							if (at.getName().equals(indexMemberType.getName())) {
								member = at;
								break;
							}
					}
					if (member == null) {
						for (Association ass : ent.getAssociations())
							if (ass.getName().equals(indexMemberType.getName())) {
								member = ass;
								break;
							}
					}

					// found
					if (member != null) {
						idx.getMembers().add(member);
					} else
						throw new RuntimeException(String.format("Index member \"%s\", in entity %s, is undefined",
								indexMemberType.getName(), ent.getName()));
				}
			}
		}

        // unique groups
        if (modelEntity.getUniqueGroup() != null) {
            List<UniqueGroup> uniqueGroups = new ArrayList<UniqueGroup>();
            ent.setUniqueGroups(uniqueGroups);
            for (UniqueGroupType uniqueGroup : modelEntity.getUniqueGroup()) {
                UniqueGroup ug = new UniqueGroup();
                uniqueGroups.add(ug);
                ug.setId(uniqueGroup.getId());
                ug.setName(uniqueGroup.getName());
                for (UniqueGroupMemberType uniqueGroupMemberType : uniqueGroup.getMember()) {
                    Element member = null;
                    if (ent.getIdentity() != null && uniqueGroupMemberType.getName().equals(ent.getIdentity().getName())) {
                        member = ent.getIdentity();
                    }
                    if (member == null) {
                        for (Attribute at : ent.getAttributes())
                            if (at.getName().equals(uniqueGroupMemberType.getName())) {
                                member = at;
                                break;
                            }
                    }
                    if (member == null) {
                        for (Association ass : ent.getAssociations())
                            if (ass.getName().equals(uniqueGroupMemberType.getName())) {
                                member = ass;
                                break;
                            }
                    }

                    // found
                    if (member != null) {
                        if (member instanceof Attribute && ((Attribute) member).getUnique())
                            throw new RuntimeException(String.format(
                                    "Unique Group member (as Attribute) \"%s\", in entity %s, is already defined as unique.",
                                    uniqueGroupMemberType.getName(), ent.getName()));
                        ug.getMembers().add(member);
                    } else
                        throw new RuntimeException(String.format("Unique Group member \"%s\", in entity %s, is undefined",
                                uniqueGroupMemberType.getName(), ent.getName()));
                }
            }
        }
        
        // nested types
        if (modelEntity.getAttributes() != null) {
            List<Attribute> attrs = ent.getAttributes();
            for (BaseAttributeType attr : modelEntity.getAttributes().getAttributesList()) {
                Attribute at = null;
                // System.out.println("======> " + ent.getName() + "." + attr.getName() + ": " + attr.getElementType());
                if ("nested".equals(attr.getElementType())) {
                    EntNested nested = (EntNested) attr;
                    at = defineAttribute(new Attribute(ent), attr);
                    // find entity with the name == kind attribute
                    pt.quintans.mda.model.entity.Entity targetEntity = (pt.quintans.mda.model.entity.Entity) model.getTransformedObject(nested.getKind());
                    at.setModel(targetEntity);
                    attrs.add(at);
                }
            }
        }

	}

	private List<String> buildRoles(Model model, Entity modelEntity, String rolesStr) {
		List<String> rs = new ArrayList<String>();
		// exposed to roles
		if (rolesStr != null) {
			String roles[] = rolesStr.split(",");
			for (String role : roles) {
				String s = role.trim();
				ModelElement ref = model.getModelElement(s);
				Element element = (Element) ref.getTransformed();
				if (element == null)
					throw new RuntimeException(String.format("Unknown role %s in %s does not exist in the model", s,
							modelEntity.getName()));

				if (element instanceof Role) {
					rs.add(s);
				} else {
					throw new RuntimeException(String.format("Invalid Role %s declared in %", s, modelEntity.getName()));
				}
			}
		}

		return rs;
	}

	@Override
	public void allRelated(Model model) {
		EventMediator.addListener(EventMediator.EVT_MODEL_CONFIGURED, this);
		EventMediator.fire(new Event(EventMediator.EVT_ENTITIES_RELATED, model));

	}

	@Override
	public void onEvent(Event event) {
		if (!WorkerStore.get().isQuiet())
			System.out.println(String.format("Building all <<%s>> DAOs on event %s.", stereotype, event.getType()));

		Model model = (Model) event.getData();

		if(EventMediator.EVT_MODEL_CONFIGURED.equals(event.getType())){
			List<ModelElement> modelElementList = model.getModelElementList(stereotype);
			if (modelElementList != null) {
				for (ModelElement me : modelElementList) {
				    MRoot ent = me.getTransformed();
					ent.pushWeight(new HashSet<Object>());
				}
				Collections.sort(modelElementList, new Comparator<ModelElement>(){
					@Override
					public int compare(ModelElement arg0, ModelElement arg1) {
						MRoot ent0 = arg0.getTransformed();
						MRoot ent1 = arg1.getTransformed();
						return ent1.compareTo(ent0);
					}
				});
			}
		}

		EventMediator.fire(new Event(EventMediator.EVT_ENTITIES_CONFIGURED, model));
	}

	@Override
	public void allCreated(Model model) {
		List<ModelElement> modelElementList = model.getModelElementList(stereotype);
		if (modelElementList != null) {
			for (ModelElement me : modelElementList) {
				Entity basic = (Entity) me.getSource();
				pt.quintans.mda.model.entity.Entity ent = (pt.quintans.mda.model.entity.Entity) me.getTransformed();
				// extends - parent
				if (basic.getParent() != null) {
					ent.setParent((Element) model.getTransformedObject(basic.getParent()));
				}
			}
		}
	}

}
