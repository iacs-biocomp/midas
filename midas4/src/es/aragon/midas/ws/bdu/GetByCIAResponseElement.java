
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
 *         &lt;element name="result" type="{http://ws.bdu.salud.aragon.es/types/}Person"/>
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
    "result"
})
@XmlRootElement(name = "getByCIAResponseElement")
public class GetByCIAResponseElement {

    @XmlElement(required = true, nillable = true)
    protected Person result;

    /**
     * Obtiene el valor de la propiedad result.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getResult() {
        return result;
    }

    /**
     * Define el valor de la propiedad result.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setResult(Person value) {
        this.result = value;
    }

}
