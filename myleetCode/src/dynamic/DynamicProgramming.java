package dynamic;

import java.util.List;

/**
 * 动态规划习题
 */
public class DynamicProgramming {

    /**
     * 72.编辑距离
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        // 构建一个二维的动态规划转移方程
        int word1Length = word1.length();
        int word2Length = word2.length();
        // dp状态转移方程的定义
        // dp[i][j] word1的前i个字符->替换到word2的前j个字符，最少需要多少步
        int dp[][] = new int[word1Length+1][word2Length+1];
        // 初始化
        for (int i = 0; i <= word1Length; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i <= word2Length; i++) {
            dp[0][i] = i;
        }
        // 动态规划的递推公式
        for (int i = 1; i <= word1Length; i++) {
            for (int j = 1; j <= word2Length; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {// 两个相等
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // dp[i-1][j-1]表示替换，dp[i-1][j]表示word1的删除，dp[i][j-1]表示word1的插入
                    dp[i][j] = 1 + Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]);
                }
            }
        }
        return dp[word1Length][word2Length];
    }

    /**
     * 91.解码方法
     * 带有约束信息的斐波拉契数列问题
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        int sLength = s.length();
        int dp[] = new int[sLength + 1];
        dp[sLength - 1] = s.charAt(sLength - 1) != '0' ? 1 : 0;
        dp[sLength] = 1;
        for(int i = sLength - 2; i >= 0; i--) {
            char c1 = s.charAt(i);
            char c2 = s.charAt(i + 1);
            if(c2 == '0' && !(c1 <= '2' && c1 >= '1') || c1 == '0') {
                dp[i] = 0;
            } else if(c1 <= '1' || (c1 <= '2' && c2 <= '6'))
                dp[i] = dp[i + 1] + dp[i + 2];
            else
                dp[i] = dp[i + 1];
        }
        return dp[0];
    }

    /**
     * 120.给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int sum = Integer.MAX_VALUE;
        int[] dp = new int[triangle.size()+1];
        for (int i = 0; i < triangle.size(); i++) {
            List<Integer> row = triangle.get(i);
            for (int j = 0; j < row.size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + row.get(j);
            }
        }
        for (int i=0;i<dp.length;i++){
            if (dp[i]<sum && dp[i] > 0){
                sum = dp[i];
            }
        }
        return sum;
    }

    /**
     * 198. 打家劫舍
     * @param nums
     * @return 最优解
     */
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }else if(nums.length == 1){
            return nums[0];
        }else if(nums.length == 2){
            return Math.max(nums[0],nums[1]);
        }
        int n = nums.length;
        int sum[] = new int[n];
        sum[0] = nums[0];
        sum[1] = Math.max(nums[0],nums[1]);
        for (int i = 2;i< n;i++){
            sum[i] = Math.max(sum[i-2]+nums[i],sum[i-1]);
        }
        return sum[n - 1];
    }

    /**
     * 213. 打家劫舍 II 数组是头尾相连的，分别去除头尾计算最优解，然后比较他们的最大值
     * @param nums
     * @return
     */
    public int rob2(int[] nums){
        if (nums == null || nums.length == 0){
            return 0;
        }else if(nums.length == 1){
            return nums[0];
        }else if(nums.length == 2){
            return Math.max(nums[0],nums[1]);
        }
        // 去掉尾巴
        int a1 = nums[0];
        int b1 = Math.max(nums[0],nums[1]);
        int c1 = b1;
        for (int i = 2;i<nums.length - 1 ;i++){
            c1 = Math.max(a1+nums[i],b1);
            a1 = b1;
            b1 = c1;
        }
        // 去掉头部
        int a2 = nums[1];
        int b2 = Math.max(nums[1],nums[2]);
        int c2 = b2;
        for (int i = 3;i<nums.length;i++){
            c2 = Math.max(a2+nums[i],b2);
            a2 = b2;
            b2 = c2;
        }
        return Math.max(c1,c2);
    }

    /**
     * 朋友圈
     * @param M
     * @return
     */
    public int findCircleNum(int[][] M) {
        int count = 0;
        boolean[] visited = new boolean[M.length];
        for (int i=0;i<M.length;i++){
            if (!visited[i]){
                // 没有被访问过
                DFSFind(M,i,visited);
                count++;// 形成一个朋友圈
            }
        }
        return count;
    }

    private void DFSFind(int[][] M,int k,boolean[] visited){
        visited[k] = true;// 能进来，记录这个点被访问了
        for (int i=0;i<M.length;i++){
            if (M[i][k] == 1 && !visited[i]){// 这个点的两人互相认识，且没有被访问过
                DFSFind(M,i,visited);// 继续往下寻找
            }
        }
    }

    /**
     * 322.零钱兑换
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        return 0;
    }

    /**
     * 5114.删除树节点
     * @param nodes
     * @param parent
     * @param value
     * @return
     */
    public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
        // 从后往前找，自底向上递推
        int result = nodes;
        boolean[] isVisit = new boolean[value.length];// 记录节点是否访问过
        int[] childNum = getChildNum(value.length);// 每个节点有效的孩子个数（为0删除之后，就没有孩子了）
        // 倒数第一个节点默认访问了
        isVisit[isVisit.length - 1] = true;
        int lastParentNode = parent[parent.length - 1];// 最后一个节点的父节点先记录下来
        int countValue = value[value.length - 1];
        if (countValue == 0){
            childNum[childNum.length - 1] = 0;// 没有子树了
            result -= 1;// 第一个元素的特殊处理如果是0直接干掉
        }else {
            childNum[lastParentNode + 1]+= childNum[childNum.length - 1];
        }
        for (int i = parent.length - 2; i>=0;i--){
            if (isVisit[i]){
                continue;// 被访问过，直接过
            }
            isVisit[i] = true;// 访问过
            if (parent[i] == lastParentNode){
                // 父节点一样的记录下来
                countValue += value[i];
                childNum[lastParentNode + 1] = childNum[lastParentNode + 1] + childNum[i];// 当前节点的孩子
            }else {
                // 不相等了，说明不是同一个子树了
                // 子树值合计到父节点上来
                countValue += value[lastParentNode + 1];
                if (countValue == 0){
                    result = result - childNum[lastParentNode + 1];// 合计是0，去掉全部的子节点个数和父节点本身
                    isVisit[lastParentNode + 1] = true;// 父节点也访问过
                }
                value[lastParentNode + 1] = countValue;
            }
            // 每次都变一下吧
            lastParentNode = parent[i];
            if (value[i] == 0){
                result--;
                childNum[lastParentNode + 1]--;
            }
        }
        return result;
    }

    private int[] getChildNum(int length){
        int[] childNum = new int[length];
        for (int i = 0;i < childNum.length;i++){
            childNum[i] = 1;
        }
        return childNum;
    }

//    class Solution {
//        static class TreeNode {
//            Integer value, sum = null, num = null;
//            List<TreeNode> children;
//
//            public TreeNode() {
//                this.children = new ArrayList<TreeNode>();
//            }
//
//            public TreeNode(int value) {
//                this.value = value;
//                this.children = new ArrayList<TreeNode>();
//            }
//
//            public void addTreeNode(TreeNode treeNode) {
//                this.children.add(treeNode);
//            }
//        }
//
//        public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
//            TreeNode root = null;
//            TreeNode[] treeArr = new TreeNode[nodes];
//
//            // 创建节点对象
//            for(int i = 0; i < nodes; i++) {
//                treeArr[i] = new TreeNode(value[i]);
//                if (parent[i] == -1) root = treeArr[i];
//            }
//            // 构造节点关系
//            for(int i = 0; i < nodes; i++) {
//                if (parent[i] != -1) {
//                    treeArr[parent[i]].addTreeNode(treeArr[i]);
//                }
//            }
//            // 计算每个子树的节点数量和总和值
//            computeTree(root);
//
//            if (root.sum == 0) return 0;
//            return nodes - removeZeroTreeNode(root);
//        }
//
//        // 递归计算该及其孩子节点的“节点总数”和“值的加和”
//        public void computeTree(TreeNode node) {
//            node.sum = node.value;
//            node.num = 1;
//
//            for(TreeNode child : node.children) {
//                computeTree(child);
//                node.sum += child.sum;
//                node.num += child.num;
//            }
//        }
//
//        // 计算该节点及其孩子节点会被移除的节点总数
//        public int removeZeroTreeNode(TreeNode node) {
//            int removeNum = 0;
//
//            for(int i = 0; i < node.children.size(); i++) {
//                TreeNode child = node.children.get(i);
//                if (child.sum == 0) {
//                    removeNum += child.num;
//                } else {
//                    removeNum += removeZeroTreeNode(child);
//                }
//            }
//            return removeNum;
//        }
//    }

}
