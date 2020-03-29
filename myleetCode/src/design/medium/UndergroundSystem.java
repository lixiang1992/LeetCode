package design.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * 设计地铁系统
 */
public class UndergroundSystem {

    // 人员的进站
    private Map<Integer, String> manInStation = new HashMap<>();
    // 人员进站时间
    private Map<Integer, Integer> manInStationTime = new HashMap<>();
    // s->e的时间差和次数
    private Map<String, int[]> stationTime = new HashMap<>();

    public UndergroundSystem() {

    }

    // 进站
    public void checkIn(int id, String stationName, int t) {
        // 人员进站地点和时间
        manInStation.put(id, stationName);
        manInStationTime.put(id, t);
    }

    // 出站
    public void checkOut(int id, String stationName, int t) {
        // 获取进站地点和时间
        String inStation = manInStation.get(id);
        int st = manInStationTime.get(id);
        // 获取本次路程时间差和次数
        int[] time = stationTime.computeIfAbsent(getJourney(inStation,stationName), k -> new int[]{0, 0});
        time[0] += t - st;// 出入站时间消耗
        time[1]++;//
    }

    public double getAverageTime(String startStation, String endStation) {
        int[] time = stationTime.get(getJourney(startStation,endStation));
        return 1D * time[0]/time[1];
    }

    // 获取路程
    private String getJourney(String startStation,String endStation){
        return startStation + "->" + endStation;
    }
}
