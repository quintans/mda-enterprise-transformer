package pt.quintans.mda.model.entity;

import java.util.List;

import pt.quintans.mda.core.MRoot;
import pt.quintans.mda.core.Tools;
import pt.quintans.mda.model.lov.ListOfValues;

public class Attribute extends Value {
	protected boolean modeledType = false;
	protected Element model;
	protected boolean customType = false;
	private Boolean key = false;
	private Boolean unique = false;
	private boolean single = true;
	private Integer length;
	private ListOfValues lov;
	private boolean composite = false;
	private boolean deleter = true;
	private List<Constraint> constraints;

	public Attribute(MRoot parent){
		this.parent = parent;
	}
	
	@Override
	public String toString(){
		return String.format("%s.%s", parent.getName(), name);
	}
	
	public Boolean getComposite() {
		return composite;
	}
	public void setComposite(Boolean composite) {
		this.composite = composite;
	}

	public ListOfValues getLov() {
		return lov;
	}

	public void setLov(ListOfValues lov) {
		this.lov = lov;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Boolean getKey() {
		return key;
	}

	public void setKey(Boolean key) {
		this.key = key;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	public boolean isModeledType() {
		return modeledType;
	}
	
	public Element getModel() {
		return model;
	}

	public void setModel(Element model) {
		this.model = model;
		this.modeledType = true;
		if(modeledType)
			customType = false;
	}

	public boolean isCustomType() {
		return customType;
	}

	public void setCustomType(boolean customType) {
		this.customType = customType;
		if(customType)
			modeledType = false;
	}

	public boolean isDeleter() {
		return deleter;
	}

	public void setDeleter(boolean deleter) {
		this.deleter = deleter;
	}

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public static Attribute differentiate(Attribute oldAttribute, Attribute newAttribute){
		boolean diff = false;
		
		if(newAttribute == null){
			oldAttribute.setDrop(true);
			return oldAttribute;
		} else if(oldAttribute == null){
			return newAttribute;
		} 
		
		Attribute attribute = new Attribute(oldAttribute.getParent());
		attribute.setOld(oldAttribute);

		if(!newAttribute.getAlias().equals(oldAttribute.getAlias())){
			attribute.setAlias(newAttribute.getAlias());
			attribute.setName(newAttribute.getName());
			diff = true;
		}

		if(newAttribute.getNullable() != oldAttribute.getNullable()){
			attribute.setNullable(newAttribute.getNullable());
			diff = true;
		}
		
		if(!newAttribute.getType().equals(oldAttribute.getType())){
			attribute.setType(newAttribute.getType());
			diff = true;
		}

		// not mandatory
		if(!Tools.similar(newAttribute.getLength(), oldAttribute.getLength())){
			attribute.setLength(newAttribute.getLength());
			diff = true;
		}


		if(diff){
			return attribute;
		} else
			return null;
	}
	
}
