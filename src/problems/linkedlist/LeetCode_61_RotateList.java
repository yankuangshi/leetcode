package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 61. 旋转链表
 * 难度：中等
 * <p>
 * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 1->2->3->4->5->NULL, k = 2
 * 输出: 4->5->1->2->3->NULL
 * 解释:
 * 向右旋转 1 步: 5->1->2->3->4->NULL
 * 向右旋转 2 步: 4->5->1->2->3->NULL
 * 示例 2:
 * <p>
 * 输入: 0->1->2->NULL, k = 4
 * 输出: 2->0->1->NULL
 * 解释:
 * 向右旋转 1 步: 2->0->1->NULL
 * 向右旋转 2 步: 1->2->0->NULL
 * 向右旋转 3 步: 0->1->2->NULL
 * 向右旋转 4 步: 2->0->1->NULL
 *
 * @author kyan
 * @date 2020/1/15
 */
public class LeetCode_61_RotateList {


    /**
     * time 1ms, beat 99%
     * space 36.8MB, beat 81.71%
     */
    public static class Solution1 {

        public ListNode rotateRight(ListNode head, int k) {
            if (k == 0 || head == null || head.next == null) return head;
            ListNode preHead = new ListNode(-1);
            preHead.next = head;
            ListNode cur = head;
            int size = 0;
            while (cur != null) {
                cur = cur.next;
                size++;
            }
            k = k % size;
            ListNode fast = preHead, slow = preHead;
            for (int i = 0; i < k; i++) {
                fast = fast.next;
            }
            while (fast.next != null) {
                fast = fast.next;
                slow = slow.next;
            }
            fast.next = preHead.next;
            preHead.next = slow.next;
            slow.next = null;
            return preHead.next;
        }
    }

    public static void main(String[] args) {
        //1->2->3->4->5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        Solution1 solution1 = new Solution1();
        System.out.println(solution1.rotateRight(head, 2));
        //should print 4->5->1->2->3

        //0->1->2
        head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        System.out.println(solution1.rotateRight(head, 4));
        //should print 2->0->1
    }
}
