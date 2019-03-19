package tree.binarysearchtree;

import java.util.TreeSet;

/**
 * 715.Range模块
 * Range 模块是跟踪数字范围的模块。你的任务是以一种有效的方式设计和实现以下接口。
 * <p>
 * addRange(int left, int right) 添加半开区间 [left, right)，跟踪该区间中的每个实数。添加与当前跟踪的数字部分重叠的区间时，应当添加在区间 [left, right) 中尚未跟踪的任何数字到该区间中。
 * queryRange(int left, int right) 只有在当前正在跟踪区间 [left, right) 中的每一个实数时，才返回 true。
 * removeRange(int left, int right) 停止跟踪区间 [left, right) 中当前正在跟踪的每个实数
 */
public class RangeModule {

    private static class Range implements Comparable<Range> {
        int left;// 左侧闭区间
        int right;// 右侧开区间

        Range(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Range o) {
            if (this.left == o.left) {
                return this.right - o.right;
            }
            return this.left - o.left;
        }
    }

    private int MAX_VALUE = 100000001;// 上限值
    private TreeSet<Range> ts;// treeSet

    public RangeModule() {
        ts = new TreeSet<>();
    }

    public void addRange(int left, int right) {
        if (left >= right) {
            return;
        }
        int finalLeft = left;
        int finalRight = right;
        Range high = new Range(right, MAX_VALUE);
        while (true){
            Range r = ts.lower(high);// 比high小的第一个元素
            if (r == null || r.right < left){
                break;// 第一个元素的上线比区间下限小，直接跳出循环，加入新的range
            }
            if (r.right > right){// 元素上限大于区间上限，最终上限由区间上限变为元素上限，因为移除后需要补齐
                finalRight = r.right;
            }
            if (r.left < left){// 元素下限小于区间下限，最终下限由区间下限变为元素下限，因为移除后需要补齐
                finalLeft = r.left;
            }
            ts.remove(r);
        }
        ts.add(new Range(finalLeft,finalRight));// 加入新的range包括了之前被移除的旧range
    }

    public boolean queryRange(int left, int right) {
        if (left >= right) {
            return false;
        }
        // 左侧最靠近left区间
        Range target = ts.floor(new Range(left, MAX_VALUE));
        if (target == null) {
            return false;
        }
        // 模板区间包含条件区间
        return target.left <= left && target.right >= right;
    }

    public void removeRange(int left, int right) {
        if (left >= right) {
            return;
        }
        Range high = new Range(right, right);
        while (true) {
            Range r = ts.lower(high);// 比high小的第一个元素
            if (r == null || r.right <= left) {
                break;// 没有比区间右侧小的元素，直接break，或者比high小的元素的上线小于等于区间左侧
            }
            if (r.right > right) {// 元素的上限大于区间上限，从区间上限到元素上限的部分需要补齐
                ts.add(new Range(right, r.right));
            }
            if (r.left < left) {// 元素的下限小于区间的下限，从元素下限到区间下限的部分需要补齐
                ts.add(new Range(r.left,left));
            }
            ts.remove(r);
        }
    }
}
