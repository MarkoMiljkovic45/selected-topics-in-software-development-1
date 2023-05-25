package hr.fer.oprpp1.hw08.jnotepadpp.models;

import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.SingleDocumentListener;

import javax.swing.*;
import java.nio.file.Path;

public interface SingleDocumentModel {

    /**
     * @return The text component of the document
     */
    JTextArea getTextComponent();

    /**
     * @return path of the document
     */
    Path getFilePath();

    /**
     * Sets the path of the document
     * @param path new path to be set
     * @throws NullPointerException if path is <code>null</code>
     */
    void setFilePath(Path path);

    /**
     * @return <code>true</code> if the document has been modified, <code>false</code> otherwise
     */
    boolean isModified();

    /**
     * Sets the modified attribute
     * @param modified new state of modified attribute
     */
    void setModified(boolean modified);

    /**
     * Adds a SingleDocumentListener from SingleDocumentModel
     * @param l listener to be added
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Removes a SingleDocumentListener from SingleDocumentModel
     * @param l listener to be removed
     */
    void removeSingleDocumentListener(SingleDocumentListener l);
}

