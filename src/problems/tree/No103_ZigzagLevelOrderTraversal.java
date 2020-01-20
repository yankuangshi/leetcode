package problems.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * [MEDIUM]
 * 103. 二叉树的锯齿形层次遍历
 *
 * 给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 *
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回锯齿形层次遍历如下：
 *
 * [
 *   [3],
 *   [20,9],
 *   [15,7]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal
 *
 * @author kyan
 * @date 2020/1/3
 */
public class No103_ZigzagLevelOrderTraversal {

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

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) {
            return levels;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        boolean fromHead = false;
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            LinkedList<Integer> level = new LinkedList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.pollFirst();
                if (fromHead) {
                    level.addFirst(node.val);
                } else {
                    level.addLast(node.val);
                }
                if (node.left != null) queue.offerLast(node.left);
                if (node.right != null) queue.offerLast(node.right);

            }
            levels.add(level);
            fromHead = !fromHead;
        }
        return levels;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        No103_ZigzagLevelOrderTraversal solution = new No103_ZigzagLevelOrderTraversal();
        System.out.println(solution.zigzagLevelOrder(root));

        //      1
        //     2 3
        //    4   5
        // 输出应该是：[1],[3,2],[4,5]
        TreeNode test = new TreeNode(1);
        test.left = new TreeNode(2);
        test.left.left = new TreeNode(4);
        test.right = new TreeNode(3);
        test.right.right = new TreeNode(5);
        System.out.println(solution.zigzagLevelOrder(test));
    }
}
