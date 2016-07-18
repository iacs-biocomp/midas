package es.aragon.midas.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchResult;

/**
 * Objeto Usuario con los valores obtenidos del LDAP
 * @author Jorge Landa
 */
public class UserLdap {
	/**
	 * Patrón de busqueda de grupos del LDAP para los usuarios.
	 * 		El patron obtiene el String de cada uno de los grupos de la siguiente forma:
	 * 	Empieza por "CN= " + Crea un grupo para todo el contenido que tenga
	 * 		Letras, números y "-" hasta encontrar un separador, en este caso ","
	 * 	Despues tiene en cuenta cualquier tipo de contenido encontrado. El cual simplemente
	 * 		Descartaremos
	 *  
	 *  Obtenemos este primer grupo ya que es donde se muestra el valor cada uno de los grupos
	 *  LDAP a los que pertenece el usuario.
	 */
	private final Pattern p = Pattern.compile("^CN=([1-9a-zA-Z-]+).*");

	// Variables del usuario obtenido del LDAP
	private String login;
	private String mail;
	private String nif;
	private String name;
	private String surnames;
    private List<String> groupsLDAP;
    
    /**
     * Constructor de UserLdap
     * 		Según el resultado de la busqueda se establecen los parametros.
     * @param result
     * @throws NamingException
     */
	public UserLdap(SearchResult result) throws NamingException{
		login = ((String) result.getAttributes().get("sAMAccountName").get()).toUpperCase();
		nif = ((String) result.getAttributes().get("name").get()).toUpperCase();

		Attribute mailAtrb = result.getAttributes().get("mail");
		if(mailAtrb != null){
			mail = ((String) result.getAttributes().get("mail").get()).toUpperCase();
		}
		
		Attribute nameAtrb = result.getAttributes().get("givenName");
		if(nameAtrb != null){
			name = ((String) result.getAttributes().get("givenName").get()).toUpperCase();
		}
		
		Attribute surnamesAtrb = result.getAttributes().get("sn");
		if(surnamesAtrb != null){
			surnames = ((String) result.getAttributes().get("sn").get()).toUpperCase();
		}
		
		/**
		 * Recorre todos los atributos memberOf encontrados en el LDAP
		 * 		y establece los valores de los grupos según el filtro definido al principio.
		 * 	Se obtiene así los grupos LDAP a los que pertenece el usuario y se guardan en un List.
		 * 
		 * 	Estos grupos serán los que nos permitan obtener los grants que tendrá el usuario
		 * 		según sus roles de LDAP.
		 */
		groupsLDAP = new ArrayList<String>();
		Attribute memberOf = result.getAttributes().get("memberOf");
		if(memberOf != null){
			for(int i = 0; i < memberOf.size(); i++){
				Matcher m = p.matcher(memberOf.get(i).toString());
				if(m.find()){
					groupsLDAP.add(m.group(1));
				}
			}
		}
    }

	// <---- Getters and Setters
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public List<String> getGroupsLDAP() {
		return groupsLDAP;
	}

	public void setGroupsLDAP(List<String> groupsLDAP) {
		this.groupsLDAP = groupsLDAP;
	}
}
