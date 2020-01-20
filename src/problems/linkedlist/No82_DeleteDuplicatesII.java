package problems.linkedlist;

/**
 * [MEDIUM]
 * 82. 删除排序链表中的重复元素 II
 *
 * 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。
 *
 * 示例 1:
 *
 * 输入: 1->2->3->3->4->4->5
 * 输出: 1->2->5
 * 示例 2:
 *
 * 输入: 1->1->1->2->3
 * 输出: 2->3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii
 *
 * @author kyan
 * @date 2020/1/18
 */
public class No82_DeleteDuplicatesII {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }

    /**
     * time 1ms, beat 98.66%
     * space 36.4MB, beat 77.56%
     * @param head
     * @return
     */
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
