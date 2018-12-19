<%@ taglib prefix="s" uri="/struts-tags" %>

 <s:iterator value="userMenu.children" var="parent">
	<li id="<s:property value="mnId"/>" class="has-sub <s:property value="activeClass" />"><a href="<s:property value="mnLink"/>"><i class="<s:property value="mnStyle"/>"></i><span class="title"><s:property value="mnTexto"/></span></a>
         <s:if test="%{#parent.children.size > 0}">
         	<ul class="nav collapse">
                 <s:iterator value="#parent.children" var="child">
				<li id="<s:property value="mnId"/>" class="<s:property value="activeClass"/>"><a href="<s:property value="mnLink"/>" target="<s:property value="mnTarget"/>"><span class="title"><s:property value="mnTexto"/></span></a></li>                            
                 </s:iterator>
             </ul>
         </s:if>
     </li>
 </s:iterator>			
