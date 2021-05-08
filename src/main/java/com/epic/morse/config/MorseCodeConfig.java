package com.epic.morse.config;

import com.epic.morse.service.ValidationService;
import com.epic.morse.service.ValidationServiceImpl;

/**
 * <h1>Morse Code Config Class</h1>
 * This is the class used to configure the word and letter separators which is used to covert morse code to text or text to morse code.
 * It is not required to do so as there are defaults for both.
 * However if you are using an online Morse Code Translator you may want to set the separators to the same as the translators.
 * To use this class Use the {@link #getInstance()}.
 * Only one instance of this class is allowed to prevent multiple instances of the config from being created and messing up the separators.
 * <br>This class is completely optional if you do not set any values the default values will be used.
 *
 * <br> Default word separator = "  " -> (2 spaces)
 * <br> Default letter separator = " " -> (1 space)
 * <pre>
 *     {@code
 *
 *     Example: Setting the separators
 *     MorseCodeConfig config = MorseCodeConfig.getInstance();
 *     config.setWordSeparator("|");
 *     config.setLetterSeparator("*");
 *     }
 * </pre>
 *
 * @author SethEpic
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MorseCodeConfig {
    private static final ValidationService validationService = new ValidationServiceImpl();
    private static MorseCodeConfig configInstance = null;
    private boolean ignoreValidationExceptions = false;
    private boolean isUsingDefaultLetterSeparator = true;
    private boolean isUsingDefaultWordSeparator = true;
    private String letterSeparator = " ";
    private String wordSeparator = "  ";

    private MorseCodeConfig() {
    }

    /**
     * Used to get an the instance of the MorseCodeConfig if it does not exist it will be created here
     * {@return MorseCodeConfig}
     */
    public static MorseCodeConfig getInstance() {
        if (configInstance == null) {
            configInstance = new MorseCodeConfig();
        }
        return configInstance;
    }

    /**
     * Method to set word separator if you do not set one the default word separator will be used
     *
     * @param wordSeparator String that will be used to separate words
     */
    public final void setWordSeparator(String wordSeparator) {
        validationService.validateWordSeparator(wordSeparator);
        this.isUsingDefaultWordSeparator = false;
        this.wordSeparator = wordSeparator;
    }

    public final boolean isUsingDefaultWordSeparator() {
        return this.isUsingDefaultWordSeparator;
    }

    public final String getWordSeparator() {
        return this.wordSeparator;
    }

    /**
     * Method to set letter separator if you do not set one the default letter separator will be used
     *
     * @param letterSeparator String that will be used to separate words
     */
    public final void setLetterSeparator(String letterSeparator) {
        validationService.validateLetterSeparator(letterSeparator);
        this.isUsingDefaultLetterSeparator = false;
        this.letterSeparator = letterSeparator;
    }

    public final String getLetterSeparator() {
        return this.letterSeparator;
    }

    public final boolean isUsingDefaultLetterSeparator() {
        return this.isUsingDefaultLetterSeparator;
    }
}
