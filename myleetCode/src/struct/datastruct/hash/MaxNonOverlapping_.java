package struct.datastruct.hash;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 5471.和为目标值的最大数目不重叠非空子数组数目
 */
public class MaxNonOverlapping_ {

    /**
     * 给你一个数组 nums 和一个整数 target 。
     *
     * 请你返回 非空不重叠 子数组的最大数目，且每个子数组中数字和都为 target 。
     * @param nums
     * @param target
     * @return
     */
    public int maxNonOverlapping(int[] nums, int target) {
        // 前缀和，最小下标
        Map<Integer,Integer> prefixMap = new HashMap<>();
        // 下限，上限
        TreeMap<Integer,Integer> flag = new TreeMap<>();
        int sum = 0;
        int ans = 0;
        // 前缀和为0的，下标是-1，也就是最开始的前缀和就是0
        prefixMap.put(0,-1);
        for(int i = 0;i < nums.length;i++){
            sum += nums[i];
            // 前缀和查找目标，必须要先查找，再加入
            Integer start = prefixMap.get(sum - target);
            prefixMap.put(sum,i);
            if(start == null){
                continue;
            }
            // 重叠校验
            Map.Entry<Integer,Integer> low = flag.floorEntry(start);
            if(low != null && low.getValue() > start){
                continue;// 前节点考虑
            }
            Integer high = flag.higherKey(start);
            if (high != null && high < i){
                continue;// 后节点考虑
            }
            // 记录重叠区间
            flag.put(start,i);
            ans++;
        }
        return ans;
    }
}
