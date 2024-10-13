
public class TwoArrays4A {

	private int[] indegree;
	private int[] outdegree;

	public TwoArrays4A(int[] tab1, int[] tab2) {
		indegree = tab1;
		outdegree = tab2;
	}

	public int[] in() {
		return indegree;
	}

	public int[] out() {
		return outdegree;
	}

}
