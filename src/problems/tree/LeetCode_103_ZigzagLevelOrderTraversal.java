package problems.tree;

import problems.common.util.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 103. 二叉树的锯齿形层次遍历
 * 难度：中等
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
 * @author kyan
 * @date 2020/1/3
 */
public class LeetCode_103_ZigzagLevelOrderTraversal {
    
    public static class Solution1 {
        
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
    }


    public static void main(String[] args) {
        //     3
        //    / \
        //   9  20
        //     /  \
        //    15   7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(new Solution1().zigzagLevelOrder(root));
        //should print [[3], [20, 9], [15, 7]]

        //      1
        //     2 3
        //    4   5
        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.right = new TreeNode(3);
        root.right.right = new TreeNode(5);
        System.out.println(new Solution1().zigzagLevelOrder(root));
        //should print [[1], [3, 2], [4, 5]]
    }
}
