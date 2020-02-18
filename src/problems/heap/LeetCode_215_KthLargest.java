package problems.heap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

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
public class LeetCode_215_KthLargest {

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
     * 解法一中对数组进行了全局排序，但是其实对于求第K大元素，只需要对最大的前K个元素进行局部排序就可以了。因此可以借助冒泡思想，每次从数组中找到最大值，经过K次冒泡，就可以求出第K大元素。
     *
     * 我们知道全局的冒泡排序平均时间复杂度是O(N^2)，那么对于局部的只冒泡K次的时间复杂度是O(K*N)。当 K 比较小时，比如1、2，那么最好的时间复杂度是 O(N)，但是当 K 等于 N/2 或者接近 N 时，
     * 这种情况的时间复杂度就是O(N^2)，从实际的代码运行中，我们也可以看出该解法的效率很低。
     * 97 ms, 5.04%
     * 37.9 MB, 45.73%
     */
    public static class Solution2 {

        public int findKthLargest(int[] nums, int k) {
            int len = nums.length;
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < len - 1 - i; j++) {
                    if (nums[j] > nums[j+1]) {
                        swap(nums, j, j+1);
                    }
                }
            }
            return nums[len-k];
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

    }

    /**
     * 解法三
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
    public static class Solution3 {

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
    public static class Solution4 {

        public int findKthLargest(int[] nums, int k) {
            //构造函数，利用lambda表达式构造大顶堆 (a,b) -> b-a
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
    public static class Solution5 {

        public int findKthLargest(int[] nums, int k) {
            //构造函数，默认就是小顶堆
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
    public static class Solution6 {

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

    /**
     * 解法七
     * 利用"快速排序"中的"快速选择" partition（分区函数）的方法
     * partition 函数会任意选择一个元素（该解法中选择最后一个元素arr[len-1]）作为 pivot（分区点），将数组中小于 pivot 的点都放置到其左边，将大于pivot的点都放置在其右边
     * 最终 partition函数返回 pivot 的下标 index
     * 经过这一步骤后，数组将分成3部分
     * 1、arr[0] ~ arr[index-1] 都是不大于 pivot 的元素
     * 2、arr[index+1] ~ arr[len-1] 都是不小于 pivot 的元素
     * 3、arr[index] 是 pivot 元素
     *
     * 如果 pivot 点刚好是第K大元素，那么它的左边一定有 K-1 个不小于它的元素，它的下标应该是 len-K（数组尾左边是 len-1）
     * 如果 partition 函数返回的下标 index=len-1，则 arr[index] 就是我们要求的第K大元素
     * 如果 partition 函数返回的下标 index<len-1，那么说明第K大元素在 index 下标的右边，我们重新分区 partition(arr, index+1, len-1)
     * 如果 partition 函数返回的下标 index>len-1，那么说明第K大元素在 index 下标的左边，我们重新分区 partition(arr, 0, index-1)
     *
     * 该方法的时间复杂度是O(n)，因为第一次分区，需要对大小为n的数组执行分区操作，遍历n个元素；
     * 第二次分区，只需要对大小为n/2的数组执行分区操作，遍历n/2个元素；
     * 第三次分区，遍历n/4；
     * 第四次分区，遍历n/8；
     * ...
     * 直到缩减为1
     * 累加起来 n + n/2 + n/4 + n/8 + ... + 1 该公式是个等比数列，等比数列求和
     * 假设 S = n + n/2 + n/4 + n/8 + .... + 1，两边同时乘以2
     * 2S = 2n + n + n/2 + n/4 + ... + 2
     * 2S - S = (2n + n + n/2 + n/4 + ... + 2) - (n + n/2 + n/4 + n/8 + .... + 1) = 2n-1
     * S = 2n-1
     * 所以总体时间复杂度为O(n)
     *
     * 当然这只是理想情况，每次分区都能分到一半，如果遇到特殊极端情况，例如数组是升序数组，而每次 pivot 点都选择首尾元素，那么分区的效率就会大大降低
     *
     * 这也是为什么在loj上的执行效率会那么低的缘故，因为测试用例中有极端情况的数组
     *
     * 41 ms, 14.81%
     * 38.1 MB, 26.92%
     */
    public static class Solution7 {
        public int findKthLargest(int[] nums, int k) {
            int len = nums.length;
            int targetIndex = len - k;
            int low = 0, high = len - 1;
            while (true) {
                int index = partition(nums, low, high);
                if (index == targetIndex) {
                    return nums[index];
                } else if (index < targetIndex) {
                    low = index + 1;
                } else {
                    high = index - 1;
                }
            }
        }

        private int partition(int[] arr, int low, int high) {
            int i = low;
            int pivot = arr[high];
            for (int j = low; j < high; j++) {
                if (arr[j] < pivot) {
                    swap(arr, i, j);
                    i++;
                }
            }
            swap(arr, i, high);
            return i;
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    /**
     * 解法七
     * 在解法六的基础上优化分区函数的pivot分区点的选择，通过随机选择的方法提高分区效率
     *
     * 2 ms, 96.88%
     * 37.1 MB, 92.06%
     */
    public static class Solution8 {
        public int findKthLargest(int[] nums, int k) {
            int len = nums.length;
            int targetIndex = len - k;
            int low = 0, high = len - 1;
            while (true) {
                int index = partition(nums, low, high);
                if (index == targetIndex) {
                    return nums[index];
                } else if (index < targetIndex) {
                    low = index + 1;
                } else {
                    high = index - 1;
                }
            }
        }

        private int partition(int[] arr, int low, int high) {
            if (high > low) {
                int random = low + new Random().nextInt(high - low);
                swap(arr, high, random);
            }
            int i = low;
            int pivot = arr[high];
            for (int j = low; j < high; j++) {
                if (arr[j] < pivot) {
                    swap(arr, i, j);
                    i++;
                }
            }
            swap(arr, i, high);
            return i;
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] nums = {3,2,3,1,2,4,5,5,5,6};
//        int[] nums = {3,2,3,1,2,4,5,5,6,7,7,8,2,3,1,1,1,10,11,5,6,2,4,7,8,5,6};
        System.out.println(new Solution1().findKthLargest(nums, 5));
        System.out.println(new Solution2().findKthLargest(nums, 5));
        System.out.println(new Solution3().findKthLargest(nums, 5));
        System.out.println(new Solution4().findKthLargest(nums, 5));
        System.out.println(new Solution5().findKthLargest(nums, 5));
        System.out.println(new Solution6().findKthLargest(nums, 5));
        System.out.println(new Solution7().findKthLargest(nums, 5));
        System.out.println(new Solution8().findKthLargest(nums, 5));
    }
}
