package leetcode.程序员面试金典;
//URL化。编写一种方法，将字符串中的空格全部替换为%20。假定该字符串尾部有足够的空间存放新增字符，并且知道字符串的“真实”长度。
// （注：用Java实现的话，请使用字符数组实现，以便直接在数组上操作。）
//
//
//
//示例 1：
//
//输入："Mr John Smith    ", 13
//输出："Mr%20John%20Smith"
//示例 2：
//
//输入："               ", 5
//输出："%20%20%20%20%20"
//
//
//提示：
//
//字符串长度在 [0, 500000] 范围内。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/string-to-url-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _01_03_URL化{
    class Solution {
        public String replaceSpaces(String S, int length) {
            int spaceCount = 0, index, i = 0;
            char[] str = S.toCharArray();
            for (i = 0; i < length; i++) {
                if (str[i] == ' ') {
                    spaceCount++;
                }
            }
            int total= index = length + spaceCount * 2;
            if (length < str.length) {
                str[length] = '\0'; // 数组结束
            }
            for (i = length-1; i >=0 ; i--) {
                if (str[i] == ' ') {
                    str[index - 1] = '0';
                    str[index - 2] = '2';
                    str[index - 3] = '%';
                    index -= 3;
                } else {
                    str[index - 1] = str[i];
                    index--;
                }
            }
            return new String (str, 0, total);
        }
    }


    class Solution1 {
        public String replaceSpaces(String S, int length) {
            byte[] ans = S.getBytes();
            int i=ans.length-1,j=length-1;
            while(j >= 0){
                if(ans[j] == ' '){
                    ans[i--] = '0';
                    ans[i--] = '2';
                    ans[i--] = '%';
                }else{
                    ans[i--] = ans[j];
                }
                --j;
            }
            return new String(ans,i+1,ans.length-i-1);
        }
    }

    @Test
    public void test() {
        String s = "ds sdfs afs sdfa dfssf asdf             ";
        int l = 27;
        Solution1 solution = new Solution1();
        String s1 = solution.replaceSpaces(s, l);
        Assert.assertEquals("ds%20sdfs%20afs%20sdfa%20dfssf%20asdf", s1);
    }

}
