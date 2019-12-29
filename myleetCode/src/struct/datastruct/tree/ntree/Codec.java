package struct.datastruct.tree.ntree;

import struct.pub.tree.TreeNode;

import java.util.ArrayList;

/**
 * 431. 将 N 叉树编码为二叉树
 *
 * 设计一个算法，可以将 N 叉树编码为二叉树，并能将该二叉树解码为原 N 叉树。一个 N 叉树是指每个节点都有不超过 N 个孩子节点的有根树。类似地，一个二叉树是指每个节点都有不超过 2 个孩子节点的有根树。你的编码 / 解码的算法的实现没有限制，你只需要保证一个 N 叉树可以编码为二叉树且该二叉树可以解码回原始 N 叉树即可。
 *
 * 核心思想：树转森林，左孩子是孩子，右孩子是兄弟
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/encode-n-ary-tree-to-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Codec {

    // Encodes an n-ary tree to a binary tree.
    // 思路：N叉树孩子节点，第一个孩子，作为二叉树的左节点，其他孩子是兄弟节点，作为二叉树左孩子的右节点，一直往右孩子迭代
    public TreeNode encode(Node root) {
        if (root == null){
            return null;
        }
        // 二叉树的root
        TreeNode treeRoot = new TreeNode(root.val);
        // N叉转二叉
        encodeToBinaryTree(treeRoot,root);
        return treeRoot;
    }

    /**
     * N叉树转为二叉树
     * node.children的第一个节点是treeNode.left，其余节点是treeNode.left为root的right子树上
     * @param treeNode 二叉树节点
     * @param node  N叉树节点
     */
    private void encodeToBinaryTree(TreeNode treeNode, Node node){
        if (node.children == null){
            return;
        }
        int i = 0;
        // 这里为什么这样写，因为我不知道这个list是arrayList还是linkedList，用forEach更快一些
        for (Node child : node.children) {
            if (i==0){
                // 第一个孩子设置为treeNode的左孩子
                treeNode.left = new TreeNode(child.val);
                treeNode = treeNode.left;// 往左子树递推
            }else {
                // 其余孩子设置为treeNode.left的右孩子，转换为森林的思想
                treeNode.right = new TreeNode(child.val);
                treeNode = treeNode.right;// 往右子树递推
            }
            // 递归调用，思想其实深度优先遍历
            encodeToBinaryTree(treeNode,child);
            i++;
        }
    }

    // Decodes your binary tree to an n-ary tree.
    // 思路：节点的左孩子作为当前节点孩子的第一个节点，右孩子一直迭代下去到空，全部加入当前节点的孩子List中
    public Node decode(TreeNode root) {
        if (root == null){
            return null;
        }
        // N叉树的根节点
        Node nodeRoot = new Node(root.val,new ArrayList<>());
        // 二叉转N叉
        decodeToNTree(root.left,nodeRoot);
        return nodeRoot;
    }

    /**
     * 二叉树转为N叉树
     * @param treeNode 二叉树节点
     * @param node N叉树节点
     */
    private void decodeToNTree(TreeNode treeNode,Node node){
        //treeNode不空，把treeNode的右孩子全部转换为兄弟节点
        while (treeNode != null){
            // 当前treeNode加入到node.child中
            Node newNode = new Node(treeNode.val,new ArrayList<>());
            node.children.add(newNode);
            // 左子树继续往下走，因为treeNode.left应该在newNode.children中
            decodeToNTree(treeNode.left,newNode);
            // 右子树是newNode的兄弟节点，也就是要设置到node.children中
            treeNode = treeNode.right;
        }
    }
}
