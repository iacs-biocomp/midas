package es.aragon.midas.config;

import es.aragon.midas.dao.MenuDAO;
import javax.naming.InitialContext;

public class Menu {

	private static MidMenu mainmenu;
	
	public static MidMenu getMainMenu(){

        if (mainmenu == null) {
            try{
                MenuDAO dao = (MenuDAO) new InitialContext().lookup("java:module/MenuDAO");
                mainmenu = dao.getMenuTree(dao.find());
            }catch (Exception e) {
            	e.printStackTrace();
            	System.out.println("Error leyendo menu de BD. " + e.getMessage());
            }
        }
		return mainmenu;
	}
	
	public static MidMenu getCustomizedMenu(MidUser user){
		return getMainMenu().cloneWithGrants(user.getGrants());
	}
	
	
}
