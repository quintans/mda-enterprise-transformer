package pt.quintans.mda.enterprise.model.constant;

import java.util.ArrayList;
import java.util.List;

import pt.quintans.mda.enterprise.model.entity.Element;

public class Constants extends Element {
    protected List<Item> items = new ArrayList<Item>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
}
