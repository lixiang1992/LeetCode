package design;

import java.util.Stack;

/**
 * 232. 用栈实现队列
 */
public class MyQueue {
    private Stack<Integer> stack1;// 负责进
    private Stack<Integer> stack2;// 负责出
    private int size = 0;
    /** Initialize your data structure here. */
    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        stack1.push(x);
        size++;
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if (stack2.isEmpty()){
            while(!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        size--;
        return stack2.pop();
    }

    /** Get the front element. */
    public int peek() {
        if (stack2.isEmpty()){
            while(!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        return stack2.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return size == 0;
    }
}