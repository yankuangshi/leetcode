package problems.offer;

import java.util.Arrays;

/**
 * 面试题05. 替换空格
 * 难度：简单
 *
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 * 示例 1：
 *
 * 输入：s = "We are happy."
 * 输出："We%20are%20happy."
 *
 * @author kyan
 * @date 2020/2/21
 */
public class Offer_5_ReplaceSpace {

    /**
     * double 100%
     */
    public static class Solution1 {
        public String replaceSpace(String s) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != ' ') {
                    sb.append(s.charAt(i));
                } else {
                    sb.append("%20");
                }
            }
            return sb.toString();
        }
    }

    /**
     * double 100%
     */
    public static class Solution2 {
        public String replaceSpace(String s) {
            int len = s.length();
            char[] chars = new char[len * 3];
            int i = 0;
            for (char ch : s.toCharArray()) {
                if (ch != ' ') {
                    chars[i++] = ch;
                } else {
                    chars[i++] = '%';
                    chars[i++] = '2';
                    chars[i++] = '0';
                }
            }
            return String.valueOf(chars, 0, i);
        }
    }

    public static void main(String[] args) {
        String s = "We are happy!";
        System.out.println(new Solution1().replaceSpace(s));
        System.out.println(new Solution2().replaceSpace(s));
    }
}
