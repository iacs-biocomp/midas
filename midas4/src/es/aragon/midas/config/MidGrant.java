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
@Table(name = "mid_grants")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "findGrantsByUser", query="SELECT g "
					 + "FROM MidGrant g JOIN g.midRoleList r " 
					 + "JOIN r.midUserList u "
					 + "WHERE u.userName = :username"),
    @NamedQuery(name = "findGrantsByLdap", query="SELECT g "
					 + "FROM MidGrant g JOIN g.midRoleList r " 
					 + "WHERE r.roleLdap = :roleLdap"),    
    @NamedQuery(name = "MidGrant.findAll", query = "SELECT m FROM MidGrant m"),
    @NamedQuery(name = "MidGrant.findByGrId", query = "SELECT m FROM MidGrant m WHERE m.grId = :grId"),
    @NamedQuery(name = "MidGrant.findByGrDesc", query = "SELECT m FROM MidGrant m WHERE m.grDesc = :grDesc")})
public class MidGrant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "gr_id")
    private String grId;
    @Column(name = "gr_desc")
    private String grDesc;
    
    @JoinTable(name = "mid_rolegrants", joinColumns = {
        @JoinColumn(name = "rg_grant", referencedColumnName = "gr_id")}, inverseJoinColumns = {
        @JoinColumn(name = "rg_role", referencedColumnName = "role_id")})
    @ManyToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MidRole> midRoleList;
    
    @OneToMany(mappedBy = "mnGrantreq")
    private List<MidMenu> midMenuList;

    public MidGrant() {
    }

    public MidGrant(String grId) {
        this.grId = grId;
    }

    public String getGrId() {
        return grId;
    }

    public void setGrId(String grId) {
        this.grId = grId;
    }

    public String getGrDesc() {
        return grDesc;
    }

    public void setGrDesc(String grDesc) {
        this.grDesc = grDesc;
    }

    @XmlTransient
    public List<MidRole> getMidRoleList() {
        return midRoleList;
    }

    public void setMidRoleList(List<MidRole> midRoleList) {
        this.midRoleList = midRoleList;
    }

    @XmlTransient
    public List<MidMenu> getMidMenuList() {
        return midMenuList;
    }

    public void setMidMenuList(List<MidMenu> midMenuList) {
        this.midMenuList = midMenuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grId != null ? grId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidGrant)) {
            return false;
        }
        MidGrant other = (MidGrant) object;
        if ((this.grId == null && other.grId != null) || (this.grId != null && !this.grId.equals(other.grId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.aragon.midas.config.MidGrant[ grId=" + grId + " ]";
    }
    
}
