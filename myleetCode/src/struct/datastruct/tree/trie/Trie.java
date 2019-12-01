package struct.datastruct.tree.trie;


/**
 * 208.实现前缀树
 */
public class Trie {

    private TrieNode root;
    private static final char MINLIMIT = 'a';

    public Trie() {
        this.root = new TrieNode('/');
    }

    private static class TrieNode {
        char data;// 字符
        TrieNode[] children = new TrieNode[26];// 子元素
        boolean isEndChar = false;

        TrieNode() {
        }

        TrieNode(char data) {
            this.data = data;
        }
    }

    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char one = word.charAt(i);
            int index = one - MINLIMIT;// 通过ASCll码来确定下标
            // 当前节点对应子节点的index没有值，直接加在这里
            if (node.children[index] == null) {
                node.children[index] = new TrieNode(one);
            }
            node = node.children[index];// 继续往子节点前进
        }
        node.isEndChar = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char one = word.charAt(i);
            int index = one - MINLIMIT;
            // 当前节点对应子节点的index没有值，直接加在这里
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];// 继续往子节点前进
        }
        return node.isEndChar;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            char one = prefix.charAt(i);
            int index = one - MINLIMIT;
            // 当前节点对应子节点的index没有值，直接加在这里
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];// 继续往子节点前进
        }
        return true;
    }

}
