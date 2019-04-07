package design;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 155.最小栈
 */
public class MinStack {

    private Deque<Integer> stack;// 标准栈
    private Deque<Integer> deque;// 记录最小元素

    /** initialize your data structure here. */
    public MinStack() {
        stack = new LinkedList<>();
        deque = new LinkedList<>();
    }

    public void push(int x) {
        stack.push(x);
        if (deque.isEmpty() || deque.peekLast() >= x){
            // 链式双端队列的话，这里必须要用>=否则有相同元素进入，队列没有记录元素，但是弹出的时候，会把对应的值弹出，造成npt问题
            deque.offer(x);
        }
    }

    public void pop() {
        int top = stack.pop();
        if (top <= deque.peekLast()){
            deque.pollLast();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return deque.peekLast();
    }
}
