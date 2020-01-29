package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 86. 分隔链表
 * 难度：中等
 * <p>
 * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
 * <p>
 * 你应当保留两个分区中每个节点的初始相对位置。
 * <p>
 * 示例:
 * <p>
 * 输入: head = 1->4->3->2->5->2, x = 3
 * 输出: 1->2->2->4->3->5
 *
 * @author kyan
 * @date 2020/1/19
 */
public class _86_PartitionList {

    /**
     * time 1ms, beat 71.14%
     * space 35.9MB, beat 25.47%
     */
    public static class Solution1 {

        public ListNode partition(ListNode head, int x) {
            if (head == null || head.next == null) return head;
            ListNode preHead1 = new ListNode(-1);
            ListNode pre1 = preHead1;
            ListNode preHead2 = new ListNode(-1);
            ListNode pre2 = preHead2;
            while (head != null) {
                if (head.val < x) {
                    pre1.next = head;
                    pre1 = pre1.next;
                } else {
                    pre2.next = head;
                    pre2 = pre2.next;
                }
                head = head.next;
            }
            pre1.next = preHead2.next;
            pre2.next = null;//把pre2设为null很重要，不然会造成环形链表
            return preHead1.next;
        }
    }


    public static void main(String[] args) {
        //1->4->3->2->5->2
        ListNode head = new ListNode(1);
        head.next = new ListNode(4);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(2);
        Solution1 solution = new Solution1();
        System.out.println((solution.partition(head, 3)));
        //should print 1->2->2->4->3->5
    }

}
