package problems.linkedlist;

/**
 * [EASY]
 * 203. 移除链表元素
 *
 * 删除链表中等于给定值 val 的所有节点。
 *
 * 示例:
 *
 * 输入: 1->2->6->3->4->5->6, val = 6
 * 输出: 1->2->3->4->5
 *
 * @author kyan
 * @date 2020/1/22
 */
public class No203_RemoveElements {

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
     * time 1ms, beat 100%
     * space 40.1MB, beat 7.75%
     *
     * @param head
     * @param val
     * @return
     */
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
