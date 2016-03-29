/**
 *
 */
package es.aragon.midas.security;

import com.opensymphony.xwork2.ActionSupport;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.exception.MidasJPAException;
import es.aragon.midas.logging.IAccessLogger;
import es.aragon.midas.logging.ILOPDLogger;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.security.auth.LoginValidator;
import es.aragon.midas.util.LdapUtils;
import es.aragon.midas.util.StringUtils;

import java.util.Calendar;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 * @author carlos
 * 
 */
public class LoginAction extends ActionSupport implements SessionAware,
		ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private String username;
	private String password;
	private String ticket;
	@Inject
	private LoginValidator loginValidator;
	@Inject
	private IAccessLogger accessLogService;
	@Inject
	private ILOPDLogger lopd;
	private HttpServletRequest request;
	private Logger log = new Logger();

	/**
	 * Carga el autenticador seleccionado y autentica al usuario que intenta
	 * logarse
	 */
	@Override
	public String execute() throws MidasJPAException {
		boolean validateLocal = AppProperties.getParameter(
				Constants.CFG_VALIDATE_LOCAL).equals("true");
		MidUser user;
		UsersDAO dao;

		try {
			dao = (UsersDAO) new InitialContext()
					.lookup("java:module/UsersDAO");
		} catch (NamingException e) {
			log.error("Error accediendo a EJB UsersDAO");
			return ERROR;
		}

		try {
			if (accessLogService != null) {
				accessLogService.setIp(request.getRemoteAddr());
			} else {
				log.error("accessLog Nulo. No se puede asignar IP ni grabar acceso");
			}

			if (!StringUtils.nb(ticket)) {
				user = loginValidator.authenticate(ticket);
			} else {
				if (StringUtils.nb(username)) {
					addActionError("El nombre no puede estar en blanco");
					log.error("El nombre no puede estar en blanco");
				}
				if (StringUtils.nb(password)) {
					addActionError("La contraseña no puede estar en blanco");
					log.error("La contraseña no puede estar en blanco");
				}

				if (StringUtils.nb(username) || StringUtils.nb(password)) {
					accessLogService.blank();
					return INPUT;
				}

				// Comprobamos si el usuario existe ya en BD
				user = dao.find(username);

				// Comprueba que el usuario esté activo en la aplicación
				if (user != null
						&& !String.valueOf(user.getActive()).equalsIgnoreCase(
								"Y")) {
					addActionError("El usuario está desactivado");
					log.error("El usuario está desactivado");
					accessLogService.noAutorizado();
					return INPUT;
				}

				if (user == null && validateLocal) {
					accessLogService.noAutorizado();
					addActionError("El usuario no está autorizado");
					log.warn("Usuario " + username
							+ "No registrado en Base de datos");
				} else {
					if (AppProperties
							.getParameter(Constants.CFG_AUTH_IGNOREPASS) == null
							|| AppProperties.getParameter(
									Constants.CFG_AUTH_IGNOREPASS).equals(
									Constants.FALSE)) {
						log.debug("Autenticando acceso de usuario " + username);
						user = loginValidator.authenticate(username, password);
					}
				}
			}

			// Comprueba si hay algun error al autenticar en el LDAP
			if (loginValidator.getException() != null) {
				// Obtiene el mensaje de error del LDAP
				String msgError = LdapUtils.getDescError(loginValidator
						.getException());
				addActionError(msgError);
				log.error("Ha ocurrido un error al autenticar contra el AD. Desc: "
						+ msgError);
				accessLogService.error();
				return INPUT;
			}

			// TODO autenticacion con estados: red, incorrecto, no autorizado...
			if (user != null) {
				user.setLastLogin(Calendar.getInstance().getTime());

				dao.update(user);
				// Todos los usuarios tienen grant PUBLIC
				user.grant("PUBLIC");
				// Captura los grants configurados en BD
				user.obtainGrants();

				// Captura los contexts configurados en BD
				user.obtainContexts();

				// user.grantLdapRole("APP-MID-ADMIN");
				session.put(Constants.USER, user);
				accessLogService.setUser(user.getUserName());
				lopd.setUser(user.getUserName());
				accessLogService.access();
				log.debug("Creando sesión de usuario " + username);
				return SUCCESS;
			} else {
				addActionError("El usuario o la contraseña no son correctos");
				log.warn("El usuario o la contraseña no son correctos: "
						+ username);
				accessLogService.fail();
				return INPUT;
			}
		} catch (Exception ce) {
			try {
				log.error("Error autenticando usuario", ce);
				accessLogService.error();
			} catch (Exception ex) {
				log.error("Error autenticando usuario", ex);
			}
			return INPUT;
		}
	}

	/**
	 * 
	 * @param lv
	 */
	public void setAuthenticationService(LoginValidator lv) {
		this.loginValidator = lv;
	}

	/**
	 * 
	 * @param accessLogger_
	 */
	public void setAccessLogService(IAccessLogger accessLogger_) {
		this.accessLogService = accessLogger_;
	}

	/**
	 * 
	 * @param lopd_
	 */
	public void setLopdLogService(ILOPDLogger lopd_) {
		this.lopd = lopd_;
	}

	/**
	 * Asigna la sesiï¿½n en la que se estï¿½ ejecutando el autenticador
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * Obtiene el id del usuario
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Indica el ID del usuario
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
		if (accessLogService != null) {
			accessLogService.setUser(username);
		}
	}

	/**
	 * Introduce el password con el que intenta autenticarse el usuario
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Devuelve el password del usuario
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public void setServletRequest(HttpServletRequest hsr) {
		this.request = hsr;
	}

	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * @param ticket
	 *            the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
