package pt.quintans.bus;

import java.util.Set;

public interface IGenericDAO<T> {
	/**
	 * @param instance
	 */
	public void save(T instance);
	
	/**
	 * @param id
	 * @return
	 */
	public T findById(String id);
	
	/**
	 * @return
	 */
	public Set<T> findAll();
	
	/**
	 * @param instance
	 */
	public void remove(T instance);	
}
