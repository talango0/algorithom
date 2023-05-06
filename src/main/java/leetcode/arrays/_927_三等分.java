package leetcode.arrays;

import java.util.Arrays;
//给定一个由 0 和 1 组成的数组 arr ，将数组分成  3 个非空的部分 ，使得所有这些部分表示相同的二进制值。
//
//如果可以做到，请返回任何 [i, j]，其中 i+1 < j，这样一来：
//
//arr[0], arr[1], ..., arr[i] 为第一部分；
//arr[i + 1], arr[i + 2], ..., arr[j - 1] 为第二部分；
//arr[j], arr[j + 1], ..., arr[arr.length - 1] 为第三部分。
//这三个部分所表示的二进制值相等。
//如果无法做到，就返回 [-1, -1]。
//
//注意，在考虑每个部分所表示的二进制时，应当将其看作一个整体。例如，[1,1,0] 表示十进制中的 6，
// 而不会是 3。此外，前导零也是被允许的，所以 [0,1,1] 和 [1,1] 表示相同的值。
//
// 
//
//示例 1：
//输入：arr = [1,0,1,0,1]
//输出：[0,3]
//
//示例 2：
//输入：arr = [1,1,0,1,1]
//输出：[-1,-1]
//
//示例 3:
//输入：arr = [1,1,0,0,1]
//输出：[0,2]
// 
//
//提示：
//
//3 <= arr.length <= 3 * 104
//arr[i] 是 0 或 1
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/three-equal-parts
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2022-09-11.
 */
public class _927_三等分{

    class Solution{
        //[0, i],[i+1,j-1],[j,n-1]
        public int[] threeEqualParts(int[] arr) {
            int[] ans = new int[]{-1, -1};
            int n = arr.length;
            // s 为arr中1 的个数，最终每一块1的数量相同，即 t = s/3 ,且s能够被3整除，否则不可分。
            int s = 0;
            for (int x : arr) {
                s += x;
            }
            if (s % 3 != 0) {
                return ans;
            }
            int t = s / 3;
            if (t == 0) {
                return new int[]{0, n - 1};
            }
            //arr 中的第i个1个，第t个，第 t+1 个， 第 2t 个， 第 2t+1 个 1，形成了3个区间 [i1, j1],[i2, j2],[i3, j3]
            int i1 = -1, j1 = -1, i2 = -1, j2 = -1, i3 = -1, j3 = -1;
            int su = 0;
            for (int i = 0; i < n; i++) {
                if (arr[i] == 1) {
                    su += 1;
                    if (su == 1) {
                        i1 = i;
                    }
                    if (su == t) {
                        j1 = i;
                    }
                    if (su == t + 1) {
                        i2 = i;
                    }
                    if (su == 2 * t) {
                        j2 = i;
                    }
                    if (su == 2 * t + 1) {
                        i3 = i;
                    }
                    if (su == 3 * t) {
                        j3 = i;
                    }
                }
            }

            // The array is in the form  W[i1, j1], X[j2, j2], Y[i3,j3] Z where [i1, j1] is a block of 1s, etc.
            int[] part1 = Arrays.copyOfRange(arr, i1, j1 + 1);
            int[] part2 = Arrays.copyOfRange(arr, i2, j2 + 1);
            int[] part3 = Arrays.copyOfRange(arr, i3, j3 + 1);
            if (!Arrays.equals(part1, part2) || !Arrays.equals(part1, part3)) {
                return ans;
            }

            // x, y, z: the number of zeros after part 1,2,3
            // for [1,1,0,1] ,3-1-1 = 1
            int x = i2 - j1 - 1;
            int y = i3 - j2 - 1;
            int z = n - j3 - 1;
            if (x < z || y < z) {
                return ans;
            }
            return new int[]{j1 + z, j2 + z + 1};

        }
    }

    class Solution2{
        /**
         * <pre>
         * 思路：
         * 首先判断 1 的总个数能否被 3 整除，然后从末尾先选出第一个包含正确个数个 1 且不包含前导零的序列
         * （这个序列可以确定，因为它一定属于第三部分，并且添加若干个前导零值也不会变）；根据这个序列再划分第一、二部分。
         *
         * 首先获取不考虑前导零应该有的序列（此时 seperator2 不是指代第二个分割点）。
         *
         * 利用第一部分去除前导零的序列 [start1, seperator1] 检查 [seperator2, ...)， 如果不相等，说明第二部分分割失败。
         *
         * 如果第二部分也分割成功，此时序列已经被分成三个部分：
         * 第一部分是前导零 [0, start1) 和正式序列 [start1, seperator1]构成的第一部分；
         * 第二部分是前导零加上正式序列的(seperator1, seperator2)；
         * 那么剩下的自然分给第三部分。
         *
         * 第三部分一定是合法的，首先，我们已经保证第一二部分的 1 的个数都是 sum 个，
         * 且是由末尾的第一个包含 sum 个 1 的不含前导零的序列确定的，那么第三部分一定包含了末尾的这个序列，
         * 前面可能还有第二部分之后的若干个前导零。这时候两个分割点都确定了，要注意 seperator2 是第三部分开始的下标，所以不需要再自减。
         * </pre>
         * @param arr
         * @return
         */
        public int[] threeEqualParts(int[] arr) {

            int n = arr.length;
            // 首先对整个数组求和，要能够分成三个相同的二进制数，1 的个数必须能被三等分。
            int sum = 0;
            for (int num : arr) {
                sum += num;
            }
            // 如果 1 的个数不是 3 的倍数，分割一定不会成功。
            if (sum % 3 != 0) {
                return new int[]{-1, -1};
            }
            // 如果没有 1，返回头尾两个分割点（题目没说明白）。
            if (sum == 0) {
                return new int[]{0, n - 1};
            }
            // 此时的 sum 是每一份应该有的 1 的个数。
            sum /= 3;
            /**首先获取不考虑前导零应该有的序列（此时 seperator2 不是指代第二个分割点）。**/
            int count = 0;
            int seperator2 = n - 1;
            while (count < sum) {
                if (arr[seperator2--] == 1) {
                    count++;
                }
            }
            seperator2++;
            /** 到现在为止 [seperator2, n) 就是除掉前导零每一部分应该有的序列，先求出第一个分割点。 **/
            int seperator1;
            // 忽略第一部分的前导零。
            for (seperator1 = 0; seperator1 < n && arr[seperator1] == 0; seperator1++) ;
            // start1 标记第一部分除去前导零开始的位置。
            int start1 = seperator1;
            /** [start1, ...) 和 [seperator2, n) 比较，如果存在对应位置不相等，就说明分割失败，
             否则 seperator1 应该可以增加到 n - seperator2 + start1 处。 **/
            for (int i = seperator2; i < n; seperator1++, i++) {
                if (arr[seperator1] != arr[i]) {
                    return new int[]{-1, -1};
                }
            }
            // 如果分割成功，seperator1 = n - seperator2 + start1（第二部分的起点），减去 1 就是第一个分割点的位置
            seperator1--;
            // seperator2 作为第二个分割点，同样地先忽略第二部分的前导零
            for (seperator2 = seperator1 + 1; seperator2 < n && arr[seperator2] == 0; seperator2++) ;
            /** 利用第一部分去除前导零的序列 [start1, seperator1] 检查 [seperator2, ...)，
             如果不相等，说明第二部分分割失败。 **/
            for (int i = start1; i <= seperator1; i++, seperator2++) {
                if (arr[i] != arr[seperator2]) {
                    return new int[]{-1, -1};
                }
            }
            /** 如果第二部分也分割成功，此时序列已经被分成三个部分：第
             一部分是前导零 [0, start1) 和正式序列 [start1, seperator1]
             构成的第一部分；第二部分是前导零加上正式序列的
             (seperator1, seperator2)；那么剩下的自然分给第三部分。
             第三部分一定是合法的，首先，我们已经保证第一二部分的 1
             的个数都是 sum 个，且是由末尾的第一个包含 sum 个 1 的
             不含前导零的序列确定的，那么第三部分一定包含了末尾的这
             个序列，前面可能还有第二部分之后的若干个前导零。这时候
             两个分割点都确定了，要注意 seperator2 是第三部分开始的
             下标，所以不需要再自减。 **/
            return new int[]{seperator1, seperator2};
        }
    }


}
