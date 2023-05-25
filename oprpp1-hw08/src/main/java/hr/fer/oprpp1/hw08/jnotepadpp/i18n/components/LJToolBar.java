package hr.fer.oprpp1.hw08.jnotepadpp.i18n.components;

import hr.fer.oprpp1.hw08.jnotepadpp.i18n.ILocalizationProvider;

import javax.swing.*;

/**
 * Localizable version of JToolBar
 */
public class LJToolBar extends JToolBar {

    private final String key;
    private final ILocalizationProvider provider;

    /**
     * Constructor used to initialize the JLabel
     * @param key Used for localization
     * @param provider Localization Provider
     */
    public LJToolBar(String key, ILocalizationProvider provider) {
        this.key = key;
        this.provider = provider;

        provider.addLocalizationListener(this::updateName);

        updateName();
    }

    /**
     * Used to update the name based on the localization
     */
    private void updateName() {
        setName(provider.getString(key));
    }
}
