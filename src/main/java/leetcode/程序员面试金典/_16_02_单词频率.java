package leetcode.程序员面试金典;
//设计一个方法，找出任意指定单词在一本书中的出现频率。
//
//你的实现应该支持如下操作：
//
//WordsFrequency(book)构造函数，参数为字符串数组构成的一本书
//get(word)查询指定单词在书中出现的频率
//示例：
//
//WordsFrequency wordsFrequency = new WordsFrequency({"i", "have", "an", "apple", "he", "have", "a", "pen"});
//wordsFrequency.get("you"); //返回0，"you"没有出现过
//wordsFrequency.get("have"); //返回2，"have"出现2次
//wordsFrequency.get("an"); //返回1
//wordsFrequency.get("apple"); //返回1
//wordsFrequency.get("pen"); //返回1
//提示：
//
//book[i]中只包含小写字母
//1 <= book.length <= 100000
//1 <= book[i].length <= 10
//get函数的调用次数不会超过100000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/words-frequency-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2023-06-06.
 */
public class _16_02_单词频率{
    class Solution1{
        class WordsFrequency{
            private Map<String, Integer> map = null;

            public WordsFrequency(String[] book) {
                map = new HashMap<String, Integer>(book.length);
                for (String s : book) {
                    map.putIfAbsent(s, 0);
                    map.put(s, map.get(s) + 1);
                }
                //Arrays.stream(book).parallel().forEach(a -> map.compute(a, (s, i) -> Objects.nonNull(i) ? i + 1 :1));
            }

            public int get(String word) {
                return map.getOrDefault(word, 0);
            }
        }

    }
}
