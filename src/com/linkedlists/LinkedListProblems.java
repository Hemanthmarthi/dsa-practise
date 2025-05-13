package com.linkedlists;

import java.util.*;

public class LinkedListProblems {
    /**
      - FLOYD'S CYCLE DETECTION PROBLEMS:
      1  Middle of the Linked list (LC#876) - Singly LL
           Space-time tradeoff - use two pointers: fast, slow
      2 Linked list cycle (141) - Singly LL
      3 Happy number (202) - Number sequences
      4 Pseudo-Random number - Number sequences
      5 LL cycle 2 (142) - Singly LL
      6 Circular Array Loop (457) - Array sequences
      7 Find the duplicate number (287) - Array sequences

     - MANIPULATE LL: INSERTION AND DELETION PROBLEMS
     1. Design Linked List (707)
     2. Delete Node in a Linked List (237)
     3. Remove Linked List Elements (203)
     4. Remove Duplicates from Sorted List (83)
     5. Remove Duplicates from Sorted List II (82)
     6. Remove Nth Node from End of List (19)
     7. Delete N Nodes after M Nodes of a Linked List (1474)
     8. Insert Into a Sorted Circular Linked List (708)

     - LIST REARRANGEMENTS
     1. Merge Two Sorted Lists(21)
     2. Sort List(148)
        a. wrt to the hare and tortoise example; tortoise will always stop at the middle element. i.e is mid point
     3. Merge K Sorted Lists
     4. Insertion Sort List
     5. Partition List
     6. Rotate List

     - LIST REVERSAL
     1. Reverse Linked List (206)
     2. Reverse Linked List II (92)
     3. Palindrome Linked List (234)
     4. Reorder List (143)
     5. Reverse Nodes in k-Group (25)
      * */


    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public static ListNode middleNode(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // noticing the pattern for even number of length input
        // and as the problem wants to return the second middle element
        if (fast.next != null) {
            slow = slow.next;
        }

        return slow;
    }

    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public static boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) { // circular track, pointing to the same list node
                return true;
            }
        }

        // have reached the end of the list
        return false;
    }

    /**
     *  Time: O(log n).
     *  Auxiliary space: O(1).
     * */
    public static boolean isHappyNumber(int number) {

        int fast = number;
        int slow = number;

        while (true) {
            fast = getNext(getNext(fast));
            slow = getNext(slow);

            if (slow == fast) {
                return fast == 1;
            }
        }
    }
    private static int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }

    /**
     *  Time: O(m).
     *  Auxiliary space: O(1).
     * */
    public static int findCycleLength(ListNode head) {
        if (head == null) { // Empty list edge case
            return 0;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) { // circular track, pointing to the same list node
                ListNode third = slow;
                int cycleLength = 1;
                while (slow != third) {
                    third = third.next;
                    cycleLength++;
                }
                return cycleLength;
            }
        }
        return 0;
    }

    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public static ListNode firstNodeInACycle(ListNode head) {
        if (head == null) { // Empty list edge case
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) { // circular track, pointing to the same list node
                ListNode third = head;
                while (slow != third) {
                    slow = slow.next;
                    third = third.next;
                }
                return slow;
            }
        }

        return null;
    }

    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public static boolean circularArrayLoop(int[] nums) {
        int n = nums.length;
        for(int i = 0; i < n; i++) {
            if(nums[i] == 0) {
                continue;
            }

            int j = i, k = getIndex(i, nums);
            while(nums[k] * nums[i] > 0 && nums[getIndex(k, nums)] * nums[i] > 0) {
                if(j == k) {
                    if(j == getIndex(j, nums)) {
                        break;
                    }
                    return true;
                }
                j = getIndex(j, nums);
                k = getIndex(getIndex(k, nums), nums);
            }
            j = i;
            int val = nums[i];
            while(nums[j] * val > 0) {
                int next = getIndex(j, nums);
                nums[j] = 0;
                j = next;
            }
        }
        return false;
    }
    private static int getIndex(int i, int[] nums) {
        int n = nums.length;
        return (i + nums[i] % n + n) % n;
    }


    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public int findDuplicate1(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i) {
                int idx = nums[i];
                if (nums[idx] != nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[idx];
                    nums[idx] = temp;
                } else {
                    break;
                }
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                return nums[i];
            }
        }

        return -1;
    }

    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public static int findDuplicate2(int[] nums) {
        // Find the intersection point of the two runners. Cycle detection
        int tortoise = nums[0];
        int hare = nums[0];

        while (true) {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
            if (hare == tortoise) { // cycle detected
                // find the index where the cycle began
                int third = nums[0];
                while (third != tortoise) {
                    third = nums[third];
                    tortoise = nums[tortoise];
                }
                return third;
            }
        }
    }


    /**
     *  Time: O(1).
     *  Auxiliary space: O(1).
     * */
    public void deleteNode(ListNode node) {
        // Overwrite data of next node on current node.
        node.val = node.next.val;
        // Make current node point to next of next node.
        node.next = node.next.next;
    }

    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public ListNode removeElements(ListNode head, int val){
        ListNode sentinel = new ListNode(Integer.MIN_VALUE, head);
        ListNode predecessor = sentinel;
        ListNode current = head;

        while (current != null) { // since we have to traverse through the entire list
            // work to be done on the current node
            if (current.val == val) { // delete current node
                predecessor.next = current.next;
                current = current.next;
            } else { // move on
                predecessor = current;
                current = current.next;
            }
        }

        head = sentinel.next;
        return head;
    }

    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public ListNode removeDuplicateElements(ListNode head){
        ListNode sentinel = new ListNode(Integer.MIN_VALUE, head);
        ListNode predecessor = sentinel;
        ListNode current = head;

        while (current != null) { // since we have to traverse through the entire list
            // work to be done on the current node
            if (current.val == predecessor.val) { // delete current node
                predecessor.next = current.next;
                current = current.next;
            } else { // move on
                predecessor = current;
                current = current.next;
            }
        }

        head = sentinel.next;
        return head;
    }

    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public ListNode removeAllDuplicateElementsLeaveDistinct(ListNode head){
        ListNode sentinel = new ListNode(Integer.MIN_VALUE, head);
        ListNode predecessor = sentinel;
        ListNode current = head;

        while (current != null) { // since we have to traverse through the entire list
            // work to be done on the current node
            if (current.next != null && current.next.val == current.val) { // delete all copies of current value
                ListNode p = current.next;
                while (p != null && p.val == current.val){
                    p = p.next;
                }
                predecessor.next = p; // long jump
                current = p;
            } else { // move on
                predecessor = current;
                current = current.next;
            }
        }

        head = sentinel.next;
        return head;
    }

    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public ListNode removeNthNodeFromEnd(ListNode head, int n){
        ListNode sentinel = new ListNode(Integer.MIN_VALUE, head);
        ListNode leader = sentinel;
        for (int i = 0; i < n; i++){
            leader = leader.next;
        }
        ListNode follower = sentinel;
        ListNode predecessor = null;
        while (leader != null) { // since we have to traverse through the entire list
            leader = leader.next;
            predecessor = follower;
            follower = follower.next;
        }

        predecessor.next = follower.next; // this will remove the nth node from the edge of the list
        head = sentinel.next;
        return head;
    }

    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public ListNode deleteNMNodesAfterEveryNodes(ListNode head, int m, int n) {
        ListNode currentNode = head;
        ListNode lastMNode = head;
        while (currentNode != null) {
            // initialize mCount to m and nCount to n
            int mCount = m, nCount = n;
            // traverse m nodes
            while (currentNode != null && mCount != 0) {
                lastMNode = currentNode;
                currentNode = currentNode.next;
                mCount--;
            }
            // traverse n nodes
            while (currentNode != null && nCount != 0) {
                currentNode = currentNode.next;
                nCount--;
            }
            // delete n nodes
            lastMNode.next = currentNode;
        }
        return head;
    }

    /**
     *  Time: O(n).
     *  Auxiliary space: O(1).
     * */
    public ListNode insertIntoSortedCircularList(ListNode head, int insertVal) {
        ListNode node = new ListNode(insertVal, null);

        if (head == null) {
            node.next = node;
            return node;
        } else if (head.next == head) {
            node.next = head;
            head.next = node;
            return head;
        }

        ListNode current = head.next;
        ListNode predecessor = head;

        while (current.val >= predecessor.val && current != head) {
            current = current.next;
            predecessor = predecessor.next;
        }
        // current is at the smallest value in the circular list. Predecessor is at the largest value
        if (insertVal > predecessor.val) {
            predecessor.next = node;
            node.next = current;
            return head;
        }
        while (current.val < insertVal) {
            current = current.next;
            predecessor = predecessor.next;
        }

        predecessor.next = node;
        node.next = current;
        return head;
    }


    /**
     *  Time: O(m+n).
     *  Auxiliary space: O(1).
     * */
    public ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
        ListNode sentinel = new ListNode(Integer.MIN_VALUE, null); // creation of a new list
        ListNode tail = sentinel;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                tail = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                tail = l2;
                l2 = l2.next;
            }
            tail.next = null;
        }
        // gather pahse
        if (l1 != null) {
            tail.next = l1;
        } else {
            tail.next = l2;
        }
        return sentinel.next;
    }

    /**
     *  Time: O(nlogn).
     *  Auxiliary space: O(1); will include stack space
     * */
    public ListNode sortList(ListNode head){

        return sortListHelper(head);
    }

    private static ListNode sortListHelper(ListNode node) {
        // Base case: Leaf node
        if (node == null || node.next == null) {
            return node;
        }
        // recursive case
        ListNode hare = node;
        ListNode tortoise = node;
        while (hare.next != null && hare.next.next != null) {
            hare = hare.next.next;
            tortoise = tortoise.next;
        }
        ListNode node2 = tortoise.next;
        tortoise.next = null;

        ListNode l1 = sortListHelper(node);
        ListNode l2 = sortListHelper(node2);

        ListNode sentinel = new ListNode(Integer.MIN_VALUE, null);
        ListNode tail = sentinel;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                tail = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                tail = l2;
                l2 = l2.next;
            }
            tail.next = null;
        }
        // gather pahse
        if (l1 != null) {
            tail.next = l1;
        } else {
            tail.next = l2;
        }
        return sentinel.next;
    }


    /**
     *  Time(n,k): O(n*k logK).
     *  Auxiliary space: O(1).
     * */
    public ListNode mergeKLists(List<ListNode> lists){
        ListNode sentinel = new ListNode(Integer.MIN_VALUE, null);
        ListNode tail = sentinel;
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        for (ListNode list : lists) {
            if (list != null) {
                pq.add(list);
            }
        }

        while (!pq.isEmpty()){
            ListNode node = pq.poll();
            tail.next = node;
            tail = node;
            if (node.next != null) {
                pq.add(node.next);
            }
        }
        return sentinel.next;
    }


    public boolean isPalindrome(ListNode head) {

        if (head == null) return true;

        // Find the end of first half and reverse second half.
        ListNode firstHalfEnd = endOfFirstHalf(head);
        ListNode secondHalfStart = reverseList(firstHalfEnd.next);

        // Check whether there is a palindrome.
        ListNode p1 = head;
        ListNode p2 = secondHalfStart;
        boolean result = true;
        while (result && p2 != null) {
            if (p1.val != p2.val) result = false;
            p1 = p1.next;
            p2 = p2.next;
        }

        // Restore the list and return the result.
        firstHalfEnd.next = reverseList(secondHalfStart);
        return result;
    }

    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    private ListNode endOfFirstHalf(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    class LRUCache {
        int capacity;
        Map<Integer, ListNode> dic;
        ListNode head;
        ListNode tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            dic = new HashMap<>();
            head = new ListNode(-1, -1);
            tail = new ListNode(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if (!dic.containsKey(key)) {
                return -1;
            }

            ListNode node = dic.get(key);
            remove(node);
            add(node);
            return node.val;
        }

        public void put(int key, int value) {
            if (dic.containsKey(key)) {
                ListNode oldNode = dic.get(key);
                remove(oldNode);
            }

            ListNode node = new ListNode(key, value);
            dic.put(key, node);
            add(node);

            if (dic.size() > capacity) {
                ListNode nodeToDelete = head.next;
                remove(nodeToDelete);
                dic.remove(nodeToDelete.key);
            }
        }

        public void add(ListNode node) {
            ListNode previousEnd = tail.prev;
            previousEnd.next = node;
            node.prev = previousEnd;
            node.next = tail;
            tail.prev = node;
        }

        public void remove(ListNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */

    class LRUCache2 {
        int capacity;
        LinkedHashMap<Integer, Integer> dic;

        public LRUCache2(int capacity) {
            this.capacity = capacity;
            dic = new LinkedHashMap<>(5, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(
                        Map.Entry<Integer, Integer> eldest
                ) {
                    return size() > capacity;
                }
            };
        }

        public int get(int key) {
            return dic.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            dic.put(key, value);
        }
    }

    /**
    * TC = O(1)
    * SC = O(n)
     * */
    class MinStack {
        private Stack<int[]> stack = new Stack<>();

        public MinStack() {}

        public void push(int x) {
            /* If the stack is empty, then the min value
             * must just be the first value we add. */
            if (stack.isEmpty()) {
                stack.push(new int[] { x, x });
                return;
            }

            int currentMin = stack.peek()[1];
            stack.push(new int[] { x, Math.min(x, currentMin) });
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek()[0]; // the value that was pushed (x)
        }

        public int getMin() {
            return stack.peek()[1]; // the minimum value in the stack up to that point
        }
    }

    class MinStack2 {
        private Stack<Integer> stack = new Stack<>();
        private Stack<Integer> minStack = new Stack<>();

        public MinStack2() {}

        public void push(int x) {
            stack.push(x);
            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
            }
        }

        public void pop() {
            if (stack.peek().equals(minStack.peek())) {
                minStack.pop();
            }
            stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    class MinStack3 {
        private Stack<Integer> stack = new Stack<>();
        private Stack<int[]> minStack = new Stack<>();

        public MinStack3() {}

        public void push(int x) {
            // We always put the number onto the main stack.
            stack.push(x);

            // If the min stack is empty, or this number is smaller than
            // the top of the min stack, put it on with a count of 1.
            if (minStack.isEmpty() || x < minStack.peek()[0]) {
                minStack.push(new int[] { x, 1 });
            }
            // Else if this number is equal to what's currently at the top
            // of the min stack, then increment the count at the top by 1.
            else if (x == minStack.peek()[0]) {
                minStack.peek()[1]++;
            }
        }

        public void pop() {
            // If the top of min stack is the same as the top of stack
            // then we need to decrement the count at the top by 1.
            if (stack.peek().equals(minStack.peek()[0])) {
                minStack.peek()[1]--;
            }

            // If the count at the top of min stack is now 0, then remove
            // that value as we're done with it.
            if (minStack.peek()[1] == 0) {
                minStack.pop();
            }

            // And like before, pop the top of the main stack.
            stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek()[0];
        }
    }


    public static void main(String[] args) {
        System.out.println(findDuplicate2(new int[]{1,3,4,2,2}));
    }

    public  static class ListNode {
        int key;
        int val;
        ListNode next;
        ListNode prev;

        public ListNode(int key, int val) {
            this.key = key;
            this.val = val;
        }

        ListNode(int x, ListNode node) {
            val = x;
            this.next = node;
        }
    }

}

