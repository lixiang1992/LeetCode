package struct.datastruct.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 图
 */
public class Graph {

    /**
     * 课程表2
     * 现在你总共有 n 门课需要选，记为 0 到 n-1。
     *
     * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]
     *
     * 给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。
     *
     * 可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组。
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 有向图的拓扑排序
        // 邻接表
        Map<Integer, List<Integer>> adjTable = new HashMap<>();
        // 入度
        int[] inDegree = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            // 有向图邻接表
            adjTable.computeIfAbsent(prerequisite[1], k -> new ArrayList<>()).add(prerequisite[0]);
            // 入度
            inDegree[prerequisite[0]]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0){
                queue.offer(i);// 加入入度为0的顶点
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()){
            int v = queue.poll();// 顶点出队
            res.add(v);
            // 下一级
            List<Integer> adjList = adjTable.get(v);
            if (adjList == null){
                continue;
            }
            for (Integer adj : adjList) {
                // 入度-1
                inDegree[adj]--;
                if (inDegree[adj] == 0){
                    queue.offer(adj);// 加入入度为0的顶点
                }
            }
        }
        if (res.size() < numCourses){
            return new int[]{};
        }
        int[] ans = new int[numCourses];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = res.get(i);
        }
        return ans;
    }

    /**
     * 1334.有 n 个城市，按从 0 到 n-1 编号。
     *
     * 给你一个边数组 edges，其中 edges[i] = [fromi, toi, weighti] 代表 fromi 和 toi 两个城市之间的双向加权边，距离阈值是一个整数 distanceThreshold。
     *
     * 返回能通过某些路径到达其他城市数目最少、且路径距离 最大 为 distanceThreshold 的城市。如果有多个这样的城市，则返回编号最大的城市。
     *
     * 注意，连接城市 i 和 j 的路径的距离等于沿该路径的所有边的权重之和。
     *
     * @param n
     * @param edges
     * @param distanceThreshold
     * @return
     */
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] graphs = new int[n][n];
        // 初始化填充
        for (int i = 0; i < graphs.length; i++) {
            for (int j = 0; j < graphs[i].length; j++) {
                graphs[i][j] = i == j ? 0 : Integer.MAX_VALUE;
            }
        }
        // 建立邻接矩阵
        for (int[] edge : edges) {
            graphs[edge[0]][edge[1]] = edge[2];
            graphs[edge[1]][edge[0]] = edge[2];
        }
        // floyd算法,求多源最短路径
        floyd(graphs,n);

        int res = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int temp = 0;
            for (int j = 0; j < n; j++) {
                // 除去自身
                if (i != j && graphs[i][j] <= distanceThreshold){
                    temp++;
                }
            }
            // 城市数较大的，覆盖小的那个
            if (temp <= min){
                min = temp;
                res = i;
            }
        }
        return res;
    }

    /**
     * floyd算法，求多源最短路径
     * @param graphs
     * @param n
     */
    private void floyd(int[][] graphs,int n){
        for (int k = 0; k < n; k++) {
            // n个顶点依次作为插入点
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j || graphs[i][k] == Integer.MAX_VALUE || graphs[k][j] == Integer.MAX_VALUE){
                        continue;
                    }
                    // floyd核心代码
                    graphs[i][j] = Math.min(graphs[i][j],graphs[i][k] + graphs[k][j]);
                }
            }
        }
    }
}
