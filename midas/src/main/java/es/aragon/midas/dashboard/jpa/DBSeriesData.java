package es.aragon.midas.dashboard.jpa;


import java.io.Serializable;


public class DBSeriesData implements Serializable {
	
	private static final long serialVersionUID = 746896431465243931L;

	private String xvalue;
    private String ykey;
    private String ylabel;
    private double yvalue;

    
    public DBSeriesData(String xvalue,
    					   String ykey,
    					   String ylabel,
    					   String yvalue) {
    	this.xvalue = xvalue;
    	this.ykey = ykey;
    	this.ylabel = ylabel;
    	this.yvalue = Double.parseDouble(yvalue);
    	
    }
    
    public String getXvalue() {
		return xvalue;
	}

	public void setXvalue(String xvalue) {
		this.xvalue = xvalue;
	}

	public String getYkey() {
		return ykey;
	}

	public void setYkey(String ykey) {
		this.ykey = ykey;
	}

	public String getYlabel() {
		return ylabel;
	}

	public void setYlabel(String ylabel) {
		this.ylabel = ylabel;
	}

	public double getYvalue() {
		return yvalue;
	}

	public void setYvalue(double yvalue) {
		this.yvalue = yvalue;
	}

    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DBSeriesData)) {
            return false;
        }
        DBSeriesData other = (DBSeriesData) object;
        if ((this.xvalue == null && other.xvalue != null) || (this.yvalue != other.yvalue)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.aragon.iacs.dbPortal.jpa.DBSeriesData";
    }

}