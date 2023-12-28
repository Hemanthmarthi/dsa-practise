package com.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DynamicProgrammingProblems {

    /*
    Asymptotic complexity in terms of `n` = size of the input list:
    * Time: O(n^2).
    * Auxiliary space: O(n).
    * Total space: O(n).
    */
    static Integer cutTheRodToMaximizeProfit(ArrayList<Integer> price) {
        int rod_length = price.size();

        // dp[i] stores the maximum profit that can be made for rod of length i
        int[] dp = new int[rod_length + 1];
        dp[0] = 0;

        for (int i = 1; i <= rod_length; i++) {
            // Price for rod of length i is stored in (i-1)th index of price, here we choose not to cut
            // the rod of length i into smaller pieces and put the value for profit against dp[i]
            dp[i] = price.get(i - 1);

            // Cut rod into lengths < i and try to get maximum profit
            for (int j = 1; j < i; j++) {

                // Sum up profit obtained by cutting a length of j, i.e. price.get(j-1) and the profit
                // obtained from remaining rod length, i.e. dp[i-j]
                dp[i] = Math.max(dp[i], price.get(j - 1) + dp[i - j]);
            }
        }

        return dp[rod_length];
    }

    /*
    Asymptotic complexity in terms of `n` = size of the input list:
    * Time: O(n^2).
    * Auxiliary space: O(n).
    * Total space: O(n).
    */
    static Integer cutTheRodToMaximizeProfit2(ArrayList<Integer> price) {
        Integer rod_length = price.size();

        // dp[i] stores the maximum profit that can be made for rod of length i
        int[] dp = new int[rod_length + 1];

        return cutTheRodToMaximizeProfitHelper2(price, price.size(), dp);
    }
    static int cutTheRodToMaximizeProfitHelper2(ArrayList<Integer> price, int rod_length, int[] dp) {
        if (rod_length <= 0) return 0;
        if (dp[rod_length] != 0) return dp[rod_length];

        int max_profit = 0;
        for (int i = 1; i <= rod_length; i++) {

            // Cut a piece of length i from the rod and recurse for the remaining length,
            // i.e. (rod_length - i)
            max_profit = Math.max(max_profit,
                    price.get(i - 1) + cutTheRodToMaximizeProfitHelper2(price, rod_length - i, dp));
        }

        dp[rod_length] = max_profit;
        return max_profit;
    }

    static Integer numberOfWaysToMakeChange(ArrayList<Integer> coins, Integer amount) {
        // Write your code here.
        int[] dp = new int[amount+1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int a = 1; a <= amount; a++) {
                if (coin <= a) {
                    dp[a] = dp[a]%1000000007+dp[a-coin]%1000000007;
                }
            }
        }
        return dp[amount]%1000000007;
    }

    /*
        Asymptotic complexity in terms of length of string `n`, length of dictionary `m`, and maximum length of any word in the dictionary `w`:
            * Time: O(n^2 * w).
            * Auxiliary space: O(n + m * w).
            * Total space: O(n + m * w).
    */
    public static boolean wordBreak(String s, ArrayList<String> wordsDictionary) {
        Set<String> wordsSet = new HashSet<>(wordsDictionary);
        int n = s.length();
        // dp[i] stores a boolean value, indicating whether it is possible to break a string starting from index i to n - 1.
        boolean[] dp = new boolean[n + 1];
        dp[n] = true;

        for (int i = n - 1; i >= 0; i--) {
            StringBuilder word = new StringBuilder();
            for (int j = i; j < n; j++) {
                word.append(s.charAt(j));
                // As the maximum word length in the dictionary is 20.
                if (word.length() > 20) {
                    break;
                }
                if (dp[j + 1] && wordsSet.contains(word.toString())) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[0];
    }

    /*
    Asymptotic complexity in terms of the size of the grid `n` * `m`:
    * Time: O(n * m).
    * Auxiliary space: O(n * m).
    * Total space: O(n * m).
    */
    static final int mod = 1000000007;
    // This function will return number of unique paths from the [1, 1] to the [n, m]
    static int getNumberOfPaths(int n, int m, int[][] dp) {
        if (dp[n][m] != -1) return dp[n][m];
        int result;
        if (n == 1 && m == 1) {
            result = 1;
        } else if (n == 1) {
            result = getNumberOfPaths(n, m - 1, dp);
        } else if (m == 1) {
            result = getNumberOfPaths(n - 1, m, dp);
        } else {
            result = (getNumberOfPaths(n, m - 1, dp) % mod + getNumberOfPaths(n - 1, m, dp) % mod) % mod;
        }
        return dp[n][m] = (result % mod);
    }
    static int uniquePaths(int n, int m) {
        // dp[i][j] denotes the number of unique paths from [1, 1] to the [i, j]
        int[][] dp = new int[n + 1][m + 1];
        for (int[] row : dp)
            Arrays.fill(row, -1);
        return getNumberOfPaths(n, m, dp);
    }

    /*
    Asymptotic complexity in terms of the size of the grid `n` * `m`:
    * Time: O(n * m).
    * Auxiliary space: O(n * m).
    * Total space: O(n * m).
    */
    static int uniquePaths2(int n, int m) {
        // dp[i][j] denotes the number of unique paths from [1, 1] to the [i, j]
        int[][] dp = new int[n + 1][m + 1];
        final int mod = 1000000007;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (i == 1 && j == 1) {
                    dp[i][j] = 1;
                } else if (i == 1) {
                    dp[i][j] += dp[i][j - 1];
                } else if (j == 1) {
                    dp[i][j] += dp[i - 1][j];
                } else {
                    dp[i][j] += dp[i][j - 1] + dp[i - 1][j];
                }
                dp[i][j] %= mod;
            }
        }
        return dp[n][m];
    }

    static Long jump_ways(Integer n) {

        if(n==0){
            return 0L;
        }
        if(n==1){
            return 1L;
        }
        if(n==2){
            return 2L;
        }

        //try DP
        //indices go from 0 to n
        //tab[n] = number of ways to jump a length n
        Long[] tab = new Long[n+1];
        tab[0]=0L;
        tab[1]=1L;
        tab[2]=2L;

        for(int i=3; i<=n; i++){
            tab[i]=tab[i-1]+tab[i-2];
        }

        return tab[n];
    }

    static Integer find_fibonacci(Integer n) {
        int[] memo = new int[3];
        memo[0] = 0;
        memo[1] = 1;
        for(int i = 2; i <= n.intValue(); i++){
            int index = i%3;
            int value = memo[(i-1)%3] + memo[(i-2)%3];
            memo[index] = value;
        }
        return memo[n.intValue()%3];
    }

}