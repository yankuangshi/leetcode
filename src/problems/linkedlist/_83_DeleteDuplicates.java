package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 83. 删除排序链表中的重复元素
 * 难度：简单
 * <p>
 * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 1->1->2
 * 输出: 1->2
 * 示例 2:
 * <p>
 * 输入: 1->1->2->3->3
 * 输出: 1->2->3
 *
 * @author kyan
 * @date 2020/1/15
 */
public class _83_DeleteDuplicates {

    /**
     * 自己实现的实在是太蠢了！！！
     */
    public static class Solution1 {

        public ListNode deleteDuplicates(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode slow = head;
            ListNode fast = head.next;
            while (slow != null && slow.next != null) {
                while (fast != null && slow.val == fast.val) {
                    slow.next = fast.next;
                    fast.next = null;
                    fast = slow.next;
                }
                slow = slow.next;
                if (fast != null) {
                    fast = fast.next;
                }
            }
            return head;
        }
    }

    /**
     * 精简版
     */
    public static class Solution2 {

        public ListNode deleteDuplicates(ListNode head) {
            ListNode cur = head;
            while (cur != null && cur.next != null) {
                if (cur.val == cur.next.val) {
                    cur.next = cur.next.next;
                } else {
                    cur = cur.next;
                }
            }
            return head;
        }
    }

    public static void main(String[] args) {
        //1->1->2->2->3
        ListNode head = new ListNode(1);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(3);
        Solution1 solution1 = new Solution1();
        System.out.println(solution1.deleteDuplicates(head));
        //should print 1->2->3
    }

}
