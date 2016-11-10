package pt.quintans.mda.model.dao;

import java.util.ArrayList;
import java.util.List;

public class Example {
	private String name = "";
	private List<ExampleTarget> targets = new ArrayList<ExampleTarget>();
	private boolean customizable = false;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExampleTarget> getTargets() {
		return targets;
	}

	public void setTargets(List<ExampleTarget> targets) {
		this.targets = targets;
	}
	
	public boolean isCustomizable() {
		return customizable;
	}

	public void setCustomizable(boolean customizable) {
		this.customizable = customizable;
	}

	@Override
	public String toString(){
		return name;
	}
	
}
