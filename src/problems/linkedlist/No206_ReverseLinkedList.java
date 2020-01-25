package problems.linkedlist;

import java.util.LinkedList;

/**
 * [EASY]
 * 206. 反转链表
 *
 * 反转一个单链表。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 * 进阶:
 * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-linked-list
 *
 * @author kyan
 * @date 2020/1/5
 */
public class No206_ReverseLinkedList {

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
     * 解法1：利用stack栈暂存链表节点，然后从栈中一个一个取出来创建新链表
     *
     * time 1ms, beat 7.85%
     * space 37.6MB, beat 13.42%
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) return null;
        LinkedList<ListNode> stack = new LinkedList<>();
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        ListNode prehead = new ListNode(-1);
        ListNode prev = prehead;
        while (!stack.isEmpty()) {
            ListNode tempNode = stack.pop();
            tempNode.next = null;
            prev.next = tempNode;
            prev = prev.next;
        }
        return prehead.next;
    }

    /**
     * 解法2：双指针（一前一后），并且前面的指针遍历过的节点的next指针都转方向指向后指针指向的节点
     * time 0ms, beat 100%
     * space 37.5MB, beat 18.95%
     *
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) return null;
//        ListNode prev = head;
//        ListNode curr = prev.next;
//        head.next = null;
        //以上处理还是太啰嗦，看以下的解法，可以直接免去head.next=null这个处理
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    /**
     * 递归解法：假设head节点之后的链表已经反转完成
     * a->b<-c<-d
     * 则接下来就是处理节点a和b之间
     * 需要a.next.next = a
     * a.next = null (这一步很关键，否则将会造成循环链表）
     *
     * time 0ms, beat 100%
     * space 36.9MB, beat 48%
     *
     * @param head
     * @return
     */
    public ListNode reverseListRecursively(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode reverseHead = reverseListRecursively(head.next);
        head.next.next = head;
        head.next = null;
        return reverseHead;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        No206_ReverseLinkedList solution = new No206_ReverseLinkedList();
//        System.out.println(solution.reverseList(head));
//        System.out.println(solution.reverseList2(head));
        System.out.println(solution.reverseListRecursively(head));
    }
}
