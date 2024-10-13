
import java.util.Scanner;

public class GraphM4A {

	private int n;
	private int type; // 0 if UnDirected, 1 if Directed
	private int weighted; // 0 if UnWeighted, 1 if Weighted
	private float[][] adjmat;
	private float[][] trsmat;

	public GraphM4A(Scanner sc) {
		String[] firstline = sc.nextLine().split(" ");
		this.n = Integer.parseInt(firstline[0]);
		System.out.println("Number of vertices " + this.n);
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

		this.adjmat = new float[this.n][this.n];
		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.n; j++)
				adjmat[i][j] = 0; // replace 0 with something else if the weights can be 0

		for (int k = 0; k < this.n; k++) {
			String[] line = sc.nextLine().split(" : ");
			int i = Integer.parseInt(line[0]); // the vertex "source"
			if (weighted == 0) {
				if ((line.length > 1) && (line[1].charAt(0) != ' ')) { // modif ici
					String[] successors = line[1].split(", ");
					// System.out.println(i+ " "+ successors.length);
					for (int h = 0; h < successors.length; h++) {
						// System.out.println(Integer.parseInt(successors[h]));
						this.adjmat[i - 1][Integer.parseInt(successors[h]) - 1] = 1;
					}
				}
			} else {
				line = line[1].split(" // ");
				if ((line.length == 2) && (line[1].charAt(0) != ' ')) {// if there really are somme successors, then we
																		// must have something different from " " after
																		// "// "
					String[] successors = line[0].split(", ");
					String[] theirweights = line[1].split(", ");
					for (int h = 0; h < successors.length; h++)
						this.adjmat[i - 1][Integer.parseInt(successors[h]) - 1] = Float.parseFloat(theirweights[h]);
				}
			}
		}

	}

//method to be applied only when type=0
	public int[] degree() {
		int[] tmp = new int[this.n];
		for (int i = 0; i < this.n; i++)
			tmp[i] = 0;
		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.n; j++)
				if (this.adjmat[i][j] != 0)
					tmp[i] = tmp[i] + 1;
		return tmp;

	}

//method to be applied only when type=1
	public TwoArrays4A degrees() {
		int[] tmp1 = new int[this.n]; // indegrees
		int[] tmp2 = new int[this.n]; // outdegrees
		for (int i = 0; i < this.n; i++) {
			tmp1[i] = 0;
			tmp2[i] = 0;
		}
		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.n; j++)
				if (this.adjmat[i][j] != 0) {
					tmp2[i] = tmp2[i] + 1;
					tmp1[j] = tmp1[j] + 1;
				}
		return (new TwoArrays4A(tmp1, tmp2));

	}

	public int getType() {
		return this.type;
	}

	public float[][] getAdjMat() {
		return this.adjmat;
	}

	public float[][] getTrsMat() {
		GraphTrsMat();
		return this.trsmat;
	}

	public void GraphTrsMat() { //Exercice 1 TP1 (Create Transpose Matrix From Matrix)
							   //Worst case : O(n^2)
		// create Transpose matrix
		this.trsmat = new float[this.n][this.n];
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				trsmat[j][i] = adjmat[i][j];
			}
		}
	}

	public void DisplayMat(float[][] Mat) { //Exercice 1 TP1 (Display Matrix)
										   //Worst case : O(n^2)
		for (int i = 0; i < Mat.length; i++) {

			// Loop through all elements of current row
			for (int j = 0; j < Mat[i].length; j++)
				System.out.print((int) (Mat[i][j]) + " ");

			System.out.print("\n");
		}

	}

	public void MatrixToListUnweighted() { //Exercice 1 TP1 (Create Transpose List from Adjacency Matrix)
										  //Worst case : O(n^2)
		Node4A[] adjList = new Node4A[this.n];

		for (int i = 0; i < this.n; i++) {
			Node4A currentNode = null;
			for (int j = this.n - 1; j >= 0; j--) {
				if (this.adjmat[i][j] != 0) { // There's an edge from i to j
					Node4A newNode = new Node4A(j + 1, currentNode);
					currentNode = newNode;
				}
			}
			adjList[i] = currentNode; // Set the head of the list for node i
		}

		if (this.weighted == 0) {
			for (int i = 0; i < adjList.length; i++) {
				System.out.print(i + 1 + ": ");
				Node4A current = adjList[i];
				while (current != null) {
					System.out.print(current.getVal() + "->");
					current = current.getNext();
				}
				System.out.println();
			}
		}
	}

	public void MatrixToListWeighted() { //Exercice 1 TP1 (Create Transpose Weighted List from Adjacency Matrix) 
										//Worst case : O(n^2)
		WeightedNode4A[] adjListW = new WeightedNode4A[this.n];

		for (int i = 0; i < this.n; i++) {
			WeightedNode4A currentNode = null;
			for (int j = this.n - 1; j >= 0; j--) {
				if (this.adjmat[i][j] != 0) { // There's an edge with weight from i to j
					WeightedNode4A newNode = new WeightedNode4A(j + 1, currentNode, this.adjmat[i][j]);
					currentNode = newNode;
				}
			}
			adjListW[i] = currentNode; // Set the head of the list for node i
		}

		if (this.weighted == 1) {
			for (int i = 0; i < adjListW.length; i++) {
				System.out.print(i + 1 + ": ");
				WeightedNode4A current = adjListW[i];
				while (current != null) {
					System.out.print("(" + current.getVal() + "//" + current.getWeight() + ")" + " -> ");
					current = current.getNext();
				}
				System.out.println();
			}
		}
	}

	public int getWeighted() {
		return weighted;
	}
	
	public int getN() {
		return n;
	}
	
	public boolean PathCheckMatrix(int[] vertices) { // Exercice 2 TP1 (Check Path from v1 to vn)
													//Worst case : O(n)
	    for (int i = 0; i < vertices.length - 1; i++) {
	        int from = vertices[i] - 1;
	        int to = vertices[i + 1] - 1;
	        
	        if (this.adjmat[from][to] == 0) {
	            return false;
	        }
	    }
	    return true;
	}

}