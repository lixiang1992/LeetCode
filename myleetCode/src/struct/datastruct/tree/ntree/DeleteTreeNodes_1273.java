package struct.datastruct.tree.ntree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1273.删除树节点
 * 给你一棵以节点 0 为根节点的树，定义如下：
 *
 * 节点的总数为 nodes 个；
 * 第 i 个节点的值为 value[i] ；
 * 第 i 个节点的父节点是 parent[i] 。
 * 请你删除节点值之和为 0 的每一棵子树。
 *
 * 在完成所有删除之后，返回树中剩余节点的数目。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/delete-tree-nodes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class DeleteTreeNodes_1273 {

    //---N叉树思路

    /**
     * N叉树节点
     */
    private static class NTreeNode {
        int index;// 节点下标
        int val;// 节点值
        int childValSum;// 孩子节点值的和，包括本身
        int childNumSum;// 孩子节点数量的和，包括本身
        List<NTreeNode> child;// 孩子节点

        NTreeNode(int index, int val) {
            this.index = index;
            this.val = val;
            this.childValSum = val;
            this.childNumSum = 1;
            this.child = new ArrayList<>();
        }

        void addChild(NTreeNode node) {
            this.child.add(node);
        }

        /**
         * 计算孩子节点值的sum和所有孩子节点数的sum
         */
        void computeSonTree() {
            for (NTreeNode node : this.child) {
                childValSum += node.childValSum;// 孩子节点值的和
                childNumSum += node.childNumSum;// 所有孩子节点数量的和
            }
        }
    }

    public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
        NTreeNode root = initNTree(nodes, parent, value);
        if (root == null) {
            return 0;
        }
        // 计算树的节点和
        computeTree(root);
        if (root.childNumSum == 0) {
            return 0;// 根节点都是0了，直接返回0了
        }
        return nodes - removeZeroTreeNode(root);
    }

    /**
     * 计算节点和
     *
     * @param node N叉树节点
     */
    private void computeTree(NTreeNode node) {
        for (NTreeNode child : node.child) {
            computeTree(child);
        }
        node.computeSonTree();
    }

    /**
     * 需要删除的节点数量
     *
     * @param node N叉树节点
     * @return 需要删除的节点数
     */
    private int removeZeroTreeNode(NTreeNode node) {
        if (node.childValSum == 0) {
            return node.childNumSum;
        }
        int sum = 0;
        for (NTreeNode child : node.child) {
            sum += removeZeroTreeNode(child);
        }
        return sum;
    }

    /**
     * 初始化N叉树
     *
     * @param nodes  节点数
     * @param parent 父节点数组
     * @param value  孩子节点数据
     * @return N叉树的root节点
     */
    private NTreeNode initNTree(int nodes, int[] parent, int[] value) {
        Map<Integer, NTreeNode> cache = new HashMap<>();
        NTreeNode root = null;
        // 全部节点的初始化
        for (int i = 0; i < nodes; i++) {
            NTreeNode node = new NTreeNode(i, value[i]);
            cache.put(i, node);
            if (parent[i] == -1) {
                root = node;
            }
        }
        // 节点构造N叉树
        for (int i = 0; i < nodes; i++) {
            if (parent[i] == -1) {
                continue;// 根节点跳过
            }
            // 孩子节点
            NTreeNode node = cache.get(i);
            // 父节点
            NTreeNode pNode = cache.get(parent[i]);
            pNode.addChild(node);
        }
        return root;
    }
    //---N叉树思路

    //---DP思路
    /*
    1.第一次从后往前遍历，把子节点的值累加到父节点。复杂度O(n)。(如果节点顺序是任意的，就需要二重循环，复杂度O(n^2))
    2.第二次从前往后，计算自身不为0且父节点不为0的节点的数量，遍历过程中要把父节点为0自身不为0的节点设置为0，以便能处理下一级节点。
    ps：我觉得题目缺少一个隐含条件，即子节点的索引值<父节点索引值。
    */
    public int deleteTreeNodes_DP(int nodes, int[] parent, int[] value) {
        for (int i = nodes - 1; i > 0; i--) {
            // 子节点的值累加到父节点
            value[parent[i]] += value[i];
        }
        // i=0,j=1
        int count = 0;
        if (value[0] == 0) {
            return count;
        } else {
            count++;// 根节点不为0，自加
        }
        for (int i = 1; i < nodes; i++) {
            if (value[i] != 0) {// 自身不为0才判断，为0了，节点数不会增加
                // 如果父节点为0，表示子树全部要清除了，所以子树也修改成0
                if (value[parent[i]] == 0) {
                    value[i] = 0;
                } else {
                    // 父节点不是0，子节点也不是0，表示不删除这个节点，节点数+1
                    count++;
                }
            }
        }
        return count;
    }
    //---DP思路

    //---拓扑排序思路
    //我们使用深度优先搜索的原因是：
    // 在计算某个节点 u 的一系列值之前，我们需要计算出其每个子节点 v 的一系列值。
    // 如果我们可以找出所有节点的一个排列，使得对于任意一个节点 u，它的子节点 v 在排列中都出现在 u 之前，
    // 那么我们就可以按照顺序依次遍历排列中的每个节点，计算出这一系列的值，而省去了深度优先搜索的步骤。

    //我们可以使用拓扑排序找出这样的一个排列。
    // 如果 u 是 v 的父节点，那么我们从 v 到 u 连接一条有向边。
    // 对这个图进行拓扑排序，我们就可以得到一个满足要求的排列。
    // 使用拓扑排序的另一个好处是省去了邻接表，我们可以直接通过数组 parent 构造拓扑排序对应的图，
    // 并且在得到排列后，我们计算出 v 的一系列值可以直接累加到 u 对应的值中，而不用遍历到 u 的时候再进行累加。

}
