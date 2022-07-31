

//****NOT CURRENTLY IN USE****

/**
 * AgentList class
 * Acts as a Linked List for Agents
 * @author  Chris Litting
 * @version 1.0
 */
public class AgentList {

  	// ======
	// FIELDS
	// ======
	public Agent head = null; //tracks the list head
	public Agent end = null; //tracks the list end (or tail)
	public int length = 0; //tracks the length of the list
  
	// ============
	// CONSTRUCTORS
	// ============

	/**
	 * Constructor for empty LinkList
	 */
	public AgentList(){
		this.head = null;
		this.end = null;
		this.length = 0;
	}
  
	/**
	 * Constructor for non-empty LinkList
	 * @param newAgent as a Agent
	 * @param newEnd as a Agent
	 * @param newLength as an int
	 */
	public AgentList(Agent newAgent, Agent newEnd, int newLength){
		this.head = newAgent;
		this.end = newEnd;
		this.length = newLength;
	}
  
	// =======
	// GETTERS
	// =======

	/**
	 * Returns the length
	 * @return length as an int
	 */
	public final int length() {
		return this.length;
	}

	/**
	 * Returns whether LinkList is empty
	 * @return isEmpty as a boolean
	 */
	public final boolean isEmpty() {
		return (this.length <= 0);
	}

	// =========
	// UTILITIES
	// =========

	/**
	 * Adds a Agent to the end of the LinkList
	 * @param t as a Agent
	 */
	public final void appendAgent(Agent t) {
		if (head == null) {
			head = t;
			end = t;
			end.next = null;
		} else {
			end.next = t;
			end = t;
			end.next = null;
		}
		this.length++;
	}

	/**
	 * Removes the Agent at the head of the LinkList
	 */
	public final void removeAgent() {
		if (this.length > 1) {
			Agent second = this.head.next;
			head.next = null;
			this.head = second;
			this.length--;
		} else if (this.length > 0) {
			this.head = null;
			this.end = null;
			this.length--;
		}
	}

	/**
	 * Empties the LinkList completely
	 */
	public final void clear() {
		this.head = null;
		this.end = null;
		this.length = 0;
	}
	
	/**
	 * Appends another list onto this one
	 * @param l as an AgentList
	 */
	public final void appendList(AgentList l) {
		if (this.isEmpty() && !l.isEmpty()) {
			this.head = l.head;
			this.end = l.end;
			this.length = l.length;
		} else if (!l.isEmpty()) {
			this.end.next = l.head;
			this.end = l.end;
			this.length = this.length + l.length;
		}
	}

	// ======
	// OUTPUT
	// ======

	/**
	 * Prints out the list, starting with head
	 */
	public final void print() {
		Agent tmpAgent = this.head;
		while (tmpAgent != null){	
			System.out.println(tmpAgent);
			tmpAgent = tmpAgent.next;
		}
	}


}
