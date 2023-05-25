package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexPolynomialTest {

    @Test
    public void testComplexPolynomialConstructor() {
        assertDoesNotThrow(() -> new ComplexPolynomial(Complex.ONE, Complex.ONE, Complex.ONE));
    }

    @Test
    public void testComplexPolynomialOrder() {
        ComplexPolynomial p = new ComplexPolynomial(Complex.ONE, Complex.ONE, Complex.ONE, Complex.ONE);
        assertEquals(3, p.order());
    }

    @Test
    public void testComplexPolynomialMultiply() {
        /*
         * p1 = 1 + z + z^2
         * p2 = i + iz
         *
         * ans = p1 * p2 = (1 + z + z^2) * (i + iz)
         *     = i + iz +iz + iz^2 + iz^2 + iz^3
         *     = i + 2iz + 2iz^2 + iz^3
         */

        ComplexPolynomial p1 = new ComplexPolynomial(Complex.ONE, Complex.ONE, Complex.ONE);
        ComplexPolynomial p2 = new ComplexPolynomial(Complex.IM, Complex.IM);

        ComplexPolynomial ans = new ComplexPolynomial(
                Complex.IM,
                new Complex(0, 2),
                new Complex(0, 2),
                Complex.IM
        );

        assertEquals(ans, p1.multiply(p2));
    }

    @Test
    public void testComplexPolynomialDerivative() {
        /*
         * p = 1 + iz + z^2
         *
         * ans = p' = i + 2z
         */

        ComplexPolynomial p = new ComplexPolynomial(Complex.ONE, Complex.IM, Complex.ONE);

        ComplexPolynomial ans = new ComplexPolynomial(
                Complex.IM,
                new Complex(2, 0)
        );

        assertEquals(ans, p.derive());
    }

    @Test
    public void testComplexPolynomialApply() {
        /*
         * p(z) = 1 + iz + z^2
         * z = 1 + i
         *
         * ans = p(z) = 1 + i - 1 + (1 + i)^2
         *     = i + 1 + 2i - 1
         *     = 3i
         */

        ComplexPolynomial p = new ComplexPolynomial(Complex.ONE, Complex.IM, Complex.ONE);
        Complex z = new Complex(1, 1);

        Complex ans = new Complex(0, 3);

        assertEquals(ans, p.apply(z));
    }


}
