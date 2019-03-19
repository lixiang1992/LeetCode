package design;

import java.util.TreeMap;

/**
 * 百度搜到的我的日程安排二，leetCode 731 实现逻辑还没看懂
 * TODO 研究其逻辑
 */
public class MyCalendarTwo2 {
    private TreeMap<Integer, Integer> treeMap;

    public MyCalendarTwo2() {
        treeMap = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        int a = treeMap.getOrDefault(start, 0);
        int b = treeMap.getOrDefault(end, 0);
        treeMap.put(start, a + 1);
        treeMap.put(end, b - 1);
        int count = 0;

        for (Integer val : treeMap.values()) {
            count += val;//记录当前已开始但未结束的事件个数
            if (count > 2) {//如果事件个数>2，则说明有三个或者以上的重叠，不满足条件，要取消刚刚的插入
                if (a == 0) {//如果插入前的个数为0则可以直接删除这条记录，否则对次数进行更改
                    treeMap.remove(start);
                } else {
                    treeMap.put(start, a);
                }
                if (b == 0) {
                    treeMap.remove(end);
                } else {
                    treeMap.put(end, b);
                }
                return false;
            }
        }
        return true;
    }
}
