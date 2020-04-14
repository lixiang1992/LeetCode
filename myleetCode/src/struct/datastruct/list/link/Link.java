package struct.datastruct.list.link;


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
     * 141.检测链表是否有环
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
     * 142.给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     *
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
     *
     * 说明：不允许修改给定的链表。利用set集合，进阶用双指针
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if(head == null || head.next == null){
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(fast == slow){
                slow = head;
                while(slow != fast){
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
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
     * 24.两两交换链表节点
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        ListNode prev = null;
        ListNode node = head;
        // 交换链表节点的核心逻辑
        ListNode nextOne = null;// 下一步要到达的节点
        while(node != null){
            // 确认下一个开始的节点
            if (node.next != null){
                nextOne = node.next.next;
            }else{
                break;
            }
            if (prev != null) {
                prev.next = node.next;// 节点调换，prev的next指向当前节点的next
            } else {
              head = node.next; // prev指针为空，表示是头节点，需要转换头节点
            }
            node.next.next = node;// node.next的next指针指向当前node，断开之前的链表，完成两两交换的第一步
            node.next = nextOne;// node的next的指针指向下一个开始的节点
            prev = node;// 前指针转移
            node = nextOne;// 当前指针后移
        }
        return head;
    }

    /**
     * K个一组翻转链表，还是有点乱，要理一下
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode first = new ListNode(0);// 虚拟节点
        first.next = head;

        ListNode node = head;
        ListNode prevTailNode = first;// 上一组翻转的尾节点
        ListNode curReverseTailNode = first;// 当前反转链表的尾部节点

        while(true){
            int i = 0;
            // 遍历找到下一个翻转节点
            while (curReverseTailNode != null && i< k){
                curReverseTailNode = curReverseTailNode.next;
                i++;
            }
            // 为空表示数量不够了，不翻转，直接返回
            if (curReverseTailNode ==null){
                prevTailNode.next = node;
                break;
            }else{
                // 下一个翻转节点记录
                ListNode nextReverseNode = curReverseTailNode.next;
                // 链表断开，为了之后的链表翻转
                curReverseTailNode.next = null;
                // 上一个链表的尾部和反转后的头部链接起来
                prevTailNode.next = curReverseTailNode;
                // node节点是尾节点了
                prevTailNode = node;
                curReverseTailNode = node;
                // 链表翻转
                reverseList(node);
                node = nextReverseNode;
                // next指针重新执行，否则找第K个节点会断开
                curReverseTailNode.next = nextReverseNode;
            }
        }
        return first.next;
    }

    /**
     * 链表反转
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while(cur!=null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode first = new ListNode(0);// 虚拟节点
        first.next = head;
        ListNode firstTail = first;
        int i = 1;
        while(i<m){
            // 记录不反转之前链表的尾部节点
            firstTail = firstTail.next;
            i++;
        }

        ListNode prev = firstTail;
        ListNode node = firstTail.next;// 开始翻转的节点
        ListNode reverseTail = node;// 翻转后的尾部

        while(node != null && i<=n){
            firstTail.next = node;// 记录翻转链表的头部
            ListNode next = node.next;
            node.next = prev;
            prev = node;
            node = next;
            i++;
        }
        reverseTail.next = node;// 全部翻转完毕，链表重新接上
        return first.next;
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

    /**
     * 82. 删除排序链表中的重复元素 II
     * 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode dummy = new ListNode(head.val-1);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode node = head;
        while(true){
            ListNode next = node.next;
            if (next == null){
                break;
            }
            if (node.val == next.val){
                // 出现重复元素，前置链表断开
                prev.next = null;
//                // 这一步可以不要，
                node.next = null;
                // 后移，这一段都不要了
                node = next;
            }else {
                // 两者不等了
                // 之前存在相同元素，prev.next才会空
                if (prev.next == null){
                    // 指向下一个，但是prev不移动，无法确定next是不是重复元素
                    prev.next = next;
                    node.next = null;
                }else {
                    prev = prev.next;
                }
                node = next;
            }
        }
        return dummy.next;
    }

    /**
     * 203.移除链表元素
     * @param head
     * @param val
     * @return
     */
    public ListNode removeElements(ListNode head, int val) {
        if (head == null){
            return null;
        }
        ListNode prev = null;
        ListNode node = head;
        while (node != null){
            if (node.val == val){
                if (prev == null){
                    ListNode next = head.next;
                    head.next = null;
                    head = next;
                    node = head;
                }else {
                    prev.next = node.next;
                    node.next = null;
                    node = prev.next;
                }
            } else {
                prev = node;
                node = node.next;
            }

        }
        return head;
    }

    /**
     * 143.重排链表
     * 给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
     * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
     *
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     *
     * 1.双指针前进，找到中间节点和尾部节点
     * 2.从中间节点开始反转链表，到最后一个节点位置
     * 3.头尾同时遍历，尾部往头部的中间插入，完美
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null){
            return;
        }
        ListNode slow = head;
        ListNode fast = slow;
        ListNode tailPrev = null;
        // 找到中间节点
        while (fast != null && fast.next != null){
            slow = slow.next;
            tailPrev = fast.next;
            fast = fast.next.next;
        }
        // 快指针如果出了链表了，尾部节点就是tailPrev
        if (fast == null){
            fast = tailPrev;
        }
        // 从中间节点反转链表
        ListNode prev = slow;
        ListNode cur = prev.next;
        while (cur != null){
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        // 从头开始遍历
        ListNode front = head;
        ListNode next = front.next;
        ListNode tail = fast;
        ListNode tailNext = fast.next;
        while (front != slow && tailNext != null){
            tail.next = next;
            front.next = tail;
            front = next;
            next = next.next;
            tail = tailNext;
            tailNext = tailNext.next;
        }
        slow.next = null;
    }

    /**
     * 147.对链表进行插入排序
     * @param head
     * @return
     */
    public ListNode insertionSortList(ListNode head) {
        if (head == null){
            return head;
        }
        ListNode newHead = head;// 新的头部节点


        ListNode node = head.next;
        ListNode prevNode = head;// 前指针
        ListNode nextNode;// 下一个要遍历的指针

        while(node != null){
            nextNode = node.next;// 记录下一个节点
            if (node.val < newHead.val){// 比头节点要小
                // 从这删除
                prevNode.next = node.next;// 先删除这个节点，再插入到应该去的地方
                node.next = null;
                node.next = newHead;
                newHead = node;
                node = nextNode;// 设置到头部，新头部指针移动
            }else {
                // 指针后移
                while (node.next != null){
                    prevNode = node;
                    node = node.next;
                }
            }

        }
        return newHead;
    }

    /**
     * 圆圈中最后留下的数字
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining(int n, int m) {
        List<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        int i = 1;
        while (n > 1){
            // 定位到下一个
            i = (i + m - 1) % n;
            list.remove(i);
            n--;
        }
        return list.get(0);
    }

    /**
     * 445.两数相加
     * 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。
     * 它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
     *
     * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
     *
     * 进阶：
     * 如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
     *
     * @param l1 list1
     * @param l2 list2
     * @return 合并后的List
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Deque<ListNode> stack1 = new LinkedList<>();
        Deque<ListNode> stack2 = new LinkedList<>();
        // 栈初始化
        initStack(l1,stack1);
        initStack(l2,stack2);
        // 哨兵头节点
        ListNode head = new ListNode(-1);
        int add = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty() || add > 0){
            int val1 = stack1.isEmpty() ?  0 : stack1.pop().val;
            int val2 = stack2.isEmpty() ?  0 : stack2.pop().val;
            int val = val1 + val2 + add;
            // 插入到head的屁股后面
            insertNodeByHead(head,val % 10);
            // 进位的地方
            add = val / 10;
        }

        return head.next;
    }

    private void insertNodeByHead(ListNode head,int val){
        ListNode node = new ListNode(val);
        node.next = head.next;
        head.next = node;
    }

    private void initStack(ListNode node,Deque<ListNode> stack){
        while (node != null){
            stack.push(node);
            node = node.next;
        }
    }
}
