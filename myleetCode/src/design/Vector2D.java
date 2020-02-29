package design;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 251.展开二维向量
 */
public class Vector2D {

    private Queue<Integer> queue = new LinkedList<>();

    public Vector2D(int[][] v) {
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++){
                queue.offer(v[i][j]);
            }
        }
    }

    public int next() {
        return queue.poll();
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }
}
