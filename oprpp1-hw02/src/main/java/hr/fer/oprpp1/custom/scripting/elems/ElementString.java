package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Used to represent a string expression
 */
public class ElementString extends Element {

    /**
     * Contains the string value of this expression
     */
    private final String value;

    /**
     * The main constructor used to set the value of this string expression
     * @param value string value of this expression
     */
    public ElementString(String value) {
        this.value = value;
    }

    /**
     * Getter method for value
     * @return value
     */
    public String getValue() {
        return value;
    }

    @Override
    public String asText() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementString that = (ElementString) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
