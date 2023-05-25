package hr.fer.oprpp1.hw08.jnotepadpp.models.impl;

import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of the SingleDocumentModel
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

    /**
     * Path to document
     */
    private Path path;

    /**
     * Used to display and edit the document
     */
    private final JTextArea textArea;

    /**
     * True if the document has been modified, false otherwise
     */
    private boolean modified;

    /**
     * List of all event listeners
     */
    private final List<SingleDocumentListener> listeners;

    /**
     * Constructor for the DefaultSingleDocumentModel
     *
     * @param path to document that this object represents
     * @param textContent the initial content of the JTextArea
     */
    public DefaultSingleDocumentModel(Path path, String textContent) {
        this.path = path;
        this.textArea = new JTextArea(textContent);
        this.modified = false;
        this.listeners = new ArrayList<>();

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setModified(true);
            }
        });
    }

    public DefaultSingleDocumentModel() {
        this(null, "");
    }

    @Override
    public JTextArea getTextComponent() {
        return textArea;
    }

    @Override
    public Path getFilePath() {
        return path;
    }

    @Override
    public void setFilePath(Path path) {
        this.path = path;
        listeners.forEach(l -> l.documentFilePathUpdated(this));
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
        listeners.forEach(l -> l.documentModifyStatusUpdated(this));
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultSingleDocumentModel that = (DefaultSingleDocumentModel) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
