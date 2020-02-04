package problems.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 18. 四数之和
 * 难度：中等
 *
 * 题目描述
 *
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
 *
 * 注意：
 *
 * 答案中不可以包含重复的四元组。
 *
 * 示例：
 *
 * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
 *
 * 满足要求的四元组集合为：
 * [
 *   [-1,  0, 0, 1],
 *   [-2, -1, 1, 2],
 *   [-2,  0, 0, 2]
 * ]
 *
 * @author kyan
 * @date 2020/2/4
 */
public class _18_FourSum {

    /**
     * 解法一
     * 参考3sum的思路
     * 先对数组进行从小到大排序，使用4个指针分别指向4个元素，固定第一个指针i，然后求3sum之和等于target-nums[i]
     * 求3sum的方法就是 固定第二个指针j(j=i+1 ~ nums.length-2)，然后移动j指针后面的最左指针l和最右指针r，
     * 使之和 nums[i]+nums[j]+nums[l]+nums[r]=target
     *
     * 28 ms, 56.39%
     * 36.7 MB, 92.40%
     */
    public static class Solution1 {

        public List<List<Integer>> fourSum(int[] nums, int target) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null || nums.length < 4) return res;
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 3; i++) {
                if (i > 0 && nums[i] == nums[i-1]) continue;
                for (int j = i+1; j < nums.length - 2; j++) {
                    if (j > (i+1) && nums[j] == nums[j-1]) continue;
                    int l = j+1, r = nums.length-1;
                    while (l < r) {
                        int sum = nums[i] + nums[j] + nums[l] + nums[r];
                        if (sum == target) {
                            res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                            l++;
                            r--;
                            while (l < r && nums[l] == nums[l-1]) l++;
                            while (l < r && nums[r] == nums[r+1]) r--;
                        }
                        if (sum < target) l++;
                        if (sum > target) r--;
                    }
                }
            }
            return res;
        }
    }

    /**
     * 解法二（解法一基础上代码优化）
     * 在解法一代码的基础上进行了提前跳出循环或进入下一个循环的判断
     * ① 第一个指针i固定后，因为数组是从小到大排序好的，所以可以求出4sum的最小值是从i开始（包括i）的连续4个数 min = nums[i] + nums[i+1] + nums[i+2] + nums[i+3]
     * 如果 min > target，那之后再遍历的话，4sum只会越来越大，因此可以直接跳出for循环
     * ② 同理，第一个指针i固定后，求4sum的最大值，是nums[i]元素和最后3个元素值的和 max = nums[i] + nums[len-1] + nums[len-2] + nums[len-3]
     * 如果 max < target，说明 nums[i] 偏小了，因此可以直接进入下一个循环，i++
     * ③ 第一个指针i，第二个指针j固定后，4sum的最小值是 min = nums[i] + nums[j] + nums[j+1] + nums[j+2]
     * 如果 min > target，可以直接跳出for循环
     * ④ 同理，第一个指针i，第二个指针j固定后，4sum的最大值是 max = nums[i] + nums[j] + nums[r] + nums[r-1]
     * 如果 max < target，可以直接进入下一个循环 j++
     *
     * 4 ms, 99.59%
     * 37.6 MB, 53.84%
     */
    public static class Solution2 {

        public List<List<Integer>> fourSum(int[] nums, int target) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null || nums.length < 4) return res;
            Arrays.sort(nums);
            int len = nums.length;
            //加上这一行反而执行时间慢了
//            if (nums[len-1] + nums[len-2] + nums[len-3] + nums[len-4] < target) return res;
            int l, r, min, max;
            for (int i = 0; i < len - 3; i++) {
                if (i > 0 && nums[i] == nums[i-1]) continue;
                min = nums[i] + nums[i+1] + nums[i+2] + nums[i+3]; //①
                if (min > target) break;
                max = nums[i] + nums[len-1] + nums[len-2] + nums[len-3]; //②
                if (max < target) continue;
                for (int j = i+1; j < len - 2; j++) {
                    if (j > i+1 && nums[j] == nums[j-1]) continue;
                    l = j+1;
                    r = len-1;
                    min = nums[i] + nums[j] + nums[j+1] + nums[j+2]; //③
                    if (min > target) break;
                    max = nums[i] + nums[j] + nums[r] + nums[r-1]; //④
                    if (max < target) continue;
                    while (l < r) {
                        int sum = nums[i] + nums[j] + nums[l] + nums[r];
                        if (sum == target) {
                            res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                            l++;
                            while (l < r && nums[l] == nums[l-1]) l++;
                            r--;
                            while (l < r && nums[r] == nums[r+1]) r--;
                        }
                        if (sum < target) l++;
                        if (sum > target) r--;
                    }
                }
            }
            return res;
        }

    }


    public static void main(String[] args) {
//            int[] nums = {-2,-1,0,0,1,2};
//            int[] nums = {-2,-2,-1,0,0,1,2};
//            int[] nums = {-2,-1,-1,0,0,1,2};
        int[] nums = {1,-2,-5,-4,-3,3,3,5};
        System.out.println(new Solution1().fourSum(nums, -11));
        System.out.println(new Solution2().fourSum(nums, -11));
    }
}
