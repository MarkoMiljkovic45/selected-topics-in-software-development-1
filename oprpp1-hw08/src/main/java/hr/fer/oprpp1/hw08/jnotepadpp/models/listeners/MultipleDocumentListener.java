package hr.fer.oprpp1.hw08.jnotepadpp.models.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

public interface MultipleDocumentListener {

    /**
     * Triggered when the user changes switches documents.
     * <p>
     * previousModel or currentModel can be <code>null</code> but not both.
     * <p>
     * Here the term “current” document means the one that the user is currently interacting with.
     * <p>
     * You can also think about it as the “active” document.
     *
     * @param previousModel the previous currentModel
     * @param currentModel the active document
     * @throws NullPointerException if both previousModel and currentModel are <code>null</code>
     */
    void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

    /**
     * Triggered when a model is added
     * @param model the new model
     */
    void documentAdded(SingleDocumentModel model);

    /**
     * Triggered when a model is removed
     * @param model the removed model
     */
    void documentRemoved(SingleDocumentModel model);
}
