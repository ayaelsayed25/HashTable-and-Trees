public class FinalAssignmentSolution {
	public class MiscUtils implements IMiscUtils{

		boolean flag = false;
		int temp;
		@Override
		public BinaryTreeNode insert(BinaryTreeNode root, int element) {
			//Creating a new Node 
			BinaryTreeNode newNode = new BinaryTreeNode(element);
			//if the tree is empty : set the node as the root
			if(root == null)
				root = newNode;
			else
			{
				BinaryTreeNode focusNode = root;
				BinaryTreeNode parent;
				//traversing throw the tree:
				while(true)
				{
					parent = focusNode;
					//if so, move to the left side 
					if(element < (int)focusNode.element)
					{
						focusNode = focusNode.leftChild;
						if(focusNode == null) 
						{
							parent.leftChild = newNode; 
							break;
						}
					}
					//if the key already exists, don't do anything
					else if(element == (int)focusNode.element) /////////FOR DUPLICATE KEYS/////////////
							break;
					else
					{
						//if element > focusNode.element, move to the right side 
						focusNode = focusNode.rightChild;
						if(focusNode == null)
						{
							parent.rightChild = newNode;
							break;
						}
					}
				}
			}
			return root;
		}
		
		
		@Override
		public int sumRange(BinaryTreeNode root, int low, int high) {
			return sum(0, root, low, high); //call a private method to calculate the sum
		}
		private int sum(int sum, BinaryTreeNode root, int low, int high) {
			if(root != null)
			{
				sum = sum(sum, root.leftChild, low, high);
				if((int)root.element >= low && (int)root.element <= high) //check each node : if it satisfies the condition add it to the sum. complexity O(N)
					sum = sum + (int)root.element;
				sum = sum(sum, root.rightChild, low, high);
			}
			return sum; //return the sum
		}
		
		
		@Override
		public boolean isValidBST(BinaryTreeNode root) //TRYING TO USE THE PROPERTIES OF INORDER TRAVERSAL 
		{
			return isValid(true, root); //calls a recursive function
		}
		
		private boolean isValid(boolean b, BinaryTreeNode root)
		{
			if(root != null)
			{
				b = isValid(b, root.leftChild); 
				if(!flag)
				{
					temp = (int) root.element;   //save the leftmost value in temp and don't change temp here again! >> flag is changed.
					flag = true;
				}
				if( (int)root.element < (int) temp )  //if the two consecutive inorder values do not satisfy the condition return false
						b = false;
				temp = (int) root.element; //update temp 
				b = isValid(b, root.rightChild);
			}
			return b;
		}
		
		
		@Override
		public int[] nextSmallerNumber(int[] array) {
			if(array == null)
				throw new NullPointerException("Array is empty");
			int l = array.length;
			int[] arr = new int[l];
			QueueLinked q = new QueueLinked(); 
			for(int i=0; i<l; i++)
			{
				q.enqueue(i); //moving each index to the queue
			}
			int count = 1;
			boolean flag;
			while (true)
			{
				flag = false;
				int size = q.size(); //update the size of queue
				for(int i=0; i<size; i++)
				{
					int index = (int)q.front();
					if(index + count >= l ) 
					{
						arr[index] = -1;  //we reached to the end of array : assign arr[index] to -1
						q.dequeue();      //then dequeue it    
					}
					else if(array[index + count] < array[index])
					{
						arr[index] = index + count; //we found the next smaller number : assign the index to arr 
						q.dequeue();   //then dequeue it
					}
					else
					{
						q.enqueue(q.dequeue());   //else : dequeue it and enqueue it again : move it to the back
					}
					flag = true; // flag is true if size of queue is not zero
				}
				if(!flag) //if flag = false : the queue is empty
					break;
				else
					count++; 
			}
			return arr;
		}

	}
	public class HashTableDictionary <K, V> implements IDictionary<K,V>{

		HashData[] theArray;
		int arraySize;
		public HashTableDictionary(int size)
		{
			this.arraySize = size; //Size of hash table
			theArray = new HashData[size]; //intializing the array
		}

		@Override
		public V get(K key) {
			if(key == null)
				throw new NullPointerException("Key is null");
			int hashKey = (int)key % arraySize;
			if(theArray[hashKey].key == key) //if the key of array at the hash key equals the key we're searching for return the value
				return (V) theArray[hashKey].value;
			else //move through the attached linked list and return the value
			{
				HashData temp = theArray[hashKey];
				while(temp.next != null)
				{
					temp = temp.next;
					if(temp.key == key)
						return (V) temp.value;
				}
			}
			return null; //return null if the key is not found
		}

		@Override
		public V set(K key, V value) {
			if(key ==null || value == null) //throw an exception if any of these is null
				throw new NullPointerException("The enteries are null");
			HashData data = new HashData(key, value); 
			int hashKey =(int) key % arraySize; //get the hashKey by getting the modulus
			if(theArray[hashKey] == null) //if the array is empty at hash key, insert the data
				theArray[hashKey] = data;
			else //else move through the attached linkedList and put it in the right place
			{
				boolean flag = false; //flag is true if the key already exists
				HashData temp = theArray[hashKey];
				if(temp.key == key)
					flag = true;
				while(!flag && temp.next != null)
				{
					temp = temp.next;
					if(temp.key == key)
						flag = true;
				}
				if(flag)
				{
					V v = (V) temp.value; //Update the value
					temp.value = value;
					return v; //return the old value
				}
				temp.next = data;
			}
			return null;
		}

		@Override
		public V remove(K key) {
			if(key == null)
				throw new NullPointerException("Key is Null");
			int hashKey = (int)key % arraySize;
			V valueRemoved;
			if(theArray[hashKey] == null) //if there is no data with the hash key return null
				return null;
			HashData data = theArray[hashKey];
			if(data.key == key)  //if the two key are equal
			{
				valueRemoved = (V)data.value;
				theArray[hashKey] = data.next; //assign the array at hash key to the next element
				return valueRemoved;
			}
			else //move through the attached linked list and search for the key
			{
				HashData previous = data; 
				while(data.next != null)
				{
					previous = data;
					data = data.next;
					if(data.key == key)
					{
						valueRemoved = (V) data.value;
						previous.next = data.next;
						return valueRemoved;
					}
				}
			}
			
			return null; //return null if the key is not found
		}

		@Override
		public boolean isEmpty() {
			for(int i=0; i<arraySize; i++)
			{
				if(theArray[i] != null) //if an element does not equal null, the array is not empty
					return false;
			}
			return true;
		}

	}

	public class TreeDictionary<K extends Comparable <K>, V> implements IDictionary<K, V>
	{
		BNode root;
		@Override
		public V get(K key) {
			if(key == null) 
				throw new NullPointerException("Key is null");
			BNode focusNode = root; //start from the root
			while(focusNode != null)
			{
				int cmp = key.compareTo((K) focusNode.key); 
				if(cmp == 0)  //if the key and the key of the current node are equal return the value
				{
					return (V) focusNode.value;
				}
				else if(cmp < 0)   //move to the left side
					focusNode = focusNode.leftChild;
				else //move to the right side
					focusNode = focusNode.rightChild;
			}
			return null; //return null if the key doesn't exist
		}

		@Override
		public V set(K key, V value) {
			if(key == null || value == null)
				throw new NullPointerException("You entered null values");
			//Creating a new Node 
			BNode newNode = new BNode(key, value);
			if(root == null)   		//if the tree is empty : set the node as the root
			{
				root = newNode;
				return null;
			}
			else
			{
				BNode focusNode = root;
				BNode parent;
				//traversing throw the tree:
				while(true)
				{
					parent = focusNode;
					int cmp = key.compareTo((K) focusNode.key);
					if(cmp < 0)    //if so, move to the left side 
					{
						focusNode = focusNode.leftChild;
						if(focusNode == null)
						{
							parent.leftChild = newNode;
							return null;
						}
					}
					else if(cmp == 0) /////////FOR DUPLICATE KEYS/////////////
					{
						V v = (V) focusNode.value;
						focusNode.value = newNode.value;
						return v ; //if the key already exists, freturn the old value
					}
					else  //if element > focusNode.element, move to the right side 
					{
						focusNode = focusNode.rightChild;
						if(focusNode == null)
						{
							parent.rightChild = newNode;
							return null;
						}
					}
				}
			}
		}

		@Override
		public V remove(K key) {
			if(key == null)
				throw new NullPointerException("Key is null");
			V v = null ;
			BNode focusNode = root;
			BNode parent = root;
			boolean isLeftChild = true;
			while(key.compareTo((K) focusNode.key) != 0)
			{
				parent = focusNode;
				if(key.compareTo((K) focusNode.key) < 0)
				{
					isLeftChild = true;
					focusNode = focusNode.leftChild;
				}
				else
				{
					isLeftChild = false;
					focusNode = focusNode.rightChild;
				}
				if(focusNode == null)
					return null;
				v = (V) focusNode.value;
			}
			//As leaf :
			if(focusNode.leftChild == null && focusNode.rightChild == null)
			{
				if(focusNode == root)
					root = null;
				else if(isLeftChild)
					parent.leftChild = null;
				else
					parent.rightChild = null;
				
			}
			else if(focusNode.rightChild == null)
			{
				if(focusNode == root)
					root = focusNode.leftChild;
				else if(isLeftChild)
					parent.leftChild = focusNode.leftChild;
				else
					parent.rightChild = focusNode.leftChild;
			}
			else if(focusNode.leftChild == null)
			{
				if(focusNode == root)
					root = focusNode.rightChild;
				else if(isLeftChild)
					parent.leftChild = focusNode.rightChild;
				else
					parent.rightChild = focusNode.rightChild;
			}
			else
			{
				BNode replacement = getReplacementNode(focusNode);
				if(focusNode == root)
				{
					root = replacement;
				}
				else if(isLeftChild)
				{
					parent.leftChild = replacement;
				}
				else
				{
					parent.rightChild = replacement;
				}
				replacement.leftChild = focusNode.leftChild;
			}
			return v;
		}
		public BNode getReplacementNode(BNode replacedNode)
		{ 
			BNode replacementParent = replacedNode;
			BNode replacement = replacedNode;
			BNode focusNode = replacedNode.rightChild;
			while(focusNode != null)
			{
				replacementParent = replacement;
				replacement = focusNode;
				focusNode = focusNode.leftChild;
			}
			if (replacement != replacedNode.rightChild)
			{
				replacementParent.leftChild = replacement.rightChild;
	            replacement.rightChild = replacedNode.rightChild;
			}
			return replacement;
		}
		

		
		@Override
		public boolean isEmpty() {

			return (root == null);
		}

	}

	public class QueueLinked{
	    Node front;
	    Node rear;
	    int size =0;

	    public int size()
	    {
	        return size;
	    }

	    
	    public boolean isEmpty()
	    {
	        return(front == null);
	    }
	    
	    public void enqueue(Object data)
	    {
	        Node n = new Node(data);
	        if(isEmpty())
	            front = rear = n;
	        else
	        {
	            rear.setNext(n);
	            rear = rear.getNext();
	        }
	        size++;
	    }
	    public Object dequeue()
	    {
	        if(isEmpty())
	            throw new NullPointerException("The queue is already empty!");
	        Object data = front.getData();
	        front = front.getNext();
	        size--;
	        if(front == null)
	            rear = null;
	        return data;
	    }
	    public Object front()
	    {
	        if(isEmpty())
	            throw new NullPointerException("The queue is already empty!");
	        Object data = front.getData();
	        return data;
	    }
	    public void show()
	    {
	    	if(isEmpty())
	    		System.out.println("Nothing to show!");
	    	else
	    	{
	    		Node n = front;
	            while(n != null)
	            {
	                System.out.print(n.getData() + " ");
	                n = n.getNext();
	            } 		
	    	}
	    }
	}
	class Node
	{
		Object data;
		Node next;
		public Node(Object data)
		{
			this.data = data;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}

	}
	class HashData <K,V>{
		K key;
		V value;
		HashData next;
		public HashData(K key, V value)
		{
			this.key = key;
			this.value = value;
		}
		public String toString()
		{
			return "key: " + key + ", Value: " + value;
		}
	}
	class BNode<K,V>
	{
		K key;
		V value;
		BNode leftChild;
		BNode rightChild;
		public BNode(K key, V value)
		{
			this.key = key;
			this.value = value;
		}
	}

}


