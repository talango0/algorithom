package inductiontoalgorithm.dynamicprogramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2020-07-28 15:21:45
 */
public class CutRod {
    private static int [] p = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
    static class Solution{
        /**
         * 自底向上的动态规划
         * @param p 价格数组
         * @param n 规模
         * @return
         */
        public Map<String,int[]> extendBottomUpCutRot(int [] p, int n){
            //用于记录子问题最优解，大小为初始化为 n+1
            int [] r = new int[n+1];
            //用于记录切割方案，大小初始化为 n+1
            int [] s = new int[n+1];
            //规模为0时收益为0
            r[0] = 0;
            //j比较大的问题规模，上面已经初始化了规模为0是的收益了，这里从 i= 1 开始，表示从规模为1开始算起，注意结束条件包含规模n
            for(int j = 1;j<=n;j++){
                //初始化为负无穷
                int q = Integer.MIN_VALUE;
                //i表示比较小的规模，从规模为1开始，循环条件是小于等于规模j
                for(int i = 1; i<=j; i++){
                    // q = max(q, p[i]+r[j-i])
                    if(q < p[i] + r[j-i]){
                        q = p[i] + r[j-i];
                        //用于保存规模j的最优切割方案
                        s[j] = i;
                    }
                }
                r[j] = q;
            }
            HashMap<String, int[]> res = new HashMap<>(2);
            res.put("r", r);
            res.put("s", s);
            return res;
        }

        public void printCutRotSolution(int [] p, int n){
            Map<String, int[]> rs = extendBottomUpCutRot(p, n);
            int[] r = rs.get("r");
            int[] s = rs.get("s");
            System.out.println("r="+Arrays.toString(r));
            System.out.println("s="+Arrays.toString(s));
            int i= 0;
            while (n>0){
                System.out.println("第"+(++i)+"段："+s[n]);
                n = n-s[n];
            }
        }

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.printCutRotSolution(p, 7);
    }
}
