/**
 *
 */
package es.aragon.midas.security;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.SessionAware;

import es.aragon.midas.action.MidasActionSupport;
import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.exception.MidasJPAException;
import es.aragon.midas.logging.IAccessLogger;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.util.StringUtils;
import es.aragon.midas.util.Utils;
import es.aragon.midas.ws.guia.GuiaConnection;
import es.aragon.midas.ws.guia.InfoUserResponse;

/**
 * Action que permite que un usuario asuma la identidad de otro usuario
 * @author carlos
 * 
 */
public class AliasAction extends MidasActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private String newUsername;
	private static final String AUDIT_ENTITY = "Alias";	

	@Inject
	private IAccessLogger accessLogService;

	private HttpServletRequest request;
	private Logger log = new Logger();

	{
		setGrantRequired("ALIAS");
	}
	
	/**
	 * Asigna como user de sesión la identidad del usuario indicado en newUsername (form parameter), si existe.
	 * @return ERROR si hay algún error
	 * @return INPUT si hay algún error en la identificación, volviendo al formulario original
	 * @return SUCCESS si se ha modificado la identidad del usuario sin problemas 
	 * @throws MidasJPAException
	 */
	public String set() throws MidasJPAException {

		MidUser newUser = null;
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

			// Verificamos que username y password no son blanco/nulo
			if (StringUtils.nb(newUsername)) {
				addActionError("El nombre no puede estar en blanco");
				log.error("El nombre no puede estar en blanco");
			} else {
				log.warn("Buscando usuario " +  newUsername + " para aliasing");
				newUser = dao.find(newUsername);
			}
			
			
			if (newUser == null) {
				addActionError("El usuario no existe en el sistema");
				log.warn("Usuario " +  newUsername + "No registrado en Base de datos");
				return INPUT;				
			}

			
			newUser.assignGrants();
			
			newUser.setAliased(true);
			newUser.setActualUser(user);
			newUser.setIdd(user.getIdd());
			
			// user.grantLdapRole("APP-MID-ADMIN");
			session.put(Constants.USER, newUser);
			request.setAttribute(Constants.USER, newUser);
			log.debug("Creando sesion de usuario " + newUsername);

			audit.log("ALS", AUDIT_ENTITY, user.getUserName(), "El usuario asume la identidad de  " + newUsername);

			return SUCCESS;

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
	 * Muestra el formulario para insertar el nombre del usuario alias
	 * @return
	 */
	public String show() {
		return INPUT;
	}
	
	
	
	
	/**
	 * 
	 * @param accessLogger_
	 */
	public void setAccessLogService(IAccessLogger accessLogger_) {
		this.accessLogService = accessLogger_;
	}

	/**
	 * Asigna la sesion en la que se esta ejecutando el autenticador
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}


	/**
	 * Asigna el httpServletRequest de la solicitud
	 */
	public void setServletRequest(HttpServletRequest hsr) {
		this.request = hsr;
	}


	/**
	 * Devuelve el nuevo username 
	 * @return newUsername
	 */
	public String getNewUsername() {
		return newUsername;
	}


	/**
	 * Asigna el nuevo username desde parámetro de formulario o query
	 * @param newUsername
	 */
	public void setNewUsername(String newUsername) {
		this.newUsername = newUsername;
	}

}
