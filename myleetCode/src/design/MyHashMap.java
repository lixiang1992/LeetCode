package design;

import java.util.Objects;

/**
 * 706. 设计哈希映射
 * 不使用任何内建的哈希表库设计一个哈希映射
 * <p>
 * 具体地说，你的设计应该包含以下的功能
 * <p>
 * put(key, value)：向哈希映射中插入(键,值)的数值对。如果键对应的值已经存在，更新这个值。
 * get(key)：返回给定的键所对应的值，如果映射中不包含这个键，返回-1。
 * remove(key)：如果映射中存在这个键，删除这个数值对。
 */
public class MyHashMap {

    private Node[] tab;
    /**
     * Initialize your data structure here.
     */
    public MyHashMap() {
        tab = new Node[128];
    }

    /**
     * value will always be non-negative.
     */
    public void put(int key, int value) {
        int hash = hash(key);
        Node node = tab[(tab.length - 1) & hash];
        Node prev = null;
        if (node == null){
            tab[(tab.length - 1) & hash] = new Node(hash,key,value,null);
        }else{
            while (node != null){
                if (node.hash == hash && node.key == key){
                    node.value = value;
                    return;
                }
                prev = node;
                node = node.next;
            }
            prev.next = new Node(hash,key,value,null);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
     */
    public int get(int key) {
        int hash = hash(key);
        Node node = tab[(tab.length - 1) & hash];
        while (node != null){
            if (node.hash == hash && node.key == key){
                return node.value;
            }
            node = node.next;
        }
        return -1;
    }

    /**
     * Removes the mapping of the specified value key if this map contains a mapping for the key
     */
    public void remove(int key) {
        int hash = hash(key);
        Node node = tab[(tab.length - 1) & hash];
        Node prev = null;
        while (node != null){
            if (node.hash == hash && node.key == key){
                if (prev != null){
                    prev.next = node.next;
                }else {
                    tab[(tab.length - 1) & hash] = node.next;
                }
                node.next = null;// help GC
                node = null;// help GC
                return;
            }
            prev = node;
            node = node.next;
        }
    }

    private static int hash(int key){
        return key;
    }

    private static class Node {
        final int hash;
        int key;
        int value;
        Node next;

        Node(int hash, int key, int value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return key == node.key &&
                    value == node.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
}
