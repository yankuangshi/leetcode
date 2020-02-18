package problems.stack;

import java.util.LinkedList;

/**
 * 42. 接雨水
 * 难度：困难
 *
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 *   |
 *   |
 * 3 |                           +---+
 *   |                           |   |
 * 2 |           +---+           +---+---+   +---+
 *   |           |   |\\\\\\\\\\\|   |   |\\\|   |
 * 1 |   +---+   +---+---+   +---+---+---+---+---+---+
 *   |   |   |\\\|   |   |\\\|   |   |   |   |   |   |
 * 0 |------------------------------------------------
 *     0   1   0   2   1   0   1   3   2   1   2   1
 *
 * 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（\\\部分表示雨水）
 *
 * 示例:
 *
 * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出: 6
 *
 * @author kyan
 * @date 2020/2/1
 */
public class LeetCode_42_TrappingRainWater {


    /**
     * 思路1
     * 利用单调栈（递减栈）
     *
     *   |
     *   |
     * 3 |                           +---+
     *   |                           |   |
     * 2 |           +---+           +---+---+   +---+
     *   |           |   |\\\\\\\\\\\|   |   |\\\|   |
     * 1 |   +---+   +---+---+   +---+---+---+---+---+---+
     *   |   |   |\\\|   |   |\\\|   |   |   |   |   |   |
     * 0 |------------------------------------------------
     *     0   1   0   2   1   0   1   3   2   1   2   1
     *
     * 例如，某一个时刻栈的状态如下
     *
     * |   |
     * | 0 |
     * |_1_|
     *
     * 接下来是数字2入栈，由于是递减栈，2>top(0)，栈顶数字0需要被弹出，[1,0,2] 就形成了一个凹槽，由此可以来计算接水的面积
     *
     *   |
     *   |
     * 3 |
     *   |
     * 2 |           +---+
     *   |           |   |
     * 1 |   +---+   +---+
     *   |   |   |\\\|   |
     * 0 |----------------
     *     0   1   0   2
     * idx 0   1   2   3
     *
     * 面积计算=[min(待入栈height,栈顶height) - 出栈height]*(待入栈height的下标 - 栈顶height的下标 - 1)
     * [min(2,1)-0]*(3-1-1)=1
     *
     * time 5ms, beat 32.92%
     * space 37.3MB, beat 81.32%
     */
    public static class Solution1 {

        public int trap(int[] heights) {
            int res = 0;
            LinkedList<Integer> stack = new LinkedList<>();
            for (int i = 0; i < heights.length; i++) {
                while (!stack.isEmpty() && heights[stack.peek()] < heights[i]) {
                    int tempHeight = heights[stack.pop()];
                    if (!stack.isEmpty()) {
                        int leftIndex = stack.peek();
                        res += (Math.min(heights[leftIndex], heights[i]) - tempHeight) * (i - leftIndex - 1);
                    }
                }
                stack.push(i);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int[] heights = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(new Solution1().trap(heights));
    }
}
