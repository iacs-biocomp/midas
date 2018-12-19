var currentAction = 'listGrants.action';

function nuevoGrant(){
	$('#formGrants').attr('action', 'nuevoGrants.action');
	$('#formGrants').submit();
	$('#formGrants').attr('action', currentAction);
}

function borrarGrant(grId){
	if(grId != '' && confirm('Confirme borrado...')){
		$('#formGrants').attr('action', 'borrarGrants.action');
		$('#grant_grId').val(grId);
		$('#formGrants').submit();
		$('#formGrants').attr('action', currentAction);
		$('#grant_grId').val('');
	}
}
