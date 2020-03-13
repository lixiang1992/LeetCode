package design;

import java.util.*;

/**
 * 635. 设计日志存储系统
 * 你将获得多条日志，每条日志都有唯一的 id 和 timestamp，
 * timestamp 是形如 Year:Month:Day:Hour:Minute:Second 的字符串，
 * 例如 2017:01:01:23:59:59，所有值域都是零填充的十进制数。
 *
 * 设计一个日志存储系统实现如下功能：
 *
 * void Put(int id, string timestamp)：
 * 给定日志的 id 和 timestamp，将这个日志存入你的存储系统中。
 *
 * int[] Retrieve(String start, String end, String granularity)：
 * 返回在给定时间区间内的所有日志的 id。start 、 end 和 timestamp 的格式相同，
 * granularity 表示考虑的时间级。
 * 比如，start = "2017:01:01:23:59:59", end = "2017:01:02:23:59:59",
 * granularity = "Day" 代表区间 2017 年 1 月 1 日到 2017 年 1 月 2 日。
 *
 */
public class LogSystem {

    // key->时间 value->日志id
    private TreeMap<String,Integer> m_logMap;
    // key->gra value->0截取长度，1开始时间的后序，2结束时间的后序
    private Map<String,String[]> m_graMap = new HashMap<>();

    public LogSystem() {
        m_logMap = new TreeMap<>();
        m_graMap.put("Year",new String[]{"4",":01:01:00:00:00",":12:31:23:59:59"});
        m_graMap.put("Month",new String[]{"7",":01:00:00:00",":31:23:59:59"});
        m_graMap.put("Day",new String[]{"10",":00:00:00",":23:59:59"});
        m_graMap.put("Hour",new String[]{"13",":00:00",":59:59"});
        m_graMap.put("Minute",new String[]{"16",":00",":59"});
        m_graMap.put("Second",new String[]{"19","",""});
    }

    public void put(int id, String timestamp) {
        m_logMap.put(timestamp,id);
    }

    public List<Integer> retrieve(String s, String e, String gra) {
        String[] graAttr = m_graMap.get(gra);
        int limit = Integer.valueOf(graAttr[0]);
        // 时间重新填充
        s = s.substring(0,limit) + graAttr[1];
        e = e.substring(0,limit) + graAttr[2];
        // 把treeMap里的id全部输出
        return new ArrayList<>(m_logMap.subMap(s,true,e,true).values());
    }
}
