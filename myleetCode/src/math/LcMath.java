package math;

import java.util.ArrayList;
import java.util.List;

/**
 * leetCode数学类型解题
 */
public class LcMath {
    /**
     * 整数反转
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int cur = x % 10;
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10) && cur > 7)
                return 0;
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10) && cur < -8)
                return 0;
            result = result * 10 + cur;
            x = x / 10;
        }
        return result;
    }

    public int bulbSwitch(int n) {
        int i = 1;
        while (i * i <= n) {
            i++;
        }
        return i - 1;
    }

    /**
     * 9.回文数
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        } else if (x == 0) {
            return true;
        } else {
            int temp = x;
            int reverse = 0;
            while (temp != 0) {
                reverse = reverse * 10 + temp % 10;
                temp = temp / 10;
            }
            return reverse == x;
        }
    }

    public int divide(int dividend, int divisor) {

        return 0;
    }

    /**
     * 50.pow(x,n)，思路:分治，拆分为子问题解决
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        return java.lang.Math.pow(x,n);
    }

    public boolean canWinNim(int n) {
        return (n & 3) != 0;// 这个意思就是n%4 !=0 ,只有2^n次幂才能转化为x&2^n-1;
    }

    /**
     * 268. 缺失数字
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = n*(n+1) >> 1;
        int res=sum;
        for (int i = 0;i<n;i++){
            res = res-nums[i];
        }
        return res;
    }

    /**
     * 5113.删除区间
     * @param intervals
     * @param toBeRemoved
     * @return
     */
    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
        int removeMin = toBeRemoved[0];
        int removeMax = toBeRemoved[1];
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0;i<intervals.length;i++){
            // 如果移除区间最大值比当前区间最小值还小或者移除区间最小值比比当前区间最大值要大
            if (removeMax < intervals[i][0] || removeMin >= intervals[i][1]){
                result.add(getEveryList(intervals[i][0],intervals[i][1]));// 当前区间不移除
            }else if(removeMin > intervals[i][0] && removeMax >= intervals[i][1]){
                // 移除区间的头部在当前区间内，但是尾部在当前区间外
                result.add(getEveryList(intervals[i][0],removeMin));
            }else if (removeMin <= intervals[i][0] && removeMax < intervals[i][1]){
                // 移除区间尾部在当前区间内，但是头部在当前区间外
                result.add(getEveryList(removeMax,intervals[i][1]));
            }else if(removeMin > intervals[i][0] && removeMax < intervals[i][1]){
                // 移除区间在当前区间内的
                result.add(getEveryList(intervals[i][0],removeMin));
                result.add(getEveryList(removeMax,intervals[i][1]));
            }else {
                continue;
            }
        }
        return result;
    }

    private List<Integer> getEveryList(int min,int max){
        List<Integer> everyOne = new ArrayList<>();
        everyOne.add(min);
        everyOne.add(max);
        return everyOne;
    }

    /**
     * 时针和分针的夹角
     * @param hour 时
     * @param minutes 分
     * @return 小于180度的夹角
     */
    public double angleClock(int hour, int minutes) {
        hour = hour % 12;
        // 一个小时有30度
        double hourPoint = 30 * hour;// 小时距离12点的角度
        // 一分钟有6度
        double minPoint = 6 * minutes;
        // 时针在分针转动时候，移动的角度
        hourPoint =  minPoint/12 + hourPoint;
        double res = Math.abs(minPoint - hourPoint);
        return Math.min(res,360-res);
    }

    /**
     * 最接近的因子
     * @param num
     * @return
     */
    public int[] closestDivisors(int num) {
        int sum1 = num + 1;
        int sum2 = num + 2;
        int[] res1 = getDivisors(sum1);
        int[] res2 = getDivisors(sum2);
        return Math.abs(res1[0] - res1[1]) < Math.abs(res2[0] - res2[1]) ? res1:res2;
    }

    private int[] getDivisors(int sum){
        int num1 = (int) Math.sqrt(sum);
        while (true){
            if (sum % num1 == 0){
                int num2 = sum / num1;
                return new int[]{num1,num2};
            }else {
                num1--;
            }
        }
    }
}
