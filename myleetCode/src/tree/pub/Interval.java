package tree.pub;

/**
 * 区间间隔，为352的内部类（作为结构体使用）
 */
public class Interval {
    public int start;
    public int end;

    public Interval() {
        start = 0;
        end = 0;
    }

    public Interval(int s, int e) {
        start = s;
        end = e;
    }
}
