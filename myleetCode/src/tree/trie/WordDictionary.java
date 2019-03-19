package tree.trie;

public class WordDictionary {

    private TrieNode root;

    private static class TrieNode {
        char data;// 字符
        TrieNode parent;// 父元素
        TrieNode[] children = new TrieNode[26];// 子元素
        boolean isEndChar = false;

        TrieNode(char data) {
            this.data = data;
        }
    }

    public WordDictionary() {
        this.root = new TrieNode('/');
    }

    public void addWord(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char one = word.charAt(i);
            int index = one - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode(one);
                node.children[index].parent = node;
            }
            node = node.children[index];
        }
        node.isEndChar = true;
    }

    /**
     * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
     */
    public boolean search(String word) {
        int spec = word.indexOf(".");
        if (spec == -1){
            return searchNoContainSpec(word);
        }else {
            TrieNode node = root;
            for (int i = 0;i<word.length();i++){
                char cur = word.charAt(i);
            }
        }
//        for (int i = 0; i < word.length(); i++) {
//            char cur = word.charAt(i);
//            if (cur != '.') {
//                int index = cur - 'a';
//                if (node.children[index] == null) {
//                    return false;
//                } else {
//                    node = node.children[index];
//                }
//            } else {
//                List<Character> list = new ArrayList<>();
//
//            }
//        }
        return false;
    }

    /**
     * 不包含特殊符号的查询匹配
     * @param word
     * @return
     */
    private boolean searchNoContainSpec(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char cur = word.charAt(i);
            int index = cur - 'a';
            if (node.children[index] == null) {
                return false;
            } else {
                node = node.children[index];
            }
        }
        return node.isEndChar;
    }
}
