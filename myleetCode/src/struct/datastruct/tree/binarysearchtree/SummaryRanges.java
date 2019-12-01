package struct.datastruct.tree.binarysearchtree;

import struct.datastruct.tree.pub.Interval;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * 352.将数据流变为多个不相交间隔
 * 给定一个非负整数的数据流输入 a1，a2，…，an，…，将到目前为止看到的数字总结为不相交的间隔列表。
 *
 * 例如，假设数据流中的整数为 1，3，7，2，6，…，每次的总结为：
 * [1, 1]
 * [1, 1], [3, 3]
 * [1, 1], [3, 3], [7, 7]
 * [1, 3], [7, 7]
 * [1, 3], [6, 7]
 */
public class SummaryRanges {

    // 利用TreeSet内部的红黑树，定位到区间所在
    private TreeSet<Interval> intervalSet;

    public SummaryRanges() {
        intervalSet = new TreeSet<>((o1, o2) -> o1.start - o2.start);
    }

    public void addNum(int val) {
        Interval interval = new Interval(val,val);
        //返回这个集合中最大的元素小于或等于给定元素，如果没有这样的元素，则返回null
        Interval floor = intervalSet.floor(interval);
        if (floor != null){
            if (floor.end >= val){
                return;
            }else if (floor.end + 1 == val){
                // 新区间和floor区间合并，旧的floor移除
                interval.start = floor.start;
                intervalSet.remove(floor);
            }
        }
        //返回这个集合中最小元素大于或等于给定元素，如果没有这样的元素，则返回null
        Interval higher = intervalSet.ceiling(interval);
        if (higher != null && higher.start - 1 == val){
            // 新区间和higher区间合并，旧的higher移除
            interval.end = higher.end;
            intervalSet.remove(higher);
        }
        intervalSet.add(interval);
    }

    public List<Interval> getIntervals() {
        return Arrays.asList(intervalSet.toArray(new Interval[0]));
    }
}
