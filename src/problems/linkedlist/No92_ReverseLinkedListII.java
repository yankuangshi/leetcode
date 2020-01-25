package problems.linkedlist;

/**
 * [MEDIUM]
 * 92. 反转链表 II
 *
 * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
 *
 * 说明:
 * 1 ≤ m ≤ n ≤ 链表长度。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL, m = 2, n = 4
 * 输出: 1->4->3->2->5->NULL
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-linked-list-ii
 *
 * 相似题【No206】
 *
 * @author kyan
 * @date 2020/1/20
 */
public class No92_ReverseLinkedListII {

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
     * 迭代思想
     * 先找到第m个节点，然后开始反转 =》 1->2->...->(m-1)<-(m)<-(m+1)<-...<-(n) (n+1)->...->null
     * Node(m-1).next = (n)
     * Node(m).next = (n+1)
     * 所以需要找到m-1和n+1节点
     *
     * time 0ms, beat 100%
     * space 34.3MB, beat 54.36%
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || head.next == null) return head;
        ListNode preHead = new ListNode(-1);
        preHead.next = head;
        ListNode pre = preHead;
        for (int i = 1; i < m; i++) {
            pre = pre.next;
        }
        //遍历完m-1次，pre指向第m-1个节点
        ListNode start = pre;
        ListNode end = pre.next;
        ListNode cur = pre.next; //指向第m个节点
        //遍历第m到n个节点，进行反转
        for (int i = m; i <= n; i++) {
            ListNode nextTemp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nextTemp;
        }
        //遍历完成后，cur指向第n+1个节点
        start.next = pre;
        end.next = cur;
        return preHead.next;
    }


    /**
     * 反转前n个节点，例如 1->2->3->4 n=2 ==> 2->1->3->4
     *
     * @param head
     * @param n
     * @return
     */
    private ListNode successor = null;
    public ListNode reverseN(ListNode head, int n) {
        if (head == null || head.next == null) return head;
        if (n==1) {
            successor = head.next;
            return head;
        }
        ListNode reverseHead = reverseN(head.next, n-1);
        head.next.next = head;
        head.next = successor;
        return reverseHead;
    }

    /**
     * time 0ms, beat 100%
     * space 34.4MB, beat 43.14%
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetweenRecursivly(ListNode head, int m, int n) {
        if (head == null || head.next == null) return head;
        if (m == 1) {
            return reverseN(head, n);
        } else {
            head.next = reverseBetweenRecursivly(head.next, m-1, n-1);
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        No92_ReverseLinkedListII solution = new No92_ReverseLinkedListII();
//        System.out.println(solution.reverseBetween(head, 2, 4));
//        System.out.println(solution.reverseN(head, 3));
        System.out.println(solution.reverseBetweenRecursivly(head, 2, 4));
    }
}
