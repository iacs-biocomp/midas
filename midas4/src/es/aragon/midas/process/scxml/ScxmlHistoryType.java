////////////////////////////////////////////////////////////////////////
//
// scxml_history_type.java
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


public class ScxmlHistoryType extends com.altova.xml.TypeBase
{
		public static com.altova.xml.meta.ComplexType getStaticInfo() { return new com.altova.xml.meta.ComplexType(es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getTypes()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_ti_altova_scxml_history_type]); }
	
	public ScxmlHistoryType(org.w3c.dom.Node init)
	{
		super(init);
		instantiateMembers();
	}
	
	private void instantiateMembers()
	{
		id = new MemberAttribute_id (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_history_type._id]);
		type = new MemberAttribute_type (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_history_type._type]);

		transition= new MemberElement_transition (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_history_type._transition]);
	}
	// Attributes
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


	// Elements
	
	public MemberElement_transition transition;

		public static class MemberElement_transition
		{
			public static class MemberElement_transition_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_transition member;
				public MemberElement_transition_Iterator(MemberElement_transition member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlTransitionType nx = new ScxmlTransitionType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_transition (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlTransitionType at(int index) {return new ScxmlTransitionType(owner.getElementAt(info, index));}
			public ScxmlTransitionType first() {return new ScxmlTransitionType(owner.getElementFirst(info));}
			public ScxmlTransitionType last(){return new ScxmlTransitionType(owner.getElementLast(info));}
			public ScxmlTransitionType append(){return new ScxmlTransitionType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_transition_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}

		public void setXsiType() {com.altova.xml.XmlTreeOperations.setAttribute(getNode(), "http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "http://www.w3.org/2005/07/scxml", "scxml.history.type");}
}
