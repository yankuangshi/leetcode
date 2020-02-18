package problems.stack;

import java.util.LinkedList;

/**
 * 84. 柱状图中最大的矩形
 * 难度：困难
 *
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 *
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 *
 *             +---+
 *             |   |
 *         +---+---+
 *         |\\\|\\\|
 *         +---+---+
 *         |\\\|\\\|
 *         +---+---+   +---+
 *         |\\\|\\\|   |   |
 * +---+   +---+---+---+---+
 * |   |   |\\\|\\\|   |   |
 * +---+---+---+---+---+---+
 * |   |   |\\\|\\\|   |   |
 * -------------------------
 *   2   1   5   6   2   3
 *
 * 以上是柱状图的示例，其中每个柱子的宽度为 1，给定的高度为 [2,1,5,6,2,3]。
 *
 * 示例:
 *
 * 输入: [2,1,5,6,2,3]
 * 输出: 10
 *
 *
 * @author kyan
 * @date 2020/1/31
 */
public class LeetCode_84_LargestRectangleInHistogram {


    /**
     * 思路1
     * 对于数组T，利用单调栈
     * 寻找当前数的 pre smaller element 坐标（如果没有，index=-1）
     * 寻找当前数的 next smaller element 坐标（如果没有，index=T.length）
     * 例如数组 T = [2,1,5,6,2,3]
     * 2的pre smaller element不存在，index=-1；next smaller element为1，index=1
     * 1的pre smaller element不存在，index=-1；next smaller element不存在，index=T.length=6
     * 5的pre smaller element为1，index=1；next smaller element为2，index=4
     * 6的pre smaller element为5，index=2；next smaller element为2，index=4
     * 2的pre smaller element为1，index=1；next smaller element为不存在，index=T.length=6
     * 3的pre smaller element为2，index=4；next smaller element为不存在，index=T.length=6
     *
     * 然后计算把当前数包括进去的面积
     * 2=>2*[1-(-1)-1]=2
     * 1=>1*[6-(-1)-1]=6
     * 5=>5*(4-1-1)=10
     * 6=>6*(4-2-1)=6
     * 2=>2*(6-1-1)=8
     * 3=>3*(6-4-1)=3
     *
     * 所以最大的是5=>5*(4-1-1)=10
     *
     * time 18ms, beat 83.54%
     * space 40.4MB, beat 86.66%
     */
    public static class Solution1 {

        public int largestRectangleArea(int[] heights) {
            int[] preSmallerElements = preSmallerElements(heights);
            int[] nextSmallerElements = nextSmallerElements(heights);
            int res = 0;
            for (int i = 0; i < heights.length; i++) {
                res = Math.max(res, heights[i] * (nextSmallerElements[i] - preSmallerElements[i] - 1));
            }
            return res;
        }

        private int[] preSmallerElements(int[] heights) {
            int[] ans = new int[heights.length];
            LinkedList<Integer> stack = new LinkedList<>();
            //从后往前遍历
            for (int i = heights.length - 1; i >= 0; i--) {
                ans[i] = -1;
                while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                    ans[stack.pop()] = i;
                }
                stack.push(i);
            }
            return ans;
        }

        private int[] nextSmallerElements(int[] heights) {
            int[] ans = new int[heights.length];
            LinkedList<Integer> stack = new LinkedList<>();
            //从前往后遍历
            for (int i = 0; i < heights.length; i++) {
                ans[i] = heights.length;
                while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                    ans[stack.pop()] = i;
                }
                stack.push(i);
            }
            return ans;
        }
    }


    /**
     * 思路2
     * 思路1的优化
     * preSmallerElements和nextSmallerElements都从前往后遍历，只需要一次遍历
     *
     * time 10ms, beat 90.50%
     * space 39.6MB, beat 98.02%
     */
    public static class Solution2 {

        public int largestRectangleArea(int[] heights) {
            int[] preSmallerElements = new int[heights.length];
            int[] nextSmallerElements = new int[heights.length];
            LinkedList<Integer> stack = new LinkedList<>();
            for (int i = 0; i < heights.length; i++) {
                nextSmallerElements[i] = heights.length;
                while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                    nextSmallerElements[stack.pop()] = i;
                }
                preSmallerElements[i] = stack.isEmpty() ? -1 : stack.peek();
                stack.push(i);
            }
            int res = 0;
            for (int i = 0; i < heights.length; i++) {
                res = Math.max(res, heights[i] * (nextSmallerElements[i] - preSmallerElements[i] - 1));
            }
            return res;
        }

        public static void main(String[] args) {
            int[] heights = {2,1,5,6,2,3};
            System.out.println(new Solution1().largestRectangleArea(heights));
            System.out.println(new Solution2().largestRectangleArea(heights));
        }

    }
}
