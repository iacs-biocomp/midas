package es.aragon.midas.rest;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import es.aragon.midas.config.MidMessage;
import es.aragon.midas.dao.IMessagesDAO;
import es.aragon.midas.rest.MidasRestAbstractController;


@Path("/messages")
public class MessageController extends MidasRestAbstractController {
    
	
	@Inject
    private IMessagesDAO dao;

	{
		setGrantRequired("PUBLIC");
	}
	
    public MessageController() {
	}

	
	@GET
	@Path("/all")
	@Produces("application/json")
	public List<MidMessage> getAllMessages() {
		List<MidMessage> lista;
		lista = new ArrayList<MidMessage>();
		MidMessage m = new MidMessage();
		m.setMessage("Error creando mensajes");
		lista.add(m);
		if(setUser() ) {
			if (dao != null) {
				lista = dao.findByReceiver(user.getUserName());
				log.info("Obteniendo mensajes del usuario " + user.getUserName());
			}
		} else {
			log.info("Obteniendo mensajes de Usuario no definido");
		}
		return lista;
	}
	

	@GET
	@Path("/unread")
	@Produces("application/json")
	public List<MidMessage> getUnread() {
		List<MidMessage> lista;
		lista = new ArrayList<MidMessage>();
		MidMessage m = new MidMessage();
		m.setMessage("Error creando mensajes");
		lista.add(m);

		if(setUser() ) {
			if (dao != null) {
				lista = dao.findByReceiverStatus(user.getUserName(), "S");
				log.info("Obteniendo mensajes del usuario " + user.getUserName());
			} 
		}  else {
			log.info("Obteniendo mensajes de Usuario no definido");
		}
		return lista;
	}
	

	
	@POST
	@Path("/change")
	@Consumes("application/json")
	@Produces(MediaType.TEXT_PLAIN)
	public Response changeStatus(MidMessage m) {
		if(setUser() ) {
			if (dao != null && m.getId() > 0) {
				dao.changeMsgStatus(m.getId(), m.getStatus());
			}
			return Response.status(200).entity("OK").build();
		} else {
			return Response.status(500).entity("ERROR").build();
		}

	}
	
	
	@POST
	@Path("/cau")
	@Consumes("application/json")
	public Response messageToCau (MidMessage msg) {
		if(setUser() ) {
			log.debug("Message received from " + msg.getSender() );
			msg.setSendDate(Calendar.getInstance().getTime());
			msg.setStatus("S");
			if (dao != null) {
				dao.insertAdminMessage(msg);	
			}
			return Response.status(200).entity("OK").build();
		} else {
			return Response.status(500).entity("ERROR").build();
		}
	}
	

}