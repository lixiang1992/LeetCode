package struct.datastruct.tree.binarytree;

import java.util.HashMap;
import java.util.Map;

/**
 * 666. 路径和 IV
 *
 * 对于一棵深度小于 5 的树，可以用一组三位十进制整数来表示。
 *
 * 对于每个整数：
 *
 * 百位上的数字表示这个节点的深度 D，1 <= D <= 4。
 * 十位上的数字表示这个节点在当前层所在的位置 P， 1 <= P <= 8。位置编号与一棵满二叉树的位置编号相同。
 * 个位上的数字表示这个节点的权值 V，0 <= V <= 9。
 * 给定一个包含三位整数的升序数组，表示一棵深度小于 5 的二叉树，请你返回从根到所有叶子结点的路径之和。
 *
 */
public class PathSum_666 {

    // 优化思路：从后往前遍历，把全部的路径和（权值）累加到根节点，这样就只需要遍历一次，
    // 但是需要注意的是，如果一个节点 每 多出现 1 个孩子，那么这个节点 所有的父节点 的值就要 全部 累加 1 次，
    // 换句话说，就是一个节点如果有 2 个孩子，其本身的值就要加 2 次，其 兄弟 节点如果只有 1 个孩子，那么当前节点的 父节点 的值就要加 3 次
    // （自己2个孩子，兄弟1个孩子，所以父亲节点要加3次），如果 兄弟节点 是 2 个孩子，那么当前节点的 父节点 的值就要加 4 次。
    public int pathSum(int[] nums) {
        // key : level * 10 + position -> value : 包含路径上所有元素的和
        Map<Integer,Integer> indexValueMap = new HashMap<>();
        indexValueMap.put(getNumKey(nums[0]),getNumValue(nums[0]));
        for (int i = 1;i< nums.length;i++){
            int num = nums[i];
            int level = getNumLevel(num);// 获取层
            int position = getNumPosition(num);// 获取位置
            int value = getNumValue(num);// 获取值
            int parentKey = (level - 1) * 10 + ((position + 1) >> 1);// 父节点的key
            int parentValue = indexValueMap.get(parentKey);// 父节点的值
            // 孩子节点的值加入
            indexValueMap.put(getNumKey(num), parentValue + value);
        }

        int sumValue = 0;
        for (Map.Entry<Integer,Integer> entry : indexValueMap.entrySet()){
            Integer key = entry.getKey();
            int childLevel = (key / 10 + 1) * 10;// 孩子的层
            int childPosition = (key % 10) << 1;// 孩子的位置
            int childKey = childLevel + childPosition;// 孩子的key
            // 左右孩子都不存在，就是表示是叶子节点
            if (!indexValueMap.containsKey(childKey - 1) && !indexValueMap.containsKey(childKey)){
                // 叶子节点值加入
                sumValue += entry.getValue();
            }
        }
        return sumValue;
    }

    /**
     * 获取数字的key
     * @param num num
     * @return key
     */
    private int getNumKey(int num){
        return num / 10;
    }

    /**
     * 获取数字的层
     * @param num num
     * @return level
     */
    private int getNumLevel(int num){
        return num / 100;
    }

    /**
     * 获取数字的位置
     * @param num num
     * @return position
     */
    private int getNumPosition(int num){
        return num / 10 % 10;
    }

    /**
     * 获取数字的值
     * @param num num
     * @return value
     */
    private int getNumValue(int num){
        return num % 10;
    }
}
