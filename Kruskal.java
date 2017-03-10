import java.util.*;

/**
 * @author Lam Nguyen
 * An implementation of Kruskal algorithm to find the minimum spanning tree of a weighted graph.
 * The graph is represented using adjacency lists.
 * Priority queue is used to efficiently choose the edge to add to the tree. 
 *
 */
public class Kruskal {
	int numVertices;
	ArrayList<ArrayList<Edge>> edges;
	HashSet<Integer> reached; // the list of vertices in the tree
	PriorityQueue<Edge> queue;
	ArrayList<Edge> MST; // list of edges in the MST
	long MSTweight;
	
	public Kruskal(ArrayList<ArrayList<Edge>> edges) {
		this.edges = edges;
		numVertices = edges.size() - 1;
		reached = new HashSet<Integer>();
		queue = new PriorityQueue<Edge>();
		for (ArrayList<Edge> list : edges) {
			for (Edge e : list) {
				queue.add(e);
			}
		}
		MST = new ArrayList<Edge>();
		MSTweight = 0;
	}
	
	// Returns the total weight of the MST of the graph
	// MST stores the list of edges in the tree 
	public long findMST() {
		Set[] sets = new Set[numVertices + 1];
		for (int i = 1; i < sets.length; i++) {
			sets[i] = new Set(i, 1);
		}
		int count = 0;
		while (count < numVertices - 1) {
			Edge curEdge = queue.remove();
			// skip the edge if the two vertices are already connected
			if (find(sets, curEdge.from) == find(sets, curEdge.to) ) continue;
			MSTweight += curEdge.cost;
			union(sets, curEdge.from, curEdge.to);
			count++;
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
		
		Kruskal k = new Kruskal(edges);
		System.out.println(k.findMST());
		
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
	
	// The Set class represents a collection of disjoint sets, with two operations: find and union
	// This implementation is specific for sets of integers (mostly to use as sets of vertices).
	private static class Set {
		int parent;
		int rank; // size of the set
		
		Set(int p, int r) {
			parent = p;
			rank = r;
		}	
	}
	
	// This function finds the set that contains the given element, using path compression
	public static int find(Set[] sets, int x) {
		if (sets[x].parent != x) {
			sets[x].parent = find(sets, sets[x].parent);
		}
		return sets[x].parent;
	}
	
	// This function unions the set containing x with the set containing y
	public static void union(Set[] sets, int x, int y) {
		int xparent = find(sets, x);
		int yparent = find(sets, y);
		if (xparent == yparent) return;
		if (sets[xparent].rank < sets[yparent].rank) {
			sets[xparent].parent = yparent;
		}
		else if (sets[xparent].rank > sets[yparent].rank) {
			sets[yparent].parent = xparent;
		}
		else {
			sets[xparent].parent = yparent;
			sets[yparent].rank += 1;
		}
	}
}