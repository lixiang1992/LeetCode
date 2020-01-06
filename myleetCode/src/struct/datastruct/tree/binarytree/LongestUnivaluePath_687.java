package struct.datastruct.tree.binarytree;

import struct.pub.tree.TreeNode;

/**
 * 687.最长同值路径
 * 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
 *
 * 注意：两个节点之间的路径长度由它们之间的边数表示。
 */
public class LongestUnivaluePath_687 {

    private int maxPath = 0;

    public int longestUnivaluePath(TreeNode root) {
        if (root == null){
            return 0;
        }
        helper(root);
        return maxPath;
    }

    /**
     * 递归获取当前root为根节点的最长同值路径
     * @param root root
     * @return 最长同值路径边的个数
     */
    private int helper(TreeNode root){
        if (root == null){
            return 0;
        }
        int left = helper(root.left);// 左子树当前同值数
        int right = helper(root.right);// 右子树当前同值数
        if (root.left != null && root.val == root.left.val){
            left++;// 如果root和left相同，左子树当前同值数+1
        }else {
            left = 0;// 不同的话，最长同值数在这里就断开了，左子树当前同值数归0
        }
        if (root.right != null && root.val == root.right.val){
            right++;// 如果root和right相同，右子树当前同值数+1
        }else {
            right = 0;// 不同的话，最长同值数在这里就断开了，右子树当前同值数归0
        }
        // 当前节点往父节点走的最大路径节点数，因为左右子树只能留下一个，所以取较大的那一个
        int curPath = Math.max(left,right);
        // 经过当前节点的最长同值路径
        maxPath = Math.max(maxPath,left + right);// 同值路径可以经过root，也可以不经过root，所以左右直接相加
        return curPath;// 返回root的当前最长同值路径
    }
}
