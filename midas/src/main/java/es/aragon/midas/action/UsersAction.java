package es.aragon.midas.action;

import java.util.List;

import javax.ejb.EJB;

import org.apache.commons.validator.GenericValidator;

import es.aragon.midas.config.MidContext;
import es.aragon.midas.config.MidRole;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.ContextsDAO;
import es.aragon.midas.dao.RolesDAO;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.ldap.LdapUtils;
import es.aragon.midas.ldap.UserLdap;

/**
 * Acciones para la gestión de usuarios de una aplicación
 * 
 * @author Carlos
 * 
 */
public class UsersAction extends MidasActionSupport {

	static final long serialVersionUID = 1L;

	{
		setGrantRequired("ADMIN");
	}

	@EJB(name = "RolesDAO")
	RolesDAO rolesDAO;

	@EJB(name = "ContextsDAO")
	ContextsDAO contextsDAO;

	@EJB(name = "UsersDAO")
	UsersDAO usersDAO;

	private MidUser userMod = new MidUser();
	private MidRole userRol = new MidRole();
	private MidContext userContext = new MidContext();

	private MidUser userNew = new MidUser();
	private String readOnly = "false";

	/*****************************************
	 * Métodos de action
	 *****************************************/

	/**
	 * Acción por defecto. Lleva a la página principal de gestión de usuarios
	 */
	@Override
	public String execute() {
		return "users";
	}

	/**
	 * Redirige a la página de listado de usuarios
	 * 
	 * @return
	 */
	public String list() {
		return "users";
	}

	/**
	 * Busca en el LDAP el nombre de acceso a partir de su email
	 * 
	 * @return Recarga el div de newUserName con el nombre de acceso
	 */
	public String searchUserName() {

		log.debug("Buscando el username para el email: " + userNew.getEmail());
		try {
			UserLdap userByLdap = LdapUtils.getUserLogin(userNew.getEmail());
			if(userByLdap != null){
				userNew.setUserName(userByLdap.getLogin());
				userNew.setIdd(userByLdap.getNif());
				userNew.setName(userByLdap.getName());
				userNew.setLastname1(userByLdap.getSurnames());
				readOnly = "true";
			}else{
				readOnly = "false";
			}

		} catch (Exception e) {
			log.error("Error al obtener el nombre de usuario del LDAP", e);
		}

		log.debug("Resultado de la búsqueda: " + userNew.getUserName());

		return "newUserName";
	}

	/**
	 * Guarda en BD un nuevo usuario
	 * 
	 * @return Muestra el error de creación o la ventana de permisos de
	 *         usuarios
	 */
	public String nuevo() {

		// Comprueba que se haya encontrado el userName
		if (GenericValidator.isBlankOrNull(userNew.getUserName())) {
			addActionError("UserName no puede estár en blanco");
			return "users";
		}

		// Comprueba que el usuario no se encuentre ya registrado
		if (usersDAO.find(userNew.getUserName()) != null) {
			addActionError("El usuario ya está registrado");
			return "users";
		}

		// Crea al usuario en la tabla de MID_USERS
		userNew.setUserName(userNew.getUserName().toUpperCase());
		userNew.setActive('Y');
		usersDAO.create(userNew);
		userMod = usersDAO.findByUserName(userNew.getUserName());

		return "users";
	}

	/**
	 * Modifica nombre, apellidos e idd del usuario
	 * 
	 * @return Ventana de usuario con los datos modificados del usuario
	 */
	public String modificar() {
		// Busca el usuario sin cambios
		MidUser userMod = usersDAO.findByUserName(this.userMod.getUserName());

		// Actualiza los campos modificables
		userMod.setIdd(this.userMod.getIdd());
		userMod.setName(this.userMod.getName());
		userMod.setLastname1(this.userMod.getLastname1());
		userMod.setLastname2(this.userMod.getLastname2());

		// Modifica al usuario en base de datos
		this.userMod = usersDAO.update(userMod);

		addActionMessage("Usuario modificado correctamente");
		return "users";
	}

	/**
	 * Activa/desactiva un usuario
	 * 
	 * @return
	 */
	public String activar() {
		Character activo = userMod.getActive();
		if (!GenericValidator.isBlankOrNull(userMod.getUserName())
				&& activo != null) {
			userMod = usersDAO.findByUserName(userMod.getUserName());
			userMod.setActive(activo);
			usersDAO.update(userMod);
			userMod = new MidUser();
		}
		return "users";
	}

	/**
	 * Selecciona un usuario
	 * 
	 * @return
	 */
	public String select() {
		if (!GenericValidator.isBlankOrNull(userMod.getUserName())) {
			userMod = usersDAO.findByUserName(userMod.getUserName());
		}
		return "users";
	}

	/**
	 * Asocia un rol a un usuario
	 * 
	 * @return
	 */
	public String nuevoUR() {
		if (!GenericValidator.isBlankOrNull(userMod.getUserName())
				&& !GenericValidator.isBlankOrNull(userRol.getRoleId())) {
			userMod = usersDAO.findByUserName(userMod.getUserName());
			userRol = rolesDAO.findByRoleId(userRol.getRoleId());
			if (!userRol.getMidUserList().contains(userMod)) {
				usersDAO.addUserRole(userMod, userRol);
			}
		}
		return "users";
	}

	/**
	 * Elimina un rol a un usuario
	 * 
	 * @return
	 */
	public String borrarUR() {
		if (!GenericValidator.isBlankOrNull(userMod.getUserName())
				&& !GenericValidator.isBlankOrNull(userRol.getRoleId())) {
			userMod = usersDAO.findByUserName(userMod.getUserName());
			userRol = rolesDAO.findByRoleId(userRol.getRoleId());
			if (userRol.getMidUserList().contains(userMod)) {
				usersDAO.deleteUserRole(userMod, userRol);
			}
			userMod = usersDAO.findByUserName(userMod.getUserName());
		}
		return "users";
	}

	/**
	 * Añade un contexto a un usuario
	 * @return
	 */
	public String nuevoUC() {
		if (!GenericValidator.isBlankOrNull(userMod.getUserName())
				&& userContext.getCxId() != null) {
			userMod = usersDAO.findByUserName(userMod.getUserName());
			userContext = contextsDAO.findByCxId(userContext.getCxId());
			if (!userContext.getMidUserList().contains(userMod)) {
				usersDAO.addUserContext(userMod, userContext);
			}
			userMod = usersDAO.findByUserName(userMod.getUserName());
		}
		return "users";
	}

	/**
	 * Elimina un contexto a un usuario
	 * 
	 * @return
	 */
	public String borrarUC() {
		if (!GenericValidator.isBlankOrNull(userMod.getUserName())
				&& userContext.getCxId() != null) {
			userMod = usersDAO.findByUserName(userMod.getUserName());
			userContext = contextsDAO.findByCxId(userContext.getCxId());
			if (userContext.getMidUserList().contains(userMod)) {
				usersDAO.deleteUserContext(userMod, userContext);
			}
			userMod = usersDAO.findByUserName(userMod.getUserName());
		}
		return "users";
	}

	/* *****************************************
	 * SET/GET*****************************************
	 */

	/**
	 * Devuelve el usuario desde variable de sesión
	 * 
	 * @return
	 */
	public MidUser getUserMod() {
		return userMod;
	}

	/**
	 * Asocia un usuario a una variable de sesión
	 * 
	 * @param user
	 */
	public void setUserMod(MidUser user) {
		this.userMod = user;
	}

	/**
	 * Devuelve la lista de usuarios
	 * 
	 * @return
	 */
	public List<MidUser> getUsers() {
		return usersDAO.findAll();
	}

	/**
	 * Devuelve la lista de roles desde BD
	 * 
	 * @return
	 */
	public List<MidRole> getRoles() {
		return rolesDAO.findAll();
	}

	/**
	 * Devuelve la lista de roles no asignados aún, desde BD
	 * 
	 * @return
	 */
	public List<MidRole> getRolesUngiven() {
		return rolesDAO.findAllUngiven(userMod);
	}

	/**
	 * Devuelve la lista de contextos desde BD
	 * 
	 * @return
	 */
	public List<MidContext> getContexts() {
		return contextsDAO.findAll();
	}

	/**
	 * Devuelve el rol de usuario
	 * 
	 * @return
	 */
	public MidRole getUserRol() {
		return userRol;
	}

	/**
	 * Asigna el rol de usuario
	 * 
	 * @param userRol
	 */
	public void setUserRol(MidRole userRol) {
		this.userRol = userRol;
	}

	/**
	 * Devuelve el contexto de un usuario
	 * 
	 * @return
	 */
	public MidContext getUserContext() {
		return userContext;
	}

	/**
	 * Asigna el contexto de un usuario
	 * 
	 * @param userContext
	 */
	public void setUserContext(MidContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * Devuelve el nuevo usuario
	 * 
	 * @return
	 */
	public MidUser getUserNew() {
		return userNew;
	}

	/**
	 * Asigna el nuevo usuario
	 * 
	 * @param userNew
	 */
	public void setUserNew(MidUser userNew) {
		this.userNew = userNew;
	}
	
	public String getReadOnly(){
		return readOnly;
	}
}
