package pt.quintans.mda.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Flags extends Attribute {
	private List<FlagBool> bools = new ArrayList<FlagBool>();
	private List<FlagLov> lovs = new ArrayList<FlagLov>();
	
	public Flags(Element parent) {
		super(parent);
	}
	public List<FlagBool> getBools() {
		return bools;
	}
	public void setBools(List<FlagBool> bools) {
		this.bools = bools;
	}
	public List<FlagLov> getLovs() {
		return lovs;
	}
	public void setLovs(List<FlagLov> lovs) {
		this.lovs = lovs;
	}
}
