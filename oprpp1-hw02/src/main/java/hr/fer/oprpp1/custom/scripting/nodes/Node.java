package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes
 */
public class Node {

    /**
     * Internally managed collection of direct children of this node
     */
    private ArrayIndexedCollection children;

    public Node() {
        this.children = null;
    }

    /**
     * Adds given child to an internally managed collection of children
     * @param child to be added
     */
    public void addChildNode(Node child) {
        if (children == null) children = new ArrayIndexedCollection();
        children.add(child);
    }

    /**
     * Returns a number of (direct) children
     * @return number of (direct) children
     */
    public int numberOfChildren() {
        if (children == null) {
            return 0;
        } else {
            return children.size();
        }
    }

    /**
     * Returns child at index
     * @param index of child to be returned
     * @return child at index
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than or equal to numberOfChildren()
     */
    public Node getChild(int index) {
        if (numberOfChildren() == 0) throw new IndexOutOfBoundsException();
        return (Node) children.get(index);
    }

    /**
     * Getter method for children array
     * @return an ordered array of node children
     */
    public Node[] getChildren() {
        if (children == null) return null;

        int length = children.size();
        Node[] childrenArr = new Node[length];

        for (int i = 0; i < length; i++) {
            childrenArr[i] = (Node) children.get(i);
        }

        return childrenArr;
    }
}
