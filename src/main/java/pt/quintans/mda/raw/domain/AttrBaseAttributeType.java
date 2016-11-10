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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttrBaseAttributeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttrBaseAttributeType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.mda.quintans.pt}BaseType">
 *       &lt;attribute name="single" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="nullable" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttrBaseAttributeType")
@XmlSeeAlso({
    SrvDate.class,
    SrvBoolean.class,
    DtoTime.class,
    DtoDate.class,
    SrvByte.class,
    SrvBinary.class,
    SrvLong.class,
    DtoDecimal.class,
    SrvInteger.class,
    AttrCustom.class,
    DtoLong.class,
    DtoInteger.class,
    DtoTimestamp.class,
    DtoLov.class,
    SrvString.class,
    DtoString.class,
    AttrModel.class,
    SrvDecimal.class,
    DtoDatetime.class,
    DtoBoolean.class,
    DtoCurrency.class
})
public class AttrBaseAttributeType
    extends BaseType
{

    @XmlAttribute
    protected Boolean single;
    @XmlAttribute
    protected Boolean nullable;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String defaultValue;

    /**
     * Gets the value of the single property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getSingle() {
        if (single == null) {
            return true;
        } else {
            return single;
        }
    }

    /**
     * Sets the value of the single property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSingle(Boolean value) {
        this.single = value;
    }

    /**
     * Gets the value of the nullable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getNullable() {
        if (nullable == null) {
            return true;
        } else {
            return nullable;
        }
    }

    /**
     * Sets the value of the nullable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNullable(Boolean value) {
        this.nullable = value;
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

}
