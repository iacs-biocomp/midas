////////////////////////////////////////////////////////////////////////
//
// scxml_invoke_type.java
//
// This file was generated by XMLSpy 2009 Enterprise Edition.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the XMLSpy Documentation for further details.
// http://www.altova.com/xmlspy
//
////////////////////////////////////////////////////////////////////////

package es.aragon.midas.process.scxml;


public class ScxmlInvokeType extends com.altova.xml.TypeBase
{
		public static com.altova.xml.meta.ComplexType getStaticInfo() { return new com.altova.xml.meta.ComplexType(es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getTypes()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_ti_altova_scxml_invoke_type]); }
	
	public ScxmlInvokeType(org.w3c.dom.Node init)
	{
		super(init);
		instantiateMembers();
	}
	
	private void instantiateMembers()
	{
		type = new MemberAttribute_type (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._type]);
		typeexpr = new MemberAttribute_typeexpr (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._typeexpr]);
		src = new MemberAttribute_src (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._src]);
		srcexpr = new MemberAttribute_srcexpr (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._srcexpr]);
		id = new MemberAttribute_id (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._id]);
		idlocation = new MemberAttribute_idlocation (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._idlocation]);
		namelist = new MemberAttribute_namelist (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._namelist]);
		autoforward = new MemberAttribute_autoforward (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._autoforward]);

		content= new MemberElement_content (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._content]);
		param= new MemberElement_param (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._param]);
		finalize= new MemberElement_finalize (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_invoke_type._finalize]);
	}
	// Attributes
	public MemberAttribute_type type;
		public static class MemberAttribute_type
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_type (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
			public String getValue() {
				return (String)com.altova.xml.XmlTreeOperations.castToString(com.altova.xml.XmlTreeOperations.findAttribute(owner.getNode(), info), info);
			}
			public void setValue(String value) {
				com.altova.xml.XmlTreeOperations.setValue(owner.getNode(), info, value);		
			}
			public boolean exists() {return owner.getAttribute(info) != null;}
			public void remove() {owner.removeAttribute(info);} 
			public com.altova.xml.meta.Attribute getInfo() {return new com.altova.xml.meta.Attribute(info);}
		}
	public MemberAttribute_typeexpr typeexpr;
		public static class MemberAttribute_typeexpr
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_typeexpr (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
			public String getValue() {
				return (String)com.altova.xml.XmlTreeOperations.castToString(com.altova.xml.XmlTreeOperations.findAttribute(owner.getNode(), info), info);
			}
			public void setValue(String value) {
				com.altova.xml.XmlTreeOperations.setValue(owner.getNode(), info, value);		
			}
			public boolean exists() {return owner.getAttribute(info) != null;}
			public void remove() {owner.removeAttribute(info);} 
			public com.altova.xml.meta.Attribute getInfo() {return new com.altova.xml.meta.Attribute(info);}
		}
	public MemberAttribute_src src;
		public static class MemberAttribute_src
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_src (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
			public String getValue() {
				return (String)com.altova.xml.XmlTreeOperations.castToString(com.altova.xml.XmlTreeOperations.findAttribute(owner.getNode(), info), info);
			}
			public void setValue(String value) {
				com.altova.xml.XmlTreeOperations.setValue(owner.getNode(), info, value);		
			}
			public boolean exists() {return owner.getAttribute(info) != null;}
			public void remove() {owner.removeAttribute(info);} 
			public com.altova.xml.meta.Attribute getInfo() {return new com.altova.xml.meta.Attribute(info);}
		}
	public MemberAttribute_srcexpr srcexpr;
		public static class MemberAttribute_srcexpr
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_srcexpr (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
			public String getValue() {
				return (String)com.altova.xml.XmlTreeOperations.castToString(com.altova.xml.XmlTreeOperations.findAttribute(owner.getNode(), info), info);
			}
			public void setValue(String value) {
				com.altova.xml.XmlTreeOperations.setValue(owner.getNode(), info, value);		
			}
			public boolean exists() {return owner.getAttribute(info) != null;}
			public void remove() {owner.removeAttribute(info);} 
			public com.altova.xml.meta.Attribute getInfo() {return new com.altova.xml.meta.Attribute(info);}
		}
	public MemberAttribute_id id;
		public static class MemberAttribute_id
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_id (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
			public String getValue() {
				return (String)com.altova.xml.XmlTreeOperations.castToString(com.altova.xml.XmlTreeOperations.findAttribute(owner.getNode(), info), info);
			}
			public void setValue(String value) {
				com.altova.xml.XmlTreeOperations.setValue(owner.getNode(), info, value);		
			}
			public boolean exists() {return owner.getAttribute(info) != null;}
			public void remove() {owner.removeAttribute(info);} 
			public com.altova.xml.meta.Attribute getInfo() {return new com.altova.xml.meta.Attribute(info);}
		}
	public MemberAttribute_idlocation idlocation;
		public static class MemberAttribute_idlocation
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_idlocation (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
			public String getValue() {
				return (String)com.altova.xml.XmlTreeOperations.castToString(com.altova.xml.XmlTreeOperations.findAttribute(owner.getNode(), info), info);
			}
			public void setValue(String value) {
				com.altova.xml.XmlTreeOperations.setValue(owner.getNode(), info, value);		
			}
			public boolean exists() {return owner.getAttribute(info) != null;}
			public void remove() {owner.removeAttribute(info);} 
			public com.altova.xml.meta.Attribute getInfo() {return new com.altova.xml.meta.Attribute(info);}
		}
	public MemberAttribute_namelist namelist;
		public static class MemberAttribute_namelist
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_namelist (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
			public String getValue() {
				return (String)com.altova.xml.XmlTreeOperations.castToString(com.altova.xml.XmlTreeOperations.findAttribute(owner.getNode(), info), info);
			}
			public void setValue(String value) {
				com.altova.xml.XmlTreeOperations.setValue(owner.getNode(), info, value);		
			}
			public boolean exists() {return owner.getAttribute(info) != null;}
			public void remove() {owner.removeAttribute(info);} 
			public com.altova.xml.meta.Attribute getInfo() {return new com.altova.xml.meta.Attribute(info);}
		}
	public MemberAttribute_autoforward autoforward;
		public static class MemberAttribute_autoforward
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_autoforward (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
			public String getValue() {
				return (String)com.altova.xml.XmlTreeOperations.castToString(com.altova.xml.XmlTreeOperations.findAttribute(owner.getNode(), info), info);
			}
			public void setValue(String value) {
				com.altova.xml.XmlTreeOperations.setValue(owner.getNode(), info, value);		
			}
			public boolean exists() {return owner.getAttribute(info) != null;}
			public void remove() {owner.removeAttribute(info);} 
			public com.altova.xml.meta.Attribute getInfo() {return new com.altova.xml.meta.Attribute(info);}
		}


	// Elements
	
	public MemberElement_content content;

		public static class MemberElement_content
		{
			public static class MemberElement_content_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_content member;
				public MemberElement_content_Iterator(MemberElement_content member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
				public boolean hasNext() 
				{
					while (nextNode != null)
					{
						if (com.altova.xml.TypeBase.memberEqualsNode(member.info, nextNode))
							return true;
						nextNode = nextNode.getNextSibling();
					}
					return false;
				}
				
				public Object next()
				{
					ScxmlContentType nx = new ScxmlContentType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_content (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlContentType at(int index) {return new ScxmlContentType(owner.getElementAt(info, index));}
			public ScxmlContentType first() {return new ScxmlContentType(owner.getElementFirst(info));}
			public ScxmlContentType last(){return new ScxmlContentType(owner.getElementLast(info));}
			public ScxmlContentType append(){return new ScxmlContentType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_content_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_param param;

		public static class MemberElement_param
		{
			public static class MemberElement_param_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_param member;
				public MemberElement_param_Iterator(MemberElement_param member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
				public boolean hasNext() 
				{
					while (nextNode != null)
					{
						if (com.altova.xml.TypeBase.memberEqualsNode(member.info, nextNode))
							return true;
						nextNode = nextNode.getNextSibling();
					}
					return false;
				}
				
				public Object next()
				{
					ScxmlParamType nx = new ScxmlParamType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_param (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlParamType at(int index) {return new ScxmlParamType(owner.getElementAt(info, index));}
			public ScxmlParamType first() {return new ScxmlParamType(owner.getElementFirst(info));}
			public ScxmlParamType last(){return new ScxmlParamType(owner.getElementLast(info));}
			public ScxmlParamType append(){return new ScxmlParamType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_param_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_finalize finalize;

		public static class MemberElement_finalize
		{
			public static class MemberElement_finalize_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_finalize member;
				public MemberElement_finalize_Iterator(MemberElement_finalize member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
				public boolean hasNext() 
				{
					while (nextNode != null)
					{
						if (com.altova.xml.TypeBase.memberEqualsNode(member.info, nextNode))
							return true;
						nextNode = nextNode.getNextSibling();
					}
					return false;
				}
				
				public Object next()
				{
					ScxmlFinalizeType nx = new ScxmlFinalizeType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_finalize (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlFinalizeType at(int index) {return new ScxmlFinalizeType(owner.getElementAt(info, index));}
			public ScxmlFinalizeType first() {return new ScxmlFinalizeType(owner.getElementFirst(info));}
			public ScxmlFinalizeType last(){return new ScxmlFinalizeType(owner.getElementLast(info));}
			public ScxmlFinalizeType append(){return new ScxmlFinalizeType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_finalize_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}

		public void setXsiType() {com.altova.xml.XmlTreeOperations.setAttribute(getNode(), "http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "http://www.w3.org/2005/07/scxml", "scxml.invoke.type");}
}
