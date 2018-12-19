var currentAction = 'listRoles.action';

function nuevoRol(){
	$('#formRoles').attr('action', 'nuevoRoles.action');
	$('#formRoles').submit();
	$('#formRoles').attr('action', currentAction);
}

function borrarRol(rolId){
	if(rolId != '' && confirm('Confirme borrado...')){
		$('#formRoles').attr('action', 'borrarRoles.action');
		$('#rol_roleId').val(rolId);
		$('#formRoles').submit();
		$('#formRoles').attr('action', currentAction);
		$('#rol_roleId').val('');
	}
}

function nuevoRoleGrant(){
	var rolId = $("#selectRG_R").val();
	var grId = $("#selectRG_G").val();
	if(rolId != '' && grId != ''){
		$('#formRoles').attr('action', 'nuevoRGRoles.action');
		$('#rol_roleId').val(rolId);
		$('#grant_grId').val(grId);
		$('#formRoles').submit();
		$('#formRoles').attr('action', currentAction);
		$('#rol_roleId').val('');
	}
}

function borrarRoleGrant(rolId, grId){
	if(rolId != '' && grId != '' && confirm('Confirme borrado...')){
		$('#formRoles').attr('action', 'borrarRGRoles.action');
		$('#rol_roleId').val(rolId);
		$('#grant_grId').val(grId);
		$('#formRoles').submit();
		$('#formRoles').attr('action', currentAction);
		$('#rol_roleId').val('');
	}
}

function nuevoRoleContext(){
	var rolId = $("#selectRC_R").val();
	var cxId = $("#selectRC_C").val();
	if(rolId != '' && cxId != ''){
		$('#formRoles').attr('action', 'nuevoRCRoles.action');
		$('#rol_roleId').val(rolId);
		$('#context_cxId').val(cxId);
		$('#formRoles').submit();
		$('#formRoles').attr('action', currentAction);
		$('#rol_roleId').val('');
	}
}

function borrarRoleContext(rolId, cxId){
	if(rolId != '' && cxId != '' && confirm('Confirme borrado...')){
		$('#formRoles').attr('action', 'borrarRCRoles.action');
		$('#rol_roleId').val(rolId);
		$('#context_cxId').val(cxId);
		$('#formRoles').submit();
		$('#formRoles').attr('action', currentAction);
		$('#rol_roleId').val('');
	}
}