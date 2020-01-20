package problems.tree;

/**
 * [EASY]
 * 110. 平衡二叉树
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
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/balanced-binary-tree
 *
 * @author kyan
 * @date 2019/12/31
 */
public class No110_BalancedBinaryTree {

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
     * 思路（递归）Top-down 自顶向下策略，可以理解为是【104. 二叉树的最大深度】题的延伸版
     * 求root根节点的各自左右子树的最大高度，要求高度差绝对值不大于1，且左右子树也是平衡树
     *
     * @param root
     * @return
     */
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

    /**
     * 思路：Bottom-up 自底向上（提前阻断）
     *
     * @param root
     * @return
     */
    public boolean isBalanced2(TreeNode root) {
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

    public static void main(String[] args) {
        No110_BalancedBinaryTree solution = new No110_BalancedBinaryTree();
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
        System.out.println(solution.isBalanced(root));//true
        System.out.println(solution.isBalanced2(root));

        //[1,2,2,3,3,null,null,4,4]
        //       1
        //      / \
        //     2   2
        //    / \
        //   3   3
        //  / \
        // 4   4
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(2);
        root2.left.left = new TreeNode(3);
        root2.left.right = new TreeNode(3);
        root2.left.left.left = new TreeNode(4);
        root2.left.left.right = new TreeNode(4);
        System.out.println(solution.isBalanced(root2));//false
        System.out.println(solution.isBalanced2(root2));//false

        //[1,2,2,3,null,null,3,4,null,null,4]
        //      1
        //     / \
        //    2   2
        //   /     \
        //  3       3
        // /         \
        //4           4
        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(2);
        root3.left.left = new TreeNode(3);
        root3.left.left.left = new TreeNode(4);
        root3.right = new TreeNode(2);
        root3.right.right = new TreeNode(3);
        root3.right.right.right = new TreeNode(4);
        System.out.println(solution.isBalanced(root3));//false
        System.out.println(solution.isBalanced2(root3));//false
    }
}
