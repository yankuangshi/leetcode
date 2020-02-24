package problems.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 39. 组合总和
 * 难度：中等
 * 题目描述
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的数字可以无限制重复被选取。
 *
 * 说明：
 *
 * 所有数字（包括 target）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1:
 *
 * 输入: candidates = [2,3,6,7], target = 7,
 * 所求解集为:
 * [
 *   [7],
 *   [2,2,3]
 * ]
 * 示例 2:
 *
 * 输入: candidates = [2,3,5], target = 8,
 * 所求解集为:
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 *
 * @author kyan
 * @date 2020/2/24
 */
public class LeetCode_39_CombinationSum {

    /**
     * 2 ms, 100.00%
     * 41.5 MB, 6.66%
     */
    public static class Solution {

        private List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> combinationSum(int[] candidates, int target) {
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
                if ((total + candidates[i]) <= target) {
                    total += candidates[i];
                    list.add(candidates[i]);
                    backtrack(candidates, target, i, total, list);
                    total -= candidates[i];
                    list.remove(list.size() - 1);
                } else {
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] candidates = {2,3,5};
        System.out.println(new Solution().combinationSum(candidates, 8));
    }
}
