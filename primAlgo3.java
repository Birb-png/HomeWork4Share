import java.util.*;

public class primAlgo3 {
    static class Edge implements Comparable<Edge> {
        int cost;
        char n1;
        char n2;

        public Edge(int cost, char n1, char n2) {
            this.cost = cost;
            this.n1 = n1;
            this.n2 = n2;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.cost, other.cost);
        }

        @Override
        public String toString() {
            return "(" + n1 + ", " + n2 + ", " + cost + ")";
        }
    }

    static List<Edge> prims(int[][] adjacencyMatrix, char start) {
        List<Edge> minimumSpanningTree = new ArrayList<>();
        int totalCost = 0;

        Set<Character> visited = new HashSet<>();
        visited.add(start);

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        int startIndex = start - 'A'; // Convert char to index

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[startIndex][i] != 0) {
                priorityQueue.offer(new Edge(adjacencyMatrix[startIndex][i], start, (char)('A' + i)));
            }
        }

        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.poll();
            char n1 = edge.n1;
            char n2 = edge.n2;
            int cost = edge.cost;

            char newNode = visited.contains(n1) ? n2 : n1;

            if (!visited.contains(newNode)) {
                visited.add(newNode);
                minimumSpanningTree.add(edge);
                totalCost += cost;

                int newNodeIndex = newNode - 'A'; // Convert char to index

                for (int i = 0; i < adjacencyMatrix.length; i++) {
                    if (adjacencyMatrix[newNodeIndex][i] != 0 && !visited.contains((char)('A' + i))) {
                        priorityQueue.offer(new Edge(adjacencyMatrix[newNodeIndex][i], newNode, (char)('A' + i)));
                    }
                }
            }
        }

        System.out.println("Total cost: " + totalCost);
        return minimumSpanningTree;
    }

    public static void main(String[] args) {
        int[][] adjacencyMatrix = makeGraphManually();
        List<Edge> minimumSpanningTree = prims(adjacencyMatrix, 'A');

        System.out.println("Minimum spanning tree: ");
        for (Edge edge : minimumSpanningTree) {
            System.out.println(edge);
        }
    }

    static int[][] makeGraphManually() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of vertices: ");
        int numVertices = scanner.nextInt();
        int[][] adjacencyMatrix = new int[numVertices][numVertices];
        
        System.out.println("Enter the adjacency matrix (0 for no connection):");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                System.out.printf("input at [%d][%d] " , i,j);
                adjacencyMatrix[i][j] = scanner.nextInt();
            }
            // Print the current state of the matrix after each row
            for (int[] row : adjacencyMatrix) {
                for (int element : row) {
                    System.out.print(element + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        scanner.close();
        return adjacencyMatrix;
    }
}
