package problems.tree;

import problems.common.util.TreeNode;

import java.util.HashMap;

/**
 * 105. 从前序与中序遍历序列构造二叉树
 * 难度：中等
 *
 * 根据一棵树的前序遍历与中序遍历构造二叉树。
 *
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * 例如，给出
 *
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
 *
 * 相关练习：106. 从中序与后序遍历序列构造二叉树
 *
 * @author kyan
 * @date 2020/1/2
 */
public class LeetCode_105_PreorderAndInorderToBT {

    /**
     * 递归思路
     * 使用前序遍历数组提供根节点，然后根据中序遍历数组拆分成左子树和右子树
     *
     * 回忆
     * preorder: root ->left->right
     * inorder: left ->root->right
     */
    public static class Solution1 {

        private int[] preorder, inorder;
        //用于在先序遍历数组中指定根节点下标
        private int rootIndex = 0;
        //用于存储中序遍历数组中 node值和index的映射关系
        private HashMap<Integer, Integer> inorderMap = new HashMap<>();


        public TreeNode buildTree(int[] preorder, int[] inorder) {
            if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0) return null;
            this.preorder = preorder;
            this.inorder = inorder;
            for (int i = 0; i < inorder.length; i++) {
                inorderMap.put(inorder[i], i);
            }
            return buildTreeInternally(0, inorder.length - 1);
        }

        private TreeNode buildTreeInternally(int leftIndex, int rightIndex) {
            if (leftIndex > rightIndex) {
                return null;
            }
            Integer value = preorder[rootIndex];
            rootIndex++;
            TreeNode node = new TreeNode(value);
            Integer inorderIndex = inorderMap.get(value);
            node.left = buildTreeInternally(leftIndex, inorderIndex - 1);
            node.right = buildTreeInternally(inorderIndex + 1, rightIndex);
            return node;
        }
    }

    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        System.out.println("buildTree: " + new Solution1().buildTree(preorder, inorder));
    }
}
