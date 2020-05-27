package hash;

import java.util.HashMap;
import java.util.Map;

/**
 * 974.和可被 K 整除的子数组
 * 给定一个整数数组 A，返回其中元素之和可被 K 整除的（连续、非空）子数组的数目。
 */
public class SubarraysDivByK_974 {

    public int subarraysDivByK(int[] A, int K) {
        // 前缀(同余)匹配
        Map<Integer,Integer> prefixMap = new HashMap<>();
        prefixMap.put(0,1);
        int sum = 0;
        int ans = 0;
        // 若a % x = b % x 则有 (a - b) % x = 0。
        for (int a : A) {
            sum += a;// 前缀同余
            sum %= K;// 对K取余
            if (sum < 0){
                sum += K;// 如果是负数，转换为正数
            }
            ans += prefixMap.getOrDefault(sum, 0);
            // 前缀和记录个数
            prefixMap.put(sum, prefixMap.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }
}
