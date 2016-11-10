package pt.quintans.mda.model.entity;

import pt.quintans.mda.model.lov.ListOfValues;

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
