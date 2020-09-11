	package es.aragon.midas.ws.guia;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * Clase para almacenar la respuesta de GUIA al llamar a validateToken y a authGUIA
 */
public class AuthGuiaDetails {
    /** Contiene la descripcion del error cuando el resultado es 'KO' */
    private String errorDesc;

    // CAMPOS COMUNES EN AUTENTICACION
    private String statusUser;
    private String statusGUIA;
    private String statusLDAPSalud;
    private String login;
    private String nif;
    private String name;
    private String surname1;
    private String surname2;
    private String numColegiado;
    private String cenSanMapId;
    private String groupsLDAP;
    private String cssUcs;

    // CAMPOS ESPECIFICOS EN AUTENTICACION POR USUARIO + PASSWORD
    private String authType;
    private String cias;

    // CAMPOS ESPECIFICOS EN AUTENTICACION POR TOKEN
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



    public AuthGuiaDetails() throws Exception {
    }

    
    /**
     * Constructor a partir de una String que contiene el XML devuelto al autenticar en GUIA
     * @param resultadoGuiaXML XML con la respuesta de GUIA
     * @throws Exception
     */
    public AuthGuiaDetails(String resultadoGuiaXML) throws Exception {
        String status = null;

        SAXBuilder builder = new SAXBuilder();
        Document xml_jdom = builder.build(new StringReader(resultadoGuiaXML));
        Element dataRoot = xml_jdom.getRootElement();

        status = dataRoot.getChildText("result");

        if (status != null && status.equals("OK")) {
            Element operadorDOM;
            // Resultado obtenido autenticando por usuario + password
            operadorDOM = dataRoot.getChild("authGUIA");
            // Resultado obtenido autenticando por token
            if (operadorDOM == null) {
                operadorDOM = dataRoot.getChild("infoUser");
            }


            // CAMPOS COMUNES EN AUTENTICACIoN
            // Resultado obtenido autenticando por usuario + password
            setStatusUser(operadorDOM.getChildText("statusGUIA"));
            // Resultado obtenido autenticando por token
            if (getStatusUser() == null || getStatusUser().equals("")) {
                setStatusUser(operadorDOM.getChildText("status"));
            }
            setStatusLDAPSalud(operadorDOM.getChildText("statusLDAPSalud"));
            setLogin(operadorDOM.getChildText("login"));
            setNif(operadorDOM.getChildText("nif"));
            setName(operadorDOM.getChildText("name"));
            setSurname1(operadorDOM.getChildText("surname1"));
            setSurname2(operadorDOM.getChildText("surname2"));
            setNumColegiado(operadorDOM.getChildText("numColegiado"));
            setCenSanMapId(operadorDOM.getChildText("cenSanMapId"));
            setGroupsLDAP(operadorDOM.getChildText("groupsLDAP"));
            setCssUcs(operadorDOM.getChildText("cssUcs"));

            // CAMPOS ESPECiFICOS EN AUTENTICACIoN POR USUARIO + PASSWORD
            setAuthType(operadorDOM.getChildText("authType"));
            setCias(operadorDOM.getChildText("cias"));

            // CAMPOS ESPECiFICOS EN AUTENTICACIoN POR TOKEN
            setEmail(operadorDOM.getChildText("email"));
            setPhone(operadorDOM.getChildText("phone"));
            setSecId(operadorDOM.getChildText("secId"));
            setSecName(operadorDOM.getChildText("secName"));
            setCenId(operadorDOM.getChildText("cenId"));
            setCenName(operadorDOM.getChildText("cenName"));
            setGfhId(operadorDOM.getChildText("gfhId"));
            setGfhName(operadorDOM.getChildText("gfhName"));
            setCatrId(operadorDOM.getChildText("catrId"));
            setCatrName(operadorDOM.getChildText("catrName"));

        } else {
            // DESCRIPCION DEL ERROR
            setErrorDesc(dataRoot.getChildText("msg"));
        }

    }


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

    public String getStatusGUIA() {
		return statusGUIA;
	}


	public void setStatusGUIA(String statusGUIA) {
		this.statusGUIA = statusGUIA;
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

    public List<String> getGroupsLDAPList() {
        try {
            return Arrays.asList(groupsLDAP.split(","));
        } catch (Exception e) {
            return new ArrayList<String>();
        }
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

    public void setNumColegiado(String numColegiado) {
        this.numColegiado = numColegiado;
    }

    public String getNumColegiado() {
        return numColegiado;
    }

    public void setCias(String cias) {
        this.cias = cias;
    }

    public String getCias() {
        return cias;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecName() {
        return secName;
    }

    public void setCenId(String cenId) {
        this.cenId = cenId;
    }

    public String getCenId() {
        return cenId;
    }

    public void setCenName(String cenName) {
        this.cenName = cenName;
    }

    public String getCenName() {
        return cenName;
    }

    public void setGfhId(String gfhId) {
        this.gfhId = gfhId;
    }

    public String getGfhId() {
        return gfhId;
    }

    public void setGfhName(String gfhName) {
        this.gfhName = gfhName;
    }

    public String getGfhName() {
        return gfhName;
    }

    public void setCatrId(String catrId) {
        this.catrId = catrId;
    }

    public String getCatrId() {
        return catrId;
    }

    public void setCatrName(String catrName) {
        this.catrName = catrName;
    }

    public String getCatrName() {
        return catrName;
    }

    /**
     * Comprueba que el usuario esta activo en el AD.
     * @return true si el usuario esta activo en el AD, false si no.
     */
    public boolean isValidUser() {
        return getStatusUser() != null && getStatusUser().equals("A");
    }
}
