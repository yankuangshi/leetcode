package problems.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * 503. 下一个更大元素 II
 * 难度：中等
 *
 * 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
 *
 * 示例 1:
 *
 * 输入: [1,2,1]
 * 输出: [2,-1,2]
 * 解释: 第一个 1 的下一个更大的数是 2；
 * 数字 2 找不到下一个更大的数；
 * 第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
 * 注意: 输入数组的长度不会超过 10000。
 *
 * 相关题目：496. 下一个更大元素 I
 *
 * @author kyan
 * @date 2020/1/31
 */
public class _503_NextGreaterElementII {

    /**
     * 思路1
     * 单调栈，把原数组扩展至2倍，从后往前遍历
     * 例如 [3,8,4,1,2] 扩展2倍 [3,8,4,1,2,3,8,4,1,2]
     * 然后创建一个数组用于存储结果，从后往前遍历数组 [3,8,4,1,2]
     *
     * step1：[3,8,4,1,2,3,8,4,1,2] stack empty,push(2)
     *                           ^
     * |   |
     * |   | [ , , , , , , , , ,-1]
     * |_2_|
     *
     * step2：[3,8,4,1,2,3,8,4,1,2] 1<top(2),push(1)
     *                         ^
     * |   |
     * | 1 | [ , , , , , , , ,2,-1]
     * |_2_|
     *
     * step3：[3,8,4,1,2,3,8,4,1,2] 4>top(1),pop(1),4>top(2),pop(2),push(4)
     *                       ^
     * |   |
     * |   | [ , , , , , , ,-1,2,-1]
     * |_4_|
     *
     * step4：[3,8,4,1,2,3,8,4,1,2] 8>top(4),pop(4),push(8)
     *                     ^
     * |   |
     * |   | [ , , , , , ,-1,-1,2,-1]
     * |_8_|
     *
     * step5：[3,8,4,1,2,3,8,4,1,2] 3<top(8),push(3)
     *                   ^
     * |   |
     * | 3 | [ , , , , ,8,-1,-1,2,-1]
     * |_8_|
     *
     * step6：[3,8,4,1,2,3,8,4,1,2] 2<top(3),push(2)
     *                 ^
     * | 2 |
     * | 3 | [ , , , ,3,8,-1,-1,2,-1]
     * |_8_|
     *
     * step7：[3,8,4,1,2,3,8,4,1,2] 1<top(2),push(1)
     *               ^
     * | 1 |
     * | 2 |
     * | 3 | [ , , ,2,3,8,-1,-1,2,-1]
     * |_8_|
     *
     * step8：[3,8,4,1,2,3,8,4,1,2] 4>top(1),pop(1),4>top(2),pop(2),4>top(3),pop(3),4<top(8),push(4)
     *             ^
     * |   |
     * | 4 | [ , ,8,2,3,8,-1,-1,2,-1]
     * |_8_|
     *
     * step9：[3,8,4,1,2,3,8,4,1,2] 8>top(4),pop(4),8>=top(8),pop(8),push(8)
     *           ^
     * |   |
     * |   | [ ,-1,8,2,3,8,-1,-1,2,-1]
     * |_8_|
     *
     * step10：[3,8,4,1,2,3,8,4,1,2] 3<top(8),push(3)
     *          ^
     * |   |
     * | 3 | [8,-1,8,2,3,8,-1,-1,2,-1]
     * |_8_|
     *
     * 所以最后结果取前5位 [8,-1,8,2,3]
     *
     * time 56ms, beat 42.59%
     * space 40.4MB, beat 48.20%
     */
    public static class Solution1 {

        public int[] nextGreaterElements(int[] nums) {
            Stack<Integer> stack = new Stack<>();
            int[] ans = new int[nums.length];
            int[] tempAns = new int[nums.length*2];
            int[] tempNums = new int[nums.length*2];
            //扩展2倍
            for (int i = 0; i < nums.length; i++) {
                tempNums[i] = nums[i];
                tempNums[nums.length + i] = nums[i];
            }
            for (int i = tempNums.length-1; i >= 0; i--) {
                while (!stack.isEmpty() && stack.peek() <= tempNums[i]) {
                    //把小的栈顶数字弹出
                    stack.pop();
                }
                tempAns[i] = stack.isEmpty() ? -1 : stack.peek();
                stack.push(tempNums[i]);
            }
            for (int i = 0; i < nums.length; i++) {
                ans[i] = tempAns[i];
            }
            return ans;
        }
    }

    /**
     * 思路2
     * 和思路1相似，但是不扩展原数组，在原数组的基础上循环入栈，也是从后往前遍历入栈
     * 例如：[3,8,4,1,2]
     *
     * step1：[3,8,4,1,2] stack empty,push(2)
     *                 ^
     * |   |
     * |   | [ , , , ,-1]
     * |_2_|
     *
     * step2：[3,8,4,1,2] 1<top(2),push(1)
     *               ^
     * |   |
     * | 1 | [ , , ,2,-1]
     * |_2_|
     *
     * step3：[3,8,4,1,2] 4>top(1),pop(1),4>top(2),pop(2),push(4)
     *             ^
     * |   |
     * |   | [ , ,-1,2,-1]
     * |_4_|
     *
     * step4：[3,8,4,1,2] 8>top(4),pop(4),push(8)
     *           ^
     * |   |
     * |   | [ ,-1,-1,2,-1]
     * |_8_|
     *
     * step5：[3,8,4,1,2] 3<top(8),push(3)
     *         ^
     * |   |
     * | 3 | [8,-1,-1,2,-1]
     * |_8_|
     *
     * step6：第二次循环 [3,8,4,1,2] 2<top(3),push(2)
     *                           ^
     * | 2 |
     * | 3 | [8,-1,-1,2,3]
     * |_8_|
     *
     * step7：[3,8,4,1,2] 1<top(2),push(1)
     *               ^
     * | 1 |
     * | 2 |
     * | 3 | [8,-1,-1,2,3]
     * |_8_|
     *
     * step8：[3,8,4,1,2] 4>top(1),pop(1),4>top(2),pop(2),4>top(3),pop(3),4<top(8),push(4)
     *             ^
     * |   |
     * | 4 | [8,-1,8,2,3]
     * |_8_|
     *
     * step9：[3,8,4,1,2] 8>top(4),pop(4),8>=top(8),pop(8),push(8)
     *           ^
     * |   |
     * |   | [8,-1,8,2,3]
     * |_8_|
     *
     * step10：[3,8,4,1,2] 3<top(8),push(3)
     *          ^
     * |   |
     * | 3 | [8,-1,8,2,3]
     * |_8_|
     *
     * time 61ms, beat 24.74%
     * space 45.3MB, beat 15.09%
     *
     */
    public static class Solution2 {

        public int[] nextGreaterElements(int[] nums) {
            int[] ans = new int[nums.length];
            Stack<Integer> stack = new Stack<>();
            for (int i = nums.length*2-1; i >=0; i--) {
                int index = i % nums.length;
                while (!stack.isEmpty() && stack.peek() <= nums[index]) {
                    stack.pop();
                }
                ans[index] = stack.isEmpty() ? -1 : stack.peek();
                stack.push(nums[index]);
            }
            return ans;
        }
    }


    public static void main(String[] args) {
//        int[] nums = {2,1,2,1,4,3};
        int[] nums = {100,1,11,1,120,111,123,1,-1,-100};
        System.out.println(Arrays.toString(new Solution1().nextGreaterElements(nums)));
        System.out.println(Arrays.toString(new Solution2().nextGreaterElements(nums)));
    }
}
