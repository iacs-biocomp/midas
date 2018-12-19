<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">

	/* <-------- Redirige a la ruta que se selecciona en el path*/
	function redirigir(ruta){
		var searchPath = document.getElementById("path").value;
		var numSearch = searchPath.indexOf(ruta) + ruta.length;
		document.getElementById("path").value = searchPath.substring(0, numSearch);
		document.forms["pathForm"].submit();
	}
	
	/* <-------- Accede a la ruta de un fichero seleccionado en el listado*/
	function irRuta(tipo, ruta){
		document.getElementById("path").value += ruta;
		document.forms["pathForm"].submit();

		//Si el archivo es un fichero se elimina el nombre del mismo de la ruta una vez descargado
		if(tipo == "F"){
			var searchPath = document.getElementById("path").value;
			var resPath = searchPath.length - ruta.length;
			document.getElementById("path").value = searchPath.substring(0, resPath);
		}
	}

	/* <-------- Comprueba que el fichero que se va a subir no es nulo o vacío*/
	function comprobar(){
		if(document.getElementById("uploadFile").value != "" && document.getElementById("uploadFile").value != null){
			document.getElementById("submitUpload").disabled = false;
		}
	}
</script>

<style>
	.direct {
		font-size: 14px;
		font-weight: bold;
		text-decoration: underline;
		color: #5F95D7;
		cursor: pointer;
	}
	.arrow {
		font-size: 24px;
	}
	.col3 {
		padding-right: 0px;
		padding-left: 0px;
		float: left;
		padding-bottom: 0px;
		margin: 15px 0px;
		width: 100%;
		padding-top: 0px;
		list-style-type: none
	}
		 
	.col3 li{
		padding-right: 2px;
		display: inline;
		padding-left: 2px;
		float: left;
		padding-bottom: 2px;
		width: 48%;
		padding-top: 2px
	}
</style>

<div class="row-fluid">
	<div class="span10 offset1">
		<!-- Formulario de navegación en el path -->
		<s:form id="pathForm" action="listFiles">
			<s:hidden id="path" name="path"/>		
		</s:form>
		
		<h4>Directorio de Archivos</h4>
		<hr/>
		
		<!-- Pinta la ruta y permite interacturar con ella -->
		<s:iterator value="redirect">
			<span class="direct" onclick="redirigir('<s:property/>')"> <s:property/> </span> &#10097;
		</s:iterator>
		<div class="arrow">&#10551;</div>

		<!-- Pinta los ficheros y directorios de la ruta en la que nos encontramos -->
		<ul class="col3">
		<s:iterator value="three">
			<li>
				<a title="Tamaño: <s:property value="size"/> Kb" href='#' onclick="irRuta('<s:property value="tipo"/>','<s:property value="nombre"/>')">
					<s:if test='tipo == "D"'>
						<img src="./css/icons/folder_32.png" height="24" width="24"/>
					</s:if>
					<s:else>
						<img src="./css/icons/page_text_32.png" height="24" width="24"/>
					</s:else>
				 	<s:property value="nombre"/>
				</a>
			</li>
		</s:iterator>
		</ul>
	</div>
	
	<div class="span10 offset1">
		<hr/>
		<!-- Formulario de subida de ficheros al servidor -->
		<s:form id="uploadForm" action="uploadFiles" enctype="multipart/form-data" method="post">
			<s:hidden id="path" name="path"/>
			<div class="row-fluid">
				<div class="span6">
					<s:file name="uploadFile" id="uploadFile" onchange="comprobar()"/>
				</div>
				<div class="span6">
					<s:submit value="Subir Archivo" id="submitUpload" disabled="true"/>
				</div>
			</div>
		</s:form>
	</div>
</div>

