package java.solver;

/**
 * @author Gordon Pham-Nguyen
 * @ID 40018402
 *
 *     Assignment 2 programming question
 *
 *     COMP 352-S Winter 2019
 *
 *     ArrayList class to use with Stack class and in assignment
 *
 */
public class MyArrayList<E> {
	public int size = 0;
	public E[] elements;
	public static final int INITIAL_CAPACITY = 10;

	public int arraySize() {
		return elements.length;
	}

	// default constructor
	public MyArrayList() {
		elements = (E[]) new Object[INITIAL_CAPACITY];
	}

	// parameterized constructor
	public MyArrayList(int capacity) {
		elements = (E[]) new Object[capacity];
	}

	/**
	 * @param element
	 * @return successful or not
	 *
	 *         add element at the end
	 */
	public boolean add(E element) {
		checkToIncreaseSize();

		elements[size] = element;
		size++;

		return true;
	}

	/**
	 * @param index
	 * @param element
	 *
	 *                add an element to a specified index
	 */
	public void add(int index, E element) {
		checkToIncreaseSize();

		if ((index > size) || (index < 0)) {
			System.out.println("You cannot add an element in this index.");
		} else {
			for (int i = size - 1; i >= index; i--) {
				elements[i + 1] = elements[i];
			}

			elements[index] = element;
			size++;
		}
	}

	/**
	 * delete all elements
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}

		size = 0;
	}

	/**
	 * @param index
	 * @return element removed remove element at a specified index
	 */
	public E remove(int index) {
		if ((index >= size) || (index < 0)) {
			System.out.println("You cannot remove an element at this index.");

			return null;
		}

		E temp = elements[index];
		elements[index] = null;
		// after removing the element, shift all the other elements
		for (int i = index; i <= (size - 2); i++) {
			elements[i] = elements[i + 1];
		}

		elements[size - 1] = null;
		size--;

		checkToDecreaseSize();

		return temp;
	}

	/**
	 * @param o element to search and remove
	 * @return successful or not
	 *
	 *         remove specific element without knowing the index
	 */
	public boolean remove(Object o) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(o)) {
				remove(i);
				checkToDecreaseSize();

				return true;
			}
		}

		return false;
	}

	/**
	 * increase size of array if not big enough
	 */
	public void checkToIncreaseSize() {
		if (size == elements.length) {
			E[] y = (E[]) new Object[elements.length * 2];

			for (int i = 0; i < elements.length; i++) {
				y[i] = elements[i];
			}

			elements = y;
		}
	}

	/**
	 * decrease size of array if too large
	 */
	public void checkToDecreaseSize() {
		if (size < (elements.length / 4)) {
			E[] y = (E[]) new Object[elements.length / 2];

			for (int i = 0; i < size; i++) {
				y[i] = elements[i];
			}

			elements = y;
		}
	}

	/**
	 * @return size of array
	 */
	public int size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < elements.length; i++) {
			temp.append(elements[i]);
			temp.append(" ");
		}

		return temp.toString();
	}

	/**
	 * @return if empty or not
	 */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}

		return false;
	}

	/**
	 * @return the element removed at the end
	 *
	 *         remove the element at the end
	 */
	public E removeEnd() {
		if (size == 0) {
			System.out.println("There is no element to remove at this index");
			return null;
		}

		E temp = elements[size - 1];
		elements[size - 1] = null;
		size--;

		checkToDecreaseSize();

		return temp;
	}

	/**
	 * @param index
	 * @return the element at the index
	 *
	 *         see the element at the index
	 */
	public E viewElement(int index) {
		if ((index >= size) || (index < 0)) {
			System.out.println("You cannot view an element at this index.");

			return null;
		}

		return elements[index];
	}
}