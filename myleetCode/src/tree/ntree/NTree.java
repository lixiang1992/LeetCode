package tree.ntree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * N叉树类
 */
public class NTree {

    /**
     * 429. N叉树的层序遍历
     *
     * @param root 根节点
     * @return 层次遍历次序
     */
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        List<Integer> oneLevel = new LinkedList<>();

        int front = 0;
        int rear = queue.size();
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            oneLevel.add(node.val);// 遍历节点记录
            front++;// 队头后移
            if (node.children != null && node.children.size() > 0) {
                queue.addAll(node.children);
            }
            if (front == rear) {// 当前层遍历结束
                front = 0;
                rear = queue.size();
                result.add(oneLevel);
                oneLevel = new LinkedList<>();
            }
        }
        return result;
    }

    /**
     * 559.N叉树的最大深度
     *
     * @param root root
     * @return depth
     */
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        int front = 0;
        int rear = queue.size();
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            front++;// 队头后移
            if (node.children != null && node.children.size() > 0) {
                queue.addAll(node.children);
            }
            if (front == rear) {
                front = 0;
                rear = queue.size();
                depth++;
            }
        }
        return depth;
    }

    /**
     * 589.N叉树的前序遍历
     *
     * @param root root
     * @return 前序遍历结果集
     */
    public List<Integer> preorder(Node root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        Deque<Node> stack = new LinkedList<>();
        Node node = root;
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.pop();
            result.add(node.val);
            List<Node> children = node.children;
            if (children != null) {
                for (int i = children.size() - 1; i >= 0; i--) {
                    stack.push(children.get(i));
                }
            }
        }
        return result;
    }

    /**
     * 590.N叉树的后续遍历
     *
     * @param root root
     * @return 后续遍历结果集
     */
    public List<Integer> postorder(Node root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        Deque<Node> stack = new LinkedList<>();
        Node node = root;
        Node lastNode = node;// 记录扫描到的最后一个节点，
        stack.push(node);
        while (!stack.isEmpty()){
            node = stack.peek();
            List<Node> children = node.children;
            if (children != null && children.size() > 0 && children.get(children.size() - 1) != lastNode) {
                // 这里判断最后一个节点是否和上一次扫描的节点一致，如果一致表示扫描过了，需要弹出，否则会重复扫描
                for (int i = children.size() - 1; i >= 0; i--) {
                    stack.push(children.get(i));
                }
            } else {//如果所有孩子都被扫描完了，则加入当前节点
                lastNode = node;
                result.add(stack.pop().val);
            }
        }
        return result;
    }
}
