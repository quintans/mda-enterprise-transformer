package pt.quintans.mda.enterprise.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pt.quintans.mda.enterprise.model.Reference;
import pt.quintans.mda.enterprise.model.entity.Attribute;
import pt.quintans.mda.enterprise.model.entity.Element;

public class DataTranferObject extends Element {
    private List<Attribute> attributes = new ArrayList<Attribute>();
    private List<Reference> references = new ArrayList<Reference>();

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    @Override
    public void pushWeight(Set<Object> set) {
        incWeight();
        pushWeight(set, parent);
        for(Reference ref : references) {
            pushWeight(set, ref.getModel());
        }
    }
}
