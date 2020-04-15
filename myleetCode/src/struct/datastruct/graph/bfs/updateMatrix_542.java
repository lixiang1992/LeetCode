package struct.datastruct.graph.bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 542.0-1矩阵给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。
 *
 * 两个相邻元素间的距离为 1 。
 */
public class updateMatrix_542 {

    public int[][] updateMatrix(int[][] matrix) {
        int[][] res = new int[matrix.length][matrix[0].length];
        // 访问数组
        boolean[][] visit = new boolean[matrix.length][matrix[0].length];
        Queue<int[]> queue = new LinkedList<>();
        // 思想，多元BFS遍历
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                res[i][j] = matrix[i][j];
                if (res[i][j] == 0){
                    visit[i][j] = true;// 标记被访问
                    queue.offer(new int[]{i,j});
                }
            }
        }
        // 标记节点是否被访问过
        while (!queue.isEmpty()){
            int[] pos = queue.poll();
            // 上
            if (pos[0] - 1>= 0 && !visit[pos[0] - 1][pos[1]]){
                visit[pos[0] - 1][pos[1]] = true;
                res[pos[0]-1][pos[1]] = res[pos[0]][pos[1]] + 1;
                queue.offer(new int[]{pos[0] - 1,pos[1]});
            }
            // 下
            if (pos[0] + 1 < matrix.length && !visit[pos[0] + 1][pos[1]]){
                visit[pos[0] + 1][pos[1]] = true;
                res[pos[0]+1][pos[1]] = res[pos[0]][pos[1]] + 1;
                queue.offer(new int[]{pos[0] + 1,pos[1]});
            }
            // 左
            if (pos[1] - 1 >= 0 && !visit[pos[0]][pos[1] - 1]){
                visit[pos[0]][pos[1] - 1] = true;
                res[pos[0]][pos[1]-1] = res[pos[0]][pos[1]] + 1;
                queue.offer(new int[]{pos[0],pos[1]-1});
            }
            // 右
            if (pos[1] + 1 < matrix[0].length && !visit[pos[0]][pos[1]+1]){
                visit[pos[0]][pos[1] + 1] = true;
                res[pos[0]][pos[1]+1] = res[pos[0]][pos[1]] + 1;
                queue.offer(new int[]{pos[0],pos[1]+1});
            }
        }
        return res;
    }
}
