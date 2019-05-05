package tree.ntree;

import java.util.List;

/**
 *  N叉树节点，结构类
 */
public class Node {
    public int val;
    public List<Node> children;

    public Node() {
    }

    public Node(int _val){
        this(_val,null);
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
}
