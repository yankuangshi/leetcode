package problems.tree;

/**
 * 211. 添加与搜索单词 - 数据结构设计
 * 难度：中等
 * 题目描述
 * 设计一个支持以下两种操作的数据结构：
 *
 * void addWord(word)
 * bool search(word)
 * search(word) 可以搜索文字或正则表达式字符串，字符串只包含字母 . 或 a-z 。 . 可以表示任何一个字母。
 *
 * 示例:
 *
 * addWord("bad")
 * addWord("dad")
 * addWord("mad")
 * search("pad") -> false
 * search("bad") -> true
 * search(".ad") -> true
 * search("b..") -> true
 * 说明:
 *
 * 你可以假设所有单词都是由小写字母 a-z 组成的。
 *
 * @author kyan
 * @date 2020/2/22
 */
public class LeetCode_211_WordDictionary {

    /**
     * 62 ms, 57.53%
     * 53.7 MB, 94.24%
     */
    public static class WordDictionary {

        public class TrieNode {
            char data;
            TrieNode[] children = new TrieNode[26];
            boolean isEnd;

            public TrieNode(char data) {
                this.data = data;
            }
        }

        private TrieNode root;

        /** Initialize your data structure here. */
        public WordDictionary() {
            root = new TrieNode('/');
        }

        /** Adds a word into the data structure. */
        public void addWord(String word) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode(word.charAt(i));
                }
                node = node.children[index];
            }
            node.isEnd = true;
        }

        /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
        public boolean search(String word) {
            return searchHelper(word, root);
        }

        private boolean searchHelper(String word, TrieNode root) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == '.') {
                    //如果遇到"."，则遍历TrieNode下的所有子节点（要求子节点不为空），并且递归查询剩余的字符串 word.subString(i+1)
                    for (TrieNode child : root.children) {
                        if (child != null && searchHelper(word.substring(i+1), child)) {
                            return true;
                        }
                    }
                    //如果所有的子节点中没有查找到，则返回false
                    return false;
                } else {
                    int index = word.charAt(i) - 'a';
                    if (root.children[index] == null) return false;
                    root = root.children[index];
                }
            }
            return root.isEnd;
        }
    }

    public static void main(String[] args) {
        WordDictionary wordDictionary = new WordDictionary();
//        wordDictionary.addWord("bad");
//        wordDictionary.addWord("dad");
//        wordDictionary.addWord("mad");
//        System.out.println(wordDictionary.search(".a"));
//        System.out.println(wordDictionary.search("b.."));
        wordDictionary.addWord("at");
        wordDictionary.addWord("and");
        wordDictionary.addWord("an");
        wordDictionary.addWord("add");
        System.out.println(wordDictionary.search("a"));
        System.out.println(wordDictionary.search(".at"));
        wordDictionary.addWord("bat");
        System.out.println(wordDictionary.search(".at"));
        System.out.println(wordDictionary.search(".an"));
        System.out.println(wordDictionary.search("a.d"));
        System.out.println(wordDictionary.search("b."));
        System.out.println(wordDictionary.search("a.d"));
        System.out.println(wordDictionary.search("."));
    }

}
