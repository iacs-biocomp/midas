package es.aragon.midas.rest;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import es.aragon.midas.config.MidUser;
import es.aragon.midas.config.MidUserSessions;
import es.aragon.midas.rest.MidasRestAbstractController;


@Path("/sessions")
public class UserSessionController extends MidasRestAbstractController {
    
	MidUserSessions us;
	
    public UserSessionController() {
    	us = MidUserSessions.getInstance();
	}

	
	@GET
	@Path("/all")
	@Produces("application/json")
	public List<MidUser> getAllUserSessions() {
		List<MidUser> lista = us.getAllUserSessions();
		return lista;
	}
	

	@GET
	@Path("/byid/{id}")
	@Produces("application/json")
	public MidUser getUserSessionById(@PathParam("id") String id) {
		MidUser user = us.getUserSession(id);
		return user;
	}	
	
	@GET
	@Path("/byCode/{id}")
	@Produces("application/json")
	public MidUser getUserSessionByCode(@PathParam("id") String id) {
		log.debug("sessions byCode/" + id);
		MidUser user = us.getUserByCode(id);
		return user;
	}	
	
	@GET
	@Path("/code/{id}")
	@Produces("text/plain")
	public String getSessionCode(@PathParam("id") String id) {
		log.debug("sessions code/" + id);
		String user = us.getUserCode(id);
		return user;
	}	
	
}

