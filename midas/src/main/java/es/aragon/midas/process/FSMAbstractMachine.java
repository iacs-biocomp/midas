/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.process;

import es.aragon.midas.config.MidUser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author carlos
 */
public abstract class FSMAbstractMachine {
    
    protected HashMap<String, FSMState> states;
    protected HashMap<String, FSMEvent> events;
    protected ArrayList<FSMTransition> transitions;
    protected String initState;
    
    /**
     * 
     */
    public FSMAbstractMachine() {
        this.transitions = new ArrayList<FSMTransition>();
        this.events = new HashMap<String, FSMEvent>();
        this.states = new HashMap<String, FSMState>();
    }


    /**
     * 
     * @param item
     * @param event
     * @return 
     */
    public boolean isAllowed(IFSMStatedItem item, String event) {
        return isAllowed(item, event, null);
    }

    
   /**
    * 
    * @param item
    * @param event
    * @param user
    * @return 
    */
    public boolean isAllowed(IFSMStatedItem item, String event, MidUser user) {
        boolean allowed = false;
        String fromState = item.getState();

        for (FSMTransition tr : transitions) {
            if (tr.getFromState().equals(fromState)
                     && tr.getEvent().equals(event)) {
                boolean condition;
                try {
                    condition = tr.getCondition().equals("true") ||
                            tr.getCondition().isEmpty() ||
                            BeanUtils.getProperty(item, tr.getCondition()).equals("true");
                    condition = condition && (user == null || user.isGranted(tr.getGrant()));
                } catch (Exception e) {
                    condition = false;
                } 
                if (condition) {
                    allowed = true;
                    break;
                }
            }
        }
        return allowed;
    }    
    


    /**
     * 
     * @param item
     * @param event
     * @return 
     */
    public String newState(IFSMStatedItem item, String event) {
        return newState(item, event, null);
    }          
    
    /**
     * 
     * @param item
     * @param event
     * @param user
     * @return 
     */
    public String newState(IFSMStatedItem item, String event, MidUser user) {
        String newState = item.getState();
        String fromState = item.getState();
        for (FSMTransition tr : transitions) {
            if (tr.getFromState().equals(fromState)
                     && tr.getEvent().equals(event)) {
                boolean condition;
                try {
                    condition = tr.getCondition().equals("true") ||
                            tr.getCondition().isEmpty() ||
                            BeanUtils.getProperty(item, tr.getCondition()).equals("true");
                    condition = condition && (user == null || user.isGranted(tr.getGrant()));
                } catch (Exception ex) {
                    condition = false;
                }
                if (condition) {
                    newState = tr.getToState();
                    break;
                }
            }
        }
        return newState;
    }          
    
    
    /**
     * 
     * @param item
     * @param event
     * @return 
     */
    public String doEvent(IFSMStatedItem item, String event) {
        if (isAllowed(item, event, null)) {
            String ns = newState(item, event, null);
            item.setState(ns);
            return ns;
        } else {
            return null;
        }
    }

    /**
     * 
     * @param item
     * @param event
     * @param user
     * @return 
     */
    public String doEvent(IFSMStatedItem item, String event, MidUser user) {
        if (isAllowed(item, event, user)) {
            String ns = newState(item, event, user);
            item.setState(ns);
            return ns;
        } else {
            return null;
        }
    }    
    
    /**
     * 
     * @param item
     * @param user
     * @return 
     */
    public List<FSMEvent> getAllowedEvents(IFSMStatedItem item, MidUser user){
        ArrayList<FSMEvent> allowedEvents = new ArrayList<FSMEvent>();
        String fromState = item.getState();
        for (FSMTransition tr : transitions) {
            if (tr.getFromState().equals(fromState)) {
                boolean condition;
                try {
                    condition = tr.getCondition().equals("true") ||
                            tr.getCondition().isEmpty() ||
                            BeanUtils.getProperty(item, tr.getCondition()).equals("true");
                    condition = condition && (user == null || user.isGranted(tr.getGrant()));                    
                } catch (Exception ex) {
                    condition = false;
                }
                if (condition) {
                    allowedEvents.add(events.get(tr.getEvent()));
                }
            } else {
            }
        }
        return allowedEvents;
    }    

    
    /**
     * 
     * @param item
     * @return 
     */
    public List<FSMEvent> getAllowedEvents(IFSMStatedItem item){
        return getAllowedEvents(item, null);
    }
    
    
    
    /**
     * @return the states
     */
    protected HashMap<String, FSMState> getStates() {
        return states;
    }

    /**
     * @return the events
     */
    protected HashMap<String, FSMEvent> getEvents() {
        return events;
    }

    /**
     * @return the transitions
     */
    protected ArrayList<FSMTransition> getTransitions() {
        return transitions;
    }

    /**
     * @return the initState
     */
    public String getInitState() {
        return initState;
    }

    /**
     * @param initState the initState to set
     */
    public void setInitState(String initState) {
        this.initState = initState;
    }
    
}
