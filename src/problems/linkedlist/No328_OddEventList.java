package problems.linkedlist;

/**
 * [MEDIUM]
 * 328. 奇偶链表
 *
 * 给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。
 *
 * 请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数。
 *
 * 示例 1:
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 1->3->5->2->4->NULL
 * 示例 2:
 *
 * 输入: 2->1->3->5->6->4->7->NULL
 * 输出: 2->3->6->7->1->5->4->NULL
 * 说明:
 *
 * 应当保持奇数节点和偶数节点的相对顺序。
 * 链表的第一个节点视为奇数节点，第二个节点视为偶数节点，以此类推。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/odd-even-linked-list
 *
 * @author kyan
 * @date 2020/1/12
 */
public class No328_OddEventList {

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
     * 思路：分解成2个链表（奇数链表和偶数链表）
     * 每个链表都有个头尾节点（xxHead和xxTail）
     * 最后只需要把oddTail和evenHead连接起来即可 oddTail.next = evenHead
     * time 0ms, beat 100%
     * space 36.6MB, beat 93.63%
     *
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        //若链表为空 或 链表只有1个或2个节点
        if (head == null || head.next == null || head.next.next == null) return head;
        //设置奇链表的首尾节点
        ListNode oddHead = head;
        ListNode oddTail = head;
        //设置偶链表的首尾节点
        ListNode evenHead = head.next;
        ListNode evenTail = head.next;
        ListNode curr = evenTail.next;
        int flag = 0;
        while (curr != null) {
            //flag=0时，处理奇数位置节点
            if (flag == 0) {
                oddTail.next = curr;
                oddTail = curr;
                flag = 1;
            } else {
                //flag=1时，处理偶数位置节点
                evenTail.next = curr;
                evenTail = curr;
                flag = 0;
            }
            curr = curr.next;
        }
        if (flag == 0) {
            //如果是奇数且curr=null
            oddTail.next = null;
        } else {
            evenTail.next = null;
        }
        oddTail.next = evenHead;
        return oddHead;
    }

    /**
     * 官方解法更优雅简洁
     *
     * @param head
     * @return
     */
    public ListNode oddEvenList2(ListNode head) {
        if (head == null) return head;
        ListNode oddTail = head;
        ListNode evenHead = head.next;
        ListNode evenTail = evenHead;
        while (evenTail != null && evenTail.next != null) {
            oddTail.next = evenTail.next;
            oddTail = oddTail.next;
            evenTail.next = oddTail.next;
            evenTail = evenTail.next;
        }
        oddTail.next = evenHead;
        return head;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        No328_OddEventList solution = new No328_OddEventList();
//        System.out.println(solution.oddEvenList(head));
        System.out.println(solution.oddEvenList2(head));
    }
}
