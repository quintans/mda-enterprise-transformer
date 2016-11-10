package pt.quintans.mda.model.constant;

import pt.quintans.mda.model.entity.Element;

public class Item extends Element {
    protected String value;
    protected boolean single;
    protected String type;
    protected String kind;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

}
