package es.aragon.midas.dao;

	// TODO implementar completamente esta clase
import es.aragon.midas.config.MidAppProperty;
import es.aragon.midas.logging.Logger;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class AppPropertiesDAO {
    
    //private EntityManager em = ConnectionFactory.getMidasEMF().createEntityManager();    
    @PersistenceContext(unitName="midas4")
    private EntityManager midasEntityManager;
    
    /**
     * 
     */
	static Logger log = new Logger();

	/**
	 * 
	 */
	public AppPropertiesDAO() {
	}

	/**
	 * 
	 * @param code
	 * @param value
	 */
	public void update (String code, String value) {
		MidAppProperty newProperty = (MidAppProperty)midasEntityManager.find(MidAppProperty.class, code);
		newProperty.setValue(value);
                midasEntityManager.merge(newProperty);
	}
	
	/**
	 * 
	 * @param codigo
	 * @param valor
	 * @param descripcion
	 */
	public void create (String codigo, String valor, String descripcion) {
		MidAppProperty newProperty = new MidAppProperty(codigo, valor, descripcion);
		midasEntityManager.persist(newProperty);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
        public HashMap<String, MidAppProperty> find() throws Exception {
            log.debug("Obteniendo relacion de parametros");

            HashMap<String, MidAppProperty> prop = new HashMap<String, MidAppProperty>();
            Query query = midasEntityManager.createNamedQuery("MidAppProperty.findAll", MidAppProperty.class);

            @SuppressWarnings("unchecked")
            List<MidAppProperty> properties = query.getResultList();

            for (MidAppProperty p : properties) {
                prop.put(p.getId(), p);
            }
            return prop;
        }
        
        /**
         * Devuelve una propiedad por ID
         * @param id
         * @return 
         */
        public MidAppProperty findById(String id) {
            return (MidAppProperty) midasEntityManager.createNamedQuery("MidAppProperty.findById")
                    .setParameter("id", id)
                    .getSingleResult();
        }

}
