package leetcode.math;
//给你一个字符串 columnTitle ，表示 Excel 表格中的列名称。返回 该列名称对应的列序号 。
//
// 例如：
//
//
//A -> 1
//B -> 2
//C -> 3
//...
//Z -> 26
//AA -> 27
//AB -> 28
//...
//
//
//
// 示例 1:
//
//
//输入: columnTitle = "A"
//输出: 1
//
//
// 示例 2:
//
//
//输入: columnTitle = "AB"
//输出: 28
//
//
// 示例 3:
//
//
//输入: columnTitle = "ZY"
//输出: 701
//
//
//
// 提示：
//
//
// 1 <= columnTitle.length <= 7
// columnTitle 仅由大写英文组成
// columnTitle 在范围 ["A", "FXSHRXW"] 内
//
//
// Related Topics 数学 字符串 👍 348 👎 0


/**
 * @author mayanwei
 * @date 2022-09-24.
 */
public class _171_Excel表列序号{
    class Solution{
        /**
         * number = sum a_i * 26^i, i : 1->n-1, n为 str的长度
         * 时间复杂度： O(n), 空间复杂度 O(1)
         */
        public int titleToNumber(String columnTitle) {
            int number = 0;
            int multiple = 1;
            for (int i = columnTitle.length() - 1; i >= 0; i--) {
                int k = columnTitle.charAt(i) - 'A' + 1;
                number += k * multiple;
                multiple *= 26;
            }
            return number;
        }
    }
}
