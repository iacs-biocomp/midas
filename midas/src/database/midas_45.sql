
CREATE SEQUENCE mid_audit_au_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
;

CREATE SEQUENCE mid_audit_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
;

CREATE SEQUENCE mid_log_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
;

CREATE SEQUENCE mid_login_lg_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
;

CREATE SEQUENCE mid_messages_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 28
;

CREATE SEQUENCE mid_messages_id_seq1
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
;


CREATE SEQUENCE mid_notification_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 3
;








CREATE TABLE mid_app_properties (
	id varchar(30) NOT NULL,
	value varchar(100) NULL,
	description varchar(200) NULL,
	CONSTRAINT mid_app_properties_pkey PRIMARY KEY (id)
);

CREATE TABLE mid_audit (
	au_id int4 NOT NULL,
	au_fecha timestamp NULL,
	au_user varchar(32) NULL,
	au_idd varchar(12) NULL,
	au_oper varchar(3) NULL,
	au_entidad varchar(32) NULL,
	au_pk varchar(20) NULL,
	au_data varchar(255) NULL,
	CONSTRAINT mid_audit_pkey PRIMARY KEY (au_id)
);



CREATE TABLE portal.mid_audit_oper (
	op_code varchar(3) NULL,
	op_desc varchar(50) NULL
);


CREATE TABLE mid_contexts (
	cx_id int4 NOT NULL,
	cx_key varchar(10) NULL,
	cx_value varchar(20) NULL,
	CONSTRAINT mid_contexts_pkey PRIMARY KEY (cx_id)
);


CREATE TABLE mid_fsm_events (
	ev_id int4 NOT NULL,
	ev_name varchar(16) NULL,
	ev_icon varchar(20) NULL,
	CONSTRAINT mid_fsm_events_pkey PRIMARY KEY (ev_id)
);


CREATE TABLE mid_fsm_states (
	st_id int4 NOT NULL,
	st_name varchar(16) NULL,
	st_color varchar(7) NULL,
	CONSTRAINT mid_fsm_states_pkey PRIMARY KEY (st_id)
);



CREATE TABLE mid_fsm_transitions (
	tr_id int4 NOT NULL,
	tr_from int4 NOT NULL,
	tr_to int4 NOT NULL,
	tr_event int4 NOT NULL,
	tr_fsm varchar(16) NOT NULL,
	tr_condition varchar(32) NULL,
	tr_grant varchar(10) NULL,
	CONSTRAINT mid_fsm_transitions_pkey PRIMARY KEY (tr_id)
);



CREATE TABLE mid_grants (
	gr_id varchar(20) NOT NULL,
	gr_desc varchar(40) NULL,
	CONSTRAINT mid_grants_pkey PRIMARY KEY (gr_id)
);



CREATE TABLE mid_guia (
	app_dst varchar(20) NOT NULL,
	url_dst varchar(1000) NULL,
	secret varchar(3000) NULL,
	CONSTRAINT mid_guia_pkey PRIMARY KEY (app_dst)
);


CREATE TABLE mid_login (
	lg_id int4 NOT NULL,
	lg_date timestamp NULL,
	lg_user varchar(20) NULL,
	lg_ip varchar(15) NULL,
	lg_cod varchar(8) NULL,
	CONSTRAINT mid_login_pkey PRIMARY KEY (lg_id)
);


CREATE TABLE mid_login_msg (
	lm_codigo varchar(8) NOT NULL,
	lm_descripcion varchar(100) NULL,
	CONSTRAINT mid_login_msg_pkey PRIMARY KEY (lm_codigo)
);
    


CREATE TABLE mid_menu (
	mn_id int2 NOT NULL,
	mn_padre int2 NULL,
	mn_texto varchar(100) NULL,
	mn_link varchar(100) NULL,
	mn_grantreq varchar(10) NULL,
	mn_target varchar(10) NULL,
	mn_style varchar(25) NULL,
	CONSTRAINT mid_menu_pkey PRIMARY KEY (mn_id)
);



CREATE TABLE mid_messages (
	id int4 NOT NULL,
	sender varchar(32) NULL,
	receiver varchar(32) NULL,
	message text NULL,
	send_date date NULL,
	read_date date NULL,
	status varchar(1) NULL,
	response_to int4 NULL,
	subject varchar(100) NULL,
	CONSTRAINT mid_messages_pkey PRIMARY KEY (id)
);



CREATE TABLE mid_notification (
	id int4 NOT NULL,
	receiver varchar(32) NOT NULL,
	message text NULL,
	send_date date NULL,
	read_date date NULL,
	status varchar(1) NOT NULL,
	"style" varchar(25) NULL,
	CONSTRAINT mid_notification_pkey PRIMARY KEY (id)
);

COMMENT ON COLUMN mid_notification.read_date IS 'S=Sent, R=Read, D=Deleted';

CREATE TABLE mid_reports (
	id varchar(30) NOT NULL,
	value varchar(100) NULL,
	description varchar(200) NULL,
	CONSTRAINT mid_reports_pkey PRIMARY KEY (id)
);



CREATE TABLE mid_role_context (
	role_id varchar(20) NOT NULL,
	cx_id int4 NOT NULL,
	CONSTRAINT mid_role_context_pkey PRIMARY KEY (role_id, cx_id)
);


CREATE TABLE mid_rolegrants (
	rg_role varchar(20) NOT NULL,
	rg_grant varchar(20) NOT NULL,
	CONSTRAINT mid_rolegrants_pkey PRIMARY KEY (rg_role, rg_grant)
);


CREATE TABLE mid_roles (
	role_id varchar(20) NOT NULL,
	role_desc varchar(40) NULL,
	role_order int4 NULL,
	CONSTRAINT mid_roles_pkey PRIMARY KEY (role_id)
);



CREATE TABLE mid_roles_ldap (
	role_ldap varchar(50) NOT NULL,
	role_id varchar(20) NOT NULL,
	role_desc varchar(40) NULL,
	CONSTRAINT mid_roles_ldap_pkey PRIMARY KEY (role_ldap)
);


CREATE TABLE mid_user_context_auto (
	user_name varchar(32) NOT NULL,
	cx_id int4 NOT NULL
);


CREATE TABLE mid_user_context_manual (
	user_name varchar(32) NOT NULL,
	cx_id int4 NOT NULL,
	CONSTRAINT mid_user_context_manual_pkey PRIMARY KEY (user_name, cx_id)
);


CREATE TABLE mid_userroles (
	ur_name varchar(32) NOT NULL,
	ur_role varchar(20) NOT NULL,
	CONSTRAINT mid_userroles_pkey PRIMARY KEY (ur_name, ur_role),
	CONSTRAINT mid_userroles_mid_roles_fk FOREIGN KEY (ur_role) REFERENCES mid_roles(role_id),
	CONSTRAINT mid_userroles_mid_users_fk FOREIGN KEY (ur_name) REFERENCES mid_users(user_name)
);


CREATE TABLE mid_users (
	user_name varchar(32) NOT NULL,
	active varchar(1) NULL,
	idd varchar(15) NULL,
	last_login timestamp NULL,
	lastname1 varchar(25) NULL,
	lastname2 varchar(25) NULL,
	"name" varchar(25) NULL,
	pwd varchar(100) NULL,
	email varchar(255) NULL,
	auth_mode bpchar(1) NOT NULL DEFAULT 'S'::bpchar,
	CONSTRAINT mid_users_pkey PRIMARY KEY (user_name)
);




CREATE TABLE db_dashboards (
	id int8 NOT NULL,
	description varchar(150) NULL,
	"template" varchar(50) NULL,
	"grant" varchar(20) NULL,
	script varchar(1024) NULL,
	CONSTRAINT db_dashboards_pkey PRIMARY KEY (id)
);



CREATE TABLE db_frame_type (
	id varchar(15) NOT NULL,
	description varchar(150) NULL,
	snippet varchar(100) NULL,
	js varchar(100) NULL,
	data_snippet varchar(100) NULL,
	data_format varchar(20) NULL,
	renderer varchar(100) NULL,
	CONSTRAINT db_frame_type_pkey PRIMARY KEY (id)
);


CREATE TABLE db_frames (
	id serial NOT NULL,
	dashboard_id int8 NULL,
	description varchar(150) NULL,
	"order" int4 NULL,
	suborder int4 NULL,
	frame_type varchar(15) NULL,
	"path" varchar(150) NULL,
	query_id int8 NULL,
	"grant" varchar(20) NULL,
	title varchar(100) NULL,
	"comment" text NULL,
	js varchar(100) NULL,
	CONSTRAINT db_frames_pkey PRIMARY KEY (id),
	CONSTRAINT db_frames_db_dashboards_fk FOREIGN KEY (dashboard_id) REFERENCES db_dashboards(id),
	CONSTRAINT db_frames_db_frame_type_fk FOREIGN KEY (frame_type) REFERENCES db_frame_type(id)
);


CREATE TABLE db_queries (
	id int8 NOT NULL,
	description varchar(150) NULL,
	query text NULL,
	columns varchar(200) NULL,
	CONSTRAINT db_queries_pkey PRIMARY KEY (id)
);


























-- Views

CREATE OR REPLACE VIEW mid_user_context
AS SELECT DISTINCT userroles.user_name,
    userroles.cx_id
   FROM ( SELECT mid_user_context_auto.user_name,
            mid_user_context_auto.cx_id
           FROM mid_user_context_auto
        UNION
         SELECT mid_user_context_manual.user_name,
            mid_user_context_manual.cx_id
           FROM mid_user_context_manual) userroles;





-- Procedures

CREATE OR REPLACE FUNCTION crypt_pwd_function()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
BEGIN
  IF NEW.pwd IS NOT NULL then
    if new.auth_mode = 'S' then
    	new.pwd = null;
    else -- auth_mode = 'L'
      if old.pwd is null then
      	-- Estamos creando usuario nuevo, o cambiando de Server a Local
      	NEW.pwd := public.crypt(NEW.pwd::text, gen_salt('bf'));
      else 
        -- si el nuevo pwd es el mismo que había, o el mismo sin cifrar, mantenemos el valor actual
		if public.crypt(new.pwd::text,old.pwd::text)::varchar = old.pwd or new.pwd = old.pwd then 
			new.pwd = old.pwd;
		else 
			-- Estamos actualizando el password. Cambiamos por el nuevo valor.
			new.pwd = public.crypt(NEW.pwd::text, public.gen_salt('bf'));
     	end if;
      end if;
    end if;
  else
  	-- Autenticación local y no hay password
  	if new.auth_mode = 'L' then
  		new.pwd = 'ERROR';
  		raise 'Null password in local mode not alowed';
	end if;  
  END IF;
  RETURN NEW;
END$function$
;



CREATE OR REPLACE FUNCTION crypt_email_function()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
	_key	varchar(100);
BEGIN
  IF NEW.email IS NOT NULL THEN
	select mid_app_properties.value into _key from mid_app_properties where id = 'midas.security.key';
    NEW.email := encode(public.pgp_sym_encrypt(NEW.email, _key), 'base64');
  END IF;
  RETURN NEW;
END$function$
;





CREATE OR REPLACE FUNCTION decrypt_email(m character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
	DECLARE
		_key	varchar(100);
		_res	varchar(255);
	BEGIN
		select mid_app_properties.value into _key from mid_app_properties where id = 'midas.security.key';
	    select public.pgp_sym_decrypt(decode(m, 'base64'), _key) into _res;
	   return _res;
	end;
	$function$
;







-- Triggers

create trigger crypt_pwd_trg before insert or update of pwd
on mid_users
for each row
execute procedure crypt_pwd_function();



create trigger crypt_email_trg before insert or update of email
on mid_users
for each row
execute procedure crypt_email_function();












-- Permissions


ALTER TABLE mid_app_properties OWNER TO bigan;
ALTER TABLE mid_audit OWNER TO bigan;
ALTER TABLE portal.mid_audit_oper OWNER TO bigan;
ALTER TABLE mid_contexts OWNER TO bigan;
ALTER TABLE mid_fsm_events OWNER TO bigan;
ALTER TABLE mid_fsm_states OWNER TO bigan;
ALTER TABLE mid_fsm_transitions OWNER TO bigan;
ALTER TABLE mid_grants OWNER TO bigan;
ALTER TABLE mid_guia OWNER TO bigan;
ALTER TABLE mid_login OWNER TO bigan;
ALTER TABLE mid_login_msg OWNER TO bigan;
ALTER TABLE mid_menu OWNER TO bigan;
ALTER TABLE mid_messages OWNER TO bigan;
ALTER TABLE mid_notification OWNER TO bigan;
ALTER TABLE mid_reports OWNER TO bigan;
ALTER TABLE mid_role_context OWNER TO bigan;
ALTER TABLE mid_rolegrants OWNER TO bigan;
ALTER TABLE mid_roles OWNER TO bigan;
ALTER TABLE mid_roles_ldap OWNER TO bigan;
ALTER TABLE mid_user_context_auto OWNER TO bigan;
ALTER TABLE mid_user_context_manual OWNER TO bigan;
ALTER TABLE mid_userroles OWNER TO bigan;
ALTER TABLE mid_users OWNER TO bigan;
ALTER TABLE mid_user_context OWNER TO bigan;
ALTER TABLE bpr_v_bm OWNER TO bigan;
ALTER TABLE db_dashboards OWNER TO bigan;
ALTER TABLE db_frame_type OWNER TO bigan;
ALTER TABLE db_frames OWNER TO bigan;
ALTER TABLE db_queries OWNER TO bigan;
ALTER SEQUENCE mid_audit_au_id_seq OWNER TO bigan;
ALTER SEQUENCE mid_audit_seq OWNER TO bigan;
ALTER SEQUENCE mid_log_seq OWNER TO bigan;
ALTER SEQUENCE mid_login_lg_id_seq OWNER TO bigan;
ALTER SEQUENCE mid_messages_id_seq OWNER TO bigan;
ALTER SEQUENCE mid_messages_id_seq1 OWNER TO bigan;
ALTER SEQUENCE mid_notification_id_seq OWNER TO bigan;



GRANT ALL ON ALL TABLES in SCHEMA $schema TO $owner;
GRANT ALL ON ALL SEQUENCES in SCHEMA $schema TO $owner;



-- Data


INSERT INTO db_frame_type (id,description,snippet,js,data_snippet,data_format,renderer) VALUES 
('SHINY','R Shiny module','shiny.jsp',NULL,NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('INFO-PANEL','info panel box','info-panel.jsp',NULL,NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('IMAGE','Imagen','image.jsp',NULL,NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('INFO-PANEL-O','info panel box open','info-panel-o.jsp',NULL,NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('MAP_ARAGON','Mapa Aragon','map-aragon.jsp','map-aragon.js',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('HTML_BIGAN','Html Bigan con JS','basic-html.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('CIAS_SELECTOR','Cias selector','ciasSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('SANKEY','Sankey graph','sankey.jsp','sankey.js',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('BENCHMARK','Benchmark table','benchmark.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('HTML','Plain HTML insert','plain-html.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
;
INSERT INTO db_frame_type (id,description,snippet,js,data_snippet,data_format,renderer) VALUES 
('MOR-LINE','morris line chart','morris-line.jsp','','','','es.aragon.midas.dashboard.renderer.MorrisLineRenderer')
,('BASICTABLE','basic table','basic-table.jsp','datatables.js',NULL,NULL,'es.aragon.iacs.biganPortal.renderer.BasicTableRenderer')
,('HIGHCHART','Highchart Graph','highchart.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('DATE_SLIDER','Date Slider','dateSlider.jsp','','',NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('TREEMAP_STATIC','Treemap static','treemap-static.jsp','','treemap-static','treemap1','es.aragon.midas.dashboard.renderer.BasicRenderer')
,('WEEPEOPLE','WeePeople Graph','weepeople.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('WEEPEOPLEN','WeePeople Graph n colors','weepeople.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('HIGHCHARTPYR','Highchart Pyramid','highchartPyr.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('CIAS_SELECT_LEV','Cias selector with level','ciasSelectorLevel.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('DETAIL_SELECTOR','Detail selector','detailSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
;
INSERT INTO db_frame_type (id,description,snippet,js,data_snippet,data_format,renderer) VALUES 
('HIGHCHART_DUAL','Highchart Graph','highchartDual.jsp','highchartDual.js',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('MAP_ARAGON_S','Mapa Aragon Sectores','mapLine.jsp','/cdn/js/d3/topojson.v1.min.js',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('MAP_ARAGON_H','Mapa Aragon Sectores','mapLineH.jsp','/cdn/js/d3/topojson.v1.min.js',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('AGG_SELECTOR','Aggregation selector','aggSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('LEVEL_SELECTOR','Aggregation level selector','levelSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('DETAIL_H_SELECT','Detail Hospital Selector','detailHospSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
,('SECTOR_H_SELECT','Sector / Hospital Selector','sectorHospSelector.jsp','',NULL,NULL,'es.aragon.midas.dashboard.renderer.BasicRenderer')
;


INSERT INTO mid_app_properties (id,value,description) VALUES 
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
,('midas.authenticate.ignorePass','false','Indica si tiene que validar la contraseña contra el AD')
,('midas.guia.getInfoUser','true','Determina si capturamos o no la informacion del usuario desde GUIA')
,('midas.security.key','$1$7MPcDN4p$/kOQiwzpv3GQJ1UU8sCOA0',NULL)
;
INSERT INTO mid_app_properties (id,value,description) VALUES 
('midas.database.version','45',NULL)
;




INSERT INTO mid_audit_oper (op_code,op_desc) VALUES 
('VIS','Visualización datos')
,('DBS','Dashboard show')
,('ALS','Alias de usuario')
,('ALX','Fin de aliasing')
;

INSERT INTO mid_grants (gr_id,gr_desc) VALUES 
('SUPER','PRIVILEGIOS ESPECIALES')
,('TEST','Funcionalidades en TEST')
,('ALIAS','Permiso de adquirir identidad alias')
,('PUBLIC','Acceso básico a la plataforma')
,('ADMIN','Funciones de administracion de Bigan')
;

INSERT INTO mid_roles (role_id,role_desc,role_order) VALUES 
('ADMIN','ADMINISTRADOR',0)
,('PUBLIC','PUBLICO',0)
,('ALIAS','CAPACIDAD ALIAS',0)
;











































