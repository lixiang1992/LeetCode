package sort;

public class QuickSort {
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        // 快速排序
        quickSort(nums,0,nums.length - 1);
    }

    private void quickSort(int[] nums,int begin,int end){
        if(begin >= end){
            return;
        }
        int dp = partition(nums,begin,end);
        quickSort(nums,begin,dp-1);
        quickSort(nums,dp+1,end);
    }

    private int partition(int[] nums, int begin, int end) {
        int left = begin;
        int right = end;
        int index = left;
        int temp = nums[index];// 基准位置
        while(left < right){
            while (left < right && nums[right] >= temp){
                right--;
            }
            while (left < right && nums[left] <= temp){
                left++;
            }
            swap(nums,left,right);
        }
        swap(nums,left,index);
        index = left;
        return index;
    }

    private void swap(int[] nums ,int left ,int right){
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
