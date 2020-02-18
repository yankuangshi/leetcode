package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 82. 删除排序链表中的重复元素 II
 * 难度：中等
 * <p>
 * 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中没有重复出现的数字。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 1->2->3->3->4->4->5
 * 输出: 1->2->5
 * <p>
 * 示例 2:
 * <p>
 * 输入: 1->1->1->2->3
 * 输出: 2->3
 *
 * @author kyan
 * @date 2020/1/18
 */
public class LeetCode_82_DeleteDuplicatesII {

    /**
     * time 1ms, beat 98.66%
     * space 36.4MB, beat 77.56%
     */
    public static class Solution1 {

        public ListNode deleteDuplicates(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode preHead = new ListNode(-1);
            ListNode pre = preHead;
            pre.next = head;
            while (pre.next != null) {
                ListNode cur = pre.next;
                while (cur.next != null && cur.val == cur.next.val) {
                    cur = cur.next;
                }
                if (cur == pre.next) {
                    pre = cur;
                } else {
                    pre.next = cur.next;
                }
            }
            return preHead.next;
        }
    }

    public static void main(String[] args) {
        //1->2->3->3->4->4->5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next.next = new ListNode(5);
        Solution1 solution1 = new Solution1();
        System.out.println(solution1.deleteDuplicates(head));
        //should print 1->2->5
    }

}
