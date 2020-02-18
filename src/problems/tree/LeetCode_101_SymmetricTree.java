package problems.tree;

import problems.common.util.TreeNode;

import java.util.LinkedList;

/**
 * 101. 对称二叉树
 * 难度：简单
 * 
 * 给定一个二叉树，检查它是否是镜像对称的。
 *
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 *
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 *
 * 说明:
 *
 * 如果你可以运用递归和迭代两种方法解决这个问题，会很加分。
 *
 * @author kyan
 * @date 2019/12/31
 */
public class LeetCode_101_SymmetricTree {

    /**
     * 递归思想
     * 一颗树要对称，需要满足
     * 左右子节点对称（相等）
     * 左子节点的左子树 = 右子节点的右子树
     * 左子节点的右子树 = 右子节点的左子树
     */
    public static class Solution1 {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) return true;
            return isSymmetricInternally(root.left, root.right);
        }
        
        public boolean isSymmetricInternally(TreeNode left, TreeNode right) {
            //如果左右都空，那肯定对称
            if (left == null && right == null) return true;
            //如果左空右不空 或者 右空左不空，那肯定不对称
            if (left != null && right == null || left == null && right != null) return false;
            //如果左右都不空，判断左右子节点的值是否相同，若不同，那肯定不对称
            if (left.val != right.val) return false;
            //如果左右都不空，且左右子节点的值相同，则判断 左子节点的左子树 = 右子节点的右子树 和 左子节点的右子树 = 右子节点的左子树
            return isSymmetricInternally(left.left, right.right) && isSymmetricInternally(left.right, right.left);
        }
    }

    /**
     * 非递归的思路
     * 类似层序遍历，分别遍历root根节点的左右子树，用2个队列来存放节点
     * 区别是遍历左子树时，是先left入队，再right入队；
     * 遍历右子树时，先right入队，再left入队
     * 因此，例如二叉树：
     *     1
     *    / \
     *   2   2
     *    \   \
     *    3    3
     * 左子树入队后：[2, null, 3]
     * 右子树入队后：[2, 3, null]
     * 可以看出左右子树不对称
     */
    public static class Solution2 {
        
        public boolean isSymmetric(TreeNode root) {
            if (root == null) return true;
            LinkedList<TreeNode> leftTree = new LinkedList<>();
            LinkedList<TreeNode> rightTree = new LinkedList<>();
            leftTree.offerLast(root.left);
            rightTree.offerLast(root.right);
            while (!leftTree.isEmpty() && !rightTree.isEmpty()) {
                TreeNode leftRoot = leftTree.pollFirst();
                TreeNode rightRoot = rightTree.pollFirst();
                if (leftRoot == null && rightRoot == null) continue;
                if (leftRoot == null && rightRoot != null || leftRoot != null && rightRoot == null) return false;
                if (leftRoot.val != rightRoot.val) return false;
                leftTree.offerLast(leftRoot.left);
                leftTree.offerLast(leftRoot.right);
                rightTree.offerLast(rightRoot.right);
                rightTree.offerLast(rightRoot.left);
            }
            if (!leftTree.isEmpty() || !rightTree.isEmpty()) {
                return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        //    1
        //   / \
        //  2   2
        // / \ / \
        //3  4 4  3
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);
        System.out.println(new Solution1().isSymmetric(root));//true
        System.out.println(new Solution2().isSymmetric(root));//true

        //    1
        //   / \
        //  2   2
        //   \   \
        //   3    3
        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(3);
        System.out.println(new Solution1().isSymmetric(root));//false
        System.out.println(new Solution2().isSymmetric(root));//false


        //       2
        //     /   \
        //    3     3
        //   / \   / \
        //  4  5  5   4
        //    / \    / \
        //   8  9   9  8
        root = new TreeNode(2);
        root.left = new TreeNode(3);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(4);
        root.left.right.left = new TreeNode(8);
        root.left.right.right = new TreeNode(9);
        root.right.right.left = new TreeNode(9);
        root.right.right.right = new TreeNode(8);
        System.out.println(new Solution1().isSymmetric(root));//false
        System.out.println(new Solution2().isSymmetric(root));//false
    }
}
