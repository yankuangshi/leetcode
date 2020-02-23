package problems.backtracking;

/**
 * 52. N皇后 II
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
 * 给定一个整数 n，返回 n 皇后不同的解决方案的数量。
 *
 * 示例:
 * 输入: 4
 * 输出: 2
 * 解释: 4 皇后问题存在如下两个不同的解法。
 * [
 *  [".Q..",  // 解法 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 *
 *  ["..Q.",  // 解法 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 *
 * @author kyan
 * @date 2020/2/23
 */
public class LeetCode_52_NQueensII {

    /**
     * 2 ms, 57.83%
     * 36.4 MB, 5.08%
     */
    public static class Solution {

        private int[] queens;
        private int total;

        public int totalNQueens(int n) {
            queens = new int[n];
            solveTotalNQueensHelper(0, n);
            return total;
        }

        private void solveTotalNQueensHelper(int row, int n) {
            if (row == n) {
                total++;
                return;
            }
            for (int col = 0; col < n; col++) {
                if (isValid(row, col, n)) {
                    queens[row] = col;
                    solveTotalNQueensHelper(row+1, n);
                }
            }
        }

        private boolean isValid(int row, int col, int n) {
            for (int i = row - 1; i >= 0; i--) {
                if (queens[i] == col) return false;
            }
            for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
                if (queens[i] == j) return false;
            }
            for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
                if (queens[i] == j) return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().totalNQueens(4)); //2
        System.out.println(new Solution().totalNQueens(8)); //92

    }
}
