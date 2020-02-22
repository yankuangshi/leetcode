package problems.lcof;

import java.util.HashSet;

/**
 * 面试题03.数组中重复的数字
 * 难度：简单
 *
 * 题目描述
 * 找出数组中重复的数字。
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
 *
 * 示例 1：
 * 输入：
 * [2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 *
 * @author kyan
 * @date 2020/2/19
 */
public class LCOF_3_FindRepeatNumber {

    /**
     * 解法一
     * for循环一次遍历，时间复杂度O(n)，空间复杂度O(n)
     * 7ms, 42.85%
     * 53.1 MB, 100.00%
     */
    public static class Solution1 {
        public int findRepeatNumber(int[] nums) {
            if (nums == null || nums.length == 0) return -1;
            HashSet<Integer> numberSet = new HashSet<>();
            for (int i = 0; i < nums.length; i++) {
                if (!numberSet.add(nums[i])) {
                    return nums[i];
                }
            }
            return -1;
        }
    }

    /**
     * 解法二【推荐】原地"哈希"思想
     * 不借助其他额外空间（哈希表），可以把数组自身作为哈希表利用，因为数组中数字的特定：数字大小满足 0~n-1
     * 思路为把 当 nums[i] 不在属于他的下标位置时，把他归位到应该属于他的下标下
     * 例如对于 nums[x]=2 (x!=2)，那么应该把它放置在nums[2]，把原来nums[2]的数放到下标 x
     *
     * [2, 3, 1, 0, 2, 5, 3] nums[0]=2 不在属于他的下标下，因此 nums[0]=2 和 nums[2]=1 交换
     *  ↑
     *
     * [1, 3, 2, 0, 2, 5, 3] nums[0]=1 不在属于他的下标下，因此 nums[0]=1 和 nums[1]=3 交换
     *  ↑
     *
     * [3, 1, 2, 0, 2, 5, 3] nums[0]=3 不在属于他的下标下，因此 nums[0]=3 和 nums[3]=0 交换
     *  ↑
     *
     * [0, 1, 2, 3, 2, 5, 3] nums[0]=0 正好在属于他的下标下，不需要交换
     *  ↑
     *
     * [0, 1, 2, 3, 2, 5, 3] nums[1]=1 正好在属于他的下标下，不需要交换
     *     ↑
     *
     * [0, 1, 2, 3, 2, 5, 3] nums[2]=2 正好在属于他的下标下，不需要交换
     *        ↑
     *
     * [0, 1, 2, 3, 2, 5, 3] nums[3]=3 正好在属于他的下标下，不需要交换
     *           ↑
     *
     * [0, 1, 2, 3, 2, 5, 3] nums[4]=2 不在属于他的下标下，因此 nums[4]=2 和 nums[2]=2 交换，发现重复！
     *
     * 0 ms, 100.00%
     * 50.4 MB, 100.00%
     */
    public static class Solution2 {
        public int findRepeatNumber(int[] nums) {
            if (nums == null || nums.length == 0) return -1;
            for (int i = 0; i < nums.length ; i++) {
                while (i != nums[i]) {
                    //nums[i]没在属于他的下标位置，应该与nums[nums[i]]进行交换，如果恰好发现与nums[nums[i]]相同，则说明重复
                    if (nums[i] == nums[nums[i]]) return nums[i];
                    swap(nums, i, nums[i]);
                }
            }
            return -1;
        }

        private void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }


    public static void main(String[] args) {
        int[] nums = {4, 3, 1, 0, 2, 5, 3};
        System.out.println(new Solution1().findRepeatNumber(nums));
        System.out.println(new Solution2().findRepeatNumber(nums));
    }
}
