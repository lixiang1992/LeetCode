package struct.datastruct.tree.binarytree;

import struct.pub.tree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 5145.祖父节点值为偶数的节点和
 *
 * 给你一棵二叉树，请你返回满足以下条件的所有节点的值之和：
 *
 * 该节点的祖父节点的值为偶数。（一个节点的祖父节点是指该节点的父节点的父节点。）
 * 如果不存在祖父节点值为偶数的节点，那么返回 0 。
 *
 * 思路：层次遍历，如果节点是偶数，就将其孙子节点的和相加。
 */
public class SumEvenGrandparent_1315 {

    /**
     * 祖父节点值为偶数的节点和
     *
     * 层次遍历树，如果节点是偶数，就将其孙子节点的和相加。
     * @param root root
     * @return 祖父节点值为偶数的节点和
     */
    public int sumEvenGrandparent(TreeNode root) {
        int sum = 0;
        TreeNode node = root;
        // 层次遍历树
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            // 节点是偶数
            boolean isEven = (node.val & 1) == 0;
            if (node.left != null){
                queue.offer(node.left);
                // 节点值是偶数
                if (isEven){
                    // 左孩子的孩子节点和加入
                    sum += childNodeSumValue(node.left);
                }
            }
            if (node.right != null){
                queue.offer(node.right);
                // 节点值是偶数
                if (isEven){
                    // 右孩子的孩子节点和加入
                    sum += childNodeSumValue(node.right);
                }
            }
        }
        return sum;
    }

    /**
     * 孩子节点和
     * @param node treeNode
     * @return 孩子节点和
     */
    private int childNodeSumValue(TreeNode node){
        int childSum = 0;
        if (node.left != null){
            childSum += node.left.val;
        }
        if (node.right != null){
            childSum += node.right.val;
        }
        return childSum;
    }
}
