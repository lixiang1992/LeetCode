package design;

import struct.pub.tree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 919.完全二叉树插入器
 */
public class CBTInserter {
    private TreeNode root;
    private Queue<TreeNode> queue;// 度小于2的节点的队列
    private boolean isLeft = false;

    public CBTInserter(TreeNode root){
        this.root = root;
        this.queue = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            TreeNode node = queue.poll();
            if (node.left == null || node.right == null){
                this.queue.offer(node);
            }
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
        TreeNode node = this.queue.peek();
        if (node.left == null){
            this.isLeft = true;
        }else{
            this.isLeft = false;
        }
    }

    public int insert(int v) {
        TreeNode newNode = new TreeNode(v);
        TreeNode node = this.queue.peek();
        if (isLeft){
            node.left = newNode;
        }else {
            node.right = newNode;
            this.queue.poll();
        }
        isLeft = !isLeft;
        this.queue.offer(newNode);
        return node.val;
    }

    public TreeNode get_root() {
        return root;
    }
}
