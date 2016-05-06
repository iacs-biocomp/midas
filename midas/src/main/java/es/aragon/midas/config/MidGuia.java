package es.aragon.midas.config;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the MID_GUIA database table.
 * 
 */
@Entity
@Table(name = "MID_GUIA")
@NamedQueries({
		@NamedQuery(name = "MidGuia.findAll", query = "SELECT m FROM MidGuia m"),
		@NamedQuery(name = "MidGuia.findByAppDst", query = "SELECT m FROM MidGuia m where m.appDst = :appDst") })
public class MidGuia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "APP_DST")
	private String appDst;

	private String secret;

	@Column(name = "URL_DST")
	private String urlDst;

	public MidGuia() {
	}

	public String getAppDst() {
		return this.appDst;
	}

	public void setAppDst(String appDst) {
		this.appDst = appDst;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getUrlDst() {
		return this.urlDst;
	}

	public void setUrlDst(String urlDst) {
		this.urlDst = urlDst;
	}

}