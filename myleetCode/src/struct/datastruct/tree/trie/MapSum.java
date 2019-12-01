package struct.datastruct.tree.trie;

import java.util.Map;
import java.util.TreeMap;

/**
 * 677.实现一个 MapSum 类里的两个方法，insert 和 sum。
 * <p>
 * 对于方法 insert，你将得到一对（字符串，整数）的键值对。字符串表示键，整数表示值。如果键已经存在，那么原来的键值对将被替代成新的键值对。
 * <p>
 * 对于方法 sum，你将得到一个表示前缀的字符串，你需要返回所有以该前缀开头的键的值的总和。
 */
public class MapSum {

    private TrieNode root;

    private static class TrieNode {
        int val = 0;
        TreeMap<Character, TrieNode> childMap;

        TrieNode() {
            this(0);
        }

        TrieNode(int val) {
            this.val = val;
            childMap = new TreeMap<>();
        }
    }

    /**
     * Initialize your data structure here.
     */
    public MapSum() {
        root = new TrieNode();
    }

    public void insert(String key, int val) {
        TrieNode node = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            TrieNode child = node.childMap.get(c);// 子节点Map
            if (child == null) {
                child = new TrieNode();
                node.childMap.put(c, child);
            }
            node = child;
        }
        node.val = val;// 最后一个节点保存val
    }

    public int sum(String prefix) {
        TrieNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            TrieNode child = node.childMap.get(c);
            if (child == null){
                return 0;
            }
            node = child;
        }
        return getSum(node);
    }

    private int getSum(TrieNode node){
        int sum = node.val;
        for (Map.Entry<Character,TrieNode> entry : node.childMap.entrySet()){
            sum += getSum(entry.getValue());
        }
        return sum;
    }
}
