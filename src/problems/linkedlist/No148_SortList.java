package problems.linkedlist;

/**
 * [MEDIUM]
 * 148. 排序链表
 *
 * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
 *
 * 示例 1:
 *
 * 输入: 4->2->1->3
 * 输出: 1->2->3->4
 * 示例 2:
 *
 * 输入: -1->5->3->4->0
 * 输出: -1->0->3->4->5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sort-list
 *
 * @author kyan
 * @date 2020/1/9
 */
public class No148_SortList {

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
     * 递归思想：利用归并排序的思想
     * 先通过快慢指针找到中间节点，然后分为两个链表各自进行排序
     * 最后的merge其实就是合并有个有序链表的过程
     *
     * time 3ms, beat 99%
     * space 39.5MB, beat 98%
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode fast = head.next;
        ListNode slow = head;
        //寻找中间节点
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        //slow.next=null非常重要，把链表断开成左右2个链表
        ListNode right = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        right = sortList(right);
        //最后把已经有序的左右2个链表合并成最终的有序链表
        ListNode pre = new ListNode(0);
        ListNode curr = pre;
        while (left != null && right != null) {
            if (left.val < right.val) {
                curr.next = left;
                left = left.next;
            } else {
                curr.next = right;
                right = right.next;
            }
            curr = curr.next;
        }
        curr.next = (left != null) ? left : right;
        return pre.next;
    }

    public static void main(String[] args) {
        //-1->5->3->4->0
        ListNode head = new ListNode(-1);
        head.next = new ListNode(5);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(0);
        System.out.println("before sort: " + head);
        No148_SortList solution = new No148_SortList();
        System.out.println("after sort: " + solution.sortList(head));
    }
}
