package struct.datastruct.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 数组以及树状数组的应用
 */
public class TreeArray {

//    public int[] singleNumbers(int[] nums) {
//        int[] copy = nums.clone();
//        int num = nums[0];
//        for (int i = 1;i<nums.length;i++){
//            num = nums[i-1] ^ nums[i];
//        }
//
//    }

    /**
     * 169.多数元素
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        // 摩尔投票法
        int res = 0;
        int count = 0;
        for (int num : nums) {
            if (count == 0) {
                res = num;
            }
            if (res == num) {
                count++;
            } else {
                count--;
            }
        }
        return res;
    }

    /**
     * 370.区间加法
     * 假设你有一个长度为 n 的数组，初始情况下所有的数字均为 0，你将会被给出 k个更新的操作。
     * <p>
     * 其中，每个操作会被表示为一个三元组：[startIndex, endIndex, inc]，你需要将子数组 A[startIndex ... endIndex]（包括 startIndex 和 endIndex）增加 inc。
     * <p>
     * 请你返回 k 次操作后的数组。
     * <p>
     * 思路：差分数组的应用
     *
     * @param length  length
     * @param updates 更新区间和修改需要增加的值
     * @return 多次更新后的数组
     */
    public int[] getModifiedArray(int length, int[][] updates) {
        // 差分数组 b(n) = a(n) - a(n-1) 类比理解a(n) = S(n) - S(n-1)
        int[] series = new int[length];
        for (int i = 0; i < updates.length; i++) {
            // 差分数组头尾的变更
            series[updates[i][0]] += updates[i][2];
            // 最后一个元素没有后继了，需要特判一下
            if (updates[i][1] < length - 1) {
                series[updates[i][1] + 1] -= updates[i][2];
            }
        }
        // a(n)就是b(n)的前n项和
        // 利用差分数组求原数组
        for (int i = 1; i < series.length; i++) {
            series[i] += series[i - 1];
        }
        return series;
    }

    public int minIncrementForUnique(int[] A) {
        Arrays.sort(A);
        int count = 0;
        for (int i = 1; i < A.length; i++) {
            if (A[i - 1] == A[i]) {
                A[i]++;
                count++;
            } else if (A[i] < A[i - 1]) {
                int temp = A[i];
                A[i] = A[i - 1] + 1;
                count = count + (A[i] - temp);

            }
        }
        return count;
    }

    public int numRookCaptures(char[][] board) {
        // 定义上下左右四个方向
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // 找到白车所在的位置
                if (board[i][j] == 'R') {
                    // 分别判断白车的上、下、左、右四个方向
                    int res = 0;
                    for (int k = 0; k < 4; k++) {
                        int x = i, y = j;
                        while (true) {
                            x += dx[k];
                            y += dy[k];
                            if (x < 0 || x >= 8 || y < 0 || y >= 8 || board[x][y] == 'B') {
                                break;
                            }
                            if (board[x][y] == 'p') {
                                res++;
                                break;
                            }
                        }
                    }
                    return res;
                }
            }
        }
        return 0;
    }

    public void gameOfLife(int[][] board) {
        int[][] clone = new int[board.length][board[0].length];
        for (int i = 0; i < clone.length; i++) {
            for (int j = 0; j < clone[i].length; j++) {
                clone[i][j] = board[i][j];
            }
        }

        for (int i = 0; i < clone.length; i++) {
            for (int j = 0; j < clone[i].length; j++) {
                board[i][j] = getStatus(clone, i, j);
            }
        }
    }

    private int getStatus(int[][] board, int i, int j) {
        int[] dx = {-1, 0, 1};
        int[] dy = {-1, 0, 1};
        int count = 0;
        for (int m = 0; m < dx.length; m++) {
            for (int n = 0; n < dy.length; n++) {
                // 边界跳过
                int row = i + dx[m];
                int col = j + dy[n];
                if (row < 0 || row >= board.length || col < 0 || col >= board[i].length) {
                    continue;
                }
                // 自己跳过
                if (row == i && col == j) {
                    continue;
                }
                if (board[row][col] == 1) {
                    count++;
                }
            }
        }
        if (board[i][j] == 1) {
            return 2 <= count && count <= 3 ? 1 : 0;
        } else {
            return count == 3 ? 1 : 0;
        }
    }

    /**
     * 1013. 将数组分成和相等的三个部分
     * 前缀和
     *
     * @param A
     * @return
     */
    public boolean canThreePartsEqualSum(int[] A) {
        // key 前缀和 value 满足前缀和的所有下标
        Map<Integer, List<Integer>> prefixMap = new HashMap<>();
        int sum = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
            List<Integer> list = prefixMap.computeIfAbsent(sum, k -> new ArrayList<>());
            list.add(i);
        }
        if (sum % 3 != 0) {
            return false;
        }
        if (sum == 0) {
            return prefixMap.get(0).size() >= 3;
        }
        int avg = sum / 3;
        // 平均数
        List<Integer> list1 = prefixMap.get(avg);
        List<Integer> list2 = prefixMap.get(avg << 1);
        if (list1 == null || list2 == null) {
            return false;
        }
        // 只要有一个满足条件即可
        return list1.get(0) < list2.get(list2.size() - 1);
    }

    /**
     * 给你一个整数数组 nums 和一个整数 k。
     *
     * 如果某个 连续 子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
     *
     * 请返回这个数组中「优美子数组」的数目。
     *
     * @param nums
     * @param k
     * @return
     */
    public int numberOfSubarrays(int[] nums, int k) {
        int totalCount = 0;// 全部的组合数
        int oddCount = 0;
        // {前缀的奇数个数，数量}
        Map<Integer,Integer> cache = new HashMap<>();
        for (int i = 0; i< nums.length;i++){
            // 是奇数，数量+1
            if ((nums[i] & 1) != 0){
                oddCount++;
                // 奇数所在位置的下标
                cache.put(oddCount,i);
            }
        }
        for (Map.Entry<Integer,Integer> entry : cache.entrySet()){
            Integer key = entry.getKey() + k;
            totalCount += (entry.getValue()+1) * cache.getOrDefault(key,0);
        }
        return totalCount;
    }

    /**
     * 给你一个列表 nums ，里面每一个元素都是一个整数列表。请你依照下面各图的规则，按顺序返回 nums 中对角线上的整数。
     * @param nums
     * @return
     */
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        List<Integer> resList = new ArrayList<>();
        Map<Integer, Deque<Integer>> map = new TreeMap<>();
        for (int i = 0;i< nums.size();i++){
            for (int j = 0;j<nums.get(i).size();j++){
                // 生成一个deque
                Deque<Integer> deque = map.computeIfAbsent(i+j,k->new LinkedList<>());
                deque.push(nums.get(i).get(j));
            }
        }
        for (Deque<Integer> deque : map.values()){
            resList.addAll(deque);
        }
        int[] res = new int[resList.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = resList.get(i);
        }
        return res;
    }

    /**
     * 山脉数组中查找目标值
     * @param target
     * @param mountainArr
     * @return
     */
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int length = mountainArr.length();
        // 查找最高点
        int left = 0,right = length - 1;
        int midIndex = getHighIndex(left,right,mountainArr);
        if(mountainArr.get(midIndex) == target){
            return midIndex;
        }
        // 左侧查找
        right = midIndex;
        while(left <= right){
            int mid = left + (right - left) / 2;
            int midArr = mountainArr.get(mid);
            if(midArr == target){
                return mid;
            }else if(target < midArr){
                right = mid - 1;
            }else {
                left = mid + 1;
            }
        }
        // 往右侧查找
        left = midIndex;
        right = length - 1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            int midArr = mountainArr.get(mid);
            if(midArr == target){
                return mid;
            }else if(target < midArr){
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return -1;
    }

    private int getHighIndex(int left,int right,MountainArray mountainArr){
        int length = right;
        while(left <= right){
            int mid = left + (right - left) / 2;
            int midArr = mountainArr.get(mid);
            int leftArr = mid == 0 ? Integer.MIN_VALUE : mountainArr.get(mid-1);
            int rightArr = mid == length ? Integer.MAX_VALUE : mountainArr.get(mid+1);
            if(midArr > rightArr && midArr > leftArr){
                return mid;
            }else if(midArr <= rightArr){
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < candies.length; i++) {
            max = Math.max(max,candies[i]);
            candies[i] += extraCandies;
        }
        List<Boolean> res = new ArrayList<>(candies.length);
        for (int candy : candies) {
            res.add(candy >= max);
        }
        return res;
    }

    /**
     * 绝对差不超过限制的最长连续子数组
     * @param nums
     * @param limit
     * @return
     */
    public int longestSubarray(int[] nums, int limit) {
        // 数值，数值对应的下标
        TreeMap<Integer,Set<Integer>> treeMap = new TreeMap<>();
        int max = 0;
        int startIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            // 进入treeMap
            Set<Integer> set = treeMap.computeIfAbsent(nums[i], k->new HashSet<>());
            set.add(i);
            // 最大值和最小值的判断
            while (startIndex <= i) {
                // 用treeMap维护数值，可以快速得出最大最小值
                if (treeMap.lastKey() - treeMap.firstKey() <= limit){
                    // 记录
                    max = Math.max(max, i - startIndex + 1);
                    // 满足条件，继续遍历
                    break;
                }else {
                    // 起始下标移除
                    set = treeMap.get(nums[startIndex]);
                    // 移除下标
                    set.remove(startIndex);
                    // 下标不存在了，移除这个元素
                    if (set.isEmpty()){
                        treeMap.remove(nums[startIndex]);
                    }
                    // 起始下标指针后移
                    startIndex++;
                }
            }
        }
        return max;
    }
}
