package problems.stack;

import java.util.HashMap;
import java.util.Stack;

/**
 * 20. 有效的括号
 * 难度：简单
 * <p>
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "()"
 * 输出: true
 * <p>
 * 示例 2:
 * <p>
 * 输入: "()[]{}"
 * 输出: true
 * 示例 3:
 * <p>
 * 输入: "(]"
 * 输出: false
 * 示例 4:
 * <p>
 * 输入: "([)]"
 * 输出: false
 * 示例 5:
 * <p>
 * 输入: "{[]}"
 * 输出: true
 *
 * @author kyan
 * @date 2020/1/28
 */
public class _20_ValidParentheses {

    /**
     * 思路：
     * 利用栈，遍历字符数组，当遇到'('，'['，'{'时入栈，遇到')'，']'，'}'时判断栈是否为空（为空则可以判断为false），或栈顶字符
     * 是否匹配，如果匹配则出栈，否则判断为false
     * 遍历完成后最后判断栈是否为空，如果字符都是一一匹配的话，那么栈一定是空的
     * <p>
     * time 3ms, beat 59.6%
     * space 34.1MB, beat 80.05%
     */
    public static class Solution1 {

        public boolean isValid(String s) {
            char[] chars = s.toCharArray();
            Stack<Character> stack = new Stack<Character>();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '(' || chars[i] == '{' || chars[i] == '[') {
                    stack.push(chars[i]);
                }
                switch (chars[i]) {
                    case ')':
                        if (!stack.isEmpty() && stack.peek() == '(') {
                            stack.pop();
                            break;
                        } else {
                            return false;
                        }
                    case ']':
                        if (!stack.isEmpty() && stack.peek() == '[') {
                            stack.pop();
                            break;
                        } else {
                            return false;
                        }
                    case '}':
                        if (!stack.isEmpty() && stack.peek() == '{') {
                            stack.pop();
                            break;
                        } else {
                            return false;
                        }
                    default:
                        break;
                }
            }
            return stack.isEmpty();
        }
    }


    public static class Solution2 {

        public HashMap<Character, Character> mappings = new HashMap<Character, Character>() {
            {
                put(')', '(');
                put(']', '[');
                put('}', '{');
            }
        };

        public boolean isValid(String s) {
            char[] chars = s.toCharArray();
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < chars.length; i++) {
                if (mappings.containsKey(chars[i])) {
                    //如果为')','}',']'之一，则需要判断栈顶元素是否与之匹配
                    if (stack.isEmpty()) {
                        return false;
                    } else if (!stack.pop().equals(mappings.get(chars[i]))) {
                        return false;
                    }
                } else if (chars[i] != ' '){
                    //排除空字符
                    stack.push(chars[i]);
                }
            }
            return stack.isEmpty();
        }
    }


    public static void main(String[] args) {
        String s = "[(  )]{}";
        System.out.println(new Solution2().isValid(s));
    }
}
