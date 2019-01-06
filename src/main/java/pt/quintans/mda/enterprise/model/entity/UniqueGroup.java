package pt.quintans.mda.enterprise.model.entity;

import java.util.ArrayList;
import java.util.List;

public class UniqueGroup extends Element {
	protected List<Element> members = new ArrayList<Element>();
	
	public List<Element> getMembers() {
		return members;
	}

	public void setMembers(List<Element> members) {
		this.members = members;
	}


	public static UniqueGroup differentiate(UniqueGroup oldUniqueGroup, UniqueGroup newUniqueGroup){
		boolean diff = false;
		
		UniqueGroup uniqueGroup = new UniqueGroup();

		if(newUniqueGroup == null){
			oldUniqueGroup.setDrop(true);
			return oldUniqueGroup;
		} else if(oldUniqueGroup == null){
			return newUniqueGroup;
		} else
			uniqueGroup.setOld(oldUniqueGroup);
		
		
		if(!newUniqueGroup.getAlias().equals(oldUniqueGroup.getAlias())){
			newUniqueGroup.setAlias(newUniqueGroup.getAlias());
			newUniqueGroup.setName(newUniqueGroup.getName());
			diff = true;
		}


		if(diff){
			return uniqueGroup;
		} else
			return null;
	}
	
}
