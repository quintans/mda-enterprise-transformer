package pt.quintans.bus;

import pt.quintans.mda.core.Tools;

public class BaseDAO {
	public static final String MAIN_ENTITY_ALIAS = "e";
	public static final String OTHER_ENTITY_ALIAS = "t";
	public static final String ID_ATTRIBUTE = "id";
	public static final String FK_SUFFIX = "_fk";

	protected void buildOrder(StringBuffer order, String column, Sort sort){
		if(sort != Sort.NONE)
			order.append(Tools.concat(order.toString(), column + (sort == Sort.ASC ? " asc" : " desc"), ", "));
	}
	
	protected StringBuffer paginateQuery(StringBuffer sql, QueryObject qo, java.util.Map<String, Object> args){
		StringBuffer sqlw = new StringBuffer();
		if(qo.getFirstRecord() > 1){
			sqlw.append("select * from ( select rownum rnum, a.* from (\n");
			sqlw.append(sql.toString());
			sqlw.append("\n) a");
			if(qo.getMaxRecords() > 0){
   				sqlw.append("\nwhere rownum <= :maxRecords");
   				args.put("maxRecords", qo.getMaxRecords());
   			}			
			sqlw.append("\nwhere rnum >= :firstRecord");
   			args.put("firstRecord", qo.getFirstRecord());
		} else if(qo.getMaxRecords() > 0) {
			sqlw.append("\nselect * from (");
			sqlw.append(sql.toString());
			sqlw.append(")\nwhere rownum <= :maxRecords");
   			args.put("maxRecords", qo.getMaxRecords());
		} else
			sqlw = sql;
		
		return sqlw;
	}
}
