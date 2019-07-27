package list.link;

import struct.pub.list.ListNode;

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
}
