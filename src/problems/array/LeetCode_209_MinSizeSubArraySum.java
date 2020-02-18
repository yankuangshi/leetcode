package problems.array;

/**
 * 209. 长度最小的子数组
 * 难度：中等
 *
 * 题目描述：
 * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。如果不存在符合条件的连续子数组，返回 0。
 *
 * 示例: 
 *
 * 输入: s = 7, nums = [2,3,1,2,4,3]
 * 输出: 2
 * 解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
 * 进阶:
 *
 * 如果你已经完成了O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。
 *
 * @author kyan
 * @date 2020/2/16
 */
public class LeetCode_209_MinSizeSubArraySum {

    /**
     * 解法一
     * 滑动窗口思想，利用双指针 l、r，先右移指针 r 扩大窗口直到 sum >= s，然后右移指针 l 减小窗口直到 sum < s
     * 右移指针 r 的同时对 sum += nums[r] ，右移指针 l 的同时对 sum -= nums[l]
     * 直到指针 r 移至数组末尾，遍历结束。
     *
     * 2 3 1 2 4 3    s=7 sum=2 sum<s 右移r
     * ^
     * l
     * r
     *
     * 2 3 1 2 4 3    s=7 sum=2+3=5 sum<s 右移r
     * ^ ^
     * l r
     *
     * 2 3 1 2 4 3    s=7 sum=5+1=6 sum<s 右移r
     * ^   ^
     * l   r
     *
     * 2 3 1 2 4 3    s=7 sum=6+2=8 sum>=s 右移l 此时minLen= 4
     * ^     ^
     * l     r
     *
     * 2 3 1 2 4 3    s=7 sum=8-2=6 sum<s 右移r
     *   ^   ^
     *   l   r
     *
     * 2 3 1 2 4 3    s=7 sum=6+4=10 sum>=s 右移l 此时minLen=4
     *   ^     ^
     *   l     r
     *
     * 2 3 1 2 4 3    s=7 sum=10-3=7 sum>=s 右移l 此时minLen=3
     *     ^   ^
     *     l   r
     *
     * 2 3 1 2 4 3    s=7 sum=7-1=6 sum<s 右移r
     *       ^ ^
     *       l r
     *
     * 2 3 1 2 4 3    s=7 sum=6+3=9 sum>=s 右移l 此时minLen=3
     *       ^   ^
     *       l   r
     *
     * 2 3 1 2 4 3    s=7 sum=9-2=7 sum>=s 右移l 此时minLen=2
     *         ^ ^
     *         l r
     *
     * 2 3 1 2 4 3    s=7 sum=7-4=3 sum<s 右移r，结束
     *           ^
     *           r
     *           l
     *
     * 整个过程2个指针从左遍历到右，因此时间复杂度O(n)，空间复杂度O(1)
     *
     * 2 ms, 89.83%
     * 48.2 MB, 5.06%
     */
    public static class Solution1 {

        public int minSubArrayLen(int s, int[] nums) {
            if (nums.length == 0) return 0;
            if (nums.length == 1 && nums[0] < s) return 0;
            if (nums.length >= 1 && nums[0] >= s) return 1;
            int sum = 0, l = 0, r = 0;
            int len = nums.length, minLen = len + 1;
            while (l < len && r < len) {
                sum += nums[r];
                while (sum >= s) {
                    minLen = Math.min(minLen, r - l + 1);
                    sum -= nums[l];
                    l++;
                }
                r++;
            }
            return minLen < len + 1 ? minLen : 0;
        }
    }

    /**
     * 解法二
     * 暴力解法，时间复杂度O(n^2)
     *
     * 104 ms, 8.44%
     * 48.8 MB, 5.06%
     */
    public static class Solution2 {

        public int minSubArrayLen(int s, int[] nums) {
            if (nums.length == 0) return 0;
            int sum = 0, len = nums.length, minLen = len + 1;
            for (int i = 0; i < len; i++) {
                //子数组sum每次重置为0
                sum = 0;
                for (int j = i; j < len; j++) {
                    //累加直至sum>=s，更新minLen，跳出
                    sum += nums[j];
                    if (sum >= s) {
                        minLen = Math.min(minLen, j - i + 1);
                        break;
                    }
                }
            }
            return minLen < len + 1 ? minLen : 0;
        }
    }

    /**
     * 解法三
     * 利用二分法
     * 先对数组 nums 求前缀和，存放入新数组 sums
     * 例如原数组 nums [2 3 1 2 4 3]
     * 那么前缀和 sums [2 5 6 8 12 15]
     * 其中 sums[j] - sums[i] 的含义为原数组 nums[i+1]~nums[j] 之间元素之和
     * 利用和解法二中暴力解法的思想，进行2次循环遍历
     * for i = 0 : n-1
     *      for j = i+1 ~ n-1 中找到 j 使得 nums[i+1] ~ nums[j] 之和 >= s-nums[i]
     * nums[i+1] ~ nums[j] 之和即为 sums[j] - sums[i]，因此转化为 sums[j] - sums[i] >= s-nums[i]，sums[j] >= s-nums[i] + sums[i]
     * 即用二分法求 sums 数组中，下标 i~n-1 第一个大于等于 s-nums[i] + sums[i] 的元素下标
     *
     * 5 ms, 25.88%
     * 48.5 MB, 5.06%
     */
    public static class Solution3 {

        public int minSubArrayLen(int s, int[] nums) {
            if (nums.length == 0) return 0;
            int[] sums = new int[nums.length];
            sums[0] = nums[0];
            //创建新数组用于存放原数组的前缀和
            //原数组 2 3 1 2 4 3
            //前缀和 2 5 6 8 12 15
            for (int i = 1; i < nums.length; i++) {
                sums[i] = sums[i-1] + nums[i];
            }
            int s1 = 0, minLen = nums.length + 1;
            for (int i = 0; i < nums.length; i++) {
                s1 = s - nums[i];
                //nums[i] 大于等于 s，则直接返回结果 1
                if (s1 <= 0) return 1;
                //nums[i] 小于 s，需要求 nums[i+1] ~ nums[j] 之间和 >= s1，即求 sums[j] - sums[i] >= s1，sums[j] >= s1+sums[i]
                //此处可以通过二分查找寻找数组sums中下标 j，使得 sums[j] >= s1+sums[i]
                int j = bsearch(sums, i+1, sums.length-1, s1 + sums[i]);
                if (j > -1) {
                    minLen = Math.min(minLen, j - i + 1);
                }
            }
            return minLen < nums.length + 1 ? minLen : 0;
        }

        /**
         * 二分法寻找第一个大于等于target的数组下标
         */
        public int bsearch(int[] a, int l, int r, int target) {
            while (l <= r) {
                int mid = l + ((r - l) >> 1);
                if (a[mid] >= target) {
                    //如果a[mid]是第一个元素 或 a[mid]前面的元素小于target，那么a[mid]就是第一个大于等于target的元素
                    if (mid == l || a[mid-1] < target) return mid;
                    else r = mid - 1;
                } else {
                    //如果a[mid]<target，则更新左指针
                    l = mid + 1;
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        int s = 8;
        int[] nums = {2,3,1,2,4,3};
        System.out.println(new Solution1().minSubArrayLen(s, nums));
        System.out.println(new Solution2().minSubArrayLen(s, nums));
        System.out.println(new Solution3().minSubArrayLen(s, nums));

        //test bsearch
//        int[] a = {1,3,6,7};
//        System.out.println(new Solution3().bsearch(a, 0, a.length - 1, 5));
    }
}
