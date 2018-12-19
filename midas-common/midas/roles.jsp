<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<s:url value="/js/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src='<s:url value="/js/mid-roles.js"/>'></script>


<s:form method="post" id="formRoles" action="list" enctype="multipart/form-data" cssClass="form-horizontal" namespace="/">
 
<h4>Roles</h4>
<table class="table table-striped table-hover" id="tablaRoles">
	<thead>
		<tr>
			<th>ROLE ID</th>
			<th>ROLE DESC</th>
			<th>ROLE LDAP</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="roles">
		<tr>
			<td><s:property value="roleId" /></td>
			<td><s:property value="roleDesc" /></td>
			<td><s:property value="roleLdap" /></td>	
			<td><img title="Borrar role" alt="Borrar role" src='' style="cursor: pointer" onclick="borrarRol('<s:property value="roleId" />')"></td>
		</tr>
		</s:iterator>
		
		<tr>
			<td><input name="rol.roleId" id="rol_roleId" class="input-medium" value="" maxlength="20"/></td>
			<td><input name="rol.roleDesc" id="rol_roleDesc" class="input-medium" value="" maxlength="40"/></td>
			<td><input name="rol.roleLdap" id="rol_roleLdap" class="input-medium" value="" maxlength="50"/></td>
			<td><img title="Add role" alt="Add role" src='<s:url value="/css/images/add.png"/>' style="cursor: pointer" onclick="nuevoRol()"></td>
		</tr>
		
	</tbody>
</table>



<h4>Roles - Grants</h4>
<table class="table table-striped table-hover" id="tablaRolesGrants">
	<thead>
		<tr>
			<th>ROLE</th>
			<th>GRANT</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="rolesGrants" var="rg">
			<s:iterator value="#rg.midGrantList" var="gr">
			<tr>
				<td><s:property value="#rg.roleId" /></td>
				<td><s:property value="#gr.grId" /></td>
				<td><img title="Borrar role-grant" alt="Borrar role-grant" src='<s:url value="/css/images/delete.png"/>' style="cursor: pointer" onclick="borrarRoleGrant('<s:property value="#rg.roleId" />','<s:property value="#gr.grId" />')"></td>
			</tr>
			</s:iterator>
		</s:iterator>
		<tr>
			<td>
				<select id="selectRG_R">
					<option value=""></option>
					<s:iterator value="roles">
						<option value="<s:property value="roleId" />"><s:property value="roleId" /></option>
					</s:iterator>
				</select>
			</td>
			<td>
				<select id="selectRG_G">
					<option value=""></option>
					<s:iterator value="grants">
						<option value="<s:property value="grId" />"><s:property value="grId" /></option>
					</s:iterator>
				</select>
			</td>
			<td><img title="Add roleGrant" alt="Add roleGrant" src='<s:url value="/css/images/add.png"/>' style="cursor: pointer" onclick="nuevoRoleGrant()"></td>
		</tr>
	</tbody>
</table>




<h4>Roles - Contexts</h4>
<table class="table table-striped table-hover" id="tablaRolesContexts">
	<thead>
		<tr>
			<th>ROLE</th>
			<th>CONTEXT</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="rolesContexts" var="rc">
			<s:iterator value="#rc.midContextList" var="cr">
				<tr>
					<td><s:property value="#rc.roleId" /></td>
					<td><s:property value="#cr.cxId" /></td>
					<td><img title="Borrar role-contextt" alt="Borrar role-context" src='<s:url value="/css/images/delete.png"/>' style="cursor: pointer" onclick="borrarRoleContext('<s:property value="#rc.roleId" />','<s:property value="#cr.cxId" />')"></td>
				</tr>
			</s:iterator>
		</s:iterator>
		<tr>
			<td>
				<select id="selectRC_R">
					<option value=""></option>
					<s:iterator value="roles">
						<option value="<s:property value="roleId" />"><s:property value="roleId" /> - <s:property value="roleDesc" /></option>
					</s:iterator>
				</select>
			</td>
			<td>
				<select id="selectRC_C">
					<option value=""></option>
					<s:iterator value="contexts">
						<option value="<s:property value="cxId" />"><s:property value="cxKey" /> - <s:property value="cxValue" /></option>
					</s:iterator>
				</select>
			</td>
			<td><img title="Add roleContext" alt="Add roleContext" src='<s:url value="/css/images/add.png"/>' style="cursor: pointer" onclick="nuevoRoleContext()"></td>
		</tr>
	</tbody>
</table>

<script>
	$('#tablaRoles').DataTable();
	$('#tablaRolesGrants').DataTable();
	$('#tablaRolesContexts').DataTable();
</script>

<s:hidden name="grant.grId" id="grant_grId"  value=""></s:hidden>
<s:hidden name="context.cxId" id="context_cxId"  value=""></s:hidden>
 
 </s:form>
 