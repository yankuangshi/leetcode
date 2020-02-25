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
     * 3 ms, 11.96%
     * 38.5 MB, 5.11%
     */
    public static class Solution {

        private List<String> res = new ArrayList<>();
        private static Map<Character, List<Character>> digit2Letter;

        static {
            digit2Letter = new HashMap<>();
            digit2Letter.put('1', new ArrayList<>());
            digit2Letter.put('2', Arrays.asList('a', 'b', 'c'));
            digit2Letter.put('3', Arrays.asList('d', 'e', 'f'));
            digit2Letter.put('4', Arrays.asList('g', 'h', 'i'));
            digit2Letter.put('5', Arrays.asList('j', 'k', 'l'));
            digit2Letter.put('6', Arrays.asList('m', 'n', 'o'));
            digit2Letter.put('7', Arrays.asList('p', 'q', 'r', 's'));
            digit2Letter.put('8', Arrays.asList('t', 'u', 'v'));
            digit2Letter.put('9', Arrays.asList('w', 'x', 'y', 'z'));
        }

        public List<String> letterCombinations(String digits) {
            if (digits.isEmpty() || (digits.length() == 1 && digits.equals("1"))) return res;
            //clean 1
            digits = digits.replaceAll("1", "");
            backtrack(digits, 0, new ArrayList<Character>());
            return res;
        }

        private void backtrack(String digits, int curIndex, List<Character> comb) {
            if (comb.size() == digits.length()) {
                StringBuilder builder = new StringBuilder();
                for (Character character : comb) {
                    builder.append(character);
                }
                res.add(builder.toString());
                return;
            }
//            if (digits.charAt(curIndex) == '1') return;
            List<Character> chars = digit2Letter.get(digits.charAt(curIndex));
            for (int i = 0; i < chars.size(); i++) {
                comb.add(chars.get(i));
                backtrack(digits, curIndex+1, comb);
                comb.remove(comb.size()-1);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().letterCombinations("22"));
    }
}
