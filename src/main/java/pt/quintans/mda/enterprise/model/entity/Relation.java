package pt.quintans.mda.enterprise.model.entity;

public class Relation {
	private Attribute key;
	private Attribute foreign;
	
	public Attribute getKey() {
		return key;
	}
	
	public void setKey(Attribute key) {
		this.key = key;
	}
	
	public Attribute getForeign() {
		return foreign;
	}
	
	public void setForeign(Attribute foreign) {
		this.foreign = foreign;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s", foreign, key);
	}
	
	
}
