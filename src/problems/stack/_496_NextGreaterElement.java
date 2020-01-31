package problems.stack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 496. 下一个更大元素 I
 * 难度：简单
 *
 * 给定两个没有重复元素的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。找到 nums1 中每个元素在 nums2 中的下一个比其大的值。
 *
 * nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出-1。
 *
 * 示例 1:
 *
 * 输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
 * 输出: [-1,3,-1]
 * 解释:
 *     对于num1中的数字4，你无法在第二个数组中找到下一个更大的数字，因此输出 -1。
 *     对于num1中的数字1，第二个数组中数字1右边的下一个较大数字是 3。
 *     对于num1中的数字2，第二个数组中没有下一个更大的数字，因此输出 -1。
 * 示例 2:
 *
 * 输入: nums1 = [2,4], nums2 = [1,2,3,4].
 * 输出: [3,-1]
 * 解释:
 *     对于num1中的数字2，第二个数组中的下一个较大数字是3。
 *     对于num1中的数字4，第二个数组中没有下一个更大的数字，因此输出 -1。
 *
 * 注意:
 *
 * nums1和nums2中所有元素是唯一的。
 * nums1和nums2 的数组大小都不超过1000。
 *
 * @author kyan
 * @date 2020/1/30
 */
public class _496_NextGreaterElement {

    /**
     * 思路1：
     * 暴力解法，先在nums2中找到相应的数字x，然后再继续判断后面的第一个比数字x大的数字
     *
     * time 6ms, beat 40.28%
     * space 36.3MB, beat 94.23%
     */
    public static class Solution1 {

        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            int[] ans = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                ans[i] = -1;
                //首先在nums2数组中找到相应的元素
                int j = 0;
                while (j < nums2.length) {
                    if (nums2[j] == nums1[i]) break;
                    j++;
                }
                j++;
                while (j < nums2.length) {
                    if (nums2[j] > nums1[i]) {
                        ans[i] = nums2[j];
                        break;
                    }
                    j++;
                }
            }
            return ans;
        }
    }

    /**
     * 思路2：
     * 利用2次for循环遍历nums2数组，找出每个数字右边的第一个比它的数，并存入哈希表中
     *
     * time 3ms, beat 95.68%
     * space 36.9MB, beat 63.41%
     */
    public static class Solution2 {

        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Map<Integer, Integer> mappings = new HashMap<>();
            for (int i = 0; i < nums2.length; i++) {
                mappings.put(nums2[i], -1);
                for (int j = i + 1; j < nums2.length; j++) {
                    if (nums2[j] > nums2[i]) {
                        mappings.put(nums2[i], nums2[j]);
                        break;
                    }
                }
            }
            int[] ans = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                ans[i] = mappings.get(nums1[i]);
            }
            return ans;
        }
    }

    /**
     * 思路3：
     * 利用单调栈的特点（单调递增或单调递减）可以找出每个数字的右边的第一个比它的数
     * 例如：nums2 = [2,1,3,4]
     * 需要一个栈和一个哈希表，栈用于实现单调栈，哈希表用于存储每个数字对应的右边的第一个比它的数
     *
     * 我们构造一个栈顶到栈底单调递增的栈
     * step1：2入栈
     *
     * |   |
     * |   |
     * |_2_|
     *
     * step2：1入栈（1比2小）
     *
     * |   |
     * | 1 |
     * |_2_|
     *
     * step3：3入栈，但是比栈顶1大，所以需要弹出栈顶数字1，继续比较栈顶数2，继续弹出数字2，栈空，3入栈
     *
     * |   |   mappings (1,3) (2,3)
     * |   |
     * |_3_|
     *
     * step4：4入栈，但是比栈顶3大，所以需要弹出栈顶数字3，栈空，4入栈
     *
     * |   |   mappings (1,3) (2,3) (3,4)
     * |   |
     * |_4_|
     *
     * step5：清空栈，弹出栈顶4
     *
     * |   |   mappings (1,3) (2,3) (3,4) (4,-1)
     * |   |
     * |___|
     *
     * 所以对于nums2=[2,1,3,4]
     * 数字2右边比它大的第一个数是3
     * 数字1右边比它大的第一个数是3
     * 数字3右边比它大的第一个数是4
     * 数字4右边比它大的第一个数不存在，为-1
     *
     * time 4ms, beat 85.68%
     * space 37.6MB, beat 7.29%
     *
     */
    public static class Solution3 {

        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Stack<Integer> stack = new Stack<>();
            Map<Integer, Integer> mappings = new HashMap<>();
            for (int n : nums2) {
                while (!stack.isEmpty() && stack.peek() < n) {
                    mappings.put(stack.pop(), n);
                }
                stack.push(n);
            }
            while (!stack.isEmpty()) {
                mappings.put(stack.pop(), -1);
            }
            int[] ans = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                ans[i] = mappings.get(nums1[i]);
            }
            return ans;
        }
    }

    /**
     * 思路4：
     * 也是利用单调栈
     * 这个问题可以这样抽象思考：把数组的元素想象成并列站立的人，元素大小想象成人的身高。
     * 这些人面对你站成一列，如何求元素「2」的 Next Greater Number 呢？很简单，如果能够看到元素「2」，
     * 那么他后面可见的第一个人就是「2」的 Next Greater Number，因为比「2」小的元素身高不够，都被「2」挡住了，第一个露出来的就是答案。
     *
     * 2  1  2   4   3
     *           |
     *           |   |
     * |     |   |   |
     * |  |  |   |   |
     * -------------------
     * 4  2  4   -1  -1
     *
     * 作者：labuladong
     * 链接：https://leetcode-cn.com/problems/next-greater-element-i/solution/dan-diao-zhan-jie-jue-next-greater-number-yi-lei-w/
     *
     * 该作者的解法是从后往前遍历nums2数组，并且构造一个栈顶到栈底单调递增栈
     *
     * time 5ms, beat 66.13%
     * space 37.4MB, beat 13.54%
     *
     */
    public static class Solution4 {

        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Map<Integer, Integer> mappings = nextGreaterElementHelper(nums2);
            int[] ans = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                ans[i] = mappings.get(nums1[i]);
            }
            return ans;
        }

        private Map<Integer, Integer> nextGreaterElementHelper(int[] nums) {
            Map<Integer, Integer> mappings = new HashMap<>();
            Stack<Integer> stack = new Stack<>();
            for (int i = nums.length - 1; i >= 0; i--) {
                while (!stack.isEmpty() && nums[i] > stack.peek()) {
                    //因为是构造单调递增栈，所以比nums[i]小的栈顶数字需要弹出，直到栈顶数字比nums[i]大或者栈为空
                    stack.pop();
                }
                mappings.put(nums[i], stack.isEmpty() ? -1 : stack.peek());
                stack.push(nums[i]);
            }
            return mappings;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {4,1,2};
        int[] nums2 = {1,3,4,2};
        System.out.println(Arrays.toString(new Solution1().nextGreaterElement(nums1, nums2)));
        System.out.println(Arrays.toString(new Solution2().nextGreaterElement(nums1, nums2)));
        System.out.println(Arrays.toString(new Solution3().nextGreaterElement(nums1, nums2)));
        System.out.println(Arrays.toString(new Solution4().nextGreaterElement(nums1, nums2)));
    }
}
