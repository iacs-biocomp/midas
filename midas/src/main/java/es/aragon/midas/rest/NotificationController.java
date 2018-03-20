package es.aragon.midas.rest;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import es.aragon.midas.config.MidNotification;
import es.aragon.midas.dao.IMessagesDAO;

@Path("/notifications")
public class NotificationController extends MidasRestAbstractController {
    
	
	@Inject
    private IMessagesDAO dao;
	
	{
		setGrantRequired("PUBLIC");
	}

    public NotificationController() {
	}

	
	@POST
	@Path("/read")
	@Consumes("application/json")
	@Produces(MediaType.TEXT_PLAIN)
	public String markAsRead(MidNotification m) {
		if(setUser() ) {
			if (dao != null && m.getId() > 0) {
				dao.markNotAsRead(m.getId());
			}
			return "OK";
		
		} else {
			
			return "ERROR";
		}
	}

	
    @GET
	@Path("/unread")
	@Produces("application/json")
	public List<MidNotification> getUnread() {
		List<MidNotification> lista;
		lista = new ArrayList<MidNotification>();

		if(setUser() ) {

			log.info("Obteniendo notificaciones del usuario " + user.getUserName());
			if (dao != null)
				lista = dao.findNotByReceiverStatus(user.getUserName(), "S");
			else {
				MidNotification m = new MidNotification();
				m.setMessage("Error creando mensajes");
				lista.add(m);
			}

		} else {
			log.info("Obteniendo mensajes de Usuario no definido");
		}
		return lista;
	}
	

}