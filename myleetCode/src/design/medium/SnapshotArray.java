package design.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 1146.快照数组
 */
public class SnapshotArray {

    // 快照数组，为什么使用TreeMap,因为没有改动的，不需要存储，每次获取floor即可
    private TreeMap<Integer,Map<Integer,Integer>> snapMap = new TreeMap<>();
    private int curIndex = 0;

    public SnapshotArray(int length) {

    }

    public void set(int index, int val) {
        Map<Integer,Integer> snap = snapMap.computeIfAbsent(curIndex, k->new HashMap<>());
        if(snap.isEmpty()){
            Map.Entry<Integer,Map<Integer,Integer>> entry = snapMap.floorEntry(curIndex-1);
            if(entry != null){
                snap.putAll(entry.getValue());
            }
        }
        snap.put(index,val);
    }

    public int snap() {
        int index = curIndex;
        curIndex++;
        return index;
    }

    public int get(int index, int snap_id) {
        Map.Entry<Integer,Map<Integer,Integer>> entry = snapMap.floorEntry(snap_id);
        if(entry == null){
            return 0;
        }
        return entry.getValue().getOrDefault(index,0);
    }
}
