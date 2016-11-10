package pt.quintans.mda.model.screen;

import java.util.ArrayList;
import java.util.List;

import pt.quintans.mda.model.entity.Association;
import pt.quintans.mda.model.entity.Attribute;
import pt.quintans.mda.model.entity.FlagBase;

public class Field {
	private Attribute target;
	private Association association;
	private boolean readOnly;
	private boolean required;
	private boolean exact;
	private boolean pop;
	private boolean raw;
	private boolean custom;
	private boolean editable;
	private Integer width;
	private Integer labelWidth;
	private Integer fieldWidth;
	private Integer length;
	private Integer lines;
	private List<String> attributesName;
	private String show;
	private String searcher;
	private String description;
	private String alias;
	private String name;
	private String defaultValue;
	private List<FlagBase> flags = new ArrayList<FlagBase>();
	private List<Association> associationsChain = new ArrayList<Association>();	
	private Chain chain;
	private int span = 1;

	public int getSpan() {
		return span;
	}
	public void setSpan(int span) {
		this.span = span;
	}

	public Integer getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(Integer labelWidth) {
		this.labelWidth = labelWidth;
	}
	
	public Integer getFieldWidth() {
		return fieldWidth;
	}
	public void setFieldWidth(Integer fieldWidth) {
		this.fieldWidth = fieldWidth;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public List<Association> getAssociationsChain() {
		return associationsChain;
	}
	public void setAssociationsChain(List<Association> associationsChain) {
		this.associationsChain = associationsChain;
	}
	public List<FlagBase> getFlags() {
		return flags;
	}
	public void setFlags(List<FlagBase> flags) {
		this.flags = flags;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public boolean isRaw() {
		return raw;
	}
	public void setRaw(boolean raw) {
		this.raw = raw;
	}
	public boolean isCustom() {
		return custom;
	}
	public void setCustom(boolean custom) {
		this.custom = custom;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public boolean isPop() {
		return pop;
	}
	public void setPop(boolean pop) {
		this.pop = pop;
	}
	public Chain getChain() {
		return chain;
	}
	public void setChain(Chain chain) {
		this.chain = chain;
	}
	public boolean isExact() {
		return exact;
	}
	public void setExact(boolean exact) {
		this.exact = exact;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSearcher() {
		return searcher;
	}
	public void setSearcher(String searcher) {
		this.searcher = searcher;
	}
	public Attribute getTarget() {
		return target;
	}
	public void setTarget(Attribute target) {
		this.target = target;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getLines() {
		return lines;
	}
	public void setLines(Integer lines) {
		this.lines = lines;
	}
	public Association getAssociation() {
		return association;
	}
	public void setAssociation(Association association) {
		this.association = association;
	}
	public List<String> getAttributesName() {
		return attributesName;
	}
	public void setAttributesName(List<String> attributesName) {
		this.attributesName = attributesName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
