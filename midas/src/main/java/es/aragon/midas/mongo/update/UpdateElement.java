package es.aragon.midas.mongo.update;

/**
 * Clase encargada de almacenar una modificacion para mongo. Almacena el campo
 * que hay que modificar y el nuevo valor que va a tomar.
 * 
 * @author ARTURO
 * @since 4.3.0
 * 
 */
public class UpdateElement {

	private String field;
	private Object newValue;

	public UpdateElement(String field, Object newValue) {
		this.field = field;
		this.newValue = newValue;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getNewValue() {
		return newValue;
	}

	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

}
