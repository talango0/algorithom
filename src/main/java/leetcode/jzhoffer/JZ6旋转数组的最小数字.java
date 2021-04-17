package leetcode.jzhoffer;


import java.util.Arrays;

/*
题目描述
把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
示例1
输入

[3,4,5,1,2]
返回值
1

 */
public class JZ6旋转数组的最小数字 {
/**
 * 暴力法
 */
public class Solution {
    public int minNumberInRotateArray(int [] array) {
        if(array == null || array.length<1){
            return 0;
        }
        Arrays.sort(array);
        return array[0];
    }
}


/**
 * 二分查找法
 * 这里我们把target 看作是右端点，来进行分析，那就要分析以下三种情况，看是否可以达到上述的目标。
 *
 * 情况1，arr[mid] > target：4 5 6 1 2 3
 * arr[mid] 为 6， target为右端点 3， arr[mid] > target, 说明[first ... mid] 都是 >= target 的，因为原始数组是非递减，所以可以确定答案为 [mid+1...last]区间,所以 first = mid + 1
 * 情况2，arr[mid] < target: 5 6 1 2 3 4
 * arr[mid] 为 1， target为右端点 4， arr[mid] < target, 说明答案肯定不在[mid+1...last]，但是arr[mid] 有可能是答案,所以答案在[first, mid]区间，所以last = mid;
 * 情况3，arr[mid] == target:
 * 如果是 1 0 1 1 1， arr[mid] = target = 1, 显然答案在左边
 * 如果是 1 1 1 0 1, arr[mid] = target = 1, 显然答案在右边
 * 所以这种情况，不能确定答案在左边还是右边，那么就让last = last - 1;慢慢缩少区间，同时也不会错过答案。
 */

public class Solution2{
    public int minNumberInRotateArray(int [] rotateArray){
        if (rotateArray == null || rotateArray.length == 0){
            return 0;
        }
        int first = 0, last = rotateArray.length - 1;
        while (first < last) { // 最后剩下一个元素，即为答案
            if (rotateArray[first] < rotateArray[last]) { // 提前退出
                return rotateArray[first];
            }
            int mid = first + ((last - first) >> 1);
            if (rotateArray[mid] > rotateArray[last]) { // 情况1
                first = mid + 1;

            }else if(rotateArray[mid] < rotateArray[last]) { //情况2
                last = mid;
            }else{ // 情况3
                --last;
            }
        }
        return rotateArray[first];
    }
}
}
