package problems.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 448. 找到所有数组中消失的数字
 * 难度：简单
 *
 * 题目描述
 *
 * 给定一个范围在  1 ≤ a[i] ≤ n ( n = 数组大小 ) 的 整型数组，数组中的元素一些出现了两次，另一些只出现一次。
 * 找到所有在 [1, n] 范围之间没有出现在数组中的数字。
 * 您能在不使用额外空间且时间复杂度为O(n)的情况下完成这个任务吗? 你可以假定返回的数组不算在额外空间内。
 *
 * 示例:
 * 输入:
 * [4,3,2,7,8,2,3,1]
 * 输出:
 * [5,6]
 *
 * @author kyan
 * @date 2020/2/20
 */
public class LeetCode_448_FindDisappearedNumbers {

    /**
     * 7 ms, 86.25%
     * 48.1 MB, 36.51%
     */
    public static class Solution1 {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            if (nums.length <= 1) return new ArrayList<>();
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                while (nums[i] != (i+1) && nums[i] != 0) {
                    if (nums[i] == nums[nums[i]-1]) {
                        nums[i] = 0;
                    } else {
                        int tmp = nums[nums[i]-1];
                        nums[nums[i]-1] = nums[i];
                        nums[i] = tmp;
                    }
                }
            }
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 0) res.add(i+1);
            }
            return res;
        }
    }

    /**
     * 解法二（优化）
     * 6 ms, 91.85%
     * 48.4 MB, 28.25%
     */
    public static class Solution2 {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                int index = Math.abs(nums[i]) - 1;
                if (nums[index] > 0) {
                    nums[index] *= -1;
                }
            }
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] > 0) res.add(i+1);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {4,3,2,7,8,2,3,1};
        System.out.println(new Solution1().findDisappearedNumbers(nums1));
        System.out.println(Arrays.toString(nums1));

        int[] nums2 = {4,3,2,7,8,2,3,1};
        System.out.println(new Solution2().findDisappearedNumbers(nums2));
        System.out.println(Arrays.toString(nums2));
    }
}
