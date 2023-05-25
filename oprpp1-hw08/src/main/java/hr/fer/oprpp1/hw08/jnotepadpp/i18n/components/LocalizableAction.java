package hr.fer.oprpp1.hw08.jnotepadpp.i18n.components;

import hr.fer.oprpp1.hw08.jnotepadpp.i18n.ILocalizationProvider;

import javax.swing.*;

/**
 * Localizable version of an AbstractAction
 */
public abstract class LocalizableAction extends AbstractAction {

    /**
     * Constructor for LocalizableAction
     * <p>
     * Uses the key and ILocalizationProvider to set the Action.NAME
     * <p>
     * Also registers a listener to the ILocalizationProvider so that the Action.NAME will update each time the
     * localization changes
     *
     * @param key used for translating Action.NAME
     * @param provider ILocalizationProvider used for localization
     */
    public LocalizableAction(String key, ILocalizationProvider provider) {
        putValue(Action.NAME, provider.getString(key));
        provider.addLocalizationListener(() -> putValue(NAME, provider.getString(key)));
    }
}
