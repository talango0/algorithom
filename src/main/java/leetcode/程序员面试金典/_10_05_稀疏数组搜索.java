package leetcode.程序员面试金典;
//稀疏数组搜索。有个排好序的字符串数组，其中散布着一些空字符串，编写一种方法，找出给定字符串的位置。
//
//示例1:
//
// 输入: words = ["at", "", "", "", "ball", "", "", "car", "", "","dad", "", ""], s = "ta"
// 输出：-1
// 说明: 不存在返回-1。
//示例2:
//
// 输入：words = ["at", "", "", "", "ball", "", "", "car", "", "","dad", "", ""], s = "ball"
// 输出：4
//提示:
//
//words的长度在[1, 1000000]之间
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/sparse-array-search-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-05-31.
 */
public class _10_05_稀疏数组搜索{
    class Solution{
        public int findString(String[] words, String s) {
            int index = -1;
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(s)) {
                    index = i;
                    break;
                }
            }
            return index;
        }
    }

    class Solution1{
        public int findString(String[] words, String s) {
            if (words == null || s == null || s.isEmpty()) {
                return -1;
            }
            return search(words, s, 0, words.length - 1);
        }

        private int search(String[] words, String s, int first, int last) {
            if (first > last) {
                return -1;
            }
            // 将mid移到中间
            int mid = (first + last) / 2;
            // 若mid 是空字符串，就找出离它最近的非空字符串
            if (words[mid].isEmpty()) {
                int left = mid - 1;
                int right = mid + 1;
                while (true) {
                    if (left < first && right > last) {
                        return -1;
                    }
                    else if (right <= last && !words[right].isEmpty()) {
                        mid = right;
                        break;
                    }
                    else if (left >= first && !words[left].isEmpty()) {
                        mid = left;
                        break;
                    }
                    right++;
                    left--;
                }
            }
            // 检查字符串，如果必要继续递归
            if (s.equals(words[mid])) {
                return mid;
            }
            else if (words[mid].compareTo(s) < 0) {
                return search(words, s, mid + 1, last);
            }
            else {
                return search(words, s, first, mid - 1);
            }
        }
    }
}

