package struct.datastruct.queue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 面试题59 - II. 队列的最大值
 * 请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的时间复杂度都是O(1)。
 * <p>
 * 若队列为空，pop_front 和 max_value 需要返回 -1
 * <p>
 * 思路：多维护一个双端单调递减队列
 * <p>
 * 本算法基于问题的一个重要性质：当一个元素进入队列的时候，它前面所有比它小的元素就不会再对答案产生影响。
 * <p>
 * 举个例子，
 * 如果我们向队列中插入数字序列 1 1 1 1 2，
 * 那么在第一个数字 2 被插入后，数字 2 前面的所有数字 1 将不会对结果产生影响。
 * 因为按照队列的取出顺序，数字 2 只能在所有的数字 1 被取出之后才能被取出，
 * 因此如果数字 1 如果在队列中，那么数字 2 必然也在队列中，使得数字 1 对结果没有影响。
 * <p>
 * 按照上面的思路，我们可以设计这样的方法：
 * 从队列尾部插入元素时，我们可以提前取出队列中所有比这个元素小的元素，使得队列中只保留对结果有影响的数字。
 * 这样的方法等价于要求维持队列单调递减，即要保证每个元素的前面都没有比它小的元素。
 */
public class MaxQueue {

    private Queue<Integer> queue;// 标准队列
    private Deque<Integer> maxDeque;// 单调双端队列，用来存储一个单调递减的元素数组

    public MaxQueue() {
        queue = new LinkedList<>();
        maxDeque = new LinkedList<>();
    }

    public int max_value() {
        return maxDeque.isEmpty() ? -1 : maxDeque.peek();
    }

    public void push_back(int value) {
        queue.offer(value);
        // 单调递减队列，移除掉之前比当前元素小的元素，保持队列的单调递减性，
        while (!maxDeque.isEmpty() && value > maxDeque.peekLast()) {
            maxDeque.pollLast();
        }
        maxDeque.offer(value);
    }

    public int pop_front() {
        // 队列空，返回-1，否则返回队头元素
        if (queue.isEmpty()) {
            return -1;
        }
        int value = queue.poll();
        if (value == maxDeque.peek()) {
            maxDeque.pop();
        }
        return value;
    }
}
