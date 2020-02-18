package problems.stack;

import java.util.LinkedList;

/**
 * 844. 比较含退格的字符串
 * 难度：简单
 *
 * 题目描述
 *
 * 给定 S 和 T 两个字符串，当它们分别被输入到空白的文本编辑器后，判断二者是否相等，并返回结果。 # 代表退格字符。
 *
 * 示例 1：
 * 输入：S = "ab#c", T = "ad#c"
 * 输出：true
 * 解释：S 和 T 都会变成 “ac”。
 *
 * 示例 2：
 * 输入：S = "ab##", T = "c#d#"
 * 输出：true
 * 解释：S 和 T 都会变成 “”。
 *
 * 示例 3：
 * 输入：S = "a##c", T = "#a#c"
 * 输出：true
 * 解释：S 和 T 都会变成 “c”。
 *
 * 示例 4：
 * 输入：S = "a#c", T = "b"
 * 输出：false
 * 解释：S 会变成 “c”，但 T 仍然是 “b”。
 *
 * @author kyan
 * @date 2020/2/2
 */
public class LeetCode_844_BackspaceStrCompare {

    /**
     * 解法一
     * 除#外的字符入栈，遇到#则弹出栈顶字符，对S和T都进行同样操作，最后对比S的栈和T的栈是否相同
     *
     * 2 ms, 79.61%
     * 34.8 MB, 26.34%
     */
    public static class Solution1 {

        public boolean backspaceCompare(String S, String T) {
            LinkedList<Character> S1 = new LinkedList<Character>();
            LinkedList<Character> T1 = new LinkedList<Character>();
            for (Character c : S.toCharArray()) {
                if (c != '#') {
                    S1.push(c);
                } else if (!S1.isEmpty()) {
                    S1.pop();
                }
            }
            for (Character c : T.toCharArray()) {
                if (c != '#') {
                    T1.push(c);
                } else if (!T1.isEmpty()) {
                    T1.pop();
                }
            }
            if (S1.size() != T1.size()) {
                return false;
            }
            while (!S1.isEmpty() && !T1.isEmpty()) {
                if (S1.pop() != T1.pop()) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        String S = "a##c";
        String T = "#a#c";
        System.out.println(new Solution1().backspaceCompare(S, T));
    }
}
