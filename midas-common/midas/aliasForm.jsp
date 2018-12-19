<%@ taglib prefix="s" uri="/struts-tags"%>
<!--Message Modal
	Depends on midas mid-message.js script loaded
-->

<div id="alias-form">
  <div class="modal-dialog">
	<div class="panel panel-default">
		<div class="panel-heading clearfix">
			<h3 class="panel-title">Selección del usuario a simular</h3>
			<ul class="panel-tool-options"> 
				<li><a data-rel="collapse" href="#"><i class="icon-down-open"></i></a></li>
				<li><a data-rel="reload" href="#"><i class="icon-arrows-ccw"></i></a></li>
				<li><a data-rel="close" href="#"><i class="icon-cancel"></i></a></li>
			</ul>
		</div>
		<div class="panel-body">
		     <s:form action = "setAlias" method = "post" enctype = "multipart/form-data">
		         <s:textfield key = "newUsername" name = "newUsername" onkeyup="textToUpper(this);" />
		         <s:submit key = "Aceptar" />
		         
				<s:if test="hasActionErrors()">
					<div class="errors">
						<s:actionerror escape="false" />
					</div>
				</s:if>		         
		      </s:form>		
		</div>
	</div>
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--End Basic Modal--> 