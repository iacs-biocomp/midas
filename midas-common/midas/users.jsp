<%@page import="es.aragon.midas.ldap.LdapUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<s:url value="/js/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src='<s:url value="/js/mid-users.js"/>'></script>
<style>
.userActivo {
		color: black;
	}

.userInactivo {
		color: grey;
		background-color: #E5E5E5;
	}
</style>


<s:form method="post" id="formUsers" action="listUsers"
	enctype="multipart/form-data"
	cssClass="form-horizontal" namespace="/">
	<s:hidden name="userMod.userName" id="userId"></s:hidden>
	<s:hidden name="userMod.active" id="userActive" value=""></s:hidden>


	<s:if test="%{userMod.userName == null || userMod.userName == ''}">
		<h4>Usuarios</h4>
		<table class="table table-hover dataTable" id="tablaUsers">
			<thead>
				<tr>
					<th>USER NAME</th>
					<th>NOMBRE</th>
					<th>ULT. LOGIN</th>
					<th>ACTIVO</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="users">
					<tr <s:if test="%{active == 'Y'}">class="userActivo"</s:if>
						<s:else>class="userInactivo"</s:else>>
						<td><s:property value="userName" /></td>
						<td><s:property value="nombreCompleto" /></td>
						<td><s:property value="lastLogin" /></td>
						<td><img class="imgConfirmacion" title="Inactivo"
							alt="Inactivo" id="inactivo<s:property value='userName'/>"
							src='<s:url value="/css/images/cancel.png"/>'
							onclick="activarUser('<s:property value="userName" />','N')">
							<img class="imgConfirmacion" title="Activo" alt="Activo"
							id="activo<s:property value='userName'/>"
							src='<s:url value="/css/images/checked.png"/>'
							onclick="activarUser('<s:property value="userName" />','Y')">
						</td>
						<td><a href="javascript:void(0)"
							onclick="seleccionarUser('<s:property value="userName" />')">Seleccionar</a></td>
					</tr>
					<script type="text/javascript">
						resaltaEstado('<s:property value="userName"/>',
								'<s:property value="active"/>');
					</script>
				</s:iterator>
			</tbody>
		</table>
		<script>
			$('#tablaUsers').DataTable();
		</script>

		<br>
		<hr>
		

		<h4>Nuevo</h4>
		<form>
		Email: <s:textfield id="userNew_email" name="userNew.email" label="Email"
			onblur="setNombreAD(this.value);" />
		<script type="text/javascript">
			function setNombreAD(email) {
				ajaxLoadDiv("newUserName", "searchUserNameUsers?"
						+ $("#formUsers").serialize());
			}
		</script>

		<!-- div que se recarga con el nombre de usuario -->
		<div id="newUserName">
			<jsp:include page="newUserName.jsp" />
		</div>
		<s:if test="hasActionErrors()">
			<div class="errors">
				<s:actionerror escape="false" />
			</div>
		</s:if>
		<input type="button" name="btnNuevoUsuario" id="btnNuevoUsuario"
			value="Dar de alta" onclick="nuevoUser()" class="btn btn-success" />
		</form>
	</s:if>

	<s:else>

		<br>

		email: <s:textfield name="userMod.userName" id="userMod_userName"
			label="UserName" disabled="true" />
		IDD: <s:textfield name="userMod.idd" id="userMod_idd" label="IDD"
			onkeyup="textToUpper(this);" />
		Nombre: <s:textfield name="userMod.name" id="userMod_name" label="Nombre"
			onkeyup="textToUpper(this);" />
		Primer apellido: <s:textfield name="userMod.lastname1" id="userMod_lastname1"
			label="Apellido 1" onkeyup="textToUpper(this);" />
		Segundo apellido: <s:textfield name="userMod.lastname2" id="userMod_lastname2"
			label="Apellido 2" onkeyup="textToUpper(this);" />

		<s:if test="hasActionMessages()">
			<s:actionmessage escape="false" />
		</s:if>
		<div class="offset3">
			<input type="button" class="btn btn-success"
				onclick="modificarUser('')" value="Modificar" /> 
			<input
				type="button" class="btn" onclick="seleccionarUser('')"
				value="Volver" />
		</div>


		<br>
		<s:if test="%{!roles.isEmpty()}">
			<table class="table table-hover" id="tablaUserRoles"
				style="width: 50%">
				<thead>
					<tr>
						<th>ROL</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="userMod.midRoleList" var="rolMod">
						<tr>
							<td><s:property value="#rolMod.roleId" /></td>
							<td><img title="Borrar role" alt="Borrar role"
								src='<s:url value="/css/images/delete.png"/>'
								style="cursor: pointer"
								onclick="borrarUserRole('<s:property value="#rolMod.roleId" />')"></td>
						</tr>
					</s:iterator>
					<tr>
						<td><select name="userRol.roleId" id="user_roleId">
								<option value=""></option>
								<s:iterator value="roles">
									<option value="<s:property value="roleId" />"><s:property
											value="roleId" /></option>
								</s:iterator>
						</select></td>
						<td><img title="Add userRole" alt="Add userRole"
							src='<s:url value="/css/images/add.png"/>'
							style="cursor: pointer" onclick="nuevoUserRol()"></td>
					</tr>
				</tbody>
			</table>
		</s:if>
		<br>
		<s:if test="%{!contexts.isEmpty()}">
			<table class="table table-hover" id="tablaUserContexts"
				style="width: 50%">
				<thead>
					<tr>
						<th>CONTEXT</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="userMod.midContextList" var="ctxMod">
						<tr>
							<td><s:property value="#ctxMod.cxKey" /></td>
							<td><img title="Borrar context" alt="Borrar context"
								src='<s:url value="/css/images/delete.png"/>'
								style="cursor: pointer"
								onclick="borrarUserContext('<s:property value="#ctxMod.cxId" />')"></td>
						</tr>
					</s:iterator>
					<tr>
						<td><select name="userContext.cxId" id="user_cxId">
								<option value=""></option>
								<s:iterator value="contexts">
									<option value="<s:property value="cxId" />"><s:property
											value="cxKey" /></option>
								</s:iterator>
						</select></td>
						<td><img title="Add userContext" alt="Add userContext"
							src='<s:url value="/css/images/add.png"/>'
							style="cursor: pointer" onclick="nuevoUserContext()"></td>
					</tr>
				</tbody>
			</table>
		</s:if>
	</s:else>
</s:form>