/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.config;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlos
 */
@Entity
@Table(name = "mid_audit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidAudit.findAll", query = "SELECT m FROM MidAudit m"),
    @NamedQuery(name = "MidAudit.findByAuId", query = "SELECT m FROM MidAudit m WHERE m.auId = :auId"),
    @NamedQuery(name = "MidAudit.findByAuFecha", query = "SELECT m FROM MidAudit m WHERE m.auFecha = :auFecha"),
    @NamedQuery(name = "MidAudit.findByAuUser", query = "SELECT m FROM MidAudit m WHERE m.auUser = :auUser"),
    @NamedQuery(name = "MidAudit.findByAuIdd", query = "SELECT m FROM MidAudit m WHERE m.auIdd = :auIdd"),
    @NamedQuery(name = "MidAudit.findByAuOper", query = "SELECT m FROM MidAudit m WHERE m.auOper = :auOper"),
    @NamedQuery(name = "MidAudit.findByAuEntidad", query = "SELECT m FROM MidAudit m WHERE m.auEntidad = :auEntidad"),
    @NamedQuery(name = "MidAudit.findByAuPk", query = "SELECT m FROM MidAudit m WHERE m.auPk = :auPk"),
    @NamedQuery(name = "MidAudit.findByAuData", query = "SELECT m FROM MidAudit m WHERE m.auData = :auData")})
public class MidAudit implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AudSeq")
    @SequenceGenerator(name="AudSeq",sequenceName="mid_audit_seq", allocationSize=1)
    @Column(name = "au_id")
    private BigDecimal auId;
    @Column(name = "au_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auFecha;
    @Column(name = "au_user")
    private String auUser;
    @Column(name = "au_idd")
    private String auIdd;
    @Column(name = "au_oper")
    private String auOper;
    @Column(name = "au_entidad")
    private String auEntidad;
    @Column(name = "au_pk")
    private String auPk;
    @Column(name = "au_data")
    private String auData;

    public MidAudit() {
    }

    public MidAudit(BigDecimal auId) {
        this.auId = auId;
    }

    public BigDecimal getAuId() {
        return auId;
    }

    public void setAuId(BigDecimal auId) {
        this.auId = auId;
    }

    public Date getAuFecha() {
        return auFecha;
    }

    public void setAuFecha(Date auFecha) {
        this.auFecha = auFecha;
    }

    public String getAuUser() {
        return auUser;
    }

    public void setAuUser(String auUser) {
        this.auUser = auUser;
    }

    public String getAuIdd() {
        return auIdd;
    }

    public void setAuIdd(String auIdd) {
        this.auIdd = auIdd;
    }

    public String getAuOper() {
        return auOper;
    }

    public void setAuOper(String auOper) {
        this.auOper = auOper;
    }

    public String getAuEntidad() {
        return auEntidad;
    }

    public void setAuEntidad(String auEntidad) {
        this.auEntidad = auEntidad;
    }

    public String getAuPk() {
        return auPk;
    }

    public void setAuPk(String auPk) {
        this.auPk = auPk;
    }

    public String getAuData() {
        return auData;
    }

    public void setAuData(String auData) {
        this.auData = auData;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (auId != null ? auId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidAudit)) {
            return false;
        }
        MidAudit other = (MidAudit) object;
        if ((this.auId == null && other.auId != null) || (this.auId != null && !this.auId.equals(other.auId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.aragon.midas.config.MidAudit[ auId=" + auId + " ]";
    }
    
}
