package leetcode.string;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Manacher 算法求字符串最大回文子串 LPS
 *
 * @author mayanwei
 * @date 2022-10-01.
 * @see _647_回文子串
 */
public class Manacher{
    /**
     * <pre>
     * ┌─────────────┐
     * │  Manacher   │
     * └─────────────┘
     * ┌─────────────────┐
     * │a b c b p b c b a│
     * │─────────────────│
     * │  ─────   ─────  │
     * └────────────▲────┘
     * ┌────────────┴────────────────────────────┐
     * │The first case:                          │
     * │MirrorCenter lies completely inside      │
     * │another palindrome                       │
     * └─────────────────────────────────────────┘
     * ┌─────────────────────────────────────────────────────────────────────────────┐
     * │The second case:                                                             │
     * │the palindrome at MirrorCenter extends outside the "Old" palindrome.         │
     * ├─────────────────┬───────────────────────────────────────────────────────────┤
     * │a b a b c        │                                                           │
     * │  ─────          │                                                           │
     * │─────            │The old palindrome could be "bab" with the Center.         │
     * │    ───          │being the second "b" and the MirroredCenter being the first│
     * │                 │"b".                                                       │
     * │                 │                                                           │
     * │                 │the palindrome at the MirrorCenter is "aba" and extends    │
     * │                 │beyond the boundaries of the "Old" palindrome, the longest │
     * │                 │palindrome at the second "b" can only extend up to the     │
     * │                 │border of the "Old" palindrome. We know this because if the│
     * │                 │character after the "Old" palindrome had been an "a"       │
     * │                 │instead of a "c".The "Old" palindrome would have been      │
     * │                 │longer.                                                    │
     * └─────────────────┴───────────────────────────────────────────────────────────┘
     * ┌─────────────────────────────────────────────────────────────────────────────┐
     * │The third and the last case:                                                 │
     * │the palindrome at MirrorCenter extends exactly up to he border of the "Old"  │
     * │palindrome.                                                                  │
     * ├─────────────────┬───────────────────────────────────────────────────────────┤
     * │a b c b p b c b p│                                                           │
     * │  ─────────────  │                                                           │
     * │  ─────          │                                                           │
     * │          ─────  │In this case, we don't know if the character after the     │
     * │                 │"Old" palindrome might make the palindrome at Center longer│
     * │                 │than the one at MirroredCenter. But we do know that the    │
     * │                 │palindrome at Center is at least as long as the one at     │
     * │                 │MirroredCenter.                                            │
     * │                 │                                                           │
     * │                 │In this case, Radius is initialized to the radius of the   │
     * │                 │palindrome at MirrorCenter and the search starts from here.│
     * └─────────────────┴───────────────────────────────────────────────────────────┘
     * </pre>
     * @param text
     * @return
     */
    public String findLongestPalindromicString(String text) {
        int N = text.length();
        if (N == 0) {
            return "";
        }

        // Position count
        N = 2 * N + 1;

        // LPS Length Array
        int[] L = new int[N];
        L[0] = 0;
        L[1] = 1;

        // centerPosition
        int C = 1;

        // centerRightPosition
        int R = 2;

        // currentRightPosition
        int i = 0;

        // currentLeftPosition
        int iMirror;
        int expand = -1;
        int diff = -1;
        int maxLPSLength = 0;
        int maxLPSCenterPosition = 0;
        int start = -1;
        int end = -1;

        // Uncomment it to print LPS Length array
        // printf("%d %d ", L[0], L[1]);
        for (i = 2; i < N; i++) {
            // Get currentLeftPosition iMirror for currentRightPosition i
            iMirror = 2 * C - i;
            // Reset expand - means no expansion required
            expand = 0;
            diff = R - i;

            // If currentRightPosition i is within centerRightPosition R
            if (diff >= 0) {
                // Case 1
                if (L[iMirror] < diff) {
                    L[i] = L[iMirror];
                }
                // Case 2
                else if (L[iMirror] == diff && R == N - 1) {
                    L[i] = L[iMirror];
                }
                // Case 3
                else if (L[iMirror] == diff && R < N - 1) {
                    L[i] = L[iMirror];
                    // Expansion required
                    expand = 1;
                }
                // Case 4
                else if (L[iMirror] > diff) {
                    L[i] = diff;
                    // Expansion required
                    expand = 1;
                }
            }
            else {
                L[i] = 0;
                // Expansion required
                expand = 1;
            }
            if (expand == 1) {
                // Attempt to expand palindrome centered at currentRightPosition i.
                // Here for odd positions, we compare characters and
                // if match then increment LPS Length by ONE
                // If even position, we just increment LPS by ONE without any character comparison
                try {
                    while (((i + L[i]) < N && (i - L[i]) > 0) && (((i + L[i] + 1) % 2 == 0)
                            || (text.charAt((i + L[i] + 1) / 2) == text.charAt((i - L[i] - 1) / 2)))) {
                        L[i]++;
                    }
                } catch (Exception e) {
                    assert true;
                }
            }

            // Track maxLPSLength
            if (L[i] > maxLPSLength) {
                maxLPSLength = L[i];
                maxLPSCenterPosition = i;
            }

            // If palindrome centered at
            // currentRightPosition i expand
            // beyond centerRightPosition R,
            // adjust centerPosition C based
            // on expanded palindrome.
            if (i + L[i] > R) {
                C = i;
                R = i + L[i];
            }

            //Uncomment it to print LPS Length array
            //System.out.print("%d ", L[i]);
        }

        start = (maxLPSCenterPosition - maxLPSLength) / 2;
        end = start + maxLPSLength - 1;

        //System.out.print("start: %d end: %d\n",
        //                  start, end);
        //System.out.println("LPS of string is " +
        //        text + " : ");
        //
        //for (i = start; i <= end; i++) {
        //    System.out.print(text.charAt(i));
        //}
        return text.substring(start, end + 1);
    }

    @Test
    public void test() {
        Manacher manacher = new Manacher();
        Assert.assertEquals("abcbabcba", manacher.findLongestPalindromicString("babcbabcbaccba"));
        Assert.assertEquals("abaaba", manacher.findLongestPalindromicString("abaaba"));
    }
}