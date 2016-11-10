//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.10 at 12:26:33 PM WET 
//


package pt.quintans.mda.raw.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import pt.quintans.mda.core.ElementType;


/**
 * <p>Java class for fieldType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fieldType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="target" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="show" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="readonly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="exact" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="pop" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="raw" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="labelWidth" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="fieldWidth" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="length" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="lines" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *       &lt;attribute name="span" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *       &lt;attribute name="searcher" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="alias" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="subTarget" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="custom" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="editable" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fieldType")
public class FieldType
    extends ElementType
{

    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String target;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String name;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String show;
    @XmlAttribute
    protected Boolean readonly;
    @XmlAttribute
    protected Boolean required;
    @XmlAttribute
    protected Boolean exact;
    @XmlAttribute
    protected Boolean pop;
    @XmlAttribute
    protected Boolean raw;
    @XmlAttribute
    protected Integer width;
    @XmlAttribute
    protected Integer labelWidth;
    @XmlAttribute
    protected Integer fieldWidth;
    @XmlAttribute
    protected Integer length;
    @XmlAttribute
    protected Integer lines;
    @XmlAttribute
    protected Integer span;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String searcher;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String description;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String alias;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String defaultValue;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String subTarget;
    @XmlAttribute
    protected Boolean custom;
    @XmlAttribute
    protected Boolean editable;

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the show property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShow() {
        return show;
    }

    /**
     * Sets the value of the show property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShow(String value) {
        this.show = value;
    }

    /**
     * Gets the value of the readonly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getReadonly() {
        if (readonly == null) {
            return false;
        } else {
            return readonly;
        }
    }

    /**
     * Sets the value of the readonly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReadonly(Boolean value) {
        this.readonly = value;
    }

    /**
     * Gets the value of the required property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * Sets the value of the required property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequired(Boolean value) {
        this.required = value;
    }

    /**
     * Gets the value of the exact property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getExact() {
        if (exact == null) {
            return false;
        } else {
            return exact;
        }
    }

    /**
     * Sets the value of the exact property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExact(Boolean value) {
        this.exact = value;
    }

    /**
     * Gets the value of the pop property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getPop() {
        if (pop == null) {
            return true;
        } else {
            return pop;
        }
    }

    /**
     * Sets the value of the pop property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPop(Boolean value) {
        this.pop = value;
    }

    /**
     * Gets the value of the raw property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getRaw() {
        if (raw == null) {
            return false;
        } else {
            return raw;
        }
    }

    /**
     * Sets the value of the raw property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRaw(Boolean value) {
        this.raw = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidth(Integer value) {
        this.width = value;
    }

    /**
     * Gets the value of the labelWidth property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLabelWidth() {
        return labelWidth;
    }

    /**
     * Sets the value of the labelWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLabelWidth(Integer value) {
        this.labelWidth = value;
    }

    /**
     * Gets the value of the fieldWidth property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFieldWidth() {
        return fieldWidth;
    }

    /**
     * Sets the value of the fieldWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFieldWidth(Integer value) {
        this.fieldWidth = value;
    }

    /**
     * Gets the value of the length property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLength(Integer value) {
        this.length = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getLines() {
        if (lines == null) {
            return  1;
        } else {
            return lines;
        }
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLines(Integer value) {
        this.lines = value;
    }

    /**
     * Gets the value of the span property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getSpan() {
        if (span == null) {
            return  1;
        } else {
            return span;
        }
    }

    /**
     * Sets the value of the span property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSpan(Integer value) {
        this.span = value;
    }

    /**
     * Gets the value of the searcher property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearcher() {
        return searcher;
    }

    /**
     * Sets the value of the searcher property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearcher(String value) {
        this.searcher = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the alias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Gets the value of the defaultValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the subTarget property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubTarget() {
        return subTarget;
    }

    /**
     * Sets the value of the subTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubTarget(String value) {
        this.subTarget = value;
    }

    /**
     * Gets the value of the custom property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getCustom() {
        if (custom == null) {
            return false;
        } else {
            return custom;
        }
    }

    /**
     * Sets the value of the custom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCustom(Boolean value) {
        this.custom = value;
    }

    /**
     * Gets the value of the editable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getEditable() {
        if (editable == null) {
            return false;
        } else {
            return editable;
        }
    }

    /**
     * Sets the value of the editable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEditable(Boolean value) {
        this.editable = value;
    }

}