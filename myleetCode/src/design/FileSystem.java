package design;

import java.util.HashMap;
import java.util.Map;

/**
 * 1166.设计文件系统
 *
 * 你需要设计一个能提供下面两个函数的文件系统：
 *
 * create(path, value): 创建一个新的路径，并尽可能将值 value 与路径 path 关联，然后返回 True。如果路径已经存在或者路径的父路径不存在，则返回 False。
 * get(path): 返回与路径关联的值。如果路径不存在，则返回 -1。
 * “路径” 是由一个或多个符合下述格式的字符串连接起来形成的：在 / 后跟着一个或多个小写英文字母。
 *
 * 例如 /leetcode 和 /leetcode/problems 都是有效的路径，但空字符串和 / 不是有效的路径。
 *
 * 先用哈希表的思路，通用的可以考虑trieTree
 */
public class FileSystem {

    private Map<String,Integer> fileMap;// 文件路径

    public FileSystem() {
        fileMap = new HashMap<>();
        // 缓存根节点路径
        fileMap.put("",-1);
    }

    public boolean createPath(String path, int value) {
        // 没有删除的文件的情况，path只要存在，就一直存在
        if (fileMap.containsKey(path)){
            return false;// 文件已经存在了，返回false
        }
        // 父路径作为一个整体进行考虑，不用逐层递推解决，父路径是整体存在于map中的
        // 分离父路径
        int lastIndex = path.lastIndexOf("/");
        String parentPath = path.substring(0,lastIndex);
        // 父路径不存在，则返回false
        if (!fileMap.containsKey(parentPath)){
            return false;
        }
        // 文件记录
        fileMap.put(path,value);
        return true;
    }

    public int get(String path) {
        // 获取文件的值，不存在就是-1
        return fileMap.getOrDefault(path,-1);
    }
}
