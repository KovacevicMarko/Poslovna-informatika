//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.29 at 11:44:03 PM CEST 
//


package modelFromXsd;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ZaglavljeMT102 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZaglavljeMT102">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idKliringa" type="{}Str50"/>
 *         &lt;element name="datumKliringa" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="ukupanIznos" type="{}Dec15d2"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZaglavljeMT102", propOrder = {
    "idKliringa",
    "datumKliringa",
    "ukupanIznos"
})
public class ZaglavljeMT102 {

    @XmlElement(required = true)
    protected String idKliringa;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumKliringa;
    @XmlElement(required = true)
    protected BigDecimal ukupanIznos;

    /**
     * Gets the value of the idKliringa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdKliringa() {
        return idKliringa;
    }

    /**
     * Sets the value of the idKliringa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdKliringa(String value) {
        this.idKliringa = value;
    }

    /**
     * Gets the value of the datumKliringa property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumKliringa() {
        return datumKliringa;
    }

    /**
     * Sets the value of the datumKliringa property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumKliringa(XMLGregorianCalendar value) {
        this.datumKliringa = value;
    }

    /**
     * Gets the value of the ukupanIznos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupanIznos() {
        return ukupanIznos;
    }

    /**
     * Sets the value of the ukupanIznos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupanIznos(BigDecimal value) {
        this.ukupanIznos = value;
    }

}
