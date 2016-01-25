package es.aragon.midas.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import es.aragon.midas.exception.MidasException;
import es.aragon.midas.mongo.filter.MongoFilter;
import es.aragon.midas.mongo.update.UpdateList;

/**
 * Clase encargada de gestionar las operaciones que se realizar contra una base
 * de datos de MongoDB
 * 
 * @author ARTURO
 * @since 4.3.0
 * 
 */
public class MongoManager {

	private String host;
	private int port;
	private String dataBase;

	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;

	/**
	 * Asigna los parámetros para conectar a la base de MongoDB
	 * 
	 * @param host
	 *            IP donde se encuentra el servidor mongo
	 * @param port
	 *            Puerto en el que escucha el servicio de mongo
	 * @param dataBase
	 *            Nombre de la base de datos contra la que se conecta
	 */
	public MongoManager(String host, int port, String dataBase) {
		this.host = host;
		this.port = port;
		this.dataBase = dataBase;
	}

	/**
	 * Abre la conexión con mongo
	 */
	public void open() {
		mongoClient = new MongoClient(host, port);
		mongoDatabase = mongoClient.getDatabase(dataBase);
	}

	/**
	 * Cierra la conexión con mongo
	 */
	public void close() {
		mongoClient.close();

	}

	/**
	 * Busca todos los documentos en una colección
	 * 
	 * @param collection
	 *            Nombre de la colección
	 * @return Lista con todos los documentos JSON encontrados en la colección
	 * @throws MidasException
	 */
	public List<Document> findAll(String collection) throws MidasException {
		return find(collection, new MongoFilter());
	}

	/**
	 * Realiza una consulta a una coleccion aplicando un filtro
	 * 
	 * @param collection
	 *            Nombre de la colección
	 * @param filter
	 *            Contiene las condiciones que se aplican el la consulta y las
	 *            reglas para ordenar el resultado obtenido
	 * @return Lista con todos los documentos JSON encontrados en la colección
	 * @throws MidasException
	 */
	public List<Document> find(String collection, MongoFilter filter)
			throws MidasException {
		// Realiza la consulta en la base mongo aplicando los filtros
		FindIterable<Document> iterable = mongoDatabase.getCollection(
				collection).find(filter.generateFilter());

		// Ordena los resultados obtenidos
		iterable.sort(filter.generateSort());

		return FindIterableToList(iterable);
	}

	/**
	 * Crea un nuevo documento JSON en una colección
	 * 
	 * @param collection
	 *            Nombre de la colección
	 * @param document
	 *            Documento JSON para crear
	 */
	public void create(String collection, Document document) {
		mongoDatabase.getCollection(collection).insertOne(document);

	}

	/**
	 * Crea varios documentos JSON en una colección
	 * 
	 * @param collection
	 *            Nombre de la colección
	 * @param documents
	 *            Lista con los documentos JSON para insertar
	 */
	public void create(String collection, List<Document> documents) {
		mongoDatabase.getCollection(collection).insertMany(documents);

	}

	/**
	 * Modifica los documentos que cumplan alguna condición en una coleccion
	 * 
	 * @param collection
	 *            Nombre de la colección
	 * @param filter
	 *            Condiciones para que el documento sea modificado
	 * @param updates
	 *            Lista de cambios para aplicar en la modificación
	 * @throws MidasException
	 */
	public void update(String collection, MongoFilter filter, UpdateList updates)
			throws MidasException {
		mongoDatabase.getCollection(collection).updateMany(
				filter.generateFilter(), updates.generateUpdates());
	}

	/**
	 * Remplaza en una colección un documento por otro que mantendrá en mismo
	 * _id
	 * 
	 * @param collection
	 *            Nombre de la colección
	 * @param oldDocument
	 *            Version anterior del documento para buscarla en la base de
	 *            datos
	 * @param newDocument
	 *            Nueva versión por la cual será remplazada
	 * @throws MongoWriteException
	 *             El _id de los dos documentos debe ser el mismo
	 */
	public void replace(String collection, Document oldDocument,
			Document newDocument) throws MongoWriteException {
		mongoDatabase.getCollection(collection).replaceOne(oldDocument,
				newDocument);
	}

	/**
	 * Elimina un documento de una colección
	 * 
	 * @param collection
	 *            Nombre de la colección
	 * @param document
	 *            JSON del documento para identificarlo
	 */
	public void delete(String collection, Document document) {
		mongoDatabase.getCollection(collection).deleteOne(document);

	}

	/**
	 * Elimina los documentos de una colección cuando cumplan un filtro
	 * 
	 * @param collection
	 *            Nombre de la colección
	 * @param filter
	 *            Condiciones que aplica para buscar que documentos hay que
	 *            borrar
	 * @throws MidasException
	 */
	public void delete(String collection, MongoFilter filter)
			throws MidasException {
		mongoDatabase.getCollection(collection).deleteMany(
				filter.generateFilter());

	}

	/**
	 * Genera una lista de documentos a partir del resultado obtenido de una
	 * consulta
	 * 
	 * @param iterable
	 *            Resultado obtenido de una consulta a mongo
	 * @return Lista con los documentos JSON obtenidos de la consulta
	 */
	private List<Document> FindIterableToList(FindIterable<Document> iterable) {
		List<Document> documents = new ArrayList<Document>();
		for (Document document : iterable) {
			documents.add(document);
		}
		return documents;
	}

}
