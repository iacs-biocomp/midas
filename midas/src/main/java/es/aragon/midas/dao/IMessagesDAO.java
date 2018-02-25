package es.aragon.midas.dao;

import java.util.List;

import es.aragon.midas.config.MidMessage;
import es.aragon.midas.config.MidNotification;

public interface IMessagesDAO {

	/**
	 * Devuelve la lista completa de mensajes disponibles
	 * @return lista de mensajes MidMessage
	 */
	List<MidMessage> findAll();

	/**
	 * Devuelve el detalle de un mensaje a partir de su ID
	 * @param id identificador del mensaje
	 * @return MidMessage con el detalle del mensaje
	 */
	MidMessage findById(int id);

	/**
	 * Devuelve los mensajes de un destinatario en un estado determinado
	 * @param receiver
	 * @param status
	 * @return lista de MidMessage con los mensajes buscados
	 */
	List<MidMessage> findByReceiver(String receiver);

	/**
	 * Devuelve los mensajes de un destinatario en un estado determinado
	 * @param receiver
	 * @param status
	 * @return lista de MidMessage con los mensajes buscados
	 */
	List<MidMessage> findByReceiverStatus(String receiver, String status);

	
	/**
	 * Devuelve las notificaciones de un destinatario en un estado determinado
	 * @param receiver
	 * @param status
	 * @return lista de MidNotification con los mensajes buscados
	 */
	List<MidNotification> findNotByReceiverStatus(String receiver, String status);

	
	/**
	 * Inserta un nuevo mensaje en la base de datos
	 * @param msg el mensaje a insertar
	 */
	void insertMessage(MidMessage msg);

	/**
	 * Actualiza el estado de un mensaje
	 * @param id identificador del mensaje
	 * @param newStatus Nuevo estado del mensaje
	 */
	void changeMsgStatus(int id, String newStatus);

	
	/**
	 * Marca una notificación como leída
	 * @param id Identificador de la notificación a modificar
	 */
	void markNotAsRead(int id);

	void insertAdminMessage(MidMessage msg);

	void markMsgAsRead(int id);

}