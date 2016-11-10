package pt.quintans.mda.custom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pt.quintans.mda.core.AbstractTransformer;
import pt.quintans.mda.core.ElementType;
import pt.quintans.mda.core.Event;
import pt.quintans.mda.core.EventMediator;
import pt.quintans.mda.core.Model;
import pt.quintans.mda.core.ModelElement;
import pt.quintans.mda.core.WorkerStore;
import pt.quintans.mda.model.dao.Example;
import pt.quintans.mda.model.dto.DataTranferObject;
import pt.quintans.mda.model.entity.Association;
import pt.quintans.mda.model.entity.Attribute;
import pt.quintans.mda.model.entity.Entity;
import pt.quintans.mda.model.entity.FlagBool;
import pt.quintans.mda.model.entity.FlagLov;
import pt.quintans.mda.model.entity.Flags;
import pt.quintans.mda.model.entity.Relation;
import pt.quintans.mda.model.screen.Chain;
import pt.quintans.mda.model.screen.Column;
import pt.quintans.mda.model.screen.Field;
import pt.quintans.mda.model.screen.Navigation;
import pt.quintans.mda.model.screen.Order;
import pt.quintans.mda.model.screen.Screen;
import pt.quintans.mda.model.screen.View;
import pt.quintans.mda.raw.domain.Basic;
import pt.quintans.mda.raw.domain.ChainType;
import pt.quintans.mda.raw.domain.FieldType;
import pt.quintans.mda.raw.domain.NavigationType;
import pt.quintans.mda.raw.domain.OrderType;
import pt.quintans.mda.raw.domain.ViewType;

public class ScreenTransformer extends AbstractTransformer {

	public Screen instanciate() {
		return new Screen();
	}
	
	@Override
	public void create(Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

		if(me.getTransformed() != null)
			return;

		if(!WorkerStore.get().isQuiet())
			System.out.println(String.format("Transforming <<%s>> %s", stereotype, objectName));

		Basic basic = me.getSource();

		Screen screen = instanciate();
		me.setTransformed(screen);
		//screen.setName(objectName);
		screen.loadBasic(basic);
		screen.setStereotype(getStereotype());
		screen.setStereotypeAlias(getStereotypeAlias());

		pt.quintans.mda.raw.domain.Screen modelScreen = (pt.quintans.mda.raw.domain.Screen) basic;			
		screen.setSoftDelete(modelScreen.getSoftDelete());
		screen.setCanDelete(modelScreen.getCanDelete());		
		screen.setCanSave(modelScreen.getCanSave());		
	}

	@Override
	public void relate(Model model, String objectName) {
		ModelElement me = model.getModelElement(objectName);

	    if(!WorkerStore.get().isQuiet())
	    	System.out.println(String.format("Relating <<%s>> %s", stereotype, objectName));

		Basic basic = me.getSource();
		pt.quintans.mda.raw.domain.Screen modelScreen = (pt.quintans.mda.raw.domain.Screen) basic;
		String entName = modelScreen.getEntity();
		String assocName = null;
		int x;
		if((x = modelScreen.getEntity().indexOf(".")) > 0){
			assocName = entName.substring(x + 1);
			entName = entName.substring(0, x);
		}
		Screen screen = (Screen) me.getTransformed();
		Entity entity = (Entity) model.getTransformedObject(entName);
		if(entity == null)
			throw new RuntimeException(String.format("A entidade %s não foi encontrado em %s", modelScreen.getEntity(), objectName));
		
		// copia
		entity = new Entity(entity);
		// adiciono os atributos e associações
		if(entity.getParent() != null){
			Entity parent = (Entity) entity.getParent();
			entity.getAttributes().addAll(parent.getAttributes());
			entity.getAllAttributes().addAll(parent.getAllAttributes());
			entity.getAssociations().addAll(parent.getAssociations());
		}
		
		if(assocName != null){
			try {
				screen.setAssociation( entity.fetchAssociation(assocName) );
			} catch (Exception e) {
				throw new RuntimeException(String.format("A associação '%s' não foi encontrada no ecra '%s'", assocName, objectName), e);
			}
		}
		screen.setEntity(entity);

		if(modelScreen.getHead() != null){
			screen.setHead(loadView(screen, modelScreen.getHead().getView(), entity, objectName, false, true).get(0));
			if(modelScreen.getHead().getTarget() != null && !modelScreen.getHead().getTarget().equals("")){
				try {
					screen.setHeadAssociation( entity.fetchAssociation( modelScreen.getHead().getTarget().substring(1) ) );
				} catch (Exception e) {
					throw new RuntimeException(String.format("A associação '%s' não foi encontrada no ecra '%s'", modelScreen.getHead().getTarget(), objectName), e);
				}
			}
		}

		if(modelScreen.getSearch() != null)
			screen.setSearches(loadView(screen, modelScreen.getSearch().getView(), entity, objectName, true, false));
		if(modelScreen.getResult() != null){
			screen.setResults(loadView(screen, modelScreen.getResult().getView(), entity, objectName, false, false));
			screen.setResultPageSize(modelScreen.getResult().getPageSize());
			screen.setResultSelectable(modelScreen.getResult().getSelectable());
			screen.setResultSortable(modelScreen.getResult().getSortable());
		}
		if(modelScreen.getDisplay() != null){
			screen.setDisplays(loadView(screen, modelScreen.getDisplay().getView(), entity, objectName, false, false));
			screen.setEmbededDisplay(modelScreen.getDisplay().getEmbeded());
		}


		// Navigations
		if(modelScreen.getNavigations() != null){
			for(NavigationType nt : modelScreen.getNavigations().getNavigation()){
				Navigation navigation = new Navigation();
				screen.getNavigations().add(navigation);
				navigation.setTarget( (Screen) model.getTransformedObject(nt.getTarget()) );
				if(navigation.getTarget() == null)
					throw new RuntimeException(String.format("O target '%s' da navegação não foi encontrado em %s", nt.getTarget(), objectName));

				navigation.setName(nt.getName());
				navigation.setDescription(nt.getDescription() != null ? nt.getDescription() : nt.getName());
				navigation.setTip(nt.getTip());
			}

		}

		// orders
		if(modelScreen.getOrders() != null){
			int index = 0;
			for(OrderType ot : modelScreen.getOrders().getOrder()){
				Order order = new Order();
				screen.getOrders().add(order);
				order.setDescription(ot.getDescription());

				index++;
				if(ot.getDefault())
					screen.setDefaultOrderIndex(index);

				String path = ot.getTarget();
				Entity e = entity;
				if(path.startsWith(".")){
					// remove o ultimo, pois e' um campo
					path = path.substring(1, path.lastIndexOf("."));
					try {
						List<Association> assocs = entity.fetchAssociationChain(path);
						for(Association assoc : assocs)
							screen.addDependency(assoc.getTarget());

						order.setAssociationPath(assocs);
						Association assoc = assocs.get(assocs.size() - 1);
						e = assoc.getTarget();
					} catch (Exception ex) {
						throw new RuntimeException(String.format("O target '%s' da navegaço não foi encontrado em %s", path, objectName));
					}
					path = ot.getTarget().substring(ot.getTarget().lastIndexOf(".")+1);
				} else if(screen.getAssociation() != null){
					e = screen.getAssociation().getTarget();
				}

				for(Attribute attribute : e.getAllAttributes()){
					if(attribute.getName().equals(path)){
						order.setTarget(attribute);
						break;
					}
				}
				if(order.getTarget() == null)
					throw new RuntimeException(String.format("O target '%s' da navegação não foi encontrado em %s", ot.getTarget(), objectName));

			}

			if(screen.getDefaultOrderIndex() == null)
				screen.setDefaultOrderIndex(1);
		}

	}

	private void setMaxWidth(List<Column> columns, Chain chain){
		// redimensiona se necessário
		if(columns.size() < chain.getFields().size()){
			for(int i = columns.size(); i < chain.getFields().size(); i++ ){
				columns.add(new Column());
			}
		}

		Iterator<Column> iterator = columns.iterator();

		for(Field field : chain.getFields()){
			Column column = iterator.next();

			if(field.getLabelWidth() != null){
				column.setLabelWidth( Math.max(column.getLabelWidth(), field.getLabelWidth()) );
			}

			if(field.getFieldWidth() != null){
				column.setFieldWidth( Math.max(column.getFieldWidth(), field.getFieldWidth()) );
			}

			if(field.getWidth() != null){
				column.setWidth( Math.max(column.getWidth(), field.getWidth()) );
			}
		}

	}

	private List<View> loadView(Screen screen, List<ViewType> viewTypes, Entity entity, String objectName, boolean search, boolean head){
		List<View> views = new ArrayList<View>();
		if(viewTypes != null && viewTypes.size() > 0){
			for(ViewType vt : viewTypes){
				View view = new View();
				views.add(view);
				view.setName(vt.getName());
				view.setDescription(vt.getDescription());
				List<Column> columns = new ArrayList<Column>();
				view.setColumns(columns);

				Integer maxColumn = 1;
				for(ElementType et : vt.getAttributesList()){
					Chain chain = new Chain();
					// os "fields" isolados são envolvidos num "chain"
					if(et instanceof FieldType){						
						FieldType ft = (FieldType) et;
						Field field = addField(ft, null, screen, entity, view.getName(), objectName, search, head);
						view.setEditable(view.isEditable() || field.isEditable());
						chain.setDescription(field.getDescription());
						chain.setName( field.getName() + "$" );
						chain.addField(field);
						view.getChains().add( chain );
					} else if(et instanceof ChainType){
						ChainType ct = (ChainType) et;
						chain.setName(ct.getName());
						chain.setDescription(ct.getDescription());
						chain.setCompact(ct.getCompact());
						for(FieldType ft : ct.getField()){
							Field field = addField(ft, chain, screen, entity, view.getName(), objectName, search, head);
							view.setEditable(view.isEditable() || field.isEditable());
							chain.addField( field );
						}
						view.getChains().add( chain );
						// determina máximo dos fields numa chain
						maxColumn = Math.max(maxColumn, chain.getFields().size());
					}
					// maximo width por column
					setMaxWidth(columns, chain);
				}
				view.setMaxColumn(maxColumn);
				if(view.isEditable())
					screen.setEditable(true);
			}
		} else {
			// usa todos os campos
			View view = new View();
			views.add(view);
			view.setName("");
			for(Attribute attr : entity.getAttributes()){
				screen.addUsedAttributes(attr);
				Field field = new Field();
				field.setTarget(attr);
				field.setWidth(attr.getLength());
				field.setLength(attr.getLength());

				Chain chain = new Chain();
				chain.setDescription(field.getDescription());
				chain.setName( field.getName() + "$" );
				chain.addField( field );
				view.getChains().add( chain );				
			}
			if(view.isEditable())
				screen.setEditable(true);
		}

		return views;
	}

	private Field addField(FieldType ft, Chain chain, Screen screen, Entity entity, String viewName, String objectName, boolean search, boolean head){		
		Field field = new Field();
		field.setChain(chain);
		if(ft.getName() != null){
			field.setName(ft.getName());
		} else if(ft.getTarget().startsWith("."))
			field.setName(ft.getTarget().substring(1));
		else
			field.setName(ft.getTarget());

		field.setSpan(ft.getSpan());

		field.setLabelWidth(ft.getLabelWidth());
		field.setFieldWidth(ft.getFieldWidth());
		field.setWidth(ft.getWidth() != null ? ft.getWidth() : ft.getFieldWidth());
		field.setLength(ft.getLength() != null ? ft.getLength() : ft.getWidth());
		field.setDescription(ft.getDescription());

		field.setCustom(ft.getCustom());		
		/*
		if(field.getCustom())
			return field;
			*/

		field.setReadOnly(head || ft.getReadonly());
		field.setExact(ft.getExact());
		field.setLines(ft.getLines());
		field.setSearcher(ft.getSearcher() != null ? ft.getSearcher() : ft.getName()); // para a pop up lov
		field.setAlias(ft.getAlias() != null ? ft.getAlias() : field.getDescription());
		field.setShow(ft.getShow());
		field.setRaw(ft.getRaw());
		field.setEditable(ft.getEditable());
		field.setPop(ft.getPop());
		if(ft.getDefaultValue() != null)
			field.setDefaultValue(ft.getDefaultValue());

		if(!ft.getTarget().equals("")){			
			if(!ft.getTarget().startsWith(".")){
				Entity e;
				if(screen.getAssociation() != null){
					e = screen.getAssociation().getTarget();
				} else {
					e = entity;
				}
				for(Attribute attr : e.getAllAttributes()){
					if(attr.getName().equals(ft.getTarget())){
						screen.addUsedAttributes(attr);
						field.setTarget(attr);
						if(search)
							field.setRequired( ft.getRequired() == null ? false : ft.getRequired() );
						else
							field.setRequired(ft.getRequired() == null ? (!attr.getNullable() || attr.getKey()) : ft.getRequired());
						/*
						if(field.getWidth() == null)
							field.setWidth(attr.getLength());
						if(field.getLength() == null)
							field.setLength(field.getWidth());
							*/
						break;
					}
				}
				// verifica se é flags
				if(field.getTarget() != null && ft.getSubTarget() != null){
					Flags flags = (Flags) field.getTarget();
					// tenta descobrir qual o subitem
					boolean found = false;
					for(FlagBool fb : flags.getBools()){
						if(fb.getName().equals(ft.getSubTarget())){
							field.getFlags().add(fb);
							found = true;
							break;
						}	
					}
					if(!found) {
						for(FlagLov fl : flags.getLovs()){
							if(fl.getName().equals(ft.getSubTarget())){
								field.getFlags().add(fl);
								found = true;
								break;
							}	
						}
					}					
					if(!found)
						throw new RuntimeException(String.format("O subTarget '%s' do target '%s' na view '%s' n�o foi encontrado na entidade '%s' do ecra %s", 
								ft.getSubTarget(), viewName, ft.getTarget(), e.getName(), objectName));						
				}
				// verifica se é identity
				if(field.getTarget() == null && 
						e.getIdentity() != null &&  
						e.getIdentity().getName().equals(ft.getTarget())){
					if(search)
						field.setRequired(ft.getRequired() == null ? false : ft.getRequired());
					else
						field.setRequired(true && (ft.getRequired() == null ? true : ft.getRequired()));
					screen.addUsedAttributes(e.getIdentity());
					field.setTarget(e.getIdentity());
				} 

				if(field.getTarget() == null && !WorkerStore.get().isQuiet())
					System.out.println(String.format("AVISO: O target '%s' na view '%s' não foi encontrado na entidade '%s' do ecra %s", 
							ft.getTarget(), viewName, e.getName(), objectName));						

			} else {
				// formato = .<association>
				String target = ft.getTarget().substring(1);

				try {
					field.setAssociation( entity.fetchAssociation(target) );
					// adiciona as associações
					field.setAssociationsChain( entity.fetchAssociationChain( target ) );
				} catch (Exception ex) {
					throw new RuntimeException(String.format("A associação '%s' do target '%s' na view '%s' não foi encontrada no ecra '%s'", 
							target, viewName, ft.getTarget(), objectName), ex);
				}

				if(!ft.getRaw() && ft.getShow() == null)
					throw new RuntimeException(String.format("O atributo 'show' deve ser especificado em 'field' %s da 'view' %s em %s", target, viewName, objectName));

				if(search)
					field.setRequired(ft.getRequired() == null ? false : ft.getRequired());
				else{
					boolean key = false;
					Association association = field.getAssociation();
					if(association.getRelations() != null){ // <-- many to one
						key = true;
						for(Relation relation : association.getRelations()){
							key = key && relation.getForeign().getKey() != null && relation.getForeign().getKey();
						}
					}

					field.setRequired(ft.getRequired() == null ? (!association.getNullable() || key) : ft.getRequired());
				}

				if(search && field.getSearcher() == null){ // se não tem searcher, e para procurar directamente
					field.setTarget( field.getAssociation().getTarget().fetchAttribute( field.getShow() ) );
				} 

				if(!ft.getRaw()){
					String attrs[] = ft.getShow().split("\\+");
					List<String> custom = new ArrayList<String>();
					field.setAttributesName(custom);
					if(!field.isPop()){
						screen.addPoplesses(field);
					}
					for(String a : attrs)
						custom.add( a.trim() );
				}
			}
		}

		return field;
	}

	@Override
	public void allRelated(Model model){
		EventMediator.addListener(EventMediator.EVT_ENTITIES_CONFIGURED, this);
	}

	@Override
	public void onEvent(Event event) {
	    if(!WorkerStore.get().isQuiet())
	    	System.out.println(String.format("Building all <<%s>> DAOs on event.", stereotype));

		Model model = (Model) event.getData();		
		//DAO - devem ser processados depois de todas as associações estarem linkadas, pois percorre as mesmas
		for(ModelElement me : model.getModelElementList(stereotype)){
			pt.quintans.mda.raw.domain.Screen modelObject = (pt.quintans.mda.raw.domain.Screen) me.getSource();
			Screen screen = (Screen) me.getTransformed();

			if(modelObject.getExample() != null)
				screen.setExample( screen.getEntity().fetchExampleByName(modelObject.getExample()) );
			else {
				Example example = new Example();
				example.setName("");
				screen.setExample(example);
			}
		}
	}

}
