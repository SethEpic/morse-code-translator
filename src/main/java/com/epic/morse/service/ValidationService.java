package com.epic.morse.service;

public interface ValidationService {
    void validateTextToMorseCode(String text);

    void validateMorseCodeToText(String morseCode);

    void validateSeparator(String var1, String separatorName);
}
