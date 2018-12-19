<style>
.unread {
	font-weight: bold;
}
.selected
{
    background-color:#e0f0ff;
}
</style>

		<div class="row">
			<div class="col-lg-3 animatedParent animateOnce z-index-50">
				<div class="animated fadeInUp">
					<p><a class="btn btn-block btn-red" href="mail-compose.html">Compose</a></p>
					<ul class="list-unstyled mail-list">
						<li class="active">
							<a href="mail-inbox.html"><i class="fa fa-inbox"></i> Inbox <b>(6)</b></a>
						</li>
						<li>
							<a href="#/"><i class="fa fa-send-o"></i> Sent</a>
						</li>
						<li>
							<a href="#/"><i class="fa fa-edit"></i> Drafts <b>(15)</b></a>
						</li>
						<li>
							<a href="#/"><i class="fa fa-star-o"></i> Important</a>
						</li>
						<li>
							<a href="#/"><i class="fa fa-trash-o"></i> Trash <b>(205)</b></a>
						</li>
					</ul>
					
					<h3 class="title text-uppercase m-l-20">Labels</h3>
					<ul class="list-unstyled category-list">
						<li><a href="#"> <i class="fa fa-circle text-purple"></i> Work </a></li>
						<li><a href="#"> <i class="fa fa-circle text-warning"></i> Clients</a></li>
						<li><a href="#"> <i class="fa fa-circle text-danger"></i> Documents</a></li>
						<li><a href="#"> <i class="fa fa-circle text-primary"></i> Social</a></li>
						<li><a href="#"> <i class="fa fa-circle text-info"></i> Advertising</a></li>
					</ul>
				</div>
			</div>
			<div class="col-lg-9 animatedParent animateOnce z-index-49">
			
				<div class="mail-box animated fadeInUp slow">
					<div class="mail-box-header clearfix">
						<h3 class="mail-title">Inbox <span class="count">(6)</span></h3>
						<form class="mail-search" role="form" method="get"> 
							<div class="input-group"> 
								<input type="text" placeholder="Search for mail..." name="s" class="form-control"> <span class="input-group-btn"> <button type="button" class="btn btn-primary">Search</button> </span> 
							</div> 
						</form>
						<div class="mail-tools clearfix">
							<div class="btn-group pull-right">
								<button class="btn btn-white btn-sm"><i class="fa fa-arrow-left"></i></button>
								<button class="btn btn-white btn-sm"><i class="fa fa-arrow-right"></i></button>
							</div>
							<button title="Refresh inbox" data-placement="left" data-toggle="tooltip" class="btn btn-white btn-sm"><i class="fa fa-refresh"></i> Refresh</button>
							<button title="Mark as read" data-placement="top" data-toggle="tooltip" class="btn btn-white btn-sm"><i class="fa fa-eye"></i> </button>
							<button title="Mark as important" data-placement="top" data-toggle="tooltip" class="btn btn-white btn-sm"><i class="fa fa-exclamation"></i> </button>
							<button title="Move to trash" data-placement="top" data-toggle="tooltip" class="btn btn-white btn-sm"><i class="fa fa-trash-o"></i> </button>
						</div>
					</div>
					<div class="table-responsive">
						<table class="table table-hover table-mails cau-bindable bigan-dt" id="cauMessagesTable">
							<tbody data-bind="foreach: CauMessageForm.cauMessages">
								<tr class="unread" data-bind="css: { unread: status == 'S', 'selected':selectedItem() == $data }, click: selectItem">
									<td class="mail-select">
										<div class="form-checkbox">
											<input type="checkbox" id="checkbox1" checked="checked"> <span class="check"><i class="fa fa-check"></i></span>
										</div>
									</td>
									<td>
										<i class="fa fa-star text-warning"></i>
									</td>
									<td>
										<a href="mail-read.html"><i class="fa fa-circle text-purple m-r-15"></i> <span data-bind="text: sender"></span></a>
									</td>
									<td>
										<a href="mail-read.html"><span data-bind="text: subject" ></span></a>
									</td>
									<td>
										<i class="fa fa-paperclip"></i>
									</td>
									<td class="text-right" data-bind="text: sendDateS">
									</td>
								</tr>
							</tbody>
						</table>
					</div>
               </div>
			  
			</div>
		</div>

<!-- 
			<div class="row height100">
				<div class="col-lg-6">
					<div class="panel panel-default height100">
						<div class="panel-heading clearfix"> 
							<div class="panel-title">Bandeja de Entrada</div> 
							 <ul class="panel-tool-options"> 
	<!-- 							<li><a data-rel="collapse" href="#"><i class="icon-down-open"></i></a></li>
								<li><a data-rel="reload" href="#"><i class="icon-arrows-ccw"></i></a></li>
								<li><a data-rel="close" href="#"><i class="icon-cancel"></i></a></li> -->
<!-- 							</ul>  
						</div> 

						<!-- panel body -> 
						<div class="panel-body"> 					
							<table id="cauMessagesTable" class="cau-bindable bigan-dt table">
								<thead>
									<tr>
										<th style="width: 20%;">Remitente</th>
										<th style="width: 60%;">Asunto</th>								
										<th style="width: 15%;">Fecha</th>
										<th style="width: 5%;"></th>
									</tr>
						        </thead>
					          <tbody data-bind="foreach: CauMessageForm.cauMessages">
					            <tr  data-bind="css: { unread: status == 'S', 'selected':selectedItem() == $data }, click: selectItem">
					              <td data-bind="text: sender"></td>
					              <td data-bind="text: subject"></td>
					              <td data-bind="text: sendDateS"></td>
					              <td><a href="" data-bind="click: CauMessageForm.deleteMessage"><i class="fa fa-trash"></i></a></td>
					            </tr>
					          </tbody>							
							</table>
						</div>
					</div>
				</div>
				
				<div class="col-lg-6 hidable">
					<div class="panel panel-default height100">
						<div class="panel-heading clearfix"> 
							<div class="panel-title">Mensaje</div> 
							 <ul class="panel-tool-options"> 
	<!-- 							<li><a data-rel="collapse" href="#"><i class="icon-down-open"></i></a></li>
								<li><a data-rel="reload" href="#"><i class="icon-arrows-ccw"></i></a></li>
								<li><a data-rel="close" href="#"><i class="icon-cancel"></i></a></li> ->
							</ul>  
						</div> 

						<!-- panel body -> 
						<div class="panel-body"> 
							<div class="cau-bindable">
								<span data-bind="text: CauMessageForm.selectedItem().message"></span>
							</div>					
						</div> 
					</div>				
				</div>
			</div>
			 -->
<script>
	$(function(){CauMessageForm.getCauMessages();});
</script>