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
     *
     * 思路：本题核心：
     *  1.如果存在叶子节点，则叶子节点的父节点一定要是监控节点
     *  2.如果左右孩子节点有一个没有监控，则该节点需要设置监控器
     *  3.如果左右孩子节点都被监控了，该节点可以暂时不处理，只要他有父节点，就可以回来监控他
     *  4.到了root节点，如果没有被监控，则加上监控器。否则不处理
     *
     * @param root 根节点
     * @return 最小监控的数量
     */
    public int minCameraCover_1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int ans = 0;
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        TreeNode tempNode = node;// 上一个遍历到的节点
        // 采用后续遍历
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.peek();
                if (node.right == null || node.right == tempNode) {
                    // 节点出栈，判断左右子树的状态，决定自身状态
                    // -1表示不需要监控，0表示未监控，1表示被监控，2表示是监控器
                    int leftVal = getNodeVal(node.left);
                    int rightVal = getNodeVal(node.right);
                    if (leftVal == 0 || rightVal == 0){
                        // 左右孩子存在一个没有被监控，则当前节点要设置为监控器节点
                        node.val = 2;
                        ans++;// 监控器+1
                    }else if (leftVal == 2 || rightVal == 2){
                        // 左右孩子有一个是监控器，则当前节点被监控了
                        node.val = 1;
                    }
                    tempNode = stack.pop();
                    node = null;
                } else {
                    // 进入右子树
                    node = node.right;
                }
            }
        }
        if (root.val == 0) {// root一个节点可能漏掉
            root.val = 2;// 标记为监控节点
            ans++;
        }
        return ans;
    }

    /**
     * 获取node.val
     * @param node node
     * @return node的状态
     */
    private int getNodeVal(TreeNode node){
        return node == null ? -1 : node.val;
    }

    // 递归解法
    public int minCameraCover(TreeNode root){
        int[] ans = {0};
        int status = minCameraCoverRecursion(root,ans);
        return status == 0 ? ans[0] + 1 : ans[0];
    }

    //     * 思路：本题核心：
    //     *  1.如果存在叶子节点，则叶子节点的父节点一定要是监控节点
    //     *  2.如果左右孩子节点有一个没有监控，则该节点需要设置监控器
    //     *  3.如果左右孩子节点都被监控了，该节点可以暂时不处理，只要他有父节点，就可以回来监控他
    //     *  4.到了root节点，如果没有被监控，则加上监控器。否则不处理
    private int minCameraCoverRecursion(TreeNode root,int[] ans){
        // -1表示不需要监控，0表示未监控，1表示被监控，2表示是监控器
        if (root == null){
            return -1;
        }
        if (root.left == null && root.right == null) {
            // 叶子节点特判，因为叶子节点的父节点是一定要被监控的
            return 0;
        }
        // 后序遍历，获取左右孩子的状态
        int left = minCameraCoverRecursion(root.left,ans);
        int right = minCameraCoverRecursion(root.right,ans);
        // 根据左右孩子状态，决定当前节点是否放置监控器，以及当前节点的状态
        if (left == 0 || right == 0){
            // 左右孩子存在一个没有被监控，则当前节点要设置为监控器节点
            ans[0]++;
            return 2;
        }
        if (left == 1 && right == 1){
            // 左右孩子都被监控了，则当前节点是暂不处理的，因为当前节点父节点会回来照顾他的
            return 0;
        }
        if (left == 2 || right == 2){
            // 左右孩子有一个是监控器，则当前节点被监控了
            return 1;
        }
        return 0;
    }

}
