package pt.quintans.mda.model.entity;

public class FlagBool extends FlagBase{
	private String trueValue;
	private String falseValue;
	
	public FlagBool() {
		super();
		type = "bool";
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
