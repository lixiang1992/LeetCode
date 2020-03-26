package struct.datastruct.array;

import java.util.*;

/**
 * 数组以及树状数组的应用
 */
public class TreeArray {

    /**
     * 169.多数元素
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        // 摩尔投票法
        int res = 0;
        int count = 0;
        for (int num : nums) {
            if (count == 0) {
                res = num;
            }
            if (res == num) {
                count++;
            } else {
                count--;
            }
        }
        return res;
    }

    /**
     * 370.区间加法
     * 假设你有一个长度为 n 的数组，初始情况下所有的数字均为 0，你将会被给出 k个更新的操作。
     * <p>
     * 其中，每个操作会被表示为一个三元组：[startIndex, endIndex, inc]，你需要将子数组 A[startIndex ... endIndex]（包括 startIndex 和 endIndex）增加 inc。
     * <p>
     * 请你返回 k 次操作后的数组。
     * <p>
     * 思路：差分数组的应用
     *
     * @param length  length
     * @param updates 更新区间和修改需要增加的值
     * @return 多次更新后的数组
     */
    public int[] getModifiedArray(int length, int[][] updates) {
        // 差分数组 b(n) = a(n) - a(n-1) 类比理解a(n) = S(n) - S(n-1)
        int[] series = new int[length];
        for (int i = 0; i < updates.length; i++) {
            // 差分数组头尾的变更
            series[updates[i][0]] += updates[i][2];
            // 最后一个元素没有后继了，需要特判一下
            if (updates[i][1] < length - 1) {
                series[updates[i][1] + 1] -= updates[i][2];
            }
        }
        // a(n)就是b(n)的前n项和
        // 利用差分数组求原数组
        for (int i = 1; i < series.length; i++) {
            series[i] += series[i - 1];
        }
        return series;
    }

    public int minIncrementForUnique(int[] A) {
        Arrays.sort(A);
        int count = 0;
        for (int i = 1; i < A.length; i++) {
            if (A[i-1] == A[i]){
                A[i]++;
                count++;
            } else if (A[i] < A[i-1]){
                int temp = A[i];
                A[i] = A[i-1]+1;
                count = count +(A[i] - temp);

            }
        }
        return count;
    }

    public int numRookCaptures(char[][] board) {
        // 定义上下左右四个方向
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // 找到白车所在的位置
                if (board[i][j] == 'R') {
                    // 分别判断白车的上、下、左、右四个方向
                    int res = 0;
                    for (int k = 0; k < 4; k++) {
                        int x = i, y = j;
                        while (true) {
                            x += dx[k];
                            y += dy[k];
                            if (x < 0 || x >= 8 || y < 0 || y >= 8 || board[x][y] == 'B') {
                                break;
                            }
                            if (board[x][y] == 'p') {
                                res++;
                                break;
                            }
                        }
                    }
                    return res;
                }
            }
        }
        return 0;
    }

    /**
     * 1013. 将数组分成和相等的三个部分
     * 前缀和
     *
     * @param A
     * @return
     */
    public boolean canThreePartsEqualSum(int[] A) {
        // key 前缀和 value 满足前缀和的所有下标
        Map<Integer, List<Integer>> prefixMap = new HashMap<>();
        int sum = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
            List<Integer> list = prefixMap.computeIfAbsent(sum, k -> new ArrayList<>());
            list.add(i);
        }
        if (sum % 3 != 0) {
            return false;
        }
        if (sum == 0) {
            return prefixMap.get(0).size() >= 3;
        }
        int avg = sum / 3;
        // 平均数
        List<Integer> list1 = prefixMap.get(avg);
        List<Integer> list2 = prefixMap.get(avg << 1);
        if (list1 == null || list2 == null) {
            return false;
        }
        // 只要有一个满足条件即可
        return list1.get(0) < list2.get(list2.size() - 1);
    }
}
