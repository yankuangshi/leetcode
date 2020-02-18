package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 143. 重排链表
 * 难度：中等
 * <p>
 * 给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
 * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
 * <p>
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 * <p>
 * 示例 1:
 * <p>
 * 给定链表 1->2->3->4, 重新排列为 1->4->2->3.
 * 示例 2:
 * <p>
 * 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.
 *
 * @author kyan
 * @date 2020/1/24
 */
public class LeetCode_143_ReorderList {

    /**
     * 思路1：分3步
     * 1. 通过快慢指针找到中间节点【参考No876】
     * 2. 找到中间节点后把链表一分为二，然后把后半段链表进行反转 例如：1->2 4->3 【参考No206】
     * 3. 把两个链表组合成一个链表【参考No21】
     * <p>
     * time 2ms, beat 83.04%
     * space 39.2MB, beat 60.18%
     *
     */
    public static class Solution1 {
        public void reorderList(ListNode head) {
            //如果head为空 或 只有1个或2个节点 则不需要重新排序
            if (head == null || head.next == null || head.next.next == null) return;
            ListNode fast = head, slow = head;
            while (fast != null && fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            ListNode newHead = slow.next;
            slow.next = null;
            ListNode l1 = head;
            ListNode l2 = reverse(newHead);
            while (l1 != null && l2 != null) {
                ListNode nextTemp = l2.next;
                l2.next = l1.next;
                l1.next = l2;
                l1 = l2.next;
                l2 = nextTemp;
            }
        }

        private ListNode reverse(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode pre = null;
            ListNode cur = head;
            while (cur != null) {
                ListNode nextTemp = cur.next;
                cur.next = pre;
                pre = cur;
                cur = nextTemp;
            }
            return pre;
        }

    }


    public static void main(String[] args) {
        //1->2->3->4->5->6
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(6);
        Solution1 solution1 = new Solution1();
        solution1.reorderList(head);
        System.out.println(head);
        //should print 1->6->2->5->3->4
    }
}
