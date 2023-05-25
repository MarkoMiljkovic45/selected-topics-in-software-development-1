package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public interface ElementsGetter<E> {

    /**
     * Tests if there are more elements to return from the collection
     * @return true if there are more elements to return, false otherwise
     * @throws ConcurrentModificationException if the collection has been modified since the creation of this
     * ElementsGetter
     */
    boolean hasNextElement();

    /**
     * Returns an element from a collection. Every element will be returned exactly once in
     * some order.
     * @return an element from a collection
     * @throws NoSuchElementException if there are no more objects to return
     * @throws ConcurrentModificationException if the collection has been modified since the creation of this
     * ElementsGetter
     */
    E getNextElement();

    /**
     * Processes the remaining elements using processor p
     * @param p processor to be used on the remaining elements
     */
    default void processRemaining(Processor<E> p) {
        while (hasNextElement())
            p.process(getNextElement());
    }
}
