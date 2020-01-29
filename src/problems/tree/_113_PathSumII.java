package problems.tree;

import problems.common.util.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 113. 路径总和 II
 * 难度：中等
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
 *
 * @author kyan
 * @date 2020/1/3
 */
public class _113_PathSumII {

    public static class Solution1 {

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
        System.out.println(new Solution1().pathSum(root, 22));
        //[[5, 4, 11, 2], [5, 8, 4, 5]]
    }
}
