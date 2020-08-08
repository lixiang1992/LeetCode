package struct.datastruct.queue;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * 862. 和至少为 K 的最短子数组
 * 返回 A 的最短的非空连续子数组的长度，该子数组的和至少为 K 。
 * <p>
 * 如果没有和至少为 K 的非空子数组，返回 -1 。
 */
public class ShortestSubarray_862 {

    /**
     * 前缀和+treeMap做法
     * @param A
     * @param K
     * @return
     */
    public int shortestSubarray_TreeMap(int[] A, int K) {
        // 前缀和，最小下标
        TreeMap<Long, Integer> prefixMap = new TreeMap<>();
        long sum = 0L;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < A.length; i++) {
            if(A[i] >= K){
                return 1;
            }
            sum += A[i];
            if(sum >= K){
                ans = Math.min(ans,i + 1);
            }
            Map.Entry<Long,Integer> entry = prefixMap.floorEntry(sum - K);
            // 比entry小的，都找出来
            while (entry != null){
                ans = Math.min(ans,i - entry.getValue());
                // 这里移除掉，是因为后面再来的距离，肯定比现在这个i到满足条件的距离大，可以舍弃了
                prefixMap.remove(entry.getKey());
                entry = prefixMap.floorEntry(sum - K);
            }
            // 前缀和，对应下标
            prefixMap.put(sum,i);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public int shortestSubarray(int[] A, int K) {
        long sum = 0L;
        int ans = Integer.MAX_VALUE;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < A.length; i++) {

        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
