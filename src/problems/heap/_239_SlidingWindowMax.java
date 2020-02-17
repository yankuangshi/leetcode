package problems.heap;

import java.util.*;

/**
 * 239. 滑动窗口最大值
 * 难度：困难
 *
 * 题目描述
 *
 * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回滑动窗口中的最大值。
 *
 * 示例:
 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 * 输出: [3,3,5,5,6,7]
 * 解释:
 *   滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 *
 * 提示：
 * 你可以假设 k 总是有效的，在输入数组不为空的情况下，1 ≤ k ≤ 输入数组的大小。
 *
 * 进阶：
 * 你能在线性时间复杂度内解决此题吗？
 *
 * @author kyan
 * @date 2020/2/17
 */
public class _239_SlidingWindowMax {

    /**
     * 解法一
     * 大顶堆
     * 64 ms, 7.95%
     * 54.3 MB, 5.03%
     */
    public static class Solution1 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length == 0 || k == 1) return nums;
            PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, (n1, n2) -> (n2 - n1));
            int len = nums.length;
            //长度len的数组，有 len-k+1 个长度为k的窗口
            int[] res = new int[len-k+1];
            for (int i = 0; i < len; i++) {
                if (i < k-1) {
                    maxHeap.add(nums[i]);
                } else {
                    maxHeap.add(nums[i]);
                    res[i-k+1] = maxHeap.peek();
                    maxHeap.remove(nums[i-k+1]);
                }
            }
            return res;
        }
    }

    /**
     * 解法二
     * 解法一基础上优化
     * 28 ms, 30.42%
     * 53.8 MB, 5.03%
     */
    public static class Solution2 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length == 0 || k == 1) return nums;
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, (i, j) -> (nums[j] - nums[i]));
            int len = nums.length;
            int[] res = new int[len-k+1];
            for (int i = 0; i < len; i++) {
                maxHeap.add(i);
                if (i >= k-1) {
                    while (maxHeap.peek() < i-k+1) {
                        maxHeap.remove();
                    }
                    res[i-k+1] = nums[maxHeap.peek()];
                }
            }
            return res;
        }
    }

    /**
     * 解法三
     * 利用单调队列
     * 21 ms, 37.27%
     * 53.7 MB, 5.03%
     */
    public static class Solution3 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length == 0 || k == 1) return nums;
            Deque<Integer> deque = new LinkedList<>();
            int len = nums.length;
            int[] res = new int[len-k+1];
            for (int i = 0; i < len; i++) {
                while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                    deque.removeLast();
                }
                deque.addLast(i);
                if (i >= k-1) {
                    while (deque.peekFirst() < i-k+1) {
                        deque.removeFirst();
                    }
                    res[i-k+1] = nums[deque.peekFirst()];
                }
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int[] nums = {7,2,4};
        int k = 2;
        System.out.println(Arrays.toString(new Solution1().maxSlidingWindow(nums, k)));
        System.out.println(Arrays.toString(new Solution2().maxSlidingWindow(nums, k)));
        System.out.println(Arrays.toString(new Solution3().maxSlidingWindow(nums, k)));
    }
}
