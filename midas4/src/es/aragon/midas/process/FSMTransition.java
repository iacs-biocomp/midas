/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.process;

/**
 *
 * @author Carlos
 */
public class FSMTransition {
    private String fromState;
    private String event;
    private String condition;
    private String toState;
    private String grant;

    
    /**
     * 
     */
    public FSMTransition() {
        this.fromState = "";
        this.event = "";
        this.toState = "";
        this.condition = "true";
        this.grant="PUBLIC";        
    }
    
    /**
     * 
     * @param _fromState
     * @param _event
     * @param _toState 
     */
    public FSMTransition(String _fromState, String _event, String _toState) {
        this.fromState = _fromState;
        this.event = _event;
        this.toState = _toState;
        this.condition = "true";
        this.grant="PUBLIC";
    }
    
    /**
     * 
     * @param _fromState
     * @param _event
     * @param _toState
     * @param _condition 
     */
    public FSMTransition(String _fromState, String _event, String _toState, String _condition) {
        this.fromState = _fromState;
        this.event = _event;
        this.toState = _toState;
        this.condition = _condition;
        this.grant="PUBLIC";
    }
    
    /**
     * 
     * @param _fromState
     * @param _event
     * @param _toState
     * @param _condition
     * @param grant 
     */
    public FSMTransition(String _fromState, String _event, String _toState, String _condition, String grant) {
        this.fromState = _fromState;
        this.event = _event;
        this.toState = _toState;
        this.condition = _condition;
        this.grant=grant;
    }    
    
    
    /**
     * @return the fromState
     */
    public String getFromState() {
        return fromState;
    }

    /**
     * @param fromState the fromState to set
     */
    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    /**
     * @return the event
     */
    public String getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return the toState
     */
    public String getToState() {
        return toState;
    }

    /**
     * @param toState the toState to set
     */
    public void setToState(String toState) {
        this.toState = toState;
    }

    /**
     * @return the grant
     */
    protected String getGrant() {
        return grant;
    }

    /**
     * @param grant the grant to set
     */
    protected void setGrant(String grant) {
        this.grant = grant;
    }
    
    
}
