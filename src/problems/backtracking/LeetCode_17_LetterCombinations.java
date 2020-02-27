package problems.backtracking;


import java.util.*;

/**
 * 17. 电话号码的字母组合
 * 难度：中等
 * 题目描述
 *
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 *
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * 示例:
 * 输入："23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 *
 * @author kyan
 * @date 2020/2/24
 */
public class LeetCode_17_LetterCombinations {

    /**
     * 解法一和解法二类似，只是一个用hashmap存储数字到字母映射，一个用数组存储数字到字母映射
     * 7 ms, 9.4%
     * 38.5 MB, 5.11%
     */
    public static class Solution1 {

        private static Map<Character, String> digit2Letters = new HashMap<Character, String>() {
            {
                put('1', "");
                put('2', "abc");
                put('3', "def");
                put('4', "ghi");
                put('5', "jkl");
                put('6', "mno");
                put('7', "pqrs");
                put('8', "tuv");
                put('9', "wxyz");
            }
        };

        public List<String> letterCombinations(String digits) {
            List<String> res = new ArrayList<>();
            if (digits.isEmpty() || (digits.length() == 1 && digits.equals("1"))) return res;
            backtrack(digits, 0, "", res);
            return res;
        }

        private void backtrack(String digits, int index, String s, List<String> res) {
            if (index == digits.length()) {
                res.add(s);
                return;
            }
            String letters = digit2Letters.get(digits.charAt(index));
            for (int i = 0; i < letters.length(); i++) {
                backtrack(digits, index + 1, s + letters.charAt(i), res);
            }
        }
    }

    public static class Solution2 {

        private String[] digit2Letters = {
            "",     //按键1
            "abc",  //按键2
            "def",  //按键3
            "ghi",  //按键4
            "jkl",  //按键5
            "mno",  //按键6
            "pqrs", //按键7
            "tuv",  //按键8
            "wxyz"  //按键9
        };

        public List<String> letterCombinations(String digits) {
            List<String> res = new ArrayList<>();
            if (digits.isEmpty() || (digits.length() == 1 && digits.equals("1"))) return res;
            backtrack(digits, 0, "", res);
            return res;
        }

        private void backtrack(String digits, int index, String s, List<String> res) {
            if (index == digits.length()) {
                res.add(s);
                return;
            }
            String letters = digit2Letters[digits.charAt(index) - '1'];
            for (int i = 0; i < letters.length(); i++) {
                backtrack(digits, index + 1, s + letters.charAt(i), res);
            }
        }
    }

    /**
     * 解法一和二之所以慢是因为字符串拼接使用了String所致，解法三中改为StringBuffer，效率可以大大提升
     * 1 ms, 92.43%
     * 37.9 MB, 5.28%
     */
    public static class Solution3 {

        private String[] digit2Letters = {
                "",     //按键1
                "abc",  //按键2
                "def",  //按键3
                "ghi",  //按键4
                "jkl",  //按键5
                "mno",  //按键6
                "pqrs", //按键7
                "tuv",  //按键8
                "wxyz"  //按键9
        };

        public List<String> letterCombinations(String digits) {
            List<String> res = new ArrayList<>();
            if (digits.isEmpty() || (digits.length() == 1 && digits.equals("1"))) return res;
            backtrack(digits, 0, new StringBuffer(), res);
            return res;
        }

        private void backtrack(String digits, int index, StringBuffer buffer, List<String> res) {
            if (index == digits.length()) {
                res.add(buffer.toString());
                return;
            }
            String letters = digit2Letters[digits.charAt(index) - '1'];
            for (int i = 0; i < letters.length(); i++) {
                backtrack(digits, index + 1, buffer.append(letters.charAt(i)), res);
                buffer.deleteCharAt(buffer.length() - 1);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution1().letterCombinations("23"));
        System.out.println(new Solution2().letterCombinations("23"));
        System.out.println(new Solution3().letterCombinations("23"));
    }
}
