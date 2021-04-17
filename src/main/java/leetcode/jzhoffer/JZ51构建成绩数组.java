package leetcode.jzhoffer;

/**
 * @author mayanwei
 */
public class JZ51构建成绩数组 {


/**
 *     给定一个数组A[0,1,...,n-1],
 *     请构建一个数组B[0,1,...,n-1],
 *     其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。不能使用除法。
 *     （注意：规定B[0] = A[1] * A[2] * ... * A[n-1]，B[n-1] = A[0] * A[1] * ... * A[n-2];）
 *
 */
    public class Solution {
    /*
     B[i] = A[0] * A[1] * ... * A[i-1] * A[i+1] * ... * A[n-1]

     假设：
     left[i] = A[0] * ...* A[i-1]
     right[i] = A[i+1] * ... * A[n-1]
     则 B[i] = left[i] * right[i]

     B[0] = 1 * A[1] * A[2] * ... * A[n-1]
     B[1] = A[0] * 1 * A[2] * A[3] * ... * A[n-1]
     B[2] = A[0] * A[1] * 1 * A[3] * ... * A[n-1]
     B[3]
     ...
     B[n-1] = A[1] * A[2] * ... * A[n-2] * 1

     可知：
     left[i+1] = A[0] * ... * A[i]
     right[i+1] = A[i+2] * ... * A[n-1]

     于是：
     left[i+1] = left[i] * A[i]
     right[i] = right[i+1] * A[i+1]

    class Solution {
    public:
        vector<int> multiply(const vector<int>& A) {
            vector<int> B(A.size(), 1);
            for (int i=1; i<A.size(); ++i) {
                B[i] = B[i-1] * A[i-1]; // left[i]用B[i]代替
            }
            int tmp = 1;
            for (int j=A.size()-2; j>=0; --j) {
                tmp *= A[j+1]; // right[i]用tmp代替
                B[j] *= tmp;
            }
            return B;
        }
    };
     */

        public int[] multiply(int[] A) {
            int[] B = new int[A.length];
            int temp = 0;
            for (int i = 0; i < A.length; i++) {
                temp = A[i];
                A[i] = 1;
                B[i] = 1;
                for (int j = 0; j < A.length; j++) {
                    B[i] *= A[j];
                }
                A[i] = temp;
            }
            return B;
        }
    }
}
