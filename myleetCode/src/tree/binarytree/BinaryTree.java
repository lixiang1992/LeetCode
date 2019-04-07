package tree.binarytree;

import struct.pub.tree.TreeLinkNode;
import struct.pub.tree.TreeNode;

import java.util.*;

public class BinaryTree {

    /**
     * 94.中序遍历二叉树
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> resultList = new LinkedList<>();
        if (root == null) {
            return resultList;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            stack.push(node);
            node = node.left;
            while (node == null && !stack.isEmpty()) {
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
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> resultList = new LinkedList<>();
        if (root == null) {
            return resultList;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            resultList.add(node.val);
            stack.push(node);
            node = node.left;
            while (node == null && !stack.isEmpty()) {
                node = stack.pop();
                node = node.right;
            }
        }
        return resultList;
    }

    /**
     * 145.二叉树后序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> resultList = new LinkedList<>();
        TreeNode node = root;
        TreeNode tempNode = node;
        Stack<TreeNode> stack = new Stack<>();
        while (node != null) {
            while (node.left != null) {
                stack.push(node);
                node = node.left;
            }
            while (node != null && (node.right == null || node.right == tempNode)) {
                resultList.add(node.val);
                tempNode = node;// 上一个已经输出的节点
                if (stack.isEmpty()) {
                    return resultList;
                }
                node = stack.pop();
            }
            stack.push(node);
            node = node.right;
        }
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
     * 102.层次遍历分层显示
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
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
                level.add(everyLevel);
                everyLevel = new LinkedList<>();
                index = 0;
                rear = queue.size();
            }
        }
        return level;
    }

    /**
     * 倒序层次遍历分层显示
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
     * 103.锯齿形层次遍历
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
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
        boolean flag = true;// true表示正向，false表示反向
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (flag)
                everyLevel.add(node.val);
            else
                ((LinkedList<Integer>) everyLevel).addFirst(node.val);
            index++;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (index == rear) {
                level.add(everyLevel);
                everyLevel = new LinkedList<>();
                index = 0;
                rear = queue.size();
                flag = !flag;
            }
        }
        return level;
    }

    /**
     * 105.前序和中序构造二叉树
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeHelper(preorder,inorder,0,preorder.length - 1,0,inorder.length - 1);
    }

    private TreeNode buildTreeHelper(int[] preorder,int[] inorder,int preStart,int preEnd,int inStart,int inEnd) {
        if (preStart >= preorder.length){
            return null;
        }
        // 先序遍历的首元素为新节点
        TreeNode node = new TreeNode(preorder[preStart]);
        if (preStart == preEnd){// 到了最后一个元素，不需要继续往下走了，返回
            return node;
        }
        for (int index = inEnd; index >= inStart; index--) {
            // 先序遍历的首元素在中序遍历中找到了对应下标，
            // 中序遍历下标往左的都是左子树，往右的都是右子树
            if (preorder[preStart] == inorder[index]) {
                int length = index - inStart;// 左节点的长度
                node.left = buildTreeHelper(preorder, inorder, preStart + 1, preStart + length, inStart, index - 1);
                node.right = buildTreeHelper(preorder, inorder, preStart + length + 1, preEnd, index + 1, inEnd);
                return node;
            }
        }
        return null;
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
    private int max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        if (root == null) return 0;
        maxPathHelper(root);
        return max;
    }

    private int maxPathHelper(TreeNode root){
        if (root == null) return 0;
        int leftMaxPathSum = maxPathHelper(root.left);
        int rightMaxPathSum = maxPathHelper(root.right);
        leftMaxPathSum = Math.max(leftMaxPathSum,0);
        rightMaxPathSum = Math.max(rightMaxPathSum,0);
        max = Math.max(max , root.val+leftMaxPathSum+rightMaxPathSum);
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
     * 226.翻转二叉树
     * 层次遍历，交换每个左右子树
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if(root == null){
            return null;
        }
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while(!queue.isEmpty()){
            node = queue.poll();
            TreeNode tempNode = node.left;
            node.left = node.right;
            node.right = tempNode;
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
        }
        return root;
    }

    /**
     * 使用O(1)的空间复杂度层次遍历二叉树,
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
     * 使用O(1)的空间复杂度层次遍历二叉树,自己写的通用算法
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
        while (node != null) {
            // 左子树入栈
            while (node.left != null) {
                tempSum += node.val;
                stack.push(node);
                node = node.left;
            }
            // 右子树处理
            while (node != null && (node.right == null || node.right == tempNode)) {
                tempSum += node.val;
                if (node.left == null && node.right == null) {
                    if (tempSum == sum) {
                        return true;
                    }
                }
                tempSum -= node.val;
                tempNode = node;
                if (stack.isEmpty()) {
                    return false;
                }
                node = stack.pop();
                tempSum -= node.val;
            }
            // 右子树入栈
            tempSum += node.val;
            stack.push(node);
            node = node.right;
        }
        return false;
    }

    /**
     * 待优化
     *
     * @param root
     * @param sum
     * @return
     */
    public List<List<Integer>> PathSum(TreeNode root, int sum) {
        // 后序遍历二叉树，找到对应路径
        TreeNode node = root;
        Stack<TreeNode> stack = new Stack<>();
        LinkedList<Integer> everyResult = new LinkedList<>();
        List<List<Integer>> resultList = new LinkedList<>();
        int tempSum = 0;
        TreeNode tempNode = root;// 表示上一个已经被访问的孩子
        while (node != null) {
            // 左子树入栈
            while (node.left != null) {
                tempSum += node.val;
                stack.push(node);
                everyResult.add(node.val);
                node = node.left;
            }
            // 右子树处理
            while (node != null && (node.right == null || node.right == tempNode)) {
                everyResult.add(node.val);
                tempSum += node.val;
                if (node.left == null && node.right == null) {
                    if (tempSum == sum) {
                        resultList.add(new ArrayList<>(everyResult));
                    }
                }
                everyResult.removeLast();
                tempSum -= node.val;
                tempNode = node;
                if (stack.isEmpty()) {
                    return resultList;
                }
                node = stack.pop();
                everyResult.removeLast();
                tempSum -= node.val;
            }
            // 右子树入栈
            tempSum += node.val;
            everyResult.add(node.val);
            stack.push(node);
            node = node.right;
        }
        return resultList;
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
        //return leftNode == null ? rightNode : rightNode == null ? leftNode : root
        if(leftNode != null && rightNode != null){
            return root;// p q 分别位于左右子树的情况
        }
        return leftNode == null ? rightNode : leftNode;
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
     * 337.打家劫舍 III
     * @param root 根节点
     * @return 最优解
     */
    public int rob(TreeNode root) {
        int[] leftValue = new int[]{0},rightValue = new int[]{0};
        return robHelper(root,leftValue,rightValue);
    }

    private int robHelper(TreeNode root,int[] leftValue,int[] rightValue){
        if (root == null){
            return 0;
        }
        int[] ll = new int[]{0},lr = new int[]{0},rl = new int[]{0},rr = new int[]{0};
        leftValue[0] = robHelper(root.left,ll,lr);
        rightValue[0] = robHelper(root.right,rl,rr);
        return Math.max(root.val + ll[0]+lr[0]+rl[0]+rr[0],leftValue[0]+rightValue[0]);
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
        int left = 1 + depth(root.left);
        int right = 1 + depth(root.right);
        return Math.max(left,right);
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
     * 968.监控二叉树
     * 给定一个二叉树，我们在树的节点上安装摄像头。
     * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
     * 计算监控树的所有节点所需的最小摄像头数量。
     * @param root
     * @return
     */
    public int minCameraCover(TreeNode root) {
        if(root == null){
            return 0;
        }
        return 0;
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
        while (node != null || !stack.isEmpty()){
            stack.push(node);
            if (index >= voyage.length){
                return getFalseResult(resultList);
            }
            if(!compareNodeAndTarget(node,voyage[index++])){
                return getFalseResult(resultList);
            }
            // 左孩子不为空，且对应值不同则交换
            if (node.left != null && node.left.val != voyage[index]){
                changeChild(node);// 交换左右子树
                resultList.add(node.val);
            }
            node = node.left;
            while (node == null && !stack.isEmpty()){
                node = stack.pop();
                node = node.right;
            }
        }
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
        getDepth(root);
        return res;
    }

    private int res = 0;

    private int getDepth(TreeNode root){
        if (root == null){
            return 0;
        }
        int left = getDepth(root.left);
        int right = getDepth(root.right);
        res = Math.abs(left) + Math.abs(right);
        return left + right + root.val - 1;
    }

    /**
     * 坐标
     */
    private static class Coordinate implements Comparable<Coordinate>{

        private int x;
        private int y;
        private int value;

        Coordinate(int x,int y,int value){
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public int compareTo(Coordinate o) {
            if (this.x == o.x && this.y == o.y) {
                return this.value - o.value;
            }else if(this.x == o.x){
                return o.y - this.y;// 纵坐标大的在前面
            }
            return this.x - o.x;
        }
    }

    private static class TreeNode_987 implements Comparable<TreeNode_987>{
        private TreeNode node;
        private int x;
        private int y;

        public TreeNode_987(TreeNode node, int x, int y) {
            this.node = node;
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(TreeNode_987 o) {
            if (this.x == o.x && this.y == o.y) {
                return this.node.val - o.node.val;
            }else if(this.x == o.x){
                return o.y - this.y;// 纵坐标大的在前面
            }
            return this.x - o.x;
        }
    }

    /**
     * 987.二叉树的垂序遍历
     * @param root root
     * @return 遍历结果集
     */
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        if (root == null){
            return new ArrayList<>();
        }
        List<List<Integer>> result = new ArrayList<>();
        // key 横坐标
        Map<Integer,Set<Coordinate>> orderNumValueMap = new TreeMap<>();
        orderNumValueMap.put(0,new TreeSet<>());
        Coordinate coordinate = new Coordinate(0,0,root.val);
        orderNumValueMap.get(0).add(coordinate);
        Map<TreeNode,Coordinate> nodeNumMap = new HashMap<>();
        nodeNumMap.put(root,coordinate);
        TreeNode node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            coordinate = nodeNumMap.get(node);
            if (node.left != null){
                queue.offer(node.left);
                int left_x = coordinate.x - 1;
                int left_y = coordinate.y - 1;
                Set<Coordinate> oneResult = orderNumValueMap.computeIfAbsent(left_x, k -> new TreeSet<>());
                Coordinate leftCoordinate = new Coordinate(left_x,left_y,node.left.val);
                oneResult.add(leftCoordinate);
                nodeNumMap.put(node.left,leftCoordinate);
            }
            if (node.right != null){
                queue.offer(node.right);
                int right_x = coordinate.x + 1;
                int right_y = coordinate.y - 1;
                Set<Coordinate> oneResult = orderNumValueMap.computeIfAbsent(right_x, k -> new TreeSet<>());
                Coordinate rightCoordinate = new Coordinate(right_x,right_y,node.right.val);
                oneResult.add(rightCoordinate);
                nodeNumMap.put(node.right,rightCoordinate);
            }
        }
        for (Map.Entry<Integer,Set<Coordinate>> entry : orderNumValueMap.entrySet()){
            List<Integer> oneList = new ArrayList<>();
            for (Coordinate c: entry.getValue()){
                oneList.add(c.value);
            }
            result.add(oneList);
        }
        return result;
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
     * @param root
     * @param val
     * @return
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
        Map<TreeNode,TreeNode> parentMap = new HashMap<>();
        TreeNode node = root;
        while (node != null && node.val >= val){
            if (node.right == null){
                node.right = new TreeNode(val);// 新的节点都在右侧
                return root;
            }else {
                // 只能往右侧查找，新加入的值在右边
                parentMap.put(node.right,node);
                node = node.right;
            }
        }
        TreeNode newNode = new TreeNode(val);
        TreeNode parentNode = parentMap.get(node);
        while (newNode.val > parentNode.val){
            node = parentNode;
            parentNode = parentMap.get(node);
        }
        // 旧的节点比新节点大，肯定在新节点的左边，所以新节点为旧节点的右孩子
        parentNode.right = newNode;
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
}
