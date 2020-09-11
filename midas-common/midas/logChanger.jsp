<%@ taglib prefix="s" uri="/struts-tags" %>

<!--    <as:actionerror theme="bootstrap"/>
        <as:actionmessage theme="bootstrap"/>
        <as:fielderror theme="bootstrap"/>
 -->
        <s:form action="changeLog" id="formLogChanger"  cssClass="form-horizontal"
                label="Cambio de nivel de log">

            <s:select list = "loggers" name = "logName" id="logName" label = "Logger" headerKey="" 
                      headerValue = "-- Seleccione logger --"  tooltip="Seleccione un log"/>

           <!-- <s:textfield label="Nivel" name="logLevel" tooltip="Nivel configurado en BBDD" readonly="true"  /> -->||

            <select name = "logLevel" id="logLevel" label = "Level" 
                      caption = "-- Seleccione nivel --"  tooltip="Seleccione un nivel de log">
                      <option value="trace">trace</option>
                      <option value="debug">debug</option>
                      <option value="info">info</option>
                      <option value="warn">warn</option>
                      <option value="error">error</option>
            </select>


            <div class="row-fluid">
                <div class="span12 offset2">  
                    <s:submit cssClass="btn" value="Cambiar"/>
                </div>
            </div>

			<s:url id="fileDownload" namespace="/" action="downloadLog"></s:url>
			
			<h2>Download file - <s:a href="%{fileDownload}">log.txt</s:a>
			</h2>


        </s:form>
