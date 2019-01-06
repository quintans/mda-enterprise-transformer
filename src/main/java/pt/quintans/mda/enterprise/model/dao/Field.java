package pt.quintans.mda.enterprise.model.dao;

import pt.quintans.mda.enterprise.model.entity.Value;

public class Field extends Value {
	private String tableAlias;
	private String projectionName;
	private Join join;
	private Boolean single = true;

	public Boolean getSingle() {
		return single;
	}

	public void setSingle(Boolean single) {
		this.single = single;
	}

	public Join getJoin() {
		return join;
	}

	public void setJoin(Join join) {
		this.join = join;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public String getProjectionName() {
		return projectionName;
	}

	public void setProjectionName(String projectionName) {
		this.projectionName = projectionName;
	}

}

