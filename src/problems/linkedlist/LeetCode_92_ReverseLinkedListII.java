package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 92. 反转链表 II
 * 难度：中等
 * <p>
 * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
 * <p>
 * 说明:
 * 1 ≤ m ≤ n ≤ 链表长度。
 * <p>
 * 示例:
 * <p>
 * 输入: 1->2->3->4->5->NULL, m = 2, n = 4
 * 输出: 1->4->3->2->5->NULL
 * <p>
 * 相似题：206. 反转链表
 *
 * @author kyan
 * @date 2020/1/20
 */
public class LeetCode_92_ReverseLinkedListII {

    /**
     * 迭代思想
     * 先找到第m个节点，然后开始反转 =》 1->2->...->(m-1)<-(m)<-(m+1)<-...<-(n) (n+1)->...->null
     * Node(m-1).next = (n)
     * Node(m).next = (n+1)
     * 所以需要找到m-1和n+1节点
     * <p>
     * time 0ms, beat 100%
     * space 34.3MB, beat 54.36%
     */
    public static class Solution1 {

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
    }

    /**
     * 递归
     * time 0ms, beat 100%
     * space 34.4MB, beat 43.14%
     */
    public static class Solution2 {

        private ListNode successor = null;

        public ListNode reverseBetween(ListNode head, int m, int n) {
            if (head == null || head.next == null) return head;
            if (m == 1) {
                return reverseN(head, n);
            } else {
                head.next = reverseBetween(head.next, m - 1, n - 1);
            }
            return head;
        }

        /**
         * 反转前n个节点，例如 1->2->3->4 n=2 ==> 2->1->3->4
         */
        private ListNode reverseN(ListNode head, int n) {
            if (head == null || head.next == null) return head;
            if (n == 1) {
                successor = head.next;
                return head;
            }
            ListNode reverseHead = reverseN(head.next, n - 1);
            head.next.next = head;
            head.next = successor;
            return reverseHead;
        }
    }


    public static void main(String[] args) {
        //1->2->3->4->5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
//        Solution1 solution1 = new Solution1();
//        System.out.println(solution1.reverseBetween(head, 2, 4));
        Solution2 solution2 = new Solution2();
        System.out.println(solution2.reverseBetween(head, 2, 4));
        //should print 1->4->3->2->5
    }
}
