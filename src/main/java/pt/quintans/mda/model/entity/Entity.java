package pt.quintans.mda.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pt.quintans.mda.core.Tools;
import pt.quintans.mda.model.Reference;
import pt.quintans.mda.model.dao.Example;
import pt.quintans.mda.model.dao.Query;

public class Entity extends Element {
	private boolean manyAssociated;
	private boolean singleAssociated;
	private Attribute identity = null;
	private Attribute deleter = null;
	private Attribute version = null;
	private Boolean readOnly = Boolean.FALSE;
    private Boolean nested = Boolean.FALSE;
	private List<Attribute> allAttributes = new ArrayList<Attribute>();
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private List<Attribute> keys = new ArrayList<Attribute>();
	private List<Association> associations = new ArrayList<Association>();
	private List<Index> indexes = new ArrayList<Index>();
    private List<UniqueGroup> uniqueGroups = new ArrayList<UniqueGroup>();
	private List<Operation> operations = new ArrayList<Operation>();
	private List<Query> queries = new ArrayList<Query>();
	private List<Reference> references = new ArrayList<Reference>();
	private List<String> exposedToRoles = new ArrayList<String>();
	private List<String> writingRoles = new ArrayList<String>();
	private List<Example> examples = new ArrayList<Example>();

	@Override
	public void copy(Object o) {
		if (o != null && o instanceof Entity) {
			Entity obj = (Entity) o;
			super.copy(obj);

			setManyAssociated(obj.isManyAssociated());
			setSingleAssociated(obj.isSingleAssociated());
			setIdentity(obj.getIdentity());
			setDeleter(obj.getDeleter());
			setVersion(obj.getVersion());
			setReadOnly(obj.getReadOnly());
			nested = obj.nested;
			setAllAttributes(Tools.duplicateList(obj.getAllAttributes()));
			setAttributes(Tools.duplicateList(obj.getAttributes()));
			setKeys(Tools.duplicateList(obj.getKeys()));
			setAssociations(Tools.duplicateList(obj.getAssociations()));
            setIndexes(Tools.duplicateList(obj.getIndexes()));
			setUniqueGroups(Tools.duplicateList(obj.getUniqueGroups()));
			setOperations(Tools.duplicateList(obj.getOperations()));
			setQueries(Tools.duplicateList(obj.getQueries()));
			setReferences(Tools.duplicateList(obj.getReferences()));
			setExposedToRoles(Tools.duplicateList(obj.getExposedToRoles()));
			setWritingRoles(Tools.duplicateList(obj.getWritingRoles()));
			setExamples(Tools.duplicateList(obj.getExamples()));
		}
	}

	public Entity() {
	}

	public Entity(Entity e) {
		this.copy(e);
	}

	public Example fetchExampleByName(String name) {
		for (Example example : examples)
			if (example.getName().equals(name))
				return example;

		throw new RuntimeException(String.format("Exemplo com o nome %s não foi encontrado", name));
	}

	public Attribute getSingleKey() {
		if (keys.size() == 1)
			return keys.get(0);
		else
			return null;
	}

	public List<Example> getExamples() {
		return examples;
	}

	public void setExamples(List<Example> examples) {
		this.examples = examples;
	}

	public boolean isSingleAssociated() {
		return singleAssociated;
	}

	public void setSingleAssociated(boolean singleAssociated) {
		this.singleAssociated = singleAssociated;
	}

	public boolean isManyAssociated() {
		return manyAssociated;
	}

	public void setManyAssociated(boolean manyAssociated) {
		this.manyAssociated = manyAssociated;
	}

	public Attribute getVersion() {
		return version;
	}

	public void setVersion(Attribute version) {
		this.version = version;
	}

	public Attribute getIdentity() {
		return identity;
	}

	public void setIdentity(Attribute identity) {
		this.identity = identity;
	}

	public Attribute getDeleter() {
		return deleter;
	}

	public void setDeleter(Attribute deleter) {
		this.deleter = deleter;
	}

	public List<String> getExposedToRoles() {
		return exposedToRoles;
	}

	public void setExposedToRoles(List<String> exposedToRoles) {
		this.exposedToRoles = exposedToRoles;
	}

	public List<String> getWritingRoles() {
		return writingRoles;
	}

	public void setWritingRoles(List<String> writingRoles) {
		this.writingRoles = writingRoles;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Boolean getNested() {
        return nested;
    }

    public void setNested(Boolean nested) {
        this.nested = nested;
    }

    public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public List<Attribute> getKeys() {
		return keys;
	}

	public void setKeys(List<Attribute> keys) {
		this.keys = keys;
	}

	public List<Attribute> getAllAttributes() {
		return allAttributes;
	}

	public void setAllAttributes(List<Attribute> allAttributes) {
		this.allAttributes = allAttributes;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public List<Association> getAssociations() {
		return associations;
	}

	public void setAssociations(List<Association> associations) {
		this.associations = associations;
	}

	public List<Query> getQueries() {
		return queries;
	}

	public void setQueries(List<Query> queries) {
		this.queries = queries;
	}

	public List<Reference> getReferences() {
		return references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}

	public List<UniqueGroup> getUniqueGroups() {
		return uniqueGroups;
	}

	public void setUniqueGroups(List<UniqueGroup> uniqueGroups) {
		this.uniqueGroups = uniqueGroups;
	}

	public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public static Entity differentiate(Entity oldEntity, Entity newEntity) {
		boolean diff = false;

		Entity entity = new Entity();

		if (newEntity == null) {
			oldEntity.setDrop(true);
			return oldEntity;
		} else if (oldEntity == null) {
			return newEntity;
		} else
			entity.setOld(oldEntity);

		// newEntity.copy(entity);

		if (!newEntity.getAlias().equals(oldEntity.getAlias())) {
			entity.setAlias(newEntity.getAlias());
			entity.setName(newEntity.getName());
			diff = true;
		}

		// to change and to drop
		List<Attribute> attrs = new ArrayList<Attribute>();
		for (Attribute attribute : oldEntity.getAttributes()) {
			Attribute newAttribute = containsAttribute(attribute, newEntity.getAttributes());
			// se há diferenciação guardada uma copia (old) no próprio item
			if ((newAttribute = Attribute.differentiate(attribute, newAttribute)) != null) {
				attrs.add(newAttribute);
				diff = true;
			}
		}

		// ADD
		for (Attribute attribute : newEntity.getAttributes()) {
			Attribute oldAttribute = containsAttribute(attribute, oldEntity.getAttributes());
			// se há diferenciação é guardada uma copia (old) no próprio item
			if (oldAttribute == null && (oldAttribute = Attribute.differentiate(oldAttribute, attribute)) != null) {
				attrs.add(oldAttribute);
				diff = true;
			}
		}

		entity.setAttributes(attrs);

		// to change and to drop
		List<Association> assocs = new ArrayList<Association>();
		for (Association association : oldEntity.getAssociations()) {
			Association newAssociation = containsAssociation(association, newEntity.getAssociations());
			// se há diferenciação é guardada uma copia (old) no proprio item
			if ((newAssociation = Association.differentiate(association, newAssociation)) != null) {
				assocs.add(newAssociation);
				diff = true;
			}
		}

		// ADD
		for (Association association : newEntity.getAssociations()) {
			Association oldAssociation = containsAssociation(association, oldEntity.getAssociations());
			// se há diferenciação é guardada uma copia (old) no proprio item
			if (oldAssociation == null && (oldAssociation = Association.differentiate(oldAssociation, association)) != null) {
				assocs.add(oldAssociation);
				diff = true;
			}
		}

		entity.setAssociations(assocs);

		if (!diff) {
			return null;
		} else
			return entity;
	}

	private static Attribute containsAttribute(Attribute attrx, List<Attribute> oriAttributes) {
		// por id
		if (attrx.getId() == null)
			throw new RuntimeException(String.format("O atributo \"%s\" não tem id definido.", attrx.toString()));
		else if (attrx.getId().length() < 36)
			return attrx;

		for (Attribute attribute : oriAttributes) {
			if (attribute.getId().equals(attrx.getId()))
				return attribute;
		}

		return null;
	}

	private static Association containsAssociation(Association assocx, List<Association> oriAssociations) {
		if (assocx.getId() == null)
			return null;

		// por id
		if (assocx.getId().length() < 36)
			return assocx;

		for (Association association : oriAssociations) {
			if (association.getId().equals(assocx.getId()))
				return association;
		}

		return null;
	}

	public Attribute fetchAttribute(String attr) {
		Attribute a = fetchEntityAttribute(this, attr);
		if(a == null)
			a = fetchEntityAttribute((Entity)getParent(), attr);
		
		return a;
	}

	private Attribute fetchEntityAttribute(Entity e, String attr) {
		if(e == null)
			return null;
		
		Attribute id = e.getIdentity();
		// verifica se e identity
		if (id != null && id.getName().equals(attr))
			return id;

		for (Attribute attribute : e.getAllAttributes()) {
			if (attribute.getName().equals(attr))
				return attribute;
		}
		
		return null;
	}
	

	private void fetchAssociationChain(String path, List<Association> chain) throws Exception {
		String assoc = null;
		int idx = path.indexOf(".");
		if (idx > 0) {
			assoc = path.substring(0, idx);
		} else
			assoc = path;

		Association a = null;
		for (Association association : associations) {
			if (association.getName().equals(assoc)) {
				a = association;
				chain.add(a);
				break;
			}
		}

		if (a == null)
			throw new Exception(String.format("A associação %s na entidade %s é inválida", assoc, name));

		if (a != null && idx > 0) {
			a.getTarget().fetchAssociationChain(path.substring(idx + 1), chain);
		}
	}

	public List<Association> fetchAssociationChain(String path) throws Exception {
		List<Association> chain = new ArrayList<Association>();
		fetchAssociationChain(path, chain);
		return chain;
	}

	public Association fetchAssociation(String path) throws Exception {
		List<Association> chain = new ArrayList<Association>();
		fetchAssociationChain(path, chain);
		if (chain.size() > 0)
			return chain.get(chain.size() - 1);
		else
			return null;
	}

	public Element fetchElement(String path) {
		String elements[] = path.split("\\.");
		Element et = null;
		Entity entity = this;
		for (int i = 0; i < elements.length; i++) {
			String element = elements[i];
			// verifico se é atributo
			Attribute attr = entity.fetchAttribute(element);
			if (attr != null)
				et = attr;
			else {
				for (Association association : entity.getAssociations()) {
					if (association.getName().equals(element)) {
						if (i == elements.length - 1)
							et = association;
						else
							entity = association.getTarget();
						break;
					}
				}
			}

			if (et == null)
				throw new RuntimeException(String.format("O elemento %s na path %s é inválido", element, path));
		}

		return et;
	}

	@Override
	public String toString() {
		return name;
	}

    @Override
	public void pushWeight(Set<Object> set){
		incWeight();
		if(associations != null){
			for(Association association : associations)
				if(!set.contains(association) && 
						(!association.getMany() && association.getFromTarget().getMany() || 
						!association.getMany() && !association.getFromTarget().getMany() && association.getOwner())){
					set.add(association);
					association.getTarget().pushWeight(set);
				}
		}
	}
	
}
