package pt.quintans.mda.custom;

import pt.quintans.mda.core.AbstractTransformer;
import pt.quintans.mda.core.Model;
import pt.quintans.mda.core.ModelElement;
import pt.quintans.mda.core.WorkerStore;
import pt.quintans.mda.model.dto.DataTranferObject;

public class RoleTransformer extends AbstractTransformer {

	public pt.quintans.mda.model.service.Role instanciate() {
		return new pt.quintans.mda.model.service.Role();
	}
	
	@Override
	public void create(Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

		if(me.getTransformed() != null)
			return;
		
		if(!WorkerStore.get().isQuiet())
			System.out.println(String.format("Transforming <<%s>> %s", stereotype, objectName));
		
		pt.quintans.mda.model.service.Role role = instanciate();
		me.setTransformed(role);
		role.setName(objectName);
		
	}

	@Override
	public void relate(Model model, String objectName) {
	}

}
