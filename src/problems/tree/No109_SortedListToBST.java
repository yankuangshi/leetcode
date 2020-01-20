package problems.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * [MEDIUM]
 * 109. 有序链表转换二叉搜索树
 *
 * 给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。
 *
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 *
 * 示例:
 *
 * 给定的有序链表： [-10, -3, 0, 5, 9],
 *
 * 一个可能的答案是：[0, -3, 9, -10, null, 5], 它可以表示下面这个高度平衡二叉搜索树：
 *
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/convert-sorted-list-to-binary-search-tree
 *
 * @author kyan
 * @date 2020/1/2
 */
public class No109_SortedListToBST {

     public static class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
     }

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

    /**
     * 一种思路和 题108. 将有序数组转换为二叉搜索树 相同
     * 先把有序链表转换成有序数组，然后再转换成BST即可，但是效率会很低
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        List<Integer> sortedArrayList = mapSortedListToSortedArray(head);
        return sortedArrayToBST(sortedArrayList, 0, sortedArrayList.size() - 1);
    }

    private TreeNode sortedArrayToBST(List<Integer> sortedArray, int low, int high) {
         if (low > high) return null;
         int mid = (low + high) / 2;
         TreeNode root = new TreeNode(sortedArray.get(mid));
         root.left = sortedArrayToBST(sortedArray, low, mid - 1);
         root.right = sortedArrayToBST(sortedArray, mid + 1, high);
         return root;
    }

    private List<Integer> mapSortedListToSortedArray(ListNode node) {
         List<Integer> array = new ArrayList<>();
         while (node != null) {
             array.add(node.val);
             node = node.next;
         }
         return array;
    }

    /**
     * 另一种思路，中序遍历一颗BST的访问顺序是链表顺序一模一样，所以可以模拟中序遍历来构建BST
     *
     * @param head
     * @return
     */
    private ListNode head;
    public TreeNode sortedListToBST2(ListNode head) {
        this.head = head;
        int size = 0;
        while (head != null) {
            head = head.next;
            size++;
        }
        return toBST(0, size - 1);
    }

    private TreeNode toBST(int low, int high) {
        if (low > high) return null;
        int mid = (low + high) / 2;
        TreeNode left = toBST(low, mid - 1);
        TreeNode root = new TreeNode(this.head.val);
        root.left = left;
        this.head = this.head.next;
        TreeNode right = toBST(mid + 1, high);
        root.right = right;
        return root;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(-10);
        head.next = new ListNode(-3);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(5);
        head.next.next.next.next = new ListNode(9);
        No109_SortedListToBST solution = new No109_SortedListToBST();
        System.out.println(solution.sortedListToBST(head));
        System.out.println(solution.sortedListToBST2(head));
    }
}
