package com.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 20 practise problems
public class StringProblems {

    // Check whether a String is palindrome or not
    // O(n) time | O(1) space
    public static boolean isPalindrome(String str) {
        int leftIdx = 0;
        int rightIdx = str.length() - 1;
        while (leftIdx < rightIdx) {
            if (str.charAt(leftIdx) != str.charAt(rightIdx)) {
                return false;
            }
            leftIdx++;
            rightIdx--;
        }
        return true;
    }

    // Caesar Cipher Encryptor
    // O(n) time | O(n) space
    public static String caesarCypherEncryptor(String str, int key) {
        char[] newLetters = new char[str.length()];
        int newKey = key % 26;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < str.length(); i++) {
            newLetters[i] = getNewLetter(str.charAt(i), newKey, alphabet);
        }
        return new String(newLetters);
    }

    private static char getNewLetter(char letter, int key, String alphabet) {
        int newLetterCode = alphabet.indexOf(letter) + key;
        return alphabet.charAt(newLetterCode % 26);
    }

    // Run-Length encoding
    // O(n) time | O(n) space - where n is the length of the input string
    public static String runLengthEncoding(String string) {
        // The input string is guaranteed to be non-empty,
        // so our first run will be of at least length 1
        StringBuilder encodedStringCharacters = new StringBuilder();
        int currentRunLength = 1;

        for (int i = 1; i < string.length(); i++) {
            char currentCharacter = string.charAt(i);
            char previousCharacter = string.charAt(i - 1);

            if ((currentCharacter != previousCharacter) || (currentRunLength == 9)) {
                encodedStringCharacters.append(currentRunLength);
                encodedStringCharacters.append(previousCharacter);
                currentRunLength = 0;
            }
            currentRunLength += 1;
        }
        // Handle the last run
        encodedStringCharacters.append(currentRunLength);
        encodedStringCharacters.append(string.charAt(string.length() - 1));


        return encodedStringCharacters.toString();
    }

    // Write a function takes a non-empty stings & returns a list of characters that are common to all the strings in the lists.
    // O(n * m) time | O(m) space - where n is the number of strings,
    // and m is the length of the longest string
    static String[] commonCharacters(String[] strings) {
        String smallestString = getSmallestString(strings);
        HashSet<Character> potentialCommonCharacters = new HashSet<>();
        for (int i = 0; i < smallestString.length(); i++) {
            potentialCommonCharacters.add(smallestString.charAt(i));
        }

        for (String string : strings) {
            removeNonexistentCharacters(string, potentialCommonCharacters);
        }

        String[] output = new String[potentialCommonCharacters.size()];
        int i = 0;
        for (Character character : potentialCommonCharacters) {
            output[i] = character.toString();
            i++;
        }

        return output;
    }
    private static String getSmallestString(String[] strings) {
        String smallestString = strings[0];
        for (String string : strings) {
            if (string.length() < smallestString.length()) {
                smallestString = string;
            }
        }
        return smallestString;
    }
    private static void removeNonexistentCharacters(String string, HashSet<Character> potentialCommonCharacters) {
        HashSet<Character> uniqueStringCharacters = new HashSet<>();
        for (int i = 0; i < string.length(); i++) {
            uniqueStringCharacters.add(string.charAt(i));
        }

        HashSet<Character> charactersToRemove = new HashSet<>();
        for (char character : potentialCommonCharacters) {
            if (!uniqueStringCharacters.contains(character)) {
                charactersToRemove.add(character);
            }
        }
        for (char character : charactersToRemove) {
            potentialCommonCharacters.remove(character);
        }
    }

    // O(n + m) time | O(c) space - where n is the number of characters, m is the length of the document,
    // and c is the number of unique characters in the character string
    static boolean generateDocument(String characters, String document) {
        HashMap<Character, Integer> characterCounts = new HashMap<>();

        for (char character : characters.toCharArray()) {
            characterCounts.put(character, characterCounts.getOrDefault(character, 0) + 1);
        }
        for (char character : document.toCharArray()) {
            if (!characterCounts.containsKey(character) || characterCounts.get(character) == 0) {
                return false;
            }
            characterCounts.put(character, characterCounts.get(character) - 1);
        }

        return true;
    }

    // Write a function that takes a string of lowercase english-alphabet letter and returns the index of the
    // string's first non-repeating character
    // O(n) time | O(1) space
    // where n is the length of the input string
    // Constant space because the input string only has lowercase english-alphabet letters;
    // thus, the hashmap table will never have more than 26 character frequencies.
    static int firstNonRepeatingCharacter(String string) {
        HashMap<Character, Integer> characterFrequencies = new HashMap<>();

        for (char character : string.toCharArray()) {
            characterFrequencies.put(character, characterFrequencies.getOrDefault(character, 0) + 1);
        }
        for (int idx = 0; idx < string.length(); idx++) {
            char character = string.charAt(idx);
            if (characterFrequencies.get(character) == 1) {
                return idx;
            }
        }

        return -1;
    }

    // O(n * m) time | O(n * m ) space - where n is the number of words & m is te length of the longest word
    static ArrayList<ArrayList<String>> semordnilap(String[] words) {
        HashSet<String> wordsSet = new HashSet<>(Arrays.asList(words));
        ArrayList<ArrayList<String>> semordnilaPairs = new ArrayList<>();

        for (String word : words) {
            String reverse = new StringBuilder(word).reverse().toString();
            if (wordsSet.contains(reverse) && !reverse.equals(word)) {
                ArrayList<String> semordnilapPair = new ArrayList<>();
                semordnilapPair.add(word);
                semordnilapPair.add(reverse);
                semordnilaPairs.add(semordnilapPair);
                wordsSet.remove(word);
                wordsSet.remove(reverse);
            }
        }

        return semordnilaPairs;
    }


    // O(n^2) time | O(n) space - since we have to slice and store the final substring.
    static String longestPalindromicSubstring(String str) {
        int[] currentLongest = {0, 1};
        for (int i = 1; i < str.length(); i++) {
            int[] odd = getLongestPalindromeFrom(str, i - 1, i + 1);
            int[] even = getLongestPalindromeFrom(str, i - 1, i);
            int[] longest = odd[1] - odd[0] > even[1] - even[0] ? odd : even;
            currentLongest =
                    currentLongest[1] - currentLongest[0] > longest[1] - longest[0]
                            ? currentLongest
                            : longest;
        }
        return str.substring(currentLongest[0], currentLongest[1]);
    }
    private static int[] getLongestPalindromeFrom(String str, int leftIdx, int rightIdx) {
        while (leftIdx >= 0 && rightIdx < str.length()) {
            if (str.charAt(leftIdx) != str.charAt(rightIdx)) {
                break;
            }
            leftIdx--;
            rightIdx++;
        }
        return new int[]{leftIdx + 1, rightIdx};
    }


    // O(w * n log (n)) time | O(wn) space - where w is the number of words & n is the length of the longest word
    static List<List<String>> groupAnagrams(List<String> words) {
        Map<String, List<String>> anagrams = new HashMap<>();

        for (String word : words) {
            char[] charArray = word.toCharArray();
            Arrays.sort(charArray);
            String sortedWord = new String(charArray);

            if (anagrams.containsKey(sortedWord)) {
                anagrams.get(sortedWord).add(word);
            } else {
                anagrams.put(sortedWord, new ArrayList<>(Arrays.asList(word)));
            }
        }

        return new ArrayList<>(anagrams.values());
    }


    // O(1) Time | O(1) space
    public static ArrayList<String> validIPAddresses(String string) {
        ArrayList<String> ipAddressesFound = new ArrayList<String>();
        for (int i = 1; i < Math.min(string.length(), 4); i++) {
            String[] currentIPAddressParts = new String[]{"", "", "", ""};
            currentIPAddressParts[0] = string.substring(0, i);
            if (!isValidPart(currentIPAddressParts[0])) {
                continue;
            }
            for (int j = i + 1; j < i + Math.min((int) string.length() - i, 4); j++) {
                currentIPAddressParts[1] = string.substring(i, j);
                if (!isValidPart(currentIPAddressParts[1])) {
                    continue;
                }
                for (int k = j + 1; k < j + Math.min((int) string.length() - j, 4);
                     k++) {
                    currentIPAddressParts[2] = string.substring(j, k);
                    currentIPAddressParts[3] = string.substring(k);
                    if (isValidPart(currentIPAddressParts[2]) && isValidPart(currentIPAddressParts[3])) {
                        ipAddressesFound.add(join(currentIPAddressParts));
                    }
                }
            }
        }
        return ipAddressesFound;
    }
    static boolean isValidPart(String string) {
        int stringAsInt = Integer.parseInt(string);
        if (stringAsInt > 255) {
            return false;
        }
        return string.length() == Integer.toString(stringAsInt).length(); // check for leading 0
    }
    static String join(String[] strings) {
        StringBuilder sb = new StringBuilder();
        for (int l = 0; l < strings.length; l++) {
            sb.append(strings[l]);
            if (l < strings.length - 1) {

                sb.append(".");

            }
        }
        return sb.toString();
    }


    // O(n) Time | O(n) Space
    static String reverseWordsInString(String string) {
        char[] characters = string.toCharArray();
        reverseListRange(characters, 0, characters.length - 1);
        int startOfWord = 0;
        while (startOfWord < characters.length) {
            int endOfWord = startOfWord;
            while (endOfWord < characters.length && characters[endOfWord] != ' ') {
                endOfWord++;
            }
            reverseListRange(characters, startOfWord, endOfWord - 1);
            startOfWord = endOfWord + 1;
        }
        return new String(characters);
    }
    static void reverseListRange(char[] list, int start, int end) {
        while (start < end) {
            char temp = list[start];
            list[start] = list[end];
            list[end] = temp;
            start++;
            end--;
        }
    }


    // O(n * l) time | O(c) space - where n is the number of words,
    // l is the length of the longest word,
    // and c is the number of unique characters across all words
    public char[] minimumCharactersForWords(String[] words) {
        HashMap<Character, Integer> maximumCharacterFrequencies = new HashMap<>();
        for (String word : words) {
            HashMap<Character, Integer> characterFrequencies = countCharacterFrequencies(word);
            updateMaximumFrequencies(characterFrequencies, maximumCharacterFrequencies);
        }

        return makeArrayFromCharacterFrequencies(maximumCharacterFrequencies);
    }
    private static HashMap<Character, Integer> countCharacterFrequencies(String string) {
        HashMap<Character, Integer> characterFrequencies = new HashMap<>();
        for (char character : string.toCharArray()) {
            characterFrequencies.put(character, characterFrequencies.getOrDefault(character, 0) + 1);
        }
        return characterFrequencies;
    }
    private static void updateMaximumFrequencies(
            HashMap<Character, Integer> frequencies, HashMap<Character, Integer> maximumFrequencies) {
        for (Map.Entry<Character, Integer> frequency : frequencies.entrySet()) {
            char character = frequency.getKey();
            int characterFrequency = frequency.getValue();
            if (maximumFrequencies.containsKey(character)) {
                maximumFrequencies.put(
                        character,
                        Math.max(characterFrequency, maximumFrequencies.get(character))
                );
            } else {
                maximumFrequencies.put(character, characterFrequency);
            }
        }
    }
    private static char[] makeArrayFromCharacterFrequencies(HashMap<Character, Integer> characterFrequencies) {
        ArrayList<Character> characters = new ArrayList<>();
        for (Map.Entry<Character, Integer> frequency : characterFrequencies.entrySet()) {
            char character = frequency.getKey();
            int characterFrequency = frequency.getValue();
            for (int idx = 0; idx < characterFrequency; idx++) {
                characters.add(character);
            }
        }

        char[] charactersArray = new char[characters.size()];
        for (int idx = 0; idx < characters.size(); idx++) {
            charactersArray[idx] = characters.get(idx);
        }
        return charactersArray;
    }


    // O(n) Time | O(1) Space - where n is the length of the shorter string
    static boolean oneEdit(String stringOne, String stringTwo) {
        int lengthOne = stringOne.length();
        int lengthTwo = stringTwo.length();
        if (Math.abs(lengthOne - lengthTwo) > 1) {
            return false;
        }
        boolean madeEdit = false;
        int indexOne = 0;
        int indexTwo = 0;

        while (indexOne < lengthOne && indexTwo < lengthTwo) {
            if (stringOne.charAt(indexOne) != stringTwo.charAt(indexTwo)) {
                if (madeEdit) {
                    return false;
                }
                madeEdit = true;

                if (lengthOne > lengthTwo) {
                    indexOne++;
                } else if (lengthTwo > lengthOne) {
                    indexTwo++;
                } else {
                    indexOne++;
                    indexTwo++;
                }
            } else {
                indexOne++;
                indexTwo++;
            }
        }

        return true;
    }

    // O(n) time | O(n) space
    static String zigzagAWord(Integer nlines, String word) {
        if (nlines == 1) return word;
        int r = nlines, c = word.length();
        StringBuilder[] mat = new StringBuilder[r];
        // simulate the zig zag
        // go down from row 0 to row r-1
        // go up from row r-2 to 0
        int i = 0;
        boolean goingDown = true;
        for (int k = 0; k < word.length(); k++) {
            if (mat[i] == null) {
                mat[i] = new StringBuilder();
            }
            mat[i].append(word.charAt(k));
            if (goingDown) {
                // col is constant ie j
                i++;
            } else {
                // both row and col change
                // going to right upper diagonal cell
                i--;
            }
            if (i == 0) {
                goingDown = true;
            }
            if (i == nlines - 1) {
                goingDown = false;
            }
        }
        StringBuilder answer = new StringBuilder();
        for (StringBuilder row : mat) {
            if (row != null) {
                answer.append(row);
            }
        }

        return answer.toString();
    }


    // O(n) time | O(min(n, a)) space
    static String longestSubstringWithoutDuplication(String str) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int[] longest = {0, 1};
        int startIdx = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (lastSeen.containsKey(c)) {
                startIdx = Math.max(startIdx, lastSeen.get(c) + 1);
            }
            if (longest[1] - longest[0] < i + 1 - startIdx) {
                longest = new int[]{startIdx, i + 1};
            }
            lastSeen.put(c, i);
        }
        String result = str.substring(longest[0], longest[1]);

        return result;
    }


    // O(n) time | O(1) space
    static String shortestSubstringControlledBySet(String str, Set<Character> set) {
        if (str == null || str.isEmpty() || set.isEmpty()) {
            return "";
        }

        Map<Character, Integer> required = new HashMap<>();
        for (char ch : set) {
            required.put(ch, required.getOrDefault(ch, 0) + 1);
        }

        int left = 0, minLen = Integer.MAX_VALUE;
        int requiredCount = set.size();
        Map<Character, Integer> windowCounts = new HashMap<>();

        int start = 0, end = 0;

        while (end < str.length()) {
            char endChar = str.charAt(end);
            windowCounts.put(endChar, windowCounts.getOrDefault(endChar, 0) + 1);

            if (required.containsKey(endChar) && windowCounts.get(endChar).equals(required.get(endChar))) {
                requiredCount--;
            }

            while (requiredCount == 0) {
                if (end - start + 1 < minLen) {
                    minLen = end - start + 1;
                    left = start;
                }

                char startChar = str.charAt(start);
                windowCounts.put(startChar, windowCounts.get(startChar) - 1);

                if (required.containsKey(startChar) && windowCounts.get(startChar) < required.get(startChar)) {
                    requiredCount++;
                }

                start++;
            }

            end++;
        }

        return minLen == Integer.MAX_VALUE ? "" : str.substring(left, left + minLen);
    }


    // O(n) time | O(n) space
    static String findSmallestNumberByRemovingKDigits(String num, int k) {
        LinkedList<Character> stack = new LinkedList<>();

        for (char digit : num.toCharArray()) {
            while (!stack.isEmpty() && k > 0 && stack.peekLast() > digit) {
                stack.removeLast();
                k -= 1;
            }
            stack.addLast(digit);
        }

        /* remove the remaining digits from the tail. */
        for (int i = 0; i < k; ++i) {
            stack.removeLast();
        }

        // build the final string, while removing the leading zeros.
        StringBuilder ret = new StringBuilder();
        boolean leadingZero = true;
        for (char digit : stack) {
            if (leadingZero && digit == '0') continue;
            leadingZero = false;
            ret.append(digit);
        }

        /* return the final string  */
        if (ret.length() == 0) return "0";
        return ret.toString();
    }


    // O(n) time | O(1) space - where n is the length of the input string
    public int longestBalancedSubstring(String string) {

        return Math.max(
                getLongestBalancedInDirection(string, true),
                getLongestBalancedInDirection(string, false));
    }
    static int getLongestBalancedInDirection(String string, Boolean leftToRight) {
        char openingParens = leftToRight ? '(' : ')';
        int startIdx = leftToRight ? 0 : string.length() - 1;
        int step = leftToRight ? 1 : -1;

        int maxLength = 0;

        int openingCount = 0;
        int closingCount = 0;

        int idx = startIdx;
        while (idx >= 0 && idx < string.length()) {
            char c = string.charAt(idx);

            if (c == openingParens) {
                openingCount++;
            } else {
                closingCount++;
            }

            if (openingCount == closingCount) {
                maxLength = Math.max(maxLength, closingCount * 2);
            } else if (closingCount > openingCount) {
                openingCount = 0;
                closingCount = 0;
            }

            idx += step;
        }
        return maxLength;
    }



    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    }

    static class SuffixTrie {
        TrieNode root = new TrieNode();
        char endSymbol = '*';

        public SuffixTrie(String str) {
            populateSuffixTrieFrom(str);
        }

        // O(n^2) time | O(n^2) space
        public void populateSuffixTrieFrom(String str) {
            for (int i = 0; i < str.length(); i++) {
                insertSubStringStartingAt(i, str);
            }
        }

        void insertSubStringStartingAt(int i, String str) {
            TrieNode node = root;
            for (int j = i; j < str.length(); j++) {
                char letter = str.charAt(j);
                if (!node.children.containsKey(letter)) {
                    TrieNode newNode = new TrieNode();
                    node.children.put(letter, newNode);
                }
                node = node.children.get(letter);
            }
            node.children.put(endSymbol, null);
        }

        // O(m) time | O(1) space
        public boolean contains(String str) {
            TrieNode node = root;
            for (int i = 0; i < str.length(); i++) {
                char letter = str.charAt(i);
                if (!node.children.containsKey(letter)) {
                    return false;
                }
                node = node.children.get(letter);
            }
            return node.children.containsKey(endSymbol);
        }
    }

    static String wordleGuess(String answer, String guess){
        if(answer.length() != guess.length()){
            return null; //throw an exception
        }

        int length = answer.length();

        answer = answer.toLowerCase();
        guess = guess.toLowerCase();

        char[] answerCharArr = answer.toCharArray();
        char[] guessCharArr = guess.toCharArray();

        HashSet<Character> answerSet = new HashSet<>();
        for (int i = 0; i < length; i++){
            answerSet.add(answer.charAt(i));
        }

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < length; i++){
            output.append(rules(answerCharArr[i], guessCharArr[i], answerSet));
        }

        return output.toString();
    }

    static char rules(char a, char g, HashSet<Character> answerSet){
        if(a == g) {
            answerSet.remove(g);
            return '1';
        } else if(answerSet.contains(g)) {
            answerSet.remove(g);
            return '0';
        } else {
            return '.';
        }
    }



// -------------------------------------- //
    public static void main(String[] args) {
        System.out.println(runLengthEncoding("A"));

        String[] input = new String[] {"abc", "bcd", "cbad"};
        System.out.println(Arrays.toString(commonCharacters(input)));

        String characters = "helloworldO ";
        String document = "hello wOrld";
        System.out.println(generateDocument(characters, document));

        String str = "helloworld";
        Set<Character> set = new HashSet<>(Arrays.asList('l', 'r', 'w'));
        System.out.println(shortestSubstringControlledBySet(str, set));


        System.out.println(new SuffixTrie("babc").contains("abc"));

        System.out.println(wordleGuess("apple", "table"));
        System.out.println(wordleGuess("apple", "APPLE"));
        System.out.println(wordleGuess("apple", "cloth"));
        System.out.println(wordleGuess("aapplep", "ppppppe"));
    }


}