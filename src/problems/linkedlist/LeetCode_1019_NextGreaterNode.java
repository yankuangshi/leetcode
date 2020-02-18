package problems.linkedlist;

import problems.common.util.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 1019. 链表中的下一个更大节点
 * 难度：中等
 *
 * 给出一个以头节点 head 作为第一个节点的链表。链表中的节点分别编号为：node_1, node_2, node_3, ... 。
 *
 * 每个节点都可能有下一个更大值（next larger value）：对于 node_i，如果其 next_larger(node_i) 是 node_j.val，那么就有 j > i 且  node_j.val > node_i.val，而 j 是可能的选项中最小的那个。如果不存在这样的 j，那么下一个更大值为 0 。
 *
 * 返回整数答案数组 answer，其中 answer[i] = next_larger(node_{i+1}) 。
 *
 * 注意：在下面的示例中，诸如 [2,1,5] 这样的输入（不是输出）是链表的序列化表示，其头节点的值为 2，第二个节点值为 1，第三个节点值为 5 。
 *
 * 示例 1：
 *
 * 输入：[2,1,5]
 * 输出：[5,5,0]
 * 示例 2：
 *
 * 输入：[2,7,4,3,5]
 * 输出：[7,0,5,5,0]
 * 示例 3：
 *
 * 输入：[1,7,5,1,9,2,5,1]
 * 输出：[7,9,9,9,0,5,0,0]
 *
 * @author kyan
 * @date 2020/2/1
 */
public class LeetCode_1019_NextGreaterNode {


    /**
     * 思路1
     * 利用单调栈（递减栈），与数组的区别在于需要记录遍历的节点的index
     * 因此可以将 ListNode和Index包装成一个类IndexNode(ListNode, int)
     * 总共需要遍历2次链表，第一次计算链表长度，用于创建数组结果int[] res=new int[list_size]
     *
     * 例如：2->7->4->3->5
     *
     * step1：2->7->4->3->5 stack empty,push(2)
     *        ^
     * |            |
     * |            |
     * |_(node2, 0)_|  res=[0,0,0,0,0]
     *
     * step2：2->7->4->3->5 7>top(2),pop(2),res[0]=7,push(7)
     *           ^
     * |            |
     * |            |
     * |_(node7, 1)_|  res=[7,0,0,0,0]
     *
     * step3：2->7->4->3->5 4<top(7),push(4)
     *              ^
     * |            |
     * | (node4, 2) |
     * |_(node7, 1)_|  res=[7,0,0,0,0]
     *
     * step4：2->7->4->3->5 3<top(4),push(3)
     *                 ^
     * | (node3, 3) |
     * | (node4, 2) |
     * |_(node7, 1)_|  res=[7,0,0,0,0]
     *
     * step4：2->7->4->3->5 5>top(3),pop(3),res[3]=5,5>top(4),pop(4),res[2]=5,5<top(7),push(5)
     *                    ^
     * |            |
     * | (node5, 4) |
     * |_(node7, 1)_|  res=[7,0,5,5,0]
     *
     * time 13ms, beat 95.50%
     * space 41.3MB, beat 12.84%
     */
    public static class Solution1 {

        static class IndexNode {
            ListNode node;
            int index;

            public IndexNode(ListNode node, int index) {
                this.node = node;
                this.index = index;
            }
        }
        public int[] nextLargerNodes(ListNode head) {
            if (head == null) return null;
            if (head.next == null) return new int[1];
            int count = 0;
            ListNode cursor = head;
            while (cursor != null) {
                count++;
                cursor = cursor.next;
            }
            int[] res = new int[count];
            int i = 0;
            LinkedList<IndexNode> stack = new LinkedList<>();
            while (head != null) {
                while (!stack.isEmpty() && stack.peek().node.val < head.val) {
                    res[stack.pop().index] = head.val;
                }
                stack.push(new IndexNode(head, i));
                i++;
                head = head.next;
            }
            return res;
        }
    }

    /**
     * 思路2
     * 单调栈的优化，把链表先转换成数组，那么解法就类似题496
     * 但是把链表转换成数组，需要先确定链表长度，由于题目中假设给定列表的长度在 [0, 10000] 范围内，因此初始化的数组长度我们可以设置为10000
     *
     * 18 ms, 94.77%
     * 40.4 MB, 46.32%
     */
    public static class Solution2 {

        public int[] nextLargerNodes(ListNode head) {
            if (head == null) return null;
            if (head.next == null) return new int[1];
            int[] nums = new int[10000];
            int count = 0;
            while (head != null) {
                nums[count] = head.val;
                count++;
                head = head.next;
            }
            int[] res = new int[count];
            LinkedList<Integer> stack = new LinkedList<>();
            for (int i = count-1; i >=0; i--) {
                while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                    stack.pop();
                }
                res[i] = stack.isEmpty() ? 0 : stack.peek();
                stack.push(nums[i]);
            }
            return res;
        }
    }

    /**
     * 思路3
     * 同样是利用单调栈，初始化一个大小10000的nums数组用于存放链表节点数，一个大小10000的ans数组用于存放临时结果
     * 一边遍历链表一边填充数组nums和ans的数据，遍历完成后链表的长度count也就计算出来了
     * 最后再初始化一个大小等于链表长度count的数组，把ans中的前count个值转移到新数组res中
     * 明显的一个缺点是占用了大量的空间，因为数组是申请的连续内存空间
     *
     * 22ms, beat 93.33%
     * 40.2MB, beat 60.05%
     */
    public static class Solution3 {

        public int[] nextLargerNodes(ListNode head) {
            int[] nums = new int[10000];
            int[] ans = new int[10000];
            LinkedList<Integer> stack = new LinkedList<>();
            int count = 0;
            while (head != null) {
                nums[count] = head.val;
                while (!stack.isEmpty() && nums[stack.peek()] < head.val) {
                    ans[stack.pop()] = head.val;
                }
                stack.push(count);
                count++;
                head = head.next;
            }
            int[] res = new int[count];
            for (int i = 0; i < count; i++) {
                res[i] = ans[i];
            }
            return res;
        }
    }

    /**
     * 思路4
     * 利用单调栈，但是从后往前遍历，可以利用链表的递归，非常巧妙！
     *
     * 21ms, beat 93.69%
     * 42.1MB, beat 5.09%
     */
    public static class Solution4 {

        public int[] nextLargerNodes(ListNode head) {
            LinkedList<Integer> stack = new LinkedList<>();
            ArrayList<Integer> array = new ArrayList<>();
            reduce(head, stack, array);
            int size = array.size();
            int[] res = new int[size];
            for (int i = 0; i < size; i++) {
                res[i] = array.get(size -i-1);
            }
            return res;
        }

        private void reduce(ListNode node, LinkedList<Integer> stack, ArrayList<Integer> array) {
            if (node == null) {
                return;
            }
            reduce(node.next, stack, array);
            while (!stack.isEmpty() && stack.peek() <= node.val) {
                stack.pop();
            }
            array.add(stack.isEmpty() ? 0 : stack.peek());
            stack.push(node.val);
        }
    }

    public static void main(String[] args) {
        //2->7->4->3->5
        ListNode head = new ListNode(2);
        head.next = new ListNode(7);
        head.next.next = new ListNode(4);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(5);
        System.out.println(Arrays.toString(new Solution1().nextLargerNodes(head)));
        System.out.println(Arrays.toString(new Solution2().nextLargerNodes(head)));
        System.out.println(Arrays.toString(new Solution3().nextLargerNodes(head)));
        System.out.println(Arrays.toString(new Solution4().nextLargerNodes(head)));
        //should print [7, 0, 5, 5, 0]
    }
}
