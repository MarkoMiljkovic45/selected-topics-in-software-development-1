package hr.fer.oprpp1.custom.collections;

public class Collection {

    protected Collection() {
    }

    /**
     * Implemented utilizing the method size()
     * @return true if collection contains no objects and false otherwise.
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     *
     * @return the number of currently stored objects in this collection.
     */
    public int size() {
        return 0;
    }

    /**
     * Adds the given object into this collection.
     * @param value value to be added.
     */
    public void add(Object value) {
    }

    /**
     * Compares the given object to the objects in the collection as determined by equals method.
     * It is OK to ask if collection contains null.
     * @param value the value to be searched in the collection
     * @return true only if the collection contains given value.
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * Removes one occurrence of the given value as determined by equals method (in this class it is not specified which one).
     * @param value the value to be removed.
     * @return true only if the collection contains given value as determined by equals method
     */
    public boolean remove(Object value) {
        return false;
    }

    /**
     * Allocates new array with size equals to the size of this collection, fills it with collection content and
     * returns the array. This method never returns null.
     * @return an array containing all the elements in this collection
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method calls processor.process(.) for each element of this collection. The order in which elements
     * will be sent is undefined in this class.
     * @param processor the type of processor
     */
    public void forEach(Processor processor) {
    }

    /**
     * Method adds into the current collection all elements from the given collection. This other collection
     * remains unchanged.
     * @param other the collection to be added
     */
    public void addAll(Collection other) {
        class AddProcessor extends Processor {
            private AddProcessor() {
            }

            @Override
            public void process(Object value) {
                add(value);
            }
        }

        other.forEach(new AddProcessor());
    }

    /**
     * Removes all elements from this collection.
     */
    public void clear() {
    }
}
