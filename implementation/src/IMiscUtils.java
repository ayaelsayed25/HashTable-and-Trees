
public interface IMiscUtils {
	/**
	* Inserts an integer value in a BST of integers.
	* Result should be a valid BST.
	* BST has no duplicates.
	*
	*@param root: BST root, a valid BST of integers
	*@param element: integer value to insert.
	*
	* */
	public BinaryTreeNode insert(BinaryTreeNode root, int element);
	/**
	* Returns the sum of the elements in the tree in
	* the specified range [low, high] inclusive.
	*
	*
	*@param root: BST root, a valid BST of integers.
	*@param low: range lower limit.
	*@param high: range upper limit.
	*
	* */
	public int sumRange(BinaryTreeNode root, int low, int high);
	/**
	 * Returns true if the input is a valid BST, false otherwise
	 * @param root: Tree root.
	 *
	 * */
	public boolean isValidBST(BinaryTreeNode root);
	/**
	 * Given an array of integers, return an array containing
	 * the indices of the next smaller number of every number
	 * or -1 if the next smaller number does not exist. .
	 * @param array: array of numbers.
	 * @throws: throws an exception in the input array is null.
	 * */
	/*
Example:
input: 10, 9, 2, 7, 6, 1, 2
output: 1, 2, 5, 4, 5, -1, -1
Your algorithm should run in O(n) time,
where n is the size of the input array.
	 */
	public int[] nextSmallerNumber(int[] array);

}
