package com.arrays;

import java.util.*;

public class SlidingWindowFixedLength {
    /**
     * 11 problems
     * Moving Average from Data Stream (346)
     * Maximum Average Subarray I (643)
     * Number of Sub-arrays of Size K and Average Greater than or Equal to Threshold (1343)
     * Diet Plan Performance (1176)
     * Grumpy Bookstore Owner (1052)
     * Maximum Number of Vowels in a Substring of Given Length (1456)
     * Find K-Length Substrings With No Repeated Characters (1100)
     * Sliding Window Maximum (239)
     *
     * Permutation in String (567)
     * Find All Anagrams in a String (438)
     * Sliding Window Median (480)
     */

    /**
    * Sliding Window Fixed-Length Template
	    1.	Edge Case Handling:
	        • If the input is invalid (e.g., `k > n`), return an appropriate message like `"no answer"`.
	    2.	Initialization:
	        • Compute metrics for the first window of size `k`.
	        • Initialize variables to track the global answer and current window metrics.
	    3.	Sliding the Window:
	        • Iterate from index `k` to `n - 1`:
	        • Add the new element (entering the window) to the current metrics.
	        • Remove the old element (leaving the window) from the current metrics.
	        • Update the global answer based on the current window’s metrics(local answer).
	    4.	Return Result:
	        • Return the global answer after processing all windows.
    * These problems could be either Counting | Optimization | Decision
    * */


    class MovingAverage {
        private Queue<Integer> window;
        private int maxSize;
        private int total;

        // Constructor to initialize the data structure
        public MovingAverage(int size) {
            this.window = new LinkedList<>();
            this.maxSize = size;
            this.total = 0;
        }

        // Method to add a new value and calculate the moving average
        public double next(int val) {
            // Add the new value to the window
            window.add(val);
            total += val;

            // If the window size exceeds maxSize, remove the oldest value
            if (window.size() > maxSize) {
                int popped = window.poll();
                total -= popped;
            }

            // Return the moving average
            return (double) total / window.size();
        }
    }

    /*
     * TC = O(n)
     * SC = O(1)
     * */
    public double findMaxAverage(int[] nums, int k) {
        // Calculate the sum of the first `k` elements
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        // Initialize global max with the sum of the first window
        int globalMax = windowSum;

        // Slide the window across the array
        for (int i = k; i < nums.length; i++) {
            // Update the window sum by adding the rightmost element and subtract the leftmost element of the previous window
            windowSum += nums[i] - nums[i - k];

            // Update the global max if the current window sum is greater
            globalMax = Math.max(globalMax, windowSum);
        }

        // Return the maximum average
        return (double) globalMax / k;
    }

    /*
     * TC = O(n)
     * SC = O(1)
     * */
    public int numOfSubarraysOfSizeK(int[] arr, int k, int threshold) { // maximumSubarraySumWithSizeEqualsK
        // Calculate the total threshold for comparison
        int totalThreshold = threshold * k;

        // Calculate the sum of the first window of size k
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        // Initialize count based on the first window
        int count = (windowSum >= totalThreshold) ? 1 : 0;

        // Slide the window across the array
        for (int i = k; i < arr.length; i++) {
            // Update the window sum by adding the new element and removing the old element
            windowSum += arr[i] - arr[i - k];

            // Check if the current window meets the condition
            if (windowSum >= totalThreshold) {
                count++;
            }
        }

        return count;
    }

    /*
     * TC = O(n)
     * SC = O(1)
     * */
    public int dietPlanPerformance(int[] calories, int k, int lower, int upper) {
        // Calculate the sum of the first window of size k
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += calories[i];
        }

        // Initialize points based on the first window
        int points = 0;
        if (windowSum < lower) {
            points -= 1;
        } else if (windowSum > upper) {
            points += 1;
        }

        // Slide the window across the array
        for (int i = k; i < calories.length; i++) {
            // Update the window sum by adding the new element and removing the old element
            windowSum += calories[i] - calories[i - k];

            // Update points based on the current window sum
            if (windowSum < lower) {
                points -= 1;
            } else if (windowSum > upper) {
                points += 1;
            }
        }

        return points;
    }

    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int totalSatisfied = 0;

        // Calculate the initial satisfied customers (when grumpy[i] == 0)
        for (int i = 0; i < customers.length; i++) {
            if (grumpy[i] == 0) {
                totalSatisfied += customers[i];
            }
        }

        // Calculate the initial number of unsatisfied customers in the first window of size X
        int numNotSatisfied = 0;
        for (int i = 0; i < X; i++) {
            if (grumpy[i] == 1) {
                numNotSatisfied += customers[i];
            }
        }

        // Initialize the global maximum with the first window
        int globalMax = numNotSatisfied;

        // Slide the window across the array
        for (int i = X; i < customers.length; i++) {
            // Add unsatisfied customers at the right end of the window
            if (grumpy[i] == 1) {
                numNotSatisfied += customers[i];
            }

            // Remove unsatisfied customers at the left end of the window
            if (grumpy[i - X] == 1) {
                numNotSatisfied -= customers[i - X];
            }

            // Update the global maximum
            globalMax = Math.max(globalMax, numNotSatisfied);
        }

        // Add the maximum number of previously unsatisfied customers that can now be satisfied
        return totalSatisfied + globalMax;
    }

    public int maxVowels(String s, int k) {
        // Define a set of vowels
        String vowels = "aeiou";

        // Initialize the count of vowels in the first window
        int numVowels = 0;
        for (int i = 0; i < k; i++) {
            if (vowels.indexOf(s.charAt(i)) != -1) {
                numVowels++;
            }
        }

        // Initialize the maximum number of vowels
        int maxVowels = numVowels;

        // Slide the window across the string
        for (int i = k; i < s.length(); i++) {
            // Add the new character to the window
            if (vowels.indexOf(s.charAt(i)) != -1) {
                numVowels++;
            }

            // Remove the character that is sliding out of the window
            if (vowels.indexOf(s.charAt(i - k)) != -1) {
                numVowels--;
            }

            // Update the maximum number of vowels
            maxVowels = Math.max(maxVowels, numVowels);
        }

        return maxVowels;
    }

    /*
     * TC = O(n)
     * SC = O(K) or O(1)
     * */
    public int numKLenSubstrsNoRepeats(String S, int K) {
        // If K is greater than the length of the string, return 0
        if (K > S.length()) {
            return 0;
        }

        // HashMap to store character frequencies in the current window
        HashMap<Character, Integer> hmap = new HashMap<>();
        int total = 0;

        // Initialize the first window of size K
        for (int i = 0; i < K; i++) {
            char c = S.charAt(i);
            hmap.put(c, hmap.getOrDefault(c, 0) + 1);
        }

        // Check if the first window has no repeated characters
        if (hmap.size() == K) {
            total = 1;
        }

        // Slide the window across the string
        for (int i = K; i < S.length(); i++) {
            // Add the new character to the window
            char newChar = S.charAt(i);
            hmap.put(newChar, hmap.getOrDefault(newChar, 0) + 1);

            // Remove the character that is sliding out of the window
            char oldChar = S.charAt(i - K);
            hmap.put(oldChar, hmap.get(oldChar) - 1);
            if (hmap.get(oldChar) == 0) {
                hmap.remove(oldChar);
            }

            // Check if the current window has no repeated characters
            if (hmap.size() == K) {
                total++;
            }
        }

        return total;
    }

    /*
	•	TC = O(n), where  is the length of `nums`. Each element is added to and removed from the `Deque` at most once.
	•	SC = O(k), as we store at most `k` indices in the `Deque`.
     * */
    public int[] maxValueInASlidingWindow(int[] nums, int k) {
        // Edge case: if nums is empty or k is 0
        if (nums == null || nums.length == 0 || k == 0) {
            return new int[0];
        }

        // Deque to store indices of elements in the current window
        Deque<Integer> deque = new ArrayDeque<>();
        int[] result = new int[nums.length - k + 1]; // front element of deque
        int resultIndex = 0;

        // Process the first window
        for (int i = 0; i < k; i++) {
            pushIn(nums, deque, i);
        }
        result[resultIndex++] = nums[deque.peekFirst()];

        // Process the remaining windows
        for (int i = k; i < nums.length; i++) {
            // Remove the element that is sliding out of the window
            if (!deque.isEmpty() && deque.peekFirst() == i - k) {
                deque.pollFirst();
            }

            // Push the current element into the deque
            pushIn(nums, deque, i);

            // Add the maximum of the current window to the result
            result[resultIndex++] = nums[deque.peekFirst()];
        }

        return result;
    }

    // Helper method to push an index into the deque while maintaining descending order
    private void pushIn(int[] nums, Deque<Integer> deque, int index) {
        // back element of the deque < nums[i] -> delete the back element
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[index]) {
            deque.pollLast();
        }
        // push nums[i] at the back of the deque
        deque.addLast(index);
    }

    public boolean permutationInString(String s1, String s2) {
        // Edge case: If s1 is longer than s2, it's impossible for s2 to contain a permutation of s1
        if (s1.length() > s2.length()) {
            return false;
        }

        // Initialize frequency maps for s1 and the first window of s2
        Map<Character, Integer> hmapS1 = new HashMap<>();
        Map<Character, Integer> hmapS2 = new HashMap<>();

        // Populate the frequency map for s1
        for (char c : s1.toCharArray()) {
            hmapS1.put(c, hmapS1.getOrDefault(c, 0) + 1);
        }

        // Populate the frequency map for the first window in s2
        for (int i = 0; i < s1.length(); i++) {
            char c = s2.charAt(i);
            hmapS2.put(c, hmapS2.getOrDefault(c, 0) + 1);
        }

        // Check if the first window is a permutation of s1
        if (hmapS1.equals(hmapS2)) {
            return true;
        }

        // Slide the window across the rest of s2
        for (int i = s1.length(); i < s2.length(); i++) {
            char newChar = s2.charAt(i); // New character entering the window
            char oldChar = s2.charAt(i - s1.length()); // Old character leaving the window

            // Add the new character to the frequency map
            hmapS2.put(newChar, hmapS2.getOrDefault(newChar, 0) + 1);

            // Remove or decrement the count of the old character from the frequency map
            if (hmapS2.get(oldChar) == 1) {
                hmapS2.remove(oldChar);
            } else {
                hmapS2.put(oldChar, hmapS2.get(oldChar) - 1);
            }

            // Check if the current window is a permutation of s1
            if (hmapS1.equals(hmapS2)) {
                return true;
            }
        }

        return false;
    }

    public List<Integer> findAnagramsInString(String s, String p) {
        List<Integer> result = new ArrayList<>();

        // Edge case: If p is longer than s, no anagrams are possible
        if (p.length() > s.length()) {
            return result;
        }

        // Initialize frequency maps for p and the first window of s
        Map<Character, Integer> hmapP = new HashMap<>();
        Map<Character, Integer> hmapS = new HashMap<>();

        // Populate the frequency map for p
        for (char c : p.toCharArray()) {
            hmapP.put(c, hmapP.getOrDefault(c, 0) + 1);
        }

        // Populate the frequency map for the first window in s
        int k = p.length();
        for (int i = 0; i < k; i++) {
            char c = s.charAt(i);
            hmapS.put(c, hmapS.getOrDefault(c, 0) + 1);
        }

        // Check if the first window is an anagram of p
        if (hmapP.equals(hmapS)) {
            result.add(0);
        }

        // Slide the window across the rest of s
        for (int i = k; i < s.length(); i++) {
            char newChar = s.charAt(i); // New character entering the window
            char oldChar = s.charAt(i - k); // Old character leaving the window

            // Add the new character to the frequency map
            hmapS.put(newChar, hmapS.getOrDefault(newChar, 0) + 1);

            // Remove or decrement the count of the old character from the frequency map
            if (hmapS.get(oldChar) == 1) {
                hmapS.remove(oldChar);
            } else {
                hmapS.put(oldChar, hmapS.get(oldChar) - 1);
            }

            // Check if the current window is an anagram of p
            if (hmapP.equals(hmapS)) {
                result.add(i - k + 1);
            }
        }

        return result;
    }

    /*
    * TC = O(NLogK)
    * SC = O(N)
    * */
    public double[] medianSlidingWindow(int[] nums, int k) {
        // Result array to store medians
        List<Double> medians = new ArrayList<>();

        // HashMap to track invalid numbers for lazy deletion
        Map<Integer, Integer> hashTable = new HashMap<>();

        // Two heaps: maxHeap for the smaller half, minHeap for the larger half
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // Max heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // Min heap

        int i = 0; // Index of current incoming element being processed

        // Initialize the heaps
        while (i < k) {
            maxHeap.offer(nums[i++]);
        }
        for (int j = 0; j < k / 2; j++) {
            minHeap.offer(maxHeap.poll());
        }

        while (true) {
            // Get median of current window
            if (k % 2 == 1) {
                medians.add((double) maxHeap.peek());
            } else {
                medians.add(((double) maxHeap.peek() + (double) minHeap.peek()) / 2.0);
            }

            if (i >= nums.length) {
                break; // Break if all elements have been processed
            }

            int outNum = nums[i - k]; // Outgoing element
            int inNum = nums[i++];    // Incoming element
            int balance = 0;          // Balance factor

            // Number `outNum` exits window
            balance += (outNum <= maxHeap.peek() ? -1 : 1);
            hashTable.put(outNum, hashTable.getOrDefault(outNum, 0) + 1);

            // Number `inNum` enters window
            if (!maxHeap.isEmpty() && inNum <= maxHeap.peek()) {
                balance++;
                maxHeap.offer(inNum);
            } else {
                balance--;
                minHeap.offer(inNum);
            }

            // Re-balance heaps
            if (balance < 0) { // `maxHeap` needs more valid elements
                maxHeap.offer(minHeap.poll());
                balance++;
            }
            if (balance > 0) { // `minHeap` needs more valid elements
                minHeap.offer(maxHeap.poll());
                balance--;
            }

            // Remove invalid numbers from heap tops
            while (!maxHeap.isEmpty() && hashTable.getOrDefault(maxHeap.peek(), 0) > 0) {
                hashTable.put(maxHeap.peek(), hashTable.get(maxHeap.peek()) - 1);
                maxHeap.poll();
            }
            while (!minHeap.isEmpty() && hashTable.getOrDefault(minHeap.peek(), 0) > 0) {
                hashTable.put(minHeap.peek(), hashTable.get(minHeap.peek()) - 1);
                minHeap.poll();
            }
        }

        // Convert result list to array and return it
        double[] result = new double[medians.size()];
        for (int j = 0; j < medians.size(); j++) {
            result[j] = medians.get(j);
        }
        return result;
    }

}