package problems.lcof;

/**
 * 面试题04.二维数组中查找
 * 难度：简单
 *
 * 题目描述
 * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 *
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
 * 相似题：
 * LeetCode 240. 搜索二维矩阵II（https://leetcode-cn.com/problems/search-a-2d-matrix-ii/）
 *
 * @author kyan
 * @date 2020/2/19
 */
public class LCOF_4_FindNumberIn2DArray {

    /**
     * 解法一
     * 从右上角出发作为起点，可以看成是一个二叉树
     * 二分+递归
     * 1 ms, 38.24%
     * 48.7 MB, 100.00%
     */
    public static class Solution1 {
        public boolean findNumberIn2DArray(int[][] matrix, int target) {
            if (matrix.length == 0 || matrix[0].length == 0) return false;
            return findNumberInternally(matrix, target, 0, matrix[0].length-1);
        }

        public boolean findNumberInternally(int[][] matrix, int target, int i, int j) {
            if (i == matrix.length || j == -1) return false;
            if (target == matrix[i][j]) return true;
            if (target > matrix[i][j]) {
                return findNumberInternally(matrix, target, ++i, j);
            } else {
                return findNumberInternally(matrix, target, i, --j);
            }
        }
    }

    /**
     * 解法二【推荐】
     * 非递归
     * 0 ms, 100.00%
     * 48.5 MB, 100.00%
     */
    public static class Solution2 {
        public boolean findNumberIn2DArray(int[][] matrix, int target) {
            if (matrix.length == 0 || matrix[0].length == 0) return false;
            int i = 0, j = matrix[0].length - 1;
            while (i < matrix.length && j > -1) {
                if (target == matrix[i][j]) return true;
                if (target > matrix[i][j]) {
                    ++i;
                } else {
                    j--;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                { 1,  4,  7, 11, 15},
                { 2,  5,  8, 12, 19},
                { 3,  6,  9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        System.out.println(new Solution1().findNumberIn2DArray(matrix, 5));//true
        System.out.println(new Solution1().findNumberIn2DArray(matrix, 20));//false
        System.out.println(new Solution2().findNumberIn2DArray(matrix, 5));//true
        System.out.println(new Solution2().findNumberIn2DArray(matrix, 20));//false
    }
}
