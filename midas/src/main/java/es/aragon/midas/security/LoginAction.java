/**
 *
 */
package es.aragon.midas.security;

import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.exception.MidasJPAException;
import es.aragon.midas.ldap.LdapUtils;
import es.aragon.midas.logging.IAccessLogger;
import es.aragon.midas.logging.ILOPDLogger;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.security.auth.LoginValidator;
import es.aragon.midas.util.StringUtils;
import es.aragon.midas.ws.guia.AuthGuiaDetails;
import es.aragon.midas.ws.guia.GuiaConnection;

/**
 * @author carlos
 * 
 */
public class LoginAction extends ActionSupport implements SessionAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private String username;
	private String password;
	private String ticket;

	// GUIA TOKEN
	private String appSrc;
	private String token;
	private String action;

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
		boolean validateLocal = AppProperties.getParameter(Constants.CFG_VALIDATE_LOCAL).equals("true");
		MidUser user = null;
		List<String> LdapRoles;
		UsersDAO dao;
		
		try {
			dao = (UsersDAO) new InitialContext().lookup("java:module/UsersDAO");
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

			// **********************************************************
			// Login mediante token de GUIA
			// **********************************************************
			if (!StringUtils.nb(token)) {
				log.debug("Validaci�n por token de GUIA");
				GuiaConnection guiaConnection = new GuiaConnection();

				// Realiza la consulta a GUIA
				String respuestaGuia = guiaConnection.validateToken(token, appSrc);

				AuthGuiaDetails userGuia = new AuthGuiaDetails(respuestaGuia);

				// Comprueba que no haya habido errores al consultar en GUIA
				if (!StringUtils.nb(userGuia.getErrorDesc())) {
					log.error("Error en la validacion de token GUIA: " + userGuia.getErrorDesc());
					accessLogService.error();
					return INPUT;
				} else {
					// Busca al usuario en midas MID_USERS
					user = dao.find(userGuia.getLogin().toUpperCase());
					
					// Busca los Roles que tiene el usuario en el LDAP
					//		y le pasa los grants al usuario de la aplicaci�n para le sesi�n
					LdapRoles = userGuia.getGroupsLDAPList();
					ListIterator<String> it = LdapRoles.listIterator();
					while(it.hasNext()){
						String m = it.next();
						log.debug("ROL COGIDO DE LDAP: " + m);
						user.grantLdapRole(m);
					}
				}
			// **********************************************************
			// Login mediante Tarjeta
			// **********************************************************	
			} else if (!StringUtils.nb(ticket)) {
				user = loginValidator.authenticate(ticket);
			// **********************************************************
			// Login mediante Nombre y Contrase�a
			// **********************************************************
			} else {
				if (StringUtils.nb(username)) {
					addActionError("El nombre no puede estar en blanco");
					log.error("El nombre no puede estar en blanco");
				}
				if (StringUtils.nb(password)) {
					addActionError("La contrase�a no puede estar en blanco");
					log.error("La contrase�a no puede estar en blanco");
				}

				if (StringUtils.nb(username) || StringUtils.nb(password)) {
					accessLogService.blank();
					return INPUT;
				}

				// Comprobamos si el usuario existe ya en BD
				user = dao.find(username);

				if (user == null && validateLocal) {
					accessLogService.noAutorizado();
					addActionError("El usuario no est� autorizado");
					log.warn("Usuario " + username + "No registrado en Base de datos");
				} else {
					if (AppProperties.getParameter(Constants.CFG_AUTH_IGNOREPASS) == null
							|| AppProperties.getParameter(Constants.CFG_AUTH_IGNOREPASS).equals(Constants.FALSE)) {
						log.debug("Autenticando acceso de usuario " + username);
						user = loginValidator.authenticate(username, password);
					}
				}
			}

			// Comprueba que el usuario est� activo en la aplicaci�n
			if (user != null && !String.valueOf(user.getActive()).equalsIgnoreCase("Y")) {
				addActionError("El usuario est� desactivado");
				log.error("El usuario est� desactivado");
				accessLogService.noAutorizado();
				return INPUT;
			}

			// Comprueba si hay algun error al autenticar en el LDAP
			if (loginValidator.getException() != null) {
				// Obtiene el mensaje de error del LDAP
				String msgError = LdapUtils.getDescError(loginValidator.getException());
				addActionError(msgError);
				log.error("Ha ocurrido un error al autenticar contra el AD. Desc: " + msgError);
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
				log.debug("Creando sesi�n de usuario " + username);

				// Si se ha especificado una acci�n de struts intenta cargarla
				try {
					if (!StringUtils.nb(action)) {
						this.action = getActionUrl();
						return "guiaAction";
					}
				} catch (Exception e) {
					log.error("Error al redirigir a la acci�n de struts: " + action, e);
				}

				return SUCCESS;
			} else {
				addActionError("El usuario o la contrase�a no son correctos");
				log.warn("El usuario o la contrase�a no son correctos: " + username);
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
	 * Parsea el JSON del par�metro action para generar la URL de struts que
	 * dirige. <br>
	 * EJEMPLO: <br>
	 * <b>Entrada:</b> {"action":"CargarFormularioModificar",
	 * "params":{"id":300, "version":3}} <br>
	 * <b>Salida:</b> CargarFormularioModificar?id=300&version=3
	 * 
	 * @return URL de struts a la que hacer la redirecci�n
	 */
	private String getActionUrl() {
		JSONObject jsonAction = new JSONObject(action);
		// Obtiene la lista de parametros y su objeto JSON
		JSONObject params = jsonAction.getJSONObject("params");
		String[] paramsNames = JSONObject.getNames(params);

		StringBuilder actionBuild = new StringBuilder();
		actionBuild.append(jsonAction.getString("action"));

		// Recorre los par�metros para constuir su String
		if (paramsNames != null) {
			actionBuild.append("?");
			for (int i = 0; i < paramsNames.length; i++) {
				String paramName = paramsNames[i];
				actionBuild.append(paramName);
				actionBuild.append("=");
				actionBuild.append(params.get(paramName));
				// Si es el �ltimo parametro no a�ade &
				if (i < paramsNames.length - 1) {
					actionBuild.append("&");
				}
			}
		}

		return actionBuild.toString();
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
	 * Asigna la sesi�n en la que se est� ejecutando el autenticador
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppSrc() {
		return appSrc;
	}

	public void setAppSrc(String appSrc) {
		this.appSrc = appSrc;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
