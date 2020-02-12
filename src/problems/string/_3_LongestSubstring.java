package problems.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 3. 无重复字符的最长子串
 * 难度：中等
 *
 * 题目描述
 *
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * @author kyan
 * @date 2020/2/10
 */
public class _3_LongestSubstring {

    /**
     *
     * 执行用时：21 ms, 26.74%
     * 内存消耗：46.8 MB, 5.02%
     */
    public static class Solution1 {

        public int lengthOfLongestSubstring(String s) {
            if (s.isEmpty()) return 0;
            if (s.length() == 1) return 1;
            int ans = 0, left = 0, right = 0;
            int len = s.length();
            Set<Character> set = new HashSet<>();
            while (left < len && right < len) {
                Character c = s.charAt(right);
                while (set.contains(c)) {
                    set.remove(s.charAt(left++));
                }
                set.add(c);
                ans = Math.max(ans, right - left + 1);
                right++;
            }
            return ans;
        }
    }

    /**
     * 执行用时：13 ms, 49.00%
     * 内存消耗：46.7 MB, 5.02%
     */
    public static class Solution2 {

        public int lengthOfLongestSubstring(String s) {
            if (s.isEmpty()) return 0;
            if (s.length() == 1) return 1;
            int ans = 0, left = 0, right = 0;
            int len = s.length();
            Map<Character, Integer> map = new HashMap<>();
            while (left < len && right < len) {
                Character c = s.charAt(right);
                if (map.containsKey(c)) {
                    left = Math.max(left, map.get(c) + 1);
                }
                map.put(c, right);
                ans = Math.max(ans, right - left + 1);
                right++;
            }
            return ans;
        }
    }

    /**
     * 执行用时：4m ms, 90.84%
     * 内存消耗：44.8 MB, 5.02%
     */
    public static class Solution3 {

        public int lengthOfLongestSubstring(String s) {
            if (s.isEmpty()) return 0;
            if (s.length() == 1) return 1;
            int ans = 0, left = 0, right = 0, len = s.length();
            int[] index = new int[128];
            while (right < len) {
                left = Math.max(left, index[s.charAt(right)]);
                ans = Math.max(ans, right - left + 1);
                index[s.charAt(right)] = right + 1;
                right++;
            }
            return ans;
        }
    }

    public static void main(String[] args) {
//        String s = "abccba";
//        String s = "bbbbbbbb";
//        String s = "pwwkew";
//        String s = "a";
//        String s = "";
        String s = "abc";
        System.out.println(new Solution1().lengthOfLongestSubstring(s));
        System.out.println(new Solution2().lengthOfLongestSubstring(s));
        System.out.println(new Solution3().lengthOfLongestSubstring(s));
    }
}
