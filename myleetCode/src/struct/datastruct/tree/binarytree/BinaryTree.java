package struct.datastruct.tree.binarytree;

import struct.pub.list.ListNode;
import struct.pub.tree.TreeLinkNode;
import struct.pub.tree.TreeNode;

import java.util.*;

/**
 * 二叉树类型题目
 */
public class BinaryTree {

    /**
     * 94.中序遍历二叉树
     *
     * @param root root
     * @return 中序遍历顺序
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> resultList = new ArrayList<>();
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()){
            if (node != null){
                stack.push(node);
                node = node.left;
            }else {
                node = stack.pop();
                resultList.add(node.val);
                node = node.right;
            }
        }
        return resultList;
    }

    /**
     * 144.先序遍历二叉树
     *
     * @param root root
     * @return 先序遍历顺序
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> resultList = new ArrayList<>();
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()){
            if (node != null){
                resultList.add(node.val);
                stack.push(node);
                node = node.left;
            }else {
                node = stack.pop();
                node = node.right;
            }
        }
        return resultList;
    }

    /**
     * 145.二叉树后序遍历
     *
     * @param root root
     * @return 后序遍历顺序
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> resultList = new LinkedList<>();
        TreeNode node = root;
        TreeNode tempNode = node;
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()){
            if (node != null){
                // 左子树压栈
                stack.push(node);
                node = node.left;
            }else {
                // node为空了，获取栈顶元素
                node = stack.peek();
                if (node.right == null || node.right == tempNode){
                    // node没有右子树，或者右子树已经被访问过，则表示当前节点被访问，并弹出
                    tempNode = stack.pop();
                    resultList.add(node.val);
                    node = null;
                } else {
                    // 右子树还没有被访问，进入右子树
                    node = node.right;
                }
            }
        }
//        while (node != null) {
//            while (node.left != null) {
//                stack.push(node);
//                node = node.left;
//            }
//            while (node != null && (node.right == null || node.right == tempNode)) {
//                resultList.add(node.val);
//                tempNode = node;// 上一个已经输出的节点
//                if (stack.isEmpty()) {
//                    return resultList;
//                }
//                node = stack.pop();
//            }
//            stack.push(node);
//            node = node.right;
//        }
        return resultList;
    }

    /**
     * 100.相同的二叉树
     * @param p p
     * @param q q
     * @return 是否相同
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null){
            return true;
        }else if(p == null || q == null){
            return false;
        }
        return p.val == q.val && isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }

    /**
     * 101.对称二叉树，递归解法
     * @param root root
     * @return 是否对称二叉树
     */
    public boolean isSymmetricRecursion(TreeNode root) {
        if(root == null){
            return true;
        }
        return isSymmetricRecursion(root.left,root.right);
    }

    /**
     * 对称二叉树
     * @param node1 node1
     * @param node2 node2
     * @return 是否对称
     */
    private boolean isSymmetricRecursion(TreeNode node1,TreeNode node2){
        if (node1 == null && node2 == null){
            return true;
        }
        if (node1 == null || node2 == null || node1.val != node2.val){
            return false;
        }
        return isSymmetricRecursion(node1.left,node2.right) && isSymmetricRecursion(node1.right,node2.left);
    }

    /**
     * 104.二叉树最大深度，层次遍历或者递归都可以
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return leftDepth > rightDepth? 1+ leftDepth:1+rightDepth;
    }

    /**
     * 111.二叉树最小深度，层次遍历和递归都可以
     * 但是层次遍历只要找到第一个叶子节点就可以返回了，不用遍历整个二叉树
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int rear = queue.size();
        int front = 0;
        int floor = 1;
        while (!queue.isEmpty()){
            node = queue.poll();
            front++;
            if(node.left == null && node.right == null){
                break;
            }
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
            if(front == rear){// 进入下一层
                front = 0;
                rear = queue.size();
                floor++;
            }
        }
        return floor;
    }

    /**
     * 从上到下打印二叉树
     * @param root
     * @return
     */
    public int[] levelOrder1(TreeNode root) {
        if(root == null){
            return new int[]{};
        }
        List<Integer> res = new ArrayList<>();

        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            node = queue.poll();
            res.add(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        int[] resArr = new int[res.size()];
        for (int i = 0; i < resArr.length; i++) {
            resArr[i] = res.get(i);
        }
        return resArr;
    }

    /**
     * 102.层次遍历分层显示
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> level = new ArrayList<>();
        List<Integer> everyLevel = new ArrayList<>();
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int rear = queue.size();
        int index = 0;
        while (!queue.isEmpty()) {
            node = queue.poll();
            everyLevel.add(node.val);
            index++;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (index == rear) {
                level.add(everyLevel);
                everyLevel = new ArrayList<>();
                index = 0;
                rear = queue.size();
            }
        }
        return level;
    }

    /**
     * 103.锯齿形层次遍历
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> level = new ArrayList<>();
        LinkedList<Integer> everyLevel = new LinkedList<>();
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int rear = queue.size();
        boolean flag = true;// true表示正向，false表示反向
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (flag)
                everyLevel.add(node.val);
            else
                everyLevel.addFirst(node.val);
            rear--;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (rear == 0){
                level.add(everyLevel);
                everyLevel = new LinkedList<>();
                rear = queue.size();
                flag = !flag;
            }
        }
        return level;
    }

    /**
     * 107.倒序层次遍历分层显示
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) {
            return new LinkedList<>();
        }
        List<List<Integer>> level = new LinkedList<>();
        List<Integer> everyLevel = new LinkedList<>();
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int rear = queue.size();
        int index = 0;
        while (!queue.isEmpty()) {
            node = queue.poll();
            everyLevel.add(node.val);
            index++;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (index == rear) {
                ((LinkedList<List<Integer>>) level).addFirst(everyLevel);
                everyLevel = new LinkedList<>();
                index = 0;
                rear = queue.size();
            }
        }
        return level;
    }


    /**
     * 110.判断是否平衡二叉树
     * 自己写的
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root){
        if (root == null) {
            return true;
        }
        int leftDepth = depth(root.left);
        int rightDepth = depth(root.right);
        if (Math.abs(leftDepth - rightDepth) > 1) {
            return false;
        } else {
            return isBalanced(root.left) && isBalanced(root.right);
        }
    }

    /**
     * 110.判断是否平衡二叉树
     * 来自leetcode快速代码
     * @param root
     * @return
     */
    public boolean isBalanced2(TreeNode root) {
        return balancedDepth(root) != -1;
    }

    private int balancedDepth(TreeNode root) {
        if (root == null)
            return 0;

        int left = balancedDepth(root.left);
        if (left == -1)
            return -1;
        int right = balancedDepth(root.right);
        if (right == -1)
            return -1;

        if (Math.abs(left - right) > 1)
            return -1;
        return Math.max(left, right) + 1;
    }

    /**
     * 114.二叉树原地展开为链表，都在右孩子节点上
     * 另类的先序遍历，只是每次压入栈的都是当前节点的右孩子（前提是右孩子不为空）
     * 一直往左孩子遍历
     * @param root
     */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode curNode = root;
        TreeNode leftNode;
        Deque<TreeNode> stack = new LinkedList<>();
        while (curNode != null) {// 这个条件其实就是while(true)
            while (curNode.left != null) {
                if (curNode.right != null) {
                    stack.offer(curNode.right);
                }
                leftNode = curNode.left;
                curNode.right = leftNode;
                curNode.left = null;
                curNode = leftNode;
            }
            if (curNode.right != null) {
                curNode = curNode.right;
            } else {
                if (stack.isEmpty()) {
                    break;
                }
                curNode.right = stack.pollLast();
                curNode = curNode.right;
            }
        }
    }

    /**
     * 116.使用O(1)的空间复杂度层次遍历二叉树,
     * 完全二叉树的算法
     *
     * @param root
     */
    public void connect(TreeLinkNode root) {
        if (root == null) {
            return;
        }
        TreeLinkNode start = root;
        TreeLinkNode cur;
        while (start.left != null) {
            cur = start;
            while (cur != null) {
                cur.left.next = cur.right;
                if (cur.next != null) {
                    cur.right.next = cur.next.left;
                }
                cur = cur.next;
            }
            start = start.left;
        }
    }

    /**
     * 117.使用O(1)的空间复杂度层次遍历二叉树,自己写的通用算法
     * 通用算法
     *
     * @param root
     */
    public void connectByGeneral(TreeLinkNode root) {
        if (root == null) {
            return;
        }
        TreeLinkNode parent = root;
        TreeLinkNode start;
        TreeLinkNode cur;
        if (parent.left != null) {
            start = parent.left;
        } else if (parent.right != null) {
            start = parent.right;
        } else {
            return;
        }
        while (start != null) {
            cur = start;
            while (parent != null) {
                if (cur == parent.left) {
                    if (parent.right != null) {
                        cur.next = parent.right;
                        cur = cur.next;
                    } else {
                        parent = parent.next;
                    }
                } else if (cur == parent.right) {
                    parent = parent.next;
                } else {
                    if (parent.left != null) {
                        cur.next = parent.left;
                        cur = cur.next;
                    } else if (parent.right != null) {
                        cur.next = parent.right;
                        cur = cur.next;
                    } else {
                        parent = parent.next;
                    }
                }
            }
            parent = start;
            while (start != null) {
                if (start.left != null) {
                    start = start.left;
                    break;
                } else if (start.right != null) {
                    start = start.right;
                    break;
                } else {
                    start = start.next;
                }
            }
        }
    }

    /**
     * 124. 二叉树中的最大路径和
     * 思路：二叉树中某一个节点为根结点的最大路径和，
     * 等于该节点的节点值，加上左子树的最大路径和（若为负，直接取0，不要这子树了），
     * 加上右子树的最大路径和（若为负，直接取0，不要这子树了）。
     *
     * 其中左右子树的最大路径和：贪心来做，状态转移方程为
     *
     * maxSum(root)=max(root.val, root.val+max(maxSum(root.left), maxSum(root.right))).
     * @param root
     * @return
     */

    public int maxPathSum(TreeNode root) {
        if (root == null) return 0;
        int[] res = new int[]{Integer.MIN_VALUE};
        maxPathHelper(res,root);
        return res[0];
    }

    private int maxPathHelper(int[] res,TreeNode root){
        if (root == null) return 0;
        int leftMaxPathSum = maxPathHelper(res,root.left);
        int rightMaxPathSum = maxPathHelper(res,root.right);
        leftMaxPathSum = Math.max(leftMaxPathSum,0);
        rightMaxPathSum = Math.max(rightMaxPathSum,0);
        res[0] = Math.max(res[0] , root.val+leftMaxPathSum+rightMaxPathSum);
        return Math.max(root.val,Math.max(root.val + leftMaxPathSum,root.val + rightMaxPathSum));
    }

    /**
     * 129. 求根到叶子节点数字之和，深度优先遍历
     * @param root 根节点
     * @return
     */
    public int sumNumbersDFS(TreeNode root) {
        int sum = 0;
        if (root == null){
            return sum;
        }
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(node);
        while (!stack.isEmpty()){
            node = stack.pop();
            if (node.left == null && node.right == null){
                sum += node.val;
            }else {
                if (node.left != null){
                    node.left.val += node.val*10;
                    stack.push(node.left);
                }
                if (node.right != null){
                    node.right.val += node.val*10;
                    stack.push(node.right);
                }
            }
        }
        return sum;
    }

    /**
     * 129. 求根到叶子节点数字之和，广度优先遍历
     * @param root 根节点
     * @return
     */
    public int sumNumbersBFS(TreeNode root) {
        int sum = 0;
        if (root == null){
            return sum;
        }
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            if (node.left == null && node.right == null){
                sum += node.val;
            }else {
                if (node.left != null){
                    node.left.val += node.val*10;
                    queue.offer(node.left);
                }
                if (node.right != null){
                    node.right.val += node.val*10;
                    queue.offer(node.right);
                }
            }
        }
        return sum;
    }

    /**
     * 156.上下翻转二叉树
     * 先翻转左子树的链表，反转后左孩子的右子树指针，赋值给当前节点的右子树，再交换左右子树
     * @param root root
     * @return 上下反转后的root
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null){
            return null;
        }
        // 1.树沿着左子树反转
        TreeNode curNode = root;
        TreeNode prevNode = null;
        // 开始反转
        while (curNode != null){
            TreeNode nextNode = curNode.left;// 缓存原来的左孩子
            curNode.left = prevNode;// 左孩子反转
            prevNode = curNode;// 前置指针后移
            curNode = nextNode;// 当前指针后移
        }
        root = prevNode;
        // 这时候curNode是反转完之后的头节点了
        curNode = root;
        while (curNode.left != null){
            // 2.现在左孩子的右子树，变成自己的右子树
            curNode.right = curNode.left.right;
            curNode.left.right = null;// 之前的指针置空
            // 3.交换左右子树
            changeChild(curNode);
            curNode = curNode.right;// 这里为什么是right呢，因为左右节点被交换了
        }
        return root;
    }

    /**
     * 199.二叉树的右视图
     * 层次遍历，取最后一个
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null){
            return new ArrayList<>();
        }
        List<Integer> result = new LinkedList<>();
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int front = 0;
        int rear = queue.size();
        while (!queue.isEmpty()){
            node = queue.poll();
            front++;
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
            if (front == rear){
                result.add(node.val);
                front = 0;
                rear = queue.size();
            }
        }
        return result;
    }

    /**
     * 112.路径之和
     *
     * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，
     * 这条路径上所有节点值相加等于目标和。
     *
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        // 后序遍历二叉树，找到对应路径
        TreeNode node = root;
        Stack<TreeNode> stack = new Stack<>();
        int tempSum = 0;
        TreeNode tempNode = root;// 表示上一个已经被访问的孩子
        while(node != null || !stack.isEmpty()){
            if (node != null){
                tempSum += node.val;// 路径和记录
                // 后序遍历常规操作
                stack.push(node);
                node = node.left;
            }else {
                node = stack.peek();
                if (node.right == null || node.right == tempNode){
                    // 是叶子节点，且路径之和与目标值一致，加入结果集
                    if(node.left == null && node.right == null && tempSum == sum) {
                        return true;
                    }
                    // 当前节点处理，要出栈了，移除掉
                    tempSum -= node.val;
                    // 记录上个节点，后序遍历常规操作
                    tempNode = stack.pop();
                    node = null;
                }else {
                    // 进入右子树，不需要其他操作，当前node的处理，在之前的node != null的代码块中已经处理了
                    node = node.right;
                }
            }
        }
        return false;
    }

    /**
     * 113.路径之和2，后序遍历的非递归解法
     * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
     *
     * @param root root
     * @param sum 目标sum
     * @return res结果集
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        // 后序遍历二叉树，找到对应路径
        TreeNode node = root;
        Stack<TreeNode> stack = new Stack<>();
        LinkedList<Integer> everyResult = new LinkedList<>();
        List<List<Integer>> res = new LinkedList<>();
        int tempSum = 0;
        TreeNode tempNode = root;// 表示上一个已经被访问的孩子
        while(node != null || !stack.isEmpty()){
            if (node != null){
                tempSum += node.val;// 路径和记录
                everyResult.add(node.val);// 当前路径记录
                // 后序遍历常规操作
                stack.push(node);
                node = node.left;
            }else {
                node = stack.peek();
                if (node.right == null || node.right == tempNode){
                    // 是叶子节点，且路径之和与目标值一致，加入结果集
                    if(node.left == null && node.right == null && tempSum == sum) {
                        res.add(new ArrayList<>(everyResult));
                    }
                    // 当前节点处理，要出栈了，移除掉，路径和去掉当前节点
                    everyResult.removeLast();
                    tempSum -= node.val;
                    // 记录上个节点，后序遍历常规操作
                    tempNode = stack.pop();
                    node = null;
                }else {
                    // 进入右子树，不需要其他操作，当前node的处理，在之前的node != null的代码块中已经处理了
                    node = node.right;
                }
            }
        }
        return res;
    }

    /**
     * 113.路径之和2递归解法
     * @param root root
     * @param sum sum
     * @return 路径
     */
    public List<List<Integer>> pathSumByRecursion(TreeNode root, int sum){
        List<List<Integer>> res = new ArrayList<>();
        pathSumRecursion(root,sum,0,new LinkedList<>(),res);
        return res;
    }

    /**
     * 递归求到叶子节点的路径和
     * @param node node
     * @param sum 目标sum
     * @param pathSum 当前路径的sum
     * @param currentPath 当前路径
     * @param res 最终结果
     */
    private void pathSumRecursion(TreeNode node,int sum,int pathSum,Deque<Integer> currentPath,List<List<Integer>> res){
        if (node == null){
            return ;
        }
        // 路径sum和路径记录
        pathSum += node.val;
        currentPath.add(node.val);

        if (node.left == null && node.right == null && pathSum == sum){
            // 为叶子节点，sum相同加入
            res.add(new ArrayList<>(currentPath));
        }else {
            // 递归进入左右子树
            pathSumRecursion(node.left,sum,pathSum,currentPath,res);
            pathSumRecursion(node.right,sum,pathSum,currentPath,res);
        }
        // 回到本层，移除这个节点
        currentPath.pollLast();
    }

    /**
     * 222.完全二叉树的节点个数
     * 层次遍历肯定能搞定，但是这里采用二分查找，提高效率
     * @param root 根节点
     * @return 完全二叉树节点数
     */
    public int countNodes(TreeNode root) {
        if (root == null){
            return 0;
        }
        TreeNode node = root;
        int height = getLeftHeight(node);
        if(height == 1){
            return 1;
        }
        int countNode = 0;
        while (node != null){
            int rightHeight = getLeftHeight(node.right);
            if (rightHeight == height - 1){
                countNode += 1 << height - 1;
                node = node.right;
            }else {
                countNode += 1<< height - 2;
                node = node.left;
            }
            height--;
        }
        return countNode;
//        TreeNode prevNode = null;
//        int beforeNodes = (1<< height - 1) - 1;// 到倒数第二层的节点总数
//        int lastCount = 0;// 最后一层节点数
//        while (node != null){
//            int leftHeight = getLeftHeight(node);
//            int rightHeight = getRightHeight(node);
//            if (leftHeight == rightHeight){
//                if (null == prevNode || node == prevNode.right){
//                    if (!(node.left == null && node.right == null)){
//                        lastCount += 1 << leftHeight - 1;
//                    }
//                    break;
//                }
//                lastCount += 1 << leftHeight - 1;
//                node = prevNode.right;
//            }else {
//                prevNode = node;
//                node = node.left;
//            }
//        }
//        return beforeNodes + lastCount;
    }

    private int getLeftHeight(TreeNode node){
        int height = 0;
        while (node != null){
            height++;
            node = node.left;
        }
        return height;
    }

    /**
     * 236.二叉树公共祖先
     * 思路，根节点分别往左右子树查找，如果同时能获取到左右子树，则表示该节点是公共祖先，
     * 如果左为空，则表示p,q都在右子树中，反之则p,q都在左子树中，然后递归进行查找
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode leftNode = lowestCommonAncestor(root.left, p, q);
        TreeNode rightNode = lowestCommonAncestor(root.right, p, q);
        //return leftNode == null ? rightNode : rightNode == null ? leftNode : root；
        if(leftNode != null && rightNode != null){
            return root;// p q 分别位于左右子树的情况
        }
        return leftNode == null ? rightNode : leftNode;
    }

    /**
     * 250.统计同值的子树
     * 给定一个二叉树，统计该二叉树数值相同的子树个数。
     *
     * 同值子树是指该子树的所有节点都拥有相同的数值。
     * @param root
     * @return
     */
    public int countUnivalSubtrees(TreeNode root) {
        int[] res = new int[]{0};
        isUnivalSubtree(res,root);
        return res[0];
    }

    private boolean isUnivalSubtree(int[] res,TreeNode root) {
        if (root == null){
            return true;
        }
        boolean leftValueEquals = isUnivalSubtree(res,root.left);
        if (leftValueEquals && root.left != null){
            leftValueEquals = root.val == root.left.val;
        }
        boolean rightValueEquals = isUnivalSubtree(res,root.right);
        if (rightValueEquals && root.right != null){
            rightValueEquals = root.val == root.right.val;
        }
        // 左右同值，同值子树+1
        if (leftValueEquals && rightValueEquals){
            res[0]++;
        }
        return leftValueEquals && rightValueEquals;
    }

    /**
     * 257.二叉树所有路径
     * @param root root
     * @return 路径list
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        preOrderTree_257(root,"",res);
        return res;
    }

    private void preOrderTree_257(TreeNode node,String prev,List<String> res){
        if (node == null){
            return;
        }
        // 路径加入前缀
        prev += node.val;
        if (node.left == null && node.right == null){
            res.add(prev);// 叶子节点是路径终点
        }else {
            // 进入左右子树，加上连接符
            prev += "->";
            preOrderTree_257(node.left,prev,res);
            preOrderTree_257(node.right,prev,res);
        }
    }

    /**
     * 298.二叉树最长连续序列
     * 给你一棵指定的二叉树，请你计算它最长连续序列路径的长度。
     *
     * 该路径，可以是从某个初始结点到树中任意结点，通过「父 - 子」关系连接而产生的任意路径。
     *
     * 这个最长连续的路径，必须从父结点到子结点，反过来是不可以的。
     *
     * @param root root
     * @return 最长连续路径
     */
    public int longestConsecutive_298(TreeNode root) {
        int[] res = new int[]{0};
        longestConsecutiveHelper(res,root);
        return res[0];
    }

    private int longestConsecutiveHelper(int[] res,TreeNode root){
        if (root == null){
            return 0;
        }
        // 左孩子为root的最长连续序列，累加上自己
        int leftLongest = longestConsecutiveHelper(res,root.left) + 1;
        // 右孩子为root的最长连续序列，累加上自己
        int rightLongest = longestConsecutiveHelper(res,root.right) + 1;
        // 不满足最长连续，只记录自己
        if (root.left != null && root.val + 1 != root.left.val){
            leftLongest = 1;
        }
        // 不满足最长连续，只记录自己
        if (root.right != null && root.val + 1 != root.right.val){
            rightLongest = 1;
        }
        // 最长连续序列获取，当前最长序列为左右序列中较大的那一个
        int max = Math.max(leftLongest,rightLongest);
        res[0] = Math.max(res[0],max);
        return max;
    }

    /**
     * 549.二叉树中最长的连续路径，不一定要父子，可以子父子
     * @param root root
     * @return
     */
    public int longestConsecutive(TreeNode root){
        int[] res = {0};
        getLongestConsecutive(res,root);
        return res[0];
    }

    /**
     * 根节点有可能处于递增或递减位置，
     * 所以创建一个数组，分别用来保存当前节点的递增数列的连续路径数和递减数列的连续路径数
     * （这里路径长度是节点个数，所以数组要初始化为1），
     * 最后返回这个数组
     *
     * 1.根节点能对结果造成什么影响：如果根节点处于左子树或右子树的递增或递减序列中，则结果为包括根节点的递增序列数+包括根节点的递减序列数-1（去除重复根节点）
     *
     * 2.对下次递归的贡献：求出当前节点的递增和递减序列数。
     *
     * 当当前根节点处于左子树的递增位置时，当前节点的递增序列数为左子树的递增序列数+1
     * 当当前根节点处于左子树的递减位置时，当前节点的递减序列数为左子树的递减序列数+1
     * 当当前根节点处于右子树的递增位置时，当前节点的递增序列数为左子树递增序列数+1和右子树递增序列数+1的最大值
     * 当当前根节点处于右子树的递减位置时，当前节点的递减序列数为左子树递减序列数+1和右子树递减序列数+1的最大值
     *
     * @param res res结果
     * @param root 节点
     * @return 最长升序和最长降序节点数
     */
    private int[] getLongestConsecutive(int[] res,TreeNode root){
        if (root == null){
            return new int[]{0,0};
        }
        // 当前升降的最长序列，包括根节点自己
        int asc = 1,desc = 1;
        if (root.left != null){
            // 0升序，1降序
            // 左子树的升降序最长序列
            int[] left = getLongestConsecutive(res,root.left);
            // 左子树和root的关系不可能既升又降，对root进行一个判断，更新左子树升序降序的路径节点值
            if (root.val - 1 == root.left.val){
                // 升序，root比left多1
                asc = left[0] + 1;
            }else if(root.val + 1 == root.left.val){
                // 降序，root比left少1
                desc = left[1] + 1;
            }
        }
        if (root.right != null){
            // 0升序，1降序
            // 右子树的升降序最长序列
            int[] right = getLongestConsecutive(res,root.right);
            // 右子树和root的关系不可能既升又降，对root进行一个判断，更新右子树升序降序的路径节点值
            if (root.val - 1 == root.right.val){
                // 升序，root比right多1
                asc = Math.max(asc,right[0] + 1);
            }else if (root.val + 1 == root.right.val){
                // 降序，root比right少1
                desc = Math.max(desc,right[1] + 1);
            }
        }
        // 左到右的全升序是左升，右降的形式，所以两者相加去掉自身即可
        // 左到右的全降序是左降，右升的形式，所以两者相加去掉自身即可
        res[0] = Math.max(res[0],asc + desc - 1);
        return new int[]{asc,desc};
    }

    /**
     * 337.打家劫舍 III
     *
     * 动态规划算法思想：以node为根节点的最优解要氛围两种状态
     * 1.当前根节点node不取，则node的最优解为node.left的最优解+node.right的最优解。
     *  即->node.maxValue = node.left.maxValue + node.right.maxValue
     * 2.当前根节点node要取，则node的直接左右孩子都不能取，node的最优解是node.left的左右子树最优解之和+node.right的左右子树最优解之和
     * 即-> node.maxValue = node.value + node.left.left.maxValue + node.left.right.maxValue + node.right.left.maxValue + node.right.right.maxValue
     *
     * @param root 根节点
     * @return 最优解
     */
    public int rob(TreeNode root) {
        int[] res = dpRob(root);
        return Math.max(res[0],res[1]);
    }

    /**
     * 用一个数组来建立备忘录，不用再次重复计算左右子节点的最优值
     * TODO 考虑使用非递归的后序遍历解决，自底向上，同样可以避免重复计算
     * @param root
     * @return
     */
    private int[] dpRob(TreeNode root){
        // res[0]表示不选当前节点的最优解，res[1]表示选择当前节点的最优解
        int[] res = new int[]{0,0};
        if (root == null){
            return res;
        }
        int[] leftRes = dpRob(root.left);
        int[] rightRes = dpRob(root.right);
        // 左右子树可以随便抢，不一定非要从左右子树节点选择，所以要计算一下最优值，然后取和
        res[0] = Math.max(leftRes[0],leftRes[1]) + Math.max(rightRes[0],rightRes[1]);
        res[1] = root.val + leftRes[0] + rightRes[0];
        return res;
    }

    // 这种算法产生了大量的重复计算
    private int robHelper(TreeNode root){
        if (root == null){
            return 0;
        }
        // 1.这里的 robHelper(root.left)和robHelper(root.right)已经计算过子节点的最优解的
        int exclude = robHelper(root.left) + robHelper(root.right);// 不取当前根节点的最优解
        int include = root.val;// 取当前根节点的最优解
        if (root.left!= null){
            // 2.这里又重新计算的左孩子节点的左右子树最优解，与1中的robHelper(root.left)计算重复
            include  +=robHelper(root.left.left) + robHelper(root.left.right);
        }
        if (root.right != null){
            // 3.同理，这里又重新计算的右孩子节点的左右子树最优解，与1中的robHelper(root.right)计算重复
            include  += robHelper(root.right.left) + robHelper(root.right.right);
        }
        return Math.max(exclude,include);
    }

    /**
     * 366.寻找完全二叉树的叶子节点
     * 这个完全二叉树的概念呢不对，骗老子
     * 重点：完全二叉树
     * @param root
     * @return
     */
    public List<List<Integer>> findLeaves(TreeNode root){
        List<List<Integer>> result = new ArrayList<>();
        if (root == null){
            return result;
        }
        List<TreeNode> levelNodeList = new ArrayList<>();// 每一层的节点
        Deque<List<TreeNode>> levelQueue = new LinkedList<>();// 层次队列
        Queue<TreeNode> queue = new LinkedList<>();// 遍历用的队列
        TreeNode node = root;
        queue.offer(node);
        int front = 0;
        int rear = queue.size();
        // 层次遍历分层
        while (!queue.isEmpty()){
            node = queue.poll();
            front++;
            levelNodeList.add(node);
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
            if (front == rear){
                // 当前层结束，队头队尾重新初始化
                front = 0;
                rear = queue.size();
                // 节点层进入层级双端队列
                levelQueue.push(levelNodeList);
                levelNodeList = new ArrayList<>();
            }
        }
        levelNodeList = levelQueue.pop();// 当前层
        while (true){
            List<Integer> tempResult = new ArrayList<>();
            for (TreeNode tempNode : levelNodeList){
                tempResult.add(tempNode.val);// 当前层全部加入
            }
            result.add(tempResult);
            int currentLength = levelNodeList.size();// 当前层节点数
            if (levelQueue.isEmpty()){
                break;
            }
            List<TreeNode> lastList = levelQueue.peek();// 上一层
            int lastLeavesStart = ((currentLength + 1) >> 1) - 1;// 上一层叶子节点开始的位置
            if (lastLeavesStart < lastList.size() - 1){
                for (int i = lastList.size() - 1;i > lastLeavesStart;i--){
                    tempResult.add(lastList.get(i).val);
                    lastList.remove(i);
                }
            }
            levelNodeList = levelQueue.pop();
        }
        return result;
    }

    /**
     * 508. 出现次数最多的子树元素和
     * 给出二叉树的根，找出出现次数最多的子树元素和。
     * 一个结点的子树元素和定义为以该结点为根的二叉树上所有结点的元素之和（包括结点本身）。
     * 然后求出出现次数最多的子树元素和。
     * 如果有多个元素出现的次数相同，返回所有出现次数最多的元素（不限顺序）
     *
     * 思路，后序遍历
     * @param root
     * @return
     */
    public int[] findFrequentTreeSum(TreeNode root) {
        Map<Integer,Integer> resultCount = new HashMap<>();
        if (root == null) {
            return new int[0];
        }
        countSum(root,resultCount);
        int max = 0;
        List<Integer> result = new ArrayList<>();
        for (Map.Entry entry:resultCount.entrySet()){
            Integer key = (Integer) entry.getKey();
            Integer value = (Integer) entry.getValue();
            if (value > max){
                max = value;
                result.clear();
                result.add(key);
            }else if (value == max){
                result.add(key);
            }
        }
        int[] ret = new int[result.size()];
        for(int i = 0;i<ret.length;i++){
            ret[i] = result.get(i);
        }
        return ret;
    }

    /**
     * 508.记录每个子树节点出现的次数
     * @param root
     * @param resultCount
     * @return
     */
    private int countSum(TreeNode root, Map<Integer,Integer> resultCount) {
        int sum = 0;
        sum += root.val;
        if (root.left != null){
            sum+=countSum(root.left,resultCount);
        }
        if (root.right != null){
            sum+=countSum(root.right,resultCount);
        }
        Integer count = resultCount.getOrDefault(sum,0) + 1;
        resultCount.put(sum,count);
        return sum;
    }


    /**
     * 513. 找树左下角的值
     * 给定一个二叉树，在树的最后一行找到最左边的值。
     * 层次遍历，每次记录下第一个节点
     * @param root
     * @return
     */
    public int findBottomLeftValue(TreeNode root) {
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        TreeNode leftestNode = root;
        int front = 0;
        int rear = queue.size();
        while (!queue.isEmpty()){
            node = queue.poll();
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
            if (front == 0){
                leftestNode = node;
            }
            front++;
            if(front == rear){
                front = 0;
                rear = queue.size();
            }
        }
        return leftestNode.val;
    }

    /**
     * 515. 在每个树行中找最大值
     * 您需要在二叉树的每一行中找到最大的值。
     * 依旧层次遍历
     * @param root
     * @return
     */
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> resultList = new LinkedList<>();
        if (root == null){
            return resultList;
        }
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int front = 0;
        int rear = queue.size();
        int max = Integer.MIN_VALUE;
        while (!queue.isEmpty()){
            node = queue.poll();
            if (node.val > max){
                max = node.val;
            }
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
            front++;
            if(front == rear){
                resultList.add(max);
                front = 0;
                rear = queue.size();
                max = Integer.MIN_VALUE;
            }
        }
        return resultList;
    }

    /**
     * 二叉树中第二小的值
     * @param root
     * @return
     */
    public int findSecondMinimumValue(TreeNode root) {
        if(root == null){
            return -1;
        }
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();// 层次遍历的队列
        Queue<TreeNode> pq = new PriorityQueue<>(2, new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode o1, TreeNode o2) {
                return o2.val - o1.val;
            }
        });
        queue.offer(node);
        pq.offer(node);
        Set<Integer> set = new HashSet<>();
        set.add(node.val);
        while (!queue.isEmpty()){
            node = queue.poll();
            if (pq.size() < 2){
                if (node.val != pq.peek().val){
                    pq.offer(node);
                }
            }else {
                if (node.val < pq.peek().val && !set.contains(node.val)){
                    set.remove(pq.poll().val);
                    set.add(node.val);
                    pq.offer(node);
                }
            }
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
        if (pq.size() < 2){
            return -1;
        }
        return pq.peek().val;
    }

    /**
     * 536.从字符串中生成二叉树
     * @param s s
     * @return
     */
    public TreeNode str2tree(String s) {
        if (s == null ||s.length() == 0){
            return null;
        }
        // 处理根节点
        int index = s.indexOf('(');
        if (index < 0){
            return new TreeNode(Integer.valueOf(s));
        }
        // 根节点
        TreeNode root = new TreeNode(Integer.parseInt(s.substring(0,index)));
        StringBuilder prefix = new StringBuilder();
        Deque<TreeNode> stack = new LinkedList<>();// 栈
        stack.push(root);// 根节点入栈
        for (int i = index + 1; i < s.length(); i++){
            char c = s.charAt(i);
            if ((c >= '0' && c <= '9') || c == '-'){
                prefix.append(c);
            }else {
                if (prefix.length() > 0){
                    // 新元素入栈
                    stack.push(new TreeNode(Integer.parseInt(prefix.toString())));
                    prefix.setLength(0);
                }
                if (c == ')'){
                    // 反括号表示栈顶节点生成结束，没有子树了，要出栈
                    TreeNode node = stack.pop();
                    TreeNode parent = stack.peek();
                    if (parent.left == null){
                        parent.left = node;
                    }else {
                        parent.right = node;
                    }
                }
            }
        }
        return root;
    }

    /**
     * 543.二叉树的直径
     * @param root root
     * @return 直径
     */
    public int diameterOfBinaryTree(TreeNode root) {
        int[] diameter = new int[]{0};
        diameterNode(diameter,root);
        return diameter[0];
    }

    /**
     * 543.最大直径累计
     * @param diameter 直径
     * @param node node
     * @return 节点的最长直径
     */
    private int diameterNode(int[] diameter,TreeNode node){
        if (node == null){
            return 0;
        }
        int leftPath = diameterNode(diameter, node.left);
        int rightPath = diameterNode(diameter, node.right);
        int sum = leftPath + rightPath;
        diameter[0] = Math.max(diameter[0],sum);
        // 节点本身的最长路径，左右的最大值 带上自己
        return Math.max(leftPath,rightPath) + 1;
    }

    /**
     * 563. 二叉树的坡度
     * 给定一个二叉树，计算整个树的坡度。
     *
     * 一个树的节点的坡度定义即为，该节点左子树的结点之和和右子树结点之和的差的绝对值。空结点的的坡度是0。
     *
     * 整个树的坡度就是其所有节点的坡度之和。
     *
     * @param root root
     * @return tilt
     */
    public int findTilt(TreeNode root) {
        int[] tilt = new int[]{0};
        sumNodeTilt(tilt,root);
        return tilt[0];
    }

    /**
     * 563.坡度累计
     * @param tilt 坡度引用
     * @param node 节点
     * @return 节点之和
     */
    private int sumNodeTilt(int[] tilt, TreeNode node){
        if (node == null){
            return 0;
        }
        int leftSum = sumNodeTilt(tilt,node.left);
        int rightSum = sumNodeTilt(tilt,node.right);
        // 节点坡度累加
        tilt[0] = tilt[0] + Math.abs(leftSum - rightSum);
        // 节点之和
        return node.val + leftSum + rightSum;
    }


    /**
     * 572.另一个树的子树
     * 给定两个非空二叉树 s 和 t，检验 s 中是否包含和 t 具有相同结构和节点值的子树。s 的一个子树包括 s 的一个节点和这个节点的所有子孙。s 也可以看做它自身的一棵子树。
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {
        // 从s中找到t，先序遍历找到全部满足条件的t
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = s;
        while (node != null || !stack.isEmpty()){
            if (node != null){
                if (node.val == t.val){
                    // 每一个直接判断就行，可以就返回了，不用继续遍历
                    if (isSub(node,t)){
                        return true;
                    }
                }
                stack.push(node);
                node = node.left;
            }else {
                node = stack.pop();
                node = node.right;
            }
        }
        return false;
    }

    /**
     * 两棵树是否相同结构
     * @param root1 root1
     * @param root2 root2
     * @return 是否相同结构
     */
    private boolean isSub(TreeNode root1,TreeNode root2){
        if (root1 == null && root2 == null){
            return true;
        }else if (root1 == null || root2 == null){
            return false;
        }else {
            return root1.val == root2.val && isSub(root1.left,root2.left) && isSub(root1.right,root2.right);
        }
    }

    /**
     * 617.合并二叉树
     * @param t1 root1
     * @param t2 root2
     * @return new root
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null){
            return t2;
        }
        if (t2 == null){
            return t1;
        }
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left,t2.left);
        t1.right = mergeTrees(t1.right,t2.right);
        return t1;
    }

    /**
     * 623.二叉树中添加一行
     * @param root 根节点
     * @param v value 节点value
     * @param d 层数
     * @return 树的根节点
     */
    public TreeNode addOneRow(TreeNode root, int v, int d) {
        if(d == 1){
            TreeNode newRoot = new TreeNode(v);
            newRoot.left = root;
            return newRoot;
        }
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int k = 1;
        int front = 0;
        int rear = queue.size();
        while (!queue.isEmpty() && k < d - 1){
            node = queue.poll();
            front++;
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
            if(front == rear){// 进入下一层
                front = 0;
                rear = queue.size();
                k++;
            }
        }
        while (!queue.isEmpty()){
            node = queue.poll();
            TreeNode newLeftNode = new TreeNode(v);
            newLeftNode.left = node.left;
            node.left = newLeftNode;
            TreeNode newRightNode = new TreeNode(v);
            newRightNode.right = node.right;
            node.right = newRightNode;
        }
        return root;
    }

    /**
     * 637. 二叉树的层平均值
     * @param root root
     * @return 每一层的平均值
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if (root == null){
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int front = 0;
        int rear = queue.size();
        double sum = 0.0;
        while (!queue.isEmpty()){
            TreeNode node = queue.poll();
            front++;
            sum += node.val;
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
            if (front == rear){
                double avg = sum/rear;
                result.add(avg);
                sum = 0.0;
                front = 0;
                rear = queue.size();
            }
        }
        return result;
    }

    /**
     * 652.寻找重复的子树
     * 序列化思维，记录下访问节点的后序遍历路径，序列化路径为node.val,leftPath,rightPath
     * 这里采用后序遍历来操作，用备忘录思想来存储左右节点之前的序列化路径
     * @param root root
     * @return
     */
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> result = new ArrayList<>();
        Map<TreeNode,String> nodePathMap = new HashMap<>();// 记录每个节点的序列化路径，备忘录
        Map<String,Integer> pathCount = new HashMap<>();// 路径计数器，其实只记录出现一次的
        TreeNode node = root;// 遍历的节点
        TreeNode tempNode = node;// 上一个遍历的节点
        Deque<TreeNode> stack = new LinkedList<>();// 老规矩，双端队列模拟栈
        // 后序遍历
        while (node != null) {
            while (node.left != null) {
                stack.push(node);
                node = node.left;
            }
            // 右节点为空，或者右节点已经被访问过
            while (node != null && (node.right == null || node.right == tempNode)) {
                //-------路径访问，备忘路径算法开始
                // 获取路径
                String path = getNodePath(node, nodePathMap);
                // 路径加入备忘录
                nodePathMap.put(node, path);
                // 之前不包括的，直接记录下来
                if (pathCount.get(path) != null && pathCount.get(path) == 1) {
                    // 如果包括了，代表路径重复了，加入返回的结果集
                    result.add(node);
                }
                // 路径次数记录
                pathCount.put(path, pathCount.getOrDefault(path, 0) + 1);
                //-------路径访问，备忘路径算法开始结束
                tempNode = node;// 上一个已经输出的节点
                if (stack.isEmpty()) {
                    return result;
                }
                node = stack.pop();
            }
            stack.push(node);
            node = node.right;
        }
        return result;
    }

    /**
     * 获取节点路径
     * @param node
     * @param nodePathMap
     * @return
     */
    private String getNodePath(TreeNode node,Map<TreeNode,String> nodePathMap){
        String leftNodePath = node.left == null ? "" : nodePathMap.get(node.left);
        String rightNodePath = node.right == null ? "" : nodePathMap.get(node.right);
        return node.val + "," + leftNodePath + "," + rightNodePath;
    }

    /**
     * 654.最大二叉树
     * 给定一个不含重复元素的整数数组。一个以此数组构建的最大二叉树定义如下：
     *
     * 二叉树的根是数组中的最大元素。
     * 左子树是通过数组中最大值左边部分构造出的最大二叉树。
     * 右子树是通过数组中最大值右边部分构造出的最大二叉树。
     * 通过给定的数组构建最大二叉树，并且输出这个树的根节点。
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        TreeNode root = new TreeNode(nums[0]);
        for (int i=1;i<nums.length;i++){
            TreeNode newNode = new TreeNode(nums[i]);
            if (nums[i] > root.val){// 比原根节点大，成为新的根节点
                newNode.left = root;// 旧根节点的下标肯定小于新节点，作为新根节点的左孩子
                root = newNode;// 新的根节点
            }else {
                TreeNode prev = root;
                TreeNode node = root.right;
                while (node != null && node.val > newNode.val){
                    prev = node;// 新的节点只会是旧节点的右子树，旧节点只要比新节点大，就继续往右子树找
                    node = node.right;// 右子树移动
                }
                prev.right = newNode;// 插入新节点
                newNode.left = node;
            }
        }
        return root;
    }

    /**
     * 662.二叉树最大宽度的结构类
     */
    private class MyPair{
        TreeNode node;// 树节点
        int index;// 节点的下标
        MyPair(TreeNode node, int index){
            this.node = node;
            this.index = index;
        }
    }

    /**
     * 662. 二叉树最大宽度
     * 给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。
     *
     * 每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。
     * @param root
     * @return
     */
    public int widthOfBinaryTree(TreeNode root) {
        if(root==null)
            return 0;
        Queue<MyPair> queue = new LinkedList<>();
        int max = 0;
        queue.add(new MyPair(root,0));
        while(!queue.isEmpty()){
            Queue<MyPair> nextQueue = new LinkedList<>();
            int left,right;
            left=Integer.MAX_VALUE;
            right=-1;
            int size = queue.size();
            while(!queue.isEmpty()){
                MyPair pair = queue.poll();
                TreeNode node = pair.node;
                int index = size==1? 0 : pair.index;
                if(index<left)
                    left = index;
                if(index>right)
                    right = index;
                if(node.left!=null)
                    nextQueue.add(new MyPair(node.left,index*2+1));
                if(node.right!=null)
                    nextQueue.add(new MyPair(node.right,(index+1)*2));
            }
            if(right-left+1 > max)
                max = right-left+1;
            queue = nextQueue;
        }
        return max;
    }

    /**
     * 663.均等树的划分
     * 给定一棵有 n 个结点的二叉树，你的任务是检查是否可以通过去掉树上的一条边将树分成两棵，且这两棵树结点之和相等
     *
     * 思路 ： 先后序遍历，记录下每个节点孩子节点值的集合
     * @param root root
     * @return
     */
    public boolean checkEqualTree(TreeNode root) {
        Map<TreeNode,Integer> nodeSumChildValue = sumTreeNodeChildValue(root);
        // 节点遍历开始，开始偏移计算
        int rootSum = nodeSumChildValue.get(root);
        if ((rootSum & 1) == 1){
            return false;// 奇数不行
        }

        return false;
    }

    private boolean findTreeNode(TreeNode node,int sum,Map<TreeNode,Integer> nodeSumChildValue){
        return false;
    }

    /**
     * 记录下所有节点，node.val+leftValue+rightValue的和
     * @param root
     * @return
     */
    private Map<TreeNode,Integer> sumTreeNodeChildValue(TreeNode root){
        if (root == null){
            return null;
        }
        Map<TreeNode,Integer> nodeSumChildValue = new HashMap<>();
        TreeNode node = root;
        TreeNode tempNode = null;// 记录上一个被访问的节点
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null){
            while (node.left != null){
                stack.push(node);
                node = node.left;
            }
            while (node != null && (node.right == null || node.right == tempNode)){
                tempNode = node;// 当前节点被访问
                Integer leftSumValue = nodeSumChildValue.getOrDefault(node.left,0);
                Integer rightSumValue = nodeSumChildValue.getOrDefault(node.right,0);
                nodeSumChildValue.put(node,node.val + leftSumValue + rightSumValue);
                if (stack.isEmpty()){
                    return nodeSumChildValue;
                }
                node = stack.pop();// 节点出栈
            }
            stack.push(node);
            node = node.right;
        }
        return nodeSumChildValue;
    }

    /**
     * 669.修剪二叉搜索树
     * @param root root
     * @param L 下限值
     * @param R 上限值
     * @return 修剪后的二叉搜索树
     */
    public TreeNode trimBST(TreeNode root, int L, int R) {
        TreeNode newRoot = null;
        TreeNode node = root;
        while (node != null){
            if (L <= node.val && node.val <= R){
                newRoot = node;// 找到剪枝后的根节点
                break;
            }else if(node.val < L){
                node = node.right;
            }else {
                node = node.left;
            }
        }
        if (newRoot == null){
            return null;
        }
        // L处理
        TreeNode tempNode = newRoot;
        node = newRoot.left;
        while (node != null){
            if (node.val > L){
                // 左指针接上
                tempNode.left = node;
                // 临时指针左移
                tempNode = node;
                node = node.left;
            }else if (node.val < L){
                // 数值小的，不要了
                tempNode.left = null;
                node = node.right;
            }else {
                // 左指针接上
                tempNode.left = node;
                // 最小值匹配到，比他小的都不要了
                node.left = null;
                break;
            }
        }
        // R值处理
        tempNode = newRoot;
        node = newRoot.right;
        while (node != null){
            if (node.val > R){
                // node.val比最大值还大的，不要了
                tempNode.right = null;
                node = node.left;
            }else if (node.val < R){
                // 右指针接上
                tempNode.right = node;
                // 临时指针右移
                tempNode = node;
                node = node.right;
            }else {
                // 右指针接上
                tempNode.right = node;
                // 最大值匹配到，比他大的都不管了
                node.right = null;
                break;
            }
        }
        return newRoot;
    }

    /**
     * 742.二叉树最近叶节点
     * 给定一个 每个结点的值互不相同 的二叉树，和一个目标值 k，找出树中与目标值 k 最近的叶结点。 
     *
     * 这里，与叶结点 最近 表示在二叉树中到达该叶节点需要行进的边数与到达其它叶结点相比最少。而且，当一个结点没有孩子结点时称其为叶结点。
     *
     * 在下面的例子中，输入的树以逐行的平铺形式表示。实际上的有根树 root 将以TreeNode对象的形式给出。
     *
     * @param root root
     * @param k 目标值
     * @return 最近叶子节点的值
     */
    public int findClosestLeaf(TreeNode root, int k) {
        // 先深度优先遍历
        TreeNode node = root;
        Map<TreeNode,TreeNode> parentMap = new HashMap<>();
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(node);
        while (!stack.isEmpty()){
            node = stack.pop();
            if (node.val == k){
                break;
            }
            if (node.right != null){
                parentMap.put(node.right,node);
                stack.push(node.right);
            }
            if (node.left != null){
                parentMap.put(node.left,node);
                stack.push(node.left);
            }
        }
        // 如果node就是叶子节点，就直接返回自己
        // 这一块已经冗余在下面的遍历情况中了
//        if (node.left == null && node.right == null){
//            return node.val;
//        }
        // key 路径数 value node.val
        int[] nearLeafValue = new int[]{Integer.MAX_VALUE,Integer.MAX_VALUE};
        TreeNode currentNode = node;
        int initLength = 0;
        while (currentNode != null){
            if (node == currentNode.left){
                node = currentNode.right;
            }else if(node == currentNode.right){
                node = currentNode.left;
            }else {
                node = currentNode;
            }
            // 当前node获取最小叶子节点路径长度
            makeMinLeaf(node,initLength,nearLeafValue);
            // currentNode往上递归
            currentNode = parentMap.get(currentNode);
            initLength+=2;// 向之前的兄弟节点遍历找叶子节点
            if (nearLeafValue[0] <= initLength){
                return nearLeafValue[1];
            }
        }
        return nearLeafValue[1];
    }

    /**
     * 记录每个root节点初始长度length下的最短路径，记录在nearLeafValue中
     * @param root root
     * @param length 初始化路径的长度
     * @param nearLeafValue 最近叶子节点的层数->叶子节点值
     */
    private void makeMinLeaf(TreeNode root,int length,int[] nearLeafValue){
        if (root == null){
            return ;
        }
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int front = 0;
        int rear = queue.size();
        int floor = length;
        while (!queue.isEmpty()){
            node = queue.poll();
            front++;
            if (node.left == null && node.right == null){
                // 找到第一个叶子节点记录下来返回
                // 之前的直接覆盖
                nearLeafValue[0] = floor;
                nearLeafValue[1] = node.val;
                return;
            }
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
            if (front == rear){
                front = 0;
                rear = queue.size();
                floor++;
                // 比最小的那个层还大了，就没比较继续遍历了
                if (nearLeafValue[0] <= floor){
                    return;
                }
            }
        }
    }


    /**
     * 814.二叉树剪枝 后续遍历删除节点
     * @param root root
     * @return
     */
    public TreeNode pruneTree(TreeNode root) {
        if (root == null){// 递归终止条件
            return null;
        }
        root.left = pruneTree(root.left);// 进入左子树，并重新赋值给父节点的左孩子
        root.right = pruneTree(root.right);// 进入右子树，并重新赋值给父节点的右孩子
        if (root.left == null && root.right == null && root.val == 0){
            return null;// 叶子节点，并且val是0的删除
        }
        return root;// 返回原节点
    }

    /**
     * 863.二叉树中所有距离为 K 的结点
     * target上面的节点已target为首节点
     * 深度优先遍历找到target，再往上递推找其他节点
     * target下面的节点已target为根，层次遍历查找
     * @param root
     * @param target
     * @param K
     * @return
     */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> resultList = new LinkedList<>();
        if (root == null){
            return resultList;
        }
        if (K == 0){
            resultList.add(target.val);
            return resultList;
        }
        TreeNode node = root;
        Map<TreeNode,TreeNode> parentMap = new HashMap<>();// 记录父子关系的map
        Deque<TreeNode> stack = new LinkedList<>();// 深度优先遍历的栈
        stack.push(node);
        while (!stack.isEmpty()){
            node = stack.pop();
            if (node == target) {
                break;
            }
            if (node.right != null) {
                stack.push(node.right);
                parentMap.put(node.right, node);
            }
            if (node.left != null) {
                stack.push(node.left);
                parentMap.put(node.left, node);
            }
        }

        // path只存储到target的路径
        TreeNode preNode = target;
        // 下面用广度优先，层次遍历
        addDistanceKNode(target,K,resultList);
        int i = K;
        while (i>0){
            node = parentMap.get(node);
            if (node == null){
                break;
            }
            if (i == 1){
                resultList.add(node.val);
            }
            i--;
            if(preNode == node.left){
                addDistanceKNode(node.right,i - 1,resultList);
            }else {
                addDistanceKNode(node.left,i - 1,resultList);
            }
            preNode = node;
        }

        return resultList;
    }

    /**
     * 层次遍历距离为K的节点
     * @param target
     * @param K
     * @param resultList
     */
    private void addDistanceKNode(TreeNode target,int K,List<Integer> resultList){
        if (target == null || K < 0){
            return;
        }
        TreeNode node = target;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int temp = 0;
        int front = 0;
        int rear = queue.size();
        while (!queue.isEmpty() && temp < K){
            node = queue.poll();
            if (node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
            front++;
            if (front == rear){
                front = 0;
                rear = queue.size();
                temp++;
            }
        }
        for (TreeNode tempNode : queue){
            resultList.add(tempNode.val);
        }
    }

    /**
     * 865. 具有所有最深结点的最小子树
     * 给定一个根为 root 的二叉树，每个结点的深度是它到根的最短距离。
     * 如果一个结点在整个树的任意结点之间具有最大的深度，则该结点是最深的。
     * 一个结点的子树是该结点加上它的所有后代的集合。
     * 深度优先遍历，找到的第一个左右子树深度相等的节点，就是最小子树的根子树
     * @param root
     * @return
     */
    public TreeNode subtreeWithAllDeepest(TreeNode root){
        return findNode(root);
    }

    /**
     * 获取节点的深度
     * @param root
     * @return
     */
    private int depth(TreeNode root){
        if (root == null){
            return 0;
        }
        int left = depth(root.left);
        int right = depth(root.right);
        return 1 + Math.max(left,right);
    }

    /**
     * 找到最大深度最小子树根节点
     * @param root
     * @return
     */
    private TreeNode findNode(TreeNode root){
        if (root == null){
            return null;
        }
        int left = depth(root.left);
        int right = depth(root.right);
        if (left == right){
            return root;
        }else if (left > right){
            return findNode(root.left);
        }else {
            return findNode(root.right);
        }
    }

    /**
     * 865. 具有所有最深结点的最小子树
     * 自己想的广度优先遍历，但是这个思路并不是太好，多遍历了节点，空间复杂度还增加了
     * @param root
     * @return
     */
    public TreeNode subtreeWithAllDeepestByMe(TreeNode root) {
        if (root == null){
            return null;
        }
        Map<TreeNode,TreeNode> map = new HashMap<>();// key ：子节点 , value : 父节点
        Queue<TreeNode> subNode = new LinkedList<>();// 最深节点的集合
        // 开始层次遍历
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int front = 0;
        int rear = queue.size();
        while(!queue.isEmpty()){
            node = queue.poll();
            subNode.offer(node);
            if (node.left != null){
                queue.offer(node.left);
                map.put(node.left,node);
            }
            if (node.right != null){
                queue.offer(node.right);
                map.put(node.right,node);
            }
            front++;
            if (front == rear){
                front = 0;
                rear = queue.size();
                if(rear != 0){
                    subNode.clear();
                }
            }
        }
        Set<TreeNode> set = new HashSet<>();
        while (subNode.size() > 1){
            node = subNode.poll();
            TreeNode parent = map.get(node);
            if (!set.contains(parent)){
                subNode.offer(parent);
                set.add(parent);
            }
        }
        return subNode.poll();
    }

    /**
     * 872. 叶子相似的树
     * @param root1
     * @param root2
     * @return
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null){
            return false;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        List<TreeNode> leaf1 = new ArrayList<>();
        List<TreeNode> leaf2 = new ArrayList<>();
        TreeNode node = root1;
        while (node != null || !stack.isEmpty()){
            if (node != null){
                stack.push(node);
                node = node.left;
            }else {
                node = stack.pop();
                if (node.left == null && node.right == null){
                    leaf1.add(node);
                }
                node = node.right;
            }
        }
        node = root2;
        stack.clear();
        boolean similar = true;
        while (node != null || !stack.isEmpty()){
            if (node != null){
                stack.push(node);
                node = node.left;
            }else {
                node = stack.pop();
                if (node.left == null && node.right == null){
                    leaf2.add(node);
                    if (leaf2.size() > leaf1.size() || node.val != leaf1.get(leaf2.size() - 1).val){
                        similar = false;
                        break;
                    }
                }
                node = node.right;
            }
        }
        return similar && leaf1.size() == leaf2.size();
    }

    /**
     * 889. 根据前序和后序遍历构造二叉树
     * 返回与给定的前序和后序遍历匹配的任何二叉树。
     *
     *  pre 和 post 遍历中的值是不同的正整数。
     * @param pre 前序遍历数组
     * @param post 后序遍历数组
     * @return 构造的二叉树
     */
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        // pre和post的长度总是相等的，
        if (pre.length == 0){
            return null;
        }
        if (pre.length == 1){
            return new TreeNode(pre[0]);
        }
        // 正向先序root->left->right，逆向后序root->right->left
        // pre的第一个，总是和post的最后一个相等
        int length = pre.length;
        TreeNode root = new TreeNode(pre[0]);
        // left的分隔点
        int postLeft = 1;
        for (int i = length - 1 ; i >= 0;i--){
            // 寻找后序遍历的左子树节点，前序的index为1的节点就是左子树的root，只要在后序遍历中找到相同的，从0->i就都是左子树
            if (pre[1] == post[i]){
                postLeft = i + 1;// 这个节点往前的（不包括postLeft，i才是真正的leftRootIndex），都是后序遍历的左子树
                break;
            }
        }
        // 左子树的前序遍历数组，前闭后开区间
        int[] leftPre = Arrays.copyOfRange(pre,1,1 + postLeft);
        // 左子树的后序遍历数组，前闭后开区间
        int[] leftPost = Arrays.copyOfRange(post,0,postLeft);
        // 递归构造左子树
        root.left = constructFromPrePost(leftPre,leftPost);

        // 右子树的前序遍历数组，前闭后开区间
        int[] rightPre = Arrays.copyOfRange(pre,1 + postLeft,length);
        // 右子树的后序遍历数组，前闭后开区间
        int[] rightPost = Arrays.copyOfRange(post,postLeft,length - 1);
        // 递归构造右子树
        root.right = constructFromPrePost(rightPre,rightPost);
        return root;
    }

    /**
     * 894.所有的满二叉树
     *
     * 满二叉树是一类二叉树，其中每个结点恰好有 0 或 2 个子结点。
     *
     * 返回包含 N 个结点的所有可能满二叉树的列表。 答案的每个元素都是一个可能树的根结点。
     *
     * 答案中每个树的每个结点都必须有 node.val=0。
     *
     * 你可以按任何顺序返回树的最终列表。
     *
     * 解题思路：
     * 当N = 1时，只有一个节点本身。
     * 当N = 3时，一个根节点，左边是N = 1时的子树，右边是N= 3 - 1 - 1，所以也是N= 1的子树。
     * 当N = 5时，一个根节点，左边可以是N = 1 或者N =3，相应的右边是N=3或者N=1。
     * 都可以拆成更小的子问题来解决，这点其实向分治（说是DP也行，但是这不是最优子结构的问题）
     * @param N
     * @return
     */
    public List<TreeNode> allPossibleFBT(int N) {
        List<TreeNode> result = new ArrayList<>();
        if((N & 1) == 0){
            // N为偶数，无法构成满二叉树
            return new ArrayList<>();
        }
        // 缓存，避免重复计算，因为左右对称，只需要求一半就行了
        Map<Integer,List<TreeNode>> cache = new HashMap<>();
        List<TreeNode> res = getAllFBT(N,cache);
        return res;
    }

    private List<TreeNode> getAllFBT(int N,Map<Integer,List<TreeNode>> cache){
        List<TreeNode> res = new ArrayList<>();
        if (N == 1){
            TreeNode node = new TreeNode(0);
            res.add(node);
            return res;
        }
        for (int i = 1; i < N ;i = i+2) {
            int rightN = N - 1 - i;
            List<TreeNode> leftRes = cache.get(i);
            if (leftRes == null){
                // 计算得出，计入缓存中
                leftRes = getAllFBT(i,cache);
                cache.put(i,leftRes);
            }
            List<TreeNode> rightRes = cache.get(rightN);
            if (rightRes == null){
                // 计算得出，计入缓存中
                rightRes = getAllFBT(rightN,cache);
                cache.put(rightN,rightRes);
            }
            for (TreeNode left : leftRes){
                for (TreeNode right : rightRes){
                    TreeNode node = new TreeNode(0);
                    node.left = left;
                    node.right = right;
                    res.add(node);
                }
            }
        }
        return res;
    }

    /**
     * 894.所有可能的满二叉树
     * 非递归
     * @param N
     * @return
     */
    public List<TreeNode> allPossibleFBT_FDG(int N) {
        Map<Integer, List<TreeNode>> cache = new HashMap<>();
        List<TreeNode> res = new ArrayList<>();
        if (N % 2 == 0) {
            return res;
        }
        TreeNode root = new TreeNode(0);
        res.add(root);
        cache.put(1, res);// 1的结果集
        // 偶数无法构成满二叉树，所以每次都+2
        for(int i=3; i<=N; i+=2){
            // 树的root求解
            res = new ArrayList<>();
            // 一侧子树数量从少到多加入结果集
            for(int j=1; j<=i-2; j+=2){
                // 每个 left 左子树， 对应 cache.get(i-j-1) 个右子树，一共有cache.get(j)个左子树
                for(TreeNode left : cache.get(j)){// 左侧树节点列表
                    for(TreeNode right: cache.get(i-j-1)){// 对应的右侧树的集合
                        TreeNode node = new TreeNode(0);
                        node.left = left;
                        node.right = right;
                        res.add(node);
                    }
                }
            }
            cache.put(i, res);
        }
        return cache.get(N);
    }

    /**
     * 897.递增顺序查找树
     * @param root
     * @return
     */
    public TreeNode increasingBST(TreeNode root) {
        TreeNode node = root;
        TreeNode newNode = new TreeNode(-1);// 新的root
        TreeNode prevNode = newNode;
        Deque<TreeNode> stack = new LinkedList<>();
        while(node != null || !stack.isEmpty()){
            if (node != null){
                stack.push(node);
                node = node.left;
            }else {
                node = stack.pop();

                prevNode.right = node;
                node.left = null;
                prevNode = node;

                node = node.right;
            }
        }
        return newNode.right;
    }

    /**
     * 951.翻转等价二叉树
     * 思路：层次遍历，不同则交换
     * @param root1 树1
     * @param root2 树2
     * @return
     */
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null){
            return true;
        }
        if (root1 == null || root2 == null){
            return false;
        }
        TreeNode node1 = root1;
        Queue<TreeNode> queue1 = new LinkedList<>();
        queue1.offer(node1);

        TreeNode node2 = root2;
        Queue<TreeNode> queue2 = new LinkedList<>();
        queue2.offer(node2);
        while (!queue1.isEmpty() && !queue2.isEmpty()){
            node1 = queue1.poll();
            node2 = queue2.poll();
            if (node1.val != node2.val){
                return false;
            }
            if(!checkStruct(node1,node2)){
                return false;
            }
            if(checkSwapNode(node1,node2)){
                changeChild(node1);
            }
            if (node1.left != null){
                queue1.offer(node1.left);
            }
            if (node1.right != null){
                queue1.offer(node1.right);
            }
            if (node2.left != null){
                queue2.offer(node2.left);
            }
            if (node2.right != null){
                queue2.offer(node2.right);
            }
        }
        if (!queue1.isEmpty() || !queue2.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 检验结构体
     * @param node1
     * @param node2
     * @return
     */
    private boolean checkStruct(TreeNode node1,TreeNode node2){
        if (node1.left == null && node1.right == null && (node2.left != null || node2.right != null)){
            return false;
        }
        if ((node1.left != null || node1.right != null) && (node2.left == null && node2.right == null)){
            return false;
        }
        return true;
    }

    /**
     * 检验是否要交换左右子树
     * @param node1
     * @param node2
     * @return
     */
    private boolean checkSwapNode(TreeNode node1,TreeNode node2){
        if (node1.left != null && node2.left != null){
            if (node1.left.val == node2.left.val){
                return false;
            }else{
                return true;
            }
        }else if((node1.left == null && node2.left != null) || (node1.left != null && node2.left == null)){
            return true;
        }else {
            return false;
        }

    }

    /**
     * 交换左右子树
     * @param node
     */
    private void changeChild(TreeNode node){
        if (node == null){
            return;
        }
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    /**
     * 958.判断是否完全二叉树
     * @param root
     * @return
     */
    public boolean isCompleteTree(TreeNode root) {
        if (root == null){
            return false;
        }
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        boolean haveMiss = false;// 中间是否空缺的标志位
        while (!queue.isEmpty()){
            node = queue.poll();
            if (node.left != null){
                if (haveMiss){
                    return false;
                }
                queue.offer(node.left);
            }else {
                haveMiss = true;
            }
            if (node.right != null){
                if (haveMiss){
                    return false;
                }
                queue.offer(node.right);
            }else {
                haveMiss = true;
            }
        }
        return true;
    }


    /**
     * 965.单值二叉树
     * @param root
     * @return
     */
    public boolean isUnivalTree(TreeNode root) {
        if(root == null){
            return true;
        }
        int sameValue = root.val;
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            if (node.val != sameValue){
                return false;
            }
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
        return true;
    }

    /**
     * 971.交换二叉树子节点适配先序遍历
     * @param root
     * @param voyage
     * @return
     */
    public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
        List<Integer> resultList = new LinkedList<>();
        if (root == null || root.val != voyage[0]){// 空的，或者根节点就不相同返回-1
            return getFalseResult(resultList);
        }
        TreeNode node = root;// 当前节点
        Deque<TreeNode> stack = new LinkedList<>();// 先序遍历的栈
        int index = 0;
        // 非递归的先序遍历
        while (node != null || !stack.isEmpty()){
            stack.push(node);
            if (index >= voyage.length){
                // 下标超了，返回失败结果集
                return getFalseResult(resultList);
            }
            // 当前节点和路径上的节点值不一样，表示失败了
            // 为什么呢，如果是根节点，开始就不一样，没法交换，直接失败
            // 如果是子节点，在下一步左孩子不空的，且对应节点值不同时候，已经交换了左右节点，然后入栈
            // 这个时候的node节点，已经是交换过的节点，再不相等，表示左右孩子都无法满足条件，直接返回失败的结果集
            if(!compareNodeAndTarget(node,voyage[index++])){
                return getFalseResult(resultList);
            }
            // 左孩子不为空，且对应值不相等，则交换左右孩子，同时记录下当前翻转的节点
            if (node.left != null && node.left.val != voyage[index]){
                changeChild(node);// 交换左右子树
                resultList.add(node.val);// 记录当前翻转节点
            }
            node = node.left;
            while (node == null && !stack.isEmpty()){
                node = stack.pop();
                node = node.right;
            }
        }
        // 如果遍历完了，voyage还没遍历完，则无法匹配，返回失败的结果
        if (index < voyage.length - 1){
            return getFalseResult(resultList);
        }
        return resultList;
    }

    /**
     * 比较节点的值和目标的值是否相等
     * @param node
     * @param targetValue
     * @return
     */
    private boolean compareNodeAndTarget(TreeNode node,int targetValue){
        if (node == null){
            return false;
        }
        return node.val == targetValue;
    }

    /**
     * 返回失败的结果集
     * @param resultList
     * @return
     */
    private List<Integer> getFalseResult(List<Integer> resultList){
        resultList.clear();
        resultList.add(-1);
        return resultList;
    }

    /**
     * 979. 在二叉树中分配硬币
     * 给定一个有 N 个结点的二叉树的根结点 root，树中的每个结点上都对应有 node.val 枚硬币，并且总共有 N 枚硬币。
     * 在一次移动中，我们可以选择两个相邻的结点，然后将一枚硬币从其中一个结点移动到另一个结点。(移动可以是从父结点到子结点，或者从子结点移动到父结点。)。
     * 返回使每个结点上只有一枚硬币所需的移动次数
     * @param root
     * @return
     */
    public int distributeCoins(TreeNode root) {
        int[] res = new int[]{0};
        getDepth979(res,root);
        return res[0];
    }

    private int getDepth979(int[] res,TreeNode root){
        if (root == null){
            return 0;
        }
        int left = getDepth979(res,root.left);
        int right = getDepth979(res,root.right);
        // 移动次数累加
        res[0] += Math.abs(left) + Math.abs(right);
        return left + right + root.val - 1;
    }

    /**
     * 988. 从叶结点开始的最小字符串
     * @param root
     * @return
     */
    public String smallestFromLeaf(TreeNode root) {
        if(root == null) return "";
        String left = smallestFromLeaf(root.left);
        String right = smallestFromLeaf(root.right);
        String prefix = String.valueOf((char)('a' + root.val));
        if("".equals(left) ^ "".equals(right)){
            return left + right + prefix;
        }
        if(left.compareTo(right) > 0) {
            return right + prefix;
        } else {
            return left + prefix;
        }
    }

    /**
     * 993.二叉树的堂兄弟节点
     * 如果二叉树的两个节点深度相同，但父节点不同，则它们是一对堂兄弟节点
     * @param root
     * @param x
     * @param y
     * @return
     */
    public boolean isCousins(TreeNode root, int x, int y) {
        if (root == null){
            return false;
        }
        TreeNode node = root;
        int x_depth = -1;// 深度初始化
        int y_depth = -1;// 深度初始化
        TreeNode xNode = null;
        TreeNode yNode = null;
        Map<TreeNode,TreeNode> map = new HashMap<>();// 当前节点对应的父节点
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int front = 0;
        int rear = queue.size();
        int depth = 0;// 深度
        while (!queue.isEmpty()){
            node = queue.poll();
            if (node.val == x){
                xNode = node;
                x_depth = depth;
            }else if(node.val == y){
                yNode = node;
                y_depth = depth;
            }
            if (x_depth != -1 && y_depth != -1){
                break;// 找到两个元素
            }
            if (node.left != null){
                map.put(node.left,node);
                queue.offer(node.left);
            }
            if (node.right != null){
                map.put(node.right,node);
                queue.offer(node.right);
            }
            front++;
            if (front == rear){
                front = 0;
                rear = queue.size();
                depth++;
            }
        }
        if (x_depth == -1 || y_depth == -1 || x_depth != y_depth){
            return false;
        }
        return map.get(xNode) != map.get(yNode);
    }

    /**
     * 998.最大二叉树II
     *
     * 新来的节点在数组的最右侧，那么这个节点肯定要往右子树添加。
     * 因为新节点加在数组的末尾，其他节点在方位上，都应该在新节点的左侧。
     * 也就是说，比新节点大的点，新节点应该是它的右孩子，比新节点小的点，它是新节点的左孩子。
     *
     * 1.如果他是最大的，直接改变root,整个树都是他的左孩子。
     * 2.往右子树迭代，只要当前node.val >= val，就需要往右子树迭代，同时记录下最近一个大于val的节点(bigNode)，便于后续节点加入，交换指针。
     * 3.如果发现node.right为空，则新节点直接加入到node.right即可。代码完成
     * 4.迭代中，如果node.val < val了，这时候新的newNode就是bigNode的右孩子(取代之前node的位置)，node.val < newNode.val，则node就是newNode的左孩子。
     *
     * @param root root
     * @param val 新节点的值
     * @return 新的二叉树
     */
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        if (root == null){
            return null;
        }
        if (root.val < val){
            TreeNode newNode = new TreeNode(val);
            newNode.left = root;// 当前值最大，小于当前值的节点都在数组左侧
            return newNode;
        }
        // 不需要使用Map来缓存了，因为不需要往父节点遍历，只需要记录一个
        TreeNode bigNode = root;// 记录最近一个比val大的node节点
        TreeNode node = root;
        // node不会是null,如果node.right是null的时候，就直接返回了，不会继续往右子树遍历了
        while (node.val >= val){
            if (node.right == null){
                node.right = new TreeNode(val);// 新的节点都在右侧
                return root;// 直接返回了
            }else {
                // 记录下最近较大的元素
                bigNode = node;
                // 只能往右侧查找，新加入的值在右边
                node = node.right;
            }
        }
        TreeNode newNode = new TreeNode(val);
        // bigNode比newNode大，newNode要取代之前node所在的位置
        // 旧的节点比新节点大，肯定在新节点的左边，所以新节点为旧节点的右孩子
        bigNode.right = newNode;
        // 原来的节点比新节点小，新节点加在右侧，那旧节点就为他的左孩子
        newNode.left = node;
        return root;
    }

    /**
     * 1008.返回与给定先序遍历 preorder 相匹配的二叉搜索树（binary search tree）的根结点。
     * @param preorder
     * @return
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        if(preorder == null || preorder.length == 0){
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        for (int i = 1 ; i< preorder.length;i++){
            TreeNode node = root;
            TreeNode prevNode = root;
            boolean isLeft = true;
            while (node != null){
                if (preorder[i] < node.val){
                    prevNode = node;
                    node = node.left;
                    isLeft = true;
                }else {
                    prevNode = node;
                    node = node.right;
                    isLeft = false;
                }
            }
            node = new TreeNode(preorder[i]);
            if (isLeft){
                prevNode.left = node;
            }else {
                prevNode.right = node;
            }
        }
        return root;
    }


    /**
     * 1022.根到叶子节点的二进制之和
     * @param root
     * @return
     */
    public int sumRootToLeaf(TreeNode root) {
        return sumRootToLeaf(root, 0);
    }

    /**
     * 1022的递归的深度优先遍历
     * @param root
     * @param val
     * @return
     */
    private int sumRootToLeaf(TreeNode root, int val) {
        if(root == null)
            return 0;
        val = (val << 1) + root.val;
        return root.left == root.right ? val : sumRootToLeaf(root.left, val)
                + sumRootToLeaf(root.right, val);
    }

    /**
     * 1026. 节点与其祖先之间的最大差值
     * 给定二叉树的根节点 root，找出存在于不同节点 A 和 B 之间的最大值 V，其中 V = |A.val - B.val|，且 A 是 B 的祖先。
     *
     * （如果 A 的任何子节点之一为 B，或者 A 的任何子节点是 B 的祖先，那么我们认为 A 是 B 的祖先）
     * @param root root
     * @return 最大差的绝对值
     */
    public int maxAncestorDiff(TreeNode root) {
        int left = maxAncestorDiff(root.left, root.val, root.val);
        int right = maxAncestorDiff(root.right, root.val, root.val);
        return Math.max(left,right);
    }

    private int maxAncestorDiff(TreeNode root, int max, int min){
        // 递归终止条件
        if(root == null){
            return 0;
        }
        if(root.val > max){
            max = root.val;
        } else if(root.val < min){
            min = root.val;
        }
        if(root.left == null && root.right == null){
            return max - min;
        }
        int left = maxAncestorDiff(root.left, max, min);
        int right = maxAncestorDiff(root.right, max, min);
        return Math.max(left,right);
    }

    /**
     * 1028. 从先序遍历还原二叉树
     * 我们从二叉树的根节点 root 开始进行深度优先搜索。
     *
     * 在遍历中的每个节点处，我们输出 D 条短划线（其中 D 是该节点的深度），然后输出该节点的值。（如果节点的深度为 D，则其直接子节点的深度为 D + 1。根节点的深度为 0）。
     *
     * 如果节点只有一个子节点，那么保证该子节点为左子节点。
     *
     * 给出遍历输出 S，还原树并返回其根节点 root
     * @param S 先序遍历路径
     * @return root
     */
    public TreeNode recoverFromPreorder(String S) {
        int firstNumIndex = S.indexOf('-');
        if(firstNumIndex == -1){
            return new TreeNode(Integer.valueOf(S));
        }
        String rootValue = S.substring(0,firstNumIndex);
        TreeNode root = new TreeNode(Integer.valueOf(rootValue));// 构造根节点

        Deque<TreeNode> stack = new LinkedList<>();// 双端队列模拟栈
        stack.push(root);// 根节点入栈
        int nodeValue = 0;
        int prevDepth = 0;// 上一次的深度
        int depth = 0;// 当前深度
        for (int i = firstNumIndex;i<S.length();i++){
            char cur = S.charAt(i);
            if ('-' == cur){
                // 表示深度的字符
                if (nodeValue > 0){
                    // 表示之前是数值
                    TreeNode node = new TreeNode(nodeValue);
                    buildNodeRelation(depth,prevDepth,stack,node);
                    stack.push(node);// 节点入栈，先序遍历
                    nodeValue = 0;// 节点值清空
                    prevDepth = depth;
                    depth = 1;
                }else {
                    depth++;
                }
            }else {
                // 这个代表字符是数字
                nodeValue = nodeValue * 10 + (cur - '0');
                if (i == S.length() - 1){// 最后一个元素了
                    // 表示之前是数值
                    TreeNode node = new TreeNode(nodeValue);
                    buildNodeRelation(depth,prevDepth,stack,node);
                }
            }
        }
        return root;
    }

    /**
     * 处理1028题中树中节点关系的方法
     * @param depth 当前深度
     * @param prevDepth 上一个节点的深度
     * @param stack 用来遍历的栈
     * @param node 当前节点
     */
    private void buildNodeRelation(int depth,int prevDepth,Deque<TreeNode> stack,TreeNode node){
        if (depth > prevDepth){// 深度更大，表示是左子树
            TreeNode prevNode = stack.peek();
            prevNode.left = node;
        }else if (depth == prevDepth){// 深度一样是右子树
            stack.pop();// 之前那个不在使用了
            TreeNode prevNode = stack.peek();
            prevNode.right = node;
        }else {// 深度小，则一直从栈中弹出，直到相等
            int diff = prevDepth - depth + 2;
            TreeNode prevNode = null;
            while (diff>0 && !stack.isEmpty()){
                prevNode = stack.pop();
                diff--;
            }
            if (prevNode != null){
                prevNode.right = node;
                stack.push(prevNode);// 这里设置回去是为了之前弹出的时候不漏掉，只有子节点都不会再使用的时候才pop
            }
        }
    }

    /**
     * 1104.二叉树寻路
     * 在一棵无限的二叉树上，每个节点都有两个子节点，树中的节点 逐行 依次按 “之” 字形进行标记。
     * 如下图所示，在奇数行（即，第一行、第三行、第五行……）中，按从左到右的顺序进行标记；
     * 而偶数行（即，第二行、第四行、第六行……）中，按从右到左的顺序进行标记。
     * 给你树上某一个节点的标号 label，请你返回从根节点到该标号为 label 节点的路径，该路径是由途经的节点标号所组成的。
     * @param label
     * @return
     */
    public List<Integer> pathInZigZagTree(int label) {
        LinkedList<Integer> result = new LinkedList<>();
        int base = 2;
        int level = log(label,base);// 获取当前层数
        int value = label;
        while (level > 0){
            result.addFirst(value);
            value = value>>1;// 上一层原本的值直接/2就行了
            // 值的转换公式是
            // 对应元素和的公式 = [2^(level+1)+2^level]-1
            value =  (base+1)*(int)Math.pow(base,level-1) - 1 - value;
            level--;
        }
        result.addFirst(1);
        return result;
    }

    /**
     * 对数的换底公式
     * @param value 求对数的值
     * @param base 对数的底
     * @return 换底之后的值
     */
    private int log(int value,int base){
        double result = Math.log(value)/Math.log(base);
        return (int) result;
    }

    /**
     * 1110.删点成林
     * 给出二叉树的根节点 root，树上每个节点都有一个不同的值。
     *
     * 如果节点值在 to_delete 中出现，我们就把该节点从树上删去，最后得到一个森林（一些不相交的树构成的集合）。
     *
     * 返回森林中的每棵树。你可以按任意顺序组织答案。
     *
     * @param root root
     * @param to_delete 要删除的节点
     * @return
     */
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> result = new ArrayList<>();// 返回结果
        Set<Integer> set = new HashSet<>();
        for (int data : to_delete){
            set.add(data);
        }
        //直接把根插入集合，因为经过后续判断后就是删除后的root
        if (!set.contains(root.val)) {
            result.add(root);
        }
        TreeNode node = root;
        TreeNode tempNode = node;// 上一个遍历到的节点
        // 后序遍历处理，简化需要考虑的事情
        Deque<TreeNode> stack = new LinkedList<>();// 双端队列模拟栈
        while (node != null || !stack.isEmpty()){
            if (node != null){// 节点不空，正常遍历入栈
                stack.push(node);
                node = node.left;
            }else{
                node = stack.peek();
                if(node.right == null || node.right == tempNode){// 节点已经访问过
                    // 当前节点要去掉的情况
                    if(set.contains(node.val)){
                        if (node.left != null && !set.contains(node.left.val)){
                            result.add(node.left);
                        }
                        if (node.right != null && !set.contains(node.right.val)){
                            result.add(node.right);
                        }
                    }
                    //若该节点是不需要删除的节点，则判断左右节点，若需要删除直接指向null
                    if (node.left != null && set.contains(node.left.val)) {
                        node.left = null;
                    }
                    if (node.right != null && set.contains(node.right.val)) {
                        node.right = null;
                    }
                    // 其实node就是stack.pop()了
                    tempNode = stack.pop();// 记录上一个已经访问的节点
                    node = null;
                }else {
                    node = node.right;
                }
            }
        }
        return result;
    }

    /**
     * 1120.子树的最大平均值
     * @param root
     * @return
     */
    public double maximumAverageSubtree(TreeNode root) {
        if (root == null){
            return 0.0;
        }
        // 可以用后序遍历来做
        return averageSubTree(root)[2];
    }

    private double[] averageSubTree(TreeNode node){
        // 0 节点总和，1节点数，2节点和左右子树最大平均值
        double[] arr = new double[3];
        if (node == null){
            return arr;
        }
        double[] left = averageSubTree(node.left);
        double[] right = averageSubTree(node.right);
        double nodeSum = node.val + left[0] + right[0];
        double nodeCount = left[1] + right[1] + 1;
        double nodeAvg = (nodeSum)/nodeCount;
        double nodeMaxAvg = Math.max(Math.max(left[2],right[2]),nodeAvg);
        arr[0] = nodeSum;
        arr[1] = nodeCount;
        arr[2] = nodeMaxAvg;
        return arr;
    }

    /**
     * 1123.最深叶节点的最近公共祖先
     *
     * 思路：最深叶子节点的公共祖先的左右子树高度相同，也就是最深叶子节点的深度一定相同。
     * 如果左右子树不等高，高度小的那个子树节点的叶子节点的深度肯定不是最深的（因为比高度大的子树深度小）。
     * 所以，最深叶子节点肯定在深度较大的子树当中，采用深度优先遍历，每次只要继续往深度更大的子树进行递归即可。
     * 如果左右子树深度相同，表示获取到了最深叶子节点的最近公共祖先
     * @param root root
     * @return 公共祖先
     */
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        if (root == null){
            return null;
        }
        int left = depth(root.left);
        int right = depth(root.right);
        if (left == right){
            return root;
        }else if(left > right){
            return lcaDeepestLeaves(root.left);
        }else {
            return lcaDeepestLeaves(root.right);
        }
    }

    /**
     * 1130. 叶值的最小代价生成树
     * 给你一个正整数数组 arr，考虑所有满足以下条件的二叉树：
     *
     * 每个节点都有 0 个或是 2 个子节点。
     * 数组 arr 中的值与树的中序遍历中每个叶节点的值一一对应。（知识回顾：如果一个节点有 0 个子节点，那么该节点为叶节点。）
     * 每个非叶节点的值等于其左子树和右子树中叶节点的最大值的乘积。
     * 在所有这样的二叉树中，返回每个非叶节点的值的最小可能总和。这个和的值是一个 32 位整数。
     *
     * 思路：DP或者单调栈
     * 先说单调栈思路
     * @param arr 树的数组
     * @return 最小代价生成树的值
     */
    public int mctFromLeafValues(int[] arr) {
        // 单调栈
        int sum = 0;
        Deque<Integer> stack = new LinkedList<>();// 构造一个单调递减的栈，遇到增加的就弹出相乘
        stack.push(arr[0]);// 第一个节点先进来，让for里面代码不那么冗余
        for (int i = 1;i<arr.length;i++){
            while (!stack.isEmpty() && arr[i] > stack.peek()){// 比最小的元素大了，要一直弹出，保证栈的单调性，直到栈的最小元素大于arr[i]
                // 弹出之后的第二小的元素，是在stack中还是新来的这个arr[i]不知道，所以要比较一下
                int min1 = stack.pop();
                int min2;
                if (!stack.isEmpty()){
                    min2 = Math.min(arr[i],stack.peek());
                }else{
                    min2 = arr[i];
                }
                sum += min1 * min2;
            }
            stack.push(arr[i]);
        }
        // 如果栈不空，剩下的叶子节点要乘起来
        while (stack.size()>1){
            sum += stack.pop()*stack.peek();
        }
        return sum;
    }

    /**
     * 1145. 二叉树着色游戏
     * 1.找到X所在节点
     * 2.统计X左右节点个数，也就获取到X父节点所拥有的节点数
     * 3.从左，右，父节点数中，找到最大的数字
     * ps：还有一种思路，采用后序遍历统计节点数，遍历过程中可以知道x所在节点，然后求解，主要思路是一致的，方向不同
     * @param root
     * @param n
     * @param x
     * @return
     */
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        if (root == null){
            return false;
        }
        // 1.找到x所在节点
        TreeNode node = findXNode(root,x);
        if(node == null){
            return true;// 都找不到X了，就随便他玩了
        }
        // 2.统计x左节点总数和x右节点总数
        int leftCount = nodeCount(node.left);
        int rightCount = nodeCount(node.right);
        // 3.从左，右，非x子树（父）的节点数中，找到最大的数字，看是否大于n/2
        int parentCount = n - leftCount - rightCount - 1;// 属于父节点的节点总数
        int winCount = n >> 1;// 获胜的数字
        int maxCount = Math.max(Math.max(leftCount,rightCount),parentCount);// 三个选择点最大的计数
        return maxCount > winCount ;
    }

    /**
     * 找到x所在的节点，层次遍历寻找
     * @param root 根节点
     * @param x 值为x的节点
     * @return 值为x的节点
     */
    private TreeNode findXNode(TreeNode root,int x){
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        boolean find_x = false;
        while (!queue.isEmpty()){
            node = queue.poll();
            if (node.val == x){
                find_x = true;
                break;
            }
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
        return find_x ? node : null;
    }

    /**
     * 节点数统计
     * @param node 当前节点
     * @return 这个节点的子树有多少个节点数
     */
    private int nodeCount(TreeNode node){
        if (node == null){
            return 0;
        }
        return 1 + nodeCount(node.left) + nodeCount(node.right);
    }

    /**
     * 1302.层数最深叶子节点之和
     * 二叉树层次遍历，每一层都累加叶子节点值，进入下一层累计值就清空sum值
     * @param root root
     * @return 层数最深叶子节点之和
     */
    public int deepestLeavesSum(TreeNode root) {
        if (root == null){
            return 0;
        }
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        int front = 0;
        int rear = queue.size();
        int sum = 0;
        while (true){
            node = queue.poll();
            front++;
            if (node.left == null && node.right == null){
                sum += node.val;// 叶子节点值累加
            }else {
                if (node.left != null){
                    queue.offer(node.left);
                }
                if (node.right != null){
                    queue.offer(node.right);
                }
            }
            if (queue.isEmpty()){
                break;// 队列空表示所有节点都遍历结束
            }
            // 进入下一层了，之前累加的节点清空
            if (front == rear){
                front = 0;
                rear = queue.size();
                sum = 0;
            }
        }
        return sum;
    }

    /**
     * 1305.两棵二叉搜索树中的所有元素
     * 转为链表，然后合并
     * @param root1 root1
     * @param root2 root2
     * @return
     */
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = inorderTraversal(root1);
        List<Integer> list2 = inorderTraversal(root2);
        return mergeTwoList(list1,list2);
    }

    /**
     * 合并两个有序数组
     * @param list1 list1
     * @param list2 list2
     * @return
     */
    private List<Integer> mergeTwoList(List<Integer> list1,List<Integer> list2){
        List<Integer> result = new ArrayList<>(list1.size() + list2.size());
        int i  = 0, j = 0;
        while (i < list1.size() && j < list2.size()){
            Integer num1 = list1.get(i);
            Integer num2 = list2.get(j);
            if (num1 <= num2){
                result.add(num1);
                i++;
            }else {
                result.add(num2);
                j++;
            }
        }
        while (i < list1.size()){
            result.add(list1.get(i++));
        }
        while (j < list2.size()){
            result.add(list2.get(j++));
        }
        return result;
    }

    /**
     * 剑指offer26.树的子结构 B是A的子结构
     * 思路.遍历A树，每个节点当做root开始确认是否是子树
     * @param A 树A
     * @param B 树B
     * @return
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null){
            return false;
        }
        TreeNode node = A;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        // 层次遍历每个节点，去比较
        while (!queue.isEmpty()){
            node = queue.poll();
            if (isSonTree(node,B)){
                return true;
            }
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
        return false;// 最后都没找到，就是false
    }

    private boolean isSonTree(TreeNode node1,TreeNode node2){
        // 都是空，相同
        if (node2 == null){
            return true;
        }
        // node1空了，不是子结构，节点值不等，不是子结构
        if (node1 == null || node1.val != node2.val){
            return false;
        }
        // 节点值相等，进入左右子树继续判断
        return isSonTree(node1.left,node2.left) && isSonTree(node1.right,node2.right);
    }

    /**
     * 面试04.03 特定深度节点链表
     * 给定一棵二叉树，设计一个算法，创建含有某一深度上所有节点的链表（比如，若一棵树的深度为 D，则会创建出 D 个链表）。返回一个包含所有深度的链表的数组。
     * @param tree tree
     * @return 链表节点
     */
    public ListNode[] listOfDepth(TreeNode tree) {
        if (tree == null){
            return new ListNode[]{};
        }
        TreeNode tNode = tree;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(tNode);
        int rear = queue.size();

        List<ListNode> res = new ArrayList<>();
        ListNode head = new ListNode(0);
        ListNode LNode = head;
        while (!queue.isEmpty()){
            tNode = queue.poll();
            rear--;// 当前层-1

            // listNode节点处理
            LNode.next = new ListNode(tNode.val);
            LNode = LNode.next;// list节点后移

            if (tNode.left != null){
                queue.offer(tNode.left);
            }
            if (tNode.right != null){
                queue.offer(tNode.right);
            }
            if (rear == 0){
                // 当前层遍历结束，全部ListNode加入
                res.add(head.next);
                // 链表节点重置到头部
                LNode = head;
                // 新一层的数量
                rear = queue.size();
            }
        }
        return res.toArray(new ListNode[res.size()]);
    }

    /**
     * 1361.验证二叉树
     * @param n 节点数
     * @param leftChild 左孩子下标
     * @param rightChild 右孩子下标
     * @return 是否为二叉树
     */
    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        Set<Integer> set = new HashSet<>(n);// 初始化，防止频繁扩容
        set.add(0);// 加入根节点下标
        for (int i = 0;i < leftChild.length ;i++){
            if (!set.contains(i)){
                return false;// 根节点要包含
            }
            if (leftChild[i] != -1){
                if (set.contains(leftChild[i])){
                    return false;
                }
                set.add(leftChild[i]);
            }
            if (rightChild[i] != -1){
                if (set.contains(rightChild[i])){
                    return false;
                }
                set.add(rightChild[i]);
            }
        }
        return true;
    }

    /**
     * 5346.二叉树中的列表
     * 给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表。
     *
     * 如果在二叉树中，存在一条一直向下的路径，且每个点的数值恰好一一对应以 head 为首的链表中每个节点的值，那么请你返回 True ，否则返回 False 。
     *
     * 一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。
     *
     * @param head ListNode
     * @param root TreeNode
     * @return 是否匹配
     */
    public boolean isSubPath(ListNode head, TreeNode root) {
        if (root == null){
            return false;
        }
        // 相等的匹配
        if (root.val == head.val){
            boolean isSub = matchSubPath(head.next,root.left) || matchSubPath(head.next,root.right);
            if (isSub){
                // 匹配上了，返回true
                return true;
            }
            // 不匹配，要进入不等的匹配
        }
        // 匹配不上，ListNode不变，进入左右子树
        return isSubPath(head,root.left) || isSubPath(head,root.right);
    }

    /**
     * root.val == head.val的匹配,考虑剪枝
     * @param head ListNode
     * @param node TreeNode
     * @return 是否匹配上
     */
    private boolean matchSubPath(ListNode head, TreeNode node){
        // 链表匹配结束，匹配上
        if (head == null){
            return true;
        }
        // 树节点空了，无法匹配
        if (node == null){
            return false;
        }
        // 不相等的，无法匹配
        if (head.val != node.val){
            return false;
        }
        // 相等继续进入左右子树，和链表的next节点匹配
        return matchSubPath(head.next,node.left) || matchSubPath(head.next,node.right);
    }

    /**
     * 5338.二叉树中的最长交错路径
     *
     * 给你一棵以 root 为根的二叉树，二叉树中的交错路径定义如下：
     *
     * 选择二叉树中 任意 节点和一个方向（左或者右）。
     * 如果前进方向为右，那么移动到当前节点的的右子节点，否则移动到它的左子节点。
     * 改变前进方向：左变右或者右变左。
     * 重复第二步和第三步，直到你在树中无法继续移动。
     * 交错路径的长度定义为：访问过的节点数目 - 1（单个节点的路径长度为 0 ）。
     *
     * 请你返回给定树中最长 交错路径 的长度。
     *
     * @param root root
     * @return 最长交错路径
     */
    public int longestZigZag(TreeNode root) {
        int[] res = {0};
        nodeZigZag(res,root,true);
        return res[0];
    }

    /**
     * 递归求左右孩子
     * @param res res
     * @param node node
     * @param isLeft 当前node是否左孩子
     * @return 经过node的锯齿最大路径
     */
    private int nodeZigZag(int[] res,TreeNode node,boolean isLeft){
        if (node == null){
            return 0;// 递归终止条件
        }
        // 左右子树锯齿情况
        int lCount = nodeZigZag(res,node.left,true);
        int rCount = nodeZigZag(res,node.right,false);
        // node为根节点的最大值
        // 左右子树取更大的
        int max = Math.max(lCount,rCount);
        // 路线的最大值
        res[0] = Math.max(res[0],max);
        // 当前节点是左孩子，走右边，否则走左边
        return 1 + (isLeft ? rCount : lCount);
    }
}
