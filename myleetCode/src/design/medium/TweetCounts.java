package design.medium;

import java.util.*;

/**
 * 1348.推文计数
 * 请你实现一个能够支持以下两种方法的推文计数类 TweetCounts：
 *
 * 1. recordTweet(string tweetName, int time)
 *
 * 记录推文发布情况：用户 tweetName 在 time（以 秒 为单位）时刻发布了一条推文。
 * 2. getTweetCountsPerFrequency(string freq, string tweetName, int startTime, int endTime)
 *
 * 返回从开始时间 startTime（以 秒 为单位）到结束时间 endTime（以 秒 为单位）内，每 分 minute，时 hour 或者 日 day （取决于 freq）内指定用户 tweetName 发布的推文总数。
 * freq 的值始终为 分 minute，时 hour 或者 日 day 之一，表示获取指定用户 tweetName 发布推文次数的时间间隔。
 * 第一个时间间隔始终从 startTime 开始，因此时间间隔为 [startTime, startTime + delta*1>,  [startTime + delta*1, startTime + delta*2>, [startTime + delta*2, startTime + delta*3>, ... , [startTime + delta*i, min(startTime + delta*(i+1), endTime + 1)>，其中 i 和 delta（取决于 freq）都是非负整数。
 *
 */
public class TweetCounts {

    // key 用户->推文时间->该时间推文发布数目
    //用TreeMap比用List时间复杂度更低一些，因为用TreeMap查找记录，直接通过二分查找定位，这一步节省了很多时间，定位到开始的位置之后，接着就是迭代查找后继了（这明明就是跳表呀）。
    private Map<String, TreeMap<Integer,Integer>> userTweetMap;

    public TweetCounts() {
        userTweetMap = new HashMap<>();
    }

    /**
     * 发布推文
     * @param tweetName 推特用户
     * @param time 推文时间
     */
    public void recordTweet(String tweetName, int time) {
        // 发布推文
        TreeMap<Integer, Integer> tweetMap = userTweetMap.computeIfAbsent(tweetName, k -> new TreeMap<>());// 当前用户推文集合
        // 推文时间记录，比之前次数多1
        tweetMap.put(time,tweetMap.getOrDefault(time,0) + 1);// 推文加入
    }

    /**
     * 返回从开始时间 startTime（以 秒 为单位）到结束时间 endTime（以 秒 为单位）内，
     * 每 分 minute，时 hour 或者 日 day （取决于 freq）内指定用户 tweetName 发布的推文总数。
     *
     * @param freq 统计类型 分，时，日
     * @param tweetName 推特用户
     * @param startTime 开始时长 单位秒
     * @param endTime 结束时长 单位秒
     * @return 发布推文总数
     */
    public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
        List<Integer> res = new ArrayList<>();
        if (!userTweetMap.containsKey(tweetName)){
            res.add(0);
            return res;
        }
        // 单位是秒
        int freqTime = 1;
        if ("minute".equals(freq)){
            freqTime = 60;
        }else if ("hour".equals(freq)){
            freqTime = 3600;
        }else if ("day".equals(freq)){
            freqTime = 86400;
        }
        // 用户的全部推文时间集合
        TreeMap<Integer,Integer> tweetMap = userTweetMap.get(tweetName);
        int start = startTime;
        int end = Math.min(start + freqTime,endTime + 1);
        while (start <= endTime){// 最后的endTime是一个闭区间，而end如果超过了endTime，就被会重新设置为endTime+1的开区间
            int count = 0;
            // 找到发文时间大于等于start的推文
            Map.Entry<Integer,Integer> entry = tweetMap.ceilingEntry(start);
            while (entry != null && entry.getKey() < end){
                count += entry.getValue();// 推文数累加
                // 找比当前大的推文时间
                entry = tweetMap.higherEntry(entry.getKey());
            }
            res.add(count);
            // 时间后移
            start = end;
            end = Math.min(end + freqTime,endTime + 1);
        }
        return res;
    }
}
