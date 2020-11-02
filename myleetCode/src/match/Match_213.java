package match;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Match_213 {

    public boolean canFormArray(int[] arr, int[][] pieces) {
        Map<Integer,int[]> map = new HashMap<>();
        for(int[] price : pieces){
            map.put(price[0],price);
        }
        for(int i = 0;i < arr.length;){
            int[] piece = map.get(arr[i]);
            if(piece == null){
                return false;
            }
            for(int j = 0;j < piece.length;j++){
                if(arr[i] != piece[j]){
                    return false;
                }
                i++;
            }
        }
        return true;
    }

    public int countVowelStrings(int n) {
        int[] cnt = new int[5];
        Arrays.fill(cnt,1);
        for(int i = 2;i <= n;i++){
            for(int j = 1;j < 5;j++){
                cnt[j] += cnt[j - 1];
            }
        }
        int ans = 0;
        for(int num : cnt){
            ans += num;
        }
        return ans;
    }

    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        Queue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int n = heights.length;
        for(int i = 1;i < heights.length;i++){
            if(heights[i] <= heights[i - 1]){
                continue;
            }
            int delta = heights[i] - heights[i - 1];
            pq.offer(delta);
            if(pq.size() > ladders){
                bricks -= pq.poll();
            }
            if(bricks <= 0){
                return i - 1;
            }
        }
        return n - 1;
    }

    public String kthSmallestPath(int[] destination, int k) {
        int sum = destination[0] + destination[1];
        int row = destination[0], col = destination[1];
        // 获取组合数
        long[][] comp = getComb(sum);
        StringBuilder ans = new StringBuilder();
        while(true){
            if(col == 0){
                while(row > 0){
                    ans.append("V");
                    row--;
                }
                break;
            } else if(row == 0){
                while(col > 0){
                    ans.append("H");
                    col--;
                }
                break;
            }
            // 往右走的方案数比K大
            if(comp[sum - 1][col - 1] >= k){
                ans.append("H");
                col--;
            } else {
                ans.append("V");
                k -= comp[sum - 1][col - 1];
                row--;
            }
            sum--;
        }
        return ans.toString();
    }

    private long[][] getComb(int n){
        long[][] comp = new long[n + 1][n + 1];
        comp[0][0] = 1L;
        for(int i = 1;i <= n;i++){
            comp[i][0] = 1L;
            for(int j = 1;j <= i;j++){
                comp[i][j] = comp[i-1][j-1] + comp[i-1][j];
            }
        }
        return comp;
    }
}
