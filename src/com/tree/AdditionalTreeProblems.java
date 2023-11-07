package com.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class AdditionalTreeProblems {

    /** 15 problems
     *************** Examples of using both Top down & BottomUp approaches in a single problem **************
     * Convert Binary Search Tree to Sorted Doubly Linked List
     * Kth Smallest Element in a BST
     * Flatten Binary Tree to Linked List
     * Delete Nodes And Return Forest (LC#1110)
     *
     * *************** Iterative stack DFS ************
     * Binary Search Tree Iterator | LC # 173
     *
     * ***************  Boundary walk DFS ************
     *  Boundary of Binary Tree (LC#545)
     *
     * *************** Tree construction patterns ************
        * Top-down(preorder)
            1) Figure out & create root node
            2) Recursively construct the left subtree
            2) Recursively construct the right subtree
         * Left-to-right (inorder)
            1) Recursively construct the left subtree
            2) Figure out & create root node
            3) Recursively construct the right subtree
     * Convert Sorted Array to Binary Search Tree
     * Convert sorted List to Binary Search Tree (LC#109) - TC = O(n)
        * Left-to-right tree construction
     * Merge Two BSTs
     * Construct Binary Tree from Preorder and Inorder Traversal (LC#105)
     * Construct Binary Search Tree from Preorder Traversal (LC#1008)
     * Construct Binary Tree from Inorder and Postorder Traversal (LC#106)
     * Serialize and Deserialize BST
     * Serialize and Deserialize Binary Tree
     *
     * **************** MISC *********************
     * PostOrder Traversal Without Recursion
     * Convert A Binary Tree Into A Circular Doubly Linked List
     *  */

    static int findKthSmallest(TreeNode root, int k) {
        // Root is not null

        int[] kthSmallest = {0};
        findKthSmallestHelper(root, k, 0, kthSmallest);
        return kthSmallest[0];
    }
    static int findKthSmallestHelper(TreeNode node, int k, int numOfPredecessor, int[] kthSmallest) {
        // Backtracking case
        if (numOfPredecessor >= k) {
            return numOfPredecessor;
        }
        // Base case: leaf node
        if (node.left == null && node.right == null) {
            numOfPredecessor += 1;
            if (numOfPredecessor == k) {
                kthSmallest[0] = node.val;
            }
            return numOfPredecessor;
        }
        // Recursive case: Internal node
        if (node.left != null) {
            numOfPredecessor = findKthSmallestHelper(node.left, k, numOfPredecessor, kthSmallest);
        }

        numOfPredecessor += 1;
        if (numOfPredecessor == k) {
            kthSmallest[0] = node.val;
        }

        if (node.right != null) {
            numOfPredecessor = findKthSmallestHelper(node.right, k, numOfPredecessor, kthSmallest);
        }
        return numOfPredecessor;
    }

    static List<TreeNode> deleteNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> remaining = new ArrayList<>();
        Set<Integer> toDeleteSet = new HashSet<>();
        for (int i : to_delete) {
            toDeleteSet.add(i);
        }
        removeNodes(root, toDeleteSet, remaining);
        if (!toDeleteSet.contains(root.val)) {
            remaining.add(root);
        }
        return remaining;
    }
    static TreeNode removeNodes(TreeNode root, Set<Integer> toDeleteSet, List<TreeNode> remaining) {
        if (root == null) return null;
        root.left = removeNodes(root.left, toDeleteSet, remaining);
        root.right = removeNodes(root.right, toDeleteSet, remaining);
        if (toDeleteSet.contains(root.val)) {
            if (root.left != null) {
                remaining.add(root.left);
            }
            if (root.right != null) {
                remaining.add(root.right);
            }
            return null;
        }
        return root;
    }

    static class BSTIterator {
        private List<Integer> nums = new ArrayList<>();
        private Iterator<Integer> it;

        public BSTIterator(TreeNode root) {
            inorder(root);
            it = nums.iterator();
        }

        private void inorder(TreeNode root) {
            if (root == null)
                return;

            inorder(root.left);
            nums.add(root.val);
            inorder(root.right);
        }

        public int next() {
            return it.next();
        }

        public boolean hasNext() {
            return it.hasNext();
        }
    }

    List<Integer> boundaryOfBinaryTree(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();

        if (root == null) {
            return result;
        } else if (root.left == null && root.right == null) { // root is leaf
            return Arrays.asList(root.val);
        }

        result.add(root.val);

        // Collect the left boundary
        collectLeftBoundary(root.left, result);

        // Collect the leaves
        collectLeavesDFS(root, result);

        // Collect the right boundary in reverse order
        collectRightBoundary(root.right, result);

        return result;
    }
    void collectLeftBoundary(TreeNode node, List<Integer> result) {
        List<Integer> leftBoundary = new ArrayList<>();
        if (node.left != null) {
            TreeNode current = node.left;
            while (current != null) {
                leftBoundary.add(current.val);
                if (current.left != null) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            leftBoundary.remove(leftBoundary.size() - 1); // remove the leaf node: will collect all leaves together later
        }
        leftBoundary.forEach(e -> result.add(e));
    }
    static void collectRightBoundary(TreeNode node, List<Integer> result) {
        List<Integer> rightBoundary = new ArrayList<>();
        if (node.right != null) {
            TreeNode current = node.right;
            while (current != null) {
                rightBoundary.add(current.val);
                if (current.right != null) {
                    current = current.right;
                } else {
                    current = current.left;
                }
            }
            rightBoundary.remove(rightBoundary.size() - 1); // remove the leaf node: will collect all leaves together later
        }
        Collections.reverse(rightBoundary); // Remember we need to output the nodes in anti-clockwise direction
        rightBoundary.forEach(e -> result.add(e));
    }
    static void collectLeavesDFS(TreeNode node, List<Integer> result) {
        // Base Case: Leaf node
        if (node.left == null && node.right == null) {
            result.add(node.val);
        }

        // Recursive case: Internal node
        if (node.left != null) {
            collectLeavesDFS(node.left, result);
        }
        if (node.right != null) {
            collectLeavesDFS(node.right, result);
        }
    }

    static TreeNode sortedArrayToBst(ArrayList<Integer> nodeValues, int low, int high) {
        if (low > high) {
            return null;
        }
        if (low == high) {
            return new TreeNode(nodeValues.get(low));
        }

        int middle = low + (high - low) / 2;
        TreeNode root = new TreeNode(nodeValues.get(middle));
        root.left = sortedArrayToBst(nodeValues, low, middle - 1);
        root.right = sortedArrayToBst(nodeValues, middle + 1, high);

        return root;
    }

    static TreeNode sortedLinkedListToBst(LinkedListNode head) {
        ArrayList<Integer> node_values = convertLinkedListToArray(head);
        return sortedArrayToBst(node_values, 0, node_values.size() - 1);
    }
    /**
     * Asymptotic complexity in terms of the number of nodes in the given linked list `n`:
     * Time: O(n).
     * Auxiliary space: O(n).
     * Total space: O(n).
     */
    static ArrayList<Integer> convertLinkedListToArray(LinkedListNode head) {
        ArrayList<Integer> result = new ArrayList<>();

        while (head != null) {
            result.add(head.value);
            head = head.next;
        }
        return result;
    }

    /**
     * BottomUp DFS using Divide and Conquer strategy
     * here if the provided input is sorted then the TC = O(n) otherwise TC = O(nlogn)
     * SC: implicit = O(logn). Explicit = O(n)
     */
    static TreeNode convertSortedListToBinarySearchTree(ArrayList<Integer> preorder) {
        return buildBSTHelperFromSortedList(preorder, 0, preorder.size() - 1);
    }
    static TreeNode buildBSTHelperFromSortedList(ArrayList<Integer> preorder, int start, int end) {
        // Base case:
        if (start > end) { // sub problem size = 0
            return null;
        }
        // For sub problem size = 1, the below recursive case would handle it
        // Internal worker node
        int mid = start + (end - start) / 2;

        TreeNode node = new TreeNode(mid);

        TreeNode leftNode = buildBSTHelperFromSortedList(preorder, start, mid - 1);
        TreeNode rightNode = buildBSTHelperFromSortedList(preorder, mid + 1, end);

        node.left = leftNode;
        node.right = rightNode;

        return node;
    }

    /**
     * Asymptotic complexity in terms of length of `preorder` (or total number of nodes) `n`:
     * Time: O(n * log(n)).
     * Auxiliary space: O(n).
     * Total space: O(n).
     */
    static TreeNode constructBinarySearchTreeFromItsPreorder(ArrayList<Integer> preorder) {
        return buildBSTHelperFromPreorder(preorder, 0, preorder.size() - 1);
    }
    static TreeNode buildBSTHelperFromPreorder(ArrayList<Integer> input, int start, int end) {

        if (start > end) {
            return null;
        }
        if (start == end) {
            return new TreeNode(input.get(start));
        }
        TreeNode root = new TreeNode(input.get(start));
        int rightStart = findRightStartPoint(input, input.get(start), start + 1, end);
        if (rightStart == -1) {
            root.right = null;
            root.left = buildBSTHelperFromPreorder(input, start + 1, end);
            return root;
        }
        root.left = buildBSTHelperFromPreorder(input, start + 1, rightStart - 1);
        root.right = buildBSTHelperFromPreorder(input, rightStart, end);
        return root;
    }
    static int findRightStartPoint(ArrayList<Integer> input, int pivot, int start, int end) {
        if (start >= end) {
            if (input.get(start) > pivot) {
                return start;
            }
            return -1;
        }

        int mid = start + ((end - start) / 2);
        if (input.get(mid) > pivot) {
            return findRightStartPoint(input, pivot, start, mid);
        } else {
            return findRightStartPoint(input, pivot, mid + 1, end);
        }
    }

    static String serializeBT(TreeNode root) {
        if (root == null) {
            return "null";
        }

        // here the result could be stored as a string(delimiter ",") or list or queue
        StringBuilder result = new StringBuilder();
        // choosing the preorder serialization as the root is at the beginning
        serializeBTHelper(root, result);
        return result.toString();
    }
    static void serializeBTHelper(TreeNode currentNode, StringBuilder result) {
        // Base case not necessary, as the leaf nodes are not treated any different

        // Recursive case: internal worker node

        // Arrival Zone
        result.append(currentNode.val).append(",");

        if (currentNode.left != null) {
            serializeBTHelper(currentNode.left, result);
        } else {
            result.append("null").append(",");
        }

        // Interim zone

        if (currentNode.right != null) {
            serializeBTHelper(currentNode.right, result);
        } else {
            result.append("null").append(",");
        }

        // Departure zone
    }
    /**
     * This approach works for both the BT, BST and even if the tree has duplicates
     */
    static TreeNode deSerializeBT(String data) {
        Deque<String> deque = new ArrayDeque<>(Arrays.asList(data.split(",")));

        if (deque.isEmpty()) {
            return null;
        }
        return deSerializeBTHelper(deque);
    }
    static TreeNode deSerializeBTHelper(Deque<String> data) {
        // Base case
        if (data.isEmpty()) {
            return null;
        }

        // Recursive case: internal worker node

        // Arrival Zone
        String value = data.pollFirst();

        if (value.equalsIgnoreCase("null")) {
            return null;
        } else {
            // Top down tree construction:
            // create root
            // recursively construct left subtree
            // recursively construct right subtree
            TreeNode root = new TreeNode(Integer.valueOf(value));
            root.left = deSerializeBTHelper(data);
            root.right = deSerializeBTHelper(data);
            return root;
        }

    }


    //////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);



        // Creating a sample tree:
        //       1
        //      / \
        //     2   3
        //    /|   |\
        //   4 5   6 7
        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(2);
        root3.right = new TreeNode(3);
        root3.left.left = new TreeNode(4);
        root3.left.right = new TreeNode(5);


        TreeNode treeNode = new TreeNode();
        treeNode.val = 1;
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);
        treeNode.left.left = new TreeNode(4);
        treeNode.left.right = new TreeNode(5);
        treeNode.right.left = new TreeNode(6);
        treeNode.right.right = new TreeNode(7);
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode next;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

    }

    static class LinkedListNode {
        Integer value;
        LinkedListNode next;

        LinkedListNode(Integer value) {
            this.value = value;
            this.next = null;
        }
    }

}