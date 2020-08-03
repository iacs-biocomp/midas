<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="Midas - Mouldifi. A fully responsive, HTML5 based admin theme">
		<meta name="keywords" content="Responsive, HTML5, admin theme, business, professional, Mouldifi, web design, CSS3">
	
		<title><tiles:insertAttribute name="title" ignore="true" /></title>
	
		<!-- Site favicon -->
		<link rel='shortcut icon' type='image/x-icon' href='<s:url value="/images/favicon.ico"/>' />
		<!-- /site favicon -->
		
	
		<link href="<s:url value="/css/entypo.css"/>" rel="stylesheet">
		<link href="<s:url value="/css/font-awesome.min.css"/>" rel="stylesheet">
		<link href="<s:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
		<%= "<link href='" + request.getContextPath() + "/css/" + es.aragon.midas.config.EnvProperties.getProperty("midas.style") + "' rel='stylesheet'>" %>
		<link href="<s:url value="/css/mouldifi-forms.css"/>" rel="stylesheet">

		<tiles:insertAttribute name="appstyle" ignore="true"/>
	    
		<!--Load JQuery-->
	    <script type="text/javascript" src="<s:url value="/js/jquery.min.js"/>"></script>
	    <script type="text/javascript" src="<s:url value="/js/bootstrap.min.js"/>"></script>
		<script src="<s:url value="/js/plugins/metismenu/jquery.metisMenu.js"/>"></script>
	    <script type="text/javascript" src="/cdn/js/midas/midas.js"></script>	 
	    
	    
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	      <script src="js/ie9/html5shiv.min.js"></script>
	      <script src="js/ie9/respond.min.js"></script>
	<![endif]-->
	
	<!--[if lte IE 8]>
		<script src="js/plugins/flot/excanvas.min.js"></script>
	<![endif]-->
	
	
	</head>







    <body>
        <tiles:insertAttribute name="body" />
	</body>
</html>
