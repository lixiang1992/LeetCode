package dynamic;

/**
 * 303. 区域和检索 - 数组不可变
 * 前缀和
 */
public class NumArray {

    private int[] prefix;

    public NumArray(int[] nums) {
        if (nums.length == 0){
            return;
        }
        // 多申请一个空间长度，避免后序遇到0要特判
        prefix = new int[nums.length+1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i+1] = prefix[i] + nums[i];
        }
    }

    public int sumRange(int i, int j) {
        // 正常范围是[j]-[i-1]，由于初始化都往后移了一位，所以这里变成[j+1]-[i]
        return prefix[j+1] - prefix[i];
    }
}
