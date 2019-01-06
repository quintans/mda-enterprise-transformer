package pt.quintans.mda.enterprise.model.dao;

public enum CriteriaEnum {
	EQUAL("="), LIKE("like"), GREATER(">"), GREATER_EQUAL(">=");
	
	private final String symbol;
	CriteriaEnum(String symbol){
		this.symbol = symbol;
	}
	
	public String toString(){
		return symbol;
	}
}
