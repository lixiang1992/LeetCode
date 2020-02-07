package struct.datastruct.tree.binarytree;

import struct.pub.tree.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 226.翻转二叉树，4种遍历
 */
public class InvertTree_226 {

    /**
     * 交换左右子树
     * @param node treeNode
     */
    private void inventTreeNode(TreeNode node){
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    /**
     * 先序遍历翻转二叉树
     * @param root root
     * @return root
     */
    public TreeNode invertTreeByPre(TreeNode root){
        if (root == null){
            return null;
        }
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()){
            if (node != null){
                // 交换左右子树
                inventTreeNode(node);
                // 原先的先序遍历
                stack.push(node);
                node = node.left;
            }else {
                // 空，出栈，进入右子树
                node = stack.pop();
                node = node.right;
            }
        }
        return root;
    }

    /**
     * 中序遍历翻转二叉树
     * @param root root
     * @return root
     */
    public TreeNode invertTreeByIn(TreeNode root){
        if (root == null){
            return null;
        }
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()){
            if (node != null){
                stack.push(node);
                node = node.left;
            }else {
                // 空，出栈
                node = stack.pop();
                // 交换左右子树
                inventTreeNode(node);
                // 这里因为交换了，不能继续往右子树走，因为现在的右子树，是原来的左子树
                // 所以接下来要走左边
                node = node.left;
                // 正常的中序遍历走右边
//                node = node.right;
            }
        }
        return root;
    }

    /**
     * 后序遍历翻转二叉树
     * @param root root
     * @return root
     */
    public TreeNode invertTreeByPost(TreeNode root){
        if (root == null){
            return null;
        }
        TreeNode node = root;
        TreeNode tempNode = null;// 记录上次访问的节点
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()){
            if (node != null){
                stack.push(node);
                node = node.left;
            } else {
                node = stack.peek();
                if (node.right == null || node.right == tempNode){
                    // 右节点为空，或者右节点已经被访问过
                    // 当前节点出栈，并记录上次的访问
                    tempNode = stack.pop();
                    // 交换左右子树
                    inventTreeNode(node);
                    // 这里要置空，不然node还是存在，又会往左子树去了
                    node = null;
                }else {
                    // 访问右子树
                    node = node.right;
                }
            }
        }
        return root;
    }

    /**
     * 层次遍历翻转二叉树
     * @param root root
     * @return root
     */
    public TreeNode invertTreeByLevel(TreeNode root) {
        if(root == null){
            return null;
        }
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while(!queue.isEmpty()){
            node = queue.poll();
            // 交换左右子树
            inventTreeNode(node);
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
        }
        return root;
    }

}
