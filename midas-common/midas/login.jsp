<%@ taglib prefix="s" uri="/struts-tags"%>

<!--<script type="text/javascript">
	$(document).ready(function() {
		// binds form submission and fields to the validation engine
		$("#login").validationEngine();
	});
</script>
-->







<body class="login-page">
<div class="login-container">
	<div class="login-branding">
		<a href="index.html"><img src='<s:url value="/images/logo.png" />' alt="Identificacion" title="Identificacion"></a>
	</div>
	<div class="login-content">
		<h2><strong>Bienvenido</strong>, por favor, identifícate</h2>
		<s:form action="login" id="formLogin" namespace="/">
			<div class="form-group">
				<input type="text" placeholder="Usuario" class="form-control" 
						onkeyup="textToUpper(this);" id="username" size="30" autocomplete="off" name="username" >
			</div>                        
			<div class="form-group">
				<input type="password" placeholder="Contraseña" class="form-control" 
					   size="30" value="" id="password" name="password">
			</div>
			
			<!-- // remember me 
			<div class="form-group">
				 <div class="checkbox checkbox-replace">
					<input type="checkbox" id="remeber">
					<label for="remeber">Recuérdame</label>
				  </div>
			 </div> 
			 -->

				<s:if test="hasActionErrors()">
					<div class="errors">
						<s:actionerror escape="false" />
					</div>
				</s:if>

			<div class="form-group">
				<s:submit cssClass="btn btn-primary btn-block" value="Acceder" name="" type="submit" />
				<!-- <button class="btn btn-primary btn-block">Entrar</button> -->
			</div>

			<!--	// Recover password 
				<p class="text-center"><a href="forgot-password.html">¿Olvidaste tu contraseña?</a></p> 
			-->


			<!--  // Acceso mediante certificado digital -->
			<div id="mensaje" style="padding: 20px" class="centrado tarjeta">
				<div class="mensaje">Buscando certificado ...</div>
				<input value="Reintentar" name="btnCardRetry" id="btnCardRetry"
					type="button" class="btn" onclick="loginCert()"> <input
					value="Acceso sin tarjeta" name="btnUserPwd" id="btnUserPwd"
					type="button" class="btn-prymary" onclick="loginUP()">
			</div>	
			<s:hidden name="urlGUIA" id="urlGUIA" value="%{#request.URLAuthGUIACard}" />		
			<input type="hidden" name="urlAction" id="action" value="${requestScope['javax.servlet.forward.servlet_path']}?${requestScope['javax.servlet.forward.query_string']}" />
    
		</s:form>
	</div>
</div>


<script type="text/javascript" src='<s:url value="/js/jquery.jsonp-2.4.0.min.js"/>'></script>

<script type="text/javascript" src='<s:url value="/js/midas/mid-loginGUIA.js"/>'></script>

<!--<script type="text/javascript">
	$(document).ready(function() {
		// binds form submission and fields to the validation engine
		$("#login").validationEngine();
	});
</script>
-->