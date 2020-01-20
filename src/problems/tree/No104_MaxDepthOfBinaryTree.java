package problems.tree;

import java.util.LinkedList;

/**
 * [EASY]
 * 104. 二叉树的最大深度
 * <p>
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * 说明: 叶子节点是指没有子节点的节点。
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 返回它的最大深度 3 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
 *
 * @author kyan
 * @date 2019/12/30
 */
public class No104_MaxDepthOfBinaryTree {

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
     * 递归，DFS（深度优先搜索）求左右个子树的最大高度
     *
     * @param root
     * @return
     */
    public int maxDepthByRecursive(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepthByRecursive(root.left), maxDepthByRecursive(root.right)) + 1;
    }


    /**
     * 非递归，基于BFS（广度优先搜索）的层序遍历求高度
     * 借助队列FIFO完成
     *
     * @param root
     * @return
     */
    public int maxDepthByBFS(TreeNode root) {
        if (root == null) return 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        int maxDepth = 0;
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            maxDepth++;
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                TreeNode tmpNode = queue.pollFirst();
                if (tmpNode.left != null) {
                    queue.offerLast(tmpNode.left);
                }
                if (tmpNode.right != null) {
                    queue.offerLast(tmpNode.right);
                }
            }
        }
        return maxDepth;
    }

    /**
     * 非递归，基于DFS（深度优先）的前序遍历求高度
     * 借助栈stack完成，stack中存放Pair<TreeNode, Depth>，其中Depth代表着每个节点所在的深度
     * 一边遍历节点一边更新maxDepth
     *
     * @param root
     * @return
     */
    public int maxDepthByDFS(TreeNode root) {
        if (root == null) return 0;
        LinkedList<Pair<TreeNode, Integer>> stack = new LinkedList<>();
        stack.push(new Pair<>(root, 1));
        int maxDepth = 0;
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode tmpNode = pair.key;
            Integer currDepth = pair.value;
            maxDepth = Math.max(maxDepth, currDepth);
            if (tmpNode.right != null) {
                stack.push(new Pair<>(tmpNode.right, currDepth + 1));
            }
            if (tmpNode.left != null) {
                stack.push(new Pair<>(tmpNode.left, currDepth + 1));
            }
        }
        return maxDepth;
    }

    public static class Pair<K, V> {
        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    public static void main(String[] args) {
        No104_MaxDepthOfBinaryTree solution = new No104_MaxDepthOfBinaryTree();
        //[3,9,20,null,null,15,7]
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(root);
        System.out.println("MaxDepth: " + solution.maxDepthByRecursive(root));
        System.out.println("MaxDepthByBFS: " + solution.maxDepthByBFS(root));
        System.out.println("MaxDepthByDFS: " + solution.maxDepthByDFS(root));
    }
}
