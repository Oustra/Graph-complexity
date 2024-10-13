
public class Node4A {

	private int val;
	private Node4A next;

	public Node4A(int val, Node4A next) {
		this.val = val;
		this.next = next;
	}

	public void setNext(Node4A w) {
		this.next = w;
	}

	public Node4A getNext() {
		return (this.next);
	}

	public void setVal(int w) {
		this.val = w;
	}

	public int getVal() {
		return (this.val);
	}

}
