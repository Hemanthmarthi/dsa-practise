package com.linkedlists;

public class MyLinkedList {

    ListNode head;
    ListNode tail;

    public MyLinkedList() {
        ListNode head = new ListNode(0, null);
        ListNode tail = new ListNode(0, null);
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    int get(int index) {
        int nodeIndex = 0;
        ListNode current = this.head;

        while (current != null && nodeIndex != index) {
            current = current.next;
            nodeIndex++;
        }
        if (current == null) {
            return -1;
        } else {
            return current.val;
        }
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        ListNode node = new ListNode(val, head);
        this.head = node;
        if (this.tail == null) {
            this.tail = this.head;
        }
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        ListNode node = new ListNode(val, null);
        if (this.tail == null) {
            this.tail = node;
            this.head = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
    }

    /** Add a node of value val before the index-th node in the linked list.
     * If index equals to the length of linked list, the node will be appended to the end of linked list.
     * If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        ListNode sentinel = new ListNode(0, this.head);
        
        int nodeIndex = 0;
        ListNode current = this.head;
        ListNode predecessor = sentinel;
        while (current != null && nodeIndex != index){
            current = current.next;
            predecessor = predecessor.next;
            nodeIndex++;
        }
        ListNode node = new ListNode(val,  null);
        if (current != null) {
            predecessor.next = node;
            node.next = current;
        } else if (nodeIndex == index) {
            predecessor.next = node;
            this.tail = node;
        }
        this.head = sentinel.next;
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        ListNode sentinel = new ListNode(0, this.head);
        int nodeIndex = 0;
        ListNode current = this.head;
        ListNode predecessor = sentinel;
        while (current != null && nodeIndex != index) {
            current = current.next;
            predecessor = predecessor.next;
            nodeIndex++;
        }
        if (current != null) {
            predecessor.next = current.next;
            if (this.tail == current) {
                this.tail = predecessor;
                if (this.tail == sentinel) {
                    this.tail = null;
                }
            }
        }
        this.head = sentinel.next;
    }


    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val, ListNode node) {
            this.val = val;
            this.next = node;
        }
    }

}
