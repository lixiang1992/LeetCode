package struct.datastruct.tree.binarytree;

import struct.pub.tree.TreeNode;

import java.util.*;

/**
 * 987.二叉树的垂序遍历
 */
public class VerticalTraversal_987 {

    /**
     * 坐标结构体
     */
    private static class Coordinate implements Comparable<Coordinate> {
        int x;// 横坐标
        int y;// 纵坐标
        int val;

        Coordinate(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }

        @Override
        public int compareTo(Coordinate o) {
            if (this.x == o.x && this.y == o.y){
                return this.val - o.val;// 坐标相同，值小的在前
            }else if (this.x == o.x) {
                return this.y - o.y;// 横坐标相同，纵坐标小的在前
            }
            // 横坐标小的在前
            return this.x - o.x;
        }
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null){
            return res;
        }

        Coordinate coordinate = new Coordinate(0,0,root.val);
        Map<TreeNode,Coordinate> nodeCoordinateMap = new HashMap<>();// 节点坐标对应映射
        nodeCoordinateMap.put(root,coordinate);// 根节点->坐标初始化

        // 横坐标为key，value是相同横坐标上所有的坐标集合(TreeMap)->节点出现的次数，有序来Tree，肯定没错
        Map<Integer,Map<Coordinate,Integer>> orderTreeNodeMap = new TreeMap<>();
        // ---相同横坐标上所有的坐标集合
        orderTreeNodeMap.put(0,new TreeMap<>());
        orderTreeNodeMap.get(0).put(coordinate,1);
        // 深度优先遍历(层次遍历也可以，不过层次遍历写太多了，这里也改一下)
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.pop();
            // 当前节点的坐标
            Coordinate parentCoordinate = nodeCoordinateMap.get(node);
            // 深度优先，先右后左
            if (node.right != null){
                // 横坐标node.x + 1, 纵坐标 node.y + 1
                int x = parentCoordinate.x + 1;
                int y = parentCoordinate.y + 1;
                // 新坐标生成，加入节点坐标映射
                coordinate = new Coordinate(x,y,node.right.val);
                nodeCoordinateMap.put(node.right,coordinate);

                // 相同横坐标上所有的坐标集合
                Map<Coordinate,Integer> coordinateCount = orderTreeNodeMap.computeIfAbsent(x, k -> new TreeMap<>());
                coordinateCount.put(coordinate,coordinateCount.getOrDefault(coordinate,0) + 1);
//                orderTreeNodeMap.get(x).add(coordinate);

                stack.push(node.right);
            }
            if (node.left != null){
                // 横坐标node.x - 1, 纵坐标 node.y + 1
                int x = parentCoordinate.x - 1;
                int y = parentCoordinate.y + 1;
                // 新坐标生成，加入节点坐标映射
                coordinate = new Coordinate(x,y,node.left.val);
                nodeCoordinateMap.put(node.left,coordinate);

                // 相同横坐标上所有的坐标集合
                Map<Coordinate,Integer> coordinateCount = orderTreeNodeMap.computeIfAbsent(x, k -> new TreeMap<>());
                coordinateCount.put(coordinate,coordinateCount.getOrDefault(coordinate,0) + 1);
//                orderTreeNodeMap.get(x).add(coordinate);

                stack.push(node.left);
            }
        }
        // 集合遍历，集合是有序的
        for (Map.Entry<Integer,Map<Coordinate,Integer>> entry : orderTreeNodeMap.entrySet()){
            List<Integer> temp = new ArrayList<>();
            for (Map.Entry<Coordinate,Integer> entry1 : entry.getValue().entrySet()){
                int count = entry1.getValue();
                while (count > 0){
                    count--;
                    temp.add(entry1.getKey().val);
                }
            }
            res.add(temp);
        }
        return res;
    }
}
