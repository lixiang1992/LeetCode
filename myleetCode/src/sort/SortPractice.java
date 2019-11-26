package sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 排序练习
 */
public class SortPractice {

    private class LargestComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            String order1 = o1 + o2;
            String order2 = o2 + o1;
            return order2.compareTo(order1);// 大的在前面
        }
    }

    /**
     * 179.给定一组非负整数，重新排列它们的顺序使之组成一个最大的整数
     * @param nums
     * @return
     */
    public String largestNumber(int[] nums) {
        String[] strArray = new String[nums.length];
        for (int i = 0;i < nums.length;i++){
            strArray[i] = String.valueOf(nums[i]);
        }
        // 排序
        Arrays.sort(strArray,new LargestComparator());
        if (strArray[0].equals("0")){
            return "0";
        }
        StringBuilder result = new StringBuilder();
        for (int i=0;i<strArray.length;i++){
            result.append(strArray[i]);
        }
        return result.toString();
    }
}
