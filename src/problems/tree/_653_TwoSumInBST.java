package problems.tree;

import problems.common.util.TreeNode;

import java.util.*;

/**
 * 653. 两数之和 IV - 输入 BST
 * 难度：简单
 *
 * 题目描述
 *
 * 给定一个二叉搜索树和一个目标结果，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。
 *
 * 案例 1:
 *
 * 输入:
 *     5
 *    / \
 *   3   6
 *  / \   \
 * 2   4   7
 *
 * Target = 9
 *
 * 输出: True
 *  
 *
 * 案例 2:
 *
 * 输入:
 *     5
 *    / \
 *   3   6
 *  / \   \
 * 2   4   7
 *
 * Target = 28
 *
 * 输出: False
 *
 * @author kyan
 * @date 2020/2/8
 */
public class _653_TwoSumInBST {

    /**
     * 解法一
     * 思路同 TwoSum 中 HashMap的方法类似，只是这里使用了HashSet（因为不存在下标）
     * DFS遍历版本
     * 5 ms, 53.23%
     * 43.9 MB, 20.25%
     */
    public static class Solution1 {

        public boolean findTarget(TreeNode root, int k) {
            if (root == null || (root.left == null && root.right == null)) return false;
            Set<Integer> set = new HashSet<>();
            return dfs(root, k, set);
        }

        private boolean dfs(TreeNode root, int k, Set<Integer> set) {
            if (root == null) return false;
            if (set.contains(k - root.val)) {
                return true;
            }
            set.add(root.val);
            return dfs(root.left, k, set) || dfs(root.right, k, set);
        }
    }

    /**
     * 解法二
     * 思路同解法一，BFS遍历版本
     * 6 ms, 38.59%
     * 44.5 MB, 11.18%
     */
    public static class Solution2 {

        public boolean findTarget(TreeNode root, int k) {
            if (root == null || (root.left == null && root.right == null)) return false;
            LinkedList<TreeNode> queue = new LinkedList<>();
            Set<Integer> set = new HashSet<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                TreeNode node = queue.pollFirst();
                if (set.contains(k - node.val)) {
                    return true;
                }
                set.add(node.val);
                if (node.left != null) queue.offerLast(node.left);
                if (node.right != null) queue.offerLast(node.right);
            }
            return false;
        }
    }

    /**
     * 解法三
     * 利用BST的特性，中序遍历BST，得到的是升序排列的一组数
     * 因此可以转化为 TwoSum II 中的Shrink Range思路，使用左右双指针往中间移动
     *
     * 执行用时：4 ms, 73.43%
     * 内存消耗：43.4 MB, 29.33%
     */
    public static class Solution3 {

        private List<Integer> list = new ArrayList<>();

        public boolean findTarget(TreeNode root, int k) {
            inorder(root);
            int l = 0, r = list.size() - 1;
            while (l < r) {
                if (list.get(l) + list.get(r) == k) {
                    return true;
                }
                if (list.get(l) + list.get(r) < k) {
                    l++;
                } else {
                    r--;
                }
            }
            return false;
        }

        private void inorder(TreeNode root) {
            if (root == null) return;
            inorder(root.left);
            list.add(root.val);
            inorder(root.right);
        }
    }

    /**
     * 解法四
     * 利用BST的特征
     * 节点的左子树只包含小于当前节点的数。
     * 节点的右子树只包含大于当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * 直接在BST上进行遍历，例如遍历到节点 node 时，在BST上深度遍历是否存在 k-node.val 的节点
     *
     * 执行用时：1 ms, 100.00%
     * 内存消耗：42.3 MB, 35.98%
     */
    public static class Solution4 {

        private TreeNode ROOT;

        public boolean findTarget(TreeNode root, int k) {
            if (root == null || (root.left == null && root.right == null)) return false;
            ROOT = root;
            return helper(root, k);
        }

        private boolean helper(TreeNode root, int k) {
            if (root == null) return false;
            return (k != root.val * 2 && contain(k - root.val))
                    || helper(root.left, k)
                    || helper(root.right, k);
        }

        private boolean contain(int val) {
            TreeNode node = ROOT;
            while (node != null) {
                if (val == node.val) return true;
                if (val < node.val) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);
        System.out.println(new Solution1().findTarget(root, 10));
        System.out.println(new Solution2().findTarget(root, 10));
        System.out.println(new Solution3().findTarget(root, 10));
        System.out.println(new Solution4().findTarget(root, 10));
    }
}
