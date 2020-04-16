package struct.datastruct.graph.bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1162.地图分析
 * 你现在手里有一份大小为 N x N 的『地图』（网格） grid，上面的每个『区域』（单元格）都用 0 和 1 标记好了。
 * find a water cell such that its distance to the nearest land cell is maximized and return the distance.
 * 其中 0 代表海洋，1 代表陆地，你知道距离陆地区域最远的海洋区域是是哪一个吗？请返回该海洋区域到离它最近的陆地区域的距离。
 *
 * 我们这里说的距离是『曼哈顿距离』（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个区域之间的距离是 |x0 - x1| + |y0 - y1| 。
 *
 * 如果我们的地图上只有陆地或者海洋，请返回 -1
 *
 */
public class MaxDistance_1162 {

    /**
     * 多元BFS
     * @param grid
     * @return
     */
    public int maxDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        // 克隆一份，不改变入参
        int[][] clone = grid.clone();
        int length = clone.length;
        // 找到所有陆地，这是一个整体的入口，把陆地当成一个整体考虑
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (clone[i][j] == 1){
                    queue.offer(new int[]{i,j});
                }
            }
        }

        // 如果全是海洋，或者全是陆地，返回-1
        if (queue.isEmpty() || queue.size() == length * length){
            return -1;
        }
        // 这里为什么-1呢，因为第一次访问的都是陆地，是没有海洋的
        int res = -1;
        // 遍历队列，实现BFS
        while (!queue.isEmpty()){
            res++;
            int n = queue.size();
            for (int i = 0;i < n;i++){
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];
                // 上下左右四个方向遍历
                // 上
                if (row > 0 && clone[row-1][col] == 0){
                    clone[row-1][col] = -1;// 标记已经访问过
                    queue.offer(new int[]{row - 1,col});
                }
                // 下
                if (row < length-1 && clone[row + 1][col] == 0){
                    clone[row + 1][col] = -1;
                    queue.offer(new int[]{row+1,col});
                }
                // 左
                if (col > 0 && clone[row][col-1] == 0){
                    clone[row][col-1] = -1;
                    queue.offer(new int[]{row,col-1});
                }
                // 右
                if (col < length - 1 && clone[row][col + 1] == 0){
                    clone[row][col + 1] = -1;
                    queue.offer(new int[]{row,col+1});
                }
            }
        }

        return res;
    }
}
