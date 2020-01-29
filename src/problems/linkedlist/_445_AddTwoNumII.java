package problems.linkedlist;

import problems.common.util.ListNode;

import java.util.Stack;

/**
 * 445. 两数相加 II
 * 难度：中等
 * <p>
 * 给定两个非空链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储单个数字。将这两数相加会返回一个新的链表。
 * <p>
 *  
 * <p>
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 * <p>
 * 进阶:
 * <p>
 * 如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
 * <p>
 * 示例:
 * <p>
 * 输入: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出: 7 -> 8 -> 0 -> 7
 *
 * @author kyan
 * @date 2020/1/25
 */
public class _445_AddTwoNumII {

    /**
     * 思路1：利用2个栈
     * <p>
     * time 7ms, beat 56.55%
     * space 45.8MB, beat 7.10%
     */
    public static class Solution1 {

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            Stack<ListNode> s1 = new Stack<>();
            Stack<ListNode> s2 = new Stack<>();
            while (l1 != null) {
                s1.push(l1);
                l1 = l1.next;
            }
            while (l2 != null) {
                s2.push(l2);
                l2 = l2.next;
            }
            ListNode head = null;
            int carry = 0, sum, n1, n2;
            while (!s1.isEmpty() || !s2.isEmpty() || carry > 0) {
                n1 = !s1.isEmpty() ? s1.pop().val : 0;
                n2 = !s2.isEmpty() ? s2.pop().val : 0;
                sum = (n1 + n2 + carry) % 10;
                carry = (n1 + n2 + carry) / 10;
                ListNode newNode = new ListNode(sum);
                newNode.next = head;
                head = newNode;
            }
            return head;
        }
    }


    public static void main(String[] args) {
        //7243:7->2->4->3
        ListNode l1 = new ListNode(7);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);
        l1.next.next.next = new ListNode(3);
        //564:5->6->4
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        System.out.println(new Solution1().addTwoNumbers(l1, l2));
        //should print 7->8->0->7
    }
}
