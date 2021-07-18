package com.epic.morse.service;

public final class MorseCodeConfig {
    private static final MorseCodeConfig configInstance = new MorseCodeConfig();
    private final String internationalLetterSeparatorDefault_SingleSpace = " ";
    private final String internationalWordSeparatorDefault_DoubleSpace = "  ";
    private final String americanLetterSeparatorDefault_TripleSpace = "/";
    private final String americanWordSeparatorDefault_Pipe = "|";
    private String letterSeparator = internationalLetterSeparatorDefault_SingleSpace;
    private String wordSeparator = internationalWordSeparatorDefault_DoubleSpace;
    private boolean usingDefaultLetterSeparator = true;
    private boolean usingDefaultWordSeparator = true;
    private MorseCodeType morseCodeType = MorseCodeType.INTERNATIONAL;

    private MorseCodeConfig() {
    }

    public static MorseCodeConfig getInstance() {
        return configInstance;
    }

    public final void setWordSeparator(String wordSeparator) {
        ValidationService.validateWordSeparator(wordSeparator);
        usingDefaultWordSeparator = internationalLetterSeparatorDefault_SingleSpace.equals(wordSeparator);
        this.wordSeparator = wordSeparator;
        Utils.updateWordRegexCache(wordSeparator);
    }

    public final String getWordSeparator() {
        return this.wordSeparator;
    }

    public final void setLetterSeparator(String letterSeparator) {
        ValidationService.validateLetterSeparator(letterSeparator);
        usingDefaultLetterSeparator = internationalWordSeparatorDefault_DoubleSpace.equals(letterSeparator);
        this.letterSeparator = letterSeparator;
        Utils.updateLetterRegexCache(letterSeparator);
    }

    public final String getLetterSeparator() {
        return this.letterSeparator;
    }

    public boolean isUsingDefaultWordSeparator() {
        return usingDefaultWordSeparator;
    }

    public boolean isUsingDefaultLetterSeparator() {
        return usingDefaultLetterSeparator;
    }

    public String getInternationalDefaultLetterSeparator() {
        return internationalLetterSeparatorDefault_SingleSpace;
    }

    public String getInternationalDefaultWordSeparator() {
        return internationalWordSeparatorDefault_DoubleSpace;
    }

    public String getAmericanDefaultLetterSeparator() {
        return americanLetterSeparatorDefault_TripleSpace;
    }

    public String getAmericanDefaultWordSeparator() {
        return americanWordSeparatorDefault_Pipe;
    }

    public final MorseCodeType getMorseCodeType() {
        return this.morseCodeType;
    }

    public final void setMorseCodeType(MorseCodeType morseCodeType) {
        if (this.morseCodeType.equals(morseCodeType) || morseCodeType == null) {
            return;
        }

        this.morseCodeType = morseCodeType;
        configureDefaultSeparatorsForType(morseCodeType);
        MorseCodeSearcher.setLanguageCaches(morseCodeType);
    }

    private void configureDefaultSeparatorsForType(MorseCodeType type) {
        if (MorseCodeType.AMERICAN.equals(type)) {
            if (usingDefaultWordSeparator || (wordSeparator.isBlank() && wordSeparator.length() < 3)) {
                setWordSeparator(americanWordSeparatorDefault_Pipe);
                usingDefaultWordSeparator = true;
            }

            if (usingDefaultLetterSeparator || (letterSeparator.isBlank() && letterSeparator.length() < 3)) {
                setLetterSeparator(americanLetterSeparatorDefault_TripleSpace);
                usingDefaultLetterSeparator = true;
            }
        } else if (MorseCodeType.INTERNATIONAL.equals(type)) {
            if (usingDefaultWordSeparator && americanWordSeparatorDefault_Pipe.equals(wordSeparator)) {
                setWordSeparator(internationalWordSeparatorDefault_DoubleSpace);
                usingDefaultWordSeparator = true;
            }

            if (usingDefaultLetterSeparator && americanLetterSeparatorDefault_TripleSpace.equals(letterSeparator)) {
                setLetterSeparator(internationalLetterSeparatorDefault_SingleSpace);
                usingDefaultLetterSeparator = true;
            }
        }
    }
}
