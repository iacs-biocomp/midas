package es.aragon.midas.util;
import java.util.Vector;

/**
 * Encapsula un men� de aplicaci�n completo
 */
public class Menu {

  private int pointer0;
  private int pointer1;
  private Vector<MenuItem> level1;
  private Vector<MenuItem> level0;
  private int subMenu;
  private boolean populated;
  private boolean sessionValid;
  private int totalLevel0;

/**
 * Constructor
 */
  public Menu() {
    super();
    level0 = new Vector<MenuItem>();
    level1 = new Vector<MenuItem>();
    populated = false;
    totalLevel0 = 0;
    reset();
  }


/**
 * Inicializa los punteros para la lectura de opciones de men�.
 */
  public void reset() {
    subMenu = 0;
    pointer0 = -1;
    pointer1 = -1;
  }
  
  /***************************************************/


  /**
   * A�ade un item de men� al men�.
   * @param id  Identificador �nico de la opci�n
   * @param padre N�mero del submen� al que pertenece la opci�n
   * @param texto Texto que debe aparecer en el men�
   * @param link  Link que debe ejecutarse al pulsar sobre el men�
   * @param target Ventana target de la opci�n seleccionada
   */
  public void addItem(int id, 
                         int padre, 
                         String texto, 
                         String link,
                         String target)
  {
    if (padre == 0) {
      level0.add(new MenuItem(id, padre, texto, link, target));
      ++totalLevel0;
    } else {
      level1.add(new MenuItem(id, padre, texto, link, target));
    }
  }

  /**
   * Devuelve el texto del enlace de la siguiente opci�n del men�.
   */
  public String getLink() {
    return ((MenuItem)level1.elementAt(pointer1)).getLink();
  }

  /**
   * Devuelve el texto que debe aparecer en la siguiente opci�n de men�.
   */
  public String getTexto() {
    return ((MenuItem)level1.elementAt(pointer1)).getTexto();
  }


  /**
   * Devuelve el id de la opcion indicada
   */
  public int getOptionId() {
    return ((MenuItem)level1.elementAt(pointer1)).getId();  
  }
  
  /**
   * Devuelve el texto del men� de nivel 0 actual.
   */
  public String getMenuTitle() {
    return ((MenuItem)level0.elementAt(pointer0)).getTexto();
  }

  /**
   * Devuelve el target de la opci�n del menu.
   */
   public String getTarget() {
     return ((MenuItem)level1.elementAt(pointer1)).getTarget();     
   }

  /**
   * Devuelve el id �nico de la opci�n de men� principal actual. Este id
   * corresponder� al campo padre de todas las opciones asociadas 
   * a este submen�.
   */
  public int getMenuTitleId() {
    return ((MenuItem)level0.elementAt(pointer0)).getId();  
  }
  
  /**
   * Devuelve el total de entradas en el men� principal (nivel 0).
   */
  public int getTotalLevel0() {
    return totalLevel0;
  }
  
  /**
   * Determina si quedan entradas en el men� principal por leer, y en caso
   * afirmativo, posiciona el cursor de lectura en la siguiente entrada
   * de men�.
   * @return true en caso afirmativo, o null en caso contrario
   */
  public boolean nextMenu() {
    if (!populated)
      return false;
    ++pointer0;
    if (pointer0 < level0.size() ) {
      return true;
    } else {
      return false;
    }
  }
  
  
  /**
   * Establece el n�mero de submen� que se desea recorrer.
   */
  public void setSubMenu(int mn) {
    if (!populated)
      return;
      
    subMenu = mn;
    pointer1 = 0;

    while (pointer1 < level1.size() && 
      ((MenuItem)level1.elementAt(pointer1)).getPadre() != subMenu) {
      ++ pointer1;
    }
    
    if (pointer1 == level1.size()) {
      subMenu = 0;
      pointer1 = 0;
    }
    --pointer1;
  }


  /**
   * Devuelve el n�mero de submen� activo.
   */
  public int getMenuNumber() {
    return subMenu;
  }

  /**
   * Determina si hay m�s opciones de men� disponibles en el submen� escogido.
   * En caso afirmativo, posiciona el puntero sobre la 
   * siguiente opcion, de modo que la siguiente llamanda a getTexto y getLink
   * devuelvan los valores correspondientes.
   * @return true si quedan opciones por leer, y false en caso contrario.
   */
  public boolean next() {
    if (!populated)
      return false;
    ++pointer1;
    if (pointer1 < level1.size() &&
        ((MenuItem)level1.elementAt(pointer1)).getPadre() == subMenu) {
      return true;
    } else {
      setSubMenu(subMenu);
      return false;
    }
  }

  /**
   * Establece si el menu est� poblado de items o no.
   */
  public void setPopulated(boolean p) {
    populated = p;
    if (!populated) {
      level1.clear();
      level0.clear();
      subMenu = 0;
      pointer0 = 0;
      pointer1 = 0;
    }
  }

  /**
   * Determina si el men� est� poblado o no.
   */
  public boolean getPopulated() {
    return populated;
  }

  /**
   * Establece si la sesi�n es v�lida o no.
   */
  public void setSessionValid(boolean s) {
    sessionValid = s;
  }

  /**
   * Determina si la sesi�n es v�lida o no.
   */
  public String getSessionValid() {
    if (sessionValid)
      return "true";
    else
      return "false";
  }

/**
 * La clase MenuItem encapsula una opci�n de men� de aplicaci�n.
 */


private class MenuItem 
{
  private String link;
  private String texto;
  private int id;
  private int padre;
  private String target;
  
  /**
   * Constructor
   * @param id identificador de menu
   * @param padre id del menu padre del actual
   * @param texto texto del menu
   * @param link URL de destino del enlace
   * @param acceso Rol de acceso al menu
   */
  public MenuItem(int id, int padre, String texto, String link, String target) {
    this.id = id;
    this.padre = padre;
    this.link = link;
    this.texto = texto;
    this.target = target;
  }

  /**
   * Devuelve la url asociada al elemento del menu
   */
  String getLink()
  {
    return link;
  }


  /**
   * Devuelve el texto asociado al elemento del menu
   */
  String getTexto()
  {
    return texto;
  }


  /**
   * Devuelve el id de menu de esta opci�n.
   */
  int getId()
  {
    return id;
  }


  /**
   * Devuelve el id del menu padre
   */
   int getPadre() {
     return padre;
   }

  /**
   * Devuelve el target de la opcion
   */
   String getTarget() {
     if (target == null)
      return "_self";
     else
      return target;
   }

}


}