package pt.quintans.mda.enterprise.core;

import java.lang.annotation.Annotation;

import javax.xml.bind.annotation.XmlType;

public class ElementType {
	private String location;
	private String namespace;
		
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getElementType(){
		Annotation anno = this.getClass().getAnnotation(XmlType.class);
		if(anno instanceof XmlType){
			//@XmlType(name = "StringType")
			String types[] = ((XmlType) anno).name().split("_");
			if(types.length > 1)
				return types[1];
			else
				return types[0];
		}
		
		return "";
	}

}
