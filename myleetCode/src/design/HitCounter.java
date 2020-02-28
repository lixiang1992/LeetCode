package design;

import java.util.Map;
import java.util.TreeMap;

/**
 * 362.设计一个敲击计数器，使它可以统计在过去5分钟内被敲击次数。
 *
 * 每个函数会接收一个时间戳参数（以秒为单位），你可以假设最早的时间戳从1开始，且都是按照时间顺序对系统进行调用（即时间戳是单调递增）。
 *
 * 在同一时刻有可能会有多次敲击。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/design-hit-counter
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class HitCounter {
    // key 敲击时刻 value 在这一时刻之前的闭区间计数之和
    private TreeMap<Integer,Integer> m_hitCountMap;// 敲击计数treeMap
    private int m_lastHitTime = 0;// 上一次的敲击时刻

    /** Initialize your data structure here. */
    public HitCounter() {
        m_hitCountMap = new TreeMap<>();
    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        // 把上一次敲击时间次数累加到这一次，运用前缀和的思想
        // 因为timestamp是递增的，所以上次的时间一定<=这次时间
        m_hitCountMap.put(timestamp,m_hitCountMap.getOrDefault(m_lastHitTime,0) + 1);
        m_lastHitTime = timestamp;
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        if (m_lastHitTime <= 0){
            return 0;// 不存在上次的敲击时刻，直接返回0
        }
        int minTime = timestamp - 300;
        // 5分钟前的时刻，利用前缀和获取敲击时间<=minTime的最接近时刻
        Map.Entry<Integer,Integer> minEntry = m_hitCountMap.floorEntry(minTime);
        // 上一次敲击的时刻
        int max = m_hitCountMap.get(m_lastHitTime);
        int min = minEntry == null ? 0 : minEntry.getValue();
        // 两者之差就是这个时间范围内的敲击次数
        return max - min;
    }
}
