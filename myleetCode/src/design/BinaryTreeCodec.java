package design;

import struct.pub.tree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 297. 二叉树的序列化与反序列化
 * 序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
 * <p>
 * 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
 */
public class BinaryTreeCodec {

    private final String EMPTY_VALUE = "#";
    private final String DEPART_VALUE = ",";
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root == null){
            sb.append(EMPTY_VALUE);
            return sb.toString();
        }
        TreeNode node = root;
        sb.append(node.val).append(DEPART_VALUE);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            if (node.left != null){
                queue.offer(node.left);
                sb.append(node.left.val).append(DEPART_VALUE);
            }else{
                sb.append(EMPTY_VALUE).append(DEPART_VALUE);
            }
            if(node.right != null){
                queue.offer(node.right);
                sb.append(node.right.val).append(DEPART_VALUE);
            }else {
                sb.append(EMPTY_VALUE).append(DEPART_VALUE);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] nodeArr = data.split(DEPART_VALUE);
        String rootValue = nodeArr[0];
        if (EMPTY_VALUE.equals(rootValue)){
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(rootValue));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        for (int i = 1;i<nodeArr.length - 1;i=i+2){
            TreeNode node = queue.poll();
            String leftValue = nodeArr[i];
            String rightValue = nodeArr[i+1];
            if (EMPTY_VALUE.equals(leftValue)){
                node.left = null;
            }else {
                node.left = new TreeNode(Integer.valueOf(leftValue));
                queue.offer(node.left);
            }
            if(EMPTY_VALUE.equals(rightValue)){
                node.right = null;
            }else {
                node.right = new TreeNode(Integer.valueOf(rightValue));
                queue.offer(node.right);
            }
        }
        return root;
    }
}
