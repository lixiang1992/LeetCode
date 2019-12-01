package string;

import java.util.*;

public class MyStringEx {

    /**
     * 字符串转换整数 (atoi)
     * @param str
     * @return
     */
    public int myAtoi(String str) {
//        str = str.trim();// 不能直接去掉空格，会报错
        char[] chars = str.toCharArray();
        if (chars == null || chars.length == 0){
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        boolean firstIsZero = false;
        char c0 = chars[0];
        if ((c0 > '0' && c0 <= '9') || c0 == '-'){
            sb.append(c0);
        }else if(c0 == '0'){
            firstIsZero = true;
        }else {
            return 0;
        }
        boolean startIsZero = firstIsZero;
        for (int i = 1;i<chars.length;i++){
            if (startIsZero){
                if (chars[i] == '0'){
                    continue;
                }else if(chars[i] > '0' && chars[i] <= '9'){
                    sb.append(chars[i]);
                    startIsZero = false;
                }else {
                    break;
                }
            }else {
                if((chars[i] >= '0' && chars[i] <= '9')){
                    sb.append(chars[i]);
                }else {
                    break;
                }
            }
        }
        if ((sb.length() == 0) || (sb.length() == 1 && sb.indexOf("-") == 0)){
            return 0;
        }else {
            long value = Long.parseLong(sb.toString());
            if(value > Integer.MAX_VALUE || value < Integer.MIN_VALUE){
                return 0;
            }else {
                return Integer.valueOf(sb.toString());
            }
        }
    }

    /**
     * 13.罗马数字转整数
     * @param s
     * @return
     */
    public int romanToInt(String s) {
        Map<Character,Integer> mapping = getRomanIntMapping();
        Map<Character, Set<Character>> specialMapping = getSpecialMapping();
        int num = 0;
        char lastChar = s.charAt(0);
        num += mapping.get(lastChar);
        for (int i = 1;i < s.length();i++){
            char c = s.charAt(i);
            num += mapping.get(c);
            // 上一个元素对应的特殊元素存在
            Set<Character> special = specialMapping.get(lastChar);
            if (special != null && special.contains(c)){
                // 是特殊元素，需要减去上一个元素对应的值，这里减两次是因为在之前已经把这个元素加上了
                num -= mapping.get(lastChar)*2;
            }
            lastChar = c;// lastChar修改
        }
        return num;
    }

    private Map<Character,Integer> getRomanIntMapping(){
        Map<Character,Integer> mapping = new HashMap<>();
        mapping.put('I',1);
        mapping.put('V',5);
        mapping.put('X',10);
        mapping.put('L',50);
        mapping.put('C',100);
        mapping.put('D',500);
        mapping.put('M',1000);
        return mapping;
    }

    private Map<Character, Set<Character>> getSpecialMapping(){
        Map<Character, Set<Character>> mapping = new HashMap<>();

        Set<Character> set = new HashSet<>();
        set.add('V');
        set.add('X');
        mapping.put('I',set);

        set = new HashSet<>();
        set.add('L');
        set.add('C');
        mapping.put('X',set);

        set = new HashSet<>();
        set.add('D');
        set.add('M');
        mapping.put('C',set);
        return mapping;
    }

    public int strStr(String haystack, String needle) {
        if ("".equals(needle)){
            return 0;
        }
        for (int i = 0;i<haystack.length()-needle.length()+1;i++){
            int j = 0;
            while (j<needle.length()){
                if (haystack.charAt(i+j) != needle.charAt(j)){
                    break;// 不相等了，直接break
                }
                j++;
            }
            if (j==needle.length()){
                return i;
            }
        }
        return -1;
    }

    /**
     * 1023.驼峰匹配
     * @param queries
     * @param pattern
     * @return
     */
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        char[] patternCharArray = pattern.toCharArray();// 模式串数组
        List<Boolean> resList = new ArrayList<>();
        int patternLength = pattern.length();
        for (String query : queries){
            int index = 0;
            char[] queryCharArray = query.toCharArray();// 查询字符数组
            boolean res = true;
            for (char qc : queryCharArray){
                if (index < patternLength && qc == patternCharArray[index]){
                    // 匹配上了，往模式串的下一个字符移动
                    index++;
                }else if(qc >= 'A' && qc <= 'Z'){
                    res = false;// 是大写字母表示匹配过程是不行的，跳出当前字符串匹配，匹配失败
                    break;
                }
            }
            // 模式串如果没有完全匹配完，则表示是匹配不上的
            resList.add(res && index == patternLength);
        }
        return resList;
    }
}
