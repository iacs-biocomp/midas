package es.aragon.midas.dao;

import java.util.List;
import java.util.Set;

import es.aragon.midas.config.MidGrant;
import es.aragon.midas.config.MidRole;

public interface IGrantsDAO {

	/**
	 * Devuelve un HashSet con todos los permisos de un grupo LDAP
	 * @param role
	 * @return
	 */
	Set<String> grantsByLdapRole(String role);

	/**
	 * Devuelve un HashSet con todos los permisos de un usuario
	 * @param username
	 * @return
	 */
	Set<String> grantsByUser(String username);
	
	
	/**
	 * Devuelve todos los Grants de la base de datos
	 * @return lista de MidGrants
	 */
	List<MidGrant> findAll();
	
	
	/**
	 * Busca un Grant por su ID
	 * @param grId ID del permiso buscado
	 * @return Permiso cuyo ID coincide con el buscado
	 */
	MidGrant findByGrId(String grId);

	/**
	 * Guarda un grant en Base de datos
	 * @param grant
	 */
	void save(MidGrant grant);

	/**
	 * Borra un grant de la base de datos
	 * @param grant
	 */
	void delete(MidGrant grant);

	/**
	 * Busca un rol que coincida con el código de grupo LDAP o categoría profesional pasado como parámetro.
	 * Los grupos LDAP están configurados en base de datos como "patrones" (expresiones regulares básicas). Se devolverá el 
	 * primer rol cuyo grupo LDAP asociado haga match con el grupo LDAP buscado. Esta opción permite asociar varios grupos LDAP
	 * o categorías profesionales, que respondan al mismo patrón, a un único Rol de la aplicación Midas.
	 * @param roleLdap
	 * @return el rol que coincide con el buscado, o nulo si no se encuentra ninguno.
	 */
	MidRole getRoleByLdap(String roleLdap);

	/**
	 * Devuelve todos los roles de la base de datos.
	 * @return Lista de roles completa, ordenada por order y descripción
	 */
	List<MidRole> findAllRoles();

}