package struct.datastruct.tree.segmenttree;

/**
 * 307.区域和检索 - 数组可修改
 * 给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。
 *
 * update(i, val) 函数可以通过将下标为 i 的数值更新为 val，从而对数列进行修改。
 *
 * 示例:
 *
 * Given nums = [1, 3, 5]
 *
 * sumRange(0, 2) -> 9
 * update(1, 2)
 * sumRange(0, 2) -> 8
 * 说明:
 *
 * 数组仅可以在 update 函数下进行修改。
 * 你可以假设 update 函数与 sumRange 函数的调用次数是均匀分布的。
 *
 * 采用segment tree来处理
 */
public class NumArray_307 {

    // 线段树的root节点
    private SegmentTree root;

    /**
     * 线段树结构体类
     */
    private static class SegmentTree {
        int start;// 节点线段的开始下标
        int end;// 节点线段的结束下标
        int sumValue;// 节点线段开始-结束所有值的和，start==end的时候，就是节点本身的值

        SegmentTree left;// 左孩子
        SegmentTree right;// 右孩子
        SegmentTree parent;// 父亲指针

        SegmentTree(int start,int end){
            this(start,end,0);
        }

        SegmentTree(int start,int end,int sumValue){
            this.start = start;
            this.end = end;
            this.sumValue = sumValue;
        }
    }

    public NumArray_307(int[] nums) {
        this.root = buildSegmentTree(nums,0,nums.length - 1);
    }

    /**
     * 构造线段树
     * @param nums nums
     * @param start 线段开始位置
     * @param end 线段结束位置
     * @return 线段树节点
     */
    private SegmentTree buildSegmentTree(int[] nums,int start,int end){
        if (start > end){
            return null;
        }else if(start == end){
            // 节点只有一个值了
            return new SegmentTree(start,end,nums[start]);
        }
        int mid = start + (end - start >> 1);// 取得中间节点
        // 递归构造左孩子
        SegmentTree left = buildSegmentTree(nums,start,mid);
        // 递归构造右孩子
        SegmentTree right = buildSegmentTree(nums,mid + 1,end);
        // 当前节点
        SegmentTree curRoot = new SegmentTree(start,end);
        // 当前节点的值，等于左右节点之和
        if (left != null){
            curRoot.left = left;
            left.parent = curRoot;
            curRoot.sumValue += left.sumValue;
        }
        if (right != null){
            curRoot.sumValue += right.sumValue;
            right.parent = curRoot;
            curRoot.right = right;
        }
        return curRoot;
    }

    public void update(int i, int val) {
        if (i < this.root.start || i > this.root.end){
            return;
        }
        SegmentTree node = this.root;
        // 想等就是当前节点
        while (node.start != node.end){
            int  mid = node.start + (node.end - node.start >> 1);
            // 左偏移可以等于，右边不行
            if (mid >= i){
                // 左偏移
                node = node.left;
            }else {
                // 右偏移
                node = node.right;
            }
        }
        // 节点替换
        node.sumValue = val;
        // 递推更新父节点
        while (node.parent != null){
            node.parent.sumValue = node.parent.left.sumValue + node.parent.right.sumValue;
            node = node.parent;
        }
    }

    /**
     * 区间求和
     * @param i 区间开始下标
     * @param j 区间结束下标
     * @return 区间和
     */
    public int sumRange(int i, int j) {
        return sumNode(root,i,j);
    }

    /**
     * 区间求和
     * @param root 当前的线段树节点
     * @param i 区间开始下标
     * @param j 区间结束下标
     * @return 区间和
     */
    private int sumNode(SegmentTree root,int i,int j){
        if(root.end == j && root.start == i){
            return root.sumValue;
        }else{
            int mid = root.start + (root.end-root.start >> 1);
            if(mid >= j){
                return sumNode(root.left,i,j);
            }else if(mid<i){
                return sumNode(root.right,i,j);
            }else{
                return sumNode(root.left,i,mid)+sumNode(root.right,mid+1,j);
            }
        }
    }
}
