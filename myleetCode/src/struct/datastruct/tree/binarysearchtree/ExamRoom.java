package struct.datastruct.tree.binarysearchtree;

import java.util.TreeSet;

/**
 * 855.考场就坐
 * 在考场里，一排有 N 个座位，分别编号为 0, 1, 2, ..., N-1 。
 * <p>
 * 当学生进入考场后，他必须坐在能够使他与离他最近的人之间的距离达到最大化的座位上。如果有多个这样的座位，他会坐在编号最小的座位上。(另外，如果考场里没有人，那么学生就坐在 0 号座位上。)
 * <p>
 * 返回 ExamRoom(int N) 类，它有两个公开的函数：其中，函数 ExamRoom.seat() 会返回一个 int （整型数据），代表学生坐的位置；
 * 函数 ExamRoom.leave(int p) 代表坐在座位 p 上的学生现在离开了考场。每次调用 ExamRoom.leave(p) 时都保证有学生坐在座位 p 上。
 *
 * 思路：但凡遇到在动态过程中取最值的情况，肯定要用到有序数据结构，常用的就是堆和红黑树了。
 */
public class ExamRoom {

    private TreeSet<Integer> room;
    private int size;

    public ExamRoom(int N) {
        this.room = new TreeSet<>();
        this.size = N - 1;
    }

    public int seat() {
        int index = 0;
        if (room.size() > 0){
            int dist = room.first();// 第一个元素到0的距离就是其本身，默认其为最大距离
            int prev = dist;// 前一个人的位置，默认是最小的开始
            for (Integer seat : room){// 遍历考场中的全部座位
                int d = (seat - prev) >> 1;// 获取当前位置和上一个位置的距离
                if (d > dist){// 替换最大距离和对应的位置下标
                    dist = d;
                    index = (seat + prev) >> 1;
                }
                prev = seat;// 前一个位置后移到当前位置
            }
            if (size - room.last() > dist){
                index = size;// 最后一个位置的判断
            }
        }
        room.add(index);
        return index;
    }

    public void leave(int p) {
        room.remove(p);
    }
}
