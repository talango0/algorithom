package leetcode.程序员面试金典;
//给定一个32位整数 num，你可以将一个数位从0变为1。请编写一个程序，找出你能够获得的最长的一串1的长度。
//
//示例 1：
//
//输入: num = 1775(110111011112)
//输出: 8
//示例 2：
//
//输入: num = 7(01112)
//输出: 4
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/reverse-bits-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.ArrayList;

/**
 * @author mayanwei
 * @date 2023-05-17.
 */
public class _05_03_翻转数位{

    class Solution0{
        public int reverseBase(int num) {
            int preLen = 0, curLen = 0, maxLen = 0, bits = 32;
            while (bits-- > 0) {
                if ((num & 1) == 0) {
                    curLen -= preLen;
                    preLen = curLen + 1;
                }
                curLen++;
                maxLen = Math.max(maxLen, curLen);
                num = num >> 1;
            }
            return maxLen;
        }
    }

    /**
     * <pre>
     *  思路：可以认为每个整数数值都是由 0 序例和 1序列交替构成。每当发现一个0序列的长度位 1，我们即有可能将相邻的两个1序列合并。
     * ┌────────────────────────┐
     * │    11011101111         │
     * │ [0 ,4 ,1 ,3 ,1 ,2 ,21 ]│
     * │   0  1  0  1  0  1   0 │
     * └────────────────────────┘
     * 其中下表表示长度对应的是0序列还是1序例。
     * </pre>
     * 其时间复杂度和􏲈间复杂度􏲍为 O(b)，其中 b 为序列的长度
     */
    class Solution{
        public int reverseBits(int num) {
            if (num == -1) {
                return Integer.BYTES * 8;
            }
            ArrayList<Integer> sequences = getAlternatingSequences(num);
            return findLongestSequences(sequences);
        }

        /**
         * 返回所有序列的尺寸组成的链表。序列由0的个数开始（或许为0），之后是每个值的个数
         *
         * @param n
         * @return
         */
        private ArrayList<Integer> getAlternatingSequences(int n) {
            ArrayList<Integer> sequences = new ArrayList<>();
            int searchingFor = 0;
            int counter = 0;
            for (int i = 0; i < Integer.SIZE; i++) {
                if ((n & 1) != searchingFor) {
                    sequences.add(n);
                    searchingFor = n & 1; // 将 1 翻转为 0 或者 0翻转为1
                    counter = 0;
                }
                counter++;
                n >>>= 1;
            }
            sequences.add(counter);
            return sequences;
        }

        /**
         * 给定由 0 和 1 交替组成的序例大小，找出我们可以构造的最长的序列
         *
         * @param seq
         * @return
         */
        private int findLongestSequences(ArrayList<Integer> seq) {
            int maxSeq = 1;
            for (int i = 0; i < seq.size(); i += 2) {
                int zeroSeq = seq.get(i);
                int onesSeqRight = i - 1 >= 0 ? seq.get(i - 1) :0;
                int onesSeqLeft = i + 1 < seq.size() ? seq.get(i + 1) :0;

                int thisSeq = 0;
                if (zeroSeq == 1) { // 可以合并
                    thisSeq = onesSeqLeft + 1 + onesSeqRight;
                }
                else if (zeroSeq > 1) { //将0加入到其中一端
                    thisSeq = 1 + Math.max(onesSeqLeft, onesSeqRight);
                }
                else if (zeroSeq == 0) { // 无0，因此尝试加入到另一端
                    thisSeq = Math.max(onesSeqLeft, onesSeqRight);
                }
                maxSeq = Math.max(thisSeq, maxSeq);
            }
            return maxSeq;
        }
    }

    /**
     * 为了减少内存的使用，注意，我们并不是从始至终保留与序列长度相等的的空间。
     * 我们仅需要的比较前后相邻的两个1序列的长度即可。
     * <p>
     * 因此，在遍历的过程中，追踪当前1序列的长度和上一段1序列的长度。当发现一个比特位为0时，更新 previousLength 的值。
     * 如果下一个比特位是 1，那么 previousLength 应被设置为 currentLength 的值。
     * 如果下一比特位为 0，那么则不能合并这两个1序例。因此 previousLength 的值置为0
     */
    class Solution2{
        public int reverseBits(int num) {
            // 如果都是1，这已经是最长的序列了
            if (~num == 0) return Integer.SIZE;

            int currentLength = 0;
            int previousLength = 0;
            int maxLength = 1; // 我们总能找到至少包含一个1的序列
            while (num != 0) {
                if ((num & 1) == 1) {
                    currentLength++;
                }
                else if ((num & 1) == 0) {
                    // 更新为0（若下一位是0）或currentLength（若下一位是1）
                    previousLength = (num & 2) == 0 ? 0 :currentLength;
                    currentLength = 0;
                }
                maxLength = Math.max(previousLength + currentLength + 1, maxLength);
                num >>>= 1;
            }
            return maxLength;
        }
    }
}
