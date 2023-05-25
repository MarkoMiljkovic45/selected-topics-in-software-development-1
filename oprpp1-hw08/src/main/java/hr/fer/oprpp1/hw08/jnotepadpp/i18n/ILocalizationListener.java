package hr.fer.oprpp1.hw08.jnotepadpp.i18n;

/**
 * A subscriber for a ILocalizationProvider
 */
public interface ILocalizationListener {

    /**
     * Triggered when the i18n language changes
     */
    void localizationChanged();
}
