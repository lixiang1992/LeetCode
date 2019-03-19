package tree.ntree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * N叉树类
 */
public class NTree {
    // N叉树节点，内部类
    private class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val,List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    /**
     * 429. N叉树的层序遍历
     * @param root 根节点
     * @return 层次遍历次序
     */
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new LinkedList<>();
        if (root == null){
            return result;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        List<Integer> oneLevel = new LinkedList<>();

        int front = 0;
        int rear = queue.size();
        while (!queue.isEmpty()){
            Node node = queue.poll();
            oneLevel.add(node.val);// 遍历节点记录
            front++;// 队头后移
            if (node.children != null && node.children.size() > 0){
                for (Node child:node.children){
                    queue.offer(child);
                }
            }
            if (front == rear){// 当前层遍历结束
                front = 0;
                rear = queue.size();
                result.add(oneLevel);
                oneLevel = new LinkedList<>();
            }
        }
        return result;
    }
}
