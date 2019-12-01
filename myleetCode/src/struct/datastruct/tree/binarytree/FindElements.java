package struct.datastruct.tree.binarytree;

import struct.pub.tree.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 受污染的二叉树
 * 给出一个满足下述规则的二叉树：
 * <p>
 * root.val == 0
 * 如果 treeNode.val == x 且 treeNode.left != null，那么 treeNode.left.val == 2 * x + 1
 * 如果 treeNode.val == x 且 treeNode.right != null，那么 treeNode.right.val == 2 * x + 2
 * 现在这个二叉树受到「污染」，所有的 treeNode.val 都变成了 -1。
 * <p>
 * 请你先还原二叉树，然后实现 FindElements 类：
 * <p>
 * FindElements(TreeNode* root) 用受污染的二叉树初始化对象，你需要先把它还原。
 * bool find(int target) 判断目标值 target 是否存在于还原后的二叉树中并返回结果。
 */
public class FindElements {

    private TreeNode root;

    public FindElements(TreeNode root) {
        // 层次遍历还原
        root.val = 0;
        this.root = root;
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (node.left != null) {
                node.left.val = (node.val << 1) + 1;
                queue.offer(node.left);
            }
            if (node.right != null) {
                node.right.val = (node.val << 1) + 2;
                queue.offer(node.right);
            }
        }
    }

    /**
     * 不用set的话，可以采用满二叉树遍历的方法
     * target == ((target - 1) / 2 * 2 + 1) 满足这个条件的，就是其父节点的左孩子，否则是右孩子，至于为什么可以画图理解
     * 然后target = (target - 1) / 2 往上递归，每次倒序记录是这个是左还是右（用个栈来记录），直到根节点
     * 然后再从根节点按照栈的弹出顺序，往左孩子或者右孩子递推，中间如果有空节点，则找不到，否则是找到
     */
    public boolean find(int target) {
        Deque<Boolean> stack = new LinkedList<>();// 双端队列模拟栈
        while (target > 0) {
            stack.push(target == ((target - 1) / 2 * 2 + 1));// 是否是左孩子
            target = (target - 1) >> 1;
        }
        TreeNode node = this.root;
        while (!stack.isEmpty()) {
            if (stack.pop()) {// 是左孩子，往左节点递推
                node = node.left;
            } else {// 否则往右节点递推
                node = node.right;
            }
            if (node == null) {
                return false;
            }
        }
        return true;
    }
}
