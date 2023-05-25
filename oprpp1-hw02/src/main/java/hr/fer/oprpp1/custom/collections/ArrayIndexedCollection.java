package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A resizable array-backed collection of objects.
 * Duplicate elements are allowed, storage of null references is not allowed.
 */
public class ArrayIndexedCollection implements List {

    /**
     * size - the number of elements currently in the collection
     */
    private int size;
    /**
     * element - an array of objects used for storing elements
     */
    private Object[] elements;
    /**
     * modificationCount - number of element modifications (adding/removing/swapping)
     */
    private long modificationCount;

    /**
     * The default constructor.
     * Creates an empty ArrayIndexedCollection of size 16
     */
    public ArrayIndexedCollection() {
        this(16);
    }

    /**
     * Creates an empty ArrayIndexCollection of size initialCapacity
     * @param initialCapacity the desired size of the ArrayIndexCollector
     * @throws IllegalArgumentException if initialCapacity is less than 1
     */
    public ArrayIndexedCollection(int initialCapacity) {
        this(initialCapacity, null, false);
    }

    /**
     * Constructs an ArrayIndexCollection by copying elements from the given collection
     * @param other the collection to be copied
     * @throws NullPointerException if other is null
     */
    public ArrayIndexedCollection(Collection other) {
        this(-1, other, true);
    }

    /**
     * Constructs an ArrayIndexCollection by copying elements from the given collection of size initialCapacity
     * If the other collection is bigger than initialCapacity than the created ArrayIndexCollection is the same size as the other collection
     * @param other the collection to be copied
     * @param initialCapacity the desired size of the ArrayIndexCollection
     * @throws NullPointerException if other is null
     */
    public ArrayIndexedCollection(Collection other, int initialCapacity) {
        this(initialCapacity, other, true);
    }

    /**
     * A helper constructor to delegate work from other constructors to
     * @param initialCapacity the desired size of the ArrayIndexedCollection
     * @param other the collection to be copied if passed
     * @param otherCollectionPassed true if other is passed, false otherwise
     */
    private ArrayIndexedCollection(int initialCapacity, Collection other, boolean otherCollectionPassed) {
        if (initialCapacity < 1 && !otherCollectionPassed) throw new IllegalArgumentException("Array Index Collection capacity can't be less than 1!");
        if (other == null && otherCollectionPassed) throw new NullPointerException("The other collection can't be null");

        if (otherCollectionPassed) initialCapacity = Math.max(initialCapacity, other.size());

        if(otherCollectionPassed && initialCapacity == 0) initialCapacity = 16;

        this.size = 0;
        this.elements = new Object[initialCapacity];
        this.modificationCount = 0;

        if (otherCollectionPassed) addAll(other);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    /**
     * Adds the given object into this collection. Reference is added into first empty place in the elements array.
     * If the elements array is full, it will be reallocated by doubling its size. The method will refuse to
     * add null as element by throwing the NullPointerException.
     * Average complexity is O(1).
     * @param value value to be added.
     * @throws NullPointerException if value is null
     */
    @Override
    public void add(Object value) {
        if (value == null) throw new NullPointerException("Storage of null references in ArrayIndexedCollection is not allowed.");

        if (this.elements.length == this.size) {
            this.elements = Arrays.copyOf(this.elements, this.elements.length * 2);
        }
        this.elements[this.size++] = value;
        this.modificationCount++;
    }

    @Override
    public boolean contains(Object value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(this.elements[i], value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object value) {
        int i = 0;
        while(i < this.size && !Objects.equals(value, this.elements[i])) i++;

        if (i < this.size) {
            for (int j = i; j < this.size - 1; j++) {
                this.elements[j] = this.elements[j+1];
            }
            this.elements[this.size - 1] = null;
            this.size--;
            this.modificationCount++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] objArr = new Object[this.size];

        System.arraycopy(this.elements, 0, objArr, 0, this.size);

        return objArr;
    }

    /**
     * Returns the object that is stored in backing array at position index. Valid indexes are 0 to size-1. If index
     * is invalid throws IndexOutOfBoundsException. Average complexity is O(n).
     * @param index of the desired element
     * @return object at desired index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    @Override
    public Object get(int index) {
        return this.elements[index];
    }

    /**
     * Removes all elements from the collection. The allocated array is left at current capacity.
     */
    @Override
    public void clear() {
        int arrayLength = this.elements.length;
        for (int i = 0; i < arrayLength; i++) {
            this.elements[i] = null;
        }
        this.size = 0;
        this.modificationCount++;
    }

    /**
     * Inserts the given value at the given position in array. Before the actual insertion elements at position
     * and at greater positions must be shifted one place toward the end, so that an empty place is created at
     * position. The legal positions are 0 to size (both are included). If position is invalid,
     * IndexOutOfBoundsException is thrown. If the elements array is full it is reallocated by doubling its size.
     * The method will refuse to add null as element by throwing the NullPointerException.
     * The average complexity is O(n).
     * @param value to be inserted
     * @param position at which value will be inserted
     * @throws IndexOutOfBoundsException if position is less than 0 or greater than size
     * @throws NullPointerException if value is null
     */
    @Override
    public void insert(Object value, int position) {
        if(value == null) throw new NullPointerException("Value can't be null!");
        if(position < 0 || position > this.size) throw new IndexOutOfBoundsException("Position is out of bounds!");

        if(position == this.size) {
            add(value);
        } else {
            if (this.size == this.elements.length) {
                this.elements = Arrays.copyOf(this.elements, this.elements.length * 2);
            }

            this.size++;
            this.modificationCount++;

            Object tmp1 = this.elements[position];
            Object tmp2;
            this.elements[position] = value;

             for (int i = position + 1; i < size; i++) {
                 tmp2 = this.elements[i];
                 this.elements[i] = tmp1;
                 tmp1 = tmp2;
             }
        }
    }

    /**
     * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
     * not found. Argument can be null and the result must be that this element is not found (since the collection
     * can not contain null). The equality is determined using the equals method.
     * The average time complexity is O(n).
     * @param value to be searched
     * @return index of value or -1 if value is not found
     */
    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(this.elements[i], value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes element at specified index from collection. Element that was previously at location index+1
     * after this operation is on location index, etc. Legal indexes are 0 to size-1. In case of invalid index
     * IndexOutOfBoundsException is thrown.
     * @param index of element to be removed
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException("Index is out of bounds!");
        for(int i = index; i < size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.elements[this.size - 1] = null;
        this.size--;
        this.modificationCount++;
    }

    /**
     * Creates an ElementsGetter that returns objects from the ArrayIndexedCollection
     * in the order that they were added
     * @return ElementsGetter for this ArrayIndexedCollection
     */
    @Override
    public ElementsGetter createElementsGetter() {
        return new ArrayIndexedCollectionElementsGetter(this);
    }

    private static class ArrayIndexedCollectionElementsGetter implements ElementsGetter {

        private final ArrayIndexedCollection col;
        private int currentIndex;
        private final long savedModificationCount;

        public ArrayIndexedCollectionElementsGetter(ArrayIndexedCollection col) {
            this.col = col;
            this.currentIndex = 0;
            this.savedModificationCount = col.modificationCount;
        }

        @Override
        public boolean hasNextElement() {
            if (savedModificationCount != col.modificationCount) throw new ConcurrentModificationException();
            return currentIndex < col.size();
        }

        @Override
        public Object getNextElement() {
            if (savedModificationCount != col.modificationCount) throw new ConcurrentModificationException();
            if (!hasNextElement()) throw new NoSuchElementException();
            return col.elements[currentIndex++];
        }
    }
}
