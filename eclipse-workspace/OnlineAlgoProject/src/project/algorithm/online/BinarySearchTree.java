package project.algorithm.online;

public class BinarySearchTree {

    Node root; 
	BinarySearchTree() {  
        root = null;  
    } 
	
	// This method mainly calls insertRec() 
    void insert(int key) { 
       root = insertRec(root, key); 
    }
    /* A recursive function to insert a new key in BST */
    Node insertRec(Node current, int key) { 
  
        /* If the tree is empty, return a new node */
        if (current == null) { 
        		return new Node(key); 
        } 
  
        /* Otherwise, recur down the tree */
        if (key < current.key) 
        		current.left = insertRec(current.left, key); 
        else if (key > current.key) 
        		current.right = insertRec(current.right, key); 
  
        /* return the (unchanged) node pointer */
        return current; 
    } 
    
    // This method mainly calls InorderRec() 
    void inorder()  { 
       inorderRec(root); 
    } 
  
    // A utility function to do inorder traversal of BST 
    void inorderRec(Node root) { 
        if (root != null) { 
            inorderRec(root.left); 
            System.out.println(root.key); 
            inorderRec(root.right); 
        } 
    } 
    
    public int search(int key)
    {
    		return searchRec(root, key, 0);
    }
    
    // A utility function to search a given key in BST 
    public int searchRec(Node current, int key, int accessCost) 
    { 
        // Base Cases: root is null or key is present at root 
        if (current==null || current.key==key) 
            return accessCost; 
      
        // val is greater than root's key 
        if (current.key > key) 
            return searchRec(current.left, key, accessCost+1); 
      
        // val is less than root's key 
        return searchRec(current.right, key, accessCost+1); 
    } 
}
