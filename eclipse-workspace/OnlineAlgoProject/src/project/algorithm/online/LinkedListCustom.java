package project.algorithm.online;

// A class for LinkedList
public class LinkedListCustom {
	LinkedListNode head;
	
	// Add a node to LinkedList
	public void add(int k)
	{
		if(head == null)
		{
			head = new LinkedListNode(k);
		}
		else
		{
			LinkedListNode temp = head;
			while(temp.next != null)
			{
				temp = temp.next;
			}
			temp.next = new LinkedListNode(k);
		}
	}
	
	// Search the linked list and return access cost
	public int search(int k)
	{
		int accessCost = 0;
		LinkedListNode temp = head;
		while(temp != null && temp.key != k)
		{
			accessCost++;
			temp = temp.next;
		}
		return accessCost;
	}
	
	// Print the linked list
	public void print()
	{
		LinkedListNode temp = head;
		while(temp != null)
		{
			System.out.println(temp.key);
			temp = temp.next;
		}
	}
	
	// Search the linked list using MTF and get access cost
	public int searchMoveToFront(int k)
	{
		if(head.key == k)
			return 0;
		int accessCost = 1;
		LinkedListNode prev = head;
		while(prev != null && prev.next.key != k)
		{
			accessCost++;
			prev = prev.next;
		}
		// Move the accessed node to front
		moveToFront(k, prev);
		
		return accessCost;
	}
	
	// Move the accessed node to front
	public void moveToFront(int k, LinkedListNode prev)
	{
		LinkedListNode accessNode = prev.next;
		if(k != accessNode.key)
		{
			System.out.println("Error: Key not same.");
			return;
		}
		prev.next = accessNode.next;
		accessNode.next = head;
		head = accessNode;
	}

	// Search the linked list using Transpose and get access cost
	public int searchTranspose(int k)
	{
		if(head.key == k)
			return 0;
		int accessCost = 1;
		LinkedListNode parent = head;
		LinkedListNode grandParent = head;
		while(parent != null && parent.next.key != k)
		{
			accessCost++;
			grandParent = parent;
			parent = parent.next;
		}
		// if parent is root then just do MTF, else do transpose
		if(head == parent)
			moveToFront(k, head);
		else transpose(k, grandParent);
		
		return accessCost;
	}

	// Swap accessed node with parent i.e transpose
	public void transpose(int k, LinkedListNode grandParent)
	{
		LinkedListNode parent = grandParent.next;
		LinkedListNode accessNode = parent.next;
		if(k != accessNode.key)
		{
			System.out.println("Error: Key not same.");
			return;
		}
		parent.next = accessNode.next;
		accessNode.next = parent;
		grandParent.next = accessNode;
	}
	
}
