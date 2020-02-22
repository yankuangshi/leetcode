package problems.lcof;

import problems.common.util.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 面试题07.重建二叉树
 * 难度：中等
 * 题目描述
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 *
 * 例如，给出
 * 前序遍历 preorder = [3,9,20,15,7]
 * 中序遍历 inorder = [9,3,15,20,7]
 * 返回如下的二叉树：
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 相似题：
 * LeetCode 105. 从前序与中序遍历序列构造二叉树 https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 * LeetCode 106. 从中序与后序遍历序列构造二叉树 https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal
 *
 * @author kyan
 * @date 2020/2/22
 */
public class LCOF_7_BuildTree {

    /**
     * 3 ms, 96.62%
     * 41.3 MB, 100.00%
     */
    public static class Solution1 {

        private int[] preorder, inorder;
        private int rootIndex;
        private Map<Integer, Integer> inorderMap;

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0) return null;
            this.preorder = preorder;
            this.inorder = inorder;
            inorderMap = new HashMap<>(inorder.length);
            for (int i = 0; i < inorder.length; i++) {
                inorderMap.put(inorder[i], i);
            }
            return buildHelper(0, inorder.length - 1);
        }

        public TreeNode buildHelper(int leftIndex, int rightIndex) {
            if (leftIndex > rightIndex) return null;
            TreeNode root = new TreeNode(preorder[rootIndex]);
            int inorderIndex = inorderMap.get(preorder[rootIndex]);
            rootIndex++;
            root.left = buildHelper(leftIndex, inorderIndex -1);
            root.right = buildHelper(inorderIndex + 1, rightIndex);
            return root;
        }
    }

    public static void main(String[] args) {
        //      3
        //     / \
        //    9   20
        //   /   /  \
        //  10  15   7
        int[] preorder = {3, 9, 10, 20, 15, 7};
        int[] inorder = {10, 9, 3, 15, 20, 7};
        TreeNode root = new Solution1().buildTree(preorder, inorder);
        System.out.println(root);
    }
}
