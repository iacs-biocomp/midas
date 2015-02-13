/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.ws.guia;

/**
 *
 * @author j2ee.salud
 */

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="ticketAnswer")
public class AuthGuiaTicketResponse {
    public AuthGuiaTicketResponse() {
    }
    private String result;
    private String nif;
    private String id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    
    
    private ArrayList<String> grupos;
   

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getApellido2() {
        return apellido2;
    }

   

    public void setGrupos(ArrayList<String> grupos) {
        this.grupos = grupos;
    }
    
    @XmlElementWrapper(name = "grupos")
    @XmlElement(name = "grupo")
    public ArrayList<String> getGrupos() {
        return grupos;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[AuthGuiaTicketResponse]:");
        sb.append(" result:").append(result);
        sb.append(" nif:").append(nif);
        sb.append(" id:").append(id);
        sb.append(" nombre:").append(nombre);
        sb.append(" apellido1:").append(apellido1);
        sb.append(" apellido2:").append(apellido2);
        sb.append(" grupos:").append(grupos);
        
        return sb.toString();
    }
}
