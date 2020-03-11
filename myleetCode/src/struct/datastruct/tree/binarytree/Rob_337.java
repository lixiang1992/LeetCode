package struct.datastruct.tree.binarytree;

import struct.pub.tree.TreeNode;

/**
 * 337.打家劫舍3
 * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。
 * 这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。
 * 一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。
 * 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
 * <p>
 * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
 */
public class Rob_337 {

    /**
     * 思路：自底向上递推，先处理左右子树情况，再决定是否选择当前节点的值，每次返回的都是两个值
     * 0-选择当前节点的最优解，1-不选当前节点的最优解
     * @param root 根节点
     * @return 获取物品价值的最大值
     */
    public int rob(TreeNode root) {
        int[] res = robRecursion(root);
        return Math.max(res[0],res[1]);
    }

    /**
     * 当前节点node存在两种情况，
     * 1.选当前，则左右子树不能选
     * 2.不选当前，则选择左右子树最优（左右孩子不是必选，左最优+右最优）
     * 取两者中的较大值即可
     *
     * @param node node
     * @return 结果数组
     */
    private int[] robRecursion(TreeNode node) {
        if (node == null){
            // res 0-表示选择当前，1-表示不选当前
            return new int[]{0,0};
        }
        // 后序遍历，先考虑左右子树的情况
        int[] leftRes = robRecursion(node.left);
        int[] rightRes = robRecursion(node.right);
        // 选中根节点的情况，左右孩子是不选的
        int choose = node.val + leftRes[1] + rightRes[1];
        // 不选根节点的情况，左右孩子任意节点可选，所以要取左右孩子选或不选两者情况的较大值累加
        // 即左子树的最优解+右子树的最优解
        int noChoose = Math.max(leftRes[0],leftRes[1]) + Math.max(rightRes[0],rightRes[1]);
        // 0-选择当前节点的最大情况，1-不选当前节点的最大情况
        return new int[]{choose,noChoose};
    }
}
