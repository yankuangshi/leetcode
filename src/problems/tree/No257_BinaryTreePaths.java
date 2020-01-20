package problems.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * [EASY]
 * 257. 二叉树的所有路径
 *
 * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 *
 * 输入:
 *
 *    1
 *  /   \
 * 2     3
 *  \
 *   5
 *
 * 输出: ["1->2->5", "1->3"]
 *
 * 解释: 所有根节点到叶子节点的路径为: 1->2->5, 1->3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-paths
 *
 * @author kyan
 * @date 2020/1/3
 */
public class No257_BinaryTreePaths {

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

    public List<List<Integer>> binaryTreePaths(TreeNode root) {
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        constructPath(root, path, paths);
        return paths;
    }

    private void constructPath(TreeNode root, List<Integer> path, List<List<Integer>> paths) {
        if (root == null) return;
        path.add(root.val);
        if (root.left == null && root.right == null) {
            paths.add(new ArrayList<>(path));
        } else {
            constructPath(root.left, path, paths);
            constructPath(root.right, path, paths);
        }
        path.remove(path.size() - 1);
    }

    public List<String> binaryTreePaths2(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null)
            return paths;
        constructPath2(root, "", paths);
        return paths;
    }

    private void constructPath2(TreeNode root, String path, List<String> paths) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            path += root.val;
            paths.add(path);
            return;
        }
        path += root.val + "->";
        constructPath2(root.left, path, paths);
        constructPath2(root.right, path, paths);
    }

    public List<String> binaryTreePaths3(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null) {
            return paths;
        }
        LinkedList<TreeNode> nodeStack = new LinkedList<>();
        LinkedList<String> pathStack = new LinkedList<>();
        nodeStack.push(root);
        pathStack.push(String.valueOf(root.val));
        while (!nodeStack.isEmpty() && !pathStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            String currPath = pathStack.pop();
            //叶子节点
            if (node.left == null && node.right == null) {
                paths.add(currPath);
            }
            //非叶子节点
            if (node.left != null) {
                nodeStack.push(node.left);
                pathStack.push(currPath + "->" + node.left.val);
            }
            if (node.right != null) {
                nodeStack.push(node.right);
                pathStack.push(currPath + "->" + node.right.val);
            }
        }
        return paths;
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        No257_BinaryTreePaths solution = new No257_BinaryTreePaths();
        System.out.println(solution.binaryTreePaths(root));
        System.out.println(solution.binaryTreePaths2(root));
        System.out.println(solution.binaryTreePaths3(root));
    }
}
