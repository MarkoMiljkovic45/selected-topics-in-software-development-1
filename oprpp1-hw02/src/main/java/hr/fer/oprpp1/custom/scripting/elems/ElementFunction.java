package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Used to represent a function expression
 */
public class ElementFunction extends Element {

    /**
     * The name of the function
     */
    private final String name;

    /**
     * The main constructor used to set the name of this function
     * @param name of the function
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    /**
     * Getter method for name
     * @return name
     */
    public String getName() {
        return name;
    }

    @Override
    public String asText() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementFunction that = (ElementFunction) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
