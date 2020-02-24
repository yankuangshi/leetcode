package problems.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kyan
 * @date 2020/2/24
 */
public class LeetCode_216_CombinationSumIII {

    /**
     * 0 ms, 100.00%
     * 36.6 MB, 5.08%
     */
    public static class Solution {

        private int[] nums = {1,2,3,4,5,6,7,8,9};
        private List<List<Integer>> res = new ArrayList<>();
        private int size, target;

        public List<List<Integer>> combinationSum3(int k, int n) {
            if (k < 1 || k > 9 || n < 1 || n > 45) return res;
            size = k;
            target = n;
            backtrack(0, 0, new ArrayList<Integer>());
            return res;
        }

        private void backtrack(int firstIndex, int sum, List<Integer> tmp) {
            if (sum == target && tmp.size() == size) {
                res.add(new ArrayList<>(tmp));
                return;
            }
            for (int i = firstIndex; i < nums.length; i++) {
                if (sum + nums[i] <= target && tmp.size() + 1 <= size) {
                    sum += nums[i];
                    tmp.add(nums[i]);
                    backtrack(i+1, sum, tmp);
                    sum -= nums[i];
                    tmp.remove(tmp.size() - 1);
                } else {
                    return;
                }
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(new Solution().combinationSum3(3, 7));
        System.out.println(new Solution().combinationSum3(3, 9));
    }
}
