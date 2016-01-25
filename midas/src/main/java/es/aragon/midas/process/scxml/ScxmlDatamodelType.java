////////////////////////////////////////////////////////////////////////
//
// scxml_datamodel_type.java
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


public class ScxmlDatamodelType extends com.altova.xml.TypeBase
{
		public static com.altova.xml.meta.ComplexType getStaticInfo() { return new com.altova.xml.meta.ComplexType(es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getTypes()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_ti_altova_scxml_datamodel_type]); }
	
	public ScxmlDatamodelType(org.w3c.dom.Node init)
	{
		super(init);
		instantiateMembers();
	}
	
	private void instantiateMembers()
	{

		data= new MemberElement_data (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_datamodel_type._data]);
	}
	// Attributes


	// Elements
	
	public MemberElement_data data;

		public static class MemberElement_data
		{
			@SuppressWarnings("rawtypes")
			public static class MemberElement_data_Iterator implements java.util.Iterator
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_data member;
				public MemberElement_data_Iterator(MemberElement_data member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlDataType nx = new ScxmlDataType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_data (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlDataType at(int index) {return new ScxmlDataType(owner.getElementAt(info, index));}
			public ScxmlDataType first() {return new ScxmlDataType(owner.getElementFirst(info));}
			public ScxmlDataType last(){return new ScxmlDataType(owner.getElementLast(info));}
			public ScxmlDataType append(){return new ScxmlDataType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			@SuppressWarnings("rawtypes")
			public java.util.Iterator iterator() {return new MemberElement_data_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}

		public void setXsiType() {com.altova.xml.XmlTreeOperations.setAttribute(getNode(), "http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "http://www.w3.org/2005/07/scxml", "scxml.datamodel.type");}
}