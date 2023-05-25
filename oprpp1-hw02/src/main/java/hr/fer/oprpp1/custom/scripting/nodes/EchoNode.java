package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

import java.util.Arrays;

/**
 * A node representing a command which generates some textual output dynamically.
 */
public class EchoNode extends Node {

    /**
     * Contains all expressions of this echo tag
     */
    private final Element[] elements;

    /**
     * The main constructor used to store all the expressions in this tag
     * @param elements expressions used in tag (in order)
     */
    public EchoNode(Element...elements) {
        this.elements = elements;
    }

    /**
     * Getter method for expression elements
     * @return elements
     */
    public Element[] getElements() {
        return elements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{$= ");

        if (elements != null) {
            for (Element e : elements) {
                sb.append(e.asText()).append(" ");
            }
        }

        sb.append("$}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EchoNode other = (EchoNode) o;
        return Arrays.equals(elements, other.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }
}
