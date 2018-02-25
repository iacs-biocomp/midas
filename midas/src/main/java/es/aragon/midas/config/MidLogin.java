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
@Table(name = "mid_login")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidLogin.findAll", query = "SELECT m FROM MidLogin m"),
    @NamedQuery(name = "MidLogin.findByLgDate", query = "SELECT m FROM MidLogin m WHERE m.lgDate = :lgDate"),
    @NamedQuery(name = "MidLogin.findByLgUser", query = "SELECT m FROM MidLogin m WHERE m.lgUser = :lgUser"),
    @NamedQuery(name = "MidLogin.findByLgIp", query = "SELECT m FROM MidLogin m WHERE m.lgIp = :lgIp"),
    @NamedQuery(name = "MidLogin.findByLgCod", query = "SELECT m FROM MidLogin m WHERE m.lgCod = :lgCod"),
    @NamedQuery(name = "MidLogin.findByLgId", query = "SELECT m FROM MidLogin m WHERE m.lgId = :lgId")})
public class MidLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "lg_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lgDate;
    @Column(name = "lg_user")
    private String lgUser;
    @Column(name = "lg_ip")
    private String lgIp;
    @Column(name = "lg_cod")
    private String lgCod;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LogSeq")
    @SequenceGenerator(name="LogSeq",sequenceName="mid_log_seq", allocationSize=1)
    @Column(name = "lg_id")
    private BigDecimal lgId;

    public MidLogin() {
    }

    public MidLogin(BigDecimal lgId) {
        this.lgId = lgId;
    }

    public Date getLgDate() {
        return lgDate;
    }

    public void setLgDate(Date lgDate) {
        this.lgDate = lgDate;
    }

    public String getLgUser() {
        return lgUser;
    }

    public void setLgUser(String lgUser) {
        this.lgUser = lgUser;
    }

    public String getLgIp() {
        return lgIp;
    }

    public void setLgIp(String lgIp) {
        this.lgIp = lgIp;
    }

    public String getLgCod() {
        return lgCod;
    }

    public void setLgCod(String lgCod) {
        this.lgCod = lgCod;
    }

    public BigDecimal getLgId() {
        return lgId;
    }

    public void setLgId(BigDecimal lgId) {
        this.lgId = lgId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lgId != null ? lgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidLogin)) {
            return false;
        }
        MidLogin other = (MidLogin) object;
        if ((this.lgId == null && other.lgId != null) || (this.lgId != null && !this.lgId.equals(other.lgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.aragon.midas.config.MidLogin[ lgId=" + lgId + " ]";
    }
    
}
