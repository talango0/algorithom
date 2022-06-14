package leetcode.dp;

import java.util.HashSet;
import java.util.Set;

public class _518_零钱兑换2 {
    class Solution {
        public int change(int amount, int[] coins) {
            int[] dp = new int[amount + 1];
            dp[0] = 1;
            for (int coin : coins) {
                for (int i = coin; i <= amount; i++) {
                    dp[i] += dp[i - coin];
                }
            }
            return dp[amount]; }
    }

    @Deprecated
    class Solution1{
        private int res = 0;
        Set<Integer> numCoinSet = new HashSet<>();
        public int change(int amount, int[] coins) {

            fun(amount, coins, 0);
            return res;
        }
        private void fun(int amount,int [] coins, int coinCount) {
            if (amount < 0){return;}
            else if (amount == 0){
                if(!numCoinSet.contains(coinCount)){
                    numCoinSet.add(coinCount);
                    res++;
                }
            }
            else{
                coinCount = coinCount+1;
                for (int coin: coins) {
                    if (amount<coin) {
                        continue;
                    }
                    fun(amount-coin,coins,coinCount);
                }
            }
        }
    }
}
