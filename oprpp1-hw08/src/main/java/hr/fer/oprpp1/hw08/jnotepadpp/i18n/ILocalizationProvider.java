package hr.fer.oprpp1.hw08.jnotepadpp.i18n;

/**
 * Used to notify all subscribers when the localization changes and gives them the appropriate translations
 */
public interface ILocalizationProvider {

    /**
     * Subscribes a listener to the ILocalizationProvider
     * @param l listener to be subscribed
     */
    void addLocalizationListener(ILocalizationListener l);

    /**
     * Removes a listener from the ILocalizationProvider
     * @param l listener to be removed
     */
    void removeLocalizationListener(ILocalizationListener l);

    /**
     * @return ILocalizationProvider current language tag
     */
    String getLanguage();

    /**
     * Returns an appropriate translation for key given the currently selected language
     * @param key of the word to be translated
     * @return A translated term
     */
    String getString(String key);
}
