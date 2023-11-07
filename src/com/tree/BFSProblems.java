package com.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BFSProblems {

    /** 22 problems
     * Binary Tree Level Order Traversal (LC#102)
     * Binary Tree Level Order Traversal Bottom (LC#107)
     * N-ary Tree Level Order Traversal (LC#429)
     * Binary Tree ZigZag Level Order Traversal (LC#103)
     * Average of Levels in Binary Tree (LC#637)
     * Find Largest Value in Each Tree Row (LC#515)
     * Binary Tree Right Side View (LC#199)
     * Minimum Depth of Binary Tree (LC#111)
     * Maximum Depth of Binary Tree (LC#104)
     * Maximum Depth of N-ary Tree (LC#559)
     * Maximum Level Sum of a Binary Tree (LC#1161)
     * Univalued Binary Tree - in BT, every node in the tree has the same value (LC#965)
     * Cousins in Binary Tree (LC#993)
     * Populating Next Right Pointers in Each Node (LC#116)
     * Add One Row To Three (LC#623)
     * Maximum Width of Binary Tree (LC#662)
     * Check Completeness of a Binary Tree (LC#958)
     * Same Tree (LC#100)
     * Symmetric Tree (LC#101)
     * Clone a Binary Tree
     * Find bottom left tree value (LC#513). Can also be done using top-down DFS
     * Bottom view of a tree
     */

    /**
     * Asymptotic complexity in terms of total number of nodes in the given tree `n`:
     * Time: O(n).
     * Auxiliary space: O(n).
     * Total space: O(n).
     */
    public static List<List<Integer>> binaryTreeLevelOrderTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // The queue currently has all the nodes of a single level.
            ArrayList<Integer> levelNodes = new ArrayList<>();
            int currentLevelNodeCount = queue.size(); // find out how many nodes are in the current level

            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; ++i) {
                TreeNode current = queue.poll();
                levelNodes.add(current.val);

                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
            result.add(levelNodes);
        }

        return result;
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            // The queue currently has all the nodes of a single level.
            ArrayList<Integer> level = new ArrayList<>();
            int currentLevelNodeCount = q.size(); // find out how many nodes are in the current level

            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; ++i) {
                TreeNode current = q.poll();
                level.add(current.val);

                if (current.left != null) {
                    q.offer(current.left);
                }
                if (current.right != null) {
                    q.offer(current.right);
                }
            }

            result.add(level);
            // result.add(0, level); // new list is always added to the beginning of the list
        }
        Collections.reverse(result);
        return result;
    }

    public static List<List<Integer>> nAryLevelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int level = queue.size();
            List<Integer> levelNodes = new ArrayList<>();
            for (int i = 0; i < level; i++) {
                Node node = queue.poll();
                levelNodes.add(node.val);
                for (Node child : node.children) {
                    if (child != null) {
                        queue.offer(child);
                    }
                }
            }
            result.add(levelNodes);
        }

        return result;
    }

    static List<List<Integer>> zigzagLevelOrderTraversal1(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        //int level = 1;
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            ArrayList<Integer> levelNodes = new ArrayList<>();

            // The queue currently has all the nodes from only a single level.
            int currentLevelNodeCount = queue.size();

            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; i++) {
                TreeNode currentNode = queue.poll();
                levelNodes.add(currentNode.val);
                if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.add(currentNode.right);
                }
            }

            // Reversing the alternate levels.
            /*if (level % 2 == 0) {
                Collections.reverse(levelNodes);
            }*/
            if (!leftToRight) {
                Collections.reverse(levelNodes);
            }
            leftToRight = !leftToRight;
            result.add(levelNodes);
        }

        return result;
    }

    public static List<Integer> findLargestValue(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // The queue currently has all the nodes of a single level.
            Integer largest = Integer.MIN_VALUE;
            int currentLevelNodeCount = queue.size(); // find out how many nodes are in the current level

            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; ++i) {
                TreeNode current = queue.poll();
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
                largest = Integer.max(current.val, largest);
            }
            result.add(largest);
        }

        return result;
    }

    public static List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // The queue currently has all the nodes of a single level.
            Double levelTotal = 0.0;
            int currentLevelNodeCount = queue.size(); // find out how many nodes are in the current level

            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; i++) {
                TreeNode current = queue.poll();
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
                levelTotal += current.val;
            }

            result.add(levelTotal / currentLevelNodeCount);
        }

        return result;
    }

    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // The queue currently has all the nodes of a single level.
            Integer rightMost = null;
            int currentLevelNodeCount = queue.size(); // find out how many nodes are in the current level

            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; ++i) {
                TreeNode current = queue.poll();
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
                rightMost = current.val;
            }
            result.add(rightMost);
        }

        return result;
    }

    public static Integer minimumDepthOfBT(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        Integer level = 0;
        while (!queue.isEmpty()) {
            // The queue currently has all the nodes of a single level.
            int currentLevelNodeCount = queue.size(); // find out how many nodes are in the current level
            level += 1;
            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; ++i) {
                TreeNode current = queue.poll();
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
                if (current.left == null && current.right == null) { //The very first leaf encountered in BFS
                    return level;
                }
            }
        }

        return 0;
    }

    public static Integer maximumDepthOfBT(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        Integer level = 0;
        while (!queue.isEmpty()) {
            // The queue currently has all the nodes of a single level.
            int currentLevelNodeCount = queue.size(); // find out how many nodes are in the current level
            level += 1;
            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; i++) {
                TreeNode current = queue.poll();
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
        }

        return level;
    }

    public static Integer maximumDepthOfNAryTree(Node root) {
        if (root == null) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        Integer level = 0;
        while (!queue.isEmpty()) {
            // The queue currently has all the nodes of a single level.
            int currentLevelNodeCount = queue.size(); // find out how many nodes are in the current level
            level += 1;
            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; i++) {
                Node current = queue.poll();
                for (Node child : current.children) {
                    if (child != null) {
                        queue.offer(child);
                    }
                }
            }
        }

        return level;
    }

    public static int maximumLevelSumOfBT(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int level = 0;
        int maxSum = Integer.MIN_VALUE;
        int minimumLevel = 0;
        while (!queue.isEmpty()) {
            // The queue currently has all the nodes of a single level.
            int numberOfNodes = queue.size(); // find out how many nodes are in the current level
            level += 1;
            int total = 0;
            // Processing the nodes from the current level.
            for (int i = 0; i < numberOfNodes; i++) {
                TreeNode current = queue.poll();
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
                total += current.val;
            }
            if (total > maxSum) {
                maxSum = total;
                minimumLevel = level;
            }
        }

        return minimumLevel;
    }

    public static boolean isUniValued(TreeNode root) {
        if (root == null) {
            return false;
        }

        int uniValue = root.val;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            // The queue currently has all the nodes from only a single level.
            int currentLevelNodeCount = q.size();

            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; i++) {
                TreeNode currentNode = q.poll();

                if (currentNode.left != null) {
                    q.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    q.add(currentNode.right);
                }
                if (currentNode.val != uniValue) {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean isCousins(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }

        Integer parentOfX = null, parentOfY = null;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            // The queue currently has all the nodes from only a single level.
            int currentLevelNodeCount = q.size();

            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; ++i) {
                TreeNode currentNode = q.poll();

                if (currentNode.left != null) {
                    q.add(currentNode.left);
                    if (currentNode.left.val == x) {
                        parentOfX = currentNode.val;
                    }
                    if (currentNode.left.val == y) {
                        parentOfY = currentNode.val;
                    }
                }
                if (currentNode.right != null) {
                    q.add(currentNode.right);
                    if (currentNode.right.val == x) {
                        parentOfX = currentNode.val;
                    }
                    if (currentNode.right.val == y) {
                        parentOfY = currentNode.val;
                    }
                }
            }

            if (parentOfX != null || parentOfY != null) {
                return (parentOfX != null && parentOfY != null) && !parentOfX.equals(parentOfY);
            }
        }

        return false;
    }

    // Alternative question, connect to successor in BT
    static TreeNode populatingNextRightPointersInEachNode(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            // The queue currently has all the nodes from only a single level.
            int currentLevelNodeCount = q.size();
            TreeNode previousNode = null;

            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; ++i) {
                TreeNode currentNode = q.poll();

                if (currentNode.left != null) {
                    q.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    q.add(currentNode.right);
                }
                if (previousNode != null) {
                    previousNode.next = currentNode;
                }
                previousNode = currentNode;
            }
        }

        return root;
    }

    static TreeNode connectAllNodesInBT(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        TreeNode previousNode = null;
        while (!q.isEmpty()) {
            TreeNode currentNode = q.poll();
            if (currentNode.left != null) {
                q.offer(currentNode.left);
            }
            if (currentNode.right != null) {
                q.offer(currentNode.right);
            }
            if (previousNode != null) {
                previousNode.next = currentNode;
            }
            previousNode = currentNode;
        }

        return root;
    }

    static TreeNode addOneRow(TreeNode root, int value, int depth) {
        if (depth == 1) { // Edge case
            TreeNode newNode = new TreeNode(value);
            newNode.left = root;
            root = newNode;
            return root;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int numNodes = queue.size();
            level += 1;
            for (int i = 0; i < numNodes; i++) {
                TreeNode currentNode = queue.poll();
                if (level < depth - 1) {
                    if (currentNode.left != null) {
                        queue.offer(currentNode.left);
                    }
                    if (currentNode.right != null) {
                        queue.offer(currentNode.right);
                    }
                } else if (level == depth - 1) {
                    TreeNode newLeft = new TreeNode(value);
                    TreeNode newRight = new TreeNode(value);
                    newLeft.left = currentNode.left;
                    newRight.right = currentNode.right;
                    currentNode.left = newLeft;
                    currentNode.right = newRight;
                }
            }
        }
        return root;
    }

    // the input tree is a complete binary tree
    static int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int maxWidth = 1;
        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(root, 0));

        while (!q.isEmpty()) {
            int currentLevelNodeCount = q.size();
            int curMin = Integer.MAX_VALUE;
            int curMax = Integer.MIN_VALUE;

            for (int i = 0; i < currentLevelNodeCount; i++) {
                Pair currentNode = q.poll();

                curMin = Math.min(curMin, currentNode.index);
                curMax = Math.max(curMax, currentNode.index);

                if (currentNode.node.left != null) {
                    q.add(new Pair(currentNode.node.left, currentNode.index * 2 + 1));
                }
                if (currentNode.node.right != null) {
                    q.add(new Pair(currentNode.node.right, currentNode.index * 2 + 2));
                }
            }
            maxWidth = Math.max(maxWidth, curMax - curMin + 1);
        }
        return maxWidth;
    }

    static boolean isCompleteBinaryTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        int expectedId = 0;
        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(root, 0));

        while (!q.isEmpty()) {
            int currentLevelNodeCount = q.size();

            for (int i = 0; i < currentLevelNodeCount; i++) {
                Pair currentNode = q.poll();
                if (currentNode.index == expectedId) {
                    expectedId += 1;
                } else {
                    return false;
                }

                if (currentNode.node.left != null) {
                    q.add(new Pair(currentNode.node.left, currentNode.index * 2 + 1));
                }
                if (currentNode.node.right != null) {
                    q.add(new Pair(currentNode.node.right, currentNode.index * 2 + 2));
                }
            }
        }
        return true;
    }

    static Boolean checkIfSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode nodeL = queue.poll();
            TreeNode nodeR = queue.poll();

            if (nodeL.left != null && nodeR.right != null) {
                queue.add(nodeL.left);
                queue.add(nodeR.right);
            } else if (nodeL.left != null || nodeR.right != null) {
                return false;
            }

            if (nodeL.right != null && nodeR.left != null) {
                queue.add(nodeL.right);
                queue.add(nodeR.left);
            } else if (nodeL.right != null || nodeR.left != null) {
                return false;
            }

            if (nodeL.val != nodeR.val) {
                return false;
            }
        }
        return true;
    }

    static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if ((p == null && q != null) || (p != null && q == null)) {
            return false;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(p);
        queue.add(q);

        while (!queue.isEmpty()) {
            TreeNode nodeP = queue.poll();
            TreeNode nodeQ = queue.poll();

            if (nodeP.left != null && nodeQ.left != null) {
                queue.add(nodeP.left);
                queue.add(nodeQ.left);
            } else if (nodeP.left != null || nodeQ.left != null) {
                return false;
            }

            if (nodeP.right != null && nodeQ.right != null) {
                queue.add(nodeP.right);
                queue.add(nodeQ.right);
            } else if (nodeP.right != null || nodeQ.right != null) {
                return false;
            }

            if (nodeP.val != nodeQ.val) {
                return false;
            }
        }

        return true;
    }

    static TreeNode cloneBinaryTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode root2 = new TreeNode(root.val);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(root2);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode node2 = queue.poll();

            if (node.left != null) {
                node2.left = new TreeNode(node.left.val);
                queue.add(node.left);
                queue.add(node2.left);
            }
            if (node.right != null) {
                node2.right = new TreeNode(node.right.val);
                queue.add(node.right);
                queue.add(node2.right);
            }
        }
        return root2;
    }

    public static ArrayList<ArrayList<Integer>> reverseLevelOrderTraversal(TreeNode root) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Stack<ArrayList<Integer>> resultStack = new Stack<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            ArrayList<Integer> currentLevelNodes = new ArrayList<>();

            // The queue currently has all the nodes from only a single level.
            int currentLevelNodeCount = q.size();

            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; ++i) {
                TreeNode currentNode = q.poll();

                currentLevelNodes.add(currentNode.val);

                if (currentNode.left != null) {
                    q.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    q.add(currentNode.right);
                }
            }

            resultStack.push(currentLevelNodes);
        }

        while (!resultStack.isEmpty()) {
            result.add(resultStack.pop());
        }
        //Collections.reverse(resultStack);
        return result;
    }

    static ArrayList<ArrayList<Integer>> zigzagLevelOrderTraversal2(TreeNode root) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Stack<TreeNode> currentLevel = new Stack<>(), nextLevel = new Stack<>();

        boolean leftToRight = true; // First level is traversed from left to right.
        currentLevel.push(root);

        while (!currentLevel.isEmpty()) {
            int currentLevelNodeCount = currentLevel.size();
            ArrayList<Integer> levelLabels = new ArrayList<>();

            // Traversing all the nodes of the current level.
            for (int i = 0; i < currentLevelNodeCount; ++i) {
                TreeNode node = currentLevel.pop();

                levelLabels.add(node.val);

                // If the current level is traversed from left to right,
                // the next level will be traversed from right to left.
                // But since a stack follows the last-in-first-out property,
                // we will push the left child before the right child
                // so that the right child gets popped out first from the next level.
                if (leftToRight) {
                    if (node.left != null) {
                        nextLevel.push(node.left);
                    }
                    if (node.right != null) {
                        nextLevel.push(node.right);
                    }
                }

                // If the current level is traversed from right to left,
                // the next level will be traversed from left to right.
                else {
                    if (node.right != null) {
                        nextLevel.push(node.right);
                    }
                    if (node.left != null) {
                        nextLevel.push(node.left);
                    }
                }
            }

            result.add(levelLabels);
            leftToRight = !leftToRight;

            // The current "nextLevel" will be the "currentLevel" of the next iteration.
            Stack<TreeNode> temp1 = new Stack<>(), temp2 = new Stack<>();

            // Swapping currentLevel and nextLevel
            while (!nextLevel.isEmpty()) {
                temp1.push(nextLevel.pop());
            }
            while (!currentLevel.isEmpty()) {
                temp2.push(currentLevel.pop());
            }
            while (!temp1.isEmpty()) {
                currentLevel.push(temp1.pop());
            }
            while (!temp2.isEmpty()) {
                nextLevel.push(temp2.pop());
            }
        }

        return result;
    }

    static Integer findBottomLeftValue(TreeNode root) {
        Integer bottomLeftValue = null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int currentLevelNodeCount = queue.size(); // find out how many nodes are in the current level
            Integer firstValue = null;
            // Processing the nodes from the current level.
            for (int i = 0; i < currentLevelNodeCount; i++) {
                TreeNode current = queue.poll();
                if (firstValue == null) {
                    firstValue = current.val;
                }

                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
            bottomLeftValue = firstValue;
        }
        return bottomLeftValue;
    }

    static int findBottomLeftValueTD(TreeNode root) {
        List<Integer> leftMost = new ArrayList<>();
        findBottomLeftValueTDHelper(root, 0, leftMost);
        return leftMost.remove(leftMost.size() - 1);
    }

    static void findBottomLeftValueTDHelper(TreeNode node, int parentDepth, List<Integer> leftMost) {
        int myDepth = parentDepth + 1;
        if (myDepth > leftMost.size()) {
            leftMost.add(node.val);
        }
        // Base Case: Leaf Node
        if (node.left == null && node.right == null) {
            // Do nothing
        }
        // Recursive case: Internal Node
        if (node.left != null) {
            findBottomLeftValueTDHelper(node.left, myDepth, leftMost);
        }
        if (node.right != null) {
            findBottomLeftValueTDHelper(node.right, myDepth, leftMost);
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

    static class Pair{
        TreeNode node;
        TreeNode node2;
        int index;

        public Pair(TreeNode node, int index) {
            this.node = node;
            this.index = index;
        }
        public Pair(TreeNode node1, TreeNode node2) {
            this.node = node1;
            this.node2 = node2;
        }
    }

    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    };

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        List<List<Integer>> result = binaryTreeLevelOrderTraversal(null);

        for (List<Integer> level : result) {
            System.out.println(level);
        }

        /*------------------------------------------------------*/
        // Creating a sample tree:
        //       1
        //      / \
        //     2   3
        //    /|   |\
        //   4 5   6 7
        TreeNode root3 = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        
        // Print the reverse level-order traversal result
        for (List<Integer> level : result) {
            System.out.println(level);
        }

        TreeNode treeNode = new TreeNode();
        treeNode.val = 1;
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);
        treeNode.left.left = new TreeNode(4);
        treeNode.left.right = new TreeNode(5);
        treeNode.right.left = new TreeNode(6);
        treeNode.right.right = new TreeNode(7);

        isCousins(treeNode, 4,3);
        connectAllNodesInBT(treeNode);
        isCompleteBinaryTree(treeNode);
        isSameTree(treeNode, treeNode);
        TreeNode clonedBinaryTree = cloneBinaryTree(treeNode);
        System.out.println("isSameTree-" + isSameTree(treeNode, clonedBinaryTree));

    }

}
