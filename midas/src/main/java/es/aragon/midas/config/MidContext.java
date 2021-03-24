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
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "mid_contexts")
@NamedQueries({
    @NamedQuery(name = "MidContext.findAll", query = "SELECT m FROM MidContext m ORDER BY m.cxValue, m.cxId"),
    @NamedQuery(name = "MidContext.findByCxId", query = "SELECT m FROM MidContext m WHERE m.cxId = :cxId"),
    @NamedQuery(name = "MidContext.findByCxKey", query = "SELECT m FROM MidContext m WHERE m.cxKey = :cxKey"),
    @NamedQuery(name = "MidContext.findByCxValue", query = "SELECT m FROM MidContext m WHERE m.cxValue = :cxValue")})

public class MidContext implements Serializable {

	private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "cx_id")
    private Integer cxId;
    @Column(name = "cx_key")
    private String cxKey;
    @Column(name = "cx_value")
    private String cxValue;
    
    @XmlTransient 
    @JoinTable(name = "mid_role_context", joinColumns = {
        @JoinColumn(name = "cx_id", referencedColumnName = "cx_id")}, inverseJoinColumns = {
        @JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MidRole> midRoleList;
    
    @XmlTransient 
    @JoinTable(name = "mid_user_context", joinColumns = {
        @JoinColumn(name = "cx_id", referencedColumnName = "cx_id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_name", referencedColumnName = "user_name")})
    @ManyToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MidUser> midUserList;

    public MidContext() {
    }

    public MidContext(Integer cxId) {
        this.cxId = cxId;
    }

    public MidContext(String key, String value) {
        this.cxKey = key;
        this.cxValue = value;
    }

    public MidContext(Integer id, String key, String value) {
    	this.cxId = id;
        this.cxKey = key;
        this.cxValue = value;
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
        if (this.cxKey.equals(other.cxKey) && this.cxValue.equals(other.cxValue))
        		return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "es.aragon.midas.config.MidContext[ cxId=" + cxId + " ]";
    }
    
}
