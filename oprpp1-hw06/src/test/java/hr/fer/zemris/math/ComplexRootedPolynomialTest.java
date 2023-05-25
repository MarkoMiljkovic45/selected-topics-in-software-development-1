package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexRootedPolynomialTest {

    @Test
    public void testComplexRootedPolynomialConstructor() {
        assertDoesNotThrow(() -> new ComplexRootedPolynomial(Complex.ONE, Complex.ONE));
    }

    @Test
    public void testComplexRootedPolynomialApply() {
        /*
         * p(z) = i * (z - 1)
         * z = 1 - i
         *
         * ans = p(z) = i * (1 - i - 1) =
         *     = i * (-i)
         *     = 1
         */

        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
                Complex.IM,
                Complex.ONE
        );

        Complex z = new Complex(1, -1);

        assertEquals(Complex.ONE, crp.apply(z));
    }

    @Test
    public void testComplexRootedPolynomial() {
        /*
         * p(z) = -1 * (z + i) * (z - 1)
         *
         * ans = -1 * (z^2 - z + iz - i) =
         *     = -1 * z^2 + (1 - i) * z + i
         */

        ComplexRootedPolynomial cpr = new ComplexRootedPolynomial(
                Complex.ONE_NEG,
                Complex.IM_NEG,
                Complex.ONE
        );

        ComplexPolynomial ans = new ComplexPolynomial(
                Complex.IM,
                new Complex(1, -1),
                Complex.ONE_NEG
        );

        assertEquals(ans, cpr.toComplexPolynomial());
    }

    @Test
    public void testComplexRootedPolynomialIndexOfClosestRootFor() {
        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
                Complex.ONE,
                Complex.ONE,
                new Complex(1, 1)
        );

        Complex z = new Complex(1, 0.1);

        assertEquals(0, crp.indexOfClosestRootFor(z, 1));
    }

    @Test
    public void testComplexRootedPolynomialIndexOfClosestRootForOutsideThreshold() {
        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
                Complex.ONE,
                Complex.ONE,
                new Complex(1, 1)
        );


        assertEquals(-1, crp.indexOfClosestRootFor(Complex.ONE_NEG, 0.1));
    }
}
