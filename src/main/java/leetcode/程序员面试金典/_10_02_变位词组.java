package leetcode.程序员面试金典;
//编写一种方法，对字符串数组进行排序，将所有变位词组合在一起。变位词是指字母相同，但排列不同的字符串。
//
//注意：本题相对原题稍作修改
//
//示例:
//
//输入: ["eat", "tea", "tan", "ate", "nat", "bat"],
//输出:
//[
//  ["ate","eat","tea"],
//  ["nat","tan"],
//  ["bat"]
//]
//说明：
//
//所有输入均为小写字母。
//不考虑答案输出的顺序。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/group-anagrams-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Test;

import java.util.*;

/**
 * @author mayanwei
 * @date 2023-05-30.
 */
public class _10_02_变位词组{
    class Solution {
        public List<List<String>> groupAnagrams(String[] strs) {
            Map<String, List<String>> codeToGroup = new HashMap<>();
            for (String s:strs) {
                String code = encode(s);

                codeToGroup.putIfAbsent(code, new LinkedList<>());
                codeToGroup.get(code).add(s);
            }
            List<List<String>> ans = new ArrayList<>();
            for (List<String> group: codeToGroup.values()) {
                ans.add(group);
            }
            return ans;
        }
        String encode(String s) {
            char [] count = new char[26];
            for (char c:s.toCharArray()) {
                int delta = c - 'a';
                count[delta] ++;
            }
            return new String(count);
        }
    }


    // 原题：变位词组。编写一种方法，对字符串数组进行排序，将所有变位词排在相邻的位置。
    class Solution1{
        // 思考，只要求对数组中的字符串进行分组，将变位词。 并没有要求这些词按特顺序排列。
        // 界定异位词：将字符排序后，就很容易检查出新单词是否相同
        // 然后用通用排序算法（归并，快速），并修改比较器（comparator）。
        class AnagramComparator implements Comparator<String> {
            String sortChars(String s) {
                char[] content = s.toCharArray();
                Arrays.sort(content);
                return new String(content);
            }

            @Override
            public int compare(String s1, String s2) {
                return sortChars(s1).compareTo(sortChars(s2));
            }
        }
        /**
         * 这个算法的时间复杂度位 O(nlog(n)),这个通用排序算法获得最佳情况了，
         * 但实际上，不需要对整个数组进行排序，只需要对变位词进行分组放在一起即可。
         * @see Solution2
         */
        public void sort(String [] strs) {
            Arrays.sort(strs, new AnagramComparator());
        }
    }

    class Solution2 {
        /**
         * 散列表做到这一点。这个散列表会将排序后的单词映射到它的一个变位词列表。
         * @param strs
         */
        public void sort(String [] strs) {
            HashMapList<String, String> mapList = new HashMapList<>();
            // 将同为变位词分在同一组
            for (String s : strs) {
                String key = sortChars(s);
                mapList.put(key, s);
            }
            // 将散列表变为数组
            int index = 0;
            for (String key : mapList.keySet()) {
                ArrayList<String> list = mapList.get(key);
                for (String t : list) {
                    strs[index] = t;
                    index++;
                }
            }
        }

        String sortChars(String s) {
            char[] content = s.toCharArray();
            Arrays.sort(content);
            return new String(content);
        }


    }
    class HashMapList<T,E> {
        private HashMap<T,ArrayList<E>> map = new HashMap<T,ArrayList<E>>();
        // 在key出以链表形式插入
        public void put(T key,E item) {
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(item);
        }
        // 在key处插入一组项目
        public void put(T key, ArrayList<E> items) {
            map.put(key, items);
        }

        public ArrayList<E> get(T key) {
            return map.get(key);
        }

        public boolean containsKeyValue(T key, E value) {
            ArrayList<E> list = get(key);
            if (list == null) {
                return false;
            }
            return list.contains(value);
        }

        public Set<T> keySet(){
            return map.keySet();
        }

        @Override
        public String toString() {
            return map.toString();
        }
    }

    @Test
    public void test(){
        Solution1 solution1 = new Solution1();
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        solution1.sort(strs);
        System.out.println(Arrays.toString(strs));
        Solution2 solution2 = new Solution2();
        strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        solution2.sort(strs);
        System.out.println(Arrays.toString(strs));
    }


}
