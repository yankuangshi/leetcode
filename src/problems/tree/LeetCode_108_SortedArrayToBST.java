package problems.tree;

import problems.common.util.TreeNode;

/**
 * 108. 将有序数组转换为二叉搜索树
 * 难度：简单
 *
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 *
 * 给定有序数组: [-10,-3,0,5,9],
 *
 * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
 *
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 *
 * @author kyan
 * @date 2019/12/30
 */
public class LeetCode_108_SortedArrayToBST {

    /**
     * 分治思想，通过二分法查找出root根节点，然后左右子节点即是左右剩余数组的root根节点
     * 需要注意的是边界条件(low>high)，返回null节点
     */
    public static class Solution1 {

        public TreeNode sortedArrayToBST(int[] nums) {
            return sortedArrayToBSTInternally(nums, 0, nums.length - 1);
        }

        public TreeNode sortedArrayToBSTInternally(int[] a, int low, int high) {
            if (low > high) return null;
            int mid = (low + high) / 2;
            TreeNode root = new TreeNode(a[mid]);
            root.left = sortedArrayToBSTInternally(a, low, mid-1);
            root.right = sortedArrayToBSTInternally(a, mid+1, high);
            return root;
        }
    }


    public static void main(String[] args) {
        int[] nums = {-10, -3, 0, 5, 9};
        System.out.println(new Solution1().sortedArrayToBST(nums));
        //TreeNode{val=0, left=TreeNode{val=-10, left=null, right=TreeNode{val=-3, left=null, right=null}}, right=TreeNode{val=5, left=null, right=TreeNode{val=9, left=null, right=null}}}
    }

}
