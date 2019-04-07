package design;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 225.用队列实现栈
 */
public class MyStack {

    private Queue<Integer> queue1;// 队列1
    private Queue<Integer> queue2;// 队列2
    private int size = 0;
    public MyStack() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        if (queue2.isEmpty()){
            queue1.offer(x);
        }else{
            queue2.offer(x);
        }
        size++;
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        int top;
        if (queue2.isEmpty()){
            while (queue1.size() > 1){
                queue2.offer(queue1.poll());
            }
            top = queue1.poll();
        }else {
            while (queue2.size() > 1){
                queue1.offer(queue2.poll());
            }
            top = queue2.poll();
        }
        size--;
        return top;
    }

    /** Get the top element. */
    public int top() {
        int top = 0;
        if (queue2.isEmpty()){
            while (!queue1.isEmpty()){
                top = queue1.poll();
                queue2.offer(top);
            }
        }else {
            while (!queue2.isEmpty()){
                top = queue2.poll();
                queue1.offer(top);
            }
        }
        return top;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return size == 0;
    }
}
