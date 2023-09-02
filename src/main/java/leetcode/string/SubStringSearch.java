package leetcode.string;

import leetcode.dp._28_实现strStr;
import leetcode.math.ModOp;

import java.math.BigInteger;
import java.util.Random;

/**
 * @see _28_实现strStr
 */
public class SubStringSearch{

    /**
     * 暴力法
     */
    public static int bruteForceSearch(String pat, String txt) {
        int M = pat.length(), N = txt.length();
        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break;
                }
            }
            if (j == M) {
                return i;
            }
        }
        return N;
    }


    /**
     * 字符串匹配之Knuth-Morris-Pratt
     */
    public static class KMP{

        //模拟指针的回退
        /*
        文本字符用 txt 表示
        模式用 pat 表示

        dfa[][] 记录匹配失败时模式指针 j 应该回退多远。
        对于每个 c ，比较了 c 和 pat.charAt(j) 之后，
            dfa[c][j] 表示的是和下个文本字符比较的 ** 模式字符的位置 ** 。
        在查找中， dfa[txt.charAt(i)][j] 是在比较了 txt.charAt(i) 和 pat.charAt(j) 之后 应该和 txt.charAt(i+1) 比较的模式字符位置。
         - 在匹配时，继续比较下一个字符，因此 dfa[pat.charAt(j)][j] = j+1。
         - 在不匹配时，不仅可以知道 txt.charAt(i) ,也可以知道正文中的前 j-1 个字符，它们就是模式中的 j-1 个字符。
         对于每个字符 c， 首先将模式字符串的一个副本覆盖在这 j 个字符之上（模式中的前j-1个字符以及字符c——需要判断的是当这些字符就是 txt.charAt(i-j+1..i) 应该怎么办）
         然后从左到右滑动这个副本知道所有重叠的字符都相互匹配（或者没有相匹配的字符）时停下来。
         这将指明模式字符串可能产生匹配的下一个位置。


         * 如何构造DFA
            在pat.charAt(j) 处匹配失败时，希望知道如果回退了文本指针，并在右移一位之后重新扫描已知的文本字符，DFA的状态是什么？
            其实我们并不想回退，只是想将DFA重置到适合的状态，这就好像已经回退过文本指针一样。
            这里的关键在于，需要重新扫描的文本字符正式pat.charAt(1) 和 pat.charAt(j-1) 之间，忽略了首字母是因为模式需要右移
            一位，忽略了最后一个字符是因为匹配失败。这些模式中的字符都是已知的，因此对于每个可能匹配失败的位置都可以预先知道重启DFA
            的正确状态。

        步骤：
        1. copies dfa[][X] to dfa[][j] while mismatch
        2. sets dfa[pat.charAt(j)][j] =  j+1 while match
        3. update X <- dfa[pat.charAt(j)][X]
         */

        private String pat;
        private int[][] dfa;

        public KMP(String pat) {
            // 通过pat构建dp
            //O(M)
            //有模式字符串构造dfa
            this.pat = pat;
            int M = pat.length();
            int R = 256;
            // dp[字符][状态] ,记录模式指针 j 应该回退多远
            dfa = new int[R][M];
            // base case 只有遇到 pat[0] 这个字符才能使状态从 0 转移到 1，遇到其他字符的话还停留在状态 0 （java默认初始化数组全为0）
            dfa[pat.charAt(0)][0] = 1;
            // X（影子状态）初始化为0，当前状态j 从1开始，所谓影子状态，就是和当前状态具有相同的前缀。
            // 状态j会把这个字符委托给状态X处理，也就是dp['A'][j] = dp['A'][X]
            // 为什么这样可以呢？因为：既然j这边已经确定字符 "A" 无法推进状态，只能回退，而且 KMP 算法就是要尽可能少的回退，以免多余的计算。
            // 那么j就可以去问问和自己具有相同前缀的X，如果X遇见 "A" 可以进行「状态推进」，那就转移过去，因为这样回退最少。
            // 你也许会问，这个X怎么知道遇到字符 "B" 要回退到状态 0 呢？因为X永远跟在j的身后，状态X如何转移，在之前就已经算出来了。
            // 动态规划算法不就是利用过去的结果解决现在的问题吗？
            // int X # 影子状态
            // for 0 <= j < M:
            //    for 0 <= c < 256:
            //        if c == pat[j]:
            //            # 状态推进
            //            dp[j][c] = j + 1
            //        else:
            //            # 状态重启
            //            # 委托 X 计算重启位置
            //            dp[j][c] = dp[X][c]
            for (int X = 0, j = 1; j < M; j++) {
                // 计算dfa[][j]
                for (int c = 0; c < R; c++) {
                    //匹配失败情况下的值
                    dfa[c][j] = dfa[c][X];
                }
                //设置匹配成功的值
                dfa[pat.charAt(j)][j] = j + 1;
                //更新重启状态
                X = dfa[pat.charAt(j)][X];
            }
        }

        public int search(String txt) {
            //O(N)
            //在txt上模拟dfa的运行
            int i, j, N = txt.length(), M = pat.length();
            for (i = 0, j = 0; i < N && j < M; i++) {
                j = dfa[txt.charAt(i)][j];
            }
            if (j == M) {
                return i - M;
            }
            else {
                return N;
            }
        }
    }


    /**
     * Boyer-Moore
     * 思路：构造方法根据模式字符串 pat 构造出了一张每个字符在最右位置的表格。
     * 查找算法会从右向左扫描模式字符串，并在匹配失败时通过跳跃表将文本 txt 中的字符
     * 和它在模式字符串中的最右位置对齐。
     */

    static class BoyerMoore{
        private int[] right;
        private String pat;

        BoyerMoore(String pat) {
            //计算跳跃表
            this.pat = pat;
            int R = 256, M = pat.length();
            right = new int[R];
            for (int c = 0; c < R; c++) {
                right[c] = -1;
            }
            for (int j = 0; j < M; j++) {
                right[pat.charAt(j)] = j;
            }
        }

        public int search(String txt) {
            //在txt中查找模式字符串
            int N = txt.length();
            int M = pat.length();
            int skip;
            for (int i = 0; i <= N - M; i += skip) {
                //does the pattern match the text at position i?
                skip = 0;
                for (int j = M - 1; j >= 0; j--) {
                    if (pat.charAt(j) != txt.charAt(i + j)) {
                        skip = j - right[txt.charAt(i + j)];
                        if (skip < 1) {
                            skip = 1;
                        }
                        break;
                    }
                }
                if (skip == 0) {
                    //找到匹配
                    return i;
                }
            }
            //未找到匹配
            return N;
        }
    }


    /**
     * 基于散列
     * 思路： 在构造函数总计算模式pat的散列值，并在文本txt中查找该散列值的匹配。
     * 指纹字符串查找算法。
     * <p>
     * 我们不要每次都去一个字符一个字符地比较子串和模式串，而是维护一个滑动窗口，运用滑动哈希算法一边滑动一边计算窗口中字符串的哈希值，
     * 拿这个哈希值去和模式串的哈希值比较，这样就可以避免截取子串，从而把匹配算法降低为 O(N)，这就是 Rabin-Karp 指纹字符串查找算法的核心逻辑。
     * <p>
     * 其实就是滑动哈希配合滑动窗口，滑动哈希就是处理数字的一个小技巧
     *
     * @see ModOp
     * @see _187_重复的DNA序列
     */
    static class RabinKarp{
        /**
         * 模式字符串（仅拉斯维加斯算法需要）
         */
        private String pat;
        /**
         * 模式hash值
         */
        private long patHash;
        /**
         * 模式字符串的长度
         */
        private int M;
        /**
         * 一个很大的素数
         */
        private long Q;
        /**
         * 字母表的大小
         */
        private int R = 256;
        /**
         * R^(M-1)%Q
         */
        private long RM;

        public RabinKarp(String pat) {
            this.pat = pat;
            this.M = pat.length();
            Q = longRandomPrime();
            RM = 1;
            for (int i = 1; i <= M - 1; i++) {
                RM = (R * RM) % Q;
            }
            patHash = hash(pat, M);
        }

        /**
         * 用除留余数法计算散列值。
         *
         * @param pat
         * @param m
         * @return
         */
        private long hash(String pat, int m) {
            //计算key[0..M-1]的散列值
            long h = 0;
            for (int j = 0; j < M; j++) {
                h = (R * h + pat.charAt(j)) % Q;
            }
            return h;
        }

        /**
         * 123
         * 0*10 + 1 -> 1
         * 1*10 + 2 ->  12
         * 12*10 + 3 -> 123
         * <p>
         * ABCC
         * <p>
         * 256*0 +  'A'-> 65
         * 65 * 256 + 'B' ->
         * <p>
         * (x + y)%Q = (x % Q + y %Q) % Q
         */


        private long longRandomPrime() {
            BigInteger prime = BigInteger.probablePrime(31, new Random());
            return prime.longValue();
        }

        /**
         * Monte Carlo
         *
         * @param txt
         * @param i
         * @return
         */
        private boolean check(String txt, int i) {
            // 对于 monte Carlo 检查模式与txt(i..i-M+1)的匹配
//            return true;
            for (int j = 0; j < M; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    return false;
                }
            }
            return true;
        }

        public int search(String txt) {
            int N = txt.length();
            long txtHash = hash(txt, M);
            if (patHash == txtHash && check(txt, 0)) {
                return 0;
            }
            for (int i = M; i < N; i++) {
                //减去第一个数，加入最后一个数字，再次检查
                txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
                txtHash = (txtHash * R + txt.charAt(i)) % Q;
                if (txtHash == patHash) {
                    // 可能重复，需要检查一下，但因为是大素数所以冲突的概率很小
                    if (check(txt, i - M + 1)) {
                        return i - M + 1;
                    }
                }
            }
            return N;
        }
    }

    public static void main(String[] args) {
        System.out.println((int) 'A');
        BigInteger bigInteger = BigInteger.probablePrime(31, new Random());
        long l = bigInteger.longValue();
        System.out.println(l);

    }
}
