package leetcode.string;

/**
 * <pre>
 * https://leetcode-cn.com/explore/interview/card/bytedance/242/string/1013/
 * path = "/home/", => "/home"
 * path = "/a/./b/../../c/", => "/c"
 * path = "/a/../../b/../c//.//", => "/c"
 * path = "/a//b////c/d//././/..", => "/a/b/c"
 *
 * Corner Cases:
 * Did you consider the case where path = "/../"?
 *  In this case, you should return "/".
 * Another corner case is the path might contain multiple slashes '/' together, such as "/home//foo/".
 *  In this case, you should ignore redundant slashes and return "/home/foo".
 * </pre>
 */
public class SimplifyPath {

    public static void main(String[] args) {

        Solution solution = new Solution();
        String path = "/../";
        for (String s:
                path.split("/") ) {
            System.out.println(s);

        }
        String s = solution.simplifyPath(path);
        System.out.println(s);

    }
    static class Solution {
        public String simplifyPath(String path) {
            int n =  path.length();
            Stack stack = new Stack(n);
            String[] split = path.split("/");
            for (String s: split) {
                if(s.equals(".")){
                    continue;
                }else if(s.equals("..")){
                    stack.rightOut();
                    continue;
                }else if(s.equals("")){
                    continue;
                }else {
                  stack.in(s);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            while (!stack.isEmpty()){
                stringBuilder.append("/");
                stringBuilder.append(stack.leftOut());

            }
            String result = stringBuilder.toString();

            return result.equals("") ? "/":result;
        }
        class Stack{
            public  int compacity;
            public  int bottom=-1;
            public int top = 0;
            public String [] elem ;

            public Stack(int n) {
                compacity = 100;
                if(n<0){
                    this.elem = new String[this.compacity];
                }else{
                    this.elem = new String[n];
                }

            }
            public boolean isEmpty () {
                if(top-bottom == 1){
                    return true;
                }
                return false;
            }

            public void in( String elem){
                if (top < compacity){
                    this.elem[top++] = elem;
                }
            }

            public String rightOut(){
                if (top-bottom > 1){
                    return this.elem[--top];
                }
                return null;
            }

            public String leftOut(){
                if (top-bottom>1){
                    return this.elem[++bottom];
                }
                return null;
            }
        }
    }
}

