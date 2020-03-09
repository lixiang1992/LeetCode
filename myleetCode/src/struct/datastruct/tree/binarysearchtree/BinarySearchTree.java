package struct.datastruct.tree.binarysearchtree;

import struct.pub.list.ListNode;
import struct.pub.tree.TreeNode;

import java.util.*;

/**
 * 力扣BST
 */
public class BinarySearchTree {

    /**
     * 95.不同的二叉搜索树2
     * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的二叉搜索树
     *
     * @param n n个节点
     * @return 不同的树的根节点
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }
        return constructTrees(1, n);
    }

    /**
     * 递归左右子树
     *
     * @param start 开始下标
     * @param end   结束下标
     * @return 结果集
     */
    private List<TreeNode> constructTrees(int start, int end) {
        List<TreeNode> result = new LinkedList<>();
        if (start > end) {
            result.add(null);
            return result;
        }
        for (int i = start; i <= end; i++) {
            // i为root的左右子树集合
            List<TreeNode> leftTrees = constructTrees(start, i - 1);
            List<TreeNode> rightTrees = constructTrees(i + 1, end);
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    // 根节点i
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
     * 96.不同的二叉搜索树
     * 思路
     * 标签：动态规划
     * 假设n个节点存在二叉排序树的个数是G(n)，令f(i)为以i为根的二叉搜索树的个数，则
     * G(n) = f(1) + f(2) + f(3) + f(4) + ... + f(n)
     * <p>
     * 当i为根节点时，其左子树节点个数为i-1个，右子树节点为n-i，则
     * f(i) = G(i-1)*G(n-i)
     * <p>
     * 综合两个公式可以得到 卡特兰数 公式
     * G(n) = G(0)*G(n-1)+G(1)*G(n-2)+...+G(n-1)*G(0)
     *
     * @param n n
     * @return 二叉搜索树树个数
     */
    public int numTrees(int n) {
        int[] dp = new int[n + 1];

        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] = dp[j] * dp[i - j - 1] + dp[i];
            }
        }
        return dp[n];
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
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        TreeNode preNode = null;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                if (preNode != null && preNode.val >= node.val) {
                    return false;
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
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        TreeNode preNode = null;// 当前节点的前驱节点
        TreeNode bigNode = null;
        TreeNode smallNode = null;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                if (preNode != null && preNode.val > node.val) {
                    if (bigNode == null) {// 只有第一次出现preNode>node，才是大节点
                        bigNode = preNode;
                    }
                    smallNode = node;// node较小，赋值给smallNode
                }
                preNode = node;// 记录前驱节点
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
     * 701.二叉搜索树插入
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
        int cmp;
        TreeNode preNode;// 上一个访问的节点
        do {
            preNode = node;
            cmp = val - node.val;
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return root;
            }
        } while (node != null);
        TreeNode newNode = new TreeNode(val);
        if (cmp < 0) {
            preNode.left = newNode;
        } else {
            preNode.right = newNode;
        }
        return root;
    }

    /**
     * 450.二叉搜索树节点删除
     *
     * @param root root
     * @param key  删除的key
     * @return 删除节点后的root节点
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode node = root;
        TreeNode preNode = null;
        while (node != null && node.val != key) {
            if (node.val > key) {
                preNode = node;
                node = node.left;
            } else {
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
                root.val = findDelNodeSuccessorVal(root);
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
            node.val = findDelNodeSuccessorVal(node);
        }
        return root;
    }

    /**
     * 找到node节点的后继节点的值
     * 走到这个方法里来的node节点，都是左右子树存在的节点
     *
     * @param node node
     * @return 后继节点val
     */
    private int findDelNodeSuccessorVal(TreeNode node) {
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
     * left<root<right
     *
     * @param root 根节点
     * @param val  值
     * @return val所在的节点
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        TreeNode node = root;
        while (node != null) {
            if (val < node.val) {
                // 目标val比当前node.val要小，则表示目标在node的左子树中
                node = node.left;
            } else if (val > node.val) {
                // 目标val比当前node.val要大，则表示目标在node的右子树中
                node = node.right;
            } else {
                // val和node.val想等了，就找到了节点
                return node;
            }
        }
        return null;
    }

    /**
     * 有序数组生成平衡二叉搜索树
     *
     * @param nums 数组
     * @return 平衡二叉树root
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = start + (end - start >> 1);
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, start, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, end);
        return root;
    }

    /**
     * 109.有序链表转平衡二叉搜索树
     *
     * @param head 链表的头节点
     * @return 二叉搜索树的根节点
     */
    public TreeNode sortedListToBST(ListNode head) {
        return sortedTreeNode(head, null);
    }

    /**
     * 链表转二叉树的核心算法
     * 快慢指针找中间节点，作为root，然后递归左右
     *
     * @param head 头结点
     * @param tail 尾节点
     * @return 根节点
     */
    private TreeNode sortedTreeNode(ListNode head, ListNode tail) {
        if (head == tail) {
            return null;
        }
        if (head.next == tail) {
            return new TreeNode(head.val);
        }
        // 快慢指针找中间节点
        ListNode midNode = head;
        ListNode rear = head;
        while (rear != tail && rear.next != tail) {
            midNode = midNode.next;
            rear = rear.next.next;
        }
        // 中间节点为root
        TreeNode node = new TreeNode(midNode.val);
        // 递归进入左右子树
        node.left = sortedTreeNode(head, midNode);
        node.right = sortedTreeNode(midNode.next, tail);
        // 返回root
        return node;
    }

    /**
     * 220.给定一个整数数组，判断数组中是否有两个不同的索引 i 和 j，
     * 使得 nums [i] 和 nums [j] 的差的绝对值最大为 t，并且 i 和 j 之间的差的绝对值最大为 ķ。
     *
     * @param nums nums
     * @param k    索引k
     * @param t    索引t
     * @return 是否有两个不同的索引
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
     * 230.二叉搜索树第K小节点
     * 中序遍历到第k个节点
     *
     * @param root 根节点
     * @param k    第K小节点
     * @return 第K小节点的值
     */
    public int kthSmallest(TreeNode root, int k) {
        int i = 0;
        TreeNode node = root;
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                i++;
                if (i == k) {
                    return node.val;
                }
                node = node.right;
            }
        }
        return 0;
    }

    /**
     * 二叉搜索树的第K大节点
     * 逆向中序遍历，right->root->left
     *
     * @param root 根节点
     * @param k    第K大节点
     * @return 第K大节点的值
     */
    public int kthLargest(TreeNode root, int k) {
        int i = 0;
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.right;
            } else {
                node = stack.pop();
                i++;
                if (i == k) {
                    return node.val;
                }
                node = node.left;
            }
        }
        return 0;
    }

    /**
     * 235.二叉搜索树最近公共祖先
     *
     * @param root root
     * @param p    p
     * @param q    q
     * @return 公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode node = root;
        // 当p<=node<=q或者p>=node>=q的时候，node就是公共祖先
        while (node != null) {
            if (node == p || node == q) {
                return node;
            }
            if (p.val < node.val && q.val < node.val) {
                node = node.left;
            } else if (p.val > node.val && q.val > node.val) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    /**
     * 255.验证前序遍历序列二叉搜索树
     * 单调栈->单调递减栈存储preorder的思路
     * 二叉搜索树是left < root < right的，先序遍历又是root->left->right的，基于这样的性质和遍历方式，我们知道越往左越小，这样，就可以构造一个单调递减的栈，来记录遍历的元素。
     * <p>
     * 为什么要用单调栈呢，因为往左子树遍历的过程，value是越来越小的，一旦出现了value大于栈顶元素value的时候，就表示要开始进入右子树了（如果不是，就应该继续进入左子树，不理解的请看下二叉搜索树的定义），但是这个右子树是从哪个节点开始的呢？
     * <p>
     * 单调栈帮我们记录了这些节点，只要栈顶元素还比当前节点小，就表示还是左子树，要移除，因为我们要找到这个右孩子节点直接连接的父节点，也就是找到这个子树的根，只要栈顶元素还小于当前节点，就要一直弹出，直到栈顶元素大于节点，或者栈为空。栈顶的上一个元素就是子树节点的根。
     * <p>
     * 接下来，数组继续往后遍历，之后的右子树的每个节点，都要比子树的根要大，才能满足，否则就不是二叉搜索树
     *
     * @param preorder 先序遍历数组
     * @return 是否是先序遍历二叉搜索树
     */
    public boolean verifyPreorder(int[] preorder) {
        // 单调栈使用，单调递减的单调栈
        Deque<Integer> stack = new LinkedList<>();
        int prevElement = Integer.MIN_VALUE;
        for (int i = 0; i < preorder.length; i++) {
            // 右子树元素必须要大于递减栈被peek访问的元素，否则就不是二叉搜索树
            if (preorder[i] < prevElement) {
                return false;
            }
            while (!stack.isEmpty() && preorder[i] > stack.peek()) {
                // 数组元素大于单调栈的元素了，表示往右子树走了，记录下上个根节点
                // 找到这个右子树对应的根节点，之前左子树全部弹出，不再记录，因为不可能在往根节点的左子树走了
                prevElement = stack.pop();
            }
            // 这个新元素入栈
            stack.push(preorder[i]);
        }
        return true;
    }

    /**
     * 二叉搜索树的后序遍历序列
     * 同255.验证前序遍历序列二叉搜索树，
     * 后序遍历由left->right->root转化为root->right->left的翻转先序遍历
     * 构造一个单调递增的单调栈，因为right>root>left
     *
     * @param postorder 后序序遍历数组
     * @return 是否后序遍历二叉搜索树
     */
    public boolean verifyPostorder(int[] postorder) {
        // 单调栈使用，单调递增的单调栈
        Deque<Integer> stack = new LinkedList<>();
        int pervElem = Integer.MAX_VALUE;
        // 逆向遍历，就是翻转的先序遍历
        for (int i = postorder.length - 1; i >= 0; i--) {
            // 左子树元素必须要小于递增栈被peek访问的元素，否则就不是二叉搜索树
            if (postorder[i] > pervElem) {
                return false;
            }
            while (!stack.isEmpty() && postorder[i] < stack.peek()) {
                // 数组元素小于单调栈的元素了，表示往左子树走了，记录下上个根节点
                // 找到这个左子树对应的根节点，之前右子树全部弹出，不再记录，因为不可能在往根节点的右子树走了
                pervElem = stack.pop();
            }
            // 这个新元素入栈
            stack.push(postorder[i]);
        }
        return true;
    }

    /**
     * 二叉搜索树后序遍历序列递归写法
     *
     * @param postorder 后序遍历数组
     * @return 是否二叉搜索树后序遍历
     */
    public boolean verifyPostorderRecursion(int[] postorder) {
        // 如果数组为空 说明满足后续遍历的题条件
        if (postorder.length == 0) return true;
        // 找到根节点值 val 也就是后续的最后一个点
        int mid = postorder[postorder.length - 1];
        int left;
        // 找到满足后序遍历的情况下左子树的划分点
        for (left = 0; left < postorder.length - 1; left++) {
            if (postorder[left] > mid) break;
        }
        // 看右子树是不是都满足大于root val 否则
        for (int i = left + 1; i < postorder.length - 1; i++) {
            if (postorder[i] < mid) return false;
        }
        // 递归 看左右的内部是否满足
        return verifyPostorderRecursion(Arrays.copyOfRange(postorder, 0, left)) &&
                verifyPostorderRecursion(Arrays.copyOfRange(postorder, left, postorder.length - 1));
    }

    /**
     * 270. 最接近的二叉搜索树值
     * 其实完全可以把target看成是在TreeNode中要查找的点
     * target如果大于root.val，那么最接近target的值，肯定在root.right的子树中，我们只要进入右子树去查找就行
     * 反之就去root.left的子树中查找
     * 每次都计算一下node.val和target的差的绝对值
     * 遍历的同时，使用找最小值的思想，log(N)的时间复杂度就能找到最接近的值，空间复杂度是O(1)，还避免的递归中栈的消耗。
     *
     * @param root   root节点
     * @param target target目标值
     * @return 最接近的值
     */
    public int closestValue(TreeNode root, double target) {
        double minDiff = Double.MAX_VALUE;
        TreeNode closestNode = root;// 最接近的节点
        TreeNode node = root;
        while (node != null) {
            double diff = Math.abs(node.val - target);// node.val和target差的绝对值
            if (diff < minDiff) {// 比之前的最小值还小，记录下最小值和最接近的节点
                minDiff = diff;
                closestNode = node;
            }
            if (node.val > target) {// target在node.left中
                node = node.left;
            } else if (node.val < target) {// target在node.right中
                node = node.right;
            } else {// 这时候差是0，肯定不可能还有比0大的绝对值了，直接返回
                return node.val;
            }
        }
        return closestNode.val;
    }

    /**
     * 272.给定一个不为空的二叉搜索树和一个目标值 target，请在该二叉搜索树中找到最接近目标值 target 的 k 个值。
     * <p>
     * 注意：
     * <p>
     * 给定的目标值 target 是一个浮点数
     * 你可以默认 k 值永远是有效的，即 k ≤ 总结点数
     * 题目保证该二叉搜索树中只会存在一种 k 个值集合最接近目标值
     * 先和270一样，找到最接近的节点，然后以最接近节点为中心，分别往前往后找K个节点
     *
     * @param root   root
     * @param target 目标截止
     * @param k      k个节点数
     * @return 最接近的k的节点
     */
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        double minDiff = Double.MAX_VALUE;
        TreeNode closestNode = root;// 最接近的节点
        TreeNode node = root;
        while (node != null) {
            double diff = Math.abs(node.val - target);// node.val和target差的绝对值
            if (diff < minDiff) {// 比之前的最小值还小，记录下最小值和最接近的节点
                minDiff = diff;
                closestNode = node;
            }
            if (target < node.val) {// target在node.left中
                node = node.left;
            } else if (target > node.val) {// target在node.right中
                node = node.right;
            } else {
                closestNode = node;
                break;
            }
        }
        // 此时closestNode是最接近target的节点
        List<Integer> result = new ArrayList<>(k);
        result.add(closestNode.val);// 最接近的节点加入

        TreeNode precursorNode = getPredecessor(root, closestNode.val);// 第一个前驱节点
        TreeNode successorNode = getSuccessor(root, closestNode.val);// 第一个后继节点
        while (result.size() < k) {// 结果集不满
            // 寻找前驱和后继
            if (precursorNode != null && successorNode != null) {
                if (Math.abs(precursorNode.val - target) < Math.abs(successorNode.val - target)) {
                    result.add(precursorNode.val);
                    precursorNode = getPredecessor(root, precursorNode.val);
                } else {
                    result.add(successorNode.val);
                    successorNode = getSuccessor(root, successorNode.val);
                }
            } else if (successorNode != null) {
                result.add(successorNode.val);
                // 寻找后继节点的后继
                successorNode = getSuccessor(root, successorNode.val);
            } else if (precursorNode != null) {
                result.add(precursorNode.val);
                // 寻找前驱节点的前驱
                precursorNode = getPredecessor(root, precursorNode.val);
            } else {
                // 已经找不到前驱和后继了，跳出
                break;
            }
        }
        return result;
    }

    /**
     * 寻找target节点的前驱
     *
     * @param root   root节点
     * @param target 目标值
     * @return 前驱节点
     */
    private TreeNode getPredecessor(TreeNode root, int target) {
        TreeNode curNode = root;
        TreeNode preParentNode = null;// 前驱父节点
        while (curNode.val != target) {// 默认curNode != null
            if (target < curNode.val) {
                curNode = curNode.left;
            } else {
                // 往右子树找target，这时候current比target小了，记录parent
                preParentNode = curNode;
                curNode = curNode.right;
            }
        }
        // curNode.left不空，那么curNode的前驱节点就是curNode.left的最右子树节点
        if (curNode.left != null) {
            curNode = curNode.left;
            while (curNode.right != null) {
                curNode = curNode.right;
            }
            return curNode;
        } else {
            // curNode.left为空，
            return preParentNode;
        }
    }

    /**
     * 寻找target节点的后继
     *
     * @param root   root
     * @param target 目标值
     * @return 后继节点
     */
    private TreeNode getSuccessor(TreeNode root, int target) {
        TreeNode curNode = root;
        TreeNode successParentNode = null;// 后继父节点
        while (curNode.val != target) {
            if (target < curNode.val) {
                successParentNode = curNode;
                curNode = curNode.left;
            } else {
                curNode = curNode.right;
            }
        }
        // curNode.right不空，那么curNode的后继节点就是curNode.right的最左子树节点
        if (curNode.right != null) {
            curNode = curNode.right;
            while (curNode.left != null) {
                curNode = curNode.left;
            }
            return curNode;
        } else {
            return successParentNode;
        }
    }


    /**
     * 285. 二叉搜索树中的顺序后继
     * 1.p存在右子树，那么p的后继就是p.right子树的最左节点
     * 2.p不存在右子树，那么p的后继就是p所在子树的第一个左孩子的父节点
     * 怎么找父节点，用栈来找，二叉搜索树找节点的方式
     * 也可以利用二叉搜索树的性质，来找比p大的第一个节点
     *
     * @param root root
     * @param p    需要寻找的节点
     * @return p的顺序后继
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        // p存在右子树，直接后继就是右子树的最左节点
        if (p.right != null) {
            p = p.right;
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }
        // p不存在右子树
        TreeNode node = root;
        TreeNode res = null;
        // 按理来说，p不会不在root的树中
        while (node != null) {
            if (p.val < node.val) {
                // p比node小，表示node在p的后继路径上
                res = node;// 左孩子的父节点
                node = node.left;
            } else if (p.val > node.val) {
                // p比node大，表示node在p的前驱路径上
                node = node.right;
            } else {
                // 相等退出
                break;
            }
        }
        return res;
    }

    /**
     * 333.最大的BST树
     * 如果左右子树都是BST，只要判断root比左子树最大节点大，root比右子树最小节点小就行了
     *
     * @param root root
     * @return 最大BST树的节点数
     */
    public int largestBSTSubtree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
            return nodeCount(root);
        }
        int left = largestBSTSubtree(root.left);
        int right = largestBSTSubtree(root.right);
        return Math.max(left, right);
    }

    /**
     * 判断root为根节点的树是否是二叉搜索树
     *
     * @param root root节点
     * @param min  最小值
     * @param max  最大值
     * @return 是否是二叉搜索树
     */
    private boolean isBST(TreeNode root, int min, int max) {
        if (root == null) {
            return true;
        }
        // 这里不建立备忘录是因为，一个节点第一次进来的时候，可能是root节点的子树，而不是以root为根节点的判断，判断BST是其root节点，不是节点本身，所以备忘录是没有用的
        return min < root.val && root.val < max && isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }

    /**
     * 节点数统计
     *
     * @param node 当前节点
     * @return 这个节点的子树有多少个节点数
     */
    private int nodeCount(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + nodeCount(node.left) + nodeCount(node.right);
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
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
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
     * 426,510的Node节点
     */
    private static class Node {
        int val;
        Node left;
        Node right;
        Node parent;

        Node() {
        }

        Node(int _val) {
            val = _val;
        }

        Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }


    /**
     * 426.将一个二叉搜索树就地转化为一个已排序的双向循环链表。可以将左右孩子指针作为双向循环链表的前驱和后继指针。
     *
     * @param root root
     * @return BST转DoubleLink的头结点
     */
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        Node head = new Node();// 哨兵
        Node curLinkNode = head;// 链表的当前节点
        // 中序遍历
        Node node = root;
        Deque<Node> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                // 中序遍历的节点出栈了
                node = stack.pop();
                // ---树节点接入链表中
                curLinkNode.right = node;// 先接右
                node.left = curLinkNode;// 再接左
                // ---树节点接入链表结束
                // 当前链表指针后移
                curLinkNode = node;
                // 进入右子树
                node = node.right;
            }
        }
        curLinkNode.right = head.right;// 最后一个节点right指向头部
        head.right.left = curLinkNode;// 这一步是把链表的真正第一个节点的left指针指向尾部
        return head.right;
    }

    /**
     * 501.二叉搜索树中的众数
     *
     * @param root root
     * @return 众数集合
     */
    public int[] findMode(TreeNode root) {
        TreeNode node = root;
        int maxCount = 0;
        List<Integer> count = new ArrayList<>();
        int lastValue = Integer.MIN_VALUE;
        int curCount = 0;// 当前相同数计数
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                if (node.val == lastValue) {
                    curCount++;
                } else {
                    curCount = 1;
                }
                if (maxCount == curCount) {
                    count.add(node.val);
                } else if (maxCount < curCount) {
                    // 众数变化了
                    maxCount = curCount;
                    count.clear();
                    count.add(node.val);
                }
                lastValue = node.val;// 记录上一个访问节点的值
                node = node.right;
            }
        }
        int[] res = new int[count.size()];
        for (int i = 0; i < count.size(); i++) {
            res[i] = count.get(i);
        }
        return res;
    }

    /**
     * 510. 二叉搜索树中的中序后继 II
     * 给定一棵二叉搜索树和其中的一个节点，找到该节点在树中的中序后继。
     * <p>
     * 一个结点 p 的中序后继是键值比 p.val大所有的结点中键值最小的那个。
     * <p>
     * 你可以直接访问结点，但无法直接访问树。每个节点都会有其父节点的引用。
     * 思路：红黑树（TreeMap）是怎么找后继的，这个就怎么找就行了，不需要中序遍历。
     * <p>
     * 1.x存在右子树，那么x的后继就是x.right子树的最左节点
     * 2.x不存在右子树，那么x的后继就是x所在子树的第一个左孩子的父节点
     *
     * @param x 当前节点
     * @return x的后继节点
     */
    public Node inorderSuccessor(Node x) {
        Node node = x.right;
        if (node != null) {
            // 存在右子树，那么x的后继就是node的最左节点
            while (node.left != null) {
                node = node.left;
            }
            return node;
        } else {
            // 不存在右子树，那么x的后继就是x所在子树的第一个左孩子的父节点
            node = x;
            Node parent = node.parent;
            while (parent != null && parent.right == node) {
                node = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    /**
     * 653. 两数之和 IV - 输入 BST
     * 给定一个二叉搜索树和一个目标结果，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。
     *
     * @param root root
     * @param k    目标k
     * @return findTarget
     */
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                int target = k - node.val;
                if (target != node.val) {
                    if (searchBST(root, target) != null) {
                        return true;
                    }
                }
                node = node.right;
            }
        }
        return false;
    }

    /**
     * 776.拆分二叉搜索树
     * 给你一棵二叉搜索树（BST）、它的根结点 root 以及目标值 V。
     * <p>
     * 请将该树按要求拆分为两个子树：其中一个子树结点的值都必须小于等于给定的目标值 V；另一个子树结点的值都必须大于目标值 V；树中并非一定要存在值为 V 的结点。
     * <p>
     * 除此之外，树中大部分结构都需要保留，也就是说原始树中父节点 P 的任意子节点 C，假如拆分后它们仍在同一个子树中，那么结点 P 应仍为 C 的子结点
     * <p>
     * 思路：利用二叉搜索树的查找元素的思想，来遍历root，小于等于V的node加入小树中，大于V的node加入到大树中，出现拐点的地方，往之前前进的反方向继续遍历，拐点和正方向的子树断开
     *
     * @param root root
     * @param V    目标值
     * @return 拆分的两棵树
     */
    public TreeNode[] splitBST(TreeNode root, int V) {
        // 存储>V值的节点，本身是一个哨兵，不参与真正运算，简化实现
        TreeNode bigTreeNode = new TreeNode(Integer.MAX_VALUE);
        // 存储<=V值的节点，本身是一个哨兵，不参与真正运算，简化实现
        TreeNode smallTreeNode = new TreeNode(Integer.MAX_VALUE);
        // 当前bigTree的节点
        TreeNode curBigTreeNode = bigTreeNode;
        // 当前smallTree的节点
        TreeNode curSmallTreeNode = smallTreeNode;
        // node节点
        TreeNode node = root;
        while (node != null) {
            if (node.val > V) {
                // node.val比V大，加入到bigTree中
                // bigTree中操作
                if (node.val <= curBigTreeNode.val) {
                    curBigTreeNode.left = node;
                } else {
                    curBigTreeNode.right = node;
                }
                // 当前大树指针移动
                curBigTreeNode = node;
                // 目标V在node的左子树中，node.right.val肯定都比V要大，全部都进入bigTree中
                node = node.left;
                // 之前node比V大，下一个节点的值比V小了，拐点出现，原来的树要断开了,bigTree中存的是比V大的值
                // 现在curBigTreeNode指向的node.parent节点，node.parent.left不能再加入到bigTree中，要断开
                // 但是node.parent.right中还是有可能要加入到bigTree中，这个在接下来的遍历中在bigTree操作中会处理到
                if (node != null && node.val <= V) {
                    curBigTreeNode.left = null;
                }
            } else {
                // node.val比V小，加入到smallTree中
                // smallTree中操作
                if (node.val <= curSmallTreeNode.val) {
                    curSmallTreeNode.left = node;
                } else {
                    curSmallTreeNode.right = node;
                }
                // 当前小树指针移动
                curSmallTreeNode = node;
                // 目标V在node的右子树中，node.left.val肯定都比V要小，全部都进入smallTree中
                node = node.right;
                // 之前node比V小，下一个节点的值比V大了，拐点出现，原来的树要断开了，smallTree中存的是比V小的值
                // 现在curSmallTreeNode指向的node.parent节点，node.parent.right不能再加入到smallTree中，要断开
                // 但是node.parent.left中还是有可能要加入到smallTree中，这个在接下来的遍历中在smallTree操作中会处理到
                if (node != null && node.val > V) {
                    curSmallTreeNode.right = null;
                }
            }
        }
        return new TreeNode[]{smallTreeNode.left, bigTreeNode.left};
    }

    /**
     * 846.一手顺子
     * 爱丽丝有一手（hand）由整数数组给定的牌。 
     * <p>
     * 现在她想把牌重新排列成组，使得每个组的大小都是 W，且由 W 张连续的牌组成。
     * <p>
     * 如果她可以完成分组就返回 true，否则返回 false。
     *
     * @param hand 整数数组给定的牌
     * @param W    每个组的大小都是 W
     * @return 是否能完成分组
     */
    public boolean isNStraightHand(int[] hand, int W) {
        if (hand.length % W != 0) {
            return false;// 不能整除就不能重新排列为顺子
        }
        TreeMap<Integer, Integer> orderMap = new TreeMap<>();
        for (int i = 0; i < hand.length; i++) {
            int count = orderMap.getOrDefault(hand[i], 0);
            orderMap.put(hand[i], count + 1);// 对原来的hand数组排序并且记录个数
        }
        // map不为空，继续遍历
        while (!orderMap.isEmpty()) {
            Integer currentKey = orderMap.firstKey();
            // 遍历
            for (int i = 0; i < W; i++) {
                Integer currentValue = orderMap.get(currentKey);
                if (currentValue == null) {
                    // value为空，表示currentKey不存在
                    // 则说明在一个W的范围内，无法连续的生产W个元素，返回false
                    return false;
                }
                currentValue--;
                // 如果数量等于0了，则表示map中没有这个数了，移除掉
                if (currentValue == 0) {
                    orderMap.remove(currentKey);
                } else {
                    orderMap.put(currentKey, currentValue);// 修改count值
                }
                // key后移，因为顺子是连续的，只会差一个数
                currentKey++;
            }
        }
        return true;
    }

    /**
     * 938. 二叉搜索树的范围和
     *
     * @param root root
     * @param L    范围的开始
     * @param R    范围的结束
     * @return sum
     */
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        int sum = 0;
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                if (node.val >= L) {
                    // 剪枝操作，当前根节点比区间最小值大，才继续左孩子遍历，找到最小值
                    stack.push(node);
                    node = node.left;
                } else {
                    // 当前根节点比区间最小值还要小，则左孩子没有再遍历的必要，直接进入右子树
                    node = node.right;
                }
            } else {
                node = stack.pop();
                if (node.val >= L && node.val <= R) {
                    sum += node.val;
                } else if (node.val > R) {
                    return sum;
                }
                node = node.right;
            }
        }
        return sum;
    }

    /**
     * 975.奇偶跳
     * <p>
     * 奇数次跳跃时，在右边找比它大的数中最小的数
     * 偶数次跳跃时，在右边找比它小的树中最大的数
     * TreeMap  刚好有满足上面数据结构的方法
     *
     * @param A 数组A
     * @return 几次跳跃
     */
    public int oddEvenJumps(int[] A) {
        int size = A.length;
        boolean[] odd = new boolean[size];// 第i个节点奇数次跳跃能否到达最后节点
        boolean[] even = new boolean[size];// 第i个节点偶数次跳跃能否到达最后节点

        // key：数组的值，value：数组下标
        TreeMap<Integer, Integer> tm = new TreeMap<>();

        odd[size - 1] = even[size - 1] = true;// 最后一个节点跳到本身是肯定可以的
        tm.put(A[size - 1], size - 1);
        int ret = 1;
        for (int i = size - 2; i >= 0; i--) {
            Integer ceil = tm.ceilingKey(A[i]);// 不小于当前A[i]的key 数组的值
            Integer floor = tm.floorKey(A[i]); // 不大于当前A[i]的key 数组的值

            // 找不到，表示没有这样的元素了，肯定就没法到达最后节点
            if (ceil != null) {
                // 奇数次跳跃是找i之后(i<j)，数值A[i] <= A[j] 的最小A[j]->key所对应的index。
                // 奇数跳跃的下一次肯定是偶数跳跃，所以从偶数跳跃的数组中获取index得到能否到达末尾节点。
                odd[i] = even[tm.get(ceil)];
            }
            if (floor != null) {
                // 偶数次跳跃是找到i之后(i>j)，数值A[i] >= A[j] 的最大A[j]->key所对应的index。
                // 偶数次跳跃的下一次肯定是奇数跳跃，所以从奇数跳跃的数组中获取index得到是否能到达末尾节点
                even[i] = odd[tm.get(floor)];
            }
            if (odd[i]) {
                ret++;// 第一次跳跃就能到达末尾的，永远都是奇数跳跃，所以查看奇数的数组
            }
            // 节点映射加入treeMap
            tm.put(A[i], i); // as a result, it will always keep a biggest pos of A[i].
        }
        return ret;
    }

    /**
     * 1038.从二叉搜索树到更大和树
     * <p>
     * 给出二叉搜索树的根节点，该二叉树的节点值各不相同，修改二叉树，使每个节点 node 的新值等于原树中大于或等于 node.val 的值之和。
     * 反向中序遍历，前一个节点的值加在后一个节点上
     *
     * @param root 根节点
     * @return 根节点
     */
    public TreeNode bstToGst(TreeNode root) {
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        int sumValue = 0;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.right;
            } else {
                node = stack.pop();
                node.val += sumValue;// 节点加上之前node.val的和
                sumValue = node.val;// 之前node.val值的和更新
                node = node.left;// 左子树遍历
            }
        }
        return root;
    }

    /**
     * 1214.查找两颗二叉搜索树之和
     * 给出两棵二叉搜索树，请你从两棵树中各找出一个节点，使得这两个节点的值之和等于目标值 Target。
     * <p>
     * 如果可以找到返回 True，否则返回 False。
     * <p>
     * 参考两数之和的做法来做这个，利用二叉搜索树的性质（left<root<right）来找另一个节点的值
     *
     * @param root1  第一颗二叉搜索树root
     * @param root2  第二颗二叉搜索树root
     * @param target 目标值
     * @return 是否存在这个target
     */
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        if (root1 == null || root2 == null) {
            return false;
        }
        TreeNode node = root1;
        Deque<TreeNode> stack = new LinkedList<>();// 双端队列模拟栈
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                int value = target - node.val;// 目标value值
                // 从另一个二叉搜索树中寻找value所在的节点
                if (searchBST(root2, value) != null) {
                    // 节点不空则满足题意，否则继续遍历
                    return true;
                }
                node = node.right;
            }
        }
        return false;
    }

    /**
     * 1373.二叉搜索子树的最大键值和
     *  给你一棵以 root 为根的 二叉树 ，请你返回 任意 二叉搜索子树的最大键值和。
     *
     * 二叉搜索树的定义如下：
     *
     * 任意节点的左子树中的键值都 小于 此节点的键值。
     * 任意节点的右子树中的键值都 大于 此节点的键值。
     * 任意节点的左子树和右子树都是二叉搜索树。
     *
     * @param root root
     * @return 二叉搜索子树的最大键值和
     */
    public int maxSumBST(TreeNode root) {
        int[] res = {0};
        // 求最大键值和
        maxSumBST(res, root);
        return res[0];
    }

    /**
     * 1373.求节点和的最大值
     *
     * @param res res结果集
     * @param node node节点
     * @return 这个返回没啥意义
     */
    private int maxSumBST(int[] res, TreeNode node) {
        // 这个节点是BST，直接求和了
        if (isBST(node, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
            // 是二叉搜索树，求节点和，节点和的最优解，肯定在子节点求和的过程中
            return sumNodeValue(res, node);
        }
        int left = maxSumBST(res, node.left);
        int right = maxSumBST(res, node.right);
        return Math.max(left, right);
    }

    /**
     * 1373.以node为root的节点和
     *
     * @param res  结果集
     * @param node node
     * @return 节点之和
     */
    private int sumNodeValue(int[] res, TreeNode node) {
        if (node == null) {
            return 0;
        }
        // 二叉搜索树节点和的最大值，一定在子节点中会出现
        int sum = node.val + sumNodeValue(res, node.left) + sumNodeValue(res, node.right);
        // 这里记录结果的最大值
        res[0] = Math.max(res[0], sum);
        return sum;
    }

    /**
     * 面试17.12 BiNode BST转链表
     *
     * @param root root节点
     * @return 链表头指针
     */
    public TreeNode convertBiNode(TreeNode root) {
        TreeNode head = new TreeNode(0);// 单链表的头指针哨兵
        TreeNode prev = head;// 移动的链表前置指针
        // 开始中序遍历
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                // ---链表处理
                node.left = null;// 左指针置空
                prev.right = node;// 右指针作为链表的next指针
                prev = node;// 指针后移
                // ---链表处理
                // 中序遍历进入右子树
                node = node.right;
            }
        }
        return head.right;
    }

    /**
     * 面试17.12 BiNode BST转链表 递归写法
     *
     * @param root root节点
     * @return 链表头指针
     */
    public TreeNode convertRecursionBiNode(TreeNode root) {
        TreeNode head = new TreeNode(0);// 单链表的头指针哨兵
        // 开始中序遍历
        inorder(root, head);
        return head.right;
    }

    private TreeNode inorder(TreeNode root, TreeNode prev) {
        if (root != null) {
            prev = inorder(root.left, prev);
            root.left = null;
            prev.right = root;
            prev = root;
            prev = inorder(root.right, prev);
        }
        return prev;
    }

//    public List<List<Integer>> BSTSequences(TreeNode root) {
//        List<List<Integer>> res = new ArrayList<>();
//
//
//        return BSTSequencesProcess(root);
//    }
//
//    public List<List<Integer>> BSTSequencesProcess(TreeNode node){
//        if (node == null){
//            return new ArrayList<>();
//        }
//        // 分别获取左右结果集
//        List<List<Integer>> leftRes = BSTSequencesProcess(node.left);
//        List<List<Integer>> rightRes = BSTSequencesProcess(node.right);
//        // 只要保证根节点在最前面，左子树往右子树插空即可
//        List<List<Integer>> res = new ArrayList<>();
//        if (leftRes.size() == 0 && rightRes.size() == 0){
//            List<Integer> oneRes = new LinkedList<>();
//            oneRes.add(node.val);
//            res.add(oneRes);
//        }else {
//            for (List<Integer> left : leftRes){
//                for (List<Integer> right : rightRes){
//                    List<Integer> oneRes = new LinkedList<>();
//                    oneRes.add(node.val);
//                    int i = 0;
//                    int length = right.size();
//                    while (i < length){
//                        right.addAll(i,left);
//                        i++;
//                    }
//                    right.addAll(left);
//                    oneRes.addAll(right);
//                    res.add(oneRes);
//                }
//            }
//        }
//        return res;
//    }
}
