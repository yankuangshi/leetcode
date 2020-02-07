package problems.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 215. 数组中第K个最大元素
 * 难度：中等
 *
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 *
 * 示例 1:
 *
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 * 示例 2:
 *
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 * 说明:
 *
 * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 *
 * @author kyan
 * @date 2020/2/5
 */
public class _215_KthLargest {

    /**
     * 解法一（最简单）
     * 先对数组排序（升序排序），第 K 大元素是倒数第 K 个元素，即 nums[len-k]
     *
     * 时间复杂度：因为使用的JDK的Arrays.sort，默认是快排方式，时间复杂度O(NlogN)
     * 空间复杂度：使用的原地排序，空间复杂度O(1)
     *
     * 2 ms, 96.89%
     * 37 MB, 92.87%
     */
    public static class Solution1 {

        public int findKthLargest(int[] nums, int k) {
            Arrays.sort(nums);
            return nums[nums.length-k];
        }

    }

    /**
     * 解法二
     * 利用小顶堆的特性（堆顶元素最小），先对前K个数组元素进行"原地"建小顶堆，建完小顶堆后，堆顶的元素最小，正好是这K个元素的第K大元素。
     * 然后遍历剩下的元素 nums[k] ~ nums[len-1]
     * 1、如果比堆顶元素小，跳过
     * 2、如果比堆顶元素大，和堆顶元素交换后重新堆化
     *
     * 建堆 buildHeap 时间复杂度 O(K)，遍历剩下元素并且堆化 时间复杂度(N-K)*O(logK)，总体的时间复杂度 O(NlogK)
     *
     * 1 ms, 100%
     * 36.9 MB, 94.47%
     */
    public static class Solution2 {

        public int findKthLargest(int[] nums, int k) {
            //前K个元素原地建小顶堆
            buildHeap(nums, k);
            //遍历剩下元素，比堆顶小，跳过；比堆顶大，交换后重新堆化
            for (int i = k; i < nums.length; i++) {
                if (nums[i] > nums[0]) {
                    swap(nums, i, 0);
                    heapify(nums, k, 0);
                }
            }
            //K个元素的小顶堆的堆顶即是第K大元素
            return nums[0];
        }

        /**
         * 建堆函数
         * 从倒数第一个非叶子节点开始堆化，倒数第一个非叶子节点下标为 K/2-1
         * @param a
         * @param k
         */
        public void buildHeap(int[] a, int k) {
            for (int i = k/2 - 1; i >= 0; i--) {
                heapify(a, k, i);
            }
        }

        /**
         * 堆化函数
         * 父节点下标i，左右子节点的下标分别为 2*i+1 和 2*i+2
         * @param a
         * @param k
         * @param i
         */
        public void heapify(int[] a, int k, int i) {
            //临时变量 minPos 用于存储最小值的下标，先假设父节点最小
            int minPos = i;
            while (true) {
                //和左子节点比较
                if (i*2+1 < k && a[i*2+1] < a[i]) minPos = i*2+1;
                //和右子节点比较
                if (i*2+2 < k && a[i*2+2] < a[minPos]) minPos = i*2+2;
                //如果minPos没有发生变化，说明父节点已经是最小了，直接跳出
                if (minPos == i) break;
                //否则交换
                swap(a, i, minPos);
                //父节点下标进行更新，继续堆化
                i = minPos;
            }
        }

        public void swap(int[] a, int n, int m) {
            int tmp = a[n];
            a[n] = a[m];
            a[m] = tmp;
        }

    }

    /**
     * 12 ms, 39.37%
     * 36.8 MB, 95.08%
     */
    public static class Solution3 {

        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(nums.length, (a, b) -> b - a);
            for (int i = 0; i < nums.length; i++) {
                maxHeap.offer(nums[i]);
            }
            for (int j = 1; j < k; j++) {
                maxHeap.poll();
            }
            return maxHeap.peek();
        }
    }

    /**
     * 10 ms, 44.51%
     * 37 MB, 92.81%
     */
    public static class Solution4 {

        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> minHeap = new PriorityQueue<>(nums.length);
            for (int i = 0; i < nums.length; i++) {
                minHeap.offer(nums[i]);
            }
            for (int j = 0; j < nums.length - k; j++) {
                minHeap.poll();
            }
            return minHeap.peek();
        }
    }

    /**
     * 5 ms, 65.84%
     * 38.6 MB, 5.05%
     */
    public static class Solution5 {

        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
            for (int i = 0; i < k; i++) {
                minHeap.offer(nums[i]);
            }
            for (int j = k; j < nums.length; j++) {
                if (nums[j] > minHeap.peek()) {
                    minHeap.poll();
                    minHeap.offer(nums[j]);
                }
            }
            return minHeap.peek();
        }

    }

    public static class Solution6 {
        //TODO partition 解法
    }

    public static void main(String[] args) {
        int[] nums = {3,2,3,1,2,4,5,5,5,6};
//        int[] nums = {2,1};
        System.out.println(new Solution1().findKthLargest(nums, 5));
        System.out.println(new Solution2().findKthLargest(nums, 5));
        System.out.println(new Solution3().findKthLargest(nums, 5));
        System.out.println(new Solution4().findKthLargest(nums, 5));
        System.out.println(new Solution5().findKthLargest(nums, 5));
    }
}
