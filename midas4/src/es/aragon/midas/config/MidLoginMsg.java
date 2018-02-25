/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.config;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlos
 */
@Entity
@Table(name = "mid_login_msg")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidLoginMsg.findAll", query = "SELECT m FROM MidLoginMsg m"),
    @NamedQuery(name = "MidLoginMsg.findByLmCodigo", query = "SELECT m FROM MidLoginMsg m WHERE m.lmCodigo = :lmCodigo"),
    @NamedQuery(name = "MidLoginMsg.findByLmDescripcion", query = "SELECT m FROM MidLoginMsg m WHERE m.lmDescripcion = :lmDescripcion")})
public class MidLoginMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "lm_codigo")
    private String lmCodigo;
    @Column(name = "lm_descripcion")
    private String lmDescripcion;

    public MidLoginMsg() {
    }

    public MidLoginMsg(String lmCodigo) {
        this.lmCodigo = lmCodigo;
    }

    public String getLmCodigo() {
        return lmCodigo;
    }

    public void setLmCodigo(String lmCodigo) {
        this.lmCodigo = lmCodigo;
    }

    public String getLmDescripcion() {
        return lmDescripcion;
    }

    public void setLmDescripcion(String lmDescripcion) {
        this.lmDescripcion = lmDescripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lmCodigo != null ? lmCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidLoginMsg)) {
            return false;
        }
        MidLoginMsg other = (MidLoginMsg) object;
        if ((this.lmCodigo == null && other.lmCodigo != null) || (this.lmCodigo != null && !this.lmCodigo.equals(other.lmCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.aragon.midas.config.MidLoginMsg[ lmCodigo=" + lmCodigo + " ]";
    }
    
}
