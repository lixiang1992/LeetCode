package design;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 295.数据流的中位数
 * 采用两个堆，一个小顶堆一个大顶堆，两个堆大小的绝对值不能超过1
 */
public class MedianFinder {

    private Queue<Integer> maxHeap;// 大顶堆
    private Queue<Integer> minHeap;// 小顶堆

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
        maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        minHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if (maxHeap.isEmpty()) {
            maxHeap.offer(num);
            return;
        }
        if (num > maxHeap.peek()) {// 比大顶堆堆顶元素大，进入对面的小顶堆
            minHeap.offer(num);
        } else {// 比大顶堆堆顶元素小，进入大顶堆
            maxHeap.offer(num);
        }
        if (maxHeap.size() - minHeap.size() > 1) {// 平衡元素，将大顶堆的堆顶元素加入小顶堆
            minHeap.offer(maxHeap.poll());
        } else if (maxHeap.size() - minHeap.size() < -1) {// 平衡元素，将小顶堆的堆顶元素加入大顶堆
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        int maxSize = maxHeap.size();
        int minSize = minHeap.size();
        if(maxSize == minSize) {
            if(maxSize==0){// 空的返回0
                return 0;
            }
            // 否则取两者的平均数
            return (maxHeap.peek()+minHeap.peek())/(double)2;
        }
        if(maxSize > minSize) {
            return maxHeap.peek();
        }
        return minHeap.peek();
    }
}
