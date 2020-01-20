package problems.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * [HARD]
 * 145. 二叉树的后序遍历
 * 给定一个二叉树，返回它的 后序 遍历。
 *
 * 示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [3,2,1]
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-postorder-traversal
 *
 * 相关练习：
 * 94. 二叉树的中序遍历
 * 144. 二叉树的前序遍历
 * 102. 二叉树的层次遍历
 * 107. 二叉树的层次遍历 II
 *
 * @author kyan
 * @date 2020/1/4
 */
public class No145_BinaryTreePostorderTraversal {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    /**
     * 递归思想
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        postorderTraversalInternally(root, list);
        return list;
    }

    private void postorderTraversalInternally(TreeNode root, List<Integer> list) {
        if (root == null) return;
        postorderTraversalInternally(root.left, list);
        postorderTraversalInternally(root.right, list);
        list.add(root.val);
    }

    /**
     * 非递归思想：依靠栈完成后续遍历，和前序遍历区别在于返回队列用头插
     *      1
     *     / \
     *    2   3
     *   / \ / \
     *  4  5 6  7
     * 根节点先入栈，然后出栈判断是否存在左右子节点，如果有，先左子节点入栈，再右子节点入栈，直到栈空为止
     * 入栈[1]    队列[]
     * 出栈[]     队列[1]
     * 入栈[2,3]  队列[1]
     * 出栈[2]    队列[3,1]
     * 入栈[2,6,7]队列[3,1]
     * 出栈[2,6]  队列[7,3,1]
     * 出栈[2]    队列[6,7,3,1]
     * 出栈[]     队列[2,6,7,3,1]
     * 入栈[4,5]  队列[2,6,7,3,1]
     * 出栈[4]    队列[5,2,6,7,3,1]
     * 出栈[]     队列[4,5,2,6,7,3,1]
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversalNonRecursively(TreeNode root) {
        LinkedList<Integer> list = new LinkedList<>();
        if (root == null) return list;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode currNode = stack.pop();
            list.addFirst(currNode.val);
            if (currNode.left != null) {
                stack.push(currNode.left);
            }
            if (currNode.right != null) {
                stack.push(currNode.right);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        //     1
        //    / \
        //   2   3
        //  / \ / \
        // 4  5 6  7
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        No145_BinaryTreePostorderTraversal solution = new No145_BinaryTreePostorderTraversal();
        System.out.println(solution.postorderTraversal(root)); //[4, 5, 2, 6, 7, 3, 1]
        System.out.println(solution.postorderTraversalNonRecursively(root));
    }
}
