package problems.tree;

import problems.common.util.TreeNode;

import java.util.LinkedList;

/**
 * 100. 相同的树
 * 难度：简单
 *
 * 给定两个二叉树，编写一个函数来检验它们是否相同。
 *
 * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
 *
 * 示例 1:
 *
 * 输入:       1         1
 *           / \       / \
 *          2   3     2   3
 *
 *         [1,2,3],   [1,2,3]
 *
 * 输出: true
 *
 * 示例 2:
 *
 * 输入:      1          1
 *           /           \
 *          2             2
 *
 *         [1,2],     [1,null,2]
 *
 * 输出: false
 * 示例 3:
 *
 * 输入:       1         1
 *           / \       / \
 *          2   1     1   2
 *
 *         [1,2,1],   [1,1,2]
 *
 * 输出: false
 *
 * 相关练习：101. 对称二叉树
 *
 * @author kyan
 * @date 2020/1/1
 */
public class LeetCode_100_SameTree {

    /**
     * 递归思想
     * 要判断2棵树（p和q）是否相同
     * p和q都为空，则相同
     * p为空q不为空、或者 p不为空q为空，则肯定不相同
     * p和q都不为空，若p的值不等于q的值，则也肯定不相同
     * p和q都不为空，且p的值等于q的值，则继续判断p.left和q.left是否相同，及p.right和q.right是否相同
     */
    public static class Solution1 {

        public boolean isSameTree(TreeNode p, TreeNode q) {
            // p和q都为空，则相同
            if (p == null && q == null)
                return true;
            // p空q不空 或 p不空q空，则肯定不相同
            if (p == null && q != null || p != null && q == null)
                return false;
            // p和q都不空的情况下，比较p和q的值，如果不相等，则肯定不相同
            if (p.val != q.val) return false;
            // 剩下情况就是，p和q都不空，且p和q的值相同，则继续判断p和q的左右子树是否相同
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }

    /**
     * 递归思想，代码逻辑优化
     */
    public static class Solution2 {

        public boolean isSameTree(TreeNode p, TreeNode q) {
            if (p == null && q == null)
                return true;
            if (p != null && q != null && p.val == q.val) {
                return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
            } else {
                return false;
            }
        }
    }

    /**
     * 非递归思想，双队列实现
     */
    public static class Solution3 {

        public boolean isSameTree(TreeNode p, TreeNode q) {
            LinkedList<TreeNode> queue1 = new LinkedList<>();
            LinkedList<TreeNode> queue2 = new LinkedList<>();
            //enqueue root
            queue1.offerLast(p);
            queue2.offerLast(q);
            while (!queue1.isEmpty() && !queue2.isEmpty()) {
                TreeNode node1 = queue1.pollFirst();
                TreeNode node2 = queue2.pollFirst();
                if (node1 == null && node2 == null) continue;
                if (node1 == null && node2 != null || node1 != null && node2 == null) return false;
                if (node1.val != node2.val) return false;
                //左子节点入队、右子节点入队
                queue1.offerLast(node1.left);
                queue1.offerLast(node1.right);
                //左子节点入队、右子节点入队
                queue2.offerLast(node2.left);
                queue2.offerLast(node2.right);
            }
            if (!queue1.isEmpty() || !queue2.isEmpty()) {
                return false;
            }
            return true;
        }
    }


    public static void main(String[] args) {
        //构造测试用例1
        TreeNode p = new TreeNode(1);
        p.left = new TreeNode(2);
        p.right = new TreeNode(3);
        TreeNode q = new TreeNode(1);
        q.left = new TreeNode(2);
        q.right = new TreeNode(3);
        System.out.println("p==q : " + new Solution1().isSameTree(p, q)); //true
        System.out.println("p==q : " + new Solution3().isSameTree(p, q)); // true

        //测试用例2
        TreeNode p2 = new TreeNode(1);
        p2.left = new TreeNode(2);
        TreeNode q2 = new TreeNode(1);
        q2.right = new TreeNode(2);
        System.out.println("p2==q2 : " + new Solution2().isSameTree(p2, q2)); //false
        System.out.println("p2==q2 : " + new Solution3().isSameTree(p2, q2)); //false
    }
}
