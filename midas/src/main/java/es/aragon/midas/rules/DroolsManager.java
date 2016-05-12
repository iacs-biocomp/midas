package es.aragon.midas.rules;


import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.logging.Logger;

/**
 * Clase que gestiona la implementacion de drools
 */
public class DroolsManager {

    private StatefulKnowledgeSession ksession;
    private DroolsEventListener escuchadorEventos;

    private Logger objLog;
    private String operador;
    private String nombreFichero;


    /**
     * Constructor del Objeto
     * @param operador Operador que realiza la accion
     * @param ficheroReglas nombre del fichero drl que se quiere ejecutar
     * @throws Exception
     */
    public DroolsManager(String operador, String ficheroReglas) {
        this.objLog = new Logger();
        this.objLog.setUser(operador);
        this.operador = operador;
        this.nombreFichero = ficheroReglas;
        objLog.debug("Generando el Objeto de Drools del fichero " + nombreFichero);
        //Carga el fichero drl
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(
				ResourceFactory.newFileResource(AppProperties
						.getParameter("midas.rules.path") + 
                    ficheroReglas), ResourceType.DRL);

        //Comprueba que sea un fichero drl valido 
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                objLog.error(error.getMessage());
            }
            throw new IllegalArgumentException("Error en el fichero de reglas " + nombreFichero);
        }

        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        ksession = kbase.newStatefulKnowledgeSession();

        //Se crea un listener inicial para evitar errores al llamar a los metodos de log
        escuchadorEventos = new DroolsEventListener(operador);
        ksession.addEventListener(escuchadorEventos);

        objLog.debug("Saliendo del constructor de Drools del fichero " + nombreFichero);
    }

    /**
     * Dispara las reglas del fichero drl correspondiente del objeto 
     * @param obj Objeto que pasara por las reglas
     */
    public void fireRules(Object obj) {
        List<Object> objects = new ArrayList<Object>();
        objects.add(obj);
        fireRules(objects);

    }

    /**
     * Dispara las reglas del fichero drl correspondiente del objeto 
     * @param objects Lista de objetos para enviar a las reglas
     */
    public void fireRules(List<Object> objects) {
        objLog.debug("Lanzando las reglas del fichero " + nombreFichero + " de Drools");
        //Elimina el anterior escuchador de eventos del objeto
        ksession.removeEventListener(escuchadorEventos);

        //Crea un nuevo escuchador de eventos para el objeto
        escuchadorEventos = new DroolsEventListener(operador);
        ksession.addEventListener(escuchadorEventos);


        //Activa las reglas del drl
        for (Object obj: objects) {
            ksession.insert(obj);
        }
        ksession.fireAllRules();

        objLog.debug("Reglas del fichero " + nombreFichero + " de Drools lanzadas");
    }

    /**
     * Cierra el objeto
     */
    public void close() {
        objLog.debug("Cerrando objeto Drools del fichero " + nombreFichero);
        ksession.dispose();
        objLog.debug("Drools cerrado del fichero " + nombreFichero);
    }

    /**
     * Escribe en el log un mensaje de info con las reglas activadas
     * @param identificador String que indique a que objeto se esta haciendo referencia
     */
    public void info(String identificador) {
        escuchadorEventos.info(identificador);
    }

    /**
     * Escribe en el log un mensaje de debug con las reglas activadas
     * @param identificador String que indique a que objeto se esta haciendo referencia
     */
    public void debug(String identificador) {
        escuchadorEventos.debug(identificador);
    }

}
