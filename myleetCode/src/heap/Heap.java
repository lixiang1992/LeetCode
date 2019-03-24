package heap;

import java.util.*;

public class Heap {


    /**
     * 347.给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
     * @param nums
     * @param k
     * @return
     */
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int num : nums){
            map.put(num,map.getOrDefault(num,0) + 1);
        }
        Queue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return map.get(o1) - map.get(o2);
            }
        });
        for (Map.Entry<Integer,Integer> entry : map.entrySet()){
            if (pq.size() >= k){
                if (entry.getValue() > map.get(pq.peek())){
                    pq.poll();
                    pq.offer(entry.getKey());
                }
            }else {
                pq.offer(entry.getKey());
            }
        }
        List<Integer> result = new LinkedList<>();
        while (!pq.isEmpty()){
            ((LinkedList<Integer>) result).addFirst(pq.poll());
        }
        return result;
    }

    private class WordCount implements Comparable<WordCount>{
        int count;
        String word;

        WordCount(int count,String word){
            this.count = count;
            this.word = word;
        }

        @Override
        public int compareTo(WordCount o) {
            int difference = this.count - o.count;
            if (difference == 0){
                return o.word.compareTo(this.word);
            }
            return difference;
        }
    }

    /**
     * 692. 前K个高频单词
     * 给一非空的单词列表，返回前 k 个出现次数最多的单词。
     *
     * 返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率，按字母顺序排序。
     * @param words
     * @param k
     * @return
     */
    public List<String> topKFrequent(String[] words, int k) {
        Queue<WordCount> pq = new PriorityQueue<>();
        Map<String,WordCount> map = new HashMap<>();
        for (String word:words){
            WordCount wordCount = map.get(word);
            if (wordCount == null){
                wordCount = new WordCount(1,word);
                map.put(word,wordCount);
            }else {
                wordCount.count++;
                pq.remove(wordCount);// 这个时候workCount的值发生了变化，但优先队列本身并不知道，需要先移除再插入，来保证顺序
            }
            if (pq.size() >= k){
                if (wordCount.compareTo(pq.peek()) > 0){
                    pq.poll();
                    pq.offer(wordCount);
                }
            }else {
                pq.offer(wordCount);
            }
        }
        List<String> result = new LinkedList<>();
        while (!pq.isEmpty()){
            ((LinkedList<String>) result).addFirst(pq.poll().word);
        }
        return result;
    }
}
