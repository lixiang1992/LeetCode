package struct.pub.tree;

/**
 * trie(字典)树的节点
 */
public class TrieNode {
    public char data;// 字符
    public TrieNode[] children = new TrieNode[26];// 子元素

    public TrieNode(){

    }

    public TrieNode(char data) {
        this.data = data;
    }
}
