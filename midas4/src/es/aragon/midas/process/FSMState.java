/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.process;

/**
 *
 * @author Carlos
 */
public class FSMState {
    private String code;
    private String name;
    private String color;

    
    public FSMState() {
    }
    
    public FSMState(String _code, String _name) {
        code = _code;
        name = _name;
    }
    
    public FSMState(String _code, String _name, String _color) {
        code = _code;
        name = _name;
        color = _color;
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
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
    
    
    
    
}
