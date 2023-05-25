package hr.fer.oprpp1.hw08.jnotepadpp.models;

import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.MultipleDocumentListener;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
    /**
     * @return The component that should be displayed
     */
    JComponent getVisualComponent();

    /**
     * Creates and adds a new document to the MultipleDocumentModel
     * @return The created SingleDocumentModel
     */
    SingleDocumentModel createNewDocument();

    /**
     * @return The currently focused document
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Loads document from disk
     * @param path to document to load
     * @return SingleDocumentModel of the loaded document
     * @throws NullPointerException if path is <code>null</code>
     * @throws IOException if the file can't be loaded
     */
    SingleDocumentModel loadDocument(Path path) throws IOException;

    /**
     * Saves document to disk using newPath. Also updates the documents path to newPath
     * <p>
     * If newPath is <code>null</code> the document will be saved using its associated path
     *
     * @param model to be saved
     * @param newPath on disk to save to
     * @throws IllegalArgumentException if newPath points to an already opened document
     * @throws IOException if saving to disk fails
     */
    void saveDocument(SingleDocumentModel model, Path newPath) throws IOException;

    /**
     * Removes SingleDocumentModel from MultipleDocumentModel
     * @param model to be removed
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Adds a MultipleDocumentListener from MultipleDocumentModel
     * @param l listener to be added
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Removes a MultipleDocumentLister from MultipleDocumentModel
     * @param l listener to be removed
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * @return The number of documents in MultipleDocumentModel
     */
    int getNumberOfDocuments();

    /**
     * Gets document at index from MultipleDocumentModel
     * @param index of document
     * @return document at index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    SingleDocumentModel getDocument(int index);

    /**
     * Checks if a document with path is present in the MultipleDocumentModel
     * @param path of file to check
     * @return SingleDocumentModel of file if found or null if no such model exits
     */
    SingleDocumentModel findForPath(Path path);

    /**
     * Returns the index of a SingleDocumentModel in the MultipleDocumentModel
     * @param doc to be searched
     * @return The index of a SingleDocumentModel if present, -1 otherwise
     */
    int getIndexOfDocument(SingleDocumentModel doc); //-1 if not present
}
