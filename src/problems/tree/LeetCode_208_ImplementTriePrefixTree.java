package problems.tree;

import com.sun.org.apache.regexp.internal.RE;

/**
 * 208. 实现 Trie (前缀树)
 * 难度：中等
 * 题目描述
 * 实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。
 *
 * 示例:
 *
 * Trie trie = new Trie();
 *
 * trie.insert("apple");
 * trie.search("apple");   // 返回 true
 * trie.search("app");     // 返回 false
 * trie.startsWith("app"); // 返回 true
 * trie.insert("app");
 * trie.search("app");     // 返回 true
 * 说明:
 *
 * 你可以假设所有的输入都是由小写字母 a-z 构成的。
 * 保证所有输入均为非空字符串。
 *
 * @author kyan
 * @date 2020/2/22
 */
public class LeetCode_208_ImplementTriePrefixTree {

    /**
     * 参考极客时间的Trie树实现方法：https://time.geekbang.org/column/article/72414
     * 43 ms, 82.43%
     * 53.7 MB, 29.97%
     */
    public static class Trie {

        public static class TrieNode {
            char ch;
            TrieNode[] children = new TrieNode[26];
            boolean isEnd;

            public TrieNode(char ch) {
                this.ch = ch;
            }
        }

        private TrieNode root;

        /** Initialize your data structure here. */
        public Trie() {
            root = new TrieNode('/');
        }

        /** Inserts a word into the trie. */
        public void insert(String word) {
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

        public TrieNode searchPrefix(String prefix) {
            TrieNode node = root;
            for (int i = 0; i < prefix.length(); i++) {
                int index = prefix.charAt(i) - 'a';
                if (node.children[index] == null) return null;
                node = node.children[index];
            }
            return node;
        }

        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            TrieNode node = searchPrefix(word);
            return node != null && node.isEnd;
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            return searchPrefix(prefix) != null;
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple")); //ture
        System.out.println(trie.search("app")); //false
        System.out.println(trie.startsWith("app")); //true
        trie.insert("app");
        System.out.println(trie.search("app")); //true
    }
}
