package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayIndexCollectionTest {

    //Default constructor
    @Test
    public void testDefaultConstructor() {
        assertDoesNotThrow(() -> new ArrayIndexedCollection<>());
    }

    //Second constructor
    @Test
    public void testConstructorWithInitialCapacityArgument() {
        assertDoesNotThrow(() -> new ArrayIndexedCollection<>(1));
    }

    @Test
    public void testConstructorWithInitialCapacityArgumentIfArgumentLessThanOne() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection<>(0));
    }

    //Third constructor
    @Test
    public void testConstructorThatCopiesCollection() {
        assertDoesNotThrow(() -> new ArrayIndexedCollection<>(new ArrayIndexedCollection<>()));
    }

    @Test
    public void testConstructorThatCopiesCollectionWithNullAsArgument() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<>(null));
    }

    //Fourth constructor
    @Test
    public void testConstructorThatCopiesCollectionAndInitialCapacity() {
        assertDoesNotThrow(() -> new ArrayIndexedCollection<>(new ArrayIndexedCollection<>(1), 2));
    }

    @Test
    public void testConstructorThatCopiesCollectionBiggerThanInitialCapacity() {
        assertDoesNotThrow(() -> new ArrayIndexedCollection<>(new ArrayIndexedCollection<>(2), 1));
    }

    @Test
    public void testConstructorThatCopiesCollectionAndInitialCapacityWithNullArgument() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<>(null, 2));
    }

    //isEmpty()
    @Test
    public void testEmptyArrayIndexedCollection() {
        ArrayIndexedCollection<Integer> aic = new ArrayIndexedCollection<>(1);
        assertTrue(aic.isEmpty());
    }

    @Test
    public void testNotEmptyArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        aic.add("Test");
        assertFalse(aic.isEmpty());
    }

    //size()
    @Test
    public void testArrayIndexedCollectionSize1() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        aic.add("Test");
        assertEquals(1, aic.size());
    }

    @Test
    public void testArrayIndexedCollectionSize2() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        aic.add("Test1");
        aic.add("Test2");
        assertEquals(2, aic.size());
    }

    //add()
    @Test
    public void testAddToArrayIndexedCollection() {
        String[] arr = {"T1", "T2", "T3"};
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(2);

        aic.add(arr[0]);
        aic.add(arr[1]);
        aic.add(arr[2]);

        assertArrayEquals(arr, aic.toArray());
    }

    @Test
    public void testAddNullToArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        assertThrows(NullPointerException.class, () -> aic.add(null));
    }

    //contains()
    @Test
    public void testArrayIndexedCollectionContainsElement() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        aic.add("Test");
        assertTrue(aic.contains("Test"));
    }

    @Test
    public void testArrayIndexedCollectionDoesNotContainElement() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(2);
        aic.add("Test1");
        assertFalse(aic.contains("Test2"));
    }

    @Test
    public void testArrayIndexedCollectionContainsNull() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        assertDoesNotThrow(() -> aic.contains(null));
        assertFalse(aic.contains(null));
    }

    //remove(Object value)
    @Test
    public void testRemoveElementFromArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        aic.add("Test1");
        aic.add("Test2");
        aic.add("Test3");
        assertTrue(aic.remove("Test2"));
        assertArrayEquals(new Object[] {"Test1", "Test3"}, aic.toArray());
    }

    @Test
    public void testRemoveElementNotFromArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        aic.add("Test1");
        assertFalse(aic.remove("Test2"));
    }

    @Test
    public void testRemoveNullFromArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        aic.add("Test");
        assertFalse(aic.remove(null));
    }

    //toArray()
    @Test
    public void testArrayIndexedCollectionToArray() {
        String[] arr = {"Test1", "Test2"};
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(2);

        aic.add(arr[0]);
        aic.add(arr[1]);

        assertArrayEquals(arr, aic.toArray());
    }

    @Test
    public void testEmptyArrayIndexedCollectionToArray() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);

        assertNotEquals(null, aic.toArray());
    }

    //addAll()
    @Test
    public void testAddAllToArrayIndexedCollection() {
        String[] arr = {"T1", "T2", "T3"};
        ArrayIndexedCollection<String> aic1 = new ArrayIndexedCollection<>(3);
        ArrayIndexedCollection<String> aic2 = new ArrayIndexedCollection<>(3);

        aic1.add(arr[0]);
        aic1.add(arr[1]);
        aic1.add(arr[2]);

        aic2.addAll(aic1);

        assertArrayEquals(aic1.toArray(), aic2.toArray());
    }

    //get()
    @Test
    public void testGetFromArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        aic.add("Test");
        assertEquals("Test", aic.get(0));
    }

    @Test
    public void testGetIndexOutOfBoundsFromArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        assertThrows(IndexOutOfBoundsException.class, () -> aic.get(1));
    }

    //clear()
    @Test
    public void testClearArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        aic.add("Test");
        aic.clear();
        assertFalse(aic.contains("Test"));
    }

    //insert()
    @Test
    public void testInsertIntoArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        aic.add("Test1");
        aic.add("Test2");
        aic.insert("Test3", 0);
        assertArrayEquals(new Object[] {"Test3", "Test1", "Test2"}, aic.toArray());
    }

    @Test
    public void testInsertIntoFullArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(2);
        aic.add("Test1");
        aic.add("Test2");
        aic.insert("Test3", 0);
        assertArrayEquals(new Object[] {"Test3", "Test1", "Test2"}, aic.toArray());
    }

    @Test
    public void testInsertOnPositionSizeInArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(2);
        aic.add("Test1");
        aic.add("Test2");
        aic.insert("Test3", 2);
        assertArrayEquals(new Object[] {"Test1", "Test2", "Test3"}, aic.toArray());
    }

    @Test
    public void testInsertNullIntoArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(2);
        aic.add("Test1");
        aic.add("Test2");
        assertThrows(NullPointerException.class, () -> aic.insert(null, 2));
    }

    @Test
    public void testInsertOutOfBoundsArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(2);
        aic.add("Test1");
        aic.add("Test2");
        assertThrows(IndexOutOfBoundsException.class, () -> aic.insert("Test3", 3));
    }

    //indexOf()
    @Test
    public void testIndexOfPresentElementInArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        aic.add("Test1");
        aic.add("Test2");
        aic.add("Test3");
        assertEquals(1, aic.indexOf("Test2"));
    }

    @Test
    public void testIndexOfAbsentElementInArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        aic.add("Test1");
        aic.add("Test2");
        aic.add("Test3");
        assertEquals(-1, aic.indexOf("Test4"));
    }

    @Test
    public void testIndexOfNullInArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        aic.add("Test1");
        assertEquals(-1, aic.indexOf(null));
    }

    @Test
    public void testIndexOfNullInEmptyArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        assertEquals(-1, aic.indexOf(null));
    }

    //remove(int index)
    @Test
    public void testRemoveElementAtIndexFromArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        aic.add("Test1");
        aic.add("Test2");
        aic.add("Test3");
        aic.remove(1);
        assertArrayEquals(new Object[] {"Test1", "Test3"}, aic.toArray());
    }

    @Test
    public void testRemoveElementAtIndexLessThanZeroFromArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        aic.add("Test1");
        assertThrows(IndexOutOfBoundsException.class, () -> aic.remove(-1));
    }

    @Test
    public void testRemoveElementAtIndexGreaterThanSizeFromArrayIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        aic.add("Test1");
        assertThrows(IndexOutOfBoundsException.class, () -> aic.remove(2));
    }

    @Test
    public void testCreateElementsGetterForArrayIndexedCollection() {
        ArrayIndexedCollection<Integer> aic = new ArrayIndexedCollection<>();
        aic.add(1);
        ElementsGetter<Integer> getter = aic.createElementsGetter();
        assertNotEquals(null, getter);
    }

    @Test
    public void testElementsGetterHasNextForArrayIndexedCollection() {
        ArrayIndexedCollection<Integer> aic = new ArrayIndexedCollection<>();
        aic.add(1);
        ElementsGetter<Integer> getter = aic.createElementsGetter();
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        getter.getNextElement();
        assertFalse(getter.hasNextElement());
    }

    @Test
    public void testElementsGetterGetNextForArrayIndexedCollection() {
        ArrayIndexedCollection<Integer> aic = new ArrayIndexedCollection<>();
        aic.add(1);
        assertEquals(1, aic.createElementsGetter().getNextElement());
    }

    @Test
    public void testElementsGetterGetNextNoSuchElementsForArrayIndexedCollection() {
        ArrayIndexedCollection<Integer> aic = new ArrayIndexedCollection<>();
        assertThrows(NoSuchElementException.class, () -> aic.createElementsGetter().getNextElement());
    }

    @Test
    public void testElementsGetterHasNextConcurrentModificationExceptionForArrayIndexedCollection() {
        ArrayIndexedCollection<Integer> aic = new ArrayIndexedCollection<>();
        aic.add(1);
        ElementsGetter<Integer> getter = aic.createElementsGetter();
        aic.clear();
        assertThrows(ConcurrentModificationException.class, getter::hasNextElement);
    }

    @Test
    public void testElementsGetterGetNextConcurrentModificationExceptionForArrayIndexedCollection() {
        ArrayIndexedCollection<Integer> aic = new ArrayIndexedCollection<>();
        aic.add(1);
        ElementsGetter<Integer> getter = aic.createElementsGetter();
        aic.clear();
        assertThrows(ConcurrentModificationException.class, getter::getNextElement);
    }

    @Test
    public void testMultipleElementsGetterForArrayIndexedCollection() {
        ArrayIndexedCollection<Integer> aic = new ArrayIndexedCollection<>();
        aic.add(1);
        aic.add(2);
        aic.add(3);

        ElementsGetter<Integer> getter1 = aic.createElementsGetter();
        ElementsGetter<Integer> getter2 = aic.createElementsGetter();

        assertEquals(1, getter1.getNextElement());
        assertEquals(1, getter2.getNextElement());

        assertEquals(2, getter2.getNextElement());
        assertEquals(3, getter2.getNextElement());

        assertEquals(2, getter1.getNextElement());
        assertEquals(3, getter1.getNextElement());
    }

}
