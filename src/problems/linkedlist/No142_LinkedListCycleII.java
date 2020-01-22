package problems.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * [MEDIUM]
 * 142. 环形链表 II
 *
 * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 *
 * @author kyan
 * @date 2020/1/22
 */
public class No142_LinkedListCycleII {

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
     * 思路1：使用Set存储遍历过的节点，如果遍历结束则不成环，如果成环的话，等遍历到入环的第一个节点的时候，则在Set中会重复出现
     * time 6ms, beat 30.99%
     * space 38.3MB, beat 5%
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        Set<ListNode> container = new HashSet<>();
        while (head != null) {
            if (container.contains(head)) {
                return head;
            }
            container.add(head);
            head = head.next;
        }
        return null;
    }


    /**
     * 思路2：
     * 假设不成环的部分节点数为a，成环的节点数b，链表总长度为a+b。
     * 设置fast和slow快慢指针，fast每次走2步，slow每次走1步，记f为fast指针遍历过的节点数，s为slow指针遍历过的节点数
     * 由于每次fast走的步数是slow的2倍，因此f=2s。
     * 又因为fast和slow在环内第一次相遇时，fast必然会比slow都走n圈（n>=1），因此会有f-s=nb
     * 可以得到f=2nb，s=nb，也就是当fast和slow第一次相遇时，slow遍历了nb个节点。
     * 同时又因为，一个指针（每次只走一步），如果要遍历到入环节点处，必然遍历的节点数为a+nb，所以可以让slow指针继续走a个节点，那么slow指针到达的位置就是入环节点位置
     * 那么如何控制只让slow指针走a个节点呢？因为不成环的部分刚好等于a，所以可以让另一个指针从head位置出发，每次走1步，那么和slow指针相遇的位置一定为入环节点位置。
     *
     * time 0ms, beat 100%
     * space 38.6MB, beat 5%
     *
     * @param head
     * @return
     */
    public ListNode detectCycle2(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                //第一次相遇，然后制造第二次相遇
                fast = head;
                while (fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }
                return slow;

            }
        }
        return null;
    }
}
