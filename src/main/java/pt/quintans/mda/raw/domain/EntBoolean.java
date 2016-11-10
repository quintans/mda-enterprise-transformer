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


/**
 * <p>Java class for ent_boolean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ent_boolean">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.mda.quintans.pt}BaseAttributeType">
 *       &lt;attribute name="format" type="{http://www.mda.quintans.pt}boolType" />
 *       &lt;attribute name="trueValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="falseValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ent_boolean")
public class EntBoolean
    extends BaseAttributeType
{

    @XmlAttribute
    protected BoolType format;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String trueValue;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String falseValue;

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link BoolType }
     *     
     */
    public BoolType getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link BoolType }
     *     
     */
    public void setFormat(BoolType value) {
        this.format = value;
    }

    /**
     * Gets the value of the trueValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrueValue() {
        return trueValue;
    }

    /**
     * Sets the value of the trueValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrueValue(String value) {
        this.trueValue = value;
    }

    /**
     * Gets the value of the falseValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFalseValue() {
        return falseValue;
    }

    /**
     * Sets the value of the falseValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFalseValue(String value) {
        this.falseValue = value;
    }

}
