package es.aragon.midas.dao;

import es.aragon.midas.config.MidGrant;
import es.aragon.midas.config.MidMenu;
import es.aragon.midas.logging.Logger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MenuDAO {
    
    //private EntityManager em = ConnectionFactory.getMidasEMF().createEntityManager();    
    @PersistenceContext(unitName="midas4")
    private EntityManager midasEntityManager;
    
    	static Logger log = new Logger();        

	public List<MidMenu> find () {
		Query query = midasEntityManager.createNamedQuery("MidMenu.findOrdered");
		@SuppressWarnings("unchecked")
		List<MidMenu> menu = query.getResultList(); 
		return menu;
	}
	
	
	/**
	 * Convierte una lista de MidMenus en un arbol de menus anidados
	 * @param list
	 * @return
	 */
	public MidMenu getMenuTree(List<MidMenu> list) {
		short zero = 0;
        Short padre = new Short(zero);
		MidMenu menu = new MidMenu(padre, 
                                            new MidGrant("PUBLIC"),
                                            padre,
                                            "",
                                            "#",
                                            "", 
                                            "");
		
		for (MidMenu mn : list) {
			// busca el menu cuyo id es el padre del actual
			short pd = mn.getMnPadre().shortValue();
			MidMenu buscado = findMenu(menu, pd);
			// inserta el menu mn en el padre encontrado
			mn.setParent(buscado);
			buscado.addChild(mn);
		}
		return menu;
	}
	
	/**
	 * Devuelve el menu en forma de arbol de menus y submenus anidados
	 * @return
	 */
	public MidMenu getMenuTree() {
		return getMenuTree(find());
	}
	
	
	/**
	 * Localiza recursivamente el padre de un menu a partir del ID del padre (pd)
	 * @param root
	 * @param pd
	 * @return
	 */
	private MidMenu findMenu(MidMenu root, short pd) {
		if (root.getMnId() == pd) {
                return root;
            }
		else {
			for (MidMenu mn : root.getChildren()) {
				MidMenu result = findMenu(mn, pd); 
				if (result != null) {
                                return result;
                            } 
			}
			return null;
		}
	}
	
}
