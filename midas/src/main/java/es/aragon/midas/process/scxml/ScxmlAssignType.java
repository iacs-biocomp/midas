////////////////////////////////////////////////////////////////////////
//
// scxml_assign_type.java
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


public class ScxmlAssignType extends com.altova.xml.TypeBase
{
		public static com.altova.xml.meta.ComplexType getStaticInfo() { return new com.altova.xml.meta.ComplexType(es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getTypes()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_ti_altova_scxml_assign_type]); }
	
	public ScxmlAssignType(org.w3c.dom.Node init)
	{
		super(init);
		instantiateMembers();
	}
	
	private void instantiateMembers()
	{
		location = new MemberAttribute_location (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_assign_type._location]);
		expr = new MemberAttribute_expr (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_assign_type._expr]);
		type = new MemberAttribute_type (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_assign_type._type]);
		attr = new MemberAttribute_attr (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_assign_type._attr]);

	}
	// Attributes
	public String getValue() 
	{ 
		com.altova.typeinfo.MemberInfo member = es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_assign_type._unnamed];
		return (String)com.altova.xml.XmlTreeOperations.castToString(getNode(), member);
	}
	
	public void setValue(String value)
	{
		com.altova.typeinfo.MemberInfo member = es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_assign_type._unnamed];
		com.altova.xml.XmlTreeOperations.setValue(getNode(), member, value);
	}
	
	public MemberAttribute_location location;
		public static class MemberAttribute_location
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_location (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
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
	public MemberAttribute_expr expr;
		public static class MemberAttribute_expr
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_expr (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
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
	public MemberAttribute_attr attr;
		public static class MemberAttribute_attr
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_attr (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
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

		public void setXsiType() {com.altova.xml.XmlTreeOperations.setAttribute(getNode(), "http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "http://www.w3.org/2005/07/scxml", "scxml.assign.type");}
}
