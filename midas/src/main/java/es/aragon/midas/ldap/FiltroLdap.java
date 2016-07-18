package es.aragon.midas.ldap;

/**
 * Filtrado para el LDAP
 * @author Jorge Landa
 */
public class FiltroLdap {
	
	// Variables de Filtrado
	private String mail;
	private String username;
	
	/**
	 * Constructor del filto.
	 * 		Obtiene los valores de filtrado posibles para ser captados por el LDAP
	 * @param mail
	 * @param username
	 */
	public FiltroLdap(String mail, String username){
		this.mail = mail;
		this.username = username;
	}
	
	/**
	 * Devuelve los filtros para ser utilizados en el LDAP.
	 * 		Comprueba los valores de ambas variables y establece los valores
	 * 		para poder ser filtrados en el LDAP
	 * @return
	 */
	
	public String getFilters(){
		String filters = "(";
		
		if(mail != null && !mail.isEmpty()){
			filters = filters + "mail=" + mail;
			if(username != null && !username.isEmpty()){
				filters = filters + ", sAMAccountName=" + username;
			}
		}else{
			if(username != null && !username.isEmpty()){
				filters = filters + "sAMAccountName=" + username;
			}
		}
		
		filters = filters + ")";
		return filters;
	}
	
	/** <--- Getters and Setters de los valores de los filtros */
	
	public void setMail(String mail){
		this.mail = mail;
	}
	
	public String getMail(){
		return mail;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
}
