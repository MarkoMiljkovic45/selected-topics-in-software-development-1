package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Objects;

/**
 * A node representing a piece of textual data.
 */
public class TextNode extends Node {

    /**
     * Used to store text content of this node
     */
    private final String text;

    /**
     * The main constructor used to set text content
     * @param text content to be stored
     */
    public TextNode(String text) {
        this.text = text;
    }

    /**
     * Getter method for text content of this node
     * @return text content of this node
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextNode textNode = (TextNode) o;
        return Objects.equals(text, textNode.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
