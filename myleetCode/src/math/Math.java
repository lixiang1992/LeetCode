package math;

public class Math {
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
}
