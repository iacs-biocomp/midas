package es.aragon.midas.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import es.aragon.midas.config.MidMessage;
import es.aragon.midas.config.MidNotification;
import es.aragon.midas.config.MidRole;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.logging.Logger;



@Stateless
public class MessagesDAO implements IMessagesDAO {

    //private EntityManager em = ConnectionFactory.getMidasEMF().createEntityManager();  
    @PersistenceContext(unitName="midas4")
    private EntityManager midasEntityManager;

    @Context
    private HttpServletRequest request;
        
	static Logger log = new Logger();        
    

	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IMessagesDAO#findAll()
	 */
	@Override
	public List<MidMessage> findAll () {
		Query query = midasEntityManager.createNamedQuery("MidMessage.findAll");
		@SuppressWarnings("unchecked")
		List<MidMessage> messages = query.getResultList();		
		return messages;
	}
	
	
	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IMessagesDAO#findById(java.lang.String)
	 */
	@Override
	public MidMessage findById(int id){
		Query query = midasEntityManager.createNamedQuery("MidMessage.findById");
		MidMessage message = (MidMessage) query.setParameter("id",id).getSingleResult();		
		return message; 
	}
	
	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IMessagesDAO#findByReceiver(java.lang.String, java.lang.String)
	 */
	@Override
	public List<MidMessage> findByReceiver(String receiver){
		Query query = midasEntityManager.createNamedQuery("MidMessage.findByReceiver");
		@SuppressWarnings("unchecked")
		List<MidMessage> messages = query.setParameter("receiver",receiver).getResultList();		
		return messages; 
	}
	
	
	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IMessagesDAO#findByReceiverStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public List<MidMessage> findByReceiverStatus(String receiver, String status){
		Query query = midasEntityManager.createNamedQuery("MidMessage.findByReceiverStatus");
		@SuppressWarnings("unchecked")
		List<MidMessage> messages = query.setParameter("receiver",receiver).setParameter("status", status).getResultList();		
		return messages; 
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IMessagesDAO#findByReceiverStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public List<MidNotification> findNotByReceiverStatus(String receiver, String status){
		Query query = midasEntityManager.createNamedQuery("MidNotification.findByReceiverStatus");
		@SuppressWarnings("unchecked")
		List<MidNotification> messages = query.setParameter("receiver",receiver).setParameter("status", status).getResultList();		
		return messages; 
	}
	
	
	@Override
	public void insertMessage(MidMessage msg) {
		midasEntityManager.persist(msg);
	}
	
	
	@Override
	public void changeMsgStatus(int id, String newStatus) {
		MidMessage msg = findById(id);
		msg.setStatus(newStatus);
		if ("R".equals(newStatus)) {
			msg.setReadDate(new Date());
		}
		midasEntityManager.merge(msg);
	}

	@Override
	public void markNotAsRead(int id) {
		changeNotStatus(id, "R");
	}
	

	@Override
	public void markMsgAsRead(int id) {
		changeMsgStatus(id, "R");
	}	
	
	private void changeNotStatus(int id, String status) {
		MidNotification not = midasEntityManager.find(MidNotification.class, id);
		if (not != null) {
			not.setStatus(status);
			midasEntityManager.merge(not);
		}
	}
	
	@Override
	public void insertAdminMessage(MidMessage msg) {
		log.debug("Obteniendo relación de usuarios con rol SUPPORT");
		List<MidUser> users = midasEntityManager.find(MidRole.class, "SUPPORT").getMidUserList();
		
		for (MidUser u : users) {
			log.debug("Sending msg to " + u.getUserName());
			MidMessage m = new MidMessage(msg);
			m.setReceiver(u.getUserName());
			midasEntityManager.persist(m);
		}
		
	}
}
