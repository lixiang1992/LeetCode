package design;

import java.util.*;

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

    // 维护一个积分排名的数组，保证有序，后面进来的采用二分查找法插入
    private Map<Integer,Integer> m_rank;

    public Leaderboard() {
        m_rank = new HashMap<>();
    }

    public void addScore(int playerId, int score) {
        m_rank.merge(playerId, score, (a, b) -> a + b);
    }

    public int top(int K) {

        return 0;
    }

    public void reset(int playerId) {
        // 积分清零
        m_rank.put(playerId,0);
    }
}
