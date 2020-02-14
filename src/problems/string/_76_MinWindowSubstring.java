package problems.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 76. 最小覆盖子串
 * 难度：困难
 * <p>
 * 题目描述：
 * <p>
 * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
 * <p>
 * 示例：
 * <p>
 * 输入: S = "ADOBECODEBANC", T = "ABC"
 * 输出: "BANC"
 * 说明：
 * <p>
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
            int l = 0, r = 0, match = 0;
            String res = "";
            for (char c : t.toCharArray()) {
                needMap.put(c, needMap.getOrDefault(c, 0) + 1);
            }
            while (l < len && r < len) {
                char ch = s.charAt(r);
                if (needMap.containsKey(ch)) {
                    windowMap.put(ch, windowMap.getOrDefault(ch, 0) + 1);
//                    if (windowMap.get(charToAdd) == needMap.get(charToAdd)) {
                    //以上 == 判断在长字符串情况下报错
                    if (windowMap.get(ch).compareTo(needMap.get(ch)) == 0) {
                        match++;
                    }
                }
                while (match == needMap.size()) {
                    if (r - l + 1 < minLen) {
                        minLen = r - l + 1;
                        res = s.substring(l, r + 1);
                    }
                    ch = s.charAt(l);
                    if (needMap.containsKey(ch)) {
                        windowMap.put(ch, windowMap.get(ch) - 1);
                        if (windowMap.get(ch) < needMap.get(ch)) {
                            match--;
                        }
                    }
                    l++;
                }
                r++;
            }
            return res;
        }
    }

    /**
     * 执行用时：7 ms, 76.60%
     * 内存消耗：45.5 MB, 5.02%
     */
    public static class Solution2 {

        public String minWindow(String s, String t) {
            int[] needIndex = new int[128];
            int[] windowIndex = new int[128];
            int len = s.length(), minLen = len + 1;
            int l = 0, r = 0, match = 0;
            String res = "";
            for (char c : t.toCharArray()) {
                needIndex[c] = needIndex[c] + 1;
            }
            while (r < len) {
                char ch = s.charAt(r);
                if (needIndex[ch] > 0) {
                    windowIndex[ch] = windowIndex[ch] + 1;
                    if (windowIndex[ch] <= needIndex[ch]) {
                        match++;
                    }
                }
                while (match == t.length()) {
                    if (r - l + 1 < minLen) {
                        minLen = r - l + 1;
                        res = s.substring(l, r + 1);
                    }
                    ch = s.charAt(l);
                    if (needIndex[ch] > 0) {
                        windowIndex[ch] = windowIndex[ch] - 1;
                        if (windowIndex[ch] < needIndex[ch]) {
                            match--;
                        }
                    }
                    l++;
                }
                r++;
            }
            return res;
        }
    }

    public static void main(String[] args) {
//        String S = "cabwefgewcwaefgcf";
//        String T = "cae";
//        String S = "a";
//        String T = "aa";
        String S = "ADOBECODEBANC";
        String T = "AABC";
        System.out.println(new Solution1().minWindow(S, T));
        System.out.println(new Solution2().minWindow(S, T));
    }
}
