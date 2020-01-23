package problems.linkedlist;

import java.util.LinkedList;

/**
 * [EASY]
 * 1290. 二进制链表转整数
 *
 * 给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
 *
 * 请你返回该链表所表示数字的 十进制值 。
 *
 * 输入：head = [1,0,1]
 * 输出：5
 * 解释：二进制数 (101) 转化为十进制数 (5)
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/convert-binary-number-in-a-linked-list-to-integer
 *
 * @author kyan
 * @date 2020/1/22
 */
public class No1290_ConvertBinary2Integer {

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
     * time 1ms, beat 28.63%
     * space 34.3MB, beat 55.17%
     *
     * @param head
     * @return
     */
    public int getDecimalValue(ListNode head) {
        LinkedList<ListNode> stack = new LinkedList<ListNode>();
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        int i = 0;
        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop().val * (1<<i);
            i++;
        }
        return sum;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(0);
        head.next.next = new ListNode(1);
        No1290_ConvertBinary2Integer solution = new No1290_ConvertBinary2Integer();
        System.out.println(solution.getDecimalValue(head));
    }
}
