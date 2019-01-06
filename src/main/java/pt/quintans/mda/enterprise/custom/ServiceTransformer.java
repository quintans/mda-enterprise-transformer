package pt.quintans.mda.enterprise.custom;

import java.util.ArrayList;
import java.util.List;

import pt.quintans.mda.core.AbstractTransformer;
import pt.quintans.mda.core.Model;
import pt.quintans.mda.core.ModelElement;
import pt.quintans.mda.core.Tools;
import pt.quintans.mda.core.WorkerStore;
import pt.quintans.mda.enterprise.model.entity.Attribute;
import pt.quintans.mda.enterprise.model.entity.Element;
import pt.quintans.mda.enterprise.model.entity.Entity;
import pt.quintans.mda.enterprise.model.lov.ListOfValues;
import pt.quintans.mda.enterprise.model.service.Operation;
import pt.quintans.mda.enterprise.model.service.Role;
import pt.quintans.mda.raw.domain.AttrBaseAttributeType;
import pt.quintans.mda.raw.domain.AttrCustom;
import pt.quintans.mda.raw.domain.AttrModel;
import pt.quintans.mda.raw.domain.Basic;
import pt.quintans.mda.raw.domain.OperationType;
import pt.quintans.mda.raw.domain.ReferenceType;
import pt.quintans.mda.raw.domain.Service;

public class ServiceTransformer extends AbstractTransformer {

	public pt.quintans.mda.enterprise.model.service.Service instanciate() {
		return new pt.quintans.mda.enterprise.model.service.Service();
	}
	
	@Override
	public void create(Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

		if(me.getTransformed() != null)
			return;
		
	    if(!WorkerStore.get().isQuiet())
	    	System.out.println(String.format("Transforming <<%s>> %s", stereotype, objectName));
		
		Basic basic = me.getSource();
		Service modelObject = (Service) basic;
		
		pt.quintans.mda.enterprise.model.service.Service srv = instanciate();
		me.setTransformed(srv);
		srv.setName(objectName);
		srv.setAlias(Tools.Null2(basic.getAlias(), srv.getName()));
		srv.setNamespace(basic.getNamespace());
		srv.setLocation(basic.getLocation());
		srv.setStereotype(getStereotype());
		srv.setStereotypeAlias(getStereotypeAlias());
		srv.setBehaviors(basic.getBehaviors());
		srv.setInternal(modelObject.isInternal());
	}

	@Override
	public void relate(Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

	    if(!WorkerStore.get().isQuiet())
	    	System.out.println(String.format("Relating <<%s>> %s", stereotype, objectName));
		
		Basic basic = me.getSource();
		Service modelObject = (Service) basic;
			
		pt.quintans.mda.enterprise.model.service.Service srv = (pt.quintans.mda.enterprise.model.service.Service) me.getTransformed();
		
		// operations
		if(modelObject.getOperations() != null){
			List<Operation> operations = new ArrayList<Operation>();
			srv.setOperations(operations);
			for(OperationType oper : modelObject.getOperations().getOperation()){
				Operation operation = new Operation();
				operations.add(operation);
				operation.setName(oper.getName());
				operation.setAlias(Tools.Null2(oper.getAlias(), oper.getName()));
				operation.setType(oper.getType());
				if(model.hasObject(oper.getType())){
					Element element = model.getTransformedObject(oper.getType());
					operation.setModeledType(true);
					operation.addDependency(element);
				} else if(oper.getType() != null && oper.getType().indexOf(".") > 0)
					operation.setCustomType(true);
				operation.setSingle(oper.isSingle());
				operation.setPaginate(oper.isPaginate());
				operation.setBehaviors(oper.getBehaviors());
				operation.setComments(oper.getComments());
				operation.setTransactional(oper.isTransactional());
				// if undefined use parents
                operation.setInternal(oper.isInternal() != null ? oper.isInternal() : modelObject.isInternal());
                operation.setScheduled(oper.getScheduled());
				
				// attributes
				if(oper.getAttributesList() != null){
					List<Attribute> attrs = new ArrayList<Attribute>();
					operation.setAttributes(attrs);
					for(AttrBaseAttributeType attr : oper.getAttributesList()){
						Attribute at = new Attribute(operation);
						attrs.add(at);
						at.setName(attr.getName());
						at.setNullable(attr.isNullable());
						// lov
						if(attr.getElementType().equals("model")){
							AttrModel attrModel = (AttrModel) attr;
							if(model.hasObject(attrModel.getType())){
								Element element = model.getTransformedObject(attrModel.getType());
								srv.addDependency(element);
								at.setType(attrModel.getType());
								at.setModel(element);
								if(element instanceof ListOfValues)
									at.setLov((ListOfValues) element);
							}else
								throw new RuntimeException(String.format("Element model %s was not found", attrModel.getType()));
						} else if (attr.getElementType().equals("custom")) {
							AttrCustom custom = (AttrCustom) attr;
							at.setType(custom.getType());
							at.setCustomType(true);
						} else {
							at.setType(attr.getElementType());
						}
							
						at.setSingle(attr.isSingle());
						at.setComments(attr.getComments());
					}
				}		
			}
		}		
		
		//Roles
		String roles = modelObject.getRoles();
		if(roles != null){
			String [] rs = roles.split(",");
			// checks role
			for(String r : rs){
				Role role = (Role) model.getTransformedObject(r.trim());
				srv.getRoles().add(role);
			}
		}
		
		for(OperationType oper : modelObject.getOperations().getOperation()){
			roles = oper.getRoles();
			if(roles != null){
				String [] rs = roles.split(",");
				// checks role
				for(String r : rs){
					Role role = (Role) model.getTransformedObject(r.trim());
					// finds matching operation
					for(Operation operation : srv.getOperations()){
						if(oper.getName().equals(operation.getName()))
							operation.getRoles().add(role);
					}
				}
			}
		}
		
		// references
		if(modelObject.getReferences() != null && modelObject.getReferences().getReference() != null){
			List<ReferenceType> list = modelObject.getReferences().getReference();
			for(ReferenceType reference : list){
				ModelElement ref = model.getModelElement(reference.getName());
				Element element = (Element) ref.getTransformed();
				if(element == null)
					throw new RuntimeException(String.format("Unknown reference %s in %s does not exist in the model", reference.getName(), basic.getName()));
				
				if(element instanceof Entity){
					srv.addDependency(element);
					srv.getEntities().add((Entity)element);
				} else {
					throw new RuntimeException(String.format("Reference %s not allowed for %s", reference.getName(), basic.getName()));
				}
			}
		}
		
	}

}
