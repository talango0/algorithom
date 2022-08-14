package leetcode.string;
//DNA序列 由一系列核苷酸组成，缩写为 'A', 'C', 'G' 和 'T'.。
//
//例如，"ACGAATTCCG" 是一个 DNA序列 。
//在研究 DNA 时，识别 DNA 中的重复序列非常有用。
//
//给定一个表示 DNA序列 的字符串 s ，返回所有在 DNA 分子中出现不止一次的 长度为 10 的序列(子字符串)。你可以按 任意顺序 返回答案。
//
//示例 1：
//
//输入：s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
//输出：["AAAAACCCCC","CCCCCAAAAA"]
//示例 2：
//
//输入：s = "AAAAAAAAAAAAA"
//输出：["AAAAAAAAAA"]
//提示：
//
//0 <= s.length <= 105
//s[i]=='A'、'C'、'G' or 'T'
//Related Topics
//
//👍 396, 👎 0

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-08-14.
 * @see SubStringSearch.RabinKarp
 */
public class _187_重复的DNA序列{
    class Solution {
        // O(NL)
        public List<String> findRepeatedDnaSequences(String s) {
            int n = s.length();
            // 记录出现过的子串
            HashSet<String> seen = new HashSet<>();
            //  记录重复出现过多次的子串
            //  主要要用 HashSet 防止出现重复的结果
            HashSet<String> res = new HashSet<>();
            for (int i = 0; i + 10 <= n; i++) {
                String subStr = s.substring(i, i+10);
                if (seen.contains(subStr)) {
                    // 之前出现过，找到一个重复的
                    res.add(subStr);
                }
                else {
                    // 之前没出现，加入集合
                    seen.add(subStr);
                }
            }
            return new LinkedList<String>(res);
        }
    }


    class Solution2 {
        // O(N),而窗口滑动的过程中的操作耗时都是 O(1)（给 res 添加子串的过程不算），所以整体的时间复杂度是 O(N)
        public List<String> findRepeatedDnaSequences(String s) {
            // 先把字符串转变成四进制的数字组合
            int [] nums = new int[s.length()];
            for (int i = 0; i < nums.length; i ++) {
                switch (s.charAt(i)) {
                    case 'A':
                        nums[i] = 0;
                        break;
                    case 'C':
                        nums[i] = 1;
                        break;
                    case 'G':
                        nums[i] = 2;
                        break;
                    case 'T':
                        nums[i] = 3;
                        break;
                }
            }

            // 记录重复出现的哈希值
            HashSet<Integer> seen = new HashSet<>();
            // 记录重复出现的字符串结果
            HashSet<String> res = new HashSet<>();

            // 数字位数
            int L = 10;
            // 进制
            int R = 4;
            // 存储 R^(L-1) 的结果
            int RL = (int) Math.pow(R, L-1);
            // 维护滑动窗口中字符串的哈希值
            int windowHash = 0;

            // 滑动窗口，时间复杂度为 O(N)
            int left = 0, right = 0;
            while (right < nums.length) {
                // 扩大窗口，移入字符，并维护窗口哈希值（在最低位添加数字）
                windowHash = R * windowHash + nums[right];
                right ++;

                // 当子串的长度达到10
                if (right - left == L) {
                    // 根据 哈希值 判断是否曾经出现过相同的子串
                    if (seen.contains(windowHash)) {
                        // 当前窗口中的子串是否出现过
                        res.add(s.substring(left, right));
                    }
                    else {
                        // 当前窗口中的子串之前没有出现过，记下来
                        seen.add(windowHash);
                    }
                    // 缩小窗口，移出字符，并维护窗口哈希值（删除最高位数字）
                    windowHash = windowHash - nums[left] * RL;
                    left ++;
                }
            }
            // 转化成题目要求的 list 类型
            return new LinkedList<String>(res);
        }
    }
}
