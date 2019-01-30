<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><tiles:insertAttribute name="title" ignore="true" /></title>
        <link rel="stylesheet" href="<s:url value="/css/validationEngine.jquery.css"/>" type="text/css"/>    
        <link rel="stylesheet" href="<s:url value="/css/bootstrap.min.css"/>" type="text/css"/>
        <link rel="stylesheet" href="<s:url value="/css/midas.css"/>" media="screen" type="text/css"  />
        <script type="text/javascript" src="<s:url value="/js/jquery.min.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.maskedinput.min.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.validationEngine-es.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.validationEngine.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.datePicker.js"/>"></script>
        <script type="text/javascript" src="/cdn/js/midas.js"></script>
    <s:head/>
    </head>
    <body>
        <div id="fondo_cabecera"></div>
        <div id="cabecera">
            <div id="logo_aragon"></div>
            <h1 class="midas"><tiles:insertAttribute name="title" ignore="true" /></h1>
            <div id="opciones">
                <span class="nombre"><s:property value="#session.userObject.name" /> 
                    <s:property value="#session.userObject.lastname1" /> 
                    <s:property value="#session.userObject.lastname2" />
                </span> | <span><a href="<s:url action="index" namespace="/" />">Cerrar sesión</a></span>
            </div>
            <div id="menu"><tiles:insertAttribute name="menu" ignore="true"/></div>
        </div>
            
        <div id="contenedor">
            <div id="contenido">
                <div class="row-fluid">
                    <div class="span2" id="midasTray"> 
                        <tiles:insertAttribute name="tray" ignore="true"/>
                    </div>
                    <div class="span9" id="midasBody">
                        <tiles:insertAttribute name="body" />
                    </div>
                </div>
            </div>
        </div>
</body>
</html>
