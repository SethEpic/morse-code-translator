package com.epic.morse.config;

import com.epic.morse.service.MorseCode;
import com.epic.morse.service.MorseCodeType;
import com.epic.morse.service.ValidationService;
import com.epic.morse.service.ValidationServiceImpl;

public final class MorseCodeConfig {
    private static final ValidationService validationService = new ValidationServiceImpl();
    private static volatile MorseCodeConfig configInstance = null;
    private String letterSeparator = " ";
    private String wordSeparator = "  ";
    private MorseCodeType morseCodeType = MorseCodeType.INTERNATIONAL;

    private MorseCodeConfig() {
    }

    public static MorseCodeConfig getInstance() {
        if (configInstance == null) {
            synchronized (MorseCodeConfig.class) {
                if (configInstance == null) {
                    configInstance = new MorseCodeConfig();
                }
            }
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

    public final MorseCodeType getMorseCodeType() {
        return this.morseCodeType;
    }

    public final void setMorseCodeType(MorseCodeType morseCodeType) {
        if (this.morseCodeType.equals(morseCodeType)) {
            return;
        }

        this.morseCodeType = morseCodeType;
        MorseCode.setLanguageCaches(morseCodeType);
    }
}
