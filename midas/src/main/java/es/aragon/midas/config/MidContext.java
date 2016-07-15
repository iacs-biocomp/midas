/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.config;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "MID_CONTEXTS")
@NamedQueries({
    @NamedQuery(name = "MidContext.findAll", query = "SELECT m FROM MidContext m ORDER BY cxValue, cxId"),
    @NamedQuery(name = "MidContext.findByCxId", query = "SELECT m FROM MidContext m WHERE m.cxId = :cxId"),
    @NamedQuery(name = "MidContext.findByCxKey", query = "SELECT m FROM MidContext m WHERE m.cxKey = :cxKey"),
    @NamedQuery(name = "MidContext.findByCxValue", query = "SELECT m FROM MidContext m WHERE m.cxValue = :cxValue")})
public class MidContext implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CX_ID")
    private Integer cxId;
    @Column(name = "CX_KEY")
    private String cxKey;
    @Column(name = "CX_VALUE")
    private String cxValue;
    
    @JoinTable(name = "MID_ROLE_CONTEXT", joinColumns = {
        @JoinColumn(name = "CX_ID", referencedColumnName = "CX_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")})
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MidRole> midRoleList;
    
    @JoinTable(name = "MID_USER_CONTEXT", joinColumns = {
        @JoinColumn(name = "CX_ID", referencedColumnName = "CX_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "USER_NAME", referencedColumnName = "USER_NAME")})
    @ManyToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MidUser> midUserList;

    public MidContext() {
    }

    public MidContext(Integer cxId) {
        this.cxId = cxId;
    }

    public Integer getCxId() {
        return cxId;
    }

    public void setCxId(Integer cxId) {
        this.cxId = cxId;
    }

    public String getCxKey() {
        return cxKey;
    }

    public void setCxKey(String cxKey) {
        this.cxKey = cxKey;
    }

    public String getCxValue() {
        return cxValue;
    }

    public void setCxValue(String cxValue) {
        this.cxValue = cxValue;
    }

    public List<MidRole> getMidRoleList() {
        return midRoleList;
    }

    public void setMidRoleList(List<MidRole> midRoleList) {
        this.midRoleList = midRoleList;
    }

    public List<MidUser> getMidUserList() {
        return midUserList;
    }

    public void setMidUserList(List<MidUser> midUserList) {
        this.midUserList = midUserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxId != null ? cxId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidContext)) {
            return false;
        }
        MidContext other = (MidContext) object;
        if ((this.cxId == null && other.cxId != null) || (this.cxId != null && !this.cxId.equals(other.cxId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.aragon.midas.config.MidContext[ cxId=" + cxId + " ]";
    }
    
}
