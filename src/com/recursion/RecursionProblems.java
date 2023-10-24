package com.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 12 practise problems
public class RecursionProblems {

    static Integer countAllSubsets(Integer n) {
        if (n == 0) {
            return 1;
        } else {
            return 2 * countAllSubsets(n - 1);
        }
    }

    static Integer findFibonacci(Integer n) {
        int[] memo = new int[3];
        memo[0] = 0;
        memo[1] = 1;
        for (int i = 2; i <= n.intValue(); i++) {
            int index = i % 3;
            int value = memo[(i - 1) % 3] + memo[(i - 2) % 3];
            memo[index] = value;
        }
        return memo[n.intValue() % 3];
    }

    // N choose K combinations
    static ArrayList<ArrayList<Integer>> NChooseKCombinations(Integer n, Integer k) {

        ArrayList<Integer> current = new ArrayList<>();

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        NChooseKCombinationsHelper(1, n, k, current, result);

        return result;
    }

    static void NChooseKCombinationsHelper(int currentNum, Integer n, Integer k, ArrayList<Integer> current,
                                           ArrayList<ArrayList<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (currentNum == n + 1) return;
        current.add(currentNum);
        NChooseKCombinationsHelper(currentNum + 1, n, k, current, result);
        current.remove(current.size() - 1);
        NChooseKCombinationsHelper(currentNum + 1, n, k, current, result);
    }

    static ArrayList<String> getBinaryStrings(Integer n) {
        // Write your code here.
        ArrayList<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder("");
        getBinaryStringsHelper(n, sb, res);
        return res;
    }

    static void getBinaryStringsHelper(int n, StringBuilder sb, ArrayList<String> res) {
        if (n == 0) {
            res.add(sb.toString());
            return;
        }

        sb.append("0");
        getBinaryStringsHelper(n - 1, sb, res);
        sb.deleteCharAt(sb.length() - 1);

        sb.append("1");
        getBinaryStringsHelper(n - 1, sb, res);
        sb.deleteCharAt(sb.length() - 1);

        return;
    }

    static ArrayList<ArrayList<Integer>> towerOfHanoi(Integer n) {
        ArrayList<ArrayList<Integer>> result = new ArrayList();
        towerOfHanoiHelper(n, 1, 2, 3, result);
        return result;
    }

    static void towerOfHanoiHelper(Integer n, int src, int aux, int dest, ArrayList<ArrayList<Integer>> result) {

        if (n == 1) {
            result.add(new ArrayList<>(Arrays.asList(src, dest)));
            return;
        }
        //  transfer n-1 disks from source to aux
        towerOfHanoiHelper(n - 1, src, dest, aux, result);
        //  transfer 1 disk from source to dest
        towerOfHanoiHelper(1, src, aux, dest, result);
        //  transfer n-1 disks from aux to dest
        towerOfHanoiHelper(n - 1, aux, src, dest, result);

    }

    /**
     * Asymptotic complexity in terms of `n` =  size of the input array:
     * Time: O(n * n!).
     * Auxiliary space: O(n).
     * Total space: O(n * n!).
     */
    static ArrayList<ArrayList<Integer>> permuteArrayOfUniqueIntegers(ArrayList<Integer> arr) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        permuteArrayOfUniqueIntegersHelper(arr, 0, ans);
        return ans;
    }

    static void permuteArrayOfUniqueIntegersHelper(ArrayList<Integer> arr, int fixed_index, ArrayList<ArrayList<Integer>> ans) {
        // Base case
        if (fixed_index >= arr.size()) {
            // Create a new copy of arr and add to ans
            ans.add(new ArrayList<>(arr));
            return;
        }

        for (int i = fixed_index; i < arr.size(); i++) {
            Collections.swap(arr, fixed_index, i);
            permuteArrayOfUniqueIntegersHelper(arr, fixed_index + 1, ans);
            // Re-swap to restore the original arr state
            Collections.swap(arr, fixed_index, i);
        }
    }

    /**
     * Asymptotic complexity in terms of the length of `s` `n`:
     * Time: O(2^n * n).
     * Auxiliary space: O(2^n * n).
     * Total space: O(2^n * n).
     */
    static ArrayList<String> generateAllSubsets(String s) {

        ArrayList<String> output = new ArrayList<>();

        // Recursive function to generate subsets
        generateAllSubsetsHelper(new ArrayList<>(), s, 0, output);

        return output;
    }

    private static void generateAllSubsetsHelper(ArrayList<Character> soFar, String s, int idx, ArrayList<String> output) {
        if (idx == s.length()) {
            // Base case: We reached the end of the string.
            // Convert the list of characters to a string and store it in the output.
            StringBuilder subset = new StringBuilder();
            for (char c : soFar) {
                subset.append(c);
            }
            output.add(subset.toString());
            return;
        }

        // Recurse after adding the current character and not adding the current character to soFar
        ArrayList<Character> withCurrent = new ArrayList<>(soFar);
        withCurrent.add(s.charAt(idx));
        generateAllSubsetsHelper(withCurrent, s, idx + 1, output);
        generateAllSubsetsHelper(soFar, s, idx + 1, output);
    }

    /**
     * TC = O(2^n)
     * SC = O(n + 2^n)
     */

    static void populatePermutationsRecursively(char[] chars,
                                                int index,
                                                ArrayList<String> res) {
        if (index == chars.length) {
            res.add(new String(chars));
            return;
        }

        // If current character is not a letter, we leave it unchanged and make only one recursive call.
        if (Character.isDigit(chars[index])) {
            populatePermutationsRecursively(chars, index + 1, res);
        } else {
            // Converting current character to uppercase and recursively exploring the remainder of the string.
            chars[index] = Character.toUpperCase(chars[index]);
            populatePermutationsRecursively(chars, index + 1, res);

            // Converting current character to lowercase and recursively exploring the remainder of the string.
            chars[index] = Character.toLowerCase(chars[index]);
            populatePermutationsRecursively(chars, index + 1, res);
        }
    }

    static ArrayList<String> letterCasePermutation(String s) {
        if (s == null) {
            return new ArrayList<>();
        }
        ArrayList<String> res = new ArrayList<>();
        populatePermutationsRecursively(s.toCharArray(), 0, res);
        return res;
    }

    static ArrayList<ArrayList<Integer>> permuteArrayOfUniqueIntegersWithDuplicates(ArrayList<Integer> arr) {
        ArrayList<Integer> ps = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        permuteArrayOfUniqueIntegersWithDuplicatesHelper(arr, 0, ps, result);

        Set<ArrayList<Integer>> hs = new HashSet<>(result);
        ArrayList<ArrayList<Integer>> finResult = new ArrayList<>(hs);

        return finResult;
    }

    public static ArrayList<ArrayList<Integer>> permuteArrayOfUniqueIntegersWithDuplicatesHelper(ArrayList<Integer> input, int i, ArrayList<Integer> ps,
                                                                                                 ArrayList<ArrayList<Integer>> result) {

        if (input.size() == i) {
            result.add(new ArrayList<>(ps));
            return result;
        }

        for (int j = i; j < input.size(); j++) {
            Collections.swap(input, i, j);
            ps.add(input.get(i));
            permuteArrayOfUniqueIntegersWithDuplicatesHelper(input, i + 1, ps, result);
            Collections.swap(input, j, i);
            ps.remove(i);
        }

        return result;

    }

    // Subsets With Duplicate Characters
    static ArrayList<String> getDistinctSubsets(String s) {
        ArrayList<String> result = new ArrayList<>();
        char[] ch = s.toCharArray();
        Arrays.sort(ch);
        getDistinctSubsetsHelper(ch, new StringBuilder(), 0, result);
        return result;
    }

    static void getDistinctSubsetsHelper(char[] ch_arr, StringBuilder currStr, int idx, ArrayList<String> result) {
        // Backtracking case
        if (idx == ch_arr.length) {
            result.add(currStr.toString());
            return;
        } else {
            // Base case
            if (ch_arr.length == currStr.length()) {
                return;
            }
            // internal leaf node worker
            //inclusion
            currStr.append(ch_arr[idx]);
            getDistinctSubsetsHelper(ch_arr, currStr, idx + 1, result);
            currStr.deleteCharAt(currStr.length() - 1);

            while (idx < ch_arr.length - 1 && ch_arr[idx] == ch_arr[idx + 1]) {
                idx++;
            }

            //exclusion
            getDistinctSubsetsHelper(ch_arr, currStr, idx + 1, result);
        }
    }


    /**
     * Asymptotic complexity in terms of the size of the input string `n`:
     * Time: O(n * 4^n).
     * Auxiliary space: O(n).
     * Total space: O(4^n).
     */
    static ArrayList<String> getWordsFromPhoneNumber(String phone_number) {
        Map<Character, String> digitMapping = new HashMap<>();
        digitMapping.put('2', "abc");
        digitMapping.put('3', "def");
        digitMapping.put('4', "ghi");
        digitMapping.put('5', "jkl");
        digitMapping.put('6', "mno");
        digitMapping.put('7', "pqrs");
        digitMapping.put('8', "tuv");
        digitMapping.put('9', "wxyz");

        ArrayList<String> result = new ArrayList<>();

        // Since digits 0 and 1 map to no characters, remove them from the input string as they have no effect on the output
        phone_number = phone_number.replace("0", "").replace("1", "");
        if (phone_number.length() == 0) {
            result.add("");
        } else {
            getWordsFromPhoneNumberHelper(phone_number, result, digitMapping, 0, new StringBuffer());
        }
        return result;
    }

    private static void getWordsFromPhoneNumberHelper(String phone_number,
                                                      ArrayList<String> result,
                                                      Map<Character, String> digitMapping,
                                                      int currentIndex,
                                                      StringBuffer word) {

        if (currentIndex == phone_number.length()) {
            result.add(word.toString());
            return;
        }

        String letters = digitMapping.get(phone_number.charAt(currentIndex));

        for (char letter : letters.toCharArray()) {
            getWordsFromPhoneNumberHelper(phone_number, result, digitMapping, currentIndex + 1, word.append(letter));
            word.deleteCharAt(currentIndex); // Backtrack.
        }
    }

    /**
     * TC = O(n * 2n)
     * SC = O(n * 2n).
     * Space used for input: O(n).
     * Auxiliary space used: O(n).
     * Space used for output: O(n * 2n).
     * So, total space complexity: O(n * 2n).
     */
    // Generate All Combinations With Sum Equal To Target
    public static ArrayList<ArrayList<Integer>> generateAllCombinations(List<Integer> arr, int target) {
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();

        Collections.sort(arr);
        generateCombinationSum(arr, target, results, new ArrayList<Integer>(), 0, 0);

        return results;
    }

    private static void generateCombinationSum(final List<Integer> input, final int target, final ArrayList<ArrayList<Integer>> results,
                                               final ArrayList<Integer> candidate,
                                               final int index, final int runningSum) {
        if (runningSum == target) {
            results.add(new ArrayList<>(candidate));
            return;
        }

        if (index >= input.size()) {
            return;
        }

        Integer prevValue = null;
        for (int nextIndex = index; nextIndex < input.size(); nextIndex++) {
            Integer nextValue = input.get(nextIndex);
            if (prevValue == null || !prevValue.equals(nextValue)) {
                prevValue = nextValue;
                candidate.add(nextValue);
                generateCombinationSum(input, target, results, candidate, nextIndex + 1, runningSum + nextValue);
                candidate.remove(candidate.size() - 1);
            }
        }

        return;
    }

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        System.out.println(permuteArrayOfUniqueIntegers(integers));
        //System.out.println(letterCasePermutation("a1b2"));
        System.out.println(getDistinctSubsets("aabc"));
    }
}
