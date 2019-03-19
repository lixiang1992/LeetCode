package tree.binarysearchtree;

import struct.pub.list.ListNode;
import struct.pub.tree.TreeNode;

import java.util.*;

/**
 * leetCode二叉搜索树
 */
public class BinarySearchTree {

    /**
     * 95.不同的二叉搜索树
     * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的二叉搜索树
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0){
            return new ArrayList<>() ;
        }
        return constructTrees(1,n);
    }

    /**
     * 递归左右子树
     * @param start
     * @param end
     * @return
     */
    private List<TreeNode> constructTrees(int start,int end){
        List<TreeNode> result = new LinkedList<>();
        if (start > end){
            result.add(null);
            return result;
        }
        for (int i = start;i <= end;i++){
            List<TreeNode> leftTrees = constructTrees(start,i-1);
            List<TreeNode> rightTrees = constructTrees(i+1,end);
            for (TreeNode left:leftTrees){
                for (TreeNode right:rightTrees){
                    TreeNode node = new TreeNode(i);
                    node.right = right;
                    node.left = left;
                    result.add(node);
                }
            }
        }
        return result;
    }

    /**
     * 98.验证是否是二叉搜索树
     *
     * @param root 根节点
     * @return 是否为二叉搜索树
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        TreeNode preNode = null;
        while (node != null || !stack.isEmpty()) {
            stack.push(node);
            node = node.left;
            while (node == null && !stack.isEmpty()) {
                node = stack.pop();
                if (preNode != null) {
                    if (preNode.val >= node.val) return false;
                }
                preNode = node;
                node = node.right;
            }
        }
        return true;
    }

    /**
     * 99.恢复搜索二叉树
     * 思路：中序遍历二叉树，找到的第一个不符合要求的节点，这个是bigNode,
     * 然后继续往后面遍历，找到值最小的那个节点记为smallNode，交换bigNode和smallNode即可
     *
     * @param root 根节点
     */
    public void recoverTree(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        TreeNode preNode = null;
        TreeNode bigNode = null;
        TreeNode smallNode = null;
        while (node != null || !stack.isEmpty()) {
            stack.push(node);
            node = node.left;
            while (node == null && !stack.isEmpty()) {
                node = stack.pop();
                if (preNode != null) {
                    if (preNode.val >= node.val && bigNode == null) {
                        bigNode = preNode;
                        smallNode = node;
                    } else if (smallNode != null && node.val < smallNode.val) {
                        smallNode = node;
                    }
                }
                preNode = node;
                node = node.right;
            }
        }
        if (bigNode != null) {
            int tempVal = bigNode.val;
            bigNode.val = smallNode.val;
            smallNode.val = tempVal;
        }
    }

    /**
     * 二叉搜索树插入
     *
     * @param root 根节点
     * @param val  需要查找的节点值
     * @return 根节点
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        TreeNode node = root;
        TreeNode preNode = root;
        boolean flag = true;// true表示左孩子，false表示右孩子
        while (node != null) {
            preNode = node;
            if (val < node.val) {
                flag = true;
                node = node.left;
            } else if (val > node.val) {
                flag = false;
                node = node.right;
            } else {
                return root;
            }
        }
        TreeNode newNode = new TreeNode(val);
        if (flag) {
            preNode.left = newNode;
        } else {
            preNode.right = newNode;
        }
        return root;
    }

    /**
     * 二叉搜索树节点删除
     *
     * @param root
     * @param key
     * @return
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode node = root;
        TreeNode preNode = null;
        while (node != null && node.val != key) {
            if (node.val > key) {
                preNode = node;
                node = node.left;
            } else if (node.val < key) {
                preNode = node;
                node = node.right;
            }
        }
        if (node == null) {
            return root;
        }
        if (preNode == null) {
            // 移除的是根节点
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.left == null) {
                root = root.right;
            } else if (root.right == null) {
                root = root.left;
            } else {
                root.val = findSuccessorVal(root);
            }
            return root;
        }
        if (node.left == null && node.right == null) {
            if (node == preNode.left) {
                preNode.left = null;
            } else if (node == preNode.right) {
                preNode.right = null;
            }
        } else if (node.left == null) {
            if (node == preNode.left) {
                preNode.left = node.right;
            } else if (node == preNode.right) {
                preNode.right = node.right;
            }
            node.right = null;
        } else if (node.right == null) {
            if (node == preNode.left) {
                preNode.left = node.left;
            } else if (node == preNode.right) {
                preNode.right = node.left;
            }
            node.left = null;
        } else {
            node.val = findSuccessorVal(node);
        }
        return root;
    }

    /**
     * 找到node节点的后继节点的值
     *
     * @param node
     * @return
     */
    private int findSuccessorVal(TreeNode node) {
        TreeNode preNode = node;
        node = node.right;
        if (node.left == null) {
            preNode.right = node.right;
            node.right = null;
        } else {
            while (node.left != null) {
                preNode = node;
                node = node.left;
            }
            if (node.right == null) {
                preNode.left = null;
            } else {
                preNode.left = node.right;
                node.right = null;
            }
        }
        return node.val;
    }

    /**
     * 查询二叉搜索树的节点
     *
     * @param root 根节点
     * @param val 值
     * @return val所在的节点
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        TreeNode node = root;
        while (node != null) {
            if (val < node.val) {
                node = node.left;
            } else if (val > node.val) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    /**
     * 有序链表转二叉搜索树
     *
     * @param head 链表的头节点
     * @return 二叉搜索树的根节点
     */
    public TreeNode sortedListToBST(ListNode head) {
        return sortedTreeNode(head, null);
    }

    /**
     * 链表转二叉树的核心算法
     * @param head
     * @param tail
     * @return
     */
    private TreeNode sortedTreeNode(ListNode head, ListNode tail) {
        if (head == tail) {
            return null;
        }
        if (head.next == tail) {
            return new TreeNode(head.val);
        }
        ListNode midNode = head;
        ListNode rear = head;
        while (rear != tail && rear.next != tail) {
            midNode = midNode.next;
            rear = rear.next.next;
        }
        TreeNode node = new TreeNode(midNode.val);
        node.left = sortedTreeNode(head, midNode);
        node.right = sortedTreeNode(midNode.next, tail);
        return node;
    }

    /**
     * 220.给定一个整数数组，判断数组中是否有两个不同的索引 i 和 j，
     * 使得 nums [i] 和 nums [j] 的差的绝对值最大为 t，并且 i 和 j 之间的差的绝对值最大为 ķ。
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (k <= 0) {
            return false;
        }
        int len = nums.length;
        int end = 1;
        int start = 0;
        while (start < len - 1) {
            if (start != end && Math.abs((long) nums[start] - nums[end]) <= t) {
                return true;
            }
            if (end - start == k || len - 1 == end) {
                start++;
                if (t != 0) {
                    end = start + 1;
                }
            } else {
                end++;
            }
        }
        return false;
    }

    /**
     * 二叉搜索树第K小节点
     * 中序遍历到第k个节点
     *
     * @param root 根节点
     * @param k 第K个节点
     * @return 第K个节点的值
     */
    public int kthSmallest(TreeNode root, int k) {
        int i = 0;
        TreeNode node = root;
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            stack.push(node);
            node = node.left;
            while (node == null && !stack.isEmpty()) {
                i++;
                node = stack.pop();
                if (i == k) {
                    return node.val;
                }
                node = node.right;
            }
        }
        return 0;
    }

    /**
     * 235.二叉搜索树最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode node = root;
        while (node != null) {
            if (node == p || node == q) {
                return node;
            }
            if (node.val > p.val && node.val > q.val) {
                node = node.left;
            } else if (node.val < p.val && node.val < q.val) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    /**
     * 530. 二叉搜索树的最小绝对差
     *
     * @param root 根节点
     * @return 最小绝对差
     */
    public int getMinimumDifference(TreeNode root) {
        TreeNode node = root;
        TreeNode prevNode = null;
        Stack<TreeNode> stack = new Stack<>();
        int min = Integer.MAX_VALUE;
        while (node != null || !stack.isEmpty()) {
            stack.push(node);
            node = node.left;
            while (node == null && !stack.isEmpty()) {
                node = stack.pop();
                if (prevNode != null) {
                    min = Math.min(min, Math.abs(prevNode.val - node.val));
                }
                prevNode = node;
                node = node.right;
            }
        }
        return min;
    }

    /**
     * 解决第315题特殊构造的一个TreeNode结构
     * 这是一个二叉搜索树节点
     */
    private class My_315_TreeNode {
        int val;// 节点值
        My_315_TreeNode left;// 左孩子指针
        My_315_TreeNode right;// 右孩子指针
        int leftCount = 0;// 当前节点在当前时间的左子树节点个数
        int sameCount = 0;// 当前节点在当前时间val相同节点个数

        My_315_TreeNode(int val) {
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
        My_315_TreeNode root = new My_315_TreeNode(nums[nums.length - 1]);
        My_315_TreeNode preNode = root;
        result.addFirst(root.leftCount);
        for (int i = nums.length - 2; i >= 0; i--) {
            int val = nums[i];
            My_315_TreeNode node = root;
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
                My_315_TreeNode newNode = new My_315_TreeNode(val);
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

    /**
     * 938. 二叉搜索树的范围和
     * @param root
     * @param L
     * @param R
     * @return
     */
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null){
            return 0;
        }
        int sum = 0;
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()){
            if(node.val >= L){
                // 剪枝操作，当前根节点比区间最小值大，才继续左孩子遍历，找到最小值
                stack.push(node);
                node = node.left;
            } else {
                // 当前根节点比区间最小值还要小，则左孩子没有再遍历的必要，直接进入右子树
                node = node.right;
            }
            while (node == null && !stack.isEmpty()){
                node = stack.pop();
                if (node.val >= L && node.val <= R){
                    sum+= node.val;
                }else if (node.val > R){
                    return sum;
                }
                node = node.right;
            }
        }
        return sum;
    }

    private class My_493_TreeNode{
        int val;// 节点值
        My_493_TreeNode left;// 左孩子
        My_493_TreeNode right;// 右孩子
        int leftNodeCount = 0;// 当前时间该节点左节点个数
        int leftNodeCond = 0;
        int sameValueNode = 0;// 当前时间相同数目的节点数

        public My_493_TreeNode(int val){
            this.val = val;
        }
    }

    /**
     * 493.存在问题
     * @param nums
     * @return
     */
    public int reversePairs(int[] nums) {
        if (nums.length <= 1){
            return 0;
        }
        int count = 0;
        My_493_TreeNode root = new My_493_TreeNode(nums[nums.length - 1]);
        My_493_TreeNode preNode = root;
        for (int i = nums.length - 2;i >= 0; i--){
            int val = nums[i];
            // 一定满足i<j的情况，因为是倒序的
            My_493_TreeNode node = root;
            boolean needAddNode = true;
            while (node != null){
                preNode = node;
                long nodeVal = node.val;
                if (val < nodeVal){
                    if (val < (nodeVal>>1)){
                        node.leftNodeCond++;// 左节点满足条件的+1
                    }
                    node.leftNodeCount++;// 左子树+1
                    node = node.left;
                }else if(val > nodeVal){
                    if (val > (nodeVal<<1)){
                        count = count+node.sameValueNode+node.leftNodeCount +1;
                    }else {
                        My_493_TreeNode tempNode = node;
                        long tempVal = tempNode.val;
                        while (tempNode != null && val < (tempVal<< 1)){
                            tempNode = tempNode.left;
                            if (tempNode != null){
                                tempVal = tempNode.val;
                            }
                        }
                        if (tempNode != null){
                            if (tempVal == val){
                                count = count + tempNode.leftNodeCount;
                            }else {
                                count = count+tempNode.sameValueNode+tempNode.leftNodeCount +1;
                            }
                        }
                    }
                    node = node.right;
                }else {
                    needAddNode = false;
                    node.sameValueNode++;
                    count = count + node.leftNodeCond;
                    if (node.val<0){
                        count = count + node.sameValueNode;
                    }
                    break;
                }
            }
            if (needAddNode){
                add493Node(preNode,nums[i]);
            }
        }
        return count;
    }

    private void add493Node(My_493_TreeNode prevNode,int val){
        My_493_TreeNode newNode = new My_493_TreeNode(val);
        if (val < prevNode.val){
            prevNode.left = newNode;
        }else {
            prevNode.right = newNode;
        }
    }
}
