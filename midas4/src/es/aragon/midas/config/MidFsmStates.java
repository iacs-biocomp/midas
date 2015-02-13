/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.config;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "mid_fsm_states")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidFsmStates.findAll", query = "SELECT m FROM MidFsmStates m"),
    @NamedQuery(name = "MidFsmStates.findByStId", query = "SELECT m FROM MidFsmStates m WHERE m.stId = :stId"),
    @NamedQuery(name = "MidFsmStates.findByStName", query = "SELECT m FROM MidFsmStates m WHERE m.stName = :stName"),
    @NamedQuery(name = "MidFsmStates.findByStColor", query = "SELECT m FROM MidFsmStates m WHERE m.stColor = :stColor")})
public class MidFsmStates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "st_id")
    private Short stId;
    @Size(max = 16)
    @Column(name = "st_name")
    private String stName;
    @Size(max = 7)
    @Column(name = "st_color")
    private String stColor;

    public MidFsmStates() {
    }

    public MidFsmStates(Short stId) {
        this.stId = stId;
    }

    public Short getStId() {
        return stId;
    }

    public void setStId(Short stId) {
        this.stId = stId;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStColor() {
        return stColor;
    }

    public void setStColor(String stColor) {
        this.stColor = stColor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stId != null ? stId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidFsmStates)) {
            return false;
        }
        MidFsmStates other = (MidFsmStates) object;
        if ((this.stId == null && other.stId != null) || (this.stId != null && !this.stId.equals(other.stId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vinpho.midas.config.MidFsmStates[ stId=" + stId + " ]";
    }
    
}
