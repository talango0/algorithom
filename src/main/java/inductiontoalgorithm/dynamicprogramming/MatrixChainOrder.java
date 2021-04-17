package inductiontoalgorithm.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2020-08-11 16:47:51
 */
public class MatrixChainOrder {
    /*
        A1_{30*35}A2_{35*15}A3_{15*5}A4_{5*10}A5_{10*20}A6_{20*25}
     */
    /**
     * 矩阵的规模列表,假设A_i的规模为 p_{i-1} * p_{i}, p.length = n-1
     */
    private static int [] p = {30, 35, 15, 5, 10, 20, 25};

    static class Solution{
        /**
         * 自底向上递归求解矩阵链乘最优划分方案
         * @param p
         * @return
         */
        public Map<String,int[][]> matrixChainOrder(int [] p){

            if(p == null || p.length<1) {
                return null;
            }
            int n = p.length - 1;
            //定义两个变量m[n+1][n+1]和s[n+1][n+1]
            //m用于保存子问题的规模
            int [][] m = new int[n+1][n+1];
            //s用于保存最优值 m[i][j] 的分割点
            int [][] s = new int[n+1][n+1];
            //初始化规模矩阵
            for(int i = 1;i<=n;i++){
                m[i][i] = 0;
            }
            //l表示子问题的长度
            for(int l = 2;l<=n;l++){
                for(int i = 1; i<=n-l+1; i++){
                    int j = i+l-1;
                    m[i][j] = Integer.MAX_VALUE;
                    // k为最优划分点，i =< k <= j-1, 所以循环介于i和j之间的每一种划分方法找出最优的划分点k
                    for(int k = i; k<=j-1; k++){

                        int q = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j];
                        if(q < m[i][j]){
                            m[i][j] = q;
                            s[i][j] = k;
                        }
                    }
                }
            }
            HashMap<String, int[][]> res = new HashMap<>(2);
            res.put("s",s);
            res.put("m",m);

            return res;
        }

        /**
         * 构造最优解
         */
        public void printOptionParens(int [][] s, int i, int j){
            if(i==j){
                System.out.print("A"+i);
            }else {
                System.out.print("(");
                printOptionParens(s, i, s[i][j]);
                printOptionParens(s,s[i][j]+1, j);
                System.out.print(")");
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        Map<String, int[][]> res = solution.matrixChainOrder(p);
        solution.printOptionParens(res.get("s"),1, 6);
        System.out.println();
    }



}
