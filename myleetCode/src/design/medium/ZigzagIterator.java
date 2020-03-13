package design.medium;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 281. 锯齿迭代器
 * 给出两个一维的向量，请你实现一个迭代器，交替返回它们中间的元素。
 * 拓展：假如给你 k 个一维向量呢？你的代码在这种情况下的扩展性又会如何呢?
 */
public class ZigzagIterator {

//    private List<Integer> zigzagList;
    private Queue<LinkedList<Integer>> zigzagQueue;
//    private int size = 0;
//    private int currentIndex = 0;

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        // 有k个就改为K
        zigzagQueue = new LinkedList<>();
        if (!v1.isEmpty()){
            zigzagQueue.offer(new LinkedList<>(v1));
        }
        if (!v2.isEmpty()){
            zigzagQueue.offer(new LinkedList<>(v2));
        }
//        List<Integer>[] lists = new ArrayList[2];
//        lists[0] = v1;
//        lists[1] = v2;
//        int length = v1.size() + v2.size();
//        zigzagList = new ArrayList<>(length);
//        int index = 0;
//        while(size < length){
//            for (List<Integer> list : lists) {
//                addElem(list, index);
//            }
//            index++;
//        }
    }

//    private void addElem(List<Integer> list,int index){
//        if (index >= list.size()){
//            return;
//        }
//        zigzagList.add(list.get(index));
//        size++;
//    }

    public int next() {
        LinkedList<Integer> list = zigzagQueue.poll();
        int value = list.poll();
        if (list.size() > 0){
            zigzagQueue.offer(list);
        }
        return value;
    }

    public boolean hasNext() {
        return !zigzagQueue.isEmpty();
    }
}
