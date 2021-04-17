import java.util.*;

public class TestStringReverse {


static class Solution{
    public static String reverse(String str){
        if(str ==null||str.length()<1){
            return str;
        }
        List<String> list = new ArrayList<>();
        for(int i=0,j = 0; i<str.length();i++){
            if( ' ' == str.charAt(i)){
                list.add(str.substring(j, i));
                j = i;
                while (i<str.length() && ' '== str.charAt(i)){
                    i++;
                }
                list.add(str.substring(j,i));
                j = i;
            }
        }
        String res = "";
        Collections.reverse(list);
        for (String s : list) {
            res+=s;
        }
        return res;

    }
}

    public static void main(String[] args) {
        String str = " hello    my   name is   zhangsan  ";
        String strReversed = Solution.reverse(str);
        System.out.println(strReversed);
    }
}
