package string;

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
}
