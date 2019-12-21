package struct.datastruct.tree.binarytree;

import java.util.*;

/**
 * 528.杀死进程
 * hash表，层次遍历
 */
public class KillProcess {

    private Map<Integer,ProcessNode> m_cache = new HashMap<>();

    private static class ProcessNode{
        int pid;// 进程id
        List<ProcessNode> childList;// 进程的子进程

        ProcessNode(int pid){
            this.pid = pid;
        }

        void addChild(ProcessNode child){
            if (this.childList == null){
                this.childList = new LinkedList<>();
            }
            this.childList.add(child);
        }
    }

    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        constructProcessTree(pid,ppid);
        List<Integer> result = new ArrayList<>();
        // pid是唯一的
        ProcessNode node = m_cache.get(kill);
        Queue<ProcessNode> queue = new LinkedList<>();
        queue.offer(node);
        // 层次遍历杀掉进程
        while (!queue.isEmpty()){
            node = queue.poll();
            result.add(node.pid);
            if (node.childList != null){
                queue.addAll(node.childList);
            }
        }
        return result;
    }

    /**
     * 构造进程树
     * @param pid
     * @param ppid
     */
    private void constructProcessTree(List<Integer> pid, List<Integer> ppid){
        // pid是唯一的
        for (int i = 0;i<pid.size();i++){
            // 获取父进程id
            Integer parentId = ppid.get(i);
            // 构造父进程节点
            ProcessNode parentNode = constructHashProcess(parentId);
            // 获取当前进程id
            Integer id = pid.get(i);
            ProcessNode node = constructHashProcess(id);
            parentNode.addChild(node);
        }
    }

    /**
     * 进程哈希化
     * @param id
     * @return
     */
    private ProcessNode constructHashProcess(Integer id){
        ProcessNode node = m_cache.get(id);
        if (node == null){
            node = new ProcessNode(id);
            m_cache.put(id,node);
        }
        return node;
    }
}
