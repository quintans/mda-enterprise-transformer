package pt.quintans.mda.enterprise.model.screen;

import pt.quintans.mda.enterprise.model.entity.Association;

public class Popless {
	private String searcher;
	private Association association;
	
	public Popless(String searcher, Association association) {
		super();
		this.searcher = searcher;
		this.association = association;
	}

	public String getSearcher() {
		return searcher;
	}
	public void setSearcher(String searcher) {
		this.searcher = searcher;
	}
	public Association getAssociation() {
		return association;
	}
	public void setAssociation(Association association) {
		this.association = association;
	}
	
	
}
