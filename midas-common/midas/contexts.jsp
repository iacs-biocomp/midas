<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src='<s:url value="/js/mid-contexts.js"/>'></script>

<s:form method="post" id="formContexts" action="list" enctype="multipart/form-data" theme="bootstrap" cssClass="form-horizontal" namespace="/">
<h4>Contexts</h4>
<table class="table table-striped table-hover" id="tablaContexts">
	<thead>
		<tr>
			<th>CONTEXT ID</th>
			<th>CONTEXT KEY</th>
			<th>CONTEXT VALUE</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="contexts">
		<tr>
			<td><s:property value="cxId" /></td>
			<td><s:property value="cxKey" /></td>
			<td><s:property value="cxValue" /></td>
			<td><img title="Borrar context" alt="Borrar context" src='<s:url value="/css/images/delete.png"/>' style="cursor: pointer" onclick="borrarContext('<s:property value="cxId" />')"></td>
		</tr>
		</s:iterator>
		<tr>
			<td><input name="context.cxId" id="context_cxId" class="input-medium numbersOnly" value="" /></td>
			<td><input name="context.cxKey" id="context_cxKey" class="input-medium" value=""  maxlength="10"/></td>
			<td><input name="context.cxValue" id="context_cxValue" class="input-medium" value="" maxlength="20"/></td>
			<td><img title="Add context" alt="Add cxKey" src='<s:url value="/css/images/add.png"/>' style="cursor: pointer" onclick="nuevoContext()"></td>
		</tr>
	</tbody>
</table>
</s:form>