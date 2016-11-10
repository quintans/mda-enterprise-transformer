package pt.quintans.mda.model.dao;

import pt.quintans.mda.model.entity.Association;
import pt.quintans.mda.model.entity.Entity;

/**
 * representa a as ligaçõeses entre várias associações para chegar a outra entidade
 * ex: 
 * Para um Departamento com vários Empregados, para saber quais os departamentos com empregados com salario maior que 1000,
 * usa-se a relação (Departamento).empregados.salario > 1000;
 * Empregados, gera um join, entre a entidade Departamento e a entidade Empregado.
 * 
 * @author quintans
 *
 */
public class Join {
	private Entity entity;
	private boolean inner;
	private Association association;
		
	public Join(boolean inner, Entity entity, Association association) {
		super();
		this.inner = inner;
		this.entity = entity;
		this.association = association;
	}	

	public boolean isInner() {
		return inner;
	}

	public void setInner(boolean inner) {
		this.inner = inner;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Association getAssociation() {
		return association;
	}

	public void setAssociation(Association association) {
		this.association = association;
	}
	
}
