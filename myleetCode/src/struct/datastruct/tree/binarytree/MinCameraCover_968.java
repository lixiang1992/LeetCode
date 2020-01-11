package struct.datastruct.tree.binarytree;

import struct.pub.tree.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 968.监控二叉树
 * 给定一个二叉树，我们在树的节点上安装摄像头。
 * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
 * 计算监控树的所有节点所需的最小摄像头数量。
 */
public class MinCameraCover_968 {

    /**
     * 968.监控二叉树
     * 给定一个二叉树，我们在树的节点上安装摄像头。
     * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
     * 计算监控树的所有节点所需的最小摄像头数量。
     *
     * @param root 根节点
     * @return 最小监控的数量
     */
    public int minCameraCover(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int sumValue = 0;
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        TreeNode tempNode = node;// 上一个遍历到的节点
        // 采用后续遍历
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                if (isLeaf(node.left) || isLeaf(node.right)) {
                    sumValue++;
                    node.val = 1;// 1表示监控器节点
                    if (node.left != null) {
                        node.left.val = 2;// 2表示被监控到
                    }
                    if (node.right != null) {
                        node.right.val = 2;// 2表示被监控到
                    }
                }
                node = node.left;
            } else {
                node = stack.peek();
                if (node.right == null || node.right == tempNode) {
                    // 当前节点出栈，出栈判断子树和自身是否在监控中
                    if (!isControlNode(node.left) || !isControlNode(node.right)) {
                        // 左右孩子有一个没有被监控了
                        node.val = 1;
                        sumValue++;// 当前节点是监控器
                    } else if (isCameraNode(node.left) || isCameraNode(node.right)) {
                        // 左右孩子有一个是监控器节点
                        if (node.val == 0) {
                            node.val = 2;// 当前节点被监控
                        }
                    }
                    tempNode = stack.pop();
                    node = null;
                } else {
                    // 进入右子树
                    node = node.right;
                }
            }
        }
        if (tempNode.val == 0) {// 最后一个节点可能漏掉
            tempNode.val = 1;// 标记为监控节点
            sumValue++;
        }
        return sumValue;
    }

    /**
     * 是叶子节点
     *
     * @param node 节点
     * @return 是否叶子节点
     */
    private boolean isLeaf(TreeNode node) {
        return node != null && (node.left == null && node.right == null);
    }

    /**
     * 是监控器节点
     * node.val = 1表示该节点是监控器
     *
     * @param node 节点
     * @return 节点是否监控器
     */
    private boolean isCameraNode(TreeNode node) {
        return node != null && node.val == 1;
    }

    /**
     * 是被监控节点
     * node.val 是1或2都表示被监控了
     *
     * @param node 节点
     * @return 节点是否被监控
     */
    private boolean isControlNode(TreeNode node) {
        return node == null || node.val > 0;
    }

}
