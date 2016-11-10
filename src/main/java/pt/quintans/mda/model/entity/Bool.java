package pt.quintans.mda.model.entity;

public class Bool extends Attribute {
	private String format;
	private String trueValue;
	private String falseValue;
	
	public Bool(Element parent) {
		super(parent);
	}

	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getTrueValue() {
		return trueValue;
	}
	public void setTrueValue(String trueValue) {
		this.trueValue = trueValue;
	}
	public String getFalseValue() {
		return falseValue;
	}
	public void setFalseValue(String falseValue) {
		this.falseValue = falseValue;
	}
	
	
}
