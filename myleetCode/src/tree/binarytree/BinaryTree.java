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

//    private int robHelper(TreeNode root,int[] leftValue,int[] rightValue){
//        if (root == null){
//            return 0;
//        }
//        int[] ll = new int[]{0},lr = new int[]{0},rl = new int[]{0},rr = new int[]{0};
//        leftValue[0] = robHelper(root.left,ll,lr);
//        rightValue[0] = robHelper(root.right,rl,rr);
//        return Math.max(root.val + ll[0]+lr[0]+rl[0]+rr[0],leftValue[0]+rightValue[0]);
//    }

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
     * @param root
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
     * 655.输出二叉树构建的类，记录树节点，树下标，当前树为root的左右子树下标
     */
    private class My655Pair{
        TreeNode node;// 树节点
        int index;// 节点的下标
        int leftLimit;// 当前node为root的左界限
        int rightLimit;// 当前node为root的右界限

        My655Pair(TreeNode node, int index,int leftLimit,int rightLimit){
            this.node = node;
            this.index = index;
            this.leftLimit = leftLimit;
            this.rightLimit = rightLimit;
        }
    }

    /**
     * 655.输出二叉树
     * @param root
     * @return
     */
    public List<List<String>> printTree(TreeNode root) {
        // 返回的结果用arrayList
        List<List<String>> printList = new ArrayList<>();
        // 先获取高度
        int depth = depth(root);// 二叉树高度
        int width = (1 << depth) - 1;// 二叉树宽度

        Queue<My655Pair> queue = new LinkedList<>();// 队列遍历
        // root的index
        int rootIndex = width >> 1;
        // root入队列
        queue.offer(new My655Pair(root,rootIndex,0,width));
        // 每一层的打印列表
        List<String> everyFloor = getNewFloorList(width);

        int front = 0;// 记录层数，队头
        int tail = queue.size();// 记录层数，队尾
        // 层次遍历
        while (!queue.isEmpty()){
            My655Pair pair = queue.poll();// 出队列
            front++;// 每出一个元素，队列头部后移
            // 改变当前pair所在list下标的值
            everyFloor.set(pair.index,String.valueOf(pair.node.val));
            if (pair.node.left != null){
                // 左子树不空的处理
                int leftLimit = pair.leftLimit;
                int rightLimit = pair.index;
                int leftIndex = (leftLimit + rightLimit) >> 1;
                // 获取左孩子下标
                My655Pair leftPair = new My655Pair(pair.node.left,leftIndex,leftLimit,rightLimit);
                queue.offer(leftPair);
            }
            if (pair.node.right != null){
                // 右子树不空的处理
                int leftLimit = pair.index;
                int rightLimit = pair.rightLimit;
                int rightIndex = (leftLimit + rightLimit) >> 1;
                My655Pair rightPair = new My655Pair(pair.node.right,rightIndex,leftLimit,rightLimit);
                queue.offer(rightPair);
            }
            // 当前层遍历结束，层数+1，打印数组记录，重新初始化
            if (front == tail){
                printList.add(everyFloor);// 打印数组记录
                everyFloor = getNewFloorList(width);// 获取新的数组
                front = 0;// 进入下一层，初始化
                tail = queue.size();// 进入下一层，初始化
            }
        }
        return printList;
    }

    /**
     * 获取新的初始化打印List
     * @param width
     * @return
     */
    private List<String> getNewFloorList(int width){
        List<String> everyFloor = new ArrayList<>(width);
        for(int i = 0;i<width;i++){
            everyFloor.add("");
        }
        return everyFloor;
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
     * 814.二叉树剪枝 后续遍历删除节点
     * @param root
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
        if (N == 1){
            TreeNode node = new TreeNode(0);
            result.add(node);
            return result;
        }
        TreeNode root = new TreeNode(0);
        return null;
    }

    /**
     * 给定一个无向、连通的树。树中有 N 个标记为 0...N-1 的节点以及 N-1 条边 。
     *
     * 第 i 条边连接节点 edges[i][0] 和 edges[i][1] 。
     *
     * 返回一个表示节点 i 与其他所有节点距离之和的列表 ans。
     *
     * @param N
     * @param edges
     * @return
     */
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        return null;
    }

    /**
     * 897.递增顺序查找树
     * @param root
     * @return
     */
    public TreeNode increasingBST(TreeNode root) {
        TreeNode node = root;
        TreeNode prevNode = null;
        TreeNode newNode = null;// 新的root
        Deque<TreeNode> stack = new LinkedList<>();
        while(node != null || !stack.isEmpty()){
            stack.push(node);
            node = node.left;
            while(node == null && !stack.isEmpty()){
                node = stack.pop();
                if (newNode == null){
                    newNode = node;
                    prevNode = newNode;
                }else {
                    prevNode.right = node;
                    node.left = null;
                    prevNode = node;
                }
                node = node.right;
            }
        }
        return newNode;
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
     * @param root 根节点
     * @return
     */
    public int minCameraCover(TreeNode root) {
        if(root == null){
            return 0;
        }
        int sumValue = 0;
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        TreeNode tempNode = node;// 上一个遍历到的节点
        // 采用后续遍历
        while (node != null || !stack.isEmpty()){
            if (node != null){
                stack.push(node);
                if (isLeaf(node.left) || isLeaf(node.right)){
                    sumValue++;
                    node.val = 1;// 1表示监控器节点
                    if (node.left != null){
                        node.left.val = 2;// 2表示被监控到
                    }
                    if (node.right != null){
                        node.right.val = 2;// 2表示被监控到
                    }
                }
                node = node.left;
            }else {
                node = stack.peek();
                if (node.right == null || node.right == tempNode){
                    // 当前节点出栈，出栈判断子树和自身是否在监控中
                    if (!isControlNode(node.left) || !isControlNode(node.right)){
                        // 左右孩子有一个没有被监控了
                        node.val = 1;
                        sumValue++;// 当前节点是监控器
                    }else if (isCameraNode(node.left) || isCameraNode(node.right)){
                        // 左右孩子有一个是监控器节点
                        if (node.val == 0){
                            node.val = 2;// 当前节点被监控
                        }
                    }
                    tempNode = stack.pop();
                    node = null;
                }else {
                    // 进入右子树
                    node = node.right;
                }
            }
        }
        if (tempNode.val == 0){// 最后一个节点可能漏掉
            tempNode.val = 1;// 标记为监控节点
            sumValue++;
        }
        return sumValue;
    }

    /**
     * 是叶子节点
     * @param node 节点
     * @return 是否叶子节点
     */
    private boolean isLeaf(TreeNode node){
        return node != null && (node.left == null && node.right == null);
    }

    /**
     * 是监控器节点
     * node.val = 1表示该节点是监控器
     * @param node 节点
     * @return 节点是否监控器
     */
    private boolean isCameraNode(TreeNode node){
        return node != null && node.val == 1;
    }

    /**
     * 是被监控节点
     * node.val 是1或2都表示被监控了
     * @param node 节点
     * @return 节点是否被监控
     */
    private boolean isControlNode(TreeNode node){
        return node == null || node.val > 0;
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
        getDepth979(root);
        return res;
    }

    private int res = 0;

    private int getDepth979(TreeNode root){
        if (root == null){
            return 0;
        }
        int left = getDepth979(root.left);
        int right = getDepth979(root.right);
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
        return left > right ? left : right;
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
        return left > right ? left : right;
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
     * @param S
     * @return
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
     * @param root
     * @param to_delete
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
     * 1123.最深叶节点的最近公共祖先
     *
     * 思路：最深叶子节点的公共祖先的左右子树高度相同，也就是最深叶子节点的深度一定相同。
     * 如果左右子树不等高，高度小的那个子树节点的叶子节点的深度肯定不是最深的（因为比高度大的子树深度小）。
     * 所以，最深叶子节点肯定在深度较大的子树当中，采用深度优先遍历，每次只要继续往深度更大的子树进行递归即可。
     * 如果左右子树深度相同，表示获取到了最深叶子节点的最近公共祖先
     * @param root
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
     * @param arr
     * @return
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
     * @param root
     * @param x
     * @return
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
        if (find_x){
            return node;
        }else{
            return null;
        }
    }

    /**
     * 节点数统计
     * @param node
     * @return
     */
    private int nodeCount(TreeNode node){
        if (node == null){
            return 0;
        }
        return 1 + nodeCount(node.left) + nodeCount(node.right);
    }
}
