package struct.datastruct.tree.binarytree;

import struct.pub.tree.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 1343.分裂二叉树的最大乘积
 *
 * 给你一棵二叉树，它的根为 root 。请你删除 1 条边，使二叉树分裂成两棵子树，且它们子树和的乘积尽可能大。
 * 由于答案可能会很大，请你将结果对 10^9 + 7 取模后再返回。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-product-of-splitted-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxProduct_1343 {

    // 以TreeNode为root的树节点之和
    private Map<TreeNode,Long> nodeValueMap = new HashMap<>();
    private long max = Integer.MIN_VALUE;
    private int mod = 1000000007;// 结果取模数

    /**
     * 分裂二叉树的最大乘积
     * @param root root
     * @return 最大乘积
     */
    public int maxProduct(TreeNode root) {
//        nodeValueMap.put(null,0L);// 缓存null的value值
        maxProduct(root,0L);
        return (int) (max % mod);
    }

    /**
     * 子树最大乘积
     * @param root root
     * @param parentSum 父节点所在树的全部节点之和
     */
    private void maxProduct(TreeNode root,long parentSum){
        if (root == null){
            return;
        }
        parentSum += root.val;
        // 递归左树
        long leftSum = nodeValueSum(root.left);
        // 递归右树
        long rightSum = nodeValueSum(root.right);
        long maxSum = Math.max((leftSum+parentSum)*rightSum,(rightSum+parentSum)*leftSum);
        if (maxSum > max){
            max = maxSum;
            // 进入 左子树，原 右 子树的节点和要累加到父节点所在树节点之和中
            maxProduct(root.left,parentSum + nodeValueMap.getOrDefault(root.right,0L));
            // 进入 右子树，原 左 子树的节点和要累加到父节点所在树节点之和中
            maxProduct(root.right,parentSum + nodeValueMap.getOrDefault(root.left,0L));
        }
        // 小于就不再递归了，因为左右子树节点之和本身是一个定制，|a-b|的值是一个从大到小，然后又从小到大的。
        // 那么就是在|a-b|最小的时候，a*b最大,（均值不等式），如果maxCount不在大于max了，就没有继续递归的必要了
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
        Long sum = nodeValueMap.get(node);
        if (sum != null){
            return sum;
        }else {
            // 节点和
            sum = node.val + nodeValueSum(node.left) + nodeValueSum(node.right);
            // 记忆化
            nodeValueMap.put(node,sum);
        }
        return sum;
    }
}
