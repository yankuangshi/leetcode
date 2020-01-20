package problems.linkedlist;

/**
 * [EASY]
 * 21. 合并两个有序链表
 * <p>
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
 * <p>
 * 示例：
 * <p>
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-two-sorted-lists
 *
 * @author kyan
 * @date 2020/1/5
 */
public class No21_MergeTwoLists {

    /**
     * singly-linked list
     */
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
     * 迭代方法1
     * 我的解法 不够优雅！！！
     * time 1ms, beat 82.46%
     * space 39.4MB, beat 68%
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;
        if (l1 == null && l2 != null) return l2;
        if (l1 != null && l2 == null) return l1;
        //l1 l2都不空
        ListNode head = null;
        ListNode curr = null;
        ListNode p1 = l1; //point1
        ListNode p2 = l2; //point2
        //find the head node
        if (p1.val <= p2.val) {
            head = p1;
            curr = p1;
            p1 = p1.next;
        } else {
            head = p2;
            curr = p2;
            p2 = p2.next;
        }
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                curr.next = p1;
                p1 = p1.next;
            } else {
                curr.next = p2;
                p2 = p2.next;
            }
            curr = curr.next;
        }
        if (p1 != null) curr.next = p1;
        if (p2 != null) curr.next = p2;
        return head;
    }

    /**
     * 迭代方法2，利用哨兵节点 ListNode(-1) 优雅！
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode prehead = new ListNode(-1);
        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        prev.next = l1 != null ? l1 : l2;
        return prehead.next;
    }

    /**
     * 递归思想
     * time 1ms, beat 82%
     * space 38.6MB, beat 71%
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoListsRecursively(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        //l1 l2 not null
        ListNode curr = null;
        if (l1.val < l2.val) {
            curr = l1;
            curr.next = mergeTwoListsRecursively(l1.next, l2);
        } else {
            curr = l2;
            curr.next = mergeTwoListsRecursively(l1, l2.next);
        }
        return curr;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);
        No21_MergeTwoLists solution = new No21_MergeTwoLists();
//        System.out.println(solution.mergeTwoLists(l1, l2));
        System.out.println(solution.mergeTwoLists2(l1, l2));
//        System.out.println(solution.mergeTwoListsRecursively(l1, l2));
    }
}
