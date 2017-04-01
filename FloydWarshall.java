import java.util.*;

public class FloydWarshall {
	private int graph[][];
	private int numV;
	public static final int INF = Integer.MAX_VALUE;

	public FloydWarshall(int[][] matrix) {
		graph = matrix;
		numV = graph.length - 1;
	}

	// Run Floyd-Warshall algorithm
	public void floydwarshall() {
		for (int i = 1; i <= numV; i++) { // intermediate vertex
			for (int s = 1; s <= numV; s++) {
				for (int d = 1; d <= numV; d++) {
					if (graph[s][i] != INF && graph[i][d] != INF && graph[s][i] + graph[i][d] < graph[s][d])
						graph[s][d] = graph[s][i] + graph[i][d];
				}
			}
		}
	}

	// Demonstrate usage
	public static void main(String... arg) {
		Scanner s = new Scanner(System.in);
		int numV = s.nextInt();
		int numE = s.nextInt();   
		int[][] graph = new int[numV + 1][numV + 1];

		for (int i = 1; i <= numV; i++) {
			Arrays.fill(graph[i], INF);
		}

		for (int i = 1; i <= numE; i++) {
			int v1 = s.nextInt();
			int v2 = s.nextInt();
			graph[v1][v2] = s.nextInt();
			// graph[v2][v1] = graph[v1][v2]; // only if undirected graph
		}

		FloydWarshall f = new FloydWarshall(graph);
		f.floydwarshall();
		s.close();
	}
}