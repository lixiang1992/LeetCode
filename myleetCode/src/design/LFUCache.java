package design;

import java.util.HashMap;
import java.util.Map;

/**
 * 460.最不经常使用缓存
 * 采用二维链表的设计思路
 */
public class LFUCache {

    private Map<Integer,Node> cache;// 每个节点的cache
    private Map<Node,NodeList> nodeListHeadMap;// 主链表头部节点的cache
    private NodeList tailList;// 整个主链表的尾部主链节点，用来快速定位需要删除的节点链表
    private int capacity;// 容量

    public LFUCache(int capacity) {
        this.cache = new HashMap<>();
        this.nodeListHeadMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (capacity <= 0){
            return -1;
        }
        Node node = cache.get(key);
        if (node == null){
            return -1;
        }
        int value = node.value;
        node.count++;
        // 节点移动处理
        moveNode(node);
        return value;
    }

    public void put(int key, int value) {
        if (capacity <= 0){
            return;
        }
        Node node = cache.get(key);
        if (node != null){
            node.value = value;
            node.count++;
            // 节点移动处理
            moveNode(node);
        }else {
            if(cache.size() == capacity){
                Node deleteNode = tailList.tail;// 容量满了，需要删除的节点
                tailList.deleteListNode(deleteNode);// 主链表删除从节点
                updateNodeList(tailList);// 删除节点后，可能整个主链表都没有了，这里需要更新主链表关系
                cache.remove(deleteNode.key);// 移除节点的key
                nodeListHeadMap.remove(deleteNode);// 移除从链节点和主链节点的关系
            }
            node = new Node(key,value);
            if (tailList == null){// 主链表本身都不存在，这个时候初始化主链表
                tailList = new NodeList(node);
            }else if(tailList.head.count > node.count){// 主链尾节点的使用频率比新节点高，要新建一个主链节点，并且更新尾部
                NodeList newList = new NodeList(node);
                tailList.next = newList;
                newList.prev = tailList;
                tailList = newList;
            }else {// 主链尾部节点使用频率等于新节点，新节点直接加入主链的头部
                tailList.addToListHead(node);
            }
            cache.put(key,node);// 进入缓存
            nodeListHeadMap.put(node,tailList);// 新节点的主链表节点一定在尾部
        }
    }

    /**
     * 节点移动
     * @param node
     */
    private void moveNode(Node node) {
        NodeList oldNodeList = nodeListHeadMap.get(node);// 获取节点对应的主链表节点
        oldNodeList.deleteListNode(node);// 之前的主链表删掉node
        // 这个时候nodeList可能一个元素都没有了，要判断是不是要把nodeList移除出主链表，同时获取当前节点的后一个节点（不移除就是自身）
        NodeList nextList = getNextNodeList(oldNodeList);// nextList 很有可能已经是空的了，所以接下来都要做空指针的判断
        NodeList prevList = oldNodeList.prev;// 前一个主链表节点，新节点要去的地方
        if (prevList == null){// 前面没有新的主链表了
            prevList = new NodeList(node);// 当前节点作为最新的主链表节点
            nodeListHeadMap.put(node,prevList);// 更新节点和主链表节点的映射

            // 主链表节点的关系建立
            prevList.next = nextList;
            if (nextList != null){
                nextList.prev = prevList;
            }
            // 动态更新主链表的头部
            if (tailList == null){
                tailList = prevList;
            }
        } else if(prevList.head.count > node.count){// 前一个主链表节点的使用数比当前使用节点多
            NodeList currentList = new NodeList(node);// 自己单独成一个节点
            nodeListHeadMap.put(node,currentList);// 更新节点和主链表节点的映射

            // 主链表映射关系设置
            prevList.next = currentList;
            currentList.prev = prevList;
            if (nextList != null){
                nextList.prev = currentList;
            }
            currentList.next = nextList;

            // 更换尾部节点
            if (tailList == prevList){
                tailList = currentList;
            }
        }else {
            // 前一个主链表节点的头部节点使用次数和当前节点相同，直接将node加入进从链的头部
            prevList.addToListHead(node);
            nodeListHeadMap.put(node,prevList);// 更新节点和主链表节点的映射
        }


    }

    /**
     * 获取下一个主链节点
     * 如果nodeList这个主链表已经空了，就获取主链表的下一个节点，否则获取本身就行了
     * @param nodeList
     * @return
     */
    private NodeList getNextNodeList(NodeList nodeList){
        return updateNodeList(nodeList) ? nodeList.next : nodeList;
    }

    /**
     * 更新主链表
     * @param nodeList 新的链表节点
     */
    private boolean updateNodeList(NodeList nodeList) {
        if (nodeList.isEmpty()){
            // 尾部都不存在了，要移除这个主链节点
            if(tailList == nodeList){
                // 要删除的是尾部
                tailList = tailList.prev;
                if(tailList != null){// 防空指针
                    tailList.next = null;
                }
            }else {
                nodeList.next.prev = nodeList.prev;
                if (nodeList.prev != null){
                    nodeList.prev.next = nodeList.next;
                }
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * 从链节点
     */
    private static class Node{
        int key;
        int value;
        int count;

        Node prev;// 从链的前驱节点
        Node next;// 从链的后继节点

        Node(int key, int value) {
            this(key,value,1);
        }

        Node(int key, int value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }
    }

    /**
     * 主链节点，本身也是一个从链节点
     */
    private static class NodeList {
        NodeList prev;// 主链前驱
        NodeList next;// 主链后继

        Node head;// 每个主链节点的从链节点头部
        Node tail;// 每个主链节点的从链尾部节点

        NodeList(Node node){
            head = node;
            tail = node;
        }

        // 从链节点加入链表
        void addToListHead(Node node){
            node.next = head;
            head.prev = node;
            head = node;
        }

        // 删除主链中的某一个从链节点
        void deleteListNode(Node node){
            if (head == tail){// 一个节点的情况
                head = null;
                tail = null;
            }else {
                if (head == node){// 删除的是头部
                    head = head.next;
                    head.prev = null;
                }else if (tail == node){// 删除的是尾部
                    tail = tail.prev;
                    tail.next = null;
                }else {// 删除的是中间
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
            }
            node.prev = null;
            node.next = null;
        }

        public boolean isEmpty(){
            return head == null;
        }
    }

}
