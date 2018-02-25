package es.aragon.midas.config;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;


/**
 * The persistent class for the mid_notification database table.
 * 
 */
@Entity
@Table(name="mid_notification")
@NamedQueries({
    @NamedQuery(name = "MidNotification.findAll", query = "SELECT m FROM MidNotification m"),
    @NamedQuery(name = "MidNotification.findById", query = "SELECT m FROM MidNotification m WHERE m.id = :id"),
    @NamedQuery(name = "MidNotification.findByReceiver", query = "SELECT m FROM MidNotification m WHERE m.receiver = :receiver ORDER BY m.sendDate DESC"),
    @NamedQuery(name = "MidNotification.findByReceiverStatus", query = "SELECT m FROM MidNotification m WHERE m.receiver = :receiver and m.status = :status ORDER BY m.sendDate DESC")})
@XmlRootElement
public class MidNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="read_date")
	private Date readDate;

	private String receiver;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="send_date")
	private Date sendDate;

	private String status;
	
	private String style;

	public MidNotification() {
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

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}