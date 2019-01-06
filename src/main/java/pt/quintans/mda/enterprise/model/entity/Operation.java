package pt.quintans.mda.enterprise.model.entity;

import java.util.ArrayList;
import java.util.List;

import pt.quintans.mda.enterprise.model.entity.Attribute;
import pt.quintans.mda.enterprise.model.entity.Element;

public class Operation extends Element {
	protected String type;
	protected boolean customType = false;
	protected boolean modeledType = false;
	protected boolean single = true;
	protected boolean paginate = true;
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private List<String> roles = new ArrayList<String>();
    protected boolean transactional = false;
		
	public boolean isCustomType() {
		return customType;
	}
	public void setCustomType(boolean customType) {
		this.customType = customType;
	}
	public boolean isPaginate() {
		return paginate;
	}
	public void setPaginate(boolean paginate) {
		this.paginate = paginate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isSingle() {
		return single;
	}
	public void setSingle(boolean single) {
		this.single = single;
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public boolean isModeledType() {
		return modeledType;
	}
	public void setModeledType(boolean modeledType) {
		this.modeledType = modeledType;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
    public boolean isTransactional() {
        return transactional;
    }
    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }
}
