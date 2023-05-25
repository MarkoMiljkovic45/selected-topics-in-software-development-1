package hr.fer.oprpp1.hw08.jnotepadpp.models.impl;

import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.SingleDocumentListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

/**
 * A default implementation of the MultipleDocumentModel
 * <p>
 * Used for modeling application with multiple opened tabbed documents
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

    /**
     * List of all currently opened documents
     */
    private final List<SingleDocumentModel> documents;

    /**
     * List of all event listeners
     */
    private final List<MultipleDocumentListener> listeners;

    /**
     * The currently focused document
     */
    private SingleDocumentModel currentDocument;

    public static final ImageIcon GREEN_FLOPPY = loadIcon("green_floppy.png");
    private static final ImageIcon RED_FLOPPY = loadIcon("red_floppy.png");

    public static final String DEFAULT_FILE_NAME = "unnamed";

    /**
     * Constructor for DefaultMultipleDocumentModel
     */
    public DefaultMultipleDocumentModel() {
        this.documents = new LinkedList<>();
        this.listeners = new ArrayList<>();
        this.currentDocument = null;

        addChangeListener(l -> setCurrentDocument(getSelectedIndex()));
    }

    @Override
    public JComponent getVisualComponent() {
        return this;
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel newModel = new DefaultSingleDocumentModel();
        openDocument(newModel);
        return newModel;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    /**
     * Switches the currently active document to model and notifies all listeners
     * @param model new focused document
     */
    private void setCurrentDocument(SingleDocumentModel model) {
        if (model != null) {
            String title;

            if (model.getFilePath() != null) {
                title = model.getFilePath().getFileName().toString();
            } else {
                title = DEFAULT_FILE_NAME;
            }

            setCurrentDocument(indexOfTab(title));
        } else {
            setCurrentDocument(-1);
        }
    }

    /**
     * Switches the currently active document to model and notifies all listeners
     * @param index of new active document
     */
    private void setCurrentDocument(int index) {
        SingleDocumentModel model;

        if (index != -1) {
            model = getDocument(index);
            setSelectedIndex(index);
            model.getTextComponent().requestFocus();
        } else {
            model = null;
        }

        listeners.forEach(l -> l.currentDocumentChanged(currentDocument, model));
        currentDocument = model;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) throws IOException {
        String textContent;

        try {
            textContent = Files.readString(path);
        } catch (IOException io) {
            throw new IOException("Unable to open file: " + path.getFileName());
        }

        SingleDocumentModel model = new DefaultSingleDocumentModel(path, textContent);
        openDocument(model);
        return model;
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) throws IllegalArgumentException, IOException {
        Path savePath;

        if (newPath == null) {
            savePath = model.getFilePath();
        } else {
            savePath = newPath;
        }

        if (findForPath(savePath) != null) {
            throw new IllegalArgumentException("Trying to save to an already opened document. Please close before saving");
        }

        model.setFilePath(savePath);

        try {
            Files.writeString(savePath, model.getTextComponent().getText());
        } catch (IOException io) {
            throw new IOException("Unable to save file.");
        }

        model.setModified(false);
        setCurrentDocument(model);
    }

    /**
     * Opens a new tab and focuses a new document
     * @param model to be opened
     */
    private void openDocument(SingleDocumentModel model) {
        if (!documents.contains(model) || model.getFilePath() == null) {
            documents.add(model);
            listeners.forEach(l -> l.documentAdded(model));

            createTabbedPane(model);
        } else {
            setCurrentDocument(model);
        }
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        if (model != null) {
            documents.remove(model);
            listeners.forEach(l -> l.documentRemoved(model));

            removeTabAt(getSelectedIndex());

            setCurrentDocument(getSelectedIndex());
        }
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    @Override
    public SingleDocumentModel findForPath(Path path) {
        return documents.stream().filter(model -> Objects.equals(model.getFilePath(), path)).findFirst().orElse(null);
    }

    @Override
    public int getIndexOfDocument(SingleDocumentModel doc) {
        return documents.indexOf(doc);
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documents.iterator();
    }

    /**
     * Loads icon from resources
     *
     * @param fileName name of the icon file
     * @return ImageIcon of the file
     */
    private static ImageIcon loadIcon(String fileName) {
        try (InputStream is = DefaultMultipleDocumentModel.class.getResourceAsStream("/hr/fer/oprpp1/hw08/jnotepadpp/icons/" + fileName)) {
            if (is != null) {
                return new ImageIcon(is.readAllBytes());
            }
        } catch (Exception ignore) {}

        return new ImageIcon();
    }

    /**
     * Helper method for creating tabbed panes from SingleDocumentModels
     *
     * @param model used to create a tabbed pane
     */
    private void createTabbedPane(SingleDocumentModel model) {
        String title;
        String tip;
        Path modelFilePath = model.getFilePath();

        if (modelFilePath == null) {
            title = DEFAULT_FILE_NAME;
            tip = DEFAULT_FILE_NAME;
        } else {
            title = modelFilePath.getFileName().toString();
            tip = modelFilePath.toString();
        }

        model.addSingleDocumentListener(new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                int index = getSelectedIndex();

                if (model.isModified()) {
                    setIconAt(index, RED_FLOPPY);
                } else {
                    setIconAt(index, GREEN_FLOPPY);
                }
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                int index = getSelectedIndex();
                String title;
                String tip;
                Path modelFilePath = model.getFilePath();

                if (modelFilePath == null) {
                    title = DEFAULT_FILE_NAME;
                    tip = DEFAULT_FILE_NAME;
                } else {
                    title = modelFilePath.getFileName().toString();
                    tip = modelFilePath.toString();
                }

                setTitleAt(index, title);
                setToolTipTextAt(index, tip);
            }
        });

        Component component = new JScrollPane(model.getTextComponent());
        addTab(title, GREEN_FLOPPY, component, tip);
        setCurrentDocument(getTabCount() - 1);
    }
}
