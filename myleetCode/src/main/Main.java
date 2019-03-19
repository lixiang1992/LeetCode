package main;

import string.MyStringEx;
import tree.binarysearchtree.MyCalendarTwo;
import tree.trie.MapSum;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MapSum sum = new MapSum();
        sum.insert("app",3);
        sum.sum("ap");
//        String a = "jd";
//        String b = "hud";
//        System.out.println(a.compareTo(b));
//        String str = "2000000000000000000";
//        new MyStringEx().myAtoi(str);
//        List<int[]> data = new ArrayList<>();
//        setdata(data);
//        MyCalendarTwo calendarTwo = new MyCalendarTwo();
//        int i = 0;
//        while (i<39){
//            int start = data.get(i)[0];
//            int end = data.get(i)[1];
//            calendarTwo.book(start,end);
//            i++;
//        }
//        BinaryTree binaryTree = new BinaryTree();
//        TreeNode root = new TreeNode(3);
//        root.left = new TreeNode(9);
//        root.right = new TreeNode(20);
////        root.left.left = new TreeNode(3);
////        root.left.right = new TreeNode(4);
//        root.right.left = new TreeNode(15);
//        root.right.right = new TreeNode(7);
//        String count = binaryTree.smallestFromLeaf(root);
//        System.out.println(count);
    }

    private static void setdata(List<int[]> data) {
        String ss = "[51,58];[77,85];[35,44];[53,61];[86,93];[55,61];[43,50];[64,69];[76,82];[98,100];[35,40];[25,32];[8,17];[37,43];[53,60];" +
                "[86,91];[97,100];[37,43];[41,50];[83,92];[66,75];[42,48];[55,64];[37,46];[92,97];[69,76];[85,94];[60,66];[27,34];[36,44];[32,38];[56,62];[93,99];[11,18];[21,30];[81,89];[18,26];[81,90];[91,96];[43,49];[3,12];[97,100];[72,80];[15,23];[63,70];[8,16];[1,6];[16,24];[45,54];[3,9];[30,36];[29,35];[41,48];[21,26];[79,87];[27,32];[88,96];[47,55];[71,76];[32,40];[68,74];[51,59];[44,50];[65,71];[83,90];[86,94];[48,57];[26,32];[27,32];[78,83];[27,35];[19,24];[26,31];[67,75];[87,92];[6,15];[37,44];[62,68];[13,18];[41,46]";
        ss = ss.replace("[","");
        ss = ss.replace("]","");
        String[] initData = ss.split(";");
        for (String one:initData){
            String[] o = one.split(",");
            int[] add = new int[2];
            add[0] = Integer.valueOf(o[0]);
            add[1] = Integer.valueOf(o[1]);
            data.add(add);
        }
    }


}
