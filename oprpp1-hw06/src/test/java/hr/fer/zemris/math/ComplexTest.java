package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexTest {

    @Test
    public void testComplexConstructor() {
        assertEquals(new Complex(), new Complex(0, 0));
    }

    @Test
    public void testComplexModule() {
        double re = 3;
        double im = 2;

        assertEquals(Math.sqrt(re * re + im * im), new Complex(re, im).module());
    }

    @Test
    public void testComplexMultiply() {
        Complex z1 = new Complex(2, 3);
        Complex z2 = new Complex(1, 4);

        Complex ans = new Complex(-10 ,11);

        assertEquals(ans, z1.multiply(z2));
    }

    @Test
    public void testComplexDivide() {
        Complex z1 = new Complex(6, 3);
        Complex z2 = new Complex(2, 1);

        Complex ans = new Complex(3 ,0);

        assertEquals(ans, z1.divide(z2));
    }

    @Test
    public void testComplexAdd() {
        Complex z1 = new Complex(2, 3);
        Complex z2 = new Complex(1, 4);

        Complex ans = new Complex(3 ,7);

        assertEquals(ans, z1.add(z2));
    }

    @Test
    public void testComplexSubtract() {
        Complex z1 = new Complex(2, 3);
        Complex z2 = new Complex(1, 4);

        Complex ans = new Complex(1 ,-1);

        assertEquals(ans, z1.sub(z2));
    }

    @Test
    public void testComplexNegate() {
        Complex z1 = new Complex(2, 3);

        Complex ans = new Complex(-2 ,-3);

        assertEquals(ans, z1.negate());
    }

    @Test
    public void testComplexPower() {
        Complex z1 = new Complex(2, 3);

        Complex ans = new Complex(-46 ,9);

        assertEquals(ans, z1.power(3));
    }

    @Test
    public void testComplexPowerException() {
        Complex z1 = new Complex(2, 3);

        assertThrows(IllegalArgumentException.class, () -> z1.power(-1));
    }

    @Test
    public void testComplexRoot() {
        Complex z1 = Complex.ONE;

        Complex[] ans = new Complex[] {
                Complex.ONE,
                Complex.IM,
                Complex.ONE_NEG,
                Complex.IM_NEG
        };

        assertArrayEquals(ans, z1.root(4).toArray(new Complex[0]));
    }

    @Test
    public void testComplexRootException() {
        Complex z1 = new Complex(2, 3);

        assertThrows(IllegalArgumentException.class, () -> z1.root(-1));
    }
}
