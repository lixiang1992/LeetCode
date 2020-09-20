package match;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Match_207 {

    public String reorderSpaces(String text) {
        int cnt = 0;
        char last = 'A';
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < text.length();i++){
            char c = text.charAt(i);
            if(c != ' '){
                sb.append(c);
            }
            if(c == ' '){
                cnt++;
                if(sb.length() > 0 && last != ' '){
                    sb.append(",");
                }
            }
            last = c;
        }
        String[] res = sb.toString().split(",");
        if(res.length == 1){
            StringBuilder ans = new StringBuilder();
            ans.append(res[0]);
            while(cnt > 0){
                ans.append(" ");
                cnt--;
            }
            return ans.toString();
        }
        int a = cnt /(res.length - 1);
        int b = cnt % (res.length - 1);
        StringBuilder ans = new StringBuilder();
        for(int i = 0;i < res.length;i++){
            ans.append(res[i]);
            if(i == res.length - 1){
                break;
            }
            for(int j = 0;j < a;j++){
                ans.append(" ");
            }
        }
        while(b > 0){
            ans.append(" ");
            b--;
        }
        return ans.toString();
    }

    public int maxUniqueSplit(String s) {
        Set<String> set = new HashSet<>();
        int[] ans = {0};
        dfs(ans,set,0,s);
        return ans[0];
    }

    private void dfs(int[] ans,Set<String> set,int cur,String s){
        // 递归终止条件
        if(cur == s.length()){
            // 字符串遍历结束，求最大值
            ans[0] = Math.max(ans[0],set.size());
            return;
        }
        // 剪枝，后序结果不可能最优了，直接返回
        if(s.length() - cur + set.size() <= ans[0]){
            return;
        }
        StringBuilder sb = new StringBuilder();
        for(int i = cur;i < s.length();i++){
            sb.append(s.charAt(i));
            String temp = sb.toString();
            // 不在集合中，进入下一层
            if(!set.contains(temp)){
                set.add(temp);
                dfs(ans,set,i + 1,s);
                set.remove(temp);
            }
        }
    }


    public int maxProductPath(int[][] grid) {
        int mod = (int) (1e9 + 7);
        // 0-存储最大值，1-存储最小值
        long[][][] dp = new long[grid.length][grid[0].length][2];
        dp[0][0][0] = dp[0][0][1] = grid[0][0];
        for(int i = 1;i < dp.length;i++){
            long val = dp[i - 1][0][0] * grid[i][0];
            dp[i][0][0] = dp[i][0][1] = val;
        }
        for(int i = 1;i < dp[0].length;i++){
            long val = dp[0][i - 1][0] * grid[0][i];
            dp[0][i][0] = dp[0][i][1] = val;
        }
        for(int i = 1;i < grid.length;i++){
            for(int j = 1;j < grid[i].length;j++){
                long max = Math.max(Math.max(dp[i-1][j][0],dp[i-1][j][1]),Math.max(dp[i][j-1][0],dp[i][j-1][1]));
                max *= grid[i][j];
                long min = Math.min(Math.min(dp[i-1][j][0],dp[i-1][j][1]),Math.min(dp[i][j-1][0],dp[i][j-1][1]));
                min *= grid[i][j];
                dp[i][j][0] = Math.max(max,min);
                dp[i][j][1] = Math.min(max,min);
            }
        }
        long ans = dp[grid.length - 1][grid[0].length - 1][0];
        if(ans < 0L){
            return -1;
        }
        return (int) (ans % mod);
    }

    public int connectTwoGroups(List<List<Integer>> cost) {
        return 0;
    }
}
