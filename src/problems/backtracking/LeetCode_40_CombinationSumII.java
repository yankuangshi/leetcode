package problems.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 40. 组合总和 II
 * 难度：中等
 * 题目描述
 * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的每个数字在每个组合中只能使用一次。
 *
 * 说明：
 *
 * 所有数字（包括目标数）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1:
 *
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 所求解集为:
 * [
 *   [1, 7],
 *   [1, 2, 5],
 *   [2, 6],
 *   [1, 1, 6]
 * ]
 * 示例 2:
 *
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 所求解集为:
 * [
 *   [1,2,2],
 *   [5]
 * ]
 *
 * @author kyan
 * @date 2020/2/24
 */
public class LeetCode_40_CombinationSumII {

    /**
     * 2 ms, 100.00%
     * 39.2 MB, 23.37%
     */
    public static class Solution {

        private List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            Arrays.sort(candidates);
            if (target < candidates[0]) return res;
            backtrack(candidates, target, 0, 0, new ArrayList<>());
            return res;
        }

        public void backtrack(int[] candidates, int target, int firstIndex, int total, List<Integer> list) {
            if (total == target) {
                res.add(new ArrayList<>(list));
                return;
            }
            for (int i = firstIndex; i < candidates.length; i++) {
                if (i > firstIndex && candidates[i] == candidates[i-1]) continue;
                if ((total + candidates[i]) <= target) {
                    total += candidates[i];
                    list.add(candidates[i]);
                    backtrack(candidates, target, i+1, total, list);
                    total -= candidates[i];
                    list.remove(list.size() - 1);
                } else {
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
//        int[] candidates = {10,1,2,7,6,1,5};
//        int target = 8;
        int[] candidates = {2,5,2,1,2};
        int target = 5;
        System.out.println(new Solution().combinationSum2(candidates, target));
    }
}
