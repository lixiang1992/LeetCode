package hash;

import java.util.*;

public class Hash {
    private class Point {
        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int a, int b) {
            x = a;
            y = b;
        }
    }

    public int maxPoints(Point[] points) {
        if (points == null) {
            return 0;
        }

        return 0;
    }

    /**
     * 3. 无重复字符的最长子串
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int max = 0;
        Map<Character, Integer> charMap = new HashMap<>();// key字符,value字符对应的下标
        int temp = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (charMap.containsKey(c)) {
                int index = charMap.get(c);// 获取字符上一个对应的下标
                temp = i - index;// 获取新的长度
                while (start <= index) {// 移除到重复元素，从start到重复元素上一个下标的位置
                    charMap.remove(s.charAt(start));
                    start++;
                }
            } else {
                temp++;
                if (temp > max) {
                    max = temp;
                }
            }
            charMap.put(c, i);// 新的下标
        }
        return max;
    }

    /**
     * 5.最长回文串
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        return null;
    }

    /**
     * 最长有效括号
     *
     * @param s
     * @return
     */
    public static int longestValidParentheses(String s) {
        if (s == null) {
            return 0;
        }
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        Stack<Character> stack = new Stack<>();
        int sum = 0;
        int tempSum = 0;
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (map.get(c) == null) {
                stack.push(c);
                if (tempSum > sum) {
                    sum = tempSum;
                }
            } else {
                if (!stack.isEmpty()) {
                    stack.pop();
                    tempSum = tempSum + 2;
                } else {
                    if (tempSum > sum) {
                        sum = tempSum;
                    }
                    tempSum = 0;
                }
            }
        }
        if (tempSum > sum) {
            sum = tempSum;
        }
        return sum;
    }

    /**
     * 爱丽丝有一手（hand）由整数数组给定的牌。
     *
     * 现在她想把牌重新排列成组，使得每个组的大小都是 W，且由 W 张连续的牌组成。
     *
     * 如果她可以完成分组就返回 true，否则返回 false。
     * @param hand
     * @param W
     * @return
     */
    public boolean isNStraightHand(int[] hand, int W) {
        if(hand == null || hand.length == 0 || hand.length % W != 0){
            return false;
        }

        return true;
    }

    /**
     * 957.N天后的牢房
     * 八个牢房最多有2^8种排列，但实际上最多只有2^6种，
     * 因为第一个牢房和最后一个牢房没有两个相邻房间，所以总会是0。
     * 这说明了：当N很大时，里面一定会出现一个循环，也就是环。
     * 这样我们的思路就是找到这个环出现的位置并算出一个周期要走多少步，
     * 最后用剩余的步数对这个周期取余，再走余数步就到达了结果。
     *
     * @param cells
     * @param N
     * @return
     */
    public int[] prisonAfterNDays(int[] cells, int N) {
        Map<String, Integer> storage = new HashMap<>();
        while (N > 0) {
            int[] next = new int[cells.length];
            storage.put(Arrays.toString(cells), N--);
            for (int i = 1; i < 7; i++)
                next[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
            if (storage.containsKey(Arrays.toString(next))) {
                // 找到周期后算出在周期内走几步
                N %= N - storage.get(Arrays.toString(next));
                cells = next;
                break;
            }
            cells = next;
        }
        // 在一个周期内走即可
        for (int t = 0; t < N; t++) {
            int[] next = new int[cells.length];
            for (int i = 1; i < cells.length - 1; i++)
                next[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
            cells = next;
        }
        return cells;
    }
}
