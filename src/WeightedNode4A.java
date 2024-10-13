
public class WeightedNode4A {

	private int val;
	private WeightedNode4A next;
	private Float weight;

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public WeightedNode4A(int val, WeightedNode4A next, Float weight) {
		this.val = val;
		this.next = next;
		this.weight = weight;
	}

	public void setNext(WeightedNode4A w) {
		this.next = w;
	}

	public WeightedNode4A getNext() {
		return (this.next);
	}

	public void setVal(int w) {
		this.val = w;
	}

	public int getVal() {
		return (this.val);
	}

}
