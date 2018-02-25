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

import es.aragon.midas.logging.Logger;
import es.aragon.midas.util.Utils;

/**
 *
 * @author carlos
 */
@Entity
@Table(name = "mid_menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidMenu.findAll", query = "SELECT m FROM MidMenu m"),
    @NamedQuery(name = "MidMenu.findOrdered", query = "SELECT m from MidMenu m ORDER BY m.mnId "),
    @NamedQuery(name = "MidMenu.findByMnId", query = "SELECT m FROM MidMenu m WHERE m.mnId = :mnId"),
    @NamedQuery(name = "MidMenu.findByMnPadre", query = "SELECT m FROM MidMenu m WHERE m.mnPadre = :mnPadre")})
public class MidMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient
    protected Logger log = new Logger();
    
    @Id
    @Basic(optional = false)
    @Column(name = "mn_id")
    private Short mnId;
    @Column(name = "mn_padre")
    private Short mnPadre;
    @Column(name = "mn_texto")
    private String mnTexto;
    @Column(name = "mn_link")
    private String mnLink;
    @Column(name = "mn_target")
    private String mnTarget;
    @Column(name = "mn_style")
    private String mnStyle;
    @JoinColumn(name = "mn_grantreq", referencedColumnName = "gr_id")
    @ManyToOne
    private MidGrant mnGrantreq;
    
    @Transient
    private boolean active;
    @Transient
    private MidMenu parentMenu;
	
    @Transient
    private ArrayList<MidMenu> children = new ArrayList<MidMenu>();    
    
    public MidMenu() {
    }

    public MidMenu(Short mnId, MidGrant midGrants, Short mnPadre,
                    String mnTexto, String mnLink, String mnTarget, String mnStyle) {
            this.mnId = mnId;
            this.mnGrantreq = midGrants;
            this.mnPadre = mnPadre;
            this.mnTexto = mnTexto;
            this.mnLink = mnLink;
            this.mnTarget = mnTarget;
            this.mnStyle = mnStyle;
            this.active = false;
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
        if (mnPadre == 0)
        	this.parentMenu = null;
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
            result.mnStyle = this.mnStyle;
            result.active = this.active;
            
            for (MidMenu mn : children) {
                    if (grants.contains(mn.getMnGrantreq().getGrId()) 
                                    || grants.contains(Constants.SUPER)) {
                    		mn.setParent(result);
                    		log.debug("incluido menu " + mn.mnTexto + ". Grantreq = " + mn.getMnGrantreq().getGrId());
                    		MidMenu child = mn.cloneWithGrants(grants);
                    		child.setParent(result);
                    		result.addChild(child);
                    }
            }
            return result;
    }

	public boolean isActive() {
		return active;
	}
	
	public String getActiveClass() {
		return active ? "active" : ""; 
	}

	public void setActive(boolean active) {
		this.active = active;
		log.debug("menu " + this.getMnId() + " active ");
		if (this.parentMenu != null) {
			this.parentMenu.setActive(active); 
		} 
	}    
    
	/**
	 * Resetea todas las entradas del menu a No Activo
	 */
	public void setAllInactive() {
		log.debug("Desactivando menu " + this.getMnId());
		this.active = false;
		for (MidMenu m : children) {
			m.setAllInactive();
		}
	}
	
	public boolean searchActive(String link) {
		log.debug("Buscando menu activo en :" + this.getMnId());
		for (MidMenu m : children) {
			if (link.equals(m.mnLink)) {
				log.debug("Menu activo: " + m.getMnId() + "=" + link);
				m.setActive(true);
				return true;
			} else if (m.getChildren().size() > 0) {
				if(m.searchActive(link))
					return true;
			}
		}
		return false;
	}

	public MidMenu getParent() {
		return parentMenu;
	}

	public void setParent(MidMenu parent) {
		this.parentMenu = parent;
	}
	
}
