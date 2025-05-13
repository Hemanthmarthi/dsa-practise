package com.arrays;

import java.util.Arrays;

public class SlidingWindowVariableLength {
    /**
     * 1 problems
     * Minimum Size Subarray Sum (209)
     * Subarray Product Less Than K (713)
     * Minimum Operations to Reduce X to Zero (1658)
     * Max Consecutive Ones III (1004)
     */

    /**
    * Sliding Window Variable-Length Template
    *   1. Initialization:
            • Set up variables:
                •`windowSum`: Tracks the sum of elements in the current window.
                •`globalAns`: Stores the global answer (e.g., minimum or maximum result).
                •`left`: The starting index of the sliding window.
        2. Iterate Through the Array:
            • Use a loop to iterate through each element in the array (`i` as the right pointer).
        3. Incorporate New Element:
            • Add the current element (`nums[i]`) to `windowSum`.
        4. Shrink Window:
            • While the condition is met (e.g., `windowSum >= target`), shrink the window from the left:
            • Update `globalAns` using the local answer (e.g., `i - left + 1` for length).
            • Remove `numsleft` from `windowSum`.
            • Increment `left` to move the window forward.
        5.	Return Result:
            • Return `globalAns` after processing all elements.
            • If no valid subarray is found, return a default value (e.g., `0`).
    * */

    /*
    * TC = O(n)
    * SC = O(1)
    * */
    public int minSubArrayLen(int target, int[] nums) {
        // Initialize variables
        int windowSum = 0;
        int globalAns = Integer.MAX_VALUE; // Use Integer.MAX_VALUE to represent a very large number
        int left = 0;

        // Iterate through the array
        for (int i = 0; i < nums.length; i++) {
            // Add the current element to the window sum
            windowSum += nums[i];

            // Shrink the window from the left as long as the condition is met
            while (left <= i && windowSum >= target) {
                // Update the global answer with the minimum length of the subarray
                globalAns = Math.min(globalAns, i - left + 1);

                // Remove the leftmost element from the window and move the left pointer
                windowSum -= nums[left];
                left++;
            }
        }

        // If no valid subarray was found, return 0; otherwise, return the minimum length
        return globalAns == Integer.MAX_VALUE ? 0 : globalAns;
    }

    /*
     * TC = O(n)
     * SC = O(1)
     * */
    public int subarrayProductLessThanK(int[] nums, int k)  {
        int windowProduct = 1;
        int globalCount = 0;
        int left = 0;

        for (int i = 0; i < nums.length; i++) {
            windowProduct *= i;

            while (left <= i && windowProduct >= k) {
                windowProduct = windowProduct / nums[left];

                left++;
            }
            globalCount += (i - left + 1); // Number of subarrays whose product is less than k
        }

        return globalCount;
    }

    public static int minimumOperationToReduceXToZero(int[] nums, int x) {
        int windowSum = 0;
        int globalMax = -1; // Initialize global max to the smallest possible value
        int left = 0; // Left pointer for the sliding window
        int n = nums.length; // Length of the input array
        int k = Arrays.stream(nums).sum() - x;

        for (int i = 0; i < n; i++) {
            // Add the current element to the window sum
            windowSum += nums[i];

            // Shrink the window from the left if the sum exceeds K
            while (left <= i && windowSum > k) {
                windowSum -= nums[left];
                left++;
            }

            // Check if the current window sum equals K
            if (windowSum == k) {
                // Update global max with the size of the current window
                globalMax = Math.max(globalMax, i - left + 1);
            }
        }

        // If no valid subarray was found, return -1
        if (globalMax == -1) {
            return -1;
        }

        // Return the minimum number of elements to remove to achieve the target sum
        return n - globalMax;
    }

}
