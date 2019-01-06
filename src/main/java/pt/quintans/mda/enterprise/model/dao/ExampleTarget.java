package pt.quintans.mda.enterprise.model.dao;

import java.util.ArrayList;
import java.util.List;

import pt.quintans.mda.enterprise.model.entity.Association;
import pt.quintans.mda.enterprise.model.entity.Attribute;

public class ExampleTarget {
	List<Association> associationsChain = new ArrayList<Association>();
	List<Attribute> attributes = new ArrayList<Attribute>();
	List<Attribute> excludes = new ArrayList<Attribute>();
	Boolean right = false;

	public List<Association> getAssociationsChain() {
		return associationsChain;
	}
	public void setAssociationsChain(List<Association> associationsChain) {
		this.associationsChain = associationsChain;
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public List<Attribute> getExcludes() {
		return excludes;
	}
	public void setExcludes(List<Attribute> excludes) {
		this.excludes = excludes;
	}
	
	public Association getLastAssociation(){
		return associationsChain.get(associationsChain.size() - 1);
	}
	public Boolean getRight() {
		return right;
	}
	public void setRight(Boolean right) {
		this.right = right;
	}
	
	
}
