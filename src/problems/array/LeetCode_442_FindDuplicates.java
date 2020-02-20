package problems.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 442. 数组中重复的数据
 * 难度：中等
 *
 * 题目描述：
 *
 * 给定一个整数数组 a，其中1 ≤ a[i] ≤ n （n为数组长度）, 其中有些元素出现两次而其他元素出现一次。
 * 找到所有出现两次的元素。
 * 你可以不用到任何额外空间并在O(n)时间复杂度内解决这个问题吗？
 *
 * 示例：
 * 输入:
 * [4,3,2,7,8,2,3,1]
 * 输出:
 * [2,3]
 *
 * @author kyan
 * @date 2020/2/20
 */
public class LeetCode_442_FindDuplicates {

    /**
     * 解题思路：https://leetcode-cn.com/problems/find-all-duplicates-in-an-array/solution/javayuan-di-ha-xi-kong-jian-fu-za-du-o1-by-yankuan/
     *
     * TODO：提交结果不理想
     */
    public static class Solution1 {

        public List<Integer> findDuplicates(int[] nums) {
            if (nums.length <= 1) return new ArrayList<>();
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                while (nums[i] != (i+1) && nums[i] != 0) {
                    if (nums[i] != nums[nums[i]-1]) {
                        int tmp = nums[nums[i]-1];
                        nums[nums[i]-1] = nums[i];
                        nums[i] = tmp;
                    } else {
                        res.add(nums[i]);
                        nums[i] = 0;
                    }
                }
            }
            return res;
        }

        public static void main(String[] args) {
            int[] nums = {4,3,2,7,8,2,3,1};
            System.out.println(new Solution1().findDuplicates(nums));
        }

    }
}
