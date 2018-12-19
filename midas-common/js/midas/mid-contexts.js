var currentAction = 'listContexts.action';

function nuevoContext(){
	$('#formContexts').attr('action', 'nuevoContexts.action');
	$('#formContexts').submit();
	$('#formContexts').attr('action', currentAction);
}

function borrarContext(cxId){
	if(cxId != '' && confirm('Confirme borrado...')){
		$('#formContexts').attr('action', 'borrarContexts.action');
		$('#context_cxId').val(cxId);
		$('#formContexts').submit();
		$('#formContexts').attr('action', currentAction);
		$('#context_cxId').val('');
	}
}
