
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
 *         &lt;element name="Candidato_1" type="{http://ws.bdu.salud.aragon.es/types/}Candidato"/>
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
    "candidato1"
})
@XmlRootElement(name = "findElement")
public class FindElement {

    @XmlElement(name = "Candidato_1", required = true, nillable = true)
    protected Candidato candidato1;

    /**
     * Obtiene el valor de la propiedad candidato1.
     * 
     * @return
     *     possible object is
     *     {@link Candidato }
     *     
     */
    public Candidato getCandidato1() {
        return candidato1;
    }

    /**
     * Define el valor de la propiedad candidato1.
     * 
     * @param value
     *     allowed object is
     *     {@link Candidato }
     *     
     */
    public void setCandidato1(Candidato value) {
        this.candidato1 = value;
    }

}
