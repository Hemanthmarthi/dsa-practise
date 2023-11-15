package com.graph;

import java.util.*;


public class Graph {

    //    private static int numVertices;
    private static List<List<Integer>> adjacencyList;
    private static boolean[] visited;
    private static int componentCount;

    /**
     * Asymptotic complexity in terms of the number of vertices `v` and number of edges `e` in the graph.
        Time Complexity:
        Initializing adjacency list: O(V)
        Adding edges to adjacency list: O(E)
        Sorting adjacent nodes: O(V log V) for each vertex
        Total: O(V + E + V log E)
     * Space Complexity:
        Adjacency list: O(V + E)
        Each vertex has a list of its neighbors: O(V)
        Each edge is stored in the adjacency list: O(E)
        Total: O(V + E)
     */
    public static List<List<Integer>> convertEdgeListToAdjacencyList(Integer numVertices, List<List<Integer>> edges) {
        adjacencyList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(i, new ArrayList<>());
        }

        for (List<Integer> edge : edges) {
            adjacencyList.get(edge.get(0)).add(edge.get(1));
            adjacencyList.get(edge.get(1)).add(edge.get(0));
        }

        for (List<Integer> adjacentNodes : adjacencyList) {
            int nodesSize = adjacentNodes.size();
            if (nodesSize > 0) {
                Collections.sort(adjacentNodes);
            }
        }

        return adjacencyList;
    }

    /**
     Time Complexity:
        Iterating through the edge list: O(E)
        Inserting an edge into the adjacency map: O(1) (assuming hash table implementation)
        Total: O(E)

     Space Complexity:
        Adjacency map: O(V + E) (assuming hash table implementation)
        Each vertex has a HashMap to store its neighbors: O(V)
        Each edge is stored in the adjacency map: O(E)
        Total: O(V + E)
     */
    public static Map<Integer, Map<Integer, Integer>> convertEdgeListToAdjacencyMap(int numVertices, List<List<Integer>> edges) {
        Map<Integer, Map<Integer, Integer>> adjacencyMap = new HashMap<>();

        for (List<Integer> edge : edges) {
            int source = edge.get(0);
            int destination = edge.get(1);
            int weight = edge.get(2); // Assuming edge list contains weights

            if (!adjacencyMap.containsKey(source)) {
                adjacencyMap.put(source, new HashMap<>());
            }
            adjacencyMap.get(source).put(destination, weight);

            if (!adjacencyMap.containsKey(destination)) {
                adjacencyMap.put(destination, new HashMap<>());
            }
            adjacencyMap.get(destination).put(source, weight);
        }

        return adjacencyMap;
    }

    /**
     Time Complexity:
        Iterating through the edge list: O(E)
        Iterating through the adjacency matrix: O(V^2)
        Total: O(E) + O(V^2)
     Space Complexity:
        Adjacency matrix: O(V^2)
        Total: O(V^2)
     */
    public static int[][] convertEdgeListToAdjacencyMatrix(List<List<Integer>> edgeList, int numVertices) {
        // Initialize the adjacency matrix with all zeros
        int[][] adjacencyMatrix = new int[numVertices][numVertices];

        // Iterate through the edge list and add edges to the adjacency matrix
        for (List<Integer> edge : edgeList) {
            int sourceVertex = edge.get(0);
            int destinationVertex = edge.get(1);

            adjacencyMatrix[sourceVertex][destinationVertex] = 1;
            // If the graph is undirected, also add an edge from the destination vertex to the source vertex
            if (!edgeList.get(destinationVertex).contains(sourceVertex)) {
                adjacencyMatrix[destinationVertex][sourceVertex] = 1;
            }
        }

        return adjacencyMatrix;
    }

    /**
    * TC
        O(V) -> push and pop
        sum of degree of a node = 2 e => O(e)
        Total = O(v+e)
    SC
     Aux space = O(n)
     * */
    public static void bfs(int s) {
        // Initialize visited, captured, and parent arrays to 0, 0, and null
        int[] captured = new int[s];
        Arrays.fill(captured, 0);

        visited = new boolean[s];
        Arrays.fill(visited, false);

        Node[] parent = new Node[s];
        Arrays.fill(parent, null);

        // Create a queue and enqueue the starting vertex
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        // Mark the starting vertex as captured and visited
        captured[s] = 1;
        visited[s] = true;

        while (!q.isEmpty()) {
            // Capture the next vertex
            int v = q.poll();
            captured[v] = 1;

            // Explore the neighbors of the current vertex
            for (int neighbor : adjacencyList.get(v)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = new Node(v);
                    q.add(neighbor);
                }
            }
        }
    }

    /**
     TC
        O(v) -> push and pop
        sum of degree of a node = 2 e => O(e)
        Total = O(v+e)
     SC
        Aux Space  =O(v)
     * */
    static void dfs(int node){
        visited[node] = true;
        for(int neighbor: adjacencyList.get(node)){
            if (!visited[neighbor]) {
                dfs(neighbor);
            }
        }
    }

    public static int findComponents(boolean dfs) {
        int v = adjacencyList.size();
        for (int i = 1; i <= v; i++) {
            if (!visited[i]) {
                componentCount++;
                if (dfs) {
                    dfs(i);
                } else {
                    bfs(i);
                }
            }
        }

        return componentCount;
    }

    public static class Node {
        private Object data; // The data stored in the node
        private Node next; // The next node in the linked list

        public Node(Object data) {
            this.data = data;
            this.next = null;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }


    public static void main(String[] args) {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(Arrays.asList(0, 1));
        edgeList.add(Arrays.asList(1, 2));
        edgeList.add(Arrays.asList(2, 3));
        edgeList.add(Arrays.asList(3, 4));
        edgeList.add(Arrays.asList(4, 5));
        edgeList.add(Arrays.asList(5, 6));

        List<List<Integer>> adjacencyList = convertEdgeListToAdjacencyList(7, edgeList);
        Map<Integer, Map<Integer, Integer>> adjacencyMap = convertEdgeListToAdjacencyMap(7, edgeList);

        System.out.println("adjacencyList=" + adjacencyList);
        System.out.println("adjacencyMap=" + adjacencyMap);
    }
}


