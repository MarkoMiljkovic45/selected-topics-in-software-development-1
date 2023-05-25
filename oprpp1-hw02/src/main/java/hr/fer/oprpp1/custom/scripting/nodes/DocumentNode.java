package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Arrays;
import java.util.Objects;

/**
 * A node representing an entire document.
 */
public class DocumentNode extends Node {

    public DocumentNode() {
    }

    @Override
    public String toString() {
        if (getChildren() == null) return "";

        StringBuilder sb = new StringBuilder();

        for(Node n: getChildren()) {
            sb.append(n.toString());
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        DocumentNode other = (DocumentNode) o;

        return Arrays.equals(getChildren(), other.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }
}
