package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 203. 移除链表元素
 * 难度：简单
 * <p>
 * 删除链表中等于给定值 val 的所有节点。
 * <p>
 * 示例:
 * <p>
 * 输入: 1->2->6->3->4->5->6, val = 6
 * 输出: 1->2->3->4->5
 *
 * @author kyan
 * @date 2020/1/22
 */
public class LeetCode_203_RemoveElements {

    /**
     * time 1ms, beat 100%
     * space 40.1MB, beat 7.75%
     */
    public static class Solution1 {

        public ListNode removeElements(ListNode head, int val) {
            ListNode preHead = new ListNode(-1);
            preHead.next = head;
            ListNode pre = preHead;
            while (pre.next != null) {
                if (pre.next.val == val) {
                    //remove
                    pre.next = pre.next.next;
                } else {
                    pre = pre.next;
                }
            }
            return preHead.next;
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        System.out.println(new Solution1().removeElements(head, 3));
    }

}
