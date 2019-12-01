package struct.datastruct.tree.rbtree;

/**
 * 红黑树的实现
 *
 * @param <K> 泛型Key
 */
public class RBTree<K extends Comparable<K>> {

    private RBTreeNode<K> root;// 红黑树根节点

    // Red-black mechanics

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    /**
     * 红黑树节点
     *
     * @param <K>
     */
    private static final class RBTreeNode<K extends Comparable<K>> {
        K key;// 键值
        RBTreeNode<K> left;
        RBTreeNode<K> right;
        RBTreeNode<K> parent;
        boolean color = BLACK;

        RBTreeNode(K key, RBTreeNode<K> parent) {
            this.key = key;
            this.parent = parent;
        }

        public K getKey() {
            return key;
        }

        @Override
        public int hashCode() {
            return key == null ? 0 : key.hashCode();
        }

    }

    /**
     * 获取最小节点
     *
     * @return
     */
    public final RBTreeNode getFristNode() {
        RBTreeNode p = root;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
        }
        return p;
    }

    /**
     * 获取最大节点
     *
     * @return
     */
    public final RBTreeNode getLastNode() {
        RBTreeNode p = root;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
        }
        return p;
    }

    /**
     * 当前节点进行左旋
     *
     * @param p
     */
    private void rotateLeft(RBTreeNode<K> p) {
        if (p != null) {
            // 需要左旋的节点，一定有右孩子，否则不满足左旋的情况
            RBTreeNode<K> r = p.right;
            p.right = r.left;// 右子树的左节点成为当前节点的右孩子
            if (r.left != null) {// 判空，父节点指针指向
                r.left.parent = p;
            }
            r.parent = p.parent;// r的父节点指针替换
            // 原来的p是父节点左孩子还是右孩子，将r赋值到同样的位置
            if (p.parent == null) {
                root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            r.left = p;// 先指定子节点，再指定父节点，逻辑统一
            p.parent = r;
        }
    }

    /**
     * 当前节点右旋
     * @param p
     */
    private void rotateRight(RBTreeNode<K> p) {
        if (p != null) {
            RBTreeNode<K> l = p.left;
            p.left = l.right;
            if (l.right != null) {
                l.right.parent = p;
            }
            l.parent = p.parent;
            if (p.parent == null) {
                root = l;
            } else if (p.parent.left == p) {
                p.parent.left = l;
            } else {
                p.parent.right = l;
            }
            l.right = p;
            p.parent = l;
        }
    }

    public void insertNode(K key) {
        RBTreeNode t = root;
        if (t == null) {
            root = new RBTreeNode<>(key, null);
            return;
        }
    }


}
