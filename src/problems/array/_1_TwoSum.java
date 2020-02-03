package problems.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. 两数之和
 * 难度：简单
 *
 * 题目描述
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 * @author kyan
 * @date 2020/2/3
 */
public class _1_TwoSum {

    /**
     * 思路1
     * 暴力解法，2个for循环依次遍历，O(n^2)时间复杂度
     * 38 ms, 18.16%
     * 37.5 MB, 38.65%
     */
    public static class Solution1 {

        public int[] twoSum(int[] nums, int target) {
            int[] res = new int[2];
            for (int i = 0; i < nums.length; i++) {
                for (int j = i+1; j < nums.length; j++) {
                    if (nums[i] + nums[j] == target) {
                        res[0] = i;
                        res[1] = j;
                        return res;
                    }
                }
            }
            return res;
        }
    }

    /**
     * 思路2
     * 遍历2次，第一次遍历先把数组中的每个值和值下标存入哈希表，第二次遍历数组，利用哈希表判断差值是否存在
     * 但是用注意
     * 1. 需要加判断 map.get(target - nums[i]) != i 防止取到同一个元素，例如 [3,2,4] target=6，如果不加此判断条件，结果是[0,0]
     * 2. res[0] 不能是 map.get(nums[i])，因为 res[1]=map.get(target - nums[i])，如果都是从哈希表寻找，有可能会是同一个下标，因为如果数组中有重复数值的话，哈希表会覆盖最初的值的下标，例如[3,3]
     *
     * 遍历n个数，时间复杂度O(n)，空间复杂度O(n)
     * 3 ms, 97.26%
     * 36.8 MB, 81.22%
     */
    public static class Solution2 {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                map.put(nums[i], i);
            }
            int[] res = new int[2];
            for (int i = 0; i < nums.length; i++) {
                if (map.containsKey(target - nums[i])
                        && map.get(target - nums[i]) != i) {
//                    res[0] = map.get(nums[i]);
                    res[0] = i;
                    res[1] = map.get(target - nums[i]);
                    break;
                }
            }
            return res;
        }
    }

    /**
     * 思路3
     * 遍历1次，先判断target和当前数值的差值是否存在于哈希表中，如果存在直接返回2个下标；如果不存在将数值存入哈希表
     * 遍历n个数，时间复杂度O(n)，空间复杂度O(n)
     * 3 ms, 97.26%
     * 37.2 MB, 58.83%
     */
    public static class Solution3 {

        public int[] twoSum(int[] nums, int target) {
            int[] res = new int[2];
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int diff = target - nums[i];
                if (map.containsKey(diff)) {
                    res[0] = map.get(diff);
                    res[1] = i;
                    return res;
                }
                map.put(nums[i], i);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int[] nums = {3,3};
        System.out.println(Arrays.toString(new Solution1().twoSum(nums, 6)));
        System.out.println(Arrays.toString(new Solution2().twoSum(nums, 6)));
        System.out.println(Arrays.toString(new Solution3().twoSum(nums, 6)));
    }
}
