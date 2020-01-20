package problems.linkedlist;

/**
 * [MEDIUM]
 * 2. 两数相加
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 *
 * @author kyan
 * @date 2020/1/11
 */
public class No2_AddTwoNum {

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
     * 依次遍历2个链表，对于节点数少的链表进行补0
     * 并且计算进位
     *
     * time 2ms, beat 99.96%
     * space 44.7MB, beat 84.88%
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode preHead = new ListNode(0);
        ListNode pre = preHead;
        int carry = 0; //进位
        while (l1 != null || l2 != null) {
            //高位补0
            if (l1 == null) l1 = new ListNode(0);
            if (l2 == null) l2 = new ListNode(0);
            int sum = carry + l1.val + l2.val;
            if (sum >= 10) {
                carry = sum / 10;
                sum = sum % 10;
            } else {
                //如果sum小于0，要把carry清零
                carry = 0;
            }
            pre.next = new ListNode(sum);
            pre = pre.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        //当 l1 == null && l2 == null
        if (carry > 0) {
            pre.next = new ListNode(carry);
        }
        return preHead.next;
    }

    public static void main(String[] args) {
        //2->4->3 = 342
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        //5->6->4 = 465
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        No2_AddTwoNum solution = new No2_AddTwoNum();
        System.out.println("final list: " + solution.addTwoNumbers(l1, l2));
    }
}
