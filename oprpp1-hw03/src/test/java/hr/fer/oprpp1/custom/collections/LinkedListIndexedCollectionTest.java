package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListIndexedCollectionTest {

    //Default constructor
    @Test
    public void testDefaultConstructorLinkedListIndexedCollection() {
        assertDoesNotThrow(() -> new LinkedListIndexedCollection<>());
    }

    //Second constructor
    @Test
    public void testConstructorFromCollectionLinkedListIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(1);
        aic.add("Test");
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>(aic);
        assertArrayEquals(aic.toArray(), l.toArray());
    }

    @Test
    public void testConstructorFromNullLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>(null);
        assertArrayEquals(new Object[] {}, l.toArray());
    }

    //isEmpty()
    @Test
    public void testIsEmptyLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        assertTrue(l.isEmpty());
    }

    @Test
    public void testIsNotEmptyLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        assertFalse(l.isEmpty());
    }

    //size();
    @Test
    public void testSizeLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        assertEquals(0, l.size());
    }

    @Test
    public void testSizeLinkedListIndexedCollection1() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        assertEquals(1, l.size());
    }

    //add()
    @Test
    public void testAddLinkedListIndexedCollection1() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        assertArrayEquals(new Object[] {"Test"}, l.toArray());
    }

    //addAll()
    @Test
    public void testAddAllToLinkedListIndexedCollection() {
        ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>(3);
        String[] testArr = new String[] {"Test1", "Test2", "Test3"};

        aic.add(testArr[0]);
        aic.add(testArr[1]);
        aic.add(testArr[2]);

        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.addAll(aic);

        assertArrayEquals(testArr, l.toArray());
    }

    @Test
    public void testAddLinkedListIndexedCollection2() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        assertArrayEquals(new Object[] {"Test1", "Test2"}, l.toArray());
    }

    @Test
    public void testAddNullToLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        assertThrows(NullPointerException.class, () -> l.add(null));
    }

    //contains()
    @Test
    public void testContainsInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        assertTrue(l.contains("Test"));
    }

    @Test
    public void testDoesNotContainInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        assertFalse(l.contains("Test2"));
    }

    @Test
    public void testContainsNullInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        assertFalse(l.contains(null));
    }

    //remove(Object value)
    @Test
    public void testRemoveEmptyLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        assertFalse(l.remove("Test"));
    }

    @Test
    public void testRemoveOnlyNodeInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        assertTrue(l.remove("Test"));
        assertArrayEquals(new Object[] {}, l.toArray());
    }

    @Test
    public void testRemoveFirstNodeInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        l.add("Test3");
        assertTrue(l.remove("Test1"));
        assertArrayEquals(new Object[] {"Test2", "Test3"}, l.toArray());
    }

    @Test
    public void testRemoveLastNodeInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        l.add("Test3");
        assertTrue(l.remove("Test3"));
        assertArrayEquals(new Object[] {"Test1", "Test2"}, l.toArray());
    }

    @Test
    public void testRemoveMiddleNodeInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        l.add("Test3");
        assertTrue(l.remove("Test2"));
        assertArrayEquals(new Object[] {"Test1", "Test3"}, l.toArray());
    }

    @Test
    public void testRemoveAbsentNodeInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        l.add("Test3");
        assertFalse(l.remove("Test4"));
        assertArrayEquals(new Object[] {"Test1", "Test2", "Test3"}, l.toArray());
    }

    //toArray()
    @Test
    public void testToArrayLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        l.add("Test3");
        assertArrayEquals(new Object[] {"Test1", "Test2", "Test3"}, l.toArray());
    }

    @Test
    public void testToArrayEmptyLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        assertArrayEquals(new Object[] {}, l.toArray());
    }

    //insert()
    @Test
    public void testInsertToFrontOfLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        l.insert("Test0", 0);
        assertArrayEquals(new Object[] {"Test0", "Test1", "Test2"}, l.toArray());
    }

    @Test
    public void testInsertToEndOfLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        l.insert("Test3", 2);
        assertArrayEquals(new Object[] {"Test1", "Test2", "Test3"}, l.toArray());
    }

    @Test
    public void testInsertToMiddleOfLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test3");
        l.insert("Test2", 1);
        assertArrayEquals(new Object[] {"Test1", "Test2", "Test3"}, l.toArray());
    }

    @Test
    public void testInsertPositionLessThanZeroToLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        assertThrows(IndexOutOfBoundsException.class, () -> l.insert("Test", -1));
    }

    @Test
    public void testInsertPositionGreaterThanSizeToLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        assertThrows(IndexOutOfBoundsException.class, () -> l.insert("Test", 1));
    }

    @Test
    public void testInsertNullToLinkedLIstIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        assertThrows(NullPointerException.class, () -> l.insert(null, 0));
    }

    //indexOf()
    @Test
    public void testIndexOfValueInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        assertEquals(0, l.indexOf("Test1"));
    }

    @Test
    public void testIndexOfAbsentValueInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        assertEquals(-1, l.indexOf("Test3"));
    }

    @Test
    public void testIndexOfNullInLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        assertEquals(-1, l.indexOf(null));
    }

    //get()
    @Test
    public void testGetElementFromLinkedListIndexedCollection1() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        assertEquals("Test", l.get(0));
    }

    @Test
    public void testGetElementFromLinkedListIndexedCollection2() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test1");
        l.add("Test2");
        assertEquals("Test2", l.get(1));
    }

    @Test
    public void testGetElementAtIndexLessThanZeroFromLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> l.get(-1));
    }

    @Test
    public void testGetElementAtIndexGreaterThanSizeFromLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> l.get(1));
    }

    //remove(int index)
    @Test
    public void testRemoveAtIndexFromLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        l.remove(0);
        assertTrue(l.isEmpty());
    }

    @Test
    public void testRemoveAtIndexLessThanZeroFromLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> l.remove(-1));
    }

    @Test
    public void testRemoveAtIndexGreaterThanSizeFromLinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
        l.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> l.remove(1));
    }

    @Test
    public void testCreateElementsGetterForLinkedListIndexedCollection() {
        LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
        l.add(1);
        ElementsGetter<Integer> getter = l.createElementsGetter();
        assertNotEquals(null, getter);
    }

    @Test
    public void testElementsGetterHasNextForLinkedListIndexedCollection() {
        LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
        l.add(1);
        ElementsGetter<Integer> getter = l.createElementsGetter();
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        getter.getNextElement();
        assertFalse(getter.hasNextElement());
    }

    @Test
    public void testElementsGetterGetNextForLinkedListIndexedCollection() {
        LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
        l.add(1);
        assertEquals(1, l.createElementsGetter().getNextElement());
    }

    @Test
    public void testElementsGetterGetNextNoSuchElementsForLinkedListIndexedCollection() {
        LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
        assertThrows(NoSuchElementException.class, () -> l.createElementsGetter().getNextElement());
    }

    @Test
    public void testElementsGetterHasNextConcurrentModificationExceptionForLinkedListIndexedCollection() {
        LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
        l.add(1);
        ElementsGetter<Integer> getter = l.createElementsGetter();
        l.clear();
        assertThrows(ConcurrentModificationException.class, getter::hasNextElement);
    }

    @Test
    public void testElementsGetterGetNextConcurrentModificationExceptionForLinkedListIndexedCollection() {
        LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
        l.add(1);
        ElementsGetter<Integer> getter = l.createElementsGetter();
        l.clear();
        assertThrows(ConcurrentModificationException.class, getter::getNextElement);
    }

    @Test
    public void testMultipleElementsGetterForLinkedListIndexedCollection() {
        LinkedListIndexedCollection<Integer> l = new LinkedListIndexedCollection<>();
        l.add(1);
        l.add(2);
        l.add(3);

        ElementsGetter<Integer> getter1 = l.createElementsGetter();
        ElementsGetter<Integer> getter2 = l.createElementsGetter();

        assertEquals(1, getter1.getNextElement());
        assertEquals(1, getter2.getNextElement());

        assertEquals(2, getter2.getNextElement());
        assertEquals(3, getter2.getNextElement());

        assertEquals(2, getter1.getNextElement());
        assertEquals(3, getter1.getNextElement());
    }
}
