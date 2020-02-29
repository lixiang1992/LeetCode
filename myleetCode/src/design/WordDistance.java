package design;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 244. 最短单词距离 II
 * 请设计一个类，使该类的构造函数能够接收一个单词列表。
 * 然后再实现一个方法，该方法能够分别接收两个单词 word1 和 word2，并返回列表中这两个单词之间的最短距离。
 * 您的方法将被以不同的参数调用 多次。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shortest-word-distance-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class WordDistance {

    // key ：单词 value : 单词所在下标的红黑树集合
    private Map<String, TreeSet<Integer>> wordMap = new HashMap<>();

    public WordDistance(String[] words) {
        for (int i = 0; i < words.length; i++) {
            // word和下标集合的维护
            TreeSet<Integer> set = wordMap.computeIfAbsent(words[i], k -> new TreeSet<>());
            set.add(i);
        }
    }

    public int shortest(String word1, String word2) {
        // word1下标集合
        TreeSet<Integer> word1Index = wordMap.get(word1);
        // word2下标集合
        TreeSet<Integer> word2Index = wordMap.get(word2);
        int min = Integer.MAX_VALUE;
        // 遍历其中一个下标，用二分的查找的方式，找word2中的最接近下标
        for (Integer index1 : word1Index) {
            int tempDiff = Integer.MAX_VALUE;
            // 小于index1的word2的下标
            Integer lower = word2Index.lower(index1);
            if (lower != null) {
                tempDiff = Math.abs(index1 - lower);
            }
            // 大于index1的word2的下标
            Integer higher = word2Index.higher(index1);
            if (higher != null) {
                tempDiff = Math.min(tempDiff, Math.abs(higher - index1));
            }
            // 最小距离值
            min = Math.min(tempDiff,min);
        }
        return min;
    }
}
