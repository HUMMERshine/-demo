package priv.lst.leetcod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class BinaryTree {

	private static TreeNode root = init();

	public static TreeNode init() {
		TreeNode a = new TreeNode('A');
		TreeNode b = new TreeNode('B', null, a);
		TreeNode c = new TreeNode('C');
		TreeNode d = new TreeNode('D', b, c);
		TreeNode e = new TreeNode('E');
		TreeNode f = new TreeNode('F', e, null);
		TreeNode g = new TreeNode('G', null, f);
		TreeNode h = new TreeNode('H', d, g);
		return h;// root
	}

	public static void main(String[] args) {
		System.out.printf("%-10s", "递归先序遍历:");
		preorder(root);
		System.out.println();
		System.out.printf("%-10s", "递归中序遍历:");
		inorder(root);
		System.out.println();
		System.out.printf("%-10s", "递归后序遍历:");
		postorder(root);
		System.out.println();
		System.out.print("非递归先序遍历1:");
		preorder1(root);
		System.out.println();
		System.out.print("非递归先序遍历2:");
		preorder2(root);
		System.out.println();
		System.out.print("非递归中序遍历1:");
		inorder1(root);
		System.out.println();
		System.out.print("非递归后序遍历1:");
		postorder1(root);
		System.out.println();
		System.out.print("非递归后序遍历2:");
		postorder1(root);
		System.out.println();
		preorder(invertTree(root));
		lowestCommonAncestor(root, new TreeNode('G'), new TreeNode('A'));
	}

	public static void visit(TreeNode p) {
		System.out.print(p.getKey() + " ");
	}

	protected static void preorder(TreeNode p) {
		if (p != null) {
			visit(p);
			preorder(p.getLeft());
			preorder(p.getRight());
		}
	}

	protected static void preorder1(TreeNode p) {
		Stack<TreeNode> stack = new Stack<TreeNode>();

		while (p != null || !stack.isEmpty()) {
			while (p != null) {
				visit(p);
				stack.push(p);
				p = p.getLeft();
			}

			if (!stack.isEmpty()) {//在先序遍历中该条件不必要
				p = stack.pop();// 左子树已经全被访问过，就出栈。
				p = p.getRight();
			}
		}

	}
	
	protected static void preorder2(TreeNode p) {
		Stack<TreeNode> stack = new Stack<TreeNode>();

		if (p != null) {
			stack.push(p);
			while (!stack.isEmpty()) {
				p = stack.pop();
				visit(p);
				if (p.getRight() != null) {
					stack.push(p.getRight());
				}
				if (p.getLeft() != null) {
					stack.push(p.getLeft());
				}
			}
		}
	}
	
	protected static void inorder(TreeNode p) {
		if (p != null) {
			inorder(p.getLeft());
			visit(p);
			inorder(p.getRight());
		}
	}
	
	protected static void inorder1(TreeNode p) {
		Stack<TreeNode> stack = new Stack<TreeNode>();

		while (p != null || !stack.isEmpty()) {
			while (p != null) {
				stack.push(p);
				p = p.getLeft();
			}

			if (!stack.isEmpty()) {
				p = stack.pop();// 左子树已经全被访问过，就出栈。
				visit(p);
				p = p.getRight();
			}
		}
	}

	/** 递归实现后序遍历 */
	protected static void postorder(TreeNode p) {
		if (p != null) {
			postorder(p.getLeft());
			postorder(p.getRight());
			visit(p);
		}
	}
	/** 非递归后序遍历*/
	protected static void postorder1(TreeNode p) {
		LinkedList<TreeNode> stack = new LinkedList<>();
		TreeNode pre = null;
		
		while(!stack.isEmpty() || p != null){
			while(p != null){
				stack.push(p);
				p = p.left;
			}
			
			if(!stack.isEmpty()){
				TreeNode node = stack.peek().right;//保存右子树的根节点。
				if(node == null || pre == node){//判断右子树是否存在，或已经遍历过。之后遍历过右子树才能输出该节点。
					p = stack.pop();
					visit(p);
					pre = p;
					p = null;//关键点
				}else{
					p = node;//如果没有遍历过右子树，开始遍历右子树。
				}
			}
		}
	}
	
	/** 非递归后序遍历2*/
	protected static void postorder2(TreeNode p) {//按照先根遍历的思路，1：先把左右子树访问顺序调换，2：最后把整个输出顺序翻转就行了。
		Stack<TreeNode> stack = new Stack<TreeNode>();
		LinkedList<Character> list = new LinkedList<>();
		while (p != null || !stack.isEmpty()) {
			while (p != null) {
				//visit(p);
				list.add(0, (Character)p.val);//全链表翻转，就是后序遍历。
				stack.push(p);
				p = p.getRight();//先右递归。
			}

			if (!stack.isEmpty()) {//在先序遍历中该条件不必要
				p = stack.pop();// 右子树已经全被访问过，就出栈。
				p = p.getLeft();//再左子树递归。
			}
		}
		
		System.out.println(list);
	}
	//左右转换树
	protected static TreeNode invertTree(TreeNode root) {
		TreeNode p = null;
		if (root != null) {
			p = new TreeNode(root.val);
			if (root.left != null) {
				p.right = invertTree(root.left);
			}
			if (root.right != null) {
				p.left = invertTree(root.right);
			}
		}
		return p;
	}

	private static boolean flag = true;

	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p,
			TreeNode q) {
		if (p == null || q == null || root == null) {
			return null;
		}

		LinkedList<TreeNode> list1 = new LinkedList<>();
		preorder(root, p, list1);
		flag = true;
		LinkedList<TreeNode> list2 = new LinkedList<>();
		preorder(root, q, list2);

		for (TreeNode tn : list1) {
			System.out.println("array1:" + tn.val);
		}
		for (TreeNode tn : list2) {
			System.out.println("array2:" + tn.val);
		}

		int i = 0;
		while (i < list1.size() && i < list2.size()
				&& list1.get(i) == list2.get(i)) {
			i++;
		}

		if (i == 0) {
			return null;
		} else {
			System.out.println(list1.get(i - 1).val);
			return list1.get(i - 1);
		}
	}

	public static void preorder(TreeNode root, TreeNode key,
			LinkedList<TreeNode> list) {
		if (flag) {
			list.addLast(root);
			if (root.val == key.val) {
				flag = false;
			}
			if (root.left != null) {
				preorder(root.left, key, list);
			}
			if (root.right != null) {
				preorder(root.right, key, list);
			}
			if (flag)
				list.removeLast();
		}
	}
}
