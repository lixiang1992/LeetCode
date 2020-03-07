package struct.datastruct.array;

/**
 * 数组以及树状数组的应用
 */
public class TreeArray {

    /**
     * 370.区间加法
     * 假设你有一个长度为 n 的数组，初始情况下所有的数字均为 0，你将会被给出 k个更新的操作。
     *
     * 其中，每个操作会被表示为一个三元组：[startIndex, endIndex, inc]，你需要将子数组 A[startIndex ... endIndex]（包括 startIndex 和 endIndex）增加 inc。
     *
     * 请你返回 k 次操作后的数组。
     *
     * 思路：差分数组的应用
     * @param length length
     * @param updates 更新区间和修改需要增加的值
     * @return
     */
    public int[] getModifiedArray(int length, int[][] updates) {
        // 差分数组 b(n) = a(n) - a(n-1) 类比理解a(n) = S(n) - S(n-1)
        int[] series = new int[length];
        for (int i = 0; i < updates.length; i++) {
            // 差分数组头尾的变更
            series[updates[i][0]] += updates[i][2];
            // 最后一个元素没有后继了，需要特判一下
            if (updates[i][1] < length - 1){
                series[updates[i][1] + 1] -= updates[i][2];
            }
        }
        // a(n)就是b(n)的前n项和
        // 利用差分数组求原数组
        for (int i = 1; i < series.length; i++) {
            series[i] += series[i-1];

        }
        return series;
    }
}
