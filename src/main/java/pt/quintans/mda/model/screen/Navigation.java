package pt.quintans.mda.model.screen;

public class Navigation {
	private Screen target;
	private String name;
	private String description;
	private String tip;
	
	public Screen getTarget() {
		return target;
	}
	public void setTarget(Screen target) {
		this.target = target;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}	

	
}
