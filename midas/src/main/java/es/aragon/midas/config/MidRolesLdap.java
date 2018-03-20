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
@Table(name = "mid_roles_ldap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidRolesLdap.findAll", query = "SELECT m FROM MidRolesLdap m")
 })
@NamedNativeQueries({
	@NamedNativeQuery(name = "MidRolesLdap.findRoleByLdap", query = "select r.* from " +
													 "mid_roles r join mid_roles_ldap l on r.role_id = l.role_id " + 
													 "where :roleLdap like l.role_ldap", resultClass = MidRole.class)
})


public class MidRolesLdap implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Id
    @Column(name = "role_ldap")
    private String roleLdap;
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "role_desc")
    private String roleDesc;

    public MidRolesLdap() {
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleLdap != null ? roleLdap.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidLogin)) {
            return false;
        }
        MidRolesLdap other = (MidRolesLdap) object;
        if ((this.roleLdap == null && other.roleLdap != null) || (this.roleLdap != null && !this.roleLdap.equals(other.roleLdap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.aragon.midas.config.MidRolesLdap[ roleLdap=" + roleLdap + " ]";
    }



	public String getRoleLdap() {
		return roleLdap;
	}



	public void setRoleLdap(String roleLdap) {
		this.roleLdap = roleLdap;
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
    
}
