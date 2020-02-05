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
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
 		<link href="<s:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
<!-- 		<link href="<s:url value="/css/mouldifi-core.css"/>" rel="stylesheet">  -->
 		<%= "<link href='" + request.getContextPath() + "/css/" + es.aragon.midas.config.EnvProperties.getProperty("midas.style") + "' rel='stylesheet'>" %>
		<link href="<s:url value="/css/mouldifi-forms.css"/>" rel="stylesheet"> 
		<link href="/js/jquery-ui/themes/smoothness/jquery-ui.css" rel="stylesheet">
		<link href="/cdn/css/midas.css"  rel="stylesheet" type="text/css">
		<link type="text/css" href="/cdn/css/bigan.css" rel="stylesheet"> 



		<script src="<s:url value="/js/jquery.min.js"/>"></script>
		<script src="<s:url value="/js/bootstrap.min.js"/>"></script>
		<script src="<s:url value="/js/jquery-ui/jquery-ui.js"/>"></script>
		<script src="<s:url value="/js/plugins/blockui-master/jquery.blockUI.js"/>"></script>
		<script src="<s:url value="/js/plugins/metismenu/jquery.metisMenu.js"/>"></script>

  		<script src="<s:url value="/cdn/js/highcharts/highcharts.js"/>"></script>
 		<script src="<s:url value="/cdn/js/highcharts/highcharts-more.js"/>"></script>
 		<script src="<s:url value="/cdn/js/highcharts/modules/exporting.js"/>"></script>
 		<script src="<s:url value="/cdn/js/d3/d3.v4.min.js"/>"></script>
   
	    
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


<body oncontextmenu="return true;">

<!-- Page container -->
<div class="page-container height100 can-resize">

	<!-- Page Sidebar -->
	<div class="page-sidebar">
	
  	<div class="sidebar-fixed">
	
		<!-- Site header  -->
		<header class="site-header">
		  <div class="site-logo"><a href="index.html"><img src="<s:url value="/images/logo.png"/>" 
		  	alt="<tiles:insertAttribute name="title" ignore="true" />" 
		  	title="<tiles:insertAttribute name="title" ignore="true" />"></a></div>
		  <div class="sidebar-collapse hidden-xs"><a class="sidebar-collapse-icon" href="#"><i class="icon-menu"></i></a></div>
		  <div class="sidebar-mobile-menu visible-xs"><a data-target="#side-nav" data-toggle="collapse" class="mobile-menu-icon" href="#"><i class="icon-menu"></i></a></div>
		</header>
		<!-- /site header -->
		
		<!-- Main navigation -->
		<ul id="side-nav" class="main-menu navbar-collapse collapse">

	        <tiles:insertAttribute name="menu" ignore="true"/>			
		</ul>
	</div>
		<!-- /main navigation -->		
  </div>
  <!-- /page sidebar -->
  
  <!-- Main container -->
  <div class="main-container gray-bg height100">
  
		<!-- Main header -->
<!-- 		<nav class="navbar navbar-fixed-top"> -->
		<div class="main-header row">
		  <div class="col-sm-6 col-xs-7">
		  
			<!-- User info -->
			<ul class="user-info pull-left">          
			  <li class="profile-info dropdown"><a data-toggle="dropdown" class="dropdown-toggle" href="#" aria-expanded="false"> 
			  <!-- <img width="44" class="img-circle avatar" alt="" src="images/man-3.jpg">  -->
							<s:property value="#session.userObject.name" /> 
		                    <s:property value="#session.userObject.lastname1" /> 
		                    <s:property value="#session.userObject.lastname2" />	
		      <span class="caret"></span></a>
			  
				<!-- User action menu -->
				<ul class="dropdown-menu">
				  
				  <li><a href="#/" data-toggle="modal" data-target="#modal-1"><i class="icon-user"></i>Mi perfil</a></li>
				 <!--  <li><a href="#/"><i class="icon-mail"></i>Mensajes</a></li>
				  <li><a href="#"><i class="icon-clipboard"></i>Tareas</a></li> -->
				  <li class="divider"></li>
				 <!--  <li><a href="#"><i class="icon-cog"></i>Ajustes de cuenta</a></li>  -->
				  <li><a href="<s:url value="logout.action"/>"><i class="icon-logout"></i>Cerrar sesión</a></li>
				</ul>
				<!-- /user action menu -->
				
			  </li>
			</ul>
			<!-- /user info -->
			<!-- Proyectos Info -->
			
			<ul class="user-info pull-left">          
			  <li class="profile-info dropdown"><a data-toggle="dropdown" class="dropdown-toggle" href="#" aria-expanded="false"> 
			  <!-- <img width="44" class="img-circle avatar" alt="" src="images/man-3.jpg">  -->
						<i class="icon-clipboard"></i><s:property value="#session.main_project" /> 
		      <span class="caret"></span></a>
			  
				<!-- Project action menu -->
				<ul class="dropdown-menu">
				<s:iterator value="#session.projectListObject" var="project">				  
				  <li>
				  	<a href="<s:url action="changeProject.action" >
						    <s:param name="pj"><s:property value="#project"/></s:param>
						</s:url>">
				  		<i class="icon-clipboard"></i><s:property value="#project"/>
				  	</a>
				  </li>				 
				 </s:iterator>
				</ul>				
			  </li>
			</ul>
			<!-- /Proyectos Info -->
		  </div>
		  
		  <div class="col-sm-6 col-xs-5">
			<div class="pull-right">
				<!-- User alerts -->
				<ul class="user-info pull-left">
				
				  <!-- Notifications -->
				  <s:set name="notActive" value="notificationsActive"/>
				  <s:if test="%{#notActive=='true'}">
			          <tiles:insertAttribute name="notifications" ignore="true"/>			
				  </s:if>
				  <!-- /notifications -->
				  
				  <!-- Messages -->
				  <s:set name="msgActive" value="messagesActive"/>
				  <s:set name="cauFormActive" value="cauFormActive"/>				  
				  <s:if test="%{#msgActive=='true'}">
			          <tiles:insertAttribute name="messages" ignore="true"/>			
				  </s:if>
				  <s:if test="%{#cauFormActive=='true'}">
			          <tiles:insertAttribute name="cauIcon" ignore="true"/>			
				  </s:if>				  
				  <!-- /messages -->
				  
				</ul>
				<!-- /user alerts -->
				
			</div>
		  </div>
		</div>
		<!-- /main header -->
		
		<!-- Main content -->
		<div class="main-content height100">
		
			<s:set name="dbNumber" value="dbNumber"/>

			<s:if test="%{#dbNumber != 100 }">
				<tiles:insertAttribute name="breadcrumb" ignore="true"/>
			</s:if>
			
			<h1 class="page-title"><s:property value="title"/></h1>
  		 
  		 	<tiles:insertAttribute name="body" />

			
			
			<!-- Footer -->
  		 	
  		 	<tiles:insertAttribute name="footer" ignore="true" />
  		 	
  		 	<!-- /footer -->
		
	  </div>
	  <!-- /main content -->
	  
  </div>
  <!-- /main container -->
  
</div>
<!-- /page container -->
		            
<!--Load JQuery-->

<script src="/cdn/js/midas/midas.js"></script>
<script src="/cdn/js/knockout.js"></script>	
<script src="/cdn/js/midas/mid-message.js"></script>
<script src="/cdn/js/bigan/bigan.js"></script>
<script>

window.open = function (url, name, features, replace) {
    $('#iframe').attr('src', url);
}

function resizeIframe(obj) {
  obj.style.height = (obj.contentWindow.document.body.scrollHeight) + 'px';
}


// Set sender in message form
CauMessageForm.cauMessage.sender('<s:property value="#session.userObject.userName" />');

</script>

<tiles:insertAttribute name="script" ignore="true"/>
 
 
 
<!--Basic Modal-->
<div id="modal-1" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><s:property value="#session.userObject.name" /> <s:property value="#session.userObject.lastname1" /> <s:property value="#session.userObject.lastname2" /></h4>
      </div>
      <div class="modal-body">
              <p><strong>Centro:</strong> <s:property value="#session.userObject.infoUser.cenName" /> </p>		                     
              <p><strong>Unidad:</strong> <s:property value="#session.userObject.infoUser.gfhName" /> </p>
              <p><strong>CIAS:</strong> <s:property value='#session.userObject.getContextValues("CIAS")' /> </p>
              <p><strong>Categor&iacute;a:</strong> <s:property value="#session.userObject.infoUser.catrName" /> </p>
              		                     
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--End Basic Modal--> 

<!-- cau Form -->
<s:if test="%{#cauFormActive=='true'}">
       <tiles:insertAttribute name="cauForm" ignore="true"/>			
</s:if>
<!-- /cau Form -->


</body>
</html>
