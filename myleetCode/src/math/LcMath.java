package math;

import java.util.*;

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
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        return java.lang.Math.pow(x, n);
    }

    public boolean canWinNim(int n) {
        return (n & 3) != 0;// 这个意思就是n%4 !=0 ,只有2^n次幂才能转化为x&2^n-1;
    }

    /**
     * 202.快乐数
     *
     * @param n
     * @return
     */
    public boolean isHappy(int n) {
        // 类似查找链表中是否有环的思路
        return false;
    }

    /**
     * 268. 缺失数字
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = n * (n + 1) >> 1;
        int res = sum;
        for (int i = 0; i < n; i++) {
            res = res - nums[i];
        }
        return res;
    }

    /**
     * 575.分发糖果
     *
     * @param candies
     * @return
     */
    public int distributeCandies(int[] candies) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < candies.length; i++) {
            set.add(candies[i]);
        }
        return Math.min(candies.length >> 1, set.size());
    }

    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        int[] x1 = {rec1[0], rec1[2]};// x1坐标边界
        int[] y1 = {rec1[1], rec1[3]};// y1坐标边界
        int[] x2 = {rec2[0], rec2[2]};// x2坐标边界
        int[] y2 = {rec2[1], rec2[3]};// y2坐标边界
        // 不是x2的右边界小于x1的左边界，x2的左边界大于x2的右边界，y2的上边界小于y1的下边界，y2的下边界大于y1的上边界
        return !(x2[1] <= x1[0] || x2[0] >= x1[1] || y2[1] <= y1[0] || y2[0] >= y1[1]);
    }

    /**
     * 892. 三维形体的表面积
     * 在 N * N 的网格上，我们放置一些 1 * 1 * 1  的立方体。
     * <p>
     * 每个值 v = grid[i][j] 表示 v 个正方体叠放在对应单元格 (i, j) 上。
     * <p>
     * 请你返回最终形体的表面积。
     * <p>
     * are = 6 + (n-1)*4 - 2min(x) - 2min(y)
     *
     * @param grid
     * @return
     */
    public int surfaceArea(int[][] grid) {
        int area = 0;
        if (grid.length == 0) {
            return area;
        }
        int N = grid.length;
        area = pointArea(grid[0][0]);
        // 列初始化
        for (int i = 1; i < N; i++) {
            int tempArea = pointArea(grid[i][0]);
            tempArea -= 2 * Math.min(grid[i][0], grid[i - 1][0]);
            area += tempArea;
        }
        // 行初始化
        for (int i = 1; i < N; i++) {
            int tempArea = pointArea(grid[0][i]);
            tempArea -= 2 * Math.min(grid[0][i], grid[0][i - 1]);
            area += tempArea;
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                int tempArea = pointArea(grid[i][j]);
                tempArea -= 2 * Math.min(grid[i][j], grid[i - 1][j]);
                tempArea -= 2 * Math.min(grid[i][j], grid[i][j - 1]);
                area += tempArea;
            }
        }
        return area;
    }

    /**
     * 一个点数量的表面积
     *
     * @param n
     * @return
     */
    private int pointArea(int n) {
        if (n == 0) {
            // 0的特判
            return 0;
        }
//        6 + (n - 1) * 4 展开就是 4n+2
        return 4 * n + 2;
    }

    /**
     * 914.卡牌分组
     * 个数统计，然后判断是否有公约数，有公约数，就可以，没有就不行
     * @return
     */
    public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer,Integer> cache = new HashMap<>();
        for (int i = 0; i < deck.length; i++) {
            // 统计
            cache.put(deck[i],cache.getOrDefault(deck[i],0) + 1);
        }
        int x = 0;
        // 辗转相除法求最大公约数
        for (Integer cnt : cache.values()){
            x = gcd(x,cnt);
            if (x == 1){
                return false;
            }
        }
        return x >= 2;
    }

    private int gcd(int a,int b){
        // a = b*q + r ,其中q是商，r是余数，能整除b,r的，肯定能整除a
        // r = a - b*q;  (b,r) | (a,b) 同余
        return b == 0 ? a : gcd(b,a%b);
    }

    /**
     * 1103.分发糖果2
     *
     * @param candies
     * @param num_people
     * @return
     */
    public int[] distributeCandies(int candies, int num_people) {
        int[] res = new int[num_people];
        int i = 0;
        while (candies > 0) {
            // 当前发放糖果
            int send = Math.min(i + 1, candies);
            // 得到新的糖果
            res[i % num_people] += send;
            // 总糖果扣减
            candies -= send;
            i++;
        }
        return res;
    }

    public int[][] findContinuousSequence(int target) {
        List<int[]> res = new ArrayList<>();
        int[] temp = null;
        // 采用前缀和来求解,
        // key前缀和-> value下标
        Map<Long, Integer> prevFixMap = new HashMap<>();
        prevFixMap.put(0L, 0);
        int num = 1;
        // 从1开始往target逼近
        int dest = target / 2 + 1;
        while (num <= dest) {
            long sum = getSum(num);
            // 获取开始的下标
            int start = prevFixMap.getOrDefault(sum - target, -1);
            if (start > -1) {// 存在解
                // 记录下来
                int index = start + 1;
                temp = new int[num - start];
                while (index <= num) {
                    temp[index - start - 1] = index;
                    index++;
                }
                res.add(temp);
            }
            // 前缀记录
            prevFixMap.put(sum, num);
            num++;
        }
        return res.toArray(new int[0][]);
    }

    private long getSum(long i) {
        // 每一个树的前N项和
        return i * (i + 1) >> 1;
    }

    /**
     * 5113.删除区间
     *
     * @param intervals
     * @param toBeRemoved
     * @return
     */
    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
        int removeMin = toBeRemoved[0];
        int removeMax = toBeRemoved[1];
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            // 如果移除区间最大值比当前区间最小值还小或者移除区间最小值比比当前区间最大值要大
            if (removeMax < intervals[i][0] || removeMin >= intervals[i][1]) {
                result.add(getEveryList(intervals[i][0], intervals[i][1]));// 当前区间不移除
            } else if (removeMin > intervals[i][0] && removeMax >= intervals[i][1]) {
                // 移除区间的头部在当前区间内，但是尾部在当前区间外
                result.add(getEveryList(intervals[i][0], removeMin));
            } else if (removeMin <= intervals[i][0] && removeMax < intervals[i][1]) {
                // 移除区间尾部在当前区间内，但是头部在当前区间外
                result.add(getEveryList(removeMax, intervals[i][1]));
            } else if (removeMin > intervals[i][0] && removeMax < intervals[i][1]) {
                // 移除区间在当前区间内的
                result.add(getEveryList(intervals[i][0], removeMin));
                result.add(getEveryList(removeMax, intervals[i][1]));
            } else {
                continue;
            }
        }
        return result;
    }

    private List<Integer> getEveryList(int min, int max) {
        List<Integer> everyOne = new ArrayList<>();
        everyOne.add(min);
        everyOne.add(max);
        return everyOne;
    }

    /**
     * 时针和分针的夹角
     *
     * @param hour    时
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
        hourPoint = minPoint / 12 + hourPoint;
        double res = Math.abs(minPoint - hourPoint);
        return Math.min(res, 360 - res);
    }

    /**
     * 最接近的因子
     *
     * @param num
     * @return
     */
    public int[] closestDivisors(int num) {
        int sum1 = num + 1;
        int sum2 = num + 2;
        int[] res1 = getDivisors(sum1);
        int[] res2 = getDivisors(sum2);
        return Math.abs(res1[0] - res1[1]) < Math.abs(res2[0] - res2[1]) ? res1 : res2;
    }

    private int[] getDivisors(int sum) {
        int num1 = (int) Math.sqrt(sum);
        while (true) {
            if (sum % num1 == 0) {
                int num2 = sum / num1;
                return new int[]{num1, num2};
            } else {
                num1--;
            }
        }
    }
}
