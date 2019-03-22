package heap;

import java.util.PriorityQueue;

/**
 * 703.数据流中第K大元素
 */
public class KthLargest {
    private PriorityQueue<Integer> pq;
    private int size;
    public KthLargest(int k, int[] nums) {
        size = k;
        pq = new PriorityQueue<>(size);
        for (int i=0;i<nums.length;i++){
            add(nums[i]);
        }
    }

    public int add(int val) {
        if (pq.size() < size){
            pq.offer(val);
        }else if(pq.peek() < val){
            pq.poll();
            pq.offer(val);
        }
        return pq.peek();
    }
}
