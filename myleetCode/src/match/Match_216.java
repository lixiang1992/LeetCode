package match;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Match_216 {

    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (String s : word1) {
            sb1.append(s);
        }
        for (String s : word2) {
            sb2.append(s);
        }
        return sb1.toString().equals(sb2.toString());
    }

    public String getSmallestString(int n, int k) {
        StringBuilder sb = new StringBuilder();
        Deque<Integer> stack = new LinkedList<>();
        while(k >= 26){
            stack.push(26);
            k -= 26;
        }
        if(k >= 1){
            stack.push(k);
        }
        while(n > stack.size()){
            int val = stack.pop();
            if(val > 1){
                val--;
                stack.push(val);
            }
            sb.append('a');
            n--;
        }
        while(!stack.isEmpty()){
            sb.append((char) (stack.pop() - 1 +'a'));
        }
        return sb.toString();
    }

    public int waysToMakeFair(int[] nums) {
        int n = nums.length;
        Map<Integer,Integer> prefixOddMap = new HashMap<>();
        Map<Integer,Integer> prefixEvenMap = new HashMap<>();
        int sum1 = 0;
        int sum2 = 0;
        for(int i = 0;i < n;i++){
            if(i % 2 == 0){
                sum2 += nums[i];
                prefixEvenMap.put(i,sum2);
            } else {
                sum1 += nums[i];
                prefixOddMap.put(i,sum1);
            }
        }
        int total = sum1 + sum2;
        int ans = 0;
        for(int i = 0;i < n;i++){
            int odd;
            int even;
            if(i % 2 == 0){
                odd = prefixOddMap.getOrDefault(i-1,0) + sum2 - prefixEvenMap.getOrDefault(i,0);
                even = total - odd - nums[i];
            } else {
                even = prefixEvenMap.getOrDefault(i-1,0) + sum1 - prefixOddMap.getOrDefault(i,0);
                odd = total - even - nums[i];
            }
            if(odd == even){
                ans++;
            }
        }
        return ans;
    }

    public int minimumEffort(int[][] tasks) {
        Queue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            if(o2[1] - o2[0] == (o1[1] - o1[0])){
                return o1[1] - o2[1];
            }
            return o2[1] - o2[0] - (o1[1] - o1[0]);
        });
        for(int[] task : tasks){
            pq.offer(task);
        }
        int total = 0;
        int remain = 0;
        while(!pq.isEmpty()){
            int[] task = pq.poll();
            if(remain < task[1]){
                total += task[1] - remain;
                remain = task[1];
            }
            remain -= task[0];
        }
        return total;
    }
}
