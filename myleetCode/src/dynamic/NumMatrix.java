package dynamic;

/**
 * 304.二维区域和检索 - 矩阵不可变
 */
public class NumMatrix {

    // 二维前缀和数组
    private int[][] prefix;

    public NumMatrix(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        // 前缀和初始化
        prefix = new int[matrix.length][matrix[0].length];
        prefix[0][0] = matrix[0][0];
        // 第一列初始化
        for (int i = 1; i < matrix.length; i++) {
            prefix[i][0] = prefix[i - 1][0] + matrix[i][0];
        }
        // 第一行初始化
        for (int i = 1; i < matrix[0].length; i++) {
            prefix[0][i] = prefix[0][i - 1] + matrix[0][i];
        }
        // 前缀和初始化
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                // 前缀处理加上当前坐标的值
                // matrix[i][j]+[i-1][j]的前缀+[i][j-1]的前缀
                // 但是[i-1][j-1]在前缀里累加了2次，所以要减去1次
                prefix[i][j] = prefix[i - 1][j] + prefix[i][j - 1] - prefix[i - 1][j - 1] + matrix[i][j];
            }
        }

    }

    /**
     * 坐标区间求和
     *
     * @param row1 row1
     * @param col1 col1
     * @param row2 row2
     * @param col2 col2
     * @return [row2][col2] - [row1][col1] 这个范围的区间和
     */
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (prefix == null) {
            return 0;
        }
        if (row1 == 0 && col1 == 0) {
            return prefix[row2][col2];
        } else if (row1 == 0) {
            return prefix[row2][col2] - prefix[row2][col1 - 1];
        } else if (col1 == 0) {
            return prefix[row2][col2] - prefix[row1 - 1][col2];
        } else {
            // 前缀和
            // 和累加情况类似，要去掉prefix[row1-1][col2]和prefix[row2][col1-1]两个前缀和
            // prefix[row1-1][col1-1]被减了2次，所以要加回来1次
            return prefix[row2][col2] - prefix[row1 - 1][col2] - prefix[row2][col1 - 1] + prefix[row1 - 1][col1 - 1];
        }
    }
}
