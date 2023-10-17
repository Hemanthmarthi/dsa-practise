package com.searching;

import java.util.Arrays;

// 35 practise problems
public class BinarySearch {

    /**
     * Unless otherwise specified below listed complexities are same for all the problems:
     * TC = O(log n)
     * AS = O(1)
     * SC = O(n)
     */
    static int binarySearch(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (mid == target) {
                return mid;
            } else if (mid < target) {
                start = mid + 1;
            } else { // mid > target
                end = mid - 1;
            }
        }
        return -1;
    }

    // twenty Games
    static int guessNumberHigherOrLower(int n) {
        int start = 1;
        int end = n;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (guessHelper(n) == 0) {
                return mid;
            } else if (guessHelper(n) == 1) {
                start = mid + 1;
            } else { // guessHelper(n) == -1)
                end = mid - 1;
            }
        }

        return -1;
    }

    static int firstBadVersion(int n) {
        int start = 1;
        int end = n;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            //if (isBadVersion(mid)) end = mid -1;
            if (true) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        // At this point start is one more than end
        // end points to the last good version
        // start points to the first bad version
        return start;
        // whereas, if we want to return last good version then return end
    }

    static int searchInsertPosition(int[] nums, int target) {
        int start = 1;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                start = mid + 1;
            } else { // nums[mid] > target
                end = mid - 1;
            }
        }
        // If the element is not found, then the search will end with start = end + 1.
        // It is the start index where the element would be found had it been inserted in order.
        return start;
    }

    // nextGreatestLetter
    static int findSmallestLetterGreaterThanTarget(char[] letters, int target) {
        int start = 1;
        int end = letters.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (letters[mid] < target) {
                start = mid + 1;
            } else { // letters[mid] > target
                end = mid - 1;
            }
        }
        return letters[start % letters.length];
    }

    // Find First and Last Position of Element in Sorted Array
    static int[] searchIndex(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = mid + 1;
            } else { // nums[mid] >= target
                end = mid - 1;
            }
        }
        // At this point, if the target exists in sorted array,
        // then start index points to the first appearance
        if (start == nums.length || nums[start] != target) {
            return new int[]{-1, -1};
        }
        int first = start;

        // Now do a second BS to find the last (right most) position of the target
        // start need not change
        end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] <= target) {
                start = mid + 1;
            } else { // nums[mid] > target
                end = mid - 1;
            }
        }
        // At this point, end index points to the last appearance of the target
        return new int[]{first, end};
    }

    static boolean isMajorityElement(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        // Find left most occurrence of target as in the previous problem
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = mid + 1;
            } else { // nums[mid] >= target
                end = mid - 1;
            }
        }
        if (start == nums.length || nums[start] != target) {
            return false;
        }
        // There should be at least nums.length/2 + 1 occurrences of target if it is a majority element
        // So at least index = start + nums.length/2 must have value = target to be called majority element
        if (start + nums.length / 2 <= nums.length - 1 && nums[start + nums.length / 2] == target) {
            return true;
        }
        return false;
    }

    static boolean search2DMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int left = 0, right = rows * columns - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = matrix[mid / columns][mid % columns];

            if (midVal == target)
                return true;
            else if (midVal < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return false;
    }

    static int searchSortedArrayOfUnknownSize(int[] nums, int target) {
        // ArrayReader.get()
        if (guessHelper(nums[10]) == target) {
            return 0;
        }
        int end = 1;
        while (guessHelper(end) <= target)
            end *= 2;
        int start = end / 2;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            int midVal = guessHelper(mid);
            if (midVal == target) {
                return mid;
            } else if (midVal < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    static int fixedPoint(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < mid) {
                start = mid + 1;
            } else { // nums[mid] >= mid
                end = mid - 1;
            }
        }
        if (start == nums.length || nums[start] != start) {
            return -1;
        } else {
            return start;
        }
    }

    // Single element in a Sorted array
    // left side indices are even followed by odd and right side are Odd followed by even
    static int singleNonDuplicate(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums[0] != nums[1]) {
            return nums[0];
        } else if (nums[nums.length - 1] != nums[nums.length - 2]) {
            return nums[nums.length - 1];
        }

        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if ((nums[mid - 1] != nums[mid]) && (nums[mid] != nums[mid + 1])) {
                return nums[mid];
            }
            if ((mid % 2 == 0 && nums[mid + 1] == nums[mid])
                    || (mid % 2 == 1 && nums[mid - 1] == nums[mid])) {
                // Even-odd zone
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;

        /*// XOR based approach
        int XOR = 0;
        for (int i = 0; i < nums.length; i++) {
            XOR = XOR ^ nums[i];
        }
        System.out.println("The required element is "
                + XOR);*/
    }

    static int missingNumberInArithmeticProgression(int[] nums) {
        // calculate the common difference
        int d = (nums[nums.length - 1] - nums[0]) / nums.length;

        // Now search the missing value using Binary search
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            // if nums[mid] is in the right position, missing element is to the right. otherwise is to the left
            if (nums[mid] == nums[0] + mid * d) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return nums[end] + d;
    }

    // need to understand below two solutions
    static int missingElementInSortedArray(int[] nums, int k) {
        int start = 0;
        int end = nums.length - 1;
        int missing = 0;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            // calculate how many numners are missing up to this point
            // nums[mid] - nums[0] are all the numbers beyond nums[0]
            // mid = how many of those numbers there actually are in the array
            // missing numbers = (nums[mid]-nums[0])-mid
            missing = nums[mid] - nums[0] - mid;
            if (missing < k) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        missing = nums[end] - nums[0] - end;
        return nums[end] + (k - missing);
    }

    static int findKthMissingPositiveNumber(int[] nums, int k) {
        if ((nums[0] - 1) >= k) { // missing number is before the beginning of the array
            return k;
        } else if ((nums[nums.length - 1] - nums.length) < k) { // missing number is after the end of the array
            return nums[nums.length - 1] + (k - nums[nums.length - 1] - nums.length);
        }
        // we know here that the missing number is within the range of values in the array
        int start = 0;
        int end = nums.length - 1;
        int missingOnLeft;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            // Expected value at mid = mid + 1
            missingOnLeft = nums[mid] - (mid + 1);
            if (missingOnLeft >= k) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        missingOnLeft = nums[end] - (end + 1);
        return nums[end] + (k - missingOnLeft);
        // Above simplifies to => return k + start
    }

    // Need to keep in mind the edge cases where i and j are not 0 or length -1
    static boolean validMountainArray(int[] arr) {
        int n = arr.length;
        if (n < 3) {
            return false;
        }
        int i = 0;
        // walk up
        while (i <= n - 2 && arr[i] < arr[i + 1]) {
            i++;
        }
        // peak can't be first or last
        if (i == 0 || i == n - 1) return false;
        int j = n - 1;
        // walk down
        while (j >= 1 && arr[j - 1] > arr[j]) {
            j--;
        }
        return i == j;
    }

    static int peakIndexInMountainArray(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (mid != 0 && mid != arr.length - 1 && (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1])) {
                return mid; // #mid is the interior peak
            } else if (arr[mid] == 0 || arr[mid] > arr[mid - 1]) { // A[mid] lies in the ascending zone
                start = mid + 1;
            } else { // #mid == len(A)-1 or A[mid] > A[mid+1]: # A[mid] lies in the descending zone
                end = mid - 1;
            }
        }
        return -1; // should never come here as there will be a peak in the input array
    }

    // 3 Binary searches
    static int findInMountainArray(int target, MountainArray mountainArr) {
        int length = mountainArr.length();
        int start = 0;
        int end = length - 1;
        Integer peakIndex = null;

        // Find the peak element's index
        while (start <= end) {
            int mid = start + (end - start) / 2;
            int midVal = mountainArr.get(mid);
            int prevVal = (mid == 0) ? Integer.MIN_VALUE : mountainArr.get(mid - 1);
            int nextVal = (mid == length - 1) ? Integer.MIN_VALUE : mountainArr.get(mid + 1);

            if (mid > 0 && mid < length - 1 && prevVal < midVal && midVal > nextVal) {
                peakIndex = mid;
                break;
            } else if (mid == 0 || midVal > prevVal) { // arr[mid] lies in the ascending zone
                start = mid + 1;
            } else { // arr[mid] lies in the descending zone
                end = mid - 1;
            }
        }

        if (peakIndex == null) {
            return -1; // Peak not found, target cannot exist
        }

        // Search for the target in the ascending zone
        start = 0;
        end = peakIndex;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            int midVal = mountainArr.get(mid);
            if (midVal == target) {
                return mid;
            } else if (midVal < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        // Search for the target in the descending zone
        start = peakIndex + 1;
        end = length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            int midVal = mountainArr.get(mid);
            if (midVal == target) {
                return mid;
            } else if (midVal < target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return -1; // Target not found
    }

    // ascending zone, descending zone, peak or valley
    // findLocalMaxima
    static int findPeakElement(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }
        // Edge case: One of the extreme ends is the peak
        if (arr[0] > arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] > arr[arr.length - 2]) {
            return arr.length - 1;
        }

        // At this point, we know any peak must be in the interior
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            // arr[mid] can be in ascending zone, descending zone, peak or valley
            if (mid != 0 && mid != arr.length - 1
                    && arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) { // peak
                return mid;
            } else if (mid != 0 && mid != arr.length - 1
                    && arr[mid - 1] > arr[mid] && arr[mid] < arr[mid + 1]) { // valley
                end = mid - 1;
            } else if (mid == 0 || arr[mid - 1] < arr[mid]) { // Ascending zone
                start = mid + 1;
            } else { // Descending zone
                end = mid - 1;
            }
        }
        return -1; // Shouldn't ever be coming here since the array will always have a peak
    }

    // findValleyElement
    static int findLocalMinimum(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }
        // Edge case: One of the extreme ends is the peak
        if (arr[0] > arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] > arr[arr.length - 2]) {
            return arr.length - 1;
        }

        // At this point, we know any peak must be in the interior
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            // arr[mid] can be in ascending zone, descending zone, peak or valley
            if (mid != 0 && mid != arr.length - 1
                    && arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) { // peak
                end = mid - 1;
            } else if (mid != 0 && mid != arr.length - 1
                    && arr[mid - 1] > arr[mid] && arr[mid] < arr[mid + 1]) { // valley
                return mid;
            } else if (mid == 0 || arr[mid - 1] < arr[mid]) { // Ascending zone
                start = mid + 1;
            } else { // Descending zone
                end = mid - 1;
            }
        }
        return -1; // Shouldn't ever be coming here since the array will always have a peak
    }

    static int findMinimumInRotatedSortedArray(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        if (arr.length == 1) {
            return arr[0];
        }
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] <= arr[arr.length - 1]) {
                end = mid - 1;
            } else if (arr[mid] > arr[arr.length - 1]) {
                start = mid + 1;
            }
        }
        return arr[start];
        /*
         * This is a better solution because orange zone always exists(pole Star).
         * Green zone may or may not exist.
         * So compare with nums[-1] to avoid edge case where array is not rotated at all.
         * */
    }

    /**
     * TC = O(n)
     * AS = O(1)
     * SC = O(n)
     */
    static int findMinimumInRotatedSortedArrayWithDuplicates(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int leftmost = -1;
        if (nums[0] < nums[nums.length - 1]) {
            return nums[0];
        } else if (nums[0] > nums[nums.length - 1]) {
            leftmost = 0;
        } else { // nums[0] == nums[-1]
            // Find the first element different from nums[0]
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] != nums[0]) {
                    if (nums[i] < nums[0]) {
                        return nums[i];
                    } else {
                        leftmost = i;
                        break;
                    }
                }
            }
        }
        if (leftmost == -1) {
            return nums[0];
        }

        // At this point, we know that the array was rotated and its length > 2
        // We need to find the boundary between the two sections
        int start = leftmost;
        int end = nums.length - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] <= nums[nums.length - 1]) {
                // We are in the section on the right
                end = mid - 1;
            } else {
                // We are in the section on the left
                start = mid + 1;
            }
        }
        // At this point, start will point to the first element of the right section,
        // which is the min element
        return nums[start];
    }

    /**
     * TC = O(n)
     * AS = O(1)
     * SC = O(n)
     */
    static int searchInRotatedSortedArray(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        } else if (nums.length == 1) {
            if (target == nums[0]) {
                return 0;
            } else {
                return -1;
            }
        }

        int start = 0;
        int end = nums.length - 1;
        int region; // 0 for orange, 1 for green

        if (target == nums[nums.length - 1]) {
            return nums.length - 1;
        } else if (target < nums[nums.length - 1]) {
            region = 0;
        } else {
            region = 1;
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (target == nums[mid]) {
                return mid;
            }

            if (nums[mid] <= nums[nums.length - 1]) { // nums[-1] == pole star
                // mid-lies in orange region
                if (target < nums[mid] || region == 1) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                // mid-lies in green region
                if (target > nums[mid] || region == 0) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * TC = O(n)
     * AS = O(1)
     * SC = O(n)
     */
    static boolean searchInRotatedSortedArrayWithDuplicates(int[] nums, int target) {
        if (nums.length == 0) {
            return false;
        } else if (nums.length == 1) {
            return target == nums[0];
        }

        int start; // when there are duplicates we first need to identify the start position
        if (nums[0] == nums[nums.length - 1]) {
            if (target == nums[0]) { // Or it could also be that whole array is filled with identical numbers
                return true;
            }
            // Need to set start to the leftmost number that is not equal to nums[-1]
            int first = 0; // searching for the first element that is different from the pole start
            while (first <= nums.length - 1 && nums[first] == nums[nums.length - 1]) { // we could compare it with nums[0]as well(pole star)
                first++;
            }
            start = first;
        } else {
            start = 0;
        }

        int end = nums.length - 1;
        int region; // 0 for orange, 1 for green

        if (target == nums[nums.length - 1]) {
            return true;
        } else if (target < nums[nums.length - 1]) {
            region = 0;
        } else {
            region = 1;
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (target == nums[mid]) {
                return true;
            }
            if (nums[mid] <= nums[nums.length - 1]) {
                // mid lies in orange region
                if (target < nums[mid] || region == 1) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                // mid lies in green region
                if (target > nums[mid] || region == 0) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return false;
    }

    static int squareRootOfANumber(int x) {
        int start = 0;
        int end = x;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if ((mid * mid) == x) {
                return mid;
            } else if ((mid * mid) < x) {
                start = mid + 1;
            } else { // (mid * mid) > x
                end = mid - 1;
            }
        }
        return end; // integer sqrt of x
    }

    static boolean validPerfectSquare(int x) {
        int start = 0;
        int end = x;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if ((mid * mid) == x) {
                return true;
            } else if ((mid * mid) < x) {
                start = mid + 1;
            } else { // (mid * mid) > x
                end = mid - 1;
            }
        }
        return false; // integer sqrt of x
    }

    // Bisection search
    static double squareRootOfRealNumber(double x) {
        double tolerance = 0.00001;
        double start = 0;
        // using ceil as the sqrt of a number that is between 0 to 1 will be larger than x
        double end = Math.ceil(x);
        while (start <= end) {
            double mid = start + (end - start) / 2.0;
            if (Math.abs(mid * mid - x) < tolerance) {
                return mid;
            } else if ((mid * mid) < x) {
                start = mid;
            } else {
                end = mid;
            }
        }
        return 0.0;
    }

    // Largest triangular number <= n
    static int arrangeCoins(int n) {
        int start = 1;
        int end = n;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (mid * (mid + 1) / 2 == n) {
                return mid;
            } else if (mid * (mid + 1) / 2 < n) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return end;
    }

    /**
     * TC = O(nlogm)
     * AS = O(1)
     * SC = O(n)
     */
    static int kokoEatingBananas(int[] piles, int H) {
        // k = eating speed per hour
        // H = eat all the bananas with in H hours
        int startK = 1;
        int endK = Arrays.stream(piles).max().getAsInt();
        while (startK <= endK) {
            int midK = startK + (endK - startK) / 2;
            // Left zone = take > H hours
            // Right zone = take <= H hours
            int hours = 0;
            for (int pile : piles) {
                hours += Math.ceil(pile * 1.0 / midK);
                if (hours > H) {
                    startK = midK + 1;
                } else {
                    endK = midK - 1;
                }
            }
        }
        return startK; // returns minimum integer that koko can eat all bananas with in H hours
    }

    /**
     * TC = O(nlogm)
     * AS = O(1)
     * SC = O(n)
     */
    static int shipWithInDays(int[] weights, int D) {
        int startW = Arrays.stream(weights).max().getAsInt();
        int endW = Arrays.stream(weights).sum();
        while (startW <= endW) {
            int mid = startW + (endW - startW) / 2;
            // check how many days it will take for all packages to be shipped
            int days = 1;
            int load = 0;
            for (int weight : weights) {
                load += weight;
                if (load > mid) {
                    days++;
                    load = weight;
                }
            }
            if (days > D) {
                startW = mid + 1;
            } else {
                endW = mid - 1;
            }
        }
        return startW; // return least weight capacity of the ship that will result in all packages
    }

    /**
     * TC = O(nlogm)
     * AS = O(1)
     * SC = O(n)
     */
    static double MinimizeMaxDistanceToGasStation(double[] stations, int K) {
        double start = 0;
        double end = 0;
        for (int i = 0; i < stations.length - 1; i++) {
            end = Math.max(end, stations[i + 1] - stations[i]);
        }
        while (start <= end - 0.000001) {
            double mid = start + (end - start) / 2.0;
            // Check if this value of D(==mid) is even feasible given the constraints
            int numst = 0;
            for (int i = 0; i < stations.length - 1; i++) {
                numst += Math.floor((stations[i + 1] - stations[i]) / mid);
            }
            if (numst > K) {
                // Infeasible
                start = mid;
            } else {
                end = mid;
            }
        }
        return start;
    }

    // longestSubArraySum
    static int splitArrayLargestSum(int[] nums, int m) {
        int start = 0;
        int end = 0;
        for (int num : nums) {
            start = Math.max(start, num);
            end += num;
        }
        while (start <= end) {
            int mid = start + (end - start) / 2;
            int numSubarrays = 1;
            int total = 0;

            for (int num : nums) {
                total += num;
                if (total > mid) {
                    total = num;
                    numSubarrays++;
                }
            }
            if (numSubarrays > m) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }

    // maximizeSweetness
    static int divideChocolate(int nums) {
        return -1;
    }

    static double MedianOfTwoSortedArrays(int[] a, int[] b) {
        return 0.0;
    }

    // Find the number of valid pairs by greedy approach
    private int countValidPairs(int[] nums, int threshold) {
        int index = 0, count = 0;
        while (index < nums.length - 1) {
            // If a valid pair is found, skip both numbers.
            if (nums[index + 1] - nums[index] <= threshold) {
                count++;
                index++;
            }
            index++;
        }
        return count;
    }

    //lc#2616 - Minimize the maximum difference of pairs
    private int minimizeMax(int[] nums, int p) {
        Arrays.sort(nums);
        int n = nums.length;
        int left = 0, right = nums[n - 1] - nums[0];

        while (left < right) {
            int mid = left + (right - left) / 2;

            // If there are enough pairs, look for a smaller threshold.
            // Otherwise, look for a larger threshold.
            if (countValidPairs(nums, mid) >= p) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 2;
        boolean result = searchInRotatedSortedArrayWithDuplicates(nums, target);
        if (result) {
            System.out.println("Target found at index: " + result);
        } else {
            System.out.println("Target not found in the array.");
        }
    }

    // ---------------------------------------------- ----------------------------------------------//
    // UTILS
    static int guessHelper(int num) {
        if (num == 10) {
            return 0;
        } else if (num < 10) {
            return -1;
        } else {
            return 1;
        }
        // Integer.compare(num, 0)
    }

    private class MountainArray {
        public int get(int i) {
            return value;
        }

        public void setList(int value) {
            this.value = value;
        }

        int value;

        public int length() {
            return 0;
        }
    }
}
