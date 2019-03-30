package design;

import java.util.*;

/**
 * 355.设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近十条推文。你的设计需要支持以下的几个功能：
 * <p>
 * postTweet(userId, tweetId): 创建一条新的推文
 * getNewsFeed(userId): 检索最近的十条推文。每个推文都必须是由此用户关注的人或者是用户自己发出的。推文必须按照时间顺序由最近的开始排序。
 * follow(followerId, followeeId): 关注一个用户
 * unfollow(followerId, followeeId): 取消关注一个用户
 */
public class Twitter {

    // key:用户id -> value: 用户key的关注人集合
    private Map<Integer, Set<Integer>> userFollowMap;
    // key:用户id -> value:用户的最近LATELY条推文
    private Map<Integer, Queue<Tweet>> userTweetMap;
    // 最近推文的数量
    private static final int LATELY = 10;
    // 学院派算法可以不用考虑多线程导致的问题，但是工业需要考虑多线程改变时间的值
    private long curTime = Long.MIN_VALUE;

    /**
     * 推文
     */
    private static class Tweet implements Comparable<Tweet>{

        int tweetId;// 推文id
        long time;// 推送时间

        Tweet(int tweetId,long time){
            this.tweetId = tweetId;
            this.time = time;
        }

        @Override
        public int compareTo(Tweet o) {
            if (this.time - o.time > 0){
                return 1;
            }else if(this.time - o.time < 0){
                return -1;
            }else {
                return 0;
            }
        }
    }

    public Twitter() {
        userFollowMap = new HashMap<>();
        userTweetMap = new HashMap<>();
    }

    /**
     * 关注人集合初始化
     * @param userId
     * @return
     */
    private Set<Integer> getFollowSetByUserId(int userId){
        Set<Integer> followSet = userFollowMap.get(userId);// 当前关注人的关注集合
        if (followSet == null){// 不存在则新建
            followSet = new HashSet<>();
            followSet.add(userId);// 首先就要关注自己
            userFollowMap.put(userId,followSet);// 集合入桶
        }
        return followSet;
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        Queue<Tweet> tweetQueue = userTweetMap.get(userId);// 当前用户最近的推文队列
        if (tweetQueue == null) {
            tweetQueue = new PriorityQueue<>();// 最近推文是一个小顶堆
            userTweetMap.put(userId,tweetQueue);
        }
        Tweet tweet = new Tweet(tweetId,curTime++);
        if (tweetQueue.size() < LATELY){
            tweetQueue.offer(tweet);// 当前用户最近推文数量未到上限，直接加入
        }else {
            tweetQueue.poll();// 当前用户最近推文达到上限，移除最早发送的
            tweetQueue.offer(tweet);// 最近的推文加入
        }
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        Queue<Tweet> newTweetQueue = new PriorityQueue<>();// 最近的推文集合，小顶堆
        Set<Integer> followSet = getFollowSetByUserId(userId);// 当前关注人的关注集合
        for (int followId:followSet){
            Queue<Tweet> tweetQueue = userTweetMap.get(followId);
            if (tweetQueue == null){
                continue;
            }
            for (Tweet tw : tweetQueue){
                if (newTweetQueue.size() < LATELY){
                    newTweetQueue.offer(tw);
                }else if(tw.compareTo(newTweetQueue.peek()) > 0){
                    newTweetQueue.poll();
                    newTweetQueue.offer(tw);
                }
            }
        }
        LinkedList<Integer> newsFeed = new LinkedList<>();
        while (!newTweetQueue.isEmpty()){
            newsFeed.addFirst(newTweetQueue.poll().tweetId);
        }
        return newsFeed;
    }

    /**
     * followerId关注了followeeId
     *
     * @param followerId 关注人
     * @param followeeId 被关注人
     */
    public void follow(int followerId, int followeeId) {
        Set<Integer> followSet = getFollowSetByUserId(followerId);
        followSet.add(followeeId);// 加入被关注人
    }

    /**
     * followerId取消关注followeeId
     *
     * @param followerId 关注人
     * @param followeeId 被关注人
     */
    public void unfollow(int followerId, int followeeId) {
        if (followerId == followeeId){
            return;
        }
        Set<Integer> followSet = getFollowSetByUserId(followerId);
        followSet.remove(followeeId);// 移除被关注人
    }
}
