package com.arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrefixSums {
    /**
     * 9 problems
     * Running Sum of 1D Array (lc#1480)
     * Range Sum Query - Immutable
     * Range Sum Query 2D - Immutable
     * Subarray Sum Equals K
     * Subarray Sums Divisible by K
     * Maximum Size Subarray Sum Equals K
     * Number of Sub-arrays with Odd Sum
     * Contiguous Array
     * Continuous Subarray Sum
     */
//------------------------------------------------------------------------------------------------//

    /**
     * TC = O(n)
     * SC = O(n)
     */
    public List<Integer> computeRunningSums(int[] nums) {
        List<Integer> prefixSums = new ArrayList<>();

        int prefixSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefixSum = prefixSum + i;
            prefixSums.add(prefixSum);
        }

        return prefixSums;
    }

    /**
     * TC = O(n), O(1)
     * SC = O(n)
     */
    public int calculateRangeSumOf1dArray(int[] nums, int i, int j) {
        List<Integer> prefixSums = new ArrayList<>();
        int prefixSum = nums[0];
        for (int k = 1; k < nums.length; k++) {
            prefixSum = prefixSum + k;
            prefixSums.add(prefixSum);
        }

        if (i == 0) {
            return prefixSums.get(j);
        }
        return prefixSums.get(j) - prefixSums.get(i - 1);
    }

    public int[][] computePrefixSum(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new int[0][0];
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] prefixSum = new int[m][n];

        // Initialize the first cell
        prefixSum[0][0] = matrix[0][0];

        // Fill the first column
        for (int row = 1; row < m; row++) {
            prefixSum[row][0] = prefixSum[row - 1][0] + matrix[row][0];
        }

        // Fill the first row
        for (int col = 1; col < n; col++) {
            prefixSum[0][col] = prefixSum[0][col - 1] + matrix[0][col];
        }

        // Fill the rest of the prefix sum matrix
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                prefixSum[row][col] = prefixSum[row - 1][col]
                        + prefixSum[row][col - 1]
                        - prefixSum[row - 1][col - 1]
                        + matrix[row][col];
            }
        }

        return prefixSum;
    }

    public int calculateRangeSumOf2dArray(int[][] prefixSum, int row1, int col1, int row2, int col2) {
        if (prefixSum == null || prefixSum.length == 0 || prefixSum[0].length == 0) {
            return 0;
        }

        if (row1 == 0 && col1 == 0) {
            return prefixSum[row2][col2];
        } else if (row1 == 0) {
            return prefixSum[row2][col2] - prefixSum[row2][col1 - 1];
        } else if (col1 == 0) {
            return prefixSum[row2][col2] - prefixSum[row1 - 1][col2];
        } else {
            return prefixSum[row2][col2]
                    - prefixSum[row2][col1 - 1]
                    - prefixSum[row1 - 1][col2]
                    + prefixSum[row1 - 1][col1 - 1];
        }
    }


    public int subarraySum(int[] nums, int k) {
        // HashMap to store the prefix sum and its frequency
        HashMap<Integer, Integer> hmap = new HashMap<>();
        hmap.put(0, 1); // Initialize with prefix sum 0 having frequency 1

        int total = 0;
        int prefixSum = 0;

        for (int num : nums) {
            // Update the prefix sum
            prefixSum += num;

            // Check if (prefixSum - k) exists in the map
            if (hmap.containsKey(prefixSum - k)) {
                total += hmap.get(prefixSum - k);
            }

            // Update the frequency of the current prefix sum in the map
            hmap.put(prefixSum, hmap.getOrDefault(prefixSum, 0) + 1);
        }

        return total;
    }

    public int subarraysSumDivByK(int[] nums, int K) {
        // HashMap to store the remainder (prefixSum % K) and its frequency
        HashMap<Integer, Integer> hmap = new HashMap<>();
        hmap.put(0, 1); // Initialize with remainder 0 having frequency 1

        int total = 0;
        int prefixSumRemainder = 0;

        for (int num : nums) {
            // Update the prefix sum remainder
            prefixSumRemainder = (prefixSumRemainder + num) % K;

            // Handle negative remainders to ensure they are positive
            if (prefixSumRemainder < 0) {
                prefixSumRemainder += K;
            }

            // Check if the current remainder exists in the map
            // how many yellow prefixes that adds up to prefix sum
            if (hmap.containsKey(prefixSumRemainder)) {
                total += hmap.get(prefixSumRemainder); // my local answer
            }

            // Update the frequency of the current remainder in the map. Required for the bosses
            hmap.put(prefixSumRemainder, hmap.getOrDefault(prefixSumRemainder, 0) + 1);
        }

        return total;
    }

    public int maxSizeSubArraySumEqualsK(int[] nums, int k) {
        // HashMap to store prefix sums and their earliest indices
        // Given any value, what is the smallest prefix adding up to that value
        HashMap<Integer, Integer> hmap = new HashMap<>();
        hmap.put(0, 0); // Initialize with prefix sum 0 at index 0

        int globalMax = 0;
        int prefixSum = 0;

        for (int i = 0; i < nums.length; i++) {
            // Update the prefix sum
            // [j....i] = [0...i] - [0....j-1]
            prefixSum += nums[i];

            // Find the smallest length yellow prefix adding up to (prefixSum - k)
            if (hmap.containsKey(prefixSum - k)) {// length of the shortest such prefix
                // Calculate the length of the subarray
                globalMax = Math.max(globalMax, i + 1 - hmap.get(prefixSum - k)); // compare global answer with my local answer
            }

            // Add the current prefix sum to the map if it does not already exist
            // update hash map info about [0....i]
            if (!hmap.containsKey(prefixSum)) {
                hmap.put(prefixSum, i + 1);
            }
        }

        return globalMax;
    }

    /*
    * TC = O(n)
    * SC = O(1)
    * */
    public int numOfSubarraysWithOddSum(int[] arr) {
        // HashMap to store counts of prefix sums being even or odd
        int evenCount = 1; // Initialize count for even prefix sums
        int oddCount = 0;  // Initialize count for odd prefix sums
        int total = 0;
        int prefixSum = 0;

        for (int num : arr) {
            // Update the prefix sum
            prefixSum += num;

            // Check if the current prefix sum is even or odd
            if (prefixSum % 2 == 0) {
                total += oddCount; // Add the count of odd prefix sums
            } else {
                total += evenCount; // Add the count of even prefix sums
            }

            // Update counts based on the current prefix sum
            if (prefixSum % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }

            // Modulo operation to keep the result within bounds
            total %= 1000000007;
        }

        return total;
    }


    public int findMaxLengthInAContinuousSubArray(int[] nums) {
        // HashMap to store prefix sums and their earliest indices
        HashMap<Integer, Integer> hmap = new HashMap<>();
        hmap.put(0, 0); // Initialize with prefix sum 0 at index 0

        int globalMax = 0;
        int prefixSum = 0;

        for (int i = 0; i < nums.length; i++) {
            // Update the prefix sum
            if (nums[i] == 0) {
                prefixSum -= 1;
            } else {
                prefixSum += 1;
            }

            // Check if the current prefix sum exists in the map
            if (hmap.containsKey(prefixSum)) {
                // Calculate the length of the subarray
                globalMax = Math.max(globalMax, i + 1 - hmap.get(prefixSum));
            } else {
                // Add the current prefix sum to the map if it does not already exist
                hmap.put(prefixSum, i + 1);
            }
        }

        return globalMax;
    }


    public boolean checkSubarraySum(int[] nums, int k) {
        // Edge case: if the array has less than 2 elements, return false
        if (nums.length < 2) {
            return false;
        }

        // Edge case: if k is 0, check for consecutive zeros
        if (k == 0) {
            for (int i = 1; i < nums.length; i++) {
                if (nums[i - 1] == 0 && nums[i] == 0) {
                    return true;
                }
            }
            return false;
        }

        // HashMap to store remainders and their earliest indices
        HashMap<Integer, Integer> hmap = new HashMap<>();
        hmap.put(0, -1); // Initialize with remainder 0 at index -1

        int prefixSum = 0;

        for (int i = 0; i < nums.length; i++) {
            // Update the prefix sum
            prefixSum += nums[i];

            // Calculate the remainder of the prefix sum when divided by k
            int remainder = prefixSum % k;

            // Handle negative remainders to ensure they are positive
            if (remainder < 0) {
                remainder += k;
            }

            // Check if the remainder has been seen before
            if (hmap.containsKey(remainder)) {
                // If the subarray length is at least 2, return true
                if (i - hmap.get(remainder) > 1) {
                    return true;
                }
            } else {
                // Store the current remainder and its index in the map
                hmap.put(remainder, i);
            }
        }

        return false;
    }

}
