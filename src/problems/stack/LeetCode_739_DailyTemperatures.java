package problems.stack;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 739. 每日温度
 * 难度：中等
 *
 * 根据每日气温列表，请重新生成一个列表，对应位置的输入是你需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用 0 来代替。
 *
 * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
 *
 * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
 *
 * 相似题目：
 * 496. 下一个更大元素 I
 * 503. 下一个更大元素 II
 *
 * @author kyan
 * @date 2020/1/31
 */
public class LeetCode_739_DailyTemperatures {

    /**
     * 思路1
     * 相似题目496和503，利用单调栈
     * 和题496、503不同的是，单调栈中存储的是数组的小标
     * 例如：[73, 74, 75, 71, 69, 72, 76, 73]
     *
     * index：  0   1   2   3   4   5   6   7
     * step1：[73, 74, 75, 71, 69, 72, 76, 73] empty stack,push(index=7)
     *                                     ^
     * |   |
     * |   | [ , , , , , , ,7]
     * |_7_|
     *
     * index：  0   1   2   3   4   5   6   7
     * step1：[73, 74, 75, 71, 69, 72, 76, 73] 76>T[stack.peek()]=73,pop(index=7),push(index=6)
     *                                  ^
     * |   |
     * |   | [ , , , , , ,6,7]
     * |_6_|
     *
     * index：  0   1   2   3   4   5   6   7
     * step1：[73, 74, 75, 71, 69, 72, 76, 73] 72<T[stack.peek()]=76,push(index=5)
     *                              ^
     * |   |
     * | 5 | [ , , , , ,6,6,7]
     * |_6_|
     *
     * index：  0   1   2   3   4   5   6   7
     * step1：[73, 74, 75, 71, 69, 72, 76, 73] 69<T[stack.peek()]=72,push(index=4)
     *                          ^
     * | 4 |
     * | 5 | [ , , , ,5,6,6,7]
     * |_6_|
     *
     * index：  0   1   2   3   4   5   6   7
     * step1：[73, 74, 75, 71, 69, 72, 76, 73] 71>T[stack.peek()]=69,pop(index=4),71<T[stack.peek()]=72,push(index=3)
     *                      ^
     * | 3 |
     * | 5 | [ , , ,5,5,6,6,7]
     * |_6_|
     *
     * index：  0   1   2   3   4   5   6   7
     * step1：[73, 74, 75, 71, 69, 72, 76, 73] 75>T[stack.peek()]=71,pop(index=3),75>T[stack.peek()]=72,pop(index=5),75<T[stack.peek()]=76,push(index=2)
     *                  ^
     * |   |
     * | 2 | [ , ,6,5,5,6,6,7]
     * |_6_|
     *
     * index：  0   1   2   3   4   5   6   7
     * step1：[73, 74, 75, 71, 69, 72, 76, 73] 74<T[stack.peek()]=75,push(index=1)
     *              ^
     * | 1 |
     * | 2 | [ ,2,6,5,5,6,6,7]
     * |_6_|
     *
     * index：  0   1   2   3   4   5   6   7
     * step1：[73, 74, 75, 71, 69, 72, 76, 73] 73<T[stack.peek()]=74,push(index=0)
     *          ^
     * | 0 |
     * | 1 |
     * | 2 | [1,2,6,5,5,6,6,7]
     * |_6_|
     *
     * ans=[1,2,6,5,5,6,6,7] 意味着
     * T[i]的 next greater element 在index=ans[i]，例如73(T[0])的 next greater element 在index=1(ans[0])，74(T[1])的 next greater element 在index=2(ans[1])
     * 当ans[i]=i的时候，说明T[i]的 next greater element 不存在
     *
     * 最后的结果只需要 ans[i]-i 即可
     *
     * ans   = [1,2,6,5,5,6,6,7]
     * index = [0,1,2,3,4,5,6,7]
     * final = [1,1,4,2,1,1,0,0]
     *
     * time 35ms, beat 77.08%
     * space 43.5MB, beat 19.12%
     */
    public static class Solution1 {

        public int[] dailyTemperatures(int[] T) {
            int[] days = new int[T.length];
            LinkedList<Integer> stack = new LinkedList<>();
            for (int i = T.length - 1; i >= 0; i--) {
                while (!stack.isEmpty() && T[stack.peek()] <= T[i]) {
                    stack.pop();
                }
                days[i] = stack.isEmpty() ? i : stack.peek();
                stack.push(i);
            }
            for (int i = 0; i < days.length; i++) {
                days[i] = days[i] - i;
            }
            return days;
        }
    }

    /**
     * 思路2
     * 单调栈，正向遍历
     *
     * time 21ms, beat 79.43%
     * space 43.7MB, beat 15.30%
     */
    public static class Solution2 {

        public int[] dailyTemperatures(int[] T) {
            int[] days = new int[T.length];
            LinkedList<Integer> stack = new LinkedList<>();
            for (int i = 0; i < T.length; i++) {
                while (!stack.isEmpty() && T[stack.peek()] < T[i]) {
                    int index = stack.pop();
                    days[index] = i - index;
                }
                stack.push(i);
            }
            while (!stack.isEmpty()) {
                days[stack.pop()] = 0;
            }
            return days;
        }
    }

    /**
     * 思路3
     * 暴力解法，直接遍历除最后一个数外的其他数，找出比当前数大的第一个数的位置
     * 时间复杂度O(n^2)，最慢，但是真的好简单...
     *
     * time 310ms, beat 9.8%
     * space 41.7MB, beat 81.91%
     */
    public static class Solution3 {

        public int[] dailyTemperatures(int[] T) {
            int[] days = new int[T.length];
            for (int i = 0; i < T.length; i++) {
                for (int j = i+1; j < T.length; j++) {
                    if (T[j] > T[i]) {
                        days[i] = j - i;
                        break;
                    }
                }
            }
            return days;
        }
    }

    public static void main(String[] args) {
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println(Arrays.toString(new Solution1().dailyTemperatures(temperatures)));
        System.out.println(Arrays.toString(new Solution2().dailyTemperatures(temperatures)));
        System.out.println(Arrays.toString(new Solution3().dailyTemperatures(temperatures)));
    }
}
