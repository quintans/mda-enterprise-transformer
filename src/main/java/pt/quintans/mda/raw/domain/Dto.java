//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.10 at 12:26:33 PM WET 
//


package pt.quintans.mda.raw.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.mda.quintans.pt}basic">
 *       &lt;sequence>
 *         &lt;element name="attributes" type="{http://www.mda.quintans.pt}DtoAttributeType" minOccurs="0"/>
 *         &lt;element name="references" type="{http://www.mda.quintans.pt}ReferencesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "attributes",
    "references"
})
@XmlRootElement(name = "dto")
public class Dto
    extends Basic
{

    protected DtoAttributeType attributes;
    protected ReferencesType references;

    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link DtoAttributeType }
     *     
     */
    public DtoAttributeType getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link DtoAttributeType }
     *     
     */
    public void setAttributes(DtoAttributeType value) {
        this.attributes = value;
    }

    /**
     * Gets the value of the references property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencesType }
     *     
     */
    public ReferencesType getReferences() {
        return references;
    }

    /**
     * Sets the value of the references property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencesType }
     *     
     */
    public void setReferences(ReferencesType value) {
        this.references = value;
    }

}