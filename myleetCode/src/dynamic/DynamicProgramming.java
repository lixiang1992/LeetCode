package dynamic;

import java.util.List;

/**
 * 动态规划习题
 */
public class DynamicProgramming {

    /**
     * 91.解码方法
     * 带有约束信息的斐波拉契数列问题
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        int sLength = s.length();
        int dp[] = new int[sLength + 1];
        dp[sLength - 1] = s.charAt(sLength - 1) != '0' ? 1 : 0;
        dp[sLength] = 1;
        for(int i = sLength - 2; i >= 0; i--) {
            char c1 = s.charAt(i);
            char c2 = s.charAt(i + 1);
            if(c2 == '0' && !(c1 <= '2' && c1 >= '1') || c1 == '0') {
                dp[i] = 0;
            } else if(c1 <= '1' || (c1 <= '2' && c2 <= '6'))
                dp[i] = dp[i + 1] + dp[i + 2];
            else
                dp[i] = dp[i + 1];
        }
        return dp[0];
    }

    /**
     * 120.给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int sum = Integer.MAX_VALUE;
        int[] dp = new int[triangle.size()+1];
        for (int i = 0; i < triangle.size(); i++) {
            List<Integer> row = triangle.get(i);
            for (int j = 0; j < row.size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + row.get(j);
            }
        }
        for (int i=0;i<dp.length;i++){
            if (dp[i]<sum && dp[i] > 0){
                sum = dp[i];
            }
        }
        return sum;
    }

    /**
     * 198. 打家劫舍
     * @param nums
     * @return 最优解
     */
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }else if(nums.length == 1){
            return nums[0];
        }else if(nums.length == 2){
            return Math.max(nums[0],nums[1]);
        }
        int n = nums.length;
        int sum[] = new int[n];
        sum[0] = nums[0];
        sum[1] = Math.max(nums[0],nums[1]);
        for (int i = 2;i< n;i++){
            sum[i] = Math.max(sum[i-2]+nums[i],sum[i-1]);
        }
        return sum[n - 1];
    }

    /**
     * 213. 打家劫舍 II 数组是头尾相连的，分别去除头尾计算最优解，然后比较他们的最大值
     * @param nums
     * @return
     */
    public int rob2(int[] nums){
        if (nums == null || nums.length == 0){
            return 0;
        }else if(nums.length == 1){
            return nums[0];
        }else if(nums.length == 2){
            return Math.max(nums[0],nums[1]);
        }
        // 去掉尾巴
        int a1 = nums[0];
        int b1 = Math.max(nums[0],nums[1]);
        int c1 = b1;
        for (int i = 2;i<nums.length - 1 ;i++){
            c1 = Math.max(a1+nums[i],b1);
            a1 = b1;
            b1 = c1;
        }
        // 去掉头部
        int a2 = nums[1];
        int b2 = Math.max(nums[1],nums[2]);
        int c2 = b2;
        for (int i = 3;i<nums.length;i++){
            c2 = Math.max(a2+nums[i],b2);
            a2 = b2;
            b2 = c2;
        }
        return Math.max(c1,c2);
    }
}
