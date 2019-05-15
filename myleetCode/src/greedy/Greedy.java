package greedy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Greedy {
    /**
     * 分发糖果
     * @param ratings
     * @return
     */
    public int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        } else if (ratings.length == 1) {
            return 1;
        } else {
            int base = 1;//基础糖果
            int cur = base;// 当前糖果
            int sum = cur;
            int smallNum = 0;//小于计数
            int lastBigCur = cur;
            for (int i = 1; i < ratings.length; i++) {
                if (ratings[i - 1] < ratings[i]) {// 后一个比前一个分数高
                    smallNum = 0;
                    cur += base;// 当前人所得糖果比上一人+base
                    lastBigCur = cur;
                    sum += cur;
                } else if (ratings[i - 1] == ratings[i]) {
                    smallNum = 0;
                    cur = base;
                    lastBigCur = cur;
                    sum += base;
                } else {// 后一个没有比前一个分数高
                    if (cur == base) {
                        // 当前分已经是基础分了
                        smallNum++;
                        sum = sum + cur + smallNum;
                        if (lastBigCur - 1 == smallNum) {
                            lastBigCur += base;
                            sum += base;
                        }
                    } else {
                        cur = base;
                        sum += cur;
                    }
                }
            }
            return sum;
        }
    }

    public static boolean isPerfectSquare(int num) {
        if (num == 1) {
            return true;
        }
        // 使用二分查找
        long start = 2;
        long end = num;
        while (start <= end) {
            long mid = ((end - start) >> 1) + start;// 防止溢出
            if (mid * mid == num) {
                return true;
            } else if (mid * mid > num) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return false;
    }

    public int climbStairs(int n) {
        if (n <= 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            int one = 1;
            int two = 2;
            int sum = 0;
            for (int i = 2; i < n; i++) {
                sum = one + two;
                one = two;
                two = sum;
            }
            return sum;
        }
    }

    public static List<List<Integer>> combine(int n, int k) {
        if (n < k) {
            return new ArrayList<>();
        }
        List<List<Integer>> result = new LinkedList<>();
        List<Integer> everyResult = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            for(int j = i + 1;j<=n-k+1;j++){
                everyResult.add(i);
                for(int m = j;m<j+k-1;m++){
                    everyResult.add(m);
                }
                result.add(everyResult);
                everyResult = new LinkedList<>();
            }
        }
        return result;
    }

    /**
     * 134.加油站
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int index = -1;
        int currentGas = 0;// 当前油量
        int length = gas.length;
        int start = 0;
        int i = start;
        int end = 0;
        while (true){
            if (i>=length){
                i = length - i;
            }
            currentGas = currentGas + gas[i] - cost[i];// 第i个加油站还剩的汽油
            while (currentGas < 0){// 当前汽油不足了
                currentGas = currentGas - gas[start] + cost[start];// 起始加油站舍弃，重新判断当前油量是否足够
                start++;
                if (start >= length){
                    return -1;// 到达数组尾部，则没有解，直接返回
                }
            }
            i++;
            end++;
            if(end - start == length){
                index = start;
                break;
            }
        }
        return index;
    }
}
