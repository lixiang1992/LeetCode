package struct.datastruct.tree.binarytree;

import struct.pub.tree.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 1325.删除给定的叶子节点
 */
public class RemoveLeafNodes_1325 {

    /**
     * 删除给定的叶子节点
     * 后序遍历，子树如果是目标叶子节点，直接删除即可，即将节点的父节点对应孩子指针置空
     * @param root root
     * @param target 目标值
     * @return 删除目标值之后的树
     */
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if(root == null){
            return null;
        }
        TreeNode node = root;
        TreeNode tempNode = root;// 最近访问节点
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()){
            if (node != null){
                stack.push(node);
                node = node.left;
            }else {
                node = stack.peek();
                // 右孩子不为空，且没有访问过
                if (node.right != null && node.right != tempNode){
                    // 进入右子树
                    node = node.right;
                }else {
                    node = stack.pop();// 出栈
                    tempNode = node;// 上一个访问节点记录
                    removeLeaf(node,node.left,target);// 左孩子叶子节点去除
                    removeLeaf(node,node.right,target);// 右孩子叶子节点去除
                    node = null;// 这里不置空，又会进入左子树，这是不行的
                }
            }
        }
        // 根节点额外处理一下，因为之前覆盖不到
        return isRemoveLeaf(root,target) ? null : root;
    }

    /**
     * 去除叶子节点，重置父节点与子节点的关系
     * @param parentNode 父节点
     * @param node 子节点
     * @param target 目标值
     */
    private void removeLeaf(TreeNode parentNode,TreeNode node,int target){
        if (!isRemoveLeaf(node,target)){
            return;
        }
        if (node == parentNode.left){
            parentNode.left = null;// 删除左叶子节点
        }else {
            parentNode.right = null;// 删除右叶子节点
        }
    }

    /**
     * 节点是否为可移除叶子节点
     * @param node node
     * @return 是否叶子节点
     */
    private boolean isRemoveLeaf(TreeNode node,int target){
        if (node == null){
            return false;
        }
        return node.left == null && node.right == null && node.val == target;
    }
}
