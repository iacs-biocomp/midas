////////////////////////////////////////////////////////////////////////
//
// scxml_parallel_type.java
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


public class ScxmlParallelType extends com.altova.xml.TypeBase
{
		public static com.altova.xml.meta.ComplexType getStaticInfo() { return new com.altova.xml.meta.ComplexType(es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getTypes()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_ti_altova_scxml_parallel_type]); }
	
	public ScxmlParallelType(org.w3c.dom.Node init)
	{
		super(init);
		instantiateMembers();
	}
	
	private void instantiateMembers()
	{
		id = new MemberAttribute_id (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_parallel_type._id]);

		onentry= new MemberElement_onentry (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_parallel_type._onentry]);
		onexit= new MemberElement_onexit (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_parallel_type._onexit]);
		transition= new MemberElement_transition (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_parallel_type._transition]);
		state= new MemberElement_state (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_parallel_type._state]);
		parallel= new MemberElement_parallel (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_parallel_type._parallel]);
		history= new MemberElement_history (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_parallel_type._history]);
		datamodel= new MemberElement_datamodel (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_parallel_type._datamodel]);
		invoke= new MemberElement_invoke (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_parallel_type._invoke]);
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


	// Elements
	
	public MemberElement_onentry onentry;

		public static class MemberElement_onentry
		{
			public static class MemberElement_onentry_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_onentry member;
				public MemberElement_onentry_Iterator(MemberElement_onentry member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlOnentryType nx = new ScxmlOnentryType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_onentry (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlOnentryType at(int index) {return new ScxmlOnentryType(owner.getElementAt(info, index));}
			public ScxmlOnentryType first() {return new ScxmlOnentryType(owner.getElementFirst(info));}
			public ScxmlOnentryType last(){return new ScxmlOnentryType(owner.getElementLast(info));}
			public ScxmlOnentryType append(){return new ScxmlOnentryType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_onentry_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_onexit onexit;

		public static class MemberElement_onexit
		{
			public static class MemberElement_onexit_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_onexit member;
				public MemberElement_onexit_Iterator(MemberElement_onexit member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlOnexitType nx = new ScxmlOnexitType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_onexit (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlOnexitType at(int index) {return new ScxmlOnexitType(owner.getElementAt(info, index));}
			public ScxmlOnexitType first() {return new ScxmlOnexitType(owner.getElementFirst(info));}
			public ScxmlOnexitType last(){return new ScxmlOnexitType(owner.getElementLast(info));}
			public ScxmlOnexitType append(){return new ScxmlOnexitType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_onexit_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
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
	
	public MemberElement_state state;

		public static class MemberElement_state
		{
			public static class MemberElement_state_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_state member;
				public MemberElement_state_Iterator(MemberElement_state member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlStateType nx = new ScxmlStateType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_state (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlStateType at(int index) {return new ScxmlStateType(owner.getElementAt(info, index));}
			public ScxmlStateType first() {return new ScxmlStateType(owner.getElementFirst(info));}
			public ScxmlStateType last(){return new ScxmlStateType(owner.getElementLast(info));}
			public ScxmlStateType append(){return new ScxmlStateType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_state_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_parallel parallel;

		public static class MemberElement_parallel
		{
			public static class MemberElement_parallel_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_parallel member;
				public MemberElement_parallel_Iterator(MemberElement_parallel member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlParallelType nx = new ScxmlParallelType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_parallel (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlParallelType at(int index) {return new ScxmlParallelType(owner.getElementAt(info, index));}
			public ScxmlParallelType first() {return new ScxmlParallelType(owner.getElementFirst(info));}
			public ScxmlParallelType last(){return new ScxmlParallelType(owner.getElementLast(info));}
			public ScxmlParallelType append(){return new ScxmlParallelType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_parallel_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_history history;

		public static class MemberElement_history
		{
			public static class MemberElement_history_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_history member;
				public MemberElement_history_Iterator(MemberElement_history member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlHistoryType nx = new ScxmlHistoryType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_history (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlHistoryType at(int index) {return new ScxmlHistoryType(owner.getElementAt(info, index));}
			public ScxmlHistoryType first() {return new ScxmlHistoryType(owner.getElementFirst(info));}
			public ScxmlHistoryType last(){return new ScxmlHistoryType(owner.getElementLast(info));}
			public ScxmlHistoryType append(){return new ScxmlHistoryType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_history_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_datamodel datamodel;

		public static class MemberElement_datamodel
		{
			public static class MemberElement_datamodel_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_datamodel member;
				public MemberElement_datamodel_Iterator(MemberElement_datamodel member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlDatamodelType nx = new ScxmlDatamodelType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_datamodel (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlDatamodelType at(int index) {return new ScxmlDatamodelType(owner.getElementAt(info, index));}
			public ScxmlDatamodelType first() {return new ScxmlDatamodelType(owner.getElementFirst(info));}
			public ScxmlDatamodelType last(){return new ScxmlDatamodelType(owner.getElementLast(info));}
			public ScxmlDatamodelType append(){return new ScxmlDatamodelType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_datamodel_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_invoke invoke;

		public static class MemberElement_invoke
		{
			public static class MemberElement_invoke_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_invoke member;
				public MemberElement_invoke_Iterator(MemberElement_invoke member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlInvokeType nx = new ScxmlInvokeType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_invoke (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlInvokeType at(int index) {return new ScxmlInvokeType(owner.getElementAt(info, index));}
			public ScxmlInvokeType first() {return new ScxmlInvokeType(owner.getElementFirst(info));}
			public ScxmlInvokeType last(){return new ScxmlInvokeType(owner.getElementLast(info));}
			public ScxmlInvokeType append(){return new ScxmlInvokeType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_invoke_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}

		public void setXsiType() {com.altova.xml.XmlTreeOperations.setAttribute(getNode(), "http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "http://www.w3.org/2005/07/scxml", "scxml.parallel.type");}
}
