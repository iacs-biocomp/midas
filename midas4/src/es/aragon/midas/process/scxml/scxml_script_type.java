////////////////////////////////////////////////////////////////////////
//
// scxml_script_type.java
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


public class scxml_script_type extends com.altova.xml.TypeBase
{
		public static com.altova.xml.meta.ComplexType getStaticInfo() { return new com.altova.xml.meta.ComplexType(es.aragon.midas.process.scxml.scxml_TypeInfo.binder.getTypes()[es.aragon.midas.process.scxml.scxml_TypeInfo._altova_ti_altova_scxml_script_type]); }
	
	public scxml_script_type(org.w3c.dom.Node init)
	{
		super(init);
		instantiateMembers();
	}
	
	private void instantiateMembers()
	{
		src = new MemberAttribute_src (this, es.aragon.midas.process.scxml.scxml_TypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.scxml_TypeInfo._altova_mi_altova_scxml_script_type._src]);

	}
	// Attributes
	public String getValue() 
	{ 
		com.altova.typeinfo.MemberInfo member = es.aragon.midas.process.scxml.scxml_TypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.scxml_TypeInfo._altova_mi_altova_scxml_script_type._unnamed];
		return (String)com.altova.xml.XmlTreeOperations.castToString(getNode(), member);
	}
	
	public void setValue(String value)
	{
		com.altova.typeinfo.MemberInfo member = es.aragon.midas.process.scxml.scxml_TypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.scxml_TypeInfo._altova_mi_altova_scxml_script_type._unnamed];
		com.altova.xml.XmlTreeOperations.setValue(getNode(), member, value);
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


	// Elements

		public void setXsiType() {com.altova.xml.XmlTreeOperations.setAttribute(getNode(), "http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "http://www.w3.org/2005/07/scxml", "scxml.script.type");}
}
