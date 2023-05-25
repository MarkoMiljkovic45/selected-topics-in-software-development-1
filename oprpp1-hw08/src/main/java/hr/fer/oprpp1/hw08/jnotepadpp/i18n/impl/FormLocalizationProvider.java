package hr.fer.oprpp1.hw08.jnotepadpp.i18n.impl;

import hr.fer.oprpp1.hw08.jnotepadpp.i18n.ILocalizationProvider;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * FormLocalizationProvider is a class derived from LocalizationProviderBridge
 * <p>
 * In its constructor it registers itself as a WindowListener to its JFrame. When frame is opened, it calls connect
 * and when frame is closed, it calls disconnect.
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

    /**
     * Constructor for FromLocalizationProvider
     * <p>
     * Registers itself as a WindowListener to its JFrame; when frame is opened, it calls connect and when
     * frame is closed, it calls disconnect.
     *
     * @param provider the ILocalizationProvider used for localization
     * @param frame that the FormLocalizationProvider subscribes to
     */
    public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
        super(provider);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                connect();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                disconnect();
            }
        });
    }
}
