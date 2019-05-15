package bit;

/**
 * 位运算
 */
public class Bit {

    /**
     * 136.只出现一次的数字
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int num = nums[0];
        for(int i=1;i<nums.length;i++){
            num = num^nums[i];
        }
        return num;
    }
}
