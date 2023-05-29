package leetcode.程序员面试金典;

//有重复字符串的排列组合。编写一种方法，计算某字符串的所有排列组合。
//
//示例1:
//
// 输入：S = "qqe"
// 输出：["eqq","qeq","qqe"]
//示例2:
//
// 输入：S = "ab"
// 输出：["ab", "ba"]
//提示:
//
//字符都是英文字母。
//字符串长度在[1, 9]之间。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/permutation-ii-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.*;

/**
 * @author mayanwei
 * @date 2023-05-29.
 */
public class _08_08_有重复字符串的排列组合{
    class Solution {
        List<String> res;
        boolean visited[];
        Deque<Character> path;
        public String[] permutation(String S) {
            if (S == null) {
                return new String[0];
            }
            visited = new boolean[S.length()];
            res = new ArrayList<>();
            path = new LinkedList<>();
            char[] chs = S.toCharArray();
            Arrays.sort(chs);
            getPerms(chs);
            return res.toArray(new String[0]);
        }

        private void getPerms(char[] chs) {
            if (chs.length == path.size()) {
                StringBuilder str = new StringBuilder();
                for (Character ch : path) {
                    str.append(ch);
                }
                res.add(str.toString());
            }

            for (int i = 0; i < chs.length; i++) {
                if (visited[i]){
                    continue;
                }
                if (i>0 && chs[i] == chs[i-1] && !visited[i-1]) {
                    continue;
                }
                path.addLast(chs[i]);
                visited[i] = true;
                getPerms(chs);
                visited[i] = false;
                path.removeLast();
            }
        }
    }

    class Solution2 {
        char[] sChar;
        List<String> list;
        String[] result;
        public  String[] permutation(String S) {
            sChar=S.toCharArray();
            list=new ArrayList();
            getStringPermutation(0);
            return list.toArray(new String[list.size()]) ;
        }
        private  void getStringPermutation(int start) {
            int i;
            if(start==sChar.length-1) {
                list.add(new String(sChar));
                return;
            }
            boolean [] visited=new boolean[256];
            for(i=start;i<sChar.length;i++) {
                if(!visited[sChar[i]]){
                    visited[sChar[i]]=true;
                    swapArr(sChar,start,i);
                    getStringPermutation(start+1);
                    swapArr(sChar,start,i);
                }
            }
        }
        private  void swapArr(char[] arr,int i,int j) {
            char temp=arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
        }
    }


    class Solution3 {
        public  String[] permutation(String S) {
            ArrayList<String> result = new ArrayList<>();
            HashMap<Character, Integer> map = buildFreqTable(S);
            printPerm(map, "", S.length(), result);
            return result.toArray(new String[0]);
        }

        private void printPerm(HashMap<Character, Integer> map, String prefix, int remaining, ArrayList<String> result) {
            // base case
            if (remaining == 0) {
                result.add(prefix);
                return;
            }
            // 用剩余的字符生成其他排列组合
            for (Character ch : map.keySet()) {
                int count = map.get(ch);
                if (count > 0) {
                    map.put(ch, count-1);
                    printPerm(map, prefix + ch, remaining-1, result);
                    map.put(ch, count);
                }
            }
        }

        private HashMap<Character, Integer> buildFreqTable(String s) {
            char[] chs = s.toCharArray();
            HashMap<Character, Integer> res = new HashMap<>();
            for (char ch : chs) {
                if (!res.containsKey(ch)) {
                    res.put(ch, 0);
                }
                res.put(ch, res.get(ch) + 1);
            }
            return res;
        }
    }
}
