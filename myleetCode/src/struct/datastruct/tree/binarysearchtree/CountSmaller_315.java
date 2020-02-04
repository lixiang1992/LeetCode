package struct.datastruct.tree.binarysearchtree;

import java.util.LinkedList;
import java.util.List;

/**
 * 315.计算右侧小于当前元素的个数
 * 给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
 *
 * 思路，这个其实就是线段树的思想
 */
public class CountSmaller_315 {

    /**
     * 解决第315题特殊构造的一个TreeNode结构
     * 这是一个二叉搜索树节点
     */
    private class TreeNode_315 {
        int val;// 节点值
        TreeNode_315 left;// 左孩子指针
        TreeNode_315 right;// 右孩子指针
        int leftCount = 0;// 当前节点在当前时间的左子树节点个数
        int sameCount = 0;// 当前节点在当前时间val相同节点个数

        TreeNode_315(int val) {
            this.val = val;
        }

    }

    /**
     * 315.计算右侧小于当前元素的个数
     * 思路，构造一颗二叉搜索树
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        LinkedList<Integer> result = new LinkedList<>();
        if (nums == null || nums.length < 1) {
            return result;
        }
        TreeNode_315 root = new TreeNode_315(nums[nums.length - 1]);
        TreeNode_315 preNode = root;
        result.addFirst(root.leftCount);
        for (int i = nums.length - 2; i >= 0; i--) {
            int val = nums[i];
            TreeNode_315 node = root;
            boolean needAddNode = true;// true表示需要增加新节点，false表示不用
            int count = 0;
            while (node != null) {
                preNode = node;
                if (val < node.val) {
                    // 往左子树移动则表示新节点比当前节点小
                    node.leftCount++;//当前节点的左子树节点+1
                    node = node.left;
                } else if (val > node.val) {
                    // 往右子树移动则代表新节点比当前节点更大
                    // 比新节点小的数目累计,之前的+当前节点的左子树节点树+和当前节点值相同的数，这些都比新节点小
                    count = count + node.leftCount + node.sameCount + 1;
                    node = node.right;
                } else {
                    needAddNode = false;
                    node.sameCount++;
                    count = count + node.leftCount;
                    break;
                }
            }
            if (needAddNode) {
                TreeNode_315 newNode = new TreeNode_315(val);
                if (preNode.val > val) {
                    preNode.left = newNode;
                } else {
                    preNode.right = newNode;
                }
            }
            result.addFirst(count);
        }
        return result;
    }
}
