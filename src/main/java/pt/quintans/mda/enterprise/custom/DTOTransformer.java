package pt.quintans.mda.enterprise.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import pt.quintans.mda.core.AbstractTransformer;
import pt.quintans.mda.core.MRoot;
import pt.quintans.mda.core.Model;
import pt.quintans.mda.core.ModelElement;
import pt.quintans.mda.core.WorkerStore;
import pt.quintans.mda.enterprise.model.Reference;
import pt.quintans.mda.enterprise.model.dto.DataTranferObject;
import pt.quintans.mda.enterprise.model.entity.Attribute;
import pt.quintans.mda.enterprise.model.entity.Element;
import pt.quintans.mda.raw.domain.AttrBaseAttributeType;
import pt.quintans.mda.raw.domain.Basic;
import pt.quintans.mda.raw.domain.Dto;
import pt.quintans.mda.raw.domain.DtoLov;
import pt.quintans.mda.raw.domain.ReferenceType;

public class DTOTransformer extends AbstractTransformer {
	
	public DataTranferObject instanciate() {
		return new DataTranferObject();
	}

	@Override
	public void create(Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

		if (me.getTransformed() != null)
			return;

		if (!WorkerStore.get().isQuiet())
			System.out.println(String.format("Transforming <<%s>> %s", stereotype, objectName));

		Basic basic = me.getSource();
		Dto modelObject = (Dto) basic;

		DataTranferObject dto = instanciate();
		me.setTransformed(dto);
		dto.setName(objectName);
		dto.setNamespace(basic.getNamespace());
		dto.setLocation(basic.getLocation());
		dto.setStereotype(getStereotype());
		dto.setStereotypeAlias(getStereotypeAlias());
		dto.setBehaviors(basic.getBehaviors());

		// attributes
		if (modelObject.getAttributes() != null) {
			List<Attribute> attrs = new ArrayList<Attribute>();
			dto.setAttributes(attrs);
			for (AttrBaseAttributeType attr : modelObject.getAttributes().getAttributesList()) {
				Attribute at = new Attribute(dto);
				attrs.add(at);
				at.setName(attr.getName());
				at.setNullable(attr.isNullable());
				at.setType(attr.getElementType());
				at.setSingle(attr.isSingle());
				at.setDefaultValue(attr.getDefaultValue());
			}
		}

	}

	@Override
	public void relate(Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

		if (!WorkerStore.get().isQuiet())
			System.out.println(String.format("Relating <<%s>> %s", stereotype, objectName));

		Basic basic = me.getSource();
		Dto modelObject = (Dto) basic;
		DataTranferObject dto = (DataTranferObject) me.getTransformed();

		// LOV's - termina de configurar
		if (modelObject.getAttributes() != null) {
			for (AttrBaseAttributeType attr : modelObject.getAttributes().getAttributesList()) {
				if ("lov".equals(attr.getElementType())) {
					// procura o attributo ja' instanciado
					DtoLov dtoLov = (DtoLov) attr;
					for (Attribute at : dto.getAttributes()) {
						if (at.getName().equals(dtoLov.getName())) {
							pt.quintans.mda.enterprise.model.lov.ListOfValues lov = (pt.quintans.mda.enterprise.model.lov.ListOfValues) model
									.getTransformedObject(dtoLov.getTarget());
							dto.addDependency(lov);
							at.setLov(lov);
						}
					}
				}
			}
		}

		// references
		if (modelObject.getReferences() != null && modelObject.getReferences().getReference() != null) {
			List<ReferenceType> list = modelObject.getReferences().getReference();
			for (ReferenceType reference : list) {
				String nome = reference.getAlias() != null ? reference.getAlias() : reference.getName();
				ModelElement ref = model.getModelElement(reference.getName());
				Element element = (Element) ref.getTransformed();
				if (element == null)
					throw new RuntimeException(String.format("Unknown reference %s in %s does not exist in the model",
							reference.getName(), basic.getName()));

				Reference r = new Reference(nome, element, reference.isSingle());
				r.setPaginate(reference.isPaginate());
				if (element instanceof DataTranferObject || element instanceof pt.quintans.mda.enterprise.model.lov.ListOfValues) {
					dto.addDependency(element);
				} else if (element instanceof pt.quintans.mda.enterprise.model.entity.Entity) {
					dto.addDependency(element);
					r.setToEntity(true);
				} else {
					throw new RuntimeException(String.format("Reference %s not allowed for %s", nome, basic.getName()));
				}
				dto.getReferences().add(r);				
			}
		}

	}

	@Override
	public void allCreated(Model model) {
		List<ModelElement> modelElementList = model.getModelElementList(stereotype);
		if (modelElementList != null) {
			for (ModelElement me : modelElementList) {
				Dto basic = (Dto) me.getSource();
				DataTranferObject dto = (DataTranferObject) me.getTransformed();
				// extends - parent
				if (basic.getParent() != null) {
					dto.setParent((Element) model.getTransformedObject(basic.getParent()));
				}
			}
			
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

}
