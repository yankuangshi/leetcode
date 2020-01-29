package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 147. 对链表进行插入排序
 * 难度：中等
 * 题目描述：
 * 对链表进行插入排序。
 * <p>
 * 插入排序的动画演示如上。从第一个元素开始，该链表可以被认为已经部分排序（用黑色表示）。
 * 每次迭代时，从输入数据中移除一个元素（用红色表示），并原地将其插入到已排好序的链表中。
 * <p>
 * 插入排序算法：
 * <p>
 * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
 * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
 * 重复直到所有输入数据插入完为止。
 *
 * @author kyan
 * @date 2020/1/26
 */
public class _147_InsertionSortList {

    /**
     * time 28ms, beat 37.45%
     * space 38.1MB, beat 28.38%
     */
    public static class Solution1 {

        public ListNode insertionSortList(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode preHead = new ListNode(-1);
            preHead.next = head;
            ListNode last = head;
            ListNode cur = null, next = null, pre = null;
            while (last != null && last.next != null) {
                cur = last.next;
                next = cur.next;
                pre = preHead;
                while (pre != last) {
                    if (cur.val < pre.next.val) {
                        cur.next = pre.next;
                        pre.next = cur;
                        last.next = next;
                        break;
                    } else {
                        pre = pre.next;
                    }
                }
                if (last.next != next) {
                    last = cur;
                }
                cur = next;
            }
            return preHead.next;
        }
    }

    /**
     * 优化后
     * cur为已排序完部分的最末节点，next为待插入的节点
     * 只有当待插入节点值next.val < 已排序完部分最末节点值cur.val 时，才需要插入。
     * <p>
     * time 4ms, beat 96.71%
     * space 37.3MB, beat 80.98%
     */
    public static class Solution2 {

        public ListNode insertionSortList(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode preHead = new ListNode(-1);
            preHead.next = head;
            ListNode cur = head;
            ListNode pre, next;
            while (cur != null && cur.next != null) {
                next = cur.next;
                if (next.val < cur.val) {
                    //只有当next节点的值小于cur节点值时，才需要插入排序
                    pre = preHead;
                    while (pre != cur) {
                        if (next.val < pre.next.val) {
                            cur.next = next.next;
                            next.next = pre.next;
                            pre.next = next;
                            break;
                        } else {
                            pre = pre.next;
                        }
                    }
                } else {
                    cur = next;
                }
            }
            return preHead.next;
        }
    }


    public static void main(String[] args) {
        //-1->5->3->4->0
        ListNode head = new ListNode(-1);
        head.next = new ListNode(5);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(0);
//        System.out.println(new Solution1().insertionSortList(head));
        System.out.println(new Solution2().insertionSortList(head));
        //should print -1->0->3->4->5
    }
}
