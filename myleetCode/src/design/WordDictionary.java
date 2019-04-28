package design;

/**
 * 211. 添加与搜索单词 - 数据结构设计
 * 设计一个支持以下两种操作的数据结构：
 * <p>
 * void addWord(word)
 * bool search(word)
 * search(word) 可以搜索文字或正则表达式字符串，字符串只包含字母 . 或 a-z 。 . 可以表示任何一个字母。
 */
public class WordDictionary {

    private TrieNode root;
    private static final char MINLIMIT = 'a';// 最小字符
    private static final char ALLMATCH = '.';// 通配符

    /**
     * Initialize your data structure here.
     */
    public WordDictionary() {
        root = new TrieNode('/');
    }

    /**
     * Adds a word into the data structure.
     */
    public void addWord(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - MINLIMIT;
            if (node.children[index] == null) {
                node.children[index] = new TrieNode(c);
                node.children[index].parent = node;
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }

    /**
     * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
     */
    public boolean search(String word) {
        return findNode(root, word, 0);
    }

    private boolean findNode(TrieNode node, String word, int i) {
        for (; node != null && i < word.length(); i++) {
            char c = word.charAt(i);
            if (ALLMATCH == c) {
                for (TrieNode child : node.children) {
                    if (child == null) {
                        continue;
                    }
                    if (findNode(child, word, i + 1)) {
                        return true;
                    }
                }
                return false;
            } else {
                node = matchWord(node, c);
            }
        }
        return node != null && node.isEnd;
    }

    private TrieNode matchWord(TrieNode node, char c) {
        return node.children[c - MINLIMIT];
    }

    // 内部节点
    private static class TrieNode {
        char word;// 单字
        TrieNode parent;// 父节点
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;// 单词的最后一个节点

        TrieNode(char word) {
            this.word = word;
        }
    }


}
