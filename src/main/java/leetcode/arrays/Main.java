package leetcode.arrays;

import java.util.*;

/**
 * 阿里巴巴在线考试 中文English 正在拍摄  剩余时间: 00:46:53 马彦伟 | 退出
 * 0/2
 * 2 问答题
 * 电商网站中，web页面之间可以互相跳转。给定用户路径中的起始页面和终点页面，计算用户路径中，这两个页面间最少需要经过的跳转次数。
 *
 * 已输入字数: 0 / 10000   运行
 * 编译器版本: Java 1.8.0_66
 * 请使用标准输入输出(System.in, System.out)；已禁用图形、文件、网络、系统相关的操作，如java.lang.Process , javax.swing.JFrame , Runtime.getRuntime；不要自定义包名称，否则会报错，即不要添加package answer之类的语句；您可以写很多个类，但是必须有一个类名为Main，并且为public属性，并且Main为唯一的public class，Main类的里面必须包含一个名字为'main'的静态方法（函数），这个方法是程序的入口
 * 时间限制: 1S (C/C++以外的语言为: 3 S)   内存限制: 64M (C/C++以外的语言为: 576 M)
 * 输入:
 * 第一行包含两列，为起始页面和终止页面的名称。
 * 第二行到最后一行为用户行为日志，其中第一列为当前页面名称，第二列为上一页面名称。第二行的NULL表示第一个页面的上级页面为空。每列数据之间以逗号','为分隔符，具体请见输入范例。
 * 输出:
 * 上述用户的行为路径中，页面A到页面F有以下两种方式
 * 1 A->B->D->E->F
 * 2 A->E->F
 * 此时方式2跳转次数最少，为2
 * 输入范例:
 * A,F
 * A,NULL
 * B,A
 * D,B
 * F,B
 * E,A
 * F,E
 * E,D
 * D,B
 * C,B
 * B,G
 * G,F
 * C,F
 * 输出范例:
 * 2
 * 编程说明 ▲
 * 在此填写答案
 *  上一题  下一题 保存  交 卷
 */
public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int num = 0;
//        String src ="";
//        String des = "";
//        Map <String,Set> webNode = new HashMap<String,Set>();
//        if (scanner.hasNext()) {
//            String str1 = scanner.next();
//            String[] split = str1.split(",");
//            if(num == 0){
//                src = split[0];
//                des = split[1];
//            }else{
//                if(webNode.containsKey(split[1])){
//                    Set set= webNode.get(split[1]);
//                    set.add(split[0]);
//                }else{
//                    Set set =  new HashSet();
//                    set.add(split[0]);
//                    webNode.put(split[1],set); //key ==》 value
//                }
//
//            }
//        }
//
//        List<String> roadList = new ArrayList<>();
//        if(webNode.containsKey(src)){
//            Set<Map.Entry<String, Set>> entries = webNode.entrySet();
//
//
//        }
//        TreeNode left = treeNode.left;
        int [] nums = {4,7,2,1,5,3,8,6};
        Arrays.sort(nums);
        System.out.println(nums.toString());
        System.out.println(Arrays.binarySearch(nums,2));

    }




}
