package struct.datastruct.tree.segmenttree;

/**
 * 308. 二维区域和检索 - 可变
 * 给你一个 2D 矩阵 matrix，请计算出从左上角 (row1, col1) 到右下角 (row2, col2) 组成的矩形中所有元素的和。
 *
 *
 * 上述粉色矩形框内的，该矩形由左上角 (row1, col1) = (2, 1) 和右下角 (row2, col2) = (4, 3) 确定。其中，所包括的元素总和 sum = 8。
 *
 */
public class NumMatrix {

    // 线段树的root节点,按行存储，存储每个行的root节点
    private SegmentMatrix[] roots;

    /**
     * 线段树结构体类
     */
    private static class SegmentMatrix {
        int start;// 节点线段的开始下标
        int end;// 节点线段的结束下标
        int sumValue;// x+y的value
        SegmentMatrix left;// 左孩子
        SegmentMatrix right;// 右孩子

        SegmentMatrix(int start,int end,int sumValue){
            this(start,end,sumValue,null,null);
        }

        SegmentMatrix(int start, int end, int sumValue, SegmentMatrix left, SegmentMatrix right) {
            this.start = start;
            this.end = end;
            this.sumValue = sumValue;
            this.left = left;
            this.right = right;
        }
    }

    public NumMatrix(int[][] matrix) {
        if (matrix != null && matrix.length > 0 && matrix[0] != null && matrix[0].length > 0) {
            // 声明行列
            int row = matrix.length, col = matrix[0].length;
            // 声明有多少行的root节点
            roots = new SegmentMatrix[row];
            // 初始化行的root节点
            for (int i = 0; i < row; i++) {
                roots[i] = buildTree(i, 0, col - 1, matrix);
            }
        }
    }

    /**
     * 构造线段树节点
     * @param row row
     * @param start 开始下标
     * @param end 结束下标
     * @param matrix 矩阵
     * @return 当前SegmentMatrix节点
     */
    private SegmentMatrix buildTree(int row, int start, int end, int[][] matrix) {
        if (start == end){
            return new SegmentMatrix(start,end,matrix[row][start]);
        }
        // 找到中间位置
        int mid = start + (end - start) / 2;
        // 递归获取左右节点
        SegmentMatrix left = buildTree(row,start,mid,matrix);
        SegmentMatrix right = buildTree(row,mid + 1,end,matrix);
        // 返回当前节点，root.sumValue = left.sumValue + right.sumValue
        return new SegmentMatrix(start,end, left.sumValue + right.sumValue,left,right);
    }


    /**
     * 更新操作
     * @param row row
     * @param col col
     * @param val val
     */
    public void update(int row, int col, int val) {
        updateSegmentTree(roots[row],col,val);
    }

    /**
     * 行线段树节点更新
     * @param node 当前线段树节点
     * @param index 修改位置的下标
     * @param val 修改的val
     */
    private void updateSegmentTree(SegmentMatrix node,int index,int val){
        if (node.start == index && node.end == index){
            node.sumValue = val;
            return;
        }
        // 找中间节点位置
        int mid = node.start + (node.end - node.start) / 2;
        if (index <= mid){
            // <=中间，进入左子树
            updateSegmentTree(node.left,index,val);
        }else {
            // > 中间，进入右子树
            updateSegmentTree(node.right,index,val);
        }
        // 更新node节点区间和的值
        node.sumValue = node.left.sumValue + node.right.sumValue;
    }

    /**
     * 二维区间（面）和
     * @param row1 row1
     * @param col1 col1
     * @param row2 row2
     * @param col2 col2
     * @return 区面和
     */
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int sum = 0;
        for (int i = row1;i <= row2;i++){
            // 获取每个一维线段树的区间和
            sum += sumTree(roots[i],col1,col2);
        }
        return sum;
    }

    private int sumTree(SegmentMatrix node,int i,int j){
        // 查询区间正好覆盖到，直接返回
        if (node.start == i && node.end == j){
            return node.sumValue;
        }
        // 中间节点
        int mid = node.start + (node.end - node.start) / 2;
        if (j <= mid){
            return sumTree(node.left,i,j);
        }else if (i > mid){
            return sumTree(node.right,i,j);
        }else {
            // 左右子树从Mid分开获取
            return sumTree(node.left,i,mid) + sumTree(node.right,mid + 1,j);
        }
    }
}
