package leetcode.程序员面试金典;
//给定一个字符串，编写一个函数判定其是否为某个回文串的排列之一。
//
//回文串是指正反两个方向都一样的单词或短语。排列是指字母的重新排列。
//
//回文串不一定是字典当中的单词。
//
//
//
//示例1：
//
//输入："tactcoa"
//输出：true（排列有"tacocat"、"atcocta"，等等）
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/palindrome-permutation-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _01_04_回文排列{
    /**
     * 回文串:
     * 1个字符 + 2n个字符
     * 2n个字符
     * 用哈希表记录每次字符的次数，然后，遍历散列表以便确定出现奇数字符的次数不超过1个。
     * 时间复杂度O(n)
     */
    class Solution{
        public boolean canPermutePalindrome(String s) {
            int[] table = buildCharFrequency(s);
            return checkMaxOnOdd(table);
        }

        private boolean checkMaxOnOdd(int[] table) {
            boolean foundOdd = false;
            for (int i : table) {
                if ((i & 1) == 1) {
                    if (foundOdd) {
                        return false;
                    }
                    foundOdd = true;
                }
            }
            return true;
        }

        private int[] buildCharFrequency(String s) {
            int[] table = new int[256];
            for (char c : s.toCharArray()) {
                table[c]++;
            }
            return table;
        }
    }

    /**
     * 思路，
     * 可以用一个为整数值，或者位向量，每当看到一个字符，就将其映射到 0-26之间的一个数值（假设所有字符
     * 都是英语字母），然后切换该数值对应的比特为，在遍历以便结束后，需要检查是否只有一个比特位被置为1.
     */
    class Solution2{
        boolean canPermutePalindrome(String s) {
            int bitVector = createBitVector(s);
            return bitVector == 0 || checkExactlyOnceBitSet(bitVector);
        }

        /**
         * <pre>
         * 检测只有1个比特位被设置，将整数减1，并将其与原数值做 AND 操作。
         * 00010000 - 1 = 00001111
         * 00010000 & 00001111 = 0
         * </pre>
         *
         * @param bitVector
         * @return
         */
        private boolean checkExactlyOnceBitSet(int bitVector) {
            return (bitVector & bitVector - 1) == 0;
        }

        /**
         * 创建一个字符串对应的字节数组，对于每个值位i的字符，翻转第i位字节
         *
         * @param s
         * @return
         */
        private int createBitVector(String s) {
            int bitVector = 0;
            for (char c : s.toCharArray()) {
                int x = c - 'a';
                bitVector = toggle(bitVector, x);
            }
            return bitVector;
        }

        /**
         * 翻转整数中第 i 位字节
         *
         * @param bitVector
         * @param index
         * @return
         */
        private int toggle(int bitVector, int index) {
            if (index < 0) {
                return bitVector;
            }
            int mask = (1 << index);
            if ((bitVector & mask) == 0) {
                bitVector |= mask;
            }
            else {
                bitVector &= ~mask;
            }
            return bitVector;
        }


    }
}
