package design;

/**
 * 707. 设计链表
 * 设计链表的实现。您可以选择使用单链表或双链表。
 * 单链表中的节点应该具有两个属性：val 和 next。val 是当前节点的值，next 是指向下一个节点的指针/引用。
 * 如果要使用双向链表，则还需要一个属性 prev 以指示链表中的上一个节点。假设链表中的所有节点都是 0-index 的。
 */
public class MyLinkedList {

    private Node head;// 首节点
    private Node tail;// 尾节点
    private int size = 0;

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {

    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }
        return findNode(index).element;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        Node node = head;
        Node newNode = new Node(null, val, node);
        head = newNode;
        if (node == null) {// 之前首节点是空的，则表示尾节点也是空的，需要设置尾节点的指针
            tail = newNode;
        } else {
            node.prev = newNode;// 旧的首节点的prev指针指向新的节点
        }
        size++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        Node node = tail;
        Node newNode = new Node(tail, val, null);
        tail = newNode;
        if (node == null) {
            head = newNode;
        } else {
            node.next = newNode;
        }
        size++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index > size) {
            return;
        }
        if (index == size){
            addAtTail(val);
        }else {
            Node node = findNode(index);
            Node prev = node.prev;
            Node newNode = new Node(prev,val,node);
            if (prev == null){
                head = newNode;
            }else {
                prev.next = newNode;
            }
            node.prev = newNode;
            size++;
        }
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        Node node = findNode(index);
        if (node.prev == null){
            head = node.next;
            node.next.prev = null;
            node.next = null;// help GC
        }else if (node.next == null){
            tail = node.prev;
            node.prev.next = null;
            node.prev = null;// help GC
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;// help GC
            node.next = null;// help GC
        }
        size--;
    }

    /**
     * 通过下标找到节点
     *
     * @param index
     * @return
     */
    private Node findNode(int index) {
        if (index <= (size >> 1)){// index靠近头节点，就从头节点开始遍历
            int temp = 0;
            Node node = head;
            while (temp < index){
                node = node.next;
                temp++;
            }
            return node;
        } else {
            int temp = size - 1;
            Node node = tail;
            while (temp > index){
                node = node.prev;
                temp--;
            }
            return node;
        }
    }

    private static class Node {
        Integer element;
        Node next;
        Node prev;

        Node(Node prev, Integer element, Node next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
