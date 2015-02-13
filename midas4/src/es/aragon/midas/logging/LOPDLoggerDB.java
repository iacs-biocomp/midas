package es.aragon.midas.logging;

import es.aragon.midas.config.MidAudit;
import es.aragon.midas.dao.AuditLoggerDAO;
import java.util.Date;
import javax.naming.InitialContext;

/**
 *
 */
public class LOPDLoggerDB implements ILOPDLogger {

	String user = "";
	String idd = "";
	String oper = "";
	String entidad = "";
	String pk = "";
	String data = "";
	Logger log = new Logger();
	


	public String getIdd() {
		return idd;
	}


    @Override
	public void setIdd(String idd) {
		this.idd = idd;
	}



	public String getOper() {
		return oper;
	}



	public void setOper(String oper) {
		this.oper = oper;
	}



	public String getEntidad() {
		return entidad;
	}



	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}



	public String getPk() {
		return pk;
	}



	public void setPk(String pk) {
		this.pk = pk;
	}



	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}



	public String getUser() {
		return user;
	}


    public LOPDLoggerDB () {}



    /**
     * Reasigna el nombre de usuario.
     */
    @Override
    public void setUser(String user) {
        this.user = user;
    }

 
    /**
     * Registra un debug en el log LOPD.
     */
    @Override
    public void log(String _oper, String _entidad, String _pk, String _data) {
    	MidAudit au = new MidAudit();
    	au.setAuUser(user);
    	au.setAuIdd(idd);
    	au.setAuFecha(new Date());
    	au.setAuOper(_oper);
    	au.setAuEntidad(_entidad);
    	au.setAuPk(_pk);
    	au.setAuData(_data);
        try {
            AuditLoggerDAO dao = (AuditLoggerDAO) new InitialContext().lookup("java:module/AuditLoggerDAO");
            dao.persist(au);
        } catch (Exception e) {
            log.error("Imposible registrar auditoria", e);
        }
    	
    	

    }
}
