package hr.fer.oprpp1.custom.collections;

/**
 * A stack-like collection implemented using the ArrayIndexedCollection
 */
public class ObjectStack {

    /**
     * internalArray - a collection used for storing elements
     */
    private final ArrayIndexedCollection internalArray;

    public ObjectStack() {
        internalArray = new ArrayIndexedCollection();
    }

    /**
     * Tests if the stack is empty
     * @return true if stack is empty, true otherwise
     */
    public boolean isEmpty() {
        return internalArray.isEmpty();
    }

    /**
     * Returns the number of elements currently on the stack
     * @return number of elements on the stack
     */
    public int size() {
        return internalArray.size();
    }

    /**
     * Pushes given value on the stack. null value is not allowed to be
     * placed on the stack.
     * @param value to be added to the stack
     * @throws NullPointerException if value is null
     */
    public void push(Object value) {
        internalArray.add(value);
    }

    /**
     * Removes the last value pushed on to the stack from the stack and returns it. If the stack is empty when
     * method pop is called, the method will throw an EmptyStackException.
     * @return the last element placed on the stack
     * @throws EmptyStackException if pop is called when the stack is empty
     */
    public Object pop() {
        if (isEmpty()) throw new EmptyStackException("Can't pop from empty stack!");
        Object top = internalArray.get(internalArray.size() - 1);
        internalArray.remove(internalArray.size() - 1);
        return top;
    }

    /**
     *  Returns the last element placed on stack but does not delete it from stack.
     *  If the stack is empty when peek is called, the method will throw EmptyStackException
     * @return the last element placed on the stack
     * @throws EmptyStackException if peek is called when the stack is empty
     */
    public Object peek() {
        if (isEmpty()) throw new EmptyStackException("Can't peek on empty stack!");
        return internalArray.get(internalArray.size() - 1);
    }

    /**
     * Removes all elements from stack
     */
    public void clear() {
        internalArray.clear();
    }
}
