////////////////////////////////////////////////////////////////////////
//
// scxml_onentry_type.java
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


public class ScxmlOnentryType extends com.altova.xml.TypeBase
{
		public static com.altova.xml.meta.ComplexType getStaticInfo() { return new com.altova.xml.meta.ComplexType(es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getTypes()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_ti_altova_scxml_onentry_type]); }
	
	public ScxmlOnentryType(org.w3c.dom.Node init)
	{
		super(init);
		instantiateMembers();
	}
	
	private void instantiateMembers()
	{

		raise= new MemberElement_raise (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_onentry_type._raise]);
		if2= new MemberElement_if2 (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_onentry_type._if2]);
		foreach= new MemberElement_foreach (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_onentry_type._foreach]);
		send= new MemberElement_send (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_onentry_type._send]);
		script= new MemberElement_script (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_onentry_type._script]);
		assign= new MemberElement_assign (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_onentry_type._assign]);
		log= new MemberElement_log (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_onentry_type._log]);
		cancel= new MemberElement_cancel (this, es.aragon.midas.process.scxml.ScxmlTypeInfo.binder.getMembers()[es.aragon.midas.process.scxml.ScxmlTypeInfo._altova_mi_altova_scxml_onentry_type._cancel]);
	}
	// Attributes


	// Elements
	
	public MemberElement_raise raise;

		public static class MemberElement_raise
		{
			public static class MemberElement_raise_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_raise member;
				public MemberElement_raise_Iterator(MemberElement_raise member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlRaiseType nx = new ScxmlRaiseType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_raise (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlRaiseType at(int index) {return new ScxmlRaiseType(owner.getElementAt(info, index));}
			public ScxmlRaiseType first() {return new ScxmlRaiseType(owner.getElementFirst(info));}
			public ScxmlRaiseType last(){return new ScxmlRaiseType(owner.getElementLast(info));}
			public ScxmlRaiseType append(){return new ScxmlRaiseType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_raise_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_if2 if2;

		public static class MemberElement_if2
		{
			public static class MemberElement_if2_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_if2 member;
				public MemberElement_if2_Iterator(MemberElement_if2 member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlIfType nx = new ScxmlIfType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_if2 (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlIfType at(int index) {return new ScxmlIfType(owner.getElementAt(info, index));}
			public ScxmlIfType first() {return new ScxmlIfType(owner.getElementFirst(info));}
			public ScxmlIfType last(){return new ScxmlIfType(owner.getElementLast(info));}
			public ScxmlIfType append(){return new ScxmlIfType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_if2_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_foreach foreach;

		public static class MemberElement_foreach
		{
			public static class MemberElement_foreach_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_foreach member;
				public MemberElement_foreach_Iterator(MemberElement_foreach member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlForeachType nx = new ScxmlForeachType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_foreach (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlForeachType at(int index) {return new ScxmlForeachType(owner.getElementAt(info, index));}
			public ScxmlForeachType first() {return new ScxmlForeachType(owner.getElementFirst(info));}
			public ScxmlForeachType last(){return new ScxmlForeachType(owner.getElementLast(info));}
			public ScxmlForeachType append(){return new ScxmlForeachType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_foreach_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_send send;

		public static class MemberElement_send
		{
			public static class MemberElement_send_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_send member;
				public MemberElement_send_Iterator(MemberElement_send member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlSendType nx = new ScxmlSendType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_send (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlSendType at(int index) {return new ScxmlSendType(owner.getElementAt(info, index));}
			public ScxmlSendType first() {return new ScxmlSendType(owner.getElementFirst(info));}
			public ScxmlSendType last(){return new ScxmlSendType(owner.getElementLast(info));}
			public ScxmlSendType append(){return new ScxmlSendType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_send_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_script script;

		public static class MemberElement_script
		{
			public static class MemberElement_script_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_script member;
				public MemberElement_script_Iterator(MemberElement_script member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlScriptType nx = new ScxmlScriptType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_script (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlScriptType at(int index) {return new ScxmlScriptType(owner.getElementAt(info, index));}
			public ScxmlScriptType first() {return new ScxmlScriptType(owner.getElementFirst(info));}
			public ScxmlScriptType last(){return new ScxmlScriptType(owner.getElementLast(info));}
			public ScxmlScriptType append(){return new ScxmlScriptType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_script_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_assign assign;

		public static class MemberElement_assign
		{
			public static class MemberElement_assign_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_assign member;
				public MemberElement_assign_Iterator(MemberElement_assign member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlAssignType nx = new ScxmlAssignType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_assign (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlAssignType at(int index) {return new ScxmlAssignType(owner.getElementAt(info, index));}
			public ScxmlAssignType first() {return new ScxmlAssignType(owner.getElementFirst(info));}
			public ScxmlAssignType last(){return new ScxmlAssignType(owner.getElementLast(info));}
			public ScxmlAssignType append(){return new ScxmlAssignType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_assign_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_log log;

		public static class MemberElement_log
		{
			public static class MemberElement_log_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_log member;
				public MemberElement_log_Iterator(MemberElement_log member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlLogType nx = new ScxmlLogType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_log (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlLogType at(int index) {return new ScxmlLogType(owner.getElementAt(info, index));}
			public ScxmlLogType first() {return new ScxmlLogType(owner.getElementFirst(info));}
			public ScxmlLogType last(){return new ScxmlLogType(owner.getElementLast(info));}
			public ScxmlLogType append(){return new ScxmlLogType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_log_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}
	
	public MemberElement_cancel cancel;

		public static class MemberElement_cancel
		{
			public static class MemberElement_cancel_Iterator implements java.util.Iterator<Object>
			{
				private org.w3c.dom.Node nextNode;
				private MemberElement_cancel member;
				public MemberElement_cancel_Iterator(MemberElement_cancel member) {this.member=member; nextNode=member.owner.getElementFirst(member.info);}
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
					ScxmlCancelType nx = new ScxmlCancelType(nextNode);
					nextNode = nextNode.getNextSibling();
					return nx;
				}
				
				public void remove () {}
			}
			protected com.altova.xml.TypeBase owner;
			protected com.altova.typeinfo.MemberInfo info;
			public MemberElement_cancel (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) { this.owner = owner; this.info = info;}
			public ScxmlCancelType at(int index) {return new ScxmlCancelType(owner.getElementAt(info, index));}
			public ScxmlCancelType first() {return new ScxmlCancelType(owner.getElementFirst(info));}
			public ScxmlCancelType last(){return new ScxmlCancelType(owner.getElementLast(info));}
			public ScxmlCancelType append(){return new ScxmlCancelType(owner.createElement(info));}
			public boolean exists() {return count() > 0;}
			public int count() {return owner.countElement(info);}
			public void remove() {owner.removeElement(info);}
			public void removeAt(int index) {owner.removeElementAt(info, index);}
			public java.util.Iterator<Object> iterator() {return new MemberElement_cancel_Iterator(this);}
			public com.altova.xml.meta.Element getInfo() { return new com.altova.xml.meta.Element(info); }
		}

		public void setXsiType() {com.altova.xml.XmlTreeOperations.setAttribute(getNode(), "http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "http://www.w3.org/2005/07/scxml", "scxml.onentry.type");}
}
