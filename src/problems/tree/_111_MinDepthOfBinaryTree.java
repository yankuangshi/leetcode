package problems.tree;

import problems.common.util.TreeNode;

/**
 * 111. 二叉树的最小深度
 * 难度：简单
 *
 * 给定一个二叉树，找出其最小深度。
 *
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 *
 * 给定二叉树 [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 返回它的最小深度 2.
 *
 * @author kyan
 * @date 2019/12/30
 */
public class _111_MinDepthOfBinaryTree {

    /**
     * 方法1（递归思想）
     * 特别需要考虑的 [1,2]
     *   2
     *  /
     * 1
     * 此时树的最小高度应该是2，而不是1。对于2的右子树高度不应该算做是0，而可以算作是Integer.MAX_VALUE
     * 所以可以通过 hasBrother（是否有兄弟节点）来判断。
     * 当 root 节点为null（例如2的右子节点），有兄弟节点（例如1），那么树的高度应该是Integer.MAX_VALUE。
     * 如果 root 节点为null，且无兄弟节点，那么高度就是0
     *
     */
    public static class Solution1 {

        public int minDepth(TreeNode root) {
            return minDepthInternally(root, false);
        }

        private int minDepthInternally(TreeNode root, boolean hasBrother) {
            if (root == null) {
                return hasBrother ? Integer.MAX_VALUE : 0;
            }
            return Math.min(
                    minDepthInternally(root.left, root.right != null),
                    minDepthInternally(root.right, root.left != null)) + 1;
        }
    }


    /**
     * 方法2（递归思想）：相比方法1更好理解
     * 同样也需要特别考虑 [1,2]
     *   2
     *  /
     * 1
     * 即root根节点只有左子树或只有右子树的情况，这种情况只考虑对应有的左子树或右子树的深度
     *
     *     3
     *      \
     *      20
     *     /  \
     *    15   7
     *
     * 另一种情况root根节点既有左子树又有右子树，那么只考虑对应有的左子树和右子树深度的最小值
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     *
     */
    public static class Solution2 {
        public int minDepth(TreeNode root) {
            if(root==null) return 0;
            int left = minDepth(root.left);
            int right = minDepth(root.right);
            //root节点只有左边或者右边情况，left+right+1.
            //否则，取左边或者右边最小的深度+1
            return  root.left==null || root.right==null  ? left+right+1: Math.min(left,right)+1;
        }
    }

    public static void main(String[] args) {
        //[3,9,20,null,null,15,7]
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(root);
        System.out.println("MinDepth: " + new Solution1().minDepth(root));
        System.out.println("MinDepth: " + new Solution2().minDepth(root));
    }
}
