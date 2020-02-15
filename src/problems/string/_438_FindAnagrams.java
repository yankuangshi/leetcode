package problems.string;

import java.util.ArrayList;
import java.util.List;

/**
 * 438. 找到字符串中所有字母异位词
 * 难度：中等
 *
 * 题目描述：
 *
 * 给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
 *
 * 字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。
 *
 * 说明：
 *
 * 字母异位词指字母相同，但排列不同的字符串。
 * 不考虑答案输出的顺序。
 * 示例 1:
 *
 * 输入:
 * s: "cbaebabacd" p: "abc"
 *
 * 输出:
 * [0, 6]
 *
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的字母异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的字母异位词。
 *  示例 2:
 *
 * 输入:
 * s: "abab" p: "ab"
 *
 * 输出:
 * [0, 1, 2]
 *
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的字母异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的字母异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的字母异位词。
 *
 * @author kyan
 * @date 2020/2/15
 */
public class _438_FindAnagrams {

    /**
     * 解法一
     * 滑动窗口思想
     * 执行用时：14 ms, 60.66%
     * 内存消耗：46.1 MB, 5.02%
     */
    public static class Solution1 {

        public List<Integer> findAnagrams(String s, String p) {
            List<Integer> res = new ArrayList<>();
            int l = 0, r = 0, sLength = s.length(), pLength = p.length(), match = 0;
            if (s == null || sLength < pLength) return res;
            int[] needs = new int[26];
            int[] window = new int[26];
            for (char c : p.toCharArray()) {
                needs[c-'a']++;
            }
            while (r < sLength) {
                char ch = s.charAt(r);
                if (needs[ch-'a'] > 0) {
                    window[ch-'a']++;
                    if (window[ch-'a'] <= needs[ch-'a']) {
                        match++;
                    }
                }
                while (match == pLength) {
                    //解题思路同题76.最小覆盖子串，只需要把结果处理这边更改一下即可
                    if (r - l + 1 == pLength) {
                        res.add(l);
                    }
                    ch = s.charAt(l);
                    if (needs[ch-'a'] > 0) {
                        window[ch-'a']--;
                        if (window[ch-'a'] < needs[ch-'a']) {
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
//        String S = "cbaebabacd";
//        String P = "abc";
        String S = "abab";
        String P = "ab";
        System.out.println(new Solution1().findAnagrams(S, P));
    }
}
