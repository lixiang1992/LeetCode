package struct.datastruct.tree.trie;

/**
 * 676.实现一个魔法字典
 * 实现一个带有buildDict, 以及 search方法的魔法字典。
 * <p>
 * 对于buildDict方法，你将被给定一串不重复的单词来构建一个字典。
 * <p>
 * 对于search方法，你将被给定一个单词，并且判定能否只将这个单词中一个字母换成另一个字母，使得所形成的新单词存在于你构建的字典中。
 * <p>
 * 思路：
 * 1.word所有内容都参与到匹配中来（word中没有多余的字符）
 * 2.从root出发的某条路径所表示的单词参与到匹配中来（搜索到某个root处，root.isword == true）
 * 3.匹配过程使用过1次regex
 */
public class MagicDictionary {


    /**
     * 字典树节点结构类
     */
    private static class TrieNode {
        char data;
        TrieNode[] children = new TrieNode[26];
        boolean isWord = false;

        TrieNode(char data){
            this.data = data;
        }
    }

    private static final char MIN_LIMIT = 'a';// 字符最小值
    private TrieNode root;// 根节点

    public MagicDictionary() {
        root = new TrieNode('/');// 构造根节点
    }

    /**
     * 构建字典
     * @param dict
     */
    public void buildDict(String[] dict) {
        for(String word : dict){
            TrieNode node = root;
            for (int i=0;i<word.length();i++){
                char c = word.charAt(i);
                int index = c - 'a';// 子节点下标
                if (node.children[index] == null){
                    node.children[index] = new TrieNode(c);
                }
                node = node.children[index];
            }
            node.isWord = true;
        }
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (int i = 0;i<word.length();i++){
            char c = word.charAt(i);
            int index = c - MIN_LIMIT;
            if (node.children[index] == null){
                // 这个节点找不到了，子节点进行替换的二次查找
                return secondSearch(word,i,node);
            }else {
                // 正常的单词查找
                boolean instead = secondSearchInsteadNode(word,i,node,node.children[index]);
                if (instead){// 替换了可以找到，满足条件，直接返回true
                    return true;
                }
                // 替换当前子节点没法找到单词，继续往子节点递归
                node = node.children[index];
            }
        }
        return false;
    }

    /**
     * 一次替换的二次查找
     * @param word
     * @param node
     * @return
     */
    private boolean secondSearch(String word, int changeIndex,TrieNode node){
        // 从node的子节点找一个替换掉
        return secondSearchInsteadNode(word,changeIndex,node,null);
    }

    private boolean secondSearchInsteadNode(String word, int changeIndex,TrieNode node,TrieNode current){
        // 从node的子节点找一个替换掉
        boolean changeChar = false;
        for (TrieNode child: node.children){
            // current == null表示当前子节点匹配不到
            if (child == null || (current != null && current == child)){
                continue;
            }
            for (int i = changeIndex+1;i < word.length();i++){
                char c = word.charAt(i);
                int index = c - MIN_LIMIT;
                // 第二次出现了匹配不上，表示这个child不行
                if (child.children[index] == null){
                    changeChar = true;
                    break;
                }
                child = child.children[index];// 继续往子节点递归
            }
            if (child.isWord && !changeChar){// 最后的child是单词结尾，而且没有改变单词，则表示成功，直接返回
                return true;
            }else {
                changeChar = false;// 字符改变的标志位还原
            }
        }
        return false;// 遍历完了都没找到，返回false
    }
}
