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
@Table(name = "mid_fsm_events")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidFsmEvents.findAll", query = "SELECT m FROM MidFsmEvents m"),
    @NamedQuery(name = "MidFsmEvents.findByEvId", query = "SELECT m FROM MidFsmEvents m WHERE m.evId = :evId"),
    @NamedQuery(name = "MidFsmEvents.findByEvName", query = "SELECT m FROM MidFsmEvents m WHERE m.evName = :evName"),
    @NamedQuery(name = "MidFsmEvents.findByEvIcon", query = "SELECT m FROM MidFsmEvents m WHERE m.evIcon = :evIcon")})
public class MidFsmEvents implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ev_id")
    private Short evId;
    @Size(max = 16)
    @Column(name = "ev_name")
    private String evName;
    @Size(max = 20)
    @Column(name = "ev_icon")
    private String evIcon;

    public MidFsmEvents() {
    }

    public MidFsmEvents(Short evId) {
        this.evId = evId;
    }

    public Short getEvId() {
        return evId;
    }

    public void setEvId(Short evId) {
        this.evId = evId;
    }

    public String getEvName() {
        return evName;
    }

    public void setEvName(String evName) {
        this.evName = evName;
    }

    public String getEvIcon() {
        return evIcon;
    }

    public void setEvIcon(String evIcon) {
        this.evIcon = evIcon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evId != null ? evId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidFsmEvents)) {
            return false;
        }
        MidFsmEvents other = (MidFsmEvents) object;
        if ((this.evId == null && other.evId != null) || (this.evId != null && !this.evId.equals(other.evId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vinpho.midas.config.MidFsmEvents[ evId=" + evId + " ]";
    }
    
}
