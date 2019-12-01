package struct.datastruct.tree.segmenttree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 307.区域和检索 - 数组可修改
 * 采用segment tree来处理
 */
public class NumArray {

    private static class SegmentNode {
        int start;// 线段树节点的区间开始值
        int end;// 线段树节点的区间结束值
        int value;

        SegmentNode left;
        SegmentNode right;

        SegmentNode(int start, int end) {
            this.start = start;
            this.end = end;
            this.value = 0;
        }
    }

    private SegmentNode root;

    public NumArray(int[] nums) {
        this.root = buildSegmentTreeNode(nums,0,nums.length - 1);
    }

    /**
     * 构造线段树节点，递归生成
     * @param nums
     * @param start nums[] 的较小下标
     * @param end nums[] 的较大下标
     * @return
     */
    private SegmentNode buildSegmentTreeNode(int nums[],int start,int end){
        if(start > end){
            return null;
        }
        SegmentNode root = new SegmentNode(start,end);
        if (start == end){
            root.value = nums[start];
            return root;
        }
        int mid = start + (end - start >> 1) ;// 中间节点
        root.left = buildSegmentTreeNode(nums,start,mid);
        root.right = buildSegmentTreeNode(nums,mid + 1 ,end);
        if (root.left != null){
            root.value += root.left.value;
        }
        if (root.right != null){
            root.value += root.right.value;
        }
        return root;
    }

    public void update(int i, int val) {
        if (i < this.root.start || i>this.root.end){
            return;
        }
        SegmentNode node = this.root;
        Deque<SegmentNode> stack = new LinkedList<>();
        while (node.start != node.end){
            stack.push(node);// 记录一路走下来的node
            if (i <= node.left.end){// 这个就是node的mid值
                node = node.left;
            }else {
                node = node.right;
            }
        }
        node.value = val;
        while (!stack.isEmpty()){
            node = stack.pop();
            // 递推更新节点值
            node.value = node.left.value + node.right.value;
        }
    }

    public int sumRange(int i, int j) {
        return sumNode(root,i,j);
    }

    private int sumNode(SegmentNode root,int i,int j){
        if(root.end == j && root.start == i){
            return root.value;
        }else{
            int mid = root.start + (root.end-root.start)/2;
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
