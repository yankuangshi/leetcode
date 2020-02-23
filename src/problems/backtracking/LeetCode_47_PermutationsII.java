package problems.backtracking;

import java.util.*;

/**
 * 47. 全排列 II
 * 难度：中等
 * 题目描述
 * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
 *
 * 示例:
 * 输入: [1,1,2]
 * 输出:
 * [
 *   [1,1,2],
 *   [1,2,1],
 *   [2,1,1]
 * ]
 *
 * @author kyan
 * @date 2020/2/23
 */
public class LeetCode_47_PermutationsII {

    /**
     * 4 ms, 44.05%
     * 41.2 MB, 18.12%
     */
    public static class Solution1 {

        private List<List<Integer>> res = new ArrayList<>();
        private Map<Integer, Integer> countMap = new HashMap<>();
        private Set<Integer> numSet = new HashSet<>();
        private int N;

        public List<List<Integer>> permuteUnique(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                countMap.put(nums[i], countMap.getOrDefault(nums[i], 0) + 1);
                numSet.add(nums[i]);
            }
            N = nums.length;
            backtrack(numSet, new ArrayList<>());
            return res;
        }

        private void backtrack(Set<Integer> set, List<Integer> tmp) {
            if (tmp.size() == N) {
                res.add(new ArrayList<>(tmp));
                return;
            }
            for (Integer num : set) {
                if (countMap.get(num) == 0) continue;
                countMap.put(num, countMap.get(num) - 1);
                tmp.add(num);
                backtrack(set, tmp);
                countMap.put(num, countMap.get(num) + 1);
                tmp.remove(tmp.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,1,2}; //[[1, 1, 2], [1, 2, 1], [2, 1, 1]]
        System.out.println(new Solution1().permuteUnique(nums));
    }
}
