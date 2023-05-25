package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

    @Test
    public void testDictionaryConstructor() {
        assertDoesNotThrow(() -> new Dictionary<>());
    }

    @Test
    public void testEmptyDictionary() {
        assertTrue(new Dictionary<>().isEmpty());
    }

    @Test
    public void testNotEmptyDictionary() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("One", 1);

        assertFalse(dict.isEmpty());
    }

    @Test
    public void testSizeDictionary() {
        Dictionary<String, Integer> dict = new Dictionary<>();
        assertEquals(0, dict.size());

        dict.put("One", 1);
        assertEquals(1, dict.size());

        dict.clear();
        assertEquals(0, dict.size());
    }

    @Test
    public void testClearDictionary() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("One", 1);
        dict.put("Two", 2);

        dict.clear();
        assertTrue(dict.isEmpty());
    }

    @Test
    public void testPutDictionary() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        Integer firstPut = dict.put("One", 1);

        assertNull(firstPut);
        assertEquals(1, dict.size());

        Integer secondPut = dict.put("One", 2);

        assertEquals(1, secondPut);
        assertEquals(1, dict.size());
    }

    @Test
    public void testPutNullKeyDictionary() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        assertThrows(NullPointerException.class, () -> dict.put(null, 0));
    }

    @Test
    public void testGetValueDictionary() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("One", 1);
        dict.put("Two", 2);

        assertEquals(1, dict.get("One"));
        assertEquals(2, dict.get("Two"));
    }

    @Test
    public void testGetAbsentValueDictionary() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("One", 1);

        assertNull(dict.get("Two"));
    }

    @Test
    public void testRemoveDictionary() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("One", 1);

        assertEquals(1, dict.remove("One"));
        assertTrue(dict.isEmpty());
    }

    @Test
    public void testRemoveAbsentDictionary() {
        Dictionary<String, Integer> dict = new Dictionary<>();
        assertNull(dict.remove("One"));
    }
}
