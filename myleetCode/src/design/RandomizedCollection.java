package design;

import java.util.*;

/**
 * 381.设计一个支持在平均 时间复杂度 O(1) 下， 执行以下操作的数据结构。
 * <p>
 * 注意: 允许出现重复元素。
 * <p>
 * insert(val)：向集合中插入元素 val。
 * remove(val)：当 val 存在时，从集合中移除一个 val。
 * getRandom：从现有集合中随机获取一个元素。每个元素被返回的概率应该与其在集合中的数量呈线性相关。
 */
public class RandomizedCollection {

    private Map<Integer, Set<Integer>> valIndexMap;// 每个value在valueList中的位置
    private List<Integer> valueList;
    private Random random;
    private int size = 0;


    public RandomizedCollection() {
        valIndexMap = new HashMap<>();
        valueList = new ArrayList<>();
        random = new Random();
    }

    /**
     * 插入一个元素，如果存在返回false，不存在返回true
     * @param val
     * @return
     */
    public boolean insert(int val) {
        Set<Integer> indexSet = valIndexMap.get(val);
        boolean flag;
        if (flag = indexSet == null){
            indexSet = new HashSet<>();
            valIndexMap.put(val,indexSet);
        }
        indexSet.add(size);// 元素的下标链表添加元素
        valueList.add(size,val);// val插入size的位置
        size++;
        return flag;
    }

    /**
     * Removes a value from the collection. Returns true if the collection contained the specified element.
     */
    public boolean remove(int val) {
        Set<Integer> indexSet = valIndexMap.get(val);// 需要移除元素的下标的列表
        if (indexSet == null){
            return false;
        }
        if(valueList.get(size - 1) == val){
            indexSet.remove(size - 1);
        }else{
            Iterator<Integer> iterator = indexSet.iterator();
            int oldFirstIndex = iterator.next();// 找到val元素下标列表的第一个下标
            iterator.remove();// 移除第一个元素
            int tailValue = valueList.get(size-1);// 值链表的尾部元素
            valueList.set(oldFirstIndex,tailValue);// 尾部元素替换移除的元素
            Set<Integer> tailIndexSet = valIndexMap.get(tailValue);// 尾部元素所有的下标列表
            tailIndexSet.remove(size - 1);// 移除掉最后一个
            tailIndexSet.add(oldFirstIndex);// val所在的第一个下标加入
        }
        if(indexSet.isEmpty()){
            valIndexMap.remove(val);
        }
        size--;
        return true;
    }

    /**
     * Get a random element from the collection.
     */
    public int getRandom() {
        return valueList.get(random.nextInt(size));
    }
}
