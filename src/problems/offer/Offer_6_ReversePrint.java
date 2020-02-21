package problems.offer;

import problems.common.util.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 面试题06. 从尾到头打印链表
 * 难度：简单
 *
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 * 示例 1：
 *
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 *
 * 相似题：
 * LeetCode 206. 反转链表 https://leetcode-cn.com/problems/reverse-linked-list
 * LeetCode 92. 反转链表 II https://leetcode-cn.com/problems/reverse-linked-list-ii
 *
 * @author kyan
 * @date 2020/2/21
 */
public class Offer_6_ReversePrint {

    /**
     * 解法一
     * 辅助栈
     * 1 ms, 80.48%
     * 40.2 MB, 100.00%
     */
    public static class Solution1 {
        public int[] reversePrint(ListNode head) {
            LinkedList<Integer> stack = new LinkedList<>();
            while (head != null) {
                stack.push(head.val);
                head = head.next;
            }
            int[] res = new int[stack.size()];
            for (int i = 0; i < res.length; i++) {
                res[i] = stack.pop();
            }
            return res;
        }
    }

    /**
     * 解法二
     * 递归+回溯
     * 1 ms, 80.48%
     * 40.2 MB, 100.00%
     */
    public static class Solution2 {

        ArrayList<Integer> reversedList = new ArrayList<>();
        public int[] reversePrint(ListNode head) {
            recur(head);
            int[] res = new int[reversedList.size()];
            for (int i = 0; i < res.length; i++) {
                res[i] = reversedList.get(i);
            }
            return res;
        }

        private void recur(ListNode node) {
            if (node == null) return;;
            recur(node.next);
            reversedList.add(node.val);
        }
    }

    /**
     * 2 ms, 70.32%
     * 39.6 MB, 100.00%
     */
    public static class Solution3 {
        public int[] reversePrint(ListNode head) {
            if (head == null) return new int[0];
            ArrayList<Integer> reversedList = new ArrayList<>();
            ListNode pre = null;
            while (head != null) {
                ListNode nextTemp = head.next;
                head.next = pre;
                pre = head;
                head = nextTemp;
            }
            while (pre != null) {
                reversedList.add(pre.val);
                pre = pre.next;
            }
            int[] res = new int[reversedList.size()];
            for (int i = 0; i < reversedList.size(); i++) {
                res[i] = reversedList.get(i);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5};
        ListNode head = new ListNode(nums);
//        System.out.println(Arrays.toString(new Solution1().reversePrint(head)));
        System.out.println(Arrays.toString(new Solution2().reversePrint(head)));
    }
}
