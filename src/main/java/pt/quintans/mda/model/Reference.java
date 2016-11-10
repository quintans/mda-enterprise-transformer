package pt.quintans.mda.model;

import pt.quintans.mda.model.entity.Element;

public class Reference {
	protected String name;
	protected Element model;
	protected boolean single;
	protected boolean paginate;

	protected boolean toEntity;
	protected boolean toDTO;
	
	public Reference(String name, Element model, boolean single) {
		super();
		this.name = name;
		this.model = model;
		this.single = single;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Element getModel() {
		return model;
	}

	public void setModel(Element model) {
		this.model = model;
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	public boolean isPaginate() {
		return paginate;
	}

	public void setPaginate(boolean paginate) {
		this.paginate = paginate;
	}

	public boolean isToEntity() {
		return toEntity;
	}

	public void setToEntity(boolean toEntity) {
		this.toEntity = toEntity;
	}

	public boolean isToDTO() {
		return toDTO;
	}

	public void setToDTO(boolean toDTO) {
		this.toDTO = toDTO;
	}	
	
	
}
