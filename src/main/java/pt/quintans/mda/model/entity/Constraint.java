package pt.quintans.mda.model.entity;

public class Constraint {
    private Attribute target;
    private Object value;
    private boolean parameter;

    public Attribute getTarget() {
        return target;
    }

    public void setTarget(Attribute target) {
        this.target = target;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isParameter() {
        return parameter;
    }

    public void setParameter(boolean parameter) {
        this.parameter = parameter;
    }

}
