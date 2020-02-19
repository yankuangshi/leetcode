package problems.array;

/**
 * 74. 搜索二维矩阵
 * 难度：中等
 * 题目描述
 *
 * 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 * 示例 1:
 * 输入:
 * matrix = [
 *   [1,   3,  5,  7],
 *   [10, 11, 16, 20],
 *   [23, 30, 34, 50]
 * ]
 * target = 3
 * 输出: true
 *
 * 示例 2:
 * 输入:
 * matrix = [
 *   [1,   3,  5,  7],
 *   [10, 11, 16, 20],
 *   [23, 30, 34, 50]
 * ]
 * target = 13
 * 输出: false
 *
 * @author kyan
 * @date 2020/2/19
 */
public class LeetCode_74_SearchA2DMatrix {

    /**
     * 解法一（二分+递归版本）
     *
     * 这道题和[240. 搜索二维矩阵 II](https://leetcode-cn.com/problems/search-a-2d-matrix-ii/) 还是有点区别，
     * 虽然该题中的矩阵也符合题240中矩阵的特点，可以用题240的方法解答，即从右上角向左下角遍历来寻找目标值，但是由于时间复杂度为O(n+m)，因此效率并不高。
     *
     * 该题中的二维矩阵符合全部排好序这个特色，即如果把二维矩阵中每一行串起来看，其实是排好序的，因此想到可以用二分法。
     *
     * 整体思路，先把每一行看成一个整体进行二分，目的是通过二分查找定位到相应的行，然后再在行内做二分查找。
     *
     * 例如 矩阵行数 rows = matrix.length，列数 cols = matrix[0].length
     *
     * 先对矩阵的行整体做二分，中间行是 matrix[(rows-1)/2]
     *
     * 如果 target < matrix[(rows-1)/2][0]（中间行的最左端数字），则在中间行的上半部分矩阵，递归；
     * 如果 target > matrix[(rows-1)/2][cols-1]（中间行的最右端数字），则在中间行的下半部分矩阵，递归；
     * 如果 target 在两者之间，则在 matrix[(rows-1)/2] 该行内再做二分
     *
     * 0 ms, 100.00%
     * 42 MB, 32.86%
     */
    public static class Solution1 {
        public boolean searchMatrix(int[][] matrix, int target) {
            if (matrix.length == 0 || matrix[0].length == 0) return false;
            return bsearch(matrix, target, 0, matrix.length-1);
        }

        private boolean bsearch(int[][] matrix, int target, int lowRow, int highRow) {
            if (lowRow > highRow) return false;
            int midRow = lowRow + ((highRow-lowRow)>>1);
            if (target < matrix[midRow][0]) {
                return bsearch(matrix, target, lowRow, --highRow);
            } else if (target > matrix[midRow][matrix[0].length-1]){
                return bsearch(matrix, target, ++lowRow, highRow);
            } else {
                return bsearchInRow(matrix[midRow], target, 0, matrix[0].length - 1);
            }
        }

        private boolean bsearchInRow(int[] nums, int target, int lowIndex, int highIndex) {
            if (lowIndex > highIndex) return false;
            int mid = lowIndex + ((highIndex-lowIndex)>>1);
            if (nums[mid] == target) return true;
            if (nums[mid] > target) {
                return bsearchInRow(nums, target, lowIndex, --highIndex);
            } else {
                return bsearchInRow(nums, target, ++lowIndex, highIndex);
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 50}
        };
        System.out.println(new Solution1().searchMatrix(matrix, 3));
        System.out.println(new Solution1().searchMatrix(matrix, 13));
    }
}
