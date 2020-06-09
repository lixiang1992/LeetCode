package struct.datastruct.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

/**
 * 图
 */
public class Graph {

    /**
     * 以图判树
     * 1.是否连通 即从一个节点出发，每个节点是否都能访问到
     * 2.是否有环 无向图拓扑思想处理
     * @param n
     * @param edges
     * @return
     */
    public boolean validTree(int n, int[][] edges) {
        if(n == 1){
            return true;
        }
        // 这个思路不一定正确
        if (n != edges.length+1){
            return false;
        }
        Map<Integer,List<Integer>> adjTable = new HashMap<>();
        int[] degree = new int[n];
        for (int[] edge : edges){
            degree[edge[0]]++;
            degree[edge[1]]++;
            adjTable.computeIfAbsent(edge[0],k->new ArrayList<>()).add(edge[1]);
            adjTable.computeIfAbsent(edge[1],k->new ArrayList<>()).add(edge[0]);
        }
        // 校验是否连通

        // 校验是否有环，无向图的拓扑排序，入度是1的就移除
        boolean[] delete = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0;i < degree.length;i++){
            if (degree[i] == 1){
                queue.offer(i);
                delete[i] = true;
            }
        }
        if (queue.isEmpty()){
            return false;
        }
        while (!queue.isEmpty()){
            int v = queue.poll();
            List<Integer> children = adjTable.get(v);
            for (int child : children){
                if (delete[child]){
                    continue;
                }
                // 入度减1
                degree[child]--;
                if (degree[child] == 1){
                    queue.offer(child);
                    delete[child] = true;
                }
            }
        }
        for (boolean del : delete){
            if (!del){
                return false;
            }
        }
        return true;
    }

    /**
     * 310.最小高度树
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> ans = new ArrayList<>();
        if (n == 1){
            // 一个节点就是他本身了
            ans.add(0);
            return ans;
        }
        // 拓扑排序
        // 节点的入度
        int[] inDegree = new int[n];
        Map<Integer,List<Integer>> adjTable = new HashMap<>();
        for (int[] edge : edges){
            inDegree[edge[0]]++;// 入度
            inDegree[edge[1]]++;// 出度
            adjTable.computeIfAbsent(edge[0],k->new ArrayList<>()).add(edge[1]);
            adjTable.computeIfAbsent(edge[1],k->new ArrayList<>()).add(edge[0]);
        }
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visit = new boolean[n];
        for (int i = 0;i< inDegree.length;i++){
            if (inDegree[i] == 1){
                // 入度为1的节点入队列，类似于叶子节点
                queue.offer(i);
                visit[i] = true;
            }
        }
        int rear = queue.size();
        while (!queue.isEmpty()){
            int v = queue.poll();
            rear--;
            ans.add(v);
            for (int child : adjTable.get(v)){
                if (visit[child]){// 当前节点已经访问过，跳过
                    continue;
                }
                inDegree[child]--; // 入度-1
                if (inDegree[child] == 1){
                    queue.offer(child);
                    visit[child] = true;
                }
            }
            if (rear == 0){
                // 当前层遍历完。进入下一层查找
                rear = queue.size();
                if (rear > 0){
                    // 如果没有元素了，表示全部遍历了，不用清空，直接返回
                    ans.clear();
                }
            }
        }
        return ans;
    }

    public int countComponents(int n, int[][] edges) {
        Map<Integer,List<Integer>> adjTable = new HashMap<>();
        boolean[] visit = new boolean[n];// 访问节点
        for(int[] edge : edges){
            adjTable.computeIfAbsent(edge[0],k->new ArrayList<>()).add(edge[1]);
            adjTable.computeIfAbsent(edge[1],k->new ArrayList<>()).add(edge[0]);
        }
        int ans = 0;
        for (int i = 0;i< visit.length;i++){
            if (!visit[i]){
                // 开始遍历节点
                dfsOrderAdjTable(adjTable,i,visit);
//                bfsOrderAdjTable(adjTable,i,visit);
                // 一次遍历完成表示一个连通分量
                ans++;
            }
        }
        return ans;
    }

    /**
     * 深度优先遍历
     * @param adjTable
     * @param index
     * @param visit
     */
    private void dfsOrderAdjTable(Map<Integer, List<Integer>> adjTable, int index, boolean[] visit) {
        if (visit[index]){
            return;
        }
        // 当前节点标记为访问
        visit[index] = true;
        List<Integer> children = adjTable.get(index);
        if (children == null){
            return;
        }
        // DFS访问子节点
        for (int child : children){
            dfsOrderAdjTable(adjTable,child,visit);
        }
    }

    /**
     * 广度优先遍历
     * @param adjTable
     * @param index
     * @param visit
     */
    private void bfsOrderAdjTable(Map<Integer, List<Integer>> adjTable, int index, boolean[] visit){
        Queue<Integer> queue = new LinkedList<>();
        int cur = index;
        queue.offer(cur);
        while (!queue.isEmpty()){
            cur = queue.poll();
            visit[cur] = true;// 标记当前节点访问
            List<Integer> children = adjTable.get(cur);
            if (children == null){
                continue;
            }
            for (int child : children){
                if (visit[child]){
                    continue;
                }
                queue.offer(child);
            }
        }
    }

    /**
     * 332. 重新安排行程
     * @param tickets
     * @return
     */
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, TreeMap<String,Boolean>> adjTable = new HashMap<>();
        for (List<String> ticket : tickets){
            // TODO 改成优先队列，因为票可能有一样的
            adjTable.computeIfAbsent(ticket.get(0),k->new TreeMap<>()).put(ticket.get(1),false);
        }
        List<String> ans = new LinkedList<>();
        findPath(adjTable,"JFK",ans);
        return ans;
    }

    private void findPath(Map<String, TreeMap<String,Boolean>> adjTable,String from,List<String> ans){
        TreeMap<String,Boolean> treeMap = adjTable.get(from);
        if (treeMap == null){
            ans.add(0,from);// 起点
            return;
        }
        // 标记是否访问过
        for (Map.Entry<String,Boolean> entry : treeMap.entrySet()){
            if (entry.getValue()){
                continue;
            }
            entry.setValue(true);
            findPath(adjTable, entry.getKey(), ans);
        }
        ans.add(0,from);// 起点
    }

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
     * 1129.颜色交替的最短路径
     * @param n
     * @param red_edges
     * @param blue_edges
     * @return
     */
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        // 构造双邻接表
        Map<Integer,List<Integer>> redTable = buildRecTable(red_edges);// 红边邻接表
        Map<Integer,List<Integer>> blueTable = buildRecTable(blue_edges);// 蓝边邻接表
        int[] ans = new int[n];
        // 获取红色邻接表开始的结果
        int[] redAns = bfsAlternatingPaths(n,redTable,blueTable,true);
        // 获取蓝色邻接表开始的结果
        int[] blueAns = bfsAlternatingPaths(n,redTable,blueTable,false);
        for(int i = 0;i < ans.length;i++){
            ans[i] = Math.min(redAns[i],blueAns[i]);// 取两者最小值
            // 无法到达的点设置为-1
            if (ans[i] == Integer.MAX_VALUE){
                ans[i] = -1;
            }
        }
        return ans;
    }

    /**
     * bfs获取最短路径
     * @param n 节点数
     * @param redTable 红色邻接表
     * @param blueTable 蓝色邻接表
     * @param isRed 是否红色
     * @return 最短路径结果
     */
    private int[] bfsAlternatingPaths(int n,Map<Integer,List<Integer>> redTable,Map<Integer,List<Integer>> blueTable,boolean isRed){
        // 标记红色节点是否访问
        boolean[] redVisit = new boolean[n];
        // 标记蓝色节点是否访问
        boolean[] blueVisit = new boolean[n];
        // 初始化全为最大值
        int[] ans = new int[n];
        Arrays.fill(ans,Integer.MAX_VALUE);
        // 建立队列开始bfs
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);// 从0节点出发
        int rear = queue.size();// 队尾标识
        int floor = 0;// 层数
        while (!queue.isEmpty()){
            int index = queue.poll();
            rear--;// 队列元素-1
            // 获取当前访问数组
            boolean[] visit = isRed ? redVisit : blueVisit;
            visit[index] = true;// 标记访问
            // 记录步数，这里要比较，是因为这个点之前在另一个访问数组里走过了
            ans[index] = Math.min(floor,ans[index]);
            // 获取下一层的邻接表
            Map<Integer, List<Integer>> adjTable = isRed ? redTable : blueTable;
            List<Integer> children = adjTable.get(index);
            if (children != null) {
                // 查看节点是否访问过，如果父节点是红，则孩子节点是蓝，如果父节点是蓝，则孩子节点是红
                boolean[] childVisit = !isRed ? redVisit : blueVisit;
                for (int child : children) {
                    if (childVisit[child]){
                        continue;// 遍历过的跳过
                    }
                    queue.offer(child);// 加入队列
                }
            }
            // 当前层结束，进入下一层，颜色转换
            if (rear == 0){
                floor++;
                rear = queue.size();
                isRed = !isRed;
            }
        }
        return ans;
    }

    /**
     * 构造有向邻接表
     * @param edges edges
     * @return adjTable
     */
    private Map<Integer,List<Integer>> buildRecTable(int[][] edges){
        Map<Integer,List<Integer>> adjTable = new HashMap<>();
        for (int[] edge : edges){
            adjTable.computeIfAbsent(edge[0],k->new ArrayList<>()).add(edge[1]);
        }
        return adjTable;
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

    /**
     * 1462.课程安排
     * @param n
     * @param prerequisites
     * @param queries
     * @return
     */
    public List<Boolean> checkIfPrerequisite(int n, int[][] prerequisites, int[][] queries) {
        // 邻接表
        Map<Integer, Set<Integer>> adjTable = new HashMap<>();
        Map<Integer,Set<Integer>> cache = new HashMap<>();
        for (int[] prerequisite : prerequisites) {
            // 邻接表初始化
            adjTable.computeIfAbsent(prerequisite[0], k -> new HashSet<>()).add(prerequisite[1]);
            // 压缩父路径初始化
            cache.computeIfAbsent(prerequisite[1], k -> new HashSet<>()).add(prerequisite[0]);
        }
        // 不存在的父路径缓存
        Map<Integer,Set<Integer>> noExistCache = new HashMap<>();
        List<Boolean> ans = new ArrayList<>();
        for (int[] query : queries) {
            ans.add(findTarget(adjTable, query[0], query[1], cache, noExistCache));
        }
        return ans;
    }

    /**
     * 是否能找到target
     * @param adjTable
     * @param index
     * @param target
     * @param cache
     * @param noExistCache
     * @return
     */
    private boolean findTarget(Map<Integer,Set<Integer>> adjTable,int index,int target,Map<Integer,Set<Integer>> cache,Map<Integer,Set<Integer>> noExistCache){
        if(cache.computeIfAbsent(target,k->new HashSet<>()).contains(index)){
            return true;
        }
        if (noExistCache.computeIfAbsent(target,k->new HashSet<>()).contains(index)){
            return false;
        }
        Set<Integer> set = adjTable.get(index);
        if (set == null){
            noExistCache.get(target).add(index);
            return false;
        }
        if (set.contains(target)){
            // 孩子包含目标，加入父节点缓存中
            cache.get(target).add(index);
            return true;
        }
        for (int cur : set){
            if (findTarget(adjTable,cur,target,cache,noExistCache)){
                // 父节点的父节点也加入
                cache.get(target).add(index);
                return true;
            }
        }
        // 不存在节点加入
        noExistCache.get(target).add(index);
        return false;
    }

    /**
     * 1466.重新规划线路
     * @param n
     * @param connections
     * @return
     */
    public int minReorder(int n, int[][] connections) {
        // 正向邻接表
        Map<Integer,Set<Integer>> adjTable = new HashMap<>();
        // 逆向邻接表
        Map<Integer,Set<Integer>> recAdjTable = new HashMap<>();
        for (int[] connection : connections) {
            adjTable.computeIfAbsent(connection[0], k -> new HashSet<>()).add(connection[1]);
            // 逆向路径存储，逆向的，就是可达的
            recAdjTable.computeIfAbsent(connection[1], k -> new HashSet<>()).add(connection[0]);
        }
        // 标记节点是否访问过
        boolean[] visit = new boolean[n];
        // 反转边的统计
        return countReorder(adjTable,recAdjTable,0,visit);
    }

    private int countReorder(Map<Integer,Set<Integer>> adjTable,Map<Integer,Set<Integer>> recAdjTable,int index,boolean[] visit){
        if (visit[index]){
            return 0;
        }
        visit[index] = true;
        // 逆向的节点集合
        Set<Integer> recSet = recAdjTable.computeIfAbsent(index,k->new HashSet<>());
        // 正向的节点集合
        Set<Integer> set = adjTable.computeIfAbsent(index,k->new HashSet<>());
        int ans = set.size();// 正向的，就是要调整的
        recSet.addAll(set);// 加入到逆向集合中去遍历调整
        for (int child : recSet){
            // 由孩子指向当前节点的正向节点移除，因为这个路径已经转移过来了
            adjTable.computeIfAbsent(child,k->new HashSet<>()).remove(index);
            // 进入孩子节点统计节点数
            ans += countReorder(adjTable,recAdjTable,child,visit);
        }
        return ans;
    }

}
