package problems.common.util;

/**
 * ListNode is Singly-Linked List
 * ex: 1->2->3->4->5
 *
 * @author kyan
 * @date 2020/1/29
 */
public class ListNode {

    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        ListNode cursor = this;
        StringBuilder sb = new StringBuilder();
        while (cursor != null) {
            sb.append(cursor.val).append("->");
            cursor = cursor.next;
        }
        if (sb.length() > 2) {
            sb = sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }
}
