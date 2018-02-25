package es.aragon.midas.dashboard.dao;

import java.util.List;

import es.aragon.midas.dashboard.jpa.DBSeriesData;


/**
 * 
 * @author carlos
 *
 */
public interface IDBDataDAO {

    public List<DBSeriesData> getData(String queryStr);

	List<Object[]> getRawData(String queryStr);

}