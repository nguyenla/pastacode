import java.util.*;

public class Dijkstra_adjacency_list {
	private long distances[];
	private Set<Integer> visited;
	private PriorityQueue<Node> queue;
	private int numNodes;
	private ArrayList<ArrayList<Edge>> edges; // adjacency matrix representation

	// initialize all fields
	public Dijkstra_adjacency_list(int numNodes) {
		this.numNodes = numNodes;
		distances = new long[numNodes + 1];
		visited = new HashSet<Integer>();
		queue = new PriorityQueue<Node>();
		edges = new ArrayList<ArrayList<Edge>>(); // read graph in
	}

	// Run Dijkstra algorithm
	public void dijkstra_algorithm(ArrayList<ArrayList<Edge>> list, int source) {
		edges = list;
		// initialized distance array
		for (int i = 1; i <= numNodes; i++) {
			distances[i] = Integer.MAX_VALUE;
		}
		distances[source] = 0;
		queue.add(new Node(source, 0));
		
		while (!queue.isEmpty()) {
            int curSource = queue.remove().vertex; // current vertex
            visited.add(curSource);
            ArrayList<Edge> curList = edges.get(curSource);
            for (int i = 0; i < curList.size(); i++) {
                int curVertex = curList.get(i).v;
                if (!visited.contains(curVertex)) { // if the vertex has not been visited
                    long temp = distances[curSource] + curList.get(i).cost;
                    if (temp < distances[curVertex]) {
                        distances[curVertex] = temp;
                        queue.add(new Node(curVertex, temp));
                    }
                }
            }
        }
	}

	public static void main(String... arg) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the number of vertices and the number of edges:");
		int numVertices = s.nextInt();
		int numEdges = s.nextInt();

		Dijkstra_adjacency_list d = new Dijkstra_adjacency_list(numVertices); // create graph
		ArrayList<ArrayList<Edge>> edges = new ArrayList<ArrayList<Edge>>();
		for (int i = 0; i < numEdges + 1; i++) {
			edges.add(new ArrayList<Edge>());
		}
		
		System.out.println("Enter the edges:");
		int a,b;
		long w;
		for (int i = 0; i < numEdges; i++) {
			a = s.nextInt();
			b = s.nextInt();
			w = s.nextLong();
			edges.get(a).add(d.new Edge(b,w));
			edges.get(b).add(d.new Edge(a,w));
		}

		System.out.println("Enter the source ");
		int source = s.nextInt();
		
		d.dijkstra_algorithm(edges, source);
		System.out.println("Shortest paths:");
		for (int i = 1; i < d.distances.length; i++) {
			System.out.println(source + " to " + i + " is " + d.distances[i]);
		}
		
		s.close();
	}	
	
	// The Edge class 
	private class Edge {
	    int v;
	    long cost;
	    
	    public Edge(int v, long c) {
	    	this.v = v;
	        cost = c;
	    }
	}
	
	// The Node class is made comparable
	private class Node implements Comparable<Node> {
		public int vertex;
		public long cost;
		
		public Node(int vertex, long cost) {
			this.vertex = vertex;
			this.cost = cost;
		}

		public int compareTo(Node node2) {
			if (this.cost < node2.cost)
				return -1;
			if (this.cost > node2.cost)
				return 1;
			return 0;
		}
	}
}