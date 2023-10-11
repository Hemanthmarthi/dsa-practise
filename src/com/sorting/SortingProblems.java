package com.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

// 19 practise problems
public class SortingProblems {

    /**
     * Asymptotic complexity in terms of the size of `numbers` = `n`:
     * Time: O(n).
     * Auxiliary space: O(1).
     * Total space: O(n).
     */
    static ArrayList<Integer> pairSumSortedArrayTP(ArrayList<Integer> numbers, Integer target) {
        // A list to store the pair of indices that adds to target.
        ArrayList<Integer> result = new ArrayList<>();

        int left = 0, right = numbers.size() - 1;

        while (left < right) {
            // Current sum is the sum of numbers at left and right indices.

            if (numbers.get(left) + numbers.get(right) == target) {
                result.add(left);
                result.add(right);
                return result;
            }
            // If the current sum is less than target, move left to (left + 1).
            // Element at (left + 1) is greater than the element at left, as the given array is sorted.
            else if (numbers.get(left) + numbers.get(right) < target) {
                left++;
            }
            // If the current sum is more than target, move right to (right - 1).
            // Element at (right - 1) is less than the element at right.
            else {
                right--;
            }
        }

        // If no such pair of indices exist, add -1, -1 to list.
        result.add(-1);
        result.add(-1);
        return result;
    }

    /**
     * Asymptotic complexity in terms of the size of `numbers` = `n`:
     * Time: O(n).
     * Auxiliary space: O(n).
     * Total space: O(n).
     */
    static ArrayList<Integer> pairSumSortedArrayHM(ArrayList<Integer> numbers, Integer target) {
        // A list to store the pair of indices that adds to target.
        ArrayList<Integer> result = new ArrayList<>();

        // This Map stores the array element as Key and its index as Value.
        HashMap<Integer, Integer> array_index = new HashMap<>();

        for (int i = 0; i < numbers.size(); i++) {
            int current = numbers.get(i);
            int required = target - current; // complementary target pair

            if (array_index.containsKey(required)) {
                result.add(array_index.get(required)); // store index of required in result
                result.add(i);
                return result;
            }

            // Add every element to map after checking for required.
            // This ensures element does not math itself (indices to be unique).
            array_index.put(current, i);
        }

        // If no such pair of indices exist, add -1, -1 to list.
        result.add(-1);
        result.add(-1);
        return result;
    }

    /**
     * Since the meeting will be attended starting from the one with the smallest start time
     * and moving ahead in the sorted order of the start times of the listed meetings,
     * it will be beneficial to sort all the given intervals based on their starting times.
     * <p>
     * Apply primary sort on the intervals by the start time and secondary sort by the end time.
     * Compare only the adjacent intervals to check if overlapping is present or not.
     * If the end time of some interval is found greater than the start time of the next interval,
     * it means these two intervals are overlapping.
     * Note: As the intervals are sorted, we can say if all the adjacent intervals are non-overlapping,
     * intervals not adjacent are also non-overlapping.
     * <p>
     * TC = O(number_of_intervals * log(number_of_intervals)).
     * To sort intervals = O(number_of_intervals * log(number_of_intervals)).
     * To compare all the adjacent intervals = O(number_of_intervals).
     * <p>
     * SC = O(number_of_intervals).
     * Space used for input = O(number_of_intervals).
     * Space used for output = O(1).
     * Auxiliary space used = O(1).
     * Total space complexity = O(number_of_intervals).
     */
    static Integer canAttendAllMeetings(ArrayList<ArrayList<Integer>> intervals) {
        // Sorting in ascending order of start value of an interval
        // if start value is same for two intervals then sort in ascending order of end value of intervals
        intervals.sort((l1, l2) -> {
            if (l1.get(0) - l2.get(0) != 0) {
                return l1.get(0) - l2.get(0);
            }
            return l1.get(1) - l2.get(1);
        });
        // As intervals have at least one element
        int previous_end = intervals.get(0).get(1);
        for (int i = 1; i < intervals.size(); i++) {
            if (previous_end > intervals.get(i).get(0)) {
                return 0;
            }
            // Updated previous' end.
            previous_end = intervals.get(i).get(1);
        }
        return 1;
    }

    /**
     * Asymptotic complexity in terms of size of the input array `n`:
     * Time: O(n^2 + n * log(n)).
     * Auxiliary space: O(1).
     * Total space: O(n^2).
     */
    static ArrayList<String> findZeroSum(ArrayList<Integer> arr) { // 3Sum
        Set<String> answer = new HashSet<>();
        Collections.sort(arr); // A prerequisite for the two pointer technique to work.
        int n = arr.size();
        for (int index = 0; index < n; index++) {
            int currentElement = arr.get(index);
            // We will look for two elements that sum up to this:
            int neededSum = -currentElement;
            int left = index + 1, right = n - 1;
            while (left < right) {
                int sum = arr.get(left) + arr.get(right);
                if (sum == neededSum) {
                    answer.add(currentElement + "," + arr.get(left) + "," + arr.get(right));
                    left++; // "right--" would also work fine here.

                    // Note that the three numbers in our magic triplet strings
                    // will always be sorted in the increasing order because
                    // we sorted the array and index > left > right.
                    // Using a set to store the strings is enough for
                    // avoiding duplicates in the answer.
                } else if (sum > neededSum) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return new ArrayList<>(answer);
    }

    /**
     * The idea is to use the merging technique of the Merge Sort algorithm. We simulate merging the three arrays into one.
     * While doing so, we will always consider the elements of the three arrays in sorted order,
     * thus we can check for equality of the three elements being considered and get the common elements.
     * Time Complexity = O(L + M + N).
     * We iterate over array arr1, arr2, arr3 only once which leads to above time complexity.
     * Auxiliary Space Used = O(1).
     * Space Complexity = O(L + M + N).
     * Input size = O(L + M + N).
     * Output size = O(min(L, M, N)).
     * Auxiliary space used = O(1).
     * Total space complexity = O(L + M + N).
     */
    // Intersection Of Three Sorted Arrays
    static ArrayList<Integer> findIntersection(ArrayList<Integer> arr1,
                                               ArrayList<Integer> arr2,
                                               ArrayList<Integer> arr3) {
        //intersection of n arrays
        int p1 = 0, p2 = 0, p3 = 0;
        ArrayList<Integer> resp = new ArrayList<>();
        while (p1 < arr1.size() && p2 < arr2.size() && p3 < arr3.size()) {

            if (arr1.get(p1).equals(arr2.get(p2)) && arr2.get(p2).equals(arr3.get(p3))) {
                resp.add(arr1.get(p1));
                p1++;
                p2++;
                p3++;
            } else {
                int min = Math.min(arr1.get(p1), arr2.get(p2));
                min = Math.min(arr3.get(p3), min);
                if (arr1.get(p1) == min)
                    p1++;
                else if (arr2.get(p2) == min)
                    p2++;
                else
                    p3++;
            }
        }
        if (resp.isEmpty())
            resp.add(-1);
        return resp;
    }

    // Definition for singly-linked list.
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode mergeTwoSortedLists(ListNode list1, ListNode list2) {
        final ListNode root = new ListNode();
        ListNode prev = root;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                prev.next = list1;
                list1 = list1.next;
            } else {
                prev.next = list2;
                list2 = list2.next;
            }
            prev = prev.next;
        }
        prev.next = list1 != null ? list1 : list2;
        return root.next;
    }

    /**
     * Asymptotic complexity in terms of total length of all given linked lists `n` and `k`:
     * Time: O(n * log(k)).
     * Auxiliary space: O(1).
     * Total space: O(n + k).
     */

    static class LinkedListNode {
        Integer value;
        LinkedListNode next;

        LinkedListNode(Integer value) {
            this.value = value;
            this.next = null;
        }
    }

    static class Pair implements Comparable<Pair> {
        int value, index;

        public Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }

        @Override
        public int compareTo(Pair o) {
            return this.value - o.value;
        }
    }

    static LinkedListNode mergeKSortedLists(ArrayList<LinkedListNode> lists) {
        LinkedListNode head = null;
        LinkedListNode tail = null;

        PriorityQueue<Pair> pq = new PriorityQueue<>();

        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i) != null) {
                pq.add(new Pair(lists.get(i).value, i));
            }
        }

        int min_index;
        while (!pq.isEmpty()) {
            min_index = pq.peek().index;
            pq.poll();

            if (head == null) {
                head = lists.get(min_index);
                tail = lists.get(min_index);
            } else {
                tail.next = lists.get(min_index);
                tail = tail.next;
            }

            lists.set(min_index, lists.get(min_index).next);
            tail.next = null;

            if (lists.get(min_index) != null) {
                pq.add(new Pair(lists.get(min_index).value, min_index));
            }
        }
        return head;
    }

    /**
     * Asymptotic complexity in terms of size of `arr` `n` and `k`:
     * Time: O(n).
     * Auxiliary space: O(n).
     * Total space: O(n + k).
     */
    static ArrayList<Integer> findTopKFrequentElements(ArrayList<Integer> arr, Integer k) {
        HashMap<Integer, Integer> frequency = new HashMap<>();
        ArrayList<ArrayList<Integer>> numbers = new ArrayList<>();
        HashMap<Integer, Boolean> taken = new HashMap<>();

        for (int i = 0; i < arr.size() + 1; i++) {
            numbers.add(new ArrayList<>());
        }

        // Store the frequency of every element.
        for (Integer value : arr) {
            frequency.put(value, frequency.getOrDefault(value, 0) + 1);
            // numbers.get(frequency.get(value)).add(value);
        }

        // Store every element at the index equal to its frequency in 'numbers'.
        for (Integer value : arr) {
            if (taken.getOrDefault(value, false)) {
                continue;
            } else {
                taken.put(value, true);
                numbers.get(frequency.get(value)).add(value);
            }
        }

        ArrayList<Integer> result = new ArrayList<>();

        // Build 'result' by taking elements with top frequency from 'numbers'.
        for (int i = arr.size(); i >= 1; i--) {
            if (k == 0) {
                break;
            }
            while (numbers.get(i).size() > 0 && k > 0) {
                k--;
                result.add(numbers.get(i).get(numbers.get(i).size() - 1));
                numbers.get(i).remove(numbers.get(i).size() - 1);
            }
        }

        return result;
    }

    static ArrayList<Integer> kthLargestInStream(Integer k,
                                                 ArrayList<Integer> initial_stream,
                                                 ArrayList<Integer> append_stream) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a)); //  ((a, b) -> a - b)
        for (int i : initial_stream) {
            pq.offer(i);
            if (pq.size() > k) pq.poll();
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : append_stream) {
            pq.offer(i);
            if (pq.size() > k) pq.poll();
            list.add(pq.peek());
        }
        return list;
    }

    static Integer kthLargestInAnArray(ArrayList<Integer> numbers, Integer k) {
        // Use QuickSelector which runs in O(n) to find the Kth largest number
        return quickSelect(numbers, 0, numbers.size() - 1, numbers.size() - k);
    }

    private static int quickSelect(ArrayList<Integer> nums, int start, int end, int index) {
        if (start == end) {
            return nums.get(start);
        }

        //Lomuto's partition
        int pivot = partition(nums, start, end);
        if (pivot == index) {
            return nums.get(pivot);
        } else if (pivot < index) {
            return quickSelect(nums, pivot + 1, end, index);
        } else {
            return quickSelect(nums, start, pivot - 1, index);
        }
    }

    private static int partition(ArrayList<Integer> nums, int start, int end) {
        // Get a random
        int rand = new Random().nextInt(end - start + 1) + start;
        // swap rand with start index
        Collections.swap(nums, start, rand);
        int small = start;
        for (int big = start + 1; big <= end; big++) {
            if (nums.get(big) <= nums.get(start)) {
                small++;
                Collections.swap(nums, small, big);
            }
        }
        //Swap pivot(start) and small pointer
        Collections.swap(nums, start, small);
        return small;
    }

    // continuousMedian
    static ArrayList<Integer> onLineMedian(ArrayList<Integer> stream) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        ArrayList<Integer> result = new ArrayList<>();
        for (Integer num : stream) {

            if (maxHeap.isEmpty())
                maxHeap.add(num);
            else if (num > maxHeap.peek())
                minHeap.add(num);  // minHeap will hold min of max side
            else
                maxHeap.add(num);  //maxHeap will hold max of min side

            // Balance the tree
            if (minHeap.size() - maxHeap.size() == 2) {
                maxHeap.add(minHeap.remove());
            } else if (maxHeap.size() - minHeap.size() == 2) {
                minHeap.add(maxHeap.remove());
            }

            //Calculate median
            if (minHeap.size() > maxHeap.size()) {
                result.add(minHeap.peek());
            } else if (maxHeap.size() > minHeap.size()) {
                result.add(maxHeap.peek());
            } else {
                result.add((maxHeap.peek() + minHeap.peek()) / 2);
            }

        }
        return result;
    }

    /**
     * Asymptotic complexity in terms of size of `numbers` `n`:
     * Time: O(n).
     * Auxiliary space: O(1).
     * Total space: O(n).
     */
    static ArrayList<Integer> segregateEvensAndOdds(ArrayList<Integer> numbers) {
        int n = numbers.size();

        int left = 0, right = n - 1;

        while (right >= left) { // Move pointers until they cross.
            if (numbers.get(right) % 2 == 0) {
                int tmp = numbers.get(right);
                numbers.set(right, numbers.get(left));
                numbers.set(left, tmp);
                left++; // All values to the left of the left pointer will be even.
            } else {
                right--; // All values to the right of the right pointer will be odd.
            }
        }
        return numbers;
    }

    /**
     * Asymptotic complexity in terms of size of `numbers` `n`:
     * Time: O(n).
     * Auxiliary space: O(1).
     * Total space: O(n).
     */
    static ArrayList<Character> dutchFlagSort(ArrayList<Character> balls) {
        if (balls == null || balls.size() == 1) {
            return balls;
        }
        // FIRST_CHAR = 'R'
        // SECOND_CHAR = 'G'
        // THIRD_CHAR = 'B'
        // maintain 4 regions
        // 0 -> low - R
        // low -> mid - G
        // mid -> high - unknown
        // high -> end - B
        int red = 0;
        int mid = 0;
        int blue = balls.size() - 1;
        while (mid <= blue) {
            Character ball = balls.get(mid);
            if (ball == 'R') {
                Collections.swap(balls, mid, red);
                // increase the low and mid-regions
                red++;
                mid++;
            } else if (ball == 'G') {
                // low to mid is in the right place
                mid++;
            } else { // ball == 'B'
                // will be last character
                Collections.swap(balls, mid, blue);
                // lower the high number
                blue--;
            }
        }

        return balls;
    }

    /**
     * Time: O(n).
     * Auxiliary space: O(1).
     * Total space: O(n).
     */
    static ArrayList<Integer> mergeOneIntoAnother(ArrayList<Integer> first, ArrayList<Integer> second) {
        int firstIndex = first.size() - 1;
        int secondIndex = firstIndex;
        int index = second.size() - 1;

        while (firstIndex >= 0 && secondIndex >= 0) {
            if (second.get(secondIndex) >= first.get(firstIndex)) {
                second.set(index--, second.get(secondIndex--));
            } else {
                second.set(index--, first.get(firstIndex--));
            }
        }

        while (firstIndex >= 0) {
            second.set(index--, first.get(firstIndex--));
        }

        return second;
    }

    static ArrayList<ArrayList<Integer>> fourSum(ArrayList<Integer> arr, Integer target) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        Collections.sort(arr);
        for (int i = 0; i < arr.size(); i++) {
            if (i == 0 || !arr.get(i).equals(arr.get(i - 1))) {
                for (int j = i + 1; j < arr.size(); j++) {
                    if (j == i + 1 || !arr.get(j).equals(arr.get(j - 1))) {
                        threeSum(i, j, arr, target, result);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Asymptotic complexity in terms of the length of input list `n`:
     * Time: O(n^3).
     * Auxiliary space: O(1).
     * Total space: O(n^4).
     */
    static void threeSum(int i, int j, ArrayList<Integer> arr, int target, ArrayList<ArrayList<Integer>> result) {
        int low = j + 1;
        int high = arr.size() - 1;

        while (low < high) {
            int sum = arr.get(low) + arr.get(high) + arr.get(i) + arr.get(j);

            if (sum < target) {
                low++;
            } else if (sum > target) {
                high--;
            } else {
                result.add(new ArrayList<>(Arrays.asList(arr.get(i), arr.get(j), arr.get(low), arr.get(high))));
                low++;
                high--;
                while (low < high && arr.get(low).equals(arr.get(low - 1))) {
                    low++;
                }
            }
        }
    }

    /**
     * Asymptotic complexity in terms of the length of `words` ( = `n`):
     * Time: O(n * log(n)).
     * Auxiliary space: O(n).
     * Total space: O(n).
     */
    static ArrayList<String> kMostFrequent(Integer k, ArrayList<String> words) {

        //Transform and conquer : heap based sol
        HashMap<String, Integer> freqMap = new HashMap<>();

        //build map
        for (String word : words) {
            if (freqMap.containsKey(word)) {
                freqMap.put(word, freqMap.get(word) + 1);
            } else {
                freqMap.put(word, 1);
            }
        }
        //define heap impl - priorityQueue
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                if (e1.getValue() < e2.getValue()) {
                    return 1;
                } else if (e1.getValue() > e2.getValue()) {
                    return -1;
                } else {  //same freq words
                    return e1.getKey().compareTo(e2.getKey());
                }
            }
        });
        //push input in heap
        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            pq.offer(entry);
        }
        //extract max k times and build output
        ArrayList<String> kFreqWords = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            kFreqWords.add(pq.poll().getKey());
        }
        return kFreqWords;
    }

    /**
     * Asymptotic complexity in terms of length of the input list `n`:
     * Time: O(n * n).
     * Auxiliary space: O(1).
     * Total space: O(n).
     */
    static Integer countTriplets(Integer target, ArrayList<Integer> arr) {
        int count = 0;
        Collections.sort(arr);
        for (int i = 0; i < arr.size(); i++) {
            int j = i + 1;
            int k = arr.size() - 1;
            while (j < k) {
                if (arr.get(i) + arr.get(j) + arr.get(k) < target) {
                    count = count + (k - j);
                    j++;
                } else {
                    k--;
                }

            }
        }
        return count;
    }

    public static void main(String[] args) {
        ArrayList<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(4);
        input.add(5);
        input.add(5);
        input.add(3);
        input.add(3);
//        System.out.println(pairSumSortedArrayHM(input, 8));
//        System.out.println(findTopKFrequentElements(input, 3));
//        System.out.println(kthLargestInStream(2, input, input));

    }
}
