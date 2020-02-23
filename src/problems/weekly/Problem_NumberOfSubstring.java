package problems.weekly;

import java.util.HashMap;
import java.util.Map;

/**
 * BiWeekly Contest 20th
 * 5325. 包含所有三种字符的子字符串数目 https://leetcode-cn.com/problems/number-of-substrings-containing-all-three-characters/
 * 难度：中等
 *
 * 题目描述
 * 给你一个字符串 s ，它只包含三种字符 a, b 和 c 。
 * 请你返回 a，b 和 c 都 至少 出现过一次的子字符串数目。
 * 示例 1：
 * 输入：s = "abcabc"
 * 输出：10
 * 解释：包含 a，b 和 c 各至少一次的子字符串为 "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" 和 "abc" (相同字符串算多次)。
 *
 * 示例 2：
 * 输入：s = "aaacb"
 * 输出：3
 * 解释：包含 a，b 和 c 各至少一次的子字符串为 "aaacb", "aacb" 和 "acb" 。
 *
 * 示例 3：
 * 输入：s = "abc"
 * 输出：1
 *
 * @author kyan
 * @date 2020/2/22
 */
public class Problem_NumberOfSubstring {

    /**
     * 滑动窗口 50ms
     */
    public static class Solution {
        public int numberOfSubstrings(String s) {
            Map<Character, Integer> window = new HashMap<>(3);
            int l = 0, r = 0, match = 0, res = 0;
            while (l < s.length() - 2 && r < s.length()) {
                char ch = s.charAt(r);
                window.put(ch, window.getOrDefault(ch, 0) + 1);
                if (window.get(ch) == 1) {
                    match++;
                }
                while (match == 3) {
                    res += s.length() - r;
                    ch = s.charAt(l);
                    window.put(ch, window.get(ch) - 1);
                    if (window.getOrDefault(ch, 0) == 0) {
                        match--;
                    }
                    l++;
                }
                r++;
            }
            return res;
        }
    }

    public static void main(String[] args) {
        String s = "abc";
        System.out.println(new Solution().numberOfSubstrings(s));
    }
}
