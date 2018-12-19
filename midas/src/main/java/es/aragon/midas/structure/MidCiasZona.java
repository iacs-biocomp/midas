package es.aragon.midas.structure;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the mf_cias_zona database table.
 * 
 */
@Entity
@Table(name="mid_cias_zona")
@NamedQueries({
	@NamedQuery(name="MidCiasZona.findAll", query="SELECT m FROM MidCiasZona m"),
	@NamedQuery(name="MidCiasZona.findZonaByCias", query="SELECT DISTINCT m FROM MidCiasZona m WHERE m.ciasCd = :cias")
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "MidCiasZona.findByZone", 
			query = "select cias_cd, max(centro_cd) centro_cd, zbs_cd, max(cat_cd) cat_cd, 'S' activo, max(id) id \n" + 
					"from mfm.mf_cias_zona\n" + 
					"WHERE zbs_cd = :zone and activo = 'S'\n" + 
					"group by zbs_cd, cias_cd", resultClass = MidCiasZona.class)
})


@XmlRootElement
public class MidCiasZona implements Serializable {
	private static final long serialVersionUID = 1L;

	private String activo;

	@Column(name="cat_cd")
	private String catCd;

	@Column(name="centro_cd")
	private String centroCd;

	@Column(name="cias_cd")
	private String ciasCd;

	@Id
	private Integer id;

	@Column(name="zbs_cd")
	private String zbsCd;

	public MidCiasZona() {
	}

	public String getActivo() {
		return this.activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getCatCd() {
		return this.catCd;
	}

	public void setCatCd(String catCd) {
		this.catCd = catCd;
	}

	public String getCentroCd() {
		return this.centroCd;
	}

	public void setCentroCd(String centroCd) {
		this.centroCd = centroCd;
	}

	public String getCiasCd() {
		return this.ciasCd;
	}

	public void setCiasCd(String ciasCd) {
		this.ciasCd = ciasCd;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZbsCd() {
		return this.zbsCd;
	}

	public void setZbsCd(String zbsCd) {
		this.zbsCd = zbsCd;
	}

}