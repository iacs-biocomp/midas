/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.aragon.midas.dao.GrantsLoader;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.util.Utils;
import es.aragon.midas.ws.guia.GuiaConnection;
import es.aragon.midas.ws.guia.InfoUserDetails;
import es.aragon.midas.ws.guia.InfoUserResponse;

/**
 * Encapsula los datos de acceso y seguridad de un usuario de la aplicacion
 * 
 * @author carlos
 */
@Entity
@Table(name = "mid_users")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "MidUser.findAll", query = "SELECT m FROM MidUser m ORDER BY m.userName"),
		@NamedQuery(name = "MidUser.findByUserName", query = "SELECT m FROM MidUser m WHERE m.userName = :userName"),
		@NamedQuery(name = "MidUser.findByIdd", query = "SELECT m FROM MidUser m WHERE m.idd = :idd"),
		@NamedQuery(name = "MidUser.findByActive", query = "SELECT m FROM MidUser m WHERE m.active = :active") })
public class MidUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Transient
    private Logger log = new Logger();
	
	/**
	 * Codigo de acceso del usuario a la aplicacion (login)
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "user_name")
	private String userName;

	/**
	 * Nombre real del usuario
	 */
	@Column(name = "name")
	private String name;

	/**
	 * Primer apellido del usuario
	 */
	@Column(name = "lastname1")
	private String lastname1;

	/**
	 * Segundo apellido del usuario
	 */
	@Column(name = "lastname2")
	private String lastname2;

	/**
	 * Email del usuario, no se persiste en BBDD
	 */
	@Transient
	private String email;

	/**
	 * Identificador unico del usuario (DNI)
	 */
	@Column(name = "idd")
	private String idd;

	/**
	 * Contraseña encriptada, en caso de autenticacion contra Base de Datos
	 */
	@Column(name = "pwd")
	private String pwd;

	/**
	 * Fecha / hora del ultimo login en la aplicacion
	 */
	@Column(name = "last_Login")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	/**
	 * Usuario activo / inactivo Un usuario inactivo no puede acceder a la
	 * aplicacion. Para bloquear el acceso de un usuario a la aplicacion, no se
	 * elimina el mismo de la tabla de usuarios sino que se marca como ACTIVO =
	 * 0
	 */
	@Column(name = "active")
	private Character active;

	/**
	 * Lista de Roles a los que esta asociado el usuario
	 */
	@XmlTransient
	@ManyToMany(mappedBy = "midUserList")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MidRole> midRoleList;

	/**
	 * Lista de contextos a los que esta asociado el usuario
	 */
	@XmlTransient
	@ManyToMany(mappedBy = "midUserList")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MidContext> midContextList;

	
	/**
	 * Lista de permisos del usuario
	 */
	@Transient
	private Set<String> grants = new HashSet<String>(0);


	@Transient
	private InfoUserDetails infoUser;
	
	@Transient
	boolean isAliased;
	
	@Transient
	MidUser actualUser;
	
	/**
	 * Constructor por defecto
	 */
	public MidUser() {
	}

	/**
	 * Constructor del objeto MidUser
	 * 
	 * @param userName
	 */
	public MidUser(String userName) {
		this.userName = userName;
	}

	/**
	 * Constructor del objeto MidUser
	 * 
	 * @param userName
	 * @param name
	 * @param lastname1
	 * @param lastname2
	 * @param idd
	 * @param pwd
	 * @param lastLogin
	 * @param active
	 * @param midRoles
	 */
	public MidUser(String userName, String name, String lastname1,
			String lastname2, String idd, String pwd, Date lastLogin,
			Character active, List<MidRole> midRoles) {
		this.userName = userName;
		this.name = name;
		this.lastname1 = lastname1;
		this.lastname2 = lastname2;
		this.idd = idd;
		this.pwd = pwd;
		this.lastLogin = lastLogin;
		this.active = active;
		this.midRoleList = midRoles;
	}

	/**
	 * Devuelve el UserName del usuario
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getLastname1() {
		return lastname1;
	}

	/**
	 * 
	 * @param lastname1
	 */
	public void setLastname1(String lastname1) {
		this.lastname1 = lastname1;
	}

	/**
	 * 
	 * @return
	 */
	public String getLastname2() {
		return lastname2;
	}

	/**
	 * 
	 * @param lastname2
	 */
	public void setLastname2(String lastname2) {
		this.lastname2 = lastname2;
	}

	/**
	 * 
	 * @return
	 */
	public String getIdd() {
		return idd;
	}

	/**
	 * 
	 * @param idd
	 */
	public void setIdd(String idd) {
		this.idd = idd;
	}

	/**
	 * 
	 * @return
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * 
	 * @param pwd
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * 
	 * @return
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * 
	 * @param lastLogin
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * 
	 * @return
	 */
	public Character getActive() {
		return active;
	}

	/**
	 * 
	 * @param active
	 */
	public void setActive(Character active) {
		this.active = active;
	}

	/**
	 * 
	 * @return
	 */
	public List<MidRole> getMidRoleList() {
		return midRoleList;
	}

	/**
	 * 
	 * @param midRoleList
	 */
	public void setMidRoleList(List<MidRole> midRoleList) {
		this.midRoleList = midRoleList;
	}

	/**
	 * 
	 * @param grant
	 */
	public void grant(String grant) {
		grants.add(grant);
	}

	/**
	 * 
	 * @param grant
	 */
	public void revoke(String grant) {
		grants.remove(grant);
	}

	/**
	 * 
	 * @param grant
	 * @return
	 */
	public boolean isGranted(String grant) {
		return (grants.contains(grant) || grants.contains("SUPER"));
	}

	/**
	 * 
	 * @param ldapRole
	 */
	public void grantLdapRole(String ldapRole) {
		GrantsLoader ld = new GrantsLoader();
		Set<String> toAdd = ld.grantLdapRole(ldapRole);
		grants.addAll(toAdd);
		this.midRoleList.add(ld.getRoleByLdap(ldapRole));
	}

	/**
	 * Carga los contextos a partir del modelo de datos
	 */
	public void obtainGrants() {
		GrantsLoader ld = new GrantsLoader();
		grants.addAll(ld.obtainGrants(userName));
	}

	/**
	 * Devuelve un Set con todos los permisos del usuario
	 * 
	 * @return
	 */
	public Set<String> getGrants() {
		return grants;
	}

	/**
	 * Carga los contextos a partir del modelo de datos
	 */
	public void obtainContexts() {
		GrantsLoader ld = new GrantsLoader();
		setMidContextList(ld.obtainContexts(userName));
	}

	/**
	 * Devuelve la lista de contextos asociados al usuario
	 * 
	 * @return
	 */
	public List<MidContext> getMidContextList() {
		return midContextList;
	}

	/**
	 * 
	 * @param midContextList
	 */
	public void setMidContextList(List<MidContext> midContextList) {
		this.midContextList = midContextList;
	}
	
	
	/**
	 * Añade un contexto a la lista, si no existe ya dicho contexto
	 * @param ctx
	 */
	public void addMidContext(MidContext ctx) {
		if (!midContextList.contains(ctx)) {
			midContextList.add(ctx);
		}
	}
	
	/**
	 * Añade un contexto a la lista, si no existe ya dicho contexto
	 * @param ctx
	 */
	public void addMidContext(String key, String value) {
		MidContext ctx = new MidContext(key, value);
		if (!midContextList.contains(ctx)) {
			midContextList.add(ctx);
		}
	}
	
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Determina si el usuario esta asociado a un contexto determinado
	 * 
	 * @param context
	 *            Codigo del contexto buscado
	 * @return
	 */
	public boolean isInContext(String context) {
		boolean isIn = false;
		Integer ctx = new Integer(context);
		for (MidContext m : midContextList) {
			if (m.getCxId().compareTo(ctx) == 0) {
				isIn = true;
				break;
			}
		}
		return isIn;
	}

	/**
	 * Determina si el usuario esta asociado a un contexto determinado
	 * 
	 * @param context
	 *            Codigo del contexto buscado
	 * @param key
	 *            Tipo de contexto
	 * @return
	 */
	public boolean isInContext(String context, String key) {
		boolean isIn = false;
		Integer ctx = new Integer(context);
		for (MidContext m : midContextList) {
			if ((m.getCxId().compareTo(ctx) == 0) && m.getCxKey().equals(key)) {
				isIn = true;
				break;
			}
		}
		return isIn;
	}

	/**
	 * Devuelve un Set con todos los contextos asociados al usuario
	 * 
	 * @return
	 */
	public Set<String> getContextSet() {
		Set<String> s = new HashSet<String>();
		for (MidContext m : midContextList) {
			if (m.getCxValue() != null) {
				s.add(m.getCxValue());
			}
		}
		return s;
	}

	/**
	 * Devuelve un Set con los contextos del tipo indicado, asociados al usuario
	 * 
	 * @param key
	 *            Tipo de contexto buscado
	 * @return
	 */
	public Set<String> getContextSet(String key) {
		Set<String> s = new HashSet<String>();
		for (MidContext m : midContextList) {
			if (m.getCxKey().equals(key)) {
				if (m.getCxValue() != null) {
					s.add(m.getCxValue());
				}
			}
		}
		return s;
	}

	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getContextValues(String key) {
		StringBuffer s = new StringBuffer();
		boolean coma = false;
		for (MidContext m : midContextList) {
			if (m.getCxKey().equals(key)) {
				if (coma) s.append(","); else coma = true;
				s.append(m.getCxValue());
			}
		}
		return s.toString();
	}
	
	
	/**
	 * 
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userName != null ? userName.hashCode() : 0);
		return hash;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof MidUser)) {
			return false;
		}
		MidUser other = (MidUser) object;
		if ((this.userName == null && other.userName != null)
				|| (this.userName != null && !this.userName
						.equals(other.userName))) {
			return false;
		}
		return true;
	}

	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "es.aragon.midas.config.MidUser[ userName=" + userName + " ]";
	}

	
	/**
	 * 
	 * @return
	 */
	public String getNombreCompleto() {
		StringBuilder builder = new StringBuilder();
		if (lastname1 != null) {
			builder.append(lastname1);
		}
		if (lastname2 != null) {
			builder.append(" ");
			builder.append(lastname2);
		}
		if (name != null) {
			builder.append(", ");
			builder.append(name);
		}
		return builder.toString();
	}

	/**
	 * Devuelve una lista de los IDs de contexto del usuario, para una clave
	 * determinada
	 * 
	 * @param key
	 * @return
	 */
	public List<Long> getMidKeyContext(String key) {
		List<Long> keyList = new ArrayList<Long>();
		for (MidContext context : midContextList) {
			if (context.getCxKey().equalsIgnoreCase(key)) {
				keyList.add(context.getCxId().longValue());
			}
		}
		return keyList;
	}

	
	
	/**
	 * Asigna todos los permisos en funcion de los roles, grupos LDAP y categoría profesional,
	 * obtenidos desde GUIA
	 */
	public void assignGrants() {
		
		this.grant("PUBLIC");
		
			// Captura los grants configurados en BD
		this.obtainGrants();
	
			// Captura los contexts configurados en BD
		this.obtainContexts();
			
		// Si la opción GET_INFOUSER está activa, leemos toda la información del usuario desde GUIA
		if (AppProperties.getParameter(Constants.CFG_GUIA_GET_INFOUSER).equals("true")) {
	    	GuiaConnection con = new GuiaConnection();
			String username = this.getUserName();
	    	InfoUserResponse response = con.infoUser(username);
			log.debug("Leidos datos del usuario " + username + " desde GUIA.");
			if (response.getResult().startsWith("OK")) {
				this.setInfoUser(response.getInfoUser());
				List<String> groupsLDAP = Arrays.asList(this.getInfoUser().getGroupsLDAP().split("\\s*,\\s*"));
	
				// Si no hay roles propios de la aplicación, tomamos roles desde GUIA (LDAP y categoría profesional)
				if (this.getMidRoleList() == null || this.getMidRoleList().isEmpty()) {
					this.setMidRoleList(new ArrayList<MidRole>());
					for (String lg : groupsLDAP) {
						this.grantLdapRole(lg);
					}
					
					this.grantLdapRole(this.getInfoUser().getCatrId());
				}
				
				List<String> cssUcs = Arrays.asList(this.getInfoUser().getCssUcs().split("\\s*,\\s*"));
				for (String uc : cssUcs) {
					log.debug("Añadiendo CSSUC: " + uc); 
					this.addMidContext("CSSUC", uc);
				}
				
				log.debug("Cargados grupos LDAP y UCS del alias");
				
			} else {
				log.error("Error al obtener info desde GUIA para el usuario " + username);
			}
		}
	
		log.debug("Permisos del usuario: " + Utils.setToString(this.getGrants()));
		log.debug("Contextos del usuario: " + Utils.setToString(this.getContextSet()));
		
	}
	
	
	
	

	/**
	 * 
	 * @return
	 */
	public InfoUserDetails getInfoUser() {
		return infoUser;
	}


	/**
	 * 
	 * @param infoUser
	 */
	public void setInfoUser(InfoUserDetails infoUser) {
		this.infoUser = infoUser;
	}

	public boolean isAliased() {
		return isAliased;
	}

	public void setAliased(boolean isAliased) {
		this.isAliased = isAliased;
	}

	public MidUser getActualUser() {
		return actualUser;
	}

	public void setActualUser(MidUser actualUser) {
		this.actualUser = actualUser;
	}



}
