package list.link;

import struct.pub.list.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 双指针
 */
public class DoublePoint {

    /**
     * 11.盛水最多的容器
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int left = 0;// 左侧指针
        int right = height.length - 1;// 右侧指针
        int maxArea = 0;// 初始化的最大面积,底*高
        while (left < right){
            maxArea = Math.max(maxArea,(right - left) * Math.min(height[left],height[right]));
            // 这里将高度小的指针移动，左指针后移，右指针左移
            // 为什么这样做呢，因为面积的高取决于小的那一个。
            // 如果将大的一侧指针移动，面积的高依旧会被小的那一侧限制住，高要么保持不变，要么会更小，面积就会越来越小
            // 讲小的一侧指针移动，高度是可能大可能小的，这样面积才会有变大的可能。
            if (height[left] < height[right]){
                left++;// 左边高度小，左指针后移
            }else{
                right--;// 右边高度小，右指针前移
            }
        }
        return maxArea;
    }

    /**
     * 26.删除排序数组中的重复项
     * 数组完成排序后，我们可以放置两个指针 i 和 j，其中 i 是慢指针，而 j 是快指针。只要 nums[i] = nums[j]，我们就增加 j 以跳过重复项。
     *
     * 当我们遇到 nums[j] != nums[i]时，跳过重复项的运行已经结束，因此我们必须把它（nums[j]）的值复制到nums[i+1]。然后递增 i，接着我们将再次重复相同的过程，直到 j 到达数组的末尾为止。
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        int i = 0;
        for (int j = 1;j<nums.length;j++){
            if (nums[j] != nums[i]){// 不相等表示是不同元素
                i++;// 数组长度增加1，并且i表示当前不重复元素所在的下标+1的位置
                nums[i] = nums[j];//修改i+1后的数组元素
            }
        }
        return i;
    }

    /**
     * 三数之和
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0;i<nums.length;i++){
            if (nums[i] > 0){
                break;
            }
            if (i>0 && nums[i-1] == nums[i]){
                continue;// nums[i]重复了，直接跳过
            }
            int target = 0 - nums[i];
            // 前后指针往中间夹
            int j = i + 1;
            int k = nums.length - 1;
            while (j<k){
                if (nums[j] + nums[k] < target){
                    j++;
                } else if(nums[j] + nums[k] > target){
                    k--;
                }else {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[j]);
                    list.add(nums[k]);
                    result.add(list);
                    while (j<k && nums[j+1] == nums[j]){
                        j++;
                    }
                    while (j<k && nums[k-1] == nums[k]){
                        k--;
                    }
                    j++;
                    k--;
                }
            }
        }
        return result;
    }
    /**
     * 42.接雨水
     * @param height
     * @return
     */
    public int trap(int[] height) {
        
        return 0;
    }

    /**
     * 61.旋转链表
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (k <= 0 || head == null || head.next == null){
            return head;// 只有一个节点的链表没有计算的必要
        }
        ListNode temp = head;
        int length = 1;
        // 获取链表长度和尾部节点
        while (temp.next != null){
            temp = temp.next;
            length++;
        }
        ListNode tail = temp;//尾部节点
        //k%length是原来头节点现在的偏移量（下标），那么转移到新的头结点的下标，就是原尾结点减掉对应的偏移量
        int newHeadIndex = length - k % length;// 新头部节点的下标
        if(newHeadIndex == length){
            return head;
        }
        // 找到新的下标节点
        int index = 0;
        ListNode prev = null;// 前置指针是为了去掉这个节点到新头部节点的指针，否则链表会成环
        ListNode newHead = head;
        tail.next = head;// 原尾节点的next指向原来的头节点
        while (index < newHeadIndex){
            index++;
            prev = newHead;
            newHead = newHead.next;
        }
        prev.next = null;// 这时候prev是新的尾部节点了
        // 新链表形成
        return newHead;
    }

    /**
     * 86.分隔链表
     * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
     *
     * 你应当保留两个分区中每个节点的初始相对位置
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        ListNode smallerHead = null;// 比x小的头结点
        ListNode largerHead = null;// 比x大的头结点
        ListNode curNode = head;
        ListNode smallerCur = null;
        ListNode largerCur = null;
        while (curNode != null){
            ListNode next = curNode.next;
            if (curNode.val < x){
                if (smallerHead == null){// 初始化小于x的头节点
                    smallerHead = curNode;
                    smallerCur = smallerHead;
                }else{// 记录小于部分的链表
                    smallerCur.next = curNode;
                    smallerCur = smallerCur.next;
                }
            }else {
                if (largerHead == null){
                    largerHead = curNode;
                    largerCur = largerHead;
                }else {
                    largerCur.next = curNode;
                    largerCur = largerCur.next;
                }
            }
            curNode.next = null;// 下一级指针必须置空，不然链表会成环，因为旧的节点关系指针存在，不会被干掉，只能这里手动断开原来的链表关系
            curNode = next;
        }
        if (smallerCur != null){
            smallerCur.next = largerHead;// value较小链表和value较大链表链接起来
            return smallerHead;
        }else {
            return largerHead;
        }
    }

    /**
     * merge two array
     * double point , from back to front
     * 双指针法，从后往前
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // two get pointers for nums1 and nums2
        int p1 = m - 1;
        int p2 = n - 1;
        int end = m + n - 1;
        // 从尾部开始遍历
        // while there are still elements to compare
        while (p1 >= 0 && p2 >= 0){
            // compare two elements from nums1 and nums2
            // and add the largest one in nums1
            if (nums1[p1] < nums2[p2]){
                nums1[end] = nums2[p2];
                p2--;
            }else{
                nums1[end] = nums1[p1];
                p1--;
            }
            end--;
        }
        // add missing elements from nums2
        // 如果p1>= 0，就不用管了，剩下num1的数肯定是最小的
        // 如果p2>= 0，则说明nums2里面剩余的元素都比nums1[0]的小，直接复制过来
        while(p2 >=0){
            nums1[p2] = nums2[p2];
            p2--;
        }
    }

    /**
     * 76.最小覆盖子串
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        StringBuilder target = new StringBuilder();
        for (int i = 0;i<s.length();i++){
            char c = s.charAt(i);
            target.append(c);

        }
        return null;
    }

    /**
     * 80. 删除排序数组中的重复项 II
     *
     * 原地删除肯定是双指针，一个指向遍历的元素，一个指向可以写入的位置，后者的大小是小于等于前者的，关键在于题目条件的转化，如何实现限制最多两次的重复出现。
     * 我们先不考虑边界情况，只考虑中间的情况，假设当前遍历位置为i，写指针的可写入位置为current+1，对于i处的值，其写入的条件是重复小于等于2次，我们考虑已经写入的最后两位current和current-1，
     * 这两个位置的情况有两个，相等和不相等，
     * 首先考虑相等的情况，此时若i处的值和current-1或者说current处的值相同，那么，i处的值肯定不能加入；
     * 然后考虑不相等的情况，即current-1和current处值不相等，那么i处的值无论为什么，都满足题意的，即可以加入，综上所述，当i处的值与current-1处的值不相等时，i处的值可以加入，其他情况均不能加入。
     * 接着考虑边界情况，我们只需要考虑开始即可，开始时，前两个值无论等还是不等，都要原封不动的挪到新数组里，由于新数组就是在原数组上进行修改的，因此前两位直接不动即可，只需要修改遍历指针和写入指针就行。
     * 以上算法只需要进行一次遍历即可，时间复杂度O(n)，空间复杂度O(1)。
     *
     * @param nums
     * @return
     */
    public int removeDuplicatesII(int[] nums) {
        if (nums.length <= 2){
            return nums.length;
        }
        int cur = 1;
        // 从2开始是因为前两个数组，不管相等与否，都要写进来
        for (int j = 2; j<nums.length ;j++){
            // 下标为j的，其实只要与cur和cur-1两个比较即可，如果cur == cur-1，那么j与他们相同就不能进来，
            // 如果cur != cur-1，那么j无论和cur,cur-1相等与否都可以进来，所以只要cur-1和j不一样，cur+1的值就可以变成j
            if (nums[cur - 1] != nums[j]){
                nums[++cur] = nums[j];

            }
        }
        return cur + 1;
    }

    /**
     * 725.分隔链表
     * @param root
     * @param k
     * @return
     */
    public ListNode[] splitListToParts(ListNode root, int k) {
        int length = 0;
        ListNode node = root;
        // 获取链表长度
        while (node != null){
            node = node.next;
            length++;
        }
        int size = length / k;// 分成size个数组
        int mod = length % k;// 每个数组多出来的一个，有mod个
        ListNode[] listNodes = new ListNode[k];
        node = root;
        for (int i=0;i<k && node != null;i++){
            listNodes[i] = node;
            int currentSize = size + (mod-- > 0 ? 1: 0);// mod>0的时候，要多一个节点
            for (int j = 0;j < currentSize - 1;j++){
                node = node.next;
            }
            ListNode next = node.next;
            node.next = null;// 链表断开
            node = next;
        }
        return listNodes;
    }

}
