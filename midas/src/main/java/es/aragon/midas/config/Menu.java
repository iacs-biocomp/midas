package es.aragon.midas.config;

import es.aragon.midas.dao.MenuDAO;
import es.aragon.midas.logging.Logger;

import javax.naming.InitialContext;

public class Menu {

	private static MidMenu mainmenu;

	public static MidMenu getMainMenu() {

		if (mainmenu == null) {
			getDBMenu();
		}
		return mainmenu;
	}

	public static MidMenu getCustomizedMenu(MidUser user) {
		return getMainMenu().cloneWithGrants(user.getGrants());
	}

	/**
	 * Carga los datos de la estructura del menu desde la base de datos
	 */
	private static synchronized void getDBMenu() {
		Logger log = new Logger();
		log.debug("Cargando el menu en de la aplicacion desde base de datos");
		try {
			MenuDAO dao = (MenuDAO) new InitialContext()
					.lookup("java:module/MenuDAO");
			mainmenu = dao.getMenuTree(dao.find());
			log.info("Menu cargado correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error leyendo menu de BD. " + e.getMessage());
			log.error("Error al cargar los datos del menu desde base de datos",
					e);
		}
	}

	/**
	 * Recarga el menu de la aplicacion
	 */
	public static void reload() {
		getDBMenu();
	}
}
