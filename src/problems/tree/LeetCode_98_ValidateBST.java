package problems.tree;

import problems.common.util.TreeNode;

import java.util.LinkedList;

/**
 * 98. 验证二叉搜索树
 * 难度：中等
 * <p>
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 * <p>
 * 假设一个二叉搜索树具有如下特征：
 * <p>
 * 节点的左子树只包含小于当前节点的数。
 * 节点的右子树只包含大于当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 * 示例 1:
 * <p>
 * 输入:
 *  2
 * / \
 * 1   3
 * 输出: true
 * <p>
 * 示例 2:
 * <p>
 * 输入:
 *      5
 *    /  \
 *   1   4
 *      / \
 *     3   6
 * 输出: false
 * 解释: 输入为: [5,1,4,null,null,3,6]。
 *      根节点的值为 5 ，但是其右子节点值为 4 。
 * <p>
 * 这道题虽然是中等难度题，但是在标签树的算法题里面通过率是最低的！！！！
 *
 * @author kyan
 * @date 2020/1/4
 */
public class LeetCode_98_ValidateBST {

    /**
     * 递归思想
     * 每个节点都有相应的上下限
     * 如果 currNode的值比lowerLimit还小，则false
     * 如果 currNode的值比upperLimit还大，则false
     * 之后判断左右子节点，左子节点的upperLimit需要更新为父节点的值，右子节点的lowerLimit需要更新为父节点的值。
     */
    public static class Solution1 {

        public boolean isValidBST(TreeNode root) {
            return isValidBSTInternally(root, null, null);
        }

        public boolean isValidBSTInternally(TreeNode currNode, Integer lowerLimit, Integer upperLimit) {
            if (currNode == null) return true;
            if (lowerLimit != null && currNode.val <= lowerLimit) return false;
            if (upperLimit != null && currNode.val >= upperLimit) return false;
            return isValidBSTInternally(currNode.left, lowerLimit, currNode.val)
                    && isValidBSTInternally(currNode.right, currNode.val, upperLimit);
        }

    }

    /**
     * 迭代
     * 利用BST的特征 以及 中序遍历的特征，如果一棵树是BST，那么中序遍历的结果肯定是前一个值小于后一个值，例如：
     *  10
     *  / \
     * 5  15
     *    / \
     *   12 20
     * 中序遍历结果[5,10,12,15,20]，因此可以利用这一特征来进行判断是否是BST
     * 但是又和中序遍历不同的是，我们并不需要一个专门的list来存放遍历过的节点值，只需要每次保存最后一个遍历的节点值oldValue，
     * 之后的节点值newValue只需要和我们保存的节点值oldValue比较大小即可，如果newValue<=oldValue，则不是BST，否则继续遍历
     */
    public static class Solution2 {

        public boolean isValidBST(TreeNode root) {
            if (root == null) return true;
            Integer lastInorderValue = null;
            LinkedList<TreeNode> stack = new LinkedList<>();
            TreeNode currNode = root;
            while (!stack.isEmpty() || currNode != null) {
                while (currNode != null) {
                    stack.push(currNode);
                    currNode = currNode.left;
                }
                TreeNode node = stack.pop();
                //中序遍历的时候这边是把node.val加入list
                if (lastInorderValue != null && lastInorderValue >= node.val) return false;
                lastInorderValue = node.val;
                currNode = node.right;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        //   5
        //  / \
        // 1   4
        //    / \
        //   3   6
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(6);
        System.out.println(new Solution1().isValidBST(root));
        System.out.println(new Solution2().isValidBST(root));

        //      10
        //      / \
        //     5  15
        //        / \
        //       6  20
        TreeNode test = new TreeNode(10);
        test.left = new TreeNode(5);
        test.right = new TreeNode(15);
        test.right.left = new TreeNode(6);
        test.right.right = new TreeNode(20);
        System.out.println(new Solution1().isValidBST(test));
        System.out.println(new Solution2().isValidBST(test));

        //      1
        //       \
        //        1
        TreeNode test2 = new TreeNode(1);
        test2.left = new TreeNode(1);
        System.out.println(new Solution1().isValidBST(test2));
        System.out.println(new Solution2().isValidBST(test2));
    }
}
