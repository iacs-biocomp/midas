package es.aragon.midas.structure;

import java.util.List;

public interface ISpsStructureDao {

	List<MidZona> getAllZonas();

	List<MidZona> getZonasBySector(String sectorCode);

	String getSectorCode(String sectorcode);

	List<MidCiasZona> getCiasByZone(String zone);

	List<MidSector> getSectorByCode(String code);

	List<MidSector> getSectores();

	List<MidZona> getZonasByCode(String zoneCode);

	String getZonaByCias(String cias);

}
