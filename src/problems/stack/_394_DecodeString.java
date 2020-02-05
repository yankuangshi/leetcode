package problems.stack;

import java.util.LinkedList;

/**
 * 394. 字符串解码
 * 难度：中等
 *
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 *
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 *
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 *
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 *
 * 示例:
 *
 * s = "3[a]2[bc]", 返回 "aaabcbc".
 * s = "3[a2[c]]", 返回 "accaccacc".
 * s = "2[abc]3[cd]ef", 返回 "abcabccdcdcdef".
 *
 * @author kyan
 * @date 2020/2/5
 */
public class _394_DecodeString {

    /**
     * 解法一
     *
     * 利用2个栈，一个数字栈numStack，一个字母栈strStack
     * 遍历字符串
     * 1、字符为数字，解析数字（注意连续数字的情况）存入 num
     * 2、字符为字母，拼接字母 存入 str
     * 3、字符为左括号，把之前得到的数字 num 和 字母 str 分别压栈，然后把数字重置为0，字母字符串重置为空串
     * 4、字符为右括号，数字栈栈顶数字出栈，作为重复次数 n，字母栈栈顶字母出栈，作为前缀字母字符串去拼接 str 字母变量，总共拼接 n 次
     *
     * 2[abc]3[cd]ef
     *  ↑
     * 遇到左括号，把数字 num=2 和 字母 str="" 入栈，并且 num 和 str 重置
     *    |   |      |    |
     *    |   |      |    |
     *    |_2_|      |_""_|
     *   numStack    strStack
     *
     * 2[abc]3[cd]ef
     *      ↑
     * 遇到左括号，num=0 str="abc" numStack 和 strStack 栈顶元素出栈 str = strStack.pop() + str * numStack.pop() = "" + "abc" * 2 = "abcabc"
     *    |   |      |   |
     *    |   |      |   |
     *    |___|      |___|
     *   numStack    strStack
     *
     * 2[abc]3[cd]ef
     *        ↑
     * 遇到右括号，数字 num=3 和 字母 str="abcabc" 入栈，并且 num 和 str 重置
     *    |   |      |        |
     *    |   |      |        |
     *    |_3_|      |_abcabc_|
     *   numStack    strStack
     *
     * 2[abc]3[cd]ef
     *           ↑
     * 遇到左括号，num=0 str=cd numStack 和 strStack 栈顶元素出栈 str = "abcabc" + "cd" * 3 = "abcabccdcdcd"
     *    |   |      |        |
     *    |   |      |        |
     *    |_3_|      |_abcabc_|
     *   numStack    strStack
     *
     * 遍历结束，最终结果 str="abcabccdcdcdef"
     *
     * 1 ms, 93.25%
     * 34.5 MB, 48.58%
     */
    public static class Solution1 {

        public String decodeString(String s) {
            //初始化
            LinkedList<Integer> numStack = new LinkedList();
            LinkedList<String> strStack = new LinkedList();
            StringBuilder sb = new StringBuilder();
            int num = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= '0' && c <= '9') {
                    num = num * 10 + c - '0';
                } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    sb.append(c);
                } else if (c == '[') {
                    if (num > 0) numStack.push(num);
                    strStack.push(sb.toString());
                    sb = new StringBuilder();
                    num = 0;
                } else {
                    //c==']'
                    StringBuilder preSB = new StringBuilder().append(strStack.pop());
                    int times = numStack.pop();
                    for (int j = 0; j < times; j++) {
                        preSB.append(sb);
                    }
                    sb = preSB;
                }
            }
            return sb.toString();
        }
    }

    /**
     * 解法二
     * TODO：DFS
     */
    public static class Solution2 {

        public String decodeString(String s) {
            return "";
        }

    }

    public static void main(String[] args) {
//        String s = "12[ab]3[c]def";
//        String s = "12[ab3[c]]def";
        String s = "2[abc]3[d]4[e]5[f6[h]]";
        System.out.println(new Solution1().decodeString(s));
    }
}
