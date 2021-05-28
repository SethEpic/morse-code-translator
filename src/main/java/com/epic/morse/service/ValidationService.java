package com.epic.morse.service;

public interface ValidationService {
    void validateTextToMorseCode(String text);

    void validateMorseCodeToText(String morseCode);

    void validateWordSeparator(String var1);

    void validateLetterSeparator(String var1);

    void validateMorseCodeType(MorseCodeType morseCodeType, boolean isUsingDefaultWordSeparator, boolean isUsingDefaultLetterSeparator);
}
