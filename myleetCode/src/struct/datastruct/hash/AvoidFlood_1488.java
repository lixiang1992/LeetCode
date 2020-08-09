package struct.datastruct.hash;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 你的国家有无数个湖泊，所有湖泊一开始都是空的。当第 n 个湖泊下雨的时候，如果第 n 个湖泊是空的，那么它就会装满水，否则这个湖泊会发生洪水。你的目标是避免任意一个湖泊发生洪水。
 *
 * 给你一个整数数组 rains ，其中：
 *
 * rains[i] > 0 表示第 i 天时，第 rains[i] 个湖泊会下雨。
 * rains[i] == 0 表示第 i 天没有湖泊会下雨，你可以选择 一个 湖泊并 抽干 这个湖泊的水。
 * 请返回一个数组 ans ，满足：
 *
 * ans.length == rains.length
 * 如果 rains[i] > 0 ，那么ans[i] == -1 。
 * 如果 rains[i] == 0 ，ans[i] 是你第 i 天选择抽干的湖泊。
 * 如果有多种可行解，请返回它们中的 任意一个 。如果没办法阻止洪水，请返回一个 空的数组 。
 *
 * 请注意，如果你选择抽干一个装满水的湖泊，它会变成一个空的湖泊。但如果你选择抽干一个空的湖泊，那么将无事发生（详情请看示例 4）。
 *
 */
public class AvoidFlood_1488 {

    /**
     * TreeSet记录没下雨的天，HashMap记录下雨的湖泊和最新的天，延迟策略。
     * 当湖泊X再次下雨的时候，找到其上次下雨的天，从上次下雨之后的某一天（TreeSet.higher）抽水，找不到就没有解
     * @param rains
     * @return
     */
    public int[] avoidFlood(int[] rains) {
        int[] ans = new int[rains.length];
        // 记录不下雨,还未抽水的天(下标)
        TreeSet<Integer> noRainDay = new TreeSet<>();
        // 记录到当前时间，有水的湖泊（值,对应的天）
        Map<Integer,Integer> rainPool = new HashMap<>();
        for (int i = 0;i < rains.length;i++){
            if (rains[i] > 0){
                // 下雨了
                if (rainPool.containsKey(rains[i])){
                    // 之前湖泊下雨了
                    // 可抽水的天,抽水天数要在下雨天的后面，要严格的大于
                    Integer day = noRainDay.higher(rainPool.get(rains[i]));
                    if (day == null){
                        // 工作的天小于了之前下雨的天，没有结果
                        return new int[0];
                    }
                    ans[day] = rains[i];// 记录结果

                    noRainDay.remove(day);// 这一天工作了，从备选中移除
                }
                // 不管之前这个湖泊有没有下雨，对应下雨的天都要更新
                rainPool.put(rains[i],i);

                ans[i] = -1;
            } else {
                // 没下雨，肯定是不抽水的
                noRainDay.add(i);
            }
        }
        // 剩余的天补全
        for (Integer day : noRainDay) {
            ans[day] = 1;
        }
        return ans;
    }
}
