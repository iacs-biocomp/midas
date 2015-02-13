
package es.aragon.midas.ws.bdu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CIA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="system" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cia",
    "system"
})
@XmlRootElement(name = "getByCIAElement")
public class GetByCIAElement {

    @XmlElement(name = "CIA", required = true, nillable = true)
    protected String cia;
    @XmlElement(required = true, nillable = true)
    protected String system;

    /**
     * Obtiene el valor de la propiedad cia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIA() {
        return cia;
    }

    /**
     * Define el valor de la propiedad cia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIA(String value) {
        this.cia = value;
    }

    /**
     * Obtiene el valor de la propiedad system.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystem() {
        return system;
    }

    /**
     * Define el valor de la propiedad system.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystem(String value) {
        this.system = value;
    }

}
