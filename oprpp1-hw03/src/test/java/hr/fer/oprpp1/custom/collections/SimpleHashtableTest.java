package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashtableTest {

    @Test
    public void testDefaultConstructorSimpleHashtable() {
        assertDoesNotThrow(() -> new SimpleHashtable<String, Integer>());
    }

    @Test
    public void testCapacityConstructorSimpleHashtable() {
        assertDoesNotThrow(() -> new SimpleHashtable<String, Integer>(15));
    }

    @Test
    public void testPutSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertEquals(1, tbl.size());
        assertEquals(1, tbl.get("One"));
    }

    @Test
    public void testPutUpdateOldEntrySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertEquals(1, tbl.put("One", 2));
        assertEquals(2, tbl.get("One"));
    }

    @Test
    public void testPutCollisionSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(1);

        int testSize = 1000;

        for(int i = 0; i < testSize; i++) {
            tbl.put(Integer.toString(i), i);
        }

        assertEquals(testSize, tbl.size());
    }

    @Test
    public void testPutNullKeySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        assertThrows(NullPointerException.class, () -> tbl.put(null, 0));
    }

    @Test
    public void testGetSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertEquals(1, tbl.get("One"));
    }

    @Test
    public void testGetNotPresentEntrySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertNull(tbl.get("Two"));
    }

    @Test
    public void testGetNullKeySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertNull(tbl.get(null));
    }

    @Test
    public void testGetCollisionEntrySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(1);

        tbl.put("One", 1);
        tbl.put("Two", 2);

        assertEquals(2, tbl.get("Two"));
    }

    @Test
    public void testSizeAfterPutSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        assertEquals(0, tbl.size());

        tbl.put("One", 1);

        assertEquals(1, tbl.size());
    }

    @Test
    public void testSizeAfterRemoveSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertEquals(1, tbl.size());

        tbl.remove("One");

        assertEquals(0, tbl.size());
    }

    @Test
    public void testContainsKeySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertTrue(tbl.containsKey("One"));
    }

    @Test
    public void testContainsNullKeySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertFalse(tbl.containsKey(null));
    }

    @Test
    public void testContainsAbsentKeySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertFalse(tbl.containsKey("Two"));
    }

    @Test
    public void testContainsValueSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertTrue(tbl.containsValue(1));
    }

    @Test
    public void testContainsAbsentValueSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertFalse(tbl.containsValue(2));
    }

    @Test
    public void testContainsNullValueSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("Zero", null);

        assertTrue(tbl.containsValue(null));
    }

    @Test
    public void testContainsAbsentNullValueSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertFalse(tbl.containsValue(null));
    }

    @Test
    public void testRemoveSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertEquals(1, tbl.remove("One"));
        assertTrue(tbl.isEmpty());
    }

    @Test
    public void testRemoveAbsentSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertNull(tbl.remove("Two"));
        assertEquals(1, tbl.size());
    }

    @Test
    public void testRemoveNullKeySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertNull(tbl.remove(null));
    }

    @Test
    public void testRemoveCollisionEntrySimpleHashTable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(1);

        tbl.put("One", 1);

        int testSize = 1000;

        for(int i = 0; i < testSize; i++) {
            tbl.put(Integer.toString(i), i);
        }

        for (int i = 0; i < testSize; i++) {
            assertEquals(i, tbl.remove(Integer.toString(i)));
        }

        assertEquals(1, tbl.size());
        assertEquals(1, tbl.get("One"));
    }

    @Test
    public void testIsEmptySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        assertTrue(tbl.isEmpty());
    }

    @Test
    public void testIsNotEmptySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        assertFalse(tbl.isEmpty());
    }

    @Test
    public void testToStringSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        assertEquals("[]", tbl.toString());

        tbl.put("One", 1);

        assertEquals("[One=1]", tbl.toString());

        tbl.put("Two", 2);

        assertEquals("[One=1, Two=2]", tbl.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testToArraySimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);
        SimpleHashtable.TableEntry<String, Integer>[] arr = (SimpleHashtable.TableEntry<String, Integer>[]) new SimpleHashtable.TableEntry[0];

        assertArrayEquals(arr, tbl.toArray());

        tbl.put("One", 1);

        arr = (SimpleHashtable.TableEntry<String, Integer>[]) new SimpleHashtable.TableEntry[1];
        arr[0] = new SimpleHashtable.TableEntry<>("One", 1, null);

        assertArrayEquals(arr, tbl.toArray());

        tbl.put("Two", 2);

        arr = (SimpleHashtable.TableEntry<String, Integer>[]) new SimpleHashtable.TableEntry[2];
        arr[0] = new SimpleHashtable.TableEntry<>("One", 1, null);
        arr[1] = new SimpleHashtable.TableEntry<>("Two", 2, null);

        assertArrayEquals(arr, tbl.toArray());
    }

    @Test
    public void testClearSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(2);

        tbl.put("One", 1);

        tbl.clear();

        assertTrue(tbl.isEmpty());
        assertNull(tbl.get("One"));
    }

    @Test
    public void testIteratorSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(4);

        tbl.put("One", 1);
        tbl.put("Two", 2);

        var iterator = tbl.iterator();

        while(iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        assertTrue(tbl.isEmpty());
    }

    @Test
    public void testIteratorConcurrentModificationExceptionSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(4);

        tbl.put("One", 1);
        tbl.put("Two", 2);

        var iterator = tbl.iterator();

        iterator.next();

        tbl.clear();

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
        assertThrows(ConcurrentModificationException.class, iterator::next);
        assertThrows(ConcurrentModificationException.class, iterator::remove);
    }

    @Test
    public void testIteratorNoSuchElementExceptionSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(4);

        tbl.put("One", 1);

        var iterator = tbl.iterator();

        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testIteratorIllegalStateExceptionSimpleHashtable() {
        SimpleHashtable<String, Integer> tbl = new SimpleHashtable<>(4);

        tbl.put("One", 1);

        var iterator = tbl.iterator();

        iterator.next();
        iterator.remove();

        assertThrows(IllegalStateException.class, iterator::remove);
    }
}
