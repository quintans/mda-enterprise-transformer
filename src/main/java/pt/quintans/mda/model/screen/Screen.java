package pt.quintans.mda.model.screen;

import java.util.ArrayList;
import java.util.List;

import pt.quintans.mda.model.dao.Example;
import pt.quintans.mda.model.entity.Association;
import pt.quintans.mda.model.entity.Attribute;
import pt.quintans.mda.model.entity.Element;
import pt.quintans.mda.model.entity.Entity;

public class Screen  extends Element {
	private Entity entity;
	private Association association;
	private Example example;
	
	private List<Attribute> usedAttributes = new ArrayList<Attribute>();

	private Association headAssociation;
	private View head;
	private List<View> searches = new ArrayList<View>();
	private List<View> results = new ArrayList<View>();
	private List<View> displays = new ArrayList<View>();
	
	private List<Popless> poplesses = new ArrayList<Popless>();
	private List<Field> poplessFields = new ArrayList<Field>();
	
	private List<Navigation> navigations = new ArrayList<Navigation>();	
	private List<Order> orders = new ArrayList<Order>();	
	
	private boolean softDelete;
	private boolean canDelete;
	private boolean canSave;
	
	private Integer resultPageSize = 30;
	private Boolean resultSelectable = false;
	private Boolean resultSortable = false;

	private Boolean embededDisplay = false;	
	
	private Integer defaultOrderIndex;
	
	private boolean editable;

	public Boolean getResultSortable() {
		return resultSortable;
	}

	public void setResultSortable(Boolean resultSortable) {
		this.resultSortable = resultSortable;
	}

	public Boolean getResultSelectable() {
		return resultSelectable;
	}

	public void setResultSelectable(Boolean resultSelectable) {
		this.resultSelectable = resultSelectable;
	}

	public boolean isCanSave() {
		return canSave;
	}

	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Association getAssociation() {
		return association;
	}

	public void setAssociation(Association association) {
		this.association = association;
	}

	public Integer getDefaultOrderIndex() {
		return defaultOrderIndex;
	}

	public void setDefaultOrderIndex(Integer defaultOrderIndex) {
		this.defaultOrderIndex = defaultOrderIndex;
	}

	public Association getHeadAssociation() {
		return headAssociation;
	}

	public void setHeadAssociation(Association headAssociation) {
		this.headAssociation = headAssociation;
	}

	public View getHead() {
		return head;
	}

	public void setHead(View head) {
		this.head = head;
	}

	public Boolean getEmbededDisplay() {
		return embededDisplay;
	}

	public void setEmbededDisplay(Boolean embededDisplay) {
		this.embededDisplay = embededDisplay;
	}

	public Integer getResultPageSize() {
		return resultPageSize;
	}

	public void setResultPageSize(Integer resultPageSize) {
		this.resultPageSize = resultPageSize;
	}

	public boolean isSoftDelete() {
		return softDelete;
	}

	public void setSoftDelete(boolean softDelete) {
		this.softDelete = softDelete;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Field> getPoplessFields() {
		return poplessFields;
	}

	public List<Popless> getPoplesses() {
		return poplesses;
	}

	public void addPoplesses(Field field) {
		poplessFields.add(field);
		
		Popless popless = new Popless(field.getSearcher(), field.getAssociation());
		
		for(Popless f : poplesses)
			if(f.getSearcher().equals(popless.getSearcher()))
				return;
		
		this.poplesses.add(popless);
	}

	public Example getExample() {
		return example;
	}
	
	public void setExample(Example example) {
		this.example = example;
	}
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public List<Attribute> getUsedAttributes() {
		return usedAttributes;
	}
	public void setUsedAttributes(List<Attribute> usedAttributes) {
		this.usedAttributes = usedAttributes;
	}
	
	public void addUsedAttributes(Attribute attribute){
		if(!usedAttributes.contains(attribute))
			usedAttributes.add(attribute);
	}
	
	public List<View> getSearches() {
		return searches;
	}
	public void setSearches(List<View> searches) {
		this.searches = searches;
	}
	public List<View> getResults() {
		return results;
	}
	public void setResults(List<View> results) {
		this.results = results;
	}
	public List<View> getDisplays() {
		return displays;
	}
	public void setDisplays(List<View> displays) {
		this.displays = displays;
	}
	public List<Navigation> getNavigations() {
		return navigations;
	}
	public void setNavigations(List<Navigation> navigations) {
		this.navigations = navigations;
	}

	
}
