package design;

import java.util.*;

/**
 * 380.设计一个支持在平均 时间复杂度 O(1) 下，执行以下操作的数据结构。
 *
 * insert(val)：当元素 val 不存在时，向集合中插入该项。
 * remove(val)：元素 val 存在时，从集合中移除该项。
 * getRandom：随机返回现有集合中的一项。每个元素应该有相同的概率被返回。
 */
public class RandomizedSet {

    private Map<Integer,Integer> valMap;
    private List<Integer> list;
    private Random random;
    private int size = 0;
    public RandomizedSet() {
        valMap = new HashMap<>();
        list = new ArrayList<>();
        random = new Random();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (valMap.containsKey(val)){
            return false;
        }
        valMap.put(val,size);
        list.add(size,val);
        size++;
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!valMap.containsKey(val)){
            return false;
        }
        // 将尾部元素转化为要删除的元素，尾部元素赋值到当前要删除的元素上，类似于二叉搜索树的节点删除
        int oldIndex = valMap.get(val);// 要删除元素的index
        int tail = list.get(--size);// 尾部元素的值
        list.set(oldIndex,tail);// 元素替换
        valMap.put(tail,oldIndex);// tail元素对应的下标替换
        valMap.remove(val);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return list.get(random.nextInt(size));
    }
}
