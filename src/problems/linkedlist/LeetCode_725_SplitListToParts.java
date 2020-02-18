package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 725. 分隔链表
 * 难度：中等
 * <p>
 * 给定一个头结点为 root 的链表, 编写一个函数以将链表分隔为 k 个连续的部分。
 * <p>
 * 每部分的长度应该尽可能的相等: 任意两部分的长度差距不能超过 1，也就是说可能有些部分为 null。
 * <p>
 * 这k个部分应该按照在链表中出现的顺序进行输出，并且排在前面的部分的长度应该大于或等于后面的长度。
 * <p>
 * 返回一个符合上述规则的链表的列表。
 * <p>
 * 举例： 1->2->3->4, k = 5 // 5 结果 [ [1], [2], [3], [4], null ]
 * <p>
 * 示例 1：
 * 输入:
 * root = [1, 2, 3], k = 5
 * 输出: [[1],[2],[3],[],[]]
 * <p>
 * 示例 2：
 * 输入:
 * root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
 * 输出: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
 *
 * @author kyan
 * @date 2020/1/27
 */
public class LeetCode_725_SplitListToParts {

    /**
     * time 0ms, beat 100%
     * space 37.4MB, beat 7.05%
     */
    public static class Solution1 {

        public ListNode[] splitListToParts(ListNode root, int k) {
            ListNode[] ans = new ListNode[k];
            if (root == null) {
                return ans;
            } else if (k == 1) {
                ans[0] = root;
                return ans;
            }
            //遍历链表计算节点总数量count
            int count = 0;
            ListNode p = root;
            while (p != null) {
                count++;
                p = p.next;
            }
            //把链表分隔成k个part
            for (int i = 0; i < k && root != null; i++) {
                //计算每一part的节点数量
                int partCount = count / (k - i) + (count % (k - i) > 0 ? 1 : 0);
                count = count - partCount;
                ans[i] = root;
                while (--partCount > 0) {
                    root = root.next;
                }
                ListNode nextHead = root.next;
                root.next = null;
                root = nextHead;
            }
            return ans;
        }
    }


    public static void main(String[] args) {
        //1->2->3->4->5->6->7->8->9->10
        ListNode root = new ListNode(1);
        root.next = new ListNode(2);
        root.next.next = new ListNode(3);
        root.next.next.next = new ListNode(4);
        root.next.next.next.next = new ListNode(5);
        root.next.next.next.next.next = new ListNode(6);
        root.next.next.next.next.next.next = new ListNode(7);
        root.next.next.next.next.next.next.next = new ListNode(8);
        root.next.next.next.next.next.next.next.next = new ListNode(9);
        root.next.next.next.next.next.next.next.next.next = new ListNode(10);
        ListNode[] ret = new Solution1().splitListToParts(root, 3);
        for (ListNode node : ret) {
            System.out.println(node);
        }
        //should print
        //1->2->3->4
        //5->6->7
        //8->9->10
    }
}
