package struct.datastruct.tree.segmenttree;

/**
 * 732.我的日程安排III
 * 当 K 个日程安排有一些时间上的交叉时（例如K个日程安排都在同一时间内），就会产生 K 次预订。
 *
 * 每次调用 MyCalendar.book方法时，返回一个整数 K ，表示最大的 K 次预订。
 *
 * 请按照以下步骤调用MyCalendar 类: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 *
 * 思路：线段树懒惰更新
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/my-calendar-iii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MyCalendarThree {

    private SegmentTreeNode root;// 线段树根节点

    private static class SegmentTreeNode{
        int value;// 值，预定数
        int start;// 开始区间
        int end;// 结束区间

        int lazy;// 懒惰更新数，每次懒惰数不为0，更新当前value，和左右孩子value，lazy置空，

        SegmentTreeNode leftNode;
        SegmentTreeNode rightNode;

        SegmentTreeNode(int start,int end,int value){
            this.start = start;
            this.end = end;
            this.value = value;
        }

        int getMid(){
            return start + (end - start >> 1);
        }

        /**
         * 获取当前节点左孩子
         * @return 左孩子
         */
        SegmentTreeNode getLeftNode(){
            if (leftNode == null){
                leftNode = new SegmentTreeNode(start,getMid(),value);
            }
            return leftNode;
        }

        /**
         * 获取当前节点右孩子
         * @return 右孩子
         */
        SegmentTreeNode getRightNode(){
            if (rightNode == null){
                rightNode = new SegmentTreeNode(getMid() + 1,end,value);
            }
            return rightNode;
        }
    }

    public MyCalendarThree() {
        root = new SegmentTreeNode(0,Integer.MAX_VALUE,0);
    }

    public int book(int start, int end) {
        // 因为end是开区间，不取，所以转换为end - 1
        lazyUpdateTreeNode(root,start,end - 1);
        return root.value;
    }

    /**
     * 对节点进行懒惰更新
     * @param node 线段树节点
     * @param start 开始位置
     * @param end 结束位置
     */
    private void lazyUpdateTreeNode(SegmentTreeNode node,int start,int end){
        if (node.lazy != 0){
            // 节点预定数更新，添加懒惰值
            node.value += node.lazy;
            // 左右孩子的懒惰更新值添加
            if (node.leftNode != null){
                node.leftNode.lazy += node.lazy;
            }
            if (node.rightNode != null){
                node.rightNode.lazy += node.lazy;
            }
            node.lazy = 0;// 懒惰更新完毕，懒惰值清零
        }
        // 线段树节点正好相同
        if (node.start == start && node.end == end){
            node.value++;
            if (node.leftNode != null){
                node.leftNode.lazy++;
            }
            if (node.rightNode != null){
                node.rightNode.lazy++;
            }
            return;
        }

        int mid = node.getMid();
        if (end <= mid){
            // 全部在左侧，递归进入左孩子
            lazyUpdateTreeNode(node.getLeftNode(),start,end);
        }else if (start > mid){
            // 全部在右侧，递归进入右孩子
            lazyUpdateTreeNode(node.getRightNode(),start,end);
        }else {
            // 左右都有，中间分开
            lazyUpdateTreeNode(node.getLeftNode(),start,mid);
            lazyUpdateTreeNode(node.getRightNode(),mid+1,end);
        }
        node.value = Math.max(node.getLeftNode().value + node.getLeftNode().lazy, node.getRightNode().value + node.getRightNode().lazy);
    }
}
