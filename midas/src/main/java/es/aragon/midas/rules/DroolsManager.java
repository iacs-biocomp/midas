package es.aragon.midas.rules;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import es.aragon.midas.util.StringUtils;

/**
 * Clase que gestiona la implementacion de drools
 */
public class DroolsManager {

	private StatefulKnowledgeSession ksession;
	private DroolsEventListener escuchadorEventos;

	/** Map estático para almacenar las reglas que utiliza la aplicación */
	private static Map<String, KnowledgeBase> rulesFiles;

	private Logger objLog;
	private String operador;
	private String nombreFichero;

	static {
		loadRules();
	}

	/**
	 * Recarga los ficheros de reglas estáticos
	 */
	public static void loadRules() {
		Logger objLog = new Logger();
		objLog.info("Cargando las reglas de Drools en el Map estático");
		if (rulesFiles == null) {
			objLog.debug("Inicia el map de reglas");
			rulesFiles = new HashMap<String, KnowledgeBase>();
		}

		String path = AppProperties.getParameter("midas.rules.path");
		// Si no hay ruta especificada no continúa
		if (StringUtils.nb(path)) {
			objLog.info("NO HAY RUTA DROOLS DEFINIDA - No carga ningún fichero de reglas");
			return;
		}
		File directorio = new File(path);
		for (File rulesFile : directorio.listFiles()) {
			// Carga las reglas si es un fichero de reglas
			if (rulesFile.getName().toLowerCase().endsWith(".drl")) {
				objLog.debug("Generando el Objeto de Drools del fichero " + rulesFile.getName());
				KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
				kbuilder.add(ResourceFactory.newFileResource(rulesFile), ResourceType.DRL);

				// Comprueba que sea un fichero drl valido
				KnowledgeBuilderErrors errors = kbuilder.getErrors();
				if (errors.size() > 0) {
					for (KnowledgeBuilderError error : errors) {
						objLog.error(error.getMessage());
					}
					throw new IllegalArgumentException("Error en el fichero de reglas " + rulesFile.getName());
				}

				KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
				kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

				// Añade las reglas al map
				rulesFiles.put(rulesFile.getName(), kbase);

				objLog.debug("READY - Objeto de Drools del fichero " + rulesFile.getName());
			}

		}

		objLog.info("Reglas de Drools cargadas en el Map estático");
	}

	/**
	 * Constructor del Objeto
	 * 
	 * @param operador
	 *            Operador que realiza la accion
	 * @param ficheroReglas
	 *            nombre del fichero drl que se quiere ejecutar
	 * @throws Exception
	 */
	public DroolsManager(String operador, String ficheroReglas) {
		this.objLog = new Logger();
		this.objLog.setUser(operador);
		this.operador = operador;
		this.nombreFichero = ficheroReglas;
		objLog.debug("Generando el Objeto de Drools del fichero " + nombreFichero);

		// Crea una sesion con las reglas estáticas
		KnowledgeBase kbase = rulesFiles.get(ficheroReglas);
		ksession = kbase.newStatefulKnowledgeSession();

		// Necesita un listener inicial
		escuchadorEventos = new DroolsEventListener(operador);
		ksession.addEventListener(escuchadorEventos);

		objLog.debug("Saliendo del constructor de Drools del fichero " + nombreFichero);
	}

	/**
	 * Dispara las reglas del fichero drl correspondiente del objeto
	 * 
	 * @param obj
	 *            Objeto que pasara por las reglas
	 */
	public void fireRules(Object obj) {
		List<Object> objects = new ArrayList<Object>();
		objects.add(obj);
		fireRules(objects);

	}

	/**
	 * Dispara las reglas del fichero drl correspondiente del objeto
	 * 
	 * @param objects
	 *            Lista de objetos para enviar a las reglas
	 */
	public void fireRules(List<Object> objects) {
		objLog.debug("Lanzando las reglas del fichero " + nombreFichero + " de Drools");
		// Elimina el anterior escuchador de eventos del objeto
		ksession.removeEventListener(escuchadorEventos);

		// Crea un nuevo escuchador de eventos para el objeto
		escuchadorEventos = new DroolsEventListener(operador);
		ksession.addEventListener(escuchadorEventos);

		// Activa las reglas del drl
		for (Object obj : objects) {
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
	 * 
	 * @param identificador
	 *            String que indique a que objeto se esta haciendo referencia
	 */
	public void info(String identificador) {
		escuchadorEventos.info(identificador);
	}

	/**
	 * Escribe en el log un mensaje de debug con las reglas activadas
	 * 
	 * @param identificador
	 *            String que indique a que objeto se esta haciendo referencia
	 */
	public void debug(String identificador) {
		escuchadorEventos.debug(identificador);
	}

}
