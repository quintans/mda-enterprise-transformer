package pt.quintans.bus;

import java.util.*;

public class AssociationSet<T> extends AbstractSet<T> implements Set<T>, Cloneable {
 
   private transient HashMap<T, String> map;
 
   public AssociationSet() {
     map = new LinkedHashMap<T, String>();
   }
 
   public AssociationSet(Collection<T> c) {
     map = new LinkedHashMap<T, String>(c.size());
     addAll(c);
   }
 
   public AssociationSet(int initialCapacity) {
     map = new LinkedHashMap<T, String>(initialCapacity);
   }
 
   public AssociationSet(int initialCapacity, float loadFactor) {
     map = new LinkedHashMap<T, String>(initialCapacity, loadFactor);
   }
 
   public boolean add(T o) {
     Object old = map.put(o, "");
     return (old == null);
   }
 
   public void clear() {
     map.clear();
   }
 
   public Object clone() {
     return new HashSet<T>  (this);
   }
 
   public boolean contains(Object   o) {
     return map.containsKey(o);
   }
 
   public boolean isEmpty() {
     return map.isEmpty();
   }
 
   public Iterator<T> iterator() {
     return map.keySet().iterator();
   }
 
   public boolean remove(Object   o) {
     return (map.remove(o) != null);
   }
 
   public int size() {
     return map.size();
   }
 
   public String toString() {
     return map.keySet().toString();
   }
}
