package struct.datastruct.tree.binarysearchtree;

import java.util.TreeSet;

/**
 * 731. 我的日程安排表 II
 * 实现一个 MyCalendar 类来存放你的日程安排。如果要添加的时间内不会导致三重预订时，则可以存储这个新的日程安排。
 * <p>
 * MyCalendar 有一个 book(int start, int end)方法。它意味着在start到end时间内增加一个日程安排，注意，这里的时间是半开区间，即 [start, end), 实数 x 的范围为，  start <= x < end。
 * <p>
 * 当三个日程安排有一些时间上的交叉时（例如三个日程安排都在同一时间内），就会产生三重预订。
 * <p>
 * 每次调用 MyCalendar.book方法时，如果可以将日程安排成功添加到日历中而不会导致三重预订，返回 true。否则，返回 false 并且不要将该日程安排添加到日历中。
 */
public class MyCalendarTwo {

    private static class Calendar implements Comparable<Calendar>{
        private int start;// 开始的闭区间，可以取到
        private int end;// 结束的开区间，无法取到

        Calendar(int start,int end){
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Calendar o) {
            if (this.start == o.start) {
                return this.end - o.end;
            }
            return this.start - o.start;
        }
    }
    private TreeSet<Calendar> bookedSet;// 正常的区间
    private TreeSet<Calendar> overSet;// 重复的区间

    public MyCalendarTwo() {
        bookedSet = new TreeSet<>();
        overSet = new TreeSet<>();
    }

    public boolean book(int start, int end) {
        if (start >= end) {
            return false;
        }
        Calendar newCalendar = new Calendar(start, end);
        // 重复区间查找值
        Calendar overLow = overSet.floor(newCalendar);
        if (overLow != null && overLow.end > start) {
            return false;
        }
        Calendar overHigh = overSet.ceiling(newCalendar);
        if (overHigh != null && overHigh.start < end) {
            return false;
        }
        // 如果通过重复区间，则表示可以插入
        // 小于start的区间判断
        Calendar newStart = newCalendar;
        while (true) {
            if (newStart == null) {
                break;
            }
            Calendar bookLow = bookedSet.floor(newStart);
            if (bookLow != null && bookLow.end > start) {
                overSet.add(new Calendar(start,Math.min(bookLow.end, end)));
                break;
            } else if (bookLow == null) {
                break;
            } else {
                if (bookLow.equals(newStart)) {
                    newStart = bookedSet.lower(bookLow);
                } else {
                    newStart = bookLow;
                }
            }
        }
        // 大于start的区间判断
        newStart = newCalendar;
        while (true) {
            if (newStart == null) {
                break;
            }
            Calendar bookHigh = bookedSet.ceiling(newStart);
            if (bookHigh != null && bookHigh.start < end) {
                overSet.add(new Calendar(bookHigh.start,Math.min(bookHigh.end, end)));
                if (bookHigh.equals(newStart)) {
                    newStart = bookedSet.higher(bookHigh);
                } else {
                    newStart = bookHigh;
                }
            } else {
                break;
            }
        }
        bookedSet.add(newCalendar);
        return true;
    }
}
