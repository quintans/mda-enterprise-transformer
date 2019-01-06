package pt.quintans.mda.enterprise.model.dao;

import java.util.ArrayList;
import java.util.List;

public class Query {
	// lista das operações. pode conter Operation ou outras listas. 
	// Outras são consideradas como novo grupo de operações 
	private Group group;
	private String name;
	private boolean custom;
	private boolean single;
	private boolean flat;
	private String type;
	private List<Field> customAttributes = new ArrayList<Field>(); 
	private List<Field> parameters = new ArrayList<Field>(); 
	private List<Join> joins = new ArrayList<Join>(); 
	private List<List<Join>> fetchJoins = new ArrayList<List<Join>>(); 
	//private String where;
	
	
	/*
	public boolean isContainsWhere(){
		return (group != null && group.getOperations().size() > 0);
	}
	public String getWhere() {
		StringBuffer where = new StringBuffer();
		if(group != null)
			explodeGroup(group, where);
		return where.toString();
	}
	*/
		/*
		StringBuffer sql = new StringBuffer();
		sql.append("select e.ID, e.VERSION, e.SUBJECT, e.START, e.END from MEETING e");
		if(deptartmentId != null || salary != null)
			sql.append(" inner join EMPLOYEE t1 on t1.ATTENDEE_FK = e.ID");   
		
		if(deptartmentId != null)
			sql.append(" inner join DEPARTMENT t2 on t2.ID = t1.DEPT_FK");   
		
		StringBuffer where = new StringBuffer();
		
		if(deptartmentId != null)
			concat(" and ");
			where.append(" t2.name = :deptartmentId");
		else
			concat(" and ");
			where.append(" t2.name IS");
			
		if(salary != null)
			concat(" and ");
			where.append(" t1.salary >= :salary");
			
		if(where.length() > 0)
			sql.append(" where ").append(where.toString());
		*/
	/*
	private void explodeGroup(Group group, StringBuffer where){
		boolean first = true;
		boolean around = (group.getOperations().size() > 1 && GroupEnum.OR.equals(group.getType()));
		if(around)
			where.append("(");
		
		StringBuffer grp = new StringBuffer();		
		for(IGroupItem op : group.getOperations()){
			if(!first){
				grp.append(" ")
				.append(group.getType())
				.append(" ");
			} else
				first = false;
				
			if(op.isGrouper()){
				explodeGroup((Group) op, where);
			} else {
				Criteria criteria = (Criteria) op;
				grp.append(criteria.getTableAlias()).append(".").append(criteria.getAttribute());
				grp.append(criteria.getType().toString());
				
				grp.append(":").append(criteria.getName());
			}
		}
		if(around)
			where.append(")");
	}
	*/
	
	public List<List<Join>> getFetchJoins() {
		return fetchJoins;
	}
	public boolean isFlat() {
		return flat;
	}
	public void setFlat(boolean flat) {
		this.flat = flat;
	}
	public void setFetchJoins(List<List<Join>> fetchJoins) {
		this.fetchJoins = fetchJoins;
	}
	public List<Join> getJoins() {
		return joins;
	}
	public void setJoins(List<Join> joins) {
		this.joins = joins;
	}
	public List<Field> getParameters() {
		return parameters;
	}
	public void setParameters(List<Field> parameters) {
		this.parameters = parameters;
	}
	
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public List<Field> getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(List<Field> customAttributes) {
		this.customAttributes = customAttributes;
	}
	
}
