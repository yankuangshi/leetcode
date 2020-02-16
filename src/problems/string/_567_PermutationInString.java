package problems.string;

/**
 * 567. 字符串的排列
 * 难度：中等
 *
 * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
 *
 * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
 *
 * 示例1:
 *
 * 输入: s1 = "ab" s2 = "eidbaooo"
 * 输出: True
 * 解释: s2 包含 s1 的排列之一 ("ba").
 *  
 * 示例2:
 *
 * 输入: s1= "ab" s2 = "eidboaoo"
 * 输出: False
 *  
 * 注意：
 * 输入的字符串只包含小写字母
 * 两个字符串的长度都在 [1, 10,000] 之间
 *
 * @author kyan
 * @date 2020/2/16
 */
public class _567_PermutationInString {

    /**
     * 解法一
     * 执行用时: 62 ms, 31.71%
     * 内存消耗: 47.3 MB, 5.02%
     */
    public static class Solution1 {

        public boolean checkInclusion(String s1, String s2) {
            if (s1.length() > s2.length()) return false;
            int[] needs = new int[26];
            for (char c : s1.toCharArray()) {
                needs[c-'a']++;
            }
            //窗口固定长度为 s1.length()，因此左指针 i 最多可以移至下标 s2.length - s1.length
            for (int i = 0; i <= s2.length() - s1.length(); i++) {
                int[] window = new int[26];
                int match = 0;
                for (int j = i; j < i + s1.length(); j++) {
                    char ch = s2.charAt(j);
                    if (needs[ch-'a'] == 0) break;
                    window[ch-'a']++;
                    if (window[ch-'a'] > needs[ch-'a']) break;
                    match++;
                }
                if (match == s1.length()) return true;
            }
            return false;
        }
    }

    /**
     * 解法二
     * 解法一优化
     * 8 ms, 83.05%
     * 43.6 MB, 5.02%
     */
    public static class Solution2 {

        public boolean checkInclusion(String s1, String s2) {
            if (s1.length() > s2.length()) return false;
            int[] needs = new int[26];
            for (char c : s1.toCharArray()) {
                needs[c-'a']++;
            }
            //窗口固定长度为 s1.length()，因此左指针 i 最多可以移至下标 s2.length - s1.length
            for (int i = 0; i <= s2.length() - s1.length(); i++) {
                int[] window = new int[26];
                for (int j = s1.length() - 1; j >= 0; j--) {
                    char ch = s2.charAt(j + i);
                    if (needs[ch-'a'] == 0) {
                        i += j;
                        break;
                    }
                    window[ch-'a']++;
                    if (window[ch-'a'] > needs[ch-'a']) {
                        i += j;
                        break;
                    }
                    if (j == 0) return true;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
//        String s1 = "ab";
//        String s2 = "eidbaooo";
        String s1 = "oa";
        String s2 = "eidboaoo";
        System.out.println(new Solution1().checkInclusion(s1, s2));
        System.out.println(new Solution2().checkInclusion(s1, s2));
    }
}
