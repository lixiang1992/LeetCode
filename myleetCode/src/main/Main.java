package main;

import struct.datastruct.tree.binarysearchtree.BinarySearchTree;
import struct.pub.tree.TreeNode;

public class Main {

    public static void main(String[] args) {

//        int nodes = 7;
//        int[] parent = new int[]{-1,0,0,1,2,2,2};
//        int[] value = new int[]{1,-2,4,0,-2,-1,-1};
//        DynamicProgramming programming = new DynamicProgramming();
//        programming.deleteTreeNodes(nodes,parent,value);
        BinarySearchTree tree = new BinarySearchTree();
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(20);

        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(9);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(25);
        TreeNode node1 = root.left.right;
        node1.left = new TreeNode(8);
        node1.left.left = new TreeNode(6);
        node1.left.left.right = new TreeNode(7);
        tree.splitBST(root,6);
    }
}
