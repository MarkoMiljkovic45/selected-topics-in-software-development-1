package hr.fer.oprpp1.hw08.jnotepadpp.i18n.impl;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton class of a complete implementation of ILocalizationProvider
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

    /**
     * Current language tag
     */
    private String language;

    /**
     * ResourceBundle used for translation
     */
    private ResourceBundle bundle;

    /**
     * Singleton instance of LocalizationProvider
     */
    private static final LocalizationProvider instance = new LocalizationProvider();

    /**
     * Resource bundle location
     */
    private static final String resourceBundleBaseName = "hr.fer.oprpp1.hw08.jnotepadpp.i18n.impl.translations";

    private LocalizationProvider() {
        setLanguage("en");
    }

    public static LocalizationProvider getInstance() {
        return LocalizationProvider.instance;
    }

    /**
     * Sets the current language
     * @param language the desired language
     */
    public void setLanguage(String language) {
        this.language = language;
        this.bundle = ResourceBundle.getBundle(resourceBundleBaseName, Locale.forLanguageTag(language));
        fire();
    }

    /**
     * @return Current LocalizationProvider language tag
     */
    @Override
    public String getLanguage() {
        return this.language;
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }
}
