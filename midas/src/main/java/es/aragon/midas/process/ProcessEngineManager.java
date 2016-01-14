/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.process;

/**
 *
 * @author carlos
 */
public class ProcessEngineManager {
    private static Motor engine;

    public Motor getEngine() {
        return engine;
    }

    public void setEngine(Motor engine) {
        ProcessEngineManager.engine = engine;
    }
    
    public static void startEngine() {
        System.out.println("Arrancado Motor de Procesos ACTIVITI");
        ProcessEngineManager.engine = new Motor();
    }
}
