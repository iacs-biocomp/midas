package es.aragon.midas.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchResult;

public class UserLdap {
	private final Pattern p = Pattern.compile("^CN=([a-zA-Z-]+).*");

	private String login;
	private String mail;
	private String nif;
	private String name;
	private String surnames;
    private List<String> groupsLDAP;
    
    
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
