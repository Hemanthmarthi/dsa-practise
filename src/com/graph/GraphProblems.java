package com.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;


public class GraphProblems {

    /** 20 Problems
     1. Count Islands (LC#200)
        * Not and adjacency Matrix, instead an m*n grid
        * A trick, instead of maintaining a visited arrays, update the grid value from 1 to 0
     2. Maximum Area of Island (LC#695)
     3. Count Connected Components In An Undirected Graph
     4. Is It A Tree (LC#261)
     5. Complete All Courses With Dependencies (LC#207)
        * Part 2: if there is no cycle in the graph return the order in which the courses should be taken (LC#210)
     6. Friendly Groups
        * Is Graph Bipartite (explicit graph problem) LC#785
        * 2 color problem
        * Possible Bipartition (LC#886) (Implicit graph problem)
     7. Find The Town Judge
     8. Flood Fill (LC#733)
     9. Knight's Tour On A Chessboard
     10. Shortest String Transformation Using A Dictionary
     11. Shortest Transformation Sequences
     12. Course Schedule
     13. Zombie Clusters
         * adjacency Matrix
     14. Rotting Oranges
     15. Largest Connected Component In Binary Square Grid
     16. Snakes & Ladder (LC#909)
        * Concept is that it is a directed graph and need to use BFS to find the shortest path.
        * We need to make sure the way we model the graph such that every other dice throw maps to a single edge in the modeled graph
     17. Critical Connections (LC#1192). On an undirected Graph
     18. Find order of character from Alien dictionary
     19. Given a graph build a new one with reversed edges
     */

    static Boolean checkIfEulerianCycleExists(Integer n, ArrayList<ArrayList<Integer>> edges) {
        int[] vertexDegree = new int[n];

        for (ArrayList<Integer> edge : edges) {
            vertexDegree[edge.get(0)]++;
            vertexDegree[edge.get(1)]++;
        }

        for (int i = 0; i < n; i++) {
            if (vertexDegree[i] % 2 != 0) {
                return false;
            }
        }

        return true;
    }

    static Boolean checkIfEulerianPathExists(Integer n, ArrayList<ArrayList<Integer>> edges) {
        if (n == 1) {
            return true;
        }
        int[] degree = new int[n];

        for (ArrayList<Integer> edge : edges) {
            degree[edge.get(0)]++;
            degree[edge.get(1)]++;
        }

        int verticesWithOddDegree = 0;
        for (int i = 0; i < n; i++) {
            if (degree[i] % 2 > 0) {
                verticesWithOddDegree++;
            }
        }

        return verticesWithOddDegree == 2 || verticesWithOddDegree == 0;
    }

    /**
    Asymptotic complexity in terms of the number of vertices `n` and number of edges `e` in the graph.
    * Time: O(e).
    * Auxiliary space: O(1).
    * Total space: O(n * n).
    */
    static ArrayList<ArrayList<Boolean>> convertEdgeListToAdjacencyMatrix(Integer n, ArrayList<ArrayList<Integer>> edges) {

        ArrayList<ArrayList<Boolean>> result = new ArrayList();
        Boolean[][] matrix = new Boolean[n][n];
        for(int i =0; i < n ; i++){
            for(int j=0; j < n; j++){
                matrix[i][j]=false;
            }
        }
        for(ArrayList<Integer> edge: edges){
            matrix[edge.get(0)][edge.get(1)] = true;
            matrix[edge.get(1)][edge.get(0)] = true;
        }

        for (int i =0; i <n; i++){
            result.add(new ArrayList<Boolean>(Arrays.asList(matrix[i])));
        }

        return result;
    }

    /********** Undirected Graph problems *************/
    static int countComponents(int numberOfVertices, int[][] edges) {
        //1. Build Graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numberOfVertices; i++) {
            graph.add(i, new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        //2. BFS

        //3. visit all nodes, run BFS on unvisited
        boolean[] visited = new boolean[numberOfVertices];
        int components = 0;
        for (int i = 0; i < numberOfVertices; i++) {
            if (!visited[i]) {
                components++;
                max_island_size_bfs(i, visited, graph);
            }
        }
    return components;
    }

    /**
     * TC = O(number_of_nodes + number_of_edges).
        To create adjacency list: O(number_of_edges).
        BFS algorithm: O(number_of_nodes + number_of_edges).
     * SC = O(number_of_nodes + number_of_edges).
        Space used for input: O(number_of_edges).
        Auxiliary space used: O(number_of_nodes + number_of_edges).
        Space used for output: O(1).
        So, total space complexity: O(number_of_nodes + number_of_edges).
     * */
    static void max_island_size_bfs(int vertex, boolean[] visited, List<List<Integer>> graph) {
        // Create a queue and enqueue the starting vertex
        Queue<Integer> q = new LinkedList<>();
        q.add(vertex);
        // Mark the starting vertex as visited
        visited[vertex] = true;

        while (!q.isEmpty()) {
            // Capture the next vertex
            int v = q.poll();
            // Explore the neighbors of the current vertex
            for (int neighbor : graph.get(v)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    q.add(neighbor);
                }
            }
        }
    }

    /*
     * Asymptotic complexity in terms of the number of rows `n` and the number of columns `m` of the input matrix:
     * Time: O(n * m).
     * Auxiliary space: O(max(n, m)).
     * Total space: O(n * m).
     */
    // All 8 directions. Consider as pair: {addRow[i], addColumn[i]}.
    static int[] addRow = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] addColumn = {-1, -1, 0, 1, 1, 1, 0, -1};
    static void countIslandsBfs(int row, int column, ArrayList<ArrayList<Integer>> grid) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{row, column});
        grid.get(row).set(column, 0);

        while (!queue.isEmpty()) {
            int[] head = queue.poll();
            int currentRow = head[0];
            int currentColumn = head[1];

            for (int i = 0; i < 8; i++) {
                // Try to visit all 8 neighbors.
                int newRow = currentRow + addRow[i];
                int newColumn = currentColumn + addColumn[i];

                // Out of the matrix.
                if (newRow < 0 || newRow >= grid.size() || newColumn < 0 || newColumn >= grid.get(0).size()) {
                    continue;
                }

                if (grid.get(newRow).get(newColumn) != 0) {
                    grid.get(newRow).set(newColumn, 0);
                    queue.add(new int[]{newRow, newColumn});
                }
            }
        }
    }
    static int countIslands(ArrayList<ArrayList<Integer>> grid) {
        // since we can infer the adjacency list from the grid implicitly,
        // we don't have to create an explicit adjacency list
        // Further optimization, avoid the visited array instead change the value to 0 within the grid itself

        int islands = 0;

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                // When we find unvisited node, visit it and visit all the reachable nodes.
                if (grid.get(i).get(j) != 0) {
                    islands++;
                    countIslandsBfs(i, j, grid);
                }
            }
        }

        return islands;
    }
    static void countIslandsDfs(int row, int column, ArrayList<ArrayList<Integer>> matrix) {
        matrix.get(row).set(column, 0);
        for (int i = 0; i < 8; i++) {
            // Try to visit all 8 neighbors.
            int new_row = row + addRow[i];
            int new_column = column + addColumn[i];

            // Out of the matrix.
            if (new_row < 0 || new_row >= matrix.size() || new_column < 0 || new_column >= matrix.get(0).size()) {
                continue;
            }

            if (matrix.get(new_row).get(new_column) != 0) {
                countIslandsDfs(new_row, new_column, matrix);
            }
        }
    }

    static int maxAreaOfIsland(ArrayList<ArrayList<Integer>> grid) {
        int r = grid.size();
        int c = grid.get(0).size();
        Integer result = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid.get(i).get(j) == 1) {
                    result = Integer.max(maxAreaOfIslandBfs(i, j, grid, r, c), result);
                }
            }
        }
        return result;
    }
    static Integer maxAreaOfIslandBfs(int i, int j, ArrayList<ArrayList<Integer>> grid, int r, int c) {
        Queue<int[]> queue = new LinkedList<>();
        int[][] neighbors = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        grid.get(i).set(j, 0);
        queue.add(new int[]{i, j});
        int counter = 1;
        while (!queue.isEmpty()) {
            int[] node = queue.poll();
            for (int[] neighbor : neighbors) {
                int row = neighbor[0] + node[0];
                int col = neighbor[1] + node[1];
                if (row >= 0 && row < r && col >= 0 && col < c && grid.get(row).get(col) == 1) {
                    grid.get(row).set(col, 0);
                    counter++;
                    queue.add(new int[]{row, col});
                }
            }
        }
        return counter;
    }

    /*
     * Asymptotic complexity in terms of number of rows `R` and number of columns `C` in the grid:
     * Time: O(R * C).
     * Auxiliary space: O(R * C).
     * Total space: O(R * C).
     */
    static boolean[][] visited;
    static int count;
    static int[][] get_four_neighbors(int[] cell) {
        return  new int[][]{ {cell[0], cell[1] - 1},
                {cell[0], cell[1] + 1},
                {cell[0] - 1, cell[1]},
                {cell[0] + 1, cell[1]} };
    }
    static boolean can_visit(int current_row, int current_column, int R, int C, ArrayList<ArrayList<Integer>> grid) {
        if (current_row < 0 || current_row >= R)
            return false;
        if (current_column < 0 || current_column >= C)
            return false;
        if (visited[current_row][current_column])
            return false;
        if (grid.get(current_row).get(current_column) == 0)
            return false;

        return true;
    }
    static void max_island_size_bfs(int current_row, int current_column, int R, int C, ArrayList<ArrayList<Integer>> grid) {
        int[] current_cell;
        Queue<int[]> queue = new LinkedList<>();

        visited[current_row][current_column] = true;
        queue.add(new int[]{current_row, current_column});

        while (!queue.isEmpty()) {
            current_cell = queue.poll();
            count++;

            for (int[] neighbor: get_four_neighbors(current_cell)) {
                if (!can_visit(neighbor[0], neighbor[1], R, C, grid))
                    continue;
                visited[neighbor[0]][neighbor[1]] = true;
                queue.add(neighbor);
            }
        }
    }
    static Integer max_island_size(ArrayList<ArrayList<Integer>> grid) {
        int R = grid.size();
        int C = grid.get(0).size();
        int answer = 0;

        visited = new boolean[R][C];

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (can_visit(i, j, R, C, grid)) {
                    count = 0;
                    max_island_size_bfs(i, j, R, C, grid);
                    answer = Math.max(answer, count);
                }
            }
        }

        return answer;
    }

    static List<List<Integer>> adjList = new ArrayList<>();
    static int[] visitedVertices = null;
    static int[] nodeParent = null;
    /*
      Input - {"node_count": 4,"edge_start": [0, 0, 0],"edge_end": [1, 2, 3]}
      Output - 1
     */
    public static boolean isGraphATree(int nodeCount, List<Integer> edgeStart, List<Integer> edgeEnd) {
        // 1st property: The graph has to be connected
        // 2nd property: The graph cannot have a cycle
            // In a BFS traversal, if a tree has a cross edge and
            // if the discovered node is not the parent then there exists a cycle

        //1. Build a graph
        for (int i = 0; i < nodeCount; i++) {
            adjList.add(i, new ArrayList<>());
        }

        for (int i = 0; i < edgeStart.size(); i++) {
            adjList.get(edgeStart.get(i)).add(edgeEnd.get(i));
            adjList.get(edgeEnd.get(i)).add(edgeStart.get(i));
        }

        visitedVertices = new int[nodeCount];
        Arrays.fill(visitedVertices, -1);

        nodeParent = new int[nodeCount];
        Arrays.fill(nodeParent, -1);

        //2. BFS
        //3. Outer loop
        int components = 0;
        for (int i = 0; i < nodeCount; i++) {
            if (visitedVertices[i] == -1) {
                if (!isGraphATreeBfs(i) || components > 0) {
                    return false; // The given graph is not connected
                }
                components++;
            }
        }
        return true;// The given graph is connected and does not have any cycle. Hence, it is a tree.
    }
    static boolean isGraphATreeBfs(int vertex) {
        Queue<Integer> q = new LinkedList<>();
        q.add(vertex);
        visitedVertices[vertex] = 1;
        while (!q.isEmpty()) {
            int n = q.remove();
            for (int j = 0; j < adjList.get(n).size(); j++) {
                int neighbor = adjList.get(n).get(j);
                if (visitedVertices[neighbor] == -1) {
                    nodeParent[neighbor] = n;
                    visitedVertices[neighbor] = 1;
                    q.add(neighbor);
                } else {// A neighbor who has already been discovered is found.
                    if (neighbor != nodeParent[n]) {
                        return false; // Cross edge found, Cycle detected
                    }
                }
            }
        }
        return true;
    }

    /*
     * Asymptotic complexity in terms of the number of nodes `n` and the number of edges `m`:
     * Time: O(n + m).
     * Auxiliary space: O(n + m).
     * Total space: O(n + m).
     */
    static ArrayList<ArrayList<Integer>> edges; // adjacency list
    static ArrayList<Integer> color;
    static HashSet<String> unique_set_of_edges;
    static int WHITE = 0; // Not visited node
    static int GREY = 1;  // Visited node
    static int BLACK = 2; // All neighbors of this node are visited
    static boolean has_cycle;
    static void isItATreeDfs(int node, int parent) {
        color.set(node, GREY);  // As we have discovered this node and starting to work with it

        // Next we shall call dfs to all the adjacent nodes which are not yet discovered
        for (int neighbor : edges.get(node)) {
            if (color.get(neighbor) == WHITE) {
                isItATreeDfs(neighbor, node);
            }
            else if (color.get(neighbor) == GREY) {
                if (neighbor != parent) { // The condition checks if the neighbor is not a parent of the current vertex because the edge with parent does not create a cycle.
                    has_cycle = true; // A back edge is found.
                }
            }
        }

        color.set(node, BLACK); // No other nodes left to be discovered from node index
    }
    static Boolean isItATree(Integer node_count, ArrayList<Integer> edge_start, ArrayList<Integer> edge_end) {
        color = new ArrayList<>();
        edges = new ArrayList<>();
        unique_set_of_edges = new HashSet<>();
        has_cycle = false;

        for (int i = 0; i < node_count; i++) {
            color.add(0);
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < edge_start.size(); i++) {
            int start = edge_start.get(i), end = edge_end.get(i);

            if (start == end) {
                return false; // Self loop detected.
            }

            edges.get(start).add(end);
            edges.get(end).add(start);

            String current_edge = Math.min(start, end) + " " + Math.max(start, end);
            if (unique_set_of_edges.contains(current_edge)) {
                return false; // The given graph can not be a tree because it has a multi-edges.
            }
            unique_set_of_edges.add(current_edge);
        }

        // Initiating a dfs from node 0 to mark all the nodes which are connected to node 0 through a path.
        // Node 0 has no parent and the valid nodes are labeled from 0 to n - 1.
        // We are using -1 as its hypothetical parent.
        isItATreeDfs(0, -1);

        if (has_cycle) {
            return false;
        }

        for (int i = 0; i < node_count; i++) {
            if (color.get(i) == WHITE) {
                // It means we just found a node which is not connected with node 0 through a path
                // So the given graph is not connected and hence, not a tree.
                return false;
            }
        }

        return true;
    }


    // Is graph Bipartite
    static Boolean friendlyGroups(Integer numOfPeople, ArrayList<Integer> dislike1, ArrayList<Integer> dislike2) {
        List<List<Integer>> graph = new ArrayList<>(numOfPeople);
        //1. Build graph
        for (int i = 0; i < numOfPeople; i++) {
            graph.add(i, new ArrayList<>());
        }

        for (int i = 0; i < dislike1.size(); i++) {
            graph.get(dislike1.get(i)).add(dislike2.get(i));
            graph.get(dislike2.get(i)).add(dislike1.get(i));
        }

        boolean[] visited = new boolean[numOfPeople];
        int[] distance = new int[numOfPeople];

        // 3. Outer loop
        for (int i = 0; i < numOfPeople; i++) {
            if (!visited[i]) {
                if (!friendlyGroupsBfs(graph, i, visited, distance)) {
                    return false;
                }
            }
        }
        return true;
    }
    static boolean friendlyGroupsBfs(List<List<Integer>> graph, int src, boolean[] visited, int[] distance) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);
        visited[src] = true;
        distance[src] = 0;
        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            for (Integer neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                    distance[neighbor] = distance[node] + 1;
                } else {
                    if (distance[neighbor] == distance[node]) { // Cross edge and odd length cycle, not bipartite
                        return false;
                    }
                }
            }
        }
        return true; // Cross edge and even length cycle so compatible with bipartiteness
    }


    /********** Directed Graph problems *************/

    static Boolean courseSchedule(Integer n, ArrayList<Integer> a, ArrayList<Integer> b) {
        // It's possible only if we don't have a cycle.
        // When doing DFS traversals (if more than 1 component), we must make sure we don't have a back edge in the dfs tree
        // 1. Build the adjList
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        int[] visited = new int[n];
        int[] arr = new int[n]; // Arrival time, not used but good to have for potentially different requirements (i.e. forward edge)
        int[] dep = new int[n]; // Departure time

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<Integer>());
            arr[i] = -1;
            dep[i] = -1;
        }
        for (int i = 0; i < a.size(); i++) {
            int from = a.get(i);
            int to = b.get(i);
            adjList.get(from).add(to);
        }

        // 2. Call DFS for all the nodes, return true if none of them returns true (cycle found)
        for (int i = 0; i < n; i++) {
            if (arr[i] == -1) {
                if (courseScheduleDfs(adjList, i, visited, arr, dep)) {
                    return false; //Arrays.asList(-1);
                }
            }
        }
        return true; // Collections.reverse(topSortArray); // returns a sorted order of the courses
    }
    static int timestamp = 0;
    static List<Integer> topSortArray = new ArrayList<>();
    static boolean courseScheduleDfs(
            ArrayList<ArrayList<Integer>> adjList,
            int source, int[] visited,
            int[] arrivalTime, int[] departureTime) {
        visited[source] = 1;
        arrivalTime[source] = timestamp++;
        for (int neighbor : adjList.get(source)) {
            if (visited[neighbor] == 0) {
                if (courseScheduleDfs(adjList, neighbor, visited, arrivalTime, departureTime)) {
                    return true;    // we found a cycle down the dfs tree
                }
            } else { // (visited[neighbor] == 1), neighbor is already visited
                if (departureTime[neighbor] == -1) {
                    return true;   // we have a back edge (which goes back to a node that's part of the current dfs traversal)
                } else {
                    if (arrivalTime[source] < arrivalTime[neighbor]) {
                        // We have a forward edge
                    }
                    if (arrivalTime[source] > arrivalTime[neighbor]) {
                        // We have a cross edge
                    }
                }
            }
        }
        departureTime[source] = timestamp++;
        topSortArray.add(source);
        return false;
    }


    /**
     * SC =
          O(V+E)
     * TC =
            Building the adjacency list: O(V + E)
            DFS: O(V + E)
            Outer loop: O(V)
            Total: O(V + E)
     * */
    static HashMap<Character, HashSet<Character>> adjListMap = new HashMap<>();
    static HashMap<Character, Integer> arrival = new HashMap<>();
    static HashMap<Character, Integer> departure = new HashMap<>();
    static LinkedList<Character> topsort = new LinkedList<>();
    static boolean hasCycle = false;
    static String alienDictionary(ArrayList<String> words) {
        if(words.size() == 1){
            for (char character: words.get(0).toCharArray()) {
                // Need to handle these scenarios
                // "words": ["g", "g", "g", "g"]
                // "words": ["eeeeeeeeeeee"]
                // "words": ["a"]
            }
        }

        // Build graph
        for (int i = 0; i < words.size() - 1; i++) {
            String word1 = words.get(i);
            String word2 = words.get(i + 1);

            for (int j = 0; j < word1.length(); j++) {
                char c1 = word1.charAt(j);
                char c2 = word2.charAt(j);

                if (c1 != c2) {
                    if (!adjListMap.containsKey(c1)) {
                        adjListMap.put(c1, new HashSet<>());
                    }

                    adjListMap.get(c1).add(c2);
                    break;
                }
            }
        }

        // DFS

        // Outer loop
        for (char c : adjListMap.keySet()) {
            if (arrival.get(c) == null) {
                hasCycle = alienDictionaryDfs(c);

                if (hasCycle) {
                    return "";
                }
            }
        }

        // Collections.reverse(topsort); since we are using LinkedList

        StringBuilder sb = new StringBuilder();
        for (char c : topsort) {
            sb.append(c);
        }

        return sb.toString();
    }
    static boolean alienDictionaryDfs(char src) {
        arrival.put(src, timestamp);
        timestamp++;
        HashSet<Character> neighbors = adjListMap.get(src);
        if (neighbors == null) {
            departure.put(src, timestamp);
            timestamp++;
            topsort.push(src);
            return false; // No outgoing edges from this node
        }
        for (char nei : neighbors) {
            // Tree edge
            if (arrival.get(nei) == null) {
                hasCycle = alienDictionaryDfs(nei);
                if (hasCycle) {
                    return true;
                }
            } else if (departure.get(nei) == null) {
                // Back edge
                hasCycle = true;
            }
        }

        departure.put(src, timestamp);
        timestamp++;
        topsort.push(src);

        return hasCycle;
    }

    static class GraphNode {
        Integer value;
        ArrayList<GraphNode> neighbors;

        GraphNode(Integer value) {
            this.value = value;
            this.neighbors = new ArrayList<>(3);
        }
    }
    /*static class GraphNode {
        private int value;
        private HashSet<GraphNode> neighbors;

        public GraphNode(int value) {
            this.value = value;
            this.neighbors = new HashSet<>();
        }

        public int getValue() {
            return value;
        }

        public HashSet<GraphNode> getNeighbors() {
            return neighbors;
        }

        public void addNeighbor(GraphNode neighbor) {
            this.neighbors.add(neighbor);
        }
    }*/
    static GraphNode createTranspose(GraphNode node) { // reverse Graph edges
        HashSet<GraphNode> visited = new HashSet<>();
        LinkedList<GraphNode> queue = new LinkedList<>();

        Map<GraphNode, GraphNode> oldToNew = new HashMap<>();

        visited.add(node);
        queue.add(node);

        while (!queue.isEmpty()) {
            GraphNode oldNode = queue.poll();

            // Add entry into reversed_graph_map if it doesn't exist
            if (!oldToNew.containsKey(oldNode)) {
                GraphNode newNode = new GraphNode(oldNode.value);
                oldToNew.put(oldNode, newNode);
            }

            for (GraphNode nei : oldNode.neighbors) {
                if (!visited.contains(nei)) {
                    if (!oldToNew.containsKey(nei)) {
                        GraphNode newNei = new GraphNode(nei.value);
                        oldToNew.put(nei, newNei);
                    }

                    visited.add(nei);
                    queue.add(nei);

                    // Add reversal relationship
                    oldToNew.get(nei).neighbors.add(oldToNew.get(oldNode));
                }
            }
        }

        return oldToNew.get(node);
    }


    static Integer find_town_judge(Integer n, ArrayList<ArrayList<Integer>> trust) {
        boolean[] isJudge = new boolean[n + 1];
        for (int i = 0; i <= n; i++) {
            isJudge[i] = true;
        }
        for (ArrayList<Integer> persons : trust) {
            int p1 = persons.get(0);
            isJudge[p1] = false;
        }
        int count = 0;
        int judge = -1;
        for (int i = 1; i <= n; i++) {
            if (isJudge[i]) {
                count++;
                judge = i;
            }
        }
        if (count == 1)
            return judge;
        return -1;
    }

    /*
    Asymptotic complexity in terms of total number of pixels in `image`(i.e. width * height) `n`:
    * Time: O(n).
    * Auxiliary space: O(n).
    * Total space: O(n).
    */

    static ArrayList<ArrayList<Integer>> floodFill(Integer pixel_row, Integer pixel_column, Integer new_color, ArrayList<ArrayList<Integer>> image) {
        if (image.get(pixel_row).get(pixel_column).equals(new_color)) {
            return image;
        }
        Integer old_color = image.get(pixel_row).get(pixel_column);
        image.get(pixel_row).set(pixel_column, new_color);

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{pixel_row, pixel_column});

        int row, column;
        while (!queue.isEmpty()) {
            row = queue.peek()[0];
            column = queue.peek()[1];
            queue.poll();

            if (row + 1 < image.size() && image.get(row + 1).get(column).equals(old_color)) {
                image.get(row + 1).set(column, new_color);
                queue.add(new int[]{row + 1, column});
            }
            if (row - 1 >= 0 && image.get(row - 1).get(column).equals(old_color)) {
                image.get(row - 1).set(column, new_color);
                queue.add(new int[]{row - 1, column});
            }
            if (column + 1 < image.get(row).size() && image.get(row).get(column + 1).equals(old_color)) {
                image.get(row).set(column + 1, new_color);
                queue.add(new int[]{row, column + 1});
            }
            if (column - 1 >= 0 && image.get(row).get(column - 1).equals(old_color)) {
                image.get(row).set(column - 1, new_color);
                queue.add(new int[]{row, column - 1});
            }
        }

        return image;
    }


    static class Tuple{
        int row;
        int column;
        Tuple(int row, int column){
            this.row = row;
            this.column = column;
        }
    }

    static Integer find_minimum_number_of_moves(Integer rows, Integer cols, Integer start_row, Integer start_col, Integer end_row, Integer end_col) {
        return minMovesInBoard(rows, cols, start_row, start_col, end_row, end_col);
    }

    private static int minMovesInBoard(int rows, int columns, int startRow, int startColumn, int endRow, int endColumn) {
        if (startRow == endRow && startColumn == endColumn) {
            return 0;
        }

        int minMoves = 0;

        boolean[][] visited = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                visited[i][j] = false;
            }
        }

        //moves
        int[] dx = new int[]{2, 2, -2, -2, 1, 1, -1, -1};
        int[] dy = new int[]{-1, 1, -1, 1, 2, -2, -2, 2};

        //declaring queue
        Queue<Tuple> queue = new LinkedList<Tuple>();
        Tuple tuple = new Tuple(startRow, startColumn);
        queue.add(tuple);

        visited[startRow][startColumn] = true;

        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            for (int j = 0; j < queueSize; j++) {
                Tuple current = queue.poll();
                if (current.row == endRow && current.column == endColumn) {
                    return minMoves;
                }
                for (int i = 0; i < dx.length; i++) {
                    int newRow = current.row + dx[i];
                    int newCol = current.column + dy[i];
                    if (is_valid_position(rows, columns, newRow, newCol) && !visited[newRow][newCol]) {
                        visited[newRow][newCol] = true;
                        queue.add(new Tuple(newRow, newCol));
                    }

                }
            }
            minMoves++;
        }

        return -1;
    }

    private static boolean is_valid_position(int rows, int cols, int new_row, int new_col) {
        return ((0 <= new_row) && (new_row < rows) && (0 <= new_col) && (new_col < cols));
    }




    /**
     * Time Complexity:  O(E + V log V)
        * The first part of the code, which builds the graph, takes O(E) time because it iterates over all the tickets and updates the adjacency list.
        * The second part of the code, which performs a depth-first search (DFS) to reconstruct the itinerary, takes O(V log V) time. The DFS algorithm visits each vertex once, and the priority queue used to store the destinations takes O(log V) time to insert and remove elements.
     * Space Complexity:  O(E + V log V)
        * The graph itself takes O(E) space to store the edges, and the priority queues used in the DFS algorithm take O(V log V) space to store the vertices and their destinations.
     * */
    static List<String> reconstructItinerary(List<List<String>> tickets) {  // edge lists
        // Build the Graph
        Map<String, PriorityQueue<String>> graph = new HashMap<>();

        for (List<String> ticket : tickets) {
            graph.putIfAbsent(ticket.get(0), new PriorityQueue<>());
            graph.get(ticket.get(0)).add(ticket.get(1));
        }

        LinkedList<String> itinerary = new LinkedList<>();

        reconstructItineraryDfs("JFK", graph, itinerary);

        return itinerary;
    }
    static void reconstructItineraryDfs(String airport, Map<String, PriorityQueue<String>> graph, LinkedList<String> itinerary) {
        PriorityQueue<String> nextAirports = graph.get(airport);
        while (nextAirports != null && !nextAirports.isEmpty()) {
            reconstructItineraryDfs(nextAirports.poll(), graph, itinerary);
        }
        itinerary.addFirst(airport);
    }




    public static void main(String[] args) {
        List<List<String>> tickets = new ArrayList<>();
        tickets.add(Arrays.asList("JFK", "ATL"));
        tickets.add(Arrays.asList("ATL", "JFK"));
        tickets.add(Arrays.asList("JFK", "SFO"));
        tickets.add(Arrays.asList("SFO", "ATL"));
        tickets.add(Arrays.asList("ATL", "SFO"));
        System.out.println(reconstructItinerary(tickets));

        ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
        grid.add(new ArrayList<>(Arrays.asList(1,1,0)));
        grid.add(new ArrayList<>( Arrays.asList(1,1,0)));
        grid.add(new ArrayList<>(Arrays.asList(0,0,1)));
        //System.out.println(maxAreaOfIsland(grid));

        System.out.println(alienDictionary(new ArrayList<>(Arrays.asList("baa", "abcd", "abca", "cab", "cad"))));
    }
}
