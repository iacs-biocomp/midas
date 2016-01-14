package es.aragon.midas.common.jpa;

import java.math.BigDecimal;
import java.util.Date;

public interface IComVUsuario {

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IComVUsuario#getDirBloque()
	 */
	public abstract String getDirNucleo();

	/* (non-Javadoc)
	 * @see es.aragon.midas.common.jpa.IComVUsuario#getDirBloque()
	 */
	public abstract void setDirNucleo(String dirNucleo);
    /**
     * 
     * @return
     */
	public abstract String getDirCodvia();
    /**
     * 
     * @param dirCodvia
     */
	public abstract void setDirCodvia(String dirCodvia);
    /**
     * 
     * @return
     */
	public abstract String getDirCodviaDesp();
    /**
     * 
     * @param dirCodviaDesp
     */
	public abstract void setDirCodviaDesp(String dirCodviaDesp);
    /**
     * 
     * @return
     */
	public abstract String getDirCp();
    /**
     * 
     * @param dirCp
     */
	public abstract void setDirCp(String dirCp);
    /**
     * 
     * @return
     */
	public abstract String getDirCpDesp();
    /**
     * 
     * @param dirCpDesp
     */
	public abstract void setDirCpDesp(String dirCpDesp);
    /**
     * 
     * @return
     */
	public abstract String getDirNucleoDesp();
    /**
     * 
     * @param dirNucleoDesp
     */
	public abstract void setDirNucleoDesp(String dirNucleoDesp);
    /**
     * 
     * @return
     */
	public abstract String getDirIne11();
    /**
     * 
     * @param dirIne11
     */
	public abstract void setDirIne11(String dirIne11);
    /**
     * 
     * @return
     */
	public abstract String getDirNombre();
    /**
     * 
     * @param dirNombre
     */
	public abstract void setDirNombre(String dirNombre);
    /**
     * 
     * @return
     */
	public abstract String getDirNombreDesp();
    /**
     * 
     * 
     * 
     * @param dirNombreDesp
     */
	public abstract void setDirNombreDesp(String dirNombreDesp);
    /**
     * 
     * @return
     */
	public abstract String getDirNumero();
    /**
     * 
     * @param dirNumero
     */
	public abstract void setDirNumero(String dirNumero);
    /**
     * 
     * @return
     */
	public abstract String getDirNumeroDesp();
    /**
     * 
     * @param dirNumeroDesp
     */
	public abstract void setDirNumeroDesp(String dirNumeroDesp);
    /**
     * 
     * @return
     */
	public abstract String getDirOtroNum();
    /**
     * 
     * @param dirOtroNum
     */
	public abstract void setDirOtroNum(String dirOtroNum);
    /**
     * 
     * 
     * @return
     */
	public abstract String getDirOtroNumDesp();
    /**
     * 
     * @param dirOtroNumDesp
     */
	public abstract void setDirOtroNumDesp(String dirOtroNumDesp);
    /**
     * 
     * @return
     */
	public abstract String getDirPoblacionDesp();
    /**
     * 
     * @param dirPoblacionDesp
     */
	public abstract void setDirPoblacionDesp(String dirPoblacionDesp);
    /**
     * 
     * @return
     */
	public abstract String getDirTpVia();
    /**
     * 
     * @param dirTpVia
     */
	public abstract void setDirTpVia(String dirTpVia);
    /**
     * 
     * @return
     */
	public abstract String getDirTpViaDesp();
    /**
     * 
     * @param dirTpViaDesp
     */
	public abstract void setDirTpViaDesp(String dirTpViaDesp);
    /**
     * 
     * @return
     */
	public abstract String getTuCodTpusuario();
    /**
     * 
     * @param tuCodTpusuario
     */
	public abstract void setTuCodTpusuario(String tuCodTpusuario);
    /**
     * 
     * @return
     */
	public abstract BigDecimal getTuSituacion();
    /**
     * 
     * @param tuSituacion
     */
	public abstract void setTuSituacion(BigDecimal tuSituacion);
    /**
     * 
     * @return
     */
	public abstract String getTuTitularidad();
    /**
     * 
     * @param tuTitularidad
     */
	public abstract void setTuTitularidad(String tuTitularidad);
    /**
     * 
     * @return
     */
	public abstract String getUsApell1();
    /**
     * 
     * @param usApell1
     */
	public abstract void setUsApell1(String usApell1);
    /**
     * 
     * @return
     */
	public abstract String getUsApell2();
    /**
     * 
     * @param usApell2
     */
	public abstract void setUsApell2(String usApell2);
    /**
     * 
     * @return
     */
	public abstract String getUsCcaaProc();
    /**
     * 
     * @param usCcaaProc
     */
	public abstract void setUsCcaaProc(String usCcaaProc);
    /**
     * 
     * @return
     */
	public abstract String getUsCentro();
    /**
     * 
     * @param usCentro
     */
	public abstract void setUsCentro(String usCentro);
    /**
     * 
     * @return
     */
	public abstract String getUsCentroDes();
    /**
     * 
     * @param usCentroDes
     */
	public abstract void setUsCentroDes(String usCentroDes);
    /**
     * 
     * @return
     */
	public abstract String getUsCia();
    /**
     * 
     * @param usCia
     */
	public abstract void setUsCia(String usCia);
    /**
     * 
     * @return
     */
	public abstract String getUsCiaTitular();
    /**
     * 
     * @param usCiaTitular
     */
	public abstract void setUsCiaTitular(String usCiaTitular);
    /**
     * 
     * @return
     */
	public abstract BigDecimal getUsCodigo();
    /**
     * 
     * @param usCodigo
     */
	public abstract void setUsCodigo(BigDecimal usCodigo);
    /**
     * 
     * @return
     */
	public abstract BigDecimal getUsDespAragon();
    /**
     * 
     * @param usDespAragon
     */
	public abstract void setUsDespAragon(BigDecimal usDespAragon);
    /**
     * 
     * @return
     */
	public abstract BigDecimal getUsDespCa();
    /**
     * 
     * @param usDespCa
     */
	public abstract void setUsDespCa(BigDecimal usDespCa);
    /**
     * 
     * @return
     */
	public abstract String getUsDni();
    /**
     * 
     * @param usDni
     */
	public abstract void setUsDni(String usDni);
    /**
     * 
     * @param usEmail
     */
	public abstract void setUsEmail(String usEmail);
    /**
     * 
     * @return
     */
	public abstract String getUsEmail();
	/**
     * 
     * @return
     */
	public abstract String getUsExtranjero();
    /**
     * 
     * @param usExtranjero
     */
	public abstract void setUsExtranjero(String usExtranjero);
    /**
     * 
     * @return
     */
	public abstract Date getUsFFinDes();
    /**
     * 
     * @param usFFinDes
     */
	public abstract void setUsFFinDes(Date usFFinDes);
    /**
     * 
     * @return
     */
	public abstract Date getUsFIniDes();
    /**
     * 
     * @param usFIniDes
     */
	public abstract void setUsFIniDes(Date usFIniDes);
    /**
     * 
     * @return
     */
	public abstract String getUsFarTp();
    /**
     * 
     * @param usFarTp
     */
	public abstract void setUsFarTp(String usFarTp);
    /**
     * 
     * @return
     */
	public abstract Date getUsFecAlta();
    /**
     * 
     * @param usFecAlta
     */
	public abstract void setUsFecAlta(Date usFecAlta);
    /**
     * 
     * @return
     */
	public abstract Date getUsFecBaja();
    /**
     * 
     * @param usFecBaja
     */
	public abstract void setUsFecBaja(Date usFecBaja);
    /**
     * 
     * @return
     */
	public abstract Date getUsFecNac();
    /**
     * 
     * @param usFecNac
     */
	public abstract void setUsFecNac(Date usFecNac);
    /**
     * 
     * @return
     */
	public abstract BigDecimal getUsIndActivo();
    /**
     * 
     * @param usIndActivo
     */
	public abstract void setUsIndActivo(BigDecimal usIndActivo);
    /**
     * 
     * @return
     */
	public abstract String getUsMotBorrado();
    /**
     * 
     * @param usMotBorrado
     */
	public abstract void setUsMotBorrado(String usMotBorrado);
    /**
     * 
     * @return
     */
	public abstract String getUsNacionalidad();
    /**
     * 
     * @param usNacionalidad
     */
	public abstract void setUsNacionalidad(String usNacionalidad);
    /**
     * 
     * @return
     */
	public abstract String getUsNombre();
    /**
     * 
     * @param usNombre
     */
	public abstract void setUsNombre(String usNombre);
    /**
     * 
     * @return
     */
	public abstract String getUsNumafil();
    /**
     * 
     * @param usNumafil
     */
	public abstract void setUsNumafil(String usNumafil);
    /**
     * 
     * @return
     */
	public abstract String getUsNumafilPro();
    /**
     * 
     * @param usNumafilPro
     */
	public abstract void setUsNumafilPro(String usNumafilPro);
    /**
     * 
     * @return
     */
	public abstract String getUsPais();
    /**
     * 
     * @param usPais
     */
	public abstract void setUsPais(String usPais);
    /**
     * 
     * @return
     */
	public abstract String getUsPasaporte();
    /**
     * 
     * @param usPasaporte
     */
	public abstract void setUsPasaporte(String usPasaporte);
    /**
     * 
     * @return
     */
	public abstract String getUsSexo();
    /**
     * 
     * @param usSexo
     */
	public abstract void setUsSexo(String usSexo);
    /**
     * 
     * @return
     */
	public abstract String getUsSns();
    /**
     * 
     * @param usSns
     */
	public abstract void setUsSns(String usSns);
    /**
     * 
     * @return
     */
	public abstract String getUsTfnoFijo();
    /**
     * 
     * @param usTfnoFijo
     */
	public abstract void setUsTfnoFijo(String usTfnoFijo);
    /**
     * 
     * @return
     */
	public abstract String getUsTfnoMovil();
    /**
     * 
     * @param usTfnoMovil
     */
	public abstract void setUsTfnoMovil(String usTfnoMovil);
    /**
     * 
     * @return
     */
	public abstract String getUsZona();
    /**
     * 
     * @param usZona
     */
	public abstract void setUsZona(String usZona);
    /**
     * 
     * @return
     */
	public abstract String getUsZonaDes();
    /**
     * 
     * @param usZonaDes
     */
	public abstract void setUsZonaDes(String usZonaDes);
}