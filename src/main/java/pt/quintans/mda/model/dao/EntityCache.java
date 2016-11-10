package pt.quintans.mda.model.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;

import pt.quintans.bus.BaseEntity;

public class EntityCache {
    @SuppressWarnings("unchecked")
	private static ThreadLocal<java.util.HashMap<Class, java.util.HashMap<String, ? extends BaseEntity>>> cache = new ThreadLocal<java.util.HashMap<Class, java.util.HashMap<String, ? extends BaseEntity>>>() {
        protected synchronized java.util.HashMap<Class, java.util.HashMap<String, ? extends BaseEntity>> initialValue() {
            return new java.util.HashMap<Class, java.util.HashMap<String, ? extends BaseEntity>>();
        }
    };

    @SuppressWarnings("unchecked")
	public static <T extends BaseEntity> T get(Class<T> clazz, String id) {
    	java.util.HashMap<String, T> map = (HashMap<String, T>) (cache.get()).get(clazz);
    	if(map != null)	    		
    		return (T) map.get(id);
    	else
    		return null;
    }

    @SuppressWarnings("unchecked")
	public static <T extends BaseEntity> void put(T entity) {
    	if(entity != null){    	
	    	HashMap<String, BaseEntity> map = (HashMap<String, BaseEntity>) (cache.get()).get(entity.getClass());
	    	if(map == null){
	    		map = new LinkedHashMap<String, BaseEntity>();
	    		(cache.get()).put(entity.getClass(), map);
	    	}
	
	    	map.put(entity.getId(), entity);
    	}
    }

    @SuppressWarnings("unchecked")
	public static <T extends BaseEntity> T remove(T entity) {
    	if(entity != null){    	
	    	HashMap<String, T> map = (HashMap<String, T>) (cache.get()).get(entity.getClass());
	    	if(map != null)
	        	return (T) map.remove(entity.getId());
    	}
    	
    	return null;
    }
}