package struct.datastruct.tree.binarysearchtree;

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
     * @param node
     * @return
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

//    /**
//     * 699.方块类
//     */
//    private class Block implements Comparable<Block>{
//
//        int left;
//        int right;
//
//        Block(int left, int right) {
//            this.left = left;
//            this.right = right;
//        }
//
//        @Override
//        public int compareTo(Block o) {
//            if (this.left == o.left){
//                return this.right - o.right;
//            }
//            return this.left - o.left;
//        }
//    }
//
//    /**
//     * 699.掉落的方块
//     * @param positions
//     * @return
//     */
//    public List<Integer> fallingSquares(int[][] positions) {
//        TreeMap<Block,Integer> segmentMap = new TreeMap<>();
//        List<Integer> result = new ArrayList<>();
//        // 加入第一个元素
//        Block first = new Block(positions[0][0],positions[0][0] + positions[0][1]);
//        segmentMap.put(first,positions[0][1]);
//        int maxHigh = positions[0][1];
//        result.add(maxHigh);
//        for (int i = 1; i < positions.length;i++){
//            int left = positions[i][0];
//            int right = positions[i][0] + positions[i][1];
//            int high = positions[i][1];
//            Block temp = new Block(right,Integer.MAX_VALUE);
//            Block floor = segmentMap.floorKey(temp);
//            if (floor == null){// 没有比temp小的，说明新进来的位置是横坐标最小的
//                // 记录block和其对应的高度
//                segmentMap.put(new Block(left,right),high);
//                // 求最大高度
//                maxHigh = Math.max(maxHigh,high);
//                result.add(maxHigh);
//            } else {
//              // 存在比temp小的block
//                if (left < floor.right){
//                    // 新的方块左侧和旧方块的右侧有交叉
//                    int blockHigh = segmentMap.get(floor);
//                    blockHigh += high;
//                    segmentMap.remove(floor);
//                    segmentMap.put(new Block(floor.left,positions[i][0]+positions[i][1]),blockHigh);
//                }
//            }
//        }
//        return null;
//    }

    /**
     * 846.一手顺子
     * 爱丽丝有一手（hand）由整数数组给定的牌。 
     *
     * 现在她想把牌重新排列成组，使得每个组的大小都是 W，且由 W 张连续的牌组成。
     *
     * 如果她可以完成分组就返回 true，否则返回 false。
     *
     * @param hand
     * @param W
     * @return
     */
    public boolean isNStraightHand(int[] hand, int W){
        if (hand.length % W != 0){
            return false;// 不能整除就不能重新排列为顺子
        }
        TreeMap<Integer,Integer> orderMap = new TreeMap<>();
        for (int i = 0;i<hand.length;i++){
            int count = orderMap.getOrDefault(hand[i],0);
            orderMap.put(hand[i],count+1);// 对原来的hand数组排序并且记录个数
        }
        // map不为空，继续遍历
        while (!orderMap.isEmpty()){
            Integer currentKey = orderMap.firstKey();
            // 遍历
            for(int i = 0;i < W;i++){
                Integer currentValue = orderMap.get(currentKey);
                if (currentValue == null){
                    // value为空，表示currentKey不存在
                    // 则说明在一个W的范围内，无法连续的生产W个元素，返回false
                    return false;
                }
                currentValue--;
                // 如果数量等于0了，则表示map中没有这个数了，移除掉
                if (currentValue == 0){
                    orderMap.remove(currentKey);
                }else{
                    orderMap.put(currentKey,currentValue);// 修改count值
                }
                // key后移，因为顺子是连续的，只会差一个数
                currentKey++;
            }
        }
        return true;
    }

    /**
     * 975.奇偶跳
     * @param A
     * @return
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
            if (ceil != null){
                // 奇数次跳跃是找i之后(i<j)，数值A[i] <= A[j] 的最小A[j]->key所对应的index。
                // 奇数跳跃的下一次肯定是偶数跳跃，所以从偶数跳跃的数组中获取index得到能否到达末尾节点。
                odd[i] = even[tm.get(ceil)];
            }
            if (floor != null){
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
     *
     * 给出二叉搜索树的根节点，该二叉树的节点值各不相同，修改二叉树，使每个节点 node 的新值等于原树中大于或等于 node.val 的值之和。
     * 反向中序遍历，前一个节点的值加在后一个节点上
     * @param root 根节点
     * @return 根节点
     */
    public TreeNode bstToGst(TreeNode root) {
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        int sumValue = 0;
        while (node != null || !stack.isEmpty()){
            while (node != null){
                stack.push(node);
                node = node.right;
            }
            node = stack.pop();
            node.val += sumValue;// 节点加上之前node.val的和
            sumValue = node.val;// 之前node.val值的和更新
            node = node.left;// 左子树遍历
        }
        return root;
    }

    /**
     * 1214.查找两颗二叉搜索树之和
     * 给出两棵二叉搜索树，请你从两棵树中各找出一个节点，使得这两个节点的值之和等于目标值 Target。
     *
     * 如果可以找到返回 True，否则返回 False。
     *
     * 参考两数之和的做法来做这个，利用二叉搜索树的性质（left<root<right）来找另一个节点的值
     * @param root1 第一颗二叉搜索树root
     * @param root2 第二颗二叉搜索树root
     * @param target 目标值
     * @return
     */
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        if (root1 == null || root2 == null){
            return false;
        }
        TreeNode node = root1;
        Deque<TreeNode> stack = new LinkedList<>();// 双端队列模拟栈
        while(node != null || !stack.isEmpty()){
            stack.push(node);
            node = node.left;
            while (node == null && !stack.isEmpty()){
                node = stack.pop();// 中序遍历出栈
                int value = target - node.val;// 目标value值
                // 从另一个二叉搜索树中寻找value所在的节点
                TreeNode node2 = searchBST(root2,value);
                if (node2 != null){// 节点不空则满足题意，否则继续遍历
                    return true;
                }
                node = node.right;
            }
        }
        return false;
    }
}
