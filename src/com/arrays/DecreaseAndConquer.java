package com.arrays;

public class DecreaseAndConquer {
    /** 7 problems
     * Pancake Sorting
     * Wiggle Sort
     * Search a 2D Matrix II
     * Find the Celebrity
     * Maximum Subarray sum
     * Best Time to Buy and Sell Stock
     * Maximal Square
     * */
//------------------------------------------------------------------------------------------------//

    /**
     * TC = O(m + n)
     * SC = O(n)
     */
    static boolean search2DMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int maxCol = n - 1;
        int minRow = 0;

        while (maxCol >= 0 && minRow <= m - 1) {

            if (target == matrix[minRow][maxCol])
                return true;
            else if (target < matrix[minRow][maxCol]) // eliminate the entire column
                maxCol--;
            else // target is greater than the value, eliminate the entire row
                minRow++;
        }
        return false;
    }

    /**
     * TC = O(n)
     * SC = O(1)
     */
    public int findCelebrity(int n) {
        int survivor = 0;

        // Find potential celebrity
        for (int i = 1; i < n; i++) {
            if (knows(survivor, i)) {
                survivor = i;
            }
        }

        // Validate that the survivor is a celebrity
        for (int p = 0; p < n; p++) {
            if (p != survivor) {
                if (knows(survivor, p) || !knows(p, survivor)) {
                    return -1;
                }
            }
        }

        return survivor;
    }

    // This method is assumed to be provided by the problem
    private boolean knows(int a, int b) {
        // Implementation would be provided by the problem
        return false; // Placeholder return
    }

    /**
     * TC = O(n)
     * SC = O(1)
     */
    public int maxSubArraySum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int globalMax = nums[0];
        int prevAns = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // Compare and update prevAns
            prevAns = Math.max(prevAns + nums[i], nums[i]);

            // Update globalMax
            globalMax = Math.max(globalMax, prevAns);
        }

        return globalMax;
    }

    /**
     * TC = O(n)
     * SC = O(1)
     */
    public int bestTimeToSellStock(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int globalMax = 0;
        int prevMin = nums[0]; // minimum price I would have bought the stock for

        for (int i = 1; i < nums.length; i++) {
            // Update globalMax
            globalMax = Math.max(nums[i] - prevMin, globalMax);

            // Compare and update prevMin
            prevMin = Math.min(prevMin, nums[i]);
        }

        return globalMax;
    }

    /**
     * TC = O(m * n)
     * Aux SC = O(m * n)
     */
    public int maximalSquare(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] table = new int[m][n];
        int globalMax = 0;

        // Initialize first row
        for (int col = 0; col < n; col++) {
            if (matrix[0][col] == '1') {
                table[0][col] = 1;
                globalMax = 1;
            } else {
                table[0][col] = 0;
            }
        }

        // Initialize first column
        for (int row = 0; row < m; row++) {
            if (matrix[row][0] == '1') {
                table[row][0] = 1;
                globalMax = 1;
            } else {
                table[row][0] = 0;
            }
        }

        // Fill the table
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                if (matrix[row][col] == '1') {
                    table[row][col] = 1 + Math.min(Math.min(table[row][col - 1], table[row - 1][col - 1]), table[row - 1][col]);
                    globalMax = Math.max(globalMax, table[row][col]);
                } else {
                    table[row][col] = 0;
                }
            }
        }

        return globalMax * globalMax;
        // Note: The aux space(O(m*n)) that was used in the above solution can be brought down to O(n) since we require only two rows information
    }


}