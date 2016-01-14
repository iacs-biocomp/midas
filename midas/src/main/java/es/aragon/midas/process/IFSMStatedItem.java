/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.process;

/**
 *
 * @author Carlos
 */
public interface IFSMStatedItem {
    public String getState();
    
    public void setState(String state);
}
