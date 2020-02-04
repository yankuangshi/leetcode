package problems.array;

import java.util.*;

/**
 * 15. 三数之和
 * 难度：中等
 * <p>
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 *  
 * <p>
 * 示例：
 * <p>
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * <p>
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 *
 * @author kyan
 * @date 2020/2/4
 */
public class _15_ThreeSum {

    /**
     * 解法一
     * 参考TwoSum，a+b+c=0，拿出一个数比如a，-a作为target，然后求两数之和
     * 该解法不AC 例如 [0,0,0,0] 这种情况不通过
     */
    public static class Solution1 {

        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 2; i++) {
                if (i > 0 && nums[i] == nums[i - 1]) break;
                int target = -nums[i];
                Set<Integer> set = new HashSet<>();
                for (int j = i + 1; j < nums.length; j++) {
                    if (set.contains(target - nums[j])) {
                        res.add(Arrays.asList(nums[i], target - nums[j], nums[j]));
                    }
                    set.add(nums[j]);
                }
            }
            return res;
        }
    }

    /**
     * 解法二
     * 先对数组进行排序 Arrays.sort
     * 然后最外层遍历排序后的数组 nums
     * 记当前下标为i，i之后的数组中最左端下标为l=i+1，最右端下标为r=nums.length-1
     * ex: [-4,-1,-1,0,1,2]
     *       ↑  ↑        ↑
     *       i  l        r
     * 内层 while 循环 当(l<r)条件满足时，l指针和r指针往中间靠
     * 判断 sum=nums[i]+nums[l]+nums[r] 与0的关系
     * 如果 sum>0，说明偏大，r指针往左靠，r--
     * 如果 sum<0，说明偏小，l指针往右靠，l++
     * 如果 sum=0，把结果记录入结果集，同时l、r指针都分别往中间靠，r--，l++
     *
     * 需要注意的几点：
     * 1. 遍历 nums 只需要遍历下标 < nums.length-2
     * 2. 如果 nums[i]>0，可以直接跳出外层循环返回结果，因为 nums 是排完序的数组 r>l>i，nums[r]>=nums[l]>=nums[i]，三者之和不可能等于0
     * 3. 如果 nums[i]和前一个元素值相同（nums[i]==nums[i-1]）这种情况要跳过，但是注意i>0，否则会OutOfIndex
     * 4. 当 l或r指针往中间移动时，需要跳过元素相同的值，以免重复判断或结果重复
     *
     * 以下情况避免重复判断（可以跳过也可以不跳过，因为对最终结果不会有影响）
     * ex: [-4,-1,-1,0,1,2]
     *       ↑  ↑        ↑
     *       i  l        r   -4+(-1)+2<0，l右移
     *
     *     [-4,-1,-1,0,1,2]
     *       ↑     ↑     ↑
     *       i   l(skip) r   l所指元素值和上一个相同，跳过
     *
     *     [-4,-1,-1,0,1,2]
     *       ↑       ↑   ↑
     *       i       l   r
     *
     * 以下情况避免结果重复
     * ex: [-1,-1,-1,0,2,2]
     *       ↑  ↑        ↑
     *       i  l        r   此时满足条件 [-1,-1,2]，l和r都往中间移动
     *
     *     [-1,-1,-1,0,2,2]
     *       ↑     ↑   ↑
     *       i     l   r     l和r所指元素和上一个相同，跳过，否则结果就会重复
     *
     * 34 ms, 86.18%
     * 48.2 MB, 29.31%
     */
    public static class Solution2 {

        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null || nums.length < 3) return res;
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 2; i++) {
                //如果 nums[i]>0，因为nums[r]>=nums[l]>=nums[i]，相加不可能为0，跳过
                if (nums[i] > 0) break;
                //nums[i]==nums[i-1]避免重复，需跳过
                if (i > 0 && nums[i] == nums[i - 1]) continue;
                //下标i之后的最左端指针l和最右端指针r
                int l = i + 1, r = nums.length - 1;
                while (l < r) {
                    int sum = nums[i] + nums[l] + nums[r];
                    if (sum == 0) {
                        res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                        //总和等于0，左指针右移，右指针左移，但是要跳过相同值
                        l++;
                        while (l < r && nums[l] == nums[l-1]) l++;
                        r--;
                        while (l < r && nums[r] == nums[r+1]) r--;
                    }
                    if (sum > 0) r--;
                    if (sum < 0) l++;
                }
            }
            return res;
        }
    }

    /**
     * 解法三（解法二的代码优化）
     */
    public static class Solution3 {

        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null || nums.length < 3) return res;
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 2; i++) {
                //如果 nums[i]>0，因为nums[r]>=nums[l]>=nums[i]，相加不可能为0，跳过
                if (nums[i] > 0) break;
                int max = nums[i] + nums[nums.length-1] + nums[nums.length-2];
                if (max < 0) continue;
                //nums[i]==nums[i-1]避免重复，需跳过
                if (i > 0 && nums[i] == nums[i - 1]) continue;
                //下标i之后的最左端指针l和最右端指针r
                int l = i + 1, r = nums.length - 1;
                while (l < r) {
                    int sum = nums[i] + nums[l] + nums[r];
                    if (sum == 0) {
                        res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                        //总和等于0，左指针右移，右指针左移，但是要跳过相同值
                        //以上写法可以用以下语法优化
                        while (l < r && nums[l] == nums[++l]);
                        while (l < r && nums[r] == nums[--r]);
                    }
                    if (sum > 0) --r;
                    if (sum < 0) ++l;
                }
            }
            return res;
        }
    }


    public static void main(String[] args) {
//        int[] nums = {0,0,0};
//        int[] nums = {0,0,0,0};
//        int[] nums = {-2,-3,0,0,-2};
//        int[] nums = {0, -4, -1, -4, -2, -3, 2};
//        int[] nums = {-4,-1,-1,0,1,2};
        int[] nums = {-6,-5,-4,-3,-2,-1,0,1};
        System.out.println(new Solution2().threeSum(nums));
        System.out.println(new Solution3().threeSum(nums));
    }
}
