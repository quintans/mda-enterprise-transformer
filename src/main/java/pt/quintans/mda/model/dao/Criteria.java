package pt.quintans.mda.model.dao;

public class Criteria implements IGroupItem{
	private CriteriaEnum type;
	private String name;
	private String tableAlias;
	private String attribute;
	private boolean nullable;
	private String clazz;		
	
	public String getTableAlias() {
		return tableAlias;
	}
	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	public CriteriaEnum getType() {
		return type;
	}
	public void setType(CriteriaEnum type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	@Override
	public boolean isGrouper() {
		return false;
	}
	
	
}
