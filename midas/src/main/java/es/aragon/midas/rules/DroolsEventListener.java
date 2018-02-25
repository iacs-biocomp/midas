package es.aragon.midas.rules;


import java.util.ArrayList;
import java.util.List;

import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.event.rule.AfterActivationFiredEvent;

import es.aragon.midas.logging.Logger;

/**
 * Clase encargada de capturar las reglas activadas y pintar el log
 */
public class DroolsEventListener extends DefaultAgendaEventListener {

    private List<AfterActivationFiredEvent> eventosActivados;
    private Logger log;

    /**
     * Constructor del Listener
     * @param operador operador que esta realizando la accion
     */
    public DroolsEventListener(String operador) {
        eventosActivados = new ArrayList<AfterActivationFiredEvent>();
        this.log = new Logger();
        this.log.setUser(operador);
    }

    /**
     * Captura las reglas que se activan
     * @param event
     */
    @Override
    public void afterActivationFired(AfterActivationFiredEvent event) {
        //Añade al List cada regla que se activa	
        eventosActivados.add(event);
    }


    /**
     * Escribe en el log un mensaje de info con las reglas activadas
     * @param identificador String que indique a que objeto se esta haciendo referencia
     */
    public void info(String identificador) {
        for (AfterActivationFiredEvent evento: eventosActivados) {
            log.info(identificador + " | " + "Regla activada " + evento.getActivation().getRule().getName());
        }
    }

    /**
     * Escribe en el log un mensaje de debug con las reglas activadas
     * @param identificador String que indique a que objeto se esta haciendo referencia
     */
    public void debug(String identificador) {
        for (AfterActivationFiredEvent evento: eventosActivados) {
            log.debug(identificador + " | " + "Regla activada " + evento.getActivation().getRule().getName());
        }
    }
}
