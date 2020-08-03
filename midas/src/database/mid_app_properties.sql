INSERT INTO mid_app_properties (id,value,description) VALUES 
('midas.bduws.url','http://miv-jeap-01.salud.psis:8080/bduWS/CatalogoBDUWebService?WSDL','URL del web Service de consulta a BDU (es.aragon.midas.ws.bdu.BduQuery)')
,('midas.fsmPath',NULL,'Ruta para almacenar los diagramas de estados de fsm')
,('midas.ldap.baseDN','DC=salud,DC=dga,DC=es','DN utilizado para autenticar en el AD')
,('midas.ldap.server','ldap://172.25.1.66:389','URL de servidor LDAP')
,('midas.guia.appName','BIGAN','Nombre de la aplicacion origen/destino para la autenticacion por token de GUIA')
,('midas.messages.active','false','Indica si se utiliza o no el sistema de mensajeria')
,('midas.debugmode','true','Mode de depuración para varias opciones comunes')
,('midas.validateLocal','false','Indica si el usuario que accede debe estar previamente registrado en base de datos (true), o si los datos del usuario, una vez autenticado, son insertados autom�ticamente en la base de datos (false).')
,('midas.staticContent.path','/app/portal/','ruta del conteniodo estático')
,('midas.authenticate','true','Indica si se debe authenticar a los usuarios o la aplicacion es de acceso pú
blico')
;
INSERT INTO mid_app_properties (id,value,description) VALUES 
('midas.authenticateCert','false','Indica si la aplicación soporta autenticación por certificado digital.')
,('midas.backgroundClass','fondo_pre','Fondo que muestra la aplicación: ''fondo_pre'' , ''fondo_pro''')
,('midas.guia.card','https://webapps-pre.salud.psis/https/readECard.php','URL de redirección para solicitud de tarjeta criptográfica')
,('midas.jasperPath',NULL,'Ruta donde se almacenan los jasper de la aplicación')
,('midas.ldap.dominio','@salud.dga.es','Dominio que se añadirá a los DN en la autenticación por LDAP')
,('midas.ldap.portal','https://servicios3.aragon.es/integracionidMseg/altaUsuario?accion=20','Direccion para el cambio de contraseña en el AD')
,('midas.ldap.validUser','admin-dep@salud.dga.es','Usuario válido para autenticar contra el AD')
,('midas.guia.validationURL','http://webapps.salud.aragon.local/ticketData.php','URL de validación de ticket en GUIA')
,('midas.guia.authURL','http://webapps.salud.aragon.local/guiaws.php','URL de autenticación contra GUIA')
,('midas.ldap.validPassword','SZaC8t78hftZP3tG0IUjmw==','Password válido para autenticar contra el AD')
;
INSERT INTO mid_app_properties (id,value,description) VALUES 
('midas.rules.path','','Ruta donde se almacenan los ficheros de reglas de Drools')
,('app.title','BIGAN gestión clínica',NULL)
,('app.banner','Descubre el Big Data sanitario de Aragón diseñado para profesionales como tú','')
,('midas.notifications.active','false','Indica si se utiliza o no el sistema de notificaciones')
,('midas.cauform.active','false','Indica si se utiliza o no el formulario de notificación de incidencias')
,('bigan.shiny.server','https://bigangestion.iacs.es/shiny/widgets','Dirección del servidor Shiny / http://develop.bigan.eu:3838/widgets')
,('midas.authenticate.ignorePass','false','Indica si tiene que validar la contraseña contra el AD')
,('midas.guia.getInfoUser','true','Determina si capturamos o no la informacion del usuario desde GUIA')
,('bigan.node.server','http://develop.bigan.eu/node','Direccion del servidor node.js')
,('midas.security.key','$1$7MPcDN4p$/kOQiwzpv3GQJ1UU8sCOA0',NULL)
;
INSERT INTO mid_app_properties (id,value,description) VALUES 
('midas.database.version','45',NULL)
;