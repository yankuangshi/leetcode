package problems.stack;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 150. 逆波兰表达式求值
 *
 * 难度：中等
 *
 * 题目描述
 *
 * 根据逆波兰表示法，求表达式的值。
 *
 * 有效的运算符包括 +, -, *, / 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
 *
 * 说明：
 *
 * 整数除法只保留整数部分。
 * 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
 * 示例 1：
 *
 * 输入: ["2", "1", "+", "3", "*"]
 * 输出: 9
 * 解释: ((2 + 1) * 3) = 9
 * 示例 2：
 *
 * 输入: ["4", "13", "5", "/", "+"]
 * 输出: 6
 * 解释: (4 + (13 / 5)) = 6
 * 示例 3：
 *
 * 输入: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
 * 输出: 22
 * 解释:
 *   ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 *
 * @author kyan
 * @date 2020/2/2
 */
public class _150_EvalReversePolishNotation {

    /**
     * 解法一
     * 利用一个栈可以完成逆波兰表达式求值
     * 遍历数组，如果是数值则入栈，如果是运算符则弹出2个数值，第一个弹出的作为运算符的右表达式，第二个弹出的作为运算符的左表达式，计算新的值后入栈
     *
     * 例如 ["2", "1", "+", "3", "*"]
     *
     * step1: ["2", "1", "+", "3", "*"] 2入栈
     *          ↑
     * |   |
     * |   |
     * |_2_|
     *
     * step2: ["2", "1", "+", "3", "*"] 1入栈
     *               ↑
     * |   |
     * | 1 |
     * |_2_|
     *
     * step3: ["2", "1", "+", "3", "*"] 遇到"+"，1、2出栈，2+1=3入栈
     *                    ↑
     * |       |
     * |       |
     * |_(2+1)_|
     *
     * step4: ["2", "1", "+", "3", "*"] 3入栈
     *                         ↑
     * |       |
     * |   3   |
     * |_(2+1)_|
     *
     * step5: ["2", "1", "+", "3", "*"] 遇到"*"，3、(2+1)出栈，(2+1)*3=9入栈
     *                              ↑
     * |   |
     * |   |
     * |_9_|
     *
     * step6：遍历结束，9出栈
     *
     * 8 ms, 90.12%
     * 36.3 MB, 96.89%
     */
    public static class Solution1 {

        static Set<String> operators = new HashSet<String>() {{
            add("+");
            add("-");
            add("*");
            add("/");
        }};

        public int evalRPN(String[] tokens) {
            LinkedList<Integer> stack = new LinkedList<>();
            for (int i = 0; i < tokens.length; i++) {
                if (operators.contains(tokens[i])) {
                    int right = stack.pop();
                    if (tokens[i].equals("*")) {
                        stack.push(stack.pop() * right);
                    } else if (tokens[i].equals("/")) {
                        stack.push(stack.pop() / right);
                    } else if (tokens[i].equals("+")) {
                        stack.push(stack.pop() + right);
                    } else if (tokens[i].equals("-")) {
                        stack.push(stack.pop() - right);
                    }
                } else {
                    stack.push(Integer.valueOf(tokens[i]));
                }
            }
            return stack.pop();
        }
    }

    /**
     * 解法二
     * 思路同解法一，switch代替if else优化
     *
     * 6 ms, 95%
     */
    public static class Solution2 {

        public int evalRPN(String[] tokens) {
            LinkedList<Integer> stack = new LinkedList<>();
            for (String token : tokens) {
                switch (token) {
                    case "+":
                        stack.push(stack.pop() + stack.pop());
                        break;
                    case "-":
                        stack.push(-stack.pop() + stack.pop());
                        break;
                    case "*":
                        stack.push(stack.pop() * stack.pop());
                        break;
                    case "/":
                        int right = stack.pop();
                        stack.push(stack.pop() / right);
                        break;
                    default:
                        stack.push(Integer.valueOf(token));
                        break;
                }
            }
            return stack.pop();
        }
    }

    public static void main(String[] args) {
        String[] tokens = {"4", "13", "5", "/", "+"};
        System.out.println(new Solution1().evalRPN(tokens));
        System.out.println(new Solution2().evalRPN(tokens));
    }
}
