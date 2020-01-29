package problems.linkedlist;

import problems.common.util.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 160. 相交链表
 * 难度：简单
 * <p>
 * 编写一个程序，找到两个单链表相交的起始节点。
 * <p>
 * 注意：
 * <p>
 * 如果两个链表没有交点，返回 null.
 * 在返回结果后，两个链表仍须保持原有的结构。
 * 可假定整个链表结构中没有循环。
 * 程序尽量满足 O(n) 时间复杂度，且仅用 O(1) 内存。
 * <p>
 * 相关练习：141. 环形链表
 *
 * @author kyan
 * @date 2020/1/7
 */
public class _160_IntersectionOfTwoLinkedList {

    /**
     * 创建两个指针 pA 和 pB，分别初始化为链表 A 和 B 的头结点。然后让它们向后逐结点遍历。
     * 当 pA 到达链表的尾部时，将它重定位到链表 B 的头结点 (你没看错，就是链表 B); 类似的，当 pB 到达链表的尾部时，将它重定位到链表 A 的头结点。
     * 若在某一时刻 pA 和 pB 相遇，则 pA/pB 为相交结点。
     * 想弄清楚为什么这样可行, 可以考虑以下两个链表: A={1,3,5,7,9,11} 和 B={2,4,9,11}，相交于结点 9。 由于 B.length (=4) < A.length (=6)，
     * pB 比 pA 少经过 2 个结点，会先到达尾部。将 pB 重定向到 A 的头结点，pA 重定向到 B 的头结点后，pB 要比 pA 多走 2 个结点。因此，它们会同时到达交点。
     * 如果两个链表存在相交，它们末尾的结点必然相同。因此当 pA/pB 到达链表结尾时，记录下链表 A/B 对应的元素。若最后元素不相同，则两个链表不相交。
     * <p>
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/intersection-of-two-linked-lists/solution/xiang-jiao-lian-biao-by-leetcode/
     * 来源：力扣（LeetCode）
     * <p>
     * 时间复杂度O(m+n)
     * 空间复杂度O(1)
     *
     */
    public static class Solution1 {

        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    //        if (headA == null || headB == null) return null;
            ListNode p1 = headA;
            ListNode p2 = headB;
            while (p1 != p2) {
                //条件：只要p1和p2不等就一直继续（不需要怕死循环，因为如果不相交，p1和p2也总会一起变成null）
    //            if (p1 != null) {
    //                p1 = p1.next;
    //            } else {
    //                p1 = headB;
    //            }
    //            if (p2 != null) {
    //                p2 = p2.next;
    //            } else {
    //                p2 = headA;
    //            }
                p1 = (p1 != null) ? p1.next : headB;
                p2 = (p2 != null) ? p2.next : headA;
            }
            return p1;
        }
    }

    /**
     * HashSet方法：先把链表A逐个节点放入Set中，然后再遍历链表B，判断链表B中的节点是否存在Set中
     * <p>
     * 时间复杂度O(n+m)
     * 空间复杂度O(n)或O(m)
     * <p>
     * time 9ms, beat 20%
     * space 43.5MB, beat 82%
     * 该解法优势就是容易理解！但是表现很差！
     */
    public static class Solution2 {

        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            Set<ListNode> set = new HashSet<>();
            while (headA != null) {
                set.add(headA);
                headA = headA.next;
            }
            while (headB != null) {
                if (set.contains(headB)) return headB;
                headB = headB.next;
            }
            return null;
        }
    }



    public static void main(String[] args) {
        //4->1->8(相交)->4
        //5->0->1->8(相交)->4
        ListNode headA = new ListNode(4);
        headA.next = new ListNode(1);
        ListNode headB = new ListNode(5);
        headB.next = new ListNode(0);
        headB.next.next = new ListNode(1);
        ListNode intersection = new ListNode(8);
        headA.next.next = intersection;
        headB.next.next.next = intersection;
        intersection.next = new ListNode(4);
        System.out.println(new Solution1().getIntersectionNode(headA, headB));

        //不相交
        //4->1->8
        //5->0->3
        ListNode a = new ListNode(4);
        a.next = new ListNode(1);
        a.next.next = new ListNode(8);

        ListNode b = new ListNode(5);
        b.next = new ListNode(0);
        System.out.println(new Solution1().getIntersectionNode(a, b));
    }
}
