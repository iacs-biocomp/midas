set schema 'investiga'

DROP VIEW mid_user_context;
ALTER TABLE mid_users ALTER COLUMN pwd TYPE varchar(100) USING pwd::varchar;
ALTER TABLE mid_users ALTER COLUMN user_name TYPE varchar(100) USING user_name::varchar;
ALTER TABLE mid_users ADD email varchar(255) NULL;
ALTER TABLE mid_users ADD auth_mode bpchar(1) NOT NULL DEFAULT ‘S’::bpchar;
ALTER TABLE mid_user_context_auto ALTER COLUMN user_name TYPE varchar(100) USING user_name::varchar;
ALTER TABLE mid_user_context_manual ALTER COLUMN user_name TYPE varchar(100) USING user_name::varchar;
ALTER TABLE mid_userroles ALTER COLUMN ur_name TYPE varchar(100) USING ur_name::varchar;

CREATE OR REPLACE VIEW investiga.mid_user_context
AS SELECT DISTINCT userroles.user_name,
    userroles.cx_id
   FROM ( SELECT mid_user_context_auto.user_name,
            mid_user_context_auto.cx_id
           FROM investiga.mid_user_context_auto
        UNION
         SELECT mid_user_context_manual.user_name,
            mid_user_context_manual.cx_id
           FROM investiga.mid_user_context_manual) userroles;

-- Permissions

ALTER TABLE investiga.mid_user_context OWNER TO bigan;
GRANT ALL ON TABLE investiga.mid_user_context TO bigan;





INSERT INTO mid_app_properties
(id, value, description)
VALUES('midas.security.key', '$1$7MPcDN4p$/kOQiwzpv3GQJ1UU8sCOA0', NULL);

INSERT INTO mid_app_properties
(id, value, description)
VALUES('midas.database.version', '45', NULL);
--update mid_app_properties set value = 45 where id = 'midas.database.version';



CREATE OR REPLACE FUNCTION investiga.crypt_pwd_function()
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




create trigger crypt_pwd_trg before insert or update of pwd
on mid_users
for each row
execute procedure crypt_pwd_function();




CREATE OR REPLACE FUNCTION investiga.crypt_email_function()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
	_key	varchar(100);
BEGIN
  IF NEW.email IS NOT NULL THEN
	select mid_app_properties.value into _key from investiga.mid_app_properties where id = 'midas.security.key';
    NEW.email := encode(public.pgp_sym_encrypt(NEW.email, _key), 'base64');
  END IF;
  RETURN NEW;
END$function$
;



create trigger crypt_email_trg before insert or update of email
on mid_users
for each row
execute procedure crypt_email_function();




CREATE OR REPLACE FUNCTION investiga.decrypt_email(m character varying)
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

	

	

