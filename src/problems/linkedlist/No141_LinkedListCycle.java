package problems.linkedlist;

/**
 * [EASY]
 * 141. 环形链表
 *
 * 给定一个链表，判断链表中是否有环。
 *
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 *
 * 示例 1：
 *
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 *
 * 示例 2：
 *
 * 输入：head = [1,2], pos = 0
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第一个节点。
 *
 * 示例 3：
 *
 * 输入：head = [1], pos = -1
 * 输出：false
 * 解释：链表中没有环。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/linked-list-cycle
 *
 * @author kyan
 * @date 2020/1/5
 */
public class No141_LinkedListCycle {


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
     * 题目描述太奇葩，给定的参数里面根本没有pos这个参数
     *
     * 利用快慢双指针fast和slow的追及问题判断是否有环（如果有环，fast指针肯定会追上slow指针）
     * 快慢指针可以参考 234. 回文链表
     *
     * time 0ms, beat 100.00%
     * space 38MB, beat 94.62%
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode fast = head;
        ListNode slow = head;
        //如果不成环，那么fast肯定最先遍历完成，遍历完成的条件的是 fast == null || fast.next = null
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        n1.next = n2;
        n2.next = n1;
        No141_LinkedListCycle solution = new No141_LinkedListCycle();
        System.out.println(solution.hasCycle(n1));

        ListNode k1 = new ListNode(1);
        ListNode k2 = new ListNode(2);
        k1.next = k2;
        System.out.println(solution.hasCycle(k1));
    }
}
