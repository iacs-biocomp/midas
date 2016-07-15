/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.config;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author carlos
 */
@Entity
@Table(name = "MID_ROLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidRole.findAll", query = "SELECT m FROM MidRole m ORDER BY roleId "),
    @NamedQuery(name = "MidRole.findAllUngiven", 
    	query = "SELECT m FROM MidRole m WHERE m NOT IN " +
    			"	(SELECT r FROM MidRole r join r.midUserList u WHERE u.userName = :userName) ORDER BY roleId"),
    @NamedQuery(name = "MidRole.findByRoleId", query = "SELECT m FROM MidRole m WHERE m.roleId = :roleId"),
    @NamedQuery(name = "MidRole.findByRoleDesc", query = "SELECT m FROM MidRole m WHERE m.roleDesc = :roleDesc"),
    @NamedQuery(name = "MidRole.findByRoleLdap", query = "SELECT m FROM MidRole m WHERE m.roleLdap = :roleLdap")})
public class MidRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ROLE_ID")
    private String roleId;
    @Column(name = "ROLE_DESC")
    private String roleDesc;
    @Column(name = "ROLE_LDAP")
    private String roleLdap;
    @JoinTable(name = "MID_USERROLES", joinColumns = {
        @JoinColumn(name = "UR_ROLE", referencedColumnName = "ROLE_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "UR_NAME", referencedColumnName = "USER_NAME")})
    @ManyToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MidUser> midUserList;
    
    @JoinTable(name = "MID_ROLEGRANTS", joinColumns = {
        @JoinColumn(name = "RG_ROLE", referencedColumnName = "ROLE_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "RG_GRANT", referencedColumnName = "GR_ID")})
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MidGrant> midGrantList;
    
    @JoinTable(name = "MID_ROLE_CONTEXT", joinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "CX_ID", referencedColumnName = "CX_ID")})
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MidContext> midContextList;

    public MidRole() {
    }

    public MidRole(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleLdap() {
        return roleLdap;
    }

    public void setRoleLdap(String roleLdap) {
        this.roleLdap = roleLdap;
    }

    @XmlTransient
    public List<MidUser> getMidUserList() {
        return midUserList;
    }

    public void setMidUserList(List<MidUser> midUserList) {
        this.midUserList = midUserList;
    }

    @XmlTransient
    public List<MidGrant> getMidGrantList() {
        return midGrantList;
    }

    public void setMidGrantList(List<MidGrant> midGrantList) {
        this.midGrantList = midGrantList;
    }

    @XmlTransient
    public List<MidContext> getMidContextList() {
        return midContextList;
    }

    public void setMidContextList(List<MidContext> midContextList) {
        this.midContextList = midContextList;
    }
        
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidRole)) {
            return false;
        }
        MidRole other = (MidRole) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.aragon.midas.config.MidRole[ roleId=" + roleId + " ]";
    }


}
