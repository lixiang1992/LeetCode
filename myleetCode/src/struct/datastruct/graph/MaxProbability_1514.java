package struct.datastruct.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class MaxProbability_1514 {

    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        // 获取邻接表
        Map<Integer, List<Node>> adjTable = buildAdjTable(edges, succProb);
        boolean[] visited = new boolean[n];// 节点是否访问过
        // 堆优化的dijkstra
        Queue<Node> pq = new PriorityQueue<>((o1, o2) -> Double.compare(o2.val,o1.val));
        pq.offer(new Node(start, 1.0D));
        while(!pq.isEmpty()){
            Node cur = pq.poll();
            // 节点已经访问过了
            if(visited[cur.index]){
                continue;
            }
            visited[cur.index] = true;// 标记节点已经访问过
            // 到达最后一个节点，返回结果
            if(cur.index == end){
                return cur.val;
            }
            // 取孩子节点
            List<Node> children = adjTable.get(cur.index);
            if(children == null){
                continue;
            }
            for(Node child : children) {
                // 更新孩子节点概率
                child.val *= cur.val;
                pq.offer(child);
            }
        }
        return 0.0D;
    }

    /**
     * 建立邻接表
     * @param edges 边的情况
     * @param succProb 概率
     * @return
     */
    private Map<Integer, List<Node>> buildAdjTable(int[][] edges, double[] succProb) {
        Map<Integer, List<Node>> adjTable = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            adjTable.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new Node(edge[1], succProb[i]));
            adjTable.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(new Node(edge[0], succProb[i]));
        }
        return adjTable;
    }

    private class Node {
        int index;// 节点下标
        double val;// 到节点的概率值

        Node(int index, double val) {
            this.index = index;
            this.val = val;
        }
    }
}
