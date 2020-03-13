package design.medium;

import java.util.*;

/**
 * 379.电话目录管理系统
 */
public class PhoneDirectory {

    private Set<Integer> existPhone;// 存在的电话
    private Queue<Integer> queue;// 未分配的电话

    /** Initialize your data structure here
     @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
    public PhoneDirectory(int maxNumbers) {
        this.existPhone = new HashSet<>(maxNumbers);
        // 初始化未分配的电话
        this.queue = new LinkedList<>();
        int i = 0;
        while(i<maxNumbers){
            queue.offer(i++);
        }
    }

    /** Provide a number which is not assigned to anyone.
     @return - Return an available number. Return -1 if none is available. */
    public int get() {
        if (queue.isEmpty()){
            return -1;
        }
        // 分配号码
        int phone = queue.poll();
        // 存下来
        existPhone.add(phone);
        return phone;
    }

    /** Check if a number is available or not. */
    public boolean check(int number) {
        // 号码是否未分配
        return !existPhone.contains(number);
    }

    /** Recycle or release a number. */
    public void release(int number) {
        // 移除分配号码，移除的号码加入到未分配列表中
        if (existPhone.remove(number)){
            queue.offer(number);
        }
    }
}
