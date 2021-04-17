package leetcode.string;

public class _242_有效的字母异位词 {


//给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
//
// 示例 1:
//
// 输入: s = "anagram", t = "nagaram"
//输出: true
//
//
// 示例 2:
//
// 输入: s = "rat", t = "car"
//输出: false
//
// 说明:
//你可以假设字符串只包含小写字母。
//
// 进阶:
//如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
// Related Topics 排序 哈希表
// 👍 275 👎 0


    class Solution {

        public boolean isAnagram(String s, String t) {
            if((s == null && t != null) ||
                    (s != null && t == null)||
                    (s != null && t != null && s.length() != t.length()) ){
                return false;
            }

            int[] alphDic = new int[256];
            for(int i = 0; i< s.length(); i++){
                alphDic[s.charAt(i)] += 1;
            }
            for(int j = 0; j < t.length(); j++){
                alphDic[t.charAt(j)] -= 1;
            }
            for (int count : alphDic) {
                if(count != 0){
                    return false;
                }
            }
            return true;

        }
    }
    public void testSolution1() {
        Solution solution = new Solution();
        boolean anagram = solution.isAnagram("a", "b");
        System.out.println(anagram);
    }


}
