package com.tree;

public class BottomUpDFSProblems {

    /** 9 problems
     * Diameter of Binary tree (LC#543)
     * Count Univalue Subtrees (LC#250)
     * Lowest Common Ancestor of a Binary Tree (LC#236)
     * Validate Binary Search Tree (LC#98)
        * Decision problem
     * Longest Univalue Path (LC#687)
        * Optimization problem
     * Balanced Binary Tree
        * Decision problem, we can short circuit once we found the answer
     * Largest BST Subtree (LC#333)
        * Optimization problem (Longest/Shortest, Max/Minimum)
        * For subtrees bottom up is the natural way to think
     * Binary Tree Maximum Path Sum (LC#124)
     * Binary Tree Tilt - TODO
     **/

    // DFS Bottom Up using Divide and conquer approach
    static int diameterOfABinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // Global problem: Find diameter of the whole tree
        // Local (per-node) problem: Find the longest inverted-V shaped path through the node
        // Local to global: Global solution is the max of all the local solutions
        // to get the local solution each node needs to know from its two subtrees what their heoghts are
        int[] treeDiameter = {0};
        diameterOfABinaryTreeHelper(root, treeDiameter);
        return treeDiameter[0];
    }
    static int diameterOfABinaryTreeHelper(TreeNode node, int[] treeDiameter) {// Returns the height of subtree rooted an node
        // Base case: leaf node
        if (node.left == null && node.right == null) {
            return 0;
        }

        // Recursive case: Internal node
        int leftSubTreeHeight = 0;
        int rightSubTreeHeight = 0;
        int myDiameter = 0;
        int myHeight = 0;
        if (node.left != null) {
            leftSubTreeHeight = diameterOfABinaryTreeHelper(node.left, treeDiameter);
            myHeight = 1 + leftSubTreeHeight;
            myDiameter = 1 + leftSubTreeHeight;
        }
        if (node.right != null) {
            rightSubTreeHeight = diameterOfABinaryTreeHelper(node.right, treeDiameter);
            myHeight = Integer.max(myHeight, 1 + rightSubTreeHeight);
            myDiameter += 1 + rightSubTreeHeight;
        }

        if (myDiameter > treeDiameter[0]) {
            treeDiameter[0] = myDiameter;
        }

        return myHeight;
    }

    static int countUnivalueSubTrees(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int[] globalCount = {0};
        countUnivalueSubTreesHelper(root, globalCount);
        return globalCount[0];
    }
    static boolean countUnivalueSubTreesHelper(TreeNode node, int[] globalCount) {
        boolean amIUniValue = true;
        // Base case: Leaf Node
        if (node.left == null && node.right == null) {
            // do nothing
        }

        // Recursive case: Internal node
        if (node.left != null) {
            boolean isLeftUnivalue = countUnivalueSubTreesHelper(node.left, globalCount);
            if (!isLeftUnivalue || node.val != node.left.val) {
                amIUniValue = false;
            }
        }
        if (node.right != null) {
            boolean isRightUnivalue = countUnivalueSubTreesHelper(node.right, globalCount);
            if (!isRightUnivalue || node.val != node.right.val) {
                amIUniValue = false;
            }
        }
        if (amIUniValue) {
            globalCount[0] += 1;
        }
        return amIUniValue;
    }

    static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode[] lca = {null};
        lowestCommonAncestorHelper(root, p, q, lca);
        return lca[0];
    }
    static boolean[] lowestCommonAncestorHelper(TreeNode node, TreeNode p, TreeNode q, TreeNode[] lca){
        boolean pFound = false, qFound = false;
        // Common to leaf node & non-leaf nodes
        if (node.val == p.val) {
            pFound = true;
        } else if (node.val == q.val) {
            qFound = true;
        }

        // Base case: Leaf node
        if (node.left == null || node.right == null) {
            // do nothing
        }

        // Recursive case: Internal node
        if (node.left != null) {
            boolean[] pqFound = lowestCommonAncestorHelper(node.left, p, q, lca);
            pFound = pFound || pqFound[0];
            qFound = qFound || pqFound[1];
        }
        if (node.right != null) {
            boolean[] pqFound = lowestCommonAncestorHelper(node.right, p, q, lca);
            pFound = pFound || pqFound[0];
            qFound = qFound || pqFound[1];
        }
        if (pFound && qFound && (lca[0] == null)) {
            lca[0] = node;
        }
        boolean[] pqFound = new boolean[2];
        pqFound[0] = pFound;
        pqFound[1] = qFound;
        return pqFound;
    }

    static TreeNode lowestCommonAncestorHelper2(TreeNode root, TreeNode a, TreeNode b) {
        if (root == null) return null;

        if (a == root || b == root) return root;
        TreeNode left = lowestCommonAncestorHelper2(root.left, a, b);
        TreeNode right = lowestCommonAncestorHelper2(root.right, a, b);

        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    static boolean isValidBST(TreeNode root) {
        // Global problem: Determine if the tree is BST
        // Local (per-node) problem: Determine if the subtree rooted at each node is a BST
        // Local - global: AL local problems must return True for Global solution to be True
        // So if any local solution is False, global will become False
        if (root == null) {
            return true;
        }

        boolean[] isBST = {true};
        isValidBSTHelper(root, isBST);
        return isBST[0];
    }
    static Tuple isValidBSTHelper(TreeNode node, boolean[] isBST) {
        // A node will determine if it is BST by looking at its left and right subtrees
        // The largest value in left subtree should be smaller than the root value.
        // The smallest value in right subtree should be larger than the root value.
        // Both subtrees should be BSTs
        // so each node should return (smallest, largest, amIBST) values in its subtree back to its parent.

        boolean amIBST = true;
        int smallest = node.val;
        int largest = node.val;

        // Base Case: Leaf node
        if (node.left == null && node.right == null) {
            // Do nothing
        }

        // Recursive Case: Internal node
        if (node.left != null) {
            Tuple leftResult = isValidBSTHelper(node.left, isBST);
            smallest = Math.min(smallest, leftResult.smallest);
            largest = Math.max(largest, leftResult.largest);

            if (!leftResult.isBST || leftResult.largest >= node.val) {
                amIBST = false;
            }
        }

        if (node.right != null) {
            Tuple rightResult = isValidBSTHelper(node.right, isBST);
            smallest = Math.min(smallest, rightResult.smallest);
            largest = Math.max(largest, rightResult.largest);

            if (!rightResult.isBST || rightResult.smallest <= node.val) {
                amIBST = false;
            }
        }

        if (!amIBST) {
            isBST[0] = false;
        }

        return new Tuple(smallest, largest, amIBST);
    }

    static int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // Global Problem: Find the longest univalued path in a tree.
        // Local (Per-Node) Problem: Determine the longest inverted V-shaped univalued path through each node.
        // Local to Global: The global solution will be the maximum of all local solutions.
        // To compute the local solution, each node should know the length of the longest univalued path from the root to some leaf or non-leaf node.
        // This length will be the return value from each node to its parent.
        int[] globalMax = {0};
        longestUnivaluePathHelper(root, globalMax);
        return globalMax[0];
    }

    static int longestUnivaluePathHelper(TreeNode node, int[] globalMax) {
        int myLongestVPath = 0;
        int myLongestPath = 0;

        // Base Case: Leaf node
        if (node.left == null && node.right == null) {
            // Do nothing
        }

        // Internal node
        if (node.left != null) {
            int leftResult = longestUnivaluePathHelper(node.left, globalMax);
            if (node.val == node.left.val) {
                myLongestPath = 1 + leftResult;
                myLongestVPath = 1 + leftResult;
            }
        }

        if (node.right != null) {
            int rightResult = longestUnivaluePathHelper(node.right, globalMax);
            if (node.val == node.right.val) {
                myLongestPath = Math.max(myLongestPath, 1 + rightResult);
                myLongestVPath += 1 + rightResult;
            }
        }

        if (myLongestVPath > globalMax[0]) {
            globalMax[0] = myLongestVPath;
        }

        return myLongestPath;
    }

    static boolean balancedBinaryTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        boolean[] globalBalance = {true};
        balancedBinaryTreeHelper(root, globalBalance);
        return globalBalance[0];
    }
    static int balancedBinaryTreeHelper(TreeNode node, boolean[] globalBalance) {
        int myHeight = 0;
        boolean amIBalanced = true;

        // Base Case: Leaf Node
        if (node.left == null && node.right == null) {
            // Do nothing
        }
        // Recursive case: Internal node
        int leftHeight = 0, rightHeight = 0;
        if (node.left != null) {
            leftHeight = 1 + balancedBinaryTreeHelper(node.left, globalBalance);
        }
        if (node.right != null) {
            rightHeight = 1 + balancedBinaryTreeHelper(node.right, globalBalance);
        }
        myHeight = Integer.max(leftHeight, rightHeight);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            amIBalanced = false;
            globalBalance[0] = false;
        }
        return myHeight;
    }

    static int largestBSTSubTree(TreeNode root){
        // Global problem: Find the largest BST subtree
        // Local (per-node) problem: Is it a BST? If yes, then how many nodes do I have?
        // Local > Global: Global solution is the max of all local solutions which are BSTs.
        // To figure out if it's a BST, we need to figure out its left and right subtrees and the number of nodes in its subtree.
        // So each node needs to return:
            // 1) its size
            // 2) largest element
        // 3) smallest element
        // 4) whether it is a BST

        if (root == null) {
            return 0;
        }
        int[] globalSize = {0};
        largestBSTSubTreeHelper(root, globalSize);

        return globalSize[0];
    }
    static Tuple largestBSTSubTreeHelper(TreeNode node, int[] globalSize){
        int mySize = 1;
        int mySmallest = node.val, myLargest= node.val;
        boolean amIBST = true;

        // Base case: Leaf Node
        if (node.left == null && node.right == null) {
            // do nothing
        }

        // Recursive case: Internal node
        if (node.left != null) {
            Tuple tuple = largestBSTSubTreeHelper(node.left, globalSize);
            mySize += tuple.mySize;
            mySmallest = Integer.min(mySmallest, tuple.smallest);
            myLargest = Integer.max(myLargest, tuple.largest);
            if (!amIBST || tuple.largest >= node.val) {
                amIBST = false;
            }
        }
        if (node.right != null) {
            Tuple tuple = largestBSTSubTreeHelper(node.right, globalSize);
            mySize += tuple.mySize;
            mySmallest = Integer.min(mySmallest, tuple.smallest);
            myLargest = Integer.max(myLargest, tuple.largest);
            if (!amIBST || node.val >= tuple.smallest) {
                amIBST = false;
            }
        }

        if (amIBST && mySize > globalSize[0]) {
            globalSize[0] = mySize;
        }

        return new Tuple(mySize, mySmallest, myLargest, amIBST);
    }

    public int maxPathSum(TreeNode root) {
        // Global problem: Find the maximum path sum in the tree
        // Local (per-node) problem: Find the maximum path sum of an inverted V path
        // Local -> Global: Global solution will be the max of all the local solutions
        // through every node
        int[] globalMax = {Integer.MIN_VALUE};
        maxPathSumHelper(root, globalMax);
        return globalMax[0];
    }

    public int maxPathSumHelper(TreeNode node, int[] globalMax) {
        // Returns the max path sum of a root-to-descendant path in the subtree rooted at node
        if (node == null) {
            return 0;
        }

        int myMaxPathSum = node.val;
        int myMaxVPathSum = node.val;

        // Base case: Leaf node
        if (node.left == null && node.right == null) {
            // do nothing
        }

        // Recursive case: Internal node
        if (node.left != null) {
            int leftMax = maxPathSumHelper(node.left, globalMax);
            myMaxPathSum = Math.max(myMaxPathSum, myMaxPathSum + leftMax);
            myMaxVPathSum = Math.max(myMaxVPathSum, Math.max(node.val + leftMax, myMaxVPathSum + leftMax));
        }

        if (node.right != null) {
            int rightMax = maxPathSumHelper(node.right, globalMax);
            myMaxPathSum = Math.max(myMaxPathSum, node.val + rightMax);
            myMaxVPathSum = Math.max(myMaxVPathSum, Math.max(node.val + rightMax, myMaxVPathSum + rightMax));
        }

        if (myMaxVPathSum > globalMax[0]) {
            globalMax[0] = myMaxVPathSum;
        }

        return myMaxPathSum;
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

    static class Tuple {
        int mySize;
        int smallest;
        int largest;
        boolean isBST;

        public Tuple(int smallest, int largest, boolean isBST) {
            this.smallest = smallest;
            this.largest = largest;
            this.isBST = isBST;
        }
        public Tuple(int mySize, int smallest, int largest, boolean isBST) {
            this.mySize = mySize;
            this.smallest = smallest;
            this.largest = largest;
            this.isBST = isBST;
        }
    }

    public static void main(String[] args) {

        TreeNode treeNode = new TreeNode();
        treeNode.val = 1;
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);
        treeNode.left.left = new TreeNode(4);
        treeNode.left.right = new TreeNode(5);
        treeNode.right.left = new TreeNode(6);
        treeNode.right.right = new TreeNode(7);
        System.out.println(lowestCommonAncestor(treeNode, treeNode.left.right,treeNode.right.right));
    }

}