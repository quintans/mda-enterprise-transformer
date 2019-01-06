package pt.quintans.mda.enterprise.custom;

import pt.quintans.mda.core.AbstractTransformer;
import pt.quintans.mda.core.Model;
import pt.quintans.mda.core.ModelElement;
import pt.quintans.mda.core.WorkerStore;
import pt.quintans.mda.enterprise.model.lov.Item;
import pt.quintans.mda.raw.domain.Basic;
import pt.quintans.mda.raw.domain.ItemType;
import pt.quintans.mda.raw.domain.ListOfValues;

public class LovTransformer extends AbstractTransformer {

	public pt.quintans.mda.enterprise.model.lov.ListOfValues instanciate() {
		return new pt.quintans.mda.enterprise.model.lov.ListOfValues();
	}
	
	@Override
	public void create(Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

		if(me.getTransformed() != null)
			return;
		
		if(!WorkerStore.get().isQuiet())
			System.out.println(String.format("Transforming <<%s>> %s", stereotype, objectName));
		
		Basic basic = me.getSource();
		ListOfValues modelObject = (ListOfValues) basic;

		pt.quintans.mda.enterprise.model.lov.ListOfValues lov = instanciate();
		me.setTransformed(lov);
		lov.setName(objectName);
		lov.setAlias(modelObject.getAlias() == null ? modelObject.getName() : modelObject.getAlias());
		lov.setNamespace(basic.getNamespace());
		lov.setLocation(basic.getLocation());
		lov.setStereotype(getStereotype());
		lov.setStereotypeAlias(getStereotypeAlias());
		lov.setNumeric(modelObject.isNumeric());
		lov.setKeylen(modelObject.getKeylen());
		lov.setBehaviors(basic.getBehaviors());

		// items
		if(modelObject.getItem() != null){			
			for(ItemType it : modelObject.getItem()){
				Item item = new Item();
				lov.getItems().add(item);
				item.setName(it.getName());
				item.setKey(it.getKey());
				item.setValue(it.getValue());
			}
		}		
	}

	@Override
	public void relate(Model model, String objectName) {
	}

}
