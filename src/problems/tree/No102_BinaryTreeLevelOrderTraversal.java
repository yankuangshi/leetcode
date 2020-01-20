package problems.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * [Medium]
 * 102. 二叉树的层次遍历
 *
 * 给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点）。
 *
 * 例如:
 * 给定二叉树: [3,9,20,null,null,15,7],
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回其层次遍历结果：
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 *
 * 相关练习：104. 二叉树的最大深度 MaxDepthOfBinaryTree
 * 相关练习：107. 二叉树的层次遍历 II
 *
 * @author kyan
 * @date 2019/12/31
 */
public class No102_BinaryTreeLevelOrderTraversal {

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
     * 非递归
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> levels = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < queueSize; i++) {
                TreeNode node = queue.pollFirst();
                level.add(node.val);
                if (node.left != null) {
                    queue.offerLast(node.left);
                }
                if (node.right != null) {
                    queue.offerLast(node.right);
                }
            }
            levels.add(level);
        }
        return levels;
    }

    /**
     * 递归版
     * 每个节点都对应一个level层级，对应的是二维数组levels中的level
     *
     * @param root
     * @return
     */
    List<List<Integer>> levels = new ArrayList<>();
    public List<List<Integer>> levelOrderByRecursive(TreeNode root) {
        helper(root, 0);
        return levels;
    }

    private void helper(TreeNode root, int level) {
        if (root == null) return;
        if (levels.size() <= level) {
            levels.add(new ArrayList<>());
        }
        levels.get(level).add(root.val);
        helper(root.left, level + 1);
        helper(root.right, level + 1);
    }

    public static void main(String[] args) {
        No102_BinaryTreeLevelOrderTraversal solution = new No102_BinaryTreeLevelOrderTraversal();
        //[3,9,20,null,null,15,7]
        //    3
        //   / \
        //  9  20
        //    /  \
        //   15   7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(solution.levelOrder(root));//[[3], [9, 20], [15, 7]]
        System.out.println(solution.levelOrderByRecursive(root));//[[3], [9, 20], [15, 7]]
    }
}
