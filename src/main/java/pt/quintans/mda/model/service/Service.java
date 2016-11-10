package pt.quintans.mda.model.service;

import java.util.ArrayList;
import java.util.List;

import pt.quintans.mda.model.entity.Element;
import pt.quintans.mda.model.entity.Entity;

public class Service extends Element {
    private List<Operation> operations = new ArrayList<Operation>();
    private List<Entity> entities = new ArrayList<Entity>();
    private List<Role> roles = new ArrayList<Role>();
    private boolean internal;

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

}
