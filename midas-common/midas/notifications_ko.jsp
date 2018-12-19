<%@ taglib prefix="s" uri="/struts-tags" %>

				  <li class="notifications dropdown" id="not-display">
					<a data-close-others="true" data-hover="dropdown" data-toggle="dropdown" class="dropdown-toggle" href="#">
						<i class="icon-attention"></i>
						<span class="badge badge-info" data-bind="text: Notification.numOfUnreadNots, visible: Notification.numOfUnreadNots"></span></a>
					<ul class="dropdown-menu pull-right">
						<li class="first">
							<div class="small"><a class="pull-right danger" href="#">Marcar todos leídos</a> Tienes <strong><span data-bind="text: Notification.numOfUnreadNots"></span></strong> notificationes sin leer.</div>
						</li>
						<li>
							<ul class="dropdown-list" data-bind="foreach: Notification.notifications">
								<li class="unread notification-success"><a href="#"  data-bind="click: Notification.markAsRead">
									<i class="pull-right" data-bind="css: style"></i>
									<span class="block-line" data-bind="text: message, css: { strong: status() == 'S' }"></span>
									<span class="block-line small" data-bind="text: sendDate"></span></a>
								</li>
							</ul>
						</li>
						<li class="external-last"> <a class="danger" href="#">Todas las notificaciones</a> </li>
					</ul>
				  </li>

				  
<div id="not-form" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
	<div class="panel panel-default">
		<div class="panel-heading clearfix">
			<h3 class="panel-title">Notificación</h3>
			<ul class="panel-tool-options"> 
				<li><a data-toggle="modal" data-target="#not-form" href="#"><i class="icon-cancel"></i></a></li>
			</ul>
		</div>
		<div class="panel-body">
			<p><strong><span data-bind="text: notification.sendDate"></span></strong></p>
			<blockquote>
				<p><span data-bind="text: notification.message"></span></p>
			</blockquote>
			<hr>
		</div>
	</div>
  </div>
</div> 	
