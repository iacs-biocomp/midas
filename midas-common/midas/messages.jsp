<%@ taglib prefix="s" uri="/struts-tags" %>


				  <li class="notifications dropdown cau-bindable">
					<a data-close-others="true" data-hover="dropdown" data-toggle="dropdown" class="dropdown-toggle" href="#">
					<i class="icon-mail">
					</i><span class="badge badge-secondary" data-bind="text: CauMessageForm.numOfUnreadMsgs, visible: CauMessageForm.numOfUnreadMsgs ">
						<s:property value="messagesNumber"/></span></a>
					<ul class="dropdown-menu pull-right">
						<li class="first">
							<div class="dropdown-content-header"><a href="#/" data-toggle="modal" data-target="#msg-form"><i class="fa fa-pencil-square-o pull-right"></i></a> Mensajes</div>
						</li>
						<li>
							<ul class="media-list"  data-bind="foreach: CauMessageForm.unreadMessages">
							
								<li class="media unread message-success"><a href="#"></a>
									<div class="media-left"><img alt="" class="img-circle img-sm" src=""></div>
									<div class="media-body">
										<a class="media-heading" href="#">
											<span class="text-semibold" data-bind="text: sender"></span>
											<span class="media-annotation pull-right" data-bind="text: sendDateS"></span>
										</a>
										<span class="text-muted" data-bind="text: subject"></span>
									</div>								
								</li>
							</ul>
						</li>
						<li class="external-last"> <a class="danger" href="cauManager">Todos los mensajes</a> </li>
					</ul>
				  </li>


 
 
 				  
				  

