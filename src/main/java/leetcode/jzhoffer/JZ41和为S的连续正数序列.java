package leetcode.jzhoffer;

import java.util.ArrayList;

/**
 * @author mayanwei
 */
public class JZ41和为S的连续正数序列 {
    /*
    题目描述
小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。
但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。
现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
输出描述:
输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
     */

    public static class Solution {

        public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
            ArrayList<ArrayList<Integer>> res = new ArrayList<>();
            ArrayList<Integer> list;
            //左边界
            for(int i= 1 ; i<= sum/2; i++){
                //右边界
                for(int j = i+1; j<sum; j++){
                    int sum0 = (int) ((j-i+1)*(i+j)/2.0);
                    if(sum0 == sum){
                        list = new ArrayList<>();
                        for(int k = i; k <= j; k++){
                            list.add(k);
                        }
                        res.add(list);
                    }
                }
            }
            return res;
        }
    }


    public static class Solution2 {

        public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
            ArrayList<ArrayList<Integer>> res = new ArrayList<>();
            ArrayList<Integer> list;
            int l = 1,r =1;
            int tmp = 0;
            while (l <= sum/2){
                if(tmp <sum){
                    tmp += r;
                    ++r;
                }else if(tmp > sum){
                    tmp -= l;
                    ++l;
                }else {
                    list = new ArrayList<>(r-l);
                    for(int k=l; k<r; k++){
                        list.add(k);
                    }
                    res.add(list);
                    tmp -= l;
                    ++l;
                }
            }
            return res;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.FindContinuousSequence(8));

    }

}
