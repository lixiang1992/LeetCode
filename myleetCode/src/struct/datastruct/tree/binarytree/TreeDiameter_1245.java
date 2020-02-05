package struct.datastruct.tree.binarytree;

import java.util.*;

/**
 * 1245.树的直径
 * 给你这棵「无向树」，请你测算并返回它的「直径」：这棵树上最长简单路径的 边数。
 *
 * 我们用一个由所有「边」组成的数组 edges 来表示一棵无向树，其中 edges[i] = [u, v] 表示节点 u 和 v 之间的双向边。
 *
 * 树上的节点都已经用 {0, 1, ..., edges.length} 中的数做了标记，每个节点上的标记都是独一无二的。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/tree-diameter
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class TreeDiameter_1245 {

    private TreeVertex root = new TreeVertex(0);// 无向树的根节点

    private static class TreeVertex{
        int index;// 顶点下标
        List<TreeVertex> child;

        TreeVertex(int index){
            this.index = index;
            this.child = new ArrayList<>();
        }
    }

    /**
     * 解题思路
     * 1.构造无向N叉树，0下标的为root节点
     * 2.从root开始，每个孩子节点进行DFS遍历
     * 3.经过节点的最大路径，是当前节点的所有孩子节点直径前二之和，同时记录下从当前节点出发的最大直径，往父节点递推。
     *
     * 当前节点的所有孩子节点直径前二之和，这里用了一个优先队列，其实不用优先队列会快个2ms的样子，不过我为了代码更加万精油一点，用了个优先队列。
     *
     * 执行时间：14ms。
     *
     * 注意：我看到有部分题解添加了一个数组来记录当前节点是否访问过，这里我认为是没有必要的，
     * 因为这个图构造出来后是一个无向树，树是不存在环的，每个节点有且仅有一次访问，不会出现重复访问的情况。所以没有必要再消耗一个数组的空间。
     *
     * 作者：shi-huo-de-xia-tian
     * 链接：https://leetcode-cn.com/problems/tree-diameter/solution/gou-zao-wu-xiang-ncha-shu-shen-du-you-xian-bian-li/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param edges 图数组
     * @return 最大直径
     */
    public int treeDiameter(int[][] edges) {
        if (edges.length == 0){
            return 0;
        }
        Map<Integer,TreeVertex> treeVertexMap = initVertexCache();
        for (int i = 0; i < edges.length; i++) {
            // 获取父顶点
            TreeVertex vertex = getTreeVertex(treeVertexMap,edges[i][0]);
            // 获取孩子顶点
            TreeVertex childVertex = getTreeVertex(treeVertexMap,edges[i][1]);
            vertex.child.add(childVertex);
        }
        int[] maxDiameter = new int[]{0};
        getMaxDiameter(maxDiameter,root);
        return maxDiameter[0];
    }

    /**
     * 获取树当前节点出发的最大直径
     * @param maxDiameter 最大直径
     * @param vertex 顶点
     * @return 当前节点出发的最大直径
     */
    private int getMaxDiameter(int[] maxDiameter,TreeVertex vertex){
        if (vertex == null){
            return 0;
        }
        int curVertexDiameter = 0;// 从当前节点出发的最长路径
        // 记录当前节点出发的两条最长路径，小顶堆
        Queue<Integer> pq = new PriorityQueue<>(2);
        for (TreeVertex temp : vertex.child){
            int tempDiameter = getMaxDiameter(maxDiameter,temp);
            // 当前节点出发的最长路径
            curVertexDiameter = Math.max(curVertexDiameter,tempDiameter);

            // 全局最长路径，只能取vertex孩子的两条最长路径
            if (pq.size() == 2){
                // 比小顶堆堆顶要大，进入小顶堆
                if (tempDiameter > pq.peek()){
                    pq.poll();
                    pq.offer(tempDiameter);
                }
            }else {
                pq.offer(tempDiameter);
            }
        }

        int sumDiameter = 0;
        while (!pq.isEmpty()){
            sumDiameter += pq.poll();
        }
        // 全局最大直径
        maxDiameter[0] = Math.max(maxDiameter[0],sumDiameter);

        // 当前节点出发的最大直径
        return 1 + curVertexDiameter;// 带上本身
    }

    /**
     * 获取无向树顶点
     * @param treeVertexMap 无向树顶点缓存
     * @param index 顶点下标
     * @return 无向树顶点
     */
    private TreeVertex getTreeVertex(Map<Integer,TreeVertex> treeVertexMap,int index){
        TreeVertex vertex = treeVertexMap.get(index);
        if (vertex == null){
            vertex = new TreeVertex(index);
            treeVertexMap.put(index,vertex);
        }
        return vertex;
    }

    /**
     * 初始化顶点缓存
     * @return 初始化缓存
     */
    private Map<Integer,TreeVertex> initVertexCache(){
        Map<Integer,TreeVertex> treeVertexMap = new HashMap<>();
        treeVertexMap.put(0,root);
        return treeVertexMap;
    }
}
