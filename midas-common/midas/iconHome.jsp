	<%@ taglib prefix="s" uri="/struts-tags" %>
<s:iterator value="userMenu.children" var="parent">
<span class="title"><s:property value="mnTexto"/></span>
	<s:if test="%{#parent.children.size > 0}">
		<div class="row iconRow">
	    	<s:iterator value="#parent.children" var="child">
	 			<div class="col-lg-2">
					<a href="<s:property value="mnLink"/>" target="<s:property value="mnTarget"/>"><img src='/static/img/icons/<s:property value="mnStyle"/>' class="iconImage" alt='<s:property value="mnTexto"/>' title='<s:property value="mnTexto"/>'/></a>                            
	           	</div>
			</s:iterator>
		</div>
	</s:if>
</s:iterator>			
