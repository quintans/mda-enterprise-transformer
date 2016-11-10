package pt.quintans.mda.custom;

import pt.quintans.mda.core.AbstractTransformer;
import pt.quintans.mda.core.Model;
import pt.quintans.mda.core.ModelElement;
import pt.quintans.mda.core.WorkerStore;
import pt.quintans.mda.model.constant.Item;
import pt.quintans.mda.raw.domain.Basic;
import pt.quintans.mda.raw.domain.Constant;
import pt.quintans.mda.raw.domain.Constants;
import pt.quintans.mda.raw.domain.ModelVarType;

public class ConstantsTransformer extends AbstractTransformer {

	public pt.quintans.mda.model.constant.Constants instanciate() {
		return new pt.quintans.mda.model.constant.Constants();
	}
	
	@Override
	public void create(Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

		if(me.getTransformed() != null)
			return;
		
		if(!WorkerStore.get().isQuiet())
			System.out.println(String.format("Transforming <<%s>> %s", stereotype, objectName));
		
		Basic basic = me.getSource();
		Constants modelObject = (Constants) basic;

		pt.quintans.mda.model.constant.Constants constants = instanciate();
		me.setTransformed(constants);
		constants.setName(objectName);
		constants.setAlias(modelObject.getAlias() == null ? modelObject.getName() : modelObject.getAlias());
		constants.setNamespace(basic.getNamespace());
		constants.setLocation(basic.getLocation());
		constants.setStereotype(getStereotype());
		constants.setStereotypeAlias(getStereotypeAlias());
		constants.setBehaviors(basic.getBehaviors());
		constants.setComments(basic.getComments());

		// items
		if(modelObject.getConst() != null){			
			for(Constant it : modelObject.getConst()){
				Item item = new Item();
				constants.getItems().add(item);
				item.setName(it.getName());
				item.setAlias(it.getAlias() != null ? it.getAlias() : it.getName());
				item.setSingle(it.getSingle());
				item.setType(it.getType().value());
				item.setKind(it.getKind());
				item.setComments(it.getComments());
                if(it.getValue() != null) {
                    item.setValue(it.getValue());
                } else if(it.getType() == ModelVarType.STRING) {
                    item.setValue(item.getName());
                } else {
                    throw new RuntimeException("If the constante is not a string, the value must be defined!");
                }
			}
		}		
	}

	@Override
	public void relate(Model model, String objectName) {
	}

}
