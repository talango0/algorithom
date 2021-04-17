package leetcode.backtracing;

import java.util.ArrayList;
import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;
/**
 * 分割回文串
 */
public class Partition {
    private final List<List<String>> res = new ArrayList<>();
    private final Deque<String> path = new ArrayDeque<>();
    public List<List<String>> partition(String s){
        int len = s.length();
        if(len == 0){
            return res;
        }
        backTracing(s, 0, len);
        return res;
    }
    public void backTracing(String s, int cur, int length) {
        if(cur == length){
            res.add(new ArrayList<>(path));
            return;
        }
        for(int i = cur; i < length; i++){
            if(!checkPalinedrome(s, cur, i)){
                continue;
            }
            path.addLast(s.substring( cur, i+1));
            backTracing(s, i+1, length);
            path.removeLast();
        }
    }
    private boolean checkPalinedrome(String s, int left, int right){
        while(left < right){
            if(s.charAt(left) != s.charAt(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    public static void main(String[] args) {
        Partition  partition = new Partition();
        List<List<String>> res = partition.partition("aab");
        System.out.println(res.toString());
    }

}
