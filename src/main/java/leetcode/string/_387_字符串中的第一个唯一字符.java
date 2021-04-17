package leetcode.string;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class _387_字符串中的第一个唯一字符 {

    class Solution {
        public int firstUniqChar(String s) {
            if(s == null || s.length()==0){
                return -1;
            }
            Map<Character, Integer> charMap = new LinkedHashMap<Character, Integer>();
            for(int i = 0; i < s.length(); i++){
                char c = s.charAt(i);
                charMap.put(c , charMap.getOrDefault(c, 0)+1);
            }
            Set<Map.Entry<Character, Integer>> entries = charMap.entrySet();
            for (Map.Entry<Character, Integer> entry : entries) {
                if(entry.getValue() == 1){
                    return s.indexOf(entry.getKey());
                }

            }
            return -1;

        }
    }
}
