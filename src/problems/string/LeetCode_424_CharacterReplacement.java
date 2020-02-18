package problems.string;

/**
 * 424. 替换后的最长重复字符
 * 难度：中等
 *
 * 题目描述
 *
 * 给你一个仅由大写英文字母组成的字符串，你可以将任意位置上的字符替换成另外的字符，总共可最多替换 k 次。在执行上述操作后，找到包含重复字母的最长子串的长度。
 * 注意:
 * 字符串长度 和 k 不会超过 104。
 *
 * 示例 1:
 * 输入:
 * s = "ABAB", k = 2
 * 输出:
 * 4
 * 解释:
 * 用两个'A'替换为两个'B',反之亦然。
 *
 * 示例 2:
 * 输入:
 * s = "AABABBA", k = 1
 * 输出:
 * 4
 * 解释:
 * 将中间的一个'A'替换为'B',字符串变为 "AABBBBA"。
 * 子串 "BBBB" 有最长重复字母, 答案为 4。
 *
 * @author kyan
 * @date 2020/2/17
 */
public class LeetCode_424_CharacterReplacement {

    /**
     * 解法一
     * 滑动窗口思想
     * 利用双指针l、r表示窗口，利用数组 window = new int[128] 来存放窗口中出现字符的次数
     * 1、右移指针 r 扩大窗口
     * 2、将指针 r 指向的字符计入统计数组 window，同时更新窗口中重复次数最多的字符次数 maxCount
     * 3、如果 (r-l+1) - maxCount > k，则说明该窗口不符合要求，（r-l+1）为窗口长度
     *  从统计数组 window 中去除指针 l 指向的字符
     *  右移指针 l，缩小窗口
     *  循环判读直到 (r-l+1) - maxCount <= k
     * 4、更新最长字符串长度
     *
     */
    public static class Solution1 {
        public int characterReplacement(String s, int k) {
            if (s == null || s.isEmpty()) return 0;
            int[] window = new int[128];
            int l = 0, r = 0, maxCount = 0, len = s.length(), maxLen = 0;
            while (r < len) {
                window[s.charAt(r)]++;
                maxCount = Math.max(maxCount, window[s.charAt(r)]);
                while (r - l + 1 - maxCount > k) {
                    window[s.charAt(l)]--;
                    l++;
                }
                maxLen = Math.max(maxLen, r -l + 1);
                r++;
            }
            return maxLen;
        }
    }

    public static void main(String[] args) {
        String s = "AABCAABC";
        int k = 1;
        System.out.println(new Solution1().characterReplacement(s, k));
    }
}
