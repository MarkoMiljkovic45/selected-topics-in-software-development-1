package hr.fer.oprpp1.custom.collections;

public interface List extends Collection {
    /**
     * Returns the object that is stored in list at position index. Valid indexes are 0 to size-1. If index is
     * invalid, IndexOutOfBoundsException is thrown.
     * @param index of wanted element
     * @return element at index index
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than size-1
     */
    Object get(int index);

    /**
     * Inserts (does not overwrite) the given value at the given position in list. Elements starting from
     * this position are shifted one position. The legal positions are 0 to size. If position is invalid,
     * IndexOutOfBoundsException is thrown. The method will refuse to add null as element by throwing the NullPointerException.
     * The average complexity is O(n).
     * @param value to be inserted
     * @param position at which the value will be inserted
     * @throws IndexOutOfBoundsException if position is less than 0 or greater than size
     * @throws NullPointerException if value is null
     */
    void insert(Object value, int position);

    /**
     * Searches the list and returns the index of the first occurrence of the given value or -1 if the value is
     * not found. <code>null</code> is valid argument. The equality should be determined using the equals method.
     * @param value to be searched
     * @return the index of the first occurrence of the given value or -1 if the value is not found.
     */
    int indexOf(Object value);

    /**
     * Removes element at specified index from list. Element that was previously at location index+1
     * after this operation is on location index, etc. Legal indexes are 0 to size-1. In case of invalid index,
     * IndexOutOfBoundsException is thrown
     * @param index of element to be removed
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than size-1
     */
    void remove(int index);
}
