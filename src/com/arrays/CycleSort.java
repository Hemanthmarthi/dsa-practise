package com.arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CycleSort {
    /**
     * 7 Problems
     * 1. Missing Number (LC#268)
     * 2. Find all numbers disappeared in an Array (LC#448)
     * 3. Find the duplicate number (LC#287)
     * 4. Set Mismatch (LC#645)
     * 5. Find all duplicates in an Array (LC#442)
     * 6. First missing positive (LC#41)
     * 7. Couples holding hands (LC#765)
     */

    static int missingNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i && nums[i] < nums.length) {
                swap(nums, nums[i], nums[nums[i]]);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                return i;
            }
        }

        return nums.length;
    }

    static List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1) {
                int idx = nums[i] - 1;
                if (nums[idx] != nums[i]) {
                    swap(nums, nums[i], nums[idx]);
                } else {
                    break;
                }
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                result.add(i + 1);
            }
        }
        return result;
    }

    static int findDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i) {
                int idx = nums[i];
                if (nums[idx] != nums[i]) {
                    swap(nums, nums[i], nums[idx]);
                } else {
                    break;
                }
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                return nums[i];
            }
        }

        return -1;
    }

    static int[] setMismatch(int[] nums){
        for (int i = 0; i < nums.length; i++){
            while (nums[i] != i+1) {
              int idx = nums[i] - 1;
                if (nums[idx] != nums[i]) {
                    swap(nums, nums[i], nums[idx]);
                } else {
                    break;
                }
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return new int[]{nums[i], i + 1};
            }

        }
        return new int[]{-1, -1};
    }

    static List<Integer> findAllDuplicatesInAnArray(int[] nums) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1) {
                int idx = nums[i] - 1;
                if (nums[idx] != nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[idx];
                    nums[idx] = temp;
                } else {
                    break;
                }
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                result.add(nums[i]);
            }
        }
        return result;
    }

    static int[] findErrorNums(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1) {
                int idx = nums[i] - 1;
                if (idx >= 0 && idx < nums.length) { // Check for valid index
                    if (nums[idx] != nums[i]) {
                        swap(nums, nums[idx], nums[i]);
                    } else {
                        // If duplicate found, break loop and return result
                        return new int[]{nums[i], i + 1};
                    }
                } else {
                    break; // Exit loop if index is not within bounds
                }
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return new int[]{nums[i], i + 1};
            }
        }
        return new int[]{-1, -1};
    }

    static int findFirstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1) {
                int idx = nums[i] - 1;
                if (0 <= idx && idx < nums.length && nums[idx] != nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[idx];
                    nums[idx] = temp;
                } else {
                    break;
                }
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return nums.length + 1;
    }

    static int minSwapsCouples(int[] row) {
        int n = row.length / 2;
        Map<Integer, Integer> hmap = new HashMap<>();

        for (int i = 0; i < row.length; i++) {
            hmap.put(row[i], i); // Build map of IDs to indices
        }

        int numSwaps = 0;

        for (int i = 0; i < n; i++) { // Loop through sofas
            int p1 = row[2 * i]; // Get first person on sofa
            int partner = (p1 % 2 == 0) ? p1 + 1 : p1 - 1; // Calculate partner's ID

            int destIndex = hmap.get(partner); // Calculate partner's correct seat

            if (destIndex / 2 != i) { // Partner in wrong sofa
                int p2 = row[2 * i + 1]; // Get partner's current seat
                row[2 * i + 1] = row[destIndex]; // Swap partner and current person
                row[destIndex] = p2;
                hmap.put(p2, destIndex); // Update map with new positions
                hmap.put(row[2 * i + 1], 2 * i + 1);
                numSwaps++;
            }
        }

        return numSwaps;
    }

    static void swap(int[] input, int i, int j) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }

}
