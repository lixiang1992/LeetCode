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

//    /**
//     * 337.打家劫舍 III
//     *
//     * 动态规划算法思想：以node为根节点的最优解要氛围两种状态
//     * 1.当前根节点node不取，则node的最优解为node.left的最优解+node.right的最优解。
//     *  即->node.maxValue = node.left.maxValue + node.right.maxValue
//     * 2.当前根节点node要取，则node的直接左右孩子都不能取，node的最优解是node.left的左右子树最优解之和+node.right的左右子树最优解之和
//     * 即-> node.maxValue = node.value + node.left.left.maxValue + node.left.right.maxValue + node.right.left.maxValue + node.right.right.maxValue
//     *
//     * @param root 根节点
//     * @return 最优解
//     */
//    public int rob(TreeNode root) {
//        int[] res = dpRob(root);
//        return Math.max(res[0],res[1]);
//    }

//    /**
//     * 后序遍历的处理，不用再次重复计算左右子节点的最优值
//     * TODO 考虑使用非递归的后序遍历解决，自底向上，同样可以避免重复计算
//     * @param root
//     * @return
//     */
//    private int[] dpRob(TreeNode root){
//        // res[0]表示不选当前节点的最优解，res[1]表示选择当前节点的最优解
//        int[] res = new int[]{0,0};
//        if (root == null){
//            return res;
//        }
//        int[] leftRes = dpRob(root.left);
//        int[] rightRes = dpRob(root.right);
//        // 左右子树可以随便抢，不一定非要从左右子树节点选择，所以要计算一下最优值，然后取和
//        res[0] = Math.max(leftRes[0],leftRes[1]) + Math.max(rightRes[0],rightRes[1]);
//        res[1] = root.val + leftRes[0] + rightRes[0];
//        return res;
//    }
//
//    // 这种算法产生了大量的重复计算
//    private int robHelper(TreeNode root){
//        if (root == null){
//            return 0;
//        }
//        // 1.这里的 robHelper(root.left)和robHelper(root.right)已经计算过子节点的最优解的
//        int exclude = robHelper(root.left) + robHelper(root.right);// 不取当前根节点的最优解
//        int include = root.val;// 取当前根节点的最优解
//        if (root.left!= null){
//            // 2.这里又重新计算的左孩子节点的左右子树最优解，与1中的robHelper(root.left)计算重复
//            include  +=robHelper(root.left.left) + robHelper(root.left.right);
//        }
//        if (root.right != null){
//            // 3.同理，这里又重新计算的右孩子节点的左右子树最优解，与1中的robHelper(root.right)计算重复
//            include  += robHelper(root.right.left) + robHelper(root.right.right);
//        }
//        return Math.max(exclude,include);
//    }
}
