import java.io.File;
import java.util.Scanner;
//import java.io.FileNotFoundException;

public class Main4A {

	static public int InputTypeG;
	static public int OutputTypeGt;
	static public GraphM4A graphM;
	static public GraphL4A graphL;
	static public int vertices[];
	
	public static void main(String args[]) {

		try {
			Scanner sc0; // Read user InPut
			do {
				System.out.println("Choose The Input Graph Type: (0: adjMatrix / 1: adjList)");
				sc0 = new Scanner(System.in);
				InputTypeG = sc0.nextInt();
			} while (InputTypeG != 0 && InputTypeG != 1);

			do {
				System.out.println("Choose The Output transposeGraph Type:(0: trsMatrix / 1: trsList)");
				sc0 = new Scanner(System.in);
				OutputTypeGt = sc0.nextInt();
			} while (OutputTypeGt != 0 && OutputTypeGt != 1);
			sc0.close();

			System.out.println("#----------------------------------------------------------------#\n");
			File file = new File(args[0]);
			Scanner sc = new Scanner(file);
			
			// EXERCICE 1 TP1 ==============================================================================
			// If we choose the representation of G by adjacency matrix
			System.out.println("\n-> Exercice 1 ===========================================================");
			if (InputTypeG == 0) { // Graph Input -> Matrix
				System.out.println("\n-> You Choosed The Adjacency Matrix :---------------------------");
				graphM = new GraphM4A(sc);

				if (graphM.getType() == 0) { // UnDirected Graph
					int[] degree = graphM.degree();
					System.out.println("\n-> (Matrix) Degrees for vertices from 1 to " + degree.length
							+ " for the given undirected graph");
					Tools4A.printArray(degree);
				} else { // directed
					TwoArrays4A pair = graphM.degrees();
					int[] indegree = pair.in(); // The result of graphM.degrees() is a pair of arrays, InDegree and
					int[] outdegree = pair.out(); // OutDegree
					System.out.println("\n-> (Matrix)Indegrees for vertices from 1 to " + indegree.length
							+ " for the given digraph");
					Tools4A.printArray(indegree);
					System.out.println("-> (Matrix)Outdegrees for vertices from 1 to " + indegree.length
							+ " for the given digraph");
					Tools4A.printArray(outdegree);
				}

				System.out.println("\n-> Graph adjacency Matrix :--------------------------");
				float AdG[][] = graphM.getAdjMat();
				graphM.DisplayMat(AdG);

				if (OutputTypeGt == 0) { // Transpose Graph Matrix
					System.out.println("\n-> Graph transpose Matrix :-------------------------");
					float trsG[][] = graphM.getTrsMat();
					graphM.DisplayMat(trsG);
				} else if (OutputTypeGt == 1) { // Transpose Graph List
					if (graphM.getWeighted() == 0) {
						System.out.println("\n-> Unweighted Graph transpose List :-------------------------");
						graphM.MatrixToListUnweighted(); // Exercice 1 TP1
					} else if (graphM.getWeighted() == 1) {
						System.out.println("\n-> Weighted Graph transpose List :-------------------------");
						graphM.MatrixToListWeighted(); // Exercice 1 TP1
					}
				}

			}

			// If we choose the representation of G by adjacency lists
			if (InputTypeG == 1) { // Graph Input -> List
				System.out.println("\nYou Choosed The Adjacency List :");
				sc = new Scanner(file);
				graphL = new GraphL4A(sc);
				graphL.setDirected(graphL.getType());
				if (graphL.getType() == 0 && graphL.getWeighted() == 0) { // UnDirected and UnWeighted
					int[] degree = graphL.degree();
					System.out.println("(List) Degrees for vertices from 1 to " + degree.length
							+ " for the given undirected graph");
					Tools4A.printArray(degree);
				}
				if (graphL.getType() == 0 && graphL.getWeighted() == 1) { // UnDirected and Weighted
					int[] degree = graphL.degreeW();
					System.out.println("(List) Degrees for vertices from 1 to " + degree.length
							+ " for the given undirected graph");
					Tools4A.printArray(degree);
				}
				if (graphL.getType() == 1 && graphL.getWeighted() == 0) { // Directed and UnWeighted
					TwoArrays4A pair = graphL.degrees();
					int[] indegree = pair.in();
					int[] outdegree = pair.out();
					System.out.println(
							"(List) Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");
					Tools4A.printArray(indegree);
					System.out.println(
							"(List) Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");
					Tools4A.printArray(outdegree);
				}
				if (graphL.getType() == 1 && graphL.getWeighted() == 1) { // Directed and UnWeighted
					TwoArrays4A pair = graphL.degreesW();
					int[] indegree = pair.in();
					int[] outdegree = pair.out();
					System.out.println(
							"(List)Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");
					Tools4A.printArray(indegree);
					System.out.println(
							"(List)Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");
					Tools4A.printArray(outdegree);
				}
				// Display G
				System.out.println("\nGraph Adjacency List -------------------------");
				Node4A[] AdjList = graphL.getAdjlist();
				graphL.DisplayAdjList(AdjList);

				if (OutputTypeGt == 1) { // Transpose Graph -> List
					if (graphL.getWeighted() == 0) { // Weighted Graph
						Node4A[] TrsList = graphL.getTrslist();
						System.out.println("\nGraph Transpose List -------------------------");
						graphL.DisplayAdjList(TrsList);
					} else { // UnWeighted Graph
						WeightedNode4A[] TrsList = graphL.getTrslistW();
						System.out.println("\nGraph Transpose Weighted List -------------------------");
						graphL.DisplayAdjListW(TrsList);
					}
				} else if (OutputTypeGt == 0) { // Transpose Graph -> Matrix

					float Mat[][] = graphL.ListToMatrix();
					graphL.Display_List_Mat(Mat);
				}
			}
			
			// EXERCICE 2 TP1 ==============================================================================
			System.out.println("\n-> Exercice 2 ===========================================================");
			
			if(InputTypeG == 0) { // InPut Graph Matrix 
				int n = graphM.getN();
				vertices = new int[n];
				for (int i = 0; i < n; i++) {
				    vertices[i] = i + 1;
				}
				if(graphM.PathCheckMatrix(vertices)) {
					System.out.println("\n Path Exists From v1v2, ..... to ,v"+(n-1)+"v"+n);
				}
				else {
					System.out.println("\n No Path From v1v2, ..... to ,v"+(n-1)+"v"+n);
				}
			}
			
			else if (InputTypeG == 1) { // InPut Graph List
				int n = graphL.getN();
				vertices = new int[n];
				for (int i = 0; i < n; i++) {
				    vertices[i] = i + 1;
				}
				if(graphL.PathCheckList(vertices)) {
					System.out.println("\n- Path Exists From v1v2, ..... to ,v"+(n-1)+"v"+n);
				}
				else {
					System.out.println("\n- No Path From v1v2, ..... to ,v"+(n-1)+"v"+n);
				}
			}
			sc.close();
			
			//Exercice 1 TP2 =================================================================
			System.out.println("\n=> Exerice 1 TP2 ==========================================\n");
			graphL.DFSNum(2);
			
			//Exercice 2 & 3 TP2 =================================================================
			System.out.println("\n=> Exerice 2 & 3 TP2 ==========================================\n");
			graphL.isHasCycle();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
