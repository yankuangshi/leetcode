package problems.backtracking;

import java.util.Arrays;

/**
 * 37. 解数独
 * 难度：困难
 * 题目描述
 *
 * 编写一个程序，通过已填充的空格来解决数独问题。
 *
 * 一个数独的解法需遵循如下规则：
 *
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 * 空白格用 '.' 表示。
 *
 * @author kyan
 * @date 2020/2/28
 */
public class LeetCode_37_SudokuSolver {

    /**
     * 6 ms, 70.80%
     * 36.8 MB, 17.39%
     */
    public static class Solution1 {

        private boolean[][] rows = new boolean[9][10];
        private boolean[][] cols = new boolean[9][10];
        private boolean[][] boxes = new boolean[9][10];
        private boolean solved;

        public void solveSudoku(char[][] board) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] != '.') {
                        int num = board[i][j] - '0';
                        rows[i][num] = true;
                        cols[j][num] = true;
                        boxes[(i/3)*3 + (j/3)][num] = true;
                    }
                }
            }
            solve(0, 0, board);
        }

        private void solve(int row, int col, char[][] board) {
            if (row == 9) {
                solved = true;
                return;
            }
            if (row < 9 && col == 9) {
                solve(row + 1, 0, board);
            } else {
                if (board[row][col] == '.') {
                    for (int i = 1; i <= 9; i++) {
                        if (isValid(i, row, col)) {
                            rows[row][i] = true;
                            cols[col][i] = true;
                            boxes[(row / 3) * 3 + (col / 3)][i] = true;
                            board[row][col] = (char)(i + '0');
                            solve(row, col+1, board);
                            if (!solved) {
                                board[row][col] = '.';
                                rows[row][i] = false;
                                cols[col][i] = false;
                                boxes[(row / 3) * 3 + (col / 3)][i] = false;
                            }
                        }
                    }
                } else {
                    solve(row, col + 1, board);
                }
            }
        }

        private boolean isValid(int num, int row, int col) {
            return !(rows[row][num] || cols[col][num] || boxes[(row / 3) * 3 + (col / 3)][num]);
        }

    }

    public static void main(String[] args) {
        char[][] board = new char[9][9];
        board[0] = new char[]{'5', '3', '.', '.', '7', '.', '.', '.', '.'};
        board[1] = new char[]{'6', '.', '.', '1', '9', '5', '.', '.', '.'};
        board[2] = new char[]{'.', '9', '8', '.', '.', '.', '.', '6', '.'};
        board[3] = new char[]{'8', '.', '.', '.', '6', '.', '.', '.', '3'};
        board[4] = new char[]{'4', '.', '.', '8', '.', '3', '.', '.', '1'};
        board[5] = new char[]{'7', '.', '.', '.', '2', '.', '.', '.', '6'};
        board[6] = new char[]{'.', '6', '.', '.', '.', '.', '2', '8', '.'};
        board[7] = new char[]{'.', '.', '.', '4', '1', '9', '.', '.', '5'};
        board[8] = new char[]{'.', '.', '.', '.', '8', '.', '.', '7', '9'};
        for (int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
        new Solution1().solveSudoku(board);
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
    }
}
