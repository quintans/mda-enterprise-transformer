package pt.quintans.mda.enterprise.model.lov;

import java.util.Set;

import pt.quintans.mda.enterprise.model.entity.Element;

public class Item extends Element {
	protected String key;
	protected String value;
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

    @Override
    public void pushWeight(Set<Object> set) {
        // TODO Auto-generated method stub
        
    }
}
