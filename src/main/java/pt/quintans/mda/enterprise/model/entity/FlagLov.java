package pt.quintans.mda.enterprise.model.entity;

import pt.quintans.mda.enterprise.model.lov.ListOfValues;

public class FlagLov extends FlagBase{
	private ListOfValues target;

	public FlagLov() {
		super();
		type = "lov";
	}
	public ListOfValues getTarget() {
		return target;
	}
	public void setTarget(ListOfValues target) {
		this.target = target;
	}
}
