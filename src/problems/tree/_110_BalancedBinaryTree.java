package problems.tree;

import problems.common.util.TreeNode;

/**
 * 110. 平衡二叉树
 * 难度：简单
 *
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 * 本题中，一棵高度平衡二叉树定义为：
 *
 * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
 *
 * 示例 1:
 * 给定二叉树 [3,9,20,null,null,15,7]
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回true
 *
 * 示例 2:
 * 给定二叉树 [1,2,2,3,3,null,null,4,4]
 *        1
 *       / \
 *      2   2
 *     / \
 *    3   3
 *   / \
 *  4   4
 * 返回false
 *
 * @author kyan
 * @date 2019/12/31
 */
public class _110_BalancedBinaryTree {

    /**
     * 思路（递归）Top-down 自顶向下策略，可以理解为是【104. 二叉树的最大深度】题的延伸版
     * 求root根节点的各自左右子树的最大高度，要求高度差绝对值不大于1，且左右子树也是平衡树
     */
    public static class Solution1 {
        
        public boolean isBalanced(TreeNode root) {
            if (root == null) return true;
            return (Math.abs(maxDepth(root.left) - maxDepth(root.right)) <= 1)
                    && isBalanced(root.left) && isBalanced(root.right);
        }
        
        public int maxDepth(TreeNode root) {
            if (root == null)
                return 0;
            return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
        }
    }
    
    /**
     * 思路：Bottom-up 自底向上（提前阻断）
     */
    public static class Solution2 {
        
        public boolean isBalanced(TreeNode root) {
            return depth(root) != -1;
        }
    
        public int depth(TreeNode root) {
            if (root == null)
                return 0;
            int leftDepth = depth(root.left);
            if (leftDepth == -1)
                return -1;     //左子树不是平衡二叉树，则提前阻断
            int rightDepth = depth(root.right);
            if (rightDepth == -1)
                return -1;    //右子树不是平衡二叉树，则提前阻断
            return Math.abs(leftDepth - rightDepth) <= 1 ? Math.max(leftDepth, rightDepth) + 1 : -1;
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
        System.out.println(new Solution1().isBalanced(root));//true
        System.out.println(new Solution2().isBalanced(root));

        //[1,2,2,3,3,null,null,4,4]
        //       1
        //      / \
        //     2   2
        //    / \
        //   3   3
        //  / \
        // 4   4
        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(3);
        root.left.left.left = new TreeNode(4);
        root.left.left.right = new TreeNode(4);
        System.out.println(new Solution1().isBalanced(root));//false
        System.out.println(new Solution2().isBalanced(root));//false

        //[1,2,2,3,null,null,3,4,null,null,4]
        //      1
        //     / \
        //    2   2
        //   /     \
        //  3       3
        // /         \
        //4           4
        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.left.left = new TreeNode(4);
        root.right = new TreeNode(2);
        root.right.right = new TreeNode(3);
        root.right.right.right = new TreeNode(4);
        System.out.println(new Solution1().isBalanced(root));//false
        System.out.println(new Solution2().isBalanced(root));//false
    }
}
