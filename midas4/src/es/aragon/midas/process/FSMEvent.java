/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.process;

/**
 *
 * @author Carlos
 */
public class FSMEvent {
    private String code;
    private String name;
    private String icon;

    
    public FSMEvent() {
    }
    
    public FSMEvent(String _code, String _name){
        code = _code;
        name = _name;
    }
    
    public FSMEvent(String _code, String _name, String _icon){
        code = _code;
        name = _name;
        icon = _icon;
    }

    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
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
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    
}
