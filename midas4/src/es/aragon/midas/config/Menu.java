package es.aragon.midas.config;

import es.aragon.midas.dao.MenuDAO;
import javax.naming.InitialContext;

public class Menu {

	private static MidMenu mainmenu;
	
	public static MidMenu getMainMenu(){

                if (mainmenu == null) {
                    try{
                        MenuDAO dao = (MenuDAO) new InitialContext().lookup("java:module/MenuDAO");
                        //MenuDAO dao = new MenuDAO();
			mainmenu = dao.getMenuTree(dao.find());
                    }catch (Exception e) {

                    }
                }
		return mainmenu;
	}
	
	public static MidMenu getCustomizedMenu(MidUser user){
		return getMainMenu().cloneWithGrants(user.getGrants());
	}
	
	
}
