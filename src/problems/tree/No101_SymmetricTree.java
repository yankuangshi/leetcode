package problems.tree;

import java.util.LinkedList;

/**
 * [EASY]
 * 101. 对称二叉树
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
public class No101_SymmetricTree {

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
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isSymmetricInternally(root.left, root.right);
    }

    /**
     * 一颗树要对称，需要满足
     * 左右子节点对称（相等）
     * 左子节点的左子树 = 右子节点的右子树
     * 左子节点的右子树 = 右子节点的左子树
     * @param left
     * @param right
     * @return
     */
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
     *
     * @param root
     * @return
     */
    public boolean isSymmetricNonRecursive(TreeNode root) {
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

    public static void main(String[] args) {
        No101_SymmetricTree solution = new No101_SymmetricTree();
        //    1
        //   / \
        //  2   2
        // / \ / \
        //3  4 4  3
        TreeNode testRoot = new TreeNode(1);
        testRoot.left = new TreeNode(2);
        testRoot.right = new TreeNode(2);
        testRoot.left.left = new TreeNode(3);
        testRoot.left.right = new TreeNode(4);
        testRoot.right.left = new TreeNode(4);
        testRoot.right.right = new TreeNode(3);
        System.out.println(solution.isSymmetric(testRoot));//true
        System.out.println(solution.isSymmetricNonRecursive(testRoot));//true

        //    1
        //   / \
        //  2   2
        //   \   \
        //   3    3
        TreeNode testRoot2 = new TreeNode(1);
        testRoot2.left = new TreeNode(2);
        testRoot2.right = new TreeNode(2);
        testRoot2.left.right = new TreeNode(3);
        testRoot2.right.right = new TreeNode(3);
        System.out.println(solution.isSymmetric(testRoot2));//false
        System.out.println(solution.isSymmetricNonRecursive(testRoot2));//false


        //       2
        //     /   \
        //    3     3
        //   / \   / \
        //  4  5  5  4
        //   / \    / \
        //   8  9   9  8
        TreeNode testRoot3 = new TreeNode(2);
        testRoot3.left = new TreeNode(3);
        testRoot3.right = new TreeNode(3);
        testRoot3.left.left = new TreeNode(4);
        testRoot3.left.right = new TreeNode(5);
        testRoot3.right.left = new TreeNode(5);
        testRoot3.right.right = new TreeNode(4);
        testRoot3.left.right.left = new TreeNode(8);
        testRoot3.left.right.right = new TreeNode(9);
        testRoot3.right.right.left = new TreeNode(9);
        testRoot3.right.right.right = new TreeNode(8);
        System.out.println(solution.isSymmetric(testRoot3));//false
        System.out.println(solution.isSymmetricNonRecursive(testRoot3));//false
    }
}
