package es.aragon.midas.ldap;

public class FiltroLdap {
	
	private String mail;
	private String username;
	
	public FiltroLdap(String mail, String username){
		this.mail = mail;
		this.username = username;
	}
	
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
