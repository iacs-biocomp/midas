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
@Table(name = "MID_REPORTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidReport.findAll", query = "SELECT m FROM MidReport m"),
    @NamedQuery(name = "MidReport.findById", query = "SELECT m FROM MidReport m WHERE m.id = :id"),
    @NamedQuery(name = "MidReport.findByValue", query = "SELECT m FROM MidReport m WHERE m.val = :value"),
    @NamedQuery(name = "MidReport.findByDescription", query = "SELECT m FROM MidReport m WHERE m.description = :description")})
public class MidReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Column(name = "VALUE")
    private String val;
    @Column(name = "DESCRIPTION")
    private String description;

    public MidReport() {
    }

    public MidReport(String id) {
        this.id = id;
    }

    public MidReport(String id, String value, String description) {
            this.id = id;
            this.val = value;
            this.description = description;
    }	
	    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return val;
    }

    public void setValue(String value) {
        this.val = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidAppProperty)) {
            return false;
        }
        MidReport other = (MidReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.aragon.midas.config.MidReport[ id=" + id + " ]";
    }
    
}
