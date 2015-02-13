/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.process;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.process.scxml.scxml2;
import es.aragon.midas.process.scxml.scxml_datamodel_type.MemberElement_data;
import es.aragon.midas.process.scxml.scxml_final_type;
import es.aragon.midas.process.scxml.scxml_scxml_type;
import es.aragon.midas.process.scxml.scxml_state_type;
import es.aragon.midas.process.scxml.scxml_transition_type;
import es.aragon.midas.process.scxml.scxml_transition_type.MemberElement_assign;
import java.util.HashMap;

/**
 *
 * @author Carlos
 */
public class SCXMLMachine extends FSMAbstractMachine {

    private static HashMap<String, SCXMLMachine> machines = new HashMap<String, SCXMLMachine>();
    static Logger log = new Logger();
    
    /**
     * 
     * @param scFile
     * @return 
     */
    public static SCXMLMachine getMachine(String scFile) {
        if (machines.containsKey(scFile)) {
            return machines.get(scFile);
        }
        else {
            String path = AppProperties.getParameter("midas.fsmPath") + scFile;
            SCXMLMachine mac = new SCXMLMachine(path);
            machines.put(scFile, mac);
            return mac;
        }
    }
    
    
    /**
     * 
     * @param scFile 
     */
    public SCXMLMachine (String scFile) {
        super();
        try {
            scxml2 doc = scxml2.loadFromFile(scFile);
            scxml_scxml_type root = doc.scxml3.first();
            initState = root.initial.getValue();
            int count = root.state.count();

            // Recorremos los estados
            for (int i = 0; i < count; ++i ) {
                scxml_state_type state = root.state.at(i);
                String code = state.id.getValue();
                String name = code;
                String color = "#000000";
                MemberElement_data data = state.datamodel.first().data;
                int ndata = data.count();
                for (int j = 0; j < ndata; ++j) {
                    try {
                        String cod = data.at(j).src.getValue();
                        String val = data.at(j).expr.getValue();
                        if ("color".equals(cod)) {
                            color = val;
                        } else if ("name".equals(cod)) {
                            name = val;
                        }
                    } catch (Exception e) {
                        log.error("Error leyendo maquina " + scFile);
                        log.error("expr o valor nulo en datamodel de estado " + code);
                    }
                }
                states.put(code, new FSMState(code, name, color));
                
                // Recorremos las transiciones
                int ntrans = state.transition.count();
                for (int k = 0; k < ntrans; ++k) {
                    scxml_transition_type transition = state.transition.at(k);
                    String event = transition.event.getValue();
                    String target = transition.target.getValue();
                    String condition;
                    if (transition.cond.exists()) {
                        condition = transition.cond.getValue();
                    } else {
                        condition = "true";
                    }
                    String grant = "PUBLIC";
                    String icon = null;
                    String eventname = event;
               
                    MemberElement_assign assign = transition.assign;
                    int nassign = transition.assign.count();
                    for (int l = 0; l < nassign; ++l ){
                        try {
                            String val = assign.at(l).expr.getValue();
                            String cod = assign.at(l).location.getValue();
                            if ("icon".equals(cod)) {
                                    icon = val;
                            } else if ("name".equals(cod)) {
                                    eventname = val;
                            } else if ("grant".equals(cod)) {
                                    grant = val;
                            }
                        } catch (Exception e) {
                            log.error("Error leyendo maquina " + scFile);
                            log.error("Assign sin expr o location en transition " + event);
                        }
                    }
                    events.put(event, new FSMEvent(event, eventname, icon));
                    transitions.add(new FSMTransition(code, event, target, condition, grant));
                }
            }
            
            // Leemos estado final

            if (root.final2.exists()) {
                scxml_final_type finalState = root.final2.at(0);
                String finalName = finalState.id.getValue();
                states.put(finalName, new FSMState(finalName, finalName, "#000000"));
            }
                
            
        } catch (Exception e) {
            log.error("Error leyendo maquina " + scFile);
            log.error("Error general leyendo máquina", e);
        }        

    }    

    /**
     * 
     */
    public void printMAchine() {
        for (String st : states.keySet()) {
            FSMState state = states.get(st);
            System.out.println(state.getCode() + " " + state.getName() + " " + state.getColor());
        }
        for (String st : events.keySet()) {
            FSMEvent event = events.get(st);
            System.out.println(event.getCode() + " " + event.getName() + " " + event.getIcon());
        }

        for (FSMTransition tr : transitions) {
            System.out.println(tr.getFromState() + " " + tr.getEvent() + " " + tr.getToState());
        }
    }
}
