<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src='<s:url value="/js/mid-grants.js"/>'></script>

<s:form method="post" id="formGrants" action="list" enctype="multipart/form-data" theme="bootstrap" cssClass="form-horizontal" namespace="/">
<h4>Grants</h4>
<table class="table table-striped table-hover" id="tablaGrants">
	<thead>
		<tr>
			<th>GRANT ID</th>
			<th>GRANT DESC</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="grants">
		<tr>
			<td><s:property value="grId" /></td>
			<td><s:property value="grDesc" /></td>
			<td><img title="Borrar grant" alt="Borrar grant" src='<s:url value="/css/images/delete.png"/>' style="cursor: pointer" onclick="borrarGrant('<s:property value="grId" />')"></td>
		</tr>
		</s:iterator>
		<tr>
			<td><input name="grant.grId" id="grant_grId" class="input-medium" value="" maxlength="10" /></td>
			<td><input name="grant.grDesc" id="grant_grDesc" class="input-medium" value=""  maxlength="40"/></td>
			<td><img title="Add grant" alt="Add grant" src='<s:url value="/css/images/add.png"/>' style="cursor: pointer" onclick="nuevoGrant()"></td>
		</tr>
	</tbody>
</table>
</s:form>