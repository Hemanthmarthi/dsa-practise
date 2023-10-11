package com.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 6 sorting algorithms
 * */
public class Sorting {

    private static ArrayList<Integer> selectionSort(ArrayList<Integer> arr) {
        if (arr == null || arr.size() == 0) {
            return arr;
        }
        // Goal: Sort an array called arr which has n integers: arr[0...n-1]
        for (int i = 0; i < arr.size(); i++) {
            // ith iteration: Find the minimum element from index i to n-1, and put it in index i
            int minElementIndex = i;
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(j) < arr.get(minElementIndex)) {
                    minElementIndex = j;
                }
            }
            // At this point, the index of the minimum element in arr[i... n-i] is know to be minElementIndex
            // Put it in index i, by swapping it with whatever is there
            swap(arr, i, minElementIndex);
        }
        return arr;
    }

    private static ArrayList<Integer> bubbleSort(ArrayList<Integer> arr) {
        if (arr == null || arr.size() == 0) {
            return arr;
        }
        // Goal: Sort an array which has integers: arr[0...n-1]
        for (int i = 0; i < arr.size(); i++) {
            // ith iteration: Find the minimum element from index i to n-1, and put it in index i
            // Find the min element by scanning the array from index n-1 to index i+1, and swapping the current element
            // with the element to its left, if they are out of order, i.e, bubble the minimum element from right to left
            for (int j = arr.size() - 1; j > i; j--) {
                if (arr.get(j - 1) > arr.get(j)) {
                    swap(arr, j - 1, j);
                }
            }
        }
        return arr;
    }

    private static ArrayList<Integer> insertionSort(ArrayList<Integer> arr) {
        if (arr == null || arr.size() == 0) {
            return arr;
        }
        // Goal: Sort an array which has integers: arr[0...n-1]
        for (int i = 0; i < arr.size(); i++) {
            // ith iteration: Keep arr[0...i] sorted, nums[i] may not be in its right place, so insert it wherever it should go
            int j = i - 1;
            int temp = arr.get(i);
            while (j >= 0 && arr.get(j) > temp) {
                arr.set(j + 1, arr.get(j)); // slightly more efficient; shifting instead of a repeated swaps
                j--;
            }
            arr.set(j + 1, temp);
        }
        return arr;
    }

    private static ArrayList<Integer> mergeSort(ArrayList<Integer> arr) {
        mergeSortHelper(arr, 0, arr.size() - 1);
        return arr;
    }

    private static void mergeSortHelper(ArrayList<Integer> arr, int start, int end) {
        // leaf node
        if (start == end) {
            return;
        }
        // Internal node worker
        int mid = start + (end - start) / 2;
        // sort left sub array
        mergeSortHelper(arr, start, mid);
        mergeSortHelper(arr, mid + 1, end);
        // merge the sorted halves
        int i = start;
        int j = mid + 1;
        ArrayList<Integer> auxArray = new ArrayList<>();
        while (i <= mid && j <= end) {
            if (arr.get(i) <= arr.get(j)) {
                auxArray.add(arr.get(i));
                i++;
            } else { // i > j
                auxArray.add(arr.get(j));
                j++;
            }
        }
        // gather phase
        while (i <= mid) {
            auxArray.add(arr.get(i));
            i++;
        }
        // gather phase
        while (j <= end) {
            auxArray.add(arr.get(j));
            j++;
        }

        for (int k = start; k <= end; k++) {
            arr.set(k, auxArray.get(k - start));
        }
    }

    private static ArrayList<Integer> quickSort(ArrayList<Integer> arr) {
        quickSortHelper(arr, 0, arr.size() - 1);
        return arr;
    }

    private static void quickSortHelper(ArrayList<Integer> arr, int start, int end) {
        // Subproblem size = 0 or 1 means nothing to do
        if (start >= end) {
            return;
        }
        // Recursive case: Subproblem size at least 2, so cut the sub array into to halves and delegate the sorting
        // work to two other people hired under you
        // To make the two halves, partition the array around a random value
        // Pick a random pivot
        int randomPivot = new Random().nextInt((end - start) + 1) + start;
        // move it to the extreme left
        Collections.swap(arr, start, randomPivot);
        int smaller = start;
        int pivot = arr.get(start);
        for (int bigger = start + 1; bigger <= end; bigger++) {
            if (arr.get(bigger) < pivot) {
                smaller++;
                Collections.swap(arr, smaller, bigger);

            }
        }
        // Now move the pivot at the boundary of the orange and green regions
        Collections.swap(arr, smaller, start);

        // Pivot is now at the smaller index. Split the array into two partitions, and delegate their sorting work to two people under you
        quickSortHelper(arr, start, smaller - 1);
        quickSortHelper(arr, smaller + 1, end);

    }

    /**
     * This implementation uses the heapify function to build a max heap and maintain the heap property.
     * It starts by building a max heap from the input array,
     * then repeatedly extracts the maximum element (the root of the heap) and places it at the end of the array,
     * reducing the heap size by 1 each time. Finally, it produces a sorted array by swapping
     * the root element with the last element and heapifying the reduced heap.
     * The time complexity of Heap Sort is O(n log n), where n is the number of elements in the array.
     */
    static ArrayList<Integer> heapSort(ArrayList<Integer> arr) {
        int[] input = new int[arr.size()];

        for (int i = 0; i < arr.size(); i++) {
            input[i] = arr.get(i);
        }

        // heapify: Creating max  heap, iterating for all non-leaf indices, since leaf
        // indices don't have children to check for
        for (int i = input.length / 2; i >= 0; i--) {
            heapify(input, i, input.length - 1);
        }

        int length = input.length - 1;
        // Extract elements from heap one by one
        for (int i = length; i > 0; i--) {
            // Swap the root (maximum element) with the last element
            swap(input, i, 0);
            length -= 1;
            // Heapify the reduced heap
            heapify(input, 0, length);
        }

        for (int i = 0; i < input.length; i++) {
            arr.set(i, input[i]);
        }

        return arr;
    }

    static void heapify(int[] input, int i, int length) {
        // find the largest element among parent, left, right
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left <= length && input[left] > input[largest])
            largest = left;
        if (right <= length && input[right] > input[largest])
            largest = right;

        if (largest != i) {
            swap(input, i, largest);
            heapify(input, largest, length);
        }
    }

    static void swap(ArrayList<Integer> arr, int i, int j) {
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);

    }

    static void swap(int[] input, int i, int j) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }

    public static void main(String[] args) {
        ArrayList<Integer> input = new ArrayList<>();
        input.add(5);
        input.add(3);
        input.add(4);
        input.add(1);
        input.add(7);
        input.add(3);
//        System.out.println(selectionSort(input));
//        System.out.println(bubbleSort(input));
//        System.out.println(insertionSort(input));
//        System.out.println(mergeSort(input));
//        System.out.println(quickSort(input));
        System.out.println(heapSort(input));
    }

}