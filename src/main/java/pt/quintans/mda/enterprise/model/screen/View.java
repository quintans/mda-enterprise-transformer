package pt.quintans.mda.enterprise.model.screen;

import java.util.ArrayList;
import java.util.List;

public class View {
	private boolean editable;
	private Integer maxColumn = 0;
	private String name;
	private String description;
	private List<Column> columns = new ArrayList<Column>();
	private List<Chain> chains = new ArrayList<Chain>();

	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public Integer getMaxColumn() {
		return maxColumn;
	}
	public void setMaxColumn(Integer maxColumn) {
		this.maxColumn = maxColumn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Chain> getChains() {
		return chains;
	}
	public void setChains(List<Chain> chains) {
		this.chains = chains;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
}
