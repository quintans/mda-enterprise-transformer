package pt.quintans.mda.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.quintans.mda.core.MRoot;
import pt.quintans.mda.core.Tools;
import pt.quintans.mda.raw.domain.Basic;

public class Element extends MRoot {
	// usados na migração
	// indica que este elemento foi eliminado
	protected boolean drop = false;
	// se um elemento tem, estamos numa situação de alteração. 
	// Só os elementos com alterações, têm esta propriedade preenchida
	protected Element old = null;  
	// se nenhum dos anteriores se verifica, então é elemento novo
	
	protected String id;
	protected String stereotypeAlias;
	protected String alias;
	protected String description;
	protected String comments;
	protected List<Element> dependencies;
	protected Map<String, String> behaviors;
	protected Boolean root = false; 
	
	public void copy(Object o) {
		if(o != null && o instanceof Element){
			Element obj = (Element) o;

			setDrop( obj.isDrop() );
			setOld( obj.getOld() );
			setId( obj.getId() );
			setNamespace( obj.getNamespace() );
			if(obj.getSubnamespaces() != null ){
				String[] sn = obj.getSubnamespaces();
				String[] tmp = new String[sn.length];
				System.arraycopy(sn, 0, tmp, 0, sn.length);
				setSubnamespaces(tmp);
			}
			setStereotype( obj.getStereotype() );
			setStereotypeAlias( obj.getStereotypeAlias() );
			setName( obj.getName() );
			setAlias( obj.getAlias() );
			setDescription( obj.getDescription() );
			setComments( obj.getComments() );
			setLocation( obj .getLocation() );
			setParent( obj.getParent() );
			setDependencies(Tools.duplicateList(obj.getDependencies()));
			setBehaviors(new HashMap<String, String>(behaviors));
			setRoot( obj.getRoot() );
		}
	}
	
	public List<String> getBehaviors() {
		if(behaviors != null){
			List<String> b = new ArrayList<String>();
			for(String k : behaviors.keySet())
				b.add(k);
			return b;
		} else
			return null;
	}

	public void setBehaviors(Map<String, String> behaviors) {
		this.behaviors = behaviors;
	}

	public void setBehaviors(String behaviors) {
		if(behaviors != null && !behaviors.isEmpty()){
			this.behaviors = new HashMap<String, String>();
			
			String[] entries = behaviors.split(",");
			for(String entry : entries){
				String[] kv = entry.trim().split("=");
				String k = kv[0].trim();
				String v = "";
				if(kv.length > 1)
					v = kv[1].trim();
				this.behaviors.put(k, v);
			}
		}
	}
	
	public boolean hasBehavior(String behavior){
		if(behaviors != null){
			return behaviors.containsKey(behavior);
		} else
			return false;
	}

	public String behaviorValue(String behavior){
		if(behaviors != null){
			return behaviors.get(behavior);
		} else
			return null;
	}

	public String[] behaviorValues(String behavior){
		if(behaviors != null){
			String value = behaviors.get(behavior);
			if(value != null){
				return value.split("+");
			}
		} 

		return null;
	}

	public void loadBasic(Basic basic){
		namespace = basic.getNamespace();
		location = basic.getLocation();
		
	    name = basic.getName();
	    alias = basic.getAlias();
	    description = basic.getDescription() != null ? basic.getDescription() : name;
	    comments = basic.getComments();

	}
	
	public List<Element> getDependencies() {
		return dependencies;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setDependencies(List<Element> dependencies) {
		this.dependencies = dependencies;
	}
	
	public void addDependency(Element element){
		if(element.getName().equals(getName())){
			return;
		}
		
		if(dependencies == null)
			dependencies = new ArrayList<Element>();
		
		if(!dependencies.contains(element))
			dependencies.add(element);
	}	


	public String getStereotype() {
		return stereotype;
	}

	public void setStereotype(String stereotype) {
		this.stereotype = stereotype;
	}

	public String getStereotypeAlias() {
		return stereotypeAlias;
	}

	public void setStereotypeAlias(String stereotypeAlias) {
		this.stereotypeAlias = stereotypeAlias;
	}

	public boolean isDrop() {
		return drop;
	}

	public void setDrop(boolean drop) {
		this.drop = drop;
	}

	public Element getOld() {
		return old;
	}

	public void setOld(Element old) {
		this.old = old;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}


	public Boolean getRoot() {
		return root;
	}

	public void setRoot(Boolean root) {
		this.root = root;
	}
	
	
}
