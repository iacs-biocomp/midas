package es.aragon.midas.common.jpa;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the COM_V_USUARIOS database table.
 * 
 */


public class ComVUsuario implements Serializable, IComVUsuario {
	private static final long serialVersionUID = 1L;

	private String dirCodvia;
	private String dirCodviaDesp;
	private String dirCp;
	private String dirCpDesp;
	private String dirIne11;
	private String dirNombre;
	private String dirNombreDesp;
	private String dirNucleo;
	private String dirNucleoDesp;
	private String dirNumero;
	private String dirNumeroDesp;
	private String dirOtroNum;
	private String dirOtroNumDesp;
	private String dirPoblacionDesp;
	private String dirTpVia;
	private String dirTpViaDesp;
	private String tuCodTpusuario;
	private BigDecimal tuSituacion;
	private String tuTitularidad;
	private String usApell1;
	private String usApell2;
	private String usCcaaProc;
	private String usCentro;
	private String usCentroDes;
	private String usCia;
	private String usCiaTitular;
	private BigDecimal usCodigo;
	private BigDecimal usDespAragon;
	private BigDecimal usDespCa;
	private String usDni;
	private String usEmail;
	private String usExtranjero;
	private Date usFFinDes;
	private Date usFIniDes;
	private String usFarTp;
	private Date usFecAlta;
	private Date usFecBaja;
	private Date usFecNac;
	private BigDecimal usIndActivo;
	private String usMotBorrado;
	private String usNacionalidad;
	private String usNombre;
	private String usNumafil;
	private String usNumafilPro;
	private String usPais;
	private String usPasaporte;
	private String usSexo;
	private String usSns;
	private String usTfnoFijo;
	private String usTfnoMovil;
	private String usZona;
	private String usZonaDes;

	public ComVUsuario() {
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirNucleo()
	 */
	@Override
	public String getDirNucleo() {
		return this.dirNucleo;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirNucleo(java.lang.String)
	 */
	@Override
	public void setDirNucleo(String dirNucleo) {
		this.dirNucleo = dirNucleo;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirCodvia()
	 */
	@Override
	public String getDirCodvia() {
		return this.dirCodvia;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirCodvia(java.lang.String)
	 */
	@Override
	public void setDirCodvia(String dirCodvia) {
		this.dirCodvia = dirCodvia;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirCodviaDesp()
	 */
	@Override
	public String getDirCodviaDesp() {
		return this.dirCodviaDesp;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirCodviaDesp(java.lang.String)
	 */
	@Override
	public void setDirCodviaDesp(String dirCodviaDesp) {
		this.dirCodviaDesp = dirCodviaDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirCp()
	 */
	@Override
	public String getDirCp() {
		return this.dirCp;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirCp(java.lang.String)
	 */
	@Override
	public void setDirCp(String dirCp) {
		this.dirCp = dirCp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirCpDesp()
	 */
	@Override
	public String getDirCpDesp() {
		return this.dirCpDesp;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirCpDesp(java.lang.String)
	 */
	@Override
	public void setDirCpDesp(String dirCpDesp) {
		this.dirCpDesp = dirCpDesp;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirNucleoDesp()
	 */
	@Override
	public String getDirNucleoDesp() {
		return this.dirNucleoDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirNucleoDesp(java.lang.String)
	 */
	@Override
	public void setDirNucleoDesp(String dirNucleoDesp) {
		this.dirNucleoDesp = dirNucleoDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirIne11()
	 */
	@Override
	public String getDirIne11() {
		return this.dirIne11;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirIne11(java.lang.String)
	 */
	@Override
	public void setDirIne11(String dirIne11) {
		this.dirIne11 = dirIne11;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirNombre()
	 */
	@Override
	public String getDirNombre() {
		return this.dirNombre;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirNombre(java.lang.String)
	 */
	@Override
	public void setDirNombre(String dirNombre) {
		this.dirNombre = dirNombre;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirNombreDesp()
	 */
	@Override
	public String getDirNombreDesp() {
		return this.dirNombreDesp;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirNombreDesp(java.lang.String)
	 */
	@Override
	public void setDirNombreDesp(String dirNombreDesp) {
		this.dirNombreDesp = dirNombreDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirNumero()
	 */
	@Override
	public String getDirNumero() {
		return this.dirNumero;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirNumero(java.lang.String)
	 */
	@Override
	public void setDirNumero(String dirNumero) {
		this.dirNumero = dirNumero;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirNumeroDesp()
	 */
	@Override
	public String getDirNumeroDesp() {
		return this.dirNumeroDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirNumeroDesp(java.lang.String)
	 */
	@Override
	public void setDirNumeroDesp(String dirNumeroDesp) {
		this.dirNumeroDesp = dirNumeroDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirOtroNum()
	 */
	@Override
	public String getDirOtroNum() {
		return this.dirOtroNum;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirOtroNum(java.lang.String)
	 */
	@Override
	public void setDirOtroNum(String dirOtroNum) {
		this.dirOtroNum = dirOtroNum;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirOtroNumDesp()
	 */
	@Override
	public String getDirOtroNumDesp() {
		return this.dirOtroNumDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirOtroNumDesp(java.lang.String)
	 */
	@Override
	public void setDirOtroNumDesp(String dirOtroNumDesp) {
		this.dirOtroNumDesp = dirOtroNumDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirPoblacionDesp()
	 */
	@Override
	public String getDirPoblacionDesp() {
		return this.dirPoblacionDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirPoblacionDesp(java.lang.String)
	 */
	@Override
	public void setDirPoblacionDesp(String dirPoblacionDesp) {
		this.dirPoblacionDesp = dirPoblacionDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirTpVia()
	 */
	@Override
	public String getDirTpVia() {
		return this.dirTpVia;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirTpVia(java.lang.String)
	 */
	@Override
	public void setDirTpVia(String dirTpVia) {
		this.dirTpVia = dirTpVia;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getDirTpViaDesp()
	 */
	@Override
	public String getDirTpViaDesp() {
		return this.dirTpViaDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setDirTpViaDesp(java.lang.String)
	 */
	@Override
	public void setDirTpViaDesp(String dirTpViaDesp) {
		this.dirTpViaDesp = dirTpViaDesp;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getTuCodTpusuario()
	 */
	@Override
	public String getTuCodTpusuario() {
		return this.tuCodTpusuario;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setTuCodTpusuario(java.lang.String)
	 */
	@Override
	public void setTuCodTpusuario(String tuCodTpusuario) {
		this.tuCodTpusuario = tuCodTpusuario;
	}

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getTuSituacion()
	 */
	@Override
	public BigDecimal getTuSituacion() {
		return this.tuSituacion;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setTuSituacion(java.math.BigDecimal)
	 */
	@Override
	public void setTuSituacion(BigDecimal tuSituacion) {
		this.tuSituacion = tuSituacion;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getTuTitularidad()
	 */
	@Override
	public String getTuTitularidad() {
		return this.tuTitularidad;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setTuTitularidad(java.lang.String)
	 */
	@Override
	public void setTuTitularidad(String tuTitularidad) {
		this.tuTitularidad = tuTitularidad;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsApell1()
	 */
	@Override
	public String getUsApell1() {
		return this.usApell1;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsApell1(java.lang.String)
	 */
	@Override
	public void setUsApell1(String usApell1) {
		this.usApell1 = usApell1;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsApell2()
	 */
	@Override
	public String getUsApell2() {
		return this.usApell2;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsApell2(java.lang.String)
	 */
	@Override
	public void setUsApell2(String usApell2) {
		this.usApell2 = usApell2;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsCcaaProc()
	 */
	@Override
	public String getUsCcaaProc() {
		return this.usCcaaProc;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsCcaaProc(java.lang.String)
	 */
	@Override
	public void setUsCcaaProc(String usCcaaProc) {
		this.usCcaaProc = usCcaaProc;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsCentro()
	 */
	@Override
	public String getUsCentro() {
		return this.usCentro;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsCentro(java.lang.String)
	 */
	@Override
	public void setUsCentro(String usCentro) {
		this.usCentro = usCentro;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsCentroDes()
	 */
	@Override
	public String getUsCentroDes() {
		return this.usCentroDes;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsCentroDes(java.lang.String)
	 */
	@Override
	public void setUsCentroDes(String usCentroDes) {
		this.usCentroDes = usCentroDes;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsCia()
	 */
	@Override
	public String getUsCia() {
		return this.usCia;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsCia(java.lang.String)
	 */
	@Override
	public void setUsCia(String usCia) {
		this.usCia = usCia;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsCiaTitular()
	 */
	@Override
	public String getUsCiaTitular() {
		return this.usCiaTitular;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsCiaTitular(java.lang.String)
	 */
	@Override
	public void setUsCiaTitular(String usCiaTitular) {
		this.usCiaTitular = usCiaTitular;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsCodigo()
	 */
	@Override
	public BigDecimal getUsCodigo() {
		return this.usCodigo;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsCodigo(java.math.BigDecimal)
	 */
	@Override
	public void setUsCodigo(BigDecimal usCodigo) {
		this.usCodigo = usCodigo;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsDespAragon()
	 */
	@Override
	public BigDecimal getUsDespAragon() {
		return this.usDespAragon;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsDespAragon(java.math.BigDecimal)
	 */
	@Override
	public void setUsDespAragon(BigDecimal usDespAragon) {
		this.usDespAragon = usDespAragon;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsDespCa()
	 */
	@Override
	public BigDecimal getUsDespCa() {
		return this.usDespCa;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsDespCa(java.math.BigDecimal)
	 */
	@Override
	public void setUsDespCa(BigDecimal usDespCa) {
		this.usDespCa = usDespCa;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsDni()
	 */
	@Override
	public String getUsDni() {
		return this.usDni;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsDni(java.lang.String)
	 */
	@Override
	public void setUsDni(String usDni) {
		this.usDni = usDni;
	}



	public String getUsEmail() {
		return usEmail;
	}


	public void setUsEmail(String usEmail) {
		this.usEmail = usEmail;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsExtranjero()
	 */
	@Override
	public String getUsExtranjero() {
		return this.usExtranjero;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsExtranjero(java.lang.String)
	 */
	@Override
	public void setUsExtranjero(String usExtranjero) {
		this.usExtranjero = usExtranjero;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsFFinDes()
	 */
	@Override
	public Date getUsFFinDes() {
		return this.usFFinDes;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsFFinDes(java.util.Date)
	 */
	@Override
	public void setUsFFinDes(Date usFFinDes) {
		this.usFFinDes = usFFinDes;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsFIniDes()
	 */
	@Override
	public Date getUsFIniDes() {
		return this.usFIniDes;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsFIniDes(java.util.Date)
	 */
	@Override
	public void setUsFIniDes(Date usFIniDes) {
		this.usFIniDes = usFIniDes;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsFarTp()
	 */
	@Override
	public String getUsFarTp() {
		return this.usFarTp;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsFarTp(java.lang.String)
	 */
	@Override
	public void setUsFarTp(String usFarTp) {
		this.usFarTp = usFarTp;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsFecAlta()
	 */
	@Override
	public Date getUsFecAlta() {
		return this.usFecAlta;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsFecAlta(java.util.Date)
	 */
	@Override
	public void setUsFecAlta(Date usFecAlta) {
		this.usFecAlta = usFecAlta;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsFecBaja()
	 */
	@Override
	public Date getUsFecBaja() {
		return this.usFecBaja;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsFecBaja(java.util.Date)
	 */
	@Override
	public void setUsFecBaja(Date usFecBaja) {
		this.usFecBaja = usFecBaja;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsFecNac()
	 */
	@Override
	public Date getUsFecNac() {
		return this.usFecNac;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsFecNac(java.util.Date)
	 */
	@Override
	public void setUsFecNac(Date usFecNac) {
		this.usFecNac = usFecNac;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsIndActivo()
	 */
	@Override
	public BigDecimal getUsIndActivo() {
		return this.usIndActivo;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsIndActivo(java.math.BigDecimal)
	 */
	@Override
	public void setUsIndActivo(BigDecimal usIndActivo) {
		this.usIndActivo = usIndActivo;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsMotBorrado()
	 */
	@Override
	public String getUsMotBorrado() {
		return this.usMotBorrado;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsMotBorrado(java.lang.String)
	 */
	@Override
	public void setUsMotBorrado(String usMotBorrado) {
		this.usMotBorrado = usMotBorrado;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsNacionalidad()
	 */
	@Override
	public String getUsNacionalidad() {
		return this.usNacionalidad;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsNacionalidad(java.lang.String)
	 */
	@Override
	public void setUsNacionalidad(String usNacionalidad) {
		this.usNacionalidad = usNacionalidad;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsNombre()
	 */
	@Override
	public String getUsNombre() {
		return this.usNombre;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsNombre(java.lang.String)
	 */
	@Override
	public void setUsNombre(String usNombre) {
		this.usNombre = usNombre;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsNumafil()
	 */
	@Override
	public String getUsNumafil() {
		return this.usNumafil;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsNumafil(java.lang.String)
	 */
	@Override
	public void setUsNumafil(String usNumafil) {
		this.usNumafil = usNumafil;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsNumafilPro()
	 */
	@Override
	public String getUsNumafilPro() {
		return this.usNumafilPro;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsNumafilPro(java.lang.String)
	 */
	@Override
	public void setUsNumafilPro(String usNumafilPro) {
		this.usNumafilPro = usNumafilPro;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsPais()
	 */
	@Override
	public String getUsPais() {
		return this.usPais;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsPais(java.lang.String)
	 */
	@Override
	public void setUsPais(String usPais) {
		this.usPais = usPais;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsPasaporte()
	 */
	@Override
	public String getUsPasaporte() {
		return this.usPasaporte;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsPasaporte(java.lang.String)
	 */
	@Override
	public void setUsPasaporte(String usPasaporte) {
		this.usPasaporte = usPasaporte;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsSexo()
	 */
	@Override
	public String getUsSexo() {
		return this.usSexo;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsSexo(java.lang.String)
	 */
	@Override
	public void setUsSexo(String usSexo) {
		this.usSexo = usSexo;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsSns()
	 */
	@Override
	public String getUsSns() {
		return this.usSns;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsSns(java.lang.String)
	 */
	@Override
	public void setUsSns(String usSns) {
		this.usSns = usSns;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsTfnoFijo()
	 */
	@Override
	public String getUsTfnoFijo() {
		return this.usTfnoFijo;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsTfnoFijo(java.lang.String)
	 */
	@Override
	public void setUsTfnoFijo(String usTfnoFijo) {
		this.usTfnoFijo = usTfnoFijo;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsTfnoMovil()
	 */
	@Override
	public String getUsTfnoMovil() {
		return this.usTfnoMovil;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsTfnoMovil(java.lang.String)
	 */
	@Override
	public void setUsTfnoMovil(String usTfnoMovil) {
		this.usTfnoMovil = usTfnoMovil;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsZona()
	 */
	@Override
	public String getUsZona() {
		return this.usZona;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsZona(java.lang.String)
	 */
	@Override
	public void setUsZona(String usZona) {
		this.usZona = usZona;
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#getUsZonaDes()
	 */
	@Override
	public String getUsZonaDes() {
		return this.usZonaDes;
	}



	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IConVUsuario#setUsZonaDes(java.lang.String)
	 */
	@Override
	public void setUsZonaDes(String usZonaDes) {
		this.usZonaDes = usZonaDes;
	}

}