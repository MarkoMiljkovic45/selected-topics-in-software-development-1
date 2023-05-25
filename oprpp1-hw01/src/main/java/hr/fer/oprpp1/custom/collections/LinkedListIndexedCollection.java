package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * An implementation of linked list-backed collection.
 * Duplicate elements are allowed, storage of null references is not allowed
 */
public class LinkedListIndexedCollection extends Collection {

    /**
     * A helper class that models nodes in a list.
     * It stores a value and keeps references to the previous and next node.
     */
    private static class ListNode {
        public ListNode previous;
        public ListNode next;
        public Object storage;
    }

    /**
     * size - the number of elements currently in the collection
     */
    private int size;
    /**
     * first - a reference to the first ListNode in the list
     */
    private ListNode first;
    /**
     * last - a reference to the last ListNode in the list
     */
    private ListNode last;

    public LinkedListIndexedCollection() {
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    public LinkedListIndexedCollection(Collection other) {
        this();
        if(other != null) addAll(other);
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
     * Adds the given object into this collection at the end of collection; newly added element becomes the
     * element at the biggest index. The method will refuse to add null as element by throwing the
     * NullPointerException.
     * @param value value to be added.
     * @throws NullPointerException if value is null
     */
    @Override
    public void add(Object value) {
        if (value == null) throw new NullPointerException("Storage of null references in LinkedListIndexedCollection is not allowed");

        ListNode newValue = new ListNode();
        newValue.storage = value;

        if (this.first == null) {
            newValue.previous = null;
            newValue.next = null;

            this.first = newValue;
            this.last = newValue;
        } else {
            newValue.previous = this.last;
            newValue.next = null;

            this.last.next = newValue;
            this.last = newValue;
        }
        this.size++;
    }

    @Override
    public boolean contains(Object value) {
        ListNode currentNode = this.first;

        while(currentNode != null) {
            if (Objects.equals(currentNode.storage, value)) {
                return true;
            }
            currentNode = currentNode.next;
        }

        return false;
    }

    @Override
    public boolean remove(Object value) {
        ListNode currentNode = this.first;

        while(currentNode != null) {
            if (Objects.equals(currentNode.storage, value)) {
                if (this.first == this.last) {                          //Removing the one and only node
                    this.first = null;
                    this.last  = null;
                } else if (this.first == currentNode) {                 //Removing the first node
                    this.first = currentNode.next;
                    this.first.previous = null;
                } else if(this.last == currentNode) {                   //Removing the last node
                    this.last = currentNode.previous;
                    this.last.next = null;
                } else {                                                //Removing a middle node
                    currentNode.previous.next = currentNode.next;
                    currentNode.next.previous = currentNode.previous;
                }
                this.size--;
                return true;
            }
            currentNode = currentNode.next;
        }

        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] objArr      = new Object[this.size];
        ListNode currentNode = this.first;

        int i = 0;
        while(currentNode != null) {
            objArr[i++] = currentNode.storage;
            currentNode = currentNode.next;
        }

        return objArr;
    }

    @Override
    public void forEach(Processor processor) {
        ListNode currentNode = this.first;

        while(currentNode != null) {
            processor.process(currentNode.storage);
            currentNode = currentNode.next;
        }
    }

    /**
     * Removes all elements from the collection. Collection “forgets” about current linked list.
     */
    @Override
    public void clear() {
        this.first = null;
        this.last  = null;
    }

    /**
     * Inserts (does not overwrite) the given value at the given position in linked-list. Elements starting from
     * this position are shifted one position. The legal positions are 0 to size. If position is invalid,
     * IndexOutOfBoundsException is thrown. The method will refuse to add null as element by throwing the NullPointerException.
     * The average complexity is O(n).
     * @param value to be inserted
     * @param position at which the value will be inserted
     * @throws IndexOutOfBoundsException if position is less than 0 or greater than size
     * @throws NullPointerException if value is null
     */
    public void insert(Object value, int position) {
        if (value == null) throw new NullPointerException("Storage of null references in LinkedListIndexedCollection is not allowed");
        if (position < 0 || position > this.size) throw new IndexOutOfBoundsException("Position is out of bounds!");

        if (position == this.size) {                    //Inserting to the end of the list <=> Adding the list
            add(value);                                 //Also covers empty list scenario
        } else {
            ListNode newNode = new ListNode();
            newNode.storage = value;

            if (position == 0) {                        //Inserting to the front of the list
                newNode.previous = null;
                newNode.next = this.first;

                this.first.previous = newNode;
                this.first = newNode;
            } else {                                    //Inserting to the middle of the list => O(n)
                ListNode currentNode = this.first;
                int currentPosition  = 0;

                while(currentPosition < position) {
                    currentNode = currentNode.next;
                    currentPosition++;
                }

                currentNode.previous.next = newNode;
                newNode.previous = currentNode.previous;

                currentNode.previous = newNode;
                newNode.next = currentNode;
            }
            this.size++;
        }
    }

    /**
     * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
     * not found. null is valid argument. The equality should be determined using the equals method.
     * The average complexity of this method is O(n).
     * @param value to be searched
     * @return the index of the first occurrence of the given value or -1 if the value is not found.
     */
    public int indexOf(Object value) {
        ListNode currentNode = this.first;
        int index = 0;

        while(currentNode != null) {
            if (Objects.equals(value, currentNode.storage)) {
                return index;
            }
            currentNode = currentNode.next;
            index++;
        }

        return -1;
    }

    /**
     * Returns the object that is stored in linked list at position index. Valid indexes are 0 to size-1. If index is
     * invalid, IndexOutOfBoundsException is thrown.
     * @param index of wanted element
     * @return element at index index
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than size-1
     */
    public Object get(int index) {
        if (index < 0 || index > this.size-1) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");

        ListNode currentNode;

        if (index < size / 2) {
            currentNode      = this.first;
            int currentIndex = 0;

            while(currentIndex < index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
        } else {
            currentNode      = this.last;
            int currentIndex = size - 1;

            while(currentIndex > index) {
                currentNode = currentNode.previous;
                currentIndex--;
            }
        }

        return currentNode.storage;
    }

    /**
     * Removes element at specified index from collection. Element that was previously at location index+1
     * after this operation is on location index, etc. Legal indexes are 0 to size-1. In case of invalid index,
     * IndexOutOfBoundsException is thrown
     * @param index of element to be removed
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than size-1
     */
    public void remove(int index) {
        if (index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");

        if (this.first == this.last) {                      //Removing the one and only element
            this.first = null;
            this.last  = null;
        } else if (index == 0) {                            //Removing the first element
            this.first          = this.first.next;
            this.first.previous = null;
        } else if (index == this.size - 1) {                //Removing the last element
            this.last      = this.last.previous;
            this.last.next = null;
        } else {                                            //Removing a middle element
            ListNode currentNode = this.first;
            int currentIndex     = 0;

            while(currentIndex < index) {
                currentNode = currentNode.next;
                currentIndex++;
            }

            currentNode.previous.next = currentNode.next;
            currentNode.next.previous = currentNode.previous;
        }
        this.size--;
    }
}
