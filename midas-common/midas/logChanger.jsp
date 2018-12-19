<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
        <!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <sb:head/>
    </head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>

        <s:form action="logChanger" id="formLogChanger "namespace="/" theme="bootstrap" cssClass="form-horizontal"
                label="Cambio de nivel de log">

            <s:select list = "loggers" name = "logName" id="logName" label = "Logger" headerKey="" 
                      headerValue = "-- Seleccione logger --"  tooltip="Seleccione un log"/>

            <s:textfield label="Nivel" name="logLevel" tooltip="Nivel configurado en BBDD" readonly="true"  />

            <div class="row-fluid">
                <div class="span12 offset2">  
                    <s:submit cssClass="btn" value="Cambiar"/>
                </div>
            </div>

        </s:form>
    </body>
</html>