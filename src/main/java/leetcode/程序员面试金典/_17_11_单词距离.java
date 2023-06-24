package leetcode.程序员面试金典;
//有个内含单词的超大文本文件，给定任意两个不同的单词，找出在这个文件中这两个单词的最短距离(相隔单词数)。如果寻找过程在这个文件中会重复多次，
// 而每次寻找的单词不同，你能对此优化吗?
//
//示例：
//
//输入：words = ["I","am","a","student","from","a","university","in","a","city"], word1 = "a", word2 = "student"
//输出：1
//提示：
//
//words.length <= 100000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/find-closest-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-06-23.
 */
public class _17_11_单词距离{
    /**
     * 假设 word1 和 word2 谁在前后无关紧要。
     * 只需要遍历一次这个文件，在遍历期间，我们会记下最后看见 word1 和 word2 的地方，并把它们的位置存入
     * location1 和 location2 中，如果前面的位置比已知的最优位置更好，则更新已知最优位置。
     */
    class Solution{
        public int findClosest(String[] words, String word1, String word2) {
            LocationPair best = new LocationPair(-1, -1);
            LocationPair current = new LocationPair(-1, -1);
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (word.equals(word1)) {
                    current.location1 = i;
                    best.updateWithMin(current);
                }
                else if (word.equals(word2)) {
                    current.location2 = i;
                    best.updateWithMin(current); // 如果更短，则更新值
                }
            }
            return best.distance();
        }

        class LocationPair{
            int location1, location2;

            public LocationPair(int first, int second) {
                this.location1 = first;
                this.location2 = second;
            }

            public void setLocation(int location1, int location2) {
                this.location1 = location1;
                this.location2 = location2;
            }

            public void setLocation(LocationPair pair) {
                setLocation(pair.location1, pair.location2);
            }

            public int distance() {
                return Math.abs(location1 - location2);
            }

            public boolean isValid() {
                return location1 >= 0 && location2 >= 0;
            }

            public void updateWithMin(LocationPair loc) {
                if (!isValid() || loc.distance() < distance()) {
                    setLocation(loc);
                }
            }
        }
    }

    /**
     * <pre>
     * 上述代码要被重复调用（查询其他单词的最短距离），则可以构造一个散列表，记录每个单词及其出现的位置。
     * 我们只需读取一次单词列表。在那之后可以使用形似的算法，但是只需要对位置进行迭代即可。
     *
     * listA: {1,2,9,15,25}
     * listB: {4,10,19}
     *
     * 假设pA 和 pB指向每个列表的头部。我们的目标是让pA 和 pB 指向尽可能接近的值。
     * 第一对可能的值是 (1,4)
     * 移动 pA , 第二对可能的值时 (2,4) ，比第一对更优，选择它。
     * 再次移动 pA, 得到 (9,4),比之前要差。
     * 移动 pB, 得到 (9,10),比之前的最优解好，更新。
     * 接下来是 (15,1o),然后(15,19),再然后是 (25,19)
     *
     *
     * 该算法的预处理步骤花费 O(N)的时间，其中 N 为字符串中单词的数目。
     * 找到最接近的位置将会花费O(A + B)时间，其中A是第一个单词出现的次数，B是第二个单词出现的次数。
     * </pre>
     */
    class Solution2{
        public int findClosest(String[] words, String word1, String word2) {
            HashMap<String, List<Integer>> wordLocation = getWordLocation(words);
            LocationPair best = findMinDistancePair(wordLocation.get(word1), wordLocation.get(word2));
            return best.distance();
        }

        LocationPair findMinDistancePair(List<Integer> array1, List<Integer> array2) {
            if (array1 == null || array2 == null || array1.size() == 0 || array2.size() == 0) {
                return null;
            }
            int index1 = 0, index2 = 0;
            LocationPair best = new LocationPair(array1.get(0), array2.get(0));
            LocationPair current = new LocationPair(array1.get(0), array2.get(0));
            while (index1 < array1.size() && index2 < array2.size()) {
                current.setLocation(array1.get(index1), array2.get(index2));
                best.updateWithMin(current); // 如果更短，则更新值
                if (current.location1 < current.location2) {
                    index1++;
                }
                else {
                    index2++;
                }
            }
            return best;
        }

        HashMap<String, List<Integer>> getWordLocation(String[] words) {
            HashMap<String, List<Integer>> locations = new HashMap<>();
            for (int i = 0; i < words.length; i++) {
                List<Integer> indexes = locations.getOrDefault(words[i], new ArrayList<>());
                indexes.add(i);
                locations.put(words[i], indexes);
            }
            return locations;
        }

        class LocationPair{
            int location1, location2;

            public LocationPair(int first, int second) {
                this.location1 = first;
                this.location2 = second;
            }

            public void setLocation(int location1, int location2) {
                this.location1 = location1;
                this.location2 = location2;
            }

            public void setLocation(LocationPair pair) {
                setLocation(pair.location1, pair.location2);
            }

            public int distance() {
                return Math.abs(location1 - location2);
            }

            public boolean isValid() {
                return location1 >= 0 && location2 >= 0;
            }

            public void updateWithMin(LocationPair loc) {
                if (!isValid() || loc.distance() < distance()) {
                    setLocation(loc);
                }
            }
        }

    }

    class Solution3{
        public int findClosest(String[] words, String word1, String word2) {
            int L = 0, N = words.length, R = 0, min = N;
            while (L < N && R < N) {
                while (L < N && !words[L].equals(word1)) L++;
                while (R < N && !words[R].equals(word2)) R++;
                if (L < R && R < N) min = Math.min(min, R - L++);
                if (R < L && L < N) min = Math.min(min, L - R++);
                if (min == 1) return 1;
            }
            return min;
        }
    }
}
