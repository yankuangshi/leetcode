package problems.tree;

import problems.common.util.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 107. 二叉树的层次遍历 II
 * 难度：简单
 *
 * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
 *
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 返回其自底向上的层次遍历为：
 * [
 *   [15,7],
 *   [9,20],
 *   [3]
 * ]
 *
 * @author kyan
 * @date 2019/12/31
 */
public class LeetCode_107_BinaryTreeLevelOrderTraversalII {

    public static class Solution1 {

        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            LinkedList<List<Integer>> levels = new LinkedList<>();
            if (root == null) {
                return levels;
            }
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
                //和一般的levelOrder的区别就在于这里使用的是头插法
                levels.addFirst(level);
            }
            return levels;
        }
    }


    public static void main(String[] args) {
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
        System.out.println(new Solution1().levelOrderBottom(root));
        //[[15, 7], [9, 20], [3]]
    }
}
