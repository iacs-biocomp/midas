
package es.aragon.midas.ws.bdu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ListaCandidatos complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ListaCandidatos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lista" type="{http://www.oracle.com/webservices/internal/literal}arrayList"/>
 *         &lt;element name="encontrados" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="devueltos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaCandidatos", propOrder = {
    "lista",
    "encontrados",
    "devueltos"
})
public class ListaCandidatos {

    @XmlElement(required = true, nillable = true)
    protected ArrayList lista;
    protected int encontrados;
    protected int devueltos;

    /**
     * Obtiene el valor de la propiedad lista.
     * 
     * @return
     *     possible object is
     *     {@link ArrayList }
     *     
     */
    public ArrayList getLista() {
        return lista;
    }

    /**
     * Define el valor de la propiedad lista.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayList }
     *     
     */
    public void setLista(ArrayList value) {
        this.lista = value;
    }

    /**
     * Obtiene el valor de la propiedad encontrados.
     * 
     */
    public int getEncontrados() {
        return encontrados;
    }

    /**
     * Define el valor de la propiedad encontrados.
     * 
     */
    public void setEncontrados(int value) {
        this.encontrados = value;
    }

    /**
     * Obtiene el valor de la propiedad devueltos.
     * 
     */
    public int getDevueltos() {
        return devueltos;
    }

    /**
     * Define el valor de la propiedad devueltos.
     * 
     */
    public void setDevueltos(int value) {
        this.devueltos = value;
    }

}
