package design.medium;

import java.util.*;

/**
 * 146.最近最少使用缓存机制
 */
public class LRUCache {

    private class Node{
        private Node prev;
        private Node next;
        private int key;
        private int value;

        Node(int key,int value){
            this.key = key;
            this.value = value;
        }
    }

    private Node head;// 最近最少使用，类似列队的头，出队
    private Node tail;// 最近最多使用，类似队列的尾，入队
    private Map<Integer,Node> cache;
    private int capacity;

    public LRUCache(int capacity) {
        this.cache = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if(node == null){
            return -1;
        }else{
            moveNode(node);
            return node.value;
        }
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node != null){
            node.value = value;
            moveNode(node);
        }else {
            removeHead();
            node = new Node(key,value);
            addNode(node);
        }
        cache.put(key,node);
    }

    private void removeHead(){
        if (cache.size() == capacity){
            Node tempNode = head;
            cache.remove(head.key);
            head = head.next;
            tempNode.next = null;
            if (head != null){
                head.prev = null;
            }
        }
    }

    /**
     * 链表加入节点
     * @param node
     */
    private void addNode(Node node){
        if (head == null){
            head = node;
            tail = node;
        }else {
            addNodeToTail(node);
        }
    }

    private void addNodeToTail(Node node){
        node.prev = tail;
        tail.next = node;
        tail = node;
    }

    private void moveNode(Node node){
        if(head == node && node != tail){
            head = node.next;
            head.prev = null;
            node.next = null;
            addNodeToTail(node);
        }else if (tail == node){
            // 不做任何操作
        }else {
            // 普遍情况，node节点移除链表，加入到尾节点后面，tail指针指向node
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            addNodeToTail(node);
        }
    }
}
