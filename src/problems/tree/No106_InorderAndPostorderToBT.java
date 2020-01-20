package problems.tree;

import java.util.HashMap;

/**
 * [MEDIUM]
 * 106. 从中序与后序遍历序列构造二叉树
 *
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 *
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * 例如，给出
 *
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 * 返回如下的二叉树：
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal
 *
 * 相关练习：105. 从前序与中序遍历序列构造二叉树
 *
 * @author kyan
 * @date 2020/1/2
 */
public class No106_InorderAndPostorderToBT {

    public class TreeNode {
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

    private int[] inorder;
    private int[] postorder;
    private int rootIndex;
    private HashMap<Integer, Integer> inorderMap = new HashMap<>();

    /**
     * 回忆
     * inorder: left->root->right
     * postorder: left->right->root
     *
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null
                || inorder.length == 0 || postorder.length == 0
                || inorder.length != postorder.length)
            return null;
        this.inorder = inorder;
        this.postorder = postorder;
        //和先序遍历不同，后序遍历是从后往前
        rootIndex = postorder.length - 1;
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return buildTreeInternally(0, inorder.length - 1);
    }

    private TreeNode buildTreeInternally(int leftIndex, int rightIndex) {
        if (leftIndex > rightIndex) {
            return null;
        }
        Integer value = postorder[rootIndex];
        rootIndex--;
        Integer index = inorderMap.get(value);
        TreeNode node = new TreeNode(value);
        node.right = buildTreeInternally(index + 1, rightIndex);
        node.left = buildTreeInternally(leftIndex, index - 1);
        return node;
    }

    public static void main(String[] args) {
        No106_InorderAndPostorderToBT solution = new No106_InorderAndPostorderToBT();
        int[] inorder = {9,3,15,20,7};
        int[] postorder = {9,15,7,20,3};
        System.out.println("buildTree: " + solution.buildTree(inorder, postorder));
    }
}
