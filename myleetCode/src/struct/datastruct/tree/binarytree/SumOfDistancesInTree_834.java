package struct.datastruct.tree.binarytree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 834.树中距离之和
 * <p>
 * 给定一个无向、连通的树。树中有 N 个标记为 0...N-1 的节点以及 N-1 条边 。
 * <p>
 * 第 i 条边连接节点 edges[i][0] 和 edges[i][1] 。
 * <p>
 * 返回一个表示节点 i 与其他所有节点距离之和的列表 ans。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sum-of-distances-in-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 */
public class SumOfDistancesInTree_834 {

    /**
     * 思路
     * A和B拆为两个子树，A,B分别为root节点
     * 各个节点到A的距离，等于A子树各个节点到A的距离，加上B子树各个节点到B的距离再加上B子树全部节点的个数（每个节点都要多连接一个A）
     * 公式为 ans(A) = sum(A) + sum(B) + cnt(B); 同理 ans(B) = sum(B) + sum(A) + cnt(A);
     * 那么 ans(A) = ans(B) - cnt(A) + cnt(B)，又 cnt(A) + cnt(B) = N.
     * 则 ans(A) = ans(B) + N - 2*cnt(A);
     * 所以，对于任意子节点，ans(cur) = ans(child) + N - 2*cnt(child);
     *
     * @param N
     * @param edges
     * @return
     */
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        Map<Integer, Set<Integer>> adjTable = new HashMap<>();
        for (int[] edge : edges) {
            // 建立邻接表
            adjTable.computeIfAbsent(edge[0], k -> new HashSet<>()).add(edge[1]);
            adjTable.computeIfAbsent(edge[1], k -> new HashSet<>()).add(edge[0]);
        }
        if (adjTable.size() < 1) {
            return new int[]{0};
        }
        int[] ans = new int[N];
        int[] count = new int[N];
        Arrays.fill(count, 1);
        // A和B拆为两个子树，A,B分别为root节点
        // 各个节点到A的距离，等于A子树各个节点到A的距离，加上B子树各个节点到B的距离再加上B子树全部节点的个数（每个节点都要多连接一个A）
        // 公式为 ans(A) = sum(A) + sum(B) + cnt(B); 同理 ans(B) = sum(B) + sum(A) + cnt(A);
        // 那么 ans(A) = ans(B) - cnt(A) + cnt(B)，又 cnt(A) + cnt(B) = N.
        // 则 ans(A) = ans(B) + N - 2*cnt(A);
        // 所以，对于任意子节点，ans(child) = ans(cur) + N - 2*cnt(cur);
        // 邻接表遍历
        getAllNodeCnt(adjTable, 0, -1, count, ans);
        getChildAns(adjTable, 0, -1, count, ans, N);
        return ans;
    }

    private void getAllNodeCnt(Map<Integer, Set<Integer>> adjTable, int index, int parent, int[] count, int[] ans) {
        for (int child : adjTable.get(index))
            if (child != parent) {
                // 递归进入子树，统计孩子的节点总数
                getAllNodeCnt(adjTable, child, index, count, ans);
                count[index] += count[child];// 当前节点个数数量加上孩子节点数量
                // 这个时候相当于sum(index) = sum(child) + cnt(child);
                ans[index] += ans[child] + count[child];
            }
    }

    private void getChildAns(Map<Integer, Set<Integer>> adjTable, int index, int parent, int[] count, int[] ans, int N) {
        for (int child : adjTable.get(index))
            if (child != parent) {
                ans[child] = ans[index] + N - 2 * count[child];
                getChildAns(adjTable, child, index, count, ans, N);
            }
    }

}
