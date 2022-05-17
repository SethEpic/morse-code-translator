package com.epic.morse.service;

public final class MorseCodeConfig {

    private static final class MorseCodeConfigDefaults {
        private static final String internationalLetterSeparator = " ";
        private static final String internationalWordSeparator = "  ";
        private static final String americanLetterSeparator = "/";
        private static final String americanWordSeparator = "|";
    }

    private static final MorseCodeConfig configInstance = new MorseCodeConfig();
    private String letterSeparator = MorseCodeConfigDefaults.internationalLetterSeparator;
    private String wordSeparator = MorseCodeConfigDefaults.internationalWordSeparator;
    private boolean usingDefaultLetterSeparator = true;
    private boolean usingDefaultWordSeparator = true;
    private MorseCodeType morseCodeType = MorseCodeType.INTERNATIONAL;

    private MorseCodeConfig() {
    }

    public static MorseCodeConfig getInstance() {
        return configInstance;
    }

    public void setWordSeparator(String wordSeparator) {
        if (this.wordSeparator.equals(wordSeparator)) return;

        ValidationService.validateWordSeparator(wordSeparator);

        if (morseCodeType.isInternational()) {
            usingDefaultWordSeparator = MorseCodeConfigDefaults.internationalWordSeparator.equals(wordSeparator);
        } else {
            usingDefaultWordSeparator = MorseCodeConfigDefaults.americanWordSeparator.equals(wordSeparator);
        }

        this.wordSeparator = wordSeparator;
        RegexUtils.updateWordRegexCache(wordSeparator, morseCodeType);
    }

    public String getWordSeparator() {
        return wordSeparator;
    }

    public void setLetterSeparator(String letterSeparator) {
        if (this.letterSeparator.equals(letterSeparator)) return;

        ValidationService.validateLetterSeparator(letterSeparator);

        if (morseCodeType.isInternational()) {
            usingDefaultLetterSeparator = MorseCodeConfigDefaults.internationalLetterSeparator.equals(letterSeparator);
        } else {
            usingDefaultLetterSeparator = MorseCodeConfigDefaults.americanLetterSeparator.equals(letterSeparator);
        }

        this.letterSeparator = letterSeparator;
        RegexUtils.updateLetterRegexCache(letterSeparator, morseCodeType);
    }

    public String getLetterSeparator() {
        return letterSeparator;
    }

    public boolean isUsingDefaultWordSeparator() {
        return usingDefaultWordSeparator;
    }

    public boolean isUsingDefaultLetterSeparator() {
        return usingDefaultLetterSeparator;
    }

    public String getInternationalDefaultLetterSeparator() {
        return MorseCodeConfigDefaults.internationalLetterSeparator;
    }

    public String getInternationalDefaultWordSeparator() {
        return MorseCodeConfigDefaults.internationalWordSeparator;
    }

    public String getAmericanDefaultLetterSeparator() {
        return MorseCodeConfigDefaults.americanLetterSeparator;
    }

    public String getAmericanDefaultWordSeparator() {
        return MorseCodeConfigDefaults.americanWordSeparator;
    }

    public MorseCodeType getMorseCodeType() {
        return morseCodeType;
    }

    /**
     * Call this method before setting word or letter separators
     * When you set the morse code type it will reset the word and letter separators
     */
    public void setMorseCodeType(MorseCodeType morseCodeType) {
        if (this.morseCodeType.equals(morseCodeType)) return;

        this.morseCodeType = morseCodeType;
        resetWordSeparator();
        resetLetterSeparator();
        MorseCodeSearcher.setLanguageCaches(morseCodeType);
        RegexUtils.resetInvalidMorseCodeRegex(morseCodeType);
    }

    public void updateConfig(MorseCodeType morseCodeType, String letterSeparator, String wordSeparator) {
        setMorseCodeType(morseCodeType);
        setLetterSeparator(letterSeparator);
        setWordSeparator(wordSeparator);
    }

    public void reset() {
        resetMorseCodeType();
        resetWordSeparator();
        resetLetterSeparator();
    }

    public void resetWordSeparator() {
        usingDefaultWordSeparator = true;
        wordSeparator = morseCodeType.isInternational() ?
            MorseCodeConfigDefaults.internationalWordSeparator : MorseCodeConfigDefaults.americanWordSeparator;
        ValidationService.updateWordSeparator(wordSeparator);
        RegexUtils.updateWordRegexCache(wordSeparator, morseCodeType);
    }

    public void resetLetterSeparator() {
        usingDefaultLetterSeparator = true;
        letterSeparator = morseCodeType.isInternational() ?
            MorseCodeConfigDefaults.internationalLetterSeparator : MorseCodeConfigDefaults.americanLetterSeparator;
        ValidationService.updateLetterSeparator(letterSeparator);
        RegexUtils.updateLetterRegexCache(letterSeparator, morseCodeType);
    }

    public void resetMorseCodeType() {
        morseCodeType = MorseCodeType.INTERNATIONAL;
        MorseCodeSearcher.setLanguageCaches(morseCodeType);
        RegexUtils.resetInvalidMorseCodeRegex(morseCodeType);
    }
}
