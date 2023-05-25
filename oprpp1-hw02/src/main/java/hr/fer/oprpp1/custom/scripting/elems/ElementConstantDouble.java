package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Used to represent a constant double expression
 */
public class ElementConstantDouble extends Element {

    /**
     * The double value of this expression
     */
    private final double value;

    /**
     * The main constructor used to set value
     * @param value of double constant expression
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Getter method for value
     * @return value
     */
    public double getValue() {
        return value;
    }

    @Override
    public String asText() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementConstantDouble that = (ElementConstantDouble) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
