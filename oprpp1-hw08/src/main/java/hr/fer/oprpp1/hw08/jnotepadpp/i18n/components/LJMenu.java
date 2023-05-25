package hr.fer.oprpp1.hw08.jnotepadpp.i18n.components;

import hr.fer.oprpp1.hw08.jnotepadpp.i18n.ILocalizationProvider;

import javax.swing.*;

/**
 * Localizable version of JMenu
 */
public class LJMenu extends JMenu {

    private final String key;
    private final ILocalizationProvider provider;

    /**
     * Constructor used to initialize the JMenu
     * @param key Used for localization
     * @param provider Localization Provider
     */
    public LJMenu(String key, ILocalizationProvider provider) {
        this.key = key;
        this.provider = provider;

        provider.addLocalizationListener(this::updateLabel);

        updateLabel();
    }

    /**
     * Used to update the label based on the localization
     */
    private void updateLabel() {
        setText(provider.getString(key));
    }
}
