package struct.datastruct.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 346. 数据流中的移动平均值
 * 给定一个整数数据流和一个窗口大小，根据该滑动窗口的大小，计算其所有整数的移动平均值。
 *
 * 示例:
 *
 * MovingAverage m = new MovingAverage(3);
 * m.next(1) = 1
 * m.next(10) = (1 + 10) / 2
 * m.next(3) = (1 + 10 + 3) / 3
 * m.next(5) = (10 + 3 + 5) / 3
 *
 */
public class MovingAverage {

    private Queue<Integer> queue;// 队列
    private int size;// 队列大小
    private double sum = 0.0;// 数字和

    public MovingAverage(int size) {
        queue = new LinkedList<>();
        this.size = size;
    }

    public double next(int val) {
        if (queue.size() == size){
           sum -= queue.poll();
        }
        queue.offer(val);
        sum += val;
        return sum/queue.size();
    }
}
