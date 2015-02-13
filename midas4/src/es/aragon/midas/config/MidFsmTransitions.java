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
@Table(name = "mid_fsm_transitions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidFsmTransitions.findAll", query = "SELECT m FROM MidFsmTransitions m"),
    @NamedQuery(name = "MidFsmTransitions.findByTrId", query = "SELECT m FROM MidFsmTransitions m WHERE m.trId = :trId"),
    @NamedQuery(name = "MidFsmTransitions.findByTrFrom", query = "SELECT m FROM MidFsmTransitions m WHERE m.trFrom = :trFrom"),
    @NamedQuery(name = "MidFsmTransitions.findByTrEvent", query = "SELECT m FROM MidFsmTransitions m WHERE m.trEvent = :trEvent"),
    @NamedQuery(name = "MidFsmTransitions.findByTrTo", query = "SELECT m FROM MidFsmTransitions m WHERE m.trTo = :trTo"),
    @NamedQuery(name = "MidFsmTransitions.findByTrFsm", query = "SELECT m FROM MidFsmTransitions m WHERE m.trFsm = :trFsm")})
public class MidFsmTransitions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tr_id")
    private Short trId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tr_from")
    private short trFrom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tr_event")
    private short trEvent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tr_to")
    private short trTo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "tr_fsm")
    private String trFsm;
    @Size(min = 1, max = 32)
    @Column(name = "tr_condition")
    private String trCondition;
    @Size(min = 1, max = 10)
    @Column(name = "tr_grant")
    private String trGrant;

    
    
    public MidFsmTransitions() {
    }

    public MidFsmTransitions(Short trId) {
        this.trId = trId;
    }

    public MidFsmTransitions(Short trId, short trFrom, short trEvent, short trTo, String trFsm) {
        this.trId = trId;
        this.trFrom = trFrom;
        this.trEvent = trEvent;
        this.trTo = trTo;
        this.trFsm = trFsm;
    }

    public Short getTrId() {
        return trId;
    }

    public void setTrId(Short trId) {
        this.trId = trId;
    }

    public short getTrFrom() {
        return trFrom;
    }

    public void setTrFrom(short trFrom) {
        this.trFrom = trFrom;
    }

    public short getTrEvent() {
        return trEvent;
    }

    public void setTrEvent(short trEvent) {
        this.trEvent = trEvent;
    }

    public short getTrTo() {
        return trTo;
    }

    public void setTrTo(short trTo) {
        this.trTo = trTo;
    }

    public String getTrFsm() {
        return trFsm;
    }

    public void setTrFsm(String trFsm) {
        this.trFsm = trFsm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trId != null ? trId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidFsmTransitions)) {
            return false;
        }
        MidFsmTransitions other = (MidFsmTransitions) object;
        if ((this.trId == null && other.trId != null) || (this.trId != null && !this.trId.equals(other.trId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vinpho.midas.config.MidFsmTransitions[ trId=" + trId + " ]";
    }

    /**
     * @return the trCondition
     */
    protected String getTrCondition() {
        return trCondition;
    }

    /**
     * @param trCondition the trCondition to set
     */
    protected void setTrCondition(String trCondition) {
        this.trCondition = trCondition;
    }

    /**
     * @return the trGrant
     */
    protected String getTrGrant() {
        return trGrant;
    }

    /**
     * @param trGrant the trGrant to set
     */
    protected void setTrGrant(String trGrant) {
        this.trGrant = trGrant;
    }
    
}
