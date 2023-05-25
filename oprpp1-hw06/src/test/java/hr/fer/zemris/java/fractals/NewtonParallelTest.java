package hr.fer.zemris.java.fractals;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewtonParallelTest {

    @Test
    public void testGetParams1() {
        String[] args = new String[] {"--workers=2", "--tracks=10"};

        Map<String, Integer> expected = new HashMap<>();
        expected.put("workers", 2);
        expected.put("tracks", 10);

        Map<String, Integer> actual = NewtonParallel.getParams(args);

        for (String key: expected.keySet()) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    public void testGetParams2() {
        String[] args = new String[] {"--tracks=10", "--workers=2"};

        Map<String, Integer> expected = new HashMap<>();
        expected.put("workers", 2);
        expected.put("tracks", 10);

        Map<String, Integer> actual = NewtonParallel.getParams(args);

        for (String key: expected.keySet()) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    public void testGetParams3() {
        String[] args = new String[] {"-w", "2", "-t", "10"};

        Map<String, Integer> expected = new HashMap<>();
        expected.put("workers", 2);
        expected.put("tracks", 10);

        Map<String, Integer> actual = NewtonParallel.getParams(args);

        for (String key: expected.keySet()) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    public void testGetParams4() {
        String[] args = new String[] {"-t", "10", "-w", "2"};

        Map<String, Integer> expected = new HashMap<>();
        expected.put("workers", 2);
        expected.put("tracks", 10);

        Map<String, Integer> actual = NewtonParallel.getParams(args);

        for (String key: expected.keySet()) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    public void testGetParams5() {
        String[] args = new String[] {"--workers=2", "-t", "10"};

        Map<String, Integer> expected = new HashMap<>();
        expected.put("workers", 2);
        expected.put("tracks", 10);

        Map<String, Integer> actual = NewtonParallel.getParams(args);

        for (String key: expected.keySet()) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    public void testGetParams6() {
        String[] args = new String[] {"-w", "2", "--tracks=10"};

        Map<String, Integer> expected = new HashMap<>();
        expected.put("workers", 2);
        expected.put("tracks", 10);

        Map<String, Integer> actual = NewtonParallel.getParams(args);

        for (String key: expected.keySet()) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    public void testGetParams7() {
        String[] args = new String[] {"--workers=2", "-w", "2"};

        assertThrows(IllegalArgumentException.class, () -> NewtonParallel.getParams(args));
    }

    @Test
    public void testGetParams8() {
        String[] args = new String[] {"--workers=0"};

        assertThrows(IllegalArgumentException.class, () -> NewtonParallel.getParams(args));
    }

    @Test
    public void testGetParams9() {
        String[] args = new String[] {"--workers=ab"};

        assertThrows(NumberFormatException.class, () -> NewtonParallel.getParams(args));
    }

    @Test
    public void testGetParams10() {
        String[] args = new String[] {"--workers="};

        assertThrows(IndexOutOfBoundsException.class, () -> NewtonParallel.getParams(args));
    }
}
