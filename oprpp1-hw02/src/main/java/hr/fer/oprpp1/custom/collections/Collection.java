package hr.fer.oprpp1.custom.collections;

/**
 * A collection of Objects
 */
public interface Collection {

    /**
     * Implemented utilizing the method size()
     * @return true if collection contains no objects and false otherwise.
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     *
     * @return the number of currently stored objects in this collection.
     */
    int size();

    /**
     * Adds the given object into this collection.
     * @param value value to be added.
     */
    void add(Object value);

    /**
     * Compares the given object to the objects in the collection as determined by equals method.
     * It is OK to ask if collection contains null.
     * @param value the value to be searched in the collection
     * @return true only if the collection contains given value.
     */
    boolean contains(Object value);

    /**
     * Removes one occurrence of the given value as determined by equals method (in this class it is not specified which one).
     * @param value the value to be removed.
     * @return true only if the collection contains given value as determined by equals method
     */
    boolean remove(Object value);

    /**
     * Allocates new array with size equals to the size of this collection, fills it with collection content and
     * returns the array. This method never returns null.
     * @return an array containing all the elements in this collection
     */
    Object[] toArray();

    /**
     * Method calls processor.process(.) for each element of this collection. The order in which elements
     * will be sent is undefined in this class.
     * @param processor the type of processor
     */
    default void forEach(Processor processor) {
        ElementsGetter getter = createElementsGetter();
        while(getter.hasNextElement())
            processor.process(getter.getNextElement());
    }

    /**
     * Method adds into the current collection all elements from the given collection. This other collection
     * remains unchanged.
     * @param other the collection to be added
     */
    default void addAll(Collection other) {
        other.forEach(this::add);
    }

    /**
     * Removes all elements from this collection.
     */
    void clear();

    /**
     * Factory method for creating an ElementsGetter for a collection
     * @return instance of an ElementsGetter for a collection
     */
    ElementsGetter createElementsGetter();

    /**
     * Adds all elements from col that satisfy the tester condition
     * @param col collection to extract elements from
     * @param tester used to test elements from col
     */
    default void addAllSatisfying(Collection col, Tester tester) {
        ElementsGetter getter = col.createElementsGetter();
        while (getter.hasNextElement()) {
            Object element = getter.getNextElement();
            if (tester.test(element)) {
                add(element);
            }
        }
    }
}
