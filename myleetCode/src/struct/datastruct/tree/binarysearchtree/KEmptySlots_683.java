package struct.datastruct.tree.binarysearchtree;

import java.util.TreeSet;

/**
 * 683.K个空花盆
 * 花园里有 N 个花盆，每个花盆里都有一朵花。这 N 朵花会在 N 天内依次开放，每天有且仅有一朵花会开放并且会一直盛开下去。
 *
 * 给定一个数组 flowers 包含从 1 到 N 的数字，每个数字表示在那一天开放的花所在的花盆编号。
 *
 * 例如， flowers[i] = x 表示在第 i 天盛开的花在第 x 个花盆中，i 和 x 都在 1 到 N 的范围内。
 *
 * 给你一个整数 k，请你输出在哪一天恰好有两朵盛开的花，他们中间间隔了 k 朵花并且都没有开放。
 *
 * 如果不存在，输出 -1。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/k-empty-slots
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class KEmptySlots_683 {

    /**
     * 解法1.红黑树，利用TreeSet
     * @param bulbs 花盆
     * @param K 间隔数K
     * @return 第X天
     */
    public int kEmptySlotsByRBT(int[] bulbs, int K) {
        // 间隔K个花盆，则两者的差要是K+1
        // 开花的花盆编号
        TreeSet<Integer> slotSet = new TreeSet<>();
        for (int i = 0; i < bulbs.length; i++) {
            // 编号小于当前花盆的开花花盆
            Integer low = slotSet.lower(bulbs[i]);
            if (low != null && bulbs[i] - low == K + 1){
                return i + 1;// 满足条件的天
            }
            // 编号大于当前花盆开花的花盆
            Integer high = slotSet.higher(bulbs[i]);
            if (high != null && high - bulbs[i] == K + 1){
                return i + 1;// 满足条件的天
            }
            slotSet.add(bulbs[i]);// 当前花盆加入
        }
        return -1;
    }

    // 滑动窗口解法
}
