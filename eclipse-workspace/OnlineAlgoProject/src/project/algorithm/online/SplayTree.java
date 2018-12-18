package project.algorithm.online;

import java.util.LinkedList;
import java.util.Queue;

public class SplayTree {

    Node root; 
    int rotationCost = 0;
    SplayTree() {  
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
    
    void bfs()
    {
    		Queue<Node> queue = new LinkedList<Node>() ;
    		Node start = root;
    	    if (start == null)
    	        return;
    	    queue.clear();
    	    queue.add(start);
    	    while(!queue.isEmpty()){
    	    	Node node = queue.remove();
    	        System.out.print(node.key + " ");
    	        if(node.left != null) 
    	        	{
    	        		queue.add(node.left);
    	    	        System.out.print("Left: " + node.left.key + " ");
    	        	}
    	        if(node.right != null)
    	        	{
    	        		queue.add(node.right);
    	    	        System.out.print("Right: " + node.right.key);
    	        	}
    	        System.out.println();
    	    }
    }
    
    public int searchMTF(int key)
    {
		rotationCost = 0;
    		int cost = searchRec(root, key, 0);
    		root = splay(root, key);
    		return cost;
    }
    
    public int searchTranspose(int key)
    {
    		rotationCost = 0;
    		int cost = searchRec(root, key, 0);
    		Node grandParent = searchGrandParent(root, key);
    		if(grandParent.right != null)
    		{
        		if(grandParent.right.left != null && grandParent.right.left.key == key)
        		{
        			grandParent.right = splay(grandParent.right, key);
        			return cost;
        		}
        		if(grandParent.right.right != null && grandParent.right.right.key == key)
        		{
        			grandParent.right = splay(grandParent.right, key);
        			return cost;
        		}
    		}

    		if(grandParent.left != null)
    		{
        		if(grandParent.left.left != null && grandParent.left.left.key == key)
        		{
        			grandParent.left = splay(grandParent.left, key);
        			return cost;
        		}
        		if(grandParent.left.right != null && grandParent.left.right.key == key)
        		{
        			grandParent.left = splay(grandParent.left, key);
        			return cost;
        		}
    		}
    		
    		return cost;
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
    
    // A utility function to search a given key in BST 
    public Node searchGrandParent(Node current, int key) 
    { 
        // Base Cases: root is null or key is present at root 
        if (current==null || current.key==key) 
            return current; 
        
        if(current.right != null)
		{
	    		if(current.right.left != null && current.right.left.key == key)
	    			return current;
	    		if(current.right.right != null && current.right.right.key == key)
	    			return current;
		}
        if(current.left != null)
		{
	    		if(current.left.left != null && current.left.left.key == key)
	    			return current;
	    		if(current.left.right != null && current.left.right.key == key)
	    			return current;
		}
        
        // val is greater than root's key 
        if (current.key > key) 
            return searchGrandParent(current.left, key); 
      
        // val is less than root's key 
        return searchGrandParent(current.right, key); 
    } 
    
    private Node splay(Node h, Integer key) {
        if (h == null) return null;

        int cmp1 = key.compareTo(h.key);
        // if key < h.key
        if (cmp1 < 0) {
            // key not in tree, so we're done
            if (h.left == null) {
                return h;
            }
            int cmp2 = key.compareTo(h.left.key);
            // if key is less than left child of h's key
            if (cmp2 < 0) {
                h.left.left = splay(h.left.left, key);
                h = rotateRight(h);
            }// if key is greater than left child of h's key
            else if (cmp2 > 0) {
                h.left.right = splay(h.left.right, key);
                if (h.left.right != null)
                    h.left = rotateLeft(h.left);
            }
            
            if (h.left == null) return h;
            else                return rotateRight(h);
        }
        // if key > h.key
        else if (cmp1 > 0) { 
            // key not in tree, so we're done
            if (h.right == null) {
                return h;
            }

            int cmp2 = key.compareTo(h.right.key);
            // if key is less than right child of h's key
            if (cmp2 < 0) {
                h.right.left  = splay(h.right.left, key);
                if (h.right.left != null)
                    h.right = rotateRight(h.right);
            }// if key is greater than right child of h's key
            else if (cmp2 > 0) {
                h.right.right = splay(h.right.right, key);
                h = rotateLeft(h);
            }
            
            if (h.right == null) return h;
            else                 return rotateLeft(h);
        }
        // else we found key at this node
        else return h;
    }
    
    
 // right rotate
    private Node rotateRight(Node h) {
    		rotationCost++;
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    // left rotate
    private Node rotateLeft(Node h) {
		rotationCost++;
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }
}
