/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.ws.guia;

import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */

@XmlType(name="authGUIA")
public class AuthGuiaDetails {
    private String authType;
    private String statusGUIA;
    private String statusLDAPSalud;
    private String login;    
    private String name;
    private String surname1;
    private String surname2;
    private String groupsLDAP;
    private String nif;
    private String cenSanMapId;
    private String cssUcs;    

    /**
     * @return the authType
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * @param authType the authType to set
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    /**
     * @return the statusGUIA
     */
    public String getStatusGUIA() {
        return statusGUIA;
    }

    /**
     * @param statusGUIA the statusGUIA to set
     */
    public void setStatusGUIA(String statusGUIA) {
        this.statusGUIA = statusGUIA;
    }

    /**
     * @return the statusLDAPSalud
     */
    public String getStatusLDAPSalud() {
        return statusLDAPSalud;
    }

    /**
     * @param statusLDAPSalud the statusLDAPSalud to set
     */
    public void setStatusLDAPSalud(String statusLDAPSalud) {
        this.statusLDAPSalud = statusLDAPSalud;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname1
     */
    public String getSurname1() {
        return surname1;
    }

    /**
     * @param surname1 the surname1 to set
     */
    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    /**
     * @return the surname2
     */
    public String getSurname2() {
        return surname2;
    }

    /**
     * @param surname2 the surname2 to set
     */
    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    /**
     * @return the groupsLDAP
     */
    public String getGroupsLDAP() {
        return groupsLDAP;
    }

    /**
     * @param groupsLDAP the groupsLDAP to set
     */
    public void setGroupsLDAP(String groupsLDAP) {
        this.groupsLDAP = groupsLDAP;
    }

    /**
     * @return the nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * @param nif the nif to set
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the cenSanMapId
     */
    public String getCenSanMapId() {
        return cenSanMapId;
    }

    /**
     * @param cenSanMapId the cenSanMapId to set
     */
    public void setCenSanMapId(String cenSanMapId) {
        this.cenSanMapId = cenSanMapId;
    }

    /**
     * @return the cssUcs
     */
    public String getCssUcs() {
        return cssUcs;
    }

    /**
     * @param cssUcs the cssUcs to set
     */
    public void setCssUcs(String cssUcs) {
        this.cssUcs = cssUcs;
    }
}
