package hr.fer.oprpp1.hw08.jnotepadpp.i18n.impl;

import hr.fer.oprpp1.hw08.jnotepadpp.i18n.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.i18n.ILocalizationProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements generic functionalities of the ILocalizationProvider
 * <p>
 * The only omitted method is getString(String key)
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    /**
     * List of all subscribed listeners
     */
    private final List<ILocalizationListener> listeners;

    public AbstractLocalizationProvider() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void addLocalizationListener(ILocalizationListener l) {
        listeners.add(l);
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener l) {
        listeners.remove(l);
    }

    /**
     * Notifies all ILocalizationListeners that a localization change occurred
     */
    public void fire() {
        listeners.forEach(ILocalizationListener::localizationChanged);
    }
}
