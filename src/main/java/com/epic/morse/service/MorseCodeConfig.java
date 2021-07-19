package com.epic.morse.service;

public final class MorseCodeConfig {

    private static final class Defaults {
        private static final String internationalLetterSeparator_SingleSpace = " ";
        private static final String internationalWordSeparator_DoubleSpace = "  ";
        private static final String americanLetterSeparator_TripleSpace = "/";
        private static final String americanWordSeparator_Pipe = "|";
    }

    private static final MorseCodeConfig configInstance = new MorseCodeConfig();
    private String letterSeparator = Defaults.internationalLetterSeparator_SingleSpace;
    private String wordSeparator = Defaults.internationalWordSeparator_DoubleSpace;
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
        usingDefaultWordSeparator = Defaults.internationalLetterSeparator_SingleSpace.equals(wordSeparator);
        this.wordSeparator = wordSeparator;
        Utils.updateWordRegexCache(wordSeparator);
    }

    public final String getWordSeparator() {
        return this.wordSeparator;
    }

    public final void setLetterSeparator(String letterSeparator) {
        ValidationService.validateLetterSeparator(letterSeparator);
        usingDefaultLetterSeparator = Defaults.internationalWordSeparator_DoubleSpace.equals(letterSeparator);
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
        return Defaults.internationalLetterSeparator_SingleSpace;
    }

    public String getInternationalDefaultWordSeparator() {
        return Defaults.internationalWordSeparator_DoubleSpace;
    }

    public String getAmericanDefaultLetterSeparator() {
        return Defaults.americanLetterSeparator_TripleSpace;
    }

    public String getAmericanDefaultWordSeparator() {
        return Defaults.americanWordSeparator_Pipe;
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
                setWordSeparator(Defaults.americanWordSeparator_Pipe);
                usingDefaultWordSeparator = true;
            }

            if (usingDefaultLetterSeparator || (letterSeparator.isBlank() && letterSeparator.length() < 3)) {
                setLetterSeparator(Defaults.americanLetterSeparator_TripleSpace);
                usingDefaultLetterSeparator = true;
            }
        } else if (MorseCodeType.INTERNATIONAL.equals(type)) {
            if (usingDefaultWordSeparator && Defaults.americanWordSeparator_Pipe.equals(wordSeparator)) {
                setWordSeparator(Defaults.internationalWordSeparator_DoubleSpace);
                usingDefaultWordSeparator = true;
            }

            if (usingDefaultLetterSeparator && Defaults.americanLetterSeparator_TripleSpace.equals(letterSeparator)) {
                setLetterSeparator(Defaults.internationalLetterSeparator_SingleSpace);
                usingDefaultLetterSeparator = true;
            }
        }
    }

    public void reset() {
        this.wordSeparator = Defaults.internationalWordSeparator_DoubleSpace;
        ValidationService.validateWordSeparator(this.wordSeparator);
        Utils.updateWordRegexCache(this.wordSeparator);
        this.usingDefaultWordSeparator = true;

        this.letterSeparator = Defaults.internationalLetterSeparator_SingleSpace;
        ValidationService.validateLetterSeparator(this.letterSeparator);
        Utils.updateLetterRegexCache(this.letterSeparator);
        this.usingDefaultLetterSeparator = true;

        this.morseCodeType = MorseCodeType.INTERNATIONAL;
        MorseCodeSearcher.setLanguageCaches(this.morseCodeType);
    }
}
