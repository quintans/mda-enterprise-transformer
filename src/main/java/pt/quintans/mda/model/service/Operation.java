package pt.quintans.mda.model.service;

import java.util.ArrayList;
import java.util.List;

import pt.quintans.mda.model.entity.Attribute;
import pt.quintans.mda.model.entity.Element;

public class Operation extends Element {
    protected String type;
    protected boolean customType = false;
    protected boolean modeledType = false;
    protected boolean single = true;
    private List<Attribute> attributes = new ArrayList<Attribute>();
    private List<Role> roles = new ArrayList<Role>();
    private boolean paginate;
    protected boolean transactional = false;
    protected boolean internal = false;
    protected String scheduled;

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isModeledType() {
        return modeledType;
    }

    public void setModeledType(boolean modeledType) {
        this.modeledType = modeledType;
    }

    public boolean isTransactional() {
        return transactional;
    }

    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

}
