package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.i18n.components.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.i18n.components.LJToolBar;
import hr.fer.oprpp1.hw08.jnotepadpp.i18n.components.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.i18n.impl.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.i18n.impl.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.impl.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.MultipleDocumentListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A simple document editing program
 */
public class JNotepadPP  extends JFrame {

    private final FormLocalizationProvider formLocalizationProvider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
    private final MultipleDocumentModel model;
    private String clipboard;
    private JTextArea currentTextArea;
    private Caret currentCaret;
    private Document currentDocument;
    private final JLabel statusBarLength;
    private final JLabel statusBarCaret;
    private volatile boolean stopClock;

    public JNotepadPP() {
        this.formLocalizationProvider.connect();
        this.model = new DefaultMultipleDocumentModel();
        this.clipboard = "";
        this.statusBarLength = new JLabel();
        this.statusBarCaret = new JLabel();
        this.stopClock = false;

        model.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                if (currentModel == null) {
                    setTitle("JNotepad++");
                    return;
                }

                Path filePath = currentModel.getFilePath();

                if (filePath == null)
                    setTitle("(unnamed) - JNotepad++");
                else
                    setTitle(filePath + " - JNotepad++");

                updateCurrentTextArea(currentModel.getTextComponent());
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {
            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {
            }
        });

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(0, 0);
        setSize(800, 800);
        setTitle("JNotepad++");

        addWindowListener(windowListener);

        initGUI();
    }

    /**
     * Sets current textArea and switches listeners
     * @param currentTextArea new active text area
     */
    public void updateCurrentTextArea(JTextArea currentTextArea) {
        if (this.currentTextArea != null) {
            currentCaret.removeChangeListener(caretListener);
            pasteTextAction.setEnabled(false);
            displayStatisticsAction.setEnabled(false);
            statusBarLength.setText("");
            statusBarCaret.setText("");
        }

        if (currentTextArea != null) {
            this.currentTextArea = currentTextArea;
            this.currentCaret = currentTextArea.getCaret();
            this.currentDocument = currentTextArea.getDocument();

            currentCaret.addChangeListener(caretListener);
            pasteTextAction.setEnabled(true);
            displayStatisticsAction.setEnabled(true);
        }

        updateButtons();
        updateStatusBar();
    }

    private void initGUI() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(model.getVisualComponent(), BorderLayout.CENTER);

        createActions();
        createMenus();
        createToolbars();
        createStatusBar();
    }

    private final Action setLanguageEnglish = new LocalizableAction("en", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("en");
        }
    };

    private final Action setLanguageCroatian = new LocalizableAction("hr", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("hr");
        }
    };

    private final Action setLanguageGerman = new LocalizableAction("de", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("de");
        }
    };

    private final Action newDocumentAction = new LocalizableAction("new", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.createNewDocument();
        }
    };

    private final Action openDocumentAction = new LocalizableAction("open", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open file");

            if(fileChooser.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = fileChooser.getSelectedFile();
            Path filePath = fileName.toPath();

            if(!Files.isReadable(filePath)) {
                errorMessage("The file " + fileName.getAbsolutePath() + " doesn't exist!");
                return;
            }

            try {
                model.loadDocument(filePath);
            } catch(Exception ex) {
                errorMessage("Error while reading file: " + fileName.getAbsolutePath());
            }
        }
    };

    private final Action saveDocumentAction = new LocalizableAction("save", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel currentDocument = model.getCurrentDocument();
            Path filePath;

            if (currentDocument.getFilePath() == null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save file");

                if(fileChooser.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                filePath = fileChooser.getSelectedFile().toPath();
            } else {
                filePath = currentDocument.getFilePath();
                currentDocument.setFilePath(null);
            }

            try {
                model.saveDocument(currentDocument, filePath);
            } catch (IllegalArgumentException | IOException exception) {
                errorMessage(exception.getMessage());
            }
        }
    };

    private final Action saveDocumentAsAction = new LocalizableAction("save_as", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            SingleDocumentModel currentDocument = model.getCurrentDocument();
            Path filePath;

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save file as");

            if(fileChooser.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            filePath = fileChooser.getSelectedFile().toPath();

            if (Files.exists(filePath)) {
                int choice = confirmMessage("File " + filePath.getFileName() + " already exists.\nDo you wish to continue?");
                if (choice == JOptionPane.NO_OPTION)
                    return;
            }

            currentDocument.setFilePath(null);

            try {
                model.saveDocument(currentDocument, filePath);
            } catch (IllegalArgumentException | IOException exception) {
                errorMessage(exception.getMessage());
            }
        }
    };

    private final Action closeTabAction = new LocalizableAction("close", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.closeDocument(model.getCurrentDocument());
        }
    };

    private final Action cutTextAction = new LocalizableAction("cut", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dot  = currentCaret.getDot();
            int mark = currentCaret.getMark();

            int length = Math.abs(dot - mark);
            int offset = Math.min(dot, mark);

            try {
                clipboard = currentDocument.getText(offset, length);
                currentDocument.remove(offset, length);
            } catch (BadLocationException ignore) {}
        }
    };

    private final Action copyTextAction = new LocalizableAction("copy", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dot  = currentCaret.getDot();
            int mark = currentCaret.getMark();

            int length = Math.abs(dot - mark);
            int offset = Math.min(dot, mark);

            try {
                clipboard = currentDocument.getText(offset, length);
            } catch (BadLocationException ignore) {}
        }
    };

    private final Action pasteTextAction = new LocalizableAction("paste", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dot  = currentCaret.getDot();

            try {
                currentDocument.insertString(dot, clipboard, null);
            } catch (BadLocationException ignore) {}
        }
    };

    private final Action displayStatisticsAction = new LocalizableAction("statistics", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            int numberOfCharacters = currentDocument.getLength();
            int numberOfNonBlankCharacters = getNumberOfNonBlankCharacters();
            int numberOfLines = getNumberOfLines();

            String message = String.format("Your document has %d characters, %d non-blank characters and %d lines.",
                    numberOfCharacters, numberOfNonBlankCharacters, numberOfLines);

            JOptionPane.showMessageDialog(JNotepadPP.this, message, "Statistics", JOptionPane.INFORMATION_MESSAGE);
        }
    };

    private final Action exitApplicationAction = new LocalizableAction("exit", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            windowListener.windowClosing(null);
        }
    };

    private final ChangeListener caretListener = l -> {
        updateButtons();
        updateStatusBar();
    };

    private final Action toUpperCaseAction = new LocalizableAction("upperCase", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dot  = currentCaret.getDot();
            int mark = currentCaret.getMark();

            int length = Math.abs(dot - mark);
            int offset = Math.min(dot, mark);

            try {
                String selectedText = currentDocument.getText(offset, length);
                currentDocument.remove(offset, length);
                currentDocument.insertString(offset, selectedText.toUpperCase(), null);
            } catch (BadLocationException ignore) {}
        }
    };

    private final Action toLowerCaseAction = new LocalizableAction("lowerCase", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dot  = currentCaret.getDot();
            int mark = currentCaret.getMark();

            int length = Math.abs(dot - mark);
            int offset = Math.min(dot, mark);

            try {
                String selectedText = currentDocument.getText(offset, length);
                currentDocument.remove(offset, length);
                currentDocument.insertString(offset, selectedText.toLowerCase(), null);
            } catch (BadLocationException ignore) {}
        }
    };

    private final Action invertCaseAction = new LocalizableAction("invertCase", formLocalizationProvider) {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dot  = currentCaret.getDot();
            int mark = currentCaret.getMark();

            int length = Math.abs(dot - mark);
            int offset = Math.min(dot, mark);

            try {
                String selectedText = currentDocument.getText(offset, length);
                currentDocument.remove(offset, length);
                currentDocument.insertString(offset, invertCase(selectedText), null);
            } catch (BadLocationException ignore) {}
        }
    };

    private String invertCase(String str) {
        char[] arr = str.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            if (Character.isLowerCase(arr[i])) {
                arr[i] = Character.toUpperCase(arr[i]);
            } else {
                arr[i] = Character.toLowerCase(arr[i]);
            }
        }

        return new String(arr);
    }

    private final WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            for (SingleDocumentModel documentModel: model) {
                if (documentModel.isModified()) {
                    Path filePath = documentModel.getFilePath();
                    String fileName;

                    if (filePath == null)
                        fileName = DefaultMultipleDocumentModel.DEFAULT_FILE_NAME;
                    else
                        fileName = documentModel.getFilePath().getFileName().toString();

                    String message = String.format("The file %s has unsaved changes.\nWould you like to save them?", fileName);

                    int choice = JOptionPane.showConfirmDialog(JNotepadPP.this,
                            message, "You have unsaved files", JOptionPane.YES_NO_CANCEL_OPTION);

                    switch (choice) {
                        case JOptionPane.YES_OPTION -> {
                            Path savePath;

                            if (filePath == null) {
                                JFileChooser fileChooser = new JFileChooser();
                                fileChooser.setDialogTitle("Save file");

                                if(fileChooser.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                                    return;
                                }

                                savePath = fileChooser.getSelectedFile().toPath();
                            } else {
                                savePath = filePath;
                                documentModel.setFilePath(null);
                            }

                            if (Files.exists(savePath)) {
                                int overwrite = confirmMessage("File " + savePath.getFileName() + " already exists.\nDo you wish to continue?");
                                if (overwrite == JOptionPane.NO_OPTION)
                                    return;
                            }

                            try {
                                model.saveDocument(documentModel, savePath);
                            } catch (IllegalArgumentException | IOException exception) {
                                errorMessage(exception.getMessage());
                                return;
                            }
                        }
                        case JOptionPane.NO_OPTION -> {}
                        case JOptionPane.CANCEL_OPTION -> {
                            return;
                        }
                    }
                }
            }

            stopClock = true;
            JNotepadPP.this.dispose();
        }
    };

    private void createActions() {
        newDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control N"));

        openDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control O"));

        saveDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control S"));

        saveDocumentAsAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control alt S"));

        closeTabAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control W"));

        cutTextAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control X"));
        cutTextAction.setEnabled(false);

        copyTextAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control C"));
        copyTextAction.setEnabled(false);

        pasteTextAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control V"));
        pasteTextAction.setEnabled(false);

        displayStatisticsAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control E"));
        displayStatisticsAction.setEnabled(false);

        exitApplicationAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("alt F4"));

        toLowerCaseAction.setEnabled(false);
        toUpperCaseAction.setEnabled(false);
        invertCaseAction.setEnabled(false);
    }

    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();

        LJMenu fileMenu = new LJMenu("file", formLocalizationProvider);
        menuBar.add(fileMenu);

        fileMenu.add(new JMenuItem(newDocumentAction));
        fileMenu.add(new JMenuItem(openDocumentAction));
        fileMenu.add(new JMenuItem(saveDocumentAction));
        fileMenu.add(new JMenuItem(saveDocumentAsAction));
        fileMenu.add(new JMenuItem(closeTabAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(displayStatisticsAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(exitApplicationAction));

        LJMenu editMenu = new LJMenu("edit", formLocalizationProvider);
        menuBar.add(editMenu);

        editMenu.add(new JMenuItem(cutTextAction));
        editMenu.add(new JMenuItem(copyTextAction));
        editMenu.add(new JMenuItem(pasteTextAction));

        LJMenu optionsMenu = new LJMenu("options", formLocalizationProvider);
        menuBar.add(optionsMenu);

        LJMenu languageMenu = new LJMenu("language", formLocalizationProvider);
        ButtonGroup languageGroup = new ButtonGroup();
        optionsMenu.add(languageMenu);

        JRadioButtonMenuItem enBtn = new JRadioButtonMenuItem(setLanguageEnglish);
        languageMenu.add(enBtn);
        languageGroup.add(enBtn);

        JRadioButtonMenuItem hrBtn = new JRadioButtonMenuItem(setLanguageCroatian);
        languageMenu.add(hrBtn);
        languageGroup.add(hrBtn);

        JRadioButtonMenuItem deBtn = new JRadioButtonMenuItem(setLanguageGerman);
        languageMenu.add(deBtn);
        languageGroup.add(deBtn);

        languageGroup.setSelected(enBtn.getModel(), true);

        LJMenu toolsMenu = new LJMenu("tools", formLocalizationProvider);
        menuBar.add(toolsMenu);

        LJMenu changeCaseMenu = new LJMenu("changeCase", formLocalizationProvider);
        toolsMenu.add(changeCaseMenu);

        changeCaseMenu.add(new JMenuItem(toLowerCaseAction));
        changeCaseMenu.add(new JMenuItem(toUpperCaseAction));
        changeCaseMenu.add(new JMenuItem(invertCaseAction));

        setJMenuBar(menuBar);
    }

    public void createToolbars() {
        JToolBar toolBar = new LJToolBar("tools", formLocalizationProvider);
        toolBar.setFloatable(true);

        toolBar.add(new JButton(newDocumentAction));
        toolBar.add(new JButton(openDocumentAction));
        toolBar.add(new JButton(saveDocumentAction));
        toolBar.add(new JButton(saveDocumentAsAction));
        toolBar.add(new JButton(closeTabAction));

        toolBar.addSeparator();

        toolBar.add(new JButton(displayStatisticsAction));

        toolBar.addSeparator();

        toolBar.add(new JButton(exitApplicationAction));

        toolBar.addSeparator();

        toolBar.add(new JButton(cutTextAction));
        toolBar.add(new JButton(copyTextAction));
        toolBar.add(new JButton(pasteTextAction));

        toolBar.addSeparator();

        toolBar.add(new JButton(toLowerCaseAction));
        toolBar.add(new JButton(toUpperCaseAction));
        toolBar.add(new JButton(invertCaseAction));

        getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    private void createStatusBar() {
        JPanel statusPanel = new JPanel(new GridLayout(0, 3));
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        JLabel dateAndTime = new JLabel(dtf.format(LocalDateTime.now()), JLabel.RIGHT);

        Thread clock = new Thread(() -> {
            try {
                while (!stopClock) {
                    Thread.sleep(1000);
                    dateAndTime.setText(dtf.format(LocalDateTime.now()));
                }
            } catch (InterruptedException ignore) {
            }
        });

        clock.start();

        statusPanel.add(statusBarLength);
        statusPanel.add(statusBarCaret);
        statusPanel.add(dateAndTime);

        getContentPane().add(statusPanel, BorderLayout.SOUTH);
    }

    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private int confirmMessage(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Please confirm", JOptionPane.YES_NO_OPTION);
    }

    private int getNumberOfNonBlankCharacters() {
        try {
            return (int) currentDocument.getText(0, currentDocument.getLength()).chars().filter(c -> !Character.isWhitespace(c)).count();
        } catch (BadLocationException ignore) {
            return 0;
        }
    }

    private int getNumberOfLines() {
        try {
            return (int) currentDocument.getText(0, currentDocument.getLength()).chars().filter(c -> c == '\n').count() + 1;
        } catch (BadLocationException ignore) {
            return 0;
        }
    }

    private void updateButtons() {
        int dot = currentCaret.getDot();
        int mark = currentCaret.getMark();

        if (dot == mark) {
            cutTextAction.setEnabled(false);
            copyTextAction.setEnabled(false);
            toLowerCaseAction.setEnabled(false);
            toUpperCaseAction.setEnabled(false);
            invertCaseAction.setEnabled(false);
        } else {
            cutTextAction.setEnabled(true);
            copyTextAction.setEnabled(true);
            toLowerCaseAction.setEnabled(true);
            toUpperCaseAction.setEnabled(true);
            invertCaseAction.setEnabled(true);
        }
    }

    private void updateStatusBar() {
        int dot = currentCaret.getDot();
        int mark = currentCaret.getMark();

        int line = 1;
        int column = 1;

        try {
            String textContent = currentDocument.getText(0, dot);

            for (char c: textContent.toCharArray()) {
                if (c == '\n') {
                    line++;
                    column = 1;
                } else {
                    column++;
                }
            }

        } catch (BadLocationException ignore) {}

        statusBarLength.setText(String.format("length: %d", currentDocument.getLength()));
        statusBarCaret.setText(String.format("Ln: %d Col: %d Sel: %d", line, column, Math.abs(dot - mark)));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }
}
