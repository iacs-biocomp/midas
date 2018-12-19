var currentAction = 'listUsers.action';

function nuevoUser(){
	$('#formUsers').attr('action', 'nuevoUsers.action');
	$('#formUsers').submit();
	$('#formUsers').attr('action', currentAction);
}

function modificarUser(){
	$('#formUsers').attr('action', 'modificarUsers.action');
	$('#formUsers').submit();
	$('#formUsers').attr('action', currentAction);
}

function seleccionarUser(userName){
	$('#formUsers').attr('action', 'selectUsers.action');
	$('#userId').val(userName);
	$('#formUsers').submit();
	$('#formUsers').attr('action', currentAction);
}

function nuevoUserRol(){
	$('#formUsers').attr('action', 'nuevoURUsers.action');
	$('#formUsers').submit();
	$('#formUsers').attr('action', currentAction);
}

function nuevoUserContext(){
	$('#formUsers').attr('action', 'nuevoUCUsers.action');
	$('#formUsers').submit();
	$('#formUsers').attr('action', currentAction);
}

function borrarUserRole(rolId){
	$('#formUsers').attr('action', 'borrarURUsers.action');
	$('#user_roleId').val(rolId);
	$('#formUsers').submit();
	$('#formUsers').attr('action', currentAction);
}

function borrarUserContext(cxId){
	$('#formUsers').attr('action', 'borrarUCUsers.action');
	$('#user_cxId').val(cxId);
	$('#formUsers').submit();
	$('#formUsers').attr('action', currentAction);
}

function resaltaEstado(clave, estado){
	 if(estado == 'Y'){
		 $('img[id="activo' + clave + '"]').css('opacity','1');
		 $('img[id="activo' + clave + '"]').removeAttr("onclick").css("cursor","auto");
	 }else if(estado == 'N'){
		 $('img[id="inactivo' + clave + '"]').css('opacity','1');
		 $('img[id="inactivo' + clave + '"]').removeAttr("onclick").css("cursor","auto");
	 }else{
		 $('img[id="activo' + clave + '"]').css('opacity','0.3');
		 $('img[id="inactivo' + clave + '"]').css('opacity','0.3');
	 }
}

function activarUser(userName, valor){
	$('#formUsers').attr('action', 'activarUsers.action');
	$('#userId').val(userName);
	$('#userActive').val(valor);
	$('#formUsers').submit();
	$('#formUsers').attr('action', currentAction);
}
