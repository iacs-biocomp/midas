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

import javax.inject.Inject;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.aragon.midas.dao.GrantsLoader;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.structure.ISpsStructureDao;
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
@NamedNativeQueries({
	@NamedNativeQuery(name = "MidUser.findCryptedPasswd", 
					query = "select public.crypt(text(:pwd), text(bd_pw.pwd)) crp_pwd, bd_pw.pwd pwd " +
							"from (select pwd from mid_users where user_name = :userName) bd_pw") })

public class MidUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Transient
    private Logger log = new Logger();
	
	@Inject
	@Transient
    private ISpsStructureDao structDao;
	
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
	@Column(name = "email")
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
	 * Método de autenticación de usuario:
	 * L - Local
	 * S - Server
	 */
	@Column(name = "auth_mode")
	private Character authMode;
	

	/**
	 * Lista de Roles a los que esta asociado el usuario
	@XmlTransient
	 */
	@ManyToMany(mappedBy = "midUserList")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MidRole> midRoleList;

	/**
	 * Lista de contextos a los que esta asociado el usuario
	@XmlTransient
	 */
	@ManyToMany(mappedBy = "midUserList")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MidContext> midContextList;

	
	/**
	 * Lista de permisos del usuario
	 */
	@Transient
	private Set<String> grants = new HashSet<String>(0);


	/**
	 * Detalles de usuario desde infoUser
	 */
	@Transient
	private InfoUserDetails infoUser;
	
	
	/**
	 * Usuario impersonado S/N
	 */
	@Transient
	boolean isAliased;
	
	/**
	 * Usuario real (físico) en caso de impersonación
	 */
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
	public Character getAuthMode() {
		return authMode;
	}

	/**
	 * 
	 * @param authMode
	 */
	public void setAuthMode(Character authMode) {
		this.authMode = authMode == null ? new Character('L') : authMode;
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
		if (ldapRole != null && ! ldapRole.isEmpty()) { 
			GrantsLoader ld = new GrantsLoader();
			Set<String> toAdd = ld.grantLdapRole(ldapRole);
			grants.addAll(toAdd);
			this.midRoleList.add(ld.getRoleByLdap(ldapRole));
		}
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
		try {
			if (!midContextList.contains(ctx)) {
				midContextList.add(ctx);
				log.debug("Añadiendo contexto " + ctx.getCxValue());
			} else {
				log.debug("El contexto " + ctx.getCxKey() + "-" + ctx.getCxValue() + " ya existe");
			}
		} catch (Exception e) {
			log.error("Error añadiendo contexto a usuario");
			e.printStackTrace();
		}
	}
	
	/**
	 * Añade un contexto a la lista, si no existe ya dicho contexto
	 * @param ctx
	 */
	public void addMidContext(String key, String value) {
		try {
			MidContext ctx = new MidContext(key, value);
			if (!midContextList.contains(ctx)) {
				midContextList.add(ctx);
				log.debug("Añadiendo contexto " + ctx.getCxValue());
			} else {
				log.debug("El contexto " + ctx.getCxKey() + "-" + ctx.getCxValue() + " ya existe");
			}
		} catch (Exception e) {
			log.error("Error añadiendo contexto a usuario");
			e.printStackTrace();
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
		Integer ctx = -1;

		try {
			 ctx = new Integer(context);
		} catch (NumberFormatException e) {
		}

		// comparamos con valor y con id
		for (MidContext m : midContextList) {
			if (m.getCxValue().equals(context)) {
				isIn = true;
				break;
			} else if (m.getCxId().compareTo(ctx) == 0) {
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
		Integer ctx = -1;

		try {
			 ctx = new Integer(context);
		} catch (NumberFormatException e) {
		}

		for (MidContext m : midContextList) {
			if (m.getCxKey().equals(key)) {
				if (m.getCxValue().equals(context)) {
					isIn = true;
					break;
				} else 	if (m.getCxId().compareTo(ctx) == 0) {
					isIn = true;
					break;
				}

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
	 * Devuelve el primer valor encontrado para una clave de contexto determinada.
	 * Dado que los contextos manuales tienen prioridad sobre otros contextos (por ejemplo, los leídos desde getInfoUser, 
	 * esta función devolverá preferentemente los contextos manuales.
	 * @param key Clave de contexto solicitada
	 * @return Primer valor encontrado para esa clave de contexto.
	 */
	public String getFirstContextValue(String key) {
		String s="";
		for (MidContext m : midContextList) {
			if (m.getCxKey().equals(key)) {
				if (m.getCxValue() != null) {
					s = m.getCxValue();
					break;
				}
			}
		}
		return s;
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
		if (AppProperties.getParameter(Constants.CFG_GUIA_GET_INFOUSER).equals("true") &&
				(this.getAuthMode().equals(new Character('S')) || this.getAuthMode() == null ) ) {
	    	GuiaConnection con = new GuiaConnection();
			String username = this.getUserName();
	    	InfoUserResponse response = con.infoUser(username);
			log.debug("Leidos datos del usuario " + username + " desde GUIA.");
			if (response.getResult().startsWith("OK")) {
				boolean hasCias = false;
				boolean hasZbs = false;
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

				// Añadimos CIAS como contexto
				if (!this.getInfoUser().getCias().isEmpty()) {
					this.addMidContext("CIAS", this.getInfoUser().getCias());
					hasCias = true;
				}
				
				// Añadimos cenSanMap como CCSUC
				if(!this.getInfoUser().getCenSanMapId().isEmpty()) {
					this.addMidContext("CSSUC", this.getInfoUser().getCenSanMapId());
					if (this.getInfoUser().getCenSanMapId().startsWith("60") ) {
						this.addMidContext("ZBS", this.getInfoUser().getCenSanMapId().substring(2));
						hasZbs = true;
					}
				}
				
				if (hasCias && !hasZbs) {
					try {
						this.addMidContext("ZBS",structDao.getZonaByCias(this.getInfoUser().getCias()));
					} catch(Exception e) {
						log.debug("Error obteniendo ISpsStructureDao");
					}
				}
				
				// Añadimos CSSUCs como contextos
				List<String> cssUcs = Arrays.asList(this.getInfoUser().getCssUcs().split("\\s*,\\s*"));
				for (String uc : cssUcs) {
					log.debug("Añadiendo CSSUC: " + uc); 
					this.addMidContext("CSSUC", uc);
				}
				
				// Añadimos contexto de SECTOR
				this.addMidContext("SEC", this.getInfoUser().getSecId());
				
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



