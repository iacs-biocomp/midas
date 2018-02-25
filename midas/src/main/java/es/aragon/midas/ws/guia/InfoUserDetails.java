package es.aragon.midas.ws.guia;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "infoUser")
public class InfoUserDetails {
	private String status;
	private String login;
	private String nif;
	private String name;
	private String surname1;
	private String surname2;
	private String email;
	private String phone;
	private String secId;
	private String secName;
	private String cenId;
	private String cenName;
	private String gfhId;
	private String gfhName;
	private String catrId;
	private String catrName;
	private String numColegiado;
	private String cias;
	private String cenSanMapId;
	private String statusLDAPSalud;
	private String groupsLDAP;
	private String cssUcs;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname1() {
		return surname1;
	}
	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}
	public String getSurname2() {
		return surname2;
	}
	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSecId() {
		return secId;
	}
	public void setSecId(String secId) {
		this.secId = secId;
	}
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public String getCenId() {
		return cenId;
	}
	public void setCenId(String cenId) {
		this.cenId = cenId;
	}
	public String getCenName() {
		return cenName;
	}
	public void setCenName(String cenName) {
		this.cenName = cenName;
	}
	public String getGfhId() {
		return gfhId;
	}
	public void setGfhId(String gfhId) {
		this.gfhId = gfhId;
	}
	public String getGfhName() {
		return gfhName;
	}
	public void setGfhName(String gfhName) {
		this.gfhName = gfhName;
	}
	public String getCatrId() {
		return catrId;
	}
	public void setCatrId(String catrId) {
		this.catrId = catrId;
	}
	public String getCatrName() {
		return catrName;
	}
	public void setCatrName(String catrName) {
		this.catrName = catrName;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getCias() {
		return cias;
	}
	public void setCias(String cias) {
		this.cias = cias;
	}
	public String getCenSanMapId() {
		return cenSanMapId;
	}
	public void setCenSanMapId(String cenSanMapId) {
		this.cenSanMapId = cenSanMapId;
	}
	public String getStatusLDAPSalud() {
		return statusLDAPSalud;
	}
	public void setStatusLDAPSalud(String statusLDAPSalud) {
		this.statusLDAPSalud = statusLDAPSalud;
	}
	public String getGroupsLDAP() {
		return groupsLDAP;
	}
	public void setGroupsLDAP(String groupsLDAP) {
		this.groupsLDAP = groupsLDAP;
	}
	public String getCssUcs() {
		return cssUcs;
	}
	public void setCssUcs(String cssUcs) {
		this.cssUcs = cssUcs;
	}

}
