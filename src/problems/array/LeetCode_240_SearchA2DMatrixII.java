package problems.array;

/**
 * 240. 搜索二维矩阵 II
 * 难度：中等
 *
 * 题目描述
 *
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
 *
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 * 示例:
 *
 * 现有矩阵 matrix 如下：
 *
 * [
 *   [1,   4,  7, 11, 15],
 *   [2,   5,  8, 12, 19],
 *   [3,   6,  9, 16, 22],
 *   [10, 13, 14, 17, 24],
 *   [18, 21, 23, 26, 30]
 * ]
 * 给定 target = 5，返回 true。
 * 给定 target = 20，返回 false。
 *
 * @author kyan
 * @date 2020/2/19
 */
public class LeetCode_240_SearchA2DMatrixII {

    /**
     * 解法一
     * 从右上角作为起点，可以看成是一个BST
     * 从右上角开始往左下角遍历，起始 i=0，j=列数-1
     * 如果target等于matrix[i][j]，则找到；
     * 如果target比matrix[i][j]大，则说明在下半部分矩阵中，i++；
     * 如果target比matrix[i][j]小，则说明在左半部分矩阵中，j--；
     * 当i、j出了边界，则说明矩阵中不存在target
     * 时间复杂度O(m+n)，最坏情况就是从右上角遍历到左下角。
     * 空间复杂度O(1)
     * 7 ms, 86.85%
     * 42.3 MB, 65.47%
     */
    public static class Solution1 {
        public boolean searchMatrix(int[][] matrix, int target) {
            if (matrix.length == 0 || matrix[0].length == 0) return false;
            int i = 0, j = matrix[0].length - 1;
            while (i < matrix.length && j > -1) {
                if (target == matrix[i][j]) return true;
                if (target > matrix[i][j]) {
                    i++;
                } else {
                    j--;
                }
            }
            return false;
        }
    }


    public static void main(String[] args) {
        int[][] matrix = {
                {1,   4,  7, 11, 15},
                {2,   5,  8, 12, 19},
                {3,   6,  9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        System.out.println(new Solution1().searchMatrix(matrix, 5));
        System.out.println(new Solution1().searchMatrix(matrix, 20));

    }
}
