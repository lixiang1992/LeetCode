package design.hard;

import java.util.Deque;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 1172.餐盘栈
 */
public class DinnerPlates {

    private int capacity;
    // 使用的栈，没有满的
    private TreeMap<Integer,Deque<Integer>> useMap = new TreeMap<>();
    // 满了的栈
    private TreeMap<Integer,Deque<Integer>> fullMap = new TreeMap<>();
    // 空了的栈，编号小于 userMap与fullMap最大值的才存进来，否则就不要了
    private TreeSet<Integer> emptySet = new TreeSet<>();

    public DinnerPlates(int capacity) {
        this.capacity = capacity;
        for(int i = 0;i <= 100000;i++){
            emptySet.add(i);
        }
    }

    public void push(int val) {
        Integer index;
        if(!useMap.isEmpty()){
            // 可以使用的栈编号，是use和empty中的较小的那个
            index = Math.min(useMap.firstKey(),emptySet.first());
            if(index.equals(emptySet.first())){
                index = emptySet.pollFirst();
            }
        } else {
            // 从空栈取
            index = emptySet.pollFirst();
        }
        Deque<Integer> stack = useMap.computeIfAbsent(index,k->new LinkedList<>());
        stack.push(val);
        // 栈满了，加入满的集合
        if(stack.size() == capacity){
            useMap.remove(index);
            fullMap.put(index,stack);
        }
    }

    public int pop() {
        int userIndex = getMaxIndex(useMap);
        int fullIndex = getMaxIndex(fullMap);
        int index = Math.max(userIndex,fullIndex);
        int val = -1;
        if(index == -1){
            return val;
        }
        if(index == userIndex){
            Deque<Integer> stack = useMap.get(index);
            val = stack.pop();
            if(stack.isEmpty()){
                useMap.remove(index);
                emptySet.add(index);
            }
        } else {
            Deque<Integer> stack = fullMap.get(index);
            val = stack.pop();
            fullMap.remove(index);
            if(stack.isEmpty()){
                emptySet.add(index);
            } else {
                useMap.put(index,stack);
            }
        }
        return val;
    }

    public int popAtStack(int index) {
        Deque<Integer> stack = fullMap.get(index);
        int val;
        if(stack != null){
            val = stack.pop();
            fullMap.remove(index);
            if(stack.isEmpty()){
                emptySet.add(index);
            } else {
                useMap.put(index,stack);
            }
        } else {
            stack = useMap.get(index);
            val = stack.pop();
            if(stack.isEmpty()){
                useMap.remove(index);
                emptySet.add(index);
            }
        }
        return val;
    }

    private int getMaxIndex(TreeMap<Integer,Deque<Integer>> map){
        return map.isEmpty() ? -1 : map.lastKey();
    }
}
