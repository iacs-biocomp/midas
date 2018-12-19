package es.aragon.midas.structure;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;


/**
 * The persistent class for the mf_zbs database table.
 * 
 */
@Entity
@Table(name="mid_zbs")
@NamedQueries({
	@NamedQuery(name="MidZona.findAll", query="SELECT b FROM MidZona b WHERE b.activo = true"),
	@NamedQuery(name="MidZona.findByZoneCode", query="SELECT b FROM MidZona b WHERE b.code = :zone AND b.activo = true"),
	@NamedQuery(name="MidZona.findBySectorCode", query="SELECT b FROM MidZona b WHERE b.sectorCode = :sector AND b.activo = true ORDER BY b.descrip")
})
@XmlRootElement
public class MidZona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Boolean activo;

	@Column(name="cod_cite")
	private String codCite;

	private String code;

	private String descrip;

	@Temporal(TemporalType.DATE)
	@Column(name="f_desde")
	private Date fDesde;

	@Temporal(TemporalType.DATE)
	@Column(name="f_hasta")
	private Date fHasta;

	@Column(name="grado_disp")
	private String gradoDisp;

	@Column(name="nuevo_id")
	private String nuevoId;

	@Column(name="sector_code")
	private String sectorCode;

	@Column(name="sector_id")
	private Integer sectorId;

	private String tipo;

	private Integer ver;

	public MidZona() {
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

	public String getCodCite() {
		return this.codCite;
	}

	public void setCodCite(String codCite) {
		this.codCite = codCite;
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

	public String getGradoDisp() {
		return this.gradoDisp;
	}

	public void setGradoDisp(String gradoDisp) {
		this.gradoDisp = gradoDisp;
	}

	public String getNuevoId() {
		return this.nuevoId;
	}

	public void setNuevoId(String nuevoId) {
		this.nuevoId = nuevoId;
	}

	public String getSectorCode() {
		return this.sectorCode;
	}

	public void setSectorCode(String sectorCode) {
		this.sectorCode = sectorCode;
	}

	public Integer getSectorId() {
		return this.sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

}