package problems.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 51. N皇后
 * 难度：困难
 * 题目描述
 * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * Q . . . . . . .
 * . . . . Q . . .
 * . . . . . . . Q
 * . . . . . Q . .
 * . . Q . . . . .
 * . . . . . . Q .
 * . Q . . . . . .
 * . . . Q . . . .
 * 上图为 8 皇后问题的一种解法。
 *
 * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
 * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 *
 * 示例:
 * 输入: 4
 * 输出: [
 *  [".Q..",  // 解法 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 *
 *  ["..Q.",  // 解法 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 * 解释: 4 皇后问题存在两个不同的解法。
 *
 * @author kyan
 * @date 2020/2/23
 */
public class LeetCode_51_NQueens {

    /**
     * 5 ms, 52.02%
     * 41.2 MB, 6.44%
     */
    public static class Solution {

        private List<List<String>> results = new ArrayList<>();
        private int[] queens;
        private int N;

        public List<List<String>> solveNQueens(int n) {
            this.queens = new int[n];
            this.N = n;
            solveNQueensBackTrack(0);
            return this.results;
        }

        /**
         * labuladong 大佬的回溯算法模板
         * result = []
         * def backtrack(路径, 选择列表):
         *     if 满足结束条件:
         *         result.add(路径)
         *         return
         *
         *     for 选择 in 选择列表:
         *         做选择
         *         backtrack(路径, 选择列表)
         *         撤销选择
         *  https://leetcode-cn.com/problems/n-queens/solution/hui-su-suan-fa-xiang-jie-by-labuladong/
         */
        public void solveNQueensBackTrack(int row) {
            if (row == N) {
                //添加解法
                List<String> result = new ArrayList<>();
                for (int i = 0; i < N; i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < N; j++) {
                        if (queens[i] == j) sb.append("Q");
                        else sb.append(".");
                    }
                    result.add(sb.toString());
                }
                results.add(result);
                return;
            }
            for (int col = 0; col < N; col++) {
                if (isValid(row, col)) {
                    queens[row] = col;
                    solveNQueensBackTrack(row+1);
//                    queens[row] = 0;
                }
            }
        }

        /**
         * 判断 第row行，第col列（总行数列数为n）的情况下是否可行
         * @param row
         * @param col
         * @return
         */
        private boolean isValid(int row, int col) {
            int leftUp = col-1, rightUp = col+1;
            //依次检查每一行
            for (int i = row - 1; i >=0 ; i--) {
                //检查列
                if (queens[i] == col) return false;
                //检查左上斜线
                if (leftUp >= 0 && queens[i] == leftUp) return false;
                //检查右上斜线
                if (rightUp < N && queens[i] == rightUp) return false;
                --leftUp;
                ++rightUp;
            }
            return true;
        }

        /**
         * 另一个中检查方式
         * @param row
         * @param col
         * @return
         */
        private boolean isValid2(int row, int col) {
            //检查列
            for (int i = row-1 ; i >= 0; i--) {
                if (queens[i] == col) return false;
            }
            //检查左上斜线
            for (int i = row-1, j = col-1; i >=0 && j >=0; i--, j--) {
                if (queens[i] == j) return false;
            }
            //检查右上斜线
            for (int i = row-1, j = col+1; i >=0 && j < N; i--, j++) {
                if (queens[i] == j) return false;
            }
            return true;
        }

    }


    public static void main(String[] args) {
        List<List<String>> result = new Solution().solveNQueens(4);
        System.out.println(result);
        System.out.println(result.size());
    }
}
