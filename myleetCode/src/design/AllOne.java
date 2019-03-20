package design;

import java.util.HashMap;
import java.util.Map;

/**
 * 432.实现一个数据结构支持以下操作：
 * <p>
 * Inc(key) - 插入一个新的值为 1 的 key。或者使一个存在的 key 增加一，保证 key 不为空字符串。
 * Dec(key) - 如果这个 key 的值是 1，那么把他从数据结构中移除掉。否者使一个存在的 key 值减一。如果这个 key 不存在，这个函数不做任何事情。key 保证不为空字符串。
 * GetMaxKey() - 返回 key 中值最大的任意一个。如果没有元素存在，返回一个空字符串""。
 * GetMinKey() - 返回 key 中值最小的任意一个。如果没有元素存在，返回一个空字符串""。
 * 挑战：以 O(1) 的时间复杂度实现所有操作。
 */
public class AllOne {

    private Map<String, Node> cache;
    private Node head;// 最大的节点，当作优先队列的头
    private Node tail;// 最小的节点，当作优先队列的尾

    private static class Node{
        String key;
        int val;
        Node prev;
        Node next;

        Node(String key,int val){
            this.key = key;
            this.val = val;
        }
    }

    public AllOne() {
        cache = new HashMap<>();
        head = new Node("",0);
        tail = new Node("",0);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 插入一个新的值为 1 的 key。或者使一个存在的 key 增加一，保证 key 不为空字符串。
     */
    public void inc(String key) {
        Node node = cache.get(key);
        if (node == null){
            node = new Node(key,1);
            cache.put(key,node);
            addNodeToTail(node);
        }else{
            node.val++;
            if (node.val >= head.next.val){// 这里必须是大于等于，否则在相等的时候，node可能取代head节点的位置，导致head节点失效
                removeNode(node);
                addNodeToHead(node);
            }else if (node.val > node.prev.val){
                Node prev = node.prev;
                Node next = node.next;
                // 移除node
                prev.next = next;
                next.prev = prev;
                // node插入到prev和prev.prev中
                node.next = prev;
                node.prev = prev.prev;
                prev.prev.next = node;
                prev.prev = node;
            }
        }
    }

    /**
     * Dec(key) - 如果这个 key 的值是 1，那么把他从数据结构中移除掉。否者使一个存在的 key 值减一。如果这个 key 不存在，这个函数不做任何事情。key 保证不为空字符串。
     */
    public void dec(String key) {
        Node node = cache.get(key);
        if (node == null){
        }else if(node.val == 1){
            cache.remove(key);
            removeNode(node);
        }else {
            node.val--;
            if (node.val <= tail.prev.val){// 这里必须是大于等于，否则在相等的时候，node可能取代tail节点的位置，导致tail节点失效
                removeNode(node);
                addNodeToTail(node);
            }else if (node.val < node.next.val){
                Node prev = node.prev;
                Node next = node.next;
                // 移除node
                prev.next = next;
                next.prev = prev;
                // node插入到next和next.next中
                node.prev = next;
                node.next = next.next;
                next.next.prev = node;
                next.next = node;
            }
        }
    }

    /**
     * GetMaxKey() - 返回 key 中值最大的任意一个。如果没有元素存在，返回一个空字符串""。
     */
    public String getMaxKey() {
        if (cache.isEmpty()){
            return "";
        }
        return head.next.key;
    }

    /**
     * GetMinKey() - 返回 key 中值最小的任意一个。如果没有元素存在，返回一个空字符串""。
     */
    public String getMinKey() {
        if (cache.isEmpty()){
            return "";
        }
        return tail.prev.key;
    }

    private void removeNode(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * 头部添加节点
     * @param node 当期节点
     */
    private void addNodeToHead(Node node){
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    /**
     * 尾部添加节点
     * @param node 当前节点
     */
    private void addNodeToTail(Node node){
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
    }
}
