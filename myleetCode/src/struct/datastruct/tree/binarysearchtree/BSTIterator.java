package struct.datastruct.tree.binarysearchtree;

import struct.pub.tree.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 173.二叉搜索树迭代器
 * 实现一个二叉搜索树迭代器。你将使用二叉搜索树的根节点初始化迭代器。
 *
 * 调用 next() 将返回二叉搜索树中的下一个最小的数。
 *
 * 注意: next() 和hasNext() 操作的均摊时间复杂度是O(1)，并使用 O(h) 内存，其中 h 是树的高度。
 */
public class BSTIterator {

    private Deque<TreeNode> deque = new LinkedList<>();// 双端队列，模拟栈

    public BSTIterator(TreeNode root) {
        addNode(root);
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !deque.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode curNode = deque.pollLast();// 获得最小节点
        int value = curNode.val;
        curNode = curNode.right;// 往右边移动
        addNode(curNode);// 因为左子树在之前已经加入队列，这个时候把最小节点的右子树加入
        return value;
    }

    private void addNode(TreeNode curNode){
        // 二叉搜索树的性质 left < root < right
        // 每个节点都把其左子树加入双端队列，当前双端队列的队尾肯定是最小的
        while (curNode != null){
            deque.offer(curNode);
            curNode = curNode.left;
        }
    }
}
