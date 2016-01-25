package es.aragon.midas.mongo.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import es.aragon.midas.exception.MidasException;

/**
 * Contiene tanto la lista de condiciones que se aplican para localizar un
 * documento en una base de mongo, como el criterio para ordenar los resultados
 * obtenidos.
 * 
 * @author ARTURO
 * @since 4.3.0
 * 
 */
public class MongoFilter {

	private List<MongoCondition> conditions;
	private List<MongoSort> sort;

	public MongoFilter() {
		conditions = new ArrayList<MongoCondition>();
		sort = new ArrayList<MongoSort>();
	}

	/**
	 * Añade una condición al filtro
	 * 
	 * @param condition
	 *            Restricciones para aplicar en el filtro
	 */
	public void addCondition(MongoCondition condition) {
		conditions.add(condition);
	}

	/**
	 * Añade una regla para ordenar el resultado obtenido de una consulta mongo
	 * 
	 * @param field
	 *            Campo por el cual ordena
	 * @param sortType
	 *            Tipo de ordenación. Campos estaticos en MongoSort
	 */
	public void addSort(String field, int sortType) {
		sort.add(new MongoSort(field, sortType));
	}

	/**
	 * Genera el Document que contiene la estructura del filtro
	 * 
	 * @return JSON con las condiciones que aplicará el filtro
	 */
	public Document generateFilter() throws MidasException {
		Document filter = new Document();
		// Por cada condición obtiene el map con los datos que añadirá al
		// Document final
		for (MongoCondition condition : conditions) {
			Map<String, Object> mapCondition = condition.generateCondition();
			for (String key : mapCondition.keySet()) {
				filter.append(key, mapCondition.get(key));
			}
		}

		return filter;
	}

	/**
	 * Genera el document para ordenar el resultado obtenido
	 * 
	 * @return JSON con las reglas de como ordenar el resultado de la consulta
	 */
	public Document generateSort() {
		Document documentSort = new Document();

		for (MongoSort sort : this.sort) {
			documentSort.append(sort.getField(), sort.getType());
		}

		return documentSort;
	}

}
