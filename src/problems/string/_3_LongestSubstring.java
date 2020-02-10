package problems.string;

import java.util.HashMap;
import java.util.Map;

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
     * 执行用时：14 ms, 43.32%
     * 内存消耗：47.6 MB, 5.01%
     */
    public static class Solution1 {

        public int lengthOfLongestSubstring(String s) {
            if (s.isEmpty()) return 0;
            if (s.length() == 1) return 1;
            int longest = 0, i = 0, j;
            Map<Character, Integer> hashmap = new HashMap<>();
            for (j = 0; j < s.length(); j++) {
                if (!hashmap.containsKey(s.charAt(j))) {
                    hashmap.put(s.charAt(j), j);
                } else {
                    longest = Math.max(longest, (j-i));
                    int oldPos = hashmap.get(s.charAt(j));
                    while (i < oldPos) {
                        hashmap.remove(s.charAt(i));
                        i++;
                    }
                    i = oldPos + 1;
                    hashmap.put(s.charAt(j), j);
                }
            }
            longest = Math.max(longest, (j-i));
            return longest;
        }
    }

    /**
     * 执行用时：12 ms, 56.18%
     * 内存消耗：46 MB, 5.01%
     */
    public static class Solution2 {

        public int lengthOfLongestSubstring(String s) {
            if (s.isEmpty()) return 0;
            if (s.length() == 1) return 1;
            int longest = 0, i = 0, j;
            Map<Character, Integer> hashmap = new HashMap<>();
            for (j = 0; j < s.length(); j++) {
                char c = s.charAt(j);
                if (hashmap.containsKey(c)) {
                    longest = Math.max(longest, (j-i));
                    i = Math.max(hashmap.get(c) + 1, i);
                }
                hashmap.put(s.charAt(j), j);
            }
            longest = Math.max(longest, (j-i));
            return longest;
        }
    }

    public static void main(String[] args) {
//        String s = "abcabcbb";
//        String s = "bbbbbbbb";
//        String s = "pwwkew";
//        String s = "a";
//        String s = "";
        String s = "abc";
        System.out.println(new Solution1().lengthOfLongestSubstring(s));
        System.out.println(new Solution2().lengthOfLongestSubstring(s));
    }
}
