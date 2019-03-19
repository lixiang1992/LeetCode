package list.link;


import struct.pub.list.ListNode;
import struct.pub.tree.TreeNode;

import java.util.*;


public class Link {
    /**
     * 有序数组转二叉搜索树
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if(nums == null){
            return null;
        }
        return sortBST(nums,0,nums.length - 1);
    }

    private TreeNode sortBST(int[] nums,int start,int end){
        if(start > end){
            return null;
        }
        int mid = start + ((end-start)>>1);
        TreeNode node = new TreeNode(nums[mid]);
        node.left = sortBST(nums,start,mid - 1);
        node.right = sortBST(nums,mid+1,end);
        return node;
    }

    /**
     * 检测链表是否有环
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        ListNode slowNode = head;
        ListNode fastNode = head;
        while (fastNode != null && fastNode.next != null){
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
            if(slowNode == fastNode){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return 0.0;
    }

    /**
     * 19.删除链表倒数第N个节点
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode node = head;
        ListNode numKNode = head;
        ListNode prevNode = null;
        int i = 1;
        while (i<n && numKNode != null){
            numKNode = numKNode.next;
            i++;
        }
        if(numKNode == null){
            return head;
        }
        while (numKNode.next != null){
            prevNode = node;
            node = node.next;
            numKNode = numKNode.next;
        }
        if (prevNode != null){
            prevNode.next = node.next;
            node.next = null;
        }else {
            head = head.next;
        }
        return head;
    }

    /**
     * 20.有效的括号
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Map<Character,Character> map = new HashMap<>();
        map.put(')','(');
        map.put(']','[');
        map.put('}','{');
        Stack<Character> stack = new Stack<>();
        for (int i = 0;i<s.length();i++){
            Character c1 = s.charAt(i);
            Character c2 = map.get(c1);
            if (c2 == null){
                stack.push(c1);
            }else if(stack.isEmpty() || !c2.equals(stack.pop())){
                return false;
            }

        }
        return stack.isEmpty();
    }

    /**
     * 20.合并两个有序链表 O(N)复杂度，可以优化为O(logN)，采用分治思想
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        ListNode node1 = l1;
        ListNode node2 = l2;
        ListNode head;
        if (node1.val <= node2.val){
            head = node1;
            node1 = node1.next;
        }else {
            head = node2;
            node2 = node2.next;
        }
        ListNode prev = head;
        while (node1 != null && node2 != null){
            if(node1.val <= node2.val){
                prev.next = node1;
                node1 = node1.next;
            }else {
                prev.next = node2;
                node2 = node2.next;
            }
            prev = prev.next;
        }
        if(node1 == null){
            prev.next = node2;
        }else if(node2 == null){
            prev.next = node1;
        }
        return head;
    }
    /**
     * 23.合并K个有序的链表
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        Queue<ListNode> queue = new LinkedList<>();
        for (ListNode node : lists){
            queue.offer(node);
        }
        while (queue.size() > 1){
            ListNode head1 = queue.poll();
            ListNode head2 = queue.poll();
            ListNode newNode = mergeTwoLists(head1,head2);
            queue.offer(newNode);
        }
        return queue.peek();
    }

    /**
     * 41.缺失的第一个正整数
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
//        for (int i = 0;i<nums.length;i++){
//            if (nums[i] < 1 || nums[i] > nums.length || nums[i] == i + 1){// 非正整数和值大于数组长度的跳过
//                continue;
//            }
//            int temp = nums[nums[i]-1];
//            nums[nums[i]-1] = nums[i];
//            nums[i] = temp;
//        }
//        for (int i=0;i<nums.length;i++){
//            if (nums[i] != i+1){
//                return i+1;
//            }
//        }
//        return nums.length+1;
        int len = nums.length;
        for(int i = 0; i < len;){
            if(nums[i] == i + 1){
                i++ ;
            }
            else{
                if(nums[i] >= 1 && nums[i] <= len && nums[nums[i] - 1] != nums[i]){
                        int temp = nums[nums[i]-1];
                        nums[nums[i]-1] = nums[i];
                        nums[i] = temp;
                }
                else{
                    i++;
                }
            }
        }
        for(int i = 0; i < len; i++){
            if(nums[i] != i + 1){
                return i + 1;
            }
        }
        return len + 1;
    }
}
