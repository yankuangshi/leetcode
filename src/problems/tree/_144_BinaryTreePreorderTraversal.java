package problems.tree;

import problems.common.util.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 144. 二叉树的前序遍历
 * 难度：中等
 *
 * 给定一个二叉树，返回它的 前序 遍历。
 *
 *  示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,2,3]
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 *
 * 相关练习:
 * 94. 二叉树的中序遍历
 * 102. 二叉树的层次遍历
 * 107. 二叉树的层次遍历 II
 * 145. 二叉树的后序遍历
 *
 * @author kyan
 * @date 2020/1/4
 */
public class _144_BinaryTreePreorderTraversal {

    /**
     * 递归思想
     */
    public static class Solution1 {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            preorderTraversalInternally(root, list);
            return list;
        }

        private void preorderTraversalInternally(TreeNode root, List<Integer> list) {
            if (root == null) return;
            list.add(root.val);
            preorderTraversalInternally(root.left, list);
            preorderTraversalInternally(root.right, list);
        }

    }

    /**
     * 非递归思想：依靠栈来完成遍历前序遍历
     *      1
     *     / \
     *    2   3
     *   / \ / \
     *  4  5 6  7
     * 根节点先入栈，每次出栈检查出栈节点是否含有左右节点，如果有，则右节点先入栈，左节点再入栈，直到栈清空为止
     * 入栈[1]    队列[]
     * 出栈[]     队列[1]
     * 入栈[3,2]  队列[1]
     * 出栈[3]    队列[1,2]
     * 入栈[3,5,4]队列[1,2]
     * 出栈[3,5]  队列[1,2,4]
     * 出栈[3]    队列[1,2,4,5]
     * 出栈[]     队列[1,2,4,5,3]
     * 入栈[7,6]  队列[1,2,4,5,3]
     * 出栈[7]    队列[1,2,4,5,3,6]
     * 出栈[]     队列[1,2,4,5,3,6,7]
     */
    public static class Solution2 {

        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            if (root == null) return list;
            LinkedList<TreeNode> stack = new LinkedList<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                list.add(node.val);
                //先右子节点入栈
                if (node.right != null) {
                    stack.push(node.right);
                }
                //再左子节点入栈
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
            return list;
        }
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
        System.out.println(new Solution1().preorderTraversal(root));
        System.out.println(new Solution2().preorderTraversal(root));
        //[1, 2, 4, 5, 3, 6, 7]
    }
}
