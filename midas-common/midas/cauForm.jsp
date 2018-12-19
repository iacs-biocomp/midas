<!--Message Modal
	Depends on midas mid-message.js script loaded
-->

<div id="msg-form" class="modal fade cau-bindable" tabindex="-1" role="dialog">
  <div class="modal-dialog">
	<div class="panel panel-default">
		<div class="panel-heading clearfix">
			<h3 class="panel-title">Formulario notificacion incidencias / sugerencias</h3>
			<ul class="panel-tool-options"> 
				<li><a data-toggle="modal" data-target="#msg-form" href="#"><i class="icon-cancel"></i></a></li>
			</ul>
		</div>
		<div class="panel-body">
			 <form data-bind="submit: CauMessageForm.submitCauMessage">
				  <div class="form-group">
					<label for="asunto">Asunto</label>
					<input type="text" class="form-control" id="asunto" placeholder="Asunto" data-bind="value: CauMessageForm.cauMessage.subject">
				  </div>
				  <div class="form-group">
					<label for="msgText">Texto</label>
					<textarea class="form-control" rows="5" id="msgText" data-bind="value: CauMessageForm.cauMessage.message"></textarea>
				  </div>
				  <input type="hidden" data-bind="attr{ value: CauMessageForm.cauMessage.sender} ">
				  <button type="submit" class="btn btn-primary">Enviar</button>
			</form>
		</div>
	</div>
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--End Basic Modal--> 