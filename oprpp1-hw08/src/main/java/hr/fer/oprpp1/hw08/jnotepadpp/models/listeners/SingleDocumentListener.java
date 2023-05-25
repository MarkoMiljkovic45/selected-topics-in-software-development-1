package hr.fer.oprpp1.hw08.jnotepadpp.models.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

public interface SingleDocumentListener {

    /**
     * Triggers when the model modified attribute changes
     * @param model in which the change occurred
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Triggers when the model path attribute changes
     * @param model in which the change occurred
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
