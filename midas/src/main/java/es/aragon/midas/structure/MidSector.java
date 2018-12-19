package es.aragon.midas.structure;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;


/**
 * The persistent class for the mf_sector database table.
 * 
 */
@Entity
@Table(name="mid_sector")
@NamedQueries({
	@NamedQuery(name="MidSector.findAll", query="SELECT m FROM MidSector m WHERE m.activo = true ORDER BY m.descrip"),
	@NamedQuery(name="MidSector.findByCode", query="SELECT m FROM MidSector m WHERE (m.code = :code OR m.abr = :code) AND m.activo = true")
})
@XmlRootElement
public class MidSector implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Boolean activo;

	private String code;

	private String descrip;

	@Temporal(TemporalType.DATE)
	@Column(name="f_desde")
	private Date fDesde;

	@Temporal(TemporalType.DATE)
	@Column(name="f_hasta")
	private Date fHasta;

	@Column(name="nuevo_id")
	private String nuevoId;

	private Integer ver;
	
	private String abr;

	public MidSector() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescrip() {
		return this.descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public Date getFDesde() {
		return this.fDesde;
	}

	public void setFDesde(Date fDesde) {
		this.fDesde = fDesde;
	}

	public Date getFHasta() {
		return this.fHasta;
	}

	public void setFHasta(Date fHasta) {
		this.fHasta = fHasta;
	}

	public String getNuevoId() {
		return this.nuevoId;
	}

	public void setNuevoId(String nuevoId) {
		this.nuevoId = nuevoId;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public String getAbr() {
		return abr;
	}

	public void setAbr(String abr) {
		this.abr = abr;
	}

}