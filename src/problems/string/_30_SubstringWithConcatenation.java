package problems.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 30. 串联所有单词的子串
 * 难度：困难
 *
 * 给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
 *
 * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。
 *
 * 示例 1：
 *
 * 输入：
 *   s = "barfoothefoobarman",
 *   words = ["foo","bar"]
 * 输出：[0,9]
 * 解释：
 * 从索引 0 和 9 开始的子串分别是 "barfoo" 和 "foobar" 。
 * 输出的顺序不重要, [9,0] 也是有效答案。
 * 示例 2：
 *
 * 输入：
 *   s = "wordgoodgoodgoodbestword",
 *   words = ["word","good","best","word"]
 * 输出：[]
 *
 * @author kyan
 * @date 2020/2/15
 */
public class _30_SubstringWithConcatenation {

    /**
     * 执行用时：106 ms，53.12%
     * 内存消耗：51 MB，5.02%
     */
    public static class Solution1 {

        public List<Integer> findSubstring(String s, String[] words) {
            if (s == null || s.isEmpty() || words == null || words.length == 0) return new ArrayList<>();
            int wordsCount = words.length;
            int wordLength = words[0].length();
            int windowLength = wordLength * wordsCount;
            if (s.length() < windowLength) return new ArrayList<>();
            int l = 0;
            List<Integer> res = new ArrayList<>();
            Map<String, Integer> needs = new HashMap<>();
            for (String w : words) {
                needs.put(w, needs.getOrDefault(w, 0) + 1);
            }
            while (l + windowLength <= s.length()) {
                Map<String, Integer> window = new HashMap<>();
                int count = 0;
                while (count < wordsCount) {
                    int r = l + count * wordLength;
                    String word = s.substring(r, r + wordLength);
                    if (needs.containsKey(word)) {
                        window.put(word, window.getOrDefault(word, 0) + 1);
                        if (window.get(word) > needs.get(word)) {
                            break;
                        }
                    } else {
                        break;
                    }
                    count++;
                }
                if (count == wordsCount) res.add(l);
                l++;
            }
            return res;
        }
    }

    public static void main(String[] args) {
//        String s = "foobarfoo";
//        String[] words = {"foo"};
        String s = "wordgoodgoodgoodbestword";
        String[] words = {"good", "good"};
        System.out.println(new Solution1().findSubstring(s, words));
    }
}
