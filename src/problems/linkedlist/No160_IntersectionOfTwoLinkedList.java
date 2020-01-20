package problems.linkedlist;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * [EASY]
 * 160. 相交链表
 *
 * 编写一个程序，找到两个单链表相交的起始节点。
 *
 * 注意：
 *
 * 如果两个链表没有交点，返回 null.
 * 在返回结果后，两个链表仍须保持原有的结构。
 * 可假定整个链表结构中没有循环。
 * 程序尽量满足 O(n) 时间复杂度，且仅用 O(1) 内存。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/intersection-of-two-linked-lists
 *
 * 相关练习：141. 环形链表
 *
 * @author kyan
 * @date 2020/1/7
 */
public class No160_IntersectionOfTwoLinkedList {

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
     * 创建两个指针 pApA 和 pBpB，分别初始化为链表 A 和 B 的头结点。然后让它们向后逐结点遍历。
     * 当 pApA 到达链表的尾部时，将它重定位到链表 B 的头结点 (你没看错，就是链表 B); 类似的，当 pBpB 到达链表的尾部时，将它重定位到链表 A 的头结点。
     * 若在某一时刻 pApA 和 pBpB 相遇，则 pApA/pBpB 为相交结点。
     * 想弄清楚为什么这样可行, 可以考虑以下两个链表: A={1,3,5,7,9,11} 和 B={2,4,9,11}，相交于结点 9。 由于 B.length (=4) < A.length (=6)，pBpB 比 pApA 少经过 22 个结点，会先到达尾部。将 pBpB 重定向到 A 的头结点，pApA 重定向到 B 的头结点后，pBpB 要比 pApA 多走 2 个结点。因此，它们会同时到达交点。
     * 如果两个链表存在相交，它们末尾的结点必然相同。因此当 pApA/pBpB 到达链表结尾时，记录下链表 A/B 对应的元素。若最后元素不相同，则两个链表不相交。
     *
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/intersection-of-two-linked-lists/solution/xiang-jiao-lian-biao-by-leetcode/
     * 来源：力扣（LeetCode）
     *
     * 时间复杂度O(m+n)
     * 空间复杂度O(1)
     * @param headA
     * @param headB
     * @return
     */
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

    /**
     * HashSet方法：先把链表A逐个节点放入Set中，然后再遍历链表B，判断链表B中的节点是否存在Set中
     *
     * 时间复杂度O(n+m)
     * 空间复杂度O(n)或O(m)
     *
     * time 9ms, beat 20%
     * space 43.5MB, beat 82%
     * 该解法优势就是容易理解！但是表现很差！
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
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
        No160_IntersectionOfTwoLinkedList solution = new No160_IntersectionOfTwoLinkedList();
//        System.out.println(solution.getIntersectionNode(headA, headB));
        System.out.println(solution.getIntersectionNode2(headA, headB));

        //不相交
        //4->1->8
        //5->0->3
        ListNode a = new ListNode(4);
        a.next = new ListNode(1);
        a.next.next = new ListNode(8);

        ListNode b = new ListNode(5);
        b.next = new ListNode(0);
//        System.out.println(solution.getIntersectionNode(a, b));
        System.out.println(solution.getIntersectionNode2(a, b));
    }
}
