package design;


import com.sun.deploy.panel.JHighDPITable;

import java.util.Objects;

/**
 * 705. 设计哈希集合
 * 不使用任何内建的哈希表库设计一个哈希集合
 * <p>
 * 具体地说，你的设计应该包含以下的功能
 * <p>
 * add(value)：向哈希集合中插入一个值。
 * contains(value) ：返回哈希集合中是否存在这个值。
 * remove(value)：将给定值从哈希集合中删除。如果哈希集合中没有这个值，什么也不做。
 */
public class MyHashSet {

    private Node[] tab;
    private static final int LENGHT = 64;
    /** Initialize your data structure here. */
    public MyHashSet() {
        tab = new Node[LENGHT];
    }

    public void add(int key) {
        int hash = hash(key);
        Node node = tab[(LENGHT - 1) & hash];
        Node prev = null;
        if (node == null){
            tab[(LENGHT - 1) & hash] = new Node(hash,key,null);
        } else {
            while (node != null){
                if (node.hash == hash && node.key == key){
                    return;// 找到了相同元素，直接返回
                }
                prev = node;
                node = node.next;
            }
            prev.next = new Node(hash,key,null);
        }
    }

    public void remove(int key) {
        int hash = hash(key);
        Node node = tab[(LENGHT - 1) & hash];
        Node prev = null;
        while (node != null){
            if (node.hash == hash && node.key == key){
                if (prev != null){
                    prev.next = node.next;
                }else {
                    tab[(LENGHT - 1) & hash] = node.next;
                }
                node.next = null;// help GC
                return;
            }
            prev = node;
            node = node.next;
        }
    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        int hash = hash(key);
        Node node = tab[(LENGHT - 1) & hash];
        while (node != null){
            if (node.hash == hash && node.key == key){
                return true;
            }
            node = node.next;
        }
        return false;
    }

    /**
     * key
     * @param key
     * @return
     */
    private static int hash(int key){
        return key;
    }

    private static class Node {
        final int hash;// hash值生成后不能修改
        int key;
        Node next;

        public Node(int hash,int key,Node next) {
            this.hash = hash;
            this.key = key;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return key == node.key;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
}
