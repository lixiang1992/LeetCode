package design;

import java.util.HashMap;
import java.util.Map;

/**
 * 460.最不经常使用缓存,未完成
 */
public class LFUCache {

    private static class Node{
        int key;// key
        int value;// value
        Node prev;// 前驱指针
        Node next;// 后继指针

        Node(int key,int value){
            this.key = key;
            this.value = value;
        }
    }
    private Map<Integer,Node> cache;// 缓存
    private Node head;// 队头,最不经常使用
    private Node tail;// 队尾,最经常使用
    private int capacity;// 容量

    public LFUCache(int capacity) {
        cache = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null){
            return -1;
        }
        int value = node.value;
        // node指针移除队列
        node.prev.next = node.next;
        node.next.prev = node.prev;
        return value;
    }

    private void moveNode(Node node){
    }

    /**
     * 头插法，新节点插入头部
     * @param node
     */
    private void addNodeToHead(Node node){
        if(head == null){
            head = tail = node;
        }else {
            head.prev = node;
            node.next = head;
            head = node;
        }
    }

    public void put(int key, int value) {
        Node node = cache.get(key);

    }

}
