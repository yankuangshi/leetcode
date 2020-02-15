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
     * 解法一
     * 滑动窗口的经典题目
     * 根据作者labuladong 在 https://leetcode-cn.com/problems/minimum-window-substring/solution/hua-dong-chuang-kou-suan-fa-tong-yong-si-xiang-by-/ 介绍的方法改写成java代码
     * 滑动窗口算法的思路是这样：
     * 1、我们在字符串 S 中使用双指针中的左右指针技巧，初始化 left = right = 0，把索引闭区间 [left, right] 称为一个「窗口」。
     * 2、我们先不断地增加 right 指针扩大窗口 [left, right]，直到窗口中的字符串符合要求（包含了 T 中的所有字符）。
     * 3、此时，我们停止增加 right，转而不断增加 left 指针缩小窗口 [left, right]，直到窗口中的字符串不再符合要求（不包含 T 中的所有字符了）。同时，每次增加 left，我们都要更新一轮结果。
     * 4、重复第 2 和第 3 步，直到 right 到达字符串 S 的尽头。
     *
     * 例如 S = "ADOBECODEBANC" T="ABC"，我们的利用needs和window来作为计数器，needs={A:1, B:1, C:1}
     *
     *  A D O B E C O D E B A N C
     * lr                               window={A:1} 此时不符合 needs，扩大窗口
     *
     *  A D O B E C O D E B A N C
     *  l         r                     window={A:1, B:1, C:1} 此时 window 窗口符合 needs，开始缩小窗口
     *
     *  A D O B E C O D E B A N C
     *  l         r                     把 A 从 window 中减去，window={A:0, B:1, C:1} 此时 window 不符合 needs，又重新开始扩大窗口
     *
     *  A D O B E C O D E B A N C
     *    l                 r           window={A:1, B:2, C:1} 此时 window 重新符合 needs，开始缩小窗口
     *
     *  A D O B E C O D E B A N C
     *        l             r           把 B 从 window 中减去，window={A:1, B:1, C:1} 此时 window 还是符合 needs，继续缩小窗口
     *
     *  A D O B E C O D E B A N C
     *            l         r           把 C 从 window 中减去，window={A:1, B:1, C:0} 此时 window 不符合 needs，重新开始扩大窗口
     *
     *  A D O B E C O D E B A N C
     *              l           r       window={A:1, B:1, C:1} 此时 window 符合 needs，继续缩小窗口
     *
     *  A D O B E C O D E B A N C
     *                    l     r       把 B 从 window 中减去，window={A:1, B:0, C:1} 此时 window 不符合 needs，并且此时 l 到 r 的字符串最短 BANC
     *
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
     * 解法二
     * 在解法一基础上优化，用数组 int[128] 代替 HashMap，优化方法同《3.无重复字符的最长子串》中的解法三
     *
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
                needIndex[c]++;
            }
            while (r < len) {
                char ch = s.charAt(r);
                if (needIndex[ch] > 0) {
                    windowIndex[ch]++;
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
                        windowIndex[ch]--;
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
