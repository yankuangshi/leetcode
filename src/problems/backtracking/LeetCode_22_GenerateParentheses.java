package problems.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 22. 括号生成
 * 难度：中等
 * 题目描述
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 *
 * 例如，给出 n = 3，生成结果为：
 *
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 *
 * @author kyan
 * @date 2020/2/27
 */
public class LeetCode_22_GenerateParentheses {


    /**
     * 解法一（回溯+剪枝）
     * 先画出整个决策树（假设n=2）
     *               [ ]
     *             /    \
     *         (           )
     *       /  \        /  \
     *     (     )     (     )
     *    / \   / \   / \   / \
     *   (  )  (  )  (  )  (  )
     *   x /\ /\ /\ /\ /\ /\ /\
     *    ( )( )( )( )( )( )( )
     *    x  x  x xx xx xx xx x
     *
     * 最终结果只有 (()) 和 ()()
     *
     * 通过以下2个规则可以剪枝来减少决策树分叉
     * 1. 生成左子树（左括号）的要求是：剩余左括号>0
     * 2. 生成右子树（右括号）的要求是：当前右括号个数小于左括号个数，例如()这种情况下左右括号数相等，再加一个右括号必然会产生无效括号
     *
     * 通过剪枝后的决策树：
     *
     *            [ ]
     *           /
     *         (
     *       /  \
     *     (     )
     *      \   /
     *      )  (
     *      \  \
     *      )  )
     *
     * 3 ms, 28% 😢
     * 41 MB, 5%
     */
    public static class Solution1 {

        private List<String> res = new ArrayList<>();
        private int leftCount = 0, rightCount = 0;

        public List<String> generateParenthesis(int n) {
            List<Character> parentheses = new ArrayList<>();
            if (n == 0) return res;
            backtrack(n, parentheses);
            return res;
        }

        private void backtrack(int n, List<Character> parentheses) {
            if (parentheses.size() == n*2) {
                StringBuilder builder = new StringBuilder();
                for (Character character : parentheses) {
                    builder.append(character);
                }
                res.add(builder.toString());
                return;
            }
            if (leftCount < n) {
                leftCount++;
                parentheses.add('(');
                backtrack(n, parentheses);
                leftCount--;
                parentheses.remove(parentheses.size() - 1);
            }
            if (rightCount < leftCount) {
                rightCount++;
                parentheses.add(')');
                backtrack(n, parentheses);
                rightCount--;
                parentheses.remove(parentheses.size() - 1);
            }
        }
    }

    /**
     * 解法二（代码优化，效率更高）
     * 2 ms, 53.47%
     * 39.7 MB, 5.00%
     */
    public static class Solution2 {

        public List<String> generateParenthesis(int n) {
            List<String> res = new ArrayList<>();
            if (n == 0) return res;
            backtrack("", 0, 0, n, res);
            return res;
        }

        private void backtrack(String result, int leftCount, int rightCount, int n, List<String> res) {
            if (leftCount == n && rightCount == n) {
                res.add(result);
                return;
            }
            if (leftCount < rightCount) {
                return;
            }
            if (leftCount < n) {
                backtrack(result + "(", leftCount + 1, rightCount, n, res);
            }
            if (rightCount < n) {
                backtrack(result + ")", leftCount, rightCount + 1, n, res);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution1().generateParenthesis(1));
        System.out.println(new Solution2().generateParenthesis(2));
        System.out.println(new Solution1().generateParenthesis(3));
    }
}
