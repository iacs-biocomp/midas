package es.aragon.midas.mongo.update;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

/**
 * Clase encargada de gestionar las modificaciones para MongoDB. En esta clase
 * se almacenan las modificaciones para realizar y se encarga de generar el
 * documento que envia con las modificaciones.
 * 
 * @author ARTURO
 * @since 4.3.0
 * 
 */
public class UpdateList {

	private List<UpdateElement> updates;

	public UpdateList() {
		updates = new ArrayList<UpdateElement>();
	}

	/**
	 * Añade una modificacion a la lista de modificaciones
	 * 
	 * @param field
	 *            Campo a modificar.
	 * @param newValue
	 *            Nuevo valor para el campo.
	 */
	public void addUpdate(String field, Object newValue) {
		updates.add(new UpdateElement(field, newValue));
	}

	/**
	 * Genera el objeto necesario para indicarle a mongo que elementos debe
	 * modificar.
	 * 
	 * @return Documento indicando que debe modificar con los valores que se han
	 *         ido añadiendo en addUpdate
	 */
	public Document generateUpdates() {
		Document documentUpdates = new Document();
		for (UpdateElement update : updates) {
			documentUpdates.append(update.getField(), update.getNewValue());
		}

		return new Document("$set", documentUpdates);
	}

}
