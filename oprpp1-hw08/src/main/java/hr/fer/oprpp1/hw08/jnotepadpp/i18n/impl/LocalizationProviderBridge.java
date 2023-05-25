package hr.fer.oprpp1.hw08.jnotepadpp.i18n.impl;

import hr.fer.oprpp1.hw08.jnotepadpp.i18n.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.i18n.ILocalizationProvider;

import java.util.Objects;

/**
 * A proxy class for a ILocalizationProvider object
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    /**
     * True if the LocalizationProviderBridge is connected to a component, false otherwise
     */
    private boolean connected;

    /**
     * Current language tag of the LocalizationProviderBridge
     */
    private String currentLanguage;

    /**
     * Parent of this proxy
     */
    private final ILocalizationProvider parent;

    /**
     * Used to subscribe itself to the parent
     */
    private final ILocalizationListener listener;

    /**
     * Constructor that connects this proxy to a parent
     * @param provider Parent ILocalizationProvider object
     */
    public LocalizationProviderBridge(ILocalizationProvider provider) {
        this.parent = provider;
        this.connected = false;

        this.listener = () -> {
            currentLanguage = parent.getLanguage();
            fire();
        };
    }

    /**
     * Disconnects the LocalizationProviderBridge from the parent ILocalizationProvider
     */
    public void disconnect() {
        parent.removeLocalizationListener(listener);
        connected = false;
    }

    /**
     * Connects the LocalizationProviderBridge to the parent ILocalizationProvider
     * <p>
     * Checks if the parent uses the same language as the bridge. If they don't, sets the bridge language
     * to the parent language and notifies all listeners.
     */
    public void connect() {
        if (connected) return;

        parent.addLocalizationListener(listener);

        if (!Objects.equals(currentLanguage, parent.getLanguage()))
            listener.localizationChanged();

        connected = true;
    }

    @Override
    public String getLanguage() {
        return currentLanguage;
    }

    @Override
    public String getString(String key) {
        return parent.getString(key);
    }
}
