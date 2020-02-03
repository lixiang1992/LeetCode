package struct.datastruct.tree.segmenttree;

/**
 * 729.我的日程安排表 I
 * 实现一个 MyCalendar 类来存放你的日程安排。如果要添加的时间内没有其他安排，则可以存储这个新的日程安排。
 *
 * MyCalendar 有一个 book(int start, int end)方法。它意味着在 start 到 end 时间内增加一个日程安排，注意，这里的时间是半开区间，即 [start, end), 实数 x 的范围为，  start <= x < end。
 *
 * 当两个日程安排有一些时间上的交叉时（例如两个日程安排都在同一时间内），就会产生重复预订。
 *
 * 每次调用 MyCalendar.book方法时，如果可以将日程安排成功添加到日历中而不会导致重复预订，返回 true。否则，返回 false 并且不要将该日程安排添加到日历中。
 */
public class MyCalendar {

    // 线段树的根
    private SegmentTreeNode root;

    /**
     * 线段树结构体
     */
    private static class SegmentTreeNode {
        int start;// 时间开始区间
        int end;// 时间结束区间

        SegmentTreeNode left;// 区间左孩子
        SegmentTreeNode right;// 区间右孩子

        SegmentTreeNode(int start,int end){
            this.start = start;
            this.end = end;
        }
    }

    public MyCalendar() {
        root = new SegmentTreeNode(0,0);
    }

    public boolean book(int start, int end) {
        return updateSegment(root,start,end);
    }

    /**
     * 更新线段树节点
     * @param root 线段树节点
     * @param start 新插入的开始时间
     * @param end 新插入的结束时间
     * @return
     */
    private boolean updateSegment(SegmentTreeNode root,int start,int end){
        // 新节点，要么只能全在node.start往左，要么只能全在node.end往右
        SegmentTreeNode node = root;
        while (true){
            // 因为start,end是前闭后开区间，所以end可以取等号
            if (end <= node.start){
                // 左子树为空，表示可以添加
                if (node.left == null){
                    node.left = new SegmentTreeNode(start,end);
                    return true;
                }
                // 进入左子树线段树
                node = node.left;
            }else if (start >= node.end){
                // 右子树为空，表示可以添加
                if (node.right == null){
                    node.right = new SegmentTreeNode(start, end);
                    return true;
                }
                // 进入右子树的线段树
                node = node.right;
            }else {
                // start或end在[node.start,node.end)中，产生了重复预定
                return false;
            }
        }
    }
}
