//Birb
import java.util.*;

public class PrimAlgo3 {
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
        int[][] adjacencyMatrix = makeGraph();
        List<Edge> minimumSpanningTree = prims(adjacencyMatrix, 'A');

        System.out.println("Minimum spanning tree: ");
        for (Edge edge : minimumSpanningTree) {
            System.out.println(edge);
        }
    }

    static int[][] makeGraph() {
        int[][] adjacencyMatrix = {
                {0, 3, 3, 0, 0, 0, 0},
                {2, 0, 4, 0, 3, 0, 0},
                {3, 0, 0, 5, 1, 6, 0},
                {3, 0, 5, 0, 0, 0, 7},
                {0, 2, 0, 1, 0, 8, 0},
                {0, 0, 6, 0, 8, 0, 9},
                {0, 0, 0, 0, 0, 7, 0}
        };
        return adjacencyMatrix;
    }
}
