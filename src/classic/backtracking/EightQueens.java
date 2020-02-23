package classic.backtracking;

/**
 * @author kyan
 * @date 2020/2/23
 */
public class EightQueens {

    public static class Solution {

        /**
         * int数组存放最终结果，下标表示行，值表示列，例如 result[0]=1 表示第0行，第1列
         */
        private int[] queens = new int[8];
        private int total;

        public void solve8Queens() {
            solve8QueensHelper(0);
            System.out.println("Total: " + total);
        }

        /**
         * 参数row表示正在尝试第row行，row从0-7共8行
         */
        private void solve8QueensHelper(int row) {
            if (row == 8) {
                print8queens();
                total++;
                return;
            }
            for (int col = 0; col < 8; col++) {
                if (isOk(row, col)) {
                    queens[row] = col;
                    solve8QueensHelper(row+1);
                }
            }
        }

        private boolean isOk(int row, int col) {
            //因为是按每行从上往下尝试的，所以当尝试第row行的时候，只需要判断0~row-1行
            //判断的位置有正上方、左上、右上
            //行确定的情况下，左上的列为 col-1，右上的列为 col+1
            int leftUp = col - 1, rightUp = col + 1;
            for (int i = row-1; i >= 0; i--) {
                if (queens[i] == col) return false;
                if (leftUp >= 0) {
                    if (queens[i] == leftUp) return false;
                }
                if (rightUp < 8) {
                    if (queens[i] == rightUp) return false;
                }
                --leftUp;
                ++rightUp;
            }
            return true;
        }

        private void print8queens() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (queens[row] == col) System.out.print("Q ");
                    else System.out.print(". ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new Solution().solve8Queens();
    }
}
