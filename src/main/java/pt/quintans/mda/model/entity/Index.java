package pt.quintans.mda.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Index extends Element {
	protected List<Element> members = new ArrayList<Element>();
	
	public List<Element> getMembers() {
		return members;
	}

	public void setMembers(List<Element> members) {
		this.members = members;
	}


	public static Index differentiate(Index oldIndex, Index newIndex){
		boolean diff = false;
		
		Index index = new Index();

		if(newIndex == null){
			oldIndex.setDrop(true);
			return oldIndex;
		} else if(oldIndex == null){
			return newIndex;
		} else
			index.setOld(oldIndex);
		
		
		if(!newIndex.getAlias().equals(oldIndex.getAlias())){
			newIndex.setAlias(newIndex.getAlias());
			newIndex.setName(newIndex.getName());
			diff = true;
		}


		if(diff){
			return index;
		} else
			return null;
	}
	
}
