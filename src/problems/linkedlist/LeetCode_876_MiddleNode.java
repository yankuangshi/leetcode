package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 876. 链表的中间结点
 * 难度：简单
 * <p>
 * 给定一个带有头结点 head 的非空单链表，返回链表的中间结点。
 * <p>
 * 如果有两个中间结点，则返回第二个中间结点。
 * <p>
 * 示例 1：
 * <p>
 * 输入：[1,2,3,4,5]
 * 输出：此列表中的结点 3 (序列化形式：[3,4,5])
 * 返回的结点值为 3 。 (测评系统对该结点序列化表述是 [3,4,5])。
 * 注意，我们返回了一个 ListNode 类型的对象 ans，这样：
 * ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及 ans.next.next.next = NULL.
 * 示例 2：
 * <p>
 * 输入：[1,2,3,4,5,6]
 * 输出：此列表中的结点 4 (序列化形式：[4,5,6])
 * 由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。
 *
 * @author kyan
 * @date 2020/1/22
 */
public class LeetCode_876_MiddleNode {

    /**
     * 思路：快慢指针
     * <p>
     * 偶数个节点情况：1->2->3->4->5->6
     * fast指针遍历节点1-3-5-null，slow指针遍历节点1-2-3-4，最后4就是要返回的中间节点
     * <p>
     * 奇数个节点情况：1->2->3->4->5
     * fast指针遍历节点1-3-5，slow指针遍历节点1-2-3，最后3就是要返回的中间节点
     * <p>
     * time 0ms, beat 100%
     * space 34.2MB, beat 5.54%
     */
    public static class Solution1 {

        public ListNode middleNode(ListNode head) {
            ListNode fast = head;
            ListNode slow = head;
            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            return slow;
        }
    }

    public static void main(String[] args) {
        //1->2->3->4->5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        System.out.println(new Solution1().middleNode(head).val);
        //should print 3
    }

}
