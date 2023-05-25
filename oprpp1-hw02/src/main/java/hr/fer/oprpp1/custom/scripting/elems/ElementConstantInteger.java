package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Used to represent a constant integer expression
 */
public class ElementConstantInteger extends Element {

    /**
     * The integer value of this expression
     */
    private final int value;

    /**
     * The main constructor used to set value
     * @param value of integer constant expression
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Getter method for value
     * @return value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String asText() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementConstantInteger that = (ElementConstantInteger) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
