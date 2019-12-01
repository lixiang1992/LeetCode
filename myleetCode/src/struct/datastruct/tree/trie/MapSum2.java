package struct.datastruct.tree.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * 677的另一种解法，利用字符串
 */
public class MapSum2 {
    private Map<String,Integer> cache;

    /**
     * Initialize your data structure here.
     */
    public MapSum2() {
        cache = new HashMap<>();
    }

    public void insert(String key, int val) {
        cache.put(key,val);
    }

    public int sum(String prefix) {
        int sum = 0;
        for (Map.Entry<String,Integer> entry: cache.entrySet()){
            if (entry.getKey().indexOf(prefix) == 0){
                sum +=entry.getValue();
            }
        }
        return sum;
    }

}
