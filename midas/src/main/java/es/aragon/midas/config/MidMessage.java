package es.aragon.midas.config;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.time.DateUtils;

import es.aragon.midas.logging.Logger;

import java.util.Calendar;
import java.util.Date;


/**
 * The persistent class for the mid_messages database table.
 * 
 */
@Entity
@Table(name="mid_messages")
@NamedQueries({
    @NamedQuery(name = "MidMessage.findAll", query = "SELECT m FROM MidMessage m"),
    @NamedQuery(name = "MidMessage.findById", query = "SELECT m FROM MidMessage m WHERE m.id = :id"),
    @NamedQuery(name = "MidMessage.findByReceiver", query = "SELECT m FROM MidMessage m WHERE m.receiver = :receiver AND m.status <> 'D' ORDER BY m.sendDate DESC"),
    @NamedQuery(name = "MidMessage.findByReceiverDel", query = "SELECT m FROM MidMessage m WHERE m.receiver = :receiver ORDER BY m.sendDate DESC"),
    @NamedQuery(name = "MidMessage.findByReceiverStatus", query = "SELECT m FROM MidMessage m WHERE m.receiver = :receiver and m.status = :status ORDER BY m.sendDate DESC")})
@XmlRootElement
public class MidMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	static Logger log = new Logger();      
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;

	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="read_date")
	private Date readDate;

	private String receiver;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="send_date")
	private Date sendDate;

	private String sender;

	private String status;
	
	@Column(name="response_to")
	private Integer responseTo;
	
	private String subject;
	
	@Transient
	private String sendDateS;

	public MidMessage() {
	}

	
	public MidMessage(MidMessage m) {
		this.message = m.message;
		this.readDate = m.readDate;
		this.receiver = m.receiver;
		this.sendDate = m.sendDate;
		this.sender = m.sender;
		this.status = m.status;
		this.responseTo = m.responseTo;
		this.subject = m.subject;
	}
	
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getReadDate() {
		return this.readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public Date getSendDate() {
		return this.sendDate;
	}

	
	public String getSendDateS() {
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dt2 = new SimpleDateFormat("HH:mm");
		Date truncatedDate = DateUtils.truncate(this.sendDate, Calendar.DATE);
		Date today = DateUtils.truncate(new Date(), Calendar.DATE);
		if (truncatedDate.equals(today) ) {
			return dt2.format(this.sendDate);
		} else {
			return dt1.format(this.sendDate);
		}
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getResponseTo() {
		return responseTo;
	}

	public void setResponseTo(Integer responseTo) {
		this.responseTo = responseTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}