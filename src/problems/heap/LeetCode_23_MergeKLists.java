package problems.heap;

import problems.common.util.ListNode;

import java.util.PriorityQueue;

/**
 * 23. 合并K个排序链表
 * 难度：困难
 *
 * 题目描述
 * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
 *
 * 示例:
 *
 * 输入:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 输出: 1->1->2->3->4->4->5->6
 *
 * @author kyan
 * @date 2020/2/7
 */
public class LeetCode_23_MergeKLists {

    /**
     * 解法一
     * 建一个初始大小为链表个数 len 的小顶堆，把每个链表的头节点都放入堆中，堆顶的节点就是最小值节点
     * 循环：当堆不为空
     * 从堆顶移除堆顶节点 minNode，加入合并链表的尾部，判断被移除的堆顶节点 minNode 是否有 next 节点，有，把 next 节点加入堆
     * 继续循环
     *
     * 时间复杂度：链表个数为K，小顶堆大小为K，从小顶堆中选出堆顶后重新堆化时间复杂度为O(logK)，所有链表节点总数为N，因此总的时间复杂度是log(NlogK)
     * 空间复杂度：占用了大小为K的堆，空间复杂度O(K)
     *
     * 7 ms, 54.46%
     * 42.1 MB, 44.87%
     */
    public static class Solution1 {

        public ListNode mergeKLists(ListNode[] lists) {
            //极端情况处理
            int len = 0;
            if (lists == null || (len=lists.length) == 0) return null;
            if (len == 1) return lists[0];
            //2个或以上个链表的情况
            ListNode preHead = new ListNode(-1);
            ListNode cur = preHead;
            //按节点数值大小比较建立最小堆
            PriorityQueue<ListNode> minHeap = new PriorityQueue<>(len, (n1, n2) -> (n1.val - n2.val));
            for (ListNode list : lists) {
                if (list != null) minHeap.offer(list);
            }
            while (!minHeap.isEmpty()) {
                ListNode minNode = minHeap.poll();
                cur.next = minNode;
                cur = cur.next;
                if (minNode.next != null) {
                    minHeap.offer(minNode.next);
                }
            }
            return preHead.next;
        }
    }

    /**
     * 4 ms, 71.87%
     * 40.7 MB, 62.64%
     */
    public static class Solution2 {

        public ListNode mergeKLists(ListNode[] lists) {
            int len = 0;
            if (lists == null || (len = lists.length) == 0) return null;
            if (len == 1) return lists[0];
            if (len == 2) return merge2Lists(lists[0], lists[1]);
            return mergeInternally(lists, 0, len-1);
        }

        private ListNode mergeInternally(ListNode[] lists, int left, int right) {
            if (left == right) return lists[left];
            int mid = left + ((right - left) >> 1);
            ListNode l1 = mergeInternally(lists, left, mid);
            ListNode l2 = mergeInternally(lists, mid+1, right);
            return merge2Lists(l1, l2);
        }

        public ListNode merge2Lists(ListNode l1, ListNode l2) {
            if (l1 == null) return l2;
            if (l2 == null) return l1;
            if (l1.val < l2.val) {
                l1.next = merge2Lists(l1.next, l2);
                return l1;
            } else {
                l2.next = merge2Lists(l1, l2.next);
                return l2;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1,4,5};
        int[] nums2 = {1,3,4};
        int[] nums3 = {2,6};
        ListNode l1 = new ListNode(nums1);
        ListNode l2 = new ListNode(nums2);
        ListNode l3 = new ListNode(nums3);
        ListNode l4 = null;
        ListNode[] lists = new ListNode[4];
        lists[0] = l1;
        lists[1] = l2;
        lists[2] = l3;
        lists[3] = l4;
//        System.out.println(new Solution1().mergeKLists(lists));
        System.out.println(new Solution2().mergeKLists(lists));
    }
}
