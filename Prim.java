import java.util.*;

/**
 * @author Lam Nguyen
 * An implementation of Prim algorithm to find the minimum spanning tree of a weighted graph.
 * The graph is represented using adjacency lists.
 * Priority queue is used to efficiently choose the edge to add to the tree. 
 *
 */
public class Prim {
	int numVertices;
	ArrayList<ArrayList<Edge>> edges;
	HashSet<Integer> reached; // the list of vertices in the tree
	PriorityQueue<Edge> queue;
	ArrayList<Edge> MST; // list of edges in the MST
	long MSTweight;
	
	public Prim(ArrayList<ArrayList<Edge>> edges) {
		this.edges = edges;
		numVertices = edges.size() - 1;
		reached = new HashSet<Integer>();
		queue = new PriorityQueue<Edge>();
		MST = new ArrayList<Edge>();
		MSTweight = 0;
	}
	
	// Returns the total weight of the MST of the graph
	// MST stores the list of edges in the tree 
	public long findMST(int start) {
		reached.add(start);
		for (Edge e: edges.get(start)) {
			queue.add(e);
		}
		
		while (reached.size() < numVertices) {
			Edge curEdge = queue.remove();
			if (reached.contains(curEdge.to)) continue;
			reached.add(curEdge.to);
			
			// Only add edges that connect this vertex to vertices that are not already added
			for (Edge e: edges.get(curEdge.to)) {
				if (!reached.contains(e.to)) {
					queue.add(e);
				}
			}
			MSTweight += curEdge.cost;
		}
		
		return MSTweight;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		// read the graph in
		System.out.println("Enter the number of vertices and the number of edges:");
		int numVertices = s.nextInt();
		int numEdges = s.nextInt();

		ArrayList<ArrayList<Edge>> edges = new ArrayList<ArrayList<Edge>>();
		for (int i = 0; i < numVertices + 1; i++) {
			edges.add(new ArrayList<Edge>());
		}

		System.out.println("Enter the edges:");
		int a,b;
		long w;
		for (int i = 0; i < numEdges; i++) {
			a = s.nextInt();
			b = s.nextInt();
			w = s.nextLong();
			edges.get(a).add(new Edge(a,b,w));
			edges.get(b).add(new Edge(b,a,w));
		}
		System.out.println("Enter the starting node:");
		int start = s.nextInt();
		
		Prim p = new Prim(edges);
		System.out.println(p.findMST(start));
		
		s.close();
	}

	// The Edge class 
	private static class Edge implements Comparable<Edge>{
		int from;
		int to;
		long cost;

		public Edge(int from, int to, long c) {
			this.from = from;
			this.to = to;
			this.cost = c;
		}
		
		public int compareTo(Edge edge) {
			return Long.compare(this.cost, edge.cost);
		}
	}
}