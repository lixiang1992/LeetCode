package struct.datastruct.tree.binarytree;

import struct.pub.tree.TreeNode;

/**
 * 1339.分裂二叉树的最大乘积
 *
 * 给你一棵二叉树，它的根为 root 。请你删除 1 条边，使二叉树分裂成两棵子树，且它们子树和的乘积尽可能大。
 * 由于答案可能会很大，请你将结果对 10^9 + 7 取模后再返回。
 *
 * 思路：自底向上推理，一旦自顶向下推理，考虑的逻辑变得复杂，可以转换思路，有自底向上（先序->后序）
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-product-of-splitted-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxProduct_1339 {

    private long max = 0;
    private int mod = 1000000007;// 结果取模数

    /**
     * 分裂二叉树的最大乘积
     * @param root root
     * @return 最大乘积
     */
    public int maxProduct(TreeNode root) {
        maxProduct(root,nodeValueSum(root));
        return (int) (max % mod);
    }

    /**
     * 子树最大乘积
     * @param root root
     * @param sum 树的全部节点之和
     */
    private long maxProduct(TreeNode root,long sum){
        if (root == null){
            return 0L;
        }
        // 后序遍历，取节点之和，获取最大值
        long nodeSum = root.val + maxProduct(root.left,sum) +  maxProduct(root.right,sum);
        max = Math.max(max,(sum-nodeSum)*nodeSum);
        return nodeSum;// 节点和
    }

    /**
     * 节点子树节点之和
     * @param node node
     * @return 子树之和
     */
    private long nodeValueSum(TreeNode node){
        if (node == null){
            return 0L;
        }
        return node.val + nodeValueSum(node.left) + nodeValueSum(node.right);
    }
}
