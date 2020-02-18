package problems.heap;

import java.util.*;

/**
 * 480. 滑动窗口中位数
 * 难度：困难
 *
 * 题目描述
 *
 * 中位数是有序序列最中间的那个数。如果序列的大小是偶数，则没有最中间的数；此时中位数是最中间的两个数的平均数。
 * 例如：
 * [2,3,4]，中位数是 3
 * [2,3]，中位数是 (2 + 3) / 2 = 2.5
 *
 * 给出一个数组 nums，有一个大小为 k 的窗口从最左端滑动到最右端。窗口中有 k 个数，每次窗口向右移动 1 位。你的任务是找出每次窗口移动后得到的新窗口中元素的中位数，并输出由它们组成的数组。
 * 示例：
 * 给出 nums = [1,3,-1,-3,5,3,6,7]，以及 k = 3。
 *
 * 窗口位置                      中位数
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       1
 *  1 [3  -1  -3] 5  3  6  7       -1
 *  1  3 [-1  -3  5] 3  6  7       -1
 *  1  3  -1 [-3  5  3] 6  7       3
 *  1  3  -1  -3 [5  3  6] 7       5
 *  1  3  -1  -3  5 [3  6  7]      6
 * 因此，返回该滑动窗口的中位数数组 [1,-1,-1,3,5,6]。
 *
 * 提示：
 * 你可以假设 k 始终有效，即：k 始终小于输入的非空数组的元素个数。
 * 与真实值误差在 10 ^ -5 以内的答案将被视作正确答案。
 *
 * @author kyan
 * @date 2020/2/18
 */
public class LeetCode_480_SlidingWindowMedian {

    /**
     * 41 ms, 57.49%
     * 51.7 MB, 5.45%
     */
    public static class Solution1 {
        public double[] medianSlidingWindow(int[] nums, int k) {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            int len = nums.length;
            double[] res = new double[len-k+1];
            for (int i = 0; i < k; i++) {
                //init heap
                maxHeap.add(nums[i]);
                minHeap.add(maxHeap.poll());
                if (maxHeap.size() < minHeap.size()) {
                    maxHeap.add(minHeap.poll());
                }
            }
            if (k % 2 == 0) {
                res[0] = (double)maxHeap.peek() * 0.5 + (double)minHeap.peek() * 0.5;
            } else {
                res[0] = (double)maxHeap.peek();
            }
            Map<Integer, Integer> hashMap = new HashMap<>();
            for (int i = k; i < len; i++) {
                //i为窗口最左端，则最右端是i-k+1
                int numOut = nums[i-k];
                int numIn = nums[i];
                int balance = numOut <= maxHeap.peek() ? -1 : 1;
                hashMap.put(numOut, hashMap.getOrDefault(numOut, 0) + 1);
                if (numIn <= maxHeap.peek()) {
                    balance++;
                    maxHeap.add(numIn);
                } else {
                    balance--;
                    minHeap.add(numIn);
                }
                //根据 balance 再次调整大小堆
                if (balance < 0) {
                    maxHeap.add(minHeap.poll());
                }
                if (balance > 0) {
                    minHeap.add(maxHeap.poll());
                }
                while (hashMap.getOrDefault(maxHeap.peek(), 0) > 0) {
                    Integer tmp = maxHeap.poll();
                    hashMap.put(tmp, hashMap.get(tmp) -1);
                }
                while (!minHeap.isEmpty() && hashMap.getOrDefault(minHeap.peek(), 0) > 0) {
                    Integer tmp = minHeap.poll();
                    hashMap.put(tmp, hashMap.get(tmp) -1);
                }
                if (k % 2 == 0) {
                    res[i-k+1] = (double)maxHeap.peek() * 0.5 + (double)minHeap.peek() * 0.5;
                } else {
                    res[i-k+1] = (double)maxHeap.peek();
                }
            }
            return res;
        }
    }

    /**
     * 45 ms, 50.72%
     * 52.5 MB, 5.45%
     */
    public static class Solution2 {

        public double[] medianSlidingWindow(int[] nums, int k) {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            double[] res = new double[nums.length-k+1];
            for (int i = 0; i < nums.length; i++) {
                //i从0~k-1，第一个窗口不需要删除
                if (i >= k) {
                    //i为窗口最左边界，则窗口的最右边界为i-k+1，最右边界的左边元素 nums[i-k] 即为移动窗口后需要删除的元素
                    if (nums[i-k] <= maxHeap.peek()) {
                        maxHeap.remove(nums[i-k]);
                    } else {
                        minHeap.remove(nums[i-k]);
                    }
                }
                add(maxHeap, minHeap, nums[i]);
                if (i >= k-1) {
                    if (k % 2 == 0) {
                        res[i-k+1] = (double)maxHeap.peek() * 0.5 + (double)minHeap.peek() * 0.5;
                    } else {
                        res[i-k+1] = (double)maxHeap.peek();
                    }
                }
            }
            return res;
        }

        private void add(PriorityQueue<Integer> maxHeap, PriorityQueue<Integer> minHeap, int num) {
            maxHeap.add(num);
            minHeap.add(maxHeap.poll());
            while (minHeap.size() > maxHeap.size()) {
                maxHeap.add(minHeap.poll());
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 4;
//        int[] nums = {2147483647,2147483647};
//        int k = 2;
        System.out.println(Arrays.toString(new Solution1().medianSlidingWindow(nums, k)));
        System.out.println(Arrays.toString(new Solution2().medianSlidingWindow(nums, k)));
    }
}
