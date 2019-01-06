package pt.quintans.mda.enterprise.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pt.quintans.mda.core.Model;
import pt.quintans.mda.core.Model2TextAbstract;
import pt.quintans.mda.core.Work;
import pt.quintans.mda.core.WorkerStore;
import pt.quintans.mda.enterprise.model.entity.Element;
import pt.quintans.mda.enterprise.model.entity.Entity;
import pt.quintans.mda.transformers.PipelineKeys;

public class AllInOneMigrateM2T extends Model2TextAbstract {
	private static final String MIGRATE_KEY = "Migrate";
	private static final String OLD_ENTITY_KEY = "OldEntities";
	private static final String NEW_ENTITY_KEY = "NewEntities";
	
	@SuppressWarnings("unchecked")
	@Override
	public void transform(List<Object> mappings) {
		loadMapping(mappings);
		prepare();
		
		Work work =  WorkerStore.get();
		Map<String, Object> pipeline = (Map<String, Object>) work.getPipeline();
		Model allModels[] = getFromPipe(PipelineKeys.ALL_MODELS);
		
		if(allModels.length > 1){
			List<Entity> migrate = (List<Entity>) pipeline.get(MIGRATE_KEY);

			if(migrate == null){ // not yet transformed
				migrate = new ArrayList<Entity>();
				pipeline.put(MIGRATE_KEY, migrate);

				List<Object> newElements = allModels[0].getTransformedObjectList(getStereotype());
				pipeline.put(NEW_ENTITY_KEY, newElements);
				List<Object> oldElements = allModels[1].getTransformedObjectList(getStereotype());
				pipeline.put(OLD_ENTITY_KEY, oldElements);
				// to changed or to add
				for(Object obj : newElements){
					Element element = (Element) obj;
					Element oldElement = containsElement(element, oldElements);
					if((element = Entity.differentiate((Entity)oldElement, (Entity)element)) != null){
						migrate.add((Entity)element);
					}
				}
				// to drop
				for(Object obj : oldElements){
					Element element = (Element) obj;
					Element newElement = containsElement(element, newElements);
					if(newElement == null && (element = Entity.differentiate((Entity)element, (Entity)newElement)) != null){
						migrate.add((Entity)element);
					}
				}
			}

		    String destinationFile = getDestination();
			
		    putInPipe(PipelineKeys.DESTINATION_FILE, destinationFile);
		    
		    if(!work.isQuiet())
		    	System.out.println(String.format("Generating <<%s>> [%s] => %s", getStereotype(), getTemplate(), destinationFile));						

		    dumpToFile(destinationFile);
		}  
		
	}

	private static Element containsElement(Element entity, List<Object> entities){
		// por id
		if(entity.getId().length() < 36)
			return entity;

		for(Object obj : entities){
			Element ele = (Element) obj;
			if(ele.getId().equals(entity.getId()))
				return ele;
		}
		
		return null;
	}
}
