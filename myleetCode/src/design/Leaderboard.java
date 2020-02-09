package design;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 1244.leetcode排行榜
 *
 * 新一轮的「力扣杯」编程大赛即将启动，为了动态显示参赛者的得分数据，需要设计一个排行榜 Leaderboard。
 *
 * 请你帮忙来设计这个 Leaderboard 类，使得它有如下 3 个函数：
 *
 * addScore(playerId, score)：
 * 假如参赛者已经在排行榜上，就给他的当前得分增加 score 点分值并更新排行。
 * 假如该参赛者不在排行榜上，就把他添加到榜单上，并且将分数设置为 score。
 * top(K)：返回前 K 名参赛者的 得分总和。
 * reset(playerId)：将指定参赛者的成绩清零。题目保证在调用此函数前，该参赛者已有成绩，并且在榜单上。
 * 请注意，在初始状态下，排行榜是空的。
 *
 */
public class Leaderboard {

    // key 人员 value 分数
    private Map<Integer,Integer> m_rank;
    // 维护一个积分排名的数组，保证有序，后面进来的采用二分查找法插入，
    // 用个treeMap相等于直接处理二分了
    // key 分数 value 同分人数
    private TreeMap<Integer,Integer> m_score;// 分数

    public Leaderboard() {
        m_rank = new HashMap<>();
        m_score = new TreeMap<>();
    }

    public void addScore(int playerId, int score) {
        Integer oldScore = m_rank.get(playerId);
        if (oldScore != null) {
            // 旧积分人数处理
            oldScoreNumProcess(oldScore);
            score += oldScore;
        }
        // 选手分数更新
        m_rank.put(playerId,score);
        // 分数排名更新
        m_score.put(score,m_score.getOrDefault(score,0) + 1);
    }

    /**
     * 旧积分人数的处理
     * @param score 积分
     */
    private void oldScoreNumProcess(int score){
        int scoreNum = m_score.get(score);
        if (scoreNum <= 1){
            // 该积分没有人了，积分移除
            m_score.remove(score);
        } else {
            // 该积分人数减一
            m_score.put(score,scoreNum - 1);
        }
    }

    public int top(int K) {
        int sumScore = 0;
        Map.Entry<Integer,Integer> scoreEntry = m_score.lastEntry();
        while (K > 0 && scoreEntry != null){
            // key 分数 value 人数
            // 积分人数如果大于K了，取K个人，否则全部取出
            int num = Math.min(scoreEntry.getValue(),K);
            // 分数*数量
            sumScore += scoreEntry.getKey() * num;
            // 分数前移，找小的
            scoreEntry = m_score.lowerEntry(scoreEntry.getKey());
            K-= num;
        }
        return sumScore;
    }

    public void reset(int playerId) {
        // 选手积分
        int score = m_rank.get(playerId);
        // 选手积分清零
        m_rank.put(playerId,0);
        // 积分人数处理
        oldScoreNumProcess(score);
        // 0分人数+1
        m_score.put(0,m_score.getOrDefault(0,0) + 1);
    }
}
