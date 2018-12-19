<%@ taglib prefix="s" uri="/struts-tags" %>

				  <li class="notifications dropdown">
					<a data-close-others="true" data-hover="dropdown" data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-attention"></i><span class="badge badge-info">
						<s:property value="notificationsNumber"/></span></a>
					<ul class="dropdown-menu pull-right">
						<li class="first">
							<div class="small"><a class="pull-right danger" href="#">Marcar todos leídos</a> Tienes <strong><s:property value="notificationsNumber"/></strong> nuevas notificationes.</div>
						</li>
						<li>
							<ul class="dropdown-list">
							 <s:iterator value="notifications" var="notify">
								<li class="unread notification-success"><a href="#">
									<i class="<s:property value="style"/> pull-right"></i>
									<span class="block-line strong"><s:property value="message"/></span>
									<span class="block-line small"><s:property value="sendDate"/></span></a>
								</li>
							 </s:iterator>			
							</ul>
						</li>
						<li class="external-last"> <a class="danger" href="#">Todas las notificaciones</a> </li>
					</ul>
				  </li>

 				  
				  

