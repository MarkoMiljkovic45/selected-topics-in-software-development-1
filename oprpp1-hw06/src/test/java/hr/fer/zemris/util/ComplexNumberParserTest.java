package hr.fer.zemris.util;

import hr.fer.zemris.math.Complex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComplexNumberParserTest {

    @Test
    public void testComplexNumberParser1() {
        String input = "1 + i2";
        Complex expected = new Complex(1, 2);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser2() {
        String input = "1 + i";
        Complex expected = new Complex(1, 1);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser3() {
        String input = "1 - i2";
        Complex expected = new Complex(1, -2);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser4() {
        String input = "1 - i";
        Complex expected = new Complex(1, -1);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser5() {
        String input = "-2 + i2";
        Complex expected = new Complex(-2, 2);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser6() {
        String input = "-3 - i";
        Complex expected = new Complex(-3, -1);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser7() {
        String input = "-4   +    i";
        Complex expected = new Complex(-4, 1);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser8() {
        String input = "2    -   i";
        Complex expected = new Complex(2, -1);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser9() {
        String input = "3";
        Complex expected = new Complex(3, 0);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser10() {
        String input = "-2";
        Complex expected = new Complex(-2, 0);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser11() {
        String input = "i2";
        Complex expected = new Complex(0, 2);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser12() {
        String input = "-i3";
        Complex expected = new Complex(0, -3);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser13() {
        String input = "  2 ";
        Complex expected = new Complex(2, 0);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser14() {
        String input = "   -3";
        Complex expected = new Complex(-3, 0);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser15() {
        String input = "  i2";
        Complex expected = new Complex(0, 2);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser16() {
        String input = "   -i";
        Complex expected = new Complex(0, -1);

        assertEquals(expected, ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser17() {
        String input = "";

        assertThrows(IllegalArgumentException.class, () -> ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser18() {
        String input = "1 - sdf i3";

        assertThrows(IllegalArgumentException.class, () -> ComplexNumberParser.parse(input));
    }

    @Test
    public void testComplexNumberParser19() {
        String input = "1 - i2 + i3";

        assertThrows(IllegalArgumentException.class, () -> ComplexNumberParser.parse(input));
    }
}
