package problems.tree;

import problems.common.util.TreeNode;

import java.util.HashMap;

/**
 * 106. 从中序与后序遍历序列构造二叉树
 * 难度：中等
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
 * 相关练习：105. 从前序与中序遍历序列构造二叉树
 *
 * @author kyan
 * @date 2020/1/2
 */
public class LeetCode_106_InorderAndPostorderToBT {


    /**
     * 回忆
     * inorder: left->root->right
     * postorder: left->right->root
     */
    public static class Solution1 {

        private int[] inorder;
        private int[] postorder;
        private int rootIndex;
        private HashMap<Integer, Integer> inorderMap = new HashMap<>();

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
    }



    public static void main(String[] args) {
        int[] inorder = {9,3,15,20,7};
        int[] postorder = {9,15,7,20,3};
        System.out.println("buildTree: " + new Solution1().buildTree(inorder, postorder));
    }
}
