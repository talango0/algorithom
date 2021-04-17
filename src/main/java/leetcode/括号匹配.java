package leetcode;



import java.util.Stack;

/**
 * @author mayanwei
 */
public class 括号匹配 {

    static class Solution{
        public boolean isvalidStr(String str){
            Stack<Character> stack = new Stack<>();
            if(str == null || str.length()<1){
                return true;
            }
            int length = str.length();
            for(int i=0; i<length; i++){
                char c = str.charAt(i);
                if(c == '('){
                    stack.push(c);
                }
                if(c == ')'){
                    if(stack.size() == 0){
                        stack.push(c);
                        continue;
                    }
                    Character pop = stack.peek();
                    if(pop != '*' && pop !='('){
                        return false;
                    }else {
                        stack.pop();
                    }
                }
                if(c == '*'){
                    if(stack.size() == 0){
                        stack.push(c);
                        continue;
                    }
                    Character pop = stack.peek();
                    if(pop == '(' || pop == '*'){
                        stack.pop();
                    }else {
                        stack.push(c);
                    }
                }
            }
            if(stack.size() >0){
                return false;
            }
            return true;
        }

    }


    public static void main(String [] args) {
//        int [] nums = {13, 19, 9, 5, 12, 8, 7, 4, 21, 2, 5, 11};
        Solution solution = new Solution();
//        solution.quickSort(nums, 0, nums.length-1);
        String str = "*(()*(***)()";
        System.out.println(solution.isvalidStr(str));
    }
}
