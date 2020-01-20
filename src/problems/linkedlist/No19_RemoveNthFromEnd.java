package problems.linkedlist;

/**
 * [MEDIUM]
 * 19. 删除链表的倒数第N个节点
 *
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 *
 * 示例：
 *
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 *
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：
 *
 * 给定的 n 保证是有效的。
 *
 * 进阶：
 *
 * 你能尝试使用一趟扫描实现吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 *
 * @author kyan
 * @date 2020/1/12
 */
public class No19_RemoveNthFromEnd {

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
     * 思路：通过扫描2趟完成
     * 第一趟先求出链表总长度l，那么要删除的倒数第n个节点的顺序值就是第l-n+1个
     * 第二趟进行删除，找出第l-n个节点，把它的next指针指向第l-n+2个节点
     * 时间复杂度 第一次遍历 O(n)，第二次遍历O(n-k)，因此总的时间复杂度O(2n-k) => O(n)
     * time 1ms, beat 50.57%
     * space 35.1MB, beat 86%
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int l = 0;
        ListNode pre = head;
        while (pre != null) {
            l++;
            pre = pre.next;
        }
        int k = 0;
        //设置哨兵节点，以防链表只有一个节点的情况
        ListNode preHead = new ListNode(0);
        pre = preHead;
        pre.next = head;
        while (k < l-n) {
            k++;
            pre = pre.next;
        }
        pre.next = pre.next.next;
        return preHead.next;
    }

    /**
     * 思路2：只扫描一遍
     * 利用前后指针，前指针指向第n个节点，后指针指向哨兵节点(preHead)，然后每次两个指针都只往后移动一个节点
     * 当前指针到达链表尾部时，后指针刚好指向了第l-n个节点
     *
     * time 1ms, beat 50.57%
     * space 34.6MB, beat 87.5%
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode before = preHead, after = preHead;
        for (int i = 0; i < n; i++) {
            before = before.next;
        }
        while (before.next != null) {
            before = before.next;
            after = after.next;
        }
        after.next = after.next.next;
        return preHead.next;
    }

    public static void main(String[] args) {
        //测试[1,2,3,4,5],2的情况
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        No19_RemoveNthFromEnd solution = new No19_RemoveNthFromEnd();
//        System.out.println(solution.removeNthFromEnd(head, 2));
        System.out.println(solution.removeNthFromEnd2(head, 2));
        //测试[1],1的情况
        ListNode test = new ListNode(1);
//        System.out.println(solution.removeNthFromEnd(test, 1));
        System.out.println(solution.removeNthFromEnd2(test, 1));
    }
}
