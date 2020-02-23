package problems.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 46. 全排列
 * 难度：中等
 * 题目描述
 * 给定一个没有重复数字的序列，返回其所有可能的全排列。
 *
 * 示例:
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 *
 * @author kyan
 * @date 2020/2/23
 */
public class LeetCode_46_Permutations {

    /**
     * 2 ms, 59.34%
     * 41.4 MB, 5.01%
     */
    public static class Solution1 {

        private List<List<Integer>> res = new ArrayList<>();
        private boolean[] visited;

        public List<List<Integer>> permute(int[] nums) {
            visited = new boolean[nums.length];
            backtrack(nums, new ArrayList<>());
            return res;
        }

        private void backtrack(int[] nums, List<Integer> tmp) {
            if (tmp.size() == nums.length) {
                res.add(new ArrayList<>(tmp));
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                if (visited[i]) continue;
                tmp.add(nums[i]);
                visited[i] = true;
                backtrack(nums, tmp);
                tmp.remove(tmp.size()-1);
                visited[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,1,3};
        System.out.println(new Solution1().permute(nums));
    }
}
