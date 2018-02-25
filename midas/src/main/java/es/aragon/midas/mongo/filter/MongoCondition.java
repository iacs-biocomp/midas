package es.aragon.midas.mongo.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import es.aragon.midas.exception.MidasException;
import es.aragon.midas.logging.Logger;

/**
 * Clase que contiene una condicion para aplicar en los filtros de mongo. Hay
 * dos tipos de condiciones:</br> 
 * <b>- Condicion simple:</b> Aplica una condicion a un campo de los JSON. Habra que indicar a que campo se le quiere
 * aplicar la condicion, la operacion de comprobacion que se quiera aplicar y el
 * valor con el que se compararo.</br> 
 * <b>- Condicion OR:</b> Aplica varias condiciones de las cuales se debera cumplir por lo menos una. 
 * </p> Las condiciones se generaran a partir de los constructores de esta clase.
 * 
 * @author ARTURO
 * @since 4.3.0
 * 
 */
public class MongoCondition {

	private Logger log;

	// Campos para evaluar si es una condicion simple o es un OR
	private final int AND_CONDITION = 0;
	private final int OR_CONDITION = 1;
	private int condicion;

	// Operaciones que se pueden aplicar en una condicion simple
	/**
	 * Campo <b>=</b> valor
	 */
	public static final int EQUALS = 0;

	/**
	 * Campo <b>></b> valor
	 */
	public static final int GREATER_THAN = 1;

	/**
	 * Campo <b>>=</b> valor
	 */
	public static final int GREATER_EQUAL_THAN = 2;

	/**
	 * Campo <b><</b> valor
	 */
	public static final int LESS_THAN = 3;

	/**
	 * Campo <b><=</b> valor
	 */
	public static final int LESS_EQUAL_THAN = 4;

	/**
	 * Campo <b>!=</b> valor
	 */
	public static final int NOT_EQUALS = 5;

	/**
	 * <b>NOTA: </b>El valor tendra que ser una instancia de ArrayList</br>
	 * Campo <b>in</b> (valor[0], valor[1]...)
	 */
	public static final int IN = 6;

	/**
	 * <b>NOTA: </b>El valor tendra que ser una instancia de ArrayList</br>
	 * Campo <b>not in</b> (valor[0], valor[1]...)
	 */
	public static final int NOT_IN = 7;

	/**
	 * Comprueba que el campo exista en el documento JSON. No hace falta
	 * introducir nada en valor
	 */
	public static final int EXISTS = 8;

	/**
	 * Comprueba que el campo <b>NO</b> exista en el documento JSON. No hace
	 * falta introducir nada en valor
	 */
	public static final int NOT_EXISTS = 9;

	/**
	 * Comprueba que el valor del campo tenga un formato en concreto.</br>
	 * <b>NOTA: </b>El valor tendra que ser un ArrayList de String</br>
	 * String[0]: Formato del REGEX</br> String[1]:
	 * Opciones de REGEX</br> 
	 * <a
	 * href="https://docs.mongodb.org/manual/reference/operator/query/regex/"
	 * >Ver como construir formatos regex</a>
	 */
	public static final int REGEX = 10;

	// Campos con los que se genera una condicion simple
	private String campo;
	private int operacion;
	private Object valor;

	// Lista para generar una condicion compleja con OR
	private List<MongoCondition> orConditions;

	/**
	 * Genera una condicion simple
	 * 
	 * @param campo
	 *            Direccion del campo del JSON
	 * @param operacion
	 *            Operador que se aplica a la condicion
	 * @param valor
	 *            Contenido que compara con el campo del JSON
	 */
	public MongoCondition(String campo, int operacion, Object valor) {
		log = new Logger();
		this.condicion = AND_CONDITION;
		this.campo = campo;
		this.operacion = operacion;
		this.valor = valor;
	}

	/**
	 * Indica al filtro que se deben cumplir alguna de las siguientes
	 * condiciones
	 * 
	 * @param condiciones
	 *            Condiciones que contendra el OR
	 */
	public MongoCondition(List<MongoCondition> condiciones) {
		log = new Logger();
		condicion = OR_CONDITION;
		orConditions = condiciones;
	}

	/**
	 * Genera un map con la estructura JSON que contiene la condicion
	 * 
	 * @return Map que contiene la estructura JSON con los datos de filtrado
	 */
	public Map<String, Object> generateCondition() throws MidasException {
		Map<String, Object> returnCondition = new HashMap<String, Object>();
		try {
			if (condicion == AND_CONDITION) {
				switch (operacion) {
				case EQUALS:
					returnCondition.put(campo, valor);
					break;

				case GREATER_THAN:
					returnCondition.put(campo, new Document("$gt", valor));
					break;

				case GREATER_EQUAL_THAN:
					returnCondition.put(campo, new Document("$gte", valor));
					break;

				case LESS_THAN:
					returnCondition.put(campo, new Document("$lt", valor));
					break;

				case LESS_EQUAL_THAN:
					returnCondition.put(campo, new Document("$lte", valor));
					break;

				case NOT_EQUALS:
					returnCondition.put(campo, new Document("$not",
							new Document("$eq", valor)));
					break;

				case IN:
					// Valida que el valor sea un ArrayList
					if (valor instanceof ArrayList<?>) {
						returnCondition.put(campo, new Document("$in", valor));
					} else {
						String msgError = "No se ha podido aplicar la condicion IN de MongoDB para el campo "
								+ campo + " ya que el valor no es una lista";
						log.error(msgError);
						throw new MidasException(msgError);
					}
					break;

				case NOT_IN:
					// Valida que el valor sea un ArrayList
					if (valor instanceof ArrayList<?>) {
						returnCondition.put(campo, new Document("$nin", valor));
					} else {
						String msgError = "No se ha podido aplicar la condicion NOT_IN de MongoDB para el campo "
								+ campo + " ya que el valor no es una lista";
						log.error(msgError);
						throw new MidasException(msgError);
					}
					break;

				case EXISTS:
					returnCondition.put(campo, new Document("$exists", true));
					break;

				case NOT_EXISTS:
					returnCondition.put(campo, new Document("$exists", false));
					break;

				case REGEX:
					// Valida que el valor sea un ArrayList
					if (valor instanceof ArrayList<?>) {
						@SuppressWarnings("rawtypes")
						List valores = (ArrayList) valor;
						returnCondition.put(campo, new Document("$regex",
								valores.get(0)).append("$options", valores.get(1)));
					} else {
						String msgError = "No se ha podido aplicar la condicion REGEX de MongoDB para el campo "
								+ campo + " ya que el valor no es un ArrayList<?>";
						log.error(msgError);
						throw new MidasException(msgError);
					}
					break;

				default:
					break;
				}

			} else if (condicion == OR_CONDITION) {
				List<Document> orListDocs = new ArrayList<Document>();
				// Por cada condicion del OR obtiene el map con los datos que
				// añadira al map final
				for (MongoCondition orCondition : orConditions) {
					Document orDoc = new Document();
					Map<String, Object> mapCondition = orCondition
							.generateCondition();
					for (String key : mapCondition.keySet()) {
						orDoc.append(key, mapCondition.get(key));
					}
					orListDocs.add(orDoc);
				}

				returnCondition.put("$or", orListDocs);
			}

		} catch (Exception e) {
			String msgError = "Ha ocurrido un error al generar la condicion para el campo "
					+ campo;
			log.error(msgError, e);
			throw new MidasException(msgError, e);
		}
		return returnCondition;
	}

}
