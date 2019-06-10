import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author wj
 * @description
 * @date 2019/6/9 19:34
 */
public class BinTree {
	private int [] vals = {1,2,3,4,5,6,7,8,9};
	private static List<TreeNode> nodeList = new ArrayList<>();

	static class TreeNode{
		int val;
		TreeNode leftNode;
		TreeNode rightNode;

		TreeNode(int val){
			this.val = val;
			leftNode = null;
			rightNode = null;
		}
	}

	private void createBinTree(){
		for (int val : vals) {
			nodeList.add(new TreeNode(val));
		}

		// 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
		for (int parentIndex = 0; parentIndex < vals.length / 2 - 1; parentIndex++) {
			// 左孩子
			nodeList.get(parentIndex).leftNode = nodeList
					.get(parentIndex * 2 + 1);
			// 右孩子
			nodeList.get(parentIndex).rightNode = nodeList
					.get(parentIndex * 2 + 2);
		}
		// 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理
		int lastParentIndex = vals.length / 2 - 1;
		// 左孩子
		nodeList.get(lastParentIndex).leftNode = nodeList.get(lastParentIndex * 2 + 1);
		// 右孩子,如果数组的长度为奇数才建立右孩子
		if (vals.length % 2 == 1) {
			nodeList.get(lastParentIndex).rightNode = nodeList.get(lastParentIndex * 2 + 2);
		}
	}

	/**
	 * 先序遍历
	 *
	 * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
	 *
	 * @param node
	 *            遍历的节点
	 */
	public static void preOrderTraverse(TreeNode node) {
		if (node == null) {
			return;
		}
		System.out.print(node.val + " ");
		preOrderTraverse(node.leftNode);
		preOrderTraverse(node.rightNode);
	}

	/**
	 * 中序遍历
	 *
	 * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
	 *
	 * @param node
	 *            遍历的节点
	 */
	public static void inOrderTraverse(TreeNode node) {
		if (node == null) {
			return;
		}
		inOrderTraverse(node.leftNode);
		System.out.print(node.val + " ");
		inOrderTraverse(node.rightNode);
	}

	/**
	 * 后序遍历
	 *
	 * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
	 *
	 * @param node
	 *            遍历的节点
	 */
	public static void postOrderTraverse(TreeNode node) {
		if (node == null) {
			return;
		}
		postOrderTraverse(node.leftNode);
		postOrderTraverse(node.rightNode);
		System.out.print(node.val + " ");
	}

	public static void preOreder(TreeNode node){
		if (node == null) {
			return;
		}

		Stack<TreeNode> stack = new Stack<>();
		stack.push(node);

		while (!stack.isEmpty()){
			TreeNode treeNode = stack.pop();

			System.out.print(treeNode.val + " ");

			if(treeNode.rightNode != null){
				stack.push(treeNode.rightNode);
			}

			if(treeNode.leftNode != null){
				stack.push(treeNode.leftNode);
			}
		}
	}

	/**
	 * 先序非递归2：
	 * 利用栈模拟递归过程实现循环先序遍历二叉树。
	 * 这种方式具备扩展性，它模拟递归的过程，将左子树点不断的压入栈，直到null，
	 * 然后处理栈顶节点的右子树。
	 *
	 * @param root
	 */
	public static void preOrderStack2(TreeNode root) {
		Stack<TreeNode> stack = new Stack<>();
		TreeNode treeNode = root;
		while (treeNode != null || !stack.isEmpty()) {
			//将左子树点不断的压入栈
			while (treeNode != null) {
				//先访问再入栈
				System.out.print(treeNode.val + " ");
				stack.push(treeNode);
				treeNode = treeNode.leftNode;
			}
			//出栈并处理右子树
			if (!stack.isEmpty()) {
				treeNode = stack.pop();
				treeNode = treeNode.rightNode;
			}

		}
	}

	/**
	 * 中序非递归：
	 * 利用栈模拟递归过程实现循环中序遍历二叉树。
	 * 思想和上面的先序非递归2相同，
	 * 只是访问的时间是在左子树都处理完直到null的时候出栈并访问。
	 *
	 * @param treeNode
	 */
	public static void inOrderStack(TreeNode treeNode) {
		Stack<TreeNode> stack = new Stack<>();
		while (treeNode != null || !stack.isEmpty()) {
			while (treeNode != null) {
				stack.push(treeNode);
				treeNode = treeNode.leftNode;
			}
			//左子树进栈完毕
			if (!stack.isEmpty()) {
				treeNode = stack.pop();
				System.out.print(treeNode.val + " ");
				treeNode = treeNode.rightNode;
			}
		}
	}

	public static class TagNode {
		TreeNode treeNode;
		boolean isFirst;
	}

	/**
	 * 后序非递归：
	 * 后序遍历不同于先序和中序，它是要先处理完左右子树，
	 * 然后再处理根(回溯)。
	 * <p>
	 * <p>
	 * 对于任一结点P，将其入栈，然后沿其左子树一直往下搜索，直到搜索到没有左孩子的结点，
	 * 此时该结点出现在栈顶，但是此时不能将其出栈并访问，因此其右孩子还为被访问。
	 * 所以接下来按照相同的规则对其右子树进行相同的处理，当访问完其右孩子时，该结点又出现在栈顶，
	 * 此时可以将其出栈并访问。这样就保证了正确的访问顺序。
	 * 可以看出，在这个过程中，每个结点都两次出现在栈顶，只有在第二次出现在栈顶时，才能访问它。
	 * 因此需要多设置一个变量标识该结点是否是第一次出现在栈顶，这里是在树结构里面加一个标记，然后合成一个新的TagNode。
	 *
	 * @param treeNode
	 */
	public static void postOrderStack(TreeNode treeNode) {
		Stack<TagNode> stack = new Stack<>();
		TagNode tagNode;
		while (treeNode != null || !stack.isEmpty()) {
			//沿左子树一直往下搜索，直至出现没有左子树的结点
			while (treeNode != null) {
				tagNode = new TagNode();
				tagNode.treeNode = treeNode;
				tagNode.isFirst = true;
				stack.push(tagNode);
				treeNode = treeNode.leftNode;
			}

			if (!stack.isEmpty()) {
				tagNode = stack.pop();
				//表示是第一次出现在栈顶
				if (tagNode.isFirst) {
					tagNode.isFirst = false;
					stack.push(tagNode);
					treeNode = tagNode.treeNode.rightNode;
				} else {
					//第二次出现在栈顶
					System.out.print(tagNode.treeNode.val + " ");
					treeNode = null;
				}
			}
		}
	}

	/**
	 * 后序非递归2：
	 * 要保证根结点在左孩子和右孩子访问之后才能访问，因此对于任一结点P，先将其入栈。如果P不存在左孩子和右孩子，则可以直接访问它；
	 * 或者P存在左孩子或者右孩子，但是其左孩子和右孩子都已被访问过了，则同样可以直接访问该结点。
	 * 若非上述两种情况，则将P的右孩子和左孩子依次入栈，这样就保证了每次取栈顶元素的时候，左孩子在右孩子前面被访问，
	 * 左孩子和右孩子都在根结点前面被访问。
	 *
	 * @param treeNode
	 */
	public static void postOrderStack2(TreeNode treeNode) {
		Stack<TreeNode> stack = new Stack<>();
		TreeNode currentTreeNode;
		TreeNode preTreeNode = null;
		stack.push(treeNode);

		while (!stack.isEmpty()) {
			currentTreeNode = stack.peek();
			//如果当前结点没有孩子结点或者孩子节点都已被访问过
			if ((currentTreeNode.leftNode == null && currentTreeNode.rightNode == null) || (preTreeNode != null && (preTreeNode == currentTreeNode.leftNode || preTreeNode == currentTreeNode.rightNode))) {
				System.out.print(currentTreeNode.val + " ");
				stack.pop();
				preTreeNode = currentTreeNode;
			} else {
				if (currentTreeNode.rightNode != null) {
					stack.push(currentTreeNode.rightNode);
				}
				if (currentTreeNode.leftNode != null) {
					stack.push(currentTreeNode.leftNode);
				}
			}
		}
	}

	public static void main(String[] args) {
		BinTree binTree = new BinTree();
		binTree.createBinTree();

		TreeNode root = nodeList.get(0);

		System.out.println("先序遍历：");
		preOrderTraverse(root);
		System.out.println();
		preOreder(root);
		System.out.println();
		preOrderStack2(root);
		System.out.println();

		System.out.println("中序遍历：");
		inOrderTraverse(root);
		System.out.println();
		inOrderStack(root);
		System.out.println();

		System.out.println("后序遍历：");
		postOrderTraverse(root);
		System.out.println();
		postOrderStack(root);
		System.out.println();
		postOrderStack2(root);
	}
}
