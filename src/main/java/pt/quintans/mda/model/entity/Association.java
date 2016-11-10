package pt.quintans.mda.model.entity;

import java.util.List;

import pt.quintans.mda.core.Event;
import pt.quintans.mda.core.EventMediator;
import pt.quintans.mda.core.Model;
import pt.quintans.mda.core.ModelEventListener;
import pt.quintans.mda.core.WorkerStore;
import pt.quintans.mda.raw.domain.FetchType;

public class Association extends Element implements ModelEventListener {
	private Boolean many = false;
	private Entity target;
	private boolean cascade = false;
	private boolean weak = false;
	private Boolean owner = false;
	private boolean navigate = false;
	private Boolean nullable = true;
	private Association fromTarget;
	private Entity entity;
	private String alias;
	private String otherEnd;
	
	/** Usada para permitir não gerar FKs (quando mapeamos uma associação para uma view, por exemplo)*/
	private boolean noForeignKey = false;
	
	private List<Relation> relations;	
	// indica que nao foi definida no modelo. criada por defeito
	private boolean visible = true;
	
	private FetchType fetchType;	

    private List<Constraint> constraints;
    
	public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public FetchType getFetchType() {
		return fetchType;
	}

	public void setFetchType(FetchType fetchType) {
		this.fetchType = fetchType;
	}

	@Override
	public String toString(){
		return name;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Association(Entity entity){
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}

	public String getMany2ManyName(){
		if(owner)
			return alias;
			//return String.format("%s_%s", fromTarget.getName(), name);
		else
			return fromTarget.getAlias();
			//return String.format("%s_%s", name, fromTarget.getName());
	}
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		if(alias != null)
			this.alias = alias;
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		
		if(alias == null)
			alias = name;
	}

	public Boolean getMany() {
		return many;
	}
	public void setMany(Boolean many) {
		this.many = many;
	}

	public Entity getTarget() {
		return target;
	}
	public void setTarget(Entity target) {
		this.target = target;
	}
	
	public boolean isCascade() {
		return cascade;
	}
	public void setCascade(boolean cascade) {
		this.cascade = cascade;
	}
	
	public boolean isWeak() {
		return weak;
	}

	public void setWeak(boolean weak) {
		this.weak = weak;
	}

	public Boolean getOwner() {
		return owner;
	}
	public void setOwner(Boolean owner) {
		this.owner = owner;
	}
	
	public boolean isNavigate() {
		return navigate;
	}
	
	public void setNavigate(boolean navigate) {
		this.navigate = navigate;
	}
	
	public Boolean getNullable() {
		return nullable;
	}
	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}
		
	public String getOtherEnd() {
		return otherEnd;
	}

	public void setOtherEnd(String otherEnd) {
		this.otherEnd = otherEnd;
	}
	
	public boolean isNoForeignKey() {
		return noForeignKey;
	}

	public void setNoForeignKey(boolean noForeignKey) {
		this.noForeignKey = noForeignKey;
	}

	public Association getFromTarget() {
		if(fromTarget == null){
			throw new RuntimeException(String.format("ERROR: Association %s[%s->%s] not found.", getName(), entity.getName(), target.getName()));
			/*
			// builds a default one, where navigable fromtarget is false
			int cnt = 0; // contador para o numero de associa��es encontradas.
			Association association = null;
			for(Association ass : target.getAssociations()){
				if(entity.getName().equals(ass.getTarget().getName())){
					if(otherEnd != null){
						if(otherEnd.equals(ass.getName())){							
							association = ass;
							break;
						}
					} else {
						association = ass;
						cnt++;
					}
				}
			}

			// foi encontrada mais do que uma possivel associa��o
			if(cnt > 1)
				throw new RuntimeException(String.format("More than one possible matching association was found, in the entity \"%s\" for association \"%s\"\nUse attribute \"otherEnd\" to define the matching association", target.getName(), association.getName()));
			
			if(association == null){
		    	if(!WorkerStore.get().isQuiet())
					System.out.println(String.format("WARNING: Association %s-%s[%s] not found. Creating default.", target.getName(), entity.getName(), getName()));

				Association a = new Association(target);
				//associationMap.put(key, a);
				target.getAssociations().add(a);
				a.setFromTarget(this);
				a.setTarget(entity);
				a.setNavigate(false);
				a.setMany(!this.getMany());
				a.setName(entity.getName().toLowerCase());
				association = a;
			}
			
			fromTarget = association; 
			*/
		}
		
		return fromTarget;
	}
	
	public void setFromTarget(Association fromTarget) {
		this.fromTarget = fromTarget;
	}

	private void link(Association a, Association b, Event event){
	    if(!WorkerStore.get().isQuiet())
	    	System.out.println(String.format("Associating %s[%s]-%s[%s] on event %s.", a.getEntity().getName(), a.getName(), b.getEntity().getName(), b.getName(), event.getType()));
		a.setFromTarget(b);
	}
	
	@Override
	public void onEvent(Event event) {
		if(EventMediator.EVT_MODEL_CONFIGURED.equals(event.getType())){
			//System.out.println(String.format("A definir a propriedade fromTarget da associacao %s.%s no event %s.", entity.getName(), getName(), event.getType()));

			Model model = (Model) event.getData();		
			if(fromTarget == null){
				int cnt = 0; // contador para o numero de associações encontradas.
				Entity targetEntity = (Entity) model.getTransformedObject(target.getName());
				Association association = null;
				//System.out.println(String.format("DEBUG: a linkar as associações %s -> %s.", entity.getName(), e.getName()));
				for(Association reverseAssociation : targetEntity.getAssociations()){
					//System.out.println(String.format("DEBUG: check de %s <- %s.%s[%s]",	entity.getName(), e.getName(), ass.getName(), ass.getTarget().getName()));
					if(entity.getName().equals(reverseAssociation.getTarget().getName())){
						//System.out.println(String.format("DEBUG: check de %s.%s.", e.getName(), ass.getName()));
						if(otherEnd != null){
							if(otherEnd.equals(reverseAssociation.getName())){
								association = reverseAssociation;
								break;
							}
						} else if(reverseAssociation.getOtherEnd() != null) {
							if(reverseAssociation.getOtherEnd().equals(this.getName())){
								association = reverseAssociation;
								break;
							}
						} else {
//							System.out.println(String.format("Discovered reverse association %s.%s for %s.%s.", 
//									targetEntity.getName(), reverseAssociation.getName(),
//									entity.getName(), this.getName()));
							association = reverseAssociation;
							cnt++;
						}
					}
				}
	
				// foi encontrada mais do que uma possivel associação
				if(cnt > 1) {
					throw new RuntimeException(
					        String.format("More than one possible matching association was found, in the entity \"%s\" for association \"%s.%s\"\nUse attribute \"otherEnd\" to define the matching association", 
					                target.getName(), entity.getName(), this.getName())
					                );
				}
				
				if(association != null)
					link(this, association, event);
				else {
				    System.out.println(String.format("WARNING: The reverse side of the association %s.%s->%s wasn't found. Creating default.", entity.getName(), getName(), target.getName()));
	
					association = new Association(target);
					association.setId("0");
					//associationMap.put(key, a);
					target.getAssociations().add(association);
					this.setFromTarget(association);
					association.setFromTarget(this);
					association.setTarget(entity);
					association.setMany(!this.getMany());
					fromTarget = association;
					
					if(this.getOtherEnd() == null)
						throw new RuntimeException(String.format("Since this is the default creation, attribute \"otherEnd\" has to be defined for association %s.%s", entity.getName(), this.getName()));
					association.setName(this.getOtherEnd());
					association.setOtherEnd(this.getName());
					association.setVisible(false);
					association.setNavigate(true);
					target.addDependency(entity);
					
					// copy constraints if none is defined
					if(constraints == null && association.getConstraints() != null) {
					    constraints = association.getConstraints();
					}
				}
				
				if(fromTarget != null){
					// check if a many2many has owner
					if(this.getMany() && fromTarget.getMany() && !this.getOwner() && !fromTarget.getOwner())
						throw new RuntimeException("Association [" + entity.getName() + "]." + this.getName() + "-[" + target.getName() + "]." + fromTarget.getName() + " needs to define the owner because its a many to many relanship.");
				}
			}
			
//			if(relations != null){
//				List<Attribute> keys = getTarget().getKeys();
//				if(relations.size() != keys.size()){ // não faz sentido porque posso querer associações ad-doc
//					throw new RuntimeException(String.format("A associacao %s na entidade %s tem um numero invalido de parametros.", getName(), getEntity().getName()));
//				} else {
//					List<Relation> ordered = new ArrayList<Relation>();
//					// ordena as relações de acordo com a ordem encontrada na entidade destino
//					//System.out.println(String.format("A ordenar as relacoes de %s.%s [keys: %s, relations: %s]", getEntity().getName(), getName(), keys.size(), relations.size()));
//					boolean ignoreOrder = false;
//					for(Attribute key : keys){
//						boolean found = false;
//						for(Relation relation : relations){
//							if(relation.getKey().getName().equals(key.getName())){
//								//System.out.println(String.format("-> %s", key.getName()));
//								ordered.add(relation);
//								found = true;
//							}
//						}
//						/*
//						if(!found)
//							throw new RuntimeException(String.format("Nao foi encontrada a correspondencia para a chave em %s.%s", 
//									key.getName(), getEntity().getName(), getName()));						
//						*/
//						if(!found){
//							ignoreOrder = true;
//							System.out.println(String.format("A IGNORAR ORDENACAO DAS RELACOES -> Nao foi encontrada a correspondencia para a chave em %s.%s a partir de %s.", 
//									getTarget().getName(), key.getName(), getEntity().getName(), getName()));
//							break;
//						}
//							
//					}
//					if(!ignoreOrder)
//						setRelations(ordered);
//				}
//			}
		}		
	}
		
	public static Association differentiate(Association oldAssociation, Association newAssociation){
		boolean diff = false;
		
		Association association = null;

		if(newAssociation == null){
			oldAssociation.setDrop(true);
			return oldAssociation;
		} else if(oldAssociation == null){
			return newAssociation;
		} else {
			association = new Association(newAssociation.getEntity());
			association.setOld(oldAssociation);
		}
		
		if(!newAssociation.getAlias().equals(oldAssociation.getAlias())){
			association.setAlias(newAssociation.getAlias());
			association.setName(newAssociation.getName());
			diff = true;
		} 
		
		if(newAssociation.getOwner() != oldAssociation.getOwner()){
			association.setOwner(newAssociation.getOwner());
			diff = true;
		}

		if(newAssociation.getNullable() != oldAssociation.getNullable()){
			association.setNullable(newAssociation.getNullable());
			diff = true;
		}

		if(newAssociation.getMany() != oldAssociation.getMany()){
			association.setMany(newAssociation.getMany());
			diff = true;
		}
		
		if(!newAssociation.getTarget().getAlias().equals(oldAssociation.getTarget().getAlias())){
			association.setTarget(newAssociation.getTarget());
			diff = true;
		}

		if(diff){
			return association;
		} else
			return null;
	}
	
}
