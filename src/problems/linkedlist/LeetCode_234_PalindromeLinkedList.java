package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 234. 回文链表
 * 难度：简单
 * <p>
 * 请判断一个链表是否为回文链表。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 1->2
 * 输出: false
 * 示例 2:
 * <p>
 * 输入: 1->2->2->1
 * 输出: true
 * 进阶：
 * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 *
 * @author kyan
 * @date 2020/1/6
 */
public class LeetCode_234_PalindromeLinkedList {

    /**
     * 思路1：
     * 快慢指针，寻找到"中间节点"，同时一边遍历一边对前半部分的链表进行反转
     * 然后 对反转的链表和剩下的链表继续遍历，对比各个节点元素值
     * 快慢指针需要注意fast指针的结束条件
     * <p>
     * time 1ms, beat 99%
     * space 40.9MB, beat 97%
     */
    public static class Solution1 {

        public boolean isPalindrome(ListNode head) {
            //如果head为空或者只有head节点，则肯定是回文链表
            if (head == null || head.next == null) return true;
            ListNode fast = head;
            ListNode slow = head;
            ListNode prev = null;
            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                ListNode nextTemp = slow.next;
                slow.next = prev;
                prev = slow;
                slow = nextTemp;
            }
            if (fast != null) {
                slow = slow.next;
            }
            while (slow != null && prev != null) {
                if (slow.val != prev.val) return false;
                slow = slow.next;
                prev = prev.next;
            }
            return true;
        }
    }


    public static void main(String[] args) {
        //1->2->2->1
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(1);
        System.out.println(new Solution1().isPalindrome(head));
    }
}
