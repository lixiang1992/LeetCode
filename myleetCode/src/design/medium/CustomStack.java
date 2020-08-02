package design.medium;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class CustomStack {

    private Deque<Integer> deque = new LinkedList<>();
    private int size;

    public CustomStack(int maxSize) {
        size = maxSize;
    }

    public void push(int x) {
        if (deque.size() < size){
            deque.offer(x);
        }
    }

    public int pop() {
        return deque.isEmpty() ? -1 : deque.pollLast();
    }

    public void increment(int k, int val) {
        Queue<Integer> queue = new LinkedList<>();
        Deque<Integer> tempQueue = new LinkedList<>();
        while (k < deque.size()){
            // 修改时k < deque.size()
            tempQueue.push(deque.pollLast());
        }
        while (!deque.isEmpty()){
            int temp = deque.poll();
            queue.offer(temp+val);
        }
        deque.addAll(queue);
        if (!tempQueue.isEmpty()){
            deque.addAll(tempQueue);
        }
    }
}
