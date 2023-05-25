package hr.fer.zemris.java.gui.layouts;

/**
 * Used when components are added to invalid positions of the CalcLayout
 */
public class CalcLayoutException extends RuntimeException {

    public CalcLayoutException() {
    }

    public CalcLayoutException(String message) {
        super(message);
    }
}
