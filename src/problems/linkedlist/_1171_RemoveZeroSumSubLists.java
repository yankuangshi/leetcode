package problems.linkedlist;

import problems.common.util.ListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 1171. 从链表中删去总和值为零的连续节点
 * 难度：中等
 * <p>
 * 给你一个链表的头节点 head，请你编写代码，反复删去链表中由 总和 值为 0 的连续节点组成的序列，直到不存在这样的序列为止。
 * <p>
 * 删除完毕后，请你返回最终结果链表的头节点。
 * <p>
 * 你可以返回任何满足题目要求的答案。
 * <p>
 * （注意，下面示例中的所有序列，都是对 ListNode 对象序列化的表示。）
 * <p>
 * 示例 1：
 * <p>
 * 输入：head = [1,2,-3,3,1]
 * 输出：[3,1]
 * 提示：答案 [1,2,1] 也是正确的。
 * 示例 2：
 * <p>
 * 输入：head = [1,2,3,-3,4]
 * 输出：[1,2,4]
 * 示例 3：
 * <p>
 * 输入：head = [1,2,3,-3,-2]
 * 输出：[1]
 *
 * @author kyan
 * @date 2020/1/27
 */
public class _1171_RemoveZeroSumSubLists {

    /**
     * 思路：
     * 先对每个节点求出其前缀和，例如：
     * [1,2,3,-3,4] => [1,3,6,3,7]
     * 可以看出总和3是重复出现的，也即对应的节点2之后的节点到节点-3之间的节点总和为0（3，-3）
     * 只需要删除节点3和-3，即node(2).next = node(-3).next
     * 现在的问题是如何来储存前缀和，可以利用hashmap，且对前缀和不会有重复
     * [1,node(1)]
     * [3,node(2)]
     * [6,node(3)]
     * [3,node(-3)] => 覆盖 [3,node(2)]
     * [7,node(4)]
     * 因此可以通过两次遍历，第一次遍历计算前缀和，并存入hashmap中，如果前缀和有重复，则hashmap中对应存储的一定是后一个节点
     * 第二次遍历，重新计算前缀和，并且把第一次出现的前缀和的节点的next指针指向hashmap中前缀和对应的节点的next节点
     * <p>
     * 但是，要注意有可能出现的[1,-1]这种情况，因此需要一个值为0的哨兵节点，来存储前缀和为0的情况。
     */
    public static class Solution1 {

        public ListNode removeZeroSumSublists(ListNode head) {
            if (head == null) return head;
            Map<Integer, ListNode> prefixSumMap = new HashMap<>();
            ListNode preHead = new ListNode(0);
            preHead.next = head;
            ListNode pre = preHead;
            int prefixSum = 0;
            while (pre != null) {
                prefixSum += pre.val;
                prefixSumMap.put(prefixSum, pre);
                pre = pre.next;
            }
            pre = preHead;
            prefixSum = 0;
            while (pre != null) {
                prefixSum += pre.val;
                pre.next = prefixSumMap.get(prefixSum).next;
                pre = pre.next;
            }
            return preHead.next;
        }
    }

    public static void main(String[] args) {
        //1,2,-2,2
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(-2);
        head.next.next.next = new ListNode(2);
        System.out.println(new Solution1().removeZeroSumSublists(head));
        //should print 1->2
    }
}
