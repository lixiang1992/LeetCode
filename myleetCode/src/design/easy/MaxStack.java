package design.easy;

import java.util.*;

/**
 * 716.最大栈
 * 设计一个最大栈，支持 push、pop、top、peekMax 和 popMax 操作。
 * <p>
 * push(x) -- 将元素 x 压入栈中。
 * pop() -- 移除栈顶元素并返回这个值。
 * top() -- 返回栈顶元素。
 * peekMax() -- 返回栈中最大元素。
 * popMax() -- 返回栈中最大的元素，并将其删除。如果有多个最大元素，只要删除最靠近栈顶的那个。
 *
 * 思路：利用红黑树来找最大元素
 */
public class MaxStack {

    private Deque<Integer> m_stack;
    // key : 元素 value : 元素个数
    private TreeMap<Integer,Integer> m_treeMap;

    /**
     * initialize your data structure here.
     */
    public MaxStack() {
        // 初始化
        m_stack = new LinkedList<>();
        m_treeMap = new TreeMap<>();
    }

    public void push(int x) {
        m_stack.push(x);
        m_treeMap.put(x,m_treeMap.getOrDefault(x,0) + 1);
    }

    public int pop() {
        int x = m_stack.pop();
        int count = m_treeMap.get(x);
        // 元素个数的维护
        if (count == 1){
            m_treeMap.remove(x);
        }else {
            m_treeMap.put(x,count-1);
        }
        return x;
    }

    public int top() {
        return m_stack.peek();
    }

    public int peekMax() {
        return m_treeMap.lastKey();
    }

    public int popMax() {
        // 获取最大元素
        Map.Entry<Integer,Integer> entry = m_treeMap.lastEntry();
        int x = entry.getKey();// 最大元素
        // 元素个数维护
        if (entry.getValue() == 1){
            m_treeMap.remove(entry.getKey());
        }else {
            m_treeMap.put(entry.getKey(),entry.getValue() - 1);
        }
        // 从栈中删除元素
        m_stack.remove(x);
        return x;
    }
}
