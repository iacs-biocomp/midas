/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlos
 */
@Entity
@Table(name = "MID_MENU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidMenu.findAll", query = "SELECT m FROM MidMenu m"),
    @NamedQuery(name = "MidMenu.findOrdered", query = "SELECT m from MidMenu m ORDER BY m.mnId "),
    @NamedQuery(name = "MidMenu.findByMnId", query = "SELECT m FROM MidMenu m WHERE m.mnId = :mnId"),
    @NamedQuery(name = "MidMenu.findByMnPadre", query = "SELECT m FROM MidMenu m WHERE m.mnPadre = :mnPadre")})
public class MidMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MN_ID")
    private Short mnId;
    @Column(name = "MN_PADRE")
    private Short mnPadre;
    @Column(name = "MN_TEXTO")
    private String mnTexto;
    @Column(name = "MN_LINK")
    private String mnLink;
    @Column(name = "MN_TARGET")
    private String mnTarget;
    @Column(name = "MN_STYLE")
    private String mnStyle;
    @JoinColumn(name = "MN_GRANTREQ", referencedColumnName = "GR_ID")
    @ManyToOne
    private MidGrant mnGrantreq;

	
    @Transient
    private ArrayList<MidMenu> children = new ArrayList<MidMenu>();    
    
    public MidMenu() {
    }

    public MidMenu(Short mnId, MidGrant midGrants, Short mnPadre,
                    String mnTexto, String mnLink, String mnTarget) {
            this.mnId = mnId;
            this.mnGrantreq = midGrants;
            this.mnPadre = mnPadre;
            this.mnTexto = mnTexto;
            this.mnLink = mnLink;
            this.mnTarget = mnTarget;
    }	    
    
    
    public MidMenu(Short mnId) {
        this.mnId = mnId;
    }

    public Short getMnId() {
        return mnId;
    }

    public void setMnId(Short mnId) {
        this.mnId = mnId;
    }

    public Short getMnPadre() {
        return mnPadre;
    }

    public void setMnPadre(Short mnPadre) {
        this.mnPadre = mnPadre;
    }

    public String getMnTexto() {
        return mnTexto;
    }

    public void setMnTexto(String mnTexto) {
        this.mnTexto = mnTexto;
    }

    public String getMnLink() {
        return mnLink;
    }

    public void setMnLink(String mnLink) {
        this.mnLink = mnLink;
    }

    public String getMnTarget() {
        return mnTarget;
    }

    public void setMnTarget(String mnTarget) {
        this.mnTarget = mnTarget;
    }

    public String getMnStyle() {
        return mnStyle;
    }

    public void setMnStyle(String mnStyle) {
        this.mnStyle = mnStyle;
    }

    public MidGrant getMnGrantreq() {
        return mnGrantreq;
    }

    public void setMnGrantreq(MidGrant mnGrantreq) {
        this.mnGrantreq = mnGrantreq;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mnId != null ? mnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidMenu)) {
            return false;
        }
        MidMenu other = (MidMenu) object;
        if ((this.mnId == null && other.mnId != null) || (this.mnId != null && !this.mnId.equals(other.mnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.aragon.midas.config.MidMenu[ mnId=" + mnId + " ]";
    }

    public void addChild(MidMenu child) {
            children.add(child);
    }

    public ArrayList<MidMenu> getChildren() {
            return children;
    }
        
    public MidMenu cloneWithGrants(Set<String> grants) {
            MidMenu result = new MidMenu();
            result.mnId = this.mnId;
            result.mnGrantreq = this.mnGrantreq;
            result.mnPadre = this.mnPadre;
            result.mnTexto = this.mnTexto;
            result.mnLink = this.mnLink;
            result.mnTarget = this.mnTarget;
            for (MidMenu mn : children) {
                    if (grants.contains(mn.getMnGrantreq().getGrId()) 
                                    || grants.contains(Constants.SUPER))
                            result.addChild(mn.cloneWithGrants(grants));
            }
            return result;
    }    
    
}
