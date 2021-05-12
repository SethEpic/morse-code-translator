package com.epic.morse.config;

import com.epic.morse.service.ValidationService;
import com.epic.morse.service.ValidationServiceImpl;

public final class MorseCodeConfig {
    private static final ValidationService validationService = new ValidationServiceImpl();
    private static MorseCodeConfig configInstance = null;
    private String letterSeparator = " ";
    private String wordSeparator = "  ";

    private MorseCodeConfig() {
    }

    public static MorseCodeConfig getInstance() {
        if (configInstance == null) {
            configInstance = new MorseCodeConfig();
        }
        return configInstance;
    }

    public final void setWordSeparator(String wordSeparator) {
        validationService.validateWordSeparator(wordSeparator);
        this.wordSeparator = wordSeparator;
    }

    public final String getWordSeparator() {
        return this.wordSeparator;
    }

    public final void setLetterSeparator(String letterSeparator) {
        validationService.validateLetterSeparator(letterSeparator);
        this.letterSeparator = letterSeparator;
    }

    public final String getLetterSeparator() {
        return this.letterSeparator;
    }
}
