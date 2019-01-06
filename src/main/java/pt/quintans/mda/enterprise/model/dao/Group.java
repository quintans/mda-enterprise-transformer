package pt.quintans.mda.enterprise.model.dao;

import java.util.ArrayList;
import java.util.List;

public class Group implements IGroupItem {
	// Contains operations and groups
	private List<IGroupItem> operations = new ArrayList<IGroupItem>();
	
	private GroupEnum type;
	
	public List<IGroupItem> getOperations() {
		return operations;
	}
	public void setOperations(List<IGroupItem> operations) {
		this.operations = operations;
	}
	public GroupEnum getType() {
		return type;
	}
	public void setType(GroupEnum type) {
		this.type = type;
	}
	@Override
	public boolean isGrouper() {
		return true;
	}		
}
