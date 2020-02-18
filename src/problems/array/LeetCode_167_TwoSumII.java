package problems.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 167. 两数之和 II - 输入有序数组
 * 难度：简单
 *
 * 题目描述
 *
 * 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
 *
 * 函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。
 *
 * 说明:
 *
 * 返回的下标值（index1 和 index2）不是从零开始的。
 * 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
 * 示例:
 *
 * 输入: numbers = [2, 7, 11, 15], target = 9
 * 输出: [1,2]
 * 解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
 *
 * @author kyan
 * @date 2020/2/8
 */
public class LeetCode_167_TwoSumII {

    /**
     * 解法一
     * 思路同TwoSumI中的HashMap解法相同
     * 时间复杂度O(N)，空间复杂度O(N)
     * 3 ms, 34.45%
     * 37.7 MB, 44.48%
     */
    public static class Solution1 {
        public int[] twoSum(int[] numbers, int target) {
            Map<Integer, Integer> hashMap = new HashMap<>();
            int[] res = new int[2];
            for (int i = 0; i < numbers.length; i++) {
                if (hashMap.containsKey(target - numbers[i])) {
                    res[0] = hashMap.get(target - numbers[i]);
                    res[1] = i+1;
                    return res;
                }
                hashMap.put(numbers[i], i+1);
            }
            return res;
        }
    }

    /**
     * 解法二
     * 充分利用升序数组的特性，使用 shrink range 的技巧（左右双指针往中间移动）
     * 此技巧在 ThreeSum 中也有使用
     * 设置left、right 双指针
     * 当 nums[left]+nums[right] > target，right--
     * 当 nums[left]+nums[right] < target, left++
     * 当 nums[left]+nums[right] = target，返回 left、right
     *
     * 每个元素最多被访问一次，一共有N个元素，时间复杂度O(N)
     * 只使用了两个指针，空间复杂度O(1)
     * 1 ms, 98.98%
     * 37.6 MB, 48.32%
     */
    public static class Solution2 {
        public int[] twoSum(int[] numbers, int target) {
            int left = 0, right = numbers.length - 1;
            while (left < right) {
                if (numbers[left] + numbers[right] == target) {
                    return new int[] {left+1, right+1};
                } else if (numbers[left] + numbers[right] < target) {
                    left++;
                } else {
                    right--;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        int[] numbers = {2,7,11,15};
        System.out.println(Arrays.toString(new Solution1().twoSum(numbers, 9)));
        System.out.println(Arrays.toString(new Solution2().twoSum(numbers, 9)));
    }
}
