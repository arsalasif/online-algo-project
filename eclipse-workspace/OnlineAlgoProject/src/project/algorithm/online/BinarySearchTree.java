package project.algorithm.online;

public class BinarySearchTree {

    Node root; 
	BinarySearchTree() {  
        root = null;  
    } 
	
	// Inserts a key into BST
    void insert(int key) { 
       root = insertKey(root, key); 
    }

    
    Node insertKey(Node current, int key) { 
  
        // If tree is empty, root is the new key
        if (current == null) { 
        		return new Node(key); 
        } 
  
        // search using BST to find position to insert new node in
        if (key < current.key) 
        		current.left = insertKey(current.left, key); 
        else if (key > current.key) 
        		current.right = insertKey(current.right, key); 
        return current; 
    } 
    
    public int search(int key)
    {
    		return searchKey(root, key, 0);
    }
    
    // Performs search on BST, returns access cost when searched node is found
    public int searchKey(Node current, int key, int accessCost) 
    { 
    		// if key found at node
        if (current==null || current.key==key) 
            return accessCost; 
      
        // val is greater than root's key 
        if (current.key > key) 
            return searchKey(current.left, key, accessCost+1); 
      
        // val is less than root's key 
        return searchKey(current.right, key, accessCost+1); 
    } 
}
