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
            //words数组中单词个数
            int wordsCount = words.length;
            //每个单词长度
            int wordLength = words[0].length();
            //窗口长度，因为题目中说明了单词长度一样，因此窗口长度为固定长度 单词长度*单词个数
            int windowLength = wordLength * wordsCount;
            //如果字符串s的长度比窗口还小，则直接返回空结果
            if (s.length() < windowLength) return new ArrayList<>();
            List<Integer> res = new ArrayList<>();
            //两个哈希表，一个存放目标单词数量，一个存放窗口中单词数量
            Map<String, Integer> needs = new HashMap<>();
            Map<String, Integer> window = new HashMap<>();
            //先把单词放入HashMap中进行个数统计 例如 {"foo":1, "bar":1}
            for (String w : words) {
                needs.put(w, needs.getOrDefault(w, 0) + 1);
            }
            int l = 0, r = 0, i = 0;
            //左指针可移动至的最大下标为 s.length - windowLength
            while (l <= s.length() - windowLength) {
                i = 0;
                while (i < wordsCount) {
                    //外部遍历，l 指针按 +1 移动
                    r = l + i * wordLength;
                    String word = s.substring(r, r + wordLength);
                    //如果截断的 word 不在 needs 哈希表中，直接跳出
                    if (!needs.containsKey(word)) break;
                    //在当前 window 加入 word
                    window.put(word, window.getOrDefault(word, 0) + 1);
                    //如果当前 window 中该 word 存在个数大于 needs 中该 word 存在个数，则直接跳出
                    //例如：needs={"foo":1,"bar":1}, windows={"foo":2}
                    if (window.get(word) > needs.get(word)) break;
                    i++;
                }
                //当遍历完后，如果遍历次数正好等于 wordsCount，则说明该窗口内子串符合要求
                if (i == wordsCount) res.add(l);
                window.clear();
                l++;
            }
            return res;
        }
    }

    /**
     * 执行用时: 116 ms, 47.59%
     * 内存消耗: 50.1 MB, 5.08%
     */
    public static class Solution2 {

        public List<Integer> findSubstring(String s, String[] words) {
            if (s == null || s.isEmpty() || words == null || words.length == 0) return new ArrayList<>();
            //words数组中单词个数
            int wordsCount = words.length;
            //每个单词长度
            int wordLength = words[0].length();
            //窗口长度，因为题目中说明了单词长度一样，因此窗口长度为固定长度 单词长度*单词个数
            int windowLength = wordLength * wordsCount;
            //如果字符串s的长度比窗口还小，则直接返回空结果
            if (s.length() < windowLength) return new ArrayList<>();
            List<Integer> res = new ArrayList<>();
            //两个哈希表，一个存放目标单词数量，一个存放窗口中单词数量
            Map<String, Integer> needs = new HashMap<>();
            Map<String, Integer> window = new HashMap<>();
            //先把单词放入HashMap中进行个数统计 例如 {"foo":1, "bar":1}
            for (String w : words) {
                needs.put(w, needs.getOrDefault(w, 0) + 1);
            }
            int l = 0, r = 0, i = 0;
            //左指针可移动至的最大下标为 s.length - windowLength
            while (l <= s.length() - windowLength) {
                //外部遍历，l 指针按 +1 移动
                i = 0;
                window.putAll(needs);
                while (i < wordsCount) {
                    //内部遍历，r 指针从 l 开始，每次移动 wordLength 长度
                    r = l + i * wordLength;
                    String word = s.substring(r, r + wordLength);
                    //如果当前截断的 word 不在HashMap统计中，可以直接break
                    if (!window.containsKey(word)) break;
                    window.put(word, window.get(word) - 1);
                    if (window.get(word) == 0) {
                        window.remove(word);
                    }
                    i++;
                }
                //遍历完后统计 window 中元素个数，如果为0，则说明 window 中的单词都一一匹配
                if (window.size() == 0) res.add(l);
                window.clear();
                l++;
            }
            return res;
        }
    }

    public static void main(String[] args) {
        String s = "barfoothefoobarman";
        String[] words = {"foo", "bar"};
//        String s = "wordgoodgoodgoodbestword";
//        String[] words = {"word","good","best","word"};
        System.out.println(new Solution1().findSubstring(s, words));
        System.out.println(new Solution2().findSubstring(s, words));
    }
}
