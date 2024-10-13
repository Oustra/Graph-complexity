
import java.util.Scanner;
import java.util.Stack;

public class GraphL4A {

	private int n;
	private int type; // 0 if undirected, 1 if directed
	private int weighted; // 0 if unweighted, 1 otherwise
	private int directed;
	private WeightedNode4A[] adjlistW; // one of adjlistW and adjlist is null, depending on the type of the graph
	private WeightedNode4A[] trslistW;
	private Node4A[] adjlist;
	private Node4A[] trslist;
	private boolean[] visited;   // to track visited vertices
    private int[] discovery;     // discovery times of vertices
    private int[] finish;        // finish times of vertices
    private Stack<Integer> pathStack;
    private int time;
    private boolean hasCycle;

	public GraphL4A(Scanner sc) {
		
		String[] firstline = sc.nextLine().split(" ");
		this.n = Integer.parseInt(firstline[0]);
		this.visited = new boolean[n];
        this.discovery = new int[n];
        this.finish = new int[n];
        this.time = 0;
        this.hasCycle = false;
        this.pathStack = new Stack<>();
        
		System.out.println(this.n);
		if (firstline[1].equals("undirected"))
			this.type = 0;
		else
			this.type = 1;
		System.out.println("Type= " + this.type);
		if (firstline[2].equals("unweighted"))
			this.weighted = 0;
		else
			this.weighted = 1;
		System.out.println("Weighted= " + this.weighted);
		if (this.weighted == 0) {
			this.adjlist = new Node4A[this.n];
			for (int i = 0; i < this.n; i++)
				adjlist[i] = null;
			adjlistW = null;
		} else {
			this.adjlistW = new WeightedNode4A[this.n];
			for (int i = 0; i < this.n; i++)
				adjlistW[i] = null;
			adjlist = null;
		}

		for (int k = 0; k < this.n; k++) {
			String[] line = sc.nextLine().split(" : ");
			int i = Integer.parseInt(line[0]); // the vertex "source"
			if (weighted == 0) {
				if ((line.length > 1) && (line[1].charAt(0) != ' ')) {
					String[] successors = line[1].split(", ");
					// System.out.println(i+ " "+ successors.length);
					for (int h = 0; h < successors.length; h++) {
						Node4A node = new Node4A(Integer.parseInt(successors[h]), null);
						node.setNext(adjlist[i - 1]);
						adjlist[i - 1] = node;
					}
				}
			} else {
				line = line[1].split(" // ");
				if ((line.length == 2) && (line[1].charAt(0) != ' ')) {// if there really are somme successors, then we
																		// must have something different from " " after
																		// "// "
					String[] successors = line[0].split(", ");
					String[] theirweights = line[1].split(", ");
					for (int h = 0; h < successors.length; h++) {
						WeightedNode4A nodeW = new WeightedNode4A(Integer.parseInt(successors[h]), null,
								Float.parseFloat(theirweights[h]));
						nodeW.setNext(adjlistW[i - 1]);
						adjlistW[i - 1] = nodeW;
					}

				}
			}
		}
        
	}

	// method to be applied only when type=0 and weighted=0
	public int[] degree() {
		int[] tmp = new int[this.n];
		for (int i = 0; i < this.n; i++)
			tmp[i] = 0;
		for (int i = 0; i < this.n; i++) {
			Node4A p = adjlist[i];
			while (p != null) {
				tmp[i] = tmp[i] + 1;
				p = p.getNext();
			}
		}
		return (tmp);
	}

	// method to be applied only when type=0 and weighted=1
	public int[] degreeW() {
		int[] tmp = new int[this.n];
		for (int i = 0; i < this.n; i++)
			tmp[i] = 0;
		for (int i = 0; i < this.n; i++) {
			WeightedNode4A p = adjlistW[i];
			while (p != null) {
				tmp[i] = tmp[i] + 1;
				p = p.getNext();
			}
		}
		return (tmp);
	}

	// method to be applied only when type=1 and weighted=0
	public TwoArrays4A degrees() {
		int[] tmp1 = new int[this.n]; // indegrees
		int[] tmp2 = new int[this.n]; // outdegrees
		for (int i = 0; i < this.n; i++) {
			tmp1[i] = 0;
			tmp2[i] = 0;
		}
		for (int i = 0; i < this.n; i++) {
			Node4A p = adjlist[i];
			while (p != null) {
				tmp2[i]++;
				tmp1[p.getVal() - 1]++;
				p = p.getNext();
			}
		}
		return (new TwoArrays4A(tmp1, tmp2));
	}

	// method to be applied only when type=1 and weighted=1
	public TwoArrays4A degreesW() {
		int[] tmp1 = new int[this.n]; // indegrees
		int[] tmp2 = new int[this.n]; // outdegrees
		for (int i = 0; i < this.n; i++) {
			tmp1[i] = 0;
			tmp2[i] = 0;
		}
		for (int i = 0; i < this.n; i++) {
			WeightedNode4A p = adjlistW[i];
			while (p != null) {
				tmp2[i]++;
				tmp1[p.getVal() - 1]++;
				p = p.getNext();
			}
		}
		return (new TwoArrays4A(tmp1, tmp2));
	}

	public Node4A[] getAdjlist() {
		return adjlist;
	}

	public void GraphTrsList() { //Exercice 1 TP1 (Create Transpose List from List)
							    //Worst case : O(n+m)
		if (this.weighted == 0) { // UnWeighted Graph
			this.trslist = new Node4A[adjlist.length];
			for (int i = 0; i < this.adjlist.length; i++) {
				Node4A currentNode = this.adjlist[i]; // Adjacency List
				while (currentNode != null) {

					int trsIndice = currentNode.getVal();
					trslist[trsIndice - 1] = new Node4A(i + 1, trslist[trsIndice - 1]);
					currentNode = currentNode.getNext();
				}
			}

		} else { // Weighted Graph
			this.trslistW = new WeightedNode4A[adjlistW.length];
			for (int i = 0; i < this.adjlistW.length; i++) {
				WeightedNode4A currentNode = this.adjlistW[i]; // Adjacency List
				while (currentNode != null) {

					int trsIndice = currentNode.getVal();
					trslistW[trsIndice - 1] = new WeightedNode4A(i + 1, trslistW[trsIndice - 1],
							currentNode.getWeight());
					currentNode = currentNode.getNext();
				}
			}
		}
	}

	public void DisplayAdjList(Node4A[] listt) { //Exercice 1 TP1 (Display List)
	    										//Worst case : O(n+m)
		if (this.weighted == 0) {
			Node4A[] list = listt;
			for (int i = 0; i < list.length; i++) {
				System.out.print(i + 1 + ": ");
				Node4A current = list[i];
				while (current != null) {
					System.out.print(current.getVal() + "->");
					current = current.getNext();
				}
				System.out.println();
			}
		}
	}

	public void DisplayAdjListW(WeightedNode4A[] listW) { //Exercice 1 TP1 (Display Weighted List)
	    												 //Worst case : O(n+m)
		if (this.weighted == 1) {
			WeightedNode4A[] list = listW;
			for (int i = 0; i < list.length; i++) {
				System.out.print(i + 1 + ": ");
				WeightedNode4A current = list[i];
				while (current != null) {
					System.out.print("(" + current.getVal() + "//" + current.getWeight() + ")" + " -> ");
					current = current.getNext();
				}
				System.out.println();
			}
		}
	}

	public float[][] ListToMatrix() { //Exercice 1 TP1 (Create Transpose Matrix From List) 
									 //Worst case : O(n+m)
	
		float[][] adjMatrix = new float[this.n][this.n];

		if (this.weighted == 0) { // Unweighted graph
			System.out.println("\nTranspose UnWeighted Matrix -------------------------");
			for (int i = 0; i < this.n; i++) {
				Node4A currentNode = adjlist[i];
				while (currentNode != null) {
					adjMatrix[i][currentNode.getVal() - 1] = 1; // Set 1 for edge presence
					currentNode = currentNode.getNext();
				}
			}
		} else { // Weighted graph
			System.out.println("\nTranspose Weighted Matrix -------------------------");
			for (int i = 0; i < this.n; i++) {
				WeightedNode4A currentNode = adjlistW[i];
				while (currentNode != null) {
					adjMatrix[i][currentNode.getVal() - 1] = currentNode.getWeight(); // Set weight value
					currentNode = currentNode.getNext();
				}
			}
		}

		return adjMatrix;
	}

	public void Display_List_Mat(float[][] Mat) { //Display Matrix
		 										 //Worst case : O(n^2)

		for (int i = 0; i < Mat.length; i++) {

			// Loop through all elements of current row
			for (int j = 0; j < Mat[i].length; j++)
				System.out.print((int) (Mat[i][j]) + " ");

			System.out.print("\n");
		}

	}
	
	public boolean PathCheckList(int[] vertices) { // Exercice 2 TP1 (Check Path from v1 to vn)
												  //Worst case : O(n * d_max) Max Degree
	    for (int i = 0; i < vertices.length - 1; i++) {
	        int from = vertices[i] - 1;
	        int to = vertices[i + 1];
	        boolean edgeExists = false;

	        // Traverse the linked list for 'from' vertex to find 'to' vertex
	        Node4A current = this.adjlist[from];
	        while (current != null) {
	            if (current.getVal() == to) {
	                edgeExists = true;
	                break;
	            }
	            current = current.getNext();
	        }

	        if (!edgeExists) {
	            return false; // No edge between consecutive vertices
	        }
	    }
	    return true; // All consecutive edges found
	}
	
    public void DFSNum(int s) {
    	System.out.println("=> Performing DFS NUM .........");
    	System.out.println("-----------------------------------------");
        time = 1;
        DFSnum(s-1);
    }

    private void DFSnum(int u) { //Worst case : O(n * d_max) Max Degree 
        visited[u] = true;
        discovery[u] = time++;
        System.out.println("- Vertex " + (u+1) + " discovered at time " + discovery[u]);
        pathStack.push(u); 
        Node4A node = this.adjlist[u];
        while (node != null) {
            int v = node.getVal()-1;
            if (!visited[v]) {
                System.out.println("=> Tree Edge: (" + (u+1) + ", " + (v+1) + ")");
                System.out.println("-----------------------------------------");
                DFSnum(v);
            } else if (discovery[u] < discovery[v]) {
            	 if (this.directed == 1) {
                     System.out.println("=> Forward Edge: (" + (u + 1) + ", " + (v + 1) + ")");
                 } else {
                     System.out.println("=> Cross Edge (undirected): (" + (u + 1) + ", " + (v + 1) + ")");
                 }
                System.out.println("-----------------------------------------");
            } else if (finish[v] == 0) {
                System.out.println("=> Back Edge: (" + (u+1) + ", " + (v+1) + ")");
                hasCycle = true;
                System.out.print("=> Cycle detected with vertices: ");
                
                for (int i = pathStack.size() - 1; i >= 0; i--) {
                    int vertexInPath = pathStack.get(i);
                    System.out.print((vertexInPath + 1) + "->");
                    if (vertexInPath == v) {
                        break;
                    }
                }
                System.out.println("\n-----------------------------------------");
            }else {
            	System.out.println("=> Cross Edge: (" + (u+1) + ", " + (v+1) + ")");
            }
            node = node.getNext();
        }
        
        // Setting the finish time once we're done with all adjacent vertices
        finish[u] = time++;
        System.out.println("- Vertex " + (u+1) + " finished at time " + finish[u]);
        pathStack.pop();
    }

	public Node4A[] getTrslist() {
		GraphTrsList();
		return trslist;
	}

	public WeightedNode4A[] getTrslistW() {
		GraphTrsList();
		return trslistW;
	}

	public WeightedNode4A[] getAdjlistW() {
		return adjlistW;
	}

	public int getType() {
		return this.type;
	}

	public int getWeighted() {
		return this.weighted;
	}
	
	public int getN() {
		return n;
	}
	
	public boolean isHasCycle() {
		System.out.println("-> This Graph containes a Cycle");
		return hasCycle;
	}

	public int isDirected() {
		return directed;
	}

	public void setDirected(int i) {
		this.directed = i;
	}
	

}
