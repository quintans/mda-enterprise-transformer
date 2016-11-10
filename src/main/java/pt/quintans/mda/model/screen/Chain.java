package pt.quintans.mda.model.screen;

import java.util.ArrayList;
import java.util.List;

public class Chain {
	private String description;
	private String name;
	private List<Field> fields= new ArrayList<Field>();
	private boolean compact;
	private int fieldSpan = 0;
		
	public int getFieldSpan() {
		return fieldSpan;
	}
	public boolean isCompact() {
		return compact;
	}
	public void setCompact(boolean compact) {
		this.compact = compact;
	}
	public List<Field> getFields() {
		return fields;
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
	public void addField(Field field){
		fieldSpan += field.getSpan();
		fields.add(field);
	}
}
