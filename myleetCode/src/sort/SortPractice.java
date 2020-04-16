package sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 排序练习
 */
public class SortPractice {

    /**
     * 56.合并区间
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        Deque<int[]> queue = new LinkedList<>();
        Arrays.sort(intervals, (v1, v2) -> v1[0] - v2[0]);
        // 遍历
        for (int[] current : intervals) {
            if (queue.size() > 0){
                // 上一个元素
                int[] last = queue.peekLast();
                if (last[1] >= current[0]){
                    // 区间发生了重叠
                  last[1] = Math.max(last[1],current[1]);
                } else {
                    // 区间没有重叠
                    queue.offer(current);
                }
            }else{
                queue.offer(current);
            }
        }
        int[][] res = new int[queue.size()][2];
        int i = 0;
        while (!queue.isEmpty()){
            res[i++] = queue.poll();
        }
        return res;
    }

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
        for (String s : strArray) {
            result.append(s);
        }
        return result.toString();
    }

    /**
     * 378. 有序矩阵中第K小的元素
     * N个有序数组，归并排序
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {
        int[] res = matrix[0].clone();
        for (int i = 1; i < matrix.length; i++) {

        }
        return 0;
    }
}
