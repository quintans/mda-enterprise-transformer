package pt.quintans.mda.model.screen;

import java.util.ArrayList;
import java.util.List;

import pt.quintans.mda.model.entity.Association;
import pt.quintans.mda.model.entity.Attribute;

public class Order {
	private Attribute target;
	private List<Association> associationPath = new ArrayList<Association>();
	private String description;

	public Attribute getTarget() {
		return target;
	}

	public void setTarget(Attribute target) {
		this.target = target;
	}

	public List<Association> getAssociationPath() {
		return associationPath;
	}

	public void setAssociationPath(List<Association> associationPath) {
		this.associationPath = associationPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
