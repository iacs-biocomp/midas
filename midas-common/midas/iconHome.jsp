<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row iconRow">
	 <s:iterator value="userMenu.children" var="parent">
	       <s:if test="%{#parent.children.size > 0}">
	       		<s:iterator value="#parent.children" var="child">
	 				<div class="col-lg-2">
						<a href="<s:property value="mnLink"/>"><img src='images/<s:property value="mnStyle"/>' class="iconImage" alt='<s:property value="mnTexto"/>' title='<s:property value="mnTexto"/>'/></a>                            
	           		</div>
	            </s:iterator>
	       </s:if>
	 </s:iterator>			
</div>