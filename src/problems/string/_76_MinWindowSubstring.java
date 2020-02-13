package problems.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 76. 最小覆盖子串
 * 难度：困难
 *
 * 题目描述：
 *
 * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
 *
 * 示例：
 *
 * 输入: S = "ADOBECODEBANC", T = "ABC"
 * 输出: "BANC"
 * 说明：
 *
 * 如果 S 中不存这样的子串，则返回空字符串 ""。
 * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
 *
 * @author kyan
 * @date 2020/2/13
 */
public class _76_MinWindowSubstring {

    /**
     * 执行用时：31 ms, 47.55%
     * 内存消耗：47.6 MB, 5.11%
     */
    public static class Solution1 {

        public String minWindow(String s, String t) {
            Map<Character, Integer> needMap = new HashMap<>();
            Map<Character, Integer> windowMap = new HashMap<>();
            int len = s.length(), minLen = len + 1;
            int left = 0, right = 0, match = 0;
            String res = "";
            for (Character c : t.toCharArray()) {
                needMap.put(c, needMap.getOrDefault(c, 0) + 1);
            }
            while (left < len && right < len) {
                Character charToAdd = s.charAt(right);
                if (needMap.containsKey(charToAdd)) {
                    windowMap.put(charToAdd, windowMap.getOrDefault(charToAdd, 0) + 1);
//                    if (windowMap.get(charToAdd) == needMap.get(charToAdd)) {
                    //以上 == 判断在长字符串情况下报错
                    if (windowMap.get(charToAdd).compareTo(needMap.get(charToAdd)) == 0) {
                        match++;
                    }
                }
                while (match == needMap.size()) {
                    if (right - left + 1 < minLen) {
                        minLen = right - left + 1;
                        res = s.substring(left, right + 1);
                    }
                    Character charToRemove = s.charAt(left);
                    if (needMap.containsKey(charToRemove)) {
                        windowMap.put(charToRemove, windowMap.get(charToRemove) - 1);
                        if (windowMap.get(charToRemove) < needMap.get(charToRemove)) {
                            match--;
                        }
                    }
                    left++;
                }
                right++;
            }
            return res;
        }
    }

    public static void main(String[] args) {
//        String S = "cabwefgewcwaefgcf";
//        String T = "cae";
        String S = "a";
        String T = "aa";
        System.out.println(new Solution1().minWindow(S, T));
    }
}
