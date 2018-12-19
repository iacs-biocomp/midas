<%@ taglib prefix="s" uri="/struts-tags"%>

Email: <s:textfield name="userNew.userName" id="userNew_userName"
				label="UserName" readonly="%{readOnly}" />
IDD: <s:textfield name="userNew.idd" id="userNew_idd" label="IDD"
				onkeyup="textToUpper(this);" readonly="%{readOnly}" />
Nombre: <s:textfield name="userNew.name" id="userNew_name" label="Nombre"
				onkeyup="textToUpper(this);"  readonly="%{readOnly}"/>
Primer Apellido: <s:textfield name="userNew.lastname1" id="userNew_lastname1"
				label="Apellido 1" onkeyup="textToUpper(this);" readonly="%{readOnly}" />
Segundo Apellido: <s:textfield name="userNew.lastname2" id="userNew_lastname2"
				label="Apellido 2" onkeyup="textToUpper(this);" readonly="%{readOnly}" />