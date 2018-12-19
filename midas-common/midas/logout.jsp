<%@ taglib prefix="s" uri="/struts-tags"%>

<body class="login-page">
<div class="login-container">
	<div class="login-branding">
		<a href="index.html"><img src='<s:url value="/images/logo.png" />' alt="Bigan" title="Bigan"></a>
	</div>
	<div class="login-content">
		<h2><strong>¡Hasta luego!</strong>, gracias por utilizar el portal de BigAn</h2>

		<s:form action="index" id="formInicionSesion" namespace="/">
			<div class="form-group">
				<s:submit cssClass="btn btn-primary btn-block" value="Iniciar sesión" name="" type="submit" />
			</div>
		</s:form>
	</div>
</div>


<script type="text/javascript"
	src='<s:url value="/js/jquery.jsonp-2.1.4.js"/>'></script>
