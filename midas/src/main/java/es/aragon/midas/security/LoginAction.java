	/**
 *
 */
package es.aragon.midas.security;

import java.security.MessageDigest;
import java.util.Calendar;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
//import javax.enterprise.inject.spi.CDI;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.config.MidUserSessions;
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
import es.aragon.midas.security.auth.*;
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
	private String action;	// action para autenticación con token GUIA
	private String urlAction; // action para redirección desde URL


	//	@Inject
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
		
		String authenticator = AppProperties.getParameter(Constants.CFG_AUTH_AUTHENTICATOR);
		if (authenticator == null) 
			authenticator = Constants.CFG_AUTHENTICATOR_LDAP;
		
/*		if (authenticator.equals(Constants.CFG_AUTHENTICATOR_GUIA) ) {
			loginValidator = CDI.current().select(GuiaValidator.class).get();
		} else if (authenticator.equals(Constants.CFG_AUTHENTICATOR_DUAL) ) {
			loginValidator = CDI.current().select(DualValidator.class).get();
		} else if (authenticator.equals(Constants.CFG_AUTHENTICATOR_NULL) ) {
			loginValidator = CDI.current().select(NullValidator.class).get();
		} else {
			loginValidator = CDI.current().select(LDAPValidator.class).get();
		}
*/
		if (authenticator.equals(Constants.CFG_AUTHENTICATOR_GUIA) ) {
			loginValidator = new GuiaValidator();
		} else if (authenticator.equals(Constants.CFG_AUTHENTICATOR_GUIACERT) ) {
			loginValidator = new GuiaCertValidator();
		} else if (authenticator.equals(Constants.CFG_AUTHENTICATOR_DUAL) ) {
			loginValidator = new DualValidator();
		} else if (authenticator.equals(Constants.CFG_AUTHENTICATOR_NULL) ) {
			loginValidator = new NullValidator();
		} else {
			loginValidator = new LDAPValidator();
		}
		
		MidUser user = null;
		List<String> LdapRoles;
		UsersDAO dao;
		
		log.setUser(username);
		log.setAction("Login");		
		
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
				log.debug("Validacion por token de GUIA");
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
					//		y le pasa los grants al usuario de la aplicacion para le sesion
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
			// Login mediante Nombre y Contraseña
			// **********************************************************
			} else {
				// Verificamos que username y password no son blanco/nulo
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
	
				
				
				
				// Comprobamos si el usuario existe ya en BD (caso de ser necesario)
				if (validateLocal) {
					user = dao.find(username);
					if (user == null) {
						addActionError("El usuario no esta autorizado");
						log.warn("Usuario " + username + "No registrado en Base de datos");
					}
				}
	
				// Si existe el usuario, o no es necesario que exista, intentamos login
				if (user != null || !validateLocal) {
					
					if (username.equals("root") ) {
						MessageDigest md = MessageDigest.getInstance("MD5");
						String hash = AppProperties.getParameter(Constants.CFG_SECURITY_ROOT_PWD);
						if (hash.equals(md.digest(password.getBytes()).toString()) ) {
							log.debug("User Root de Sistema");
						} else { 
							user = null;
						}						

					} else {
						log.debug("Autenticando acceso de usuario " + username);
						user = loginValidator.authenticate(username, password);
		
						// Comprueba si hay algun error al autenticar en el LDAP
						if (loginValidator.getException() != null) {
							// Obtiene el mensaje de error del LDAP
							String msgError = LdapUtils.getDescError(loginValidator.getException());
							addActionError(msgError);
							log.error("Ha ocurrido un error al autenticar contra el AD. Desc: " + msgError);
							accessLogService.error();
							return INPUT;
						}
						
					}
				
				}
					
				// Ha habido error de autenticación
				if (user == null) {
					addActionError("El usuario o la contraseña no son correctos");
					log.warn("Error en usuario o contraseña: " + username);
					accessLogService.fail();
					return INPUT;
				}
	
			}
	
	
			
			
			// Ya tenemos usuario, y se ha logado correctamente
			// Comprueba que el usuario esta activo en la aplicacion
			if (user != null && !String.valueOf(user.getActive()).equalsIgnoreCase("Y")) {
				addActionError("El usuario esta desactivado");
				log.error("El usuario esta desactivado");
				accessLogService.noAutorizado();
				return INPUT;
			}
	
	
	
			// TODO autenticacion con estados: red, incorrecto, no autorizado...
			if (user != null) {
				
				user.assignGrants(); // assignGrants llama a infoUser

				user.setLastLogin(Calendar.getInstance().getTime());
				if (user.getInfoUser() != null ) {
					// Si no tenemos IDD, lo tomamos de infoUser
					if (user.getIdd() == null || user.getIdd().isEmpty()) {
						user.setIdd(user.getInfoUser().getNif());
						user.setName(user.getInfoUser().getName());
						user.setLastname1(user.getInfoUser().getSurname1());
						user.setLastname2(user.getInfoUser().getSurname2());
					}
					// si no hay email registrado y infouser lo tiene...
					if (user.getInfoUser().getEmail() != null) {
						user.setEmail(user.getInfoUser().getEmail());
					}

				}
				dao.update(user);
				
				// user.grantLdapRole("APP-MID-ADMIN");
				session.put(Constants.USER, user);
				MidUserSessions us = MidUserSessions.getInstance();
				us.setUserSession(user.getUserName(), user);
				log.trace("Insertado el usuario " + user.getUserName() + " en UserSessions");
				request.setAttribute(Constants.USER, user);
				accessLogService.setUser(user.getUserName());
				lopd.setUser(user.getUserName());
				accessLogService.access();
				log.debug("Creando sesion de usuario " + username);
				//
				// Poner proyectos de dicho usuario
				//session.put("main_project", "Project 1");
				//List<String> listprojects = Arrays.asList("Project1","Project2", "Project3", "Project4");
				//session.put(Constants.LIST_PROJS, listprojects);
				//
				// Si se ha especificado una accion de struts intenta cargarla
				try {
					if (!StringUtils.nb(action)) {
						this.action = getActionUrl();
						log.debug("Redirigiendo a action " + action);
						return Constants.GUIA_ACTION;
					} else if (!StringUtils.nb(urlAction)) {
						log.debug("Redirigiendo a action " + urlAction);
						return Constants.URL_ACTION;
					}
	
				} catch (Exception e) {
					log.error("Error al redirigir a la accion de struts: " + action, e);
				}
	
				return SUCCESS;
			} else {
				addActionError("El usuario o la contraseña no son correctos");
				log.warn("El usuario o la contraseña no son correctos: " + username);
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
	 * Parsea el JSON del parametro action para generar la URL de struts que
	 * dirige. <br>
	 * EJEMPLO: <br>
	 * <b>Entrada:</b> {"action":"CargarFormularioModificar",
	 * "params":{"id":300, "version":3}} <br>
	 * <b>Salida:</b> CargarFormularioModificar?id=300&version=3
	 * 
	 * @return URL de struts a la que hacer la redireccion
	 */
	private String getActionUrl() {
		JSONObject jsonAction = new JSONObject(action);
		// Obtiene la lista de parametros y su objeto JSON
		JSONObject params = jsonAction.getJSONObject("params");
		String[] paramsNames = JSONObject.getNames(params);

		StringBuilder actionBuild = new StringBuilder();
		actionBuild.append(jsonAction.getString("action"));

		// Recorre los parametros para constuir su String
		if (paramsNames != null) {
			actionBuild.append("?");
			for (int i = 0; i < paramsNames.length; i++) {
				String paramName = paramsNames[i];
				actionBuild.append(paramName);
				actionBuild.append("=");
				actionBuild.append(params.get(paramName));
				// Si es el ultimo parametro no añade &
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
	 * Asigna la sesion en la que se esta ejecutando el autenticador
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

	public String getUrlAction() {
		return urlAction;
	}

	public void setUrlAction(String urlAction) {
		int l = urlAction.length();
		if (urlAction.endsWith("?")) l--;
		this.urlAction = urlAction.substring(1, l);
	}


}
