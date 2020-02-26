package design;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 288. 单词的唯一缩写
 * 一个单词的缩写需要遵循 <起始字母><中间字母数><结尾字母> 这样的格式
 *
 * 假设你有一个字典和一个单词，请你判断该单词的缩写在这本字典中是否唯一。若单词的缩写在字典中没有任何 其他 单词与其缩写相同，则被称为单词的唯一缩写。
 *
 * 字典中有这个单词，则看单词缩小是不是1，字典中没有这个单词，这看单词缩写是不是0
 */
public class ValidWordAbbr {

    // 所有单词字典
    private Set<String> dict = new HashSet<>();
    // key ： 不同单词缩写格式 value : 不同单词缩写相同数目
    private Map<String,Integer> wordAbbrMap = new HashMap<>();

    public ValidWordAbbr(String[] dictionary) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < dictionary.length; i++) {
            // 单词加入字典
            // 题目有个隐晦的条件，单词缩写出现的的次数，和本身单词不同的记录，即两个一样的单词，是同一个单词缩写，才是不同的次数
            if (!dict.add(dictionary[i])){
                continue;// 字典已经包含了，不需要再记录缩写了
            }
            // 单词缩写是针对不同单词的，这点是关键
            // 记录单词缩写
            int length = dictionary[i].length();
            if (length <= 2){
                str.append(dictionary[i]);
            } else {
                int size = length - 2;
                str.append(dictionary[i].charAt(0)) ;
                str.append(size);
                str.append(dictionary[i].charAt(length - 1));
            }
            wordAbbrMap.put(str.toString(),wordAbbrMap.getOrDefault(str.toString(),0) + 1);
            str.setLength(0);
        }
    }

    public boolean isUnique(String word) {
        // 单词是否存在
        boolean isExist = dict.contains(word);
        // 查找单词缩写
        StringBuilder str = new StringBuilder();
        int length = word.length();
        if (length <= 2){
            str.append(word);
        } else {
            int size = length - 2;
            str.append(word.charAt(0)) ;
            str.append(size);
            str.append(word.charAt(length - 1));
        }
        int count = wordAbbrMap.getOrDefault(str.toString(),0);
        // 字典中存在的，出现1次，字典中不存在，出现0次
        return  isExist ? count == 1 : count == 0;
    }
}
