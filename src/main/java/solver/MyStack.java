package java.solver;

/**
 * @author Gordon Pham-Nguyen
 * @ID 40018402
 *
 *     Assignment 2 programming question
 *
 *     COMP 352-S Winter 2019
 *
 *     Stack class to use for assignment
 *
 */
public class MyStack<G> {
	MyArrayList<G> stack;

	// default constructor
	public MyStack() {
		stack = new MyArrayList<>();
	}

	/**
	 * @return size of stack
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * @return if stack empty or not
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * @param g element to add
	 * @return add element to top of stack
	 */
	public boolean push(G g) {
		return stack.add(g);
	}

	/**
	 * @return remove the last element that entered the stack
	 */
	public G pop() {
		return stack.removeEnd();
	}

	/**
	 * @return the last element that entered the stack
	 */
	public G top() {
		return stack.viewElement(size() - 1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return stack + "";
	}
}