package problems.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * [MEDIUM]
 * 113. 路径总和 II
 *
 * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 * 给定如下二叉树，以及目标和 sum = 22，
 *
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \    / \
 *         7    2  5   1
 * 返回:
 *
 * [
 *    [5,4,11,2],
 *    [5,8,4,5]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/path-sum-ii
 *
 * @author kyan
 * @date 2020/1/3
 */
public class No113_PathSum2 {

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

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(root, sum, 0, path, paths);
        return paths;
    }

    private void dfs(TreeNode root, int target, int sumNow, List<Integer> path, List<List<Integer>> paths) {
        if (root == null) return;
        path.add(root.val);
        sumNow += root.val;
        if (root.left == null && root.right == null && sumNow == target) {
            paths.add(new ArrayList<>(path));
        } else {
            dfs(root.left, target, sumNow, path, paths);
            dfs(root.right, target, sumNow, path, paths);
        }
        path.remove(path.size() - 1);
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
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);
        No113_PathSum2 solution = new No113_PathSum2();
        System.out.println(solution.pathSum(root, 22));
    }
}
