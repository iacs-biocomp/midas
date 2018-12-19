/**
 * 
 */
package es.aragon.midas.structure;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.aragon.midas.structure.MidCiasZona;
import es.aragon.midas.structure.MidSector;
import es.aragon.midas.structure.MidZona;

/**
 * @author carlos
 *
 */
@Stateless
public class SpsStructureDao implements ISpsStructureDao {

	@PersistenceContext(unitName = "midas4")
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MidZona> getAllZonas(){
		return (List<MidZona>)em.createNamedQuery("MfZona.findAll")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MidZona> getZonasBySector(String sectorCode){
		
		MidSector s = (MidSector)em.createNamedQuery("MfSector.findByCode")
			.setParameter("code", sectorCode)
			.getSingleResult();
		return (List<MidZona>)em.createNamedQuery("MfZona.findBySectorCode")
				.setParameter("sector", s.getCode())
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MidZona> getZonasByCode(String zoneCode){
		return (List<MidZona>)em.createNamedQuery("MfZona.findByZoneCode")
				.setParameter("zone", zoneCode)
				.getResultList();
	}	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<MidSector> getSectores(){
		return (List<MidSector>)em.createNamedQuery("MfSector.findAll")
				.getResultList();
	}		
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MidSector> getSectorByCode(String code){
		return (List<MidSector>)em.createNamedQuery("MfSector.findByCode")
				.setParameter("code", code)
				.getResultList();
	}		
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MidCiasZona> getCiasByZone(String zone){
		return (List<MidCiasZona>)em.createNamedQuery("MfCiasZona.findByZone")
				.setParameter("zone", zone)
				.getResultList();
	}		
	
	@Override	
	public String getSectorCode(String sectorcode) {
		String retval = "";
		try {
			MidSector s = (MidSector)(em.createNamedQuery("MfSector.findByCode")
					.setParameter("code", sectorcode)
					.getSingleResult());
			retval = s.getCode(); 
		} catch (javax.persistence.NoResultException ex) {
			retval = "";
		}
		return retval;
	}
	
	@Override
	public String getZonaByCias(String cias) {
		String retval = "";
		@SuppressWarnings("unchecked")
		List<MidCiasZona> l = (List<MidCiasZona>)(em.createNamedQuery("MidCiasZona.findZonaByCias")
				.setParameter("cias", cias)
				.getResultList());
		
		MidCiasZona foundEntity = null;
		if(!l.isEmpty()){
			foundEntity = l.get(0);
		}			
		
		retval = foundEntity.getZbsCd(); 
		return retval;
	}
	
}
