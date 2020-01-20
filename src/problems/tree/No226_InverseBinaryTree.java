package problems.tree;

/**
 * [EASY]
 * 226. 翻转二叉树
 *
 * 翻转一棵二叉树。
 *
 * 示例：
 *
 * 输入：
 *
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 * 输出：
 *
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 *
 * 这个问题是受到 Max Howell 的 原问题 启发的 ：
 *
 * 谷歌：我们90％的工程师使用您编写的软件(Homebrew)，但是您却无法在面试时在白板上写出翻转二叉树这道题，这太糟糕了。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/invert-binary-tree
 *
 *
 * @author kyan
 * @date 2020/1/1
 */
public class No226_InverseBinaryTree {

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

    public TreeNode inverseTree(TreeNode root) {
        if (root != null) {
            inverseInternally(root);
        }
        return root;
    }

    /**
     * 递归思想
     * 根节点的左右子节点调换位置
     * 调换完成后，如果左子节点不空，则翻转左子树；如果右子节点不空，则翻转右子树；
     *
     * @param root
     */
    public void inverseInternally(TreeNode root) {
        TreeNode tmpNode = root.left;
        root.left = root.right;
        root.right = tmpNode;
        if (root.left != null) {
            inverseInternally(root.left);
        }
        if (root.right != null) {
            inverseInternally(root.right);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);
        No226_InverseBinaryTree solution = new No226_InverseBinaryTree();
        System.out.println(solution.inverseTree(root));
    }
}
