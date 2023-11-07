package com.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class TopDownDFSProblems {
    /**
     * 15 problems
     * General DFS templates
     * Print all paths of a tree
     * Path Sum (LC#112)
     * Path Sum II (LC#113)
     * Binary Tree Longest Consecutive Sequence (LC#298)
     * Binary Tree Longest Consecutive Sequence II (LC#549)
     * Path Sum III (LC#437)
     * Invert Binary Tree
     * Maximum Depth of N-ary tree (LC#559)
     * Height of K-ary Tree. Both Top-down and Bottom-Up
     * Vertical Order Traversal Of A Binary Tree (LC# 314,987)
     * Binary Tree Upside Down (LC#156)
     * Merge two Binary trees
     * Sum of root to leaf binary numbers (LC# 129, 1022)
     **/

    static ArrayList<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        ArrayList<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    static void preorderHelper(TreeNode currentNode, ArrayList<Integer> result) {
        // Base case not necessary, as they are not treated any different

        // Recursive case: internal worker node

        // Arrival Zone
        result.add(currentNode.val);

        if (currentNode.left != null) {
            preorderHelper(currentNode.left, result);
        }

        // Interim zone

        if (currentNode.right != null) {
            preorderHelper(currentNode.right, result);
        }

        // Departure zone
    }

    static ArrayList<Integer> inorder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        ArrayList<Integer> result = new ArrayList<>();

        inorderHelper(root, result);
        return result;
    }

    static void inorderHelper(TreeNode currentNode, ArrayList<Integer> result) {
        // Base case not necessary, as they are not treated any different

        // Recursive case: internal worker node

        // Arrival Zone

        if (currentNode.left != null) {
            inorderHelper(currentNode.left, result);
        }

        // Interim zone
        result.add(currentNode.val);

        if (currentNode.right != null) {
            inorderHelper(currentNode.right, result);
        }

        // Departure zone
    }

    static ArrayList<Integer> postOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        ArrayList<Integer> result = new ArrayList<>();

        postOrderHelper(root, result);
        return result;
    }

    static void postOrderHelper(TreeNode currentNode, ArrayList<Integer> result) {
        // Base case not necessary, as they are not treated any different

        // Recursive case: internal worker node

        // Arrival Zone

        if (currentNode.left != null) {
            postOrderHelper(currentNode.left, result);
        }

        // Interim zone

        if (currentNode.right != null) {
            postOrderHelper(currentNode.right, result);
        }

        // Departure zone
        result.add(currentNode.val);
    }

    // TC = O(n^2)
    // Top Down, Decrease and conquer DFS
    static List<List<Integer>> allPathsOfABinaryTreeV1(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        allPathsOfABinaryTreeHelper(root, path, result);
        return result;
    }

    // (sub-problem definition, partial solution, result)
    static void allPathsOfABinaryTreeHelper(TreeNode node, List<Integer> path, List<List<Integer>> result) {
        // Common actions for leaf and non-leaf nodes
        path.add(node.val);

        // Base case: Leaf node
        if (node.left == null && node.right == null) {
            result.add(new ArrayList<>(path));
        }

        // Recursive case: Internal node
        if (node.left != null) {
            allPathsOfABinaryTreeHelper(node, path, result);
        }
        if (node.right != null) {
            allPathsOfABinaryTreeHelper(node, path, result);
        }

        // Backtracking.
        path.remove(path.size() - 1);
    }

    /**
     * Time complexity : O(N) since we visit each node once
     * Space : O(Height) O(N) in worst case and O(logN) in avg case
     */
    static Boolean rootToLeafPathSumEqualToKTopDownV2(TreeNode root, Integer targetSum) {
        if (root == null) {
            return false;
        }

        Boolean[] hasPathSum = new Boolean[1];
        hasPathSum[0] = false;
        rootToLeafPathSumEqualToKHelperTD(root, targetSum, hasPathSum);
        return hasPathSum[0];
    }

    static void rootToLeafPathSumEqualToKHelperTD(TreeNode node, long targetSum, Boolean[] hasPathSumFlag) {
        // Backtracking, since this is a decision problem
        if (hasPathSumFlag[0]) {
            return;
        }

        // Common actions for leaf and non-leaf nodes
        targetSum -= node.val;

        // Base case: Leaf node
        if (node.left == null && node.right == null) {
            if (targetSum == 0) {
                hasPathSumFlag[0] = true;
            }
        }

        // Recursive case: Internal node
        if (node.left != null) {
            rootToLeafPathSumEqualToKHelperTD(node.left, targetSum, hasPathSumFlag);
        }
        if (node.right != null) {
            rootToLeafPathSumEqualToKHelperTD(node.right, targetSum, hasPathSumFlag);
        }
    }

    static boolean rootToLeafPathSumEqualToKBottomUp(TreeNode root, long targetSum) {
        if (root == null) {
            return false;
        }
        return rootToLeafPathSumEqualToKHelperBU(root, targetSum);
    }

    static boolean rootToLeafPathSumEqualToKHelperBU(TreeNode node, long targetSum) {
        // Common actions for leaf and non-leaf nodes
        targetSum -= node.val;

        // Base case: Leaf node
        if (node.left == null && node.right == null) {
            return node.val == targetSum;
        }

        // Recursive case: Internal node
        boolean bLeft = false, bRight = false;

        if (node.left != null) {
            bLeft = rootToLeafPathSumEqualToKHelperBU(node.left, targetSum);
        }
        if (node.right != null) {
            bRight = rootToLeafPathSumEqualToKHelperBU(node.right, targetSum);
        }

        return bLeft || bRight;
    }

    static List<List<Integer>> rootToLeafPathSumEqualToKV2TopDown(TreeNode root, int targetSum) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> slate = new ArrayList<>();
        rootToLeafPathSumEqualToKV2HelperTD(root, targetSum, slate, result);
        return result;
    }

    static void rootToLeafPathSumEqualToKV2HelperTD(TreeNode node, int targetSum, List<Integer> slate, List<List<Integer>> result) {
        // Common actions for leaf and non-leaf nodes
        slate.add(node.val);

        // Base case: Leaf node
        if (node.left == null && node.right == null) {
            if (node.val == targetSum) {
                result.add(new ArrayList<>(slate));
            }
        }

        // Recursive case: Internal node
        if (node.left != null) {
            rootToLeafPathSumEqualToKV2HelperTD(node.left, targetSum - node.val, slate, result);
        }
        if (node.right != null) {
            rootToLeafPathSumEqualToKV2HelperTD(node.right, targetSum - node.val, slate, result);
        }

        slate.remove(slate.size() - 1);
    }

    // Top-down DFS visualization, Similar to finding longest consecutive sequence in an array,
    // we need a globalMax and a running length for each consecutive sequence that we are extending
    static Long longestConsecutiveSequenceLength(TreeNode root) {
        if (root == null) {
            return 0L;
        }
        long[] globalMax = {0};
        // Similar to finding longest consecutive sequence in an array,
        // we need a globalmax and a running length for each consecutive sequence that we are extending
        longestConsecutiveSequenceLengthHelper(root, root.val, 0, globalMax);
        return globalMax[0];
    }

    static void longestConsecutiveSequenceLengthHelper(TreeNode node, long parentValue, long longestSoFar, long[] globalMax) {
        // Common actions for leaf and non-leaf nodes
        if (node.val == parentValue + 1) { // extend sequence
            longestSoFar += 1;
        } else {// reset sequence
            longestSoFar = 1;
        }
        if (longestSoFar > globalMax[0]) {
            globalMax[0] = longestSoFar;
        }

        // Base case: Leaf node
        if (node.left == null && node.right == null) {
            // do nothing
        }

        // Recursive case: Internal node
        if (node.left != null) {
            longestConsecutiveSequenceLengthHelper(node.left, node.val, longestSoFar, globalMax);
        }
        if (node.right != null) {
            longestConsecutiveSequenceLengthHelper(node.right, node.val, longestSoFar, globalMax);
        }
    }

    static int longestConsecutiveSequenceV2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int[] maxLen = {0};
        longestConsecutiveSequenceV2Helper(root, maxLen);

        return maxLen[0];
    }

    static int[] longestConsecutiveSequenceV2Helper(TreeNode node, int[] maxLen) {
        int increasingLen = 1;
        int decreasingLen = 1;

        if (node.left != null) {
            int[] leftPath = longestConsecutiveSequenceV2Helper(node.left, maxLen);
            if (node.val == node.left.val + 1) {
                increasingLen = Math.max(increasingLen, 1 + leftPath[0]);
            } else if (node.val == node.left.val - 1) {
                decreasingLen = Math.max(decreasingLen, 1 + leftPath[1]);
            }
        }

        if (node.right != null) {
            int[] rightPath = longestConsecutiveSequenceV2Helper(node.right, maxLen);
            if (node.val == node.right.val + 1) {
                increasingLen = Math.max(increasingLen, 1 + rightPath[0]);
            } else if (node.val == node.right.val - 1) {
                decreasingLen = Math.max(decreasingLen, 1 + rightPath[1]);
            }
        }

        maxLen[0] = Math.max(maxLen[0], increasingLen + decreasingLen - 1);

        return new int[]{increasingLen, decreasingLen};
    }

    // TC = O(n^2)
    // When we are at array[i], look at all the subArrays ending at array[i],
    // and determine how many additional subArrays was found
    static Integer pathSumOfABinaryTreeV3(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        int[] globalCount = {0};
        List<Integer> slate = new ArrayList<>();

        pathSumOfABinaryTreeHelperV3(root, sum, slate, globalCount);
        return globalCount[0];
    }

    static void pathSumOfABinaryTreeHelperV3(TreeNode node, int target, List<Integer> slate, int[] globalCount) {
        // Common actions for leaf and non-leaf nodes
        slate.add(node.val);
        // Check every suffix sum to see how many add up to the target sum
        long suffixSum = 0; // make sure this is long and not int; to avoid integer overflow
        for (int i = slate.size() - 1; i >= 0; i--) {
            suffixSum += slate.get(i);
            if (suffixSum == target) {
                globalCount[0] += 1;
            }
        }
        // Base case: Leaf node
        if (node.left == null && node.right == null) {
            // do nothing
        }

        // Recursive case: Internal node
        if (node.left != null) {
            pathSumOfABinaryTreeHelperV3(node.left, target, slate, globalCount);
        }
        if (node.right != null) {
            pathSumOfABinaryTreeHelperV3(node.right, target, slate, globalCount);
        }

        slate.remove(slate.size() - 1);
    }

    static TreeNode invertBinaryTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        invertBinaryTreeHelper(root);
        return root;
    }

    static void invertBinaryTreeHelper(TreeNode node) {
        // Base Case: Leaf Nodes
        if (node.left == null && node.right == null) {
            // do nothing
        }

        // Recursive case: Internal worker nodes
        TreeNode oldLeft = node.left;
        TreeNode oldRight = node.right;
        node.left = oldRight;
        node.right = oldLeft;
        if (node.left != null) {
            invertBinaryTreeHelper(node.left);
        }
        if (node.right != null) {
            invertBinaryTreeHelper(node.right);
        }
    }

    static int maxDepthOfNAryTree(Node root) {
        if (root == null) {
            return 0;
        }

        int[] globalHeight = {0}; // Initialize globalHeight to store the maximum depth.

        maxDepthOfNAryTreeHelper(root, 0, globalHeight);
        return globalHeight[0];
    }
    static void maxDepthOfNAryTreeHelper(Node node, int nodesUptoParent, int[] globalHeight) {
        // Common for leaf and non-leaf nodes
        int nodesUptoMe = 1 + nodesUptoParent;

        if (nodesUptoMe > globalHeight[0]) {
            globalHeight[0] = nodesUptoMe;
        }

        // Base case: Leaf node
        if (node.children.isEmpty()) {
            // do nothing
        }

        // Recursive case: Internal node
        for (Node child : node.children) {
            maxDepthOfNAryTreeHelper(child, nodesUptoMe, globalHeight);
        }
    }

    static int heightOfKAryTree(Node root) {
        if (root == null) {
            return 0;
        }

        int[] globalHeight = {0}; // Initialize globalHeight to store the maximum depth.

        // When the height is defined in terms of #edges instead of #nodes,
        // this is the only difference in the code.
        heightOfKAryTreeHelper(root, -1, globalHeight); // -1 because the root should be at depth 0
        return globalHeight[0];
    }
    static void heightOfKAryTreeHelper(Node node, int nodesUptoParent, int[] globalHeight) {
        // Common for leaf and non-leaf nodes
        int nodesUptoMe = 1 + nodesUptoParent;

        if (nodesUptoMe > globalHeight[0]) {
            globalHeight[0] = nodesUptoMe;
        }

        // Base case: Leaf node
        if (node.children.isEmpty()) {
            // do nothing
        }

        // Recursive case: Internal node
        for (Node child : node.children) {
            heightOfKAryTreeHelper(child, nodesUptoMe, globalHeight);
        }
    }


    public int heightOfKAryTreeBU(Node root) {
        if (root == null) {
            return 0;
        }

        return heightOfKAryTreeBUHelper(root);
    }
    private int heightOfKAryTreeBUHelper(Node node) {
        // Base case: Leaf node
        if (node.children.isEmpty()) {
            return 0;
        }

        // Recursive case: Internal node
        int nodeHeight = 0;
        for (Node child : node.children) {
            // Every time I learn the height of another child subtree, I update my height
            nodeHeight = Math.max(nodeHeight, 1 + heightOfKAryTreeBUHelper(child));
        }

        return nodeHeight;
    }

    static List<List<Integer>> verticalOrderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        TreeMap<Integer, TreeMap<Integer, List<Integer>>> result = new TreeMap<>();
        verticalOrderTraversalHelper(root, 0, 0, result);

        List<List<Integer>> output = new ArrayList<>();
        for (int x : result.keySet()) {
            List<Integer> temp = new ArrayList<>();
            for (int y : result.get(x).keySet()) {
                List<Integer> vals = result.get(x).get(y);
                Collections.sort(vals);// two variants of this problem differ by this line
                temp.addAll(vals);
            }
            output.add(temp);
        }

        return output;
    }
    static void verticalOrderTraversalHelper(TreeNode node, int x, int y,
                                             TreeMap<Integer, TreeMap<Integer, List<Integer>>> result) {
        result.putIfAbsent(x, new TreeMap<>());

        if (!result.get(x).containsKey(y)) {
            result.get(x).put(y, new ArrayList<>());
        }

        result.get(x).get(y).add(node.val);

        if (node.left != null) {
            verticalOrderTraversalHelper(node.left, x - 1, y + 1, result);
        }
        if (node.right != null) {
            verticalOrderTraversalHelper(node.right, x + 1, y + 1, result);
        }
    }

    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode[] globalRoot = {null};
        upsideDownBinaryTreeHelper(root, null, null, globalRoot);
        return globalRoot[0];
    }
    private void upsideDownBinaryTreeHelper(TreeNode node, TreeNode parent, TreeNode rightSibling, TreeNode[] globalRoot) {
        TreeNode oldLeft = node.left;
        TreeNode oldRight = node.right;
        node.right = parent;
        node.left = rightSibling;

        // Base case: Leaf node
        if (oldLeft == null && oldRight == null) {
            globalRoot[0] = node;
        }

        // Recursive case: Internal node
        if (oldLeft != null) {
            upsideDownBinaryTreeHelper(oldLeft, node, oldRight, globalRoot);
        }
    }

    static TreeNode mergeTwoBinaryTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }

        // Top-down DFS merge
        mergeTwoBinaryTreesHelper(t1, t2);

        return t1;
    }
    static void mergeTwoBinaryTreesHelper(TreeNode node1, TreeNode node2) {
        // Both nodes must be non-null

        // First node is the base, second node will merge into it
        node1.val += node2.val;

        if (node1.left != null) {
            if (node2.left != null) {
                mergeTwoBinaryTreesHelper(node1.left, node2.left);
            }
            // Nothing to do if the second node is null

        } else {
            // If the first node is null, then connect the second node into the base tree
            node1.left = node2.left;
        }

        // Similar to left child merge
        if (node1.right != null) {
            if (node2.right != null) {
                mergeTwoBinaryTreesHelper(node1.right, node2.right);
            }
            // Nothing to do if the second node is null

        } else {
            // If the first node is null, set its right child to the right child of the second node
            node1.right = node2.right;
        }
    }

    static Integer sumRootToLeafNumbers(TreeNode root) { // LC 1022
        if (root == null) {
            return 0;
        }
        int[] globalSum = {0};
        sumRootToLeafNumbersHelper(root, 0, globalSum);
        return globalSum[0];
    }
    static void sumRootToLeafNumbersHelper(TreeNode node, int current, int[] globalSum) {
        int localSum = current << 1;
        if (node.val == 1) {
            localSum++;
        }
        if (node.left == null && node.right == null) {
            globalSum[0] += localSum;
        }
        if (node.left != null) {
            sumRootToLeafNumbersHelper(node.left, localSum, globalSum);
        }
        if (node.right != null) {
            sumRootToLeafNumbersHelper(node.right, localSum, globalSum);
        }
    }

    static Integer sumRootToLeafNumbersV2(TreeNode root) { // LC 129
        if (root == null) {
            return 0;
        }
        int[] globalSum = {0};
        sumRootToLeafNumbersV2Helper(root, 0, globalSum);
        return globalSum[0];
    }
    static void sumRootToLeafNumbersV2Helper(TreeNode node, int num, int[] globalSum) {
        num = num * 10 + node.val;
        if (node.left == null && node.right == null) {
            globalSum[0] += num;
        }
        if (node.left != null) {
            sumRootToLeafNumbersV2Helper(node.left, num, globalSum);
        }
        if (node.right != null) {
            sumRootToLeafNumbersV2Helper(node.right, num, globalSum);
        }
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

    class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }

    public static void main(String[] args) {

        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.right.left = new TreeNode(6);
        root2.right.right = new TreeNode(7);
        root2.left.left = new TreeNode(4);
        root2.left.right = new TreeNode(5);

        int longestPath = longestConsecutiveSequenceV2(root2);
        System.out.println("Longest Consecutive Path Length: " + longestPath);

        TreeNode root4 = new TreeNode(1000000000);
        root4.left = new TreeNode(1000000000);
        root4.left.left = new TreeNode(294967296);
        root4.left.left.left = new TreeNode(1000000000);
        root4.left.left.left.left = new TreeNode(1000000000);
        root4.left.left.left.left.left = new TreeNode(1000000000);
        System.out.println("pathSumOfABinaryTreeV3="+ pathSumOfABinaryTreeV3(root4, 0));

        int num = 2000000000;
        System.out.println(num + 2000000000);

        TreeNode root5 = new TreeNode(4);
        root5.left = new TreeNode(2);
        root5.left.left = new TreeNode(1);
        root5.left.right = new TreeNode(3);
        root5.right = new TreeNode(7);
        root5.right.left = new TreeNode(6);
        root5.right.right = new TreeNode(9);
        //System.out.println(invertBinaryTree(root5));


    }
}
