package leetcode.graph;
//假设你是一个专业的狗仔，参加了一个 n 人派对，其中每个人被从 0 到 n - 1 标号。
// 在这个派对人群当中可能存在一位 “名人”。
// 所谓 “名人” 的定义是：其他所有 n - 1 个人都认识他/她，而他/她并不认识其他任何人。
//
//现在你想要确认这个 “名人” 是谁，或者确定这里没有 “名人”。
// 而你唯一能做的就是问诸如 “A 你好呀，请问你认不认识 B呀？” 的问题，以确定 A 是否认识 B。
// 你需要在（渐近意义上）尽可能少的问题内来确定这位 “名人” 是谁（或者确定这里没有 “名人”）。
//
//在本题中，你可以使用辅助函数 bool knows(a, b) 获取到 A 是否认识 B。请你来实现一个函数 int findCelebrity(n)。
//
//派对最多只会有一个 “名人” 参加。
// 若 “名人” 存在，请返回他/她的编号；若 “名人” 不存在，请返回 -1。

import java.time.OffsetTime;

public class _277_搜寻名人 {
    // 名人节点的入度为 n-1， 初度为0
    // n 个人的社交关系是如何表示的？领接表 vs 邻接矩阵
    // 对于名人问题，要经常访问两个人是否认识，也就是两个节点是否相邻，因此用邻接矩阵。
    // 因此名流问题描述成如下这样：
    // 给你输入一个大小为 n*n 的矩阵（邻接矩阵）graph 表示一幅有 n 个节点的图，每个人都是图中的一个节点，编号为 0~n-1
    // 如果 graph[i][j] == 1代表第i个人认识第j个人，如果 graph[i][j] == 0，则表示第i个人不认识第j个人


    class Solution {
        private boolean [][] knows;
        // 请实现，返回【名人】编号
        // 暴力法
        // 内层循环可以优化，虽然判断一个人【是名人】必须用一个 for 循环，但判断一个人【不是名人】就不用那么麻烦
        int findCelebrity(int n){
            for (int cand = 0; cand < n; cand++) {
                int other;
                for ( other = 0; other < n; other++) {
                    if (cand == other) {
                        continue;
                    }
                    // 保证其他人都认识 cand，且 cand 不认识任何其他人
                    // 否则 cand 就不可能是名人
                    if (knows(cand, other) || !knows(other, cand)) {
                        break;
                    }
                }
                if (other == n) {
                    // 找到名人
                    return cand;
                }
            }
            // 没有一个人符合名人特性
            return -1;
        }

        // 可以直接调用，能够返回 i 是否认识 j
        boolean knows(int i, int j){
            return knows[i][j];
        }
    }

    class Solution1 {
        private boolean [][] knows;
        // 请实现，返回【名人】编号
        int findCelebrity(int n){
            // 先假设 cand 是名人
            int cand = 0;
            for (int other = 1; other<n; other++) {
                if (!knows(other, cand) || knows(cand, other)){
                    // cand 不可能是名人，排除
                    // 假设 other是名人
                    cand = other;
                }
                else {
                    // other 不可能是名人，排除
                    // 什么都不用做，继续假设 cand 是名人
                }
            }

            // 现在的cand 是排除的最后结果，但不能保证一定是悯人
            for (int other = 0; other<n; other++) {
                if (cand == other) continue;
                // 需要保证其他人都认识 cand，且 cand 不认识任何其他人
                if (!knows(other, cand) || knows(cand, other)) {
                    return -1;
                }
            }
            return cand;
        }

        // 可以直接调用，能够返回 i 是否认识 j
        boolean knows(int i, int j){
            return knows[i][j];
        }
    }
}
