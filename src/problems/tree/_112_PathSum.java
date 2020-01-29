package problems.tree;

import problems.common.util.TreeNode;

import java.util.LinkedList;

/**
 * 112. 路径总和
 * 难度：简单
 *
 * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例: 
 * 给定如下二叉树，以及目标和 sum = 22，
 *
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \      \
 *         7    2      1
 *
 * 返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。
 *
 * @author kyan
 * @date 2020/1/1
 */
public class _112_PathSum {

    /**
     * 递归思想
     *
     * 如果root.left和root.right都为空，则说明root节点是叶子节点，如果此时sum==root.val，则说明刚好路径总和相同
     * 否则就继续判断 root左子树是否有sum值的路径总和 或 root右子树是否有sum值的路径总和
     *
     */
    public static class Solution1 {

        public boolean hasPathSum(TreeNode root, int sum) {
            if (root == null)
                return false;
            //root!=null的情况
            sum = sum - root.val;
            //叶子节点
            if (root.left == null && root.right == null)
                return sum == 0;
            //非叶子节点
            return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);
        }
    }

    /**
     * 非递归思想
     *
     * [5, 5]
     * [4, 9], [8, 13]
     * [4, 9], [13, 26], [4, 17]
     * [4, 9], [13, 26], [1, 18]
     * [11, 20]
     * [7, 27], [2, 22]
     * True
     */
    public static class Solution2 {

        public static class Pair<K, V> {
            K key;
            V value;

            public Pair(K key, V value) {
                this.key = key;
                this.value = value;
            }

            @Override
            public String toString() {
                return "(" + key + ", " + value + ")";
            }
        }

        public boolean hasPathSum(TreeNode root, int sum) {
            if (root == null) return false;
            LinkedList<Pair<TreeNode, Integer>> stack = new LinkedList<>();
            stack.push(new Pair<>(root, root.val));
            while (!stack.isEmpty()) {
                Pair<TreeNode, Integer> pair = stack.pop();
                TreeNode curNode = pair.key;
                Integer curSum = pair.value;
                if (curNode.left == null && curNode.right == null && curSum == sum)
                    return true;
                if (curNode.left != null) {
                    stack.push(new Pair<>(curNode.left, curSum + curNode.left.val));
                }
                if (curNode.right != null) {
                    stack.push(new Pair<>(curNode.right, curSum + curNode.right.val));
                }
//                System.out.println(stack);
            }
            return false;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right = new TreeNode(8);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.right = new TreeNode(1);
        System.out.println("hasPathSum=" + 22 + " : " + new Solution1().hasPathSum(root, 22));
        System.out.println("hasPathSum=" + 22 + " : " + new Solution2().hasPathSum(root, 22));
    }
}
