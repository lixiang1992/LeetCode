package struct.datastruct.tree.trie;

import java.util.Arrays;

/**
 * 820.单词的压缩编码
 * 给定一个单词列表，我们将这个列表编码成一个索引字符串 S 与一个索引列表 A。
 *
 * 例如，如果这个列表是 ["time", "me", "bell"]，我们就可以将其表示为 S = "time#bell#" 和 indexes = [0, 2, 5]。
 *
 * 对于每一个索引，我们可以通过从字符串 S 中索引的位置开始读取字符串，直到 "#" 结束，来恢复我们之前的单词列表。
 *
 * 那么成功对给定单词列表进行编码的最小字符串长度是多少呢？
 *
 */
public class WordEncoding_820 {

    // 字典树思路，但是这题其实是“后缀”树，是找后缀，要逆序存储
    private static class TrieNode {
        char data;
        TrieNode[] children = new TrieNode[26];

        TrieNode(char data) {
            this.data = data;
        }
    }

    private TrieNode root = new TrieNode('/');

    public int minimumLengthEncoding(String[] words) {
        // 单词按长度从大到小排序，先插入大的，再插入小的
        Arrays.sort(words, (o1, o2) -> o2.length() - o1.length());
        int res = 0;// 长度
        // 遍历词组，进行插入，返回扩大的长度
        for (String word : words) {
            res += insertWord(word);
        }

        return res;
    }

    private int insertWord(String word) {
        TrieNode node = root;
        boolean isAdd = false;
        for (int i = word.length() - 1; i >= 0; i--) {
            char data = word.charAt(i);
            int index = data - 'a';
            if (node.children[index] == null){
                // 不存在，字典索引就要新增
                node.children[index] = new TrieNode(data);
                isAdd = true;
            }
            node = node.children[index];
        }
        return isAdd ? word.length() : 0;
    }
}
