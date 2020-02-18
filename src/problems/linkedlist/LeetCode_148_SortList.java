package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 148. 排序链表
 * 难度：中等
 * <p>
 * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 4->2->1->3
 * 输出: 1->2->3->4
 * 示例 2:
 * <p>
 * 输入: -1->5->3->4->0
 * 输出: -1->0->3->4->5
 *
 * @author kyan
 * @date 2020/1/9
 */
public class LeetCode_148_SortList {

    /**
     * 递归思想：利用归并排序的思想
     * 先通过快慢指针找到中间节点，然后分为两个链表各自进行排序
     * 最后的merge其实就是合并有个有序链表的过程
     * <p>
     * time 3ms, beat 99%
     * space 39.5MB, beat 98%
     */
    public static class Solution1 {

        public ListNode sortList(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode fast = head.next;
            ListNode slow = head;
            //寻找中间节点
            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            //slow.next=null非常重要，把链表断开成左右2个链表
            ListNode right = slow.next;
            slow.next = null;
            ListNode left = sortList(head);
            right = sortList(right);
            //最后把已经有序的左右2个链表合并成最终的有序链表
            ListNode pre = new ListNode(0);
            ListNode curr = pre;
            while (left != null && right != null) {
                if (left.val < right.val) {
                    curr.next = left;
                    left = left.next;
                } else {
                    curr.next = right;
                    right = right.next;
                }
                curr = curr.next;
            }
            curr.next = (left != null) ? left : right;
            return pre.next;
        }
    }


    public static void main(String[] args) {
        //-1->5->3->4->0
        ListNode head = new ListNode(-1);
        head.next = new ListNode(5);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(0);
        System.out.println("before sort: " + head);
        System.out.println("after sort: " + new Solution1().sortList(head));
        //should print -1->0->3->4->5
    }
}
