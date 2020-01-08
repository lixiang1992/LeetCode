package struct.datastruct.tree.binarytree;


import java.util.*;

/**
 * 1257.最小公共区域
 * 给你一些区域列表 regions ，每个列表的第一个区域都包含这个列表内所有其他区域。
 *
 * 很自然地，如果区域 X 包含区域 Y ，那么区域 X  比区域 Y 大。
 *
 * 给定两个区域 region1 和 region2 ，找到同时包含这两个区域的 最小 区域。
 *
 * 如果区域列表中 r1 包含 r2 和 r3 ，那么数据保证 r2 不会包含 r3 。
 *
 * 数据同样保证最小公共区域一定存在。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/smallest-common-region
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SmallestRegion_1257 {

    private RegionTreeNode root;
    private Map<String,RegionTreeNode> m_cache = new HashMap<>();

    /**
     * 区域树节点类
     */
    private static class RegionTreeNode {

        String key;// 关键字
        RegionTreeNode parent;// 父节点

        RegionTreeNode(String key) {
            this(key,null);
        }

        RegionTreeNode(String key, RegionTreeNode parent) {
            this.key = key;
            this.parent = parent;
        }

    }

    public String findSmallestRegion(List<List<String>> regions, String region1, String region2) {
        buildRegionTree(regions);
        RegionTreeNode node1 = m_cache.get(region1);
        RegionTreeNode node2 = m_cache.get(region2);
        Set<String> set = new HashSet<>();
        while (node1 != null){
            set.add(node1.key);
            node1 = node1.parent;
        }
        while (node2 != null){
            if (set.contains(node2.key)){
                return node2.key;
            }
            node2 = node2.parent;
        }
        return null;
    }

    /**
     * 构建树结构
     * @param regions
     */
    private void buildRegionTree(List<List<String>> regions){
        for (List<String> list : regions){
            // 获取父节点的key
            String parentKey = list.get(0);
            if (root == null) {
                root = new RegionTreeNode(parentKey);
                m_cache.put(parentKey,root);
            }
            // 第一个节点是父节点
            RegionTreeNode parentNode = m_cache.get(parentKey);
            for (int i = 1;i<list.size();i++){
                String key = list.get(i);
                RegionTreeNode node = new RegionTreeNode(key);
                m_cache.put(key,node);// 加入缓存
                node.parent = parentNode;
            }
        }
    }
}
